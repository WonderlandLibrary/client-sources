package net.minecraft.src;

public class GuiOtherSettingsOF extends GuiScreen
{
    private GuiScreen prevScreen;
    protected String title;
    private GameSettings settings;
    private static EnumOptions[] enumOptions;
    private int lastMouseX;
    private int lastMouseY;
    private long mouseStillTime;
    
    static {
        GuiOtherSettingsOF.enumOptions = new EnumOptions[] { EnumOptions.LAGOMETER, EnumOptions.PROFILER, EnumOptions.WEATHER, EnumOptions.TIME, EnumOptions.USE_FULLSCREEN, EnumOptions.FULLSCREEN_MODE, EnumOptions.ANAGLYPH, EnumOptions.AUTOSAVE_TICKS };
    }
    
    public GuiOtherSettingsOF(final GuiScreen var1, final GameSettings var2) {
        this.title = "Other Settings";
        this.lastMouseX = 0;
        this.lastMouseY = 0;
        this.mouseStillTime = 0L;
        this.prevScreen = var1;
        this.settings = var2;
    }
    
    @Override
    public void initGui() {
        final StringTranslate var1 = StringTranslate.getInstance();
        int var2 = 0;
        for (final EnumOptions var6 : GuiOtherSettingsOF.enumOptions) {
            final int var7 = this.width / 2 - 155 + var2 % 2 * 160;
            final int var8 = this.height / 6 + 21 * (var2 / 2) - 10;
            if (!var6.getEnumFloat()) {
                this.buttonList.add(new GuiSmallButton(var6.returnEnumOrdinal(), var7, var8, var6, this.settings.getKeyBinding(var6)));
            }
            else {
                this.buttonList.add(new GuiSlider(var6.returnEnumOrdinal(), var7, var8, var6, this.settings.getKeyBinding(var6), this.settings.getOptionFloatValue(var6)));
            }
            ++var2;
        }
        this.buttonList.add(new GuiButton(210, this.width / 2 - 100, this.height / 6 + 168 + 11 - 22, "Reset Video Settings..."));
        this.buttonList.add(new GuiButton(200, this.width / 2 - 100, this.height / 6 + 168 + 11, var1.translateKey("gui.done")));
    }
    
    @Override
    protected void actionPerformed(final GuiButton var1) {
        if (var1.enabled) {
            if (var1.id < 100 && var1 instanceof GuiSmallButton) {
                this.settings.setOptionValue(((GuiSmallButton)var1).returnEnumOptions(), 1);
                var1.displayString = this.settings.getKeyBinding(EnumOptions.getEnumOptions(var1.id));
            }
            if (var1.id == 200) {
                this.mc.gameSettings.saveOptions();
                this.mc.displayGuiScreen(this.prevScreen);
            }
            if (var1.id == 210) {
                this.mc.gameSettings.saveOptions();
                final GuiYesNo var2 = new GuiYesNo(this, "Reset all video settings to their default values?", "", 9999);
                this.mc.displayGuiScreen(var2);
            }
            if (var1.id != EnumOptions.CLOUD_HEIGHT.ordinal()) {
                final ScaledResolution var3 = new ScaledResolution(this.mc.gameSettings, this.mc.displayWidth, this.mc.displayHeight);
                final int var4 = ScaledResolution.getScaledWidth();
                final int var5 = ScaledResolution.getScaledHeight();
                this.setWorldAndResolution(this.mc, var4, var5);
            }
        }
    }
    
    @Override
    public void confirmClicked(final boolean var1, final int var2) {
        if (var1) {
            this.mc.gameSettings.resetSettings();
        }
        this.mc.displayGuiScreen(this);
    }
    
