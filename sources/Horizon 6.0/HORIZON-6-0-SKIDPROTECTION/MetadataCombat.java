package HORIZON-6-0-SKIDPROTECTION;

public class MetadataCombat extends Metadata
{
    private static final String HorizonCode_Horizon_È = "CL_00002377";
    
    public MetadataCombat(final EntityLivingBase p_i46067_1_, final EntityLivingBase p_i46067_2_) {
        super("player_combat");
        this.HorizonCode_Horizon_È("player", p_i46067_1_.v_());
        if (p_i46067_2_ != null) {
            this.HorizonCode_Horizon_È("primary_opponent", p_i46067_2_.v_());
        }
        if (p_i46067_2_ != null) {
            this.HorizonCode_Horizon_È("Combat between " + p_i46067_1_.v_() + " and " + p_i46067_2_.v_());
        }
        else {
            this.HorizonCode_Horizon_È("Combat between " + p_i46067_1_.v_() + " and others");
        }
    }
}
