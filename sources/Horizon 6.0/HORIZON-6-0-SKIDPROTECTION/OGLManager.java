package HORIZON-6-0-SKIDPROTECTION;

import org.lwjgl.opengl.GL11;

public class OGLManager
{
    public static final RenderItem Ó;
    private static ScaledResolution HorizonCode_Horizon_È;
    
    static {
        Ó = new RenderItem(Minecraft.áŒŠà().Ø­áŒŠá, Minecraft.áŒŠà().É);
    }
    
    public static void áˆºÑ¢Õ() {
        GL11.glDisable(2929);
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glDepthMask(true);
        GL11.glEnable(2848);
        GL11.glHint(3154, 4354);
        GL11.glHint(3155, 4354);
    }
    
    public static void HorizonCode_Horizon_È(final String text, int x, final int y) {
        x *= 2;
        GL11.glScalef(0.8f, 0.8f, 0.8f);
        Minecraft.áŒŠà().µà.HorizonCode_Horizon_È(text, x, (float)y, 16777215);
        GL11.glScalef(1.25f, 1.25f, 1.25f);
    }
    
    public static void Â(final String text, int x, final int y) {
        x *= 2;
        GL11.glScalef(1.5f, 1.5f, 1.5f);
        Minecraft.áŒŠà().µà.HorizonCode_Horizon_È(text, x, (float)y, 16777215);
        GL11.glScalef(0.665f, 0.665f, 0.665f);
    }
    
    public static void ÂµÈ() {
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glEnable(2929);
        GL11.glDisable(2848);
        GL11.glHint(3154, 4352);
        GL11.glHint(3155, 4352);
    }
    
    public static void Â(final float lineWidth) {
        GL11.glDisable(3008);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glDisable(3553);
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        GL11.glEnable(2884);
        Minecraft.áŒŠà().µÕ.Ó();
        GL11.glEnable(2848);
        GL11.glHint(3154, 4354);
        GL11.glHint(3155, 4354);
        GL11.glLineWidth(lineWidth);
    }
    
