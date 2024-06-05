package rip.helium.cheat.impl.misc;

import java.util.Arrays;
import java.util.List;

import me.hippo.systems.lwjeb.annotation.Collect;
import net.minecraft.network.play.server.S02PacketChat;
import rip.helium.cheat.Cheat;
import rip.helium.cheat.CheatCategory;
import rip.helium.event.minecraft.ProcessPacketEvent;
import rip.helium.notification.mgmt.NotificationManager;

public class AutoNoU extends Cheat {
	
    private final List<String> hackMatches;
    private final List<String> noUMatches;
	
	public AutoNoU() {
		super("AutoReply", "RepliesNoYouToInsults.", CheatCategory.MISC);
		this.hackMatches = Arrays.asList("hack", "report", "cheat", "aura", "speed", "record", "what client", "ban", "bhop", "bunny hop", "hax");
        this.noUMatches = Arrays.asList("gay", "fag", "stupid", "retard", "idiot", "skid", "loser", "cheater", "retard", "kys", "neck your self", "delete self", "cunt", "suck", "kill yourself");
	}
	
    @Collect
    public void onPacket(final ProcessPacketEvent e) {
        if (this.mc.thePlayer == null || this.mc.theWorld == null) {
            return;
        }
        if (e.getPacket() instanceof S02PacketChat) {
            final S02PacketChat chatPacket = (S02PacketChat)e.getPacket();
            final String message = chatPacket.getChatComponent().getUnformattedText();
            if (message.toLowerCase().contains(this.mc.getSession().getUsername().toLowerCase())) {
                    for (final String noUMatch : this.noUMatches) {
                        if (!message.contains(noUMatch)) {
                            continue;
                        }
                        this.mc.thePlayer.sendChatMessage("no u");
                        NotificationManager.postInfo("AutoReply", "Insult recieved, Replying ''no u''");
                        
                        break;
                    }
                }
                    for (final String hackMatch : this.hackMatches) {
                        if (!message.contains(hackMatch)) {
                            continue;
                        }
                        NotificationManager.postWarning("AutoReply", "Someone has called you out for §ccheating§f.");
                        break;
                }
        }
    }

}
