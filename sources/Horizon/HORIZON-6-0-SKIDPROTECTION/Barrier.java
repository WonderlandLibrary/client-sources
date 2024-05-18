package HORIZON-6-0-SKIDPROTECTION;

public class Barrier extends EntityFX
{
    private static final String HorizonCode_Horizon_È = "CL_00002615";
    
    protected Barrier(final World worldIn, final double p_i46286_2_, final double p_i46286_4_, final double p_i46286_6_, final Item_1028566121 p_i46286_8_) {
        super(worldIn, p_i46286_2_, p_i46286_4_, p_i46286_6_, 0.0, 0.0, 0.0);
        this.HorizonCode_Horizon_È(Minecraft.áŒŠà().áˆºÏ().HorizonCode_Horizon_È().HorizonCode_Horizon_È(p_i46286_8_));
        final float áˆºÑ¢Õ = 1.0f;
        this.á = áˆºÑ¢Õ;
        this.ÂµÈ = áˆºÑ¢Õ;
        this.áˆºÑ¢Õ = áˆºÑ¢Õ;
        final double çžé = 0.0;
        this.ÇŽÕ = çžé;
        this.ˆá = çžé;
        this.ÇŽÉ = çžé;
        this.áŒŠÆ = 0.0f;
        this.à = 80;
    }
    
    @Override
    public int Ø­áŒŠá() {
        return 1;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final WorldRenderer p_180434_1_, final Entity p_180434_2_, final float p_180434_3_, final float p_180434_4_, final float p_180434_5_, final float p_180434_6_, final float p_180434_7_, final float p_180434_8_) {
        final float var9 = this.£á.Âµá€();
        final float var10 = this.£á.Ó();
        final float var11 = this.£á.à();
        final float var12 = this.£á.Ø();
        final float var13 = (float)(this.áŒŠà + (this.ŒÏ - this.áŒŠà) * p_180434_3_ - Barrier.Å);
        final float var14 = (float)(this.ŠÄ + (this.Çªà¢ - this.ŠÄ) * p_180434_3_ - Barrier.£à);
        final float var15 = (float)(this.Ñ¢á + (this.Ê - this.Ñ¢á) * p_180434_3_ - Barrier.µà);
        p_180434_1_.Â(this.áˆºÑ¢Õ, this.ÂµÈ, this.á);
        final float var16 = 0.5f;
        p_180434_1_.HorizonCode_Horizon_È(var13 - p_180434_4_ * var16 - p_180434_7_ * var16, var14 - p_180434_5_ * var16, var15 - p_180434_6_ * var16 - p_180434_8_ * var16, var10, var12);
        p_180434_1_.HorizonCode_Horizon_È(var13 - p_180434_4_ * var16 + p_180434_7_ * var16, var14 + p_180434_5_ * var16, var15 - p_180434_6_ * var16 + p_180434_8_ * var16, var10, var11);
        p_180434_1_.HorizonCode_Horizon_È(var13 + p_180434_4_ * var16 + p_180434_7_ * var16, var14 + p_180434_5_ * var16, var15 + p_180434_6_ * var16 + p_180434_8_ * var16, var9, var11);
        p_180434_1_.HorizonCode_Horizon_È(var13 + p_180434_4_ * var16 - p_180434_7_ * var16, var14 - p_180434_5_ * var16, var15 + p_180434_6_ * var16 - p_180434_8_ * var16, var9, var12);
    }
    
    public static class HorizonCode_Horizon_È implements IParticleFactory
    {
        private static final String HorizonCode_Horizon_È = "CL_00002614";
        
        @Override
        public EntityFX HorizonCode_Horizon_È(final int p_178902_1_, final World worldIn, final double p_178902_3_, final double p_178902_5_, final double p_178902_7_, final double p_178902_9_, final double p_178902_11_, final double p_178902_13_, final int... p_178902_15_) {
            return new Barrier(worldIn, p_178902_3_, p_178902_5_, p_178902_7_, Item_1028566121.HorizonCode_Horizon_È(Blocks.¥ÇªÅ));
        }
    }
}
