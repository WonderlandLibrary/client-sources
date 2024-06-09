package HORIZON-6-0-SKIDPROTECTION;

import org.lwjgl.util.glu.GLU;
import org.lwjgl.opengl.GL11;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class ActiveRenderInfo
{
    private static final IntBuffer HorizonCode_Horizon_È;
    private static final FloatBuffer Â;
    private static final FloatBuffer Ý;
    private static final FloatBuffer Ø­áŒŠá;
    private static Vec3 Âµá€;
    private static float Ó;
    private static float à;
    private static float Ø;
    private static float áŒŠÆ;
    private static float áˆºÑ¢Õ;
    private static final String ÂµÈ = "CL_00000626";
    
    static {
        HorizonCode_Horizon_È = GLAllocation.Ø­áŒŠá(16);
        Â = GLAllocation.Âµá€(16);
        Ý = GLAllocation.Âµá€(16);
        Ø­áŒŠá = GLAllocation.Âµá€(3);
        ActiveRenderInfo.Âµá€ = new Vec3(0.0, 0.0, 0.0);
    }
    
    public static void HorizonCode_Horizon_È(final EntityPlayer p_74583_0_, final boolean p_74583_1_) {
        GlStateManager.HorizonCode_Horizon_È(2982, ActiveRenderInfo.Â);
        GlStateManager.HorizonCode_Horizon_È(2983, ActiveRenderInfo.Ý);
        GL11.glGetInteger(2978, ActiveRenderInfo.HorizonCode_Horizon_È);
        final float var2 = (ActiveRenderInfo.HorizonCode_Horizon_È.get(0) + ActiveRenderInfo.HorizonCode_Horizon_È.get(2)) / 2;
        final float var3 = (ActiveRenderInfo.HorizonCode_Horizon_È.get(1) + ActiveRenderInfo.HorizonCode_Horizon_È.get(3)) / 2;
        GLU.gluUnProject(var2, var3, 0.0f, ActiveRenderInfo.Â, ActiveRenderInfo.Ý, ActiveRenderInfo.HorizonCode_Horizon_È, ActiveRenderInfo.Ø­áŒŠá);
        ActiveRenderInfo.Âµá€ = new Vec3(ActiveRenderInfo.Ø­áŒŠá.get(0), ActiveRenderInfo.Ø­áŒŠá.get(1), ActiveRenderInfo.Ø­áŒŠá.get(2));
        final int var4 = p_74583_1_ ? 1 : 0;
        final float var5 = p_74583_0_.áƒ;
        final float var6 = p_74583_0_.É;
        ActiveRenderInfo.Ó = MathHelper.Â(var6 * 3.1415927f / 180.0f) * (1 - var4 * 2);
        ActiveRenderInfo.Ø = MathHelper.HorizonCode_Horizon_È(var6 * 3.1415927f / 180.0f) * (1 - var4 * 2);
        ActiveRenderInfo.áŒŠÆ = -ActiveRenderInfo.Ø * MathHelper.HorizonCode_Horizon_È(var5 * 3.1415927f / 180.0f) * (1 - var4 * 2);
        ActiveRenderInfo.áˆºÑ¢Õ = ActiveRenderInfo.Ó * MathHelper.HorizonCode_Horizon_È(var5 * 3.1415927f / 180.0f) * (1 - var4 * 2);
        ActiveRenderInfo.à = MathHelper.Â(var5 * 3.1415927f / 180.0f);
    }
    
    public static Vec3 HorizonCode_Horizon_È(final Entity p_178806_0_, final double p_178806_1_) {
        final double var3 = p_178806_0_.áŒŠà + (p_178806_0_.ŒÏ - p_178806_0_.áŒŠà) * p_178806_1_;
        final double var4 = p_178806_0_.ŠÄ + (p_178806_0_.Çªà¢ - p_178806_0_.ŠÄ) * p_178806_1_;
        final double var5 = p_178806_0_.Ñ¢á + (p_178806_0_.Ê - p_178806_0_.Ñ¢á) * p_178806_1_;
        final double var6 = var3 + ActiveRenderInfo.Âµá€.HorizonCode_Horizon_È;
        final double var7 = var4 + ActiveRenderInfo.Âµá€.Â;
        final double var8 = var5 + ActiveRenderInfo.Âµá€.Ý;
        return new Vec3(var6, var7, var8);
    }
    
    public static Block HorizonCode_Horizon_È(final World worldIn, final Entity p_180786_1_, final float p_180786_2_) {
        final Vec3 var3 = HorizonCode_Horizon_È(p_180786_1_, p_180786_2_);
        final BlockPos var4 = new BlockPos(var3);
        final IBlockState var5 = worldIn.Â(var4);
        Block var6 = var5.Ý();
        if (var6.Ó().HorizonCode_Horizon_È()) {
            float var7 = 0.0f;
            if (var5.Ý() instanceof BlockLiquid) {
                var7 = BlockLiquid.Âµá€((int)var5.HorizonCode_Horizon_È(BlockLiquid.à¢)) - 0.11111111f;
            }
            final float var8 = var4.Â() + 1 - var7;
            if (var3.Â >= var8) {
                var6 = worldIn.Â(var4.Ø­áŒŠá()).Ý();
            }
        }
        return var6;
    }
    
    public static Vec3 HorizonCode_Horizon_È() {
        return ActiveRenderInfo.Âµá€;
    }
    
    public static float Â() {
        return ActiveRenderInfo.Ó;
    }
    
    public static float Ý() {
        return ActiveRenderInfo.à;
    }
    
    public static float Ø­áŒŠá() {
        return ActiveRenderInfo.Ø;
    }
    
    public static float Âµá€() {
        return ActiveRenderInfo.áŒŠÆ;
    }
    
    public static float Ó() {
        return ActiveRenderInfo.áˆºÑ¢Õ;
    }
}
