/*
 * Decompiled with CFR 0.143.
 */
package me.thekirkayt.client.module.modules.client;

import me.thekirkayt.client.command.Command;
import me.thekirkayt.client.command.CommandManager;
import me.thekirkayt.client.module.Module;
import me.thekirkayt.event.EventTarget;
import me.thekirkayt.event.events.PacketSendEvent;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C01PacketChatMessage;

@Module.Mod(displayName="ChatCommands")
public class ChatCommands
extends Module {
    @EventTarget
    private void onPacketSend(PacketSendEvent event) {
        C01PacketChatMessage packet;
        String message;
        if (event.getPacket() instanceof C01PacketChatMessage && (message = (packet = (C01PacketChatMessage)event.getPacket()).getMessage()).startsWith(".")) {
            event.setCancelled(true);
            message = message.replace(".", "");
            Command commandFromMessage = CommandManager.getCommandFromMessage(message);
            String[] args = message.split(" ");
            commandFromMessage.runCommand(args);
        }
    }
}

