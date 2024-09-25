package none.module.modules.render;

import org.lwjgl.input.Keyboard;

import none.Client;
import none.event.Event;
import none.event.RegisterEvent;
import none.event.events.Event2D;
import none.module.Category;
import none.module.Module;
import none.valuesystem.ModeValue;

public class NotificationHUD extends Module{

	public NotificationHUD() {
		super("Notification", "Notification", Category.RENDER, Keyboard.KEY_NONE);
	}
	
	private static String[] Ntype = {"Slow", "Instant"};
	public static ModeValue NotificationType = new ModeValue("Notification-Type", "Slow", Ntype);

	@Override
	@RegisterEvent(events = Event2D.class)
	public void onEvent(Event event) {
		if (!isEnabled()) return;
		
		if (event instanceof Event2D) {
			Client.instance.notification.render();
		}
	}

}
