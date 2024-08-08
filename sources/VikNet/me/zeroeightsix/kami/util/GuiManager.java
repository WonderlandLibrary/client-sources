package me.zeroeightsix.kami.util;

import com.mojang.realmsclient.gui.ChatFormatting;

/**
 * Created 24 November 2019 by hub
 * Updated 1 December 2019 by hub
 */
public class GuiManager {

    private float guiRed;
    private float guiGreen;
    private float guiBlue;

    private String textColor;

    private int moduleListRed;
    private int moduleListGreen;
    private int moduleListBlue;

    private ModuleListMode moduleListMode;

    private boolean textRadarPots;
    private int textRadarPlayers;

    public boolean isTextRadarPots() {
        return textRadarPots;
    }

    public void setTextRadarPots(boolean textRadarPots) {
        this.textRadarPots = textRadarPots;
    }

    public GuiManager() {

        guiRed = 0.55f;
        guiGreen = 0.70f;
        guiBlue = 0.25f;

        textColor = ChatFormatting.GRAY.toString();

        moduleListMode = ModuleListMode.RAINBOW;

        moduleListRed = 255;
        moduleListGreen = 255;
        moduleListBlue = 255;

        textRadarPots = true;
        textRadarPlayers = 8;

    }

    public int getTextRadarPlayers() {
        return textRadarPlayers;
    }

    public void setTextRadarPlayers(int textRadarPlayers) {
        this.textRadarPlayers = textRadarPlayers;
    }

    public ModuleListMode getModuleListMode() {
        return moduleListMode;
    }

    public void setModuleListMode(ModuleListMode moduleListMode) {
        this.moduleListMode = moduleListMode;
    }

    public void setModuleListColors(int r, int g, int b) {
        this.moduleListRed = r;
        this.moduleListGreen = g;
        this.moduleListBlue = b;
    }

    public void setGuiColors(float r, float g, float b) {
        this.guiRed = r;
        this.guiGreen = g;
        this.guiBlue = b;
    }

    public String getTextColor() {
        return textColor;
    }

    public void setTextColor(String textColor) {
        this.textColor = textColor;
    }

    public float getGuiRed() {
        return guiRed;
    }

    public float getGuiGreen() {
        return guiGreen;
    }

    public float getGuiBlue() {
        return guiBlue;
    }

    public int getModuleListRed() {
        return moduleListRed;
    }

    public int getModuleListGreen() {
        return moduleListGreen;
    }

    public int getModuleListBlue() {
        return moduleListBlue;
    }

    public enum ModuleListMode {
        STATIC, RAINBOW
    }

}
