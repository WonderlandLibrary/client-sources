package HORIZON-6-0-SKIDPROTECTION;

public class EntityAILookAtTradePlayer extends EntityAIWatchClosest
{
    private final EntityVillager Âµá€;
    private static final String Ó = "CL_00001593";
    
    public EntityAILookAtTradePlayer(final EntityVillager p_i1633_1_) {
        super(p_i1633_1_, EntityPlayer.class, 8.0f);
        this.Âµá€ = p_i1633_1_;
    }
    
    @Override
    public boolean HorizonCode_Horizon_È() {
        if (this.Âµá€.ÐƒÇŽà()) {
            this.Â = this.Âµá€.HorizonCode_Horizon_È();
            return true;
        }
        return false;
    }
}
