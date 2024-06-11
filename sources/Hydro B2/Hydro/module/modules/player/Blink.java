package Hydro.module.modules.player;

import Hydro.event.Event;
import Hydro.event.events.EventPacket;
import Hydro.module.Category;
import Hydro.module.Module;

public class Blink extends Module {

	public Blink() {
		super("Blink", 0, true, Category.PLAYER, "Allows you to 'teleport'");
	}
	
	@Override
	public void onEvent(Event e) {
		if(e instanceof EventPacket) {
			if(((EventPacket) e).isSending()) {
				
			}
		}
	}

}
