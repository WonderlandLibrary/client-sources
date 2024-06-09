package io.github.liticane.clients.feature.command.impl;

import io.github.liticane.clients.Client;
import io.github.liticane.clients.feature.module.Module;
import io.github.liticane.clients.util.misc.ChatUtil;
import org.lwjglx.input.Keyboard;
import io.github.liticane.clients.feature.command.Command;

public final class BindCommand extends Command {
    public BindCommand() {
        super("bind", "keybind");
    }

    @Override
    public void execute(String[] args) {
        if (args.length == 3) {
            final Module module = Client.INSTANCE.getModuleManager().get(args[1]);

            if (module == null) {
                ChatUtil.display("Could not find the module");
                return;
            }

            final String input = args[2].toUpperCase();
            final int code = Keyboard.getKeyIndex(input);

            module.setKeyBind(code);
            ChatUtil.display("Bound " + module.getName() + " to " + Keyboard.getKeyName(code) + ".");
        } else {
            ChatUtil.display("Invalid arguments");
        }
    }
}
