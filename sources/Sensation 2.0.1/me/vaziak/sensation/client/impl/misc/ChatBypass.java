package me.vaziak.sensation.client.impl.misc;

import java.util.ArrayList;

import me.vaziak.sensation.client.api.Category;
import me.vaziak.sensation.client.api.Module;
import me.vaziak.sensation.client.api.event.annotations.Collect;
import me.vaziak.sensation.client.api.event.events.SendPacketEvent;
import net.minecraft.network.play.client.C01PacketChatMessage;

public class ChatBypass extends Module { 

    public ChatBypass() {
        super("ChatBypass", Category.MISC); 
    }

    @Collect
    public void onPacketSend(SendPacketEvent event) {
    	if (event.getPacket() instanceof C01PacketChatMessage) {
            C01PacketChatMessage p = (C01PacketChatMessage) event.getPacket();
            String finalmsg = "";
            ArrayList<String> splitshit = new ArrayList();
            String[] msg = p.getMessage().split(" ");
            for (int i = 0; i < msg.length; i++) {
                char[] characters = msg[i].toCharArray();
                for (int i2 = 0; i2 < characters.length; i2++) {
                    splitshit.add(characters[i2] + "\u061C");
                }
                splitshit.add(" ");
            }
            for (int i = 0; i < splitshit.size(); i++) {
                finalmsg += splitshit.get(i);
            }
            if (p.getMessage().startsWith("-")) {
                p.setMessage(finalmsg.replaceFirst("-", ""));
                splitshit.clear();
            }
        }
    }
}
