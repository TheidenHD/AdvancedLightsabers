package com.fiskmods.lightsabers.common.tileentity;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import com.fiskmods.lightsabers.ALConstants;
import com.fiskmods.lightsabers.common.hilt.Hilt;
import com.fiskmods.lightsabers.common.item.ItemCrystal;
import com.fiskmods.lightsabers.common.item.ItemDoubleLightsaber;
import com.fiskmods.lightsabers.common.item.ItemFocusingCrystal;
import com.fiskmods.lightsabers.common.item.ItemLightsaberBase;
import com.fiskmods.lightsabers.common.item.ItemLightsaberPart;
import com.fiskmods.lightsabers.common.item.ModItems;
import com.fiskmods.lightsabers.common.lightsaber.CrystalColor;
import com.fiskmods.lightsabers.common.lightsaber.FocusingCrystal;
import com.fiskmods.lightsabers.common.lightsaber.LightsaberData;
import com.fiskmods.lightsabers.common.lightsaber.PartType;
import com.fiskmods.lightsabers.helper.ALHelper;
import com.google.common.collect.ImmutableMap;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.common.util.Constants.NBT;

public class TileEntityDisassemblyStation extends TileEntity implements ISidedInventory, ITickable
{
    public static final int TICKS_DISASSEMBLY = 2400;
    public static final int INPUT = 0;
    public static final int FUEL = 1;

    private static final int[] SLOTS_TOP = {INPUT};
    private static final int[] SLOTS_BOTTOM = {2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, FUEL};
    private static final int[] SLOTS_SIDES = {INPUT, FUEL};

    private ItemStack[] itemstacks = new ItemStack[17];

    public int fuelTicks;
    public int maxFuelTicks;

    public int progress;

    @Override
    public void update()
    {
        boolean burning = fuelTicks > 0;
        boolean dirty = false;

        if (fuelTicks > 0)
        {
            --fuelTicks;
        }

        if (!world.isRemote)
        {
            if (fuelTicks != 0 || itemstacks[FUEL] != null && itemstacks[INPUT] != null)
            {
                if (fuelTicks == 0 && canDisassemble(itemstacks[INPUT]) && (maxFuelTicks = fuelTicks = getItemBurnTime(itemstacks[FUEL])) > 0)
                {
                    dirty = true;

                    if (itemstacks[FUEL] != null)
                    {
                    	itemstacks[FUEL].setCount(itemstacks[FUEL].getCount()-1);

                        if (itemstacks[FUEL].getCount() <= 0)
                        {
                            itemstacks[FUEL] = null;
                        }
                    }
                }

                if (isBurning() && canDisassemble(itemstacks[INPUT]))
                {
                    ++progress;

                    if (progress >= TICKS_DISASSEMBLY)
                    {
                        progress = 0;
                        disassembleItem();
                        dirty = true;
                    }
                }
                else
                {
                    progress = 0;
                }
            }

            if (burning != fuelTicks > 0)
            {
                dirty = true;
            }
        }

        if (dirty)
        {
            markDirty();
        }
    }

    @SideOnly(Side.CLIENT)
    public int getCookProgressScaled(int scale)
    {
        return progress * scale / TICKS_DISASSEMBLY;
    }

    @SideOnly(Side.CLIENT)
    public int getBurnTimeRemainingScaled(int scale)
    {
        if (maxFuelTicks == 0)
        {
            maxFuelTicks = 200;
        }

        return fuelTicks * scale / maxFuelTicks;
    }

    public boolean isBurning()
    {
        return fuelTicks > 0;
    }

    public static boolean canDisassemble(ItemStack stack)
    {
        return stack != null && stack.getItem() instanceof ItemLightsaberBase;
    }

    public void disassembleItem()
    {
        if (canDisassemble(itemstacks[INPUT]))
        {
            Map<ItemStack, Float> drops = getOutput(itemstacks[INPUT]);

            for (Map.Entry<ItemStack, Float> e : drops.entrySet())
            {
                if (e.getValue() > world.rand.nextFloat())
                {
                    addOutputItem(e.getKey());
                }
            }

            itemstacks[INPUT].setCount(itemstacks[INPUT].getCount()-1);

            if (itemstacks[INPUT].getCount() <= 0)
            {
                itemstacks[INPUT] = null;
            }
        }
    }

