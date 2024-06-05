/*
 * Decompiled with CFR 0.150.
 */
package optfine;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiOptionButton;
import net.minecraft.client.gui.GuiOptionSlider;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiVideoSettings;
import net.minecraft.client.gui.GuiYesNo;
import net.minecraft.client.gui.GuiYesNoCallback;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.GameSettings;

public class GuiOtherSettingsOF
extends GuiScreen
implements GuiYesNoCallback {
    private GuiScreen prevScreen;
    protected String title = "Other Settings";
    private GameSettings settings;
    private static GameSettings.Options[] enumOptions = new GameSettings.Options[]{GameSettings.Options.LAGOMETER, GameSettings.Options.PROFILER, GameSettings.Options.WEATHER, GameSettings.Options.TIME, GameSettings.Options.USE_FULLSCREEN, GameSettings.Options.FULLSCREEN_MODE, GameSettings.Options.SHOW_FPS, GameSettings.Options.AUTOSAVE_TICKS};
    private int lastMouseX = 0;
    private int lastMouseY = 0;
    private long mouseStillTime = 0L;

    public GuiOtherSettingsOF(GuiScreen p_i36_1_, GameSettings p_i36_2_) {
        this.prevScreen = p_i36_1_;
        this.settings = p_i36_2_;
    }

    @Override
    public void initGui() {
        int i = 0;
        for (GameSettings.Options gamesettings$options : enumOptions) {
            int j = this.width / 2 - 155 + i % 2 * 160;
            int k = this.height / 6 + 21 * (i / 2) - 10;
            if (!gamesettings$options.getEnumFloat()) {
                this.buttonList.add(new GuiOptionButton(gamesettings$options.returnEnumOrdinal(), j, k, gamesettings$options, this.settings.getKeyBinding(gamesettings$options)));
            } else {
                this.buttonList.add(new GuiOptionSlider(gamesettings$options.returnEnumOrdinal(), j, k, gamesettings$options));
            }
            ++i;
        }
        this.buttonList.add(new GuiButton(210, this.width / 2 - 100, this.height / 6 + 168 + 11 - 44, "Reset Video Settings..."));
        this.buttonList.add(new GuiButton(200, this.width / 2 - 100, this.height / 6 + 168 + 11, I18n.format("gui.done", new Object[0])));
    }

    @Override
    protected void actionPerformed(GuiButton button) {
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
                GuiYesNo guiyesno = new GuiYesNo(this, "Reset all video settings to their default values?", "", 9999);
                this.mc.displayGuiScreen(guiyesno);
            }
            if (button.id != GameSettings.Options.CLOUD_HEIGHT.ordinal()) {
                ScaledResolution scaledresolution = new ScaledResolution(this.mc);
                int i = scaledresolution.getScaledWidth();
                int j = scaledresolution.getScaledHeight();
                this.setWorldAndResolution(this.mc, i, j);
            }
        }
    }

    @Override
    public void confirmClicked(boolean result, int id) {
        if (result) {
            this.mc.gameSettings.resetSettings();
        }
        this.mc.displayGuiScreen(this);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRendererObj, this.title, this.width / 2, 20, 0xFFFFFF);
        super.drawScreen(mouseX, mouseY, partialTicks);
        if (Math.abs(mouseX - this.lastMouseX) <= 5 && Math.abs(mouseY - this.lastMouseY) <= 5) {
            int i = 700;
            if (System.currentTimeMillis() >= this.mouseStillTime + (long)i) {
                int j = this.width / 2 - 150;
                int k = this.height / 6 - 5;
                if (mouseY <= k + 98) {
                    k += 105;
                }
                int l = j + 150 + 150;
                int i1 = k + 84 + 10;
                GuiButton guibutton = this.getSelectedButton(mouseX, mouseY);
                if (guibutton != null) {
                    String s = this.getButtonName(guibutton.displayString);
                    String[] astring = this.getTooltipLines(s);
                    if (astring == null) {
                        return;
                    }
                    this.drawGradientRect(j, k, l, i1, -536870912, -536870912);
                    for (int j1 = 0; j1 < astring.length; ++j1) {
                        String s1 = astring[j1];
                        this.fontRendererObj.drawStringWithShadow(s1, j + 5, k + 5 + j1 * 11, 0xDDDDDD);
                    }
                }
            }
        } else {
            this.lastMouseX = mouseX;
            this.lastMouseY = mouseY;
            this.mouseStillTime = System.currentTimeMillis();
        }
    }

    private String[] getTooltipLines(String p_getTooltipLines_1_) {
        String[] arrstring;
        if (p_getTooltipLines_1_.equals("Autosave")) {
            String[] arrstring2 = new String[3];
            arrstring2[0] = "Autosave interval";
            arrstring2[1] = "Default autosave interval (2s) is NOT RECOMMENDED.";
            arrstring = arrstring2;
            arrstring2[2] = "Autosave causes the famous Lag Spike of Death.";
        } else if (p_getTooltipLines_1_.equals("Lagometer")) {
            String[] arrstring3 = new String[8];
            arrstring3[0] = "Shows the lagometer on the debug screen (F3).";
            arrstring3[1] = "* Orange - Memory garbage collection";
            arrstring3[2] = "* Cyan - Tick";
            arrstring3[3] = "* Blue - Scheduled executables";
            arrstring3[4] = "* Purple - Chunk upload";
            arrstring3[5] = "* Red - Chunk updates";
            arrstring3[6] = "* Yellow - Visibility check";
            arrstring = arrstring3;
            arrstring3[7] = "* Green - Render terrain";
        } else if (p_getTooltipLines_1_.equals("Debug Profiler")) {
            String[] arrstring4 = new String[5];
            arrstring4[0] = "Debug Profiler";
            arrstring4[1] = "  ON - debug profiler is active, slower";
            arrstring4[2] = "  OFF - debug profiler is not active, faster";
            arrstring4[3] = "The debug profiler collects and shows debug information";
            arrstring = arrstring4;
            arrstring4[4] = "when the debug screen is open (F3)";
        } else if (p_getTooltipLines_1_.equals("Time")) {
            String[] arrstring5 = new String[6];
            arrstring5[0] = "Time";
            arrstring5[1] = " Default - normal day/night cycles";
            arrstring5[2] = " Day Only - day only";
            arrstring5[3] = " Night Only - night only";
            arrstring5[4] = "The time setting is only effective in CREATIVE mode";
            arrstring = arrstring5;
            arrstring5[5] = "and for local worlds.";
        } else if (p_getTooltipLines_1_.equals("Weather")) {
            String[] arrstring6 = new String[5];
            arrstring6[0] = "Weather";
            arrstring6[1] = "  ON - weather is active, slower";
            arrstring6[2] = "  OFF - weather is not active, faster";
            arrstring6[3] = "The weather controls rain, snow and thunderstorms.";
            arrstring = arrstring6;
            arrstring6[4] = "Weather control is only possible for local worlds.";
        } else if (p_getTooltipLines_1_.equals("Fullscreen")) {
            String[] arrstring7 = new String[5];
            arrstring7[0] = "Fullscreen";
            arrstring7[1] = "  ON - use fullscreen mode";
            arrstring7[2] = "  OFF - use window mode";
            arrstring7[3] = "Fullscreen mode may be faster or slower than";
            arrstring = arrstring7;
            arrstring7[4] = "window mode, depending on the graphics card.";
        } else if (p_getTooltipLines_1_.equals("Fullscreen Mode")) {
            String[] arrstring8 = new String[5];
            arrstring8[0] = "Fullscreen mode";
            arrstring8[1] = "  Default - use desktop screen resolution, slower";
            arrstring8[2] = "  WxH - use custom screen resolution, may be faster";
            arrstring8[3] = "The selected resolution is used in fullscreen mode (F11).";
            arrstring = arrstring8;
            arrstring8[4] = "Lower resolutions should generally be faster.";
        } else if (p_getTooltipLines_1_.equals("3D Anaglyph")) {
            String[] arrstring9 = new String[1];
            arrstring = arrstring9;
            arrstring9[0] = "3D mode used with red-cyan 3D glasses.";
        } else if (p_getTooltipLines_1_.equals("Show FPS")) {
            String[] arrstring10 = new String[6];
            arrstring10[0] = "Shows compact FPS and render information";
            arrstring10[1] = "  C: - chunk renderers";
            arrstring10[2] = "  E: - rendered entities + block entities";
            arrstring10[3] = "  U: - chunk updates";
            arrstring10[4] = "The compact FPS information is only shown when the";
            arrstring = arrstring10;
            arrstring10[5] = "debug screen is not visible.";
        } else {
            arrstring = null;
        }
        return arrstring;
    }

    private String getButtonName(String p_getButtonName_1_) {
        int i = p_getButtonName_1_.indexOf(58);
        return i < 0 ? p_getButtonName_1_ : p_getButtonName_1_.substring(0, i);
    }

    private GuiButton getSelectedButton(int p_getSelectedButton_1_, int p_getSelectedButton_2_) {
        for (int i = 0; i < this.buttonList.size(); ++i) {
            boolean flag;
            GuiButton guibutton = (GuiButton)this.buttonList.get(i);
            int j = GuiVideoSettings.getButtonWidth(guibutton);
            int k = GuiVideoSettings.getButtonHeight(guibutton);
            boolean bl = flag = p_getSelectedButton_1_ >= guibutton.xPosition && p_getSelectedButton_2_ >= guibutton.yPosition && p_getSelectedButton_1_ < guibutton.xPosition + j && p_getSelectedButton_2_ < guibutton.yPosition + k;
            if (!flag) continue;
            return guibutton;
        }
        return null;
    }
}

