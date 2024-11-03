package net.augustus.modules.misc;

import java.awt.Color;

import net.augustus.Augustus;
import net.augustus.events.EventTick;
import net.augustus.modules.Categorys;
import net.augustus.modules.Module;
import net.augustus.notify.GeneralNotifyManager;
import net.augustus.notify.NotificationType;
import net.augustus.utils.HackerDetector.Check;
import net.lenni0451.eventapi.reflection.EventTarget;
import net.minecraft.entity.player.EntityPlayer;

public class HackerDetector extends Module{
	
	public HackerDetector() {
		super("HackerDetector", Color.green, Categorys.MISC);
	}

	@EventTarget
	public void onEventTick(EventTick e) {
		if(this.isToggled()) {
			for(EntityPlayer plr : mc.theWorld.playerEntities) {
				performCheck(plr);
			}
		}
	}
	
	public void performCheck(EntityPlayer player) {
		for(Check c : Augustus.getInstance().getChecksManager().checks) {
			if(c.isEnabled()) {
				boolean failed = c.runCheck(player);
				if(failed) {
					String reason = player.getName() + " failed " + c.getName() + " check.";
					if(c.getName() == "Teleport") {
						reason = player.getName() + " teleported.";
				        GeneralNotifyManager.addNotification(reason, NotificationType.Info);
				        return;
					}
					this.log(reason);
				}
			}
		}
	}
	
	public void log(String reason) {
        GeneralNotifyManager.addNotification(reason, NotificationType.Error);
	}

}
