package com.fiskmods.lightsabers.common.config;

import net.minecraftforge.common.config.Configuration;

public class ModConfig {

	public static String CATEGORY_DYNAMIC_LIGHTS;
	public static String CATEGORY_RENDERING;
	public static Configuration configFile;
	public static int renderGlobalMultiplier;
	public static int renderWidthMultiplier;
	public static int renderSmoothingMultiplier;
	public static int renderOpacityMultiplier;
	public static boolean enableShaders;

	public static void load(Configuration config) {
		ModConfig.configFile = config;
		//TODO
		
	}

}
