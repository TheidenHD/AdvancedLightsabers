package com.fiskmods.lightsabers.handlers;

import com.fiskmods.lightsabers.common.block.BlockCrystal;
import com.fiskmods.lightsabers.common.block.ModBlocks;
import com.fiskmods.lightsabers.common.item.ItemCrystalPouch;
import com.fiskmods.lightsabers.common.item.ModItems;
import com.fiskmods.lightsabers.common.tileentity.TileEntityCrystal;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class ColourHandler {

    @SubscribeEvent
    public static void registerItemColourHandlers(final ColorHandlerEvent.Item event) {
        final ItemColors itemColors = event.getItemColors();

        final IItemColor itemColourHandler = (stack, tintIndex) -> {
            return tintIndex < 1 ? -1 : ((ItemCrystalPouch)stack.getItem()).getColor(stack);
        };

        itemColors.registerItemColorHandler(itemColourHandler, ModItems.crystalPouch);
    }

    @SubscribeEvent
    public static void registerBlockColourHandlers(final ColorHandlerEvent.Block event) {
        final BlockColors blockColors = event.getBlockColors();

        final IBlockColor blockColourHandler = (state, world, pos, tintIndex) -> {
            TileEntity tile = world.getTileEntity(pos);
            return ((TileEntityCrystal) tile).getColor().color;
        };

        blockColors.registerBlockColorHandler(blockColourHandler, ModBlocks.lightsaberCrystal);
    }
}