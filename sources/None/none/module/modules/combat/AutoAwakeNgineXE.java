package none.module.modules.combat;

import org.lwjgl.input.Keyboard;

import com.mojang.realmsclient.gui.ChatFormatting;

import none.Client;
import none.event.Event;
import none.event.RegisterEvent;
import none.event.events.EventPreMotionUpdate;
import none.module.Category;
import none.module.Module;
import none.module.modules.movement.Fly;
import none.notifications.Notification;
import none.notifications.NotificationType;

public class AutoAwakeNgineXE extends Module{

	public AutoAwakeNgineXE() {
		super("AutoAwakeNgineXE", "Awake Ngine XE", Category.COMBAT, Keyboard.KEY_NONE);
	}
	
	@Override
	protected void onEnable() {
		super.onEnable();
		Client.instance.notification.showSP(new Notification(NotificationType.SP, getName(), ChatFormatting.WHITE + " Awakening Value.", 10));
	}

	@Override
	@RegisterEvent(events = EventPreMotionUpdate.class)
	public void onEvent(Event event) {
		if (!isEnabled()) return;
		
		if (Client.instance.moduleManager.fly.isEnabled()) {
			evc(ChatFormatting.RED + "Hey Awake Ngine XE can't use Fly in Seksin");
			Client.instance.moduleManager.fly.setState(false);
		}
	}

}
