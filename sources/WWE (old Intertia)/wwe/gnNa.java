package wwe;

import org.lwjgl.opengl.*;
import javax.vecmath.*;
import java.awt.*;
import net.minecraft.client.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.gui.*;

public class gnNa
{
    private static float ULGM;
    
    public gnNa() {
        super();
    }
    
    public static float MvGO() {
        return gnNa.ULGM;
    }
    
    public static void ZODf(final int n, final int n2, final int n3, final int n4, final float n5, final int n6, final int n7) {
        NOOc();
        Gui.drawRect(n + (int)n5, n2 + (int)n5, n3 - (int)n5, n4 - (int)n5, n6);
        Gui.drawRect(n + (int)n5, n2, n3 - (int)n5, n2 + (int)n5, n7);
        Gui.drawRect(n, n2, n + (int)n5, n4, n7);
        Gui.drawRect(n3 - (int)n5, n2, n3, n4, n7);
        Gui.drawRect(n + (int)n5, n4 - (int)n5, n3 - (int)n5, n4, n7);
        uCta();
    }
    
    public static void lVby(final int n, final int n2, final int n3, final int n4, final float n5, final int n6, final int n7) {
        NOOc();
        Gui.drawRect(n + (int)n5, n2 + (int)n5, n3 - (int)n5, n4 - (int)n5, n6);
        Gui.drawRect(n + (int)n5 - 1, n2, n3 - (int)n5 + 1, n2 + (int)n5, n7);
        Gui.drawRect(n, n2, n + (int)n5, n4, n7);
        Gui.drawRect(n3 - (int)n5, n2, n3, n4, n7);
        Gui.drawRect(n + (int)n5 - 1, n4 - (int)n5, n3 - (int)n5 + 1, n4, n7);
        uCta();
    }
    
    public static void dYaT(final float n, final float n2, final float n3, final float n4, final float n5, final int n6, final int n7) {
        NOOc();
        OKXQ(n, n2, n3, n4, n6);
        zwAE(n7);
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glLineWidth(n5);
        GL11.glBegin(3);
        GL11.glVertex2f(n, n2);
        GL11.glVertex2f(n, n4);
        GL11.glVertex2f(n3, n4);
        GL11.glVertex2f(n3, n2);
        GL11.glVertex2f(n, n2);
        GL11.glEnd();
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        uCta();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
    }
    
    public static void Bdzq(final float n, final float n2, final float n3, final float n4, final float n5, final int n6, final int n7, final int n8) {
        NOOc();
        OKXQ(n, n2, n3, n4, n6);
        zwAE(n7);
        GL11.glEnable(2848);
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glLineWidth(n5);
        GL11.glBegin(3);
        GL11.glVertex2f(n, n2);
        GL11.glVertex2f(n, n4 + n8);
        GL11.glVertex2f(n3, n4);
        GL11.glVertex2f(n3, n2);
        GL11.glVertex2f(n, n2);
        GL11.glEnd();
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glDisable(2848);
        uCta();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
    }
    
    public static void Qhyb(final double n, final double n2, final double n3, final double n4, final float n5, final int n6, final int n7) {
        NOOc();
        OKXQ(n, n2, n3, n4, n6);
        zwAE(n7);
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glLineWidth(n5);
        GL11.glBegin(3);
        GL11.glVertex2d(n, n2);
        GL11.glVertex2d(n, n4);
        GL11.glVertex2d(n3, n4);
        GL11.glVertex2d(n3, n2);
        GL11.glVertex2d(n, n2);
        GL11.glEnd();
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        uCta();
    }
    
