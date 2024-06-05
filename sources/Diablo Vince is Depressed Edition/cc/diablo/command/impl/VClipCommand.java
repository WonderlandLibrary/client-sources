/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.eventbus.Subscribe
 */
package cc.diablo.command.impl;

import cc.diablo.command.Command;
import cc.diablo.event.impl.ChatEvent;
import com.google.common.eventbus.Subscribe;

public class VClipCommand
extends Command {
    public VClipCommand() {
        super("VClip", "Vertically teleport by a value");
    }

    @Subscribe
    public void onCommand(ChatEvent e) {
        String[] message = e.message.split(" ");
        if (message[0].equals("vclip")) {
            VClipCommand.mc.thePlayer.setPosition(VClipCommand.mc.thePlayer.posX, VClipCommand.mc.thePlayer.posY + Double.parseDouble(message[1]), VClipCommand.mc.thePlayer.posZ);
        }
    }
}

