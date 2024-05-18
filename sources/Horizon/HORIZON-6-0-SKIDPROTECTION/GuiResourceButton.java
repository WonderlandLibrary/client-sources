package HORIZON-6-0-SKIDPROTECTION;

import java.awt.Color;

public class GuiResourceButton extends GuiButton
{
    private ResourceLocation_1975012498 HorizonCode_Horizon_È;
    private int Â;
    private float Ý;
    
    public GuiResourceButton(final int buttonId, final int x, final int y, final int widthIn, final int heightIn, final String buttonText, final ResourceLocation_1975012498 resLoc) {
        super(buttonId, x, y, widthIn, heightIn, buttonText);
        this.Ý = 120.0f;
        this.HorizonCode_Horizon_È = resLoc;
        this.Â = -1;
    }
    
    public GuiResourceButton(final int buttonId, final int x, final int y, final String buttonText, final ResourceLocation_1975012498 resLoc) {
        super(buttonId, x, y, buttonText);
        this.Ý = 120.0f;
        this.HorizonCode_Horizon_È = resLoc;
        this.Â = -1;
    }
    
    public GuiResourceButton(final int buttonId, final int x, final int y, final int widthIn, final int heightIn, final String buttonText, final ResourceLocation_1975012498 resLoc, final int color) {
        super(buttonId, x, y, widthIn, heightIn, buttonText);
        this.Ý = 120.0f;
        this.HorizonCode_Horizon_È = resLoc;
        this.Â = color;
    }
    
    public GuiResourceButton(final int buttonId, final int x, final int y, final String buttonText, final ResourceLocation_1975012498 resLoc, final int color) {
        super(buttonId, x, y, buttonText);
        this.Ý = 120.0f;
        this.HorizonCode_Horizon_È = resLoc;
        this.Â = color;
    }
    
    @Override
    public void Ý(final Minecraft mc, final int mouseX, final int mouseY) {
        if (GuiMainMenu.áŒŠÆ) {
            this.µà = false;
        }
        else {
            this.µà = true;
        }
        this.¥Æ = (mouseX >= this.ˆÏ­ && mouseY >= this.£á && mouseX < this.ˆÏ­ + this.ÂµÈ && mouseY < this.£á + this.á);
        this.HorizonCode_Horizon_È();
        final Color color2 = new Color(1.0f, 1.0f, 1.0f, this.Ý / 255.0f);
        if (this.ˆà) {
            mc.¥à().HorizonCode_Horizon_È(this.HorizonCode_Horizon_È);
            Gui_1808253012.HorizonCode_Horizon_È(this.ˆÏ­, this.£á, 0.0f, 0.0f, this.ÂµÈ, this.á, this.ÂµÈ, this.á, this.ÂµÈ, this.á);
            UIFonts.Ø­áŒŠá.HorizonCode_Horizon_È(this.Å, this.ˆÏ­ + this.ÂµÈ / 2 - UIFonts.Ø­áŒŠá.HorizonCode_Horizon_È(this.Å) / 2 - 1, this.£á + this.á, color2.getRGB());
        }
    }
    
    public void HorizonCode_Horizon_È() {
        if (this.µà) {
            if (this.¥Æ) {
                this.Ý += 13.0f;
                if (this.Ý >= 250.0f) {
                    this.Ý = 255.0f;
                }
            }
            else {
                this.Ý -= 13.0f;
                if (this.Ý <= 120.0f) {
                    this.Ý = 120.0f;
                }
            }
        }
    }
}
