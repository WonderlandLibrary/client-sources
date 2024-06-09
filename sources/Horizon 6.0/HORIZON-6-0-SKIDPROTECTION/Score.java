package HORIZON-6-0-SKIDPROTECTION;

import java.util.List;
import java.util.Comparator;

public class Score
{
    public static final Comparator HorizonCode_Horizon_È;
    private final Scoreboard Â;
    private final ScoreObjective Ý;
    private final String Ø­áŒŠá;
    private int Âµá€;
    private boolean Ó;
    private boolean à;
    private static final String Ø = "CL_00000617";
    
    static {
        HorizonCode_Horizon_È = new Comparator() {
            private static final String HorizonCode_Horizon_È = "CL_00000618";
            
            public int HorizonCode_Horizon_È(final Score p_compare_1_, final Score p_compare_2_) {
                return (p_compare_1_.Â() > p_compare_2_.Â()) ? 1 : ((p_compare_1_.Â() < p_compare_2_.Â()) ? -1 : p_compare_2_.Ø­áŒŠá().compareToIgnoreCase(p_compare_1_.Ø­áŒŠá()));
            }
            
            @Override
            public int compare(final Object p_compare_1_, final Object p_compare_2_) {
                return this.HorizonCode_Horizon_È((Score)p_compare_1_, (Score)p_compare_2_);
            }
        };
    }
    
    public Score(final Scoreboard p_i2309_1_, final ScoreObjective p_i2309_2_, final String p_i2309_3_) {
        this.Â = p_i2309_1_;
        this.Ý = p_i2309_2_;
        this.Ø­áŒŠá = p_i2309_3_;
        this.à = true;
    }
    
    public void HorizonCode_Horizon_È(final int p_96649_1_) {
        if (this.Ý.Ý().Â()) {
            throw new IllegalStateException("Cannot modify read-only score");
        }
        this.Ý(this.Â() + p_96649_1_);
    }
    
    public void Â(final int p_96646_1_) {
        if (this.Ý.Ý().Â()) {
            throw new IllegalStateException("Cannot modify read-only score");
        }
        this.Ý(this.Â() - p_96646_1_);
    }
    
    public void HorizonCode_Horizon_È() {
        if (this.Ý.Ý().Â()) {
            throw new IllegalStateException("Cannot modify read-only score");
        }
        this.HorizonCode_Horizon_È(1);
    }
    
    public int Â() {
        return this.Âµá€;
    }
    
    public void Ý(final int p_96647_1_) {
        final int var2 = this.Âµá€;
        this.Âµá€ = p_96647_1_;
        if (var2 != p_96647_1_ || this.à) {
            this.à = false;
            this.Âµá€().HorizonCode_Horizon_È(this);
        }
    }
    
    public ScoreObjective Ý() {
        return this.Ý;
    }
    
    public String Ø­áŒŠá() {
        return this.Ø­áŒŠá;
    }
    
    public Scoreboard Âµá€() {
        return this.Â;
    }
    
    public boolean Ó() {
        return this.Ó;
    }
    
    public void HorizonCode_Horizon_È(final boolean p_178815_1_) {
        this.Ó = p_178815_1_;
    }
    
    public void HorizonCode_Horizon_È(final List p_96651_1_) {
        this.Ý(this.Ý.Ý().HorizonCode_Horizon_È(p_96651_1_));
    }
}
