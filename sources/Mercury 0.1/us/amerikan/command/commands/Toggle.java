/*
 * Decompiled with CFR 0.145.
 */
package us.amerikan.command.commands;

import java.util.List;
import us.amerikan.amerikan;
import us.amerikan.command.Command;
import us.amerikan.modules.Module;
import us.amerikan.modules.ModuleManager;

public class Toggle
extends Command {
    public Toggle() {
        super("toggle", "toggle a module");
    }

    @Override
    public void execute(String[] args) {
        if (args.length != 1) {
            Toggle.messageWithPrefix(" \u00a77toggle \u00a72<Module> \u00a7f");
            return;
        }
        String Module2 = args[0];
        if (Module2.equalsIgnoreCase("usual")) {
            for (Module m2 : amerikan.modulemanager.getModules()) {
                m2.isEnabled();
            }
            Toggle.messageWithPrefix("Enabled toggle setup");
            return;
        }
        try {
            ModuleManager.getModuleByName(Module2).toggle();
            Toggle.messageWithPrefix(" " + ModuleManager.getModuleByName(Module2).getName() + " \u00a7fwas " + (ModuleManager.getModuleByName(Module2).isEnabled() ? "\u00a7aenabled" : "\u00a7cdisabled"));
        }
        catch (Exception e2) {
            Toggle.messageWithPrefix(" \u00a7cModule not found");
        }
    }
}

