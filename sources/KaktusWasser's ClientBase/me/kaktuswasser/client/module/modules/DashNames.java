// 
// Decompiled by KaktusWasser
// https://www.youtube.com/KaktusWasserReal
// 

package me.kaktuswasser.client.module.modules;

import java.util.Iterator;

import me.kaktuswasser.client.Client;
import me.kaktuswasser.client.event.Event;
import me.kaktuswasser.client.event.events.EatMyAssYouFuckingDecompiler;
import me.kaktuswasser.client.event.events.SentPacket;
import me.kaktuswasser.client.module.Module;
import net.minecraft.network.Packet;
import net.minecraft.util.StringUtils;
import net.minecraft.network.play.client.C01PacketChatMessage;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class DashNames extends Module
{
    public DashNames() {
        super("DashNames", Category.MISC);
    }
    
    @Override
    public void onEvent(final Event e) {
        if (e instanceof SentPacket) {
            final SentPacket event = (SentPacket)e;
            if (event.getPacket() instanceof C01PacketChatMessage) {
                final C01PacketChatMessage chat = (C01PacketChatMessage)event.getPacket();
                String message = chat.getMessage();
                for (final String friend : Client.getFriendManager().getFriends().keySet()) {
                    final String nameprotect = StringUtils.stripControlCodes(Client.getFriendManager().getFriends().get(friend));
                    message = message.replaceAll("(?i)-" + nameprotect, friend);
                }
                event.setPacket(new C01PacketChatMessage(message));
            }
        }
    }
}
