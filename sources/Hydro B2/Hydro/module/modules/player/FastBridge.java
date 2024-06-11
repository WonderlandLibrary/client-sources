package Hydro.module.modules.player;

import Hydro.event.Event;
import Hydro.event.events.EventUpdate;
import Hydro.module.Category;
import Hydro.module.Module;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;

public class FastBridge extends Module {
	
	public static boolean place;

	public FastBridge() {
		super("FastBridge", 0, true, Category.PLAYER, "Sneaks at the edge of a block");
	}
	
	@Override
	public void onDisable() {
		mc.gameSettings.keyBindSneak.pressed = false;
	}
	
	@Override
	public void onEvent(Event e) {
		if(e instanceof EventUpdate) {
	        BlockPos at = new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - 2, mc.thePlayer.posZ);
			if(mc.theWorld.getBlockState(at.add(0, 1, 0)).getBlock() == Blocks.air) {
				mc.gameSettings.keyBindSneak.pressed = true;
				place = true;
			}else {
				mc.gameSettings.keyBindSneak.pressed = false;
				place = false;
			}
		}
	}

}
