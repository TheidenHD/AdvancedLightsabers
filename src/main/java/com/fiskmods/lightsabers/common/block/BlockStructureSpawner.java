package com.fiskmods.lightsabers.common.block;

import java.util.Random;

import org.lwjgl.input.Keyboard;

import com.fiskmods.lightsabers.common.generator.structure.Structure;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockStructureSpawner extends BlockBasic
{
    public BlockStructureSpawner()
    {
        super(Material.ROCK);
    }

//    @Override //TODO
//    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, BlockPos pos)
//    {
//        if (Keyboard.isKeyDown(Keyboard.KEY_M))
//        {
//            spawnStructure(world, pos);
//        }
//
//        return super.getCollisionBoundingBoxFromPool(world, pos);
//    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        spawnStructure(world, pos);
        return true;
    }

    private void spawnStructure(World world, BlockPos pos)
    {
        //Structure structure = new StructureSithTomb(world, pos); //TODO
        //Random rand = new Random();

//		//rand.setSeed(574935734654L);
        //structure.spawnStructure(rand);
    }
}
