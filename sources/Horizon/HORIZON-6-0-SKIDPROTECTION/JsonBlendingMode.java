package HORIZON-6-0-SKIDPROTECTION;

import com.google.gson.JsonObject;
import org.lwjgl.opengl.GL14;

public class JsonBlendingMode
{
    private static JsonBlendingMode HorizonCode_Horizon_È;
    private final int Â;
    private final int Ý;
    private final int Ø­áŒŠá;
    private final int Âµá€;
    private final int Ó;
    private final boolean à;
    private final boolean Ø;
    private static final String áŒŠÆ = "CL_00001038";
    
    static {
        JsonBlendingMode.HorizonCode_Horizon_È = null;
    }
    
    private JsonBlendingMode(final boolean p_i45084_1_, final boolean p_i45084_2_, final int p_i45084_3_, final int p_i45084_4_, final int p_i45084_5_, final int p_i45084_6_, final int p_i45084_7_) {
        this.à = p_i45084_1_;
        this.Â = p_i45084_3_;
        this.Ø­áŒŠá = p_i45084_4_;
        this.Ý = p_i45084_5_;
        this.Âµá€ = p_i45084_6_;
        this.Ø = p_i45084_2_;
        this.Ó = p_i45084_7_;
    }
    
    public JsonBlendingMode() {
        this(false, true, 1, 0, 1, 0, 32774);
    }
    
    public JsonBlendingMode(final int p_i45085_1_, final int p_i45085_2_, final int p_i45085_3_) {
        this(false, false, p_i45085_1_, p_i45085_2_, p_i45085_1_, p_i45085_2_, p_i45085_3_);
    }
    
    public JsonBlendingMode(final int p_i45086_1_, final int p_i45086_2_, final int p_i45086_3_, final int p_i45086_4_, final int p_i45086_5_) {
        this(true, false, p_i45086_1_, p_i45086_2_, p_i45086_3_, p_i45086_4_, p_i45086_5_);
    }
    
    public void HorizonCode_Horizon_È() {
        if (!this.equals(JsonBlendingMode.HorizonCode_Horizon_È)) {
            if (JsonBlendingMode.HorizonCode_Horizon_È == null || this.Ø != JsonBlendingMode.HorizonCode_Horizon_È.Â()) {
                JsonBlendingMode.HorizonCode_Horizon_È = this;
                if (this.Ø) {
                    GlStateManager.ÂµÈ();
                    return;
                }
                GlStateManager.á();
            }
            GL14.glBlendEquation(this.Ó);
            if (this.à) {
                GlStateManager.HorizonCode_Horizon_È(this.Â, this.Ø­áŒŠá, this.Ý, this.Âµá€);
            }
            else {
                GlStateManager.Â(this.Â, this.Ø­áŒŠá);
            }
        }
    }
    
    @Override
    public boolean equals(final Object p_equals_1_) {
        if (this == p_equals_1_) {
            return true;
        }
        if (!(p_equals_1_ instanceof JsonBlendingMode)) {
            return false;
        }
        final JsonBlendingMode var2 = (JsonBlendingMode)p_equals_1_;
        return this.Ó == var2.Ó && this.Âµá€ == var2.Âµá€ && this.Ø­áŒŠá == var2.Ø­áŒŠá && this.Ø == var2.Ø && this.à == var2.à && this.Ý == var2.Ý && this.Â == var2.Â;
    }
    
    @Override
    public int hashCode() {
        int var1 = this.Â;
        var1 = 31 * var1 + this.Ý;
        var1 = 31 * var1 + this.Ø­áŒŠá;
        var1 = 31 * var1 + this.Âµá€;
        var1 = 31 * var1 + this.Ó;
        var1 = 31 * var1 + (this.à ? 1 : 0);
        var1 = 31 * var1 + (this.Ø ? 1 : 0);
        return var1;
    }
    
    public boolean Â() {
        return this.Ø;
    }
    
    public static JsonBlendingMode HorizonCode_Horizon_È(final JsonObject p_148110_0_) {
        if (p_148110_0_ == null) {
            return new JsonBlendingMode();
        }
        int var1 = 32774;
        int var2 = 1;
        int var3 = 0;
        int var4 = 1;
        int var5 = 0;
        boolean var6 = true;
        boolean var7 = false;
        if (JsonUtils.HorizonCode_Horizon_È(p_148110_0_, "func")) {
            var1 = HorizonCode_Horizon_È(p_148110_0_.get("func").getAsString());
            if (var1 != 32774) {
                var6 = false;
            }
        }
        if (JsonUtils.HorizonCode_Horizon_È(p_148110_0_, "srcrgb")) {
            var2 = Â(p_148110_0_.get("srcrgb").getAsString());
            if (var2 != 1) {
                var6 = false;
            }
        }
        if (JsonUtils.HorizonCode_Horizon_È(p_148110_0_, "dstrgb")) {
            var3 = Â(p_148110_0_.get("dstrgb").getAsString());
            if (var3 != 0) {
                var6 = false;
            }
        }
        if (JsonUtils.HorizonCode_Horizon_È(p_148110_0_, "srcalpha")) {
            var4 = Â(p_148110_0_.get("srcalpha").getAsString());
            if (var4 != 1) {
                var6 = false;
            }
            var7 = true;
        }
        if (JsonUtils.HorizonCode_Horizon_È(p_148110_0_, "dstalpha")) {
            var5 = Â(p_148110_0_.get("dstalpha").getAsString());
            if (var5 != 0) {
                var6 = false;
            }
            var7 = true;
        }
        return var6 ? new JsonBlendingMode() : (var7 ? new JsonBlendingMode(var2, var3, var4, var5, var1) : new JsonBlendingMode(var2, var3, var1));
    }
    
    private static int HorizonCode_Horizon_È(final String p_148108_0_) {
        final String var1 = p_148108_0_.trim().toLowerCase();
        return var1.equals("add") ? 32774 : (var1.equals("subtract") ? 32778 : (var1.equals("reversesubtract") ? 32779 : (var1.equals("reverse_subtract") ? 32779 : (var1.equals("min") ? 32775 : (var1.equals("max") ? 32776 : 32774)))));
    }
    
    private static int Â(final String p_148107_0_) {
        String var1 = p_148107_0_.trim().toLowerCase();
        var1 = var1.replaceAll("_", "");
        var1 = var1.replaceAll("one", "1");
        var1 = var1.replaceAll("zero", "0");
        var1 = var1.replaceAll("minus", "-");
        return var1.equals("0") ? 0 : (var1.equals("1") ? 1 : (var1.equals("srccolor") ? 768 : (var1.equals("1-srccolor") ? 769 : (var1.equals("dstcolor") ? 774 : (var1.equals("1-dstcolor") ? 775 : (var1.equals("srcalpha") ? 770 : (var1.equals("1-srcalpha") ? 771 : (var1.equals("dstalpha") ? 772 : (var1.equals("1-dstalpha") ? 773 : -1)))))))));
    }
}
