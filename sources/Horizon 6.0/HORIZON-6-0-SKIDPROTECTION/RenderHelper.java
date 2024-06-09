package HORIZON-6-0-SKIDPROTECTION;

import org.lwjgl.opengl.GL11;
import java.nio.FloatBuffer;

public class RenderHelper
{
    private static FloatBuffer HorizonCode_Horizon_È;
    private static final Vec3 Â;
    private static final Vec3 Ý;
    private static final String Ø­áŒŠá = "CL_00000629";
    
    static {
        RenderHelper.HorizonCode_Horizon_È = GLAllocation.Âµá€(16);
        Â = new Vec3(0.20000000298023224, 1.0, -0.699999988079071).HorizonCode_Horizon_È();
        Ý = new Vec3(-0.20000000298023224, 1.0, 0.699999988079071).HorizonCode_Horizon_È();
    }
    
    public static void HorizonCode_Horizon_È() {
        GlStateManager.Ó();
        GlStateManager.Â(0);
        GlStateManager.Â(1);
        GlStateManager.Ø();
    }
    
    public static void Â() {
        GlStateManager.Âµá€();
        GlStateManager.HorizonCode_Horizon_È(0);
        GlStateManager.HorizonCode_Horizon_È(1);
        GlStateManager.à();
        GlStateManager.HorizonCode_Horizon_È(1032, 5634);
        final float var0 = 0.4f;
        final float var2 = 0.6f;
        final float var3 = 0.0f;
        GL11.glLight(16384, 4611, HorizonCode_Horizon_È(RenderHelper.Â.HorizonCode_Horizon_È, RenderHelper.Â.Â, RenderHelper.Â.Ý, 0.0));
        GL11.glLight(16384, 4609, HorizonCode_Horizon_È(var2, var2, var2, 1.0f));
        GL11.glLight(16384, 4608, HorizonCode_Horizon_È(0.0f, 0.0f, 0.0f, 1.0f));
        GL11.glLight(16384, 4610, HorizonCode_Horizon_È(var3, var3, var3, 1.0f));
        GL11.glLight(16385, 4611, HorizonCode_Horizon_È(RenderHelper.Ý.HorizonCode_Horizon_È, RenderHelper.Ý.Â, RenderHelper.Ý.Ý, 0.0));
        GL11.glLight(16385, 4609, HorizonCode_Horizon_È(var2, var2, var2, 1.0f));
        GL11.glLight(16385, 4608, HorizonCode_Horizon_È(0.0f, 0.0f, 0.0f, 1.0f));
        GL11.glLight(16385, 4610, HorizonCode_Horizon_È(var3, var3, var3, 1.0f));
        GlStateManager.áˆºÑ¢Õ(7424);
        GL11.glLightModel(2899, HorizonCode_Horizon_È(var0, var0, var0, 1.0f));
    }
    
    private static FloatBuffer HorizonCode_Horizon_È(final double p_74517_0_, final double p_74517_2_, final double p_74517_4_, final double p_74517_6_) {
        return HorizonCode_Horizon_È((float)p_74517_0_, (float)p_74517_2_, (float)p_74517_4_, (float)p_74517_6_);
    }
    
    private static FloatBuffer HorizonCode_Horizon_È(final float p_74521_0_, final float p_74521_1_, final float p_74521_2_, final float p_74521_3_) {
        RenderHelper.HorizonCode_Horizon_È.clear();
        RenderHelper.HorizonCode_Horizon_È.put(p_74521_0_).put(p_74521_1_).put(p_74521_2_).put(p_74521_3_);
        RenderHelper.HorizonCode_Horizon_È.flip();
        return RenderHelper.HorizonCode_Horizon_È;
    }
    
    public static void Ý() {
        GlStateManager.Çªà¢();
        GlStateManager.Â(-30.0f, 0.0f, 1.0f, 0.0f);
        GlStateManager.Â(165.0f, 1.0f, 0.0f, 0.0f);
        Â();
        GlStateManager.Ê();
    }
}
