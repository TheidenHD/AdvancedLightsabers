package com.fiskmods.lightsabers.common.generator;

import java.util.HashMap;
import java.util.Map;

import com.fiskmods.lightsabers.Lightsabers;
import com.fiskmods.lightsabers.common.block.ModBlocks;
import com.fiskmods.lightsabers.common.item.ItemCrystal;
import com.fiskmods.lightsabers.common.item.ModItems;
import com.fiskmods.lightsabers.common.lightsaber.CrystalColor;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

import net.minecraft.world.storage.loot.LootEntry;
import net.minecraft.world.storage.loot.LootPool;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraftforge.event.LootTableLoadEvent;


public class ModChestGen
{
    /**
     * Resource locations, path and names must fit the existing json file.
     */
    public final static ResourceLocation SITH_TOMB_ANNEX = new ResourceLocation(Lightsabers.MODID, "chests/emitter");
    public final static ResourceLocation SITH_TOMB_TREASURY = new ResourceLocation(Lightsabers.MODID, "chests/emitter");
    public final static ResourceLocation SITH_TOMB_COFFIN = new ResourceLocation(Lightsabers.MODID, "chests/emitter");
    public final static ResourceLocation JEDI_TEMPLE = new ResourceLocation(Lightsabers.MODID, "chests/emitter");

    /**
     * The instance
     */
    private static ModChestGen instance;

    /**
     * Maps vanilla lootable resource location to our loot pool to add.
     */
    private Map<ResourceLocation, ResourceLocation> lootTables = new HashMap<>();

    private ModChestGen()
    {
        init();
    }

    /**
     * Initializes the loot tables to attach to
     */
    protected void init()
    {
    	final ResourceLocation EMITTER = new ResourceLocation(Lightsabers.MODID, "chests/emitter");
        lootTables.put(LootTableList.CHESTS_VILLAGE_BLACKSMITH, EMITTER);
        lootTables.put(LootTableList.CHESTS_SIMPLE_DUNGEON, EMITTER);
   
    }

    public static ModChestGen getInstance()
    {
        if (instance == null)
        {
            instance = new ModChestGen();
        }
        return instance;
    }

    /**
     * Adds loot to the given event, if the name fits
     *
     * @param event the event.
     */
    public void addLootToEvent(LootTableLoadEvent event)
    {
        if (lootTables.containsKey(event.getName()))
        {//TODO
            //event.getTable().addPool(LootPool.lootPool().add(LootEntry.lootTableReference(lootTables.get(event.getName()))).name(MOD_ID + ":loot:" + event.getName()).build());
        }
    }
}

