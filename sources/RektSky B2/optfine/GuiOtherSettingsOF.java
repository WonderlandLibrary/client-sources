package optfine;

import net.minecraft.client.settings.*;
import net.minecraft.client.resources.*;
import net.minecraft.client.gui.*;

public class GuiOtherSettingsOF extends GuiScreen implements GuiYesNoCallback
{
    private GuiScreen prevScreen;
    protected String title;
    private GameSettings settings;
    private static GameSettings.Options[] enumOptions;
    private int lastMouseX;
    private int lastMouseY;
    private long mouseStillTime;
    
    public GuiOtherSettingsOF(final GuiScreen p_i36_1_, final GameSettings p_i36_2_) {
        this.title = "Other Settings";
        this.lastMouseX = 0;
        this.lastMouseY = 0;
        this.mouseStillTime = 0L;
        this.prevScreen = p_i36_1_;
        this.settings = p_i36_2_;
    }
    
    @Override
    public void initGui() {
        int i = 0;
        for (final GameSettings.Options gamesettings$options : GuiOtherSettingsOF.enumOptions) {
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
        this.buttonList.add(new GuiButton(210, this.width / 2 - 100, this.height / 6 + 168 + 11 - 44, "Reset Video Settings..."));
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
            if (button.id == 210) {
                this.mc.gameSettings.saveOptions();
                final GuiYesNo guiyesno = new GuiYesNo(this, "Reset all video settings to their default values?", "", 9999);
                this.mc.displayGuiScreen(guiyesno);
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
    public void confirmClicked(final boolean result, final int id) {
        if (result) {
            this.mc.gameSettings.resetSettings();
        }
        this.mc.displayGuiScreen(this);
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
                        this.fontRendererObj.drawStringWithShadow(s2, (float)(j + 5), (float)(k + 5 + j2 * 11), 14540253);
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
        return (String[])(p_getTooltipLines_1_.equals("Autosave") ? new String[] { "Autosave interval", "Default autosave interval (2s) is NOT RECOMMENDED.", "Autosave causes the famous Lag Spike of Death." } : (p_getTooltipLines_1_.equals("Lagometer") ? new String[] { "Shows the lagometer on the debug screen (F3).", "* Orange - Memory garbage collection", "* Cyan - Tick", "* Blue - Scheduled executables", "* Purple - Chunk upload", "* Red - Chunk updates", "* Yellow - Visibility check", "* Green - Render terrain" } : (p_getTooltipLines_1_.equals("Debug Profiler") ? new String[] { "Debug Profiler", "  ON - debug profiler is active, slower", "  OFF - debug profiler is not active, faster", "The debug profiler collects and shows debug information", "when the debug screen is open (F3)" } : (p_getTooltipLines_1_.equals("Time") ? new String[] { "Time", " Default - normal day/night cycles", " Day Only - day only", " Night Only - night only", "The time setting is only effective in CREATIVE mode", "and for local worlds." } : (p_getTooltipLines_1_.equals("Weather") ? new String[] { "Weather", "  ON - weather is active, slower", "  OFF - weather is not active, faster", "The weather controls rain, snow and thunderstorms.", "Weather control is only possible for local worlds." } : (p_getTooltipLines_1_.equals("Fullscreen") ? new String[] { "Fullscreen", "  ON - use fullscreen mode", "  OFF - use window mode", "Fullscreen mode may be faster or slower than", "window mode, depending on the graphics card." } : (p_getTooltipLines_1_.equals("Fullscreen Mode") ? new String[] { "Fullscreen mode", "  Default - use desktop screen resolution, slower", "  WxH - use custom screen resolution, may be faster", "The selected resolution is used in fullscreen mode (F11).", "Lower resolutions should generally be faster." } : (p_getTooltipLines_1_.equals("3D Anaglyph") ? new String[] { "3D mode used with red-cyan 3D glasses." } : (p_getTooltipLines_1_.equals("Show FPS") ? new String[] { "Shows compact FPS and render information", "  C: - chunk renderers", "  E: - rendered entities + block entities", "  U: - chunk updates", "The compact FPS information is only shown when the", "debug screen is not visible." } : null)))))))));
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
        GuiOtherSettingsOF.enumOptions = new GameSettings.Options[] { GameSettings.Options.LAGOMETER, GameSettings.Options.PROFILER, GameSettings.Options.WEATHER, GameSettings.Options.TIME, GameSettings.Options.USE_FULLSCREEN, GameSettings.Options.FULLSCREEN_MODE, GameSettings.Options.SHOW_FPS, GameSettings.Options.AUTOSAVE_TICKS };
    }
}
