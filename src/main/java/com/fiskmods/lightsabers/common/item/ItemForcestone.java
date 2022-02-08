package com.fiskmods.lightsabers.common.item;

import com.fiskmods.lightsabers.Lightsabers;
import com.fiskmods.lightsabers.common.block.BlockForcestone;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.item.ItemMultiTexture;
import net.minecraft.item.ItemStack;

public class ItemForcestone extends ItemMultiTexture
{
    public ItemForcestone(Block block)
    {
        super(block, block, BlockForcestone.NAMES);
        setCreativeTab(Lightsabers.CREATIVE_TAB);

        ModItems.ITEMS.add(this);
    }

//    @Override //TODO
//    @SideOnly(Side.CLIENT)
//    public IIcon getIconFromDamage(int metadata)
//    {
////        return super.getIconFromDamage(metadata > 2 ? metadata + 3 : metadata);
//        return super.getIconFromDamage(metadata);
//    }

    @Override
    public int getMetadata(int metadata)
    {
//        return metadata > 2 ? metadata + 2 : metadata;
        return metadata;
    }
    
//    @Override //TODO
//    public String getUnlocalizedName(ItemStack itemstack)
//    {
//        int i = itemstack.getItemDamage();
//        
//        if (i > 2)
//        {
//            i -= 2;
//        }
//
//        if (i < 0 || i >= nameFunction.length)
//        {
//            i = 0;
//        }
//
//        return super.getUnlocalizedName() + "." + field_150942_c[i];
//    }
}
