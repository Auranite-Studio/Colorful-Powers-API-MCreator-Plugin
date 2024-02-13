package com.esmods.cpapi.registry;

import com.esmods.cpapi.parts.JavabridgeReplacement;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javafx.collections.ListChangeListener;
import javafx.concurrent.Worker;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import net.mcreator.io.FileIO;
import net.mcreator.io.OS;
import net.mcreator.io.net.WebIO;
import net.mcreator.plugin.PluginLoader;
import net.mcreator.plugin.PluginUpdateInfo;
import net.mcreator.preferences.PreferencesManager;
import net.mcreator.ui.MCreator;
import net.mcreator.ui.MCreatorApplication;
import net.mcreator.ui.blockly.BlocklyEditorType;
import net.mcreator.ui.blockly.BlocklyPanel;
import net.mcreator.ui.component.util.PanelUtils;
import net.mcreator.ui.component.util.ThreadUtil;
import net.mcreator.ui.dialogs.MCreatorDialog;
import net.mcreator.ui.init.BlocklyJavaScriptsLoader;
import net.mcreator.ui.init.L10N;
import net.mcreator.ui.init.UIRES;
import net.mcreator.ui.laf.themes.Theme;
import net.mcreator.ui.modgui.ModElementGUI;
import net.mcreator.ui.modgui.ProcedureGUI;
import net.mcreator.util.DesktopUtils;
import net.mcreator.util.image.ImageUtils;
import net.mcreator.workspace.elements.VariableTypeLoader;
import com.esmods.cpapi.Launcher;
import com.esmods.cpapi.element.types.GeckolibElement;
import netscape.javascript.JSObject;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
import java.util.List;

public class PluginEventTriggers {

    private static final Set<PluginUpdateInfo> pluginUpdates = new HashSet();

    private static void checkForPluginUpdates() {
        if (MCreatorApplication.isInternet) {
            pluginUpdates.addAll(Launcher.PLUGIN_INSTANCE.parallelStream().map((plugin) -> {
                try {
                    String updateJSON = WebIO.readURLToString(plugin.getInfo().getUpdateJSONURL());
                    JsonObject updateData = JsonParser.parseString(updateJSON).getAsJsonObject().get(plugin.getID()).getAsJsonObject();
                    String version = updateData.get("latest").getAsString();
                    if (!version.equals(plugin.getPluginVersion())) {
                        return new PluginUpdateInfo(plugin, version, updateData.has("changes") ? updateData.get("changes").getAsJsonArray().asList().stream().map(JsonElement::getAsString).toList() : null);
                    }
                } catch (Exception var4) {
                    var4.printStackTrace();
                }

                return null;
            }).filter(Objects::nonNull).toList());
        }

    }

