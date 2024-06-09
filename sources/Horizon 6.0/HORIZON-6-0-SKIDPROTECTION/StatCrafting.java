package HORIZON-6-0-SKIDPROTECTION;

public class StatCrafting extends StatBase
{
    private final Item_1028566121 HorizonCode_Horizon_È;
    private static final String Â = "CL_00001470";
    
    public StatCrafting(final String p_i45910_1_, final String p_i45910_2_, final IChatComponent p_i45910_3_, final Item_1028566121 p_i45910_4_) {
        super(String.valueOf(p_i45910_1_) + p_i45910_2_, p_i45910_3_);
        this.HorizonCode_Horizon_È = p_i45910_4_;
        final int var5 = Item_1028566121.HorizonCode_Horizon_È(p_i45910_4_);
        if (var5 != 0) {
            IScoreObjectiveCriteria.HorizonCode_Horizon_È.put(String.valueOf(p_i45910_1_) + var5, this.ÂµÈ());
        }
    }
    
    public Item_1028566121 HorizonCode_Horizon_È() {
        return this.HorizonCode_Horizon_È;
    }
}
