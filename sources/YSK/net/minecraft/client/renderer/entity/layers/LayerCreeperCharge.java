package net.minecraft.client.renderer.entity.layers;

import net.minecraft.entity.monster.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.model.*;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.*;

public class LayerCreeperCharge implements LayerRenderer<EntityCreeper>
{
    private static final ResourceLocation LIGHTNING_TEXTURE;
    private final RenderCreeper creeperRenderer;
    private final ModelCreeper creeperModel;
    private static final String[] I;
    
    static {
        I();
        LIGHTNING_TEXTURE = new ResourceLocation(LayerCreeperCharge.I["".length()]);
    }
    
    public LayerCreeperCharge(final RenderCreeper creeperRenderer) {
        this.creeperModel = new ModelCreeper(2.0f);
        this.creeperRenderer = creeperRenderer;
    }
    
    @Override
    public void doRenderLayer(final EntityLivingBase entityLivingBase, final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final float n7) {
        this.doRenderLayer((EntityCreeper)entityLivingBase, n, n2, n3, n4, n5, n6, n7);
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
            if (2 == 4) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public boolean shouldCombineTextures() {
        return "".length() != 0;
    }
    
    @Override
    public void doRenderLayer(final EntityCreeper entityCreeper, final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final float n7) {
        if (entityCreeper.getPowered()) {
            final boolean invisible = entityCreeper.isInvisible();
            int n8;
            if (invisible) {
                n8 = "".length();
                "".length();
                if (2 != 2) {
                    throw null;
                }
            }
            else {
                n8 = " ".length();
            }
            GlStateManager.depthMask(n8 != 0);
            this.creeperRenderer.bindTexture(LayerCreeperCharge.LIGHTNING_TEXTURE);
            GlStateManager.matrixMode(4979 + 1233 - 4194 + 3872);
            GlStateManager.loadIdentity();
            final float n9 = entityCreeper.ticksExisted + n3;
            GlStateManager.translate(n9 * 0.01f, n9 * 0.01f, 0.0f);
            GlStateManager.matrixMode(2412 + 4292 - 4567 + 3751);
            GlStateManager.enableBlend();
            final float n10 = 0.5f;
            GlStateManager.color(n10, n10, n10, 1.0f);
            GlStateManager.disableLighting();
            GlStateManager.blendFunc(" ".length(), " ".length());
            this.creeperModel.setModelAttributes(this.creeperRenderer.getMainModel());
            this.creeperModel.render(entityCreeper, n, n2, n4, n5, n6, n7);
            GlStateManager.matrixMode(4188 + 4176 - 6382 + 3908);
            GlStateManager.loadIdentity();
            GlStateManager.matrixMode(5664 + 3831 - 5631 + 2024);
            GlStateManager.enableLighting();
            GlStateManager.disableBlend();
            GlStateManager.depthMask(invisible);
        }
    }
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("\"\f\r\f/$\f\u0006W?8\u001d\u001c\f#y\n\u0007\u001d?&\f\u0007W9$\f\u0010\b?$6\u0014\n79\u001b[\b41", "ViuxZ");
    }
}
