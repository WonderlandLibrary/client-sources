package HORIZON-6-0-SKIDPROTECTION;

public class RealmsButton
{
    protected static final ResourceLocation_1975012498 HorizonCode_Horizon_È;
    private GuiButtonRealmsProxy Â;
    private static final String Ý = "CL_00001890";
    
    static {
        HorizonCode_Horizon_È = new ResourceLocation_1975012498("textures/gui/widgets.png");
    }
    
    public RealmsButton(final int p_i1177_1_, final int p_i1177_2_, final int p_i1177_3_, final String p_i1177_4_) {
        this.Â = new GuiButtonRealmsProxy(this, p_i1177_1_, p_i1177_2_, p_i1177_3_, p_i1177_4_);
    }
    
    public RealmsButton(final int p_i1178_1_, final int p_i1178_2_, final int p_i1178_3_, final int p_i1178_4_, final int p_i1178_5_, final String p_i1178_6_) {
        this.Â = new GuiButtonRealmsProxy(this, p_i1178_1_, p_i1178_2_, p_i1178_3_, p_i1178_6_, p_i1178_4_, p_i1178_5_);
    }
    
    public GuiButton HorizonCode_Horizon_È() {
        return this.Â;
    }
    
    public int Â() {
        return this.Â.HorizonCode_Horizon_È();
    }
    
    public boolean Ý() {
        return this.Â.Â();
    }
    
    public void HorizonCode_Horizon_È(final boolean p_active_1_) {
        this.Â.Â(p_active_1_);
    }
    
    public void HorizonCode_Horizon_È(final String p_msg_1_) {
        this.Â.HorizonCode_Horizon_È(p_msg_1_);
    }
    
    public int Ø­áŒŠá() {
        return this.Â.Ø­áŒŠá();
    }
    
    public int Âµá€() {
        return this.Â.à();
    }
    
    public int Ó() {
        return this.Â.Âµá€();
    }
    
    public void HorizonCode_Horizon_È(final int p_render_1_, final int p_render_2_) {
        this.Â.Ý(Minecraft.áŒŠà(), p_render_1_, p_render_2_);
    }
    
    public void Â(final int p_clicked_1_, final int p_clicked_2_) {
    }
    
    public void Ý(final int p_released_1_, final int p_released_2_) {
    }
    
    public void HorizonCode_Horizon_È(final int p_blit_1_, final int p_blit_2_, final int p_blit_3_, final int p_blit_4_, final int p_blit_5_, final int p_blit_6_) {
        this.Â.Â(p_blit_1_, p_blit_2_, p_blit_3_, p_blit_4_, p_blit_5_, p_blit_6_);
    }
    
    public void Ø­áŒŠá(final int p_renderBg_1_, final int p_renderBg_2_) {
    }
    
    public int Â(final boolean p_getYImage_1_) {
        return this.Â.Ý(p_getYImage_1_);
    }
}
