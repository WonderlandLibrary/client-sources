/*
 * Decompiled with CFR 0.145.
 */
package us.amerikan.command.commands;

import org.lwjgl.input.Keyboard;
import us.amerikan.amerikan;
import us.amerikan.command.Command;
import us.amerikan.modules.Module;
import us.amerikan.modules.ModuleManager;
import us.amerikan.utils.file.files.FileManager;

public class Bind
extends Command {
    public Bind() {
        super("bind", "Binds a module.");
    }

    @Override
    public void execute(String[] args) {
        if (args.length != 2) {
            Bind.messageWithPrefix(" \u00a77Bind \u00a78<\u00a72Module\u00a78> \u00a78<\u00a72Button\u00a78>\u00a7f");
            return;
        }
        Module mod = ModuleManager.getModuleByName(args[0]);
        String key = args[1];
        Integer intKey = 0;
        if (mod != null) {
            if (Keyboard.getKeyIndex(key.toUpperCase()) != 0) {
                mod.setKeybind(Keyboard.getKeyIndex(args[1].toUpperCase()));
                amerikan.filemgr.saveModules();
                Bind.messageWithPrefix(" \u00a72The Module \u00a75\u00a7l" + mod.getDisplayname() + "\u00a7r\u00a72 was set on \u00a79\u00a7l" + key.toUpperCase() + ".");
            } else {
                Bind.messageWithPrefix(" \u00a7cThe Key is not avilable");
            }
        } else {
            Bind.messageWithPrefix(" \u00a7cThe Module \u00a75\u00a7l" + args[0] + "\u00a7r\u00a7c was not found :( .");
        }
    }
}

