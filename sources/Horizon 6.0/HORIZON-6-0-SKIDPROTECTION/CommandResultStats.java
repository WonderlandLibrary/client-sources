package HORIZON-6-0-SKIDPROTECTION;

public class CommandResultStats
{
    private static final int HorizonCode_Horizon_È;
    private static final String[] Â;
    private String[] Ý;
    private String[] Ø­áŒŠá;
    private static final String Âµá€ = "CL_00002364";
    
    static {
        HorizonCode_Horizon_È = CommandResultStats.HorizonCode_Horizon_È.values().length;
        Â = new String[CommandResultStats.HorizonCode_Horizon_È];
    }
    
    public CommandResultStats() {
        this.Ý = CommandResultStats.Â;
        this.Ø­áŒŠá = CommandResultStats.Â;
    }
    
    public void HorizonCode_Horizon_È(final ICommandSender p_179672_1_, final HorizonCode_Horizon_È p_179672_2_, final int p_179672_3_) {
        final String var4 = this.Ý[p_179672_2_.HorizonCode_Horizon_È()];
        if (var4 != null) {
            String var5;
            try {
                var5 = CommandBase.Âµá€(p_179672_1_, var4);
            }
            catch (EntityNotFoundException var10) {
                return;
            }
            final String var6 = this.Ø­áŒŠá[p_179672_2_.HorizonCode_Horizon_È()];
            if (var6 != null) {
                final Scoreboard var7 = p_179672_1_.k_().à¢();
                final ScoreObjective var8 = var7.HorizonCode_Horizon_È(var6);
                if (var8 != null && var7.HorizonCode_Horizon_È(var5, var8)) {
                    final Score var9 = var7.Â(var5, var8);
                    var9.Ý(p_179672_3_);
                }
            }
        }
    }
    
    public void HorizonCode_Horizon_È(final NBTTagCompound p_179668_1_) {
        if (p_179668_1_.Â("CommandStats", 10)) {
            final NBTTagCompound var2 = p_179668_1_.ˆÏ­("CommandStats");
            for (final HorizonCode_Horizon_È var6 : CommandResultStats.HorizonCode_Horizon_È.values()) {
                final String var7 = String.valueOf(var6.Â()) + "Name";
                final String var8 = String.valueOf(var6.Â()) + "Objective";
                if (var2.Â(var7, 8) && var2.Â(var8, 8)) {
                    final String var9 = var2.áˆºÑ¢Õ(var7);
                    final String var10 = var2.áˆºÑ¢Õ(var8);
                    HorizonCode_Horizon_È(this, var6, var9, var10);
                }
            }
        }
    }
    
    public void Â(final NBTTagCompound p_179670_1_) {
        final NBTTagCompound var2 = new NBTTagCompound();
        for (final HorizonCode_Horizon_È var6 : CommandResultStats.HorizonCode_Horizon_È.values()) {
            final String var7 = this.Ý[var6.HorizonCode_Horizon_È()];
            final String var8 = this.Ø­áŒŠá[var6.HorizonCode_Horizon_È()];
            if (var7 != null && var8 != null) {
                var2.HorizonCode_Horizon_È(String.valueOf(var6.Â()) + "Name", var7);
                var2.HorizonCode_Horizon_È(String.valueOf(var6.Â()) + "Objective", var8);
            }
        }
        if (!var2.Ý()) {
            p_179670_1_.HorizonCode_Horizon_È("CommandStats", var2);
        }
    }
    
    public static void HorizonCode_Horizon_È(final CommandResultStats p_179667_0_, final HorizonCode_Horizon_È p_179667_1_, final String p_179667_2_, final String p_179667_3_) {
        if (p_179667_2_ != null && p_179667_2_.length() != 0 && p_179667_3_ != null && p_179667_3_.length() != 0) {
            if (p_179667_0_.Ý == CommandResultStats.Â || p_179667_0_.Ø­áŒŠá == CommandResultStats.Â) {
                p_179667_0_.Ý = new String[CommandResultStats.HorizonCode_Horizon_È];
                p_179667_0_.Ø­áŒŠá = new String[CommandResultStats.HorizonCode_Horizon_È];
            }
            p_179667_0_.Ý[p_179667_1_.HorizonCode_Horizon_È()] = p_179667_2_;
            p_179667_0_.Ø­áŒŠá[p_179667_1_.HorizonCode_Horizon_È()] = p_179667_3_;
        }
        else {
            HorizonCode_Horizon_È(p_179667_0_, p_179667_1_);
        }
    }
    
