package me.xatzdevelopments.xatz.client.modules;

import org.lwjgl.input.Keyboard;

import me.xatzdevelopments.xatz.client.events.Event;
import me.xatzdevelopments.xatz.client.events.EventPacketRecieve;
import me.xatzdevelopments.xatz.client.events.events2.EventPacketReceive;
import me.xatzdevelopments.xatz.client.module.state.Category;
import me.xatzdevelopments.xatz.module.Module;
import net.minecraft.network.AbstractPacket;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;

public class NoRotationSet extends Module {

	public NoRotationSet() {
		super("NoRotationSet", Keyboard.KEY_NONE, Category.HIDDEN, "Prevents you from drowning in SinglePlayer only!");
	}

	@Override
	public void onDisable() {

		super.onDisable();
	}

	@Override
	public void onEnable() {
		super.onEnable();
	}

	@Override
	public void onUpdate() {

		super.onUpdate();
	}
	
	public void onPacketRecieve(final EventPacketRecieve event) {
        if (event.getPacket() instanceof S08PacketPlayerPosLook) {
            final S08PacketPlayerPosLook poslook = (S08PacketPlayerPosLook)event.getPacket();
            if (mc.thePlayer != null && mc.thePlayer.rotationYaw != -180.0f && mc.thePlayer.rotationPitch != 0.0f) {
                poslook.yaw = mc.thePlayer.rotationYaw;
                poslook.pitch = mc.thePlayer.rotationPitch;
            }
        }
    }
}

