package com.fiskmods.lightsabers.common.block;

import com.fiskmods.lightsabers.common.item.ItemCrystal;
import com.fiskmods.lightsabers.common.item.ItemDisassemblyTable;
import com.fiskmods.lightsabers.common.item.ItemForcestone;
import com.fiskmods.lightsabers.common.item.ItemHolocron;
import com.fiskmods.lightsabers.common.item.ItemLightsaberForge;
import com.fiskmods.lightsabers.common.item.ItemSithCoffin;
import com.fiskmods.lightsabers.common.item.ItemSithStoneCoffin;
import com.fiskmods.lightsabers.common.tileentity.TileEntityCrystal;
import com.fiskmods.lightsabers.common.tileentity.TileEntityDisassemblyStation;
import com.fiskmods.lightsabers.common.tileentity.TileEntityHolocron;
import com.fiskmods.lightsabers.common.tileentity.TileEntityLightsaberForge;
import com.fiskmods.lightsabers.common.tileentity.TileEntityLightsaberStand;
import com.fiskmods.lightsabers.common.tileentity.TileEntitySithCoffin;
import com.fiskmods.lightsabers.common.tileentity.TileEntitySithStoneCoffin;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.item.ItemSlab;

import java.util.ArrayList;
import java.util.List;

public class ModBlocks
{
    public static final List<Block> BLOCKS = new ArrayList<Block>();

    public static final Block lightsaberCrystal = new BlockCrystal("lightsaber_crystal");
    //public static final Block lightsaberCrystalGen = new BlockCrystalGen();
    public static final Block lightsaberStand = new BlockLightsaberStand();
    public static final Block disassemblyStation = new BlockDisassemblyStation();
    public static final Block sithCoffin = new BlockSithCoffin();
    public static final Block sithStoneCoffin = new BlockSithStoneCoffin();
    public static final Block holocron = new BlockHolocron();

    public static final Block lightForcestone = new BlockForcestone().setHardness(3.0F).setResistance(100.0F);
    public static final Block lightActivatedForcestone = new BlockPillar().setLightLevel(1.0F).setHardness(3.0F).setResistance(100.0F);
    public static final Block lightForcestoneStairs = new BlockModStairs(lightForcestone.getDefaultState()); //TODO check
    public static final Block darkForcestone = new BlockForcestone().setHardness(3.0F).setResistance(100.0F);
    public static final Block darkActivatedForcestone = new BlockPillar().setLightLevel(1.0F).setHardness(3.0F).setResistance(100.0F);
    public static final Block darkForcestoneStairs = new BlockModStairs(darkForcestone.getDefaultState());
    public static final Block forcestoneDoubleSlab = (BlockSlab) new BlockModSlab(true).setHardness(3.0F).setResistance(100.0F);
    public static final Block forcestoneSlab = (BlockSlab) new BlockModSlab(false).setHardness(3.0F).setResistance(100.0F);

    public static final Block lightsaberForgeLight = new BlockLightsaberForge(lightForcestone);
    public static final Block lightsaberForgeDark = new BlockLightsaberForge(darkForcestone);
    //test = new BlockStructureSpawner();
    //BlockRegistry.registerItemBlockAsTileEntity(lightsaberCrystal, "Lightsaber Crystal", TileEntityCrystal.class, ItemCrystal.class);

//        BlockRegistry.registerItemBlockAsTileEntity(lightsaberCrystal, "Lightsaber Crystal", TileEntityCrystal.class, ItemCrystal.class); //TODO
//        BlockRegistry.registerItemBlockAsTileEntity(lightsaberForgeLight, "Lightsaber Forge Light", TileEntityLightsaberForge.class, ItemLightsaberForge.class);
//        BlockRegistry.registerItemBlockAsTileEntity(lightsaberForgeDark, "Lightsaber Forge Dark", TileEntityLightsaberForge.class, ItemLightsaberForge.class);
//        BlockRegistry.registerTileEntity(lightsaberStand, "Lightsaber Stand", TileEntityLightsaberStand.class);
//        BlockRegistry.registerItemBlockAsTileEntity(disassemblyStation, "Disassembly Station", TileEntityDisassemblyStation.class, ItemDisassemblyTable.class);
//        BlockRegistry.registerItemBlockAsTileEntity(sithCoffin, "Sith Coffin", TileEntitySithCoffin.class, ItemSithCoffin.class);
//        BlockRegistry.registerItemBlockAsTileEntity(sithStoneCoffin, "Sith Stone Coffin", TileEntitySithStoneCoffin.class, ItemSithStoneCoffin.class);
//        BlockRegistry.registerItemBlockAsTileEntity(holocron, "Holocron", TileEntityHolocron.class, ItemHolocron.class);
//
//        BlockRegistry.registerItemBlock(lightForcestone, "Light Forcestone", new ItemForcestone(lightForcestone));
//        BlockRegistry.registerBlock(lightActivatedForcestone, "Light Activated Forcestone Pillar");
//        BlockRegistry.registerBlock(lightForcestoneStairs, "Light Forcestone Stairs");
//        BlockRegistry.registerItemBlock(darkForcestone, "Dark Forcestone", new ItemForcestone(darkForcestone));
//        BlockRegistry.registerBlock(darkActivatedForcestone, "Dark Activated Forcestone Pillar");
//        BlockRegistry.registerBlock(darkForcestoneStairs, "Dark Forcestone Stairs");
//        BlockRegistry.registerItemBlock(forcestoneDoubleSlab, "Forcestone Double Slab", new ItemSlab(forcestoneDoubleSlab, forcestoneSlab, forcestoneDoubleSlab));
//        BlockRegistry.registerItemBlock(forcestoneSlab, "Forcestone Slab", new ItemSlab(forcestoneSlab, forcestoneSlab, forcestoneDoubleSlab));

//        BlockRegistry.registerBlock(lightsaberCrystalGen, "Lightsaber Crystal Gen");
//		BlockRegistry.registerBlock(test, "Test");

}
