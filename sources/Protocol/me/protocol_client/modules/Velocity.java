package me.protocol_client.modules;

import me.protocol_client.Wrapper;
import me.protocol_client.module.Category;
import me.protocol_client.module.Module;
import me.protocol_client.thanks_slicky.properties.ClampedValue;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import darkmagician6.EventManager;
import darkmagician6.EventTarget;
import events.EventPacketRecieve;

public class Velocity extends Module{

	public Velocity() {
		super("Velocity", "velocity", 0, Category.COMBAT, new String[]{"dsdfsdfsdfsdghgh"});
	}
	private final ClampedValue<Float> percent = new ClampedValue<>("velocity_percent", 0f, 0f, 100f);
	@EventTarget
	public void onPacket(EventPacketRecieve packet){
		setDisplayName("Velocity [" + percent.getValue().longValue() + "%]");
		if(packet.getPacket() instanceof S12PacketEntityVelocity){
			if(percent.getValue() < 3){
			packet.setCancelled(true);
			percent.setValue(0f);
			percent.setSliderX(0f);
			}else{
				 S12PacketEntityVelocity packets = (S12PacketEntityVelocity)packet.getPacket();
			        if (Wrapper.getWorld().getEntityByID(packets.field_149417_a) == Wrapper.getPlayer())
			        {
			        		double mult = (percent.getValue() / 100);
			          if (mult == 0.0D)
			          {
			            packet.setCancelled(true);
			          }
			          else
			          {
			            S12PacketEntityVelocity tmp85_84 = packets;tmp85_84.field_149415_b = ((int)(tmp85_84.field_149415_b * mult)); S12PacketEntityVelocity 
			              tmp98_97 = packets;tmp98_97.field_149416_c = ((int)(tmp98_97.field_149416_c * mult)); S12PacketEntityVelocity 
			              tmp111_110 = packets;tmp111_110.field_149414_d = ((int)(tmp111_110.field_149414_d * mult));
			          }
			        }
			      }
			    }
			  }
	public void onDisable(){
		EventManager.unregister(this);
	}
	public void onEnable(){
		EventManager.register(this);
	}
}
