package HORIZON-6-0-SKIDPROTECTION;

import com.google.common.collect.Maps;
import java.util.Map;

public class ClickEvent
{
    private final HorizonCode_Horizon_È HorizonCode_Horizon_È;
    private final String Â;
    private static final String Ý = "CL_00001260";
    
    public ClickEvent(final HorizonCode_Horizon_È p_i45156_1_, final String p_i45156_2_) {
        this.HorizonCode_Horizon_È = p_i45156_1_;
        this.Â = p_i45156_2_;
    }
    
    public HorizonCode_Horizon_È HorizonCode_Horizon_È() {
        return this.HorizonCode_Horizon_È;
    }
    
    public String Â() {
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
        final ClickEvent var2 = (ClickEvent)p_equals_1_;
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
        return "ClickEvent{action=" + this.HorizonCode_Horizon_È + ", value='" + this.Â + '\'' + '}';
    }
    
    @Override
    public int hashCode() {
        int var1 = this.HorizonCode_Horizon_È.hashCode();
        var1 = 31 * var1 + ((this.Â != null) ? this.Â.hashCode() : 0);
        return var1;
    }
    
    public enum HorizonCode_Horizon_È
    {
        HorizonCode_Horizon_È("OPEN_URL", 0, "OPEN_URL", 0, "open_url", true), 
        Â("OPEN_FILE", 1, "OPEN_FILE", 1, "open_file", false), 
        Ý("RUN_COMMAND", 2, "RUN_COMMAND", 2, "run_command", true), 
        Ø­áŒŠá("TWITCH_USER_INFO", 3, "TWITCH_USER_INFO", 3, "twitch_user_info", false), 
        Âµá€("SUGGEST_COMMAND", 4, "SUGGEST_COMMAND", 4, "suggest_command", true), 
        Ó("CHANGE_PAGE", 5, "CHANGE_PAGE", 5, "change_page", true);
        
        private static final Map à;
        private final boolean Ø;
        private final String áŒŠÆ;
        private static final HorizonCode_Horizon_È[] áˆºÑ¢Õ;
        private static final String ÂµÈ = "CL_00001261";
        
        static {
            á = new HorizonCode_Horizon_È[] { HorizonCode_Horizon_È.HorizonCode_Horizon_È, HorizonCode_Horizon_È.Â, HorizonCode_Horizon_È.Ý, HorizonCode_Horizon_È.Ø­áŒŠá, HorizonCode_Horizon_È.Âµá€, HorizonCode_Horizon_È.Ó };
            à = Maps.newHashMap();
            áˆºÑ¢Õ = new HorizonCode_Horizon_È[] { HorizonCode_Horizon_È.HorizonCode_Horizon_È, HorizonCode_Horizon_È.Â, HorizonCode_Horizon_È.Ý, HorizonCode_Horizon_È.Ø­áŒŠá, HorizonCode_Horizon_È.Âµá€, HorizonCode_Horizon_È.Ó };
            for (final HorizonCode_Horizon_È var4 : values()) {
                HorizonCode_Horizon_È.à.put(var4.Â(), var4);
            }
        }
        
        private HorizonCode_Horizon_È(final String s, final int n, final String p_i45155_1_, final int p_i45155_2_, final String p_i45155_3_, final boolean p_i45155_4_) {
            this.áŒŠÆ = p_i45155_3_;
            this.Ø = p_i45155_4_;
        }
        
        public boolean HorizonCode_Horizon_È() {
            return this.Ø;
        }
        
        public String Â() {
            return this.áŒŠÆ;
        }
        
        public static HorizonCode_Horizon_È HorizonCode_Horizon_È(final String p_150672_0_) {
            return HorizonCode_Horizon_È.à.get(p_150672_0_);
        }
    }
}
