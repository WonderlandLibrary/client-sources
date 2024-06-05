package me.rich.module.misc;

import clickgui.setting.Setting;
import me.rich.Main;
import me.rich.event.EventTarget;
import me.rich.event.events.EventChatMessage;
import me.rich.helpers.friend.FriendManager;
import me.rich.module.Category;
import me.rich.module.Feature;
import me.rich.notifications.NotificationPublisher;
import me.rich.notifications.NotificationType;
import net.minecraft.entity.player.EntityPlayer;

public class AutoAccept extends Feature {

	public AutoAccept() {
		super("AutoAccept", 0, Category.MISC);
		Main.settingsManager.rSetting(new Setting("Only Friends", this, true));
	}

	@EventTarget
	public void onReceiveChat(EventChatMessage event) {
		for (EntityPlayer entity : mc.world.playerEntities) {
			if(Main.settingsManager
							.getSettingByName(Main.moduleManager.getModule(AutoAccept.class), "Only Friends")
							.getValBoolean()) {
			if ((event.getMessage().contains("/tpyes") || event.getMessage().contains("/tpaccept"))
					&& FriendManager.isFriend(entity.getName())) {
				if (timerHelper.check(500)) {
					mc.player.sendChatMessage("/tpaccept");
					timerHelper.resetwatermark();
				}
			}
			} else {
				if ((event.getMessage().contains("/tpyes") || event.getMessage().contains("/tpaccept"))) {
					if (timerHelper.check(500)) {
						mc.player.sendChatMessage("/tpaccept");
						timerHelper.resetwatermark();
					}
				}
			}
		}
	}
    @Override
    public void onEnable() {
        super.onEnable();
        NotificationPublisher.queue(getName(), "was enabled.", NotificationType.INFO);
    }

    public void onDisable() {
        NotificationPublisher.queue(getName(), "was disabled.", NotificationType.INFO);
        super.onDisable();
    }
}
