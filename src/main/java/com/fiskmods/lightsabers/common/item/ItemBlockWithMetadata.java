package com.fiskmods.lightsabers.common.item;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;

public class ItemBlockWithMetadata extends ItemBlock
{
    private Block block;

    public ItemBlockWithMetadata(Block block)
    {
        super(block);
        this.block = block;
        setMaxDamage(0);
        setHasSubtypes(true);
    }

    /**
     * Gets an icon index based on an item's damage value
     */
//    @Override //TODO
//    @SideOnly(Side.CLIENT)
//    public IIcon getIconFromDamage(int damage)
//    {
//        return block.getIcon(2, damage);
//    }

    /**
     * Returns the metadata of the block which this Item (ItemBlock) can place
     */
    @Override
    public int getMetadata(int meta)
    {
        return meta;
    }
}
