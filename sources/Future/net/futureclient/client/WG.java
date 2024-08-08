package net.futureclient.client;

import net.minecraft.client.gui.Gui;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import java.awt.Color;
import org.lwjgl.input.Mouse;
import net.minecraft.client.Minecraft;
import java.awt.Point;

public class WG
{
    public static float k;
    
    public WG() {
        super();
    }
    
    static {
        WG.k = 0.0f;
    }
    
    public static Point M() {
        final Minecraft minecraft;
        int guiScale;
        if ((guiScale = (minecraft = Minecraft.getMinecraft()).gameSettings.guiScale) == 0) {
            guiScale = 1000;
        }
        int n = 0;
        int n2;
        for (n2 = 0; n < guiScale && minecraft.displayWidth / (n2 + 1) >= 320 && minecraft.displayHeight / (n2 + 1) >= 240; n = ++n2) {}
        return new Point(Mouse.getX() / n2, minecraft.displayHeight / n2 - Mouse.getY() / n2 - 1);
    }
    
    public static void M(final Color color) {
        GL11.glColor4f(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, color.getAlpha() / 255.0f);
    }
    
    public static void M(final double n, final double n2, final double n3, final double n4, final int n5) {
        final float n6 = (n5 >> 24 & 0xFF) / 255.0f;
        final float n7 = (n5 >> 16 & 0xFF) / 255.0f;
        final float n8 = (n5 >> 8 & 0xFF) / 255.0f;
        final float n9 = (n5 & 0xFF) / 255.0f;
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GL11.glPushMatrix();
        GL11.glColor4f(n7, n8, n9, n6);
        GL11.glBegin(7);
        GL11.glVertex2d(n, n4);
        GL11.glVertex2d(n3, n4);
        GL11.glVertex2d(n3, n2);
        GL11.glVertex2d(n, n2);
        GL11.glEnd();
        GL11.glPopMatrix();
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glDisable(2848);
    }
    
    public static void M(final double n, final double n2, final double n3, final double n4, final float n5, final int n6, final int n7) {
        M(n, n2, n3, n4, n7);
        final float n8 = (n6 >> 24 & 0xFF) / 255.0f;
        final float n9 = (n6 >> 16 & 0xFF) / 255.0f;
        final float n10 = (n6 >> 8 & 0xFF) / 255.0f;
        final float n11 = (n6 & 0xFF) / 255.0f;
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GL11.glPushMatrix();
        GL11.glColor4f(n9, n10, n11, n8);
        GL11.glLineWidth(n5);
        GL11.glBegin(1);
        GL11.glVertex2d(n, n2);
        GL11.glVertex2d(n, n4);
        GL11.glVertex2d(n3, n4);
        GL11.glVertex2d(n3, n2);
        GL11.glVertex2d(n, n2);
        GL11.glVertex2d(n3, n2);
        GL11.glVertex2d(n, n4);
        GL11.glVertex2d(n3, n4);
        GL11.glEnd();
        GL11.glPopMatrix();
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glDisable(2848);
        final float n12 = 0.0f;
        final int n13 = 0;
        GL11.glColor4f((float)n13, (float)n13, n12, (float)n13);
    }
    
    public static void M(final float n, final float n2, final ResourceLocation resourceLocation) {
        GL11.glPushMatrix();
        Minecraft.getMinecraft().getTextureManager().bindTexture(resourceLocation);
        GlStateManager.enableBlend();
        GL11.glEnable(2848);
        GlStateManager.enableRescaleNormal();
        GlStateManager.enableAlpha();
        GlStateManager.alphaFunc(516, 0.1f);
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(770, 771);
        GL11.glTranslatef(n, n2, 0.0f);
        final int n3 = 0;
        final int n4 = 0;
        final float n5 = 0.0f;
        final int n6 = 0;
        final int n7 = 12;
        final int n8 = 12;
        M(n3, n4, n5, (float)n6, n8, n8, n7, n8, 12.0f, 12.0f);
        GlStateManager.disableAlpha();
        GlStateManager.disableRescaleNormal();
        GlStateManager.disableLighting();
        GlStateManager.disableRescaleNormal();
        GL11.glDisable(2848);
        GlStateManager.disableBlend();
        GL11.glPopMatrix();
    }
    
