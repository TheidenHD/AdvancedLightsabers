package com.fiskmods.lightsabers.common.entity.ai;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.EnumDifficulty;

public class EntityAIBreakBlock extends EntityAIBlockInteract
{
    private int breakingTime;
    private int field_75358_j = -1;

    public EntityAIBreakBlock(EntityLiving entity)
    {
        super(entity);
    }

    @Override
    public boolean shouldExecute()
    {
        return super.shouldExecute() && theEntity.getEntityWorld().getGameRules().getBoolean("mobGriefing");
    }

    @Override
    public void startExecuting()
    {
        super.startExecuting();
        breakingTime = 0;
    }

    @Override
    public boolean shouldContinueExecuting()
    {
        double d0 = theEntity.getDistanceSq(entityPosX, entityPosY, entityPosZ);
        return breakingTime <= 60;
    }

    @Override
    public void resetTask()
    {
        super.resetTask();
        theEntity.getEntityWorld().sendBlockBreakProgress(theEntity.getEntityId(), new BlockPos(entityPosX, entityPosY, entityPosZ), -1);
    }

    @Override
    public void updateTask()
    {
        super.updateTask();
        ++breakingTime;
        int i = (int) (breakingTime / 60.0F * 10.0F);

        if (i != field_75358_j)
        {
            theEntity.getEntityWorld().sendBlockBreakProgress(theEntity.getEntityId(), new BlockPos(entityPosX, entityPosY, entityPosZ), i);
            field_75358_j = i;
        }

        if (breakingTime == 60 && theEntity.getEntityWorld().getDifficulty() == EnumDifficulty.HARD)
        {
            theEntity.getEntityWorld().setBlockToAir(new BlockPos(entityPosX, entityPosY, entityPosZ));
            theEntity.getEntityWorld().playEvent(2001, new BlockPos(entityPosX, entityPosY, entityPosZ), Block.getIdFromBlock(field_151504_e));
        }
    }
}
