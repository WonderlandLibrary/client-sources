package HORIZON-6-0-SKIDPROTECTION;

public class GuiCheckBoxButton extends GuiButton
{
    public int HorizonCode_Horizon_È;
    public boolean Â;
    
    public GuiCheckBoxButton(final int buttonId, final int x, final int y, final String buttonText) {
        this(buttonId, x, y, 200, 20, buttonText);
    }
    
    public GuiCheckBoxButton(final int buttonId, final int x, final int y, final int widthIn, final int heightIn, final String buttonText) {
        super(buttonId, x, y, widthIn, heightIn, buttonText);
        this.HorizonCode_Horizon_È = 0;
    }
    
    @Override
    public void Ý(final Minecraft mc, final int mouseX, final int mouseY) {
        if (this.ˆà) {
            final FontRenderer var4 = UIFonts.Ø;
            if (!(this.¥Æ = (mouseX >= this.ˆÏ­ && mouseY >= this.£á && mouseX < this.ˆÏ­ + this.ÂµÈ && mouseY < this.£á + this.á))) {
                RenderHelper_1118140819.HorizonCode_Horizon_È(this.ˆÏ­, this.£á, this.ˆÏ­ + this.ÂµÈ, this.£á + 18, 2.0f, 2899536, 1879048192);
            }
            else {
                RenderHelper_1118140819.HorizonCode_Horizon_È(this.ˆÏ­, this.£á, this.ˆÏ­ + this.ÂµÈ, this.£á + 18, 2.0f, 2899536, Integer.MIN_VALUE);
            }
            this.HorizonCode_Horizon_È(mc, mouseX, mouseY);
            if (GuiLogin.HorizonCode_Horizon_È) {
                final int var5 = -13330213;
                this.HorizonCode_Horizon_È(var4, StringUtils.HorizonCode_Horizon_È(this.Å), this.ˆÏ­ + this.ÂµÈ / 2, this.£á + (this.á - 22) / 2, var5);
            }
        }
    }
    
    public void HorizonCode_Horizon_È(final String display) {
        this.Å = display;
    }
}
