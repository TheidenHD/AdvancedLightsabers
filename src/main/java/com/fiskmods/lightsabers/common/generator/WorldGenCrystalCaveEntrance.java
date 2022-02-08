package com.fiskmods.lightsabers.common.generator;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenCrystalCaveEntrance extends WorldGenerator
{
    private int numberOfBlocks;

    public WorldGenCrystalCaveEntrance(int blocks)
    {
        numberOfBlocks = blocks;
    }

    @Override
    public boolean generate(World world, Random random, BlockPos pos)
    {
        float f = random.nextFloat() * (float) Math.PI;
        double d0 = pos.getX() + random.nextInt(3) - 2;
        double d1 = pos.getX() + random.nextInt(3) - 2;
        double d2 = pos.getZ() + random.nextInt(3) - 2;
        double d3 = pos.getZ() + random.nextInt(3) - 2;
        double d4 = pos.getY() + random.nextInt(3) - 2;
        double d5 = pos.getY() + random.nextInt(3) - 2;

        for (int l = 0; l <= numberOfBlocks; ++l)
        {
            double d6 = d0 + (d1 - d0) * l / numberOfBlocks;
            double d7 = d4 + (d5 - d4) * l / numberOfBlocks;
            double d8 = d2 + (d3 - d2) * l / numberOfBlocks;
            double d9 = random.nextDouble() * numberOfBlocks / 16.0D;
            double d10 = (MathHelper.sin(l * (float) Math.PI / numberOfBlocks) + 1.0F) * d9 + 1.0D;
            double d11 = (MathHelper.sin(l * (float) Math.PI / numberOfBlocks) + 1.0F) * d9 + 1.0D;
            int i1 = MathHelper.floor(d6 - d10 / 2.0D);
            int j1 = MathHelper.floor(d7 - d11 / 2.0D);
            int k1 = MathHelper.floor(d8 - d10 / 2.0D);
            int l1 = MathHelper.floor(d6 + d10 / 2.0D);
            int i2 = MathHelper.floor(d7 + d11 / 2.0D);
            int j2 = MathHelper.floor(d8 + d10 / 2.0D);

            for (int k2 = i1; k2 <= l1; ++k2)
            {
                double d12 = (k2 + 0.5D - d6) / (d10 / 2.0D);

                if (d12 * d12 < 1.0D)
                {
                    for (int l2 = j1; l2 <= i2; ++l2)
                    {
                        double d13 = (l2 + 0.5D - d7) / (d11 / 2.0D);

                        if (d12 * d12 + d13 * d13 < 1.0D)
                        {
                            for (int i3 = k1; i3 <= j2; ++i3)
                            {
                                double d14 = (i3 + 0.5D - d8) / (d10 / 2.0D);
                                Block block1 = world.getBlockState(new BlockPos(k2, l2 + 1, i3)).getBlock();

                                if (d12 * d12 + d13 * d13 + d14 * d14 < 1.0D && world.getBlockState(new BlockPos(k2, l2, i3)).getBlock() != Blocks.CHEST)
                                {
                                    if (world.getBlockState(new BlockPos(k2 + 1, l2, i3)).getBlock() != Blocks.AIR || world.getBlockState(new BlockPos(k2 - 1, l2, i3)).getBlock() != Blocks.AIR || world.getBlockState(new BlockPos(k2, l2 + 1, i3)).getBlock() != Blocks.AIR || world.getBlockState(new BlockPos(k2, l2 - 1, i3)).getBlock() != Blocks.AIR || world.getBlockState(new BlockPos(k2, l2, i3 + 1)).getBlock() != Blocks.AIR || world.getBlockState(new BlockPos(k2, l2, i3 - 1)).getBlock() != Blocks.AIR)
                                    {
                                        world.setBlockState(new BlockPos(k2, l2, i3), Blocks.AIR.getDefaultState());
                                    }

                                    Block block = Blocks.STONE;
                                    createWalls(world, k2 + 1, l2, i3, block);
                                    createWalls(world, k2 - 1, l2, i3, block);
                                    createWalls(world, k2, l2 + 1, i3, block);
                                    createWalls(world, k2, l2 - 1, i3, block);
                                    createWalls(world, k2, l2, i3 + 1, block);
                                    createWalls(world, k2, l2, i3 - 1, block);
                                }
                            }
                        }
                    }
                }
            }
        }

        return true;
    }

    public void createWalls(World world, int x, int y, int z, Block block)
    {
//        if (world.getBlock(x, y, z).isSideSolid(world, x, y, z, ForgeDirection.UP))//TODO
//        {
//            world.setBlock(x, y, z, block, 0, 2);
//        }
    }
}