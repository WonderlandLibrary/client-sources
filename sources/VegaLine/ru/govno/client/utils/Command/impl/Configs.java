/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package ru.govno.client.utils.Command.impl;

import net.minecraft.client.Minecraft;
import org.lwjgl.Sys;
import ru.govno.client.Client;
import ru.govno.client.cfg.ConfigManager;
import ru.govno.client.cfg.GuiConfig;
import ru.govno.client.utils.Command.Command;

public class Configs
extends Command {
    private static final Minecraft mc = Minecraft.getMinecraft();

    public Configs() {
        super("Configs", new String[]{"config", "cfg"});
    }

    @Override
    public void onCommand(String[] args) {
        try {
            if (args[1].equalsIgnoreCase("dir") || args[1].equalsIgnoreCase("folder")) {
                Sys.openURL(ConfigManager.configDirectory.getAbsolutePath());
                Client.msg("\u00a7a\u00a7lConfigs:\u00a7r \u00a77\u041f\u0430\u043f\u043a\u0430 \u0441 \u043a\u043e\u043d\u0444\u0438\u0433\u0430\u043c\u0438 \u043e\u0442\u043a\u0440\u044b\u0442\u0430.", false);
            }
            if (args[1].equalsIgnoreCase("load")) {
                if (Client.configManager.loadConfig(args[2])) {
                    GuiConfig.loadedConfig = args[2];
                    Client.msg("\u00a7a\u00a7lConfigs:\u00a7r \u00a77\u041a\u043e\u043d\u0444\u0438\u0433 [\u00a7l" + args[2] + "\u00a7r\u00a77] \u00a77\u0437\u0430\u0433\u0440\u0443\u0436\u0435\u043d.", false);
                    if (!args[2].equalsIgnoreCase("Default") && Client.configManager.saveConfig("Default")) {
                        Configs.mc.entityRenderer.runCfgSaveAnim();
                    }
                } else {
                    Client.msg("\u00a7a\u00a7lConfigs:\u00a7r \u00a77\u041a\u043e\u043d\u0444\u0438\u0433 [\u00a7l" + args[2] + "\u00a7r\u00a77] \u00a77\u043d\u0435\u0431\u044b\u043b \u0437\u0430\u0433\u0440\u0443\u0436\u0435\u043d.", false);
                }
            }
            if (args[1].equalsIgnoreCase("save")) {
                if (Client.configManager.saveConfig(args[2])) {
                    Client.msg("\u00a7a\u00a7lConfigs:\u00a7r \u00a77\u041a\u043e\u043d\u0444\u0438\u0433 [\u00a7l" + args[2] + "\u00a7r\u00a77] \u00a77\u0441\u043e\u0445\u0440\u0430\u043d\u0451\u043d.", false);
                    ConfigManager.getLoadedConfigs().clear();
                    Client.configManager.load();
                } else {
                    Client.msg("\u00a7a\u00a7lConfigs:\u00a7r \u00a77\u041a\u043e\u043d\u0444\u0438\u0433 [\u00a7l" + args[2] + "\u00a7r\u00a77] \u00a77\u043d\u0435\u0431\u044b\u043b \u0441\u043e\u0445\u0440\u0430\u043d\u0451\u043d.", false);
                }
            }
            if (args[1].equalsIgnoreCase("delete") || args[1].equalsIgnoreCase("del") || args[1].equalsIgnoreCase("remove")) {
                if (Client.configManager.deleteConfig(args[2])) {
                    Client.msg("\u00a7a\u00a7lConfigs:\u00a7r \u00a77\u041a\u043e\u043d\u0444\u0438\u0433 [\u00a7l" + args[2] + "\u00a7r\u00a77] \u00a77\u0443\u0434\u0430\u043b\u0451\u043d.", false);
                } else {
                    Client.msg("\u00a7a\u00a7lConfigs:\u00a7r \u00a77\u041a\u043e\u043d\u0444\u0438\u0433 [\u00a7l" + args[2] + "\u00a7r\u00a77] \u00a77\u043d\u0435\u0431\u044b\u043b \u0443\u0434\u0430\u043b\u0451\u043d.", false);
                }
            }
            if (args[1].equalsIgnoreCase("create") || args[1].equalsIgnoreCase("add") || args[1].equalsIgnoreCase("new")) {
                Client.configManager.saveConfig(args[2]);
                Client.msg("\u00a7a\u00a7lConfigs:\u00a7r \u00a77\u041a\u043e\u043d\u0444\u0438\u0433 [\u00a7l" + args[2] + "\u00a7r\u00a77] \u00a77\u0441\u043e\u0437\u0434\u0430\u043d \u0438 \u0441\u043e\u0445\u0440\u0430\u043d\u0451\u043d.", false);
                ConfigManager.getLoadedConfigs().clear();
                Client.configManager.load();
                GuiConfig.loadedConfig = args[2];
            }
            if (args[1].equalsIgnoreCase("list") || args[1].equalsIgnoreCase("see")) {
                for (int i = 0; i < ConfigManager.getLoadedConfigs().size(); ++i) {
                    Client.msg("\u00a7a\u00a7lConfigs:\u00a7r \u00a77\u041a\u043e\u043d\u0444\u0438\u0433 \u2116" + (i + 1) + " [\u00a7l" + ConfigManager.getLoadedConfigs().get(i).getName() + "\u00a7r\u00a77].", false);
                }
            }
            if (args[1].equalsIgnoreCase("reset") || args[1].equalsIgnoreCase("unload")) {
                if (Client.configManager.loadConfig("nulled")) {
                    GuiConfig.loadedConfig = "nulled";
                    Client.msg("\u00a7a\u00a7lConfigs:\u00a7r \u00a77\u041a\u043e\u043d\u0444\u0438\u0433 \u043e\u0431\u043d\u0443\u043b\u0451\u043d.", false);
                } else {
                    Client.msg("\u00a7a\u00a7lConfigs:\u00a7r \u00a77\u041a\u043e\u043d\u0444\u0438\u0433 \u043d\u0435\u0431\u044b\u043b \u043e\u0431\u043d\u0443\u043b\u0451\u043d.", false);
                }
            }
        } catch (Exception formatException) {
            Client.msg("\u00a7a\u00a7lConfigs:\u00a7r \u00a77\u041a\u043e\u043c\u043c\u0430\u043d\u0434\u0430 \u043d\u0430\u043f\u0438\u0441\u0430\u043d\u0430 \u043d\u0435\u0432\u0435\u0440\u043d\u043e.", false);
            Client.msg("\u00a7a\u00a7lConfigs:\u00a7r \u00a77\u0441\u043e\u0445\u0440\u0430\u043d\u0438\u0442\u044c: save [\u00a7lNAME\u00a7r\u00a77]", false);
            Client.msg("\u00a7a\u00a7lConfigs:\u00a7r \u00a77\u0437\u0430\u0433\u0440\u0443\u0437\u0438\u0442\u044c: load [\u00a7lNAME\u00a7r\u00a77]", false);
            Client.msg("\u00a7a\u00a7lConfigs:\u00a7r \u00a77\u0434\u043e\u0431\u0430\u0432\u0438\u0442\u044c: add/new [\u00a7lNAME\u00a7r\u00a77]", false);
            Client.msg("\u00a7a\u00a7lConfigs:\u00a7r \u00a77\u0443\u0434\u0430\u043b\u0438\u0442\u044c: remove/del [\u00a7lNAME\u00a7r\u00a77]", false);
            Client.msg("\u00a7a\u00a7lConfigs:\u00a7r \u00a77\u043f\u043e\u043a\u0430\u0437\u0430\u0442\u044c \u0432\u0441\u0435: list/see", false);
            Client.msg("\u00a7a\u00a7lConfigs:\u00a7r \u00a77\u043f\u043e\u043a\u0430\u0437\u0430\u0442\u044c \u043f\u0430\u043f\u043a\u0443: dir/folder", false);
            Client.msg("\u00a7a\u00a7lConfigs:\u00a7r \u00a77\u043e\u0431\u043d\u0443\u043b\u0438\u0442\u044c: reset/unload", false);
            formatException.printStackTrace();
        }
    }
}

