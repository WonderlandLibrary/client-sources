package HORIZON-6-0-SKIDPROTECTION;

import javax.vecmath.AxisAngle4d;
import com.google.common.collect.Maps;
import javax.vecmath.Matrix4d;
import java.util.Map;

public enum ModelRotation
{
    HorizonCode_Horizon_È("X0_Y0", 0, "X0_Y0", 0, 0, 0), 
    Â("X0_Y90", 1, "X0_Y90", 1, 0, 90), 
    Ý("X0_Y180", 2, "X0_Y180", 2, 0, 180), 
    Ø­áŒŠá("X0_Y270", 3, "X0_Y270", 3, 0, 270), 
    Âµá€("X90_Y0", 4, "X90_Y0", 4, 90, 0), 
    Ó("X90_Y90", 5, "X90_Y90", 5, 90, 90), 
    à("X90_Y180", 6, "X90_Y180", 6, 90, 180), 
    Ø("X90_Y270", 7, "X90_Y270", 7, 90, 270), 
    áŒŠÆ("X180_Y0", 8, "X180_Y0", 8, 180, 0), 
    áˆºÑ¢Õ("X180_Y90", 9, "X180_Y90", 9, 180, 90), 
    ÂµÈ("X180_Y180", 10, "X180_Y180", 10, 180, 180), 
    á("X180_Y270", 11, "X180_Y270", 11, 180, 270), 
    ˆÏ­("X270_Y0", 12, "X270_Y0", 12, 270, 0), 
    £á("X270_Y90", 13, "X270_Y90", 13, 270, 90), 
    Å("X270_Y180", 14, "X270_Y180", 14, 270, 180), 
    £à("X270_Y270", 15, "X270_Y270", 15, 270, 270);
    
    private static final Map µà;
    private final int ˆà;
    private final Matrix4d ¥Æ;
    private final int Ø­à;
    private final int µÕ;
    private static final ModelRotation[] Æ;
    private static final String Šáƒ = "CL_00002393";
    
    static {
        Ï­Ðƒà = new ModelRotation[] { ModelRotation.HorizonCode_Horizon_È, ModelRotation.Â, ModelRotation.Ý, ModelRotation.Ø­áŒŠá, ModelRotation.Âµá€, ModelRotation.Ó, ModelRotation.à, ModelRotation.Ø, ModelRotation.áŒŠÆ, ModelRotation.áˆºÑ¢Õ, ModelRotation.ÂµÈ, ModelRotation.á, ModelRotation.ˆÏ­, ModelRotation.£á, ModelRotation.Å, ModelRotation.£à };
        µà = Maps.newHashMap();
        Æ = new ModelRotation[] { ModelRotation.HorizonCode_Horizon_È, ModelRotation.Â, ModelRotation.Ý, ModelRotation.Ø­áŒŠá, ModelRotation.Âµá€, ModelRotation.Ó, ModelRotation.à, ModelRotation.Ø, ModelRotation.áŒŠÆ, ModelRotation.áˆºÑ¢Õ, ModelRotation.ÂµÈ, ModelRotation.á, ModelRotation.ˆÏ­, ModelRotation.£á, ModelRotation.Å, ModelRotation.£à };
        for (final ModelRotation var4 : values()) {
            ModelRotation.µà.put(var4.ˆà, var4);
        }
    }
    
    private static int Â(final int p_177521_0_, final int p_177521_1_) {
        return p_177521_0_ * 360 + p_177521_1_;
    }
    
    private ModelRotation(final String s, final int n, final String p_i46087_1_, final int p_i46087_2_, final int p_i46087_3_, final int p_i46087_4_) {
        this.ˆà = Â(p_i46087_3_, p_i46087_4_);
        this.¥Æ = new Matrix4d();
        final Matrix4d var5 = new Matrix4d();
        var5.setIdentity();
        var5.setRotation(new AxisAngle4d(1.0, 0.0, 0.0, (double)(-p_i46087_3_ * 0.017453292f)));
        this.Ø­à = MathHelper.HorizonCode_Horizon_È(p_i46087_3_ / 90);
        final Matrix4d var6 = new Matrix4d();
        var6.setIdentity();
        var6.setRotation(new AxisAngle4d(0.0, 1.0, 0.0, (double)(-p_i46087_4_ * 0.017453292f)));
        this.µÕ = MathHelper.HorizonCode_Horizon_È(p_i46087_4_ / 90);
        this.¥Æ.mul(var6, var5);
    }
    
    public Matrix4d HorizonCode_Horizon_È() {
        return this.¥Æ;
    }
    
    public EnumFacing HorizonCode_Horizon_È(final EnumFacing p_177523_1_) {
        EnumFacing var2 = p_177523_1_;
        for (int var3 = 0; var3 < this.Ø­à; ++var3) {
            var2 = var2.HorizonCode_Horizon_È(EnumFacing.HorizonCode_Horizon_È.HorizonCode_Horizon_È);
        }
        if (var2.á() != EnumFacing.HorizonCode_Horizon_È.Â) {
            for (int var3 = 0; var3 < this.µÕ; ++var3) {
                var2 = var2.HorizonCode_Horizon_È(EnumFacing.HorizonCode_Horizon_È.Â);
            }
        }
        return var2;
    }
    
    public int HorizonCode_Horizon_È(final EnumFacing p_177520_1_, final int p_177520_2_) {
        int var3 = p_177520_2_;
        if (p_177520_1_.á() == EnumFacing.HorizonCode_Horizon_È.HorizonCode_Horizon_È) {
            var3 = (p_177520_2_ + this.Ø­à) % 4;
        }
        EnumFacing var4 = p_177520_1_;
        for (int var5 = 0; var5 < this.Ø­à; ++var5) {
            var4 = var4.HorizonCode_Horizon_È(EnumFacing.HorizonCode_Horizon_È.HorizonCode_Horizon_È);
        }
        if (var4.á() == EnumFacing.HorizonCode_Horizon_È.Â) {
            var3 = (var3 + this.µÕ) % 4;
        }
        return var3;
    }
    
    public static ModelRotation HorizonCode_Horizon_È(final int p_177524_0_, final int p_177524_1_) {
        return ModelRotation.µà.get(Â(MathHelper.Â(p_177524_0_, 360), MathHelper.Â(p_177524_1_, 360)));
    }
}
