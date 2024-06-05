/*
 * Decompiled with CFR 0.150.
 */
package digital.rbq.command.impl;

import java.util.List;
import digital.rbq.command.AbstractCommand;
import digital.rbq.config.ConfigManager;
import digital.rbq.core.Autumn;
import digital.rbq.utils.Logger;

public final class ConfigCommand
extends AbstractCommand {
    public ConfigCommand() {
        super("Config", "Save, load and delete configs.", "config <save/load/delete/list/refresh> <name(optional)>", "config", "c");
    }

    @Override
    public void execute(String ... arguments) {
        if (arguments.length > 1) {
            String operator = arguments[1].toUpperCase();
            if (arguments.length == 3) {
                switch (operator) {
                    case "LOAD": {
                        if (!Autumn.MANAGER_REGISTRY.configManager.load(arguments[2])) break;
                        Logger.log("Loaded config named " + arguments[2]);
                        break;
                    }
                    case "SAVE": {
                        if (!Autumn.MANAGER_REGISTRY.configManager.save(arguments[2])) break;
                        Logger.log("Saved config as " + arguments[2]);
                        break;
                    }
                    case "DELETE": {
                        if (!Autumn.MANAGER_REGISTRY.configManager.delete(arguments[2])) break;
                        Logger.log("Deleted config named " + arguments[2]);
                    }
                }
            } else if (arguments.length == 2) {
                if (operator.equalsIgnoreCase("LIST")) {
                    Logger.log("---------------- Configs ----------------");
                    List<ConfigManager.Config> configs = Autumn.MANAGER_REGISTRY.configManager.getConfigs();
                    int configsSize = configs.size();
                    for (int i = 0; i < configsSize; ++i) {
                        ConfigManager.Config config = configs.get(i);
                        Logger.log(config.getName() + String.format("\u00a7F (%s)", i));
                    }
                } else if (operator.equalsIgnoreCase("REFRESH")) {
                    Autumn.MANAGER_REGISTRY.configManager.refresh();
                }
            } else {
                this.usage();
            }
        } else {
            this.usage();
        }
    }
}

