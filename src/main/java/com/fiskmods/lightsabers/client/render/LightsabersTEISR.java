package com.fiskmods.lightsabers.client.render;

import com.fiskmods.lightsabers.client.model.tile.ModelCrystal;
import com.fiskmods.lightsabers.common.item.ModItems;
import com.fiskmods.lightsabers.common.tileentity.TileEntityCrystal;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer ;

public class LightsabersTEISR extends TileEntityItemStackRenderer  {

    private final ModelCrystal Crystal_MODEL = new ModelCrystal();
    private final TileEntityCrystal crystal;

    public LightsabersTEISR() {
        crystal = new TileEntityCrystal();
    }

    @Override
    public void renderByItem(ItemStack itemStackIn) {
        Item item = itemStackIn.getItem();
        if (item == ModItems.crystal) {
            this.crystal.setItemValues(itemStackIn);
            TileEntityRendererDispatcher.instance.render(this.crystal, 0.0D, 0.0D, 0.0D, 0.0F);
        }
    }
}
