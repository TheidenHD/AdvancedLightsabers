package com.fiskmods.lightsabers.common.proxy;

import com.fiskmods.lightsabers.ALReflection;
import com.fiskmods.lightsabers.Lightsabers;
import com.fiskmods.lightsabers.client.gui.GuiHandlerAL;
import com.fiskmods.lightsabers.client.sound.ALSounds;
import com.fiskmods.lightsabers.common.block.ModBlocks;
import com.fiskmods.lightsabers.common.data.ALData;
import com.fiskmods.lightsabers.common.data.effect.Effect;
import com.fiskmods.lightsabers.common.entity.ModEntities;
import com.fiskmods.lightsabers.common.event.CommonEventHandler;
//import com.fiskmods.lightsabers.common.event.CommonEventHandlerDL;//TODO
import com.fiskmods.lightsabers.common.force.Power;
import com.fiskmods.lightsabers.common.force.PowerData;
import com.fiskmods.lightsabers.common.generator.ModChestGen;
import com.fiskmods.lightsabers.common.generator.WorldGeneratorOres;
import com.fiskmods.lightsabers.common.generator.WorldGeneratorStructures;
import com.fiskmods.lightsabers.common.hilt.HiltManager;
import com.fiskmods.lightsabers.common.interaction.ALInteractions;
import com.fiskmods.lightsabers.common.item.ModItems;
import com.fiskmods.lightsabers.common.lightsaber.LightsaberData;
import com.fiskmods.lightsabers.common.network.ALNetworkManager;
import com.fiskmods.lightsabers.common.recipe.ModRecipes;
import com.fiskmods.lightsabers.common.tileentity.TileEntityCrystal;

import com.google.common.util.concurrent.ListenableFuture;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import fiskfille.utils.helper.NBTHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.MinecraftForge;

public class CommonProxy
{
    public void preInit()
    {
//        ALReflection.common();
//        ALData.init();
//        ALSounds.registerSounds();
//
//        ALNetworkManager.register();
//        HiltManager.register();
//        ALInteractions.register();
//        //ModItems.register();
//        //ModBlocks.register();
//        ModRecipes.register();
//        ModEntities.register();
//        //ModChestGen.register();
//        Effect.register();
//
//        registerSaveAdapters();
//
//        if (Lightsabers.isDynamicLightsLoaded)
//        {
//            //registerEventHandler(new CommonEventHandlerDL());//TODO
//        }
//
        registerEventHandler(new CommonEventHandler());
        GameRegistry.registerWorldGenerator(WorldGeneratorOres.INSTANCE, 0);
        GameRegistry.registerWorldGenerator(WorldGeneratorStructures.INSTANCE, 0);
        NetworkRegistry.INSTANCE.registerGuiHandler(Lightsabers.MODID, new GuiHandlerAL());
        GameRegistry.registerTileEntity(TileEntityCrystal.class,Lightsabers.MODID + ":crystal" );
    }

    public void init()
    {
    }
    
    public void registerSaveAdapters()
    {
        NBTHelper.registerAdapter(Power.class, Power.Adapter.class);
        NBTHelper.registerAdapter(PowerData.class, PowerData.Adapter.class);
        NBTHelper.registerAdapter(PowerData.Container.class, PowerData.Container.Adapter.class);
        NBTHelper.registerAdapter(LightsaberData.class, LightsaberData.Adapter.class);
    }

    public void registerEventHandler(Object obj)
    {
        MinecraftForge.EVENT_BUS.register(obj);
        //MinecraftForge.TERRAIN_GEN_BUS.register(obj);
        //FMLCommonHandler.instance().bus().register(obj);
    }
    
    public Side getSide()
    {
        return Side.SERVER;
    }

    public float getRenderTick()
    {
        return 1;
    }

    public EntityPlayer getPlayer()
    {
        return null;
    }

    public boolean isClientPlayer(EntityLivingBase entity)
    {
        return false;
    }

    public void registerItemRenderer(Item item, String id)
    {

    }

    public void render()
    {

    }
    public ListenableFuture<Object> addScheduledTaskClient(Runnable runnableToSchedule)
    {
        throw new IllegalStateException("This should only be called from client side");
    }

    public EntityPlayer getClientPlayer()
    {
        throw new IllegalStateException("This should only be called from client side");
    }
}
