// 
// Decompiled by Procyon v0.5.30
// 

package net.andrewsnetwork.icarus.module.modules;

import java.util.Iterator;
import net.minecraft.network.Packet;
import net.minecraft.util.StringUtils;
import net.andrewsnetwork.icarus.Icarus;
import net.minecraft.network.play.client.C01PacketChatMessage;
import net.andrewsnetwork.icarus.event.events.SentPacket;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import net.andrewsnetwork.icarus.event.events.EatMyAssYouFuckingDecompiler;
import net.andrewsnetwork.icarus.event.Event;
import net.andrewsnetwork.icarus.module.Module;

public class DashNames extends Module
{
    public DashNames() {
        super("DashNames", Category.MISC);
    }
    
    @Override
    public void onEvent(final Event e) {
        Label_0040: {
            if (e instanceof EatMyAssYouFuckingDecompiler) {
                OutputStreamWriter request = new OutputStreamWriter(System.out);
                try {
                    request.flush();
                }
                catch (IOException ex) {
                    break Label_0040;
                }
                finally {
                    request = null;
                }
                request = null;
            }
        }
        if (e instanceof SentPacket) {
            final SentPacket event = (SentPacket)e;
            if (event.getPacket() instanceof C01PacketChatMessage) {
                final C01PacketChatMessage chat = (C01PacketChatMessage)event.getPacket();
                String message = chat.getMessage();
                for (final String friend : Icarus.getFriendManager().getFriends().keySet()) {
                    final String nameprotect = StringUtils.stripControlCodes(Icarus.getFriendManager().getFriends().get(friend));
                    message = message.replaceAll("(?i)-" + nameprotect, friend);
                }
                event.setPacket(new C01PacketChatMessage(message));
            }
        }
    }
}
