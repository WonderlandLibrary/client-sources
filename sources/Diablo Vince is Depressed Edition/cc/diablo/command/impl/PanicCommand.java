/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.eventbus.Subscribe
 */
package cc.diablo.command.impl;

import cc.diablo.command.Command;
import cc.diablo.event.impl.ChatEvent;
import cc.diablo.helpers.render.ChatColor;
import cc.diablo.helpers.render.ChatHelper;
import cc.diablo.manager.module.ModuleManager;
import cc.diablo.module.Module;
import com.google.common.eventbus.Subscribe;

public class PanicCommand
extends Command {
    public PanicCommand() {
        super("Panic", "Disables all modules");
    }

    @Subscribe
    public void onCommand(ChatEvent e) {
        String[] message = e.message.split(" ");
        if (message[0].equals("p") || message[0].equals("panic")) {
            try {
                for (Module module : ModuleManager.getModules()) {
                    boolean moduleState = module.isToggled();
                    if (!moduleState) continue;
                    ChatHelper.addChat((Object)((Object)ChatColor.RED) + "Disabled " + (Object)((Object)ChatColor.WHITE) + module.getName() + "!");
                    module.setToggled(false);
                }
                ChatHelper.addChat("Disabled all modules!");
            }
            catch (Exception exception) {
                ChatHelper.addChat("ALL MODULES DISABLED BOZO");
                exception.printStackTrace();
            }
        }
    }
}

