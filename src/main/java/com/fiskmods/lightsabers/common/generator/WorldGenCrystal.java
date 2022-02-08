package com.fiskmods.lightsabers.common.generator;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenCrystal extends WorldGenerator
{
    private Block target;
    private Material growthMaterial;

    public WorldGenCrystal(Block block, Material material)
    {
        target = block;
        growthMaterial = material;
    }

    @Override
    public boolean generate(World world, Random rand, BlockPos pos)
    {
        boolean flag = false;
        int range = 3;

        for (int i = -range; i <= range && !flag; ++i)
        {
            for (int j = -range; j <= range; ++j)
            {
                int xPosition = pos.getX() >> 4 + i;
                int zPosition = pos.getZ() >> 4 + j;
                Random random = new Random(world.getSeed() + (long) (xPosition * xPosition * 0x4c1906) + (long) (xPosition * 0x5ac0db) + (long) (zPosition * zPosition) * 0x4307a7L + (long) (zPosition * 0x5f24f) ^ 0x3ad8025f);
                
                if (random.nextInt(300) == 0)
                {
                    flag = true;
                    break;
                }
            }
        }

        if (flag && world.isAirBlock(pos))
        {
            for (EnumFacing dir : EnumFacing.VALUES)
            {
                if (world.getBlockState(pos.add(dir.getXOffset(), dir.getYOffset(), dir.getZOffset())).getMaterial() == growthMaterial)
                {
                    return world.setBlockState(pos, target.getDefaultState(), 2);//TODO add rotation
                }
            }
        }

        return false;
    }
}
