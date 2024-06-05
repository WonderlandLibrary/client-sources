package net.shoreline.client.impl.command;

import net.shoreline.client.api.command.Command;
import net.shoreline.client.api.command.arg.Argument;
import net.shoreline.client.api.command.arg.arguments.ModuleArgument;
import net.shoreline.client.api.module.Module;
import net.shoreline.client.api.module.ToggleModule;
import net.shoreline.client.util.chat.ChatUtil;

/**
 * @author linus
 * @since 1.0
 */
public class ToggleCommand extends Command {
    //
    Argument<Module> moduleArgument = new ModuleArgument("Module", "The module to enable/disable");

    /**
     *
     */
    public ToggleCommand() {
        super("Toggle", "Enables/Disables a module");
    }

    @Override
    public void onCommandInput() {
        Module module = moduleArgument.getValue();
        if (module instanceof ToggleModule t) {
            t.toggle();
            ChatUtil.clientSendMessage("%s is now %s!", t.getName(), t.isEnabled() ? "§aenabled" : "§cdisabled");
        }
    }
}
