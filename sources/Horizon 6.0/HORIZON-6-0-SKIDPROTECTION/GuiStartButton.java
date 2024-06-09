package HORIZON-6-0-SKIDPROTECTION;

import java.awt.Color;

public class GuiStartButton extends GuiButton
{
    public int HorizonCode_Horizon_È;
    
    public GuiStartButton(final int buttonId, final int x, final int y, final String buttonText) {
        this(buttonId, x, y, 200, 20, buttonText);
    }
    
    public GuiStartButton(final int buttonId, final int x, final int y, final int widthIn, final int heightIn, final String buttonText) {
        super(buttonId, x, y, widthIn, heightIn, buttonText);
        this.HorizonCode_Horizon_È = 0;
    }
    
    @Override
    public void Ý(final Minecraft mc, final int mouseX, final int mouseY) {
        final Color color = new Color(0.5019608f, 0.72156864f, 0.2784314f, GuiFirstStart.µÕ / 255.0f);
        final Color fontcolor = new Color(1.0f, 1.0f, 1.0f, GuiFirstStart.µÕ / 255.0f);
        if (this.ˆà) {
            final FontRenderer var4 = UIFonts.µÕ;
            this.¥Æ = (mouseX >= this.ˆÏ­ && mouseY >= this.£á && mouseX < this.ˆÏ­ + this.ÂµÈ && mouseY < this.£á + this.á);
            if (this.µà) {
                GuiUtils.HorizonCode_Horizon_È().HorizonCode_Horizon_È(this.ˆÏ­ + this.HorizonCode_Horizon_È, this.£á, this.ˆÏ­ + this.ÂµÈ - this.HorizonCode_Horizon_È, (float)(this.£á + this.á), color.getRGB());
            }
            this.HorizonCode_Horizon_È(mc, mouseX, mouseY);
            final int var5 = 14737632;
            this.HorizonCode_Horizon_È(var4, StringUtils.HorizonCode_Horizon_È(this.Å), this.ˆÏ­ + this.ÂµÈ / 2, this.£á + (this.á - 10) / 2, fontcolor.getRGB());
        }
    }
    
    public void HorizonCode_Horizon_È(final String display) {
        this.Å = display;
    }
}
