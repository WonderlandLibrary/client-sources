package me.rich.module.misc;

import me.rich.Main;
import me.rich.event.EventTarget;
import me.rich.event.events.EventPreMotionUpdate;
import me.rich.event.events.EventUpdate;
import me.rich.module.Category;
import me.rich.module.Feature;
import me.rich.notifications.NotificationPublisher;
import me.rich.notifications.NotificationType;
import net.minecraft.network.play.client.CPacketEntityAction;

public class Disabler extends Feature {
    public Disabler() {
        super("Disabler", 0, Category.MISC);
    }

    @EventTarget
    public void onUpdate(EventPreMotionUpdate event){
        if(mc.player.ticksExisted % 4 == 0){
            mc.getConnection().sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.START_FALL_FLYING));
        }
    }
    
    public void onEnable() {
    	Main.msg("Working on MST NetWork", true);
        super.onEnable();
        NotificationPublisher.queue(this.getName(), "was enabled.", NotificationType.INFO);
    }

    public void onDisable() {
        NotificationPublisher.queue(this.getName(), "was disabled.", NotificationType.INFO);
        super.onDisable();
    }
}