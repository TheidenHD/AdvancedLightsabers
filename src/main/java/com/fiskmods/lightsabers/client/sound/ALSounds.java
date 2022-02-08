package com.fiskmods.lightsabers.client.sound;

import com.fiskmods.lightsabers.Lightsabers;
import net.minecraft.init.Bootstrap;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class ALSounds
{
    public static SoundEvent AMBIENT_FORTIFY;
    public static SoundEvent AMBIENT_STEALTH;
    public static SoundEvent AMBIENT_LIGHTSABER_HUM_HEAVY;
    public static SoundEvent AMBIENT_LIGHTSABER_HUM_LIGHT;
    public static SoundEvent AMBIENT_LIGHTSABER_HUM_MEDIUM;
    public static SoundEvent BLOCK_HOLOCRON_INVEST;
    public static SoundEvent BLOCK_HOLOCRON_UNLOCK;
    public static SoundEvent BLOCK_SITH_SARCOPHAGUS_CLOSE;
    public static SoundEvent BLOCK_SITH_SARCOPHAGUS_OPEN;
    public static SoundEvent MOB_LIGHTSABER_HIT;
    public static SoundEvent MOB_LIGHTSABER_OFF;
    public static SoundEvent MOB_LIGHTSABER_ON;
    public static SoundEvent MOB_LIGHTSABER_SWING;
    public static SoundEvent MOB_SITH_GHOST_DEATH;
    public static SoundEvent MOB_SITH_GHOST_IDLE;
    public static SoundEvent PLAYER_FORCE_CAST;
    public static SoundEvent PLAYER_FORCE_DARK;
    public static SoundEvent PLAYER_FORCE_FAIL;
    public static SoundEvent PLAYER_FORCE_HEAL;
    public static SoundEvent PLAYER_FORCE_LIGHTNING;
    public static SoundEvent PLAYER_FORCE_STEALTH_OFF;
    public static SoundEvent PLAYER_FORCE_STEALTH_ON;
    public static SoundEvent PLAYER_LIGHTSABER_HIT;
    public static SoundEvent PLAYER_LIGHTSABER_OFF;
    public static SoundEvent PLAYER_LIGHTSABER_ON;
    public static SoundEvent PLAYER_LIGHTSABER_SWEET_DREAMS;
    public static SoundEvent PLAYER_LIGHTSABER_SWING;

    private static SoundEvent getRegisteredSoundEvent(String id)
    {

        ResourceLocation location = new ResourceLocation(Lightsabers.MODID, id);
        SoundEvent event = new SoundEvent(location);
        event.setRegistryName(id);
        ForgeRegistries.SOUND_EVENTS.register(event);

        if (event == null)
        {
            throw new IllegalStateException("Invalid Sound requested: " + id);
        }
        else
        {
            return event;
        }
    }

    public static void registerSounds()
    {
        if (!Bootstrap.isRegistered())
        {
            throw new RuntimeException("Accessed Sounds before Bootstrap!");
        }
        else
        {
        	AMBIENT_FORTIFY = getRegisteredSoundEvent("ambient.force.fortify");
        	AMBIENT_STEALTH = getRegisteredSoundEvent("ambient.force.stealth1");
            AMBIENT_LIGHTSABER_HUM_HEAVY = getRegisteredSoundEvent("ambient.lightsaber.hum_heavy");
            AMBIENT_LIGHTSABER_HUM_LIGHT = getRegisteredSoundEvent("ambient.lightsaber.hum_light");
            AMBIENT_LIGHTSABER_HUM_MEDIUM = getRegisteredSoundEvent("ambient.lightsaber.hum_medium");
            BLOCK_HOLOCRON_INVEST = getRegisteredSoundEvent("block.holocron.invest");
            BLOCK_HOLOCRON_UNLOCK = getRegisteredSoundEvent("block.holocron.unlock");
            BLOCK_SITH_SARCOPHAGUS_CLOSE = getRegisteredSoundEvent("block.sith_sarcophagus.close");
            BLOCK_SITH_SARCOPHAGUS_OPEN = getRegisteredSoundEvent("block.sith_sarcophagus.open");
            MOB_LIGHTSABER_HIT = getRegisteredSoundEvent("mob.lightsaber.hit");
            MOB_LIGHTSABER_OFF = getRegisteredSoundEvent("mob.lightsaber.off");
            MOB_LIGHTSABER_ON = getRegisteredSoundEvent("mob.lightsaber.on");
            MOB_LIGHTSABER_SWING = getRegisteredSoundEvent("mob.lightsaber.swing");
            MOB_SITH_GHOST_DEATH = getRegisteredSoundEvent("mob.sith_ghost.death");
            MOB_SITH_GHOST_IDLE = getRegisteredSoundEvent("mob.sith_ghost.idle");
            PLAYER_FORCE_CAST = getRegisteredSoundEvent("player.force.cast");
            PLAYER_FORCE_DARK = getRegisteredSoundEvent("player.force.dark");
            PLAYER_FORCE_FAIL = getRegisteredSoundEvent("player.force.fail");
            PLAYER_FORCE_HEAL = getRegisteredSoundEvent("player.force.heal");
            PLAYER_FORCE_LIGHTNING = getRegisteredSoundEvent("player.force.lightning");
            PLAYER_FORCE_STEALTH_OFF = getRegisteredSoundEvent("player.force.stealth.off");
            PLAYER_FORCE_STEALTH_ON = getRegisteredSoundEvent("player.force.stealth.on");
            PLAYER_LIGHTSABER_HIT = getRegisteredSoundEvent("player.lightsaber.hit");
            PLAYER_LIGHTSABER_OFF = getRegisteredSoundEvent("player.lightsaber.off");
            PLAYER_LIGHTSABER_ON = getRegisteredSoundEvent("player.lightsaber.on");
            PLAYER_LIGHTSABER_SWEET_DREAMS = getRegisteredSoundEvent("player.lightsaber.sweet_dreams");
            PLAYER_LIGHTSABER_SWING = getRegisteredSoundEvent("player.lightsaber.swing");
        }
    }
}
