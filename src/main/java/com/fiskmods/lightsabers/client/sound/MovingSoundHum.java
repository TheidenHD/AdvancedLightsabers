package com.fiskmods.lightsabers.client.sound;

import com.fiskmods.lightsabers.Lightsabers;
import com.fiskmods.lightsabers.asm.ASMHooksClient;
import com.fiskmods.lightsabers.common.entity.EntityLightsaber;
import com.fiskmods.lightsabers.common.item.ItemLightsaberBase;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.client.audio.MovingSound;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;

@SideOnly(Side.CLIENT)
public class MovingSoundHum extends MovingSound
{
    public final Entity theEntity;

    public MovingSoundHum(Entity entity)
    {
        //super(new ResourceLocation(Lightsabers.MODID, "hum_" + type)); //TODO
    	super(SoundEvents.ITEM_ELYTRA_FLYING, SoundCategory.PLAYERS);
        theEntity = entity;
        repeat = true;
        repeatDelay = 0;
        volume = 0.25F;
    }

    @Override
    public void update()
    {
        boolean flag = false;

        if (theEntity instanceof EntityLivingBase)
        {
            EntityLivingBase entity = (EntityLivingBase) theEntity;
            ItemStack itemstack = entity.getHeldItemMainhand();
            flag = itemstack == null || !(itemstack.getItem() instanceof ItemLightsaberBase) || !ItemLightsaberBase.isActive(itemstack);
        }
        else if (theEntity instanceof EntityLightsaber)
        {

        }

        if (theEntity.isDead || flag)
        {
            donePlaying = true;
            ASMHooksClient.humSounds.remove(theEntity.getUniqueID().toString());
        }
        else
        {
            xPosF = (float) theEntity.posX;
            yPosF = (float) theEntity.posY;
            zPosF = (float) theEntity.posZ;
        }
    }
}
