package com.fiskmods.lightsabers.common.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class ContainerBasic<T extends TileEntity> extends Container
{
    protected final T tileentity;
    protected final World world;

    public ContainerBasic(T tile)
    {
        tileentity = tile;
        world = tile != null ? tile.getWorld() : null;
    }

    public void addPlayerInventory(InventoryPlayer inventoryPlayer, int yOffset)
    {
        int i;
        int j;

        for (i = 0; i < 3; ++i)
        {
            for (j = 0; j < 9; ++j)
            {
                addSlotToContainer(makeInventorySlot(inventoryPlayer, j + i * 9 + 9, 8 + j * 18, 84 + yOffset + i * 18));
            }
        }

        for (i = 0; i < 9; ++i)
        {
            addSlotToContainer(makeInventorySlot(inventoryPlayer, i, 8 + i * 18, 142 + yOffset));
        }
    }

    public Slot makeInventorySlot(InventoryPlayer inventoryPlayer, int index, int x, int y)
    {
        return new Slot(inventoryPlayer, index, x, y);
    }

    @Override
    public boolean canInteractWith(EntityPlayer player)
    {
        if (tileentity != null)
        {
            if (tileentity instanceof IInventory)
            {
                return ((IInventory) tileentity).isUsableByPlayer(player);
            }

            return player.getDistanceSq(tileentity.getPos().getX() + 0.5D, tileentity.getPos().getY() + 0.5D, tileentity.getPos().getZ() + 0.5D) <= 64.0D;
        }

        return true;
    }

    @Override
    protected boolean mergeItemStack(ItemStack stackToMove, int fromId, int toId, boolean descending)
    {
        return mergeItemStack(stackToMove, fromId, toId, descending, false);
    }

    protected boolean mergeItemStack(ItemStack stackToMove, int fromId, int toId, boolean descending, boolean check)
    {
        boolean success = false;
        int id = fromId;

        if (descending)
        {
            id = toId - 1;
        }

        Slot slot;
        ItemStack dstStack;

        if (stackToMove.isStackable())
        {
            while (stackToMove.getCount() > 0 && (!descending && id < toId || descending && id >= fromId))
            {
                slot = (Slot) inventorySlots.get(id);
                dstStack = slot.getStack();

                if ((!check || slot.isItemValid(stackToMove)) && dstStack != null && dstStack.getItem() == stackToMove.getItem() && (!stackToMove.getHasSubtypes() || stackToMove.getItemDamage() == dstStack.getItemDamage()) && ItemStack.areItemStackTagsEqual(stackToMove, dstStack))
                {
                    int maxStackSize = Math.min(slot.inventory.getInventoryStackLimit(), Math.min(dstStack.getMaxStackSize(), slot.getSlotStackLimit()));
                    int combinedStackSize = dstStack.getCount() + stackToMove.getCount();

                    if (combinedStackSize <= maxStackSize)
                    {
                        stackToMove.setCount(0);
                        dstStack.setCount(combinedStackSize);
                        slot.onSlotChanged();
                        success = true;
                    }
                    else if (dstStack.getCount() < maxStackSize)
                    {
                        stackToMove.setCount(stackToMove.getCount()-(maxStackSize - dstStack.getCount()));
                        dstStack.setCount(maxStackSize);
                        slot.onSlotChanged();
                        success = true;
                    }
                }

                if (descending)
                {
                    --id;
                }
                else
                {
                    ++id;
                }
            }
        }

        if (stackToMove.getCount() > 0)
        {
            if (descending)
            {
                id = toId - 1;
            }
            else
            {
                id = fromId;
            }

            while (!descending && id < toId || descending && id >= fromId)
            {
                slot = (Slot) inventorySlots.get(id);
                dstStack = slot.getStack();

                if ((!check || slot.isItemValid(stackToMove)) && dstStack == null)
                {
                    int maxStackSize = Math.min(slot.inventory.getInventoryStackLimit(), Math.min(stackToMove.getMaxStackSize(), slot.getSlotStackLimit()));
                    ItemStack itemstack1 = stackToMove.copy();
                    itemstack1.setCount(Math.min(maxStackSize, itemstack1.getCount()));
                    slot.putStack(itemstack1);

                    maxStackSize = Math.min(slot.inventory.getInventoryStackLimit(), Math.min(slot.getStack().getMaxStackSize(), slot.getSlotStackLimit()));
                    stackToMove.setCount(Math.max(stackToMove.getCount() - itemstack1.getCount(), 0));
                    slot.onSlotChanged();
                    success = true;
                    break;
                }

                if (descending)
                {
                    --id;
                }
                else
                {
                    ++id;
                }
            }
        }

        return success;
    }
}
