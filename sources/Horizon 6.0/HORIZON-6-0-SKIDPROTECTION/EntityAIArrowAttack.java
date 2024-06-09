package HORIZON-6-0-SKIDPROTECTION;

public class EntityAIArrowAttack extends EntityAIBase
{
    private final EntityLiving HorizonCode_Horizon_È;
    private final IRangedAttackMob Â;
    private EntityLivingBase Ý;
    private int Ø­áŒŠá;
    private double Âµá€;
    private int Ó;
    private int à;
    private int Ø;
    private float áŒŠÆ;
    private float áˆºÑ¢Õ;
    private static final String ÂµÈ = "CL_00001609";
    
    public EntityAIArrowAttack(final IRangedAttackMob p_i1649_1_, final double p_i1649_2_, final int p_i1649_4_, final float p_i1649_5_) {
        this(p_i1649_1_, p_i1649_2_, p_i1649_4_, p_i1649_4_, p_i1649_5_);
    }
    
    public EntityAIArrowAttack(final IRangedAttackMob p_i1650_1_, final double p_i1650_2_, final int p_i1650_4_, final int p_i1650_5_, final float p_i1650_6_) {
        this.Ø­áŒŠá = -1;
        if (!(p_i1650_1_ instanceof EntityLivingBase)) {
            throw new IllegalArgumentException("ArrowAttackGoal requires Mob implements RangedAttackMob");
        }
        this.Â = p_i1650_1_;
        this.HorizonCode_Horizon_È = (EntityLiving)p_i1650_1_;
        this.Âµá€ = p_i1650_2_;
        this.à = p_i1650_4_;
        this.Ø = p_i1650_5_;
        this.áŒŠÆ = p_i1650_6_;
        this.áˆºÑ¢Õ = p_i1650_6_ * p_i1650_6_;
        this.HorizonCode_Horizon_È(3);
    }
    
    @Override
    public boolean HorizonCode_Horizon_È() {
        final EntityLivingBase var1 = this.HorizonCode_Horizon_È.Ñ¢Ó();
        if (var1 == null) {
            return false;
        }
        this.Ý = var1;
        return true;
    }
    
    @Override
    public boolean Â() {
        return this.HorizonCode_Horizon_È() || !this.HorizonCode_Horizon_È.Š().Ó();
    }
    
    @Override
    public void Ý() {
        this.Ý = null;
        this.Ó = 0;
        this.Ø­áŒŠá = -1;
    }
    
    @Override
    public void Ø­áŒŠá() {
        final double var1 = this.HorizonCode_Horizon_È.Âµá€(this.Ý.ŒÏ, this.Ý.£É().Â, this.Ý.Ê);
        final boolean var2 = this.HorizonCode_Horizon_È.Ø­Ñ¢á€().HorizonCode_Horizon_È(this.Ý);
        if (var2) {
            ++this.Ó;
        }
        else {
            this.Ó = 0;
        }
        if (var1 <= this.áˆºÑ¢Õ && this.Ó >= 20) {
            this.HorizonCode_Horizon_È.Š().à();
        }
        else {
            this.HorizonCode_Horizon_È.Š().HorizonCode_Horizon_È(this.Ý, this.Âµá€);
        }
        this.HorizonCode_Horizon_È.Ñ¢á().HorizonCode_Horizon_È(this.Ý, 30.0f, 30.0f);
        final int ø­áŒŠá = this.Ø­áŒŠá - 1;
        this.Ø­áŒŠá = ø­áŒŠá;
        if (ø­áŒŠá == 0) {
            if (var1 > this.áˆºÑ¢Õ || !var2) {
                return;
            }
            final float var3 = MathHelper.HorizonCode_Horizon_È(var1) / this.áŒŠÆ;
            final float var4 = MathHelper.HorizonCode_Horizon_È(var3, 0.1f, 1.0f);
            this.Â.HorizonCode_Horizon_È(this.Ý, var4);
            this.Ø­áŒŠá = MathHelper.Ø­áŒŠá(var3 * (this.Ø - this.à) + this.à);
        }
        else if (this.Ø­áŒŠá < 0) {
            final float var3 = MathHelper.HorizonCode_Horizon_È(var1) / this.áŒŠÆ;
            this.Ø­áŒŠá = MathHelper.Ø­áŒŠá(var3 * (this.Ø - this.à) + this.à);
        }
    }
}
