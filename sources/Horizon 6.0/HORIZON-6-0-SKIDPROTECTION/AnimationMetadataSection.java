package HORIZON-6-0-SKIDPROTECTION;

import java.util.Iterator;
import java.util.HashSet;
import com.google.common.collect.Sets;
import java.util.Set;
import java.util.List;

public class AnimationMetadataSection implements IMetadataSection
{
    private final List HorizonCode_Horizon_È;
    private final int Â;
    private final int Ý;
    private final int Ø­áŒŠá;
    private final boolean Âµá€;
    private static final String Ó = "CL_00001106";
    
    public AnimationMetadataSection(final List p_i46088_1_, final int p_i46088_2_, final int p_i46088_3_, final int p_i46088_4_, final boolean p_i46088_5_) {
        this.HorizonCode_Horizon_È = p_i46088_1_;
        this.Â = p_i46088_2_;
        this.Ý = p_i46088_3_;
        this.Ø­áŒŠá = p_i46088_4_;
        this.Âµá€ = p_i46088_5_;
    }
    
    public int HorizonCode_Horizon_È() {
        return this.Ý;
    }
    
    public int Â() {
        return this.Â;
    }
    
    public int Ý() {
        return this.HorizonCode_Horizon_È.size();
    }
    
    public int Ø­áŒŠá() {
        return this.Ø­áŒŠá;
    }
    
    public boolean Âµá€() {
        return this.Âµá€;
    }
    
    private AnimationFrame Ø­áŒŠá(final int p_130072_1_) {
        return this.HorizonCode_Horizon_È.get(p_130072_1_);
    }
    
    public int HorizonCode_Horizon_È(final int p_110472_1_) {
        final AnimationFrame var2 = this.Ø­áŒŠá(p_110472_1_);
        return var2.HorizonCode_Horizon_È() ? this.Ø­áŒŠá : var2.Â();
    }
    
    public boolean Â(final int p_110470_1_) {
        return !this.HorizonCode_Horizon_È.get(p_110470_1_).HorizonCode_Horizon_È();
    }
    
    public int Ý(final int p_110468_1_) {
        return this.HorizonCode_Horizon_È.get(p_110468_1_).Ý();
    }
    
    public Set Ó() {
        final HashSet var1 = Sets.newHashSet();
        for (final AnimationFrame var3 : this.HorizonCode_Horizon_È) {
            var1.add(var3.Ý());
        }
        return var1;
    }
}