    public static void M(final AxisAlignedBB axisAlignedBB, final Color color) {
        GL11.glPushMatrix();
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glDisable(2896);
        GL11.glDisable(3553);
        GL11.glEnable(2848);
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        final int n = 2848;
        GL11.glColor4f(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, color.getAlpha() / 255.0f);
        M(axisAlignedBB);
        GL11.glDisable(n);
        GL11.glEnable(3553);
        GL11.glEnable(2896);
        GL11.glEnable(2929);
        GL11.glDepthMask(true);
        GL11.glDisable(3042);
        GL11.glPopMatrix();
    }
    
    public static void M(final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final float n7, final float n8, final Color color) {
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GL11.glPushMatrix();
        final int n9 = 7;
        M(color);
        GL11.glBegin(n9);
        GL11.glVertex2d((double)n7, (double)n8);
        GL11.glVertex2d((double)n5, (double)n6);
        GL11.glVertex2d((double)n3, (double)n4);
        GL11.glVertex2d((double)n, (double)n2);
        GL11.glEnd();
        GL11.glBegin(7);
        GL11.glVertex2d((double)n, (double)n2);
        GL11.glVertex2d((double)n3, (double)n4);
        GL11.glVertex2d((double)n5, (double)n6);
        GL11.glVertex2d((double)n7, (double)n8);
        GL11.glEnd();
        GL11.glPopMatrix();
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glDisable(2848);
    }
    
    public static void M(final AxisAlignedBB axisAlignedBB) {
        if (axisAlignedBB == null) {
            return;
        }
        GL11.glBegin(7);
        final int n = 7;
        final int n2 = 7;
        final int n3 = 7;
        final int n4 = 7;
        final int n5 = 7;
        final int n6 = 7;
        GL11.glVertex3d(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ);
        GL11.glVertex3d(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ);
        GL11.glVertex3d(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ);
        GL11.glVertex3d(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ);
        GL11.glVertex3d(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ);
        GL11.glVertex3d(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ);
        GL11.glVertex3d(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ);
        GL11.glVertex3d(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ);
        GL11.glEnd();
        GL11.glBegin(n6);
        GL11.glVertex3d(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ);
        GL11.glVertex3d(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ);
        GL11.glVertex3d(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ);
        GL11.glVertex3d(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ);
        GL11.glVertex3d(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ);
        GL11.glVertex3d(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ);
        GL11.glVertex3d(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ);
        GL11.glVertex3d(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ);
        GL11.glEnd();
        GL11.glBegin(n5);
        GL11.glVertex3d(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ);
        GL11.glVertex3d(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ);
        GL11.glVertex3d(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ);
        GL11.glVertex3d(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ);
        GL11.glEnd();
        GL11.glBegin(n4);
        GL11.glVertex3d(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ);
        GL11.glVertex3d(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ);
        GL11.glVertex3d(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ);
        GL11.glVertex3d(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ);
        GL11.glVertex3d(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ);
        GL11.glVertex3d(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ);
        GL11.glVertex3d(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ);
        GL11.glVertex3d(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ);
        GL11.glEnd();
        GL11.glBegin(n3);
        GL11.glVertex3d(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ);
        GL11.glVertex3d(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ);
        GL11.glVertex3d(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ);
        GL11.glVertex3d(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ);
        GL11.glVertex3d(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ);
        GL11.glVertex3d(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ);
        GL11.glVertex3d(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ);
        GL11.glVertex3d(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ);
        GL11.glEnd();
        GL11.glBegin(n2);
        GL11.glVertex3d(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ);
        GL11.glVertex3d(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ);
        GL11.glVertex3d(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ);
        GL11.glVertex3d(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ);
        GL11.glVertex3d(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ);
        GL11.glVertex3d(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ);
        GL11.glVertex3d(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ);
        GL11.glVertex3d(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ);
        GL11.glEnd();
        GL11.glBegin(n);
        GL11.glVertex3d(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ);
        GL11.glVertex3d(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ);
        GL11.glVertex3d(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ);
        GL11.glVertex3d(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ);
        GL11.glVertex3d(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ);
        GL11.glVertex3d(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ);
        GL11.glVertex3d(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ);
        GL11.glVertex3d(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ);
        GL11.glEnd();
    }
    
    public static String M(final String s) {
        final StringBuilder sb = new StringBuilder();
        final int n = 0;
        return sb.insert(n, String.valueOf(Character.toUpperCase(s.charAt(n)))).append(s.substring(1)).toString();
    }
    
