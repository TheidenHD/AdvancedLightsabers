package com.fiskmods.lightsabers.common.tileentity;

import java.util.UUID;

import com.fiskmods.lightsabers.common.item.ItemLightsaberBase;
import com.mojang.authlib.GameProfile;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.common.util.Constants.NBT;

public class TileEntityLightsaberStand extends TileEntity
{
    private ItemStack displayStack;
    private UUID owner;

    public boolean setDisplayStack(ItemStack stack)
    {
        if (isItemValid(stack) && !ItemStack.areItemStacksEqual(displayStack, stack))
        {
            displayStack = stack;
            //world.notifyBlockUpdate(pos);//TODO
            markDirty();

            return true;
        }

        return false;
    }

    public boolean isItemValid(ItemStack stack)
    {
        return stack == null || stack.getItem() instanceof ItemLightsaberBase;
    }

    public ItemStack getDisplayStack()
    {
        return displayStack;
    }

    public UUID getOwner()
    {
        return owner;
    }

    public void setOwner(EntityLivingBase entity)
    {
        if (entity instanceof EntityPlayer)
        {
            GameProfile profile = ((EntityPlayer) entity).getGameProfile();

            if (profile != null && profile.getId() != null)
            {
                owner = profile.getId();
            }
        }
        else if (entity != null)
        {
            owner = entity.getUniqueID();
        }
    }

    public boolean isOwner(EntityLivingBase entity)
    {
        if (owner == null)
        {
            return false;
        }

        if (entity instanceof EntityPlayer)
        {
            GameProfile profile = ((EntityPlayer) entity).getGameProfile();

            if (profile != null && profile.getId() != null)
            {
                return owner.equals(profile.getId());
            }
        }
        else if (entity != null)
        {
            return owner.equals(entity.getUniqueID());
        }

        return false;
    }
    
    @Override
    public AxisAlignedBB getRenderBoundingBox()
    {
        return new AxisAlignedBB(pos.getX(), pos.getY(), pos.getZ(), pos.getX() + 1, pos.getY() + 1, pos.getZ() + 1).expand(0.5, 0.5, 0.5);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt)
    {
        super.readFromNBT(nbt);

        if (nbt.hasKey("DisplayStack", NBT.TAG_COMPOUND))
        {
            displayStack = new ItemStack(nbt.getCompoundTag("DisplayStack"));
        }
        else
        {
            displayStack = null;
        }

        if (nbt.hasKey("Owner", NBT.TAG_COMPOUND))
        {
            NBTTagCompound compound = nbt.getCompoundTag("Owner");

            if (compound.hasKey("UUIDMost", NBT.TAG_LONG) && compound.hasKey("UUIDLeast", NBT.TAG_LONG))
            {
                owner = new UUID(compound.getLong("UUIDMost"), compound.getLong("UUIDLeast"));
            }
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbt)
    {
    	NBTTagCompound ret = super.writeToNBT(nbt);

        if (displayStack != null)
        {
            nbt.setTag("DisplayStack", displayStack.writeToNBT(new NBTTagCompound()));
        }

        if (owner != null)
        {
            NBTTagCompound compound = new NBTTagCompound();
            compound.setLong("UUIDMost", owner.getMostSignificantBits());
            compound.setLong("UUIDLeast", owner.getLeastSignificantBits());
            nbt.setTag("Owner", compound);
        }
        return ret;
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
