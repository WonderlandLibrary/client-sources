package me.vaziak.sensation.client.impl.misc;

import me.vaziak.sensation.client.api.Category;
import me.vaziak.sensation.client.api.Module;
import me.vaziak.sensation.client.api.event.annotations.Collect;
import me.vaziak.sensation.client.api.event.events.ProcessPacketEvent;
import me.vaziak.sensation.client.api.property.impl.StringsProperty;
import net.minecraft.network.play.server.S02PacketChat;

public class AutoPlay extends Module {

	private StringsProperty mode = new StringsProperty("Mode", "The fucking mode", false, true, new String[] {"Solo Insane", "Teams Insane", "Solo Normal", "Teams Normal"});
	
	private final String[] messages = new String[] {"1st Killer - ", 
			"1st Place - ", 
			"You died! Want to play again? Click here!", 
			"Winner: ", 
			" - Damage Dealt - ",
			"1st - ",
			"Winning Team - ",
			"Winners: ",
			"Winner: ",
			"Winning Team: ",
			" win the game!",
			"Top Seeker: ",
			"1st Place: ",
			"Last team standing!", "Winner #1 (",
			"Top Survivors",
			"Winners - "};
    
	public AutoPlay() {
		super("Auto Play", Category.MISC);
		registerValue(mode);
	}
	
	@Collect
	public void onEvent(ProcessPacketEvent event) {
		if (event.getPacket() instanceof S02PacketChat) {
			S02PacketChat packet = (S02PacketChat) event.getPacket();
			for (String message : messages) {
				if (packet.getChatComponent().getUnformattedText().contains(message)) {
					String gameName = mode.getSelectedStrings().get(0).toLowerCase().replace(" ", "_");
					mc.thePlayer.sendChatMessage("/play " + gameName);
				}
			}
		}
	}
}
