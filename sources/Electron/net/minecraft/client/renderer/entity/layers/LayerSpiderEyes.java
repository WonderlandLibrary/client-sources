package net.minecraft.client.renderer.entity.layers;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.RenderSpider;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.src.Config;
import net.minecraft.util.ResourceLocation;
import net.optifine.shaders.client.Shaders;

public class LayerSpiderEyes implements LayerRenderer<EntitySpider> {
    private static final ResourceLocation SPIDER_EYES = new ResourceLocation("textures/entity/spider_eyes.png");
    private final RenderSpider spiderRenderer;
    private static final String __OBFID = "CL_00002410";

    public LayerSpiderEyes(RenderSpider spiderRendererIn) {
        this.spiderRenderer = spiderRendererIn;
    }

    public void doRenderLayer(EntitySpider entitylivingbaseIn, float p_177141_2_, float p_177141_3_, float partialTicks, float p_177141_5_, float p_177141_6_, float p_177141_7_, float scale) {
        this.spiderRenderer.bindTexture(SPIDER_EYES);
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.blendFunc(1, 1);

        GlStateManager.depthMask(!entitylivingbaseIn.isInvisible());

        char c0 = 61680;
        int i = c0 % 65536;
        int j = 0;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float) i, (float) j);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

        if (Config.isShaders()) {
            Shaders.beginSpiderEyes();
        }

        this.spiderRenderer.getMainModel().render(entitylivingbaseIn, p_177141_2_, p_177141_3_, p_177141_5_, p_177141_6_, p_177141_7_, scale);

        if (Config.isShaders()) {
            Shaders.endSpiderEyes();
        }

        int k = entitylivingbaseIn.getBrightnessForRender(partialTicks);
        i = k % 65536;
        j = k / 65536;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float) i, (float) j);
        this.spiderRenderer.func_177105_a(entitylivingbaseIn, partialTicks);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
    }

    public boolean shouldCombineTextures() {
        return false;
    }
}