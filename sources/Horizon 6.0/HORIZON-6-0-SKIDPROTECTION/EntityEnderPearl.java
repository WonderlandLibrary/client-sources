package HORIZON-6-0-SKIDPROTECTION;

public class EntityEnderPearl extends EntityThrowable
{
    private static final String Ý = "CL_00001725";
    
    public EntityEnderPearl(final World worldIn, final EntityLivingBase p_i1783_2_) {
        super(worldIn, p_i1783_2_);
    }
    
    public EntityEnderPearl(final World worldIn, final double p_i1784_2_, final double p_i1784_4_, final double p_i1784_6_) {
        super(worldIn, p_i1784_2_, p_i1784_4_, p_i1784_6_);
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final MovingObjectPosition p_70184_1_) {
        final EntityLivingBase var2 = this.µà();
        if (p_70184_1_.Ø­áŒŠá != null) {
            p_70184_1_.Ø­áŒŠá.HorizonCode_Horizon_È(DamageSource.HorizonCode_Horizon_È(this, var2), 0.0f);
        }
        for (int var3 = 0; var3 < 32; ++var3) {
            this.Ï­Ðƒà.HorizonCode_Horizon_È(EnumParticleTypes.áŒŠà, this.ŒÏ, this.Çªà¢ + this.ˆáƒ.nextDouble() * 2.0, this.Ê, this.ˆáƒ.nextGaussian(), 0.0, this.ˆáƒ.nextGaussian(), new int[0]);
        }
        if (!this.Ï­Ðƒà.ŠÄ) {
            if (var2 instanceof EntityPlayerMP) {
                final EntityPlayerMP var4 = (EntityPlayerMP)var2;
                if (var4.HorizonCode_Horizon_È.Â().Âµá€() && var4.Ï­Ðƒà == this.Ï­Ðƒà && !var4.Ï­Ó()) {
                    if (this.ˆáƒ.nextFloat() < 0.05f && this.Ï­Ðƒà.Çªà¢().Â("doMobSpawning")) {
                        final EntityEndermite var5 = new EntityEndermite(this.Ï­Ðƒà);
                        var5.HorizonCode_Horizon_È(true);
                        var5.Â(var2.ŒÏ, var2.Çªà¢, var2.Ê, var2.É, var2.áƒ);
                        this.Ï­Ðƒà.HorizonCode_Horizon_È(var5);
                    }
                    if (var2.áˆºÇŽØ()) {
                        var2.HorizonCode_Horizon_È((Entity)null);
                    }
                    var2.áˆºÑ¢Õ(this.ŒÏ, this.Çªà¢, this.Ê);
                    var2.Ï­à = 0.0f;
                    var2.HorizonCode_Horizon_È(DamageSource.áŒŠÆ, 5.0f);
                }
            }
            this.á€();
        }
    }
    
    @Override
    public void á() {
        final EntityLivingBase var1 = this.µà();
        if (var1 != null && var1 instanceof EntityPlayer && !var1.Œ()) {
            this.á€();
        }
        else {
            super.á();
        }
    }
}