    public void addOutputItem(ItemStack stack)
    {
        stack = stack.copy();
        
        for (int i = 2; i < getSizeInventory(); ++i)
        {
            if (itemstacks[i] == null)
            {
                itemstacks[i] = stack.copy();
                return;
            }
            else if (ItemStack.areItemStacksEqual(stack, itemstacks[i]))
            {
                int j = Math.min(stack.getCount(), stack.getMaxStackSize() - itemstacks[i].getCount());
                itemstacks[i].setCount(itemstacks[i].getCount() + j);
                stack.setCount(stack.getCount() - j);
                
                if (stack.getCount() <= 0)
                {
                    return;
                }
            }
        }
        
        ALHelper.dropItem(world, pos.getX(), pos.getY(), pos.getZ(), stack, world.rand);
    }

    public static Map<ItemStack, Float> getOutput(ItemStack stack)
    {
        if (stack.getItem() instanceof ItemDoubleLightsaber)
        {
            LightsaberData[] data = ItemDoubleLightsaber.get(stack);
            Map<ItemStack, Float> drops = getOutput(data[0], true);
            drops.putAll(getOutput(data[1], true));

            return drops;
        }

        return getOutput(LightsaberData.get(stack), !stack.hasTagCompound() || !stack.getTagCompound().hasKey(ALConstants.TAG_LIGHTSABER_SPECIAL));
    }

    public static Map<ItemStack, Float> getOutput(LightsaberData data, boolean salvageColor)
    {
        Map<ItemStack, Float> drops = new LinkedHashMap<>();
        drops.put(new ItemStack(ModItems.circuitry), 0.25F);

        if (data.isHiltUniform())
        {
            for (PartType type : PartType.values())
            {
                drops.put(ItemLightsaberPart.create(type, data.get(type)), 1F);
            }
        }
        else
        {
            Map<Hilt, Integer> hilts = new HashMap<>();

            for (Hilt hilt : data.getHilt())
            {
                hilts.put(hilt, hilts.getOrDefault(hilt, -1) + 1);
            }

            for (PartType type : PartType.values())
            {
                Hilt hilt = data.get(type);
                drops.put(ItemLightsaberPart.create(type, hilt), 0.66F + 0.05F * hilts.get(hilt));
            }
        }

        for (FocusingCrystal crystal : data.getFocusingCrystals())
        {
            drops.put(ItemFocusingCrystal.create(crystal), 0.725F);
        }

        if (salvageColor)
        {
            CrystalColor color = data.getColor();
            drops.put(ItemCrystal.create(color), 0.35F + 0.125F * ItemCrystal.rarityMap.get(color).ordinal());
        }

        return drops;
    }

    @Override
    public int getSizeInventory()
    {
        return itemstacks.length;
    }

    @Override
    public ItemStack getStackInSlot(int slot)
    {
        return itemstacks[slot];
    }

    @Override
    public ItemStack decrStackSize(int slot, int amount)
    {
        if (itemstacks[slot] != null)
        {
            if (itemstacks[slot].getCount() <= amount)
            {
                return removeStackFromSlot(slot);
            }

            ItemStack stack = itemstacks[slot].splitStack(amount);

            if (itemstacks[slot].getCount() <= 0)
            {
                itemstacks[slot] = null;
            }

            return stack;
        }

        return null;
    }

//    @Override//TODO
//    public ItemStack getStackInSlotOnClosing(int slot)
//    {
//        if (itemstacks[slot] != null)
//        {
//            ItemStack stack = itemstacks[slot];
//            itemstacks[slot] = null;
//
//            return stack;
//        }
//
//        return null;
//    }

    @Override
    public void setInventorySlotContents(int slot, ItemStack stack)
    {
        itemstacks[slot] = stack;

        if (stack != null && stack.getCount() > getInventoryStackLimit())
        {
            stack.setCount(getInventoryStackLimit());
        }
    }

//    @Override //TODO
//    public String getInventoryName()
//    {
//        return "gui.disassembly_station";
//    }
//
//    @Override
//    public boolean hasCustomInventoryName()
//    {
//        return false;
//    }
    
