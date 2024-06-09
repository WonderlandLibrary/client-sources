package HORIZON-6-0-SKIDPROTECTION;

import org.lwjgl.opengl.GL11;

public class OGLRender
{
    private static ScaledResolution HorizonCode_Horizon_È;
    
    public static void HorizonCode_Horizon_È() {
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
    
    public static void Â() {
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glEnable(2929);
        GL11.glDisable(2848);
        GL11.glHint(3154, 4352);
        GL11.glHint(3155, 4352);
    }
    
    public static void HorizonCode_Horizon_È(final float lineWidth) {
        GL11.glDisable(3008);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glDisable(3553);
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        GL11.glEnable(2884);
        Minecraft.áŒŠà().µÕ.HorizonCode_Horizon_È(0.0);
        GL11.glEnable(2848);
        GL11.glHint(3154, 4354);
        GL11.glHint(3155, 4354);
        GL11.glLineWidth(lineWidth);
    }
    
    public static void Ý() {
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
}
