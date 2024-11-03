package net.silentclient.client.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.silentclient.client.mods.settings.FPSBoostMod;

public class CloudRenderer
{
    private static Frustum frustum;

    public static void setFrustum(final Frustum frustum) {
        CloudRenderer.frustum = frustum;
    }

    public static void renderFastClouds(final WorldRenderer bfd, final float n, final float n2, final float n3, final float n4, final float n5, final float n6) {
        final double s = Minecraft.getMinecraft().getRenderViewEntity().posX;
        final double t = Minecraft.getMinecraft().getRenderViewEntity().posY;
        final double u = Minecraft.getMinecraft().getRenderViewEntity().posZ;
        final boolean booleanValue = FPSBoostMod.basicEnabled();
        for (int i = -256; i < 256; i += 32) {
            for (int j = -256; j < 256; j += 32) {
                if (CloudRenderer.frustum.isBoxInFrustum(i + s, n + t - 2.0, j + u, i + 32 + s, n + t, j + 32 + u) || !booleanValue) {
                    bfd.pos((double)(i + 0), (double)n, (double)(j + 32)).tex((double)((i + 0) * 4.8828125E-4f + n2), (double)((j + 32) * 4.8828125E-4f + n3)).color(n4, n5, n6, 0.8f).endVertex();
                    bfd.pos((double)(i + 32), (double)n, (double)(j + 32)).tex((double)((i + 32) * 4.8828125E-4f + n2), (double)((j + 32) * 4.8828125E-4f + n3)).color(n4, n5, n6, 0.8f).endVertex();
                    bfd.pos((double)(i + 32), (double)n, (double)(j + 0)).tex((double)((i + 32) * 4.8828125E-4f + n2), (double)((j + 0) * 4.8828125E-4f + n3)).color(n4, n5, n6, 0.8f).endVertex();
                    bfd.pos((double)(i + 0), (double)n, (double)(j + 0)).tex((double)((i + 0) * 4.8828125E-4f + n2), (double)((j + 0) * 4.8828125E-4f + n3)).color(n4, n5, n6, 0.8f).endVertex();
                }
            }
        }
    }