    public static void interceptProcedurePanel(MCreator mcreator, ModElementGUI modElement) {
        if (modElement instanceof ProcedureGUI procedure) {
            ThreadUtil.runOnFxThread(() -> { // Run on FX thread to prevent issues
                try {
                    Field panel = ProcedureGUI.class.getDeclaredField("blocklyPanel");
                    panel.setAccessible(true);
                    BlocklyPanel blocklyPanel = (BlocklyPanel) panel.get(procedure); // Get the blockly panel from the procedure GUI

                    Field loaded = BlocklyPanel.class.getDeclaredField("loaded");
                    loaded.setAccessible(true);
                    loaded.set(blocklyPanel, true); // Prevent the constructor from undoing our changes

                    Field engine = BlocklyPanel.class.getDeclaredField("webEngine"); // Get the webengine field to set it after making the changes
                    engine.setAccessible(true);

                    JavabridgeReplacement javabridge = new JavabridgeReplacement(mcreator, () -> { // Initialize the javabridge class replacement
                        String newXml = (String) blocklyPanel.executeJavaScriptSynchronously("workspaceToXML();");
                        if (newXml.length() > "<xml xmlns=\"https://developers.google.com/blockly/xml\"></xml>".length()) {
                            try {
                                Field xml = BlocklyPanel.class.getDeclaredField("currentXML");
                                xml.setAccessible(true);
                                xml.set(blocklyPanel, newXml);
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                            ThreadUtil.runOnSwingThread(() -> {
                                try {
                                    Field listeners = BlocklyPanel.class.getDeclaredField("changeListeners");
                                    listeners.setAccessible(true);
                                    List<ChangeListener> changeListeners = (List<ChangeListener>) listeners.get(blocklyPanel);
                                    changeListeners.forEach((listener) -> {
                                        listener.stateChanged(new ChangeEvent(blocklyPanel));
                                    });
                                    listeners.set(blocklyPanel, changeListeners);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            });
                            return true;
                        } else {
                            return false;
                        }
                    });

                    WebView browser = new WebView(); // Run the constructor that was intercepted here
                    Scene scene = new Scene(browser);
                    Color bg = Theme.current().getSecondAltBackgroundColor();
                    scene.setFill(javafx.scene.paint.Color.rgb(bg.getRed(), bg.getGreen(), bg.getBlue()));
                    blocklyPanel.setScene(scene);
                    browser.getChildrenUnmodifiable().addListener((ListChangeListener<? super Node>) (change) -> {
                        browser.lookupAll(".scroll-bar").forEach((bar) -> {
                            bar.setVisible(false);
                        });
                    });
                    WebEngine webEngine = browser.getEngine();
                    webEngine.load(blocklyPanel.getClass().getResource("/blockly/blockly.html").toExternalForm());

                    webEngine.getLoadWorker().stateProperty().addListener((ov, oldState, newState) -> {
                        if (newState == Worker.State.SUCCEEDED && webEngine.getDocument() != null) {
                            Element styleNode = webEngine.getDocument().createElement("style");
                            String css = FileIO.readResourceToString("/blockly/css/mcreator_blockly.css");
                            if (PluginLoader.INSTANCE.getResourceAsStream("themes/" + Theme.current().getID() + "/styles/blockly.css") != null) {
                                css = css + FileIO.readResourceToString(PluginLoader.INSTANCE, "/themes/" + Theme.current().getID() + "/styles/blockly.css");
                            } else {
                                css = css + FileIO.readResourceToString(PluginLoader.INSTANCE, "/themes/default_dark/styles/blockly.css");
                            }

                            if ((Boolean) PreferencesManager.PREFERENCES.blockly.transparentBackground.get() && OS.getOS() == 0) {

                                try {
                                    Method method = BlocklyPanel.class.getDeclaredMethod("makeComponentsTransparent", Scene.class);
                                    method.setAccessible(true);
                                    method.invoke(blocklyPanel, scene);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                                css = css + FileIO.readResourceToString("/blockly/css/mcreator_blockly_transparent.css");
                            }

                            if ((Boolean) PreferencesManager.PREFERENCES.blockly.legacyFont.get()) {
                                css = css.replace("font-family: sans-serif;", "");
                            }

                            Text styleContent = webEngine.getDocument().createTextNode(css);
                            styleNode.appendChild(styleContent);
                            webEngine.getDocument().getDocumentElement().getElementsByTagName("head").item(0).appendChild(styleNode);
                            JSObject window = (JSObject) webEngine.executeScript("window");
                            window.setMember("javabridge", javabridge);

                            window.setMember("editorType", BlocklyEditorType.PROCEDURE.registryName());
                            WebEngine var10000 = webEngine;
                            Object var10001 = PreferencesManager.PREFERENCES.blockly.enableComments.get();
                            var10000.executeScript("var MCR_BLOCKLY_PREF = { 'comments' : " + var10001 + ",'renderer' : '" + ((String) PreferencesManager.PREFERENCES.blockly.blockRenderer.get()).toLowerCase(Locale.ENGLISH) + "','collapse' : " + PreferencesManager.PREFERENCES.blockly.enableCollapse.get() + ",'trashcan' : " + PreferencesManager.PREFERENCES.blockly.enableTrashcan.get() + ",'maxScale' : " + (double) (Integer) PreferencesManager.PREFERENCES.blockly.maxScale.get() / 100.0 + ",'minScale' : " + (double) (Integer) PreferencesManager.PREFERENCES.blockly.minScale.get() / 100.0 + ",'scaleSpeed' : " + (double) (Integer) PreferencesManager.PREFERENCES.blockly.scaleSpeed.get() / 100.0 + ",'saturation' :" + (double) (Integer) PreferencesManager.PREFERENCES.blockly.colorSaturation.get() / 100.0 + ",'value' :" + (double) (Integer) PreferencesManager.PREFERENCES.blockly.colorValue.get() / 100.0 + " };");
                            webEngine.executeScript(FileIO.readResourceToString("/jsdist/blockly_compressed.js"));
                            webEngine.executeScript(FileIO.readResourceToString("/jsdist/msg/" + L10N.getBlocklyLangName() + ".js"));
                            webEngine.executeScript(FileIO.readResourceToString("/jsdist/blocks_compressed.js"));
                            webEngine.executeScript(FileIO.readResourceToString("/blockly/js/mcreator_blockly.js"));
                            Iterator var10 = BlocklyJavaScriptsLoader.INSTANCE.getScripts().iterator();

                            while (var10.hasNext()) {
                                String script = (String) var10.next();
                                webEngine.executeScript(script);
                            }

                            webEngine.executeScript(VariableTypeLoader.INSTANCE.getVariableBlocklyJS());

                            try {
                                engine.set(blocklyPanel, webEngine);
                                loaded.set(blocklyPanel, true);
                                Field field = BlocklyPanel.class.getDeclaredField("runAfterLoaded");
                                field.setAccessible(true);
                                List<Runnable> runAfterLoaded = (List<Runnable>) field.get(blocklyPanel);
                                runAfterLoaded.forEach(ThreadUtil::runOnFxThread);

                                javabridge.setJavaScriptEventListener(() -> {
                                    (new Thread(() -> {
                                        try {
                                            Method method = ProcedureGUI.class.getDeclaredMethod("regenerateProcedure");
                                            method.setAccessible(true);
                                            method.invoke(procedure);
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }, "ProcedureRegenerate")).start();
                                });


                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                    });

                    engine.set(blocklyPanel, webEngine); // Replace the webengine with the one initialized here

                    panel.set(procedure, blocklyPanel); // Replace the blocklypanel with the one initialized here
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }
}