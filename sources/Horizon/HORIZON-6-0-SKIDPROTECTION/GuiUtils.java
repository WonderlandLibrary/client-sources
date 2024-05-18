package HORIZON-6-0-SKIDPROTECTION;

import org.lwjgl.opengl.GL11;

public class GuiUtils
{
    private static Minecraft HorizonCode_Horizon_È;
    private static GuiUtils Â;
    
    static {
        GuiUtils.HorizonCode_Horizon_È = Minecraft.áŒŠà();
        GuiUtils.Â = new GuiUtils();
    }
    
    public void HorizonCode_Horizon_È(final int x, final int y, final int color) {
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        OGLManager.Ø­áŒŠá(color);
        GL11.glBlendFunc(770, 771);
        GL11.glLineWidth(2.0f);
        GL11.glBegin(3);
        GL11.glVertex2f((float)(x + 1), (float)(y + 9));
        GL11.glVertex2f((float)(x + 3), y + 13.0f);
        GL11.glVertex2f((float)(x + 7), (float)(y + 2));
        GL11.glEnd();
        GL11.glDisable(3042);
        GL11.glEnable(3553);
    }
    
    public void Â(final int x, final int y, final int color) {
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        OGLManager.Ø­áŒŠá(color);
        GL11.glBlendFunc(770, 771);
        GL11.glLineWidth(2.0f);
        GL11.glBegin(3);
        GL11.glVertex2f(x - 2.0f, y + 2.5f);
        GL11.glVertex2f((float)(x + 4), y - 3.0f);
        GL11.glVertex2f((float)(x + 10), y + 2.5f);
        GL11.glEnd();
        GL11.glDisable(3042);
        GL11.glEnable(3553);
    }
    
    public void Ý(final int x, final int y, final int color) {
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        OGLManager.Ø­áŒŠá(color);
        GL11.glBlendFunc(770, 771);
        GL11.glLineWidth(2.0f);
        GL11.glBegin(3);
        GL11.glVertex2f(x - 2.0f, y + 2.5f);
        GL11.glVertex2f((float)(x + 4), y + 8.0f);
        GL11.glVertex2f((float)(x + 10), y + 2.5f);
        GL11.glEnd();
        GL11.glDisable(3042);
        GL11.glEnable(3553);
    }
    
    public void HorizonCode_Horizon_È(float x, float y, float x1, float y1, final int borderC, final int insideC) {
        x *= 2.0f;
        y *= 2.0f;
        x1 *= 2.0f;
        y1 *= 2.0f;
        GL11.glScalef(0.5f, 0.5f, 0.5f);
        this.Â(x, y + 1.0f, y1 - 2.0f, borderC);
        this.Â(x1 - 1.0f, y + 1.0f, y1 - 2.0f, borderC);
        this.HorizonCode_Horizon_È(x + 2.0f, x1 - 3.0f, y, borderC);
        this.HorizonCode_Horizon_È(x + 2.0f, x1 - 3.0f, y1 - 1.0f, borderC);
        this.HorizonCode_Horizon_È(x + 1.0f, x + 1.0f, y + 1.0f, borderC);
        this.HorizonCode_Horizon_È(x1 - 2.0f, x1 - 2.0f, y + 1.0f, borderC);
        this.HorizonCode_Horizon_È(x1 - 2.0f, x1 - 2.0f, y1 - 2.0f, borderC);
        this.HorizonCode_Horizon_È(x + 1.0f, x + 1.0f, y1 - 2.0f, borderC);
        this.HorizonCode_Horizon_È(x + 1.0f, y + 1.0f, x1 - 1.0f, y1 - 1.0f, insideC);
        GL11.glScalef(2.0f, 2.0f, 2.0f);
    }
    
    public void Â(float x, float y, float x1, float y1, final int borderC, final int insideC) {
        x *= 2.0f;
        x1 *= 2.0f;
        y *= 2.0f;
        y1 *= 2.0f;
        GL11.glScalef(0.5f, 0.5f, 0.5f);
        this.Â(x, y, y1 - 1.0f, borderC);
        this.Â(x1 - 1.0f, y, y1, borderC);
        this.HorizonCode_Horizon_È(x, x1 - 1.0f, y, borderC);
        this.HorizonCode_Horizon_È(x, x1 - 2.0f, y1 - 1.0f, borderC);
        this.HorizonCode_Horizon_È(x + 1.0f, y + 1.0f, x1 - 1.0f, y1 - 1.0f, insideC);
        GL11.glScalef(2.0f, 2.0f, 2.0f);
    }
    
    public void HorizonCode_Horizon_È(final double x, final double y, final double x2, final double y2, final float l1, final int col1, final int col2) {
        this.HorizonCode_Horizon_È((float)x, (float)y, (float)x2, (float)y2, col2);
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
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glDisable(2848);
    }
    
    public void HorizonCode_Horizon_È(float par1, float par2, final float par3, final int par4) {
        if (par2 < par1) {
            final float var5 = par1;
            par1 = par2;
            par2 = var5;
        }
        this.HorizonCode_Horizon_È(par1, par3, par2 + 1.0f, par3 + 1.0f, par4);
    }
    
    public void Â(final float par1, float par2, float par3, final int par4) {
        if (par3 < par2) {
            final float var5 = par2;
            par2 = par3;
            par3 = var5;
        }
        this.HorizonCode_Horizon_È(par1, par2 + 1.0f, par1 + 1.0f, par3, par4);
    }
    
