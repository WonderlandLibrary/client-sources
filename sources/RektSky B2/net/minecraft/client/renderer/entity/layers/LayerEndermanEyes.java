package net.minecraft.client.renderer.entity.layers;

import net.minecraft.entity.monster.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.*;

public class LayerEndermanEyes implements LayerRenderer<EntityEnderman>
{
    private static final ResourceLocation field_177203_a;
    private final RenderEnderman endermanRenderer;
    
    public LayerEndermanEyes(final RenderEnderman endermanRendererIn) {
        this.endermanRenderer = endermanRendererIn;
    }
    
    @Override
    public void doRenderLayer(final EntityEnderman entitylivingbaseIn, final float p_177141_2_, final float p_177141_3_, final float partialTicks, final float p_177141_5_, final float p_177141_6_, final float p_177141_7_, final float scale) {
        this.endermanRenderer.bindTexture(LayerEndermanEyes.field_177203_a);
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.blendFunc(1, 1);
        GlStateManager.disableLighting();
        GlStateManager.depthMask(!entitylivingbaseIn.isInvisible());
        final int i = 61680;
        final int j = i % 65536;
        final int k = i / 65536;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, j / 1.0f, k / 1.0f);
        GlStateManager.enableLighting();
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        this.endermanRenderer.getMainModel().render(entitylivingbaseIn, p_177141_2_, p_177141_3_, p_177141_5_, p_177141_6_, p_177141_7_, scale);
        this.endermanRenderer.func_177105_a(entitylivingbaseIn, partialTicks);
        GlStateManager.depthMask(true);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
    }
    
    @Override
    public boolean shouldCombineTextures() {
        return false;
    }
    
    static {
        field_177203_a = new ResourceLocation("textures/entity/enderman/enderman_eyes.png");
    }
}
