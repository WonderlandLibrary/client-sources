package net.minecraft.src;

public class GuiConfirmOpenLink extends GuiYesNo
{
    private String openLinkWarning;
    private String copyLinkButtonText;
    private String field_92028_p;
    private boolean field_92027_q;
    
    public GuiConfirmOpenLink(final GuiScreen par1GuiScreen, final String par2Str, final int par3, final boolean par4) {
        super(par1GuiScreen, StringTranslate.getInstance().translateKey(par4 ? "chat.link.confirmTrusted" : "chat.link.confirm"), par2Str, par3);
        this.field_92027_q = true;
        final StringTranslate var5 = StringTranslate.getInstance();
        this.buttonText1 = var5.translateKey(par4 ? "chat.link.open" : "gui.yes");
        this.buttonText2 = var5.translateKey(par4 ? "gui.cancel" : "gui.no");
        this.copyLinkButtonText = var5.translateKey("chat.copy");
        this.openLinkWarning = var5.translateKey("chat.link.warning");
        this.field_92028_p = par2Str;
    }
    
    @Override
    public void initGui() {
        this.buttonList.add(new GuiButton(0, this.width / 3 - 83 + 0, this.height / 6 + 96, 100, 20, this.buttonText1));
        this.buttonList.add(new GuiButton(2, this.width / 3 - 83 + 105, this.height / 6 + 96, 100, 20, this.copyLinkButtonText));
        this.buttonList.add(new GuiButton(1, this.width / 3 - 83 + 210, this.height / 6 + 96, 100, 20, this.buttonText2));
    }
    
    @Override
    protected void actionPerformed(final GuiButton par1GuiButton) {
        if (par1GuiButton.id == 2) {
            this.copyLinkToClipboard();
        }
        this.parentScreen.confirmClicked(par1GuiButton.id == 0, this.worldNumber);
    }
    
    public void copyLinkToClipboard() {
        GuiScreen.setClipboardString(this.field_92028_p);
    }
    
    @Override
    public void drawScreen(final int par1, final int par2, final float par3) {
        super.drawScreen(par1, par2, par3);
        if (this.field_92027_q) {
            this.drawCenteredString(this.fontRenderer, this.openLinkWarning, this.width / 2, 110, 16764108);
        }
    }
    
    public void func_92026_h() {
        this.field_92027_q = false;
    }
}
