/*
 * Decompiled with CFR 0.150.
 */
package digital.rbq.command.impl;

import digital.rbq.command.AbstractCommand;
import digital.rbq.core.Autumn;
import digital.rbq.utils.Logger;

public final class HelpCommand
extends AbstractCommand {
    public HelpCommand() {
        super("Help", "Lists all commands.", "help", "help", "h");
    }

    @Override
    public void execute(String ... arguments) {
        if (arguments.length == 1) {
            Logger.log("---------------- Help ----------------");
            for (AbstractCommand command : Autumn.MANAGER_REGISTRY.commandManager.getCommands()) {
                Logger.log(command.getName() + ": \u00a7F" + command.getDescription());
            }
        } else {
            this.usage();
        }
    }
}

