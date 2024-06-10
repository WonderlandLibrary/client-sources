package Snipeware.module.impl.combat;

import org.lwjgl.input.Keyboard;

import Snipeware.Client;
import Snipeware.api.annotations.Handler;
import Snipeware.events.packet.EventPacketReceive;
import Snipeware.gui.notification.Notifications;
import Snipeware.module.Module;
import Snipeware.value.impl.BooleanValue;
import Snipeware.value.impl.EnumValue;
import Snipeware.value.impl.NumberValue;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.network.play.server.S27PacketExplosion;

public class AntiVelocity extends Module {
	
	private EnumValue<Mode> velocityMode = new EnumValue<>("Velocity Mode", Mode.Cancel);
	
	private NumberValue<Integer> vertical = new NumberValue<>("Vertical", 100, 0, 100);
	
	private NumberValue<Integer> horizontal = new NumberValue<>("Horizontal", 0, 0, 100);
	
	
	
    private double motionX;
    private double motionZ;
    private double motionY;
    
	
	public AntiVelocity() {
		super("Velocity", 0, ModuleCategory.COMBAT);
		addValues(velocityMode, vertical ,horizontal);
	}
	
	public void onEnable() {
		super.onEnable();
	}
	
	public void onDisable() {
		super.onDisable();
	}
	
	@Handler
	public void onReceivePacket(final EventPacketReceive event) {
		EntityPlayerSP player = mc.thePlayer;
		switch (velocityMode.getValue()) {
		case Cancel:
			setSuffix("Cancel");
			if (event.getPacket() instanceof S12PacketEntityVelocity) {
				final S12PacketEntityVelocity s12 = (S12PacketEntityVelocity) event.getPacket();
				if (player.getEntityId() == s12.getEntityID()) {
					event.setCancelled(true);
				}
			}
			else if (event.getPacket() instanceof S27PacketExplosion) {
				event.setCancelled(true);
			}
			break;
		case Customizable:
			setSuffix(vertical.getValueAsString() + " " + horizontal.getValueAsString());
			final S12PacketEntityVelocity packet = (S12PacketEntityVelocity) event.getPacket();
            int vertical = this.vertical.getValue();
            int horizontal = this.horizontal.getValue();
            if (vertical != 0 || horizontal != 0) {
                packet.setX(horizontal * packet.getMotionX() / 100);
                packet.setY(vertical * packet.getMotionY() / 100);
                packet.setZ(horizontal * packet.getMotionZ() / 100);
            } else {
                event.setCancelled(true);
            }
            if (event.getPacket() instanceof S27PacketExplosion) {
            	event.setCancelled(true);
            }
			break;
			
		case Redesky:
			
			
			
			setSuffix("Redesky");
			if (mc.thePlayer.hurtTime == 9) {
				mc.thePlayer.setInWeb();
			}
		
		
		
		
	        
	     
			
		 
		 break;
		
		}
	
	}
	
	
	
	private enum Mode {
		Cancel, Customizable, Redesky;
	}
}
