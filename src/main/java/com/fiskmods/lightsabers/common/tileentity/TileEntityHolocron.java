package com.fiskmods.lightsabers.common.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;

public class TileEntityHolocron extends TileEntity implements ITickable
{
    public int playersUsing;
    public float openTimer;
    public float prevOpenTimer;
    public int openTicks;
    public int prevOpenTicks;

    @Override
    public void update()
    {
        prevOpenTimer = openTimer;
        prevOpenTicks = openTicks;

        if (playersUsing <= 0)
        {
            openTimer *= 0.85F;
        }
        else if (openTimer < 1)
        {
            openTimer += 0.05F;
            openTimer *= 1.05F;
        }

        if (openTimer < 1E-6)
        {
            openTimer = 0;
        }

        if (openTimer == 0)
        {
            openTicks = 0;
        }
        else if (openTimer >= 1)
        {
            ++openTicks;
        }

        openTimer = MathHelper.clamp(openTimer, 0, 1);
        playersUsing = Math.max(playersUsing, 0);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbttagcompound)
    {
        super.readFromNBT(nbttagcompound);

    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbttagcompound)
    {
        return super.writeToNBT(nbttagcompound);

    }

    @Override
    public AxisAlignedBB getRenderBoundingBox()
    {
        return new AxisAlignedBB(pos.getX(), pos.getY(), pos.getZ(), pos.getX() + 1, pos.getY() + 1, pos.getZ() + 1);
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
