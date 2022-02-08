package com.fiskmods.lightsabers.client.render.tile;

import java.util.Random;

import net.minecraft.client.renderer.GlStateManager;

import com.fiskmods.lightsabers.client.model.tile.ModelCrystal;
import com.fiskmods.lightsabers.common.tileentity.TileEntityCrystal;
import com.fiskmods.lightsabers.helper.ALRenderHelper;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;

public class RenderCrystal extends TileEntitySpecialRenderer<TileEntityCrystal>
{
    private final ModelCrystal model = new ModelCrystal();

    @Override
    public void render(TileEntityCrystal tile, double x, double y, double z, float partialTicks, int destroyStage, float alpha)
    {
        alpha = 0.6F;
        GlStateManager.pushMatrix();
        GlStateManager.translate((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);
        GlStateManager.scale(1, -1, -1);

        float[] rgb = tile.getColor().getRGB();
        GlStateManager.color(rgb[0], rgb[1], rgb[2], alpha);
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        ALRenderHelper.setLighting(ALRenderHelper.LIGHTING_LUMINOUS);
        model.render();
        ALRenderHelper.resetLighting();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }

    public void adjustRotation(TileEntityCrystal tile, int metadata)
    {
        if (metadata > 0 && metadata < 5)
        {
            int[] matrix = {0, 2, 1, 3};
            GlStateManager.rotate(matrix[metadata - 1] * 90, 0, 1, 0);
            GlStateManager.translate(1, 1, 0);
            GlStateManager.rotate(90, 0, 0, 1);
        }

        if (metadata == 0)
        {
            GlStateManager.translate(0, 2, 0);
            GlStateManager.rotate(180, 0, 0, 1);
        }

        Random rand = new Random(tile.getPos().getX() + tile.getPos().getY() + tile.getPos().getZ());
        GlStateManager.rotate(rand.nextInt(360), 0, 1, 0);
        GlStateManager.translate(0, (float) rand.nextInt(10) / 40, 0);
    }
}
