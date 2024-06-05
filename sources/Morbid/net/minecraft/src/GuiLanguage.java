package net.minecraft.src;

public class GuiLanguage extends GuiScreen
{
    protected GuiScreen parentGui;
    private int updateTimer;
    private GuiSlotLanguage languageList;
    private final GameSettings theGameSettings;
    private GuiSmallButton doneButton;
    
    public GuiLanguage(final GuiScreen par1GuiScreen, final GameSettings par2GameSettings) {
        this.updateTimer = -1;
        this.parentGui = par1GuiScreen;
        this.theGameSettings = par2GameSettings;
    }
    
    @Override
    public void initGui() {
        final StringTranslate var1 = StringTranslate.getInstance();
        this.buttonList.add(this.doneButton = new GuiSmallButton(6, this.width / 2 - 75, this.height - 38, var1.translateKey("gui.done")));
        (this.languageList = new GuiSlotLanguage(this)).registerScrollButtons(this.buttonList, 7, 8);
    }
    
    @Override
    protected void actionPerformed(final GuiButton par1GuiButton) {
        if (par1GuiButton.enabled) {
            switch (par1GuiButton.id) {
                case 5: {
                    break;
                }
                case 6: {
                    this.mc.displayGuiScreen(this.parentGui);
                    break;
                }
                default: {
                    this.languageList.actionPerformed(par1GuiButton);
                    break;
                }
            }
        }
    }
    
    @Override
    public void drawScreen(final int par1, final int par2, final float par3) {
        this.languageList.drawScreen(par1, par2, par3);
        if (this.updateTimer <= 0) {
            this.mc.texturePackList.updateAvaliableTexturePacks();
            this.updateTimer += 20;
        }
        final StringTranslate var4 = StringTranslate.getInstance();
        this.drawCenteredString(this.fontRenderer, var4.translateKey("options.language"), this.width / 2, 16, 16777215);
        this.drawCenteredString(this.fontRenderer, "(" + var4.translateKey("options.languageWarning") + ")", this.width / 2, this.height - 56, 8421504);
        super.drawScreen(par1, par2, par3);
    }
    
    @Override
    public void updateScreen() {
        super.updateScreen();
        --this.updateTimer;
    }
    
    static GameSettings getGameSettings(final GuiLanguage par0GuiLanguage) {
        return par0GuiLanguage.theGameSettings;
    }
    
    static GuiSmallButton getDoneButton(final GuiLanguage par0GuiLanguage) {
        return par0GuiLanguage.doneButton;
    }
}
