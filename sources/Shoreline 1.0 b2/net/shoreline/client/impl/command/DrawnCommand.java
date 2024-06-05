package net.shoreline.client.impl.command;

import net.shoreline.client.api.command.Command;
import net.shoreline.client.api.command.arg.Argument;
import net.shoreline.client.api.command.arg.arguments.ModuleArgument;
import net.shoreline.client.api.module.Module;
import net.shoreline.client.api.module.ToggleModule;
import net.shoreline.client.api.render.Hideable;
import net.shoreline.client.util.chat.ChatUtil;
import net.minecraft.util.Formatting;

/**
 *
 *
 * @author linus
 * @since 1.0
 *
 * @see Hideable
 */
public class DrawnCommand extends Command
{
    //
    Argument<Module> moduleArgument = new ModuleArgument("Module", "The " +
            "module to toggle the drawn state");

    /**
     *
     */
    public DrawnCommand()
    {
        super("Drawn", "Toggles the drawn state of the module");
    }

    /**
     * Runs when the command is inputted in chat
     */
    @Override
    public void onCommandInput()
    {
        Module module = moduleArgument.getValue();
        if (module instanceof ToggleModule toggle)
        {
            boolean hide = !toggle.isHidden();
            toggle.setHidden(hide);
            ChatUtil.clientSendMessage(module.getName() + " is " + Formatting.RED +
                    (hide ? "hidden" : "visible") + Formatting.RESET + " in the Hud!");
        }
    }
}
