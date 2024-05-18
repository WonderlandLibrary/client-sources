package net.minecraft.client.renderer.entity.layers;

import net.minecraft.entity.boss.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.model.*;
import net.minecraft.client.renderer.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;

public class LayerWitherAura implements LayerRenderer<EntityWither>
{
    private static final ResourceLocation WITHER_ARMOR;
    private final RenderWither witherRenderer;
    private static final String[] I;
    private final ModelWither witherModel;
    
    @Override
    public void doRenderLayer(final EntityWither entityWither, final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final float n7) {
        if (entityWither.isArmored()) {
            int n8;
            if (entityWither.isInvisible()) {
                n8 = "".length();
                "".length();
                if (0 >= 2) {
                    throw null;
                }
            }
            else {
                n8 = " ".length();
            }
            GlStateManager.depthMask(n8 != 0);
            this.witherRenderer.bindTexture(LayerWitherAura.WITHER_ARMOR);
            GlStateManager.matrixMode(5564 + 777 - 4372 + 3921);
            GlStateManager.loadIdentity();
            final float n9 = entityWither.ticksExisted + n3;
            GlStateManager.translate(MathHelper.cos(n9 * 0.02f) * 3.0f, n9 * 0.01f, 0.0f);
            GlStateManager.matrixMode(1051 + 338 + 234 + 4265);
            GlStateManager.enableBlend();
            final float n10 = 0.5f;
            GlStateManager.color(n10, n10, n10, 1.0f);
            GlStateManager.disableLighting();
            GlStateManager.blendFunc(" ".length(), " ".length());
            this.witherModel.setLivingAnimations(entityWither, n, n2, n3);
            this.witherModel.setModelAttributes(this.witherRenderer.getMainModel());
            this.witherModel.render(entityWither, n, n2, n4, n5, n6, n7);
            GlStateManager.matrixMode(5681 + 2470 - 3379 + 1118);
            GlStateManager.loadIdentity();
            GlStateManager.matrixMode(2364 + 3515 - 4655 + 4664);
            GlStateManager.enableLighting();
            GlStateManager.disableBlend();
        }
    }
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("?\u0001!\u000e%9\u0001*U5%\u00100\u000e)d\u00130\u000e8.\u0016v\r9?\f<\b\u000f*\u00164\u0015\"e\u00147\u001d", "KdYzP");
    }
    
    static {
        I();
        WITHER_ARMOR = new ResourceLocation(LayerWitherAura.I["".length()]);
    }
    
    public LayerWitherAura(final RenderWither witherRenderer) {
        this.witherModel = new ModelWither(0.5f);
        this.witherRenderer = witherRenderer;
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
            if (0 <= -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public void doRenderLayer(final EntityLivingBase entityLivingBase, final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final float n7) {
        this.doRenderLayer((EntityWither)entityLivingBase, n, n2, n3, n4, n5, n6, n7);
    }
    
    @Override
    public boolean shouldCombineTextures() {
        return "".length() != 0;
    }
}
