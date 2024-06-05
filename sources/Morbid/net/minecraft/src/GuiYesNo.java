package net.minecraft.src;

public class GuiYesNo extends GuiScreen
{
    protected GuiScreen parentScreen;
    protected String message1;
    private String message2;
    protected String buttonText1;
    protected String buttonText2;
    protected int worldNumber;
    
    public GuiYesNo(final GuiScreen par1GuiScreen, final String par2Str, final String par3Str, final int par4) {
        this.parentScreen = par1GuiScreen;
        this.message1 = par2Str;
        this.message2 = par3Str;
        this.worldNumber = par4;
        final StringTranslate var5 = StringTranslate.getInstance();
        this.buttonText1 = var5.translateKey("gui.yes");
        this.buttonText2 = var5.translateKey("gui.no");
    }
    
    public GuiYesNo(final GuiScreen par1GuiScreen, final String par2Str, final String par3Str, final String par4Str, final String par5Str, final int par6) {
        this.parentScreen = par1GuiScreen;
        this.message1 = par2Str;
        this.message2 = par3Str;
        this.buttonText1 = par4Str;
        this.buttonText2 = par5Str;
        this.worldNumber = par6;
    }
    
    @Override
    public void initGui() {
        this.buttonList.add(new GuiSmallButton(0, this.width / 2 - 155, this.height / 6 + 96, this.buttonText1));
        this.buttonList.add(new GuiSmallButton(1, this.width / 2 - 155 + 160, this.height / 6 + 96, this.buttonText2));
    }
    
    @Override
    protected void actionPerformed(final GuiButton par1GuiButton) {
        this.parentScreen.confirmClicked(par1GuiButton.id == 0, this.worldNumber);
    }
    
    @Override
    public void drawScreen(final int par1, final int par2, final float par3) {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRenderer, this.message1, this.width / 2, 70, 16777215);
        this.drawCenteredString(this.fontRenderer, this.message2, this.width / 2, 90, 16777215);
        super.drawScreen(par1, par2, par3);
    }
}
