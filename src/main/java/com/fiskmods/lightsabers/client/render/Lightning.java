package com.fiskmods.lightsabers.client.render;

import java.util.List;

import com.google.common.collect.Lists;

import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class Lightning
{
    public List<Lightning> children = Lists.newArrayList();
    public Lightning parent;

    public float length;
    public float scale;
    public float rotateAngleX;
    public float rotateAngleY;
    public float rotateAngleZ;
    public Vec3d lightningColor;

    public Lightning(float f)
    {
        this(f, new Vec3d(1, 1, 1));
    }

    public Lightning(float f, Vec3d color)
    {
        length = f;
        lightningColor = color;
    }

    public void onUpdate(World world)
    {
//        if (parent == null)
//        {
//            Lightning lightning = SHHelper.createLightning(lightningType, 0, length).setScale(scale);
//            children.clear();
//            children.addAll(lightning.children);
//            scale = lightning.scale;
//            rotateAngleX = lightning.rotateAngleX;
//            rotateAngleY = lightning.rotateAngleY;
//            rotateAngleZ = lightning.rotateAngleZ;
//        }
    }

    public Lightning addChild(Lightning child)
    {
        child.parent = this;
        child.lightningColor = lightningColor;
        children.add(child);
        return this;
    }

    public Lightning setRotation(float rotX, float rotY, float rotZ)
    {
        rotateAngleX = rotX;
        rotateAngleY = rotY;
        rotateAngleZ = rotZ;
        return this;
    }

    public Lightning setScale(float f)
    {
        scale = f;
        return this;
    }
}
