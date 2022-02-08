//package com.fiskmods.lightsabers.common.event; //TODO delete?
//
//import com.fiskmods.lightsabers.common.item.ModItems;
//
//import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
//import mods.battlegear2.api.RenderPlayerEventChild.PreRenderSheathed;
//import net.minecraft.item.ItemStack;
//
//public class ClientEventHandlerBG
//{
//    @SubscribeEvent
//    public void onPreRenderSheathed(PreRenderSheathed event)
//    {
//        ItemStack itemstack = event.element;
//
//        if (itemstack != null)
//        {
//            if (itemstack.getItem() == ModItems.lightsaber || itemstack.getItem() == ModItems.doubleLightsaber)
//            {
//                event.setCanceled(true);
//            }
//        }
//    }
//}
