package com.fiskmods.lightsabers.common.item;

import com.fiskmods.lightsabers.common.tileentity.TileEntitySithStoneCoffin;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemSithStoneCoffin extends ItemBlock
{
    public ItemSithStoneCoffin(Block block)
    {
        super(block);
    }

    @Override
    public boolean placeBlockAt(ItemStack itemstack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, IBlockState newState)
    {
        if (!world.setBlockState(pos, newState, 3))
        {
            return false;
        }
        else
        {
            TileEntitySithStoneCoffin tile = (TileEntitySithStoneCoffin) world.getTileEntity(pos);

            if (tile != null)
            {
                if (itemstack.hasTagCompound() && itemstack.getTagCompound().hasKey("Equipment"))
                {
                    NBTTagCompound nbttagcompound = itemstack.getTagCompound().getCompoundTag("Equipment");
                    tile.equipment = new ItemStack(nbttagcompound);
                }

                tile.taskFinished = true;
            }
        }

        if (world.getBlockState(pos).getBlock() == block)
        {
        	block.onBlockPlacedBy(world, pos, newState, player, itemstack);
        }

        return true;
    }
}