    @Override
    public void drawScreen(final int var1, final int var2, final float var3) {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRenderer, this.title, this.width / 2, 20, 16777215);
        super.drawScreen(var1, var2, var3);
        if (Math.abs(var1 - this.lastMouseX) <= 5 && Math.abs(var2 - this.lastMouseY) <= 5) {
            final short var4 = 700;
            if (System.currentTimeMillis() >= this.mouseStillTime + var4) {
                final int var5 = this.width / 2 - 150;
                int var6 = this.height / 6 - 5;
                if (var2 <= var6 + 98) {
                    var6 += 105;
                }
                final int var7 = var5 + 150 + 150;
                final int var8 = var6 + 84 + 10;
                final GuiButton var9 = this.getSelectedButton(var1, var2);
                if (var9 != null) {
                    final String var10 = this.getButtonName(var9.displayString);
                    final String[] var11 = this.getTooltipLines(var10);
                    if (var11 == null) {
                        return;
                    }
                    this.drawGradientRect(var5, var6, var7, var8, -536870912, -536870912);
                    for (int var12 = 0; var12 < var11.length; ++var12) {
                        final String var13 = var11[var12];
                        this.fontRenderer.drawStringWithShadow(var13, var5 + 5, var6 + 5 + var12 * 11, 14540253);
                    }
                }
            }
        }
        else {
            this.lastMouseX = var1;
            this.lastMouseY = var2;
            this.mouseStillTime = System.currentTimeMillis();
        }
    }
    
    private String[] getTooltipLines(final String var1) {
        String[] array2;
        if (var1.equals("Autosave")) {
            final String[] array = array2 = new String[3];
            array[0] = "Autosave interval";
            array[1] = "Default autosave interval (2s) is NOT RECOMMENDED.";
            array[2] = "Autosave causes the famous Lag Spike of Death.";
        }
        else if (var1.equals("Lagometer")) {
            final String[] array3 = array2 = new String[7];
            array3[0] = "Lagometer";
            array3[1] = " OFF - no lagometer, faster";
            array3[2] = " ON - debug screen with lagometer, slower";
            array3[3] = "Shows the lagometer on the debug screen (F3).";
            array3[4] = "* White - tick";
            array3[5] = "* Red - chunk loading";
            array3[6] = "* Green - frame rendering + internal server";
        }
        else if (var1.equals("Debug Profiler")) {
            final String[] array4 = array2 = new String[5];
            array4[0] = "Debug Profiler";
            array4[1] = "  ON - debug profiler is active, slower";
            array4[2] = "  OFF - debug profiler is not active, faster";
            array4[3] = "The debug profiler collects and shows debug information";
            array4[4] = "when the debug screen is open (F3)";
        }
        else if (var1.equals("Time")) {
            final String[] array5 = array2 = new String[5];
            array5[0] = "Time";
            array5[1] = " Default - normal day/night cycles";
            array5[2] = " Day Only - day only";
            array5[3] = " Night Only - night only";
            array5[4] = "The time setting is only effective in CREATIVE mode.";
        }
        else if (var1.equals("Weather")) {
            final String[] array6 = array2 = new String[4];
            array6[0] = "Weather";
            array6[1] = "  ON - weather is active, slower";
            array6[2] = "  OFF - weather is not active, faster";
            array6[3] = "The weather controls rain, snow and thunderstorms.";
        }
        else if (var1.equals("Fullscreen")) {
            final String[] array7 = array2 = new String[4];
            array7[0] = "Fullscreen resolution";
            array7[1] = "  Default - use desktop screen resolution, slower";
            array7[2] = "  WxH - use custom screen resolution, may be faster";
            array7[3] = "The selected resolution is used in fullscreen mode (F11).";
        }
        else {
            array2 = null;
        }
        return array2;
    }
    
    private String getButtonName(final String var1) {
        final int var2 = var1.indexOf(58);
        return (var2 < 0) ? var1 : var1.substring(0, var2);
    }
    
    private GuiButton getSelectedButton(final int var1, final int var2) {
        for (int var3 = 0; var3 < this.buttonList.size(); ++var3) {
            final GuiButton var4 = this.buttonList.get(var3);
            final boolean var5 = var1 >= var4.xPosition && var2 >= var4.yPosition && var1 < var4.xPosition + var4.width && var2 < var4.yPosition + var4.height;
            if (var5) {
                return var4;
            }
        }
        return null;
    }
}