    public static void M(final float n, final float n2, final ResourceLocation resourceLocation, final int n3, final int n4) {
        Minecraft.getMinecraft().getTextureManager().bindTexture(resourceLocation);
        GlStateManager.enableBlend();
        GL11.glEnable(2848);
        GlStateManager.enableRescaleNormal();
        GlStateManager.enableAlpha();
        GlStateManager.alphaFunc(516, 0.1f);
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(770, 771);
        GL11.glTranslatef(n, n2, 0.0f);
        M(0, 0, 0.0f, 0, n3, n4, n3, n4, (float)n3, (float)n4);
        GlStateManager.disableAlpha();
        GlStateManager.disableRescaleNormal();
        GlStateManager.disableLighting();
        GlStateManager.disableRescaleNormal();
        GL11.glDisable(2848);
        GlStateManager.disableBlend();
    }
    
    public static int M(final String s, final int n, final int n2, final int n3) {
        return Minecraft.getMinecraft().fontRenderer.drawString(s, (float)n, (float)n2, n3, false);
    }
    
    public static void M(final AxisAlignedBB axisAlignedBB, final float n, final Color color) {
        GL11.glPushMatrix();
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glDisable(2896);
        GL11.glDisable(3553);
        GL11.glEnable(2848);
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        GL11.glLineWidth(n);
        final float n2 = 1.0f;
        GL11.glColor4f(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, color.getAlpha() / 255.0f);
        xG.B(axisAlignedBB);
        GL11.glLineWidth(n2);
        GL11.glDisable(2848);
        GL11.glEnable(3553);
        GL11.glEnable(2896);
        GL11.glEnable(2929);
        GL11.glDepthMask(true);
        GL11.glDisable(3042);
        GL11.glPopMatrix();
    }
    
    public static void M(final int n, final int n2, double n3, double n4, final int n5) {
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glEnable(2848);
        GL11.glDisable(2884);
        GL11.glBlendFunc(770, 771);
        final double n6 = 6.984873503E-315 / n5 / 0.0;
        n4 = n4;
        final double n7 = n3 - 0.0;
        final double n8 = n3 + 0.0;
        GL11.glBegin(8);
        int i = 0;
        int n9 = 0;
        while (i <= n5) {
            n3 = n9 * 0.0 * 6.984873503E-315 / n5;
            GL11.glVertex2d(n4 * Math.cos(n3) + n, n4 * Math.sin(n3) + n2);
            GL11.glVertex2d(n7 * Math.cos(n3) + n, n7 * Math.sin(n3) + n2);
            GL11.glVertex2d(n4 * Math.cos(n3) + n, n4 * Math.sin(n3) + n2);
            final double n10 = n7 * Math.cos(n3 + 0.0 * n6) + n;
            final double n11 = n7 * Math.sin(n3 + 0.0 * n6);
            ++n9;
            GL11.glVertex2d(n10, n11 + n2);
            i = n9;
        }
        GL11.glEnd();
        GL11.glBegin(7);
        final double n12 = 6.984873503E-315 / n5 / 0.0;
        int j = 0;
        int n13 = 0;
        while (j < n5) {
            n3 = n13 * 0.0 * 6.984873503E-315 / n5;
            GL11.glVertex2d(n7 * Math.cos(n3) + n, n7 * Math.sin(n3) + n2);
            GL11.glVertex2d(n8 * Math.cos(n3 + n12) + n, n8 * Math.sin(n3 + n12) + n2);
            GL11.glVertex2d(n8 * Math.cos(n3 + 0.0 * n12) + n, n8 * Math.sin(n3 + 0.0 * n12) + n2);
            final double n14 = n7 * Math.cos(n3 + 0.0 * n12) + n;
            final double n15 = n7 * Math.sin(n3 + 0.0 * n12);
            ++n13;
            GL11.glVertex2d(n14, n15 + n2);
            j = n13;
        }
        GL11.glEnd();
        GL11.glDisable(2848);
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glEnable(2884);
    }
    
    public static void M(final int n, final int n2, final float n3, final float n4, final int n5, final int n6, final int n7, final int n8, final float n9, final float n10) {
        Gui.drawScaledCustomSizeModalRect(n, n2, n3, n4, n5, n6, n7, n8, n9, n10);
    }
}