    public static void jleU(final Vector2f vector2f, final float n, final float n2, final float[] array, final float n3, final float n4) {
        final float n5 = (float)(vector2f.x + Math.sin(n3) * n);
        final float n6 = (float)(vector2f.y + Math.cos(n3) * n);
        final float n7 = (float)(vector2f.x + Math.sin(n3) * n2);
        final float n8 = (float)(vector2f.y + Math.cos(n3) * n2);
        final float n9 = (float)(vector2f.x + Math.sin(n3 + n4) * n2);
        final float n10 = (float)(vector2f.y + Math.cos(n3 + n4) * n2);
        final float n11 = (float)(vector2f.x + Math.sin(n3 + n4) * n);
        final float n12 = (float)(vector2f.y + Math.cos(n3 + n4) * n);
        final float n13 = array[3];
        final float n14 = array[0];
        final float n15 = array[1];
        final float n16 = array[2];
        final BufferBuilder buffer = Tessellator.getInstance().getBuffer();
        buffer.endVertex();
        buffer.pos((double)n5, (double)n6, 0.0);
        buffer.pos((double)n7, (double)n8, 0.0);
        buffer.pos((double)n9, (double)n10, 0.0);
        buffer.pos((double)n11, (double)n12, 0.0);
    }
    
    public static void ZGVd(final double n, final double n2, final double n3, final double n4, final float n5, final int n6) {
        NOOc();
        OKXQ(n + (int)n5 - 1.0, n2, n3 - (int)n5 + 1.0, n2 + (int)n5, n6);
        OKXQ(n, n2, n + (int)n5, n4, n6);
        OKXQ(n3 - (int)n5, n2, n3, n4, n6);
        OKXQ(n + (int)n5 - 1.0, n4 - (int)n5, n3 - (int)n5 + 1.0, n4, n6);
        uCta();
    }
    
    public static void BsWV(final Rectangle rectangle, final int n) {
        wQYt((float)rectangle.x, (float)rectangle.y, (float)(rectangle.x + rectangle.width), (float)(rectangle.y + rectangle.height), n);
    }
    
    public static void wQYt(final float n, final float n2, final float n3, final float n4, final int n5) {
        NOOc();
        zwAE(n5);
        ccoU(n, n2, n3, n4);
        uCta();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
    }
    
    public static void SyxY(final double n, final double n2, final double n3, final double n4, final float n5, final float n6, final float n7, final float n8) {
        NOOc();
        GL11.glColor4f(n5, n6, n7, n8);
        aCuW(n, n2, n3, n4);
        uCta();
    }
    
    public static void pABc(final double n, final double n2, final double n3, final double n4, final int n5) {
        NOOc();
        zwAE(n5);
        aCuW(n, n2, n3, n4);
        uCta();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
    }
    
    public static void JsKQ(final double n, final double n2, final double n3, final double n4, final int n5, final int n6, final int n7) {
        NOOc();
        GL11.glColor3f((float)n5, (float)n6, (float)n7);
        aCuW(n, n2, n3, n4);
        uCta();
    }
    
    public static void zwAE(final int n) {
        GL11.glColor4f((n >> 16 & 0xFF) / 255.0f, (n >> 8 & 0xFF) / 255.0f, (n & 0xFF) / 255.0f, (n >> 24 & 0xFF) / 255.0f);
    }
    
    public static void ccoU(final float n, final float n2, final float n3, final float n4) {
        GL11.glBegin(7);
        GL11.glVertex2f(n, n4);
        GL11.glVertex2f(n3, n4);
        GL11.glVertex2f(n3, n2);
        GL11.glVertex2f(n, n2);
        GL11.glEnd();
    }
    
    public static void aCuW(final double n, final double n2, final double n3, final double n4) {
        GL11.glBegin(7);
        GL11.glVertex2d(n, n4);
        GL11.glVertex2d(n3, n4);
        GL11.glVertex2d(n3, n2);
        GL11.glVertex2d(n, n2);
        GL11.glEnd();
    }
    
    public static void JcNc(final double n, final double n2, final double n3, final double n4, final int n5) {
        final float n6 = (n5 >> 24 & 0xFF) / 255.0f;
        final float n7 = (n5 >> 16 & 0xFF) / 255.0f;
        final float n8 = (n5 >> 8 & 0xFF) / 255.0f;
        final float n9 = (n5 & 0xFF) / 255.0f;
        GL11.glBegin(7);
        GL11.glVertex2d(n, n4);
        GL11.glVertex2d(n3, n4);
        GL11.glVertex2d(n3, n2);
        GL11.glVertex2d(n, n2);
        GL11.glEnd();
    }
    
