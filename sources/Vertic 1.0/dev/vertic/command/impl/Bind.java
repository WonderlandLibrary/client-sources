package dev.vertic.command.impl;

import dev.vertic.Client;
import dev.vertic.command.Command;
import dev.vertic.module.Module;
import org.lwjgl.input.Keyboard;

public class Bind extends Command {

    public Bind() {
        super("Bind", "bind", "b");
    }

    @Override
    public void call(String[] args) {
        if (args.length > 1) {
            Module m = Client.instance.getModuleManager().getModuleNoSpace(args[1]);
            if (m != null) {
                if (args.length > 2) {
                    m.setKey(Keyboard.getKeyIndex(args[2].toUpperCase()));
                    addChatMessage("Bound " + m.getName() + " to key " + Keyboard.getKeyName(m.getKey()));
                } else {
                    addChatMessage("No valid key to bind " + args[1]);
                }
            } else {
                addChatMessage(args[1] + " is not a valid module.");
            }
        }
    }
}
