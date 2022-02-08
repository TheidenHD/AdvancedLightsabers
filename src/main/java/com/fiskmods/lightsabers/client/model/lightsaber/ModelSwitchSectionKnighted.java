package com.fiskmods.lightsabers.client.model.lightsaber;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelSwitchSectionKnighted extends ModelBase
{
    public ModelRenderer body1;
    public ModelRenderer shell1;
    public ModelRenderer button1;
    public ModelRenderer body2;
    public ModelRenderer body3;
    public ModelRenderer body4;
    public ModelRenderer body5;
    public ModelRenderer body6;
    public ModelRenderer body7;
    public ModelRenderer body12;
    public ModelRenderer body8;
    public ModelRenderer body9;
    public ModelRenderer body10;
    public ModelRenderer body11;
    public ModelRenderer shell2;
    public ModelRenderer shell3;
    public ModelRenderer shell4;
    public ModelRenderer shell5;
    public ModelRenderer shell7;
    public ModelRenderer shell8;
    public ModelRenderer shell9;
    public ModelRenderer shell10;
    public ModelRenderer shell6;
    public ModelRenderer button2;
    public ModelRenderer button3;
    public ModelRenderer button4;
    public ModelRenderer button5;
    public ModelRenderer button6;
    public ModelRenderer button7;
    public ModelRenderer button8;
    public ModelRenderer button9;
    public ModelRenderer button10;
    public ModelRenderer button11;
    public ModelRenderer button12;
    public ModelRenderer button13;
    public ModelRenderer button14;

    public ModelSwitchSectionKnighted()
    {
        textureWidth = 64;
        textureHeight = 32;
        body7 = new ModelRenderer(this, 0, 0);
        body7.setRotationPoint(0.0F, 0.0F, 0.0F);
        body7.addBox(-1.5F, -7.0F, 2.62F, 3, 7, 1, 0.0F);
        setRotateAngle(body7, 0.0F, -1.5707963267948966F, 0.0F);
        body10 = new ModelRenderer(this, 8, 3);
        body10.setRotationPoint(-1.0F, 0.9F, 0.5F);
        body10.addBox(-0.5F, 0.0F, -0.5F, 1, 2, 1, 0.0F);
        body4 = new ModelRenderer(this, 0, 0);
        body4.setRotationPoint(0.0F, 0.0F, 0.0F);
        body4.addBox(-1.5F, -7.0F, 2.62F, 3, 7, 1, 0.0F);
        setRotateAngle(body4, 0.0F, 2.356194490192345F, 0.0F);
        body2 = new ModelRenderer(this, 0, 0);
        body2.setRotationPoint(0.0F, 0.0F, 0.0F);
        body2.addBox(-1.5F, -7.0F, 2.62F, 3, 7, 1, 0.0F);
        setRotateAngle(body2, 0.0F, 0.7853981633974483F, 0.0F);
        button13 = new ModelRenderer(this, 8, 6);
        button13.setRotationPoint(0.0F, -2.0F, 0.0F);
        button13.addBox(-1.5F, -1.5F, 2.7F, 3, 3, 2, 0.0F);
        setRotateAngle(button13, 0.0F, -1.0471975511965976F, 0.0F);
        button7 = new ModelRenderer(this, 6, 12);
        button7.setRotationPoint(0.0F, 0.0F, 0.0F);
        button7.addBox(-1.5F, -4.0F, -0.38F, 3, 4, 4, 0.0F);
        setRotateAngle(button7, 0.0F, -1.5707963267948966F, 0.0F);
        body1 = new ModelRenderer(this, 0, 0);
        body1.setRotationPoint(0.0F, 0.0F, 0.0F);
        body1.addBox(-1.5F, -7.0F, 2.62F, 3, 7, 1, 0.0F);
        body6 = new ModelRenderer(this, 0, 0);
        body6.setRotationPoint(0.0F, 0.0F, 0.0F);
        body6.addBox(-1.5F, -7.0F, 2.62F, 3, 7, 1, 0.0F);
        setRotateAngle(body6, 0.0F, -2.356194490192345F, 0.0F);
        shell4 = new ModelRenderer(this, 0, 10);
        shell4.setRotationPoint(0.0F, 0.0F, 0.0F);
        shell4.addBox(-1.5F, -5.0F, 2.62F, 3, 5, 1, 0.0F);
        setRotateAngle(shell4, 0.0F, 2.356194490192345F, 0.0F);
        shell2 = new ModelRenderer(this, 0, 10);
        shell2.setRotationPoint(0.0F, 0.0F, 0.0F);
        shell2.addBox(-1.5F, -5.0F, 2.62F, 3, 5, 1, 0.0F);
        setRotateAngle(shell2, 0.0F, 0.7853981633974483F, 0.0F);
        shell9 = new ModelRenderer(this, 0, 8);
        shell9.setRotationPoint(0.0F, 0.0F, 0.0F);
        shell9.addBox(-1.5F, -1.0F, 2.62F, 3, 1, 1, 0.0F);
        setRotateAngle(shell9, 0.0F, -0.7853981633974483F, 0.0F);
        button1 = new ModelRenderer(this, 6, 12);
        button1.setRotationPoint(-4.35F, -4.9F, 0.0F);
        button1.addBox(-1.5F, -4.0F, -0.38F, 3, 4, 4, 0.0F);
        setRotateAngle(button1, 1.5707963267948966F, 1.5707963267948966F, 0.0F);
        body9 = new ModelRenderer(this, 8, 3);
        body9.setRotationPoint(1.0F, 0.9F, 0.5F);
        body9.addBox(-0.5F, 0.0F, -0.5F, 1, 2, 1, 0.0F);
        button8 = new ModelRenderer(this, 6, 12);
        button8.setRotationPoint(0.0F, 0.0F, 0.0F);
        button8.addBox(-1.5F, -4.0F, -0.38F, 3, 4, 4, 0.0F);
        setRotateAngle(button8, 0.0F, -0.7853981633974483F, 0.0F);
        shell3 = new ModelRenderer(this, 0, 10);
        shell3.setRotationPoint(0.0F, 0.0F, 0.0F);
        shell3.addBox(-1.5F, -5.0F, 2.62F, 3, 5, 1, 0.0F);
        setRotateAngle(shell3, 0.0F, 1.5707963267948966F, 0.0F);
        body8 = new ModelRenderer(this, 8, 0);
        body8.setRotationPoint(0.0F, -6.0F, 3.12F);
        body8.addBox(-1.5F, -1.0F, 0.0F, 3, 2, 1, 0.0F);
        body3 = new ModelRenderer(this, 0, 0);
        body3.setRotationPoint(0.0F, 0.0F, 0.0F);
        body3.addBox(-1.5F, -7.0F, 2.62F, 3, 7, 1, 0.0F);
        setRotateAngle(body3, 0.0F, 1.5707963267948966F, 0.0F);
        button12 = new ModelRenderer(this, 8, 6);
        button12.setRotationPoint(0.0F, -2.0F, 0.0F);
        button12.addBox(-1.5F, -1.5F, 2.7F, 3, 3, 2, 0.0F);
        setRotateAngle(button12, 0.0F, 2.0943951023931953F, 0.0F);
        body5 = new ModelRenderer(this, 0, 0);
        body5.setRotationPoint(0.0F, 0.0F, 0.0F);
        body5.addBox(-1.5F, -7.0F, 2.62F, 3, 7, 1, 0.0F);
        setRotateAngle(body5, 0.0F, 3.141592653589793F, 0.0F);
        button3 = new ModelRenderer(this, 6, 12);
        button3.setRotationPoint(0.0F, 0.0F, 0.0F);
        button3.addBox(-1.5F, -4.0F, -0.38F, 3, 4, 4, 0.0F);
        setRotateAngle(button3, 0.0F, 1.5707963267948966F, 0.0F);
        button14 = new ModelRenderer(this, 8, 6);
        button14.setRotationPoint(0.0F, -2.0F, 0.0F);
        button14.addBox(-1.5F, -1.5F, 2.7F, 3, 3, 2, 0.0F);
        setRotateAngle(button14, 0.0F, -2.0943951023931953F, 0.0F);
        body12 = new ModelRenderer(this, 0, 0);
        body12.setRotationPoint(0.0F, 0.0F, 0.0F);
        body12.addBox(-1.5F, -7.0F, 2.62F, 3, 7, 1, 0.0F);
        setRotateAngle(body12, 0.0F, -0.7853981633974483F, 0.0F);
        button6 = new ModelRenderer(this, 6, 12);
        button6.setRotationPoint(0.0F, 0.0F, 0.0F);
        button6.addBox(-1.5F, -4.0F, -0.38F, 3, 4, 4, 0.0F);
        setRotateAngle(button6, 0.0F, -2.356194490192345F, 0.0F);
        button4 = new ModelRenderer(this, 6, 12);
        button4.setRotationPoint(0.0F, 0.0F, 0.0F);
        button4.addBox(-1.5F, -4.0F, -0.38F, 3, 4, 4, 0.0F);
        setRotateAngle(button4, 0.0F, 2.356194490192345F, 0.0F);
        body11 = new ModelRenderer(this, 12, 3);
        body11.setRotationPoint(0.0F, 0.0F, 0.2F);
        body11.addBox(-1.0F, -0.5F, 0.0F, 2, 1, 1, 0.0F);
        shell8 = new ModelRenderer(this, 0, 8);
        shell8.setRotationPoint(0.0F, 0.0F, 0.0F);
        shell8.addBox(-1.5F, -1.0F, 2.62F, 3, 1, 1, 0.0F);
        setRotateAngle(shell8, 0.0F, -1.5707963267948966F, 0.0F);
        button2 = new ModelRenderer(this, 6, 12);
        button2.setRotationPoint(0.0F, 0.0F, 0.0F);
        button2.addBox(-1.5F, -4.0F, -0.38F, 3, 4, 4, 0.0F);
        setRotateAngle(button2, 0.0F, 0.7853981633974483F, 0.0F);
        button11 = new ModelRenderer(this, 8, 6);
        button11.setRotationPoint(0.0F, -2.0F, 0.0F);
        button11.addBox(-1.5F, -1.5F, 2.7F, 3, 3, 2, 0.0F);
        setRotateAngle(button11, 0.0F, 1.0471975511965976F, 0.0F);
        shell7 = new ModelRenderer(this, 0, 8);
        shell7.setRotationPoint(0.0F, 0.0F, 0.0F);
        shell7.addBox(-1.5F, -1.0F, 2.62F, 3, 1, 1, 0.0F);
        setRotateAngle(shell7, 0.0F, -2.356194490192345F, 0.0F);
        shell10 = new ModelRenderer(this, 0, 16);
        shell10.setRotationPoint(0.0F, -1.0F, 3.12F);
        shell10.addBox(-0.5F, -4.0F, -0.5F, 2, 4, 1, 0.0F);
        shell1 = new ModelRenderer(this, 0, 8);
        shell1.setRotationPoint(0.0F, -0.3F, 0.0F);
        shell1.addBox(-1.5F, -1.0F, 2.62F, 3, 1, 1, 0.0F);
        button9 = new ModelRenderer(this, 8, 6);
        button9.setRotationPoint(0.0F, -2.0F, 0.0F);
        button9.addBox(-1.5F, -1.5F, 2.7F, 3, 3, 2, 0.0F);
        button10 = new ModelRenderer(this, 8, 6);
        button10.setRotationPoint(0.0F, -2.0F, 0.0F);
        button10.addBox(-1.5F, -1.5F, 2.7F, 3, 3, 2, 0.0F);
        setRotateAngle(button10, 0.0F, 3.141592653589793F, 0.0F);
        button5 = new ModelRenderer(this, 6, 12);
        button5.setRotationPoint(0.0F, 0.0F, 0.0F);
        button5.addBox(-1.5F, -4.0F, -0.38F, 3, 4, 4, 0.0F);
        setRotateAngle(button5, 0.0F, 3.141592653589793F, 0.0F);
        shell6 = new ModelRenderer(this, 0, 16);
        shell6.setRotationPoint(0.0F, -1.0F, 3.12F);
        shell6.addBox(-1.5F, -4.0F, -0.5F, 2, 4, 1, 0.0F);
        shell5 = new ModelRenderer(this, 0, 8);
        shell5.setRotationPoint(0.0F, 0.0F, 0.0F);
        shell5.addBox(-1.5F, -1.0F, 2.62F, 3, 1, 1, 0.0F);
        setRotateAngle(shell5, 0.0F, 3.141592653589793F, 0.0F);
        body1.addChild(body7);
        body8.addChild(body10);
        body1.addChild(body4);
        body1.addChild(body2);
        button1.addChild(button13);
        button1.addChild(button7);
        body1.addChild(body6);
        shell1.addChild(shell4);
        shell1.addChild(shell2);
        shell1.addChild(shell9);
        body8.addChild(body9);
        button1.addChild(button8);
        shell1.addChild(shell3);
        body7.addChild(body8);
        body1.addChild(body3);
        button1.addChild(button12);
        body1.addChild(body5);
        button1.addChild(button3);
        button1.addChild(button14);
        body1.addChild(body12);
        button1.addChild(button6);
        button1.addChild(button4);
        body8.addChild(body11);
        shell1.addChild(shell8);
        button1.addChild(button2);
        button1.addChild(button11);
        shell1.addChild(shell7);
        shell1.addChild(shell10);
        button1.addChild(button9);
        button1.addChild(button10);
        button1.addChild(button5);
        shell5.addChild(shell6);
        shell1.addChild(shell5);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
        GL11.glPushMatrix();
        GL11.glTranslatef(body1.offsetX, body1.offsetY, body1.offsetZ);
        GL11.glTranslatef(body1.rotationPointX * f5, body1.rotationPointY * f5, body1.rotationPointZ * f5);
        GL11.glScaled(1.2D, 1.2D, 1.2D);
        GL11.glTranslatef(-body1.offsetX, -body1.offsetY, -body1.offsetZ);
        GL11.glTranslatef(-body1.rotationPointX * f5, -body1.rotationPointY * f5, -body1.rotationPointZ * f5);
        body1.render(f5);
        GL11.glPopMatrix();
        GL11.glPushMatrix();
        GL11.glTranslatef(button1.offsetX, button1.offsetY, button1.offsetZ);
        GL11.glTranslatef(button1.rotationPointX * f5, button1.rotationPointY * f5, button1.rotationPointZ * f5);
        GL11.glScaled(0.25D, 0.13D, 0.13D);
        GL11.glTranslatef(-button1.offsetX, -button1.offsetY, -button1.offsetZ);
        GL11.glTranslatef(-button1.rotationPointX * f5, -button1.rotationPointY * f5, -button1.rotationPointZ * f5);
        button1.render(f5);
        GL11.glPopMatrix();
        GL11.glPushMatrix();
        GL11.glTranslatef(shell1.offsetX, shell1.offsetY, shell1.offsetZ);
        GL11.glTranslatef(shell1.rotationPointX * f5, shell1.rotationPointY * f5, shell1.rotationPointZ * f5);
        GL11.glScaled(1.35D, 1.35D, 1.35D);
        GL11.glTranslatef(-shell1.offsetX, -shell1.offsetY, -shell1.offsetZ);
        GL11.glTranslatef(-shell1.rotationPointX * f5, -shell1.rotationPointY * f5, -shell1.rotationPointZ * f5);
        shell1.render(f5);
        GL11.glPopMatrix();
    }

    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z)
    {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}