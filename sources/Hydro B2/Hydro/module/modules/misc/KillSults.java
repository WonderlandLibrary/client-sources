package Hydro.module.modules.misc;

import java.util.Random;

import org.lwjgl.input.Keyboard;

import Hydro.event.Event;
import Hydro.event.events.EventChat;
import Hydro.event.events.EventPacket;
import Hydro.module.Category;
import Hydro.module.Module;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S02PacketChat;

public class KillSults extends Module {
	
	public Random generator = new Random();
	public static String[] insults = {"Hydro client on top", "Get Hydro client at http://www.foxxy.lol/hydro", "Your free trial for life has expired", "LiXo hacK??", "Wait you guys cant teleport?", "$usn just got rekt by Hydro client", "Go get Hydro client its free!", "L get good sigma hatar"};

	public KillSults() {
		super("KillSults", Keyboard.KEY_NONE, true, Category.MISC, "Sends a insult when you kill someone");
	}
	
	public void onEvent(Event e) {
		if(e instanceof EventChat) {
			String line = ((EventChat) e).getChatComponent().getUnformattedText();
			System.out.println(line);
			if(line.contains("foi morto por")) {
				System.out.println(mc.thePlayer.getDisplayName());

				String msg = line;
				
				String[] deathsplit = msg.split(" foi morto por ");
				String victm = deathsplit[0];
				String killer = deathsplit[1];
				System.out.println("'" + killer + "'");
				System.out.println("'" + victm + "'");
				System.out.println("'" + mc.thePlayer.getName() + "'");
				System.out.println(killer == mc.thePlayer.getName());

				
				if(killer.contains(mc.thePlayer.getName())) {
					String insult = insults[generator.nextInt(insults.length)];
					insult = insult.replace("$usn", victm);
					System.out.println(insult);
					mc.thePlayer.sendChatMessage(insult + " ");
				}
			}
		}
	}
}
	
