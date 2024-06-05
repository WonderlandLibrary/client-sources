package me.superskidder.lune.commands.commands;

import me.superskidder.lune.Lune;
import me.superskidder.lune.modules.Mod;
import me.superskidder.lune.commands.Command;
import me.superskidder.lune.manager.ModuleManager;
import me.superskidder.lune.utils.client.CloudConfigsUtil;
import me.superskidder.lune.utils.player.PlayerUtil;

import java.io.IOException;

/**
 * @description: 云配置的！
 * @author: QianXia
 * @create: 2020/9/26 19-39
 **/
public class CommandConfig extends Command {
    public static final String COMMAND_LIST = "list";
    public static final String COMMAND_RELOAD = "reload";

    public CommandConfig() {
        super("Config");
    }

    @Override
    public void run(String[] args) {
        if (args.length < 1) {
            PlayerUtil.sendMessage(".config list");
            return;
        }
        if (COMMAND_LIST.equalsIgnoreCase(args[0])) {
            new PrintConfigs().start();
            return;
        }

        if (COMMAND_RELOAD.equalsIgnoreCase(args[0])) {
            for (Mod m : ModuleManager.modList) {
                if (m.getState()) {
                    m.setStage(false);
                }
            }
            Lune.moduleManager.init();
            PlayerUtil.sendMessage("Config Reloaded");
            return;
        }
        LoadConfig lc = new LoadConfig();
        lc.setConfigName(args[0]);
        lc.start();
    }

    static class PrintConfigs extends Thread {
        @Override
        public void run() {
            try {
                PlayerUtil.sendMessage("Loading...");
                String[] allConfigs = CloudConfigsUtil.getAllConfigsNameList();
                for (String s : allConfigs) {
                    PlayerUtil.sendMessage(s);
                }
            } catch (IOException e) {
                e.printStackTrace();
                PlayerUtil.sendMessage("Failed to get config list.");
            }

        }
    }

    static class LoadConfig extends Thread {
        public String configName;

        @Override
        public void run() {
            try {
                PlayerUtil.sendMessage("Loading...");
                boolean result = CloudConfigsUtil.loadCloudConfig(configName);
                if (result) {
                    PlayerUtil.sendMessage("Loaded Config Successfully!");
                } else {
                    PlayerUtil.sendMessage("Config \"" + getConfigName() + "\" Not Found!");
                }
            } catch (Exception e) {
                e.printStackTrace();
                PlayerUtil.sendMessage("Failed to load Config.");
            }
        }

        public String getConfigName() {
            return configName;
        }

        public void setConfigName(String configName) {
            this.configName = configName;
        }
    }
}
