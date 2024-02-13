package com.esmods.cpapi.registry;

import com.esmods.cpapi.element.types.Aftifact;
import com.esmods.cpapi.ui.modgui.ArtifactGUI;
import net.mcreator.element.ModElementType;

import static net.mcreator.element.ModElementTypeLoader.register;

public class PluginElementTypes {
    public static ModElementType<?> ARTIFACT;

    public static void load() {

        ARTIFACT = register(
                new ModElementType<>("artifact", (Character) 'I', ArtifactGUI::new, Aftifact.class)
        );
    }
}
