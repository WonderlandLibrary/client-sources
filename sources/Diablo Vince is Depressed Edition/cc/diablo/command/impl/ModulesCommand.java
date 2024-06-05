/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.eventbus.Subscribe
 */
package cc.diablo.command.impl;

import cc.diablo.command.Command;
import cc.diablo.event.impl.ChatEvent;
import cc.diablo.helpers.render.ChatHelper;
import cc.diablo.manager.module.ModuleManager;
import cc.diablo.module.Module;
import com.google.common.eventbus.Subscribe;

public class ModulesCommand
extends Command {
    public ModulesCommand() {
        super("Modules", "Lists all modules and their descriptions");
    }

    @Subscribe
    public void onCommand(ChatEvent e) {
        String[] message = e.message.split(" ");
        if (message[0].equals("modules")) {
            for (Module m : ModuleManager.getModules()) {
                ChatHelper.addChat(m.getName() + " | " + m.getDescription());
            }
        }
    }
}

