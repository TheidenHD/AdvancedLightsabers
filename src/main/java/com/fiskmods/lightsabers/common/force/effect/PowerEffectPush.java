package com.fiskmods.lightsabers.common.force.effect;

import com.fiskmods.lightsabers.common.damage.ALDamageSources;
import com.fiskmods.lightsabers.common.data.ALData;
import com.fiskmods.lightsabers.common.force.PowerDesc;
import com.fiskmods.lightsabers.common.force.PowerDesc.Target;
import com.fiskmods.lightsabers.common.force.PowerDesc.Unit;

import net.minecraftforge.fml.relauncher.Side;
import fiskfille.utils.helper.VectorHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class PowerEffectPush extends PowerEffect
{
    public PowerEffectPush(int amplifier)
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

        if (rayTrace != null)
        {
            if (rayTrace.typeOfHit == RayTraceResult.Type.ENTITY && rayTrace.entityHit instanceof EntityLivingBase)
            {
                EntityLivingBase entity = (EntityLivingBase) rayTrace.entityHit;

                if (!entity.getEntityWorld().isRemote)
                {
                    entity.attackEntityFrom(ALDamageSources.causeForceDamage(player), getDamage(amplifier));
                    //ALEntityData.getData(entity).forcePushed = true;//TODO
                }

                Vec3d vec3 = VectorHelper.getOffsetCoords(player, 0, 0, 0.5F * getKnockback(amplifier));
                entity.motionX += (vec3.x - src.x);
                entity.motionY += (vec3.y - src.y);
                entity.motionZ += (vec3.z - src.z);
                ALData.FORCE_PUSHING_TIMER.setWithoutNotify(player, 1.0F);

                return true;
            }
        }

        return false;
    }

    @Override
    public String[] getDesc()
    {
        return new String[]
        {
            PowerDesc.create("effect2", PowerDesc.format("+%s %s", getKnockback(amplifier), Unit.KNOCKBACK), Target.TARGET),
            PowerDesc.create("effect2", PowerDesc.format("%s %s", getDamage(amplifier), Unit.DAMAGE), Target.TARGET)
        };
    }

    public static float getKnockback(int amplifier)
    {
        int i = 1;

        for (int j = 0; j < amplifier; ++j)
        {
            i *= 2;
        }

        return 3 + i;
    }

    public static float getDamage(int amplifier)
    {
        float f = 1;

        for (int j = 0; j < amplifier; ++j)
        {
            f *= f + 0.5F;
        }

        return f;
    }
}
