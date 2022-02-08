package com.fiskmods.lightsabers.common.item;

import static com.fiskmods.lightsabers.common.block.BlockDisassemblyStation.*;

//import com.fiskmods.lightsabers.common.block.BlockDisassemblyStation.Part;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class ItemDisassemblyTable extends ItemBlock
{
    public ItemDisassemblyTable(Block block)
    {
        super(block);
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float f, float f1, float f2)
    {
        if (world.isRemote)
        {
            return EnumActionResult.SUCCESS;
        }
        else
        {
        	ItemStack itemstack = player.getHeldItem(hand);
        	pos = pos.offset(facing);

            int dir = MathHelper.floor(player.rotationYaw * 4F / 360F + 0.5) & 3;
            byte x1 = 0;
            byte z1 = 0;

            if (dir == 0)
            {
                x1 = -1;
            }

            if (dir == 1)
            {
                z1 = -1;
            }

            if (dir == 2)
            {
                x1 = 1;
            }

            if (dir == 3)
            {
                z1 = 1;
            }

            if (player.canPlayerEdit(pos, facing, itemstack))
            {
//                if (world.isAirBlock(pos) && world.isAirBlock(pos.add(x1, 0, z1)) && player.canPlayerEdit(pos.add(x1, 0, z1), facing, itemstack)) //TODO
//                {
//                    world.setBlock(pos, field_150939_a, serialize(dir, Part.BASE), 3);
//
//                    if (world.getBlockState(pos) == field_150939_a)
//                    {
//                        world.setBlock(x + x1, y, z + z1, field_150939_a, serialize(dir, Part.SIDE), 2);
//                        world.setBlock(x + x1, y + 1, z + z1, field_150939_a, serialize(dir, Part.TOP), 2);
//                        world.setBlock(x, y + 1, z, field_150939_a, serialize(dir, Part.TOP), 2);
//                    }
//
//                    world.playSoundEffect(x + 0.5, y + 0.5, z + 0.5, field_150939_a.stepSound.func_150496_b(), (field_150939_a.stepSound.getVolume() + 1) / 2, field_150939_a.stepSound.getPitch() * 0.8F);
//                    --itemstack.getCount();
//                    return EnumActionResult.SUCCESS;
//                }
            }

            return EnumActionResult.FAIL;
        }
    }
}
