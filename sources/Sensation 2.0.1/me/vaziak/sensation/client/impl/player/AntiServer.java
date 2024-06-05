package me.vaziak.sensation.client.impl.player;

import me.vaziak.sensation.client.api.Category;
import me.vaziak.sensation.client.api.Module;
import me.vaziak.sensation.client.api.event.annotations.Collect;
import me.vaziak.sensation.client.api.event.events.ProcessPacketEvent;
import me.vaziak.sensation.client.api.property.impl.StringsProperty;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.network.play.server.S2EPacketCloseWindow;

/**
 * @author Spec
 */
public class AntiServer extends Module {
	  private StringsProperty prop_mode = new StringsProperty("Anti Force", "Prevent server from forcing things on the client", null, true, false, new String[]{"Rotate", "Close Window", "Open Window"});
	  
    public AntiServer() {
        super("Anti Server", Category.PLAYER);
        registerValue(prop_mode);
    }


    @Collect
    public void onProcessPacket(ProcessPacketEvent event) {
    	 
        if (event.getPacket() instanceof S2EPacketCloseWindow && prop_mode.getValue().get("Close Window")) {
        	event.setCancelled(true);
        }
        if (event.getPacket() instanceof S08PacketPlayerPosLook && prop_mode.getValue().get("Rotate")) {
            S08PacketPlayerPosLook packet = (S08PacketPlayerPosLook) event.getPacket();
            packet.setYaw(mc.thePlayer.rotationYaw);
            packet.setPitch(mc.thePlayer.prevRotationPitch);
        }
    }
}
