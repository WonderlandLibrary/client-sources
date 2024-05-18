package HORIZON-6-0-SKIDPROTECTION;

public class EntityAILookIdle extends EntityAIBase
{
    private EntityLiving HorizonCode_Horizon_È;
    private double Â;
    private double Ý;
    private int Ø­áŒŠá;
    private static final String Âµá€ = "CL_00001607";
    
    public EntityAILookIdle(final EntityLiving p_i1647_1_) {
        this.HorizonCode_Horizon_È = p_i1647_1_;
        this.HorizonCode_Horizon_È(3);
    }
    
    @Override
    public boolean HorizonCode_Horizon_È() {
        return this.HorizonCode_Horizon_È.ˆÐƒØ().nextFloat() < 0.02f;
    }
    
    @Override
    public boolean Â() {
        return this.Ø­áŒŠá >= 0;
    }
    
    @Override
    public void Âµá€() {
        final double var1 = 6.283185307179586 * this.HorizonCode_Horizon_È.ˆÐƒØ().nextDouble();
        this.Â = Math.cos(var1);
        this.Ý = Math.sin(var1);
        this.Ø­áŒŠá = 20 + this.HorizonCode_Horizon_È.ˆÐƒØ().nextInt(20);
    }
    
    @Override
    public void Ø­áŒŠá() {
        --this.Ø­áŒŠá;
        this.HorizonCode_Horizon_È.Ñ¢á().HorizonCode_Horizon_È(this.HorizonCode_Horizon_È.ŒÏ + this.Â, this.HorizonCode_Horizon_È.Çªà¢ + this.HorizonCode_Horizon_È.Ðƒáƒ(), this.HorizonCode_Horizon_È.Ê + this.Ý, 10.0f, this.HorizonCode_Horizon_È.áˆºà());
    }
}
