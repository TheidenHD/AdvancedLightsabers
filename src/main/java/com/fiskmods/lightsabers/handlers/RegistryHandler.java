package com.fiskmods.lightsabers.handlers;

import com.fiskmods.lightsabers.Lightsabers;
import com.fiskmods.lightsabers.client.gui.GuiHandlerAL;
import com.fiskmods.lightsabers.client.render.tile.RenderCrystal;
import com.fiskmods.lightsabers.client.sound.ALSounds;
import com.fiskmods.lightsabers.common.block.ModBlocks;
import com.fiskmods.lightsabers.common.command.CommandForce;
import com.fiskmods.lightsabers.common.item.ModItems;
import com.fiskmods.lightsabers.common.tileentity.TileEntityCrystal;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@EventBusSubscriber
public class RegistryHandler
{
    @SubscribeEvent
    public static void onItemRegister(RegistryEvent.Register<Item> event)
    {
        event.getRegistry().registerAll(ModItems.ITEMS.toArray(new Item[0]));
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public static void onModelRegister(ModelRegistryEvent event)
    {
        Lightsabers.proxy.registerItemRenderer(Item.getItemFromBlock(ModBlocks.lightsaberCrystal), "inventory");
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCrystal.class, new RenderCrystal());
        //RenderHandler.registerCustomMeshesAndStates();
        //RenderHandler.registerEntityRenders();
        //TileEntityRendererDispatcher.instance.render();
        for(Item item : ModItems.ITEMS)
        {
            Lightsabers.proxy.registerItemRenderer(item, "inventory");
        }

        for(Block block : ModBlocks.BLOCKS)
        {
            Lightsabers.proxy.registerItemRenderer(Item.getItemFromBlock(block), "inventory");
        }
    }

    @SubscribeEvent
    public static void onBlockRegister(RegistryEvent.Register<Block> event)
    {
        //BlockInit.getBlocks();
        event.getRegistry().registerAll(ModBlocks.BLOCKS.toArray(new Block[0]));
    }

    @SubscribeEvent
    public static void registerEnchant(RegistryEvent.Register<Enchantment> event)
    {
        //event.getRegistry().registerAll(EnchantmentInit.ENCHANTMENTS.toArray(new Enchantment[0]));
    }

    @SubscribeEvent
    public static void registerSounds(RegistryEvent.Register<SoundEvent> event){
        ALSounds.registerSounds();
    }

    public static void preInitRegistries(FMLPreInitializationEvent event)
    {
        //FluidInit.registerFluids();
        //DimensionInit.registerDimensions();
        //BiomeInit.registerBiomes();
        //GameRegistry.registerWorldGenerator(new WorldGenOres(), 3);
        //EntityInit.registerEntities();
        //EventHandler.registerEvents();
        //SoundsHandler.registerSounds();
        //ConfigHandler.registerConfig(event);
    }

    public static void initRegistries()
    {
        NetworkRegistry.INSTANCE.registerGuiHandler(Lightsabers.instance, new GuiHandlerAL());
        //SmeltingRecipes.init();
        //CraftingRecipes.init();
        //OreDictionaryInit.registerOres();
    }

    public static void serverRegistries(FMLServerStartingEvent event)
    {
        event.registerServerCommand(new CommandForce());
    }
}
