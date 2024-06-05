package me.vaziak.sensation.client.impl.combat;

import me.vaziak.sensation.client.api.event.annotations.Collect;
import me.vaziak.sensation.client.api.event.events.ProcessPacketEvent;
import me.vaziak.sensation.Sensation;
import me.vaziak.sensation.client.api.Category;
import me.vaziak.sensation.client.api.Module;
import me.vaziak.sensation.utils.math.TimerUtil;
import net.minecraft.network.play.server.S02PacketChat;
/**
 * Made by Jonathan H. (Niada)
 *
 * Sensation @  Azuma.club
 **/
public class KillSults extends Module {
	private int counter;

	public KillSults() {
		super("KillSults", Category.COMBAT);
	}

	String[] messages = new String[] {};

	private TimerUtil stopwatch = new TimerUtil();

	public void onEnable() {
		counter = 0;
	}

	@Collect
	public void onProcessPacket(ProcessPacketEvent processPacketEvent) {
		if (processPacketEvent.getPacket() instanceof S02PacketChat) {
			S02PacketChat packet = (S02PacketChat) processPacketEvent.getPacket();
			String msg = packet.getChatComponent().getUnformattedText();
			String[] insults = {"%player% is so fat that when you sat on UZI he became LIL",
					"%player% is the person that they use as the image for Diabetes's awareness month",
					"%player% so fat that every time they breath in your city needs to get air donated to it.",
					"%player%'s hair so gray they call it Ban ID gray",
					"%player%'s head is so big that when he walks in the room his hair line gets there right as he's leaving",
					"%player% was the reason Mien Koph was written",
					"%player% thought GCheat was a type of STD",
					"%player% suggested Valve implement BAC integration",
					"%player% still pays for CSGO",
					"%player% bought a copy of NCP",
					"%player% is so stupid that when  %player% talks it isnt off the top its off the bottom",
					"They had to make new definitions for autism when %player% started going to school",
					"%player% is probably friends with Vladymyr LOL :joy:",
					"Astolfo more like Asstolfo",
					"I bet your the type to quickdrop irl", "i bet you cry everytime you look in the mirror",
					"ur a skid lol",
					"Your the autistic kid in class that makes chairs levitate",
					"%player% probably watches anime with his dad",
					"%player% is the person they use as the cover picture for autism parenting guides",
					"MMC has machine learning autoclicker checks - ArchyBot", "%player% is a fucking nigger", "%player% should kill themselves", "%player% is a fucking beaner", "%player% is gonna commit suicide"};
			if (msg.contains("by ") && msg.contains(mc.thePlayer.getName() + ".") && stopwatch.hasPassed(3000)) {
				String[] split = msg.split(" ");

				if (counter >= insults.length)
					counter = 0;

				String playerName = split[0];

				String insult = (insults[counter] + (insults[counter].endsWith(".") ? "" : ".")).replaceAll("%player%", playerName);

				mc.thePlayer.sendChatMessage(Sensation.instance.cheatManager.isModuleEnabled("ChatBypass") ? "-" + insult : insult);

				counter++;
				stopwatch.reset();
			}
		}
	}
}