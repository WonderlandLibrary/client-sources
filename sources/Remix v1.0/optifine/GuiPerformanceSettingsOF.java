package optifine;

import net.minecraft.client.settings.*;
import net.minecraft.client.resources.*;
import net.minecraft.client.gui.*;

public class GuiPerformanceSettingsOF extends GuiScreen
{
    private static GameSettings.Options[] enumOptions;
    protected String title;
    private GuiScreen prevScreen;
    private GameSettings settings;
    private TooltipManager tooltipManager;
    
    public GuiPerformanceSettingsOF(final GuiScreen guiscreen, final GameSettings gamesettings) {
        this.tooltipManager = new TooltipManager(this);
        this.prevScreen = guiscreen;
        this.settings = gamesettings;
    }
    
    @Override
    public void initGui() {
        this.title = I18n.format("of.options.performanceTitle", new Object[0]);
        this.buttonList.clear();
        for (int i = 0; i < GuiPerformanceSettingsOF.enumOptions.length; ++i) {
            final GameSettings.Options enumoptions = GuiPerformanceSettingsOF.enumOptions[i];
            final int x = GuiPerformanceSettingsOF.width / 2 - 155 + i % 2 * 160;
            final int y = GuiPerformanceSettingsOF.height / 6 + 21 * (i / 2) - 12;
            if (!enumoptions.getEnumFloat()) {
                this.buttonList.add(new GuiOptionButtonOF(enumoptions.returnEnumOrdinal(), x, y, enumoptions, this.settings.getKeyBinding(enumoptions)));
            }
            else {
                this.buttonList.add(new GuiOptionSliderOF(enumoptions.returnEnumOrdinal(), x, y, enumoptions));
            }
        }
        this.buttonList.add(new GuiButton(200, GuiPerformanceSettingsOF.width / 2 - 100, GuiPerformanceSettingsOF.height / 6 + 168 + 11, I18n.format("gui.done", new Object[0])));
    }
    
    @Override
    protected void actionPerformed(final GuiButton guibutton) {
        if (guibutton.enabled) {
            if (guibutton.id < 200 && guibutton instanceof GuiOptionButton) {
                this.settings.setOptionValue(((GuiOptionButton)guibutton).returnEnumOptions(), 1);
                guibutton.displayString = this.settings.getKeyBinding(GameSettings.Options.getEnumOptions(guibutton.id));
            }
            if (guibutton.id == 200) {
                GuiPerformanceSettingsOF.mc.gameSettings.saveOptions();
                GuiPerformanceSettingsOF.mc.displayGuiScreen(this.prevScreen);
            }
        }
    }
    
    @Override
    public void drawScreen(final int x, final int y, final float f) {
        this.drawDefaultBackground();
        Gui.drawCenteredString(this.fontRendererObj, this.title, GuiPerformanceSettingsOF.width / 2, 15, 16777215);
        super.drawScreen(x, y, f);
        this.tooltipManager.drawTooltips(x, y, this.buttonList);
    }
    
    static {
        GuiPerformanceSettingsOF.enumOptions = new GameSettings.Options[] { GameSettings.Options.SMOOTH_FPS, GameSettings.Options.SMOOTH_WORLD, GameSettings.Options.FAST_RENDER, GameSettings.Options.FAST_MATH, GameSettings.Options.CHUNK_UPDATES, GameSettings.Options.CHUNK_UPDATES_DYNAMIC, GameSettings.Options.LAZY_CHUNK_LOADING };
    }
}
