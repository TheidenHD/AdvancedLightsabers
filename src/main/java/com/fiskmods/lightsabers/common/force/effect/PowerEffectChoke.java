package com.fiskmods.lightsabers.common.force.effect;

import com.fiskmods.lightsabers.common.data.effect.Effect;
import com.fiskmods.lightsabers.common.data.effect.StatusEffect;
import com.fiskmods.lightsabers.common.force.PowerDesc;
import com.fiskmods.lightsabers.common.force.PowerDesc.Target;
import com.fiskmods.lightsabers.common.force.PowerDesc.Unit;

import net.minecraftforge.fml.relauncher.Side;
import fiskfille.utils.helper.VectorHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class PowerEffectChoke extends PowerEffect
{
    public PowerEffectChoke(int amplifier)
    {
        super(amplifier);
    }

    @Override
    public boolean execute(EntityPlayer player, Side side)
    {
        World world = player.getEntityWorld();
        double range = 16;

        Vec3d src = VectorHelper.getOffsetCoords(player, 0, 0, 0);
        Vec3d dest = VectorHelper.getOffsetCoords(player, 0, 0, range);
        Vec3d hitVec = null;
        RayTraceResult rayTrace = world.rayTraceBlocks(VectorHelper.copy(src), VectorHelper.copy(dest));

        if (rayTrace == null)
        {
            hitVec = dest;
        }
        else
        {
            hitVec = rayTrace.hitVec;
        }

        double distance = player.getDistance(hitVec.x, hitVec.y, hitVec.z);

        for (double point = 0; point <= distance; point += 0.15D)
        {
            Vec3d particleVec = VectorHelper.getOffsetCoords(player, 0, 0, point);

            for (EntityLivingBase entity : VectorHelper.getEntitiesNear(EntityLivingBase.class, world, particleVec, 0.5F))
            {
                if (entity != null && entity != player && player.getRidingEntity() != entity)
                {
                    hitVec = new Vec3d(entity.posX, entity.posY, entity.posZ);
                    rayTrace = new RayTraceResult(entity, hitVec);
                    distance = player.getDistance(hitVec.x, hitVec.y, hitVec.z);
                    break;
                }
            }
        }

        if (rayTrace != null && rayTrace.typeOfHit == RayTraceResult.Type.ENTITY && rayTrace.entityHit instanceof EntityLivingBase)
        {
            StatusEffect.add((EntityLivingBase) rayTrace.entityHit, player, Effect.CHOKE, 60, amplifier);
            return true;
        }

        return false;
    }

    @Override
    public String[] getDesc()
    {
        return new String[] {PowerDesc.create("effect", PowerDesc.format("%s %s%s", 2 + amplifier * 2, Unit.DAMAGE, TextFormatting.GRAY + "/"), Target.TARGET), PowerDesc.create("effect", PowerDesc.format("%s %s%s", Effect.STUN, TextFormatting.GRAY, getStunDuration(amplifier)), Target.TARGET)};
    }

    public static float getStunDuration(int amplifier)
    {
        float f = 1.5F;

        if (amplifier > 0)
        {
            f += 1 + (amplifier - 1) * 0.5F;
        }

        return f;
    }
}
