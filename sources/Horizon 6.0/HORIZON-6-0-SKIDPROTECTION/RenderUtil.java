package HORIZON-6-0-SKIDPROTECTION;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

public class RenderUtil
{
    public static void HorizonCode_Horizon_È(final double x1, double y1, final double x2, double y2) {
        if (y1 > y2) {
            final double temp = y2;
            y2 = y1;
            y1 = temp;
        }
        GL11.glEnable(3089);
        GL11.glScissor((int)x1, (int)(Display.getHeight() - y2), (int)(x2 - x1), (int)(y2 - y1));
    }
    
    public static void HorizonCode_Horizon_È() {
        GL11.glDisable(3089);
    }
    
    public static void Â() {
        GL11.glStencilOp(7681, 7680, 7680);
        GL11.glClear(1024);
    }
    
    public static void Ý() {
        GL11.glStencilFunc(512, 1, 255);
        GL11.glStencilMask(255);
        GL11.glColorMask(false, false, false, false);
        GL11.glDepthMask(false);
    }
    
    public static void Ø­áŒŠá() {
        GL11.glColorMask(true, true, true, true);
        GL11.glDepthMask(true);
        GL11.glStencilFunc(517, 1, 255);
        GL11.glStencilMask(0);
    }
    
    public static void Âµá€() {
        GL11.glStencilFunc(519, 1, 255);
        GL11.glStencilMask(255);
        GL11.glEnable(2929);
    }
    
    public static void HorizonCode_Horizon_È(final float[] rgba) {
        GlStateManager.HorizonCode_Horizon_È(516, 0.001f);
        GlStateManager.Ý(rgba[0], rgba[1], rgba[2], rgba[3]);
        GlStateManager.Ø­áŒŠá();
        GlStateManager.á();
        OGLManager.ÂµÈ();
        GlStateManager.HorizonCode_Horizon_È(770, 771, 1, 0);
    }
    
    public static void Ó() {
        GlStateManager.HorizonCode_Horizon_È(516, 0.1f);
        GlStateManager.Ý(1.0f, 1.0f, 1.0f, 1.0f);
        GlStateManager.ÂµÈ();
        OGLManager.áˆºÑ¢Õ();
        GL11.glLineWidth(1.0f);
    }
}
