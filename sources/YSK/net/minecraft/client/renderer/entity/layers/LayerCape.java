package net.minecraft.client.renderer.entity.layers;

import net.minecraft.client.renderer.entity.*;
import net.minecraft.entity.*;
import net.minecraft.client.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.client.renderer.*;
import net.minecraft.util.*;

public class LayerCape implements LayerRenderer
{
    private static final String[] I;
    private final RenderPlayer playerRenderer;
    private static final String __OBFID;
    
    @Override
    public void doRenderLayer(final EntityLivingBase entityLivingBase, final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final float n7) {
        this.doRenderLayer((AbstractClientPlayer)entityLivingBase, n, n2, n3, n4, n5, n6, n7);
    }
    
    public void doRenderLayer(final AbstractClientPlayer abstractClientPlayer, final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final float n7) {
        if (abstractClientPlayer.hasPlayerInfo() && !abstractClientPlayer.isInvisible() && abstractClientPlayer.isWearing(EnumPlayerModelParts.CAPE) && abstractClientPlayer.getLocationCape() != null) {
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            this.playerRenderer.bindTexture(abstractClientPlayer.getLocationCape());
            GlStateManager.pushMatrix();
            GlStateManager.translate(0.0f, 0.0f, 0.125f);
            final double n8 = abstractClientPlayer.prevChasingPosX + (abstractClientPlayer.chasingPosX - abstractClientPlayer.prevChasingPosX) * n3 - (abstractClientPlayer.prevPosX + (abstractClientPlayer.posX - abstractClientPlayer.prevPosX) * n3);
            final double n9 = abstractClientPlayer.prevChasingPosY + (abstractClientPlayer.chasingPosY - abstractClientPlayer.prevChasingPosY) * n3 - (abstractClientPlayer.prevPosY + (abstractClientPlayer.posY - abstractClientPlayer.prevPosY) * n3);
            final double n10 = abstractClientPlayer.prevChasingPosZ + (abstractClientPlayer.chasingPosZ - abstractClientPlayer.prevChasingPosZ) * n3 - (abstractClientPlayer.prevPosZ + (abstractClientPlayer.posZ - abstractClientPlayer.prevPosZ) * n3);
            final float n11 = abstractClientPlayer.prevRenderYawOffset + (abstractClientPlayer.renderYawOffset - abstractClientPlayer.prevRenderYawOffset) * n3;
            final double n12 = MathHelper.sin(n11 * 3.1415927f / 180.0f);
            final double n13 = -MathHelper.cos(n11 * 3.1415927f / 180.0f);
            final float clamp_float = MathHelper.clamp_float((float)n9 * 10.0f, -6.0f, 32.0f);
            float n14 = (float)(n8 * n12 + n10 * n13) * 100.0f;
            final float n15 = (float)(n8 * n13 - n10 * n12) * 100.0f;
            if (n14 < 0.0f) {
                n14 = 0.0f;
            }
            if (n14 > 165.0f) {
                n14 = 165.0f;
            }
            float n16 = clamp_float + MathHelper.sin((abstractClientPlayer.prevDistanceWalkedModified + (abstractClientPlayer.distanceWalkedModified - abstractClientPlayer.prevDistanceWalkedModified) * n3) * 6.0f) * 32.0f * (abstractClientPlayer.prevCameraYaw + (abstractClientPlayer.cameraYaw - abstractClientPlayer.prevCameraYaw) * n3);
            if (abstractClientPlayer.isSneaking()) {
                n16 += 25.0f;
                GlStateManager.translate(0.0f, 0.142f, -0.0178f);
            }
            GlStateManager.rotate(6.0f + n14 / 2.0f + n16, 1.0f, 0.0f, 0.0f);
            GlStateManager.rotate(n15 / 2.0f, 0.0f, 0.0f, 1.0f);
            GlStateManager.rotate(-n15 / 2.0f, 0.0f, 1.0f, 0.0f);
            GlStateManager.rotate(180.0f, 0.0f, 1.0f, 0.0f);
            this.playerRenderer.getMainModel().renderCape(0.0625f);
            GlStateManager.popMatrix();
        }
    }
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("\u0005<\u0011bvv@|fts", "FpNRF");
    }
    
    @Override
    public boolean shouldCombineTextures() {
        return "".length() != 0;
    }
    
    public LayerCape(final RenderPlayer playerRenderer) {
        this.playerRenderer = playerRenderer;
    }
    
    static {
        I();
        __OBFID = LayerCape.I["".length()];
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
            if (3 < 1) {
                throw null;
            }
        }
        return sb.toString();
    }
}
