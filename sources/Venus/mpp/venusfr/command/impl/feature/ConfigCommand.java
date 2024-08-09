/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.command.impl.feature;

import java.io.File;
import java.io.IOException;
import java.util.List;
import mpp.venusfr.command.Command;
import mpp.venusfr.command.CommandWithAdvice;
import mpp.venusfr.command.Logger;
import mpp.venusfr.command.MultiNamedCommand;
import mpp.venusfr.command.Parameters;
import mpp.venusfr.command.Prefix;
import mpp.venusfr.command.impl.CommandException;
import mpp.venusfr.config.Config;
import mpp.venusfr.config.ConfigStorage;
import net.minecraft.util.text.TextFormatting;

public class ConfigCommand
implements Command,
CommandWithAdvice,
MultiNamedCommand {
    private final ConfigStorage configStorage;
    private final Prefix prefix;
    private final Logger logger;

    @Override
    public void execute(Parameters parameters) {
        String string;
        switch (string = parameters.asString(0).orElse("")) {
            case "load": {
                this.loadConfig(parameters);
                break;
            }
            case "save": {
                this.saveConfig(parameters);
                break;
            }
            case "list": {
                this.configList();
                break;
            }
            case "dir": {
                this.getDirectory();
                break;
            }
            default: {
                throw new CommandException(TextFormatting.RED + "\u0423\u043a\u0430\u0436\u0438\u0442\u0435 \u0442\u0438\u043f \u043a\u043e\u043c\u0430\u043d\u0434\u044b:" + TextFormatting.GRAY + " load, save, list, dir");
            }
        }
    }

    @Override
    public String name() {
        return "config";
    }

    @Override
    public String description() {
        return "\u041f\u043e\u0437\u0432\u043e\u043b\u044f\u0435\u0442 \u0432\u0437\u0430\u0438\u043c\u043e\u0434\u0435\u0439\u0441\u0442\u0432\u043e\u0432\u0430\u0442\u044c \u0441 \u043a\u043e\u043d\u0444\u0438\u0433\u0430\u043c\u0438 \u0432 \u0447\u0438\u0442\u0435";
    }

    @Override
    public List<String> adviceMessage() {
        String string = this.prefix.get();
        return List.of((Object)(string + this.name() + " load <config> - \u0417\u0430\u0433\u0440\u0443\u0437\u0438\u0442\u044c \u043a\u043e\u043d\u0444\u0438\u0433"), (Object)(string + this.name() + " save <config> - \u0421\u043e\u0445\u0440\u0430\u043d\u0438\u0442\u044c \u043a\u043e\u043d\u0444\u0438\u0433"), (Object)(string + this.name() + " list - \u041f\u043e\u043b\u0443\u0447\u0438\u0442\u044c \u0441\u043f\u0438\u0441\u043e\u043a \u043a\u043e\u043d\u0444\u0438\u0433\u043e\u0432"), (Object)(string + this.name() + " dir - \u041e\u0442\u043a\u0440\u044b\u0442\u044c \u043f\u0430\u043f\u043a\u0443 \u0441 \u043a\u043e\u043d\u0444\u0438\u0433\u0430\u043c\u0438"), (Object)("\u041f\u0440\u0438\u043c\u0435\u0440: " + TextFormatting.RED + string + "cfg save myConfig"), (Object)("\u041f\u0440\u0438\u043c\u0435\u0440: " + TextFormatting.RED + string + "cfg load myConfig"));
    }

    @Override
    public List<String> aliases() {
        return List.of((Object)"cfg");
    }

    private void loadConfig(Parameters parameters) {
        String string = parameters.asString(1).orElseThrow(ConfigCommand::lambda$loadConfig$0);
        if (new File(this.configStorage.CONFIG_DIR, string + ".cfg").exists()) {
            this.configStorage.loadConfiguration(string);
            this.logger.log(TextFormatting.GREEN + "\u041a\u043e\u043d\u0444\u0438\u0433\u0443\u0440\u0430\u0446\u0438\u044f " + TextFormatting.RED + string + TextFormatting.GREEN + " \u0437\u0430\u0433\u0440\u0443\u0436\u0435\u043d\u0430!");
        } else {
            this.logger.log(TextFormatting.RED + "\u041a\u043e\u043d\u0444\u0438\u0433\u0443\u0440\u0430\u0446\u0438\u044f " + TextFormatting.GRAY + string + TextFormatting.RED + " \u043d\u0435 \u043d\u0430\u0439\u0434\u0435\u043d\u0430!");
        }
    }

    private void saveConfig(Parameters parameters) {
        String string = parameters.asString(1).orElseThrow(ConfigCommand::lambda$saveConfig$1);
        this.configStorage.saveConfiguration(string);
        this.logger.log(TextFormatting.GREEN + "\u041a\u043e\u043d\u0444\u0438\u0433\u0443\u0440\u0430\u0446\u0438\u044f " + TextFormatting.RED + string + TextFormatting.GREEN + " \u0441\u043e\u0445\u0440\u0430\u043d\u0435\u043d\u0430!");
    }

    private void configList() {
        if (this.configStorage.isEmpty()) {
            this.logger.log(TextFormatting.RED + "\u0421\u043f\u0438\u0441\u043e\u043a \u043a\u043e\u043d\u0444\u0438\u0433\u0443\u0440\u0430\u0446\u0438\u0439 \u043f\u0443\u0441\u0442\u043e\u0439");
            return;
        }
        this.logger.log(TextFormatting.GRAY + "\u0421\u043f\u0438\u0441\u043e\u043a \u043a\u043e\u043d\u0444\u0438\u0433\u043e\u0432:");
        for (Config config : this.configStorage.getConfigs()) {
            this.logger.log(TextFormatting.GRAY + config.getName());
        }
    }

    private void getDirectory() {
        try {
            Runtime.getRuntime().exec("explorer " + this.configStorage.CONFIG_DIR.getAbsolutePath());
        } catch (IOException iOException) {
            this.logger.log(TextFormatting.RED + "\u041f\u0430\u043f\u043a\u0430 \u0441 \u043a\u043e\u043d\u0444\u0438\u0433\u0443\u0440\u0430\u0446\u0438\u044f\u043c\u0438 \u043d\u0435 \u043d\u0430\u0439\u0434\u0435\u043d\u0430!" + iOException.getMessage());
        }
    }

    public ConfigCommand(ConfigStorage configStorage, Prefix prefix, Logger logger) {
        this.configStorage = configStorage;
        this.prefix = prefix;
        this.logger = logger;
    }

    private static CommandException lambda$saveConfig$1() {
        return new CommandException(TextFormatting.RED + "\u0423\u043a\u0430\u0436\u0438\u0442\u0435 \u043d\u0430\u0437\u0432\u0430\u043d\u0438\u0435 \u043a\u043e\u043d\u0444\u0438\u0433\u0430!");
    }

    private static CommandException lambda$loadConfig$0() {
        return new CommandException(TextFormatting.RED + "\u0423\u043a\u0430\u0436\u0438\u0442\u0435 \u043d\u0430\u0437\u0432\u0430\u043d\u0438\u0435 \u043a\u043e\u043d\u0444\u0438\u0433\u0430!");
    }
}

