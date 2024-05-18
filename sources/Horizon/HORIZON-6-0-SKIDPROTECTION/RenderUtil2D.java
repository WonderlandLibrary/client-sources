package HORIZON-6-0-SKIDPROTECTION;

import javax.vecmath.Point2d;
import java.util.List;
import org.lwjgl.opengl.GL11;

public class RenderUtil2D extends RenderUtil
{
    public static void HorizonCode_Horizon_È(final double x1, final double y1, final double x2, final double y2, final int fill, final int outline, final float width) {
        HorizonCode_Horizon_È(x1, y1, x2, y2, fill, 1.0f, 7);
        HorizonCode_Horizon_È(x1, y1, x2, y2, outline, width, 2);
    }
    
    public static void HorizonCode_Horizon_È(final double x1, final double y1, final double x2, final double y2, final int color, final float width, final int renderCode) {
        final float[] rgba = ColorUtil.HorizonCode_Horizon_È(color);
        RenderUtil.HorizonCode_Horizon_È(rgba);
        GL11.glLineWidth((float)Math.max(0.1, width));
        final Tessellator tess = Tessellator.HorizonCode_Horizon_È();
        final WorldRenderer render = tess.Ý();
        render.HorizonCode_Horizon_È(renderCode);
        render.Â(x1, y2, 0.0);
        render.Â(x2, y2, 0.0);
        render.Â(x2, y1, 0.0);
        render.Â(x1, y1, 0.0);
        tess.Â();
        Ó();
    }
    
    public static void HorizonCode_Horizon_È(final double x1, final double y1, final double x2, final double y2, final int color) {
        final float[] rgba = ColorUtil.HorizonCode_Horizon_È(color);
        RenderUtil.HorizonCode_Horizon_È(rgba);
        final Tessellator tess = Tessellator.HorizonCode_Horizon_È();
        final WorldRenderer render = tess.Ý();
        OGLManager.áˆºÑ¢Õ();
        render.Â();
        render.HorizonCode_Horizon_È((int)x1, (int)y2, 0.0, 0.0, 1.0);
        render.HorizonCode_Horizon_È((int)x2, (int)y2, 0.0, 1.0, 1.0);
        render.HorizonCode_Horizon_È((int)x2, (int)y1, 0.0, 1.0, 0.0);
        render.HorizonCode_Horizon_È((int)x1, (int)y1, 0.0, 0.0, 0.0);
        tess.Â();
        Ó();
    }
    
    public static void HorizonCode_Horizon_È(final List<Point2d> points, final int fill, final int outline, final float width, final boolean connected) {
        HorizonCode_Horizon_È(points, fill, 1.0f, 6);
        HorizonCode_Horizon_È(points, outline, width, connected ? 2 : 3);
    }
    
    public static void HorizonCode_Horizon_È(final List<Point2d> points, final int color, final float width, final int renderCode) {
        final float[] rgba = ColorUtil.HorizonCode_Horizon_È(color);
        RenderUtil.HorizonCode_Horizon_È(rgba);
        GL11.glLineWidth((float)Math.max(0.1, width));
        final Tessellator tess = Tessellator.HorizonCode_Horizon_È();
        final WorldRenderer render = tess.Ý();
        render.HorizonCode_Horizon_È(renderCode);
        for (int i = points.size() - 1; i >= 0; --i) {
            final Point2d point = points.get(i);
            render.Â(point.x, point.y, 0.0);
        }
        tess.Â();
        Ó();
    }
    
    public static void HorizonCode_Horizon_È(final double x, final double y, final double radius, final int color) {
        final float[] rgba = ColorUtil.HorizonCode_Horizon_È(color);
        RenderUtil.HorizonCode_Horizon_È(rgba);
        final Tessellator tess = Tessellator.HorizonCode_Horizon_È();
        final WorldRenderer render = tess.Ý();
        for (double i = 0.0; i < 360.0; ++i) {
            final double cs = i * 3.141592653589793 / 180.0;
            final double ps = (i - 1.0) * 3.141592653589793 / 180.0;
            final double[] outer = { Math.cos(cs) * radius, -Math.sin(cs) * radius, Math.cos(ps) * radius, -Math.sin(ps) * radius };
            render.HorizonCode_Horizon_È(6);
            render.Â(x + outer[2], y + outer[3], 0.0);
            render.Â(x + outer[0], y + outer[1], 0.0);
            render.Â(x, y, 0.0);
            tess.Â();
        }
        GlStateManager.ÂµÈ();
        GlStateManager.Ý();
        OGLManager.áˆºÑ¢Õ();
        GlStateManager.HorizonCode_Horizon_È(516, 0.1f);
    }
    
