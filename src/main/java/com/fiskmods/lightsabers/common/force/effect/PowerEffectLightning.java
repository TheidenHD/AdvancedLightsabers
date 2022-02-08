package com.fiskmods.lightsabers.common.force.effect;

import com.fiskmods.lightsabers.client.sound.MovingSoundLightning;
import com.fiskmods.lightsabers.common.damage.ALDamageSources;
import com.fiskmods.lightsabers.common.data.effect.Effect;
import com.fiskmods.lightsabers.common.force.PowerDesc;
import com.fiskmods.lightsabers.common.force.PowerDesc.Target;
import com.fiskmods.lightsabers.common.force.PowerDesc.Unit;
import com.fiskmods.lightsabers.helper.ALHelper;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class PowerEffectLightning extends PowerEffectStatus
{
    public PowerEffectLightning(int amplifier)
    {
        super(Effect.LIGHTNING, amplifier);
    }

    @Override
    public String[] getDesc()
    {
        return new String[] {PowerDesc.create("effect", PowerDesc.format("%s %s%s", 4 + amplifier * 2, Unit.DAMAGE, TextFormatting.GRAY + "/"), Target.TARGET)};
    }

    @Override
    public void start(EntityPlayer player, Side side)
    {
        if (side.isClient())
        {
            playSound(player);
        }
    }

    @SideOnly(Side.CLIENT)
    public void playSound(EntityPlayer player)
    {
        Minecraft.getMinecraft().getSoundHandler().playSound(new MovingSoundLightning(player));
    }

    @Override
    public boolean execute(EntityPlayer player, Side side)
    {
        World world = player.getEntityWorld();
        boolean flag = super.execute(player, side);

        if (flag)
        {
            EntityLivingBase entity = ALHelper.getForceLightningTarget(player);

            if (entity != null)
            {
                entity.attackEntityFrom(ALDamageSources.causeForceLightningDamage(player), 4 + amplifier * 2);
                entity.motionX = 0;
                entity.motionY = Math.min(entity.motionY, 0);
                entity.motionZ = 0;
            }
        }

        return flag;
    }
}
