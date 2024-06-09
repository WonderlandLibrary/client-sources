package HORIZON-6-0-SKIDPROTECTION;

public class EntityAIAttackOnCollide extends EntityAIBase
{
    World HorizonCode_Horizon_È;
    protected EntityCreature Â;
    int Ý;
    double Ø­áŒŠá;
    boolean Âµá€;
    PathEntity Ó;
    Class à;
    private int Ø;
    private double áŒŠÆ;
    private double áˆºÑ¢Õ;
    private double ÂµÈ;
    private static final String á = "CL_00001595";
    
    public EntityAIAttackOnCollide(final EntityCreature p_i1635_1_, final Class p_i1635_2_, final double p_i1635_3_, final boolean p_i1635_5_) {
        this(p_i1635_1_, p_i1635_3_, p_i1635_5_);
        this.à = p_i1635_2_;
    }
    
    public EntityAIAttackOnCollide(final EntityCreature p_i1636_1_, final double p_i1636_2_, final boolean p_i1636_4_) {
        this.Â = p_i1636_1_;
        this.HorizonCode_Horizon_È = p_i1636_1_.Ï­Ðƒà;
        this.Ø­áŒŠá = p_i1636_2_;
        this.Âµá€ = p_i1636_4_;
        this.HorizonCode_Horizon_È(3);
    }
    
    @Override
    public boolean HorizonCode_Horizon_È() {
        final EntityLivingBase var1 = this.Â.Ñ¢Ó();
        if (var1 == null) {
            return false;
        }
        if (!var1.Œ()) {
            return false;
        }
        if (this.à != null && !this.à.isAssignableFrom(var1.getClass())) {
            return false;
        }
        this.Ó = this.Â.Š().HorizonCode_Horizon_È(var1);
        return this.Ó != null;
    }
    
    @Override
    public boolean Â() {
        final EntityLivingBase var1 = this.Â.Ñ¢Ó();
        return var1 != null && var1.Œ() && (this.Âµá€ ? this.Â.Ø­áŒŠá(new BlockPos(var1)) : (!this.Â.Š().Ó()));
    }
    
    @Override
    public void Âµá€() {
        this.Â.Š().HorizonCode_Horizon_È(this.Ó, this.Ø­áŒŠá);
        this.Ø = 0;
    }
    
    @Override
    public void Ý() {
        this.Â.Š().à();
    }
    
    @Override
    public void Ø­áŒŠá() {
        final EntityLivingBase var1 = this.Â.Ñ¢Ó();
        this.Â.Ñ¢á().HorizonCode_Horizon_È(var1, 30.0f, 30.0f);
        final double var2 = this.Â.Âµá€(var1.ŒÏ, var1.£É().Â, var1.Ê);
        final double var3 = this.HorizonCode_Horizon_È(var1);
        --this.Ø;
        if ((this.Âµá€ || this.Â.Ø­Ñ¢á€().HorizonCode_Horizon_È(var1)) && this.Ø <= 0 && ((this.áŒŠÆ == 0.0 && this.áˆºÑ¢Õ == 0.0 && this.ÂµÈ == 0.0) || var1.Âµá€(this.áŒŠÆ, this.áˆºÑ¢Õ, this.ÂµÈ) >= 1.0 || this.Â.ˆÐƒØ().nextFloat() < 0.05f)) {
            this.áŒŠÆ = var1.ŒÏ;
            this.áˆºÑ¢Õ = var1.£É().Â;
            this.ÂµÈ = var1.Ê;
            this.Ø = 4 + this.Â.ˆÐƒØ().nextInt(7);
            if (var2 > 1024.0) {
                this.Ø += 10;
            }
            else if (var2 > 256.0) {
                this.Ø += 5;
            }
            if (!this.Â.Š().HorizonCode_Horizon_È(var1, this.Ø­áŒŠá)) {
                this.Ø += 15;
            }
        }
        this.Ý = Math.max(this.Ý - 1, 0);
        if (var2 <= var3 && this.Ý <= 0) {
            this.Ý = 20;
            if (this.Â.Çª() != null) {
                this.Â.b_();
            }
            this.Â.Å(var1);
        }
    }
    
    protected double HorizonCode_Horizon_È(final EntityLivingBase p_179512_1_) {
        return this.Â.áŒŠ * 2.0f * this.Â.áŒŠ * 2.0f + p_179512_1_.áŒŠ;
    }
}
