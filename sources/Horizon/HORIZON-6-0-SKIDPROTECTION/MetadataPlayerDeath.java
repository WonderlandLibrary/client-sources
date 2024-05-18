package HORIZON-6-0-SKIDPROTECTION;

public class MetadataPlayerDeath extends Metadata
{
    private static final String HorizonCode_Horizon_È = "CL_00002376";
    
    public MetadataPlayerDeath(final EntityLivingBase p_i46066_1_, final EntityLivingBase p_i46066_2_) {
        super("player_death");
        if (p_i46066_1_ != null) {
            this.HorizonCode_Horizon_È("player", p_i46066_1_.v_());
        }
        if (p_i46066_2_ != null) {
            this.HorizonCode_Horizon_È("killer", p_i46066_2_.v_());
        }
    }
}
