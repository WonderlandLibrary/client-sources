package HORIZON-6-0-SKIDPROTECTION;

import java.util.Iterator;

public class ChatComponentScore extends ChatComponentStyle
{
    private final String Â;
    private final String Ý;
    private String Ø­áŒŠá;
    private static final String Âµá€ = "CL_00002309";
    
    public ChatComponentScore(final String p_i45997_1_, final String p_i45997_2_) {
        this.Ø­áŒŠá = "";
        this.Â = p_i45997_1_;
        this.Ý = p_i45997_2_;
    }
    
    public String HorizonCode_Horizon_È() {
        return this.Â;
    }
    
    public String Â() {
        return this.Ý;
    }
    
    public void HorizonCode_Horizon_È(final String p_179997_1_) {
        this.Ø­áŒŠá = p_179997_1_;
    }
    
    @Override
    public String Ý() {
        final MinecraftServer var1 = MinecraftServer.áƒ();
        if (var1 != null && var1.á€() && StringUtils.Â(this.Ø­áŒŠá)) {
            final Scoreboard var2 = var1.HorizonCode_Horizon_È(0).à¢();
            final ScoreObjective var3 = var2.HorizonCode_Horizon_È(this.Ý);
            if (var2.HorizonCode_Horizon_È(this.Â, var3)) {
                final Score var4 = var2.Â(this.Â, var3);
                this.HorizonCode_Horizon_È(String.format("%d", var4.Â()));
            }
            else {
                this.Ø­áŒŠá = "";
            }
        }
        return this.Ø­áŒŠá;
    }
    
    public ChatComponentScore Ø­áŒŠá() {
        final ChatComponentScore var1 = new ChatComponentScore(this.Â, this.Ý);
        var1.HorizonCode_Horizon_È(this.Ø­áŒŠá);
        var1.HorizonCode_Horizon_È(this.à().á());
        for (final IChatComponent var3 : this.Ó()) {
            var1.HorizonCode_Horizon_È(var3.Âµá€());
        }
        return var1;
    }
    
    @Override
    public boolean equals(final Object p_equals_1_) {
        if (this == p_equals_1_) {
            return true;
        }
        if (!(p_equals_1_ instanceof ChatComponentScore)) {
            return false;
        }
        final ChatComponentScore var2 = (ChatComponentScore)p_equals_1_;
        return this.Â.equals(var2.Â) && this.Ý.equals(var2.Ý) && super.equals(p_equals_1_);
    }
    
    @Override
    public String toString() {
        return "ScoreComponent{name='" + this.Â + '\'' + "objective='" + this.Ý + '\'' + ", siblings=" + this.HorizonCode_Horizon_È + ", style=" + this.à() + '}';
    }
    
    @Override
    public IChatComponent Âµá€() {
        return this.Ø­áŒŠá();
    }
}
