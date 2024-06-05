package net.minecraft.src;

public class ScreenChatOptions extends GuiScreen
{
    private static final EnumOptions[] allScreenChatOptions;
    private static final EnumOptions[] allMultiplayerOptions;
    private final GuiScreen theGuiScreen;
    private final GameSettings theSettings;
    private String theChatOptions;
    private String field_82268_n;
    private int field_82269_o;
    
    static {
        allScreenChatOptions = new EnumOptions[] { EnumOptions.CHAT_VISIBILITY, EnumOptions.CHAT_COLOR, EnumOptions.CHAT_LINKS, EnumOptions.CHAT_OPACITY, EnumOptions.CHAT_LINKS_PROMPT, EnumOptions.CHAT_SCALE, EnumOptions.CHAT_HEIGHT_FOCUSED, EnumOptions.CHAT_HEIGHT_UNFOCUSED, EnumOptions.CHAT_WIDTH };
        allMultiplayerOptions = new EnumOptions[] { EnumOptions.SHOW_CAPE };
    }
    
    public ScreenChatOptions(final GuiScreen par1GuiScreen, final GameSettings par2GameSettings) {
        this.field_82269_o = 0;
        this.theGuiScreen = par1GuiScreen;
        this.theSettings = par2GameSettings;
    }
    
    @Override
    public void initGui() {
        final StringTranslate var1 = StringTranslate.getInstance();
        int var2 = 0;
        this.theChatOptions = var1.translateKey("options.chat.title");
        this.field_82268_n = var1.translateKey("options.multiplayer.title");
        for (final EnumOptions var6 : ScreenChatOptions.allScreenChatOptions) {
            if (var6.getEnumFloat()) {
                this.buttonList.add(new GuiSlider(var6.returnEnumOrdinal(), this.width / 2 - 155 + var2 % 2 * 160, this.height / 6 + 24 * (var2 >> 1), var6, this.theSettings.getKeyBinding(var6), this.theSettings.getOptionFloatValue(var6)));
            }
            else {
                this.buttonList.add(new GuiSmallButton(var6.returnEnumOrdinal(), this.width / 2 - 155 + var2 % 2 * 160, this.height / 6 + 24 * (var2 >> 1), var6, this.theSettings.getKeyBinding(var6)));
            }
            ++var2;
        }
        if (var2 % 2 == 1) {
            ++var2;
        }
        this.field_82269_o = this.height / 6 + 24 * (var2 >> 1);
        var2 += 2;
        for (final EnumOptions var6 : ScreenChatOptions.allMultiplayerOptions) {
            if (var6.getEnumFloat()) {
                this.buttonList.add(new GuiSlider(var6.returnEnumOrdinal(), this.width / 2 - 155 + var2 % 2 * 160, this.height / 6 + 24 * (var2 >> 1), var6, this.theSettings.getKeyBinding(var6), this.theSettings.getOptionFloatValue(var6)));
            }
            else {
                this.buttonList.add(new GuiSmallButton(var6.returnEnumOrdinal(), this.width / 2 - 155 + var2 % 2 * 160, this.height / 6 + 24 * (var2 >> 1), var6, this.theSettings.getKeyBinding(var6)));
            }
            ++var2;
        }
        this.buttonList.add(new GuiButton(200, this.width / 2 - 100, this.height / 6 + 168, var1.translateKey("gui.done")));
    }
    
    @Override
    protected void actionPerformed(final GuiButton par1GuiButton) {
        if (par1GuiButton.enabled) {
            if (par1GuiButton.id < 100 && par1GuiButton instanceof GuiSmallButton) {
                this.theSettings.setOptionValue(((GuiSmallButton)par1GuiButton).returnEnumOptions(), 1);
                par1GuiButton.displayString = this.theSettings.getKeyBinding(EnumOptions.getEnumOptions(par1GuiButton.id));
            }
            if (par1GuiButton.id == 200) {
                this.mc.gameSettings.saveOptions();
                this.mc.displayGuiScreen(this.theGuiScreen);
            }
        }
    }
    
    @Override
    public void drawScreen(final int par1, final int par2, final float par3) {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRenderer, this.theChatOptions, this.width / 2, 20, 16777215);
        this.drawCenteredString(this.fontRenderer, this.field_82268_n, this.width / 2, this.field_82269_o + 7, 16777215);
        super.drawScreen(par1, par2, par3);
    }
}
