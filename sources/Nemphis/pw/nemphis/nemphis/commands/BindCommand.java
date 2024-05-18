/*
 * Decompiled with CFR 0_118.
 * 
 * Could not load the following classes:
 *  org.lwjgl.input.Keyboard
 */
package pw.vertexcode.nemphis.commands;

import java.io.IOException;
import java.util.List;
import org.lwjgl.input.Keyboard;
import pw.vertexcode.nemphis.Nemphis;
import pw.vertexcode.nemphis.command.Command;
import pw.vertexcode.nemphis.module.ModuleManager;
import pw.vertexcode.util.module.types.ToggleableModule;

public class BindCommand
extends Command {
    @Override
    public void execute(String[] arguments) {
        if (arguments.length == 1) {
            this.sendMessage("\u00a74\u00a7m\u00a7l=======\u00a7r \u00a77BindSystem \u00a74\u00a7m\u00a7l=======", false);
            this.sendMessage("\u00a78.\u00a77bind reset \u00a78[\u00a74module\u00a78]", false);
            this.sendMessage("\u00a78.\u00a77bind \u00a78[\u00a74module\u00a78] \u00a78[\u00a74bind\u00a78]", false);
            this.sendMessage("\u00a74\u00a7m\u00a7l=======\u00a7r \u00a77BindSystem \u00a74\u00a7m\u00a7l=======", false);
        } else if (arguments.length == 2) {
            this.sendMessage("\u00a78.\u00a77bind", true);
        } else if (arguments.length == 3) {
            if (arguments[1].equalsIgnoreCase("reset")) {
                ModuleManager moduleManager = Nemphis.instance.modulemanager;
                for (ToggleableModule module : moduleManager.getMods()) {
                    if (!module.getName().equalsIgnoreCase(arguments[2])) continue;
                    module.setKeybind(0);
                    try {
                        module.save();
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }
                    this.sendMessage("\u00a77Resetted the keybind from module \u00a78\"\u00a74" + module.getName() + "\u00a78\"\u00a77!", true);
                    return;
                }
                this.sendMessage("\u00a77Module \u00a78\"\u00a74" + arguments[2] + "\u00a78\" \u00a77not found.", true);
                return;
            }
            ModuleManager moduleManager2 = Nemphis.instance.modulemanager;
            for (ToggleableModule module : Nemphis.instance.modulemanager.getMods()) {
                String name = arguments[1];
                String modulename = module.getName();
                String keybind = arguments[2];
                if (!modulename.equalsIgnoreCase(name)) continue;
                module.setKeybind(Keyboard.getKeyIndex((String)keybind.toUpperCase()));
                try {
                    module.save();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
                this.sendMessage("\u00a77Bound module \u00a78\"\u00a74" + modulename + "\u00a78\" \u00a77to key " + keybind, true);
            }
        }
    }

    @Override
    public String getName() {
        return "bind";
    }

    @Override
    public String getDescription() {
        return "bind Modules to a Key";
    }
}

