package net.minecraft.src;

public class GuiVideoSettings extends GuiScreen
{
    private GuiScreen parentGuiScreen;
    protected String screenTitle;
    private GameSettings guiGameSettings;
    private boolean is64bit;
    private static EnumOptions[] videoOptions;
    private int lastMouseX;
    private int lastMouseY;
    private long mouseStillTime;
    
    static {
        GuiVideoSettings.videoOptions = new EnumOptions[] { EnumOptions.GRAPHICS, EnumOptions.RENDER_DISTANCE_FINE, EnumOptions.AMBIENT_OCCLUSION, EnumOptions.FRAMERATE_LIMIT_FINE, EnumOptions.AO_LEVEL, EnumOptions.VIEW_BOBBING, EnumOptions.GUI_SCALE, EnumOptions.ADVANCED_OPENGL, EnumOptions.GAMMA, EnumOptions.CHUNK_LOADING, EnumOptions.FOG_FANCY, EnumOptions.FOG_START, EnumOptions.USE_SERVER_TEXTURES };
    }
    
    public GuiVideoSettings(final GuiScreen par1GuiScreen, final GameSettings par2GameSettings) {
        this.screenTitle = "Video Settings";
        this.is64bit = false;
        this.lastMouseX = 0;
        this.lastMouseY = 0;
        this.mouseStillTime = 0L;
        this.parentGuiScreen = par1GuiScreen;
        this.guiGameSettings = par2GameSettings;
    }
    
    @Override
    public void initGui() {
        final StringTranslate var1 = StringTranslate.getInstance();
        this.screenTitle = var1.translateKey("options.videoTitle");
        int var2 = 0;
        final EnumOptions[] var3 = GuiVideoSettings.videoOptions;
        int var4;
        int var5;
        for (var4 = var3.length, var5 = 0; var5 < var4; ++var5) {
            final EnumOptions var6 = var3[var5];
            final int var7 = this.width / 2 - 155 + var5 % 2 * 160;
            final int var8 = this.height / 6 + 21 * (var5 / 2) - 10;
            if (var6.getEnumFloat()) {
                this.buttonList.add(new GuiSlider(var6.returnEnumOrdinal(), var7, var8, var6, this.guiGameSettings.getKeyBinding(var6), this.guiGameSettings.getOptionFloatValue(var6)));
            }
            else {
                this.buttonList.add(new GuiSmallButton(var6.returnEnumOrdinal(), var7, var8, var6, this.guiGameSettings.getKeyBinding(var6)));
            }
            ++var2;
        }
        int var9 = this.height / 6 + 21 * (var5 / 2) - 10;
        final boolean var10 = false;
        int var7 = this.width / 2 - 155 + 160;
        this.buttonList.add(new GuiSmallButton(102, var7, var9, "Quality..."));
        var9 += 21;
        var7 = this.width / 2 - 155 + 0;
        this.buttonList.add(new GuiSmallButton(101, var7, var9, "Details..."));
        var7 = this.width / 2 - 155 + 160;
        this.buttonList.add(new GuiSmallButton(112, var7, var9, "Performance..."));
        var9 += 21;
        var7 = this.width / 2 - 155 + 0;
        this.buttonList.add(new GuiSmallButton(111, var7, var9, "Animations..."));
        var7 = this.width / 2 - 155 + 160;
        this.buttonList.add(new GuiSmallButton(122, var7, var9, "Other..."));
        this.buttonList.add(new GuiButton(200, this.width / 2 - 100, this.height / 6 + 168 + 11, var1.translateKey("gui.done")));
        this.is64bit = false;
        final String[] var12;
        final String[] var11 = var12 = new String[] { "sun.arch.data.model", "com.ibm.vm.bitmode", "os.arch" };
        var5 = var11.length;
        for (final String var14 : var12) {
            final String var15 = System.getProperty(var14);
            if (var15 != null && var15.contains("64")) {
                this.is64bit = true;
                break;
            }
        }
    }
    
