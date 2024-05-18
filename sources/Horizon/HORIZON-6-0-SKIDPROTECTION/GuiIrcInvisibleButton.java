package HORIZON-6-0-SKIDPROTECTION;

public class GuiIrcInvisibleButton extends GuiButton
{
    public int HorizonCode_Horizon_È;
    
    public GuiIrcInvisibleButton(final int buttonId, final int x, final int y, final String buttonText) {
        this(buttonId, x, y, 200, 20, buttonText);
    }
    
    public GuiIrcInvisibleButton(final int buttonId, final int x, final int y, final int widthIn, final int heightIn, final String buttonText) {
        super(buttonId, x, y, widthIn, heightIn, buttonText);
        this.HorizonCode_Horizon_È = 0;
    }
    
    @Override
    public void Ý(final Minecraft mc, final int mouseX, final int mouseY) {
        if (this.ˆà) {
            final FontRenderer var4 = UIFonts.ˆÏ­;
            this.¥Æ = (mouseX >= this.ˆÏ­ && mouseY >= this.£á && mouseX < this.ˆÏ­ + this.ÂµÈ && mouseY < this.£á + this.á);
            this.HorizonCode_Horizon_È(mc, mouseX, mouseY);
            final int var5 = 14737632;
            GuiUtils.HorizonCode_Horizon_È().HorizonCode_Horizon_È(this.ˆÏ­ - this.HorizonCode_Horizon_È / 2 - 1, this.£á - this.HorizonCode_Horizon_È / 2 - 1, this.ˆÏ­ + this.ÂµÈ + this.HorizonCode_Horizon_È / 2, (float)(this.£á + this.á + this.HorizonCode_Horizon_È / 2), 536870912);
            this.HorizonCode_Horizon_È(var4, StringUtils.HorizonCode_Horizon_È(this.Å), this.ˆÏ­ + this.ÂµÈ / 2, this.£á + (this.á - 9) / 2, -13815242);
        }
    }
    
    public void HorizonCode_Horizon_È(final String display) {
        this.Å = display;
    }
}
