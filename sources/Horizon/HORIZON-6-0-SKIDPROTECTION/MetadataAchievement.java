package HORIZON-6-0-SKIDPROTECTION;

public class MetadataAchievement extends Metadata
{
    private static final String HorizonCode_Horizon_È = "CL_00001824";
    
    public MetadataAchievement(final Achievement p_i1032_1_) {
        super("achievement");
        this.HorizonCode_Horizon_È("achievement_id", p_i1032_1_.à);
        this.HorizonCode_Horizon_È("achievement_name", p_i1032_1_.Âµá€().Ø());
        this.HorizonCode_Horizon_È("achievement_description", p_i1032_1_.Ó());
        this.HorizonCode_Horizon_È("Achievement '" + p_i1032_1_.Âµá€().Ø() + "' obtained!");
    }
}
