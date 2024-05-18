package HORIZON-6-0-SKIDPROTECTION;

public class GuiListButton extends GuiButton
{
    private boolean HorizonCode_Horizon_È;
    private String Â;
    private final GuiPageButtonList.Ó Ý;
    private static final String Ø­áŒŠá = "CL_00001953";
    
    public GuiListButton(final GuiPageButtonList.Ó p_i45539_1_, final int p_i45539_2_, final int p_i45539_3_, final int p_i45539_4_, final String p_i45539_5_, final boolean p_i45539_6_) {
        super(p_i45539_2_, p_i45539_3_, p_i45539_4_, 150, 20, "");
        this.Â = p_i45539_5_;
        this.HorizonCode_Horizon_È = p_i45539_6_;
        this.Å = this.HorizonCode_Horizon_È();
        this.Ý = p_i45539_1_;
    }
    
    private String HorizonCode_Horizon_È() {
        return String.valueOf(I18n.HorizonCode_Horizon_È(this.Â, new Object[0])) + ": " + (this.HorizonCode_Horizon_È ? I18n.HorizonCode_Horizon_È("gui.yes", new Object[0]) : I18n.HorizonCode_Horizon_È("gui.no", new Object[0]));
    }
    
    public void Â(final boolean p_175212_1_) {
        this.HorizonCode_Horizon_È = p_175212_1_;
        this.Å = this.HorizonCode_Horizon_È();
        this.Ý.HorizonCode_Horizon_È(this.£à, p_175212_1_);
    }
    
    @Override
    public boolean Â(final Minecraft mc, final int mouseX, final int mouseY) {
        if (super.Â(mc, mouseX, mouseY)) {
            this.HorizonCode_Horizon_È = !this.HorizonCode_Horizon_È;
            this.Å = this.HorizonCode_Horizon_È();
            this.Ý.HorizonCode_Horizon_È(this.£à, this.HorizonCode_Horizon_È);
            return true;
        }
        return false;
    }
}