    @Override
    public AxisAlignedBB getRenderBoundingBox()
    {
        return new AxisAlignedBB(pos.getX(), pos.getY(), pos.getZ(),pos.getX() + 1, pos.getY() + 1, pos.getZ() + 1).expand(1.5, 1, 1.5);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt)
    {
        super.readFromNBT(nbt);
        NBTTagList list = nbt.getTagList("Items", NBT.TAG_COMPOUND);
        itemstacks = new ItemStack[getSizeInventory()];

        for (int i = 0; i < list.tagCount(); ++i)
        {
            NBTTagCompound tag = list.getCompoundTagAt(i);
            byte slot = tag.getByte("Slot");

            if (slot >= 0 && slot < itemstacks.length)
            {
                itemstacks[slot] = new ItemStack(tag);
            }
        }

        fuelTicks = nbt.getShort("BurnTime");
        progress = nbt.getShort("DisassemblyTime");
        maxFuelTicks = getItemBurnTime(itemstacks[FUEL]);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbt)
    {
    	NBTTagCompound ret = super.writeToNBT(nbt);
        nbt.setShort("BurnTime", (short) fuelTicks);
        nbt.setShort("DisassemblyTime", (short) progress);
        NBTTagList list = new NBTTagList();

        for (int i = 0; i < itemstacks.length; ++i)
        {
            if (itemstacks[i] != null)
            {
                NBTTagCompound tag = new NBTTagCompound();
                tag.setByte("Slot", (byte) i);
                list.appendTag(itemstacks[i].writeToNBT(tag));
            }
        }

        nbt.setTag("Items", list);
        return ret;
    }

    @Override
    public int getInventoryStackLimit()
    {
        return 64;
    }

    public static int getItemBurnTime(ItemStack stack)
    {
        return stack != null ? getFuelValue(stack) : 0;
    }

    public static boolean isItemFuel(ItemStack stack)
    {
        return getItemBurnTime(stack) > 0;
    }

    @Override
    public boolean isUsableByPlayer(EntityPlayer player)
    {
        return world.getTileEntity(pos) == this && player.getDistanceSq(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D) <= 64.0D;
    }

//    @Override //TODO
//    public void openInventory()
//    {
//    }
//
//    @Override
//    public void closeInventory()
//    {
//    }

    @Override
    public boolean isItemValidForSlot(int slot, ItemStack stack)
    {
        return slot == INPUT && canDisassemble(stack) || slot == FUEL && isItemFuel(stack);
    }

//    @Override //TODO
//    public int[] getAccessibleSlotsFromSide(int side)
//    {
//        return side == 0 ? SLOTS_BOTTOM : side == 1 ? SLOTS_TOP : SLOTS_SIDES;
//    }

    @Override
    public boolean canInsertItem(int slot, ItemStack stack, EnumFacing direction)
    {
        return isItemValidForSlot(slot, stack);
    }

    @Override
    public boolean canExtractItem(int slot, ItemStack stack, EnumFacing direction)
    {
        return direction != EnumFacing.UP || slot != FUEL; //TODO check correct side
    }
    
    private static final Map<ItemStack, Integer> FUELS = new HashMap<>();

    public static void registerFuel(ItemStack stack, int ticks)
    {
        FUELS.put(stack, ticks);
    }

    public static int getFuelValue(ItemStack stack)
    {
        for (Map.Entry<ItemStack, Integer> e : FUELS.entrySet())
        {
            if (e.getKey().isItemEqual(stack))
            {
                return e.getValue();
            }
        }

        return 0;
    }
    
    public static ImmutableMap<ItemStack, Integer> getFuels()
    {
        return ImmutableMap.copyOf(FUELS);
    }
    
    static
    {
        registerFuel(new ItemStack(Items.REDSTONE), 300);
        registerFuel(new ItemStack(Blocks.REDSTONE_BLOCK), 2700);
    }

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ItemStack removeStackFromSlot(int index) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void openInventory(EntityPlayer player) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void closeInventory(EntityPlayer player) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getField(int id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setField(int id, int value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getFieldCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasCustomName() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int[] getSlotsForFace(EnumFacing side) {
		// TODO Auto-generated method stub
		return null;
	}

}
