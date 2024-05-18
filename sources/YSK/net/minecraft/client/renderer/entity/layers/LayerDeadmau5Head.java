package net.minecraft.client.renderer.entity.layers;

import net.minecraft.client.entity.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.*;

public class LayerDeadmau5Head implements LayerRenderer<AbstractClientPlayer>
{
    private final RenderPlayer playerRenderer;
    private static final String[] I;
    
    static {
        I();
    }
    
    @Override
    public void doRenderLayer(final AbstractClientPlayer abstractClientPlayer, final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final float n7) {
        if (abstractClientPlayer.getName().equals(LayerDeadmau5Head.I["".length()]) && abstractClientPlayer.hasSkin() && !abstractClientPlayer.isInvisible()) {
            this.playerRenderer.bindTexture(abstractClientPlayer.getLocationSkin());
            int i = "".length();
            "".length();
            if (2 >= 4) {
                throw null;
            }
            while (i < "  ".length()) {
                final float n8 = abstractClientPlayer.prevRotationYaw + (abstractClientPlayer.rotationYaw - abstractClientPlayer.prevRotationYaw) * n3 - (abstractClientPlayer.prevRenderYawOffset + (abstractClientPlayer.renderYawOffset - abstractClientPlayer.prevRenderYawOffset) * n3);
                final float n9 = abstractClientPlayer.prevRotationPitch + (abstractClientPlayer.rotationPitch - abstractClientPlayer.prevRotationPitch) * n3;
                GlStateManager.pushMatrix();
                GlStateManager.rotate(n8, 0.0f, 1.0f, 0.0f);
                GlStateManager.rotate(n9, 1.0f, 0.0f, 0.0f);
                GlStateManager.translate(0.375f * (i * "  ".length() - " ".length()), 0.0f, 0.0f);
                GlStateManager.translate(0.0f, -0.375f, 0.0f);
                GlStateManager.rotate(-n9, 1.0f, 0.0f, 0.0f);
                GlStateManager.rotate(-n8, 0.0f, 1.0f, 0.0f);
                final float n10 = 1.3333334f;
                GlStateManager.scale(n10, n10, n10);
                this.playerRenderer.getMainModel().renderDeadmau5Head(0.0625f);
                GlStateManager.popMatrix();
                ++i;
            }
        }
    }
    
    @Override
    public boolean shouldCombineTextures() {
        return " ".length() != 0;
    }
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("\u0017&\u00116\u001d\u00126E", "sCpRp");
    }
    
    @Override
    public void doRenderLayer(final EntityLivingBase entityLivingBase, final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final float n7) {
        this.doRenderLayer((AbstractClientPlayer)entityLivingBase, n, n2, n3, n4, n5, n6, n7);
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
            if (0 == 2) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public LayerDeadmau5Head(final RenderPlayer playerRenderer) {
        this.playerRenderer = playerRenderer;
    }
}
