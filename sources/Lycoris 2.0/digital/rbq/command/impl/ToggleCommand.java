/*
 * Decompiled with CFR 0.150.
 */
package digital.rbq.command.impl;

import digital.rbq.command.AbstractCommand;
import digital.rbq.core.Autumn;
import digital.rbq.module.Module;
import digital.rbq.utils.Logger;

public final class ToggleCommand
extends AbstractCommand {
    public ToggleCommand() {
        super("Toggle", "Toggle modules on and off.", "toggle <module>", "toggle", "t");
    }

    @Override
    public void execute(String ... arguments) {
        if (arguments.length == 2) {
            String moduleName = arguments[1];
            Object module = Autumn.MANAGER_REGISTRY.moduleManager.getModuleOrNull(moduleName);
            if (module != null) {
                ((Module)module).toggle();
            } else {
                Logger.log("'" + moduleName + "' is not a module.");
            }
        } else {
            this.usage();
        }
    }
}

