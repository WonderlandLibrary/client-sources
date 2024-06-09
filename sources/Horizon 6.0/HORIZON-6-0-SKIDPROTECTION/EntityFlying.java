package HORIZON-6-0-SKIDPROTECTION;

public abstract class EntityFlying extends EntityLiving
{
    private static final String HorizonCode_Horizon_È = "CL_00001545";
    
    public EntityFlying(final World worldIn) {
        super(worldIn);
    }
    
    @Override
    public void Ø­áŒŠá(final float distance, final float damageMultiplier) {
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final double p_180433_1_, final boolean p_180433_3_, final Block p_180433_4_, final BlockPos p_180433_5_) {
    }
    
    @Override
    public void Ó(final float p_70612_1_, final float p_70612_2_) {
        if (this.£ÂµÄ()) {
            this.Â(p_70612_1_, p_70612_2_, 0.02f);
            this.HorizonCode_Horizon_È(this.ÇŽÉ, this.ˆá, this.ÇŽÕ);
            this.ÇŽÉ *= 0.800000011920929;
            this.ˆá *= 0.800000011920929;
            this.ÇŽÕ *= 0.800000011920929;
        }
        else if (this.ÇŽá€()) {
            this.Â(p_70612_1_, p_70612_2_, 0.02f);
            this.HorizonCode_Horizon_È(this.ÇŽÉ, this.ˆá, this.ÇŽÕ);
            this.ÇŽÉ *= 0.5;
            this.ˆá *= 0.5;
            this.ÇŽÕ *= 0.5;
        }
        else {
            float var3 = 0.91f;
            if (this.ŠÂµà) {
                var3 = this.Ï­Ðƒà.Â(new BlockPos(MathHelper.Ý(this.ŒÏ), MathHelper.Ý(this.£É().Â) - 1, MathHelper.Ý(this.Ê))).Ý().áƒ * 0.91f;
            }
            final float var4 = 0.16277136f / (var3 * var3 * var3);
            this.Â(p_70612_1_, p_70612_2_, this.ŠÂµà ? (0.1f * var4) : 0.02f);
            var3 = 0.91f;
            if (this.ŠÂµà) {
                var3 = this.Ï­Ðƒà.Â(new BlockPos(MathHelper.Ý(this.ŒÏ), MathHelper.Ý(this.£É().Â) - 1, MathHelper.Ý(this.Ê))).Ý().áƒ * 0.91f;
            }
            this.HorizonCode_Horizon_È(this.ÇŽÉ, this.ˆá, this.ÇŽÕ);
            this.ÇŽÉ *= var3;
            this.ˆá *= var3;
            this.ÇŽÕ *= var3;
        }
        this.Šà = this.áŒŠá€;
        final double var5 = this.ŒÏ - this.áŒŠà;
        final double var6 = this.Ê - this.Ñ¢á;
        float var7 = MathHelper.HorizonCode_Horizon_È(var5 * var5 + var6 * var6) * 4.0f;
        if (var7 > 1.0f) {
            var7 = 1.0f;
        }
        this.áŒŠá€ += (var7 - this.áŒŠá€) * 0.4f;
        this.¥Ï += this.áŒŠá€;
    }
    
    @Override
    public boolean i_() {
        return false;
    }
}
