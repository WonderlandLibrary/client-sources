/*
 * Decompiled with CFR 0.145.
 */
package us.amerikan.command.commands;

import de.Hero.settings.Setting;
import de.Hero.settings.SettingsManager;
import us.amerikan.amerikan;
import us.amerikan.command.Command;

public class AutoSettings
extends Command {
    public static String currentconfig = "Null";

    public AutoSettings() {
        super("autosettings", "");
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    @Override
    public void execute(String[] args) {
        if (args.length != 2) {
            AutoSettings.messageWithPrefix(" \u00a77autosettings \u00a78<\u00a72load\u00a78> \u00a78<\u00a72Cubecraft, SemiAAC, Jartex>\u00a78>\u00a7f");
            return;
        }
        var2_2 = args[0];
        switch (var2_2.hashCode()) {
            case 3327206: {
                if (!var2_2.equals("load")) {
                    return;
                }
                var3_3 = args[1].toLowerCase();
                switch (var3_3.hashCode()) {
                    case -1167184852: {
                        if (!var3_3.equals("jartex")) {
                            return;
                        }
                        ** GOTO lbl28
                    }
                    case -1031473397: {
                        if (var3_3.equals("cubecraft")) break;
                        return;
                    }
                    case 1979140629: {
                        if (!var3_3.equals("semiaac")) {
                            return;
                        }
                        ** GOTO lbl25
                    }
                }
                this.cubecraft();
                AutoSettings.messageWithPrefix(" \u00a72loaded");
                return;
lbl25: // 1 sources:
                this.semiaac();
                AutoSettings.messageWithPrefix(" \u00a72loaded");
                return;
lbl28: // 1 sources:
                this.jartex();
                AutoSettings.messageWithPrefix(" \u00a72loaded");
            }
        }
    }

    void cubecraft() {
        currentconfig = "Cubecraft";
        amerikan.setmgr.getSettingByName("Range").setValDouble(4.4);
        amerikan.setmgr.getSettingByName("MaxCPS").setValDouble(18.0);
        amerikan.setmgr.getSettingByName("MinCPS").setValDouble(12.0);
        amerikan.setmgr.getSettingByName("Autoblock").setValBoolean(true);
        amerikan.setmgr.getSettingByName("Hitfail").setValBoolean(false);
        amerikan.setmgr.getSettingByName("AntiBot").setValBoolean(false);
        amerikan.setmgr.getSettingByName("VisibleOnly").setValBoolean(false);
        amerikan.setmgr.getSettingByName("Teams").setValBoolean(false);
        amerikan.setmgr.getSettingByName("EvadeBlocks").setValBoolean(false);
        amerikan.setmgr.getSettingByName("InvAttack").setValBoolean(true);
        amerikan.setmgr.getSettingByName("RayCast").setValBoolean(false);
        amerikan.setmgr.getSettingByName("Fly Mode").setValString("Cubecraft");
        amerikan.setmgr.getSettingByName("Highjump Mode").setValString("Cubecraft");
        amerikan.setmgr.getSettingByName("Speed Mode").setValString("Cubecraft");
        amerikan.setmgr.getSettingByName("ChestStealer Delay").setValDouble(20.0);
        amerikan.setmgr.getSettingByName("Instant").setValBoolean(false);
        amerikan.setmgr.getSettingByName("AutoClose").setValBoolean(true);
        amerikan.setmgr.getSettingByName("ChestStealer StartDelay").setValBoolean(false);
        amerikan.setmgr.getSettingByName("Help Delay").setValDouble(40.0);
        amerikan.setmgr.getSettingByName("DropWholeStack").setValBoolean(true);
        amerikan.setmgr.getSettingByName("Instant ").setValBoolean(false);
        amerikan.setmgr.getSettingByName("Inv Only").setValBoolean(true);
        amerikan.setmgr.getSettingByName("Dublicate Only").setValBoolean(false);
        amerikan.setmgr.getSettingByName("Ignore Blocks").setValBoolean(true);
        amerikan.setmgr.getSettingByName("Nofall Mode").setValString("Cubecraft");
        amerikan.setmgr.getSettingByName("Scaffold Delay").setValDouble(20.0);
        amerikan.setmgr.getSettingByName("Slow Mode").setValBoolean(false);
        amerikan.setmgr.getSettingByName("Sprint").setValBoolean(false);
        amerikan.setmgr.getSettingByName("Silent").setValBoolean(true);
        amerikan.setmgr.getSettingByName("SafeWalk").setValBoolean(true);
        amerikan.setmgr.getSettingByName("Fastbridge").setValBoolean(false);
        amerikan.setmgr.getSettingByName("Boost ").setValBoolean(false);
        amerikan.setmgr.getSettingByName("CubeCraft").setValBoolean(true);
        amerikan.setmgr.getSettingByName("TowerMode").setValString("CC");
        amerikan.setmgr.getSettingByName("Velocity Mode").setValString("Packet");
        amerikan.setmgr.getSettingByName("Bypass-Mode").setValString("Cubecraft");
    }

    void semiaac() {
        currentconfig = "AAC";
        amerikan.setmgr.getSettingByName("Range").setValDouble(3.9);
        amerikan.setmgr.getSettingByName("MaxCPS").setValDouble(18.0);
        amerikan.setmgr.getSettingByName("MinCPS").setValDouble(7.0);
        amerikan.setmgr.getSettingByName("Autoblock").setValBoolean(true);
        amerikan.setmgr.getSettingByName("Hitfail").setValBoolean(true);
        amerikan.setmgr.getSettingByName("AntiBot").setValBoolean(false);
        amerikan.setmgr.getSettingByName("VisibleOnly").setValBoolean(true);
        amerikan.setmgr.getSettingByName("Teams").setValBoolean(false);
        amerikan.setmgr.getSettingByName("EvadeBlocks").setValBoolean(true);
        amerikan.setmgr.getSettingByName("Precision").setValDouble(13.0);
        amerikan.setmgr.getSettingByName("InvAttack").setValBoolean(false);
        amerikan.setmgr.getSettingByName("RayCast").setValBoolean(true);
        amerikan.setmgr.getSettingByName("Fly Mode").setValString("Vanilla");
        amerikan.setmgr.getSettingByName("Highjump Mode").setValString("Vanilla");
        amerikan.setmgr.getSettingByName("Speed Mode").setValString("AAC Strafe");
        amerikan.setmgr.getSettingByName("ChestStealer Delay").setValDouble(70.0);
        amerikan.setmgr.getSettingByName("Instant").setValBoolean(false);
        amerikan.setmgr.getSettingByName("AutoClose").setValBoolean(true);
        amerikan.setmgr.getSettingByName("ChestStealer StartDelay").setValBoolean(true);
        amerikan.setmgr.getSettingByName("Help Delay").setValDouble(80.0);
        amerikan.setmgr.getSettingByName("DropWholeStack").setValBoolean(true);
        amerikan.setmgr.getSettingByName("Instant ").setValBoolean(false);
        amerikan.setmgr.getSettingByName("Inv Only").setValBoolean(true);
        amerikan.setmgr.getSettingByName("Dublicate Only").setValBoolean(false);
        amerikan.setmgr.getSettingByName("Ignore Blocks").setValBoolean(true);
        amerikan.setmgr.getSettingByName("Nofall Mode").setValString("AAC");
        amerikan.setmgr.getSettingByName("Scaffold Delay").setValDouble(40.0);
        amerikan.setmgr.getSettingByName("Slow Mode").setValBoolean(false);
        amerikan.setmgr.getSettingByName("Sprint").setValBoolean(false);
        amerikan.setmgr.getSettingByName("Silent").setValBoolean(true);
        amerikan.setmgr.getSettingByName("SafeWalk").setValBoolean(true);
        amerikan.setmgr.getSettingByName("Fastbridge").setValBoolean(false);
        amerikan.setmgr.getSettingByName("Boost ").setValBoolean(false);
        amerikan.setmgr.getSettingByName("CubeCraft").setValBoolean(false);
        amerikan.setmgr.getSettingByName("TowerMode").setValString("Legit");
        amerikan.setmgr.getSettingByName("Velocity Mode").setValString("AAC Push");
        amerikan.setmgr.getSettingByName("Bypass-Mode").setValString("AAC Bypass");
    }

    void jartex() {
        currentconfig = "Jartex";
        amerikan.setmgr.getSettingByName("Range").setValDouble(3.9);
        amerikan.setmgr.getSettingByName("MaxCPS").setValDouble(20.0);
        amerikan.setmgr.getSettingByName("MinCPS").setValDouble(16.0);
        amerikan.setmgr.getSettingByName("Autoblock").setValBoolean(true);
        amerikan.setmgr.getSettingByName("Hitfail").setValBoolean(false);
        amerikan.setmgr.getSettingByName("AntiBot").setValBoolean(false);
        amerikan.setmgr.getSettingByName("VisibleOnly").setValBoolean(true);
        amerikan.setmgr.getSettingByName("Teams").setValBoolean(false);
        amerikan.setmgr.getSettingByName("EvadeBlocks").setValBoolean(true);
        amerikan.setmgr.getSettingByName("Precision").setValDouble(13.0);
        amerikan.setmgr.getSettingByName("InvAttack").setValBoolean(false);
        amerikan.setmgr.getSettingByName("RayCast").setValBoolean(true);
        amerikan.setmgr.getSettingByName("Fly Mode").setValString("Vanilla");
        amerikan.setmgr.getSettingByName("Highjump Mode").setValString("Vanilla");
        amerikan.setmgr.getSettingByName("Speed Mode").setValString("Jartex");
        amerikan.setmgr.getSettingByName("ChestStealer Delay").setValDouble(70.0);
        amerikan.setmgr.getSettingByName("Instant").setValBoolean(false);
        amerikan.setmgr.getSettingByName("AutoClose").setValBoolean(true);
        amerikan.setmgr.getSettingByName("ChestStealer StartDelay").setValBoolean(true);
        amerikan.setmgr.getSettingByName("Help Delay").setValDouble(80.0);
        amerikan.setmgr.getSettingByName("DropWholeStack").setValBoolean(true);
        amerikan.setmgr.getSettingByName("Instant ").setValBoolean(false);
        amerikan.setmgr.getSettingByName("Inv Only").setValBoolean(true);
        amerikan.setmgr.getSettingByName("Dublicate Only").setValBoolean(false);
        amerikan.setmgr.getSettingByName("Ignore Blocks").setValBoolean(true);
        amerikan.setmgr.getSettingByName("Nofall Mode").setValString("AAC");
        amerikan.setmgr.getSettingByName("Scaffold Delay").setValDouble(30.0);
        amerikan.setmgr.getSettingByName("Slow Mode").setValBoolean(false);
        amerikan.setmgr.getSettingByName("Sprint").setValBoolean(false);
        amerikan.setmgr.getSettingByName("Silent").setValBoolean(true);
        amerikan.setmgr.getSettingByName("SafeWalk").setValBoolean(true);
        amerikan.setmgr.getSettingByName("Fastbridge").setValBoolean(false);
        amerikan.setmgr.getSettingByName("Boost ").setValBoolean(false);
        amerikan.setmgr.getSettingByName("CubeCraft").setValBoolean(false);
        amerikan.setmgr.getSettingByName("TowerMode").setValString("Legit");
        amerikan.setmgr.getSettingByName("Velocity Mode").setValString("AAC Reduce");
        amerikan.setmgr.getSettingByName("Bypass-Mode").setValString("AAC Bypass");
    }
}

