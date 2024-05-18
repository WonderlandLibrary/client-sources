package me.swezedcode.client.module.modules.Player;

import org.lwjgl.input.Keyboard;

import com.darkmagician6.eventapi.EventListener;

import me.swezedcode.client.module.ModCategory;
import me.swezedcode.client.module.Module;
import me.swezedcode.client.utils.events.EventPreMotionUpdates;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemBow;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

public class FastUse extends Module {

	public FastUse() {
		super("FastUse", Keyboard.KEY_NONE, 0xFFFA3232, ModCategory.Player);
	}

	@EventListener
	public void onPre(final EventPreMotionUpdates e) {
		if(this.mc.thePlayer.onGround && this.mc.thePlayer.isEating()) {
			this.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer(true));
		}
	}

}
