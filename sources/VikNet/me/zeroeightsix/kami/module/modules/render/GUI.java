package me.zeroeightsix.kami.module.modules.render;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.zeroeightsix.kami.KamiMod;
import me.zeroeightsix.kami.module.Module;
import me.zeroeightsix.kami.setting.Setting;
import me.zeroeightsix.kami.setting.Settings;
import me.zeroeightsix.kami.util.GuiManager;

/**
 * Created 24 November 2019 by hub
 * Updated 1 December 2019 by hub
 */
@Module.Info(name = "GUI", category = Module.Category.RENDER, description = "Change the GUI")
public class GUI extends Module {

    private Setting<Integer> guiRed = register(Settings.integerBuilder("GuiRed").withMinimum(1).withValue(255).withMaximum(255).build());
    private Setting<Integer> guiGreen = register(Settings.integerBuilder("GuiGreen").withMinimum(1).withValue(255).withMaximum(255).build());
    private Setting<Integer> guiBlue = register(Settings.integerBuilder("GuiBlue").withMinimum(1).withValue(1).withMaximum(255).build());
    private Setting<TextColor> textColor = register(Settings.e("TextColor", TextColor.GRAY));
    private Setting<GuiManager.ModuleListMode> moduleListMode = register(Settings.e("ModuleListMode", GuiManager.ModuleListMode.STATIC));
    private Setting<Integer> moduleListRed = register(Settings.integerBuilder("ModuleListRed").withMinimum(1).withValue(138).withMaximum(255).withVisibility(v -> moduleListMode.getValue().equals(GuiManager.ModuleListMode.STATIC)).build());
    private Setting<Integer> moduleListGreen = register(Settings.integerBuilder("ModuleListGreen").withMinimum(1).withValue(255).withMaximum(255).withVisibility(v -> moduleListMode.getValue().equals(GuiManager.ModuleListMode.STATIC)).build());
    private Setting<Integer> moduleListBlue = register(Settings.integerBuilder("ModuleListBlue").withMinimum(1).withValue(1).withMaximum(255).withVisibility(v -> moduleListMode.getValue().equals(GuiManager.ModuleListMode.STATIC)).build());

    private Setting<Integer> textRadarPlayers = register(Settings.integerBuilder("TextRadarPlayers").withMinimum(1).withValue(8).withMaximum(32).build());
    private Setting<Boolean> textRadarPots = register(Settings.b("TextRadarPots", true));

    private GuiManager guiManager;

    @Override
    public void onDisable() {
        this.enable();
    }

    @Override
    public void onUpdate() {

        if (guiManager == null) {
            guiManager = KamiMod.getInstance().guiManager;
        }

        // Gui Color
        guiManager.setGuiColors(guiRed.getValue() / 255f, guiGreen.getValue() / 255f, guiBlue.getValue() / 255f);

        // Text Color
        // TODO: merge ChatTextUtils.FormatCodes and Colors.TextColor
        if (textColor.getValue().equals(TextColor.BLACK)) {
            guiManager.setTextColor(ChatFormatting.BLACK.toString());
        } else if (textColor.getValue().equals(TextColor.DARK_BLUE)) {
            guiManager.setTextColor(ChatFormatting.DARK_BLUE.toString());
        } else if (textColor.getValue().equals(TextColor.DARK_GREEN)) {
            guiManager.setTextColor(ChatFormatting.DARK_GREEN.toString());
        } else if (textColor.getValue().equals(TextColor.DARK_AQUA)) {
            guiManager.setTextColor(ChatFormatting.DARK_AQUA.toString());
        } else if (textColor.getValue().equals(TextColor.DARK_RED)) {
            guiManager.setTextColor(ChatFormatting.DARK_RED.toString());
        } else if (textColor.getValue().equals(TextColor.DARK_PURPLE)) {
            guiManager.setTextColor(ChatFormatting.DARK_PURPLE.toString());
        } else if (textColor.getValue().equals(TextColor.GOLD)) {
            guiManager.setTextColor(ChatFormatting.GOLD.toString());
        } else if (textColor.getValue().equals(TextColor.GRAY)) {
            guiManager.setTextColor(ChatFormatting.GRAY.toString());
        } else if (textColor.getValue().equals(TextColor.DARK_GRAY)) {
            guiManager.setTextColor(ChatFormatting.DARK_GRAY.toString());
        } else if (textColor.getValue().equals(TextColor.BLUE)) {
            guiManager.setTextColor(ChatFormatting.BLUE.toString());
        } else if (textColor.getValue().equals(TextColor.GREEN)) {
            guiManager.setTextColor(ChatFormatting.GREEN.toString());
        } else if (textColor.getValue().equals(TextColor.AQUA)) {
            guiManager.setTextColor(ChatFormatting.AQUA.toString());
        } else if (textColor.getValue().equals(TextColor.RED)) {
            guiManager.setTextColor(ChatFormatting.RED.toString());
        } else if (textColor.getValue().equals(TextColor.LIGHT_PURPLE)) {
            guiManager.setTextColor(ChatFormatting.LIGHT_PURPLE.toString());
        } else if (textColor.getValue().equals(TextColor.YELLOW)) {
            guiManager.setTextColor(ChatFormatting.YELLOW.toString());
        } else if (textColor.getValue().equals(TextColor.WHITE)) {
            guiManager.setTextColor(ChatFormatting.WHITE.toString());
        } else {
            guiManager.setTextColor(ChatFormatting.WHITE.toString());
        }

        // Module List
        guiManager.setModuleListColors(moduleListRed.getValue(), moduleListGreen.getValue(), moduleListBlue.getValue());
        guiManager.setModuleListMode(moduleListMode.getValue());

        // Text Radar
        guiManager.setTextRadarPlayers(textRadarPlayers.getValue());
        guiManager.setTextRadarPots(textRadarPots.getValue());

    }

    private enum TextColor {
        BLACK, DARK_BLUE, DARK_GREEN, DARK_AQUA, DARK_RED, DARK_PURPLE, GOLD, GRAY, DARK_GRAY, BLUE, GREEN, AQUA, RED, LIGHT_PURPLE, YELLOW, WHITE
    }

}
