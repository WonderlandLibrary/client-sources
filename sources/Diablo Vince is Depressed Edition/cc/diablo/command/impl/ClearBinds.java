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

public class ClearBinds
extends Command {
    public ClearBinds() {
        super("ClearBinds", "Clear all binds of ");
    }

    @Subscribe
    public void onCommand(ChatEvent e) {
        String[] message = e.message.split(" ");
        if (message[0].equals("clearbinds")) {
            for (Module m : ModuleManager.getModules()) {
                m.setKey(0);
            }
            ChatHelper.addChat("Reset binds");
        }
    }
}

