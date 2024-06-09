package HORIZON-6-0-SKIDPROTECTION;

import java.awt.Color;

public class GuiKeyboardButton extends GuiButton
{
    public int HorizonCode_Horizon_È;
    public int Â;
    
    public GuiKeyboardButton(final int buttonId, final int x, final int y, final String buttonText) {
        this(buttonId, x, y, 200, 20, buttonText);
    }
    
    public GuiKeyboardButton(final int buttonId, final int x, final int y, final int widthIn, final int heightIn, final String buttonText) {
        super(buttonId, x, y, widthIn, heightIn, buttonText);
        this.HorizonCode_Horizon_È = 0;
        this.Â = 80;
    }
    
    @Override
    public void Ý(final Minecraft mc, final int mouseX, final int mouseY) {
        final Color c11 = new Color(1.0f, 1.0f, 1.0f, this.Â / 255.0f);
        if (this.ˆà) {
            final FontRenderer var4 = UIFonts.µÕ;
            this.¥Æ = (mouseX >= this.ˆÏ­ && mouseY >= this.£á && mouseX < this.ˆÏ­ + this.ÂµÈ && mouseY < this.£á + this.á);
            this.HorizonCode_Horizon_È();
            if (this.HorizonCode_Horizon_È >= 4) {
                this.HorizonCode_Horizon_È = 4;
            }
            if (this.µà) {
                RenderHelper_1118140819.HorizonCode_Horizon_È(this.ˆÏ­ + this.HorizonCode_Horizon_È, this.£á, this.ˆÏ­ + this.ÂµÈ - this.HorizonCode_Horizon_È, this.£á + this.á, c11.getRGB());
            }
            else {
                RenderHelper_1118140819.HorizonCode_Horizon_È(this.ˆÏ­, this.£á, this.ˆÏ­ + this.ÂµÈ, this.£á + this.á, -1875621538);
            }
            this.HorizonCode_Horizon_È(mc, mouseX, mouseY);
            final int var5 = 14737632;
            this.HorizonCode_Horizon_È(var4, StringUtils.HorizonCode_Horizon_È(this.Å), this.ˆÏ­ + this.ÂµÈ / 2 - 1, this.£á + (this.á - 10) / 2, var5);
        }
    }
    
    public void HorizonCode_Horizon_È(final String display) {
        this.Å = display;
    }
    
    public void HorizonCode_Horizon_È() {
        if (this.µà) {
            if (this.¥Æ) {
                this.Â += 10;
                if (this.Â >= 170) {
                    this.Â = 180;
                }
            }
            else {
                this.Â -= 10;
                if (this.Â <= 80) {
                    this.Â = 80;
                }
            }
        }
    }
}