    private static void HorizonCode_Horizon_È(final CommandResultStats p_179669_0_, final HorizonCode_Horizon_È p_179669_1_) {
        if (p_179669_0_.Ý != CommandResultStats.Â && p_179669_0_.Ø­áŒŠá != CommandResultStats.Â) {
            p_179669_0_.Ý[p_179669_1_.HorizonCode_Horizon_È()] = null;
            p_179669_0_.Ø­áŒŠá[p_179669_1_.HorizonCode_Horizon_È()] = null;
            boolean var2 = true;
            for (final HorizonCode_Horizon_È var6 : CommandResultStats.HorizonCode_Horizon_È.values()) {
                if (p_179669_0_.Ý[var6.HorizonCode_Horizon_È()] != null && p_179669_0_.Ø­áŒŠá[var6.HorizonCode_Horizon_È()] != null) {
                    var2 = false;
                    break;
                }
            }
            if (var2) {
                p_179669_0_.Ý = CommandResultStats.Â;
                p_179669_0_.Ø­áŒŠá = CommandResultStats.Â;
            }
        }
    }
    
    public void HorizonCode_Horizon_È(final CommandResultStats p_179671_1_) {
        for (final HorizonCode_Horizon_È var5 : CommandResultStats.HorizonCode_Horizon_È.values()) {
            HorizonCode_Horizon_È(this, var5, p_179671_1_.Ý[var5.HorizonCode_Horizon_È()], p_179671_1_.Ø­áŒŠá[var5.HorizonCode_Horizon_È()]);
        }
    }
    
    public enum HorizonCode_Horizon_È
    {
        HorizonCode_Horizon_È("SUCCESS_COUNT", 0, "SUCCESS_COUNT", 0, 0, "SuccessCount"), 
        Â("AFFECTED_BLOCKS", 1, "AFFECTED_BLOCKS", 1, 1, "AffectedBlocks"), 
        Ý("AFFECTED_ENTITIES", 2, "AFFECTED_ENTITIES", 2, 2, "AffectedEntities"), 
        Ø­áŒŠá("AFFECTED_ITEMS", 3, "AFFECTED_ITEMS", 3, 3, "AffectedItems"), 
        Âµá€("QUERY_RESULT", 4, "QUERY_RESULT", 4, 4, "QueryResult");
        
        final int Ó;
        final String à;
        private static final HorizonCode_Horizon_È[] Ø;
        private static final String áŒŠÆ = "CL_00002363";
        
        static {
            áˆºÑ¢Õ = new HorizonCode_Horizon_È[] { HorizonCode_Horizon_È.HorizonCode_Horizon_È, HorizonCode_Horizon_È.Â, HorizonCode_Horizon_È.Ý, HorizonCode_Horizon_È.Ø­áŒŠá, HorizonCode_Horizon_È.Âµá€ };
            Ø = new HorizonCode_Horizon_È[] { HorizonCode_Horizon_È.HorizonCode_Horizon_È, HorizonCode_Horizon_È.Â, HorizonCode_Horizon_È.Ý, HorizonCode_Horizon_È.Ø­áŒŠá, HorizonCode_Horizon_È.Âµá€ };
        }
        
        private HorizonCode_Horizon_È(final String s, final int n, final String p_i46050_1_, final int p_i46050_2_, final int p_i46050_3_, final String p_i46050_4_) {
            this.Ó = p_i46050_3_;
            this.à = p_i46050_4_;
        }
        
        public int HorizonCode_Horizon_È() {
            return this.Ó;
        }
        
        public String Â() {
            return this.à;
        }
        
        public static String[] Ý() {
            final String[] var0 = new String[values().length];
            int var2 = 0;
            for (final HorizonCode_Horizon_È var6 : values()) {
                var0[var2++] = var6.Â();
            }
            return var0;
        }
        
        public static HorizonCode_Horizon_È HorizonCode_Horizon_È(final String p_179635_0_) {
            for (final HorizonCode_Horizon_È var4 : values()) {
                if (var4.Â().equals(p_179635_0_)) {
                    return var4;
                }
            }
            return null;
        }
    }
}
