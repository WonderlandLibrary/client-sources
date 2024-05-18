package HORIZON-6-0-SKIDPROTECTION;

public abstract class TileEntitySpecialRenderer
{
    protected static final ResourceLocation_1975012498[] Â;
    protected TileEntityRendererDispatcher Ý;
    private static final String HorizonCode_Horizon_È = "CL_00000964";
    
    static {
        Â = new ResourceLocation_1975012498[] { new ResourceLocation_1975012498("textures/blocks/destroy_stage_0.png"), new ResourceLocation_1975012498("textures/blocks/destroy_stage_1.png"), new ResourceLocation_1975012498("textures/blocks/destroy_stage_2.png"), new ResourceLocation_1975012498("textures/blocks/destroy_stage_3.png"), new ResourceLocation_1975012498("textures/blocks/destroy_stage_4.png"), new ResourceLocation_1975012498("textures/blocks/destroy_stage_5.png"), new ResourceLocation_1975012498("textures/blocks/destroy_stage_6.png"), new ResourceLocation_1975012498("textures/blocks/destroy_stage_7.png"), new ResourceLocation_1975012498("textures/blocks/destroy_stage_8.png"), new ResourceLocation_1975012498("textures/blocks/destroy_stage_9.png") };
    }
    
    public abstract void HorizonCode_Horizon_È(final TileEntity p0, final double p1, final double p2, final double p3, final float p4, final int p5);
    
    protected void HorizonCode_Horizon_È(final ResourceLocation_1975012498 p_147499_1_) {
        final TextureManager var2 = this.Ý.Âµá€;
        if (var2 != null) {
            var2.HorizonCode_Horizon_È(p_147499_1_);
        }
    }
    
    protected World HorizonCode_Horizon_È() {
        return this.Ý.Ó;
    }
    
    public void HorizonCode_Horizon_È(final TileEntityRendererDispatcher p_147497_1_) {
        this.Ý = p_147497_1_;
    }
    
    public FontRenderer Â() {
        return this.Ý.HorizonCode_Horizon_È();
    }
}
