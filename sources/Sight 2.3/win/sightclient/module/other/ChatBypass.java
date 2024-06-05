package win.sightclient.module.other;

import win.sightclient.event.Event;
import win.sightclient.event.events.chat.EventChatSend;
import win.sightclient.module.Category;
import win.sightclient.module.Module;

public class ChatBypass extends Module {

	public ChatBypass() {
		super("ChatBypass", Category.OTHER);
	}

	@Override
	public void onEvent(Event e) {
		if (e instanceof EventChatSend) {
			EventChatSend ecs = (EventChatSend)e;
			if (ecs.getMessage().startsWith(".")) {
				return;
			}
			if (ecs.getMessage().startsWith("/") && !ecs.getMessage().toLowerCase().startsWith("/msg ")
					&& !ecs.getMessage().toLowerCase().startsWith("/r ")) {
				return;
			}
			
			if (ecs.getMessage().startsWith("/r")) {
				if (ecs.getMessage().split(" ").length <= 1) {
					return;
				} else {
					ecs.setChat(ecs.getMessage().split(" ")[0] + this.getMessage(ecs.getMessage().substring(ecs.getMessage().indexOf(" "))));
				}
			} else if (ecs.getMessage().startsWith("/msg")) {
				String[] args = ecs.getMessage().split(" ");
				if (args.length <= 2) {
					return;
				} else {
					StringBuilder sb = new StringBuilder();
					for (int i = 0; i < args.length; i++) {
						if (i > 1) {
							sb.append(args[i]);
							sb.append(" ");
						}
					}
					ecs.setChat(ecs.getMessage().split(" ")[0] + " " + ecs.getMessage().split(" ")[1] + " " + this.getMessage(sb.toString()));
				}
			} else {
				ecs.setChat(this.getMessage(ecs.getMessage()));
			}
			System.out.println(ecs.getMessage());
		}
	}
	
	private String getMessage(String message) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < message.toCharArray().length; i++) {
			char c = message.charAt(i);
			if (i > 0) {
				sb.append("\u05fc");
			}
			sb.append(c);
		}
		return sb.toString();
	}
}