    public static void á() {
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
    
    public static int ˆÏ­() {
        return GL11.glGenTextures();
    }
    
    public static void HorizonCode_Horizon_È(final int texID, final float x, final float y, final float width, final float height) {
        GL11.glBindTexture(3553, texID);
        HorizonCode_Horizon_È(x, y, width, height);
    }
    
    public static void HorizonCode_Horizon_È(final int textureWidth, final int textureHeight, final float x, final float y, final float width, final float height, final float srcX, final float srcY, final float srcWidth, final float srcHeight) {
        final float renderSRCX = srcX / textureWidth;
        final float renderSRCY = srcY / textureHeight;
        final float renderSRCWidth = srcWidth / textureWidth;
        final float renderSRCHeight = srcHeight / textureHeight;
        final boolean tex2D = GL11.glGetBoolean(3553);
        final boolean blend = GL11.glGetBoolean(3042);
        GL11.glPushMatrix();
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(3553);
        GL11.glBegin(4);
        GL11.glTexCoord2f(renderSRCX + renderSRCWidth, renderSRCY);
        GL11.glVertex2f(x + width, y);
        GL11.glTexCoord2f(renderSRCX, renderSRCY);
        GL11.glVertex2f(x, y);
        GL11.glTexCoord2f(renderSRCX, renderSRCY + renderSRCHeight);
        GL11.glVertex2f(x, y + height);
        GL11.glTexCoord2f(renderSRCX, renderSRCY + renderSRCHeight);
        GL11.glVertex2f(x, y + height);
        GL11.glTexCoord2f(renderSRCX + renderSRCWidth, renderSRCY + renderSRCHeight);
        GL11.glVertex2f(x + width, y + height);
        GL11.glTexCoord2f(renderSRCX + renderSRCWidth, renderSRCY);
        GL11.glVertex2f(x + width, y);
        GL11.glEnd();
        if (!tex2D) {
            GL11.glDisable(3553);
        }
        if (!blend) {
            GL11.glDisable(3042);
        }
        GL11.glPopMatrix();
    }
    
    public static void HorizonCode_Horizon_È(float x, float y, float width, float height) {
        final boolean tex2D = GL11.glGetBoolean(3553);
        final boolean blend = GL11.glGetBoolean(3042);
        GL11.glPushMatrix();
        GL11.glEnable(3042);
        GL11.glEnable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glScalef(0.5f, 0.5f, 0.5f);
        x *= 2.0f;
        y *= 2.0f;
        width *= 2.0f;
        height *= 2.0f;
        GL11.glBegin(4);
        GL11.glTexCoord2f(1.0f, 0.0f);
        GL11.glVertex2f(x + width, y);
        GL11.glTexCoord2f(0.0f, 0.0f);
        GL11.glVertex2f(x, y);
        GL11.glTexCoord2f(0.0f, 1.0f);
        GL11.glVertex2f(x, y + height);
        GL11.glTexCoord2f(0.0f, 1.0f);
        GL11.glVertex2f(x, y + height);
        GL11.glTexCoord2f(1.0f, 1.0f);
        GL11.glVertex2f(x + width, y + height);
        GL11.glTexCoord2f(1.0f, 0.0f);
        GL11.glVertex2f(x + width, y);
        GL11.glEnd();
        if (!tex2D) {
            GL11.glDisable(3553);
        }
        if (!blend) {
            GL11.glDisable(3042);
        }
        GL11.glPopMatrix();
    }
    
    public static void HorizonCode_Horizon_È(final float x, final float y, final float x1, final float y1, final float width) {
        GL11.glDisable(3553);
        GL11.glLineWidth(width);
        GL11.glBegin(1);
        GL11.glVertex2f(x, y);
        GL11.glVertex2f(x1, y1);
        GL11.glEnd();
        GL11.glEnable(3553);
    }
    
    public static void HorizonCode_Horizon_È(final Rectangle rectangle, final int color) {
        HorizonCode_Horizon_È(rectangle.áˆºÑ¢Õ, rectangle.ÂµÈ, rectangle.áˆºÑ¢Õ + rectangle.HorizonCode_Horizon_È, rectangle.ÂµÈ + rectangle.Â, color);
    }
    
    public static void HorizonCode_Horizon_È(final float x, final float y, final float x1, final float y1, final int color) {
        áˆºÑ¢Õ();
        Ø­áŒŠá(color);
        Â(x, y, x1, y1);
        ÂµÈ();
    }
    
    public static void HorizonCode_Horizon_È(final float x, final float y, final float x1, final float y1, final float width, final int internalColor, final int borderColor) {
        áˆºÑ¢Õ();
        Ø­áŒŠá(internalColor);
        Â(x + width, y + width, x1 - width, y1 - width);
        Ø­áŒŠá(borderColor);
        Â(x + width, y, x1 - width, y + width);
        Â(x, y, x + width, y1);
        Â(x1 - width, y, x1, y1);
        Â(x + width, y1 - width, x1 - width, y1);
        ÂµÈ();
    }
    
    public static void HorizonCode_Horizon_È(float x, float y, float x1, float y1, final int insideC, final int borderC) {
        áˆºÑ¢Õ();
        x *= 2.0f;
        x1 *= 2.0f;
        y *= 2.0f;
        y1 *= 2.0f;
        GL11.glScalef(0.5f, 0.5f, 0.5f);
        Â(x, y, y1 - 1.0f, borderC);
        Â(x1 - 1.0f, y, y1, borderC);
        HorizonCode_Horizon_È(x, x1 - 1.0f, y, borderC);
        HorizonCode_Horizon_È(x, x1 - 2.0f, y1 - 1.0f, borderC);
        HorizonCode_Horizon_È(x + 1.0f, y + 1.0f, x1 - 1.0f, y1 - 1.0f, insideC);
        GL11.glScalef(2.0f, 2.0f, 2.0f);
        ÂµÈ();
    }
    
    public static void Â(final float x, final float y, final float x1, final float y1, final float lineWidth, final int inside, final int border) {
        áˆºÑ¢Õ();
        HorizonCode_Horizon_È(x, y, x1, y1, inside);
        Ø­áŒŠá(border);
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glLineWidth(lineWidth);
        GL11.glBegin(3);
        GL11.glVertex2f(x, y);
        GL11.glVertex2f(x, y1);
        GL11.glVertex2f(x1, y1);
        GL11.glVertex2f(x1, y);
        GL11.glVertex2f(x, y);
        GL11.glEnd();
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        ÂµÈ();
    }
    
    public static void HorizonCode_Horizon_È(final float x, final float y, final float x1, final float y1, final float lineWidth, final int border, final int bottom, final int top) {
        áˆºÑ¢Õ();
        Ý(x, y, x1, y1, top, bottom);
        Ø­áŒŠá(border);
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glLineWidth(lineWidth);
        GL11.glBegin(3);
        GL11.glVertex2f(x, y);
        GL11.glVertex2f(x, y1);
        GL11.glVertex2f(x1, y1);
        GL11.glVertex2f(x1, y);
        GL11.glVertex2f(x, y);
        GL11.glEnd();
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        ÂµÈ();
    }
    
    public static void Â(float x, float y, float x1, float y1, final int borderC, final int insideC) {
        áˆºÑ¢Õ();
        x *= 2.0f;
        y *= 2.0f;
        x1 *= 2.0f;
        y1 *= 2.0f;
        GL11.glScalef(0.5f, 0.5f, 0.5f);
        Â(x, y + 1.0f, y1 - 2.0f, borderC);
        Â(x1 - 1.0f, y + 1.0f, y1 - 2.0f, borderC);
        HorizonCode_Horizon_È(x + 2.0f, x1 - 3.0f, y, borderC);
        HorizonCode_Horizon_È(x + 2.0f, x1 - 3.0f, y1 - 1.0f, borderC);
        HorizonCode_Horizon_È(x + 1.0f, x + 1.0f, y + 1.0f, borderC);
        HorizonCode_Horizon_È(x1 - 2.0f, x1 - 2.0f, y + 1.0f, borderC);
        HorizonCode_Horizon_È(x1 - 2.0f, x1 - 2.0f, y1 - 2.0f, borderC);
        HorizonCode_Horizon_È(x + 1.0f, x + 1.0f, y1 - 2.0f, borderC);
        HorizonCode_Horizon_È(x + 1.0f, y + 1.0f, x1 - 1.0f, y1 - 1.0f, insideC);
        GL11.glScalef(2.0f, 2.0f, 2.0f);
        ÂµÈ();
    }
    
    public static void HorizonCode_Horizon_È(final Rectangle rectangle, final float width, final int internalColor, final int borderColor) {
        final float x = rectangle.áˆºÑ¢Õ;
        final float y = rectangle.ÂµÈ;
        final float x2 = rectangle.áˆºÑ¢Õ + rectangle.HorizonCode_Horizon_È;
        final float y2 = rectangle.ÂµÈ + rectangle.Â;
        áˆºÑ¢Õ();
        Ø­áŒŠá(internalColor);
        Â(x + width, y + width, x2 - width, y2 - width);
        Ø­áŒŠá(borderColor);
        Â(x + 1.0f, y, x2 - 1.0f, y + width);
        Â(x, y, x + width, y2);
        Â(x2 - width, y, x2, y2);
        Â(x + 1.0f, y2 - width, x2 - 1.0f, y2);
        ÂµÈ();
    }
    
    public static void Ý(final float x, final float y, final float x1, final float y1, final int topColor, final int bottomColor) {
        áˆºÑ¢Õ();
        GL11.glShadeModel(7425);
        GL11.glBegin(7);
        Ø­áŒŠá(topColor);
        GL11.glVertex2f(x, y1);
        GL11.glVertex2f(x1, y1);
        Ø­áŒŠá(bottomColor);
        GL11.glVertex2f(x1, y);
        GL11.glVertex2f(x, y);
        GL11.glEnd();
        GL11.glShadeModel(7424);
        ÂµÈ();
    }
    
    public static void Ø­áŒŠá(final float x, final float y, final float x1, final float y1, final int topColor, final int bottomColor) {
        áˆºÑ¢Õ();
        GL11.glShadeModel(7425);
        GL11.glBegin(7);
        Ø­áŒŠá(topColor);
        GL11.glVertex2f(x, y);
        GL11.glVertex2f(x, y1);
        Ø­áŒŠá(bottomColor);
        GL11.glVertex2f(x1, y1);
        GL11.glVertex2f(x1, y);
        GL11.glEnd();
        GL11.glShadeModel(7424);
        ÂµÈ();
    }
    
    public static void HorizonCode_Horizon_È(final double x, final double y, final double x2, final double y2, final int col1, final int col2) {
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GL11.glShadeModel(7425);
        GL11.glPushMatrix();
        GL11.glBegin(7);
        Ø­áŒŠá(col1);
        GL11.glVertex2d(x2, y);
        GL11.glVertex2d(x, y);
        Ø­áŒŠá(col2);
        GL11.glVertex2d(x, y2);
        GL11.glVertex2d(x2, y2);
        GL11.glEnd();
        GL11.glPopMatrix();
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glDisable(2848);
        GL11.glShadeModel(7424);
    }
    
    public static void HorizonCode_Horizon_È(final double x, final double y, final double x2, final double y2, final float l1, final int col1, final int col2, final int col3) {
        áˆºÑ¢Õ();
        GL11.glPushMatrix();
        Ø­áŒŠá(col1);
        GL11.glLineWidth(1.0f);
        GL11.glBegin(1);
        GL11.glVertex2d(x, y);
        GL11.glVertex2d(x, y2);
        GL11.glVertex2d(x2, y2);
        GL11.glVertex2d(x2, y);
        GL11.glVertex2d(x, y);
        GL11.glVertex2d(x2, y);
        GL11.glVertex2d(x, y2);
        GL11.glVertex2d(x2, y2);
        GL11.glEnd();
        GL11.glPopMatrix();
        HorizonCode_Horizon_È(x, y, x2, y2, col2, col3);
        ÂµÈ();
    }
    
    public static void HorizonCode_Horizon_È(final int x, final int y, final float width, final double angle, final float points, final float radius, final int color) {
        final float f1 = (color >> 24 & 0xFF) / 255.0f;
        final float f2 = (color >> 16 & 0xFF) / 255.0f;
        final float f3 = (color >> 8 & 0xFF) / 255.0f;
        final float f4 = (color & 0xFF) / 255.0f;
        GL11.glPushMatrix();
        GL11.glTranslated((double)x, (double)y, 0.0);
        GL11.glColor4f(f2, f3, f4, f1);
        GL11.glLineWidth(width);
        if (angle > 0.0) {
            GL11.glBegin(3);
            for (int i = 0; i < angle; ++i) {
                final float a = (float)(i * (angle * 3.141592653589793 / points));
                final float xc = (float)(Math.cos(a) * radius);
                final float yc = (float)(Math.sin(a) * radius);
                GL11.glVertex2f(xc, yc);
            }
            GL11.glEnd();
        }
        if (angle < 0.0) {
            GL11.glBegin(3);
            for (int i = 0; i > angle; --i) {
                final float a = (float)(i * (angle * 3.141592653589793 / points));
                final float xc = (float)(Math.cos(a) * -radius);
                final float yc = (float)(Math.sin(a) * -radius);
                GL11.glVertex2f(xc, yc);
            }
            GL11.glEnd();
        }
        ÂµÈ();
        GL11.glDisable(3479);
        GL11.glPopMatrix();
    }
    
    public static void HorizonCode_Horizon_È(float x, float y, final float x1, final int y1) {
        if (y < x) {
            final float var5 = x;
            x = y;
            y = var5;
        }
        HorizonCode_Horizon_È(x, x1, y + 1.0f, x1 + 1.0f, y1);
    }
    
    public static void Â(final float x, float y, float x1, final int y1) {
        if (x1 < y) {
            final float var5 = y;
            y = x1;
            x1 = var5;
        }
        HorizonCode_Horizon_È(x, y + 1.0f, x + 1.0f, x1, y1);
    }
    
    public static void HorizonCode_Horizon_È(float x, float y, final float x1, final int y1, final int y2) {
        if (y < x) {
            final float var5 = x;
            x = y;
            y = var5;
        }
        Ý(x, x1, y + 1.0f, x1 + 1.0f, y1, y2);
    }
    
    public static void HorizonCode_Horizon_È(final float x, final float y, final float x1, final float y1, final float r, final float g, final float b, final float a) {
        áˆºÑ¢Õ();
        GL11.glColor4f(r, g, b, a);
        Â(x, y, x1, y1);
        ÂµÈ();
    }
    
    public static void Â(final float x, final float y, final float x1, final float y1) {
        GL11.glBegin(7);
        GL11.glVertex2f(x, y1);
        GL11.glVertex2f(x1, y1);
        GL11.glVertex2f(x1, y);
        GL11.glVertex2f(x, y);
        GL11.glEnd();
    }
    
    public static void Â(float cx, float cy, float r, final int num_segments, final int c) {
        r *= 2.0f;
        cx *= 2.0f;
        cy *= 2.0f;
        final float f = (c >> 24 & 0xFF) / 255.0f;
        final float f2 = (c >> 16 & 0xFF) / 255.0f;
        final float f3 = (c >> 8 & 0xFF) / 255.0f;
        final float f4 = (c & 0xFF) / 255.0f;
        final float theta = (float)(6.2831852 / num_segments);
        final float p = (float)Math.cos(theta);
        final float s = (float)Math.sin(theta);
        float x = r;
        float y = 0.0f;
        áˆºÑ¢Õ();
        GL11.glScalef(0.5f, 0.5f, 0.5f);
        GL11.glColor4f(f2, f3, f4, f);
        GL11.glBegin(2);
        for (int ii = 0; ii < num_segments; ++ii) {
            GL11.glVertex2f(x + cx, y + cy);
            final float t = x;
            x = p * x - s * y;
            y = s * t + p * y;
        }
        GL11.glEnd();
        GL11.glScalef(2.0f, 2.0f, 2.0f);
        ÂµÈ();
    }
    
    public static void HorizonCode_Horizon_È(int cx, int cy, double r, final int c) {
        r *= 2.0;
        cx *= 2;
        cy *= 2;
        final float f = (c >> 24 & 0xFF) / 255.0f;
        final float f2 = (c >> 16 & 0xFF) / 255.0f;
        final float f3 = (c >> 8 & 0xFF) / 255.0f;
        final float f4 = (c & 0xFF) / 255.0f;
        áˆºÑ¢Õ();
        GL11.glScalef(0.5f, 0.5f, 0.5f);
        GL11.glColor4f(f2, f3, f4, f);
        GL11.glBegin(6);
        for (int i = 0; i <= 360; ++i) {
            final double x = Math.sin(i * 3.141592653589793 / 180.0) * r;
            final double y = Math.cos(i * 3.141592653589793 / 180.0) * r;
            GL11.glVertex2d(cx + x, cy + y);
        }
        GL11.glEnd();
        GL11.glScalef(2.0f, 2.0f, 2.0f);
        ÂµÈ();
    }
    
    public static void HorizonCode_Horizon_È(final Color color) {
        GL11.glColor4f(color.Ø­áŒŠá() / 255.0f, color.Âµá€() / 255.0f, color.Ó() / 255.0f, color.à() / 255.0f);
    }
    
    public static void Ø­áŒŠá(final int hex) {
        final float alpha = (hex >> 24 & 0xFF) / 255.0f;
        final float red = (hex >> 16 & 0xFF) / 255.0f;
        final float green = (hex >> 8 & 0xFF) / 255.0f;
        final float blue = (hex & 0xFF) / 255.0f;
        GL11.glColor4f(red, green, blue, alpha);
    }
    
    public static void HorizonCode_Horizon_È(final float alpha, final int redRGB, final int greenRGB, final int blueRGB) {
        final float red = 0.003921569f * redRGB;
        final float green = 0.003921569f * greenRGB;
        final float blue = 0.003921569f * blueRGB;
        GL11.glColor4f(red, green, blue, alpha);
    }
    
    public static void HorizonCode_Horizon_È(final int par1, final int par2, final int par3, final int par4, final int par5, final int par6) {
        final float var7 = 0.0039063f;
        final float var8 = 0.0039063f;
        final Tessellator var9 = Tessellator.HorizonCode_Horizon_È();
        final WorldRenderer var10 = var9.Ý();
        var10.Â();
        var10.HorizonCode_Horizon_È(par1 + 0, par2 + par6, 0.0, (par3 + 0) * var7, (par4 + par6) * var8);
        var10.HorizonCode_Horizon_È(par1 + par5, par2 + par6, 0.0, (par3 + par5) * var7, (par4 + par6) * var8);
        var10.HorizonCode_Horizon_È(par1 + par5, par2 + 0, 0.0, (par3 + par5) * var7, (par4 + 0) * var8);
        var10.HorizonCode_Horizon_È(par1 + 0, par2 + 0, 0.0, (par3 + 0) * var7, (par4 + 0) * var8);
        var9.Â();
    }
    
    public static void HorizonCode_Horizon_È(final Box box) {
        if (box == null) {
            return;
        }
        GL11.glBegin(3);
        GL11.glVertex3d(box.HorizonCode_Horizon_È, box.Â, box.Ý);
        GL11.glVertex3d(box.Ø­áŒŠá, box.Â, box.Ý);
        GL11.glVertex3d(box.Ø­áŒŠá, box.Â, box.Ó);
        GL11.glVertex3d(box.HorizonCode_Horizon_È, box.Â, box.Ó);
        GL11.glVertex3d(box.HorizonCode_Horizon_È, box.Â, box.Ý);
        GL11.glEnd();
        GL11.glBegin(3);
        GL11.glVertex3d(box.HorizonCode_Horizon_È, box.Âµá€, box.Ý);
        GL11.glVertex3d(box.Ø­áŒŠá, box.Âµá€, box.Ý);
        GL11.glVertex3d(box.Ø­áŒŠá, box.Âµá€, box.Ó);
        GL11.glVertex3d(box.HorizonCode_Horizon_È, box.Âµá€, box.Ó);
        GL11.glVertex3d(box.HorizonCode_Horizon_È, box.Âµá€, box.Ý);
        GL11.glEnd();
        GL11.glBegin(1);
        GL11.glVertex3d(box.HorizonCode_Horizon_È, box.Â, box.Ý);
        GL11.glVertex3d(box.HorizonCode_Horizon_È, box.Âµá€, box.Ý);
        GL11.glVertex3d(box.Ø­áŒŠá, box.Â, box.Ý);
        GL11.glVertex3d(box.Ø­áŒŠá, box.Âµá€, box.Ý);
        GL11.glVertex3d(box.Ø­áŒŠá, box.Â, box.Ó);
        GL11.glVertex3d(box.Ø­áŒŠá, box.Âµá€, box.Ó);
        GL11.glVertex3d(box.HorizonCode_Horizon_È, box.Â, box.Ó);
        GL11.glVertex3d(box.HorizonCode_Horizon_È, box.Âµá€, box.Ó);
        GL11.glEnd();
    }
    
    public static void Â(final Box box) {
        GL11.glBegin(1);
        GL11.glVertex3d(box.Ø­áŒŠá, box.Âµá€, box.Ó);
        GL11.glVertex3d(box.Ø­áŒŠá, box.Â, box.Ý);
        GL11.glVertex3d(box.Ø­áŒŠá, box.Âµá€, box.Ý);
        GL11.glVertex3d(box.HorizonCode_Horizon_È, box.Âµá€, box.Ó);
        GL11.glVertex3d(box.HorizonCode_Horizon_È, box.Âµá€, box.Ý);
        GL11.glVertex3d(box.Ø­áŒŠá, box.Â, box.Ý);
        GL11.glVertex3d(box.HorizonCode_Horizon_È, box.Â, box.Ó);
        GL11.glVertex3d(box.Ø­áŒŠá, box.Âµá€, box.Ó);
        GL11.glVertex3d(box.HorizonCode_Horizon_È, box.Â, box.Ó);
        GL11.glVertex3d(box.HorizonCode_Horizon_È, box.Âµá€, box.Ý);
        GL11.glVertex3d(box.HorizonCode_Horizon_È, box.Â, box.Ý);
        GL11.glVertex3d(box.Ø­áŒŠá, box.Â, box.Ó);
        GL11.glEnd();
    }
    
    public static void Ý(final Box box) {
        if (box == null) {
            return;
        }
        GL11.glBegin(7);
        GL11.glVertex3d(box.HorizonCode_Horizon_È, box.Â, box.Ó);
        GL11.glVertex3d(box.Ø­áŒŠá, box.Â, box.Ó);
        GL11.glVertex3d(box.Ø­áŒŠá, box.Âµá€, box.Ó);
        GL11.glVertex3d(box.HorizonCode_Horizon_È, box.Âµá€, box.Ó);
        GL11.glEnd();
        GL11.glBegin(7);
        GL11.glVertex3d(box.Ø­áŒŠá, box.Â, box.Ó);
        GL11.glVertex3d(box.HorizonCode_Horizon_È, box.Â, box.Ó);
        GL11.glVertex3d(box.HorizonCode_Horizon_È, box.Âµá€, box.Ó);
        GL11.glVertex3d(box.Ø­áŒŠá, box.Âµá€, box.Ó);
        GL11.glEnd();
        GL11.glBegin(7);
        GL11.glVertex3d(box.HorizonCode_Horizon_È, box.Â, box.Ý);
        GL11.glVertex3d(box.HorizonCode_Horizon_È, box.Â, box.Ó);
        GL11.glVertex3d(box.HorizonCode_Horizon_È, box.Âµá€, box.Ó);
        GL11.glVertex3d(box.HorizonCode_Horizon_È, box.Âµá€, box.Ý);
        GL11.glEnd();
        GL11.glBegin(7);
        GL11.glVertex3d(box.HorizonCode_Horizon_È, box.Â, box.Ó);
        GL11.glVertex3d(box.HorizonCode_Horizon_È, box.Â, box.Ý);
        GL11.glVertex3d(box.HorizonCode_Horizon_È, box.Âµá€, box.Ý);
        GL11.glVertex3d(box.HorizonCode_Horizon_È, box.Âµá€, box.Ó);
        GL11.glEnd();
        GL11.glBegin(7);
        GL11.glVertex3d(box.Ø­áŒŠá, box.Â, box.Ó);
        GL11.glVertex3d(box.Ø­áŒŠá, box.Â, box.Ý);
        GL11.glVertex3d(box.Ø­áŒŠá, box.Âµá€, box.Ý);
        GL11.glVertex3d(box.Ø­áŒŠá, box.Âµá€, box.Ó);
        GL11.glEnd();
        GL11.glBegin(7);
        GL11.glVertex3d(box.Ø­áŒŠá, box.Â, box.Ý);
        GL11.glVertex3d(box.Ø­áŒŠá, box.Â, box.Ó);
        GL11.glVertex3d(box.Ø­áŒŠá, box.Âµá€, box.Ó);
        GL11.glVertex3d(box.Ø­áŒŠá, box.Âµá€, box.Ý);
        GL11.glEnd();
        GL11.glBegin(7);
        GL11.glVertex3d(box.HorizonCode_Horizon_È, box.Â, box.Ý);
        GL11.glVertex3d(box.Ø­áŒŠá, box.Â, box.Ý);
        GL11.glVertex3d(box.Ø­áŒŠá, box.Âµá€, box.Ý);
        GL11.glVertex3d(box.HorizonCode_Horizon_È, box.Âµá€, box.Ý);
        GL11.glEnd();
        GL11.glBegin(7);
        GL11.glVertex3d(box.Ø­áŒŠá, box.Â, box.Ý);
        GL11.glVertex3d(box.HorizonCode_Horizon_È, box.Â, box.Ý);
        GL11.glVertex3d(box.HorizonCode_Horizon_È, box.Âµá€, box.Ý);
        GL11.glVertex3d(box.Ø­áŒŠá, box.Âµá€, box.Ý);
        GL11.glEnd();
        GL11.glBegin(7);
        GL11.glVertex3d(box.HorizonCode_Horizon_È, box.Âµá€, box.Ý);
        GL11.glVertex3d(box.Ø­áŒŠá, box.Âµá€, box.Ý);
        GL11.glVertex3d(box.Ø­áŒŠá, box.Âµá€, box.Ó);
        GL11.glVertex3d(box.HorizonCode_Horizon_È, box.Âµá€, box.Ó);
        GL11.glEnd();
        GL11.glBegin(7);
        GL11.glVertex3d(box.Ø­áŒŠá, box.Âµá€, box.Ý);
        GL11.glVertex3d(box.HorizonCode_Horizon_È, box.Âµá€, box.Ý);
        GL11.glVertex3d(box.HorizonCode_Horizon_È, box.Âµá€, box.Ó);
        GL11.glVertex3d(box.Ø­áŒŠá, box.Âµá€, box.Ó);
        GL11.glEnd();
        GL11.glBegin(7);
        GL11.glVertex3d(box.HorizonCode_Horizon_È, box.Â, box.Ý);
        GL11.glVertex3d(box.Ø­áŒŠá, box.Â, box.Ý);
        GL11.glVertex3d(box.Ø­áŒŠá, box.Â, box.Ó);
        GL11.glVertex3d(box.HorizonCode_Horizon_È, box.Â, box.Ó);
        GL11.glEnd();
        GL11.glBegin(7);
        GL11.glVertex3d(box.Ø­áŒŠá, box.Â, box.Ý);
        GL11.glVertex3d(box.HorizonCode_Horizon_È, box.Â, box.Ý);
        GL11.glVertex3d(box.HorizonCode_Horizon_È, box.Â, box.Ó);
        GL11.glVertex3d(box.Ø­áŒŠá, box.Â, box.Ó);
        GL11.glEnd();
    }
    
    public static ScaledResolution £á() {
        return OGLManager.HorizonCode_Horizon_È;
    }
}