    public static void HorizonCode_Horizon_È(final double x, final double y, final double radius, final int color, final float width) {
        final float[] rgba = ColorUtil.HorizonCode_Horizon_È(color);
        RenderUtil.HorizonCode_Horizon_È(rgba);
        GL11.glLineWidth((float)Math.max(0.1, width));
        final Tessellator tess = Tessellator.HorizonCode_Horizon_È();
        final WorldRenderer render = tess.Ý();
        render.HorizonCode_Horizon_È(2);
        for (double i = 0.0; i < 360.0; ++i) {
            final double cs = i * 3.141592653589793 / 180.0;
            final double[] outer = { Math.cos(cs) * radius, -Math.sin(cs) * radius };
            render.Â(x + outer[0], y + outer[1], 0.0);
        }
        tess.Â();
        GlStateManager.ÂµÈ();
        GlStateManager.Ý();
        OGLManager.áˆºÑ¢Õ();
        GlStateManager.HorizonCode_Horizon_È(516, 0.1f);
    }
    
    public static void Â(final double x, final double y, final double radius, final double hole, final int color) {
        final float[] rgba = ColorUtil.HorizonCode_Horizon_È(color);
        RenderUtil.HorizonCode_Horizon_È(rgba);
        final Tessellator tess = Tessellator.HorizonCode_Horizon_È();
        final WorldRenderer render = tess.Ý();
        for (double i = 0.0; i < 360.0; ++i) {
            final double cs = i * 3.141592653589793 / 180.0;
            final double ps = (i - 1.0) * 3.141592653589793 / 180.0;
            final double[] outer = { Math.cos(cs) * radius, -Math.sin(cs) * radius, Math.cos(ps) * radius, -Math.sin(ps) * radius };
            final double[] inner = { Math.cos(cs) * hole, -Math.sin(cs) * hole, Math.cos(ps) * hole, -Math.sin(ps) * hole };
            render.HorizonCode_Horizon_È(7);
            render.Â(x + inner[0], y + inner[1], 0.0);
            render.Â(x + inner[2], y + inner[3], 0.0);
            render.Â(x + outer[2], y + outer[3], 0.0);
            render.Â(x + outer[0], y + outer[1], 0.0);
            tess.Â();
        }
        Ó();
    }
    
    public static void HorizonCode_Horizon_È(final double x, final double y, final double radius, final double hole, double part, final int maxParts, final double padding, final int color) {
        final float[] rgba = ColorUtil.HorizonCode_Horizon_È(color);
        RenderUtil.HorizonCode_Horizon_È(rgba);
        final Tessellator tess = Tessellator.HorizonCode_Horizon_È();
        final WorldRenderer render = tess.Ý();
        part = (part + maxParts) % maxParts;
        for (double i = 360.0 / maxParts * (part + padding); i < 360.0 / maxParts * (part + 1.0 - padding) - 0.5; ++i) {
            final double cs = -i * 3.141592653589793 / 180.0;
            final double ps = (-i - 1.0) * 3.141592653589793 / 180.0;
            final double[] outer = { Math.cos(cs) * radius, -Math.sin(cs) * radius, Math.cos(ps) * radius, -Math.sin(ps) * radius };
            final double[] inner = { Math.cos(cs) * hole, -Math.sin(cs) * hole, Math.cos(ps) * hole, -Math.sin(ps) * hole };
            render.HorizonCode_Horizon_È(7);
            render.Â(x + inner[0], y + inner[1], 0.0);
            render.Â(x + inner[2], y + inner[3], 0.0);
            render.Â(x + outer[2], y + outer[3], 0.0);
            render.Â(x + outer[0], y + outer[1], 0.0);
            tess.Â();
        }
        Ó();
    }
    
    public static void HorizonCode_Horizon_È(final double x, final double y, final double radius, final double hole, double part, final int maxParts, final double frac, final double padding, final int color) {
        final float[] rgba = ColorUtil.HorizonCode_Horizon_È(color);
        RenderUtil.HorizonCode_Horizon_È(rgba);
        final Tessellator tess = Tessellator.HorizonCode_Horizon_È();
        final WorldRenderer render = tess.Ý();
        part = (part + maxParts) % maxParts;
        for (double i = 360.0 / maxParts * part; i < 360.0 / maxParts * (part + frac) - 0.5; ++i) {
            final double cs = -i * 3.141592653589793 / 180.0;
            final double ps = (-i - 1.0) * 3.141592653589793 / 180.0;
            final double[] outer = { Math.cos(cs) * radius, -Math.sin(cs) * radius, Math.cos(ps) * radius, -Math.sin(ps) * radius };
            final double[] inner = { Math.cos(cs) * hole, -Math.sin(cs) * hole, Math.cos(ps) * hole, -Math.sin(ps) * hole };
            render.HorizonCode_Horizon_È(7);
            render.Â(x + inner[0], y + inner[1], 0.0);
            render.Â(x + inner[2], y + inner[3], 0.0);
            render.Â(x + outer[2], y + outer[3], 0.0);
            render.Â(x + outer[0], y + outer[1], 0.0);
            tess.Â();
        }
        Ó();
    }
}