    public void HorizonCode_Horizon_È(final float paramXStart, final float paramYStart, final float paramXEnd, final float paramYEnd, final int paramColor) {
        final float alpha = (paramColor >> 24 & 0xFF) / 255.0f;
        final float red = (paramColor >> 16 & 0xFF) / 255.0f;
        final float green = (paramColor >> 8 & 0xFF) / 255.0f;
        final float blue = (paramColor & 0xFF) / 255.0f;
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GL11.glPushMatrix();
        GL11.glColor4f(red, green, blue, alpha);
        GL11.glBegin(7);
        GL11.glVertex2d((double)paramXEnd, (double)paramYStart);
        GL11.glVertex2d((double)paramXStart, (double)paramYStart);
        GL11.glVertex2d((double)paramXStart, (double)paramYEnd);
        GL11.glVertex2d((double)paramXEnd, (double)paramYEnd);
        GL11.glEnd();
        GL11.glPopMatrix();
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glDisable(2848);
    }
    
    public void HorizonCode_Horizon_È(final double x, final double y, final double x2, final double y2, final int col1, final int col2) {
        final float f = (col1 >> 24 & 0xFF) / 255.0f;
        final float f2 = (col1 >> 16 & 0xFF) / 255.0f;
        final float f3 = (col1 >> 8 & 0xFF) / 255.0f;
        final float f4 = (col1 & 0xFF) / 255.0f;
        final float f5 = (col2 >> 24 & 0xFF) / 255.0f;
        final float f6 = (col2 >> 16 & 0xFF) / 255.0f;
        final float f7 = (col2 >> 8 & 0xFF) / 255.0f;
        final float f8 = (col2 & 0xFF) / 255.0f;
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GL11.glShadeModel(7425);
        GL11.glPushMatrix();
        GL11.glBegin(7);
        GL11.glColor4f(f2, f3, f4, f);
        GL11.glVertex2d(x2, y);
        GL11.glVertex2d(x, y);
        GL11.glColor4f(f6, f7, f8, f5);
        GL11.glVertex2d(x, y2);
        GL11.glVertex2d(x2, y2);
        GL11.glEnd();
        GL11.glPopMatrix();
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glDisable(2848);
        GL11.glShadeModel(7424);
    }
    
    public void HorizonCode_Horizon_È(final double x, final double y, final double x2, final double y2, final float l1, final int col1, final int col2, final int col3) {
        final float f = (col1 >> 24 & 0xFF) / 255.0f;
        final float f2 = (col1 >> 16 & 0xFF) / 255.0f;
        final float f3 = (col1 >> 8 & 0xFF) / 255.0f;
        final float f4 = (col1 & 0xFF) / 255.0f;
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GL11.glDisable(3042);
        GL11.glPushMatrix();
        GL11.glColor4f(f2, f3, f4, f);
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
        this.HorizonCode_Horizon_È(x, y, x2, y2, col2, col3);
        GL11.glEnable(3042);
        GL11.glEnable(3553);
        GL11.glDisable(2848);
    }
    
    public void HorizonCode_Horizon_È(final int x, final int y, final float width, final double angle, final float points, final float radius, final int color) {
        GL11.glPushMatrix();
        final float f1 = (color >> 24 & 0xFF) / 255.0f;
        final float f2 = (color >> 16 & 0xFF) / 255.0f;
        final float f3 = (color >> 8 & 0xFF) / 255.0f;
        final float f4 = (color & 0xFF) / 255.0f;
        GL11.glTranslatef((float)x, (float)y, 0.0f);
        GL11.glColor4f(f2, f3, f4, f1);
        GL11.glLineWidth(width);
        GL11.glEnable(3042);
        GL11.glDisable(2929);
        GL11.glEnable(2848);
        GL11.glDisable(3553);
        GL11.glDisable(3008);
        GL11.glBlendFunc(770, 771);
        GL11.glHint(3154, 4354);
        GL11.glEnable(32925);
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
        GL11.glDisable(3042);
        GL11.glEnable(3553);
        GL11.glDisable(2848);
        GL11.glEnable(3008);
        GL11.glEnable(2929);
        GL11.glDisable(32925);
        GL11.glDisable(3479);
        GL11.glPopMatrix();
    }
    
    public void HorizonCode_Horizon_È(float cx, float cy, float r, final int num_segments, final int c) {
        GL11.glScalef(0.5f, 0.5f, 0.5f);
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
        GL11.glColor4f(f2, f3, f4, f);
        float x = r;
        float y = 0.0f;
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glEnable(2848);
        GL11.glBlendFunc(770, 771);
        GL11.glBegin(2);
        for (int ii = 0; ii < num_segments; ++ii) {
            GL11.glVertex2f(x + cx, y + cy);
            final float t = x;
            x = p * x - s * y;
            y = s * t + p * y;
        }
        GL11.glEnd();
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glDisable(2848);
        GL11.glScalef(2.0f, 2.0f, 2.0f);
    }
    
    public void HorizonCode_Horizon_È(int cx, int cy, double r, final int c) {
        GL11.glScalef(0.5f, 0.5f, 0.5f);
        r *= 2.0;
        cx *= 2;
        cy *= 2;
        final float f = (c >> 24 & 0xFF) / 255.0f;
        final float f2 = (c >> 16 & 0xFF) / 255.0f;
        final float f3 = (c >> 8 & 0xFF) / 255.0f;
        final float f4 = (c & 0xFF) / 255.0f;
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glEnable(2848);
        GL11.glBlendFunc(770, 771);
        GL11.glColor4f(f2, f3, f4, f);
        GL11.glBegin(6);
        for (int i = 0; i <= 360; ++i) {
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
    
    public static GuiUtils HorizonCode_Horizon_È() {
        return GuiUtils.Â;
    }
}
