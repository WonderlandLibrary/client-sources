package client.metaware.api.command.implementations;

import client.metaware.Metaware;
import client.metaware.api.command.Command;
import client.metaware.api.command.CommandInfo;
import client.metaware.api.command.argument.Argument;
import client.metaware.api.command.argument.implementations.MultiChoiceArgument;
import client.metaware.api.config.Config;
import client.metaware.client.Logger;
import client.metaware.impl.managers.ConfigManager;
import client.metaware.impl.utils.system.FolderUtil;

import java.util.Arrays;
import java.util.List;

@CommandInfo(name = "Config", aliases = {"configs", "cfg", "c"}, description = "Handle configs.")
public final class ConfigCommand extends Command {
    @Override
    public boolean execute(String[] args, String label) {
        if (label.equalsIgnoreCase("configs")) args[0] = "list";
        switch (args[0].toLowerCase()) {
            case "create":
                if (Metaware.INSTANCE.getConfigManager().add(new Config(args[1])))
                    Logger.print("&9" + args[1] + " &7has been created.");
                else Logger.print("&9" + args[1] + " &7 could not be created.");
                break;
            case "delete":
            case "remove":
                Config config = Metaware.INSTANCE.getConfigManager().configBy(args[1]);
                Metaware.INSTANCE.getConfigManager().remove(config);
                break;
            case "load":
                Metaware.INSTANCE.getConfigManager().init();
                config = Metaware.INSTANCE.getConfigManager().configBy(args[1]);
                if (config != null) {
                    if (Metaware.INSTANCE.getConfigManager().load(config))
                        Logger.print("&9" + config.name() + " &7has been loaded.");
                    else Logger.print("&9" + config.name() + " &7failed to load.");
                }
                break;
            case "save":
                config = Metaware.INSTANCE.getConfigManager().configBy(args[1]);
                if (config != null) {
                    if (Metaware.INSTANCE.getConfigManager().save(config))
                        Logger.print("&9" + config.name() + " &7has been saved.");
                    else Logger.print("&9" + config.name() + " &7failed to save.");
                }
                break;
            case "list":
                Logger.printWithoutPrefix(" ");
                StringBuilder stringBuilder = new StringBuilder();
                List<Config> configs = Metaware.INSTANCE.getConfigManager().configs();
                if (!configs.isEmpty()) {
                    Logger.printWithoutPrefix("&7All available configs.");
                    for (Config conf : configs)
                        stringBuilder.append("&9" + conf.name()).append(Metaware.INSTANCE.getConfigManager().currentConfig() == conf ? " (Loaded)" : "").append("&7, ");
                    Logger.printWithoutPrefix(stringBuilder.substring(0, stringBuilder.length() - 2));
                } else Logger.printWithoutPrefix("&7No available configs.");
                Logger.printWithoutPrefix(" ");
                break;
            case "folder":
                FolderUtil.openFolder(ConfigManager.CONFIG_DIRECTORY.toFile().getParentFile());
                break;
        }
        return true;
    }

    @Override
    public List<Argument> arguments(String[] args) {
        return Arrays.asList(new MultiChoiceArgument(String.class, "Operation", "Create", "Remove", "Delete", "Save", "Load", "List", "Folder"), new Argument(String.class, "Name", () -> !(args[0].equalsIgnoreCase("list") || args[0].equalsIgnoreCase("folder"))));
    }
}
