package HORIZON-6-0-SKIDPROTECTION;

public class EntityAITempt extends EntityAIBase
{
    private EntityCreature HorizonCode_Horizon_È;
    private double Â;
    private double Ý;
    private double Ø­áŒŠá;
    private double Âµá€;
    private double Ó;
    private double à;
    private EntityPlayer Ø;
    private int áŒŠÆ;
    private boolean áˆºÑ¢Õ;
    private Item_1028566121 ÂµÈ;
    private boolean á;
    private boolean ˆÏ­;
    private static final String £á = "CL_00001616";
    
    public EntityAITempt(final EntityCreature p_i45316_1_, final double p_i45316_2_, final Item_1028566121 p_i45316_4_, final boolean p_i45316_5_) {
        this.HorizonCode_Horizon_È = p_i45316_1_;
        this.Â = p_i45316_2_;
        this.ÂµÈ = p_i45316_4_;
        this.á = p_i45316_5_;
        this.HorizonCode_Horizon_È(3);
        if (!(p_i45316_1_.Š() instanceof PathNavigateGround)) {
            throw new IllegalArgumentException("Unsupported mob type for TemptGoal");
        }
    }
    
    @Override
    public boolean HorizonCode_Horizon_È() {
        if (this.áŒŠÆ > 0) {
            --this.áŒŠÆ;
            return false;
        }
        this.Ø = this.HorizonCode_Horizon_È.Ï­Ðƒà.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È, 10.0);
        if (this.Ø == null) {
            return false;
        }
        final ItemStack var1 = this.Ø.áŒŠá();
        return var1 != null && var1.HorizonCode_Horizon_È() == this.ÂµÈ;
    }
    
    @Override
    public boolean Â() {
        if (this.á) {
            if (this.HorizonCode_Horizon_È.Âµá€(this.Ø) < 36.0) {
                if (this.Ø.Âµá€(this.Ý, this.Ø­áŒŠá, this.Âµá€) > 0.010000000000000002) {
                    return false;
                }
                if (Math.abs(this.Ø.áƒ - this.Ó) > 5.0 || Math.abs(this.Ø.É - this.à) > 5.0) {
                    return false;
                }
            }
            else {
                this.Ý = this.Ø.ŒÏ;
                this.Ø­áŒŠá = this.Ø.Çªà¢;
                this.Âµá€ = this.Ø.Ê;
            }
            this.Ó = this.Ø.áƒ;
            this.à = this.Ø.É;
        }
        return this.HorizonCode_Horizon_È();
    }
    
    @Override
    public void Âµá€() {
        this.Ý = this.Ø.ŒÏ;
        this.Ø­áŒŠá = this.Ø.Çªà¢;
        this.Âµá€ = this.Ø.Ê;
        this.áˆºÑ¢Õ = true;
        this.ˆÏ­ = ((PathNavigateGround)this.HorizonCode_Horizon_È.Š()).á();
        ((PathNavigateGround)this.HorizonCode_Horizon_È.Š()).HorizonCode_Horizon_È(false);
    }
    
    @Override
    public void Ý() {
        this.Ø = null;
        this.HorizonCode_Horizon_È.Š().à();
        this.áŒŠÆ = 100;
        this.áˆºÑ¢Õ = false;
        ((PathNavigateGround)this.HorizonCode_Horizon_È.Š()).HorizonCode_Horizon_È(this.ˆÏ­);
    }
    
    @Override
    public void Ø­áŒŠá() {
        this.HorizonCode_Horizon_È.Ñ¢á().HorizonCode_Horizon_È(this.Ø, 30.0f, this.HorizonCode_Horizon_È.áˆºà());
        if (this.HorizonCode_Horizon_È.Âµá€(this.Ø) < 6.25) {
            this.HorizonCode_Horizon_È.Š().à();
        }
        else {
            this.HorizonCode_Horizon_È.Š().HorizonCode_Horizon_È(this.Ø, this.Â);
        }
    }
    
    public boolean Ø() {
        return this.áˆºÑ¢Õ;
    }
}
