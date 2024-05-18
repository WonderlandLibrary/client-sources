package net.minecraft.client.renderer.entity.layers;

import net.minecraft.entity.monster.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.*;

public class LayerEndermanEyes implements LayerRenderer<EntityEnderman>
{
    private final RenderEnderman endermanRenderer;
    private static final ResourceLocation field_177203_a;
    private static final String[] I;
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("'\u000f=\u001d#!\u000f6F3=\u001e,\u001d/|\u000f+\r3!\u0007$\u0007y6\u0004!\f$>\u000b+63*\u000f6G&=\r", "SjEiV");
    }
    
    @Override
    public void doRenderLayer(final EntityEnderman entityEnderman, final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final float n7) {
        this.endermanRenderer.bindTexture(LayerEndermanEyes.field_177203_a);
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.blendFunc(" ".length(), " ".length());
        GlStateManager.disableLighting();
        int n8;
        if (entityEnderman.isInvisible()) {
            n8 = "".length();
            "".length();
            if (4 < 0) {
                throw null;
            }
        }
        else {
            n8 = " ".length();
        }
        GlStateManager.depthMask(n8 != 0);
        final int n9 = 13564 + 45381 - 54117 + 56852;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, n9 % (4527 + 60663 - 52930 + 53276) / 1.0f, n9 / (18539 + 5105 + 6818 + 35074) / 1.0f);
        GlStateManager.enableLighting();
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        this.endermanRenderer.getMainModel().render(entityEnderman, n, n2, n4, n5, n6, n7);
        this.endermanRenderer.func_177105_a(entityEnderman, n3);
        GlStateManager.depthMask(" ".length() != 0);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
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
            if (3 < -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public LayerEndermanEyes(final RenderEnderman endermanRenderer) {
        this.endermanRenderer = endermanRenderer;
    }
    
    @Override
    public boolean shouldCombineTextures() {
        return "".length() != 0;
    }
    
    static {
        I();
        field_177203_a = new ResourceLocation(LayerEndermanEyes.I["".length()]);
    }
    
    @Override
    public void doRenderLayer(final EntityLivingBase entityLivingBase, final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final float n7) {
        this.doRenderLayer((EntityEnderman)entityLivingBase, n, n2, n3, n4, n5, n6, n7);
    }
}
