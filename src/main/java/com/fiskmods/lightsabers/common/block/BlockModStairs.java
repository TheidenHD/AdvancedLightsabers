package com.fiskmods.lightsabers.common.block;

import net.minecraft.block.BlockStairs;
import net.minecraft.block.state.IBlockState;

public class BlockModStairs extends BlockStairs
{
    public BlockModStairs(IBlockState modelState)
    {
        super(modelState);
        useNeighborBrightness = true;
    }
}