    public static void xYUr(final float n) {
        GL11.glDisable(3008);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glDisable(3553);
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        GL11.glEnable(2884);
        GL11.glEnable(2848);
        GL11.glHint(3154, 4354);
        GL11.glHint(3155, 4354);
        GL11.glLineWidth(n);
    }
    
    public static void NOOc() {
        GL11.glDisable(2929);
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glDepthMask(true);
        GL11.glEnable(2848);
        GL11.glHint(3154, 4354);
        GL11.glHint(3155, 4354);
    }
    
    public static void uCta() {
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glEnable(2929);
        GL11.glDisable(2848);
        GL11.glHint(3154, 4352);
        GL11.glHint(3155, 4352);
    }
    
    public static void eWnU() {
        GL11.glDisable(3008);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glDisable(3553);
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        GL11.glEnable(2884);
        GL11.glEnable(2848);
        GL11.glHint(3154, 4353);
        GL11.glDisable(2896);
    }
    
    public static void PtcG() {
        GL11.glEnable(2896);
        GL11.glDisable(2848);
        GL11.glEnable(3553);
        GL11.glEnable(2929);
        GL11.glDisable(3042);
        GL11.glEnable(3008);
        GL11.glDepthMask(true);
        GL11.glCullFace(1029);
    }
    
    public static void axgH(int n, int n2, int n3, int n4, final int n5, final int n6) {
        NOOc();
        n *= (int)2.0f;
        n3 *= (int)2.0f;
        n2 *= (int)2.0f;
        n4 *= (int)2.0f;
        GL11.glScalef(0.5f, 0.5f, 0.5f);
        Jmsh(n, n2, n4 - 1, n6);
        Jmsh(n3 - 1, n2, n4, n6);
        npQv(n, n3 - 1, n2, n6);
        npQv(n, n3 - 2, n4 - 1, n6);
        Gui.drawRect(n + 1, n2 + 1, n3 - 1, n4 - 1, n5);
        GL11.glScalef(2.0f, 2.0f, 2.0f);
        uCta();
    }
    
    public static void npQv(int n, int n2, final int n3, final int n4) {
        if (n2 < n) {
            final int n5 = n;
            n = n2;
            n2 = n5;
        }
        Gui.drawRect(n, n3, n2 + 1, n3 + 1, n4);
    }
    
    public static void Jmsh(final int n, int n2, int n3, final int n4) {
        if (n3 < n2) {
            final int n5 = n2;
            n2 = n3;
            n3 = n5;
        }
        Gui.drawRect(n, n2 + 1, n + 1, n3, n4);
    }
    
    public static void jwEU(float n, float n2, final float n3, final int n4, final int n5) {
        if (n2 < n) {
            final float n6 = n;
            n = n2;
            n2 = n6;
        }
        dCuz(n, n3, n2 + 1.0f, n3 + 1.0f, n4, n5);
    }
    
    public static void RoXj(final double[] array, final float[] array2, final float n) {
        GL11.glPushMatrix();
        GL11.glEnable(3042);
        GL11.glEnable(2848);
        GL11.glDisable(2929);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(3042);
        GL11.glLineWidth(n);
        GL11.glColor4f(array2[0], array2[1], array2[2], array2[3]);
        GL11.glBegin(1);
        GL11.glVertex3d(0.0, (double)Minecraft.getMinecraft().player.getEyeHeight(), 0.0);
        GL11.glVertex3d(array[0], array[1], array[2]);
        GL11.glEnd();
        GL11.glDisable(3042);
        GL11.glEnable(3553);
        GL11.glEnable(2929);
        GL11.glDisable(2848);
        GL11.glDisable(3042);
        GL11.glPopMatrix();
    }
    
