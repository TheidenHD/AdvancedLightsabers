package com.fiskmods.lightsabers.common.item;

import com.fiskmods.lightsabers.Lightsabers;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemCircuitry extends Item implements ILightsaberComponent
{
    public ItemCircuitry(String name)
    {
        setTranslationKey(name);
        setRegistryName(name);
        setCreativeTab(Lightsabers.CREATIVE_TAB);

        ModItems.ITEMS.add(this);
    }
    @Override
    public long getFingerprint(ItemStack stack, int slot)
    {
        return 0;
    }
    
    @Override
    public boolean isCompatibleSlot(ItemStack stack, int slot)
    {
        return slot == 4;
    }
}
