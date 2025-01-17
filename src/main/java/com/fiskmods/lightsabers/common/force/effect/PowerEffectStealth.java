package com.fiskmods.lightsabers.common.force.effect;

import com.fiskmods.lightsabers.Lightsabers;
import com.fiskmods.lightsabers.client.sound.ALSounds;
import com.fiskmods.lightsabers.client.sound.MovingSoundStatusEffect;
import com.fiskmods.lightsabers.common.data.effect.Effect;
import com.fiskmods.lightsabers.common.force.PowerDesc;
import com.fiskmods.lightsabers.common.force.PowerDesc.Target;
import com.fiskmods.lightsabers.common.force.PowerDesc.Unit;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.Potion;

public class PowerEffectStealth extends PowerEffectStatus
{
    public PowerEffectStealth()
    {
        super(Effect.STEALTH, 0);
    }

    @Override
    public String[] getDesc()
    {
        return new String[] {PowerDesc.create("effect2", Unit.INVISIBILITY, Target.CASTER)};
    }

    @Override
    public void start(EntityPlayer player, Side side)
    {
        if (side.isClient() && Lightsabers.proxy.isClientPlayer(player))
        {
            playSound(player);
        }

        player.setInvisible(true);

        if (Lightsabers.proxy.isClientPlayer(player))
        {
            player.playSound(ALSounds.PLAYER_FORCE_STEALTH_ON, 1.0F, 1.0F);
        }
    }

    @Override
    public void stop(EntityPlayer player, Side side)
    {
        super.stop(player, side);
        
        if (!player.isPotionActive(MobEffects.INVISIBILITY))
        {
            player.setInvisible(false);
        }

        if (Lightsabers.proxy.isClientPlayer(player))
        {
            player.playSound(ALSounds.PLAYER_FORCE_STEALTH_OFF, 1.0F, 1.0F);
        }
    }

    @SideOnly(Side.CLIENT)
    public void playSound(EntityPlayer player)
    {
        Minecraft.getMinecraft().getSoundHandler().playSound(new MovingSoundStatusEffect(player, Effect.STEALTH, ALSounds.AMBIENT_STEALTH));
    }
}
