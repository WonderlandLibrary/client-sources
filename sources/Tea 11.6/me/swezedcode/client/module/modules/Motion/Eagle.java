package me.swezedcode.client.module.modules.Motion;

import org.lwjgl.input.Keyboard;

import com.darkmagician6.eventapi.EventListener;

import me.swezedcode.client.module.ModCategory;
import me.swezedcode.client.module.Module;
import me.swezedcode.client.utils.events.EventPreMotionUpdates;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;

public class Eagle extends Module {

	public Eagle() {
		super("Eagle", Keyboard.KEY_H, 0xFF555555, ModCategory.Motion);
	}

	@EventListener
	public void onEagle(EventPreMotionUpdates e) {
		final BlockPos blockPos = new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - 1.0D, mc.thePlayer.posZ);
		this.mc.rightClickDelayTimer = 0;
		if (this.mc.thePlayer.fallDistance <= 2.0F) {
			if (this.mc.theWorld.getBlockState(blockPos).getBlock() != Blocks.air) {
				this.mc.gameSettings.keyBindSneak.pressed = false;
			} else {
				this.mc.gameSettings.keyBindSneak.pressed = true;
			}
		}
	}

	public void onDisable() {
		this.mc.rightClickDelayTimer = 6;
		this.mc.timer.timerSpeed = 1.0F;
	}
}