    public static void dCuz(final double n, final double n2, final double n3, final double n4, final int n5, final int n6) {
        final float n7 = (n5 >> 24 & 0xFF) / 255.0f;
        final float n8 = (n5 >> 16 & 0xFF) / 255.0f;
        final float n9 = (n5 >> 8 & 0xFF) / 255.0f;
        final float n10 = (n5 & 0xFF) / 255.0f;
        final float n11 = (n6 >> 24 & 0xFF) / 255.0f;
        final float n12 = (n6 >> 16 & 0xFF) / 255.0f;
        final float n13 = (n6 >> 8 & 0xFF) / 255.0f;
        final float n14 = (n6 & 0xFF) / 255.0f;
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.shadeModel(7425);
        final Tessellator instance = Tessellator.getInstance();
        final BufferBuilder buffer = instance.getBuffer();
        buffer.endVertex();
        buffer.color(n8, n9, n10, n7);
        buffer.pos(n3, n2, 0.0);
        buffer.pos(n, n2, 0.0);
        buffer.color(n12, n13, n14, n11);
        buffer.pos(n, n4, 0.0);
        buffer.pos(n3, n4, 0.0);
        instance.draw();
        GlStateManager.shadeModel(7424);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
    }
    
    public static void QFoa(final int n, final int n2, final float n3, final int n4) {
        GL11.glColor4f((n4 >> 16 & 0xFF) / 255.0f, (n4 >> 8 & 0xFF) / 255.0f, (n4 & 0xFF) / 255.0f, (n4 >> 24 & 0xFF) / 255.0f);
        GL11.glBegin(9);
        int i = 0;
        while (i <= 360) {
            GL11.glVertex2d(n + Math.sin(i * 3.141592653589793 / 180.0) * n3, n2 + Math.cos(i * 3.141592653589793 / 180.0) * n3);
            ++i;
            if (gnNa.\u2005\u2008\u2004\u200b\u200f\u200b\u2000\u2007) {
                throw null;
            }
        }
        GL11.glEnd();
    }
    
    public static void HvPy(final int n, final int n2, final float n3, final float n4, final int n5) {
        GL11.glColor4f((n5 >> 16 & 0xFF) / 255.0f, (n5 >> 8 & 0xFF) / 255.0f, (n5 & 0xFF) / 255.0f, (n5 >> 24 & 0xFF) / 255.0f);
        GL11.glLineWidth(n4);
        GL11.glEnable(2848);
        GL11.glBegin(2);
        int i = 0;
        while (i <= 360) {
            GL11.glVertex2d(n + Math.sin(i * 3.141592653589793 / 180.0) * n3, n2 + Math.cos(i * 3.141592653589793 / 180.0) * n3);
            ++i;
            if (gnNa.\u2005\u2008\u2004\u200b\u200f\u200b\u2000\u2007) {
                throw null;
            }
        }
        GL11.glEnd();
        GL11.glDisable(2848);
    }
    
    public static void oQBi(final int n, final int n2, final double n3, final int n4) {
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glEnable(2848);
        GL11.glBlendFunc(770, 771);
        zwAE(n4);
        GL11.glBegin(6);
        int i = 0;
        while (i <= 360) {
            GL11.glVertex2d(n + Math.sin(i * 3.141592653589793 / 180.0) * n3, n2 + Math.cos(i * 3.141592653589793 / 180.0) * n3);
            ++i;
            if (gnNa.\u2005\u2008\u2004\u200b\u200f\u200b\u2000\u2007) {
                throw null;
            }
        }
        GL11.glEnd();
        GL11.glDisable(2848);
        GL11.glEnable(3553);
        GL11.glDisable(3042);
    }
    
    public static void QtkP(final int n, final int n2, final int n3, final int n4, final int n5) {
        NOOc();
        zwAE(n4);
        GL11.glBegin(6);
        int i = 0;
        while (i <= 360) {
            GL11.glVertex2d(n + Math.sin(i * 3.141592653589793 / 180.0) * n3, n2 + Math.cos(i * 3.141592653589793 / 180.0) * n3);
            ++i;
            if (gnNa.\u2005\u2008\u2004\u200b\u200f\u200b\u2000\u2007) {
                throw null;
            }
        }
        GL11.glEnd();
        zwAE(n5);
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glLineWidth(1.0f);
        GL11.glBegin(3);
        int j = 0;
        while (j <= 360) {
            GL11.glVertex2d(n + Math.sin(j * 3.141592653589793 / 180.0) * n3, n2 + Math.cos(j * 3.141592653589793 / 180.0) * n3);
            ++j;
            if (gnNa.\u2005\u2008\u2004\u200b\u200f\u200b\u2000\u2007) {
                throw null;
            }
        }
        GL11.glEnd();
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        uCta();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
    }
    
