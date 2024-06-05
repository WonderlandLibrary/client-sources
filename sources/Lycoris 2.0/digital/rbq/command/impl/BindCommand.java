/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.lwjgl.input.Keyboard
 */
package digital.rbq.command.impl;

import org.lwjgl.input.Keyboard;
import digital.rbq.command.AbstractCommand;
import digital.rbq.core.Autumn;
import digital.rbq.module.Module;
import digital.rbq.utils.Logger;

public final class BindCommand
extends AbstractCommand {
    public BindCommand() {
        super("Bind", "Set and delete key binds.", "bind <module/*> <key/NONE>", "bind", "b");
    }

    @Override
    public void execute(String ... arguments) {
        if (arguments.length == 3) {
            String moduleName = arguments[1];
            Object module = Autumn.MANAGER_REGISTRY.moduleManager.getModuleOrNull(moduleName);
            String bind = arguments[2].toUpperCase();
            if (module != null) {
                ((Module)module).getKeyBind().setKeyCode(Keyboard.getKeyIndex((String)bind));
                Logger.log("Bound '" + ((Module)module).getLabel() + "' to '" + bind + "'.");
            } else if (moduleName.equals("*")) {
                for (Module module1 : Autumn.MANAGER_REGISTRY.moduleManager.getModules()) {
                    module1.getKeyBind().setKey(bind);
                }
                Logger.log("Bound all modules to '" + bind + "'.");
            } else {
                Logger.log("'" + moduleName + "' is not a module.");
            }
            Autumn.MANAGER_REGISTRY.moduleManager.saveData();
        } else {
            this.usage();
        }
    }
}

