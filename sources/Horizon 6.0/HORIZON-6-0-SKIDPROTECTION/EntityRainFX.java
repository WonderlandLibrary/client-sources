package HORIZON-6-0-SKIDPROTECTION;

public class EntityRainFX extends EntityFX
{
    private static final String HorizonCode_Horizon_È = "CL_00000934";
    
    protected EntityRainFX(final World worldIn, final double p_i1235_2_, final double p_i1235_4_, final double p_i1235_6_) {
        super(worldIn, p_i1235_2_, p_i1235_4_, p_i1235_6_, 0.0, 0.0, 0.0);
        this.ÇŽÉ *= 0.30000001192092896;
        this.ˆá = Math.random() * 0.20000000298023224 + 0.10000000149011612;
        this.ÇŽÕ *= 0.30000001192092896;
        this.áˆºÑ¢Õ = 1.0f;
        this.ÂµÈ = 1.0f;
        this.á = 1.0f;
        this.HorizonCode_Horizon_È(19 + this.ˆáƒ.nextInt(4));
        this.HorizonCode_Horizon_È(0.01f, 0.01f);
        this.áŒŠÆ = 0.06f;
        this.à = (int)(8.0 / (Math.random() * 0.8 + 0.2));
    }
    
    @Override
    public void á() {
        this.áŒŠà = this.ŒÏ;
        this.ŠÄ = this.Çªà¢;
        this.Ñ¢á = this.Ê;
        this.ˆá -= this.áŒŠÆ;
        this.HorizonCode_Horizon_È(this.ÇŽÉ, this.ˆá, this.ÇŽÕ);
        this.ÇŽÉ *= 0.9800000190734863;
        this.ˆá *= 0.9800000190734863;
        this.ÇŽÕ *= 0.9800000190734863;
        if (this.à-- <= 0) {
            this.á€();
        }
        if (this.ŠÂµà) {
            if (Math.random() < 0.5) {
                this.á€();
            }
            this.ÇŽÉ *= 0.699999988079071;
            this.ÇŽÕ *= 0.699999988079071;
        }
        final BlockPos var1 = new BlockPos(this);
        final IBlockState var2 = this.Ï­Ðƒà.Â(var1);
        final Block var3 = var2.Ý();
        var3.Ý((IBlockAccess)this.Ï­Ðƒà, var1);
        final Material var4 = var2.Ý().Ó();
        if (var4.HorizonCode_Horizon_È() || var4.Â()) {
            double var5 = 0.0;
            if (var2.Ý() instanceof BlockLiquid) {
                var5 = 1.0f - BlockLiquid.Âµá€((int)var2.HorizonCode_Horizon_È(BlockLiquid.à¢));
            }
            else {
                var5 = var3.µÕ();
            }
            final double var6 = MathHelper.Ý(this.Çªà¢) + var5;
            if (this.Çªà¢ < var6) {
                this.á€();
            }
        }
    }
    
    public static class HorizonCode_Horizon_È implements IParticleFactory
    {
        private static final String HorizonCode_Horizon_È = "CL_00002572";
        
        @Override
        public EntityFX HorizonCode_Horizon_È(final int p_178902_1_, final World worldIn, final double p_178902_3_, final double p_178902_5_, final double p_178902_7_, final double p_178902_9_, final double p_178902_11_, final double p_178902_13_, final int... p_178902_15_) {
            return new EntityRainFX(worldIn, p_178902_3_, p_178902_5_, p_178902_7_);
        }
    }
}
