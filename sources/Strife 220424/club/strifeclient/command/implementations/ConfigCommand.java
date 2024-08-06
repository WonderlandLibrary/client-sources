package club.strifeclient.command.implementations;

import club.strifeclient.Client;
import club.strifeclient.command.Command;
import club.strifeclient.command.CommandInfo;
import club.strifeclient.config.Config;
import club.strifeclient.config.ConfigManager;
import club.strifeclient.util.player.ChatUtil;
import club.strifeclient.util.system.FileUtil;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.Set;

@CommandInfo(name = "Config", description = "Manage configs.", aliases = {"cfg", "cfgs", "configs", "c"})
public class ConfigCommand extends Command {
    @Override
    public void execute(String[] args, String name) {
        if (name.equalsIgnoreCase(getAliases()[1]) || name.equalsIgnoreCase(getAliases()[2])) {
            args = new String[1];
            args[0] = "list";
        }
        ConfigManager configManager = Client.INSTANCE.getConfigManager();
        try {
            configManager.refreshFileConfigs();
        } catch (IOException e) {
            e.printStackTrace();
            ChatUtil.sendMessageWithPrefix("Configs could not be refreshed!");
            return;
        }
        if (args.length > 0) {
            switch (args[0].toLowerCase()) {
                case "load": {
                    if (args.length == 2) {
                        Config config = configManager.getFileConfig(args[1]);
                        if (config != null) {
                            if (config.load())
                                ChatUtil.sendMessageWithPrefix("&c" + config.getName() + "&7 has been loaded.");
                            else ChatUtil.sendMessageWithPrefix("&c" + config.getName() + "&7 failed to load.");
                        } else ChatUtil.sendMessageWithPrefix("Config not found.");
                    } else ChatUtil.sendMessageWithPrefix(".config load <name>");
                    break;
                }
                case "save": {
                    if (args.length == 2) {
                        Config config = configManager.getFileConfig(args[1]);
                        if(config == null) {
                            config = new Config(args[1], "dingo");
                            config.create();
                        }
                        if (config.save())
                            ChatUtil.sendMessageWithPrefix("&c" + config.getName() + "&7 has been saved.");
                        else ChatUtil.sendMessageWithPrefix("&c" + config.getName() + "&7 failed to save.");
                    } else ChatUtil.sendMessageWithPrefix(".config save <name>");
                    break;
                }
                case "delete":
                case "remove": {
                    if (args.length == 2) {
                        Config config = configManager.getFileConfig(args[1]);
                        if (config != null) {
                            if (config.delete())
                                ChatUtil.sendMessageWithPrefix("&c" + config.getName() + "&7 has been deleted.");
                            else ChatUtil.sendMessageWithPrefix("&c" + config.getName() + "&7 failed to delete.");
                        } else ChatUtil.sendMessageWithPrefix("Config not found.");
                    } else ChatUtil.sendMessageWithPrefix(".config remove <name>");
                    break;
                }
                case "folder": {
                    if (!FileUtil.openFolder(ConfigManager.CONFIG_DIRECTORY.toFile()))
                        ChatUtil.sendMessageWithPrefix("The config folder failed to open.");
                    break;
                }
                case "list": {
                    Set<Config> configs = configManager.getFileConfigs().keySet();
                    if (!configs.isEmpty()) {
                        StringBuilder stringBuilder = new StringBuilder();
                        ChatUtil.sendMessage("&7All available configs.");
                        configs.forEach(config -> stringBuilder.append("&c").append(config.getName())
                                .append(configManager.getLoadedConfig() != null && configManager.getLoadedConfig() == config ? " (Loaded)" : "").append(", "));
                        ChatUtil.sendMessage(StringUtils.substring(stringBuilder.toString(), 0, -2));
                    }
                    break;
                }
                default: {
                    ChatUtil.sendMessageWithPrefix(".config <save:load:delete:folder:list>");
                    break;
                }
            }
        } else ChatUtil.sendMessageWithPrefix(".config <save:load:delete:folder:list>");
    }
}
