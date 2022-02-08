package com.fiskmods.lightsabers.common.tileentity;

import java.util.List;

import com.fiskmods.lightsabers.common.block.BlockSithStoneCoffin;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;

public class TileEntitySithStoneCoffin extends TileEntity implements ITickable
{
    public TileEntitySithCoffin mainCoffin;
    public ItemStack equipment;
    public boolean baseplateOnly = false;
    public boolean taskFinished = false;
    private int mainCoffinX;
    private int mainCoffinY;
    private int mainCoffinZ;

    @Override
    public void update()
    {
        if (mainCoffin != null)
        {
            int x = mainCoffin.getPos().getX();
            int y = mainCoffin.getPos().getY();
            int z = mainCoffin.getPos().getZ();

            if (!baseplateOnly)
            {
                double radius = 14.0D;
                List<EntityPlayer> list = world.getEntitiesWithinAABB(EntityPlayer.class, new AxisAlignedBB(x, y, z, x + 1, y + 1, z + 1).expand(radius, 2, radius));

                if (!list.isEmpty())
                {
                    baseplateOnly = true;
                    taskFinished = true;

                    for (int i = 0; i < 2; ++i)
                    {
                        world.playEvent(2001, getPos().south(i), Block.getIdFromBlock(world.getTileEntity(getPos().south(i)).getBlockType()) + (world.getTileEntity(getPos().south(i)).getBlockMetadata() << 12));
                    }

                    if (!world.isRemote)
                    {//TODO
                        //BlockSithStoneCoffin.spawnSithGhost(world, getPos().getX(), getPos().getY(), getPos().getZ()).setAttackTarget(list.get(0));
                    }
                }
            }
        }
        else
        {
            TileEntity tile = world.getTileEntity(new BlockPos(mainCoffinX, mainCoffinY, mainCoffinZ));

            if (tile instanceof TileEntitySithCoffin)
            {
                mainCoffin = (TileEntitySithCoffin) tile;
            }
        }
    }

    public void setMainCoffin(TileEntitySithCoffin tile)
    {
        mainCoffin = tile;
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbt)
    {
    	NBTTagCompound ret = super.writeToNBT(nbt);//TODO

        if (mainCoffin != null)
        {
            nbt.setInteger("CoffinX", mainCoffin.getPos().getX());
            nbt.setInteger("CoffinY", mainCoffin.getPos().getY());
            nbt.setInteger("CoffinZ", mainCoffin.getPos().getZ());
        }

        if (equipment != null)
        {
            NBTTagCompound nbttagcompound = new NBTTagCompound();
            equipment.writeToNBT(nbttagcompound);
            nbt.setTag("Equipment", nbttagcompound);
        }

        nbt.setBoolean("BaseplateOnly", baseplateOnly);
        nbt.setBoolean("TaskFinished", taskFinished);
        return ret;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt)
    {
        super.readFromNBT(nbt);
        mainCoffinX = nbt.getInteger("CoffinX");
        mainCoffinY = nbt.getInteger("CoffinY");
        mainCoffinZ = nbt.getInteger("CoffinZ");
        baseplateOnly = nbt.getBoolean("BaseplateOnly");
        taskFinished = nbt.getBoolean("TaskFinished");

        if (nbt.hasKey("Equipment"))
        {
            NBTTagCompound nbttagcompound = nbt.getCompoundTag("Equipment");
            equipment = new ItemStack(nbttagcompound);
        }
    }

    @Override
    public AxisAlignedBB getRenderBoundingBox()
    {
        return new AxisAlignedBB(pos.getX(), pos.getY(), pos.getZ(), pos.getX() + 1, pos.getY() + 2, pos.getZ() + 1);
    }

//    @Override //TODO
//    public Packet getDescriptionPacket()
//    {
//        NBTTagCompound syncData = new NBTTagCompound();
//        writeToNBT(syncData);
//
//        return new SPacketUpdateTileEntity(pos, 1, syncData);
//    }

    @Override
    public void onDataPacket(NetworkManager netManager, SPacketUpdateTileEntity packet)
    {
        readFromNBT(packet.getNbtCompound());
    }
}
