package com.fiskmods.lightsabers.common.tileentity;

import com.fiskmods.lightsabers.common.lightsaber.CrystalColor;

import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.EnumPacketDirection;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

public class TileEntityCrystal extends TileEntity
{
    private CrystalColor crystalColor = CrystalColor.get(0);

    public void setColor(CrystalColor color)
    {
        if (crystalColor != color)
        {
            crystalColor = color;
            markDirty();
        }
    }

    public CrystalColor getColor()
    {
        return crystalColor;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt)
    {
        super.readFromNBT(nbt);
        crystalColor = CrystalColor.get(nbt.getInteger("color"));
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbt)
    {
    	NBTTagCompound ret = super.writeToNBT(nbt);
        nbt.setInteger("color", crystalColor.id);
        return ret;
    }

    public void setItemValues(ItemStack item)
    {
        this.setColor(CrystalColor.get(item.getItemDamage()));
    }

    @Override
    public NBTTagCompound getUpdateTag()
    {
        NBTTagCompound tag = super.getUpdateTag();
        tag.setInteger("color", crystalColor.id);
        return tag;
    }

    @Override
    public void handleUpdateTag(NBTTagCompound tag)
    {
        super.handleUpdateTag(tag);
        crystalColor = CrystalColor.get(tag.getInteger("color"));
    }

    @Override
    public SPacketUpdateTileEntity getUpdatePacket()
    {
        return new SPacketUpdateTileEntity(pos, getBlockMetadata(), getUpdateTag());
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt)
    {
        if(net.getDirection() == EnumPacketDirection.CLIENTBOUND)
        {
            readFromNBT(pkt.getNbtCompound());
        }
    }
}
