package HORIZON-6-0-SKIDPROTECTION;

public class GuiButton extends Gui_1808253012
{
    protected static final ResourceLocation_1975012498 áˆºÑ¢Õ;
    protected int ÂµÈ;
    protected int á;
    public int ˆÏ­;
    public int £á;
    public String Å;
    public int £à;
    public boolean µà;
    public boolean ˆà;
    protected boolean ¥Æ;
    private static final String HorizonCode_Horizon_È = "CL_00000668";
    
    static {
        áˆºÑ¢Õ = new ResourceLocation_1975012498("textures/gui/widgets.png");
    }
    
    public GuiButton(final int buttonId, final int x, final int y, final String buttonText) {
        this(buttonId, x, y, 200, 20, buttonText);
    }
    
    public GuiButton(final int buttonId, final int x, final int y, final int widthIn, final int heightIn, final String buttonText) {
        this.ÂµÈ = 200;
        this.á = 20;
        this.µà = true;
        this.ˆà = true;
        this.£à = buttonId;
        this.ˆÏ­ = x;
        this.£á = y;
        this.ÂµÈ = widthIn;
        this.á = heightIn;
        this.Å = buttonText;
    }
    
    protected int HorizonCode_Horizon_È(final boolean mouseOver) {
        byte var2 = 1;
        if (!this.µà) {
            var2 = 0;
        }
        else if (mouseOver) {
            var2 = 2;
        }
        return var2;
    }
    
    public void Ý(final Minecraft mc, final int mouseX, final int mouseY) {
        if (this.ˆà) {
            final FontRenderer var4 = mc.µà;
            mc.¥à().HorizonCode_Horizon_È(GuiButton.áˆºÑ¢Õ);
            GlStateManager.Ý(1.0f, 1.0f, 1.0f, 1.0f);
            this.¥Æ = (mouseX >= this.ˆÏ­ && mouseY >= this.£á && mouseX < this.ˆÏ­ + this.ÂµÈ && mouseY < this.£á + this.á);
            final int var5 = this.HorizonCode_Horizon_È(this.¥Æ);
            GlStateManager.á();
            GlStateManager.HorizonCode_Horizon_È(770, 771, 1, 0);
            GlStateManager.Â(770, 771);
            this.Â(this.ˆÏ­, this.£á, 0, 46 + var5 * 20, this.ÂµÈ / 2, this.á);
            this.Â(this.ˆÏ­ + this.ÂµÈ / 2, this.£á, 200 - this.ÂµÈ / 2, 46 + var5 * 20, this.ÂµÈ / 2, this.á);
            this.HorizonCode_Horizon_È(mc, mouseX, mouseY);
            int var6 = 14737632;
            if (!this.µà) {
                var6 = 10526880;
            }
            else if (this.¥Æ) {
                var6 = 16777120;
            }
            this.HorizonCode_Horizon_È(var4, this.Å, this.ˆÏ­ + this.ÂµÈ / 2, this.£á + (this.á - 8) / 2, var6);
        }
    }
    
    protected void HorizonCode_Horizon_È(final Minecraft mc, final int mouseX, final int mouseY) {
    }
    
    public void HorizonCode_Horizon_È(final int mouseX, final int mouseY) {
    }
    
    public boolean Â(final Minecraft mc, final int mouseX, final int mouseY) {
        return this.µà && this.ˆà && mouseX >= this.ˆÏ­ && mouseY >= this.£á && mouseX < this.ˆÏ­ + this.ÂµÈ && mouseY < this.£á + this.á;
    }
    
    public boolean Ý() {
        return this.¥Æ;
    }
    
    public void Â(final int mouseX, final int mouseY) {
    }
    
    public void HorizonCode_Horizon_È(final SoundHandler soundHandlerIn) {
        soundHandlerIn.HorizonCode_Horizon_È(PositionedSoundRecord.HorizonCode_Horizon_È(new ResourceLocation_1975012498("gui.button.press"), 1.0f));
    }
    
    public int Ø­áŒŠá() {
        return this.ÂµÈ;
    }
    
    public void HorizonCode_Horizon_È(final int p_175211_1_) {
        this.ÂµÈ = p_175211_1_;
    }
}
