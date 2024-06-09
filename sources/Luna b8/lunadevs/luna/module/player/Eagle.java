package lunadevs.luna.module.player;

import org.lwjgl.input.Keyboard;

import lunadevs.luna.category.Category;
import lunadevs.luna.main.Luna;
import lunadevs.luna.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.BlockPos;

public class Eagle extends Module {

	public Eagle() {
		super("Eagle", Keyboard.KEY_NONE, Category.PLAYER, false);
	}

	@Override
	public void onUpdate() {
		if (!this.isEnabled) return;
		 final BlockPos bp = new BlockPos(Minecraft.thePlayer.posX, Minecraft.thePlayer.posY - 1.0, Minecraft.thePlayer.posZ);
	        if (Minecraft.thePlayer.fallDistance <= 4.0f) {
	            if (Minecraft.theWorld.getBlockState(bp).getBlock() != Blocks.air) {
	                this.mc.gameSettings.keyBindSneak.pressed = false;
	            }
	            else {
	                this.mc.gameSettings.keyBindSneak.pressed = true;
	            }
	            super.onUpdate();
	        }
	    }

	
	@Override
	public void onEnable() {
		super.onEnable();
	}
	@Override
	public void onDisable() {
		super.onDisable();
	}

	@Override
	public String getValue() {
		return null;
	}

}
