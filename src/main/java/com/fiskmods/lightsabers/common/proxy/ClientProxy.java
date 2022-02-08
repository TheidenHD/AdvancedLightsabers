package com.fiskmods.lightsabers.common.proxy;

import com.fiskmods.lightsabers.ALReflection;
import com.fiskmods.lightsabers.client.gui.GuiOverlay;
import com.fiskmods.lightsabers.client.render.hilt.HiltRendererManager;
//import com.fiskmods.lightsabers.client.render.item.RenderItemCrystal;//TODO
//import com.fiskmods.lightsabers.client.render.item.RenderItemDisassemblyStation;
//import com.fiskmods.lightsabers.client.render.item.RenderItemDoubleLightsaber;
//import com.fiskmods.lightsabers.client.render.item.RenderItemHolocron;
//import com.fiskmods.lightsabers.client.render.item.RenderItemLightsaber;
//import com.fiskmods.lightsabers.client.render.item.RenderItemLightsaberForge;
//import com.fiskmods.lightsabers.client.render.item.RenderItemLightsaberPart;
//import com.fiskmods.lightsabers.client.render.item.RenderItemLightsaberStand;
//import com.fiskmods.lightsabers.client.render.item.RenderItemSithCoffin;
//import com.fiskmods.lightsabers.client.render.item.RenderItemSithStoneCoffin;
import com.fiskmods.lightsabers.client.render.tile.RenderCrystal;
import com.fiskmods.lightsabers.client.render.tile.RenderDisassemblyStation;
import com.fiskmods.lightsabers.client.render.tile.RenderHolocron;
import com.fiskmods.lightsabers.client.render.tile.RenderLightsaberForge;
import com.fiskmods.lightsabers.client.render.tile.RenderLightsaberStand;
import com.fiskmods.lightsabers.client.render.tile.RenderSithCoffin;
import com.fiskmods.lightsabers.client.render.tile.RenderSithStoneCoffin;
import com.fiskmods.lightsabers.common.block.ModBlocks;
import com.fiskmods.lightsabers.common.event.ClientEventHandler;
//import com.fiskmods.lightsabers.common.event.ClientEventHandlerBG;
import com.fiskmods.lightsabers.common.item.ItemCrystal;
import com.fiskmods.lightsabers.common.item.ModItems;
import com.fiskmods.lightsabers.common.keybinds.ALKeyBinds;
import com.fiskmods.lightsabers.common.tileentity.TileEntityCrystal;
import com.fiskmods.lightsabers.common.tileentity.TileEntityDisassemblyStation;
import com.fiskmods.lightsabers.common.tileentity.TileEntityHolocron;
import com.fiskmods.lightsabers.common.tileentity.TileEntityLightsaberForge;
import com.fiskmods.lightsabers.common.tileentity.TileEntityLightsaberStand;
import com.fiskmods.lightsabers.common.tileentity.TileEntitySithCoffin;
import com.fiskmods.lightsabers.common.tileentity.TileEntitySithStoneCoffin;

import com.google.common.util.concurrent.ListenableFuture;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;

import java.util.ArrayList;

public class ClientProxy extends CommonProxy
{
    @Override
    public void preInit()
    {
        super.preInit();
        ALReflection.client();
        ALKeyBinds.register();

        registerEventHandler(new ClientEventHandler());
        registerEventHandler(new GuiOverlay());
//        if (Lightsabers.isBattlegearLoaded)//TODO
//        {
//            BattlegearUtils.RENDER_BUS.register(new ClientEventHandlerBG());
//        }
    }

    @Override
    public void init()
    {
        super.init();
        //.register();
        
//        MinecraftForgeClient.registerItemRenderer(ModItems.lightsaber, new RenderItemLightsaber()); //TODO
//        MinecraftForgeClient.registerItemRenderer(ModItems.doubleLightsaber, new RenderItemDoubleLightsaber());
//        MinecraftForgeClient.registerItemRenderer(ModItems.emitter, new RenderItemLightsaberPart(PartType.EMITTER));
//        MinecraftForgeClient.registerItemRenderer(ModItems.switchSection, new RenderItemLightsaberPart(PartType.SWITCH_SECTION));
//        MinecraftForgeClient.registerItemRenderer(ModItems.grip, new RenderItemLightsaberPart(PartType.BODY));
//        MinecraftForgeClient.registerItemRenderer(ModItems.pommel, new RenderItemLightsaberPart(PartType.POMMEL));
//        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ModBlocks.lightsaberCrystal), new RenderItemCrystal());
//        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ModBlocks.lightsaberForgeLight), new RenderItemLightsaberForge());
//        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ModBlocks.lightsaberForgeDark), new RenderItemLightsaberForge());
//        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ModBlocks.lightsaberStand), RenderItemLightsaberStand.INSTANCE);
//        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ModBlocks.disassemblyStation), RenderItemDisassemblyStation.INSTANCE);
//        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ModBlocks.sithCoffin), new RenderItemSithCoffin());
//        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ModBlocks.sithStoneCoffin), new RenderItemSithStoneCoffin());
//        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ModBlocks.holocron), new RenderItemHolocron());
//
//        RenderingRegistry.registerEntityRenderingHandler(EntityLightsaber.class, new RenderLightsaber());
//        RenderingRegistry.registerEntityRenderingHandler(EntitySithGhost.class, new RenderSithGhost());
//        RenderingRegistry.registerEntityRenderingHandler(EntityForceLightning.class, new RenderForceLightning());
        ClientRegistry.bindTileEntitySpecialRenderer( TileEntityCrystal.class, new RenderCrystal());

    }
    
    @Override
    public Side getSide()
    {
        return Side.CLIENT;
    }

    @Override
    public float getRenderTick()
    {
        return ClientEventHandler.renderTick;
    }

    @Override
    public EntityPlayer getPlayer()
    {
        return Minecraft.getMinecraft().player;
    }

    @Override
    public boolean isClientPlayer(EntityLivingBase entity)
    {
        return entity instanceof EntityPlayer && !(entity instanceof EntityOtherPlayerMP);
    }

    @Override
    public void registerItemRenderer(Item item, String id)
    {
        if(!item.getHasSubtypes()) {
            ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), id));
        } else {
            NonNullList<ItemStack> list = NonNullList.create();
            item.getSubItems(null, list);
            for(ItemStack stack : list) {
                ModelLoader.setCustomModelResourceLocation(item, stack.getItemDamage(), new ModelResourceLocation(item.getRegistryName(), id));
            }
        }

    }

    @SuppressWarnings("deprecation")
    @Override
    public void render()
    {

    }

    @Override
    public ListenableFuture<Object> addScheduledTaskClient(Runnable runnableToSchedule)
    {
        return Minecraft.getMinecraft().addScheduledTask(runnableToSchedule);
    }

    @Override
    public EntityPlayer getClientPlayer()
    {
        return Minecraft.getMinecraft().player;
    }
}
