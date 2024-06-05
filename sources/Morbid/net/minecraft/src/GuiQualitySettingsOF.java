package net.minecraft.src;

public class GuiQualitySettingsOF extends GuiScreen
{
    private GuiScreen prevScreen;
    protected String title;
    private GameSettings settings;
    private static EnumOptions[] enumOptions;
    private int lastMouseX;
    private int lastMouseY;
    private long mouseStillTime;
    
    static {
        GuiQualitySettingsOF.enumOptions = new EnumOptions[] { EnumOptions.MIPMAP_LEVEL, EnumOptions.MIPMAP_TYPE, EnumOptions.AF_LEVEL, EnumOptions.AA_LEVEL, EnumOptions.CLEAR_WATER, EnumOptions.RANDOM_MOBS, EnumOptions.BETTER_GRASS, EnumOptions.BETTER_SNOW, EnumOptions.CUSTOM_FONTS, EnumOptions.CUSTOM_COLORS, EnumOptions.SWAMP_COLORS, EnumOptions.SMOOTH_BIOMES, EnumOptions.CONNECTED_TEXTURES, EnumOptions.NATURAL_TEXTURES, EnumOptions.CUSTOM_SKY };
    }
    
    public GuiQualitySettingsOF(final GuiScreen var1, final GameSettings var2) {
        this.title = "Quality Settings";
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
        for (final EnumOptions var6 : GuiQualitySettingsOF.enumOptions) {
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
            if (var1.id != EnumOptions.CLOUD_HEIGHT.ordinal()) {
                final ScaledResolution var2 = new ScaledResolution(this.mc.gameSettings, this.mc.displayWidth, this.mc.displayHeight);
                final int var3 = ScaledResolution.getScaledWidth();
                final int var4 = ScaledResolution.getScaledHeight();
                this.setWorldAndResolution(this.mc, var3, var4);
            }
        }
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
                        int var14 = 14540253;
                        if (var13.endsWith("!")) {
                            var14 = 16719904;
                        }
                        this.fontRenderer.drawStringWithShadow(var13, var5 + 5, var6 + 5 + var12 * 11, var14);
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
        if (var1.equals("Mipmap Level")) {
            final String[] array = array2 = new String[6];
            array[0] = "Visual effect which makes distant objects look better";
            array[1] = "by smoothing the texture details";
            array[2] = "  OFF - no smoothing";
            array[3] = "  1 - minimum smoothing";
            array[4] = "  4 - maximum smoothing";
            array[5] = "This option usually does not affect the performance.";
        }
        else if (var1.equals("Mipmap Type")) {
            final String[] array3 = array2 = new String[5];
            array3[0] = "Visual effect which makes distant objects look better";
            array3[1] = "by smoothing the texture details";
            array3[2] = "  Nearest - rough smoothing";
            array3[3] = "  Linear - fine smoothing";
            array3[4] = "This option usually does not affect the performance.";
        }
        else if (var1.equals("Anisotropic Filtering")) {
            final String[] array4 = array2 = new String[5];
            array4[0] = "Anisotropic Filtering";
            array4[1] = " OFF - (default) standard texture detail (faster)";
            array4[2] = " 2-16 - finer details in mipmapped textures (slower)";
            array4[3] = "The Anisotropic Filtering restores details in mipmapped";
            array4[4] = "textures. Higher values may decrease the FPS.";
        }
        else if (var1.equals("Antialiasing")) {
            final String[] array5 = array2 = new String[8];
            array5[0] = "Antialiasing";
            array5[1] = " OFF - (default) no antialiasing (faster)";
            array5[2] = " 2-16 - antialiased lines and edges (slower)";
            array5[3] = "The Antialiasing smooths jagged lines and ";
            array5[4] = "sharp color transitions.";
            array5[5] = "Higher values may substantially decrease the FPS.";
            array5[6] = "Not all levels are supported by all graphics cards.";
            array5[7] = "Effective after a RESTART!";
        }
        else if (var1.equals("Clear Water")) {
            final String[] array6 = array2 = new String[3];
            array6[0] = "Clear Water";
            array6[1] = "  ON - clear, transparent water";
            array6[2] = "  OFF - default water";
        }
        else if (var1.equals("Better Grass")) {
            final String[] array7 = array2 = new String[4];
            array7[0] = "Better Grass";
            array7[1] = "  OFF - default side grass texture, fastest";
            array7[2] = "  Fast - full side grass texture, slower";
            array7[3] = "  Fancy - dynamic side grass texture, slowest";
        }
        else if (var1.equals("Better Snow")) {
            final String[] array8 = array2 = new String[5];
            array8[0] = "Better Snow";
            array8[1] = "  OFF - default snow, faster";
            array8[2] = "  ON - better snow, slower";
            array8[3] = "Shows snow under transparent blocks (fence, tall grass)";
            array8[4] = "when bordering with snow blocks";
        }
        else if (var1.equals("Random Mobs")) {
            final String[] array9 = array2 = new String[5];
            array9[0] = "Random Mobs";
            array9[1] = "  OFF - no random mobs, faster";
            array9[2] = "  ON - random mobs, slower";
            array9[3] = "Random mobs uses random textures for the game creatures.";
            array9[4] = "It needs a texture pack which has multiple mob textures.";
        }
        else if (var1.equals("Swamp Colors")) {
            final String[] array10 = array2 = new String[4];
            array10[0] = "Swamp Colors";
            array10[1] = "  ON - use swamp colors (default), slower";
            array10[2] = "  OFF - do not use swamp colors, faster";
            array10[3] = "The swamp colors affect grass, leaves, vines and water.";
        }
        else if (var1.equals("Smooth Biomes")) {
            final String[] array11 = array2 = new String[6];
            array11[0] = "Smooth Biomes";
            array11[1] = "  ON - smoothing of biome borders (default), slower";
            array11[2] = "  OFF - no smoothing of biome borders, faster";
            array11[3] = "The smoothing of biome borders is done by sampling and";
            array11[4] = "averaging the color of all surrounding blocks.";
            array11[5] = "Affected are grass, leaves, vines and water.";
        }
        else if (var1.equals("Custom Fonts")) {
            final String[] array12 = array2 = new String[5];
            array12[0] = "Custom Fonts";
            array12[1] = "  ON - uses custom fonts (default), slower";
            array12[2] = "  OFF - uses default font, faster";
            array12[3] = "The custom fonts are supplied by the current";
            array12[4] = "texture pack";
        }
        else if (var1.equals("Custom Colors")) {
            final String[] array13 = array2 = new String[5];
            array13[0] = "Custom Colors";
            array13[1] = "  ON - uses custom colors (default), slower";
            array13[2] = "  OFF - uses default colors, faster";
            array13[3] = "The custom colors are supplied by the current";
            array13[4] = "texture pack";
        }
        else if (var1.equals("Show Capes")) {
            final String[] array14 = array2 = new String[3];
            array14[0] = "Show Capes";
            array14[1] = "  ON - show player capes (default)";
            array14[2] = "  OFF - do not show player capes";
        }
        else if (var1.equals("Connected Textures")) {
            final String[] array15 = array2 = new String[8];
            array15[0] = "Connected Textures";
            array15[1] = "  OFF - no connected textures (default)";
            array15[2] = "  Fast - fast connected textures";
            array15[3] = "  Fancy - fancy connected textures";
            array15[4] = "Connected textures joins the textures of glass,";
            array15[5] = "sandstone and bookshelves when placed next to";
            array15[6] = "each other. The connected textures are supplied";
            array15[7] = "by the current texture pack.";
        }
        else if (var1.equals("Far View")) {
            final String[] array16 = array2 = new String[7];
            array16[0] = "Far View";
            array16[1] = " OFF - (default) standard view distance";
            array16[2] = " ON - 3x view distance";
            array16[3] = "Far View is very resource demanding!";
            array16[4] = "3x view distance => 9x chunks to be loaded => FPS / 9";
            array16[5] = "Standard view distances: 32, 64, 128, 256";
            array16[6] = "Far view distances: 96, 192, 384, 512";
        }
        else if (var1.equals("Natural Textures")) {
            final String[] array17 = array2 = new String[8];
            array17[0] = "Natural Textures";
            array17[1] = "  OFF - no natural textures (default)";
            array17[2] = "  ON - use natural textures";
            array17[3] = "Natural textures remove the gridlike pattern";
            array17[4] = "created by repeating blocks of the same type.";
            array17[5] = "It uses rotated and flipped variants of the base";
            array17[6] = "block texture. The configuration for the natural";
            array17[7] = "textures is supplied by the current texture pack";
        }
        else if (var1.equals("Custom Sky")) {
            final String[] array18 = array2 = new String[5];
            array18[0] = "Custom Sky";
            array18[1] = "  ON - custom sky textures (default), slow";
            array18[2] = "  OFF - default sky, faster";
            array18[3] = "The custom sky textures are supplied by the current";
            array18[4] = "texture pack";
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
