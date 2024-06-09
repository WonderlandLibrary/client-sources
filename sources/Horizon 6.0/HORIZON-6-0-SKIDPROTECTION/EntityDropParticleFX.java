package HORIZON-6-0-SKIDPROTECTION;

public class EntityDropParticleFX extends EntityFX
{
    private Material HorizonCode_Horizon_È;
    private int ÇŽá;
    private static final String Ñ¢à = "CL_00000901";
    
    protected EntityDropParticleFX(final World worldIn, final double p_i1203_2_, final double p_i1203_4_, final double p_i1203_6_, final Material p_i1203_8_) {
        super(worldIn, p_i1203_2_, p_i1203_4_, p_i1203_6_, 0.0, 0.0, 0.0);
        final double çžé = 0.0;
        this.ÇŽÕ = çžé;
        this.ˆá = çžé;
        this.ÇŽÉ = çžé;
        if (p_i1203_8_ == Material.Ø) {
            this.áˆºÑ¢Õ = 0.0f;
            this.ÂµÈ = 0.0f;
            this.á = 1.0f;
        }
        else {
            this.áˆºÑ¢Õ = 1.0f;
            this.ÂµÈ = 0.0f;
            this.á = 0.0f;
        }
        this.HorizonCode_Horizon_È(113);
        this.HorizonCode_Horizon_È(0.01f, 0.01f);
        this.áŒŠÆ = 0.06f;
        this.HorizonCode_Horizon_È = p_i1203_8_;
        this.ÇŽá = 40;
        this.à = (int)(64.0 / (Math.random() * 0.8 + 0.2));
        final double çžé2 = 0.0;
        this.ÇŽÕ = çžé2;
        this.ˆá = çžé2;
        this.ÇŽÉ = çžé2;
    }
    
    @Override
    public int HorizonCode_Horizon_È(final float p_70070_1_) {
        return (this.HorizonCode_Horizon_È == Material.Ø) ? super.HorizonCode_Horizon_È(p_70070_1_) : 257;
    }
    
    @Override
    public float Â(final float p_70013_1_) {
        return (this.HorizonCode_Horizon_È == Material.Ø) ? super.Â(p_70013_1_) : 1.0f;
    }
    
    @Override
    public void á() {
        this.áŒŠà = this.ŒÏ;
        this.ŠÄ = this.Çªà¢;
        this.Ñ¢á = this.Ê;
        if (this.HorizonCode_Horizon_È == Material.Ø) {
            this.áˆºÑ¢Õ = 0.2f;
            this.ÂµÈ = 0.3f;
            this.á = 1.0f;
        }
        else {
            this.áˆºÑ¢Õ = 1.0f;
            this.ÂµÈ = 16.0f / (40 - this.ÇŽá + 16);
            this.á = 4.0f / (40 - this.ÇŽá + 8);
        }
        this.ˆá -= this.áŒŠÆ;
        if (this.ÇŽá-- > 0) {
            this.ÇŽÉ *= 0.02;
            this.ˆá *= 0.02;
            this.ÇŽÕ *= 0.02;
            this.HorizonCode_Horizon_È(113);
        }
        else {
            this.HorizonCode_Horizon_È(112);
        }
        this.HorizonCode_Horizon_È(this.ÇŽÉ, this.ˆá, this.ÇŽÕ);
        this.ÇŽÉ *= 0.9800000190734863;
        this.ˆá *= 0.9800000190734863;
        this.ÇŽÕ *= 0.9800000190734863;
        if (this.à-- <= 0) {
            this.á€();
        }
        if (this.ŠÂµà) {
            if (this.HorizonCode_Horizon_È == Material.Ø) {
                this.á€();
                this.Ï­Ðƒà.HorizonCode_Horizon_È(EnumParticleTypes.Ó, this.ŒÏ, this.Çªà¢, this.Ê, 0.0, 0.0, 0.0, new int[0]);
            }
            else {
                this.HorizonCode_Horizon_È(114);
            }
            this.ÇŽÉ *= 0.699999988079071;
            this.ÇŽÕ *= 0.699999988079071;
        }
        final BlockPos var1 = new BlockPos(this);
        final IBlockState var2 = this.Ï­Ðƒà.Â(var1);
        final Material var3 = var2.Ý().Ó();
        if (var3.HorizonCode_Horizon_È() || var3.Â()) {
            double var4 = 0.0;
            if (var2.Ý() instanceof BlockLiquid) {
                var4 = BlockLiquid.Âµá€((int)var2.HorizonCode_Horizon_È(BlockLiquid.à¢));
            }
            final double var5 = MathHelper.Ý(this.Çªà¢) + 1 - var4;
            if (this.Çªà¢ < var5) {
                this.á€();
            }
        }
    }
    
    public static class HorizonCode_Horizon_È implements IParticleFactory
    {
        private static final String HorizonCode_Horizon_È = "CL_00002607";
        
        @Override
        public EntityFX HorizonCode_Horizon_È(final int p_178902_1_, final World worldIn, final double p_178902_3_, final double p_178902_5_, final double p_178902_7_, final double p_178902_9_, final double p_178902_11_, final double p_178902_13_, final int... p_178902_15_) {
            return new EntityDropParticleFX(worldIn, p_178902_3_, p_178902_5_, p_178902_7_, Material.áŒŠÆ);
        }
    }
    
    public static class Â implements IParticleFactory
    {
        private static final String HorizonCode_Horizon_È = "CL_00002606";
        
        @Override
        public EntityFX HorizonCode_Horizon_È(final int p_178902_1_, final World worldIn, final double p_178902_3_, final double p_178902_5_, final double p_178902_7_, final double p_178902_9_, final double p_178902_11_, final double p_178902_13_, final int... p_178902_15_) {
            return new EntityDropParticleFX(worldIn, p_178902_3_, p_178902_5_, p_178902_7_, Material.Ø);
        }
    }
}
