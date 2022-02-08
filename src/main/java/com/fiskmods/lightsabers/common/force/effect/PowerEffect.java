package com.fiskmods.lightsabers.common.force.effect;

import java.util.Random;

import com.fiskmods.lightsabers.client.sound.ALSounds;
import com.fiskmods.lightsabers.common.force.ForceSide;
import com.fiskmods.lightsabers.common.force.Power;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.SoundEvent;

public class PowerEffect
{
    public final Random rand = new Random();
    public final int amplifier;
    
    public PowerEffect(int amplifier)
    {
        this.amplifier = amplifier;
    }

    public boolean execute(EntityPlayer player, Side side)
    {
        return true;
    }

    public float getUseCost(EntityPlayer player, Power power)
    {
        return power.powerStats.useCost;
    }

    public SoundEvent getCastSound(ForceSide side)
    {
        return side == ForceSide.DARK ? ALSounds.PLAYER_FORCE_DARK : ALSounds.PLAYER_FORCE_CAST;
    }

    public float getCastSoundVolume(ForceSide side)
    {
        return 1;
    }

    public float getCastSoundPitch(ForceSide side)
    {
        return (rand.nextFloat() - rand.nextFloat()) * 0.2F + 1;
    }

    public String[] getDesc()
    {
        return new String[] {};
    }
}
