/*
 * Decompiled with CFR 0.143.
 */
package net.minecraft.src;

import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
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
    private static GameSettings.Options[] enumOptions = new GameSettings.Options[]{GameSettings.Options.LAGOMETER, GameSettings.Options.PROFILER, GameSettings.Options.WEATHER, GameSettings.Options.TIME, GameSettings.Options.USE_FULLSCREEN, GameSettings.Options.FULLSCREEN_MODE, GameSettings.Options.AUTOSAVE_TICKS};
    private int lastMouseX = 0;
    private int lastMouseY = 0;
    private long mouseStillTime = 0L;

    public GuiOtherSettingsOF(GuiScreen guiscreen, GameSettings gamesettings) {
        this.prevScreen = guiscreen;
        this.settings = gamesettings;
    }

    @Override
    public void initGui() {
        int i = 0;
        for (GameSettings.Options enumoptions : enumOptions) {
            int x = width / 2 - 155 + i % 2 * 160;
            int y = height / 6 + 21 * (i / 2) - 10;
            if (!enumoptions.getEnumFloat()) {
                this.buttonList.add(new GuiOptionButton(enumoptions.returnEnumOrdinal(), x, y, enumoptions, this.settings.getKeyBinding(enumoptions)));
            } else {
                this.buttonList.add(new GuiOptionSlider(enumoptions.returnEnumOrdinal(), x, y, enumoptions));
            }
            ++i;
        }
        this.buttonList.add(new GuiButton(210, width / 2 - 100, height / 6 + 168 + 11 - 44, "Reset Video Settings..."));
        this.buttonList.add(new GuiButton(200, width / 2 - 100, height / 6 + 168 + 11, I18n.format("gui.done", new Object[0])));
    }

    @Override
    protected void actionPerformed(GuiButton guibutton) {
        if (guibutton.enabled) {
            if (guibutton.id < 200 && guibutton instanceof GuiOptionButton) {
                this.settings.setOptionValue(((GuiOptionButton)guibutton).returnEnumOptions(), 1);
                guibutton.displayString = this.settings.getKeyBinding(GameSettings.Options.getEnumOptions(guibutton.id));
            }
            if (guibutton.id == 200) {
                this.mc.gameSettings.saveOptions();
                this.mc.displayGuiScreen(this.prevScreen);
            }
            if (guibutton.id == 210) {
                this.mc.gameSettings.saveOptions();
                GuiYesNo scaledresolution = new GuiYesNo(this, "Reset all video settings to their default values?", "", 9999);
                this.mc.displayGuiScreen(scaledresolution);
            }
            if (guibutton.id != GameSettings.Options.CLOUD_HEIGHT.ordinal()) {
                ScaledResolution scaledresolution1 = new ScaledResolution(this.mc, this.mc.displayWidth, this.mc.displayHeight);
                int i = ScaledResolution.getScaledWidth();
                int j = ScaledResolution.getScaledHeight();
                this.setWorldAndResolution(this.mc, i, j);
            }
        }
    }

    @Override
    public void confirmClicked(boolean flag, int i) {
        if (flag) {
            this.mc.gameSettings.resetSettings();
        }
        this.mc.displayGuiScreen(this);
    }

    @Override
    public void drawScreen(int x, int y, float f) {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRendererObj, this.title, width / 2, 20, 16777215);
        super.drawScreen(x, y, f);
        if (Math.abs(x - this.lastMouseX) <= 5 && Math.abs(y - this.lastMouseY) <= 5) {
            int activateDelay = 700;
            if (System.currentTimeMillis() >= this.mouseStillTime + (long)activateDelay) {
                int x1 = width / 2 - 150;
                int y1 = height / 6 - 5;
                if (y <= y1 + 98) {
                    y1 += 105;
                }
                int x2 = x1 + 150 + 150;
                int y2 = y1 + 84 + 10;
                GuiButton btn = this.getSelectedButton(x, y);
                if (btn != null) {
                    String s = this.getButtonName(btn.displayString);
                    String[] lines = this.getTooltipLines(s);
                    if (lines == null) {
                        return;
                    }
                    this.drawGradientRect(x1, y1, x2, y2, -536870912, -536870912);
                    for (int i = 0; i < lines.length; ++i) {
                        String line = lines[i];
                        this.fontRendererObj.func_175063_a(line, x1 + 5, y1 + 5 + i * 11, 14540253);
                    }
                }
            }
        } else {
            this.lastMouseX = x;
            this.lastMouseY = y;
            this.mouseStillTime = System.currentTimeMillis();
        }
    }

    private String[] getTooltipLines(String btnName) {
        String[] arrstring;
        if (btnName.equals("Autosave")) {
            String[] arrstring2 = new String[3];
            arrstring2[0] = "Autosave interval";
            arrstring2[1] = "Default autosave interval (2s) is NOT RECOMMENDED.";
            arrstring = arrstring2;
            arrstring2[2] = "Autosave causes the famous Lag Spike of Death.";
        } else if (btnName.equals("Lagometer")) {
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
        } else if (btnName.equals("Debug Profiler")) {
            String[] arrstring4 = new String[5];
            arrstring4[0] = "Debug Profiler";
            arrstring4[1] = "  ON - debug profiler is active, slower";
            arrstring4[2] = "  OFF - debug profiler is not active, faster";
            arrstring4[3] = "The debug profiler collects and shows debug information";
            arrstring = arrstring4;
            arrstring4[4] = "when the debug screen is open (F3)";
        } else if (btnName.equals("Time")) {
            String[] arrstring5 = new String[6];
            arrstring5[0] = "Time";
            arrstring5[1] = " Default - normal day/night cycles";
            arrstring5[2] = " Day Only - day only";
            arrstring5[3] = " Night Only - night only";
            arrstring5[4] = "The time setting is only effective in CREATIVE mode";
            arrstring = arrstring5;
            arrstring5[5] = "and for local worlds.";
        } else if (btnName.equals("Weather")) {
            String[] arrstring6 = new String[5];
            arrstring6[0] = "Weather";
            arrstring6[1] = "  ON - weather is active, slower";
            arrstring6[2] = "  OFF - weather is not active, faster";
            arrstring6[3] = "The weather controls rain, snow and thunderstorms.";
            arrstring = arrstring6;
            arrstring6[4] = "Weather control is only possible for local worlds.";
        } else if (btnName.equals("Fullscreen")) {
            String[] arrstring7 = new String[5];
            arrstring7[0] = "Fullscreen";
            arrstring7[1] = "  ON - use fullscreen mode";
            arrstring7[2] = "  OFF - use window mode";
            arrstring7[3] = "Fullscreen mode may be faster or slower than";
            arrstring = arrstring7;
            arrstring7[4] = "window mode, depending on the graphics card.";
        } else if (btnName.equals("Fullscreen Mode")) {
            String[] arrstring8 = new String[5];
            arrstring8[0] = "Fullscreen mode";
            arrstring8[1] = "  Default - use desktop screen resolution, slower";
            arrstring8[2] = "  WxH - use custom screen resolution, may be faster";
            arrstring8[3] = "The selected resolution is used in fullscreen mode (F11).";
            arrstring = arrstring8;
            arrstring8[4] = "Lower resolutions should generally be faster.";
        } else if (btnName.equals("3D Anaglyph")) {
            String[] arrstring9 = new String[1];
            arrstring = arrstring9;
            arrstring9[0] = "3D mode used with red-cyan 3D glasses.";
        } else {
            arrstring = null;
        }
        return arrstring;
    }

    private String getButtonName(String displayString) {
        int pos = displayString.indexOf(58);
        return pos < 0 ? displayString : displayString.substring(0, pos);
    }

    private GuiButton getSelectedButton(int i, int j) {
        for (int k = 0; k < this.buttonList.size(); ++k) {
            boolean flag;
            GuiButton btn = (GuiButton)this.buttonList.get(k);
            int btnWidth = GuiVideoSettings.getButtonWidth(btn);
            int btnHeight = GuiVideoSettings.getButtonHeight(btn);
            boolean bl = flag = i >= btn.xPosition && j >= btn.yPosition && i < btn.xPosition + btnWidth && j < btn.yPosition + btnHeight;
            if (!flag) continue;
            return btn;
        }
        return null;
    }
}

