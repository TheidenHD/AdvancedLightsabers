package com.fiskmods.lightsabers.common.generator;

import java.util.List;
import java.util.Map;
import java.util.Random;

import com.fiskmods.lightsabers.common.block.BlockCrystalGen;
import com.fiskmods.lightsabers.common.block.ModBlocks;
import com.fiskmods.lightsabers.common.lightsaber.CrystalColor;
import com.fiskmods.lightsabers.common.tileentity.TileEntityCrystal;
import com.google.common.collect.Lists;

import net.minecraftforge.fml.common.IWorldGenerator;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumRarity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;

public enum WorldGeneratorOres implements IWorldGenerator
{
    INSTANCE;
    
    private WorldGenCrystalCaveEntrance entrance = new WorldGenCrystalCaveEntrance(32);
    
    @Override
    public void generate(Random rand, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider)
    {
        switch (world.provider.getDimension())
        {
        case 0:
            generateOverworld(world, rand, chunkX, chunkZ);
            break;
        }
    }

    public boolean isCrystalCaveChunk(World world, int x, int z)
    {
        Random rand = new Random(world.getSeed() + x * x * 0x4c1906 + x * 0x5ac0db + z * z * 0x4307a7L + z * 0x5f24f ^ 0x3ad8025f);
        return rand.nextInt(33) == 0;
    }

    public boolean isCrystalCaveChunk(World world, Chunk chunk)
    {
        return isCrystalCaveChunk(world, chunk.x, chunk.z);
    }

    public void generateOverworld(World world, Random rand, int x, int z)
    {
        if (world.getWorldInfo().getTerrainType() != WorldType.FLAT)
        {
            Chunk chunk = world.getChunk(x, z);
            x *= 16; 
            z *= 16;
            if (isCrystalCaveChunk(world, chunk) && world.getBiome(new BlockPos(x, 63, z)).getTempCategory() != Biome.TempCategory.OCEAN)
            {
                List<int[]> airBlocks = Lists.newArrayList();

                for (int i = 0; i < 16; ++i)
                {
                    for (int j = 0; j < 16; ++j)
                    {
                        int topBlock = 128;

                        while (world.getBlockState(new BlockPos(x + i, topBlock, z + j)).getBlock() == Blocks.AIR && topBlock > 0)
                        {
                            --topBlock;
                        }

                        for (int y = 0; y < topBlock - 10; ++y)
                        {
                            if (world.getBlockState(new BlockPos(x + i, y, z + j)).getBlock() == Blocks.AIR)
                            {
                                airBlocks.add(new int[] {x + i, y, z + j});
                            }
                        }
                    }
                }

                if (airBlocks.size() > 1024)
                {
                    int[] aint = airBlocks.get(rand.nextInt(airBlocks.size()));
                    int x1 = aint[0];
                    int y1 = aint[1];
                    int z1 = aint[2];
                    int count = 0;

                    while (y1 < world.getTopSolidOrLiquidBlock(new BlockPos(x1, 0, z1)).getY())
                    {
                        if (count < 10 + rand.nextInt(10))
                        {
                            y1 += 1;
                        }
                        else
                        {
                            if (rand.nextInt(3) == 0)
                            {
                                x1 += (rand.nextInt(3) - 1) * 2;
                            }

                            if (rand.nextInt(9) == 0)
                            {
                                y1 += 1;
                            }

                            if (rand.nextInt(3) == 0)
                            {
                                z1 += (rand.nextInt(3) - 1) * 2;
                            }
                        }

                        entrance.generate(world, rand, new BlockPos(x1, y1, z1));
                        ++count;
                    }

                    int radius = 1;

                    for (int i = -radius; i <= radius; ++i)
                    {
                        for (int j = -radius; j <= radius; ++j)
                        {
                            generateCrystal(100, ModBlocks.lightsaberCrystal, 64, world, rand, x + i * 16, z + j * 16);
                        }
                    }
                }
            }
        }
    }

    public void generateCrystal(int attempts, Block block, int minY, World world, Random rand, int chunkX, int chunkZ)
    {
        for (int i = 0; i < attempts; i++)
        {
            int x = chunkX + rand.nextInt(16);
            int y = rand.nextInt(minY);
            int z = chunkZ + rand.nextInt(16);
            BlockPos pos = new BlockPos(x, y, z);

            if (world.getBlockState(pos).getBlock() == Blocks.AIR)
            {
                BlockCrystalGen.replace(world, pos);
            }
        }
    }
}
