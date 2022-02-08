package com.fiskmods.lightsabers.common.block;

import java.util.Random;

import com.fiskmods.lightsabers.Lightsabers;
import com.fiskmods.lightsabers.common.item.ItemCrystal;
import com.fiskmods.lightsabers.common.tileentity.TileEntityCrystal;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
//import net.minecraft.client.renderer.texture.IIconRegister; //TODO
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockCrystalGen extends BlockBasic
{
    public BlockCrystalGen()
    {
        super(Material.AIR);
    }

    @Override
    public boolean canHarvestBlock(IBlockAccess world, BlockPos pos, EntityPlayer player)
    {
        return false;
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos)
    {
        return null;
    }

//    @Override //TODO
//    public boolean renderAsNormalBlock()
//    {
//        return false;
//    }

    @Override
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }
    
    @Override
    public void updateTick(World world, BlockPos pos, IBlockState state, Random rand)
    {
        replace(world, pos);
    }

    @Override
    public void onBlockAdded(World world, BlockPos pos, IBlockState state)
    {
        replace(world, pos);
    }

    @Override
    public void onNeighborChange(IBlockAccess world, BlockPos pos, BlockPos neighbor)
    {
//        if (block != this)  //TODO orginal??
        {
            replace((World)world, pos);
        }
    }
    
    @Override
    public int quantityDropped(Random random)
    {
        return 0;
    }

    public static void replace(World world, BlockPos pos)
    {
        if (ModBlocks.lightsaberCrystal.canPlaceBlockAt(world, pos) && world.setBlockState(pos, ModBlocks.lightsaberCrystal.getDefaultState(), 2))
        {
            TileEntityCrystal tile = (TileEntityCrystal) world.getTileEntity(pos);

            if (tile != null)
            {
                Random rand = new Random(world.getSeed() + pos.getX() * pos.getX() * 0x4c1906 + pos.getX() * 0x5ac0db + pos.getZ() * pos.getZ() * 0x4307a7L + pos.getZ() * 0x5f24f ^ 0x3ad8025f ^ pos.getY());
                tile.setColor(ItemCrystal.getRandomGen(rand));
            }
        }
        else
        {
            world.setBlockToAir(pos);
        }
    }
    
//    @Override //TODO
//    public void registerBlockIcons(IIconRegister iconRegister)
//    {
//        blockIcon = iconRegister.registerIcon(Lightsabers.MODID + ":lightsaber_crystal");
//    }
}