    public static void zmwe(final int n, final int n2, final int n3) {
        NOOc();
        zwAE(n3);
        GL11.glBegin(6);
        GL11.glVertex2d((double)n, (double)(n2 - 7));
        GL11.glVertex2d((double)(n - 3), (double)(n2 + 3));
        GL11.glVertex2d((double)(n + 3), (double)(n2 + 3));
        GL11.glEnd();
        uCta();
    }
    
    public static void jJqf(final int n, final int n2, final double n3, final int n4) {
        final float n5 = (n4 >> 24 & 0xFF) / 255.0f;
        final float n6 = (n4 >> 16 & 0xFF) / 255.0f;
        final float n7 = (n4 >> 8 & 0xFF) / 255.0f;
        final float n8 = (n4 & 0xFF) / 255.0f;
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glEnable(2848);
        GL11.glBlendFunc(770, 771);
        GL11.glColor4f(n6, n7, n8, n5);
        GL11.glBegin(2);
        int i = 0;
        while (i <= 360) {
            GL11.glVertex2d(n + Math.sin(i * 3.141592653589793 / 180.0) * n3, n2 + Math.cos(i * 3.141592653589793 / 180.0) * n3);
            ++i;
            if (gnNa.\u2005\u2008\u2004\u200b\u200f\u200b\u2000\u2007) {
                throw null;
            }
        }
        GL11.glEnd();
        GL11.glDisable(2848);
        GL11.glEnable(3553);
        GL11.glDisable(3042);
    }
    
    public static void Pgxp(final float n, final float n2, final float n3, final float n4, final int n5) {
        GL11.glEnable(3042);
        GL11.glDisable(2884);
        GL11.glDisable(3553);
        GL11.glEnable(2848);
        GL11.glBlendFunc(770, 771);
        GL11.glLineWidth(1.0f);
        final float n6 = (n5 >> 24 & 0xFF) / 255.0f;
        final float n7 = (n5 >> 16 & 0xFF) / 255.0f;
        final float n8 = (n5 >> 8 & 0xFF) / 255.0f;
        final float n9 = (n5 & 0xFF) / 255.0f;
        final float n10 = n7;
        final float n11 = n8;
        final float n12 = n9;
        float n13;
        if (n6 == 0.0f) {
            n13 = 1.0f;
            if (gnNa.\u2005\u2008\u2004\u200b\u200f\u200b\u2000\u2007) {
                throw null;
            }
        }
        else {
            n13 = n6;
        }
        GL11.glColor4f(n10, n11, n12, n13);
        GL11.glLineWidth(n4);
        final int n14 = (int)Math.min(Math.max(n3, 45.0f), 360.0f);
        GL11.glBegin(2);
        int i = 0;
        while (i < n14) {
            final double n15 = 6.283185307179586 * i / n14;
            GL11.glVertex2d(n + Math.sin(n15) * n3, n2 + Math.cos(n15) * n3);
            ++i;
            if (gnNa.\u2005\u2008\u2004\u200b\u200f\u200b\u2000\u2007) {
                throw null;
            }
        }
        GL11.glEnd();
        GL11.glDisable(3042);
        GL11.glEnable(2884);
        GL11.glEnable(3553);
        GL11.glDisable(2848);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GlStateManager.shadeModel(7424);
        GlStateManager.disableBlend();
        GlStateManager.enableTexture2D();
    }
    
