// 
// Decompiled by Procyon v0.5.36
// 

package optifine;

import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.GuiOptionButton;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.gui.GuiScreen;

public class GuiQualitySettingsOF extends GuiScreen
{
    private GuiScreen prevScreen;
    protected String title;
    private GameSettings settings;
    private static GameSettings.Options[] enumOptions;
    private TooltipManager tooltipManager;
    
    static {
        GuiQualitySettingsOF.enumOptions = new GameSettings.Options[] { GameSettings.Options.MIPMAP_LEVELS, GameSettings.Options.MIPMAP_TYPE, GameSettings.Options.AF_LEVEL, GameSettings.Options.AA_LEVEL, GameSettings.Options.CLEAR_WATER, GameSettings.Options.RANDOM_MOBS, GameSettings.Options.BETTER_GRASS, GameSettings.Options.BETTER_SNOW, GameSettings.Options.CUSTOM_FONTS, GameSettings.Options.CUSTOM_COLORS, GameSettings.Options.SWAMP_COLORS, GameSettings.Options.SMOOTH_BIOMES, GameSettings.Options.CONNECTED_TEXTURES, GameSettings.Options.NATURAL_TEXTURES, GameSettings.Options.CUSTOM_SKY, GameSettings.Options.CUSTOM_ITEMS, GameSettings.Options.DYNAMIC_LIGHTS };
    }
    
    public GuiQualitySettingsOF(final GuiScreen guiscreen, final GameSettings gamesettings) {
        this.tooltipManager = new TooltipManager(this);
        this.prevScreen = guiscreen;
        this.settings = gamesettings;
    }
    
    @Override
    public void initGui() {
        this.title = I18n.format("of.options.qualityTitle", new Object[0]);
        this.buttonList.clear();
        for (int i = 0; i < GuiQualitySettingsOF.enumOptions.length; ++i) {
            final GameSettings.Options opt = GuiQualitySettingsOF.enumOptions[i];
            final int x = this.width / 2 - 155 + i % 2 * 160;
            final int y = this.height / 6 + 21 * (i / 2) - 12;
            if (!opt.getEnumFloat()) {
                this.buttonList.add(new GuiOptionButtonOF(opt.returnEnumOrdinal(), x, y, opt, this.settings.getKeyBinding(opt)));
            }
            else {
                this.buttonList.add(new GuiOptionSliderOF(opt.returnEnumOrdinal(), x, y, opt));
            }
        }
        this.buttonList.add(new GuiButton(200, this.width / 2 - 100, this.height / 6 + 168 + 11, I18n.format("gui.done", new Object[0])));
    }
    
    @Override
    protected void actionPerformed(final GuiButton guibutton) {
        if (guibutton.enabled) {
            if (guibutton.id < 200 && guibutton instanceof GuiOptionButton) {
                this.settings.setOptionValue(((GuiOptionButton)guibutton).returnEnumOptions(), 1);
                guibutton.displayString = this.settings.getKeyBinding(GameSettings.Options.getEnumOptions(guibutton.id));
            }
            if (guibutton.id == 200) {
                this.mc.gameSettings.saveOptions();
                this.mc.displayGuiScreen(this.prevScreen);
            }
            if (guibutton.id != GameSettings.Options.AA_LEVEL.ordinal()) {
                final ScaledResolution sr = new ScaledResolution(this.mc, this.mc.displayWidth, this.mc.displayHeight);
                this.setWorldAndResolution(this.mc, sr.getScaledWidth(), sr.getScaledHeight());
            }
        }
    }
    
    @Override
    public void drawScreen(final int x, final int y, final float f) {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRendererObj, this.title, this.width / 2, 15, 16777215);
        super.drawScreen(x, y, f);
        this.tooltipManager.drawTooltips(x, y, this.buttonList);
    }
}
