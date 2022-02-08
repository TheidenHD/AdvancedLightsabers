package com.fiskmods.lightsabers.common.generator.structure;

import net.minecraft.block.Block;
import net.minecraft.block.BlockButton;
import net.minecraft.block.BlockStairs;
import net.minecraft.util.EnumFacing;

public class StructureHelper
{
    public static EnumFacing mirrorMetadata(Block block, EnumFacing metadata)
    {
        if (block instanceof BlockStairs || block instanceof BlockButton)
        {
            return metadata.getOpposite();
        }

        return metadata;
    }

    public static EnumFacing rotateMetadata(Block block, EnumFacing metadata)
    {
        if (block instanceof BlockStairs || block instanceof BlockButton)
        {
        	return metadata.rotateY();
        }
            
        return metadata;
    }
}
