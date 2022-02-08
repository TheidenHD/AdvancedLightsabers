package com.fiskmods.lightsabers.common.item;

import com.fiskmods.lightsabers.common.lightsaber.PartType;

import net.minecraft.item.Item;

import java.util.ArrayList;
import java.util.List;

public class ModItems
{
    public static final List<Item> ITEMS = new ArrayList<Item>();

    public static final Item circuitry = new ItemCircuitry("lightsaber_circuitry");
    public static final Item focusingCrystal = new ItemFocusingCrystal();
    public static final Item crystalPouch = new ItemCrystalPouch("crystal_pouch");
    public static Item crystal;

    public static final Item emitter = new ItemLightsaberPart(PartType.EMITTER);
    public static final Item switchSection = new ItemLightsaberPart(PartType.SWITCH_SECTION);
    public static final Item grip = new ItemLightsaberPart(PartType.BODY);
    public static final Item pommel = new ItemLightsaberPart(PartType.POMMEL);

    public static final Item lightsaber = new ItemLightsaber();
    public static final Item doubleLightsaber = new ItemDoubleLightsaber();

}
