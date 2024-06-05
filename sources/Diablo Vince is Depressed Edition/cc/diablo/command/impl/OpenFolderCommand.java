/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.eventbus.Subscribe
 */
package cc.diablo.command.impl;

import cc.diablo.Main;
import cc.diablo.command.Command;
import cc.diablo.event.impl.ChatEvent;
import cc.diablo.helpers.render.ChatHelper;
import com.google.common.eventbus.Subscribe;
import java.io.IOException;

public class OpenFolderCommand
extends Command {
    public OpenFolderCommand() {
        super("OpenFolder", "Opends the folder containing all diablo files");
    }

    @Subscribe
    public void onCommand(ChatEvent e) {
        String[] message = e.message.split(" ");
        if (message[0].equals("openfolder")) {
            try {
                Runtime.getRuntime().exec("explorer.exe /select," + Main.fileDir);
                ChatHelper.addChat("Opened directory in file explorer");
            }
            catch (IOException ex) {
                ChatHelper.addChat("An I/O Exception has occurred");
                ex.printStackTrace();
            }
        }
    }
}

