package net.minecraft.client.renderer.entity.layers;

import net.minecraft.entity.boss.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.*;

public class LayerEnderDragonEyes implements LayerRenderer<EntityDragon>
{
    private final RenderDragon dragonRenderer;
    private static final ResourceLocation TEXTURE;
    private static final String[] I;
    
    @Override
    public boolean shouldCombineTextures() {
        return "".length() != 0;
    }
    
    static {
        I();
        TEXTURE = new ResourceLocation(LayerEnderDragonEyes.I["".length()]);
    }
    
    @Override
    public void doRenderLayer(final EntityDragon entityDragon, final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final float n7) {
        this.dragonRenderer.bindTexture(LayerEnderDragonEyes.TEXTURE);
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.blendFunc(" ".length(), " ".length());
        GlStateManager.disableLighting();
        GlStateManager.depthFunc(292 + 422 - 542 + 342);
        final int n8 = 24681 + 24453 + 9824 + 2722;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, n8 % (55629 + 15228 - 51079 + 45758) / 1.0f, n8 / (22000 + 61434 - 21658 + 3760) / 1.0f);
        GlStateManager.enableLighting();
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        this.dragonRenderer.getMainModel().render(entityDragon, n, n2, n4, n5, n6, n7);
        this.dragonRenderer.func_177105_a(entityDragon, n3);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.depthFunc(393 + 202 - 113 + 33);
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
            if (-1 != -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public void doRenderLayer(final EntityLivingBase entityLivingBase, final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final float n7) {
        this.doRenderLayer((EntityDragon)entityLivingBase, n, n2, n3, n4, n5, n6, n7);
    }
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("\u0010\u000777'\u0016\u0007<l7\n\u0016&7+K\u0007!'7\u0016\u0006=\"5\u000b\f`' \u0005\u0005 -\r\u0001\u001b*0|\u0014\f(", "dbOCR");
    }
    
    public LayerEnderDragonEyes(final RenderDragon dragonRenderer) {
        this.dragonRenderer = dragonRenderer;
    }
}
