package de.tired.command.impl;

import de.tired.api.annotations.CommandAnnotation;
import de.tired.command.Command;
import de.tired.config.Config;
import de.tired.api.logger.impl.IngameChatLog;
import de.tired.tired.Tired;

@CommandAnnotation(name = "Config", help = "", alias = {"c", "config", "cfg"})
public class ConfigCommand extends Command {

    @Override
    public void doCommand(String[] args) {
        if (args.length < 1) {

        } else {
            switch (args[0].toLowerCase()) {
                case "save":
                    IngameChatLog.INGAME_CHAT_LOG.doLog("Saved config " + args[1]);
                    if (Tired.INSTANCE.configManager.configBy(args[1]) == null) {
                        Tired.INSTANCE.configManager.create(new Config(args[1]));
                    } else {
                        Tired.INSTANCE.configManager.save(args[1]);
                    }
                    break;
                case "load":
                    if (Tired.INSTANCE.configManager.configBy(args[1]) != null) {
                        if (Tired.INSTANCE.configManager.load(args[1])) {
                            IngameChatLog.INGAME_CHAT_LOG.doLog("Loaded config " + args[1]);
                        }
                        break;
                    }
                    IngameChatLog.INGAME_CHAT_LOG.doLog("Couldn't find config " + args[1]);
                    break;
                case "list":
                    if (Tired.INSTANCE.configManager.configs().size() > 0) {
                        IngameChatLog.INGAME_CHAT_LOG.doLog("Configs:");
                        Tired.INSTANCE.configManager.configs().forEach(config -> IngameChatLog.INGAME_CHAT_LOG.doLog(config.name()));
                        break;
                    }
                    IngameChatLog.INGAME_CHAT_LOG.doLog("You dont have any configs!");
                    break;
            }
        }
        super.doCommand(args);
    }
}
