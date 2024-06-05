package rip.helium.cheat.impl.misc;

import java.util.ArrayList;

import me.hippo.systems.lwjeb.annotation.Collect;
import net.minecraft.network.play.client.C01PacketChatMessage;
import rip.helium.cheat.Cheat;
import rip.helium.cheat.CheatCategory;
import rip.helium.event.minecraft.ProcessPacketEvent;
import rip.helium.event.minecraft.SendPacketEvent;

public class ChatBypass extends Cheat {
	
	public ChatBypass() {
		super("ChatBypass", " cool shit", CheatCategory.MISC);
	}
	
    @Collect
    public void onPacket(final SendPacketEvent event) {
        if (event.getPacket() instanceof C01PacketChatMessage) {
            final C01PacketChatMessage p = (C01PacketChatMessage)event.getPacket();
            String finalmsg = "";
            final ArrayList<String> splitshit = new ArrayList<String>();
            final String[] msg = p.getMessage().split(" ");
            for (int i = 0; i < msg.length; ++i) {
                final char[] characters = msg[i].toCharArray();
                for (int i2 = 0; i2 < characters.length; ++i2) {
                    splitshit.add(String.valueOf(characters[i2]) + "\u061c");
                }
                splitshit.add(" ");
            }
            for (int i = 0; i < splitshit.size(); ++i) {
                finalmsg = String.valueOf(finalmsg) + splitshit.get(i);
            }
            if (p.getMessage().startsWith("%")) {
                splitshit.clear();
            }
        }
    }
}


