package me.rich.module.movement;

import me.rich.event.EventTarget;
import me.rich.event.events.EventMove;
import me.rich.event.events.EventUpdate;
import me.rich.module.Category;
import me.rich.module.Feature;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;

public class Eagle extends Feature {

	public Eagle() {
		super("Eagle", 0, Category.MOVEMENT);
	}

	@EventTarget
	public void blob(EventUpdate blob) {
		mc.gameSettings.keyBindSneak.pressed = false;
		if (mc.world.getBlockState(new BlockPos(mc.player.posX, mc.player.posY - 0.5D, mc.player.posZ)).getBlock() == Blocks.AIR) {
			mc.gameSettings.keyBindSneak.pressed = true;
		}
	}
}
