package optifine;

import net.minecraft.client.settings.*;
import net.minecraft.client.resources.*;
import net.minecraft.client.gui.*;

public class GuiDetailSettingsOF extends GuiScreen
{
    private static GameSettings.Options[] enumOptions;
    protected String title;
    private GuiScreen prevScreen;
    private GameSettings settings;
    private TooltipManager tooltipManager;
    
    public GuiDetailSettingsOF(final GuiScreen guiscreen, final GameSettings gamesettings) {
        this.tooltipManager = new TooltipManager(this);
        this.prevScreen = guiscreen;
        this.settings = gamesettings;
    }
    
    @Override
    public void initGui() {
        this.title = I18n.format("of.options.detailsTitle", new Object[0]);
        this.buttonList.clear();
        for (int i = 0; i < GuiDetailSettingsOF.enumOptions.length; ++i) {
            final GameSettings.Options opt = GuiDetailSettingsOF.enumOptions[i];
            final int x = GuiDetailSettingsOF.width / 2 - 155 + i % 2 * 160;
            final int y = GuiDetailSettingsOF.height / 6 + 21 * (i / 2) - 12;
            if (!opt.getEnumFloat()) {
                this.buttonList.add(new GuiOptionButtonOF(opt.returnEnumOrdinal(), x, y, opt, this.settings.getKeyBinding(opt)));
            }
            else {
                this.buttonList.add(new GuiOptionSliderOF(opt.returnEnumOrdinal(), x, y, opt));
            }
        }
        this.buttonList.add(new GuiButton(200, GuiDetailSettingsOF.width / 2 - 100, GuiDetailSettingsOF.height / 6 + 168 + 11, I18n.format("gui.done", new Object[0])));
    }
    
    @Override
    protected void actionPerformed(final GuiButton guibutton) {
        if (guibutton.enabled) {
            if (guibutton.id < 200 && guibutton instanceof GuiOptionButton) {
                this.settings.setOptionValue(((GuiOptionButton)guibutton).returnEnumOptions(), 1);
                guibutton.displayString = this.settings.getKeyBinding(GameSettings.Options.getEnumOptions(guibutton.id));
            }
            if (guibutton.id == 200) {
                GuiDetailSettingsOF.mc.gameSettings.saveOptions();
                GuiDetailSettingsOF.mc.displayGuiScreen(this.prevScreen);
            }
        }
    }
    
    @Override
    public void drawScreen(final int x, final int y, final float f) {
        this.drawDefaultBackground();
        Gui.drawCenteredString(this.fontRendererObj, this.title, GuiDetailSettingsOF.width / 2, 15, 16777215);
        super.drawScreen(x, y, f);
        this.tooltipManager.drawTooltips(x, y, this.buttonList);
    }
    
    static {
        GuiDetailSettingsOF.enumOptions = new GameSettings.Options[] { GameSettings.Options.CLOUDS, GameSettings.Options.CLOUD_HEIGHT, GameSettings.Options.TREES, GameSettings.Options.RAIN, GameSettings.Options.SKY, GameSettings.Options.STARS, GameSettings.Options.SUN_MOON, GameSettings.Options.SHOW_CAPES, GameSettings.Options.TRANSLUCENT_BLOCKS, GameSettings.Options.HELD_ITEM_TOOLTIPS, GameSettings.Options.DROPPED_ITEMS, GameSettings.Options.VIGNETTE, GameSettings.Options.DYNAMIC_FOV };
    }
}
