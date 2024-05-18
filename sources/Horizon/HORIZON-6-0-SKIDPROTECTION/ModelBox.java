package HORIZON-6-0-SKIDPROTECTION;

public class ModelBox
{
    private PositionTextureVertex[] Ø;
    private TexturedQuad[] áŒŠÆ;
    public final float HorizonCode_Horizon_È;
    public final float Â;
    public final float Ý;
    public final float Ø­áŒŠá;
    public final float Âµá€;
    public final float Ó;
    public String à;
    private static final String áˆºÑ¢Õ = "CL_00000872";
    
    public ModelBox(final ModelRenderer p_i46359_1_, final int p_i46359_2_, final int p_i46359_3_, final float p_i46359_4_, final float p_i46359_5_, final float p_i46359_6_, final int p_i46359_7_, final int p_i46359_8_, final int p_i46359_9_, final float p_i46359_10_) {
        this(p_i46359_1_, p_i46359_2_, p_i46359_3_, p_i46359_4_, p_i46359_5_, p_i46359_6_, p_i46359_7_, p_i46359_8_, p_i46359_9_, p_i46359_10_, p_i46359_1_.áŒŠÆ);
    }
    
    public ModelBox(final ModelRenderer p_i46301_1_, final int p_i46301_2_, final int p_i46301_3_, float p_i46301_4_, float p_i46301_5_, float p_i46301_6_, final int p_i46301_7_, final int p_i46301_8_, final int p_i46301_9_, final float p_i46301_10_, final boolean p_i46301_11_) {
        this.HorizonCode_Horizon_È = p_i46301_4_;
        this.Â = p_i46301_5_;
        this.Ý = p_i46301_6_;
        this.Ø­áŒŠá = p_i46301_4_ + p_i46301_7_;
        this.Âµá€ = p_i46301_5_ + p_i46301_8_;
        this.Ó = p_i46301_6_ + p_i46301_9_;
        this.Ø = new PositionTextureVertex[8];
        this.áŒŠÆ = new TexturedQuad[6];
        float var12 = p_i46301_4_ + p_i46301_7_;
        float var13 = p_i46301_5_ + p_i46301_8_;
        float var14 = p_i46301_6_ + p_i46301_9_;
        p_i46301_4_ -= p_i46301_10_;
        p_i46301_5_ -= p_i46301_10_;
        p_i46301_6_ -= p_i46301_10_;
        var12 += p_i46301_10_;
        var13 += p_i46301_10_;
        var14 += p_i46301_10_;
        if (p_i46301_11_) {
            final float var15 = var12;
            var12 = p_i46301_4_;
            p_i46301_4_ = var15;
        }
        final PositionTextureVertex var16 = new PositionTextureVertex(p_i46301_4_, p_i46301_5_, p_i46301_6_, 0.0f, 0.0f);
        final PositionTextureVertex var17 = new PositionTextureVertex(var12, p_i46301_5_, p_i46301_6_, 0.0f, 8.0f);
        final PositionTextureVertex var18 = new PositionTextureVertex(var12, var13, p_i46301_6_, 8.0f, 8.0f);
        final PositionTextureVertex var19 = new PositionTextureVertex(p_i46301_4_, var13, p_i46301_6_, 8.0f, 0.0f);
        final PositionTextureVertex var20 = new PositionTextureVertex(p_i46301_4_, p_i46301_5_, var14, 0.0f, 0.0f);
        final PositionTextureVertex var21 = new PositionTextureVertex(var12, p_i46301_5_, var14, 0.0f, 8.0f);
        final PositionTextureVertex var22 = new PositionTextureVertex(var12, var13, var14, 8.0f, 8.0f);
        final PositionTextureVertex var23 = new PositionTextureVertex(p_i46301_4_, var13, var14, 8.0f, 0.0f);
        this.Ø[0] = var16;
        this.Ø[1] = var17;
        this.Ø[2] = var18;
        this.Ø[3] = var19;
        this.Ø[4] = var20;
        this.Ø[5] = var21;
        this.Ø[6] = var22;
        this.Ø[7] = var23;
        this.áŒŠÆ[0] = new TexturedQuad(new PositionTextureVertex[] { var21, var17, var18, var22 }, p_i46301_2_ + p_i46301_9_ + p_i46301_7_, p_i46301_3_ + p_i46301_9_, p_i46301_2_ + p_i46301_9_ + p_i46301_7_ + p_i46301_9_, p_i46301_3_ + p_i46301_9_ + p_i46301_8_, p_i46301_1_.HorizonCode_Horizon_È, p_i46301_1_.Â);
        this.áŒŠÆ[1] = new TexturedQuad(new PositionTextureVertex[] { var16, var20, var23, var19 }, p_i46301_2_, p_i46301_3_ + p_i46301_9_, p_i46301_2_ + p_i46301_9_, p_i46301_3_ + p_i46301_9_ + p_i46301_8_, p_i46301_1_.HorizonCode_Horizon_È, p_i46301_1_.Â);
        this.áŒŠÆ[2] = new TexturedQuad(new PositionTextureVertex[] { var21, var20, var16, var17 }, p_i46301_2_ + p_i46301_9_, p_i46301_3_, p_i46301_2_ + p_i46301_9_ + p_i46301_7_, p_i46301_3_ + p_i46301_9_, p_i46301_1_.HorizonCode_Horizon_È, p_i46301_1_.Â);
        this.áŒŠÆ[3] = new TexturedQuad(new PositionTextureVertex[] { var18, var19, var23, var22 }, p_i46301_2_ + p_i46301_9_ + p_i46301_7_, p_i46301_3_ + p_i46301_9_, p_i46301_2_ + p_i46301_9_ + p_i46301_7_ + p_i46301_7_, p_i46301_3_, p_i46301_1_.HorizonCode_Horizon_È, p_i46301_1_.Â);
        this.áŒŠÆ[4] = new TexturedQuad(new PositionTextureVertex[] { var17, var16, var19, var18 }, p_i46301_2_ + p_i46301_9_, p_i46301_3_ + p_i46301_9_, p_i46301_2_ + p_i46301_9_ + p_i46301_7_, p_i46301_3_ + p_i46301_9_ + p_i46301_8_, p_i46301_1_.HorizonCode_Horizon_È, p_i46301_1_.Â);
        this.áŒŠÆ[5] = new TexturedQuad(new PositionTextureVertex[] { var20, var21, var22, var23 }, p_i46301_2_ + p_i46301_9_ + p_i46301_7_ + p_i46301_9_, p_i46301_3_ + p_i46301_9_, p_i46301_2_ + p_i46301_9_ + p_i46301_7_ + p_i46301_9_ + p_i46301_7_, p_i46301_3_ + p_i46301_9_ + p_i46301_8_, p_i46301_1_.HorizonCode_Horizon_È, p_i46301_1_.Â);
        if (p_i46301_11_) {
            for (int var24 = 0; var24 < this.áŒŠÆ.length; ++var24) {
                this.áŒŠÆ[var24].HorizonCode_Horizon_È();
            }
        }
    }
    
    public void HorizonCode_Horizon_È(final WorldRenderer p_178780_1_, final float p_178780_2_) {
        for (int var3 = 0; var3 < this.áŒŠÆ.length; ++var3) {
            this.áŒŠÆ[var3].HorizonCode_Horizon_È(p_178780_1_, p_178780_2_);
        }
    }
    
    public ModelBox HorizonCode_Horizon_È(final String p_78244_1_) {
        this.à = p_78244_1_;
        return this;
    }
}