    public static void ILFo(final float n, final float n2, final float n3, final int n4) {
        GL11.glEnable(3042);
        GL11.glDisable(2884);
        GL11.glDisable(3553);
        GL11.glEnable(2848);
        GL11.glBlendFunc(770, 771);
        GL11.glLineWidth(1.0f);
        final float n5 = (n4 >> 24 & 0xFF) / 255.0f;
        final float n6 = (n4 >> 16 & 0xFF) / 255.0f;
        final float n7 = (n4 >> 8 & 0xFF) / 255.0f;
        final float n8 = (n4 & 0xFF) / 255.0f;
        final float n9 = n6;
        final float n10 = n7;
        final float n11 = n8;
        float n12;
        if (n5 == 0.0f) {
            n12 = 1.0f;
            if (gnNa.\u2005\u2008\u2004\u200b\u200f\u200b\u2000\u2007) {
                throw null;
            }
        }
        else {
            n12 = n5;
        }
        GL11.glColor4f(n9, n10, n11, n12);
        final int n13 = (int)Math.min(Math.max(n3, 45.0f), 360.0f);
        GL11.glBegin(9);
        int i = 0;
        while (i < n13) {
            final double n14 = 6.283185307179586 * i / n13;
            GL11.glVertex2d(n + Math.sin(n14) * n3, n2 + Math.cos(n14) * n3);
            ++i;
            if (gnNa.\u2005\u2008\u2004\u200b\u200f\u200b\u2000\u2007) {
                throw null;
            }
        }
        GL11.glEnd();
        GL11.glDisable(3042);
        GL11.glEnable(2884);
        GL11.glEnable(3553);
        GL11.glDisable(2848);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GlStateManager.shadeModel(7424);
        GlStateManager.disableBlend();
        GlStateManager.enableTexture2D();
        Pgxp(n, n2, n3, 1.5f, 16777215);
    }
    
    public static void OKXQ(final double n, final double n2, final double n3, final double n4, final int n5) {
        final float n6 = (n5 >> 24 & 0xFF) / 255.0f;
        final float n7 = (n5 >> 16 & 0xFF) / 255.0f;
        final float n8 = (n5 >> 8 & 0xFF) / 255.0f;
        final float n9 = (n5 & 0xFF) / 255.0f;
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GL11.glEnable(2881);
        GL11.glPushMatrix();
        GL11.glColor4f(n7, n8, n9, n6);
        GL11.glBegin(7);
        GL11.glVertex2d(n3, n2);
        GL11.glVertex2d(n, n2);
        GL11.glVertex2d(n, n4);
        GL11.glVertex2d(n3, n4);
        GL11.glEnd();
        GL11.glPopMatrix();
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glDisable(2848);
        GL11.glDisable(2881);
    }
    
