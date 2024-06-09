package HORIZON-6-0-SKIDPROTECTION;

public class EntityAIBeg extends EntityAIBase
{
    private EntityWolf HorizonCode_Horizon_È;
    private EntityPlayer Â;
    private World Ý;
    private float Ø­áŒŠá;
    private int Âµá€;
    private static final String Ó = "CL_00001576";
    
    public EntityAIBeg(final EntityWolf p_i1617_1_, final float p_i1617_2_) {
        this.HorizonCode_Horizon_È = p_i1617_1_;
        this.Ý = p_i1617_1_.Ï­Ðƒà;
        this.Ø­áŒŠá = p_i1617_2_;
        this.HorizonCode_Horizon_È(2);
    }
    
    @Override
    public boolean HorizonCode_Horizon_È() {
        this.Â = this.Ý.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È, (double)this.Ø­áŒŠá);
        return this.Â != null && this.HorizonCode_Horizon_È(this.Â);
    }
    
    @Override
    public boolean Â() {
        return this.Â.Œ() && this.HorizonCode_Horizon_È.Âµá€(this.Â) <= this.Ø­áŒŠá * this.Ø­áŒŠá && (this.Âµá€ > 0 && this.HorizonCode_Horizon_È(this.Â));
    }
    
    @Override
    public void Âµá€() {
        this.HorizonCode_Horizon_È.£à(true);
        this.Âµá€ = 40 + this.HorizonCode_Horizon_È.ˆÐƒØ().nextInt(40);
    }
    
    @Override
    public void Ý() {
        this.HorizonCode_Horizon_È.£à(false);
        this.Â = null;
    }
    
    @Override
    public void Ø­áŒŠá() {
        this.HorizonCode_Horizon_È.Ñ¢á().HorizonCode_Horizon_È(this.Â.ŒÏ, this.Â.Çªà¢ + this.Â.Ðƒáƒ(), this.Â.Ê, 10.0f, this.HorizonCode_Horizon_È.áˆºà());
        --this.Âµá€;
    }
    
    private boolean HorizonCode_Horizon_È(final EntityPlayer p_75382_1_) {
        final ItemStack var2 = p_75382_1_.Ø­Ñ¢Ï­Ø­áˆº.Ø­áŒŠá();
        return var2 != null && ((!this.HorizonCode_Horizon_È.ÐƒÓ() && var2.HorizonCode_Horizon_È() == Items.ŠÕ) || this.HorizonCode_Horizon_È.Ø­áŒŠá(var2));
    }
}
