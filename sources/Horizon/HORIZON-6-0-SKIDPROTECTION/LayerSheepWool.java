package HORIZON-6-0-SKIDPROTECTION;

public class LayerSheepWool implements LayerRenderer
{
    private static final ResourceLocation_1975012498 HorizonCode_Horizon_È;
    private final RenderSheep Â;
    private final ModelSheep1 Ý;
    private static final String Ø­áŒŠá = "CL_00002413";
    
    static {
        HorizonCode_Horizon_È = new ResourceLocation_1975012498("textures/entity/sheep/sheep_fur.png");
    }
    
    public LayerSheepWool(final RenderSheep p_i46112_1_) {
        this.Ý = new ModelSheep1();
        this.Â = p_i46112_1_;
    }
    
    public void HorizonCode_Horizon_È(final EntitySheep p_177162_1_, final float p_177162_2_, final float p_177162_3_, final float p_177162_4_, final float p_177162_5_, final float p_177162_6_, final float p_177162_7_, final float p_177162_8_) {
        if (!p_177162_1_.¥Ê() && !p_177162_1_.áŒŠÏ()) {
            this.Â.HorizonCode_Horizon_È(LayerSheepWool.HorizonCode_Horizon_È);
            if (p_177162_1_.j_() && "jeb_".equals(p_177162_1_.Šà())) {
                final boolean var17 = true;
                final int var18 = p_177162_1_.Œ / 25 + p_177162_1_.ˆá();
                final int var19 = EnumDyeColor.values().length;
                final int var20 = var18 % var19;
                final int var21 = (var18 + 1) % var19;
                final float var22 = (p_177162_1_.Œ % 25 + p_177162_4_) / 25.0f;
                final float[] var23 = EntitySheep.HorizonCode_Horizon_È(EnumDyeColor.Â(var20));
                final float[] var24 = EntitySheep.HorizonCode_Horizon_È(EnumDyeColor.Â(var21));
                GlStateManager.Ý(var23[0] * (1.0f - var22) + var24[0] * var22, var23[1] * (1.0f - var22) + var24[1] * var22, var23[2] * (1.0f - var22) + var24[2] * var22);
            }
            else {
                final float[] var25 = EntitySheep.HorizonCode_Horizon_È(p_177162_1_.ÐƒÇŽà());
                GlStateManager.Ý(var25[0], var25[1], var25[2]);
            }
            this.Ý.HorizonCode_Horizon_È(this.Â.Â());
            this.Ý.HorizonCode_Horizon_È(p_177162_1_, p_177162_2_, p_177162_3_, p_177162_4_);
            this.Ý.HorizonCode_Horizon_È(p_177162_1_, p_177162_2_, p_177162_3_, p_177162_5_, p_177162_6_, p_177162_7_, p_177162_8_);
        }
    }
    
    @Override
    public boolean Â() {
        return true;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final EntityLivingBase p_177141_1_, final float p_177141_2_, final float p_177141_3_, final float p_177141_4_, final float p_177141_5_, final float p_177141_6_, final float p_177141_7_, final float p_177141_8_) {
        this.HorizonCode_Horizon_È((EntitySheep)p_177141_1_, p_177141_2_, p_177141_3_, p_177141_4_, p_177141_5_, p_177141_6_, p_177141_7_, p_177141_8_);
    }
}
