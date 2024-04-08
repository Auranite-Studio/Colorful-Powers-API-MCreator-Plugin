package com.esmods.cpapi;

import net.mcreator.plugin.JavaPlugin;
import net.mcreator.plugin.Plugin;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Launcher extends JavaPlugin {

	private static final Logger LOG = LogManager.getLogger("Colorful Powers API Plugin");

	public Launcher(Plugin plugin) {
		super(plugin);

		LOG.info("Plugin was loaded");
	}

}