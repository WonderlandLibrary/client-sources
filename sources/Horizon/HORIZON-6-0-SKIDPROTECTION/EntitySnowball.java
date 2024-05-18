package HORIZON-6-0-SKIDPROTECTION;

public class EntitySnowball extends EntityThrowable
{
    private static final String Ý = "CL_00001722";
    
    public EntitySnowball(final World worldIn) {
        super(worldIn);
    }
    
    public EntitySnowball(final World worldIn, final EntityLivingBase p_i1774_2_) {
        super(worldIn, p_i1774_2_);
    }
    
    public EntitySnowball(final World worldIn, final double p_i1775_2_, final double p_i1775_4_, final double p_i1775_6_) {
        super(worldIn, p_i1775_2_, p_i1775_4_, p_i1775_6_);
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final MovingObjectPosition p_70184_1_) {
        if (p_70184_1_.Ø­áŒŠá != null) {
            byte var2 = 0;
            if (p_70184_1_.Ø­áŒŠá instanceof EntityBlaze) {
                var2 = 3;
            }
            p_70184_1_.Ø­áŒŠá.HorizonCode_Horizon_È(DamageSource.HorizonCode_Horizon_È(this, this.µà()), var2);
        }
        for (int var3 = 0; var3 < 8; ++var3) {
            this.Ï­Ðƒà.HorizonCode_Horizon_È(EnumParticleTypes.ˆá, this.ŒÏ, this.Çªà¢, this.Ê, 0.0, 0.0, 0.0, new int[0]);
        }
        if (!this.Ï­Ðƒà.ŠÄ) {
            this.á€();
        }
    }
}
