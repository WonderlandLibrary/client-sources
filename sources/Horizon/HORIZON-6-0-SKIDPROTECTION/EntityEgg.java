package HORIZON-6-0-SKIDPROTECTION;

public class EntityEgg extends EntityThrowable
{
    private static final String Ý = "CL_00001724";
    
    public EntityEgg(final World worldIn) {
        super(worldIn);
    }
    
    public EntityEgg(final World worldIn, final EntityLivingBase p_i1780_2_) {
        super(worldIn, p_i1780_2_);
    }
    
    public EntityEgg(final World worldIn, final double p_i1781_2_, final double p_i1781_4_, final double p_i1781_6_) {
        super(worldIn, p_i1781_2_, p_i1781_4_, p_i1781_6_);
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final MovingObjectPosition p_70184_1_) {
        if (p_70184_1_.Ø­áŒŠá != null) {
            p_70184_1_.Ø­áŒŠá.HorizonCode_Horizon_È(DamageSource.HorizonCode_Horizon_È(this, this.µà()), 0.0f);
        }
        if (!this.Ï­Ðƒà.ŠÄ && this.ˆáƒ.nextInt(8) == 0) {
            byte var2 = 1;
            if (this.ˆáƒ.nextInt(32) == 0) {
                var2 = 4;
            }
            for (int var3 = 0; var3 < var2; ++var3) {
                final EntityChicken var4 = new EntityChicken(this.Ï­Ðƒà);
                var4.Â(-24000);
                var4.Â(this.ŒÏ, this.Çªà¢, this.Ê, this.É, 0.0f);
                this.Ï­Ðƒà.HorizonCode_Horizon_È(var4);
            }
        }
        final double var5 = 0.08;
        for (int var6 = 0; var6 < 8; ++var6) {
            this.Ï­Ðƒà.HorizonCode_Horizon_È(EnumParticleTypes.Õ, this.ŒÏ, this.Çªà¢, this.Ê, (this.ˆáƒ.nextFloat() - 0.5) * 0.08, (this.ˆáƒ.nextFloat() - 0.5) * 0.08, (this.ˆáƒ.nextFloat() - 0.5) * 0.08, Item_1028566121.HorizonCode_Horizon_È(Items.¥É));
        }
        if (!this.Ï­Ðƒà.ŠÄ) {
            this.á€();
        }
    }
}
