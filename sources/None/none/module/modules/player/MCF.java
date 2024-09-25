package none.module.modules.player;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import net.minecraft.entity.player.EntityPlayer;
import none.Client;
import none.event.Event;
import none.event.RegisterEvent;
import none.event.events.EventMouse;
import none.friend.FriendManager;
import none.module.Category;
import none.module.Module;
import none.notifications.Notification;
import none.notifications.NotificationType;

public class MCF extends Module{

	public MCF() {
		super("MiddleClickFriend", "MiddleClickFriend", Category.PLAYER, Keyboard.KEY_NONE);
	}

	@Override
	@RegisterEvent(events = EventMouse.class)
	public void onEvent(Event event) {
		if (!isEnabled()) return;
		if (!(event instanceof EventMouse)) return;
		EventMouse em = (EventMouse) event;
        if (em.getButtonID() == 2 && Mouse.getEventButtonState() && mc.objectMouseOver.entityHit != null && mc.objectMouseOver.entityHit instanceof EntityPlayer) {
            EntityPlayer entityPlayer = (EntityPlayer) mc.objectMouseOver.entityHit;
            if (FriendManager.isFriend(entityPlayer.getName())) {
                evc("\247b" + entityPlayer.getName() + "\2477 has been \247cunfriended.");
                FriendManager.removeFriend(entityPlayer.getName());
                Client.instance.notification.show(new Notification(NotificationType.INFO, "Friend", "\247b" + entityPlayer.getName() + "\2477 has been \247cunfriended.", 3));
            } else {
                evc("\247b" + entityPlayer.getName() + "\2477 has been \247afriended.");
                Client.instance.notification.show(new Notification(NotificationType.INFO, "Friend", "\247b" + entityPlayer.getName() + "\2477 has been \247afriended.", 3));
                FriendManager.addFriend(entityPlayer.getName(), entityPlayer.getName());
            }
        }
	}
}
