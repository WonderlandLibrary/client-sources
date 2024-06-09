package HORIZON-6-0-SKIDPROTECTION;

public class GuiMainMenuButton extends GuiButton
{
    public int HorizonCode_Horizon_È;
    public int Â;
    public boolean Ý;
    
    public GuiMainMenuButton(final int buttonId, final int x, final int y, final String buttonText) {
        this(buttonId, x, y, 200, 20, buttonText);
    }
    
    public GuiMainMenuButton(final int buttonId, final int x, final int y, final int widthIn, final int heightIn, final String buttonText) {
        super(buttonId, x, y, widthIn, heightIn, buttonText);
        this.HorizonCode_Horizon_È = 0;
        this.Â = 0;
        this.Ý = false;
    }
    
    @Override
    public void Ý(final Minecraft mc, final int mouseX, final int mouseY) {
        if (this.ˆà) {
            final FontRenderer var4 = UIFonts.£á;
            if (!(this.¥Æ = (mouseX >= this.ˆÏ­ && mouseY >= this.£á && mouseX < this.ˆÏ­ + this.ÂµÈ && mouseY < this.£á + this.á))) {
                --this.HorizonCode_Horizon_È;
                if (this.HorizonCode_Horizon_È <= 0) {
                    this.HorizonCode_Horizon_È = 0;
                }
                this.Ý = false;
                if (this.µà) {
                    GuiUtils.HorizonCode_Horizon_È().HorizonCode_Horizon_È(this.ˆÏ­ - this.HorizonCode_Horizon_È / 2, this.£á, this.ˆÏ­ + this.ÂµÈ + this.HorizonCode_Horizon_È / 2, (float)(this.£á + this.á), 1879048192);
                }
                else {
                    GuiUtils.HorizonCode_Horizon_È().HorizonCode_Horizon_È(this.ˆÏ­ - this.HorizonCode_Horizon_È / 2, this.£á, this.ˆÏ­ + this.ÂµÈ + this.HorizonCode_Horizon_È / 2, (float)(this.£á + this.á), -2144584112);
                }
            }
            else {
                ++this.HorizonCode_Horizon_È;
                if (this.HorizonCode_Horizon_È >= 4) {
                    this.HorizonCode_Horizon_È = 4;
                }
                if (!this.Ý && !GuiMainMenu.áŒŠÆ) {
                    Horizon.à¢.áˆºáˆºÈ.HorizonCode_Horizon_È(Horizon.à¢.áŒŠ + "/mainmenu/menuclick.wav");
                }
                Horizon.à¢.áˆºáˆºÈ.HorizonCode_Horizon_È(-18.0f);
                this.Ý = true;
                if (this.µà) {
                    GuiUtils.HorizonCode_Horizon_È().HorizonCode_Horizon_È(this.ˆÏ­ - this.HorizonCode_Horizon_È / 2, this.£á, this.ˆÏ­ + this.ÂµÈ + this.HorizonCode_Horizon_È / 2, (float)(this.£á + this.á), -1728053248);
                }
                else {
                    GuiUtils.HorizonCode_Horizon_È().HorizonCode_Horizon_È(this.ˆÏ­ - this.HorizonCode_Horizon_È / 2, this.£á, this.ˆÏ­ + this.ÂµÈ + this.HorizonCode_Horizon_È / 2, (float)(this.£á + this.á), -2144584112);
                }
            }
            this.HorizonCode_Horizon_È(mc, mouseX, mouseY);
            final int var5 = -4340793;
            this.HorizonCode_Horizon_È(var4, StringUtils.HorizonCode_Horizon_È(this.Å), this.ˆÏ­ + this.ÂµÈ / 2, this.£á + (this.á - 10) / 2, var5);
        }
    }
    
    public void HorizonCode_Horizon_È(final String display) {
        this.Å = display;
    }
}
