package tk.rektsky.Module.RektSky;

import tk.rektsky.Module.*;
import tk.rektsky.Event.*;
import net.minecraft.network.play.client.*;
import java.util.*;
import tk.rektsky.*;
import tk.rektsky.Event.Events.*;
import net.minecraft.network.play.server.*;
import com.mojang.realmsclient.gui.*;
import net.minecraft.util.*;
import net.minecraft.network.*;

public class MessageEncrypter extends Module
{
    private static final String ENCRYPTED_PREFIX = "[REDEENCRYPTED]";
    
    public MessageEncrypter() {
        super("MessageEncrypter", "Encrypt your message and decrypt meessages from other player so admin can't ban you for talking about illegal topics", 0, Category.REKTSKY);
    }
    
    @Override
    public void onEvent(final Event event) {
        try {
            if (event instanceof PacketSentEvent && ((PacketSentEvent)event).getPacket() instanceof C01PacketChatMessage) {
                final C01PacketChatMessage packet = (C01PacketChatMessage)((PacketSentEvent)event).getPacket();
                String message = "[REDEENCRYPTED]";
                if (!packet.getMessage().startsWith("/p ") && (packet.getMessage().startsWith("/") || packet.getMessage().startsWith("."))) {
                    return;
                }
                if (packet.getMessage().startsWith("/") && !packet.getMessage().startsWith("/p ")) {
                    return;
                }
                if (packet.getMessage().startsWith("/p ")) {
                    message = "/p [REDEENCRYPTED]" + Base64.getEncoder().encodeToString(packet.getMessage().replaceFirst("/p ", "").getBytes());
                }
                else {
                    message += Base64.getEncoder().encodeToString(packet.getMessage().getBytes());
                }
                if (message.length() > 100) {
                    Client.addClientChat("[RektSky message encryptor] Could not encrypt message because the encrypted message is too long for Chat Limit(100 chars)! If you still want to send the message, please disable this module or shorten your message!");
                    return;
                }
                packet.message = message;
            }
            if (event instanceof PacketReceiveEvent) {
                final Packet p = ((PacketReceiveEvent)event).getPacket();
                if (p instanceof S02PacketChat) {
                    final S02PacketChat packet2 = (S02PacketChat)p;
                    if (!packet2.isChat()) {
                        return;
                    }
                    if (packet2.getChatComponent().getFormattedText().contains("[REDEENCRYPTED]")) {
                        int index = packet2.getChatComponent().getUnformattedText().indexOf("[REDEENCRYPTED]");
                        final String message2 = packet2.getChatComponent().getUnformattedText().substring(index + "[REDEENCRYPTED]".length());
                        try {
                            index = packet2.getChatComponent().getFormattedText().indexOf("[REDEENCRYPTED]");
                            packet2.chatComponent = new ChatComponentText(packet2.getChatComponent().getFormattedText().substring(0, index) + ChatFormatting.GREEN + "[DECRYPTED] " + ChatFormatting.GOLD + new String(Base64.getDecoder().decode(message2)));
                        }
                        catch (Exception ex) {}
                    }
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
