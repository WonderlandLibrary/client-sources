package io.github.nevalackin.radium.command.impl;

import io.github.nevalackin.radium.RadiumClient;
import io.github.nevalackin.radium.command.Command;
import io.github.nevalackin.radium.command.CommandExecutionException;
import io.github.nevalackin.radium.module.Module;
import io.github.nevalackin.radium.utils.Wrapper;

public final class ToggleCommand extends Command {
    @Override
    public String[] getAliases() {
        return new String[]{"toggle", "t"};
    }

    @Override
    public void execute(String[] arguments) throws CommandExecutionException {
        if (arguments.length == 2) {
            String moduleName = arguments[1];

            for (Module module : RadiumClient.getInstance().getModuleManager().getModules()) {
                if (module.getLabel().replaceAll(" ", "").equalsIgnoreCase(moduleName)) {
                    module.toggle();
                    Wrapper.addChatMessage("'" + module.getLabel() + "' has been " + (module.isEnabled() ? "\247Aenabled\2477." : "\247Cdisabled\2477."));
                    return;
                }
            }
        }

        throw new CommandExecutionException(getUsage());
    }

    @Override
    public String getUsage() {
        return "toggle/t <module>";
    }
}
