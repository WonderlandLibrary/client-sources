/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.eventbus.Subscribe
 *  org.lwjgl.input.Keyboard
 */
package cc.diablo.command.impl;

import cc.diablo.command.Command;
import cc.diablo.event.impl.ChatEvent;
import cc.diablo.helpers.render.ChatHelper;
import cc.diablo.manager.file.FileManager;
import cc.diablo.manager.module.ModuleManager;
import cc.diablo.module.Module;
import com.google.common.eventbus.Subscribe;
import java.util.Locale;
import org.lwjgl.input.Keyboard;

public class BindCommand
extends Command {
    public BindCommand() {
        super("Bind", "Bind A Module To A Key");
    }

    @Subscribe
    public void onCommand(ChatEvent e) {
        String[] message = e.message.split(" ");
        if (message[0].equals("bind")) {
            try {
                Module module = ModuleManager.getModuleByName(message[1]);
                if (module == null) {
                    ChatHelper.addChat("Invalid Module");
                    return;
                }
                module.setKey(Keyboard.getKeyIndex((String)message[2].toUpperCase(Locale.ROOT)));
                ChatHelper.addChat("Bound " + module.getName() + " to " + Keyboard.getKeyName((int)module.getKey()));
                new FileManager().saveFiles();
            }
            catch (Exception exception) {
                ChatHelper.addChat("Failed to bind module");
                exception.printStackTrace();
            }
        }
    }
}

