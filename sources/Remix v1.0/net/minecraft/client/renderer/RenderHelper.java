package net.minecraft.client.renderer;

import java.nio.*;
import org.lwjgl.opengl.*;
import shadersmod.client.*;
import net.minecraft.util.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.*;

public class RenderHelper
{
    private static final Vec3 field_82884_b;
    private static final Vec3 field_82885_c;
    private static FloatBuffer colorBuffer;
    
    public static void disableStandardItemLighting() {
        GlStateManager.disableLighting();
        GlStateManager.disableBooleanStateAt(0);
        GlStateManager.disableBooleanStateAt(1);
        GlStateManager.disableColorMaterial();
    }
    
    public static void drawVLine(final float par1, float par2, float par3, final int par4) {
        if (par3 < par2) {
            final float var5 = par2;
            par2 = par3;
            par3 = var5;
        }
        drawRect(par1, par2 + 1.0f, par1 + 1.0f, par3, par4);
    }
    
    public static void drawRoundedRect(float x, float y, float x1, float y1, final int borderC, final int insideC) {
        x *= 2.0f;
        y *= 2.0f;
        x1 *= 2.0f;
        y1 *= 2.0f;
        GL11.glScalef(0.5f, 0.5f, 0.5f);
        drawVLine(x, y + 1.0f, y1 - 2.0f, borderC);
        drawVLine(x1 - 1.0f, y + 1.0f, y1 - 2.0f, borderC);
        drawHLine(x + 2.0f, x1 - 3.0f, y, borderC);
        drawHLine(x + 2.0f, x1 - 3.0f, y1 - 1.0f, borderC);
        drawHLine(x + 1.0f, x + 1.0f, y + 1.0f, borderC);
        drawHLine(x1 - 2.0f, x1 - 2.0f, y + 1.0f, borderC);
        drawHLine(x1 - 2.0f, x1 - 2.0f, y1 - 2.0f, borderC);
        drawHLine(x + 1.0f, x + 1.0f, y1 - 2.0f, borderC);
        drawRect(x + 1.0f, y + 1.0f, x1 - 1.0f, y1 - 1.0f, insideC);
        GL11.glScalef(2.0f, 2.0f, 2.0f);
    }
    
    public static void enableStandardItemLighting() {
        GlStateManager.enableLighting();
        GlStateManager.enableBooleanStateAt(0);
        GlStateManager.enableBooleanStateAt(1);
        GlStateManager.enableColorMaterial();
        GlStateManager.colorMaterial(1032, 5634);
        final float var0 = 0.4f;
        final float var2 = 0.6f;
        final float var3 = 0.0f;
        GL11.glLight(16384, 4611, setColorBuffer(RenderHelper.field_82884_b.xCoord, RenderHelper.field_82884_b.yCoord, RenderHelper.field_82884_b.zCoord, 0.0));
        GL11.glLight(16384, 4609, setColorBuffer(var2, var2, var2, 1.0f));
        GL11.glLight(16384, 4608, setColorBuffer(0.0f, 0.0f, 0.0f, 1.0f));
        GL11.glLight(16384, 4610, setColorBuffer(var3, var3, var3, 1.0f));
        GL11.glLight(16385, 4611, setColorBuffer(RenderHelper.field_82885_c.xCoord, RenderHelper.field_82885_c.yCoord, RenderHelper.field_82885_c.zCoord, 0.0));
        GL11.glLight(16385, 4609, setColorBuffer(var2, var2, var2, 1.0f));
        GL11.glLight(16385, 4608, setColorBuffer(0.0f, 0.0f, 0.0f, 1.0f));
        GL11.glLight(16385, 4610, setColorBuffer(var3, var3, var3, 1.0f));
        GlStateManager.shadeModel(7424);
        GL11.glLightModel(2899, setColorBuffer(var0, var0, var0, 1.0f));
    }
    
