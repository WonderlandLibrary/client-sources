package net.shoreline.client.impl.command;

import net.shoreline.client.api.command.Command;
import net.shoreline.client.api.command.arg.Argument;
import net.shoreline.client.api.command.arg.arguments.ModuleArgument;
import net.shoreline.client.api.command.arg.arguments.StringArgument;
import net.shoreline.client.api.module.Module;
import net.shoreline.client.api.module.ToggleModule;
import net.shoreline.client.util.KeyboardUtil;
import net.shoreline.client.util.chat.ChatUtil;
import org.lwjgl.glfw.GLFW;

/**
 * @author linus
 * @since 1.0
 */
public class BindCommand extends Command {
    //
    Argument<Module> moduleArgument = new ModuleArgument("Module", "The param module to keybind");
    Argument<String> keybindArgument = new StringArgument("Keybind", "The new key to bind the module");

    /**
     *
     */
    public BindCommand() {
        super("Bind", "Keybinds a module");
    }

    @Override
    public void onCommandInput() {
        Module module = moduleArgument.getValue();
        if (module instanceof ToggleModule t) {
            final String key = keybindArgument.getValue();
            if (key == null || key.length() > 1) {
                ChatUtil.error("Invalid key!");
                return;
            }
            int keycode = KeyboardUtil.getKeyCode(key);
            if (keycode == GLFW.GLFW_KEY_UNKNOWN) {
                ChatUtil.error("Failed to parse key!");
                return;
            }
            t.keybind(keycode);
            ChatUtil.clientSendMessage("%s is now bound to %s!",
                    module.getName(), key.toUpperCase());
        }
    }
}
