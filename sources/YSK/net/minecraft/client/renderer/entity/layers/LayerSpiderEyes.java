package net.minecraft.client.renderer.entity.layers;

import net.minecraft.entity.monster.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.*;

public class LayerSpiderEyes implements LayerRenderer<EntitySpider>
{
    private static final String[] I;
    private final RenderSpider spiderRenderer;
    private static final ResourceLocation SPIDER_EYES;
    
    @Override
    public boolean shouldCombineTextures() {
        return "".length() != 0;
    }
    
    @Override
    public void doRenderLayer(final EntitySpider entitySpider, final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final float n7) {
        this.spiderRenderer.bindTexture(LayerSpiderEyes.SPIDER_EYES);
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.blendFunc(" ".length(), " ".length());
        if (entitySpider.isInvisible()) {
            GlStateManager.depthMask("".length() != 0);
            "".length();
            if (2 == 3) {
                throw null;
            }
        }
        else {
            GlStateManager.depthMask(" ".length() != 0);
        }
        final int n8 = 631 + 38787 - 15140 + 37402;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, n8 % (11212 + 26323 - 27089 + 55090) / 1.0f, n8 / (6221 + 7793 + 12462 + 39060) / 1.0f);
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        this.spiderRenderer.getMainModel().render(entitySpider, n, n2, n4, n5, n6, n7);
        final int brightnessForRender = entitySpider.getBrightnessForRender(n3);
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, brightnessForRender % (40911 + 27176 - 56021 + 53470) / 1.0f, brightnessForRender / (7725 + 58774 - 37021 + 36058) / 1.0f);
        this.spiderRenderer.func_177105_a(entitySpider, n3);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
    }
    
    @Override
    public void doRenderLayer(final EntityLivingBase entityLivingBase, final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final float n7) {
        this.doRenderLayer((EntitySpider)entityLivingBase, n, n2, n3, n4, n5, n6, n7);
    }
    
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (3 <= 2) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public LayerSpiderEyes(final RenderSpider spiderRenderer) {
        this.spiderRenderer = spiderRenderer;
    }
    
    static {
        I();
        SPIDER_EYES = new ResourceLocation(LayerSpiderEyes.I["".length()]);
    }
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("3\u00134!\u00025\u0013?z\u0012)\u0002%!\u000eh\u0005<<\u0013\"\u0004\u00130\u000e\"\u0005b%\u0019 ", "GvLUw");
    }
}
