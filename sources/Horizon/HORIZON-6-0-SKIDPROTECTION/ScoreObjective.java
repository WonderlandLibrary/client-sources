package HORIZON-6-0-SKIDPROTECTION;

public class ScoreObjective
{
    private final Scoreboard HorizonCode_Horizon_È;
    private final String Â;
    private final IScoreObjectiveCriteria Ý;
    private IScoreObjectiveCriteria.HorizonCode_Horizon_È Ø­áŒŠá;
    private String Âµá€;
    private static final String Ó = "CL_00000614";
    
    public ScoreObjective(final Scoreboard p_i2307_1_, final String p_i2307_2_, final IScoreObjectiveCriteria p_i2307_3_) {
        this.HorizonCode_Horizon_È = p_i2307_1_;
        this.Â = p_i2307_2_;
        this.Ý = p_i2307_3_;
        this.Âµá€ = p_i2307_2_;
        this.Ø­áŒŠá = p_i2307_3_.Ý();
    }
    
    public Scoreboard HorizonCode_Horizon_È() {
        return this.HorizonCode_Horizon_È;
    }
    
    public String Â() {
        return this.Â;
    }
    
    public IScoreObjectiveCriteria Ý() {
        return this.Ý;
    }
    
    public String Ø­áŒŠá() {
        return this.Âµá€;
    }
    
    public void HorizonCode_Horizon_È(final String p_96681_1_) {
        this.Âµá€ = p_96681_1_;
        this.HorizonCode_Horizon_È.Ø­áŒŠá(this);
    }
    
    public IScoreObjectiveCriteria.HorizonCode_Horizon_È Âµá€() {
        return this.Ø­áŒŠá;
    }
    
    public void HorizonCode_Horizon_È(final IScoreObjectiveCriteria.HorizonCode_Horizon_È p_178767_1_) {
        this.Ø­áŒŠá = p_178767_1_;
        this.HorizonCode_Horizon_È.Ø­áŒŠá(this);
    }
}
