package com.fiskmods.lightsabers;

import com.fiskmods.lightsabers.client.gui.GuiHandlerAL;
import com.fiskmods.lightsabers.common.command.CommandForce;
import com.fiskmods.lightsabers.common.config.ModConfig;
import com.fiskmods.lightsabers.common.generator.WorldGeneratorStructures;
import com.fiskmods.lightsabers.common.item.ModItems;
import com.fiskmods.lightsabers.common.network.ALNetworkManager;
import com.fiskmods.lightsabers.common.proxy.CommonProxy;

import com.fiskmods.lightsabers.handlers.RegistryHandler;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.common.registry.GameRegistry;
import fiskfille.utils.FiskUtils;
import fiskfille.utils.FiskUtils.Hook;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.config.Configuration;

@Mod(modid = Lightsabers.MODID, name = Lightsabers.NAME, version = Lightsabers.VERSION, dependencies = "required-after:forge@[14.23.0.2486,)", guiFactory = "com.fiskmods.lightsabers.client.gui.GuiFactoryAL")
public class Lightsabers //;after: + ALConstants.BATTLEGEAR + ";after:" + ALConstants.DYNAMIC_LIGHTS
{
    public static final String NAME = "Advanced Lightsabers";
    public static final String MODID = "lightsabers";
    public static final String VERSION = "${version}";

    @Instance
    public static Lightsabers instance;

    public static SimpleNetworkWrapper network;

    @SidedProxy(clientSide = "com.fiskmods.lightsabers.common.proxy.ClientProxy", serverSide = "com.fiskmods.lightsabers.common.proxy.CommonProxy")
    public static CommonProxy proxy;

    public static final CreativeTabs CREATIVE_TAB = new CreativeTabs(MODID)
    {
        @Override
        public ItemStack createIcon()
        {
            return new ItemStack(ModItems.lightsaber);
        }
    };

    public static boolean isBattlegearLoaded;
    public static boolean isDynamicLightsLoaded;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        FiskUtils.hook(Hook.PREINIT);

        isBattlegearLoaded = Loader.isModLoaded(ALConstants.BATTLEGEAR);
        isDynamicLightsLoaded = Loader.isModLoaded(ALConstants.DYNAMIC_LIGHTS);

        Configuration config = new Configuration(event.getSuggestedConfigurationFile());
        config.load();
        ModConfig.load(config);

        if (config.hasChanged())
        {
            config.save();
        }

        RegistryHandler.preInitRegistries(event);

        proxy.preInit();
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        FiskUtils.hook(Hook.INIT);
        RegistryHandler.initRegistries();
        proxy.init();
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
        FiskUtils.hook(Hook.POSTINIT);
    }

    @EventHandler
    public void serverInit(FMLServerStartingEvent event)
    {
        RegistryHandler.serverRegistries(event);
    }
}
