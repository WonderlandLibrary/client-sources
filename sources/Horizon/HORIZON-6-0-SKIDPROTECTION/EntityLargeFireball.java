package HORIZON-6-0-SKIDPROTECTION;

public class EntityLargeFireball extends EntityFireball
{
    public int Âµá€;
    private static final String Ó = "CL_00001719";
    
    public EntityLargeFireball(final World worldIn) {
        super(worldIn);
        this.Âµá€ = 1;
    }
    
    public EntityLargeFireball(final World worldIn, final double p_i1768_2_, final double p_i1768_4_, final double p_i1768_6_, final double p_i1768_8_, final double p_i1768_10_, final double p_i1768_12_) {
        super(worldIn, p_i1768_2_, p_i1768_4_, p_i1768_6_, p_i1768_8_, p_i1768_10_, p_i1768_12_);
        this.Âµá€ = 1;
    }
    
    public EntityLargeFireball(final World worldIn, final EntityLivingBase p_i1769_2_, final double p_i1769_3_, final double p_i1769_5_, final double p_i1769_7_) {
        super(worldIn, p_i1769_2_, p_i1769_3_, p_i1769_5_, p_i1769_7_);
        this.Âµá€ = 1;
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final MovingObjectPosition p_70227_1_) {
        if (!this.Ï­Ðƒà.ŠÄ) {
            if (p_70227_1_.Ø­áŒŠá != null) {
                p_70227_1_.Ø­áŒŠá.HorizonCode_Horizon_È(DamageSource.HorizonCode_Horizon_È(this, this.HorizonCode_Horizon_È), 6.0f);
                this.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È, p_70227_1_.Ø­áŒŠá);
            }
            final boolean var2 = this.Ï­Ðƒà.Çªà¢().Â("mobGriefing");
            this.Ï­Ðƒà.HorizonCode_Horizon_È(null, this.ŒÏ, this.Çªà¢, this.Ê, this.Âµá€, var2, var2);
            this.á€();
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final NBTTagCompound tagCompound) {
        super.HorizonCode_Horizon_È(tagCompound);
        tagCompound.HorizonCode_Horizon_È("ExplosionPower", this.Âµá€);
    }
    
    @Override
    public void Â(final NBTTagCompound tagCompund) {
        super.Â(tagCompund);
        if (tagCompund.Â("ExplosionPower", 99)) {
            this.Âµá€ = tagCompund.Ó("ExplosionPower");
        }
    }
}