    public static void nWXs(final int n, final int n2, final int n3, final int n4, final float n5, final int n6, final int n7) {
        GL11.glColor4f((n7 >> 16 & 0xFF) / 255.0f, (n7 >> 8 & 0xFF) / 255.0f, (n7 & 0xFF) / 255.0f, (n7 >> 24 & 0xFF) / 255.0f);
        ccoU((float)n, (float)n2, (float)n3, (float)n4);
        if (n6 > 1) {
            ccoU(n - n5, (float)(n2 + 4), (float)n, (float)(n2 - 4 + n6 * 10));
            GL11.glBegin(9);
            int i = 0;
            while (i <= 180) {
                GL11.glVertex2d(n - Math.sin(i * 3.141592653589793 / 180.0) * n5, n2 + n5 - Math.cos(i * 3.141592653589793 / 180.0) * n5);
                ++i;
                if (gnNa.\u2005\u2008\u2004\u200b\u200f\u200b\u2000\u2007) {
                    throw null;
                }
            }
            GL11.glEnd();
            GL11.glBegin(9);
            int j = 0;
            while (j <= 180) {
                GL11.glVertex2d(n - Math.sin(j * 3.141592653589793 / 180.0) * n5, n4 - n5 - Math.cos(j * 3.141592653589793 / 180.0) * n5);
                ++j;
                if (gnNa.\u2005\u2008\u2004\u200b\u200f\u200b\u2000\u2007) {
                    throw null;
                }
            }
            GL11.glEnd();
            ccoU(n3 + n5, (float)(n2 + 4), (float)n3, (float)(n2 - 4 + n6 * 10));
            GL11.glBegin(9);
            int k = 0;
            while (k <= 180) {
                GL11.glVertex2d(n3 + Math.sin(k * 3.141592653589793 / 180.0) * n5, n2 + n5 + Math.cos(k * 3.141592653589793 / 180.0) * n5);
                ++k;
                if (gnNa.\u2005\u2008\u2004\u200b\u200f\u200b\u2000\u2007) {
                    throw null;
                }
            }
            GL11.glEnd();
            GL11.glBegin(9);
            int l = 0;
            while (l <= 180) {
                GL11.glVertex2d(n3 + Math.sin(l * 3.141592653589793 / 180.0) * n5, n4 - n5 + Math.cos(l * 3.141592653589793 / 180.0) * n5);
                ++l;
                if (gnNa.\u2005\u2008\u2004\u200b\u200f\u200b\u2000\u2007) {
                    throw null;
                }
            }
            GL11.glEnd();
            if (gnNa.\u2005\u2008\u2004\u200b\u200f\u200b\u2000\u2007) {
                throw null;
            }
        }
        else {
            GL11.glBegin(9);
            int n8 = 0;
            while (n8 <= 180) {
                GL11.glVertex2d(n3 + Math.sin(n8 * 3.141592653589793 / 180.0) * n5, n2 - (n2 - n4) / 2 + Math.cos(n8 * 3.141592653589793 / 180.0) * n5);
                ++n8;
                if (gnNa.\u2005\u2008\u2004\u200b\u200f\u200b\u2000\u2007) {
                    throw null;
                }
            }
            GL11.glEnd();
            GL11.glBegin(9);
            int n9 = 0;
            while (n9 <= 180) {
                GL11.glVertex2d(n - Math.sin(n9 * 3.141592653589793 / 180.0) * n5, n2 - (n2 - n4) / 2 - Math.cos(n9 * 3.141592653589793 / 180.0) * n5);
                ++n9;
                if (gnNa.\u2005\u2008\u2004\u200b\u200f\u200b\u2000\u2007) {
                    throw null;
                }
            }
            GL11.glEnd();
        }
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
    }
    
    public static void AvjN(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        NOOc();
        zwAE(n6);
        wQYt((float)(n + n5), (float)n2, (float)(n3 - n5), (float)(n2 + n5), n6);
        wQYt((float)n, (float)n2, (float)(n + n5), (float)n4, n6);
        wQYt((float)(n3 - n5), (float)n2, (float)n3, (float)n4, n6);
        wQYt((float)(n + n5), (float)(n4 - n5), (float)(n3 - n5), (float)n4, n6);
        uCta();
    }
    
