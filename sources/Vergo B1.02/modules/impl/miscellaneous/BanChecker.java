package xyz.vergoclient.modules.impl.miscellaneous;

import xyz.vergoclient.event.Event;
import xyz.vergoclient.modules.Module;
import xyz.vergoclient.modules.OnEventInterface;

public class BanChecker extends Module implements OnEventInterface {

	public BanChecker() {
		super("BanChecker", Category.MISCELLANEOUS);
	}

	@Override
	public void onEvent(Event e) {

	}

}
