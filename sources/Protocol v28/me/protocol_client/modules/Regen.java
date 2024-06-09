package me.protocol_client.modules;

import me.protocol_client.Wrapper;
import me.protocol_client.module.Category;
import me.protocol_client.module.Module;
import net.minecraft.network.play.client.C03PacketPlayer;

import org.lwjgl.input.Keyboard;

import darkmagician6.EventManager;
import darkmagician6.EventTarget;
import events.EventPreMotionUpdates;

public class Regen extends Module {

	public Regen() {
		super("Regen", "regen", 0, Category.COMBAT, new String[] { "" });
	}
	public void onEnable(){
		EventManager.register(this);
	}
	public void onDisable(){
		EventManager.unregister(this);
	}
	@EventTarget
	public void onEvent(EventPreMotionUpdates event) {
			if (!mc.thePlayer.capabilities.isCreativeMode && mc.thePlayer.getFoodStats().getFoodLevel() > 17 && mc.thePlayer.getHealth() < 20 && mc.thePlayer.getHealth() != 0 || mc.thePlayer.noClip == true && !mc.thePlayer.capabilities.isCreativeMode && mc.thePlayer.getFoodStats().getFoodLevel() > 17 && mc.thePlayer.getHealth() < 20 && mc.thePlayer.getHealth() != 0)
				for (int i = 0; i < 500; i++)
					if (Wrapper.getPlayer().onGround) {
						Wrapper.sendPacket(new C03PacketPlayer(false));
					}
	}
}