    public static void AVqM(final int n, int n2, final int n3, int n4, final float n5, final int n6, final int n7) {
        GL11.glColor4f((n7 >> 16 & 0xFF) / 255.0f, (n7 >> 8 & 0xFF) / 255.0f, (n7 & 0xFF) / 255.0f, (n7 >> 24 & 0xFF) / 255.0f);
        ccoU((float)n, (float)(n2 + 1), (float)n3, (float)(n4 - 1));
        if (n6 > 1) {
            --n2;
            ++n4;
            ccoU(n - n5, (float)(n2 - n6 * 10 + 4), (float)n, (float)(n2 + 3 - n6 * 10 - 4 + n6 * 10));
            GL11.glBegin(9);
            int i = 0;
            while (i <= 180) {
                GL11.glVertex2d(n - Math.sin(i * 3.141592653589793 / 180.0) * n5, n2 - n6 * 10 + n5 - Math.cos(i * 3.141592653589793 / 180.0) * n5);
                ++i;
                if (gnNa.\u2005\u2008\u2004\u200b\u200f\u200b\u2000\u2007) {
                    throw null;
                }
            }
            GL11.glEnd();
            GL11.glBegin(9);
            int j = 0;
            while (j <= 180) {
                GL11.glVertex2d(n - Math.sin(j * 3.141592653589793 / 180.0) * n5, n4 + n6 * 10 - n5 - Math.cos(j * 3.141592653589793 / 180.0) * n5);
                ++j;
                if (gnNa.\u2005\u2008\u2004\u200b\u200f\u200b\u2000\u2007) {
                    throw null;
                }
            }
            GL11.glEnd();
            ccoU(n3 + n5, (float)(n2 - n6 * 10 + 4), (float)n3, (float)(n2 + 3 - n6 * 10 - 4 + n6 * 10));
            GL11.glBegin(9);
            int k = 0;
            while (k <= 180) {
                GL11.glVertex2d(n3 + Math.sin(k * 3.141592653589793 / 180.0) * n5, n2 - n6 * 10 + n5 + Math.cos(k * 3.141592653589793 / 180.0) * n5);
                ++k;
                if (gnNa.\u2005\u2008\u2004\u200b\u200f\u200b\u2000\u2007) {
                    throw null;
                }
            }
            GL11.glEnd();
            GL11.glBegin(9);
            int l = 0;
            while (l <= 180) {
                GL11.glVertex2d(n3 + Math.sin(l * 3.141592653589793 / 180.0) * n5, n4 + n6 * 10 - n5 + Math.cos(l * 3.141592653589793 / 180.0) * n5);
                ++l;
                if (gnNa.\u2005\u2008\u2004\u200b\u200f\u200b\u2000\u2007) {
                    throw null;
                }
            }
            GL11.glEnd();
            if (gnNa.\u2005\u2008\u2004\u200b\u200f\u200b\u2000\u2007) {
                throw null;
            }
        }
        else {
            ++n4;
            ccoU(n - n5, (float)(n2 - 10 + 3), (float)n, (float)(n2 + 3 - 10 - 4 + 10));
            GL11.glBegin(9);
            int n8 = 0;
            while (n8 <= 180) {
                GL11.glVertex2d(n3 + Math.sin(n8 * 3.141592653589793 / 180.0) * n5, n2 + 1 - (n2 - n4) + Math.cos(n8 * 3.141592653589793 / 180.0) * n5);
                ++n8;
                if (gnNa.\u2005\u2008\u2004\u200b\u200f\u200b\u2000\u2007) {
                    throw null;
                }
            }
            GL11.glEnd();
            GL11.glBegin(9);
            int n9 = 0;
            while (n9 <= 180) {
                GL11.glVertex2d(n3 + Math.sin(n9 * 3.141592653589793 / 180.0) * n5, n2 + 10 - n5 - (n2 - n4) + Math.cos(n9 * 3.141592653589793 / 180.0) * n5);
                ++n9;
                if (gnNa.\u2005\u2008\u2004\u200b\u200f\u200b\u2000\u2007) {
                    throw null;
                }
            }
            GL11.glEnd();
            ccoU(n3 + n5, (float)(n2 - 10 + 3), (float)n3, (float)(n2 + 3 - 10 - 4 + 10));
            GL11.glBegin(9);
            int n10 = 0;
            while (n10 <= 180) {
                GL11.glVertex2d(n - Math.sin(n10 * 3.141592653589793 / 180.0) * n5, n2 - 1 - n5 - (n2 - n4) / 2 - Math.cos(n10 * 3.141592653589793 / 180.0) * n5);
                ++n10;
                if (gnNa.\u2005\u2008\u2004\u200b\u200f\u200b\u2000\u2007) {
                    throw null;
                }
            }
            GL11.glEnd();
            GL11.glBegin(9);
            int n11 = 0;
            while (n11 <= 180) {
                GL11.glVertex2d(n - Math.sin(n11 * 3.141592653589793 / 180.0) * n5, n2 + 5 - n5 - (n2 - n4) / 2 - Math.cos(n11 * 3.141592653589793 / 180.0) * n5);
                ++n11;
                if (gnNa.\u2005\u2008\u2004\u200b\u200f\u200b\u2000\u2007) {
                    throw null;
                }
            }
            GL11.glEnd();
        }
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
    }
    
    public static void zfJJ(final double n, final double n2, final double n3, final double n4) {
        final ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());
        final int scaleFactor = scaledResolution.getScaleFactor();
        GL11.glScissor((int)(n * scaleFactor), (int)((scaledResolution.getScaledHeight_double() - n4) * scaleFactor), (int)((n3 - n) * scaleFactor), (int)((n4 - n2) * scaleFactor));
    }
    
    static {
        gnNa.ULGM = 1.0f;
    }
}
