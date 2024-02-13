package com.esmods.cpapi;

import com.esmods.cpapi.registry.PluginElementTypes;
import com.esmods.cpapi.registry.PluginEventTriggers;
import net.mcreator.plugin.events.ui.ModElementGUIEvent;
import net.mcreator.plugin.events.workspace.MCreatorLoadedEvent;
import net.mcreator.plugin.JavaPlugin;
import net.mcreator.plugin.Plugin;
import net.mcreator.plugin.events.PreGeneratorsLoadingEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.util.HashSet;
import java.util.Set;

public class Launcher extends JavaPlugin {

	public static final Logger LOG = LogManager.getLogger("Colorful Powers API Plugin");
	public static Set<Plugin> PLUGIN_INSTANCE = new HashSet<>();

	public Launcher(Plugin plugin) {
		super(plugin);
		PLUGIN_INSTANCE.add(plugin);
		addListener(PreGeneratorsLoadingEvent.class, event -> PluginElementTypes.load());
		addListener(ModElementGUIEvent.BeforeLoading.class, event -> SwingUtilities.invokeLater(() -> {
			PluginEventTriggers.interceptProcedurePanel(event.getMCreator(), event.getModElementGUI());
		}));
		addListener(MCreatorLoadedEvent.class, event -> {
		});

		LOG.info("Plugin was loaded");
	}



}