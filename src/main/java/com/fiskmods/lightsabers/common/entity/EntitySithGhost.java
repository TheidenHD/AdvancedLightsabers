package com.fiskmods.lightsabers.common.entity;

import java.util.List;

import com.fiskmods.lightsabers.Lightsabers;
import com.fiskmods.lightsabers.common.entity.ai.EntityAIBreakBlock;
import com.fiskmods.lightsabers.common.entity.ai.EntityAIRest;
import com.fiskmods.lightsabers.common.item.ItemLightsaberBase;
import com.fiskmods.lightsabers.common.lightsaber.CrystalColor;
import com.fiskmods.lightsabers.common.lightsaber.LightsaberData;
import com.fiskmods.lightsabers.common.network.ALNetworkManager;
import com.fiskmods.lightsabers.common.network.PacketThrowLightsaber;
import com.fiskmods.lightsabers.common.network.PacketTileAction;
import com.fiskmods.lightsabers.common.tileentity.TileEntitySithStoneCoffin;
import com.google.common.collect.Lists;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIOpenDoor;
import net.minecraft.entity.ai.EntityAIRestrictOpenDoor;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class EntitySithGhost extends EntityMob
{
    public boolean hasRestingPlace;
    public int restingPlaceX;
    public int restingPlaceY;
    public int restingPlaceZ;

    public int throwLightsaberCooldown;
    public int swingItemCooldown;
    public int strafeTimer;
    public int strafe = 1;
    public int taskFinished;

    public EntitySithGhost(World world)
    {
        super(world);
//        getNavigator().setAvoidsWater(true); //TODO
//        getNavigator().setBreakDoors(true);
//        getNavigator().setEnterDoors(true);
//        tasks.addTask(0, new EntityAISwimming(this));
//        tasks.addTask(0, new EntityAIBreakBlock(this));
//        tasks.addTask(1, new EntityAIAttackOnCollide(this, 0.5D, true));
//        tasks.addTask(2, new EntityAIRest(this, 0.4D));
//        tasks.addTask(2, new EntityAIWatchClosest(this, EntityPlayer.class, 4F, 10F));
//        tasks.addTask(3, new EntityAIRestrictOpenDoor(this));
//        tasks.addTask(4, new EntityAIOpenDoor(this, true));
//        tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 0.6D));
//        targetTasks.addTask(1, new EntityAIHurtByTarget(this, true));
//        targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, false));
    }

    @Override
    public boolean attackEntityAsMob(Entity entity)
    {
        if (entity instanceof EntitySithGhost)
        {
            return false;
        }

        boolean flag = super.attackEntityAsMob(entity);

        if (flag)
        {
            //swingItem(); //TODO
        }

        return flag;
    }

    @Override
    public boolean attackEntityFrom(DamageSource damageSource, float damage)
    {
        if (damageSource == DamageSource.IN_WALL)
        {
            return false;
        }

        return super.attackEntityFrom(damageSource, Math.min(damage, 10));
    }

//    @Override //TODO
//    public void swingItem()
//    {
//        if (swingItemCooldown == 0)
//        {
//            swingItemCooldown = 5;
//            super.swingItem();
//        }
//    }

    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(60);
        getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(4);
        getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.6D);
        getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(1);
        getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(32);
    }

//    @Override //TODO
//    public boolean isAIEnabled()
//    {
//        return true;
//    }

    @Override
    protected float getSoundPitch()
    {
        return super.getSoundPitch();
    }

