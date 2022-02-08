package com.fiskmods.lightsabers.common.item;

import static com.fiskmods.lightsabers.common.lightsaber.CrystalColor.*;
import static net.minecraft.item.EnumRarity.*;

import java.util.Map;
import java.util.Random;

import com.fiskmods.lightsabers.Lightsabers;
import com.fiskmods.lightsabers.client.render.LightsabersTEISR;
import com.fiskmods.lightsabers.common.block.ModBlocks;
import com.fiskmods.lightsabers.common.generator.ModChestGen;
import com.fiskmods.lightsabers.common.lightsaber.CrystalColor;
import com.google.common.collect.Maps;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.NonNullList;
import fiskfille.utils.helper.FiskMath;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraftforge.common.util.Constants.NBT;

public class ItemCrystal extends ItemBlock implements ILightsaberComponent
{
    public ItemCrystal(Block block)
    {
        super(block);
        setHasSubtypes(true);
        setRegistryName(block.getRegistryName());
        setTileEntityItemStackRenderer(new LightsabersTEISR());
        ModItems.ITEMS.add(this);
        setCreativeTab(Lightsabers.CREATIVE_TAB);
    }

    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items)
    {
        for (CrystalColor color : CrystalColor.values())
        {
            items.add(ItemCrystal.create(color));
        }
    }

    @Override
    public long getFingerprint(ItemStack stack, int slot)
    {
        return (getId(stack) & 0xFF) << 24;
    }
    
    @Override
    public boolean isCompatibleSlot(ItemStack stack, int slot)
    {
        return slot == 5;
    }

//    @Override //TODO
//    public WeightedRandomChestContent getChestGenBase(ChestGenHooks chest, Random rand, WeightedRandomChestContent original)
//    {
//        ItemStack itemstack = original.theItemId;
//        String category = "";
//
//        if (itemstack.hasTagCompound())
//        {
//            category = itemstack.getTagCompound().getString("ChestGenCategory");
//        }
//
//        List<CrystalColor> list = Lists.newArrayList();
//
//        for (CrystalColor color : CrystalColor.values())
//        {
//            if (Arrays.asList(chestMap.get(color)).contains(category))
//            {
//                for (int i = rarityMap.get(color).ordinal() - 1; i >= 0; --i)
//                {
//                    list.add(color);
//                }
//            }
//        }
//
//        if (list.isEmpty())
//        {
//            list.addAll(Arrays.asList(CrystalColor.values()));
//        }
//        
//        return new WeightedRandomChestContent(create(list.get(rand.nextInt(list.size()))), original.theMinimumChanceToGenerateItem, original.theMaximumChanceToGenerateItem, original.itemWeight);
//    }
    
    @Override
    public EnumRarity getRarity(ItemStack itemstack)
    {
        return rarityMap.get(get(itemstack));
    }
    
    @Override
    public void onUpdate(ItemStack itemstack, World world, Entity entity, int slot, boolean inHand)
    {
        if (!world.isRemote)
        {
            if (itemstack.hasTagCompound() && itemstack.getTagCompound().hasKey("color", NBT.TAG_ANY_NUMERIC))
            {
                getId(itemstack); // Makes sure any pre-1.1.2 data format is also converted server-side
            }
        }
    }
    
