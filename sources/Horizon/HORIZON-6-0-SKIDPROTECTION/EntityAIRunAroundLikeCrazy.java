package HORIZON-6-0-SKIDPROTECTION;

public class EntityAIRunAroundLikeCrazy extends EntityAIBase
{
    private EntityHorse HorizonCode_Horizon_È;
    private double Â;
    private double Ý;
    private double Ø­áŒŠá;
    private double Âµá€;
    private static final String Ó = "CL_00001612";
    
    public EntityAIRunAroundLikeCrazy(final EntityHorse p_i1653_1_, final double p_i1653_2_) {
        this.HorizonCode_Horizon_È = p_i1653_1_;
        this.Â = p_i1653_2_;
        this.HorizonCode_Horizon_È(1);
    }
    
    @Override
    public boolean HorizonCode_Horizon_È() {
        if (this.HorizonCode_Horizon_È.áˆºÕ() || this.HorizonCode_Horizon_È.µÕ == null) {
            return false;
        }
        final Vec3 var1 = RandomPositionGenerator.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È, 5, 4);
        if (var1 == null) {
            return false;
        }
        this.Ý = var1.HorizonCode_Horizon_È;
        this.Ø­áŒŠá = var1.Â;
        this.Âµá€ = var1.Ý;
        return true;
    }
    
    @Override
    public void Âµá€() {
        this.HorizonCode_Horizon_È.Š().HorizonCode_Horizon_È(this.Ý, this.Ø­áŒŠá, this.Âµá€, this.Â);
    }
    
    @Override
    public boolean Â() {
        return !this.HorizonCode_Horizon_È.Š().Ó() && this.HorizonCode_Horizon_È.µÕ != null;
    }
    
    @Override
    public void Ø­áŒŠá() {
        if (this.HorizonCode_Horizon_È.ˆÐƒØ().nextInt(50) == 0) {
            if (this.HorizonCode_Horizon_È.µÕ instanceof EntityPlayer) {
                final int var1 = this.HorizonCode_Horizon_È.ÂµÊ();
                final int var2 = this.HorizonCode_Horizon_È.£Ç();
                if (var2 > 0 && this.HorizonCode_Horizon_È.ˆÐƒØ().nextInt(var2) < var1) {
                    this.HorizonCode_Horizon_È.Ø((EntityPlayer)this.HorizonCode_Horizon_È.µÕ);
                    this.HorizonCode_Horizon_È.Ï­Ðƒà.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È, (byte)7);
                    return;
                }
                this.HorizonCode_Horizon_È.µÕ(5);
            }
            this.HorizonCode_Horizon_È.µÕ.HorizonCode_Horizon_È((Entity)null);
            this.HorizonCode_Horizon_È.µÕ = null;
            this.HorizonCode_Horizon_È.áˆºÛ();
            this.HorizonCode_Horizon_È.Ï­Ðƒà.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È, (byte)6);
        }
    }
}
