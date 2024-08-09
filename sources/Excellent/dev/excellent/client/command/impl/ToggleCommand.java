package dev.excellent.client.command.impl;

import dev.excellent.Excellent;
import dev.excellent.client.command.Command;
import dev.excellent.client.module.api.Module;
import dev.excellent.impl.util.chat.ChatUtil;

public final class ToggleCommand extends Command {

    public ToggleCommand() {
        super("", "toggle", "t");
    }

    @Override
    public void execute(final String[] args) {

        if (args.length == 2) {
            final Module module = Excellent.getInst().getModuleManager().get(args[1]);

            if (module == null) {
                ChatUtil.addText("Invalid module");
                return;
            }

            module.toggle();
            ChatUtil.addText("Module " + (module.getModuleInfo().name() + " toggled " + (module.isEnabled() ? "on" : "off")));
        } else {
            error();
        }
    }
}