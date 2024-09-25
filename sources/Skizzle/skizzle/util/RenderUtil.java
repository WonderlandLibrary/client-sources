/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package skizzle.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import org.lwjgl.opengl.GL11;

public class RenderUtil {
    public static AxisAlignedBB DEFAULT_AABB = new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 1.0, 1.0);

    public static double interpolateValue(double Nigga, double Nigga2, float Nigga3) {
        return (Nigga2 - Nigga) * (double)Nigga3 + Nigga;
    }

    public static void drawSmoothTracer(Entity Nigga, float Nigga2) {
        Minecraft Nigga3 = Minecraft.getMinecraft();
        double Nigga4 = (Nigga.posX - Nigga.lastTickPosX) * (double)Nigga2 + Nigga.lastTickPosX;
        double Nigga5 = (Nigga.posY - Nigga.lastTickPosY) * (double)Nigga2 + Nigga.lastTickPosY;
        double Nigga6 = (Nigga.posZ - Nigga.lastTickPosZ) * (double)Nigga2 + Nigga.lastTickPosZ;
        GL11.glEnable((int)2848);
        RenderUtil.drawTracer(-(Nigga3.getRenderManager().renderPosX - Nigga4), -(Nigga3.getRenderManager().renderPosY - Nigga5), -(Nigga3.getRenderManager().renderPosZ - Nigga6), Nigga.getEyeHeight());
        GL11.glDisable((int)2848);
    }

    public static void scissor(double Nigga, double Nigga2, double Nigga3, double Nigga4) {
        Minecraft Nigga5 = Minecraft.getMinecraft();
        ScaledResolution Nigga6 = new ScaledResolution(Minecraft.getMinecraft(), Nigga5.displayWidth, Nigga5.displayHeight);
        double Nigga7 = Nigga6.getScaleFactor();
        double Nigga8 = (double)Nigga6.getScaledHeight() - Nigga2;
        GL11.glScissor((int)((int)(Nigga *= Nigga7)), (int)((int)((Nigga8 *= Nigga7) - (Nigga4 *= Nigga7))), (int)((int)(Nigga3 *= Nigga7)), (int)((int)Nigga4));
    }

    public static void initMask() {
        GL11.glClearDepth((double)1.0);
        GL11.glClear((int)256);
        GL11.glColorMask((boolean)false, (boolean)false, (boolean)false, (boolean)false);
        GL11.glDepthFunc((int)513);
        GL11.glEnable((int)2929);
        GL11.glDepthMask((boolean)true);
    }

    public static void drawRoundedRect(double Nigga, double Nigga2, double Nigga3, double Nigga4, double Nigga5, int Nigga6) {
        int Nigga7;
        GL11.glScaled((double)0.0, (double)0.0, (double)0.0);
        Nigga *= 2.0;
        Nigga2 *= 2.0;
        Nigga3 *= 2.0;
        Nigga4 *= 2.0;
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        RenderUtil.glColor(Nigga6);
        GL11.glBegin((int)9);
        for (Nigga7 = 0; Nigga7 <= 90; ++Nigga7) {
            GL11.glVertex2d((double)(Nigga + Nigga5 + Math.sin((double)Nigga7 * Math.PI / 180.0) * Nigga5 * -1.0), (double)(Nigga2 + Nigga5 + Math.cos((double)Nigga7 * Math.PI / 180.0) * Nigga5 * -1.0));
        }
        for (Nigga7 = 90; Nigga7 <= 180; ++Nigga7) {
            GL11.glVertex2d((double)(Nigga + Nigga5 + Math.sin((double)Nigga7 * Math.PI / 180.0) * Nigga5 * -1.0), (double)(Nigga4 - Nigga5 + Math.cos((double)Nigga7 * Math.PI / 180.0) * Nigga5 * -1.0));
        }
        for (Nigga7 = 0; Nigga7 <= 90; ++Nigga7) {
            GL11.glVertex2d((double)(Nigga3 - Nigga5 + Math.sin((double)Nigga7 * Math.PI / 180.0) * Nigga5), (double)(Nigga4 - Nigga5 + Math.cos((double)Nigga7 * Math.PI / 180.0) * Nigga5));
        }
        for (Nigga7 = 90; Nigga7 <= 180; ++Nigga7) {
            GL11.glVertex2d((double)(Nigga3 - Nigga5 + Math.sin((double)Nigga7 * Math.PI / 180.0) * Nigga5), (double)(Nigga2 + Nigga5 + Math.cos((double)Nigga7 * Math.PI / 180.0) * Nigga5));
        }
        GL11.glEnd();
        GlStateManager.enableTexture2D();
        GL11.glScaled((double)2.0, (double)2.0, (double)2.0);
        GlStateManager.disableBlend();
        GL11.glColor4d((double)1.0, (double)1.0, (double)1.0, (double)1.0);
    }

    public static void initMask(int Nigga) {
        GL11.glClearDepth((double)Nigga);
        GL11.glClear((int)256);
        GL11.glColorMask((boolean)false, (boolean)false, (boolean)false, (boolean)false);
        GL11.glDepthFunc((int)513);
        GL11.glEnable((int)2929);
        GL11.glDepthMask((boolean)true);
    }

    public static void useMask() {
        GL11.glColorMask((boolean)true, (boolean)true, (boolean)true, (boolean)true);
        GL11.glDepthMask((boolean)true);
        GL11.glDepthFunc((int)514);
    }

    public static void disableMask() {
        GL11.glDepthMask((boolean)true);
        GL11.glEnable((int)2929);
        GL11.glEnable((int)2929);
        GL11.glDepthFunc((int)515);
    }

    public static void drawTracer(double Nigga, double Nigga2, double Nigga3, double Nigga4) {
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.disableTexture2D();
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glEnable((int)3042);
        GL11.glEnable((int)2848);
        GL11.glLineWidth((float)Float.intBitsToFloat(1.06432909E9f ^ 0x7F705F6B));
        GL11.glDisable((int)3553);
        GL11.glDisable((int)2929);
        GL11.glDepthMask((boolean)false);
        GL11.glBegin((int)1);
        GL11.glVertex3d((double)Nigga, (double)Nigga2, (double)Nigga3);
        GL11.glVertex3d((double)0.0, (double)Minecraft.getMinecraft().thePlayer.getEyeHeight(), (double)0.0);
        GL11.glEnd();
        GL11.glBegin((int)1);
        GL11.glVertex3d((double)Nigga, (double)Nigga2, (double)Nigga3);
        GL11.glVertex3d((double)Nigga, (double)(Nigga2 + Nigga4), (double)Nigga3);
        GL11.glEnd();
        GL11.glEnable((int)3553);
        GL11.glEnable((int)2929);
        GL11.glDepthMask((boolean)true);
        GL11.glDisable((int)3042);
        GL11.glDisable((int)2848);
        GlStateManager.enableDepth();
        GlStateManager.depthMask(true);
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

    public static void drawSolidBlockESP(AxisAlignedBB Nigga, float Nigga2, float Nigga3, float Nigga4, float Nigga5, int Nigga6) {
        GL11.glPushMatrix();
        GL11.glEnable((int)3042);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glDisable((int)3553);
        GL11.glEnable((int)2848);
        GL11.glDisable((int)2929);
        GL11.glDepthMask((boolean)false);
        GL11.glColor4d((double)Nigga2, (double)Nigga3, (double)Nigga4, (double)Nigga5);
        GL11.glLineWidth((float)Float.intBitsToFloat(1.06173242E9f ^ 0x7F08C050));
        RenderUtil.drawCrate(Nigga, Nigga6);
        GL11.glDisable((int)3042);
        GL11.glEnable((int)3553);
        GL11.glDisable((int)2848);
        GL11.glEnable((int)2929);
        GL11.glDepthMask((boolean)true);
        GL11.glLineWidth((float)Float.intBitsToFloat(1.08766886E9f ^ 0x7F5482A1));
        GL11.glPopMatrix();
    }

    public static void drawBoundingBox(AxisAlignedBB Nigga, int Nigga2) {
        GL11.glEnable((int)3042);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glDisable((int)3553);
        GL11.glEnable((int)2848);
        GL11.glDisable((int)2929);
        GL11.glDepthMask((boolean)false);
        GL11.glEnable((int)2848);
        Tessellator Nigga3 = Tessellator.getInstance();
        WorldRenderer Nigga4 = Nigga3.getWorldRenderer();
        Nigga4.startDrawing(Nigga2);
        Nigga4.addVertex(Nigga.minX, Nigga.minY, Nigga.minZ);
        Nigga4.addVertex(Nigga.minX, Nigga.minY, Nigga.maxZ);
        Nigga4.addVertex(Nigga.maxX, Nigga.minY, Nigga.maxZ);
        Nigga4.addVertex(Nigga.maxX, Nigga.minY, Nigga.minZ);
        Nigga4.addVertex(Nigga.minX, Nigga.minY, Nigga.minZ);
        Nigga3.draw();
        Nigga4.startDrawing(Nigga2);
        Nigga4.addVertex(Nigga.maxX, Nigga.maxY, Nigga.maxZ);
        Nigga4.addVertex(Nigga.maxX, Nigga.maxY, Nigga.minZ);
        Nigga4.addVertex(Nigga.minX, Nigga.maxY, Nigga.minZ);
        Nigga4.addVertex(Nigga.minX, Nigga.maxY, Nigga.maxZ);
        Nigga4.addVertex(Nigga.maxX, Nigga.maxY, Nigga.maxZ);
        Nigga3.draw();
        Nigga4.startDrawing(Nigga2);
        Nigga4.addVertex(Nigga.minX, Nigga.minY, Nigga.minZ);
        Nigga4.addVertex(Nigga.minX, Nigga.maxY, Nigga.minZ);
        Nigga4.addVertex(Nigga.maxX, Nigga.maxY, Nigga.minZ);
        Nigga4.addVertex(Nigga.maxX, Nigga.minY, Nigga.minZ);
        Nigga4.addVertex(Nigga.minX, Nigga.minY, Nigga.minZ);
        Nigga3.draw();
        Nigga4.startDrawing(Nigga2);
        Nigga4.addVertex(Nigga.minX, Nigga.minY, Nigga.maxZ);
        Nigga4.addVertex(Nigga.minX, Nigga.maxY, Nigga.maxZ);
        Nigga3.draw();
        Nigga4.startDrawing(Nigga2);
        Nigga4.addVertex(Nigga.maxX, Nigga.minY, Nigga.maxZ);
        Nigga4.addVertex(Nigga.maxX, Nigga.maxY, Nigga.maxZ);
        Nigga3.draw();
        GL11.glDisable((int)2848);
        GL11.glEnable((int)3553);
        GL11.glEnable((int)2929);
        GL11.glDepthMask((boolean)true);
        GL11.glDisable((int)3042);
    }

    public RenderUtil() {
        RenderUtil Nigga;
    }

    public static void drawTriangle(double Nigga, double Nigga2, double Nigga3, double Nigga4, double Nigga5, double Nigga6, int Nigga7) {
        GL11.glDisable((int)3553);
        RenderUtil.glColor(Nigga7);
        GL11.glBegin((int)4);
        GL11.glVertex2d((double)Nigga, (double)Nigga2);
        GL11.glVertex2d((double)Nigga3, (double)Nigga4);
        GL11.glVertex2d((double)Nigga5, (double)Nigga6);
        GL11.glEnd();
        GL11.glEnable((int)3553);
    }

    public static void draw2DCircle(double Nigga, double Nigga2, double Nigga3, double Nigga4, boolean Nigga5, int Nigga6, int Nigga7) {
        if (Nigga5) {
            RenderUtil.drawRoundedRect(Nigga - Nigga3, Nigga2 - Nigga3, Nigga + Nigga3, Nigga2 + Nigga3, Nigga3 * 2.0, Nigga6);
        } else {
            GlStateManager.disableTexture2D();
            GL11.glEnable((int)3042);
            RenderUtil.glColor(Nigga6);
            GL11.glEnable((int)2848);
            GL11.glBegin((int)3);
            for (double Nigga8 = 0.0; Nigga8 <= (double)Nigga7; Nigga8 += Nigga4) {
                if (Nigga8 > 360.0) break;
                double Nigga9 = Nigga8 * Math.PI / 180.0;
                GL11.glVertex2d((double)(Nigga3 * Math.cos(Nigga9) + Nigga), (double)(Nigga3 * Math.sin(Nigga9) + Nigga2));
            }
            GL11.glEnd();
            GL11.glDisable((int)2848);
            GL11.glColor4d((double)1.0, (double)1.0, (double)1.0, (double)1.0);
            GlStateManager.enableTexture2D();
        }
    }

    public static void drawLine(double Nigga, double Nigga2, double Nigga3, double Nigga4, int Nigga5) {
        GlStateManager.disableTexture2D();
        RenderUtil.glColor(Nigga5);
        GL11.glBegin((int)3);
        GL11.glVertex2d((double)Nigga, (double)Nigga2);
        GL11.glVertex2d((double)Nigga3, (double)Nigga4);
        GL11.glEnd();
        GL11.glColor4d((double)1.0, (double)1.0, (double)1.0, (double)1.0);
        GlStateManager.enableTexture2D();
    }

    public static void drawRoundedRectWithShadow(double Nigga, double Nigga2, double Nigga3, double Nigga4, double Nigga5, int Nigga6, double Nigga7, double Nigga8, int Nigga9) {
        GL11.glTranslated((double)Nigga7, (double)Nigga8, (double)0.0);
        RenderUtil.drawRoundedRect(Nigga, Nigga2, Nigga3, Nigga4, Nigga5, Nigga9);
        GL11.glTranslated((double)(-Nigga7), (double)(-Nigga8), (double)0.0);
        RenderUtil.drawRoundedRect(Nigga, Nigga2, Nigga3, Nigga4, Nigga5, Nigga6);
    }

    public static void drawBorderedRoundedRect(float Nigga, float Nigga2, float Nigga3, float Nigga4, float Nigga5, int Nigga6, int Nigga7) {
        RenderUtil.drawRoundedRect(Nigga, Nigga2, Nigga3, Nigga4, Nigga5, Nigga6);
        RenderUtil.drawRoundedRect(Nigga + Float.intBitsToFloat(1.0751927E9f ^ 0x7F1623B2), Nigga2 + Float.intBitsToFloat(1.12059341E9f ^ 0x7DCAE627), Nigga3 - Float.intBitsToFloat(1.0739488E9f ^ 0x7F0328AD), Nigga4 - Float.intBitsToFloat(1.07827546E9f ^ 0x7F452D8F), Nigga5, Nigga7);
    }

    public static void drawCircle(double Nigga, double Nigga2, double Nigga3, double Nigga4, double Nigga5, boolean Nigga6) {
        GL11.glPushMatrix();
        GL11.glEnable((int)2848);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glEnable((int)3042);
        GL11.glDisable((int)3553);
        GL11.glDisable((int)2929);
        GlStateManager.translate(Nigga, Nigga2, Nigga3);
        GlStateManager.rotate(Minecraft.getMinecraft().thePlayer.rotationYaw, Float.intBitsToFloat(2.12733862E9f ^ 0x7ECC9CA7), Float.intBitsToFloat(-1.05945165E9f ^ 0x7F5A0D03), Float.intBitsToFloat(2.12112909E9f ^ 0x7E6DDC4F));
        GlStateManager.translate(-Nigga, -Nigga2, -Nigga3);
        GL11.glBegin((int)3);
        for (double Nigga7 = 0.0; Nigga7 <= 360.0; Nigga7 += Nigga5) {
            if (Nigga7 > 360.0) break;
            double Nigga8 = Nigga7 * Math.PI / 180.0;
            GL11.glVertex3d((double)(Nigga4 * Math.cos(Nigga8) + Nigga), (double)Nigga2, (double)(Nigga4 * Math.sin(Nigga8) + Nigga3));
            if (!Nigga6) continue;
            GL11.glVertex3d((double)Nigga, (double)Nigga2, (double)Nigga3);
        }
        GL11.glEnd();
        GL11.glDisable((int)3042);
        GL11.glEnable((int)3553);
        GL11.glEnable((int)2929);
        GL11.glDepthMask((boolean)true);
        GL11.glDisable((int)2848);
        GL11.glPopMatrix();
    }

    public static void glColor(int Nigga) {
        double Nigga2 = (float)(Nigga >> 24 & 0xFF) / Float.intBitsToFloat(1.00761504E9f ^ 0x7F71FC32);
        double Nigga3 = (float)(Nigga >> 16 & 0xFF) / Float.intBitsToFloat(1.00834227E9f ^ 0x7F6514EA);
        double Nigga4 = (float)(Nigga >> 8 & 0xFF) / Float.intBitsToFloat(1.02779725E9f ^ 0x7E3DF10B);
        double Nigga5 = (float)(Nigga & 0xFF) / Float.intBitsToFloat(1.03945798E9f ^ 0x7E8BDEDF);
        GL11.glColor4d((double)Nigga3, (double)Nigga4, (double)Nigga5, (double)Nigga2);
    }

    public static void drawCrate(AxisAlignedBB Nigga, int Nigga2) {
        Tessellator Nigga3 = Tessellator.getInstance();
        WorldRenderer Nigga4 = Nigga3.getWorldRenderer();
        Nigga4.startDrawing(Nigga2);
        Nigga4.addVertex(Nigga.minX, Nigga.minY, Nigga.minZ);
        Nigga4.addVertex(Nigga.minX, Nigga.maxY, Nigga.minZ);
        Nigga4.addVertex(Nigga.maxX, Nigga.minY, Nigga.minZ);
        Nigga4.addVertex(Nigga.maxX, Nigga.maxY, Nigga.minZ);
        Nigga4.addVertex(Nigga.maxX, Nigga.minY, Nigga.maxZ);
        Nigga4.addVertex(Nigga.maxX, Nigga.maxY, Nigga.maxZ);
        Nigga4.addVertex(Nigga.minX, Nigga.minY, Nigga.maxZ);
        Nigga4.addVertex(Nigga.minX, Nigga.maxY, Nigga.maxZ);
        Nigga3.draw();
        Nigga4.startDrawing(Nigga2);
        Nigga4.addVertex(Nigga.maxX, Nigga.maxY, Nigga.minZ);
        Nigga4.addVertex(Nigga.maxX, Nigga.minY, Nigga.minZ);
        Nigga4.addVertex(Nigga.minX, Nigga.maxY, Nigga.minZ);
        Nigga4.addVertex(Nigga.minX, Nigga.minY, Nigga.minZ);
        Nigga4.addVertex(Nigga.minX, Nigga.maxY, Nigga.maxZ);
        Nigga4.addVertex(Nigga.minX, Nigga.minY, Nigga.maxZ);
        Nigga4.addVertex(Nigga.maxX, Nigga.maxY, Nigga.maxZ);
        Nigga4.addVertex(Nigga.maxX, Nigga.minY, Nigga.maxZ);
        Nigga3.draw();
        Nigga4.startDrawing(Nigga2);
        Nigga4.addVertex(Nigga.minX, Nigga.maxY, Nigga.minZ);
        Nigga4.addVertex(Nigga.maxX, Nigga.maxY, Nigga.minZ);
        Nigga4.addVertex(Nigga.maxX, Nigga.maxY, Nigga.maxZ);
        Nigga4.addVertex(Nigga.minX, Nigga.maxY, Nigga.maxZ);
        Nigga4.addVertex(Nigga.minX, Nigga.maxY, Nigga.minZ);
        Nigga4.addVertex(Nigga.minX, Nigga.maxY, Nigga.maxZ);
        Nigga4.addVertex(Nigga.maxX, Nigga.maxY, Nigga.maxZ);
        Nigga4.addVertex(Nigga.maxX, Nigga.maxY, Nigga.minZ);
        Nigga3.draw();
        Nigga4.startDrawing(Nigga2);
        Nigga4.addVertex(Nigga.minX, Nigga.minY, Nigga.minZ);
        Nigga4.addVertex(Nigga.maxX, Nigga.minY, Nigga.minZ);
        Nigga4.addVertex(Nigga.maxX, Nigga.minY, Nigga.maxZ);
        Nigga4.addVertex(Nigga.minX, Nigga.minY, Nigga.maxZ);
        Nigga4.addVertex(Nigga.minX, Nigga.minY, Nigga.minZ);
        Nigga4.addVertex(Nigga.minX, Nigga.minY, Nigga.maxZ);
        Nigga4.addVertex(Nigga.maxX, Nigga.minY, Nigga.maxZ);
        Nigga4.addVertex(Nigga.maxX, Nigga.minY, Nigga.minZ);
        Nigga3.draw();
        Nigga4.startDrawing(Nigga2);
        Nigga4.addVertex(Nigga.minX, Nigga.minY, Nigga.minZ);
        Nigga4.addVertex(Nigga.minX, Nigga.maxY, Nigga.minZ);
        Nigga4.addVertex(Nigga.minX, Nigga.minY, Nigga.maxZ);
        Nigga4.addVertex(Nigga.minX, Nigga.maxY, Nigga.maxZ);
        Nigga4.addVertex(Nigga.maxX, Nigga.minY, Nigga.maxZ);
        Nigga4.addVertex(Nigga.maxX, Nigga.maxY, Nigga.maxZ);
        Nigga4.addVertex(Nigga.maxX, Nigga.minY, Nigga.minZ);
        Nigga4.addVertex(Nigga.maxX, Nigga.maxY, Nigga.minZ);
        Nigga3.draw();
        Nigga4.startDrawing(Nigga2);
        Nigga4.addVertex(Nigga.minX, Nigga.maxY, Nigga.maxZ);
        Nigga4.addVertex(Nigga.minX, Nigga.minY, Nigga.maxZ);
        Nigga4.addVertex(Nigga.minX, Nigga.maxY, Nigga.minZ);
        Nigga4.addVertex(Nigga.minX, Nigga.minY, Nigga.minZ);
        Nigga4.addVertex(Nigga.maxX, Nigga.maxY, Nigga.minZ);
        Nigga4.addVertex(Nigga.maxX, Nigga.minY, Nigga.minZ);
        Nigga4.addVertex(Nigga.maxX, Nigga.maxY, Nigga.maxZ);
        Nigga4.addVertex(Nigga.maxX, Nigga.minY, Nigga.maxZ);
        Nigga3.draw();
        if (Nigga2 != 7) {
            Nigga4.startDrawing(Nigga2);
            Nigga4.addVertex(Nigga.minX, Nigga.maxY, Nigga.minZ);
            Nigga4.addVertex(Nigga.maxX, Nigga.maxY, Nigga.maxZ);
            Nigga4.addVertex(Nigga.minX, Nigga.maxY, Nigga.maxZ);
            Nigga4.addVertex(Nigga.maxX, Nigga.maxY, Nigga.minZ);
            Nigga3.draw();
            Nigga4.startDrawing(Nigga2);
            Nigga4.addVertex(Nigga.minX, Nigga.minY, Nigga.minZ);
            Nigga4.addVertex(Nigga.maxX, Nigga.minY, Nigga.maxZ);
            Nigga4.addVertex(Nigga.minX, Nigga.minY, Nigga.maxZ);
            Nigga4.addVertex(Nigga.maxX, Nigga.minY, Nigga.minZ);
            Nigga3.draw();
        }
    }

    public static void drawBorderedRoundedRect(float Nigga, float Nigga2, float Nigga3, float Nigga4, float Nigga5, float Nigga6, int Nigga7, int Nigga8) {
        RenderUtil.drawRoundedRect(Nigga, Nigga2, Nigga3, Nigga4, Nigga5, Nigga7);
        RenderUtil.drawRoundedRect(Nigga + Nigga6, Nigga2 + Nigga6, Nigga3 - Nigga6, Nigga4 - Nigga6, Nigga5, Nigga8);
    }
}