//    @Override //TODO
//    public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean advanced)
//    {
//        list.add(get(itemstack).getLocalizedName());
//    }
//
//    @Override
//    @SideOnly(Side.CLIENT)
//    public int getColorFromItemStack(ItemStack itemstack, int metadata)
//    {
//        return get(itemstack).color;
//    }
//    
//    @Override
//    @SideOnly(Side.CLIENT)
//    public void registerIcons(IIconRegister iconRegister)
//    {
//    }
    
    public static int getId(ItemStack itemstack)
    {
        if (itemstack.hasTagCompound() && itemstack.getTagCompound().hasKey("color", NBT.TAG_ANY_NUMERIC))
        {
            itemstack.setItemDamage(itemstack.getTagCompound().getInteger("color"));
            itemstack.getTagCompound().removeTag("color");
            
            if (itemstack.getTagCompound().isEmpty())
            {
                itemstack.setTagCompound(null);
            }
        }
        
        return itemstack.getItemDamage();
    }

    public static CrystalColor get(ItemStack itemstack)
    {
        return CrystalColor.get(getId(itemstack));
    }

    public static CrystalColor getRandomGen(Random rand)
    {
        return FiskMath.getWeightedI(rand, genRarityMap);
    }

    public static ItemStack create(CrystalColor color, Item item)
    {
        return new ItemStack(item, 1, color.id);
    }

    public static ItemStack create(CrystalColor color)
    {
        return create(color, Item.getItemFromBlock(ModBlocks.lightsaberCrystal));
    }

    public static Map<CrystalColor, EnumRarity> rarityMap = Maps.newHashMap();
    public static Map<CrystalColor, ResourceLocation[]> chestMap = Maps.newHashMap();

    private static Map<CrystalColor, Integer> genRarityMap = Maps.newHashMap();
    private static final int[] GEN_RARITY = {90, 30, 10, 1};

    public static void registerRarity(CrystalColor color, EnumRarity rarity, ResourceLocation... chests)
    {
        rarityMap.put(color, rarity);
        chestMap.put(color, chests);
        
        genRarityMap.put(color, GEN_RARITY[rarity.ordinal()]);
    }

    private static boolean hasInit = false;

    private static void init() throws Exception
    {
        if (hasInit)
        {
            return;
        }

        hasInit = true;

        registerRarity(DEEP_BLUE, COMMON, ModChestGen.SITH_TOMB_COFFIN, ModChestGen.SITH_TOMB_TREASURY, ModChestGen.SITH_TOMB_ANNEX, LootTableList.CHESTS_SIMPLE_DUNGEON);
        registerRarity(MEDIUM_BLUE, COMMON, ModChestGen.SITH_TOMB_COFFIN, ModChestGen.SITH_TOMB_TREASURY, ModChestGen.SITH_TOMB_ANNEX, LootTableList.CHESTS_SIMPLE_DUNGEON);
        registerRarity(LIGHT_BLUE, COMMON, ModChestGen.SITH_TOMB_COFFIN, ModChestGen.SITH_TOMB_TREASURY, ModChestGen.SITH_TOMB_ANNEX, LootTableList.CHESTS_SIMPLE_DUNGEON);
        registerRarity(AMBER, COMMON, ModChestGen.SITH_TOMB_COFFIN, ModChestGen.SITH_TOMB_TREASURY, ModChestGen.SITH_TOMB_ANNEX, LootTableList.CHESTS_DESERT_PYRAMID);
        registerRarity(YELLOW, COMMON, ModChestGen.SITH_TOMB_COFFIN, ModChestGen.SITH_TOMB_TREASURY, ModChestGen.SITH_TOMB_ANNEX, LootTableList.CHESTS_DESERT_PYRAMID);
        registerRarity(GOLD, COMMON, ModChestGen.SITH_TOMB_COFFIN, ModChestGen.SITH_TOMB_TREASURY, ModChestGen.SITH_TOMB_ANNEX, LootTableList.CHESTS_DESERT_PYRAMID);
        registerRarity(LIME_GREEN, COMMON, ModChestGen.SITH_TOMB_COFFIN, ModChestGen.SITH_TOMB_TREASURY, ModChestGen.SITH_TOMB_ANNEX, LootTableList.CHESTS_JUNGLE_TEMPLE, LootTableList.CHESTS_DESERT_PYRAMID);
        registerRarity(GREEN, COMMON, ModChestGen.SITH_TOMB_COFFIN, ModChestGen.SITH_TOMB_TREASURY, ModChestGen.SITH_TOMB_ANNEX, LootTableList.CHESTS_JUNGLE_TEMPLE);
        registerRarity(MINT_GREEN, COMMON, ModChestGen.SITH_TOMB_COFFIN, ModChestGen.SITH_TOMB_TREASURY, ModChestGen.SITH_TOMB_ANNEX, LootTableList.CHESTS_JUNGLE_TEMPLE);

        registerRarity(MAGENTA, UNCOMMON, ModChestGen.SITH_TOMB_COFFIN, ModChestGen.SITH_TOMB_TREASURY, ModChestGen.SITH_TOMB_ANNEX, LootTableList.CHESTS_SIMPLE_DUNGEON);
        registerRarity(PINK, UNCOMMON, ModChestGen.SITH_TOMB_COFFIN, ModChestGen.SITH_TOMB_TREASURY, ModChestGen.SITH_TOMB_ANNEX, LootTableList.CHESTS_SIMPLE_DUNGEON);
        registerRarity(RED, UNCOMMON, ModChestGen.SITH_TOMB_COFFIN, ModChestGen.SITH_TOMB_TREASURY, ModChestGen.SITH_TOMB_ANNEX, LootTableList.CHESTS_VILLAGE_BLACKSMITH);
        registerRarity(BLOOD_ORANGE, UNCOMMON, ModChestGen.SITH_TOMB_COFFIN, ModChestGen.SITH_TOMB_TREASURY, ModChestGen.SITH_TOMB_ANNEX, LootTableList.CHESTS_DESERT_PYRAMID);

        registerRarity(INDIGO, RARE, ModChestGen.SITH_TOMB_COFFIN, ModChestGen.SITH_TOMB_TREASURY, ModChestGen.SITH_TOMB_ANNEX, LootTableList.CHESTS_STRONGHOLD_LIBRARY);
        registerRarity(PURPLE, RARE, ModChestGen.SITH_TOMB_COFFIN, ModChestGen.SITH_TOMB_TREASURY, ModChestGen.SITH_TOMB_ANNEX, LootTableList.CHESTS_STRONGHOLD_LIBRARY);
        registerRarity(CYAN, RARE, ModChestGen.SITH_TOMB_COFFIN, ModChestGen.SITH_TOMB_TREASURY, ModChestGen.SITH_TOMB_ANNEX, LootTableList.CHESTS_STRONGHOLD_LIBRARY);

        registerRarity(ARCTIC_BLUE, EPIC, ModChestGen.SITH_TOMB_TREASURY, LootTableList.CHESTS_ABANDONED_MINESHAFT);
        registerRarity(WHITE, EPIC, ModChestGen.SITH_TOMB_TREASURY, LootTableList.CHESTS_ABANDONED_MINESHAFT);
    }

    static
    {
        try
        {
            init();
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }
}
