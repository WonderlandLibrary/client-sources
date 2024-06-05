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

public class ToggleCommand
extends Command {
    public ToggleCommand() {
        super("Toggle", "Toggle a module");
    }

    @Subscribe
    public void onCommand(ChatEvent e) {
        String[] message = e.message.split(" ");
        if (message[0].equals("toggle") || message[0].equals("t")) {
            try {
                Module module = ModuleManager.getModuleByName(message[1]);
                boolean moduleState = module.isToggled();
                if (moduleState) {
                    ChatHelper.addChat((Object)((Object)ChatColor.RED) + "Disabled " + (Object)((Object)ChatColor.WHITE) + module.getName() + "!");
                    module.setToggled(false);
                } else {
                    ChatHelper.addChat((Object)((Object)ChatColor.GREEN) + "Enabled " + (Object)((Object)ChatColor.WHITE) + module.getName() + "!");
                    module.setToggled(true);
                }
            }
            catch (Exception exception) {
                ChatHelper.addChat("Invalid module parsed");
                exception.printStackTrace();
            }
        }
    }
}

