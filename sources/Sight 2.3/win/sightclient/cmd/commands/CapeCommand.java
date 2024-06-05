package win.sightclient.cmd.commands;

import net.minecraft.event.ClickEvent;
import net.minecraft.event.ClickEvent.Action;
import net.minecraft.event.HoverEvent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import win.sightclient.Sight;
import win.sightclient.cmd.Command;
import win.sightclient.server.Capes;
import win.sightclient.utils.minecraft.ChatUtils;

public class CapeCommand extends Command {

	public CapeCommand() {
		super(new String[] {"Cape", "Capes"});
	}

	@Override
	public void onCommand(String message) {
		String[] args = message.split(" ");
		if (args.length > 1) {
			String name = args[1];
			if (name.equalsIgnoreCase("remove") || name.equalsIgnoreCase("delete") || name.equalsIgnoreCase("delete")) {
				Sight.instance.connection.setCape(Capes.NONE);
				ChatUtils.sendMessage("Removed your cape. (Rejoin to apply)");
				return;
			} else if (name.equalsIgnoreCase("list")) {
				ChatUtils.sendMessage("Capes: ");
				for (int i = 0; i < Capes.values().length; i++) {
					ChatStyle cs = new ChatStyle();
					if (Capes.values()[i] != Capes.NONE) {
						cs.setChatClickEvent(new ClickEvent(Action.OPEN_URL, Capes.values()[i].getURL()));
						cs.setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ChatComponentText("Click here to see what the cape looks like")));
					}
					ChatUtils.sendMessage("[" + (i + 1) + "] " + Capes.values()[i].getName(), cs);
				}
			} else {
				Capes cape = null;
				for (Capes c : Capes.values()) {
					if (c.getName().equalsIgnoreCase(name)) {
						cape = c;
					}
				}
				if (cape == null) {
					ChatUtils.sendMessage(name + " is not a cape. Type '.cape list' to get a list of capes");
				} else {
					Sight.instance.connection.setCape(cape);
					ChatUtils.sendMessage("Set your cape to: " + cape.getName() + ". (Rejoin to apply)");
				}
			}
		}
	}

}
