package com.esmods.cpapi.element.types;

import net.mcreator.element.ModElementType;
import com.esmods.cpapi.ui.modgui.ArtifactGUI;
import static net.mcreator.element.ModElementTypeLoader.register;

public class PluginElementTypes {
    public static ModElementType<?> ARTIFACT;

    public static void load() {

        ARTIFACT = register(
                new ModElementType<>("artifact", (Character) 'A', ArtifactGUI::new, Artifact.class)
        );

    }
}
