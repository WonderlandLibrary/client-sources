package optfine;

import net.minecraft.client.settings.*;
import net.minecraft.client.resources.*;
import net.minecraft.client.gui.*;

public class GuiQualitySettingsOF extends GuiScreen
{
    private GuiScreen prevScreen;
    protected String title;
    private GameSettings settings;
    private static GameSettings.Options[] enumOptions;
    private int lastMouseX;
    private int lastMouseY;
    private long mouseStillTime;
    
    public GuiQualitySettingsOF(final GuiScreen p_i38_1_, final GameSettings p_i38_2_) {
        this.title = "Quality Settings";
        this.lastMouseX = 0;
        this.lastMouseY = 0;
        this.mouseStillTime = 0L;
        this.prevScreen = p_i38_1_;
        this.settings = p_i38_2_;
    }
    
    @Override
    public void initGui() {
        int i = 0;
        for (final GameSettings.Options gamesettings$options : GuiQualitySettingsOF.enumOptions) {
            final int j = this.width / 2 - 155 + i % 2 * 160;
            final int k = this.height / 6 + 21 * (i / 2) - 10;
            if (!gamesettings$options.getEnumFloat()) {
                this.buttonList.add(new GuiOptionButton(gamesettings$options.returnEnumOrdinal(), j, k, gamesettings$options, this.settings.getKeyBinding(gamesettings$options)));
            }
            else {
                this.buttonList.add(new GuiOptionSlider(gamesettings$options.returnEnumOrdinal(), j, k, gamesettings$options));
            }
            ++i;
        }
        this.buttonList.add(new GuiButton(200, this.width / 2 - 100, this.height / 6 + 168 + 11, I18n.format("gui.done", new Object[0])));
    }
    
    @Override
    protected void actionPerformed(final GuiButton button) {
        if (button.enabled) {
            if (button.id < 200 && button instanceof GuiOptionButton) {
                this.settings.setOptionValue(((GuiOptionButton)button).returnEnumOptions(), 1);
                button.displayString = this.settings.getKeyBinding(GameSettings.Options.getEnumOptions(button.id));
            }
            if (button.id == 200) {
                this.mc.gameSettings.saveOptions();
                this.mc.displayGuiScreen(this.prevScreen);
            }
            if (button.id != GameSettings.Options.CLOUD_HEIGHT.ordinal()) {
                final ScaledResolution scaledresolution = new ScaledResolution(this.mc);
                final int i = scaledresolution.getScaledWidth();
                final int j = scaledresolution.getScaledHeight();
                this.setWorldAndResolution(this.mc, i, j);
            }
        }
    }
    
