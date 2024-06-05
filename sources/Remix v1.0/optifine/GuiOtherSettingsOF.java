package optifine;

import net.minecraft.client.settings.*;
import net.minecraft.client.resources.*;
import me.satisfactory.base.gui.*;
import net.minecraft.client.gui.*;

public class GuiOtherSettingsOF extends GuiScreen implements GuiYesNoCallback
{
    private static GameSettings.Options[] enumOptions;
    protected String title;
    private GuiScreen prevScreen;
    private GameSettings settings;
    private TooltipManager tooltipManager;
    
    public GuiOtherSettingsOF(final GuiScreen guiscreen, final GameSettings gamesettings) {
        this.tooltipManager = new TooltipManager(this);
        this.prevScreen = guiscreen;
        this.settings = gamesettings;
    }
    
    @Override
    public void initGui() {
        this.title = I18n.format("of.options.otherTitle", new Object[0]);
        this.buttonList.clear();
        for (int i = 0; i < GuiOtherSettingsOF.enumOptions.length; ++i) {
            final GameSettings.Options enumoptions = GuiOtherSettingsOF.enumOptions[i];
            final int x = GuiOtherSettingsOF.width / 2 - 155 + i % 2 * 160;
            final int y = GuiOtherSettingsOF.height / 6 + 21 * (i / 2) - 12;
            if (!enumoptions.getEnumFloat()) {
                this.buttonList.add(new GuiOptionButtonOF(enumoptions.returnEnumOrdinal(), x, y, enumoptions, this.settings.getKeyBinding(enumoptions)));
            }
            else {
                this.buttonList.add(new GuiOptionSliderOF(enumoptions.returnEnumOrdinal(), x, y, enumoptions));
            }
        }
        this.buttonList.add(new DarkButton(210, GuiOtherSettingsOF.width / 2 - 100, GuiOtherSettingsOF.height / 6 + 168 + 11 - 44, I18n.format("of.options.other.reset", new Object[0])));
        this.buttonList.add(new DarkButton(200, GuiOtherSettingsOF.width / 2 - 100, GuiOtherSettingsOF.height / 6 + 168 + 11, I18n.format("gui.done", new Object[0])));
    }
    
    @Override
    protected void actionPerformed(final GuiButton guibutton) {
        if (guibutton.enabled) {
            if (guibutton.id < 200 && guibutton instanceof GuiOptionButton) {
                this.settings.setOptionValue(((GuiOptionButton)guibutton).returnEnumOptions(), 1);
                guibutton.displayString = this.settings.getKeyBinding(GameSettings.Options.getEnumOptions(guibutton.id));
            }
            if (guibutton.id == 200) {
                GuiOtherSettingsOF.mc.gameSettings.saveOptions();
                GuiOtherSettingsOF.mc.displayGuiScreen(this.prevScreen);
            }
            if (guibutton.id == 210) {
                GuiOtherSettingsOF.mc.gameSettings.saveOptions();
                final GuiYesNo guiyesno = new GuiYesNo(this, I18n.format("of.message.other.reset", new Object[0]), "", 9999);
                GuiOtherSettingsOF.mc.displayGuiScreen(guiyesno);
            }
        }
    }
    
    @Override
    public void confirmClicked(final boolean flag, final int i) {
        if (flag) {
            GuiOtherSettingsOF.mc.gameSettings.resetSettings();
        }
        GuiOtherSettingsOF.mc.displayGuiScreen(this);
    }
    
    @Override
    public void drawScreen(final int x, final int y, final float f) {
        this.drawDefaultBackground();
        Gui.drawCenteredString(this.fontRendererObj, this.title, GuiOtherSettingsOF.width / 2, 15, 16777215);
        super.drawScreen(x, y, f);
        this.tooltipManager.drawTooltips(x, y, this.buttonList);
    }
    
    static {
        GuiOtherSettingsOF.enumOptions = new GameSettings.Options[] { GameSettings.Options.LAGOMETER, GameSettings.Options.PROFILER, GameSettings.Options.WEATHER, GameSettings.Options.TIME, GameSettings.Options.USE_FULLSCREEN, GameSettings.Options.FULLSCREEN_MODE, GameSettings.Options.SHOW_FPS, GameSettings.Options.AUTOSAVE_TICKS, GameSettings.Options.ANAGLYPH };
    }
}
