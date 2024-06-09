package HORIZON-6-0-SKIDPROTECTION;

public class GuiRoundMetroButton extends GuiButton
{
    public int HorizonCode_Horizon_È;
    
    public GuiRoundMetroButton(final int buttonId, final int x, final int y, final String buttonText) {
        this(buttonId, x, y, 200, 20, buttonText);
    }
    
    public GuiRoundMetroButton(final int buttonId, final int x, final int y, final int widthIn, final int heightIn, final String buttonText) {
        super(buttonId, x, y, widthIn, heightIn, buttonText);
        this.HorizonCode_Horizon_È = 0;
    }
    
    @Override
    public void Ý(final Minecraft mc, final int mouseX, final int mouseY) {
        if (this.ˆà) {
            final FontRenderer var4 = UIFonts.Âµá€;
            if (!(this.¥Æ = (mouseX >= this.ˆÏ­ && mouseY >= this.£á && mouseX < this.ˆÏ­ + this.ÂµÈ && mouseY < this.£á + this.á))) {
                --this.HorizonCode_Horizon_È;
                if (this.HorizonCode_Horizon_È <= 0) {
                    this.HorizonCode_Horizon_È = 0;
                }
                ModGuiUtils.HorizonCode_Horizon_È(this.ˆÏ­ + 10, this.£á + 11, (double)(11 + (this.HorizonCode_Horizon_È + 2) / 2), 360, -2130706433);
                ModGuiUtils.HorizonCode_Horizon_È(this.ˆÏ­ + 10, this.£á + 11, 7 + (this.HorizonCode_Horizon_È + 2) / 2, 1358954495);
            }
            else {
                ++this.HorizonCode_Horizon_È;
                if (this.HorizonCode_Horizon_È >= 4) {
                    this.HorizonCode_Horizon_È = 4;
                }
                ModGuiUtils.HorizonCode_Horizon_È(this.ˆÏ­ + 10, this.£á + 11, (double)(11 + (this.HorizonCode_Horizon_È + 2) / 2), 360, -1);
                ModGuiUtils.HorizonCode_Horizon_È(this.ˆÏ­ + 10, this.£á + 11, 7 + (this.HorizonCode_Horizon_È + 2) / 2, -1);
            }
            this.HorizonCode_Horizon_È(mc, mouseX, mouseY);
            final int var5 = 14737632;
            this.HorizonCode_Horizon_È(var4, StringUtils.HorizonCode_Horizon_È(this.Å), this.ˆÏ­ + this.ÂµÈ / 2, this.£á + (this.á - 12) / 2, var5);
        }
    }
    
    public void HorizonCode_Horizon_È(final String display) {
        this.Å = display;
    }
}
