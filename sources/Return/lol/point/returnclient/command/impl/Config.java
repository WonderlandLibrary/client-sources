package lol.point.returnclient.command.impl;

import lol.point.Return;
import lol.point.returnclient.command.Command;
import lol.point.returnclient.command.CommandInfo;
import lol.point.returnclient.configs.LoadConfig;
import lol.point.returnclient.configs.SaveConfig;
import lol.point.returnclient.util.client.OnlineConfig;
import lol.point.returnclient.util.minecraft.ChatUtil;
import lol.point.returnclient.util.system.FileUtil;
import lol.point.returnclient.util.system.NetworkUtil;
import net.minecraft.client.Minecraft;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@CommandInfo(
        name = "Config",
        usage = ".config <save/load/download/list> <name>",
        description = "save or load configs",
        aliases = {"config", "cfg"}
)
public class Config extends Command {

    private boolean foundOnlineCfg = false;

    @Override
    public void execute(String... args) {
        if (args.length < 1) {
            ChatUtil.addChatMessage("§9Usage: §c\"§b" + getUsage() + "§c\"");
            return;
        }

        String subCommand = args[0].toLowerCase();

        SaveConfig saveConfig = new SaveConfig();
        LoadConfig loadConfig = new LoadConfig();

        switch (subCommand) {
            case "list" -> {
                final Path runningDirectoryPath = Paths.get(Minecraft.getMinecraft().mcDataDir.getPath(), "return");
                ChatUtil.addChatMessage("Available offline configs: \n" + FileUtil.listFiles("return/configs", "json"));
                ChatUtil.addChatMessage("Available online configs:");
                for (OnlineConfig onlineConfig : Return.INSTANCE.configManager.getOnlineConfigsList()) {
                    final Path configPath = Paths.get(runningDirectoryPath + "/online-configs", onlineConfig.name + ".json");

                    ChatUtil.addChatMessage("§7 - §6" + onlineConfig.name + " §7- §a" + onlineConfig.date + (Files.exists(configPath) ? "§7 (downloaded)§r" : "§r") + (Return.INSTANCE.configManager.getOnlineConfigsList().getLast().name.equals(onlineConfig.name) ? "" : "§f,"), false);
                }
            }
            case "download" -> {
                foundOnlineCfg = false;
                if (args[1].contains(".json") || args[1].contains(".txt")) {
                    ChatUtil.addChatMessage("§cConfig names must not have file types in them!");
                    return;
                }

                final Path runningDirectoryPath = Paths.get(Minecraft.getMinecraft().mcDataDir.getPath(), "return");

                for (OnlineConfig onlineConfig : Return.INSTANCE.configManager.getOnlineConfigsList()) {
                    if (args[1].contains(onlineConfig.name)) {
                        foundOnlineCfg = true;
                        final Path configPath = Paths.get(runningDirectoryPath + "/online-configs", onlineConfig.name + ".json");
                        try {
                            if (!Files.exists(configPath)) {
                                NetworkUtil.download_nozip("https://raw.githubusercontent.com/Return-Client/cloud/main/configs/" + onlineConfig.name + ".json", runningDirectoryPath + "/online-configs");
                            }
                            loadConfig.loadFromFile(onlineConfig.name + ".json", "return/online-configs");
                            ChatUtil.addChatMessage(String.format("Loaded §6%s§r by §a%s§r successfully. §7(%s)", onlineConfig.name + ".json", onlineConfig.author, onlineConfig.date));
                        } catch (IOException e) {
                            ChatUtil.addChatMessage(String.format("An error occured while loading the config: %s", e.getMessage()));
                        }
                    } else {
                        foundOnlineCfg = false;
                    }
                }

                if (!foundOnlineCfg) {
                    ChatUtil.addChatMessage("Config §6" + args[1] + "§r was not found!");
                }
            }
            case "save" -> {
                if (args[1].contains(".json") || args[1].contains(".txt")) {
                    ChatUtil.addChatMessage("§cConfig names must not have file types in them!");
                    return;
                }

                try {
                    saveConfig.saveToFile(args[1] + ".json", "return/configs");
                    ChatUtil.addChatMessage(String.format("Saved §6%s§r successfully.", args[1]), true);
                } catch (IOException e) {
                    ChatUtil.addChatMessage(String.format("An error occured while saving the config: %s", e.getMessage()));
                }
            }
            case "load" -> {
                if (args[1].contains(".json") || args[1].contains(".txt")) {
                    ChatUtil.addChatMessage("§cConfig names must not have file types in them!");
                    return;
                }

                try {
                    loadConfig.loadFromFile(args[1] + ".json", "return/configs");
                    ChatUtil.addChatMessage(String.format("Loaded §6%s§r successfully.", args[1] + ".json"));
                } catch (IOException e) {
                    ChatUtil.addChatMessage(String.format("An error occured while loading the config: %s", e.getMessage()));
                }
            }
        }
    }
}