package me.dinozoid.strife.command.implementations;

import me.dinozoid.strife.StrifeClient;
import me.dinozoid.strife.command.Command;
import me.dinozoid.strife.command.CommandInfo;
import me.dinozoid.strife.command.argument.Argument;
import me.dinozoid.strife.command.argument.implementations.MultiChoiceArgument;
import me.dinozoid.strife.config.Config;
import me.dinozoid.strife.config.ConfigRepository;
import me.dinozoid.strife.util.player.PlayerUtil;
import me.dinozoid.strife.util.system.FolderUtil;

import java.io.File;
import java.util.Arrays;
import java.util.List;

@CommandInfo(name = "Config", aliases = {"configs", "cfg", "c"}, description = "Handle configs.")
public final class ConfigCommand extends Command {
    @Override
    public boolean execute(String[] args, String label) {
        if (label.equalsIgnoreCase("configs")) args[0] = "list";
        switch (args[0].toLowerCase()) {
            case "create":
                if (StrifeClient.INSTANCE.configRepository().add(new Config(args[1])))
                    PlayerUtil.sendMessageWithPrefix("&c" + args[1] + " &7has been created.");
                else PlayerUtil.sendMessageWithPrefix("&c" + args[1] + " &7 could not be created.");
                break;
            case "delete":
            case "remove":
                Config config = StrifeClient.INSTANCE.configRepository().configBy(args[1]);
                StrifeClient.INSTANCE.configRepository().remove(config);
                break;
            case "load":
                StrifeClient.INSTANCE.configRepository().init();
                config = StrifeClient.INSTANCE.configRepository().configBy(args[1]);
                if (config != null) {
                    if (StrifeClient.INSTANCE.configRepository().load(config))
                        PlayerUtil.sendMessageWithPrefix("&c" + config.name() + " &7has been loaded.");
                    else PlayerUtil.sendMessageWithPrefix("&c" + config.name() + " &7failed to load.");
                }
                break;
            case "save":
                config = StrifeClient.INSTANCE.configRepository().configBy(args[1]);
                if (config != null) {
                    if (StrifeClient.INSTANCE.configRepository().save(config))
                        PlayerUtil.sendMessageWithPrefix("&c" + config.name() + " &7has been saved.");
                    else PlayerUtil.sendMessageWithPrefix("&c" + config.name() + " &7failed to save.");
                }
                break;
            case "list":
                PlayerUtil.sendMessage(" ");
                StringBuilder stringBuilder = new StringBuilder();
                List<Config> configs = StrifeClient.INSTANCE.configRepository().configs();
                if (!configs.isEmpty()) {
                    PlayerUtil.sendMessage("&7All available configs.");
                    for (Config conf : configs)
                        stringBuilder.append("&c" + conf.name()).append(StrifeClient.INSTANCE.configRepository().currentConfig() == conf ? " (Loaded)" : "").append("&7, ");
                    PlayerUtil.sendMessage(stringBuilder.substring(0, stringBuilder.length() - 2));
                } else PlayerUtil.sendMessage("&7No available configs.");
                PlayerUtil.sendMessage(" ");
                break;
            case "folder":
                FolderUtil.openFolder(ConfigRepository.CONFIG_DIRECTORY.toFile().getParentFile());
                break;
        }
        return true;
    }

    @Override
    public List<Argument> arguments(String[] args) {
        return Arrays.asList(new MultiChoiceArgument(String.class, "Operation", "Create", "Remove", "Delete", "Save", "Load", "List", "Folder"), new Argument(String.class, "Name", () -> !(args[0].equalsIgnoreCase("list") || args[0].equalsIgnoreCase("folder"))));
    }
}
