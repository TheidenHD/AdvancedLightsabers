package com.fiskmods.lightsabers.common.interaction.key;

import com.fiskmods.lightsabers.ALConstants;
import com.fiskmods.lightsabers.client.sound.ALSounds;
import com.fiskmods.lightsabers.common.data.ALData;
import com.fiskmods.lightsabers.common.force.Power;
import com.fiskmods.lightsabers.common.force.PowerManager;
import com.fiskmods.lightsabers.common.force.PowerType;
import com.fiskmods.lightsabers.common.keybinds.ALKeyBinds;
import com.fiskmods.lightsabers.helper.ALHelper;

import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import fiskfille.utils.common.interaction.InteractionHandler.InteractionType;
import fiskfille.utils.common.interaction.key.KeyPressBase;
import fiskfille.utils.common.keybinds.FiskKeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.StringUtils;

public class KeyPressForceUse extends KeyPressBase
{
    @Override
    @SideOnly(Side.CLIENT)
    public FiskKeyBinding getKey(EntityPlayer player)
    {
        return ALKeyBinds.ACTIVATE_POWER;
    }
    
    @Override
    public boolean clientRequirements(EntityPlayer player, InteractionType type, int x, int y, int z)
    {
        return super.clientRequirements(player, type, x, y, z) && execute(player, Side.CLIENT);
    }

    @Override
    public void receive(EntityPlayer sender, EntityPlayer clientPlayer, InteractionType type, Side side, int x, int y, int z, Integer[] args)
    {
        if (side.isServer())
        {
            execute(sender, side);
        }
    }
    
    public boolean execute(EntityPlayer player, Side side)
    {
        Power power = PowerManager.getSelectedPower(player);
        
        if (power != null && ALData.USE_POWER_COOLDOWN.get(player) == 0 && ALHelper.getForcePowerMax(player) > 0 && power.powerStats.powerType == PowerType.PER_USE)
        {
            if (ALData.FORCE_POWER.get(player) >= power.getUseCost(player) && power.powerEffect.execute(player, side))
            {
                if (side.isServer())
                {
                    SoundEvent sound = power.powerEffect.getCastSound(power.getSide());

                    if (!StringUtils.isNullOrEmpty(sound.getSoundName().toString()))//TODO
                    {
                        player.getEntityWorld().playSound(player, player.posX, player.posY, player.posZ, sound, SoundCategory.PLAYERS, power.powerEffect.getCastSoundVolume(power.getSide()), power.powerEffect.getCastSoundPitch(power.getSide()));
                    }

                    ALData.FORCE_POWER.incr(player, -power.getUseCost(player));
                    ALData.USE_POWER_COOLDOWN.set(player, ALConstants.FORCE_POWER_COOLDOWN);
                }
                
                return true;
            }
            else if (side.isClient())
            {
                player.playSound(ALSounds.PLAYER_FORCE_FAIL, 1, 1);
            }
        }
        
        return false;
    }
    
    @Override
    public boolean syncWithServer()
    {
        return true;
    }
    
    @Override
    public TargetPoint getTargetPoint(EntityPlayer player, int x, int y, int z)
    {
        return TARGET_NONE;
    }
}