    @Override
    protected void actionPerformed(final GuiButton par1GuiButton) {
        if (par1GuiButton.enabled) {
            final int var2 = this.guiGameSettings.guiScale;
            if (par1GuiButton.id < 100 && par1GuiButton instanceof GuiSmallButton) {
                this.guiGameSettings.setOptionValue(((GuiSmallButton)par1GuiButton).returnEnumOptions(), 1);
                par1GuiButton.displayString = this.guiGameSettings.getKeyBinding(EnumOptions.getEnumOptions(par1GuiButton.id));
            }
            if (par1GuiButton.id == 200) {
                this.mc.gameSettings.saveOptions();
                this.mc.displayGuiScreen(this.parentGuiScreen);
            }
            if (this.guiGameSettings.guiScale != var2) {
                final ScaledResolution var3 = new ScaledResolution(this.mc.gameSettings, this.mc.displayWidth, this.mc.displayHeight);
                final int var4 = ScaledResolution.getScaledWidth();
                final int var5 = ScaledResolution.getScaledHeight();
                this.setWorldAndResolution(this.mc, var4, var5);
            }
            if (par1GuiButton.id == 101) {
                this.mc.gameSettings.saveOptions();
                final GuiDetailSettingsOF var6 = new GuiDetailSettingsOF(this, this.guiGameSettings);
                this.mc.displayGuiScreen(var6);
            }
            if (par1GuiButton.id == 102) {
                this.mc.gameSettings.saveOptions();
                final GuiQualitySettingsOF var7 = new GuiQualitySettingsOF(this, this.guiGameSettings);
                this.mc.displayGuiScreen(var7);
            }
            if (par1GuiButton.id == 111) {
                this.mc.gameSettings.saveOptions();
                final GuiAnimationSettingsOF var8 = new GuiAnimationSettingsOF(this, this.guiGameSettings);
                this.mc.displayGuiScreen(var8);
            }
            if (par1GuiButton.id == 112) {
                this.mc.gameSettings.saveOptions();
                final GuiPerformanceSettingsOF var9 = new GuiPerformanceSettingsOF(this, this.guiGameSettings);
                this.mc.displayGuiScreen(var9);
            }
            if (par1GuiButton.id == 122) {
                this.mc.gameSettings.saveOptions();
                final GuiOtherSettingsOF var10 = new GuiOtherSettingsOF(this, this.guiGameSettings);
                this.mc.displayGuiScreen(var10);
            }
            if (par1GuiButton.id == EnumOptions.AO_LEVEL.ordinal()) {
                return;
            }
        }
    }
    
    @Override
    public void drawScreen(final int par1, final int par2, final float par3) {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRenderer, this.screenTitle, this.width / 2, 20, 16777215);
        super.drawScreen(par1, par2, par3);
        if (Math.abs(par1 - this.lastMouseX) <= 5 && Math.abs(par2 - this.lastMouseY) <= 5) {
            final short var4 = 700;
            if (System.currentTimeMillis() >= this.mouseStillTime + var4) {
                final int var5 = this.width / 2 - 150;
                int var6 = this.height / 6 - 5;
                if (par2 <= var6 + 98) {
                    var6 += 105;
                }
                final int var7 = var5 + 150 + 150;
                final int var8 = var6 + 84 + 10;
                final GuiButton var9 = this.getSelectedButton(par1, par2);
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
            this.lastMouseX = par1;
            this.lastMouseY = par2;
            this.mouseStillTime = System.currentTimeMillis();
        }
    }
    
