package HORIZON-6-0-SKIDPROTECTION;

import java.awt.Color;
import org.lwjgl.opengl.GL11;

public final class RenderHelper_1118140819
{
    public static void HorizonCode_Horizon_È(final AxisAlignedBB mask) {
        GL11.glPushMatrix();
        GL11.glBegin(2);
        GL11.glVertex3d(mask.HorizonCode_Horizon_È, mask.Â, mask.Ý);
        GL11.glVertex3d(mask.HorizonCode_Horizon_È, mask.Âµá€, mask.Ó);
        GL11.glEnd();
        GL11.glBegin(2);
        GL11.glVertex3d(mask.Ø­áŒŠá, mask.Â, mask.Ý);
        GL11.glVertex3d(mask.Ø­áŒŠá, mask.Âµá€, mask.Ó);
        GL11.glEnd();
        GL11.glBegin(2);
        GL11.glVertex3d(mask.Ø­áŒŠá, mask.Â, mask.Ó);
        GL11.glVertex3d(mask.HorizonCode_Horizon_È, mask.Âµá€, mask.Ó);
        GL11.glEnd();
        GL11.glBegin(2);
        GL11.glVertex3d(mask.Ø­áŒŠá, mask.Â, mask.Ý);
        GL11.glVertex3d(mask.HorizonCode_Horizon_È, mask.Âµá€, mask.Ý);
        GL11.glEnd();
        GL11.glBegin(2);
        GL11.glVertex3d(mask.Ø­áŒŠá, mask.Â, mask.Ý);
        GL11.glVertex3d(mask.HorizonCode_Horizon_È, mask.Â, mask.Ó);
        GL11.glEnd();
        GL11.glBegin(2);
        GL11.glVertex3d(mask.Ø­áŒŠá, mask.Âµá€, mask.Ý);
        GL11.glVertex3d(mask.HorizonCode_Horizon_È, mask.Âµá€, mask.Ó);
        GL11.glEnd();
        GL11.glPopMatrix();
    }
    
