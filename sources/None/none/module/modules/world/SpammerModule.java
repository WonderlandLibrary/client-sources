package none.module.modules.world;

import org.lwjgl.input.Keyboard;

import none.event.Event;
import none.event.RegisterEvent;
import none.event.events.EventPreMotionUpdate;
import none.module.Category;
import none.module.Module;
import none.utils.ChatUtil;
import none.utils.TimeHelper;
import none.utils.Utils;
import none.valuesystem.NumberValue;
import none.valuesystem.StringValue;
import none.valuesystem.Value;

public class SpammerModule extends Module{

	public SpammerModule() {
		super("Spammer", "Spammer", Category.WORLD, Keyboard.KEY_NONE);
	}
	
	private NumberValue<Integer> delay = new NumberValue<>("Delay", 25, 5, 120);
	private NumberValue<Integer> length = new NumberValue<>("Random-Length", 1, 1, 14);
	public static StringValue MessageToSpammer = new StringValue("Message", "%random% Spammer.exc - SubString");
	
	TimeHelper timer = new TimeHelper();
	
	@Override
	@RegisterEvent(events = {EventPreMotionUpdate.class})
	public void onEvent(Event event) {
		if (!isEnabled()) return;
		
		if (event instanceof EventPreMotionUpdate) {
			EventPreMotionUpdate e = (EventPreMotionUpdate) event;
			
			if (e.isPre()) {
				String message = MessageToSpammer.getObject();
				if (message.contains("%random%")) {
					message = message.replaceAll("%random%", Utils.randomString(length.getObject()));
				}
				if (timer.hasTimeReached(delay.getInteger() * 100) && mc.currentScreen == null) {
					ChatUtil.sendChat(message);
					timer.setLastMS();
				}
			}
		}
	}

}
