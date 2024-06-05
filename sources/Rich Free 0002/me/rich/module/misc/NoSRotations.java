package me.rich.module.misc;

import me.rich.event.EventTarget;
import me.rich.event.events.EventReceivePacket;
import me.rich.module.Category;
import me.rich.module.Feature;
import me.rich.notifications.NotificationPublisher;
import me.rich.notifications.NotificationType;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.SPacketPlayerPosLook;

public class NoSRotations extends Feature {

	public NoSRotations() {
		super("NoSRotations", 0, Category.MISC);
	}
	
    @EventTarget
    public void onPacket(EventReceivePacket event){
        if(event.getPacket() instanceof SPacketPlayerPosLook){
            Packet packet = event.getPacket();
            ((SPacketPlayerPosLook)packet).yaw = mc.player.rotationYaw;
            ((SPacketPlayerPosLook)packet).pitch = mc.player.rotationPitch;
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
