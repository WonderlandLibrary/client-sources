package HORIZON-6-0-SKIDPROTECTION;

import java.awt.Color;

public class GuiMenuButton extends GuiButton
{
    public int Ø;
    public int áŒŠÆ;
    
    public GuiMenuButton(final int buttonId, final int x, final int y, final String buttonText) {
        this(buttonId, x, y, 200, 20, buttonText);
    }
    
    public GuiMenuButton(final int buttonId, final int x, final int y, final int widthIn, final int heightIn, final String buttonText) {
        super(buttonId, x, y, widthIn, heightIn, buttonText);
        this.Ø = 0;
        this.áŒŠÆ = 120;
    }
    
    @Override
    public void Ý(final Minecraft mc, final int mouseX, final int mouseY) {
        if (this.ˆà) {
            final Color col = new Color(0.1f, 0.1f, 0.1f, this.áŒŠÆ / 255.0f);
            final FontRenderer var4 = UIFonts.£á;
            this.HorizonCode_Horizon_È();
            if (!(this.¥Æ = (mouseX >= this.ˆÏ­ && mouseY >= this.£á && mouseX < this.ˆÏ­ + this.ÂµÈ && mouseY < this.£á + this.á))) {
                if (this.µà) {
                    GuiUtils.HorizonCode_Horizon_È().HorizonCode_Horizon_È(this.ˆÏ­ + this.Ø, this.£á, this.ˆÏ­ + this.ÂµÈ - this.Ø, (float)(this.£á + this.á), col.getRGB());
                }
                else {
                    GuiUtils.HorizonCode_Horizon_È().HorizonCode_Horizon_È(this.ˆÏ­ + this.Ø, this.£á, this.ˆÏ­ + this.ÂµÈ - this.Ø, (float)(this.£á + this.á), -2144584112);
                }
            }
            else if (this.µà) {
                GuiUtils.HorizonCode_Horizon_È().HorizonCode_Horizon_È(this.ˆÏ­ + this.Ø, this.£á, this.ˆÏ­ + this.ÂµÈ - this.Ø, (float)(this.£á + this.á), col.getRGB());
            }
            else {
                GuiUtils.HorizonCode_Horizon_È().HorizonCode_Horizon_È(this.ˆÏ­ + this.Ø, this.£á, this.ˆÏ­ + this.ÂµÈ - this.Ø, (float)(this.£á + this.á), -2144584112);
            }
            this.HorizonCode_Horizon_È(mc, mouseX, mouseY);
            final int var5 = 14737632;
            this.HorizonCode_Horizon_È(var4, StringUtils.HorizonCode_Horizon_È(this.Å), this.ˆÏ­ + this.ÂµÈ / 2, this.£á + (this.á - 10) / 2, var5);
        }
    }
    
    public void HorizonCode_Horizon_È(final String display) {
        this.Å = display;
    }
    
    public void HorizonCode_Horizon_È() {
        if (this.µà) {
            if (this.¥Æ) {
                this.áŒŠÆ += 30;
                if (this.áŒŠÆ >= 245) {
                    this.áŒŠÆ = 255;
                }
            }
            else {
                this.áŒŠÆ -= 30;
                if (this.áŒŠÆ <= 120) {
                    this.áŒŠÆ = 120;
                }
            }
        }
    }
}
