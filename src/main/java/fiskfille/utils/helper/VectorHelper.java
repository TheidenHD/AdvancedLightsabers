package fiskfille.utils.helper;

import java.util.List;

import com.fiskmods.lightsabers.Lightsabers;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class VectorHelper
{
    public static Vec3d getOffsetCoords(EntityLivingBase entity, double xOffset, double yOffset, double zOffset, float partialTicks)
    {
        Vec3d vec3 = new Vec3d(xOffset, yOffset, zOffset);
        vec3.rotatePitch(-(entity.rotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * partialTicks) * (float) Math.PI / 180.0F);
        vec3.rotateYaw(-(entity.rotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * partialTicks) * (float) Math.PI / 180.0F);

        return new Vec3d(entity.posX + vec3.x, entity.posY + getOffset(entity) + vec3.y, entity.posZ + vec3.z);
    }

    public static Vec3d getOffsetCoords(EntityLivingBase entity, double xOffset, double yOffset, double zOffset)
    {
        return getOffsetCoords(entity, xOffset, yOffset, zOffset, 1.0F);
    }

    public static Vec3d copy(Vec3d vec3)
    {
        return new Vec3d(vec3.x, vec3.y, vec3.z);
    }

    public static Vec3d add(Vec3d vec3, Vec3d vec31)
    {
        return new Vec3d(vec3.x + vec31.x, vec3.y + vec31.y, vec3.z + vec31.z);
    }

    public static Vec3d multiply(Vec3d vec3, Vec3d vec31)
    {
        return new Vec3d(vec3.x * vec31.x, vec3.y * vec31.y, vec3.z * vec31.z);
    }

    public static Vec3d multiply(Vec3d vec3, double factor)
    {
        return multiply(vec3, new Vec3d(factor, factor, factor));
    }

    public static Vec3d interpolate(Vec3d vec3, Vec3d vec31, double distance)
    {
        double d = distance / vec3.distanceTo(vec31);
        return add(multiply(vec3, d), multiply(vec31, 1 - d));
    }

    public static Vec3d centerOf(Entity entity)
    {
        return new Vec3d(entity.posX, entity.getCollisionBoundingBox().minY + entity.height / 2, entity.posZ);
    }

    public static Vec3d getPosition(Entity entity, float partialTicks)
    {
        if (partialTicks == 1.0F)
        {
            return new Vec3d(entity.posX, entity.posY, entity.posZ);
        }
        else
        {
            double d0 = entity.prevPosX + (entity.posX - entity.prevPosX) * partialTicks;
            double d1 = entity.prevPosY + (entity.posY - entity.prevPosY) * partialTicks;
            double d2 = entity.prevPosZ + (entity.posZ - entity.prevPosZ) * partialTicks;
            return new Vec3d(d0, d1, d2);
        }
    }

    public static double getOffset(EntityLivingBase entity)
    {
        double yOffset = entity.getEyeHeight();

        if (entity instanceof EntityPlayer && entity.getEntityWorld().isRemote)
        {
            yOffset -= ((EntityPlayer) entity).getDefaultEyeHeight();

            if (!Lightsabers.proxy.isClientPlayer(entity))
            {
                yOffset += 1.62F;
            }
        }

        return yOffset;
    }

    public static Vec3d getBackSideCoordsRenderYawOffset(EntityLivingBase entity, double amount, boolean side, double backAmount, boolean pitch)
    {
        Vec3d front = getFrontCoordsRenderYawOffset(entity, backAmount, pitch).add(-entity.posX, -(entity.posY + getOffset(entity)), -entity.posZ);
        return getSideCoordsRenderYawOffset(entity, amount, side).add(front.x, front.y, front.z);
    }

    public static Vec3d getSideCoordsRenderYawOffset(EntityLivingBase entity, double amount, boolean side)
    {
        return getSideCoordsRenderYawOffset(entity, amount, side ? -90 : 90);
    }

    public static Vec3d getSideCoordsRenderYawOffset(EntityLivingBase entity, double amount, int side)
    {
        float pitch = 0;
        float yaw = entity.renderYawOffset + side;
        float f3 = MathHelper.cos(-yaw * 0.017453292F - (float) Math.PI);
        float f4 = MathHelper.sin(-yaw * 0.017453292F - (float) Math.PI);
        float f5 = -MathHelper.cos(-pitch * 0.017453292F);
        float yScale = MathHelper.sin(-pitch * 0.017453292F);
        float xScale = f4 * f5;
        float zScale = f3 * f5;
        return new Vec3d(entity.posX, entity.posY + getOffset(entity), entity.posZ).add(xScale * amount, yScale * amount, zScale * amount);
    }

    public static Vec3d getFrontCoordsRenderYawOffset(EntityLivingBase entity, double amount, boolean applyPitch)
    {
        float pitch = applyPitch ? entity.rotationPitch : 0;
        float yaw = entity.renderYawOffset;

        float f3 = MathHelper.cos(-yaw * 0.017453292F - (float) Math.PI);
        float f4 = MathHelper.sin(-yaw * 0.017453292F - (float) Math.PI);
        float f5 = -MathHelper.cos(-pitch * 0.017453292F);
        float yScale = MathHelper.sin(-pitch * 0.017453292F);
        float xScale = f4 * f5;
        float zScale = f3 * f5;
        return new Vec3d(entity.posX, entity.posY + getOffset(entity), entity.posZ).add(xScale * amount, yScale * amount, zScale * amount);
    }

    public static <T extends Entity> List<T> getEntitiesNear(Class<T> type, World world, double x, double y, double z, double radius)
    {
        return world.getEntitiesWithinAABB(type, new AxisAlignedBB(x - radius, y - radius, z - radius, x + radius, y + radius, z + radius));
    }

    public static <T extends Entity> List<T> getEntitiesNear(Class<T> type, World world, Vec3d vec3, double radius)
    {
        return getEntitiesNear(type, world, vec3.x, vec3.y, vec3.z, radius);
    }
}
