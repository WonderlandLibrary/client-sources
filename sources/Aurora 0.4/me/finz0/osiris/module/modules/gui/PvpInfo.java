package me.finz0.osiris.module.modules.gui;

import de.Hero.settings.Setting;
import me.finz0.osiris.module.Module;
import me.finz0.osiris.AuroraMod;

public class PvpInfo extends Module {
    public PvpInfo() {
        super("PvpInfo", Category.GUI);
        setDrawn(false);
    }

    public Setting offRainbow;
    public Setting offR;
    public Setting offG;
    public Setting offB;
    public Setting onRainbow;
    public Setting onR;
    public Setting onG;
    public Setting onB;
    public Setting customFont;

    public void setup() {
        AuroraMod.getInstance().settingsManager.rSetting(offRainbow = new Setting("OffRainbow", this, false, "PvpInfoOffRainbow"));
        AuroraMod.getInstance().settingsManager.rSetting(offR = new Setting("OffR", this, 255, 0, 255, true, "PvpInfoOffRed"));
        AuroraMod.getInstance().settingsManager.rSetting(offG = new Setting("OffG", this, 0, 0, 255, true, "PvpInfoOffGreen"));
        AuroraMod.getInstance().settingsManager.rSetting(offB = new Setting("OffB", this, 0, 0, 255, true, "PvpInfoOffBlue"));
        AuroraMod.getInstance().settingsManager.rSetting(onRainbow = new Setting("OnRainbow", this, false, "PvpInfoOnRainbow"));
        AuroraMod.getInstance().settingsManager.rSetting(onR = new Setting("OnR", this, 0, 0, 255, true, "PvpInfoOnRed"));
        AuroraMod.getInstance().settingsManager.rSetting(onG = new Setting("OnG", this, 255, 0, 255, true, "PvpInfoOnGreen"));
        AuroraMod.getInstance().settingsManager.rSetting(onB = new Setting("OnB", this, 0, 0, 255, true, "PvpInfoOnBlue"));
        AuroraMod.getInstance().settingsManager.rSetting(customFont = new Setting("CFont", this, false, "PvpInfoCustomFont"));
    }

    public void onEnable() {
        disable();
    }

    public int getPing() {
        int p = -1;
        if (mc.player == null || mc.getConnection() == null || mc.getConnection().getPlayerInfo(mc.player.getName()) == null) {
            p = -1;
        } else {
            p = mc.getConnection().getPlayerInfo(mc.player.getName()).getResponseTime();
        }
        return p;
    }
}