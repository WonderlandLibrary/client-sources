package net.minecraft.src;

import net.minecraft.client.*;

public class GuiOptions extends GuiScreen
{
    private static final EnumOptions[] relevantOptions;
    private final GuiScreen parentScreen;
    private final GameSettings options;
    protected String screenTitle;
    
    static {
        relevantOptions = new EnumOptions[] { EnumOptions.MUSIC, EnumOptions.SOUND, EnumOptions.INVERT_MOUSE, EnumOptions.SENSITIVITY, EnumOptions.FOV, EnumOptions.DIFFICULTY, EnumOptions.TOUCHSCREEN };
    }
    
    public GuiOptions(final GuiScreen par1GuiScreen, final GameSettings par2GameSettings) {
        this.screenTitle = "Options";
        this.parentScreen = par1GuiScreen;
        this.options = par2GameSettings;
    }
    
    @Override
    public void initGui() {
        final StringTranslate var1 = StringTranslate.getInstance();
        int var2 = 0;
        this.screenTitle = var1.translateKey("options.title");
        for (final EnumOptions var6 : GuiOptions.relevantOptions) {
            if (var6.getEnumFloat()) {
                this.buttonList.add(new GuiSlider(var6.returnEnumOrdinal(), this.width / 2 - 155 + var2 % 2 * 160, this.height / 6 - 12 + 24 * (var2 >> 1), var6, this.options.getKeyBinding(var6), this.options.getOptionFloatValue(var6)));
            }
            else {
                final GuiSmallButton var7 = new GuiSmallButton(var6.returnEnumOrdinal(), this.width / 2 - 155 + var2 % 2 * 160, this.height / 6 - 12 + 24 * (var2 >> 1), var6, this.options.getKeyBinding(var6));
                if (var6 == EnumOptions.DIFFICULTY && Minecraft.theWorld != null && Minecraft.theWorld.getWorldInfo().isHardcoreModeEnabled()) {
                    var7.enabled = false;
                    var7.displayString = String.valueOf(StatCollector.translateToLocal("options.difficulty")) + ": " + StatCollector.translateToLocal("options.difficulty.hardcore");
                }
                this.buttonList.add(var7);
            }
            ++var2;
        }
        this.buttonList.add(new GuiButton(101, this.width / 2 - 152, this.height / 6 + 96 - 6, 150, 20, var1.translateKey("options.video")));
        this.buttonList.add(new GuiButton(100, this.width / 2 + 2, this.height / 6 + 96 - 6, 150, 20, var1.translateKey("options.controls")));
        this.buttonList.add(new GuiButton(102, this.width / 2 - 152, this.height / 6 + 120 - 6, 150, 20, var1.translateKey("options.language")));
        this.buttonList.add(new GuiButton(103, this.width / 2 + 2, this.height / 6 + 120 - 6, 150, 20, var1.translateKey("options.multiplayer.title")));
        this.buttonList.add(new GuiButton(105, this.width / 2 - 152, this.height / 6 + 144 - 6, 150, 20, var1.translateKey("options.texture.pack")));
        this.buttonList.add(new GuiButton(104, this.width / 2 + 2, this.height / 6 + 144 - 6, 150, 20, var1.translateKey("options.snooper.view")));
        this.buttonList.add(new GuiButton(200, this.width / 2 - 100, this.height / 6 + 168, var1.translateKey("gui.done")));
    }
    
    @Override
    protected void actionPerformed(final GuiButton par1GuiButton) {
        if (par1GuiButton.enabled) {
            if (par1GuiButton.id < 100 && par1GuiButton instanceof GuiSmallButton) {
                this.options.setOptionValue(((GuiSmallButton)par1GuiButton).returnEnumOptions(), 1);
                par1GuiButton.displayString = this.options.getKeyBinding(EnumOptions.getEnumOptions(par1GuiButton.id));
            }
            if (par1GuiButton.id == 101) {
                this.mc.gameSettings.saveOptions();
                this.mc.displayGuiScreen(new GuiVideoSettings(this, this.options));
            }
            if (par1GuiButton.id == 100) {
                this.mc.gameSettings.saveOptions();
                this.mc.displayGuiScreen(new GuiControls(this, this.options));
            }
            if (par1GuiButton.id == 102) {
                this.mc.gameSettings.saveOptions();
                this.mc.displayGuiScreen(new GuiLanguage(this, this.options));
            }
            if (par1GuiButton.id == 103) {
                this.mc.gameSettings.saveOptions();
                this.mc.displayGuiScreen(new ScreenChatOptions(this, this.options));
            }
            if (par1GuiButton.id == 104) {
                this.mc.gameSettings.saveOptions();
                this.mc.displayGuiScreen(new GuiSnooper(this, this.options));
            }
            if (par1GuiButton.id == 200) {
                this.mc.gameSettings.saveOptions();
                this.mc.displayGuiScreen(this.parentScreen);
            }
            if (par1GuiButton.id == 105) {
                this.mc.gameSettings.saveOptions();
                this.mc.displayGuiScreen(new GuiTexturePacks(this, this.options));
            }
        }
    }
    
    @Override
    public void drawScreen(final int par1, final int par2, final float par3) {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRenderer, this.screenTitle, this.width / 2, 15, 16777215);
        super.drawScreen(par1, par2, par3);
    }
}
