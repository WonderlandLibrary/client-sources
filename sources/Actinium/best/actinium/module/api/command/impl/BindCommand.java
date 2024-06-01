package best.actinium.module.api.command.impl;

import best.actinium.module.Module;
import best.actinium.module.api.command.Command;
import best.actinium.util.render.ChatUtil;
import best.actinium.Actinium;
import org.lwjglx.input.Keyboard;

import java.util.Arrays;

public final class BindCommand extends Command {
    public BindCommand() {
        super("bind", "keybind");
    }

    @Override
    public void execute(String[] args) {
        if (args.length >= 3) {
            final String moduleName = String.join(" ", Arrays.copyOfRange(args, 1, args.length - 1));
            final Module module = Actinium.INSTANCE.getModuleManager().get(moduleName);

            if (module == null) {
                ChatUtil.display("Could not find the module " + moduleName);
                return;
            }

            final String input = args[args.length - 1].toUpperCase();
            final int code = Keyboard.getKeyIndex(input);

            module.setKeyBind(code);
            ChatUtil.display("Bound " + module.getName() + " to " + Keyboard.getKeyName(code) + ".");
        } else {
            ChatUtil.display("Invalid arguments");
        }
    }
}
