package net.minecraft.client.renderer.entity.layers;

import net.minecraft.entity.boss.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.*;

public class LayerEnderDragonEyes implements LayerRenderer<EntityDragon>
{
    private static final ResourceLocation TEXTURE;
    private final RenderDragon dragonRenderer;
    
    public LayerEnderDragonEyes(final RenderDragon dragonRendererIn) {
        this.dragonRenderer = dragonRendererIn;
    }
    
    @Override
    public void doRenderLayer(final EntityDragon entitylivingbaseIn, final float p_177141_2_, final float p_177141_3_, final float partialTicks, final float p_177141_5_, final float p_177141_6_, final float p_177141_7_, final float scale) {
        this.dragonRenderer.bindTexture(LayerEnderDragonEyes.TEXTURE);
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.blendFunc(1, 1);
        GlStateManager.disableLighting();
        GlStateManager.depthFunc(514);
        final int i = 61680;
        final int j = i % 65536;
        final int k = i / 65536;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, j / 1.0f, k / 1.0f);
        GlStateManager.enableLighting();
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        this.dragonRenderer.getMainModel().render(entitylivingbaseIn, p_177141_2_, p_177141_3_, p_177141_5_, p_177141_6_, p_177141_7_, scale);
        this.dragonRenderer.func_177105_a(entitylivingbaseIn, partialTicks);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.depthFunc(515);
    }
    
    @Override
    public boolean shouldCombineTextures() {
        return false;
    }
    
    static {
        TEXTURE = new ResourceLocation("textures/entity/enderdragon/dragon_eyes.png");
    }
}
