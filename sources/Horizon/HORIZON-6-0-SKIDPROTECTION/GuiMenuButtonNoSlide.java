package HORIZON-6-0-SKIDPROTECTION;

import java.awt.Color;

public class GuiMenuButtonNoSlide extends GuiMenuButton
{
    public int Ó;
    public int à;
    
    public GuiMenuButtonNoSlide(final int buttonId, final int x, final int y, final String buttonText) {
        this(buttonId, x, y, 200, 20, buttonText);
    }
    
    public GuiMenuButtonNoSlide(final int buttonId, final int x, final int y, final int widthIn, final int heightIn, final String buttonText) {
        super(buttonId, x, y, widthIn, heightIn, buttonText);
        this.Ó = 0;
        this.à = 120;
    }
    
    @Override
    public void Ý(final Minecraft mc, final int mouseX, final int mouseY) {
        if (this.ˆà) {
            final Color col = new Color(0.1f, 0.1f, 0.1f, this.à / 255.0f);
            final FontRenderer var4 = UIFonts.£á;
            this.¥Æ = (mouseX >= this.ˆÏ­ && mouseY >= this.£á && mouseX < this.ˆÏ­ + this.ÂµÈ && mouseY < this.£á + this.á);
            this.HorizonCode_Horizon_È();
            if (!this.¥Æ) {
                GuiUtils.HorizonCode_Horizon_È().HorizonCode_Horizon_È(this.ˆÏ­ + this.Ó, this.£á, this.ˆÏ­ + this.ÂµÈ - this.Ó, (float)(this.£á + this.á), col.getRGB());
            }
            else {
                GuiUtils.HorizonCode_Horizon_È().HorizonCode_Horizon_È(this.ˆÏ­ + this.Ó, this.£á, this.ˆÏ­ + this.ÂµÈ - this.Ó, (float)(this.£á + this.á), col.getRGB());
            }
            this.HorizonCode_Horizon_È(mc, mouseX, mouseY);
            final int var5 = 14737632;
            this.HorizonCode_Horizon_È(var4, StringUtils.HorizonCode_Horizon_È(this.Å), this.ˆÏ­ + this.ÂµÈ / 2, this.£á + (this.á - 9) / 2, var5);
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final String display) {
        this.Å = display;
    }
    
    @Override
    public void HorizonCode_Horizon_È() {
        if (this.µà) {
            if (this.¥Æ) {
                this.à += 30;
                if (this.à >= 245) {
                    this.à = 255;
                }
            }
            else {
                this.à -= 30;
                if (this.à <= 120) {
                    this.à = 120;
                }
            }
        }
    }
}
