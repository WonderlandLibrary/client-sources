package net.shoreline.client.impl.command;

import net.shoreline.client.api.command.Command;
import net.shoreline.client.api.command.arg.Argument;
import net.shoreline.client.api.command.arg.arguments.StringArgument;
import net.shoreline.client.api.macro.Macro;
import net.shoreline.client.api.module.Module;
import net.shoreline.client.api.module.ToggleModule;
import net.shoreline.client.init.Managers;
import net.shoreline.client.util.KeyboardUtil;
import net.shoreline.client.util.chat.ChatUtil;
import net.minecraft.util.Formatting;

/**
 *
 *
 * @author linus
 * @since 1.0
 */
public class PrefixCommand extends Command
{
    //
    Argument<String> prefixArgument = new StringArgument("Single-char Prefix",
            "The new chat command prefix");

    /**
     *
     */
    public PrefixCommand()
    {
        super("Prefix", "Allows you to change the chat command prefix");
    }

    /**
     * Runs when the command is inputted in chat
     */
    @Override
    public void onCommandInput()
    {
        final String prefix = prefixArgument.getValue();
        if (prefix != null)
        {
            if (prefix.length() > 1)
            {
                ChatUtil.error("Prefix can only be one character!");
                return;
            }
            int keycode = KeyboardUtil.getKeyCode(prefix);
            for (Macro macro : Managers.MACRO.getMacros())
            {
                if (macro.getKeycode() == keycode)
                {
                    ChatUtil.error("Macro already bound to " + prefix + "!");
                    return;
                }
            }
            for (Module module : Managers.MODULE.getModules())
            {
                if (module instanceof ToggleModule toggle)
                {
                    Macro keybind = toggle.getKeybinding();
                    if (keybind.getKeycode() == keycode)
                    {
                        ChatUtil.error(module.getName() + " already bound to "
                                + prefix + "!");
                        return;
                    }
                }
            }
            Managers.COMMAND.setPrefix(prefix, keycode);
            ChatUtil.clientSendMessage("Client command prefix changed to " +
                    Formatting.DARK_BLUE + prefix + Formatting.RESET + "!");
        }
    }
}
