package com.fiskmods.lightsabers.common.force.effect;

import com.fiskmods.lightsabers.client.sound.ALSounds;
import com.fiskmods.lightsabers.common.force.ForceSide;
import com.fiskmods.lightsabers.common.force.PowerDesc;
import com.fiskmods.lightsabers.common.force.PowerDesc.Unit;
import com.fiskmods.lightsabers.common.item.ItemLightsaberBase;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.TextFormatting;

public class PowerEffectBladeThrow extends PowerEffect
{
    public PowerEffectBladeThrow(int amplifier)
    {
        super(amplifier);
    }
    
    @Override
    public boolean execute(EntityPlayer player, Side side)
    {
        ItemStack heldItem = player.getHeldItemMainhand();

        if (heldItem != null && heldItem.getItem() instanceof ItemLightsaberBase && ItemLightsaberBase.isActive(heldItem))
        {
            if (side.isServer())
            {
                ItemLightsaberBase.throwLightsaber(player, heldItem, amplifier);
                //player.swingItem(); //TODO
            }

            return true;
        }

        return false;
    }

    @Override
    public String[] getDesc()
    {
        return new String[][] {{}, {PowerDesc.create("multiply", Unit.IMPACT_RADIUS, PowerDesc.format("%s%s", TextFormatting.GRAY, 2))}}[amplifier];
    }

    @Override
    public SoundEvent getCastSound(ForceSide side)
    {
        return ALSounds.PLAYER_LIGHTSABER_SWING;
    }

    @Override
    public float getCastSoundPitch(ForceSide side)
    {
        return 1;
    }
}
