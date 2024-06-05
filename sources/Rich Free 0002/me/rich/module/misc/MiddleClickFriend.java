package me.rich.module.misc;

import com.mojang.realmsclient.gui.ChatFormatting;

import me.rich.Main;
import me.rich.event.EventTarget;
import me.rich.event.events.EventMouse;
import me.rich.helpers.friend.FriendManager;
import me.rich.module.Category;
import me.rich.module.Feature;
import me.rich.notifications.NotificationPublisher;
import me.rich.notifications.NotificationType;
import net.minecraft.entity.player.EntityPlayer;

public class MiddleClickFriend extends Feature {

	public MiddleClickFriend() {
		super("MiddleClickFriend", 0, Category.MISC);
	}

	@EventTarget
	public void onMouseEvent(EventMouse event) {
		if (event.key == 2 && mc.objectMouseOver.entityHit != null && mc.objectMouseOver.entityHit instanceof EntityPlayer) {
			EntityPlayer entityPlayer = (EntityPlayer) mc.objectMouseOver.entityHit;
			if (FriendManager.isFriend(entityPlayer.getName())) {
				FriendManager.removeFriend(entityPlayer.getName());
				Main.msg(ChatFormatting.RED + entityPlayer.getName() + ChatFormatting.WHITE + " was removed from ur friends list.", true);
			} else {
				FriendManager.getFriends().addFriend(entityPlayer.getName(), "MiddleClickFriend");
				Main.msg(ChatFormatting.GREEN + entityPlayer.getName() + ChatFormatting.WHITE + " added to ur friend list.", true);
			}
		}
	}
	@Override
	public void onEnable()
	{
		super.onEnable();
		NotificationPublisher.queue(this.getName(), "was enabled.", NotificationType.INFO);
	}
	public void onDisable() {
		NotificationPublisher.queue(this.getName(), "was disabled.", NotificationType.INFO);
		super.onDisable();
	}
}
