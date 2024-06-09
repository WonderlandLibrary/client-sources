package HORIZON-6-0-SKIDPROTECTION;

public class GuiWebdingsButton extends GuiButton
{
    public int HorizonCode_Horizon_È;
    
    public GuiWebdingsButton(final int buttonId, final int x, final int y, final String buttonText) {
        this(buttonId, x, y, 200, 20, buttonText);
    }
    
    public GuiWebdingsButton(final int buttonId, final int x, final int y, final int widthIn, final int heightIn, final String buttonText) {
        super(buttonId, x, y, widthIn, heightIn, buttonText);
        this.HorizonCode_Horizon_È = 0;
    }
    
    @Override
    public void Ý(final Minecraft mc, final int mouseX, final int mouseY) {
        if (this.ˆà) {
            final FontRenderer var4 = UIFonts.Â;
            if (!(this.¥Æ = (mouseX >= this.ˆÏ­ && mouseY >= this.£á && mouseX < this.ˆÏ­ + this.ÂµÈ && mouseY < this.£á + this.á))) {
                GuiUtils.HorizonCode_Horizon_È().HorizonCode_Horizon_È(this.ˆÏ­ + this.HorizonCode_Horizon_È, this.£á, this.ˆÏ­ + this.ÂµÈ - this.HorizonCode_Horizon_È, (float)(this.£á + this.á), -1618884);
            }
            else {
                GuiUtils.HorizonCode_Horizon_È().HorizonCode_Horizon_È(this.ˆÏ­ + this.HorizonCode_Horizon_È, this.£á, this.ˆÏ­ + this.ÂµÈ - this.HorizonCode_Horizon_È, (float)(this.£á + this.á), -1618884);
            }
            this.HorizonCode_Horizon_È(mc, mouseX, mouseY);
            final int var5 = 14737632;
            this.HorizonCode_Horizon_È(var4, StringUtils.HorizonCode_Horizon_È(this.Å), this.ˆÏ­ + this.ÂµÈ / 2, this.£á + (this.á - 18) / 2, var5);
        }
    }
    
    public void HorizonCode_Horizon_È(final String display) {
        this.Å = display;
    }
}