    private String[] getTooltipLines(final String var1) {
        final String[] array6;
        if (var1.equals("Graphics")) {
            final String[] array2;
            final String[] array = array2 = new String[5];
            array[0] = "Visual quality";
            array[1] = "  Fast  - lower quality, faster";
            array[2] = "  Fancy - higher quality, slower";
            array[3] = "Changes the appearance of clouds, leaves, water,";
            array[4] = "shadows and grass sides.";
        }
        else if (var1.equals("Render Distance")) {
            final String[] array2;
            final String[] array3 = array2 = new String[7];
            array3[0] = "Visible distance";
            array3[1] = "  Tiny - 32m (fastest)";
            array3[2] = "  Short - 64m (faster)";
            array3[3] = "  Normal - 128m";
            array3[4] = "  Far - 256m (slower)";
            array3[5] = "  Extreme - 512m (slowest!)";
            array3[6] = "The Extreme view distance is very resource demanding!";
        }
        else if (var1.equals("Smooth Lighting")) {
            final String[] array2;
            final String[] array4 = array2 = new String[4];
            array4[0] = "Smooth lighting";
            array4[1] = "  OFF - no smooth lighting (faster)";
            array4[2] = "  1% - light smooth lighting (slower)";
            array4[3] = "  100% - dark smooth lighting (slower)";
        }
        else if (var1.equals("Performance")) {
            final String[] array2;
            final String[] array5 = array2 = new String[7];
            array5[0] = "FPS Limit";
            array5[1] = "  Max FPS - no limit (fastest)";
            array5[2] = "  Balanced - limit 120 FPS (slower)";
            array5[3] = "  Power saver - limit 40 FPS (slowest)";
            array5[4] = "  VSync - limit to monitor framerate (60, 30, 20)";
            array5[5] = "Balanced and Power saver decrease the FPS even if";
            array5[6] = "the limit value is not reached.";
        }
        else if (var1.equals("3D Anaglyph")) {
            array6 = new String[] { "3D mode used with red-cyan 3D glasses." };
        }
        else if (var1.equals("View Bobbing")) {
            final String[] array2;
            final String[] array7 = array2 = new String[2];
            array7[0] = "More realistic movement.";
            array7[1] = "When using mipmaps set it to OFF for best results.";
        }
        else if (var1.equals("GUI Scale")) {
            final String[] array2;
            final String[] array8 = array2 = new String[2];
            array8[0] = "GUI Scale";
            array8[1] = "Smaller GUI might be faster";
        }
        else if (var1.equals("Advanced OpenGL")) {
            final String[] array2;
            final String[] array9 = array2 = new String[6];
            array9[0] = "Detect and render only visible geometry";
            array9[1] = "  OFF - all geometry is rendered (slower)";
            array9[2] = "  Fast - only visible geometry is rendered (fastest)";
            array9[3] = "  Fancy - conservative, avoids visual artifacts (faster)";
            array9[4] = "The option is available only if it is supported by the ";
            array9[5] = "graphic card.";
        }
        else if (var1.equals("Fog")) {
            final String[] array2;
            final String[] array10 = array2 = new String[6];
            array10[0] = "Fog type";
            array10[1] = "  Fast - faster fog";
            array10[2] = "  Fancy - slower fog, looks better";
            array10[3] = "  OFF - no fog, fastest";
            array10[4] = "The fancy fog is available only if it is supported by the ";
            array10[5] = "graphic card.";
        }
        else if (var1.equals("Fog Start")) {
            final String[] array2;
            final String[] array11 = array2 = new String[4];
            array11[0] = "Fog start";
            array11[1] = "  0.2 - the fog starts near the player";
            array11[2] = "  0.8 - the fog starts far from the player";
            array11[3] = "This option usually does not affect the performance.";
        }
        else if (var1.equals("Brightness")) {
            final String[] array2;
            final String[] array12 = array2 = new String[5];
            array12[0] = "Increases the brightness of darker objects";
            array12[1] = "  OFF - standard brightness";
            array12[2] = "  100% - maximum brightness for darker objects";
            array12[3] = "This options does not change the brightness of ";
            array12[4] = "fully black objects";
        }
        else if (var1.equals("Chunk Loading")) {
            final String[] array2;
            final String[] array13 = array2 = new String[8];
            array13[0] = "Chunk Loading";
            array13[1] = "  Default - unstable FPS when loading chunks";
            array13[2] = "  Smooth - stable FPS";
            array13[3] = "  Multi-Core - stable FPS, 3x faster world loading";
            array13[4] = "Smooth and Multi-Core remove the stuttering and freezes";
            array13[5] = "caused by chunk loading.";
            array13[6] = "Multi-Core can speed up 3x the world loading and";
            array13[7] = "increase FPS by using a second CPU core.";
        }
        return array6;
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
