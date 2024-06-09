package HORIZON-6-0-SKIDPROTECTION;

public class EntityAILookAtVillager extends EntityAIBase
{
    private EntityIronGolem HorizonCode_Horizon_È;
    private EntityVillager Â;
    private int Ý;
    private static final String Ø­áŒŠá = "CL_00001602";
    
    public EntityAILookAtVillager(final EntityIronGolem p_i1643_1_) {
        this.HorizonCode_Horizon_È = p_i1643_1_;
        this.HorizonCode_Horizon_È(3);
    }
    
    @Override
    public boolean HorizonCode_Horizon_È() {
        if (!this.HorizonCode_Horizon_È.Ï­Ðƒà.ÂµÈ()) {
            return false;
        }
        if (this.HorizonCode_Horizon_È.ˆÐƒØ().nextInt(8000) != 0) {
            return false;
        }
        this.Â = (EntityVillager)this.HorizonCode_Horizon_È.Ï­Ðƒà.HorizonCode_Horizon_È(EntityVillager.class, this.HorizonCode_Horizon_È.£É().Â(6.0, 2.0, 6.0), this.HorizonCode_Horizon_È);
        return this.Â != null;
    }
    
    @Override
    public boolean Â() {
        return this.Ý > 0;
    }
    
    @Override
    public void Âµá€() {
        this.Ý = 400;
        this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(true);
    }
    
    @Override
    public void Ý() {
        this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(false);
        this.Â = null;
    }
    
    @Override
    public void Ø­áŒŠá() {
        this.HorizonCode_Horizon_È.Ñ¢á().HorizonCode_Horizon_È(this.Â, 30.0f, 30.0f);
        --this.Ý;
    }
}
