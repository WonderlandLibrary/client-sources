package HORIZON-6-0-SKIDPROTECTION;

import com.google.common.collect.Maps;
import java.util.Map;
import java.util.Collection;

public abstract class Team
{
    private static final String HorizonCode_Horizon_È = "CL_00000621";
    
    public boolean HorizonCode_Horizon_È(final Team other) {
        return other != null && this == other;
    }
    
    public abstract String HorizonCode_Horizon_È();
    
    public abstract String Ø­áŒŠá(final String p0);
    
    public abstract boolean à();
    
    public abstract boolean Ó();
    
    public abstract HorizonCode_Horizon_È Ø();
    
    public abstract Collection Ý();
    
    public abstract HorizonCode_Horizon_È áŒŠÆ();
    
    public enum HorizonCode_Horizon_È
    {
        HorizonCode_Horizon_È("ALWAYS", 0, "ALWAYS", 0, "always", 0), 
        Â("NEVER", 1, "NEVER", 1, "never", 1), 
        Ý("HIDE_FOR_OTHER_TEAMS", 2, "HIDE_FOR_OTHER_TEAMS", 2, "hideForOtherTeams", 2), 
        Ø­áŒŠá("HIDE_FOR_OWN_TEAM", 3, "HIDE_FOR_OWN_TEAM", 3, "hideForOwnTeam", 3);
        
        private static Map à;
        public final String Âµá€;
        public final int Ó;
        private static final HorizonCode_Horizon_È[] Ø;
        private static final String áŒŠÆ = "CL_00001962";
        
        static {
            áˆºÑ¢Õ = new HorizonCode_Horizon_È[] { HorizonCode_Horizon_È.HorizonCode_Horizon_È, HorizonCode_Horizon_È.Â, HorizonCode_Horizon_È.Ý, HorizonCode_Horizon_È.Ø­áŒŠá };
            HorizonCode_Horizon_È.à = Maps.newHashMap();
            Ø = new HorizonCode_Horizon_È[] { HorizonCode_Horizon_È.HorizonCode_Horizon_È, HorizonCode_Horizon_È.Â, HorizonCode_Horizon_È.Ý, HorizonCode_Horizon_È.Ø­áŒŠá };
            for (final HorizonCode_Horizon_È var4 : values()) {
                HorizonCode_Horizon_È.à.put(var4.Âµá€, var4);
            }
        }
        
        public static String[] HorizonCode_Horizon_È() {
            return (String[])HorizonCode_Horizon_È.à.keySet().toArray(new String[HorizonCode_Horizon_È.à.size()]);
        }
        
        public static HorizonCode_Horizon_È HorizonCode_Horizon_È(final String p_178824_0_) {
            return HorizonCode_Horizon_È.à.get(p_178824_0_);
        }
        
        private HorizonCode_Horizon_È(final String s, final int n, final String p_i45550_1_, final int p_i45550_2_, final String p_i45550_3_, final int p_i45550_4_) {
            this.Âµá€ = p_i45550_3_;
            this.Ó = p_i45550_4_;
        }
    }
}
