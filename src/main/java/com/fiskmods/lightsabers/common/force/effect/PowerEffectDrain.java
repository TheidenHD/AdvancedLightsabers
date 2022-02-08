package com.fiskmods.lightsabers.common.force.effect;

import java.util.List;

import com.fiskmods.lightsabers.common.data.ALData;
import com.fiskmods.lightsabers.common.data.effect.Effect;
import com.fiskmods.lightsabers.common.data.effect.StatusEffect;
import com.fiskmods.lightsabers.common.force.Power;
import com.fiskmods.lightsabers.common.force.PowerDesc;
import com.fiskmods.lightsabers.common.force.PowerDesc.Target;
import com.fiskmods.lightsabers.common.force.PowerDesc.Unit;
import com.fiskmods.lightsabers.helper.ALHelper;
import com.google.common.collect.Lists;

import net.minecraftforge.fml.relauncher.Side;
import fiskfille.utils.helper.VectorHelper;
import net.minecraft.command.EntitySelector;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class PowerEffectDrain extends PowerEffect
{
    public PowerEffectDrain(int amplifier)
    {
        super(amplifier);
    }
    
    @Override
    public boolean execute(EntityPlayer player, Side side)
    {
        World world = player.getEntityWorld();
        List<EntityLivingBase> list = getTargets(player);

        for (EntityLivingBase entity : list)
        {
            StatusEffect.add(entity, player, Effect.DRAIN, 30, amplifier);
        }

        if (list.size() > 0)
        {
            ALData.DRAIN_LIFE_TIMER.setWithoutNotify(player, 1.0F);
            return true;
        }

        return false;
    }

    @Override
    public String[] getDesc()
    {
        return new String[] {PowerDesc.create("absorb", PowerDesc.format("%s %s", 4 + amplifier * 2, Unit.HEALTH), amplifier < 2 ? Target.TARGET : Target.ENEMIES)};
    }

    public List<EntityLivingBase> getTargets(EntityPlayer player)
    {
        List<EntityLivingBase> list = Lists.newArrayList();
        World world = player.getEntityWorld();
        double range = amplifier < 2 ? 5 : 7;

        if (amplifier < 2)
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
                    list.add(entity);
                }
            }
        }
        else
        {
            AxisAlignedBB aabb = player.getEntityBoundingBox().expand(range, range, range);
            List<EntityLivingBase> list1 = world.<EntityLivingBase>getEntitiesWithinAABB(EntityLivingBase.class, aabb, EntitySelectors.IS_ALIVE);

            float force = ALData.FORCE_POWER.get(player) + 15;
            float cost = super.getUseCost(player, Effect.DRAIN.getPower(amplifier));

            for (EntityLivingBase entity : list1)
            {
                if (!ALHelper.isAlly(player, entity) && entity != player)
                {
                    if (force >= cost + list.size() * 15 + 15)
                    {
                        list.add(entity);
                    }
                    else
                    {
                        break;
                    }
                }
            }
        }

        return list;
    }

    @Override
    public float getUseCost(EntityPlayer player, Power power)
    {
        float cost = super.getUseCost(player, power);

        if (amplifier >= 2)
        {
            float force = ALData.FORCE_POWER.get(player);

            if (force >= cost)
            {
                cost += (getTargets(player).size() - 1) * 15;
            }
        }

        return cost;
    }
}
