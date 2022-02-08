package com.fiskmods.lightsabers.client.sound;

import com.fiskmods.lightsabers.common.data.effect.Effect;
import com.fiskmods.lightsabers.common.data.effect.StatusEffect;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.client.audio.MovingSound;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;

@SideOnly(Side.CLIENT)
public class MovingSoundStatusEffect extends MovingSound
{
    public final EntityLivingBase theEntity;
    public final Effect theEffect;

    public MovingSoundStatusEffect(EntityLivingBase entity, Effect effect, SoundEvent ambientFortify)
    {
        //super(new ResourceLocation(name));//TODO
    	super(SoundEvents.ITEM_ELYTRA_FLYING, SoundCategory.PLAYERS);
        theEntity = entity;
        theEffect = effect;
        repeat = true;
        repeatDelay = 0;
        volume = 1.0F;
    }

    @Override
    public void update()
    {
        if (theEntity.isDead || StatusEffect.get(theEntity, theEffect) == null)
        {
            donePlaying = true;
        }
        else
        {
            xPosF = (float) theEntity.posX;
            yPosF = (float) theEntity.posY;
            zPosF = (float) theEntity.posZ;
        }
    }
}
