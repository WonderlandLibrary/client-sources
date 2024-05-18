package me.protocol_client.modules;

import me.protocol_client.Wrapper;
import me.protocol_client.module.Category;
import me.protocol_client.module.Module;
import me.protocol_client.thanks_slicky.properties.Value;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemBlock;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

import org.lwjgl.input.Keyboard;

import darkmagician6.EventManager;
import darkmagician6.EventTarget;
import events.EventPreMotionUpdates;

public class Spider extends Module {

	public Spider() {
		super("Spider", "spider", Keyboard.KEY_NONE, Category.MOVEMENT, new String[] { "spider" });
	}

	public final Value<Boolean>	fast	= new Value<>("spider_fast", false);

	public void onEnable() {
		EventManager.register(this);
	}

	public void onDisable() {
		EventManager.unregister(this);
	}

	@EventTarget
	public void onEvent(EventPreMotionUpdates event) {
		setDisplayName(getName() + (!fast.getValue() ? " [Normal]" : " [Fast]"));
		if (Minecraft.getMinecraft().thePlayer.isCollidedHorizontally && !mc.thePlayer.isOnLadder()) {
			Minecraft.getMinecraft().thePlayer.motionY = !fast.getValue() ? 0.4 : 0.4f;
		}
	}
}