//    @Override //TODO
//    protected String getLivingSound()
//    {
//        return Lightsabers.MODID + ":mob.sith_ghost.idle";
//    }
//
//    @Override
//    protected String getHurtSound()
//    {
//        return null;
//    }
//
//    @Override
//    protected SoundEvent getDeathSound()
//    {
//        return Lightsabers.MODID + ":mob.sith_ghost.death";
//    }

    @Override
    public void onUpdate()
    {
        super.onUpdate();
        EntityLivingBase target = getAttackTarget();
        ItemStack heldItem = getHeldItemMainhand();

        if (throwLightsaberCooldown > 0)
        {
            --throwLightsaberCooldown;
        }

        if (swingItemCooldown > 0)
        {
            --swingItemCooldown;
        }

        if (--strafeTimer <= 0)
        {
            strafe *= -1;
            strafeTimer = 100 + rand.nextInt(1000);
        }

        if (heldItem != null)
        {
            if (ticksExisted > 5 && !world.isRemote && !ItemLightsaberBase.isActive(heldItem))
            {
                ItemLightsaberBase.ignite(this, true);
            }

            if (target != null && target.isEntityAlive())
            {
                faceEntity(target, 100, 100);

                if (ItemLightsaberBase.isActive(heldItem) && getDistance(target) > 5 && canEntityBeSeen(target) && throwLightsaberCooldown == 0)
                {
                    throwLightsaberCooldown = 40 + rand.nextInt(60);
                    //swingItem(); //TODO
                    ALNetworkManager.wrapper.sendToServer(new PacketThrowLightsaber(this, heldItem));
                }

                if (getDistance(target) < 5 && canEntityBeSeen(target))
                {
                    //moveEntityWithHeading(0.3F * strafe, 0); //TODO
                }
            }
        }

        if (target instanceof EntitySithGhost)
        {
            //setAttackTarget(world.getClosestVulnerablePlayerToEntity(this, 32)); //TODO
        }

        if (target != null)
        {
            taskFinished = 1;
        }
        else if (taskFinished == 1)
        {
            taskFinished = 2;
        }

        if (taskFinished == 2)
        {
            int x = MathHelper.floor(posX);
            int y = MathHelper.floor(posY);
            int z = MathHelper.floor(posZ);

            if (getDistance(restingPlaceX, restingPlaceY, restingPlaceZ) <= 2D)
            {
                taskFinished = 3;

//                if (world.getTileEntity(restingPlaceX, restingPlaceY, restingPlaceZ) instanceof TileEntitySithStoneCoffin)
//                { //TODO
//                    TileEntitySithStoneCoffin tile = (TileEntitySithStoneCoffin) world.getTileEntity(restingPlaceX, restingPlaceY, restingPlaceZ);
//                    tile.equipment = getEquipmentInSlot(0);
//                    world.playAuxSFXAtEntity(null, 1017, x, y, z, 0);
//                    setDead();
//                    ALNetworkManager.wrapper.sendToServer(new PacketTileAction(this, restingPlaceX, restingPlaceY, restingPlaceZ, 0));
//                }
            }
        }
    }

    @Override
    public void setDead()
    {
        super.setDead();

        if (world.isRemote)
        {
            for (int i = 0; i < 128; ++i)
            {
                double d0 = (rand.nextFloat() * 2 - 1) * 1.2;
                double d1 = rand.nextFloat() * 2.4 - 1;
                double d2 = (rand.nextFloat() * 2 - 1) * 1.2;
                double d3 = posX + d0 * width;
                double d4 = getEntityBoundingBox().minY + d1 * height;
                double d5 = posZ + d2 * width;
                world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, d3, d4, d5, 0, 0, 0);
            }
        }
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound nbt)
    {
        super.readEntityFromNBT(nbt);
        hasRestingPlace = nbt.getBoolean("HasRestingPlace");
        restingPlaceX = nbt.getInteger("RestX");
        restingPlaceY = nbt.getInteger("RestY");
        restingPlaceZ = nbt.getInteger("RestZ");
        throwLightsaberCooldown = nbt.getInteger("ThrowCooldown");
        swingItemCooldown = nbt.getInteger("SwingCooldown");
        strafeTimer = nbt.getInteger("StrafeTimer");
        strafe = nbt.getInteger("Strafe");
        taskFinished = nbt.getInteger("TaskFinished");
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound nbt)
    {
        super.writeEntityToNBT(nbt);
        nbt.setBoolean("HasRestingPlace", hasRestingPlace);
        nbt.setInteger("RestX", restingPlaceX);
        nbt.setInteger("RestY", restingPlaceY);
        nbt.setInteger("RestZ", restingPlaceZ);
        nbt.setInteger("ThrowCooldown", throwLightsaberCooldown);
        nbt.setInteger("SwingCooldown", swingItemCooldown);
        nbt.setInteger("StrafeTimer", strafeTimer);
        nbt.setInteger("Strafe", strafe);
        nbt.setInteger("TaskFinished", taskFinished);
    }

    @Override
    protected boolean canTriggerWalking()
    {
        return false;
    }

