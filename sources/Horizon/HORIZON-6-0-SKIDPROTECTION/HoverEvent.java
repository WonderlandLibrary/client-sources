package HORIZON-6-0-SKIDPROTECTION;

import com.google.common.collect.Maps;
import java.util.Map;

public class HoverEvent
{
    private final HorizonCode_Horizon_È HorizonCode_Horizon_È;
    private final IChatComponent Â;
    private static final String Ý = "CL_00001264";
    
    public HoverEvent(final HorizonCode_Horizon_È p_i45158_1_, final IChatComponent p_i45158_2_) {
        this.HorizonCode_Horizon_È = p_i45158_1_;
        this.Â = p_i45158_2_;
    }
    
    public HorizonCode_Horizon_È HorizonCode_Horizon_È() {
        return this.HorizonCode_Horizon_È;
    }
    
    public IChatComponent Â() {
        return this.Â;
    }
    
    @Override
    public boolean equals(final Object p_equals_1_) {
        if (this == p_equals_1_) {
            return true;
        }
        if (p_equals_1_ == null || this.getClass() != p_equals_1_.getClass()) {
            return false;
        }
        final HoverEvent var2 = (HoverEvent)p_equals_1_;
        if (this.HorizonCode_Horizon_È != var2.HorizonCode_Horizon_È) {
            return false;
        }
        if (this.Â != null) {
            if (!this.Â.equals(var2.Â)) {
                return false;
            }
        }
        else if (var2.Â != null) {
            return false;
        }
        return true;
    }
    
    @Override
    public String toString() {
        return "HoverEvent{action=" + this.HorizonCode_Horizon_È + ", value='" + this.Â + '\'' + '}';
    }
    
    @Override
    public int hashCode() {
        int var1 = this.HorizonCode_Horizon_È.hashCode();
        var1 = 31 * var1 + ((this.Â != null) ? this.Â.hashCode() : 0);
        return var1;
    }
    
    public enum HorizonCode_Horizon_È
    {
        HorizonCode_Horizon_È("SHOW_TEXT", 0, "SHOW_TEXT", 0, "show_text", true), 
        Â("SHOW_ACHIEVEMENT", 1, "SHOW_ACHIEVEMENT", 1, "show_achievement", true), 
        Ý("SHOW_ITEM", 2, "SHOW_ITEM", 2, "show_item", true), 
        Ø­áŒŠá("SHOW_ENTITY", 3, "SHOW_ENTITY", 3, "show_entity", true);
        
        private static final Map Âµá€;
        private final boolean Ó;
        private final String à;
        private static final HorizonCode_Horizon_È[] Ø;
        private static final String áŒŠÆ = "CL_00001265";
        
        static {
            áˆºÑ¢Õ = new HorizonCode_Horizon_È[] { HorizonCode_Horizon_È.HorizonCode_Horizon_È, HorizonCode_Horizon_È.Â, HorizonCode_Horizon_È.Ý, HorizonCode_Horizon_È.Ø­áŒŠá };
            Âµá€ = Maps.newHashMap();
            Ø = new HorizonCode_Horizon_È[] { HorizonCode_Horizon_È.HorizonCode_Horizon_È, HorizonCode_Horizon_È.Â, HorizonCode_Horizon_È.Ý, HorizonCode_Horizon_È.Ø­áŒŠá };
            for (final HorizonCode_Horizon_È var4 : values()) {
                HorizonCode_Horizon_È.Âµá€.put(var4.Â(), var4);
            }
        }
        
        private HorizonCode_Horizon_È(final String s, final int n, final String p_i45157_1_, final int p_i45157_2_, final String p_i45157_3_, final boolean p_i45157_4_) {
            this.à = p_i45157_3_;
            this.Ó = p_i45157_4_;
        }
        
        public boolean HorizonCode_Horizon_È() {
            return this.Ó;
        }
        
        public String Â() {
            return this.à;
        }
        
        public static HorizonCode_Horizon_È HorizonCode_Horizon_È(final String p_150684_0_) {
            return HorizonCode_Horizon_È.Âµá€.get(p_150684_0_);
        }
    }
}
