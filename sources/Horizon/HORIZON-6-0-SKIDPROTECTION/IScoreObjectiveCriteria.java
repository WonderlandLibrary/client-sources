package HORIZON-6-0-SKIDPROTECTION;

import java.util.List;
import com.google.common.collect.Maps;
import java.util.Map;

public interface IScoreObjectiveCriteria
{
    public static final Map HorizonCode_Horizon_È = Maps.newHashMap();
    public static final IScoreObjectiveCriteria Â = new ScoreDummyCriteria("dummy");
    public static final IScoreObjectiveCriteria Ý = new ScoreDummyCriteria("trigger");
    public static final IScoreObjectiveCriteria Ø­áŒŠá = new ScoreDummyCriteria("deathCount");
    public static final IScoreObjectiveCriteria Âµá€ = new ScoreDummyCriteria("playerKillCount");
    public static final IScoreObjectiveCriteria Ó = new ScoreDummyCriteria("totalKillCount");
    public static final IScoreObjectiveCriteria à = new ScoreHealthCriteria("health");
    public static final IScoreObjectiveCriteria[] Ø = { new GoalColor("teamkill.", EnumChatFormatting.HorizonCode_Horizon_È), new GoalColor("teamkill.", EnumChatFormatting.Â), new GoalColor("teamkill.", EnumChatFormatting.Ý), new GoalColor("teamkill.", EnumChatFormatting.Ø­áŒŠá), new GoalColor("teamkill.", EnumChatFormatting.Âµá€), new GoalColor("teamkill.", EnumChatFormatting.Ó), new GoalColor("teamkill.", EnumChatFormatting.à), new GoalColor("teamkill.", EnumChatFormatting.Ø), new GoalColor("teamkill.", EnumChatFormatting.áŒŠÆ), new GoalColor("teamkill.", EnumChatFormatting.áˆºÑ¢Õ), new GoalColor("teamkill.", EnumChatFormatting.ÂµÈ), new GoalColor("teamkill.", EnumChatFormatting.á), new GoalColor("teamkill.", EnumChatFormatting.ˆÏ­), new GoalColor("teamkill.", EnumChatFormatting.£á), new GoalColor("teamkill.", EnumChatFormatting.Å), new GoalColor("teamkill.", EnumChatFormatting.£à) };
    public static final IScoreObjectiveCriteria[] áŒŠÆ = { new GoalColor("killedByTeam.", EnumChatFormatting.HorizonCode_Horizon_È), new GoalColor("killedByTeam.", EnumChatFormatting.Â), new GoalColor("killedByTeam.", EnumChatFormatting.Ý), new GoalColor("killedByTeam.", EnumChatFormatting.Ø­áŒŠá), new GoalColor("killedByTeam.", EnumChatFormatting.Âµá€), new GoalColor("killedByTeam.", EnumChatFormatting.Ó), new GoalColor("killedByTeam.", EnumChatFormatting.à), new GoalColor("killedByTeam.", EnumChatFormatting.Ø), new GoalColor("killedByTeam.", EnumChatFormatting.áŒŠÆ), new GoalColor("killedByTeam.", EnumChatFormatting.áˆºÑ¢Õ), new GoalColor("killedByTeam.", EnumChatFormatting.ÂµÈ), new GoalColor("killedByTeam.", EnumChatFormatting.á), new GoalColor("killedByTeam.", EnumChatFormatting.ˆÏ­), new GoalColor("killedByTeam.", EnumChatFormatting.£á), new GoalColor("killedByTeam.", EnumChatFormatting.Å), new GoalColor("killedByTeam.", EnumChatFormatting.£à) };
    
    String HorizonCode_Horizon_È();
    
    int HorizonCode_Horizon_È(final List p0);
    
    boolean Â();
    
    HorizonCode_Horizon_È Ý();
    
    public enum HorizonCode_Horizon_È
    {
        HorizonCode_Horizon_È("INTEGER", 0, "INTEGER", 0, "integer"), 
        Â("HEARTS", 1, "HEARTS", 1, "hearts");
        
        private static final Map Ý;
        private final String Ø­áŒŠá;
        private static final HorizonCode_Horizon_È[] Âµá€;
        private static final String Ó = "CL_00001960";
        
        static {
            à = new HorizonCode_Horizon_È[] { HorizonCode_Horizon_È.HorizonCode_Horizon_È, HorizonCode_Horizon_È.Â };
            Ý = Maps.newHashMap();
            Âµá€ = new HorizonCode_Horizon_È[] { HorizonCode_Horizon_È.HorizonCode_Horizon_È, HorizonCode_Horizon_È.Â };
            for (final HorizonCode_Horizon_È var4 : values()) {
                HorizonCode_Horizon_È.Ý.put(var4.HorizonCode_Horizon_È(), var4);
            }
        }
        
        private HorizonCode_Horizon_È(final String s, final int n, final String p_i45548_1_, final int p_i45548_2_, final String p_i45548_3_) {
            this.Ø­áŒŠá = p_i45548_3_;
        }
        
        public String HorizonCode_Horizon_È() {
            return this.Ø­áŒŠá;
        }
        
        public static HorizonCode_Horizon_È HorizonCode_Horizon_È(final String p_178795_0_) {
            final HorizonCode_Horizon_È var1 = HorizonCode_Horizon_È.Ý.get(p_178795_0_);
            return (var1 == null) ? HorizonCode_Horizon_È.HorizonCode_Horizon_È : var1;
        }
    }
}
