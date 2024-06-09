/*
 * Decompiled with CFR 0.145.
 */
package optifine;

import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiOptionButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiYesNo;
import net.minecraft.client.gui.GuiYesNoCallback;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.GameSettings;
import optifine.GuiOptionButtonOF;
import optifine.GuiOptionSliderOF;
import optifine.TooltipManager;

public class GuiOtherSettingsOF
extends GuiScreen
implements GuiYesNoCallback {
    private GuiScreen prevScreen;
    protected String title;
    private GameSettings settings;
    private static GameSettings.Options[] enumOptions = new GameSettings.Options[]{GameSettings.Options.LAGOMETER, GameSettings.Options.PROFILER, GameSettings.Options.WEATHER, GameSettings.Options.TIME, GameSettings.Options.USE_FULLSCREEN, GameSettings.Options.FULLSCREEN_MODE, GameSettings.Options.SHOW_FPS, GameSettings.Options.AUTOSAVE_TICKS, GameSettings.Options.ANAGLYPH};
    private TooltipManager tooltipManager = new TooltipManager(this);

    public GuiOtherSettingsOF(GuiScreen guiscreen, GameSettings gamesettings) {
        this.prevScreen = guiscreen;
        this.settings = gamesettings;
    }

    @Override
    public void initGui() {
        this.title = I18n.format("of.options.otherTitle", new Object[0]);
        this.buttonList.clear();
        for (int i2 = 0; i2 < enumOptions.length; ++i2) {
            GameSettings.Options enumoptions = enumOptions[i2];
            int x2 = this.width / 2 - 155 + i2 % 2 * 160;
            int y2 = this.height / 6 + 21 * (i2 / 2) - 12;
            if (!enumoptions.getEnumFloat()) {
                this.buttonList.add(new GuiOptionButtonOF(enumoptions.returnEnumOrdinal(), x2, y2, enumoptions, this.settings.getKeyBinding(enumoptions)));
                continue;
            }
            this.buttonList.add(new GuiOptionSliderOF(enumoptions.returnEnumOrdinal(), x2, y2, enumoptions));
        }
        this.buttonList.add(new GuiButton(210, this.width / 2 - 100, this.height / 6 + 168 + 11 - 44, I18n.format("of.options.other.reset", new Object[0])));
        this.buttonList.add(new GuiButton(200, this.width / 2 - 100, this.height / 6 + 168 + 11, I18n.format("gui.done", new Object[0])));
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
                GuiYesNo guiyesno = new GuiYesNo(this, I18n.format("of.message.other.reset", new Object[0]), "", 9999);
                this.mc.displayGuiScreen(guiyesno);
            }
        }
    }

    @Override
    public void confirmClicked(boolean flag, int i2) {
        if (flag) {
            this.mc.gameSettings.resetSettings();
        }
        this.mc.displayGuiScreen(this);
    }

    @Override
    public void drawScreen(int x2, int y2, float f2) {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRendererObj, this.title, this.width / 2, 15, 16777215);
        super.drawScreen(x2, y2, f2);
        this.tooltipManager.drawTooltips(x2, y2, this.buttonList);
    }
}