    public static void HorizonCode_Horizon_È(final float x, final float y, final float x2, final float y2, final float l1, final int col1, final int col2) {
        HorizonCode_Horizon_È(x, y, x2, y2, col2);
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
    
    public static void Â(final AxisAlignedBB mask) {
        final WorldRenderer worldRenderer = Tessellator.HorizonCode_Horizon_È.Ý();
        final Tessellator tessellator = Tessellator.HorizonCode_Horizon_È;
        worldRenderer.Â();
        worldRenderer.Â(mask.HorizonCode_Horizon_È, mask.Â, mask.Ý);
        worldRenderer.Â(mask.HorizonCode_Horizon_È, mask.Âµá€, mask.Ý);
        worldRenderer.Â(mask.Ø­áŒŠá, mask.Â, mask.Ý);
        worldRenderer.Â(mask.Ø­áŒŠá, mask.Âµá€, mask.Ý);
        worldRenderer.Â(mask.Ø­áŒŠá, mask.Â, mask.Ó);
        worldRenderer.Â(mask.Ø­áŒŠá, mask.Âµá€, mask.Ó);
        worldRenderer.Â(mask.HorizonCode_Horizon_È, mask.Â, mask.Ó);
        worldRenderer.Â(mask.HorizonCode_Horizon_È, mask.Âµá€, mask.Ó);
        tessellator.Â();
        worldRenderer.Â();
        worldRenderer.Â(mask.Ø­áŒŠá, mask.Âµá€, mask.Ý);
        worldRenderer.Â(mask.Ø­áŒŠá, mask.Â, mask.Ý);
        worldRenderer.Â(mask.HorizonCode_Horizon_È, mask.Âµá€, mask.Ý);
        worldRenderer.Â(mask.HorizonCode_Horizon_È, mask.Â, mask.Ý);
        worldRenderer.Â(mask.HorizonCode_Horizon_È, mask.Âµá€, mask.Ó);
        worldRenderer.Â(mask.HorizonCode_Horizon_È, mask.Â, mask.Ó);
        worldRenderer.Â(mask.Ø­áŒŠá, mask.Âµá€, mask.Ó);
        worldRenderer.Â(mask.Ø­áŒŠá, mask.Â, mask.Ó);
        tessellator.Â();
        worldRenderer.Â();
        worldRenderer.Â(mask.HorizonCode_Horizon_È, mask.Âµá€, mask.Ý);
        worldRenderer.Â(mask.Ø­áŒŠá, mask.Âµá€, mask.Ý);
        worldRenderer.Â(mask.Ø­áŒŠá, mask.Âµá€, mask.Ó);
        worldRenderer.Â(mask.HorizonCode_Horizon_È, mask.Âµá€, mask.Ó);
        worldRenderer.Â(mask.HorizonCode_Horizon_È, mask.Âµá€, mask.Ý);
        worldRenderer.Â(mask.HorizonCode_Horizon_È, mask.Âµá€, mask.Ó);
        worldRenderer.Â(mask.Ø­áŒŠá, mask.Âµá€, mask.Ó);
        worldRenderer.Â(mask.Ø­áŒŠá, mask.Âµá€, mask.Ý);
        tessellator.Â();
        worldRenderer.Â();
        worldRenderer.Â(mask.HorizonCode_Horizon_È, mask.Â, mask.Ý);
        worldRenderer.Â(mask.Ø­áŒŠá, mask.Â, mask.Ý);
        worldRenderer.Â(mask.Ø­áŒŠá, mask.Â, mask.Ó);
        worldRenderer.Â(mask.HorizonCode_Horizon_È, mask.Â, mask.Ó);
        worldRenderer.Â(mask.HorizonCode_Horizon_È, mask.Â, mask.Ý);
        worldRenderer.Â(mask.HorizonCode_Horizon_È, mask.Â, mask.Ó);
        worldRenderer.Â(mask.Ø­áŒŠá, mask.Â, mask.Ó);
        worldRenderer.Â(mask.Ø­áŒŠá, mask.Â, mask.Ý);
        tessellator.Â();
        worldRenderer.Â();
        worldRenderer.Â(mask.HorizonCode_Horizon_È, mask.Â, mask.Ý);
        worldRenderer.Â(mask.HorizonCode_Horizon_È, mask.Âµá€, mask.Ý);
        worldRenderer.Â(mask.HorizonCode_Horizon_È, mask.Â, mask.Ó);
        worldRenderer.Â(mask.HorizonCode_Horizon_È, mask.Âµá€, mask.Ó);
        worldRenderer.Â(mask.Ø­áŒŠá, mask.Â, mask.Ó);
        worldRenderer.Â(mask.Ø­áŒŠá, mask.Âµá€, mask.Ó);
        worldRenderer.Â(mask.Ø­áŒŠá, mask.Â, mask.Ý);
        worldRenderer.Â(mask.Ø­áŒŠá, mask.Âµá€, mask.Ý);
        tessellator.Â();
        worldRenderer.Â();
        worldRenderer.Â(mask.HorizonCode_Horizon_È, mask.Âµá€, mask.Ó);
        worldRenderer.Â(mask.HorizonCode_Horizon_È, mask.Â, mask.Ó);
        worldRenderer.Â(mask.HorizonCode_Horizon_È, mask.Âµá€, mask.Ý);
        worldRenderer.Â(mask.HorizonCode_Horizon_È, mask.Â, mask.Ý);
        worldRenderer.Â(mask.Ø­áŒŠá, mask.Âµá€, mask.Ý);
        worldRenderer.Â(mask.Ø­áŒŠá, mask.Â, mask.Ý);
        worldRenderer.Â(mask.Ø­áŒŠá, mask.Âµá€, mask.Ó);
        worldRenderer.Â(mask.Ø­áŒŠá, mask.Â, mask.Ó);
        tessellator.Â();
    }
    
    public static void HorizonCode_Horizon_È(final Color color) {
        GL11.glColor4f(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, color.getAlpha() / 255.0f);
    }
    
    public static void HorizonCode_Horizon_È(final int hex) {
        final float alpha = (hex >> 24 & 0xFF) / 255.0f;
        final float red = (hex >> 16 & 0xFF) / 255.0f;
        final float green = (hex >> 8 & 0xFF) / 255.0f;
        final float blue = (hex & 0xFF) / 255.0f;
        GL11.glColor4f(red, green, blue, alpha);
    }
    
    public static void HorizonCode_Horizon_È(final float x, final float y, final float x1, final float y1, final int topColor, final int bottomColor) {
        GL11.glEnable(1536);
        GL11.glShadeModel(7425);
        GL11.glBegin(7);
        HorizonCode_Horizon_È(topColor);
        GL11.glVertex2f(x, y1);
        GL11.glVertex2f(x1, y1);
        HorizonCode_Horizon_È(bottomColor);
        GL11.glVertex2f(x1, y);
        GL11.glVertex2f(x, y);
        GL11.glEnd();
        GL11.glShadeModel(7424);
        GL11.glDisable(1536);
    }
    
    public static void Ý(final AxisAlignedBB mask) {
        GL11.glPushMatrix();
        GL11.glBegin(2);
        GL11.glVertex3d(mask.HorizonCode_Horizon_È, mask.Â, mask.Ý);
        GL11.glVertex3d(mask.HorizonCode_Horizon_È, mask.Âµá€, mask.Ó);
        GL11.glEnd();
        GL11.glBegin(2);
        GL11.glVertex3d(mask.HorizonCode_Horizon_È, mask.Âµá€, mask.Ý);
        GL11.glVertex3d(mask.HorizonCode_Horizon_È, mask.Â, mask.Ó);
        GL11.glEnd();
        GL11.glBegin(2);
        GL11.glVertex3d(mask.Ø­áŒŠá, mask.Â, mask.Ý);
        GL11.glVertex3d(mask.Ø­áŒŠá, mask.Âµá€, mask.Ó);
        GL11.glEnd();
        GL11.glBegin(2);
        GL11.glVertex3d(mask.Ø­áŒŠá, mask.Âµá€, mask.Ý);
        GL11.glVertex3d(mask.Ø­áŒŠá, mask.Â, mask.Ó);
        GL11.glEnd();
        GL11.glBegin(2);
        GL11.glVertex3d(mask.Ø­áŒŠá, mask.Â, mask.Ó);
        GL11.glVertex3d(mask.HorizonCode_Horizon_È, mask.Âµá€, mask.Ó);
        GL11.glEnd();
        GL11.glBegin(2);
        GL11.glVertex3d(mask.Ø­áŒŠá, mask.Âµá€, mask.Ó);
        GL11.glVertex3d(mask.HorizonCode_Horizon_È, mask.Â, mask.Ó);
        GL11.glEnd();
        GL11.glBegin(2);
        GL11.glVertex3d(mask.Ø­áŒŠá, mask.Â, mask.Ý);
        GL11.glVertex3d(mask.HorizonCode_Horizon_È, mask.Âµá€, mask.Ý);
        GL11.glEnd();
        GL11.glBegin(2);
        GL11.glVertex3d(mask.Ø­áŒŠá, mask.Âµá€, mask.Ý);
        GL11.glVertex3d(mask.HorizonCode_Horizon_È, mask.Â, mask.Ý);
        GL11.glEnd();
        GL11.glBegin(2);
        GL11.glVertex3d(mask.HorizonCode_Horizon_È, mask.Âµá€, mask.Ý);
        GL11.glVertex3d(mask.Ø­áŒŠá, mask.Âµá€, mask.Ó);
        GL11.glEnd();
        GL11.glBegin(2);
        GL11.glVertex3d(mask.Ø­áŒŠá, mask.Âµá€, mask.Ý);
        GL11.glVertex3d(mask.HorizonCode_Horizon_È, mask.Âµá€, mask.Ó);
        GL11.glEnd();
        GL11.glBegin(2);
        GL11.glVertex3d(mask.HorizonCode_Horizon_È, mask.Â, mask.Ý);
        GL11.glVertex3d(mask.Ø­áŒŠá, mask.Â, mask.Ó);
        GL11.glEnd();
        GL11.glBegin(2);
        GL11.glVertex3d(mask.Ø­áŒŠá, mask.Â, mask.Ý);
        GL11.glVertex3d(mask.HorizonCode_Horizon_È, mask.Â, mask.Ó);
        GL11.glEnd();
        GL11.glPopMatrix();
    }
    
    public static void Ø­áŒŠá(final AxisAlignedBB mask) {
        final WorldRenderer var2 = Tessellator.HorizonCode_Horizon_È.Ý();
        final Tessellator var3 = Tessellator.HorizonCode_Horizon_È;
        var2.HorizonCode_Horizon_È(3);
        var2.Â(mask.HorizonCode_Horizon_È, mask.Â, mask.Ý);
        var2.Â(mask.Ø­áŒŠá, mask.Â, mask.Ý);
        var2.Â(mask.Ø­áŒŠá, mask.Â, mask.Ó);
        var2.Â(mask.HorizonCode_Horizon_È, mask.Â, mask.Ó);
        var2.Â(mask.HorizonCode_Horizon_È, mask.Â, mask.Ý);
        var3.Â();
        var2.HorizonCode_Horizon_È(3);
        var2.Â(mask.HorizonCode_Horizon_È, mask.Âµá€, mask.Ý);
        var2.Â(mask.Ø­áŒŠá, mask.Âµá€, mask.Ý);
        var2.Â(mask.Ø­áŒŠá, mask.Âµá€, mask.Ó);
        var2.Â(mask.HorizonCode_Horizon_È, mask.Âµá€, mask.Ó);
        var2.Â(mask.HorizonCode_Horizon_È, mask.Âµá€, mask.Ý);
        var3.Â();
        var2.HorizonCode_Horizon_È(1);
        var2.Â(mask.HorizonCode_Horizon_È, mask.Â, mask.Ý);
        var2.Â(mask.HorizonCode_Horizon_È, mask.Âµá€, mask.Ý);
        var2.Â(mask.Ø­áŒŠá, mask.Â, mask.Ý);
        var2.Â(mask.Ø­áŒŠá, mask.Âµá€, mask.Ý);
        var2.Â(mask.Ø­áŒŠá, mask.Â, mask.Ó);
        var2.Â(mask.Ø­áŒŠá, mask.Âµá€, mask.Ó);
        var2.Â(mask.HorizonCode_Horizon_È, mask.Â, mask.Ó);
        var2.Â(mask.HorizonCode_Horizon_È, mask.Âµá€, mask.Ó);
        var3.Â();
    }
    
    public static void HorizonCode_Horizon_È(final float g, final float h, final float i, final float j, final int col1) {
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
}
