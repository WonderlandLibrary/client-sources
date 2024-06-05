package optifine;

import net.minecraft.client.settings.*;
import net.minecraft.client.resources.*;
import net.minecraft.client.gui.*;

public class GuiQualitySettingsOF extends GuiScreen
{
    private static GameSettings.Options[] enumOptions;
    protected String title;
    private GuiScreen prevScreen;
    private GameSettings settings;
    private TooltipManager tooltipManager;
    
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
            final int x = GuiQualitySettingsOF.width / 2 - 155 + i % 2 * 160;
            final int y = GuiQualitySettingsOF.height / 6 + 21 * (i / 2) - 12;
            if (!opt.getEnumFloat()) {
                this.buttonList.add(new GuiOptionButtonOF(opt.returnEnumOrdinal(), x, y, opt, this.settings.getKeyBinding(opt)));
            }
            else {
                this.buttonList.add(new GuiOptionSliderOF(opt.returnEnumOrdinal(), x, y, opt));
            }
        }
        this.buttonList.add(new GuiButton(200, GuiQualitySettingsOF.width / 2 - 100, GuiQualitySettingsOF.height / 6 + 168 + 11, I18n.format("gui.done", new Object[0])));
    }
    
    @Override
    protected void actionPerformed(final GuiButton guibutton) {
        if (guibutton.enabled) {
            if (guibutton.id < 200 && guibutton instanceof GuiOptionButton) {
                this.settings.setOptionValue(((GuiOptionButton)guibutton).returnEnumOptions(), 1);
                guibutton.displayString = this.settings.getKeyBinding(GameSettings.Options.getEnumOptions(guibutton.id));
            }
            if (guibutton.id == 200) {
                GuiQualitySettingsOF.mc.gameSettings.saveOptions();
                GuiQualitySettingsOF.mc.displayGuiScreen(this.prevScreen);
            }
            if (guibutton.id != GameSettings.Options.AA_LEVEL.ordinal()) {
                final ScaledResolution sr = new ScaledResolution(GuiQualitySettingsOF.mc, GuiQualitySettingsOF.mc.displayWidth, GuiQualitySettingsOF.mc.displayHeight);
                this.setWorldAndResolution(GuiQualitySettingsOF.mc, sr.getScaledWidth(), sr.getScaledHeight());
            }
        }
    }
    
    @Override
    public void drawScreen(final int x, final int y, final float f) {
        this.drawDefaultBackground();
        Gui.drawCenteredString(this.fontRendererObj, this.title, GuiQualitySettingsOF.width / 2, 15, 16777215);
        super.drawScreen(x, y, f);
        this.tooltipManager.drawTooltips(x, y, this.buttonList);
    }
    
    static {
        GuiQualitySettingsOF.enumOptions = new GameSettings.Options[] { GameSettings.Options.MIPMAP_LEVELS, GameSettings.Options.MIPMAP_TYPE, GameSettings.Options.AF_LEVEL, GameSettings.Options.AA_LEVEL, GameSettings.Options.CLEAR_WATER, GameSettings.Options.RANDOM_MOBS, GameSettings.Options.BETTER_GRASS, GameSettings.Options.BETTER_SNOW, GameSettings.Options.CUSTOM_FONTS, GameSettings.Options.CUSTOM_COLORS, GameSettings.Options.SWAMP_COLORS, GameSettings.Options.SMOOTH_BIOMES, GameSettings.Options.CONNECTED_TEXTURES, GameSettings.Options.NATURAL_TEXTURES, GameSettings.Options.CUSTOM_SKY, GameSettings.Options.CUSTOM_ITEMS, GameSettings.Options.DYNAMIC_LIGHTS };
    }
}
