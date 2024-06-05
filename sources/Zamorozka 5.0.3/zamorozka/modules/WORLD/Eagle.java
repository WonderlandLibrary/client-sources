package zamorozka.modules.WORLD;

import org.lwjgl.input.Keyboard;

import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import zamorozka.module.Category;
import zamorozka.module.Module;

public class Eagle extends Module {

	public Eagle() {
		super("Eagle",Keyboard.KEY_NONE, Category.WORLD);
	}
	
	public void onUpdate() {
		if (this.getState()) {
			final BlockPos blockPos = new BlockPos(mc.player.posX, mc.player.posY - 1.0D, mc.player.posZ);
			if (this.mc.player.fallDistance <= 2.0F) {
				if (this.mc.world.getBlockState(blockPos).getBlock() != Blocks.AIR) {
					if(this.mc.gameSettings.keyBindSneak.pressed = false) {
						
					}
					}else {
						
					if(this.mc.gameSettings.keyBindSneak.pressed = true) {
						
					}
				}
			}
		}
	}
}