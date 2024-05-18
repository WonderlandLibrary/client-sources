package me.swezedcode.client.module.modules.Player;

import org.lwjgl.input.Keyboard;

import com.darkmagician6.eventapi.EventListener;

import me.swezedcode.client.module.ModCategory;
import me.swezedcode.client.module.Module;
import me.swezedcode.client.utils.events.EventReadPacket;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;

public class NoRotate extends Module {

	public NoRotate() {
		super("NoRotate", Keyboard.KEY_NONE, 0xFF35DE7B, ModCategory.Player);
		setDisplayName("No Rotate");
	}

	@EventListener
	public void onPacket(EventReadPacket e) {
		if ((e.getState() == e.getState().POST)) {
			if ((e.getPacket() instanceof S08PacketPlayerPosLook)) {
				S08PacketPlayerPosLook packet = (S08PacketPlayerPosLook) e.getPacket();
				if ((this.mc.thePlayer != null) && (this.mc.thePlayer.rotationYaw != -180.0F)
						&& (this.mc.thePlayer.rotationPitch != 0.0F)) {
					packet.field_148936_d = this.mc.thePlayer.rotationYaw;
					packet.field_148937_e = this.mc.thePlayer.rotationPitch;
				}
			}
		}
	}

}
