package com.fiskmods.lightsabers.common.force.effect;

import java.util.List;

import com.fiskmods.lightsabers.common.data.effect.Effect;
import com.fiskmods.lightsabers.common.data.effect.StatusEffect;
import com.fiskmods.lightsabers.common.force.PowerDesc;
import com.fiskmods.lightsabers.common.force.PowerDesc.Target;
import com.fiskmods.lightsabers.helper.ALHelper;

import net.minecraftforge.fml.relauncher.Side;
import fiskfille.utils.helper.VectorHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class PowerEffectStun extends PowerEffect
{
    public final float duration;
    public final int durationInt;
    
    public final boolean aoe;
    
    public PowerEffectStun(int amplifier, float duration, boolean aoe)
    {
        super(amplifier);
        this.duration = duration;
        this.durationInt = (int) duration * 20;
        this.aoe = aoe;
    }
    
    @Override
    public boolean execute(EntityPlayer player, Side side)
    {
        World world = player.getEntityWorld();
        double range = 16;

        if (aoe)
        {
            AxisAlignedBB aabb = player.getEntityBoundingBox().expand(10, 10, 10);
            List<EntityLivingBase> list = player.getEntityWorld().<EntityLivingBase>getEntitiesWithinAABB(EntityLivingBase.class, aabb, EntitySelectors.IS_ALIVE);

            for (EntityLivingBase entity : list)
            {
                if (!ALHelper.isAlly(player, entity))
                {
                    if (entity != player)
                    {
                        StatusEffect.add(entity, Effect.STUN, durationInt, amplifier);
                    }
                }
            }

            return true;
        }
        else
        {
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
                    StatusEffect.add(entity, Effect.STUN, durationInt, amplifier);

                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public String[] getDesc()
    {
        return new String[] {PowerDesc.create("effect", PowerDesc.format("%s %s%s", Effect.STUN, TextFormatting.GRAY, duration), aoe ? Target.ENEMIES : Target.TARGET)};
    }
}
