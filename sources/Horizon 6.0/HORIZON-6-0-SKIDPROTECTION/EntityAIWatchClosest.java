package HORIZON-6-0-SKIDPROTECTION;

public class EntityAIWatchClosest extends EntityAIBase
{
    protected EntityLiving HorizonCode_Horizon_È;
    protected Entity Â;
    protected float Ý;
    private int Âµá€;
    private float Ó;
    protected Class Ø­áŒŠá;
    private static final String à = "CL_00001592";
    
    public EntityAIWatchClosest(final EntityLiving p_i1631_1_, final Class p_i1631_2_, final float p_i1631_3_) {
        this.HorizonCode_Horizon_È = p_i1631_1_;
        this.Ø­áŒŠá = p_i1631_2_;
        this.Ý = p_i1631_3_;
        this.Ó = 0.02f;
        this.HorizonCode_Horizon_È(2);
    }
    
    public EntityAIWatchClosest(final EntityLiving p_i1632_1_, final Class p_i1632_2_, final float p_i1632_3_, final float p_i1632_4_) {
        this.HorizonCode_Horizon_È = p_i1632_1_;
        this.Ø­áŒŠá = p_i1632_2_;
        this.Ý = p_i1632_3_;
        this.Ó = p_i1632_4_;
        this.HorizonCode_Horizon_È(2);
    }
    
    @Override
    public boolean HorizonCode_Horizon_È() {
        if (this.HorizonCode_Horizon_È.ˆÐƒØ().nextFloat() >= this.Ó) {
            return false;
        }
        if (this.HorizonCode_Horizon_È.Ñ¢Ó() != null) {
            this.Â = this.HorizonCode_Horizon_È.Ñ¢Ó();
        }
        if (this.Ø­áŒŠá == EntityPlayer.class) {
            this.Â = this.HorizonCode_Horizon_È.Ï­Ðƒà.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È, (double)this.Ý);
        }
        else {
            this.Â = this.HorizonCode_Horizon_È.Ï­Ðƒà.HorizonCode_Horizon_È(this.Ø­áŒŠá, this.HorizonCode_Horizon_È.£É().Â(this.Ý, 3.0, this.Ý), this.HorizonCode_Horizon_È);
        }
        return this.Â != null;
    }
    
    @Override
    public boolean Â() {
        return this.Â.Œ() && this.HorizonCode_Horizon_È.Âµá€(this.Â) <= this.Ý * this.Ý && this.Âµá€ > 0;
    }
    
    @Override
    public void Âµá€() {
        this.Âµá€ = 40 + this.HorizonCode_Horizon_È.ˆÐƒØ().nextInt(40);
    }
    
    @Override
    public void Ý() {
        this.Â = null;
    }
    
    @Override
    public void Ø­áŒŠá() {
        this.HorizonCode_Horizon_È.Ñ¢á().HorizonCode_Horizon_È(this.Â.ŒÏ, this.Â.Çªà¢ + this.Â.Ðƒáƒ(), this.Â.Ê, 10.0f, this.HorizonCode_Horizon_È.áˆºà());
        --this.Âµá€;
    }
}