    public static void renderFancyClouds(final WorldRenderer bfd, final Tessellator bfx, final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final float n7, final float n8, final float n9, final float n10, final float n11, final float n12, final float n13, final float n14, final float n15, final float n16, final float n17) {
        bfd.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR_NORMAL);
        final double s = Minecraft.getMinecraft().getRenderViewEntity().posX;
        final double t = Minecraft.getMinecraft().getRenderViewEntity().posY;
        final double u = Minecraft.getMinecraft().getRenderViewEntity().posZ;
        final boolean booleanValue = FPSBoostMod.basicEnabled();
        for (int i = -3; i <= 4; ++i) {
            for (int j = -3; j <= 4; ++j) {
                final float n18 = (float)(i * 8);
                final float n19 = (float)(j * 8);
                final float n20 = n18 - n16;
                final float n21 = n19 - n17;
                if (CloudRenderer.frustum.isBoxInFrustum(n20 * 12.0 + s, n + t, n21 * 12.0 + u, (n20 + 9.0f) * 12.0 + s, n + 4.0f + t, (n21 + 9.0f) * 12.0 + u) || !booleanValue) {
                    if (n > -5.0f) {
                        bfd.pos((double)(n20 + 0.0f), (double)(n + 0.0f), (double)(n21 + 8.0f)).tex((double)((n18 + 0.0f) * 0.00390625f + n2), (double)((n19 + 8.0f) * 0.00390625f + n3)).color(n7, n8, n9, 0.8f).normal(0.0f, -1.0f, 0.0f).endVertex();
                        bfd.pos((double)(n20 + 8.0f), (double)(n + 0.0f), (double)(n21 + 8.0f)).tex((double)((n18 + 8.0f) * 0.00390625f + n2), (double)((n19 + 8.0f) * 0.00390625f + n3)).color(n7, n8, n9, 0.8f).normal(0.0f, -1.0f, 0.0f).endVertex();
                        bfd.pos((double)(n20 + 8.0f), (double)(n + 0.0f), (double)(n21 + 0.0f)).tex((double)((n18 + 8.0f) * 0.00390625f + n2), (double)((n19 + 0.0f) * 0.00390625f + n3)).color(n7, n8, n9, 0.8f).normal(0.0f, -1.0f, 0.0f).endVertex();
                        bfd.pos((double)(n20 + 0.0f), (double)(n + 0.0f), (double)(n21 + 0.0f)).tex((double)((n18 + 0.0f) * 0.00390625f + n2), (double)((n19 + 0.0f) * 0.00390625f + n3)).color(n7, n8, n9, 0.8f).normal(0.0f, -1.0f, 0.0f).endVertex();
                    }
                    if (n <= 5.0f) {
                        bfd.pos((double)(n20 + 0.0f), (double)(n + 4.0f - 9.765625E-4f), (double)(n21 + 8.0f)).tex((double)((n18 + 0.0f) * 0.00390625f + n2), (double)((n19 + 8.0f) * 0.00390625f + n3)).color(n4, n5, n6, 0.8f).normal(0.0f, 1.0f, 0.0f).endVertex();
                        bfd.pos((double)(n20 + 8.0f), (double)(n + 4.0f - 9.765625E-4f), (double)(n21 + 8.0f)).tex((double)((n18 + 8.0f) * 0.00390625f + n2), (double)((n19 + 8.0f) * 0.00390625f + n3)).color(n4, n5, n6, 0.8f).normal(0.0f, 1.0f, 0.0f).endVertex();
                        bfd.pos((double)(n20 + 8.0f), (double)(n + 4.0f - 9.765625E-4f), (double)(n21 + 0.0f)).tex((double)((n18 + 8.0f) * 0.00390625f + n2), (double)((n19 + 0.0f) * 0.00390625f + n3)).color(n4, n5, n6, 0.8f).normal(0.0f, 1.0f, 0.0f).endVertex();
                        bfd.pos((double)(n20 + 0.0f), (double)(n + 4.0f - 9.765625E-4f), (double)(n21 + 0.0f)).tex((double)((n18 + 0.0f) * 0.00390625f + n2), (double)((n19 + 0.0f) * 0.00390625f + n3)).color(n4, n5, n6, 0.8f).normal(0.0f, 1.0f, 0.0f).endVertex();
                    }
                    if (i > -1) {
                        for (int k = 0; k < 8; ++k) {
                            bfd.pos((double)(n20 + k + 0.0f), (double)(n + 0.0f), (double)(n21 + 8.0f)).tex((double)((n18 + k + 0.5f) * 0.00390625f + n2), (double)((n19 + 8.0f) * 0.00390625f + n3)).color(n13, n14, n15, 0.8f).normal(-1.0f, 0.0f, 0.0f).endVertex();
                            bfd.pos((double)(n20 + k + 0.0f), (double)(n + 4.0f), (double)(n21 + 8.0f)).tex((double)((n18 + k + 0.5f) * 0.00390625f + n2), (double)((n19 + 8.0f) * 0.00390625f + n3)).color(n13, n14, n15, 0.8f).normal(-1.0f, 0.0f, 0.0f).endVertex();
                            bfd.pos((double)(n20 + k + 0.0f), (double)(n + 4.0f), (double)(n21 + 0.0f)).tex((double)((n18 + k + 0.5f) * 0.00390625f + n2), (double)((n19 + 0.0f) * 0.00390625f + n3)).color(n13, n14, n15, 0.8f).normal(-1.0f, 0.0f, 0.0f).endVertex();
                            bfd.pos((double)(n20 + k + 0.0f), (double)(n + 0.0f), (double)(n21 + 0.0f)).tex((double)((n18 + k + 0.5f) * 0.00390625f + n2), (double)((n19 + 0.0f) * 0.00390625f + n3)).color(n13, n14, n15, 0.8f).normal(-1.0f, 0.0f, 0.0f).endVertex();
                        }
                    }
                    if (i <= 1) {
                        for (int l = 0; l < 8; ++l) {
                            bfd.pos((double)(n20 + l + 1.0f - 9.765625E-4f), (double)(n + 0.0f), (double)(n21 + 8.0f)).tex((double)((n18 + l + 0.5f) * 0.00390625f + n2), (double)((n19 + 8.0f) * 0.00390625f + n3)).color(n13, n14, n15, 0.8f).normal(1.0f, 0.0f, 0.0f).endVertex();
                            bfd.pos((double)(n20 + l + 1.0f - 9.765625E-4f), (double)(n + 4.0f), (double)(n21 + 8.0f)).tex((double)((n18 + l + 0.5f) * 0.00390625f + n2), (double)((n19 + 8.0f) * 0.00390625f + n3)).color(n13, n14, n15, 0.8f).normal(1.0f, 0.0f, 0.0f).endVertex();
                            bfd.pos((double)(n20 + l + 1.0f - 9.765625E-4f), (double)(n + 4.0f), (double)(n21 + 0.0f)).tex((double)((n18 + l + 0.5f) * 0.00390625f + n2), (double)((n19 + 0.0f) * 0.00390625f + n3)).color(n13, n14, n15, 0.8f).normal(1.0f, 0.0f, 0.0f).endVertex();
                            bfd.pos((double)(n20 + l + 1.0f - 9.765625E-4f), (double)(n + 0.0f), (double)(n21 + 0.0f)).tex((double)((n18 + l + 0.5f) * 0.00390625f + n2), (double)((n19 + 0.0f) * 0.00390625f + n3)).color(n13, n14, n15, 0.8f).normal(1.0f, 0.0f, 0.0f).endVertex();
                        }
                    }
                    if (j > -1) {
                        for (int n22 = 0; n22 < 8; ++n22) {
                            bfd.pos((double)(n20 + 0.0f), (double)(n + 4.0f), (double)(n21 + n22 + 0.0f)).tex((double)((n18 + 0.0f) * 0.00390625f + n2), (double)((n19 + n22 + 0.5f) * 0.00390625f + n3)).color(n10, n11, n12, 0.8f).normal(0.0f, 0.0f, -1.0f).endVertex();
                            bfd.pos((double)(n20 + 8.0f), (double)(n + 4.0f), (double)(n21 + n22 + 0.0f)).tex((double)((n18 + 8.0f) * 0.00390625f + n2), (double)((n19 + n22 + 0.5f) * 0.00390625f + n3)).color(n10, n11, n12, 0.8f).normal(0.0f, 0.0f, -1.0f).endVertex();
                            bfd.pos((double)(n20 + 8.0f), (double)(n + 0.0f), (double)(n21 + n22 + 0.0f)).tex((double)((n18 + 8.0f) * 0.00390625f + n2), (double)((n19 + n22 + 0.5f) * 0.00390625f + n3)).color(n10, n11, n12, 0.8f).normal(0.0f, 0.0f, -1.0f).endVertex();
                            bfd.pos((double)(n20 + 0.0f), (double)(n + 0.0f), (double)(n21 + n22 + 0.0f)).tex((double)((n18 + 0.0f) * 0.00390625f + n2), (double)((n19 + n22 + 0.5f) * 0.00390625f + n3)).color(n10, n11, n12, 0.8f).normal(0.0f, 0.0f, -1.0f).endVertex();
                        }
                    }
                    if (j <= 1) {
                        for (int n23 = 0; n23 < 8; ++n23) {
                            bfd.pos((double)(n20 + 0.0f), (double)(n + 4.0f), (double)(n21 + n23 + 1.0f - 9.765625E-4f)).tex((double)((n18 + 0.0f) * 0.00390625f + n2), (double)((n19 + n23 + 0.5f) * 0.00390625f + n3)).color(n10, n11, n12, 0.8f).normal(0.0f, 0.0f, 1.0f).endVertex();
                            bfd.pos((double)(n20 + 8.0f), (double)(n + 4.0f), (double)(n21 + n23 + 1.0f - 9.765625E-4f)).tex((double)((n18 + 8.0f) * 0.00390625f + n2), (double)((n19 + n23 + 0.5f) * 0.00390625f + n3)).color(n10, n11, n12, 0.8f).normal(0.0f, 0.0f, 1.0f).endVertex();
                            bfd.pos((double)(n20 + 8.0f), (double)(n + 0.0f), (double)(n21 + n23 + 1.0f - 9.765625E-4f)).tex((double)((n18 + 8.0f) * 0.00390625f + n2), (double)((n19 + n23 + 0.5f) * 0.00390625f + n3)).color(n10, n11, n12, 0.8f).normal(0.0f, 0.0f, 1.0f).endVertex();
                            bfd.pos((double)(n20 + 0.0f), (double)(n + 0.0f), (double)(n21 + n23 + 1.0f - 9.765625E-4f)).tex((double)((n18 + 0.0f) * 0.00390625f + n2), (double)((n19 + n23 + 0.5f) * 0.00390625f + n3)).color(n10, n11, n12, 0.8f).normal(0.0f, 0.0f, 1.0f).endVertex();
                        }
                    }
                }
            }
        }
        bfx.draw();
    }
}
