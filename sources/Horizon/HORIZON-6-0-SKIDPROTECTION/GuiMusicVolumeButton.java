package HORIZON-6-0-SKIDPROTECTION;

public class GuiMusicVolumeButton extends GuiButton
{
    public int HorizonCode_Horizon_È;
    private String Â;
    
    public GuiMusicVolumeButton(final int buttonId, final int x, final int y, final String buttonText) {
        this(buttonId, x, y, 200, 20, buttonText);
    }
    
    public GuiMusicVolumeButton(final int buttonId, final int x, final int y, final int widthIn, final int heightIn, final String buttonText) {
        super(buttonId, x, y, widthIn, heightIn, buttonText);
        this.HorizonCode_Horizon_È = 0;
        this.Â = "";
    }
    
    @Override
    public void Ý(final Minecraft mc, final int mouseX, final int mouseY) {
        if (this.ˆà) {
            final FontRenderer var4 = UIFonts.Âµá€;
            this.¥Æ = (mouseX >= this.ˆÏ­ && mouseY >= this.£á && mouseX < this.ˆÏ­ + this.ÂµÈ && mouseY < this.£á + this.á);
            GuiUtils.HorizonCode_Horizon_È().HorizonCode_Horizon_È(this.ˆÏ­ + this.HorizonCode_Horizon_È, this.£á, this.ˆÏ­ + this.ÂµÈ - this.HorizonCode_Horizon_È, (float)(this.£á + this.á), 536870912);
            this.HorizonCode_Horizon_È(mc, mouseX, mouseY);
            final int var5 = 14737632;
            this.HorizonCode_Horizon_È(var4, StringUtils.HorizonCode_Horizon_È(this.Å), this.ˆÏ­ + this.ÂµÈ / 2, this.£á + (this.á - 12) / 2, var5);
        }
    }
    
    public void HorizonCode_Horizon_È(final String display) {
        this.Å = display;
    }
    
    public String HorizonCode_Horizon_È() {
        return this.Â;
    }
}
