package com.fiskmods.lightsabers.common.generator.structure;

import java.util.List;
import java.util.Random;

import com.google.common.collect.Lists;

import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityLockableLoot;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class Structure
{
    protected final World world;
    protected BlockPos pos;

    protected boolean mirrorX;
    protected boolean mirrorZ;

    protected int maxY;
    protected boolean simulate = false;
    protected List<StructurePoint> coverage = Lists.newArrayList();

    public Structure(World world, BlockPos pos)
    {
        this.world = world;
        this.pos = pos;
    }

    public abstract void spawnStructure(Random random);

    public void setBlockState(IBlockState block, EnumFacing metadata, BlockPos pos2)
    {
        if (mirrorX && pos2.getX() > 0)
        {
            setBlockState(pos.add(-pos2.getX(), pos2.getY(), pos2.getZ()), block, StructureHelper.mirrorMetadata(block.getBlock(), metadata));
        }

        if (mirrorZ && pos2.getZ() > 0)
        {
        	setBlockState(pos.add(pos2.getX(), pos2.getY(), -pos2.getZ()), block, StructureHelper.mirrorMetadata(block.getBlock(), metadata));
        }

        setBlockState(pos.add(pos2), block, metadata);
    }

    private void setBlockState(BlockPos pos2, IBlockState block, EnumFacing metadata)
    {
    	IBlockState block1 = null;
        
        if (simulate || ((block1 = world.getBlockState(pos)) != block || (EnumFacing)world.getBlockState(pos).getValue(BlockHorizontal.FACING) != metadata) && block1.getBlockHardness(world, pos) != -1)
        {
            if (simulate)
            {
                maxY = Math.max(maxY, pos.getY());

                StructurePoint p = new StructurePoint(pos);

                if (coverage.contains(p))
                {
                    for (int i = 0; i < coverage.size(); ++i)
                    {
                        StructurePoint p1 = coverage.get(i);

                        if (p.equals(p1))
                        {
                            p1 = new StructurePoint(p1.getX(), Math.min(p1.getY(), pos.getY()), p1.getZ());
                            break;
                        }
                    }
                }
                else
                {
                    coverage.add(p);
                }
            }
            else
            {
                placeBlockState(pos, block, metadata, 2);
            }
        }
    }
    
    public void placeBlockState(BlockPos pos2, IBlockState block, EnumFacing metadata, int flags)
    {
        world.setBlockState(pos2, block, flags);//TODO metadata
    }

    protected boolean generateStructureChestContents(Random random, BlockPos pos2, ResourceLocation chestContent)
    {
    	BlockPos i = pos.add(pos2);

        if (world.getBlockState(i) != Blocks.CHEST)
        {
            world.setBlockState(i, Blocks.CHEST.correctFacing(world, pos2, Blocks.CHEST.getDefaultState()), 2);
            TileEntity tile = world.getTileEntity(i);
            
            if (tile instanceof TileEntityChest)
            {
                ((TileEntityChest)tile).setLootTable(chestContent, random.nextLong());
            }

            return true;
        }
        else
        {
            return false;
        }
    }

    protected boolean fillStructureInventory(IBlockState block, Random random, BlockPos pos2, ResourceLocation chestContent)
    {
    	BlockPos i = pos.add(pos2);

        if (world.getBlockState(i) == block)
        {
        	TileEntity inventory = world.getTileEntity(i);

            if (inventory instanceof TileEntityLockableLoot)
            {
                ((TileEntityLockableLoot)inventory).setLootTable(chestContent, random.nextLong());
            }

            return true;
        }
        else
        {
            return false;
        }
    }
}