//public class ModChestGen
//{
//    public static final String SITH_TOMB_ANNEX = "sithTombAnnex";
//    public static final String SITH_TOMB_TREASURY = "sithTombTreasury";
//    public static final String SITH_TOMB_COFFIN = "sithTombCoffin";
//    public static final String JEDI_TEMPLE = "jediTemple";
//
//    public static void register()
//    {
//        addInfo(SITH_TOMB_ANNEX, new WeightedRandomChestContent[] {new WeightedRandomChestContent(Items.bone, 0, 4, 6, 4), new WeightedRandomChestContent(Items.rotten_flesh, 0, 3, 7, 3), new WeightedRandomChestContent(Items.iron_ingot, 0, 2, 7, 3), new WeightedRandomChestContent(ModItems.circuitry, 0, 1, 1, 2), new WeightedRandomChestContent(ModItems.emitter, 0, 1, 1, 1), new WeightedRandomChestContent(ModItems.emitter, 0, 1, 1, 1), new WeightedRandomChestContent(ModItems.emitter, 0, 1, 1, 1), createColorCrystal(Item.getItemFromBlock(ModBlocks.lightsaberCrystal), 0, 1, 1, 1, SITH_TOMB_ANNEX), createColorCrystal(Item.getItemFromBlock(ModBlocks.lightsaberCrystal), 0, 1, 1, 1, SITH_TOMB_ANNEX), createColorCrystal(Item.getItemFromBlock(ModBlocks.lightsaberCrystal), 0, 1, 1, 1, SITH_TOMB_ANNEX)}, 3, 7);
//
//        addInfo(SITH_TOMB_TREASURY, new WeightedRandomChestContent[] {new WeightedRandomChestContent(Items.bone, 0, 4, 6, 14), new WeightedRandomChestContent(Items.rotten_flesh, 0, 3, 7, 12), new WeightedRandomChestContent(Items.gold_ingot, 0, 2, 7, 12), new WeightedRandomChestContent(Items.diamond, 0, 1, 2, 5), new WeightedRandomChestContent(Items.enchanted_book, 0, 1, 1, 5), new WeightedRandomChestContent(ModItems.emitter, 0, 1, 1, 2), new WeightedRandomChestContent(ModItems.emitter, 0, 1, 1, 2), new WeightedRandomChestContent(ModItems.emitter, 0, 1, 1, 2), new WeightedRandomChestContent(ModItems.focusingCrystal, 0, 1, 1, 1), new WeightedRandomChestContent(ModItems.focusingCrystal, 0, 1, 1, 1), new WeightedRandomChestContent(ModItems.focusingCrystal, 0, 1, 1, 1), new WeightedRandomChestContent(ModItems.focusingCrystal, 0, 1, 1, 1),
//                createColorCrystal(Item.getItemFromBlock(ModBlocks.lightsaberCrystal), 0, 1, 1, 4, SITH_TOMB_TREASURY), createColorCrystal(Item.getItemFromBlock(ModBlocks.lightsaberCrystal), 0, 1, 1, 4, SITH_TOMB_TREASURY), createColorCrystal(Item.getItemFromBlock(ModBlocks.lightsaberCrystal), 0, 1, 1, 4, SITH_TOMB_TREASURY)}, 5, 7);
//
//        addInfo(SITH_TOMB_COFFIN, new WeightedRandomChestContent[] {new WeightedRandomChestContent(Items.bone, 0, 4, 6, 14), new WeightedRandomChestContent(Items.rotten_flesh, 0, 3, 7, 12), new WeightedRandomChestContent(Items.gold_ingot, 0, 2, 7, 12), new WeightedRandomChestContent(Items.diamond, 0, 1, 2, 5), new WeightedRandomChestContent(Items.enchanted_book, 0, 1, 1, 5), new WeightedRandomChestContent(ModItems.circuitry, 0, 1, 1, 3), new WeightedRandomChestContent(ModItems.emitter, 0, 1, 1, 1), new WeightedRandomChestContent(ModItems.emitter, 0, 1, 1, 1), new WeightedRandomChestContent(ModItems.emitter, 0, 1, 1, 1), new WeightedRandomChestContent(ModItems.focusingCrystal, 0, 1, 1, 1), new WeightedRandomChestContent(ModItems.focusingCrystal, 0, 1, 1, 1), new WeightedRandomChestContent(ModItems.focusingCrystal, 0, 1, 1, 1),
//                new WeightedRandomChestContent(ModItems.focusingCrystal, 0, 1, 1, 1), createColorCrystal(Item.getItemFromBlock(ModBlocks.lightsaberCrystal), 0, 1, 1, 1, SITH_TOMB_COFFIN), createColorCrystal(Item.getItemFromBlock(ModBlocks.lightsaberCrystal), 0, 1, 1, 1, SITH_TOMB_COFFIN), createColorCrystal(Item.getItemFromBlock(ModBlocks.lightsaberCrystal), 0, 1, 1, 1, SITH_TOMB_COFFIN), createSithLightsaber(ModItems.lightsaber, 0, 1, 1, 6)}, 6, 14);
//
//        addInfo(JEDI_TEMPLE, new WeightedRandomChestContent[] {new WeightedRandomChestContent(Items.cooked_chicken, 0, 1, 4, 4), new WeightedRandomChestContent(Items.baked_potato, 0, 2, 6, 5), new WeightedRandomChestContent(Items.name_tag, 0, 1, 2, 2), new WeightedRandomChestContent(Items.leather, 0, 1, 8, 4), new WeightedRandomChestContent(Item.getItemFromBlock(Blocks.wool), 0, 1, 9, 10), new WeightedRandomChestContent(ModItems.circuitry, 0, 1, 2, 3), new WeightedRandomChestContent(ModItems.emitter, 0, 1, 1, 1), new WeightedRandomChestContent(ModItems.emitter, 0, 1, 1, 1), new WeightedRandomChestContent(ModItems.emitter, 0, 1, 1, 1), new WeightedRandomChestContent(ModItems.emitter, 0, 1, 1, 1), new WeightedRandomChestContent(ModItems.emitter, 0, 1, 1, 1), new WeightedRandomChestContent(ModItems.emitter, 0,
//                1, 1, 1), new WeightedRandomChestContent(ModItems.emitter, 0, 1, 1, 1), new WeightedRandomChestContent(ModItems.emitter, 0, 1, 1, 1), new WeightedRandomChestContent(ModItems.emitter, 0, 1, 1, 1), createJediLightsaber(ModItems.lightsaber, 0, 1, 1, 1),}, 4, 8);
//
//        for (Map.Entry<CrystalColor, String[]> e : ItemCrystal.chestMap.entrySet())
//        {
//            for (String chest : e.getValue())
//            {
//                if (!chest.equals(SITH_TOMB_ANNEX) && !chest.equals(SITH_TOMB_TREASURY) && !chest.equals(SITH_TOMB_COFFIN))
//                {
//                    ChestGenHooks.addItem(chest, createColorCrystal(Item.getItemFromBlock(ModBlocks.lightsaberCrystal), 0, 1, 1, 1, chest));
//                }
//            }
//        }
//
//        lootTables.
//        ChestGenHooks.addItem(ChestGenHooks.VILLAGE_BLACKSMITH, new WeightedRandomChestContent(ModItems.emitter, 0, 1, 1, 2));
//        ChestGenHooks.addItem(ChestGenHooks.VILLAGE_BLACKSMITH, new WeightedRandomChestContent(ModItems.emitter, 0, 1, 1, 2));
//        ChestGenHooks.addItem(ChestGenHooks.VILLAGE_BLACKSMITH, new WeightedRandomChestContent(ModItems.emitter, 0, 1, 1, 2));
//        ChestGenHooks.addItem(ChestGenHooks.DUNGEON_CHEST, new WeightedRandomChestContent(ModItems.emitter, 0, 1, 1, 2));
//        ChestGenHooks.addItem(ChestGenHooks.DUNGEON_CHEST, new WeightedRandomChestContent(ModItems.emitter, 0, 1, 1, 2));
//        ChestGenHooks.addItem(ChestGenHooks.DUNGEON_CHEST, new WeightedRandomChestContent(ModItems.emitter, 0, 1, 1, 2));
//    }
//
//    private static void addInfo(String category, WeightedRandomChestContent[] items, int min, int max)
//    {
//        ChestGenHooks chest = ChestGenHooks.getInfo(category);
//
//        for (WeightedRandomChestContent item : items)
//        {
//            chest.addItem(item);
//        }
//
//        chest.setMin(min);
//        chest.setMax(max);
//    }
//
//    private static WeightedRandomChestContent createSithLightsaber(Item item, int metadata, int min, int max, int weight)
//    {
//        WeightedRandomChestContent chest = new WeightedRandomChestContent(item, metadata, min, max, weight);
//        chest.theItemId.setTagCompound(new NBTTagCompound());
//        chest.theItemId.getTagCompound().setBoolean("SithTombLoot", true);
//        return chest;
//    }
//
//    private static WeightedRandomChestContent createJediLightsaber(Item item, int metadata, int min, int max, int weight)
//    {
//        WeightedRandomChestContent chest = new WeightedRandomChestContent(item, metadata, min, max, weight);
//        chest.theItemId.setTagCompound(new NBTTagCompound());
//        chest.theItemId.getTagCompound().setBoolean("JediTempleLoot", true);
//        return chest;
//    }
//
//    private static WeightedRandomChestContent createColorCrystal(Item item, int metadata, int min, int max, int weight, String category)
//    {
//        WeightedRandomChestContent chest = new WeightedRandomChestContent(item, metadata, min, max, weight);
//        chest.theItemId.setTagCompound(new NBTTagCompound());
//        chest.theItemId.getTagCompound().setString("ChestGenCategory", category);
//        return chest;
//    }
//}