    private static FloatBuffer setColorBuffer(final double p_74517_0_, final double p_74517_2_, final double p_74517_4_, final double p_74517_6_) {
        return setColorBuffer((float)p_74517_0_, (float)p_74517_2_, (float)p_74517_4_, (float)p_74517_6_);
    }
    
    private static FloatBuffer setColorBuffer(final float p_74521_0_, final float p_74521_1_, final float p_74521_2_, final float p_74521_3_) {
        RenderHelper.colorBuffer.clear();
        RenderHelper.colorBuffer.put(p_74521_0_).put(p_74521_1_).put(p_74521_2_).put(p_74521_3_);
        RenderHelper.colorBuffer.flip();
        return RenderHelper.colorBuffer;
    }
    
    public static void enableGUIStandardItemLighting() {
        GlStateManager.pushMatrix();
        GlStateManager.rotate(-30.0f, 0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(165.0f, 1.0f, 0.0f, 0.0f);
        enableStandardItemLighting();
        GlStateManager.popMatrix();
    }
    
    public static void drawHLine(float par1, float par2, final float par3, final int par4) {
        if (par2 < par1) {
            final float var5 = par1;
            par1 = par2;
            par2 = var5;
        }
        drawRect(par1, par3, par2 + 3.0f, par3 + 3.0f, par4);
    }
    
    public static void enableGL3D(final float lineWidth) {
        GL11.glDisable(3008);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glDisable(3553);
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        GL11.glEnable(2884);
        Shaders.disableLightmap();
        Shaders.disableFog();
        GL11.glEnable(2848);
        GL11.glHint(3154, 4354);
        GL11.glHint(3155, 4354);
        GL11.glLineWidth(lineWidth);
    }
    
    public static void disableGL3D() {
        GL11.glEnable(3553);
        GL11.glEnable(2929);
        GL11.glDisable(3042);
        GL11.glEnable(3008);
        GL11.glDepthMask(true);
        GL11.glCullFace(1029);
        GL11.glDisable(2848);
        GL11.glHint(3154, 4352);
        GL11.glHint(3155, 4352);
    }
    
    public static void drawRect(final float g, final float h, final float i, final float j, final int col1) {
        final float f = (col1 >> 24 & 0xFF) / 255.0f;
        final float f2 = (col1 >> 16 & 0xFF) / 255.0f;
        final float f3 = (col1 >> 8 & 0xFF) / 255.0f;
        final float f4 = (col1 & 0xFF) / 255.0f;
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GL11.glPushMatrix();
        GL11.glColor4f(f2, f3, f4, f);
        GL11.glBegin(7);
        GL11.glVertex2d((double)i, (double)h);
        GL11.glVertex2d((double)g, (double)h);
        GL11.glVertex2d((double)g, (double)j);
        GL11.glVertex2d((double)i, (double)j);
        GL11.glEnd();
        GL11.glPopMatrix();
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glDisable(2848);
    }
    
    public static void drawFullCircle(int cx, double cy, double r, final int segments, final float lineWidth, final int part, final int c) {
        GL11.glScalef(0.5f, 0.5f, 0.5f);
        r *= 2.0;
        cx *= 2;
        cy *= 2.0;
        final float f = (c >> 24 & 0xFF) / 255.0f;
        final float f2 = (c >> 16 & 0xFF) / 255.0f;
        final float f3 = (c >> 8 & 0xFF) / 255.0f;
        final float f4 = (c & 0xFF) / 255.0f;
        GL11.glEnable(3042);
        GL11.glLineWidth(lineWidth);
        GL11.glDisable(3553);
        GL11.glEnable(2848);
        GL11.glBlendFunc(770, 771);
        GL11.glColor4f(f2, f3, f4, f);
        GL11.glBegin(3);
        for (int i = segments - part; i <= segments; ++i) {
            final double x = Math.sin(i * 3.141592653589793 / 180.0) * r;
            final double y = Math.cos(i * 3.141592653589793 / 180.0) * r;
            GL11.glVertex2d(cx + x, cy + y);
        }
        GL11.glEnd();
        GL11.glDisable(2848);
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glScalef(2.0f, 2.0f, 2.0f);
    }
    
    public static void drawIcon(final float x, final float y, final int sizex, final int sizey, final ResourceLocation resourceLocation) {
        GL11.glPushMatrix();
        Minecraft.getMinecraft().getTextureManager().bindTexture(resourceLocation);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GlStateManager.enableRescaleNormal();
        GlStateManager.enableAlpha();
        GlStateManager.alphaFunc(516, 0.1f);
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(770, 771);
        GL11.glTranslatef(x, y, 0.0f);
        drawScaledRect(0, 0, 0.0f, 0.0f, sizex, sizey, sizex, sizey, (float)sizex, (float)sizey);
        GlStateManager.disableAlpha();
        GlStateManager.disableRescaleNormal();
        GlStateManager.disableLighting();
        GlStateManager.disableRescaleNormal();
        GL11.glDisable(2848);
        GlStateManager.disableBlend();
        GL11.glPopMatrix();
    }
    
    public static void drawScaledRect(final int x, final int y, final float u, final float v, final int uWidth, final int vHeight, final int width, final int height, final float tileWidth, final float tileHeight) {
        Gui.drawScaledCustomSizeModalRect(x, y, u, v, uWidth, vHeight, width, height, tileWidth, tileHeight);
    }
    
    public static void kek(final float g, final float h, final float i, final float j, final int col1) {
        final float f = (col1 >> 24 & 0xFF) / 255.0f;
        final float f2 = (col1 >> 16 & 0xFF) / 255.0f;
        final float f3 = (col1 >> 8 & 0xFF) / 255.0f;
        final float f4 = (col1 & 0xFF) / 255.0f;
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GL11.glPushMatrix();
        GL11.glColor4f(f2, f3, f4, f);
        GL11.glBegin(7);
        GL11.glVertex2d((double)i, (double)h);
        GL11.glVertex2d((double)g, (double)h);
        GL11.glVertex2d((double)g, (double)j);
        GL11.glVertex2d((double)i, (double)j);
        GL11.glEnd();
        GL11.glPopMatrix();
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glDisable(2848);
    }
    
    public static void drawBorderedRect(final float x, final float y, final float x2, final float y2, final float l1, final int col1, final int col2) {
        kek(x, y, x2, y2, col2);
        final float f = (col1 >> 24 & 0xFF) / 255.0f;
        final float f2 = (col1 >> 16 & 0xFF) / 255.0f;
        final float f3 = (col1 >> 8 & 0xFF) / 255.0f;
        final float f4 = (col1 & 0xFF) / 255.0f;
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GL11.glPushMatrix();
        GL11.glColor4f(f2, f3, f4, f);
        GL11.glLineWidth(l1);
        GL11.glBegin(1);
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
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glDisable(2848);
    }
    
    public static void renderImage(final ResourceLocation src, final int x, final int y, final int w, final int h) {
        GL11.glPushMatrix();
        Minecraft.getMinecraft().getTextureManager().bindTexture(src);
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.blendFunc(770, 771);
        GL11.glColor4d(1.0, 1.0, 1.0, 1.0);
        GL11.glEnable(3042);
        Gui.drawModalRectWithCustomSizedTexture(x, y, 0.0f, 0.0f, w, h, (float)w, (float)h);
        GL11.glPopMatrix();
    }
    
    public static void renderImage(final String src, final int x, final int y, final int w, final int h) {
        renderImage(new ResourceLocation(src), x, y, w, h);
    }
    
    static {
        field_82884_b = new Vec3(0.20000000298023224, 1.0, -0.699999988079071).normalize();
        field_82885_c = new Vec3(-0.20000000298023224, 1.0, 0.699999988079071).normalize();
        RenderHelper.colorBuffer = GLAllocation.createDirectFloatBuffer(16);
    }
}
