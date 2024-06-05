package xyz.vergoclient.modules.impl.movement;

import xyz.vergoclient.event.Event;
import xyz.vergoclient.event.impl.EventReceivePacket;
import xyz.vergoclient.modules.Module;
import xyz.vergoclient.modules.OnEventInterface;
import xyz.vergoclient.settings.BooleanSetting;
import xyz.vergoclient.settings.NumberSetting;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.network.play.server.S27PacketExplosion;

public class Velocity extends Module implements OnEventInterface {

	public Velocity() {
		super("Velocity", Category.MOVEMENT);
	}
	
	public NumberSetting normalHorizontalKnockback = new NumberSetting("Horizontal KB", 0, 0, 100, 1),
			normalVerticalKnockback = new NumberSetting("Vertical KB", 0, 0, 100, 1),
			explosionVerticalKnockback = new NumberSetting("Explosion vertical KB", 0, 0, 100, 1),
			explosionHorizontalKnockback = new NumberSetting("Explosion Horizontal KB", 0, 0, 100, 1);
	public BooleanSetting explosionKnockback = new BooleanSetting("Cancel explosion KB", true);
	
	@Override
	public void loadSettings() {
		addSettings(normalHorizontalKnockback, normalVerticalKnockback);
	}
	
	@Override
	public void onEvent(Event e) {
		
		if (e instanceof EventReceivePacket && e.isPre()) {
			EventReceivePacket event = (EventReceivePacket)e;
			
			if (event.packet instanceof S27PacketExplosion) {
//				e.setCanceled(true);
				S27PacketExplosion packet = (S27PacketExplosion)event.packet;
				packet.motionX *= (explosionHorizontalKnockback.getValueAsFloat() / 100);
				packet.motionY *= (explosionVerticalKnockback.getValueAsFloat() / 100);
				packet.motionZ *= (explosionHorizontalKnockback.getValueAsFloat() / 100);
			}
			else if (event.packet instanceof S12PacketEntityVelocity) {
				S12PacketEntityVelocity packet = (S12PacketEntityVelocity)event.packet;
				if (mc.theWorld.getEntityByID(packet.getEntityID()) != null && mc.theWorld.getEntityByID(packet.getEntityID()) instanceof EntityPlayer && ((EntityPlayer)mc.theWorld.getEntityByID(packet.getEntityID())).isUser()) {
					if (normalHorizontalKnockback.getValueAsDouble() <= 0 && normalVerticalKnockback.getValueAsDouble() <= 0) {
						e.setCanceled(true);
					}else {
						if(normalHorizontalKnockback.getValueAsDouble() >= 101) {
							packet.setMotionX((int) ((((double) packet.getMotionX()) / 100) * normalHorizontalKnockback.getValueAsDouble()));
							packet.setMotionY((int) ((((double) packet.getMotionY()) / 100) * normalVerticalKnockback.getValueAsDouble()));
							packet.setMotionZ((int) ((((double) packet.getMotionZ()) / 100) * normalHorizontalKnockback.getValueAsDouble()));
						}
					}
				}
			}
			
		}
		
	}

}
