package com.fiskmods.lightsabers.common.block;

import com.fiskmods.lightsabers.Lightsabers;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.block.BlockRotatedPillar;
import net.minecraft.block.material.Material;

public class BlockPillar extends BlockRotatedPillar
{
    public BlockPillar()
    {
        super(Material.ROCK);
    }

//    @Override //TODO
//    @SideOnly(Side.CLIENT)
//    protected IIcon getSideIcon(int i)
//    {
//        return blockIcon;
//    }
//
//    @Override
//    public void registerBlockIcons(IIconRegister par1IIconRegister)
//    {
//        field_150164_N = par1IIconRegister.registerIcon(Lightsabers.MODID + ":" + getUnlocalizedName().substring(5) + "_top");
//        blockIcon = par1IIconRegister.registerIcon(Lightsabers.MODID + ":" + getUnlocalizedName().substring(5));
//    }
}
