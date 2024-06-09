package me.protocol_client.modules;

import me.protocol_client.Wrapper;
import me.protocol_client.module.Category;
import me.protocol_client.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemFood;
import net.minecraft.network.play.client.C03PacketPlayer;

import org.lwjgl.input.Keyboard;

import darkmagician6.EventManager;
import darkmagician6.EventTarget;
import events.EventPreMotionUpdates;

public class FastEat extends Module {

	public FastEat() {
		super("Fast Eat", "fasteat", Keyboard.KEY_I, Category.PLAYER, new String[] { "" });
	}
	public void onEnable(){
		EventManager.register(this);
	}
	public void onDisable(){
		EventManager.unregister(this);
	}
	@EventTarget
	public void onEvent(EventPreMotionUpdates event) {
			if (Minecraft.getMinecraft().thePlayer.getHealth() > 0 && Minecraft.getMinecraft().thePlayer.inventory.getCurrentItem() != null && Minecraft.getMinecraft().thePlayer.inventory.getCurrentItem().getItem() instanceof ItemFood && Minecraft.getMinecraft().thePlayer.getFoodStats().needFood() && Minecraft.getMinecraft().gameSettings.keyBindUseItem.pressed)
				for (int i = 0; i < 100; i++)
					if (Wrapper.getPlayer().onGround) {
						Wrapper.sendPacket(new C03PacketPlayer(false));
		}
	}
}
