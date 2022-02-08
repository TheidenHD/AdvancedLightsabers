package com.fiskmods.lightsabers.client.sound;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.PositionedSound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;

@SideOnly(Side.CLIENT)
public class SoundAL extends PositionedSound
{
    public static SoundAL mediumHum = SoundAL.makeSound(ALSounds.AMBIENT_LIGHTSABER_HUM_MEDIUM.getSoundName(), true, 1.0F, 0.5F);

    public static SoundAL makeSound(ResourceLocation resourceLocation, boolean loop)
    {
        SoundAL sound = new SoundAL(resourceLocation, 1.0F, 1.0F, false, 0, ISound.AttenuationType.NONE, 0.0F, 0.0F, 0.0F);
        sound.repeat = loop;
        return sound;
    }

    public static SoundAL makeSound(ResourceLocation resourceLocation, boolean loop, float volume, float pitch)
    {
        SoundAL sound = new SoundAL(resourceLocation, volume, pitch, loop, 0, ISound.AttenuationType.NONE, 0.0F, 0.0F, 0.0F);
        return sound;
    }

    public static SoundAL makeSound(ResourceLocation resourceLocation, boolean loop, float x, float y, float z)
    {
        SoundAL sound = new SoundAL(resourceLocation, 1.0F, 1.0F, loop, 0, ISound.AttenuationType.LINEAR, x, y, z);
        return sound;
    }

    public static SoundAL makeSound(ResourceLocation resourceLocation, boolean loop, float x, float y, float z, float volume, float pitch, ISound.AttenuationType attenuationType)
    {
        SoundAL sound = new SoundAL(resourceLocation, volume, pitch, loop, 0, attenuationType, x, y, z);
        return sound;
    }

    public SoundAL(ResourceLocation location, float volume, float pitch, boolean repeat, int p_i45108_5_, ISound.AttenuationType p_i45108_6_, float x, float y, float z)
    {
        super(location, SoundCategory.PLAYERS); //TODO?
        this.volume = volume;
        this.pitch = pitch;
        xPosF = x;
        yPosF = y;
        zPosF = z;
        this.repeat = repeat;
        repeatDelay = p_i45108_5_;
        attenuationType = p_i45108_6_;
    }
}