    @Override
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRendererObj, this.title, this.width / 2, 20, 16777215);
        super.drawScreen(mouseX, mouseY, partialTicks);
        if (Math.abs(mouseX - this.lastMouseX) <= 5 && Math.abs(mouseY - this.lastMouseY) <= 5) {
            final int i = 700;
            if (System.currentTimeMillis() >= this.mouseStillTime + i) {
                final int j = this.width / 2 - 150;
                int k = this.height / 6 - 5;
                if (mouseY <= k + 98) {
                    k += 105;
                }
                final int l = j + 150 + 150;
                final int i2 = k + 84 + 10;
                final GuiButton guibutton = this.getSelectedButton(mouseX, mouseY);
                if (guibutton != null) {
                    final String s = this.getButtonName(guibutton.displayString);
                    final String[] astring = this.getTooltipLines(s);
                    if (astring == null) {
                        return;
                    }
                    this.drawGradientRect(j, k, l, i2, -536870912, -536870912);
                    for (int j2 = 0; j2 < astring.length; ++j2) {
                        final String s2 = astring[j2];
                        int k2 = 14540253;
                        if (s2.endsWith("!")) {
                            k2 = 16719904;
                        }
                        this.fontRendererObj.drawStringWithShadow(s2, (float)(j + 5), (float)(k + 5 + j2 * 11), k2);
                    }
                }
            }
        }
        else {
            this.lastMouseX = mouseX;
            this.lastMouseY = mouseY;
            this.mouseStillTime = System.currentTimeMillis();
        }
    }
    
    private String[] getTooltipLines(final String p_getTooltipLines_1_) {
        return (String[])(p_getTooltipLines_1_.equals("Mipmap Levels") ? new String[] { "Visual effect which makes distant objects look better", "by smoothing the texture details", "  OFF - no smoothing", "  1 - minimum smoothing", "  4 - maximum smoothing", "This option usually does not affect the performance." } : (p_getTooltipLines_1_.equals("Mipmap Type") ? new String[] { "Visual effect which makes distant objects look better", "by smoothing the texture details", "  Nearest - rough smoothing (fastest)", "  Linear - normal smoothing", "  Bilinear - fine smoothing", "  Trilinear - finest smoothing (slowest)" } : (p_getTooltipLines_1_.equals("Anisotropic Filtering") ? new String[] { "Anisotropic Filtering", " OFF - (default) standard texture detail (faster)", " 2-16 - finer details in mipmapped textures (slower)", "The Anisotropic Filtering restores details in", "mipmapped textures.", "When enabled it may substantially decrease the FPS." } : (p_getTooltipLines_1_.equals("Antialiasing") ? new String[] { "Antialiasing", " OFF - (default) no antialiasing (faster)", " 2-16 - antialiased lines and edges (slower)", "The Antialiasing smooths jagged lines and ", "sharp color transitions.", "When enabled it may substantially decrease the FPS.", "Not all levels are supported by all graphics cards.", "Effective after a RESTART!" } : (p_getTooltipLines_1_.equals("Clear Water") ? new String[] { "Clear Water", "  ON - clear, transparent water", "  OFF - default water" } : (p_getTooltipLines_1_.equals("Better Grass") ? new String[] { "Better Grass", "  OFF - default side grass texture, fastest", "  Fast - full side grass texture, slower", "  Fancy - dynamic side grass texture, slowest" } : (p_getTooltipLines_1_.equals("Better Snow") ? new String[] { "Better Snow", "  OFF - default snow, faster", "  ON - better snow, slower", "Shows snow under transparent blocks (fence, tall grass)", "when bordering with snow blocks" } : (p_getTooltipLines_1_.equals("Random Mobs") ? new String[] { "Random Mobs", "  OFF - no random mobs, faster", "  ON - random mobs, slower", "Random mobs uses random textures for the game creatures.", "It needs a texture pack which has multiple mob textures." } : (p_getTooltipLines_1_.equals("Swamp Colors") ? new String[] { "Swamp Colors", "  ON - use swamp colors (default), slower", "  OFF - do not use swamp colors, faster", "The swamp colors affect grass, leaves, vines and water." } : (p_getTooltipLines_1_.equals("Smooth Biomes") ? new String[] { "Smooth Biomes", "  ON - smoothing of biome borders (default), slower", "  OFF - no smoothing of biome borders, faster", "The smoothing of biome borders is done by sampling and", "averaging the color of all surrounding blocks.", "Affected are grass, leaves, vines and water." } : (p_getTooltipLines_1_.equals("Custom Fonts") ? new String[] { "Custom Fonts", "  ON - uses custom fonts (default), slower", "  OFF - uses default font, faster", "The custom fonts are supplied by the current", "texture pack" } : (p_getTooltipLines_1_.equals("Custom Colors") ? new String[] { "Custom Colors", "  ON - uses custom colors (default), slower", "  OFF - uses default colors, faster", "The custom colors are supplied by the current", "texture pack" } : (p_getTooltipLines_1_.equals("Show Capes") ? new String[] { "Show Capes", "  ON - show player capes (default)", "  OFF - do not show player capes" } : (p_getTooltipLines_1_.equals("Connected Textures") ? new String[] { "Connected Textures", "  OFF - no connected textures (default)", "  Fast - fast connected textures", "  Fancy - fancy connected textures", "Connected textures joins the textures of glass,", "sandstone and bookshelves when placed next to", "each other. The connected textures are supplied", "by the current texture pack." } : (p_getTooltipLines_1_.equals("Far View") ? new String[] { "Far View", " OFF - (default) standard view distance", " ON - 3x view distance", "Far View is very resource demanding!", "3x view distance => 9x chunks to be loaded => FPS / 9", "Standard view distances: 32, 64, 128, 256", "Far view distances: 96, 192, 384, 512" } : (p_getTooltipLines_1_.equals("Natural Textures") ? new String[] { "Natural Textures", "  OFF - no natural textures (default)", "  ON - use natural textures", "Natural textures remove the gridlike pattern", "created by repeating blocks of the same type.", "It uses rotated and flipped variants of the base", "block texture. The configuration for the natural", "textures is supplied by the current texture pack" } : (p_getTooltipLines_1_.equals("Custom Sky") ? new String[] { "Custom Sky", "  ON - custom sky textures (default), slow", "  OFF - default sky, faster", "The custom sky textures are supplied by the current", "texture pack" } : null)))))))))))))))));
    }
    
    private String getButtonName(final String p_getButtonName_1_) {
        final int i = p_getButtonName_1_.indexOf(58);
        return (i < 0) ? p_getButtonName_1_ : p_getButtonName_1_.substring(0, i);
    }
    
    private GuiButton getSelectedButton(final int p_getSelectedButton_1_, final int p_getSelectedButton_2_) {
        for (int i = 0; i < this.buttonList.size(); ++i) {
            final GuiButton guibutton = this.buttonList.get(i);
            final int j = GuiVideoSettings.getButtonWidth(guibutton);
            final int k = GuiVideoSettings.getButtonHeight(guibutton);
            final boolean flag = p_getSelectedButton_1_ >= guibutton.xPosition && p_getSelectedButton_2_ >= guibutton.yPosition && p_getSelectedButton_1_ < guibutton.xPosition + j && p_getSelectedButton_2_ < guibutton.yPosition + k;
            if (flag) {
                return guibutton;
            }
        }
        return null;
    }
    
    static {
        GuiQualitySettingsOF.enumOptions = new GameSettings.Options[] { GameSettings.Options.MIPMAP_LEVELS, GameSettings.Options.MIPMAP_TYPE, GameSettings.Options.AF_LEVEL, GameSettings.Options.AA_LEVEL, GameSettings.Options.CLEAR_WATER, GameSettings.Options.RANDOM_MOBS, GameSettings.Options.BETTER_GRASS, GameSettings.Options.BETTER_SNOW, GameSettings.Options.CUSTOM_FONTS, GameSettings.Options.CUSTOM_COLORS, GameSettings.Options.SWAMP_COLORS, GameSettings.Options.SMOOTH_BIOMES, GameSettings.Options.CONNECTED_TEXTURES, GameSettings.Options.NATURAL_TEXTURES, GameSettings.Options.CUSTOM_SKY };
    }
}
