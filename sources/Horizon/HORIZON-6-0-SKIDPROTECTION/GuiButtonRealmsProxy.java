package HORIZON-6-0-SKIDPROTECTION;

public class GuiButtonRealmsProxy extends GuiButton
{
    private RealmsButton HorizonCode_Horizon_È;
    private static final String Â = "CL_00001848";
    
    public GuiButtonRealmsProxy(final RealmsButton p_i46321_1_, final int p_i46321_2_, final int p_i46321_3_, final int p_i46321_4_, final String p_i46321_5_) {
        super(p_i46321_2_, p_i46321_3_, p_i46321_4_, p_i46321_5_);
        this.HorizonCode_Horizon_È = p_i46321_1_;
    }
    
    public GuiButtonRealmsProxy(final RealmsButton p_i1090_1_, final int p_i1090_2_, final int p_i1090_3_, final int p_i1090_4_, final String p_i1090_5_, final int p_i1090_6_, final int p_i1090_7_) {
        super(p_i1090_2_, p_i1090_3_, p_i1090_4_, p_i1090_6_, p_i1090_7_, p_i1090_5_);
        this.HorizonCode_Horizon_È = p_i1090_1_;
    }
    
    public int HorizonCode_Horizon_È() {
        return super.£à;
    }
    
    public boolean Â() {
        return super.µà;
    }
    
    public void Â(final boolean p_154313_1_) {
        super.µà = p_154313_1_;
    }
    
    public void HorizonCode_Horizon_È(final String p_154311_1_) {
        super.Å = p_154311_1_;
    }
    
    @Override
    public int Ø­áŒŠá() {
        return super.Ø­áŒŠá();
    }
    
    public int Âµá€() {
        return super.£á;
    }
    
    @Override
    public boolean Â(final Minecraft mc, final int mouseX, final int mouseY) {
        if (super.Â(mc, mouseX, mouseY)) {
            this.HorizonCode_Horizon_È.Â(mouseX, mouseY);
        }
        return super.Â(mc, mouseX, mouseY);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final int mouseX, final int mouseY) {
        this.HorizonCode_Horizon_È.Ý(mouseX, mouseY);
    }
    
    public void HorizonCode_Horizon_È(final Minecraft mc, final int mouseX, final int mouseY) {
        this.HorizonCode_Horizon_È.Ø­áŒŠá(mouseX, mouseY);
    }
    
    public RealmsButton Ó() {
        return this.HorizonCode_Horizon_È;
    }
    
    public int HorizonCode_Horizon_È(final boolean mouseOver) {
        return this.HorizonCode_Horizon_È.Â(mouseOver);
    }
    
    public int Ý(final boolean p_154312_1_) {
        return super.HorizonCode_Horizon_È(p_154312_1_);
    }
    
    public int à() {
        return this.á;
    }
}
