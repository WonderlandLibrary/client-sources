package net.shoreline.client.impl.command;

import net.shoreline.client.Shoreline;
import net.shoreline.client.api.command.Command;
import net.shoreline.client.api.command.arg.Argument;
import net.shoreline.client.api.command.arg.arguments.StringArgument;
import net.shoreline.client.util.chat.ChatUtil;

/**
 * @author linus
 * @since 1.0
 */
public class ConfigCommand extends Command {
    //
    Argument<String> actionArgument = new StringArgument("Action", "Whether to save or load a preset", "save", "load");
    Argument<String> nameArgument = new StringArgument("ConfigName", "The name for the config preset");

    /**
     *
     */
    public ConfigCommand() {
        super("Config", "Creates a new configuration preset");
    }

    @Override
    public void onCommandInput() {
        String action = actionArgument.getValue();
        String name = nameArgument.getValue();
        if (action == null || name == null) {
            return;
        }
        if (action.equalsIgnoreCase("save")) {
            Shoreline.CONFIG.saveModuleConfiguration(name);
            ChatUtil.clientSendMessage("Saved config with name §7" + name);
        } else if (action.equalsIgnoreCase("load")) {
            Shoreline.CONFIG.loadModuleConfiguration(name);
            ChatUtil.clientSendMessage("Loaded config with name §7" + name);
        }
    }
}
