package win.sightclient.module.other;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import win.sightclient.event.Event;
import win.sightclient.event.events.chat.EventChatReceive;
import win.sightclient.event.events.render.EventName;
import win.sightclient.module.Category;
import win.sightclient.module.Module;
import win.sightclient.utils.minecraft.ServerUtils;

public class Streamer extends Module {

	public Streamer() {
		super("Streamer", Category.OTHER);
	}

	@Override
	public void onEvent(Event e) {
		if (e instanceof EventName) {
			EventName en = (EventName)e;
			StringBuilder newName = new StringBuilder();
			boolean looking = false;
			for (int i = 0; i < en.getName().length(); i++) {
				char currentChar = en.getName().charAt(i);
				newName.append(currentChar);
				if (currentChar == '\u00a7') {
					looking = true;
				} else if (looking) {
					looking = false;
					newName.append("\u00a7k");
				}
			}
			en.setName(newName.toString());
		} else if (e instanceof EventChatReceive) {
			EventChatReceive ecr = (EventChatReceive)e;
			List<EntityPlayer> players = ServerUtils.getTabPlayerList();
			String message = ecr.getMessage().getFormattedText();
			boolean contains = false;
			String name = "";
			for (EntityPlayer ep : players) {
				if (ep != null && ep.getName() != null && message.contains(ep.getName())) {
					contains = true;
					name = ep.getName();
					continue;
				}
			}
			if (contains) {
				String newString = new String(); 
		        for (int i = 0; i < message.length(); i++) { 
		            if (i == message.indexOf(name)) {
		                newString += "\u00a7k";
		            }
		            newString += message.charAt(i);
		        }
		        ecr.setChat(new ChatComponentText(newString));
			}
		}
	}
}
