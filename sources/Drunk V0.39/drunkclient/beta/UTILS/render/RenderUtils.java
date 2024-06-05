/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 *  org.lwjgl.util.glu.Sphere
 */
package drunkclient.beta.UTILS.render;

import java.awt.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.BlockPos;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.Sphere;

public class RenderUtils {
    static Minecraft mc = Minecraft.getMinecraft();

    public static void drawEntityESP(Entity entity, int red, int green, int blue) {
        try {
            mc.getRenderManager();
            double renderPosX = RenderManager.renderPosX;
            mc.getRenderManager();
            double renderPosY = RenderManager.renderPosY;
            mc.getRenderManager();
            double renderPosZ = RenderManager.renderPosZ;
            GL11.glEnable((int)3042);
            GL11.glBlendFunc((int)770, (int)771);
            GL11.glEnable((int)2848);
            GL11.glLineWidth((float)1.0f);
            GL11.glDisable((int)3553);
            GL11.glDisable((int)2929);
            GL11.glPushMatrix();
            GL11.glColor4d((double)((float)red / 255.0f), (double)((float)green / 255.0f), (double)((float)blue / 255.0f), (double)0.05f);
            GL11.glPushMatrix();
            GL11.glTranslated((double)(entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * 5.0 - renderPosX), (double)(entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * 5.0 - renderPosY), (double)(entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * 5.0 - renderPosZ));
            GL11.glColor4d((double)((float)red / 255.0f), (double)((float)green / 255.0f), (double)((float)blue / 255.0f), (double)255.0);
            GL11.glPopMatrix();
            GL11.glPopMatrix();
            GL11.glEnable((int)2929);
            GL11.glEnable((int)3553);
            GL11.glDisable((int)3042);
            GL11.glDisable((int)2848);
            return;
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    public static void drawTargetESP(Entity entity, int red, int green, int blue) {
        try {
            mc.getRenderManager();
            double renderPosX = RenderManager.renderPosX;
            mc.getRenderManager();
            double renderPosY = RenderManager.renderPosY;
            mc.getRenderManager();
            double renderPosZ = RenderManager.renderPosZ;
            GL11.glEnable((int)3042);
            GL11.glBlendFunc((int)770, (int)771);
            GL11.glEnable((int)2848);
            GL11.glLineWidth((float)1.0f);
            GL11.glDisable((int)3553);
            GL11.glDisable((int)2929);
            GL11.glPushMatrix();
            GL11.glColor4d((double)((float)red / 255.0f), (double)((float)green / 255.0f), (double)((float)blue / 255.0f), (double)0.05f);
            GL11.glPushMatrix();
            GL11.glTranslated((double)(entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * 5.0 - renderPosX), (double)(entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * 5.0 - renderPosY), (double)(entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * 5.0 - renderPosZ));
            GL11.glColor4d((double)((float)red / 255.0f), (double)((float)green / 255.0f), (double)((float)blue / 255.0f), (double)255.0);
            GL11.glPopMatrix();
            GL11.glPopMatrix();
            GL11.glEnable((int)2929);
            GL11.glEnable((int)3553);
            GL11.glDisable((int)3042);
            GL11.glDisable((int)2848);
            return;
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    public static void drawFreecamESP(double x1, double y1, double z1, int red, int green, int blue) {
        try {
            mc.getRenderManager();
            double renderPosX = RenderManager.renderPosX;
            mc.getRenderManager();
            double renderPosY = RenderManager.renderPosY;
            mc.getRenderManager();
            double renderPosZ = RenderManager.renderPosZ;
            GL11.glPushMatrix();
            GL11.glEnable((int)3042);
            GL11.glBlendFunc((int)770, (int)771);
            GL11.glLineWidth((float)2.0f);
            GL11.glDisable((int)2896);
            GL11.glDisable((int)3553);
            GL11.glLineWidth((float)2.0f);
            GL11.glEnable((int)2848);
            GL11.glDisable((int)2929);
            GL11.glDepthMask((boolean)false);
            GL11.glColor4d((double)red, (double)green, (double)blue, (double)0.1825f);
            double x = x1 - renderPosX;
            double y = y1 - renderPosY;
            double z = z1 - renderPosZ;
            GL11.glColor4d((double)red, (double)green, (double)blue, (double)1.0);
            GL11.glLineWidth((float)2.0f);
            GL11.glDisable((int)2848);
            GL11.glEnable((int)3553);
            GL11.glEnable((int)2896);
            GL11.glDisable((int)2896);
            GL11.glEnable((int)2929);
            GL11.glDepthMask((boolean)true);
            GL11.glDisable((int)3042);
            GL11.glPopMatrix();
            return;
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    public static void drawLogoutESP(double d, double d1, double d2, double r, double g, double b) {
        GL11.glPushMatrix();
        GL11.glEnable((int)3042);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glLineWidth((float)1.5f);
        GL11.glDisable((int)2896);
        GL11.glDisable((int)3553);
        GL11.glLineWidth((float)1.0f);
        GL11.glEnable((int)2848);
        GL11.glDisable((int)2929);
        GL11.glDepthMask((boolean)false);
        GL11.glColor4d((double)r, (double)g, (double)b, (double)0.1825f);
        GL11.glColor4d((double)r, (double)g, (double)b, (double)1.0);
        GL11.glLineWidth((float)2.0f);
        GL11.glDisable((int)2848);
        GL11.glEnable((int)3553);
        GL11.glEnable((int)2896);
        GL11.glDisable((int)2896);
        GL11.glEnable((int)2929);
        GL11.glDepthMask((boolean)true);
        GL11.glDisable((int)3042);
        GL11.glPopMatrix();
    }

    public static void drawBlockESP(BlockPos blockPos, int red, int green, int blue, int alpha, int outlineR, int outlineG, int outlineB, int outlineA, int outlineWidth) {
        try {
            mc.getRenderManager();
            double renderPosX = RenderManager.renderPosX;
            mc.getRenderManager();
            double renderPosY = RenderManager.renderPosY;
            mc.getRenderManager();
            double renderPosZ = RenderManager.renderPosZ;
            double x = (double)blockPos.getX() - renderPosX;
            double y = (double)blockPos.getY() - renderPosY;
            double z = (double)blockPos.getZ() - renderPosZ;
            GL11.glPushMatrix();
            GL11.glBlendFunc((int)770, (int)771);
            GL11.glEnable((int)3042);
            GL11.glLineWidth((float)outlineWidth);
            GL11.glDisable((int)3553);
            GL11.glDisable((int)2929);
            GL11.glDepthMask((boolean)false);
            GL11.glColor4d((double)((float)red / 255.0f), (double)((float)green / 255.0f), (double)((float)blue / 255.0f), (double)((float)alpha / 255.0f));
            GL11.glColor4d((double)((float)outlineR / 255.0f), (double)((float)outlineG / 255.0f), (double)((float)outlineB / 255.0f), (double)((float)outlineA / 255.0f));
            GL11.glLineWidth((float)2.0f);
            GL11.glEnable((int)3553);
            GL11.glEnable((int)2929);
            GL11.glDepthMask((boolean)true);
            GL11.glDisable((int)3042);
            GL11.glPopMatrix();
            return;
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    public static void blockEspFrame(BlockPos blockPos, double red, double green, double blue) {
        try {
            mc.getRenderManager();
            double renderPosX = RenderManager.renderPosX;
            mc.getRenderManager();
            double renderPosY = RenderManager.renderPosY;
            mc.getRenderManager();
            double renderPosZ = RenderManager.renderPosZ;
            double x = (double)blockPos.getX() - renderPosX;
            double y = (double)blockPos.getY() - renderPosY;
            double z = (double)blockPos.getZ() - renderPosZ;
            GL11.glBlendFunc((int)770, (int)771);
            GL11.glEnable((int)3042);
            GL11.glLineWidth((float)1.0f);
            GL11.glDisable((int)3553);
            GL11.glDisable((int)2929);
            GL11.glDepthMask((boolean)false);
            GL11.glColor4d((double)red, (double)green, (double)blue, (double)1.0);
            GL11.glEnable((int)3553);
            GL11.glEnable((int)2929);
            GL11.glDepthMask((boolean)true);
            GL11.glDisable((int)3042);
            return;
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    public static void blockEspBox(BlockPos blockPos, double red, double green, double blue) {
        try {
            mc.getRenderManager();
            double renderPosX = RenderManager.renderPosX;
            mc.getRenderManager();
            double renderPosY = RenderManager.renderPosY;
            mc.getRenderManager();
            double renderPosZ = RenderManager.renderPosZ;
            double x = (double)blockPos.getX() - renderPosX;
            double y = (double)blockPos.getY() - renderPosY;
            double z = (double)blockPos.getZ() - renderPosZ;
            GL11.glBlendFunc((int)770, (int)771);
            GL11.glEnable((int)3042);
            GL11.glLineWidth((float)2.0f);
            GL11.glDisable((int)3553);
            GL11.glDisable((int)2929);
            GL11.glDepthMask((boolean)false);
            GL11.glColor4d((double)red, (double)green, (double)blue, (double)0.15f);
            GL11.glEnable((int)3553);
            GL11.glEnable((int)2929);
            GL11.glDepthMask((boolean)true);
            GL11.glDisable((int)3042);
            return;
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    public static void box(double x, double y, double z, double x2, double y2, double z2, float red, float green, float blue, float alpha) {
        try {
            mc.getRenderManager();
            double renderPosX = RenderManager.renderPosX;
            mc.getRenderManager();
            double renderPosY = RenderManager.renderPosY;
            mc.getRenderManager();
            double renderPosZ = RenderManager.renderPosZ;
            GL11.glBlendFunc((int)770, (int)771);
            GL11.glEnable((int)3042);
            GL11.glLineWidth((float)2.0f);
            GL11.glDisable((int)3553);
            GL11.glDisable((int)2929);
            GL11.glDepthMask((boolean)false);
            GL11.glDepthMask((boolean)false);
            GL11.glColor4f((float)red, (float)green, (float)blue, (float)alpha);
            GL11.glColor4d((double)0.0, (double)0.0, (double)0.0, (double)0.5);
            GL11.glEnable((int)3553);
            GL11.glEnable((int)2929);
            GL11.glDepthMask((boolean)true);
            GL11.glDisable((int)3042);
            return;
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    public static void frame(double x, double y, double z, double x2, double y2, double z2, Color color) {
        try {
            mc.getRenderManager();
            double renderPosX = RenderManager.renderPosX;
            mc.getRenderManager();
            double renderPosY = RenderManager.renderPosY;
            mc.getRenderManager();
            double renderPosZ = RenderManager.renderPosZ;
            GL11.glBlendFunc((int)770, (int)771);
            GL11.glEnable((int)3042);
            GL11.glLineWidth((float)2.0f);
            GL11.glDisable((int)3553);
            GL11.glDisable((int)2929);
            GL11.glDepthMask((boolean)false);
            GL11.glEnable((int)3553);
            GL11.glEnable((int)2929);
            GL11.glDepthMask((boolean)true);
            GL11.glDisable((int)3042);
            return;
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    public static void drawSphere(double x, double y, double z, float size, int slices, int stacks) {
        Sphere s = new Sphere();
        try {
            mc.getRenderManager();
            double renderPosX = RenderManager.renderPosX;
            mc.getRenderManager();
            double renderPosY = RenderManager.renderPosY;
            mc.getRenderManager();
            double renderPosZ = RenderManager.renderPosZ;
            GL11.glPushMatrix();
            GL11.glBlendFunc((int)770, (int)771);
            GL11.glEnable((int)3042);
            GL11.glLineWidth((float)10.0f);
            GL11.glDisable((int)3553);
            GL11.glDisable((int)2929);
            GL11.glDepthMask((boolean)false);
            s.setDrawStyle(100013);
            GL11.glTranslated((double)(x - renderPosX), (double)(y - renderPosY), (double)(z - renderPosZ));
            s.draw(size, slices, stacks);
            GL11.glEnable((int)3553);
            GL11.glEnable((int)2929);
            GL11.glDepthMask((boolean)true);
            GL11.glDisable((int)3042);
            GL11.glPopMatrix();
            return;
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    public static void drawRoundedRect(float x, float y, float x1, float y1, int borderC, int insideC) {
        GL11.glScalef((float)0.5f, (float)0.5f, (float)0.5f);
        RenderUtils.drawVLine(x *= 2.0f, (y *= 2.0f) + 1.0f, (y1 *= 2.0f) - 2.0f, borderC);
        RenderUtils.drawVLine((x1 *= 2.0f) - 1.0f, y + 1.0f, y1 - 2.0f, borderC);
        RenderUtils.drawHLine(x + 2.0f, x1 - 3.0f, y, borderC);
        RenderUtils.drawHLine(x + 2.0f, x1 - 3.0f, y1 - 1.0f, borderC);
        RenderUtils.drawHLine(x + 1.0f, x + 1.0f, y + 1.0f, borderC);
        RenderUtils.drawHLine(x1 - 2.0f, x1 - 2.0f, y + 1.0f, borderC);
        RenderUtils.drawHLine(x1 - 2.0f, x1 - 2.0f, y1 - 2.0f, borderC);
        RenderUtils.drawHLine(x + 1.0f, x + 1.0f, y1 - 2.0f, borderC);
        RenderUtils.drawRect(x + 1.0f, y + 1.0f, x1 - 1.0f, y1 - 1.0f, insideC);
        GL11.glScalef((float)2.0f, (float)2.0f, (float)2.0f);
    }

    public static void drawBetterBorderedRect(int x, float y, int x1, int y1, float size, int borderC, int insideC) {
        RenderUtils.drawRect(x, y, x1, y1, insideC);
        RenderUtils.drawRect(x, y, x1, y + size, borderC);
        RenderUtils.drawRect(x, y1, x1, (float)y1 + size, borderC);
        RenderUtils.drawRect(x1, y, (float)x1 + size, (float)y1 + size, borderC);
        RenderUtils.drawRect(x, y, (float)x + size, (float)y1 + size, borderC);
    }

    public static void drawBorderedRect(double x, double y, double x2, double y2, float l1, int col1, int col2) {
        RenderUtils.drawRect((float)x, (float)y, (float)x2, (float)y2, col2);
        float f = (float)(col1 >> 24 & 0xFF) / 255.0f;
        float f1 = (float)(col1 >> 16 & 0xFF) / 255.0f;
        float f2 = (float)(col1 >> 8 & 0xFF) / 255.0f;
        float f3 = (float)(col1 & 0xFF) / 255.0f;
        GL11.glPushMatrix();
        GL11.glEnable((int)3042);
        GL11.glDisable((int)3553);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glEnable((int)2848);
        GL11.glColor4f((float)f1, (float)f2, (float)f3, (float)f);
        GL11.glLineWidth((float)l1);
        GL11.glBegin((int)1);
        GL11.glVertex2d((double)x, (double)y);
        GL11.glVertex2d((double)x, (double)y2);
        GL11.glVertex2d((double)x2, (double)y2);
        GL11.glVertex2d((double)x2, (double)y);
        GL11.glVertex2d((double)x, (double)y);
        GL11.glVertex2d((double)x2, (double)y);
        GL11.glVertex2d((double)x, (double)y2);
        GL11.glVertex2d((double)x2, (double)y2);
        GL11.glEnd();
        GL11.glEnable((int)3553);
        GL11.glDisable((int)3042);
        GL11.glDisable((int)2848);
        GL11.glPopMatrix();
    }

    public static void drawHLine(float par1, float par2, float par3, int par4) {
        if (par2 < par1) {
            float var5 = par1;
            par1 = par2;
            par2 = var5;
        }
        RenderUtils.drawRect(par1, par3, par2 + 1.0f, par3 + 1.0f, par4);
    }

    public static void drawVLine(float par1, float par2, float par3, int par4) {
        if (par3 < par2) {
            float var5 = par2;
            par2 = par3;
            par3 = var5;
        }
        RenderUtils.drawRect(par1, par2 + 1.0f, par1 + 1.0f, par3, par4);
    }

    public static void drawRect(float paramXStart, float paramYStart, float paramXEnd, float paramYEnd, int paramColor) {
        float alpha = (float)(paramColor >> 24 & 0xFF) / 255.0f;
        float red = (float)(paramColor >> 16 & 0xFF) / 255.0f;
        float green = (float)(paramColor >> 8 & 0xFF) / 255.0f;
        float blue = (float)(paramColor & 0xFF) / 255.0f;
        GL11.glPushMatrix();
        GL11.glEnable((int)3042);
        GL11.glDisable((int)3553);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glEnable((int)2848);
        GL11.glColor4f((float)red, (float)green, (float)blue, (float)alpha);
        GL11.glBegin((int)7);
        GL11.glVertex2d((double)paramXEnd, (double)paramYStart);
        GL11.glVertex2d((double)paramXStart, (double)paramYStart);
        GL11.glVertex2d((double)paramXStart, (double)paramYEnd);
        GL11.glVertex2d((double)paramXEnd, (double)paramYEnd);
        GL11.glEnd();
        GL11.glEnable((int)3553);
        GL11.glDisable((int)3042);
        GL11.glDisable((int)2848);
        GL11.glPopMatrix();
    }

    public static void drawGradientRect(double x, double y, double x2, double y2, int col1, int col2) {
        float f = (float)(col1 >> 24 & 0xFF) / 255.0f;
        float f1 = (float)(col1 >> 16 & 0xFF) / 255.0f;
        float f2 = (float)(col1 >> 8 & 0xFF) / 255.0f;
        float f3 = (float)(col1 & 0xFF) / 255.0f;
        float f4 = (float)(col2 >> 24 & 0xFF) / 255.0f;
        float f5 = (float)(col2 >> 16 & 0xFF) / 255.0f;
        float f6 = (float)(col2 >> 8 & 0xFF) / 255.0f;
        float f7 = (float)(col2 & 0xFF) / 255.0f;
        GL11.glEnable((int)3042);
        GL11.glDisable((int)3553);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glEnable((int)2848);
        GL11.glShadeModel((int)7425);
        GL11.glPushMatrix();
        GL11.glBegin((int)7);
        GL11.glColor4f((float)f1, (float)f2, (float)f3, (float)f);
        GL11.glVertex2d((double)x2, (double)y);
        GL11.glVertex2d((double)x, (double)y);
        GL11.glColor4f((float)f5, (float)f6, (float)f7, (float)f4);
        GL11.glVertex2d((double)x, (double)y2);
        GL11.glVertex2d((double)x2, (double)y2);
        GL11.glEnd();
        GL11.glPopMatrix();
        GL11.glEnable((int)3553);
        GL11.glDisable((int)3042);
        GL11.glDisable((int)2848);
        GL11.glShadeModel((int)7424);
    }

    public static void drawGradientBorderedRect(double x, double y, double x2, double y2, float l1, int col1, int col2, int col3) {
        float f = (float)(col1 >> 24 & 0xFF) / 255.0f;
        float f1 = (float)(col1 >> 16 & 0xFF) / 255.0f;
        float f2 = (float)(col1 >> 8 & 0xFF) / 255.0f;
        float f3 = (float)(col1 & 0xFF) / 255.0f;
        GL11.glDisable((int)3553);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glEnable((int)2848);
        GL11.glDisable((int)3042);
        GL11.glPushMatrix();
        GL11.glColor4f((float)f1, (float)f2, (float)f3, (float)f);
        GL11.glLineWidth((float)1.0f);
        GL11.glBegin((int)1);
        GL11.glVertex2d((double)x, (double)y);
        GL11.glVertex2d((double)x, (double)y2);
        GL11.glVertex2d((double)x2, (double)y2);
        GL11.glVertex2d((double)x2, (double)y);
        GL11.glVertex2d((double)x, (double)y);
        GL11.glVertex2d((double)x2, (double)y);
        GL11.glVertex2d((double)x, (double)y2);
        GL11.glVertex2d((double)x2, (double)y2);
        GL11.glEnd();
        GL11.glPopMatrix();
        RenderUtils.drawGradientRect(x, y, x2, y2, col2, col3);
        GL11.glEnable((int)3042);
        GL11.glEnable((int)3553);
        GL11.glDisable((int)2848);
    }

    public static void drawStrip(int x, int y, float width, double angle, float points, float radius, int color) {
        float yc;
        float xc;
        float a;
        int i;
        GL11.glPushMatrix();
        float f1 = (float)(color >> 24 & 0xFF) / 255.0f;
        float f2 = (float)(color >> 16 & 0xFF) / 255.0f;
        float f3 = (float)(color >> 8 & 0xFF) / 255.0f;
        float f4 = (float)(color & 0xFF) / 255.0f;
        GL11.glTranslatef((float)x, (float)y, (float)0.0f);
        GL11.glColor4f((float)f2, (float)f3, (float)f4, (float)f1);
        GL11.glLineWidth((float)width);
        GL11.glEnable((int)3042);
        GL11.glDisable((int)2929);
        GL11.glEnable((int)2848);
        GL11.glDisable((int)3553);
        GL11.glDisable((int)3008);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glHint((int)3154, (int)4354);
        GL11.glEnable((int)32925);
        if (angle > 0.0) {
            GL11.glBegin((int)3);
            i = 0;
            while ((double)i < angle) {
                a = (float)((double)i * (angle * Math.PI / (double)points));
                xc = (float)(Math.cos(a) * (double)radius);
                yc = (float)(Math.sin(a) * (double)radius);
                GL11.glVertex2f((float)xc, (float)yc);
                ++i;
            }
            GL11.glEnd();
        }
        if (angle < 0.0) {
            GL11.glBegin((int)3);
            i = 0;
            while ((double)i > angle) {
                a = (float)((double)i * (angle * Math.PI / (double)points));
                xc = (float)(Math.cos(a) * (double)(-radius));
                yc = (float)(Math.sin(a) * (double)(-radius));
                GL11.glVertex2f((float)xc, (float)yc);
                --i;
            }
            GL11.glEnd();
        }
        GL11.glDisable((int)3042);
        GL11.glEnable((int)3553);
        GL11.glDisable((int)2848);
        GL11.glEnable((int)3008);
        GL11.glEnable((int)2929);
        GL11.glDisable((int)32925);
        GL11.glDisable((int)3479);
        GL11.glPopMatrix();
    }

    public static void drawCircle(float cx, float cy, float r, int num_segments, int c) {
        GL11.glScalef((float)0.5f, (float)0.5f, (float)0.5f);
        cx *= 2.0f;
        cy *= 2.0f;
        float f = (float)(c >> 24 & 0xFF) / 255.0f;
        float f1 = (float)(c >> 16 & 0xFF) / 255.0f;
        float f2 = (float)(c >> 8 & 0xFF) / 255.0f;
        float f3 = (float)(c & 0xFF) / 255.0f;
        float theta = (float)(6.2831852 / (double)num_segments);
        float p = (float)Math.cos(theta);
        float s = (float)Math.sin(theta);
        GL11.glColor4f((float)f1, (float)f2, (float)f3, (float)f);
        float x = r *= 2.0f;
        float y = 0.0f;
        GL11.glEnable((int)3042);
        GL11.glDisable((int)3553);
        GL11.glEnable((int)2848);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glBegin((int)2);
        int ii = 0;
        while (true) {
            if (ii >= num_segments) {
                GL11.glEnd();
                GL11.glEnable((int)3553);
                GL11.glDisable((int)3042);
                GL11.glDisable((int)2848);
                GL11.glScalef((float)2.0f, (float)2.0f, (float)2.0f);
                return;
            }
            GL11.glVertex2f((float)(x + cx), (float)(y + cy));
            float t = x;
            x = p * x - s * y;
            y = s * t + p * y;
            ++ii;
        }
    }

    public static void drawFullCircle(int cx, int cy, double r, int c) {
        GL11.glScalef((float)0.5f, (float)0.5f, (float)0.5f);
        r *= 2.0;
        cx *= 2;
        cy *= 2;
        float f = (float)(c >> 24 & 0xFF) / 255.0f;
        float f1 = (float)(c >> 16 & 0xFF) / 255.0f;
        float f2 = (float)(c >> 8 & 0xFF) / 255.0f;
        float f3 = (float)(c & 0xFF) / 255.0f;
        GL11.glEnable((int)3042);
        GL11.glDisable((int)3553);
        GL11.glEnable((int)2848);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glColor4f((float)f1, (float)f2, (float)f3, (float)f);
        GL11.glBegin((int)6);
        int i = 0;
        while (true) {
            if (i > 360) {
                GL11.glEnd();
                GL11.glDisable((int)2848);
                GL11.glEnable((int)3553);
                GL11.glDisable((int)3042);
                GL11.glScalef((float)2.0f, (float)2.0f, (float)2.0f);
                return;
            }
            double x = Math.sin((double)i * Math.PI / 180.0) * r;
            double y = Math.cos((double)i * Math.PI / 180.0) * r;
            GL11.glVertex2d((double)((double)cx + x), (double)((double)cy + y));
            ++i;
        }
    }
}