//    @Override //TODO
//    public IEntityLivingData onSpawnWithEgg(IEntityLivingData entityData)
//    {
//        if (getEquipmentInSlot(0) == null)
//        {
//            setCurrentItemOrArmor(0, LightsaberData.createRandom(rand, CrystalColor.RED));
//        }
//
//        for (int i = 0; i < equipmentDropChances.length; ++i)
//        {
//            equipmentDropChances[i] = 0;
//        }
//
//        return super.onSpawnWithEgg(entityData);
//    }
//
//    @Override
//    public void moveEntity(double offsetX, double offsetY, double offsetZ)
//    {
//        if (noClip)
//        {
//            //		    if (!flag)
//            //		    {
//            //		        boundingBox.offset(offsetX, offsetY, offsetZ);
//            //		        posX = (boundingBox.minX + boundingBox.maxX) / 2;
//            //		        posY = boundingBox.minY + yOffset - ySize;
//            //		        posZ = (boundingBox.minZ + boundingBox.maxZ) / 2;
//            //		        return;
//            //		    }
//
//            world.theProfiler.startSection("move");
//            ySize *= 0.4F;
//            double d3 = posX;
//            double d4 = posY;
//            double d5 = posZ;
//            double d6 = offsetX;
//            double d7 = offsetY;
//            double d8 = offsetZ;
//            AxisAlignedBB axisalignedbb = boundingBox.copy();
//            boolean flag1 = false;
//
//            List list = getCollidingBoundingBoxes(boundingBox.addCoord(offsetX, offsetY, offsetZ));
//
//            for (Object aList : list)
//            {
//                offsetY = ((AxisAlignedBB) aList).calculateYOffset(boundingBox, offsetY);
//            }
//
//            boundingBox.offset(0, offsetY, 0);
//
//            if (!field_70135_K && d7 != offsetY)
//            {
//                offsetZ = 0;
//                offsetY = 0;
//                offsetX = 0;
//            }
//
//            boolean flag2 = onGround || d7 != offsetY && d7 < 0;
//            int j;
//
//            for (j = 0; j < list.size(); ++j)
//            {
//                offsetX = ((AxisAlignedBB) list.get(j)).calculateXOffset(boundingBox, offsetX);
//            }
//
//            boundingBox.offset(offsetX, 0, 0);
//
//            if (!field_70135_K && d6 != offsetX)
//            {
//                offsetZ = 0;
//                offsetY = 0;
//                offsetX = 0;
//            }
//
//            for (j = 0; j < list.size(); ++j)
//            {
//                offsetZ = ((AxisAlignedBB) list.get(j)).calculateZOffset(boundingBox, offsetZ);
//            }
//
//            boundingBox.offset(0, 0, offsetZ);
//
//            if (!field_70135_K && d8 != offsetZ)
//            {
//                offsetZ = 0;
//                offsetY = 0;
//                offsetX = 0;
//            }
//
//            double d10;
//            double d11;
//            int k;
//            double d12;
//
//            if (stepHeight > 0 && flag2 && (flag1 || ySize < 0.05F) && (d6 != offsetX || d8 != offsetZ))
//            {
//                d12 = offsetX;
//                d10 = offsetY;
//                d11 = offsetZ;
//                offsetX = d6;
//                offsetY = stepHeight;
//                offsetZ = d8;
//                AxisAlignedBB axisalignedbb1 = boundingBox.copy();
//                boundingBox.setBB(axisalignedbb);
//                list = getCollidingBoundingBoxes(boundingBox.addCoord(d6, offsetY, d8));
//
//                for (k = 0; k < list.size(); ++k)
//                {
//                    offsetY = ((AxisAlignedBB) list.get(k)).calculateYOffset(boundingBox, offsetY);
//                }
//
//                boundingBox.offset(0, offsetY, 0);
//
//                if (!field_70135_K && d7 != offsetY)
//                {
//                    offsetZ = 0;
//                    offsetY = 0;
//                    offsetX = 0;
//                }
//
//                for (k = 0; k < list.size(); ++k)
//                {
//                    offsetX = ((AxisAlignedBB) list.get(k)).calculateXOffset(boundingBox, offsetX);
//                }
//
//                boundingBox.offset(offsetX, 0, 0);
//
//                if (!field_70135_K && d6 != offsetX)
//                {
//                    offsetZ = 0;
//                    offsetY = 0;
//                    offsetX = 0;
//                }
//
//                for (k = 0; k < list.size(); ++k)
//                {
//                    offsetZ = ((AxisAlignedBB) list.get(k)).calculateZOffset(boundingBox, offsetZ);
//                }
//
//                boundingBox.offset(0, 0, offsetZ);
//
//                if (!field_70135_K && d8 != offsetZ)
//                {
//                    offsetZ = 0;
//                    offsetY = 0;
//                    offsetX = 0;
//                }
//
//                if (!field_70135_K && d7 != offsetY)
//                {
//                    offsetZ = 0;
//                    offsetY = 0;
//                    offsetX = 0;
//                }
//                else
//                {
//                    offsetY = -stepHeight;
//
//                    for (k = 0; k < list.size(); ++k)
//                    {
//                        offsetY = ((AxisAlignedBB) list.get(k)).calculateYOffset(boundingBox, offsetY);
//                    }
//
//                    boundingBox.offset(0, offsetY, 0);
//                }
//
//                if (d12 * d12 + d11 * d11 >= offsetX * offsetX + offsetZ * offsetZ)
//                {
//                    offsetX = d12;
//                    offsetY = d10;
//                    offsetZ = d11;
//                    boundingBox.setBB(axisalignedbb1);
//                }
//            }
//
//            world.theProfiler.endSection();
//            world.theProfiler.startSection("rest");
//            posX = (boundingBox.minX + boundingBox.maxX) / 2;
//            posY = boundingBox.minY + yOffset - ySize;
//            posZ = (boundingBox.minZ + boundingBox.maxZ) / 2;
//            isCollidedHorizontally = d6 != offsetX || d8 != offsetZ;
//            isCollidedVertically = d7 != offsetY;
//            onGround = d7 != offsetY && d7 < 0;
//            isCollided = isCollidedHorizontally || isCollidedVertically;
//
//            if (d6 != offsetX)
//            {
//                motionX = 0;
//            }
//
//            if (d7 != offsetY)
//            {
//                motionY = 0;
//            }
//
//            if (d8 != offsetZ)
//            {
//                motionZ = 0;
//            }
//
//            d12 = posX - d3;
//            d10 = posY - d4;
//            d11 = posZ - d5;
//
//            world.theProfiler.endSection();
//        }
//        else
//        {
//            super.moveEntity(offsetX, offsetY, offsetZ);
//        }
//    }

    public List getCollidingBoundingBoxes(AxisAlignedBB aabb)
    {
        List<AxisAlignedBB> collidingBoundingBoxes = Lists.newArrayList();

        int minX = MathHelper.floor(aabb.minX);
        int maxX = MathHelper.floor(aabb.maxX + 1);
        int minY = MathHelper.floor(aabb.minY);
        int maxY = MathHelper.floor(aabb.maxY + 1);
        int minZ = MathHelper.floor(aabb.minZ);
        int maxZ = MathHelper.floor(aabb.maxZ + 1);

        for (int x = minX; x < maxX; ++x)
        {
            for (int z = minZ; z < maxZ; ++z)
            {
                if (world.isBlockLoaded(new BlockPos(x, 64, z)))
                {
                    for (int y = minY - 1; y < maxY; ++y)
                    {
                        IBlockState block;

                        if (x >= -30000000 && x < 30000000 && z >= -30000000 && z < 30000000)
                        {
                            block = world.getBlockState(new BlockPos(x, y, z));
                        }
                        else
                        {
                            block = Blocks.STONE.getDefaultState();
                        }

                        if (y <= minY)
                        {
                            block.addCollisionBoxToList(world, new BlockPos(x, y, z), aabb, collidingBoundingBoxes, this, false);
                        }
                    }
                }
            }
        }

        double d0 = 0.25D;
        List<Entity> list = world.getEntitiesWithinAABBExcludingEntity(this, aabb.expand(d0, d0, d0));

        for (Entity entity : list)
        {
            AxisAlignedBB aabb1 = entity.getEntityBoundingBox();//TODO check

            if (aabb1 != null && aabb1.intersects(aabb))
            {
                collidingBoundingBoxes.add(aabb1);
            }

            aabb1 = getCollisionBox(entity);

            if (aabb1 != null && aabb1.intersects(aabb))
            {
                collidingBoundingBoxes.add(aabb1);
            }
        }

        return collidingBoundingBoxes;
    }
}
