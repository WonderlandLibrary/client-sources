/**
 * 
 */
package cafe.kagu.kagu.mods.impl.move;

import cafe.kagu.kagu.eventBus.EventHandler;
import cafe.kagu.kagu.eventBus.Handler;
import cafe.kagu.kagu.eventBus.impl.EventPacketReceive;
import cafe.kagu.kagu.eventBus.impl.EventPacketSend;
import cafe.kagu.kagu.mods.Module;
import cafe.kagu.kagu.settings.impl.BooleanSetting;
import cafe.kagu.kagu.settings.impl.DoubleSetting;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.network.play.server.S27PacketExplosion;

/**
 * @author lavaflowglow
 *
 */
public class ModVelocity extends Module {
	
	public ModVelocity() {
		super("Velocity", Category.MOVEMENT);
		setSettings(cancelS12, s12XZ, s12Y, cancelS27, s27XZ, s27Y);
	}
	
	private BooleanSetting cancelS12 = new BooleanSetting("Cancel Entity Velocity", true);
	private DoubleSetting s12XZ = new DoubleSetting("Entity Velocity XZ %", 1, 0, 1, 0.01).setDependency(cancelS12::isDisabled);
	private DoubleSetting s12Y = new DoubleSetting("Entity Velocity Y %", 1, 0, 1, 0.01).setDependency(cancelS12::isDisabled);
	
	private BooleanSetting cancelS27 = new BooleanSetting("Cancel Explosion Velocity", true);
	private DoubleSetting s27XZ = new DoubleSetting("Explosion Velocity XZ %", 1, 0, 1, 0.01).setDependency(cancelS27::isDisabled);
	private DoubleSetting s27Y = new DoubleSetting("Explosion Velocity Y %", 1, 0, 1, 0.01).setDependency(cancelS27::isDisabled);
	
	@EventHandler
	private Handler<EventPacketReceive> onPacketReceive = e -> {
		if (e.isPost())
			return;
		
		if (e.getPacket() instanceof S12PacketEntityVelocity && ((S12PacketEntityVelocity)e.getPacket()).getEntityID() == mc.thePlayer.getEntityId()) {
			if (cancelS12.isEnabled()) {
				e.cancel();
				return;
			}
			S12PacketEntityVelocity s12 = (S12PacketEntityVelocity)e.getPacket();
			s12.setMotionX((int) (((double)s12.getMotionX() / 8000 * s12XZ.getValue()) * 8000));
			s12.setMotionY((int) (((double)s12.getMotionY() / 8000 * s12Y.getValue()) * 8000));
			s12.setMotionZ((int) (((double)s12.getMotionZ() / 8000 * s12XZ.getValue()) * 8000));
		}
		else if (e.getPacket() instanceof S27PacketExplosion) {
			if (cancelS27.isEnabled()) {
				e.cancel();
				return;
			}
			S27PacketExplosion s27 = (S27PacketExplosion)e.getPacket();
			s27.setMotionX((float) (s27.getMotionX() * s27XZ.getValue()));
			s27.setMotionY((float) (s27.getMotionY() * s27Y.getValue()));
			s27.setMotionZ((float) (s27.getMotionZ() * s27XZ.getValue()));
		}
		
	};
	
}
