package HORIZON-6-0-SKIDPROTECTION;

public class GuiButtonLanguage extends GuiButton
{
    private static final String HorizonCode_Horizon_È = "CL_00000672";
    
    public GuiButtonLanguage(final int p_i1041_1_, final int p_i1041_2_, final int p_i1041_3_) {
        super(p_i1041_1_, p_i1041_2_, p_i1041_3_, 20, 20, "");
    }
    
    @Override
    public void Ý(final Minecraft mc, final int mouseX, final int mouseY) {
        if (this.ˆà) {
            mc.¥à().HorizonCode_Horizon_È(GuiButton.áˆºÑ¢Õ);
            GlStateManager.Ý(1.0f, 1.0f, 1.0f, 1.0f);
            final boolean var4 = mouseX >= this.ˆÏ­ && mouseY >= this.£á && mouseX < this.ˆÏ­ + this.ÂµÈ && mouseY < this.£á + this.á;
            int var5 = 106;
            if (var4) {
                var5 += this.á;
            }
            this.Â(this.ˆÏ­, this.£á, 0, var5, this.ÂµÈ, this.á);
        }
    }
}
