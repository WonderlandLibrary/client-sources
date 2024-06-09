package HORIZON-6-0-SKIDPROTECTION;

public class EntityFootStepFX extends EntityFX
{
    private static final ResourceLocation_1975012498 HorizonCode_Horizon_È;
    private int ÇŽá;
    private int Ñ¢à;
    private TextureManager ÇªØ­;
    private static final String £áŒŠá = "CL_00000908";
    
    static {
        HorizonCode_Horizon_È = new ResourceLocation_1975012498("textures/particle/footprint.png");
    }
    
    protected EntityFootStepFX(final TextureManager p_i1210_1_, final World worldIn, final double p_i1210_3_, final double p_i1210_5_, final double p_i1210_7_) {
        super(worldIn, p_i1210_3_, p_i1210_5_, p_i1210_7_, 0.0, 0.0, 0.0);
        this.ÇªØ­ = p_i1210_1_;
        final double çžé = 0.0;
        this.ÇŽÕ = çžé;
        this.ˆá = çžé;
        this.ÇŽÉ = çžé;
        this.Ñ¢à = 200;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final WorldRenderer p_180434_1_, final Entity p_180434_2_, final float p_180434_3_, final float p_180434_4_, final float p_180434_5_, final float p_180434_6_, final float p_180434_7_, final float p_180434_8_) {
        float var9 = (this.ÇŽá + p_180434_3_) / this.Ñ¢à;
        var9 *= var9;
        float var10 = 2.0f - var9 * 2.0f;
        if (var10 > 1.0f) {
            var10 = 1.0f;
        }
        var10 *= 0.2f;
        GlStateManager.Ó();
        final float var11 = 0.125f;
        final float var12 = (float)(this.ŒÏ - EntityFootStepFX.Å);
        final float var13 = (float)(this.Çªà¢ - EntityFootStepFX.£à);
        final float var14 = (float)(this.Ê - EntityFootStepFX.µà);
        final float var15 = this.Ï­Ðƒà.£à(new BlockPos(this));
        this.ÇªØ­.HorizonCode_Horizon_È(EntityFootStepFX.HorizonCode_Horizon_È);
        GlStateManager.á();
        GlStateManager.Â(770, 771);
        p_180434_1_.Â();
        p_180434_1_.HorizonCode_Horizon_È(var15, var15, var15, var10);
        p_180434_1_.HorizonCode_Horizon_È(var12 - var11, var13, var14 + var11, 0.0, 1.0);
        p_180434_1_.HorizonCode_Horizon_È(var12 + var11, var13, var14 + var11, 1.0, 1.0);
        p_180434_1_.HorizonCode_Horizon_È(var12 + var11, var13, var14 - var11, 1.0, 0.0);
        p_180434_1_.HorizonCode_Horizon_È(var12 - var11, var13, var14 - var11, 0.0, 0.0);
        Tessellator.HorizonCode_Horizon_È().Â();
        GlStateManager.ÂµÈ();
        GlStateManager.Âµá€();
    }
    
    @Override
    public void á() {
        ++this.ÇŽá;
        if (this.ÇŽá == this.Ñ¢à) {
            this.á€();
        }
    }
    
    @Override
    public int Ø­áŒŠá() {
        return 3;
    }
    
    public static class HorizonCode_Horizon_È implements IParticleFactory
    {
        private static final String HorizonCode_Horizon_È = "CL_00002601";
        
        @Override
        public EntityFX HorizonCode_Horizon_È(final int p_178902_1_, final World worldIn, final double p_178902_3_, final double p_178902_5_, final double p_178902_7_, final double p_178902_9_, final double p_178902_11_, final double p_178902_13_, final int... p_178902_15_) {
            return new EntityFootStepFX(Minecraft.áŒŠà().¥à(), worldIn, p_178902_3_, p_178902_5_, p_178902_7_);
        }
    }
}
