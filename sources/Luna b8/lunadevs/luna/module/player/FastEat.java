package lunadevs.luna.module.player;

import org.lwjgl.input.Keyboard;

import lunadevs.luna.category.Category;
import lunadevs.luna.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemFood;
import net.minecraft.network.play.client.C03PacketPlayer;

public class FastEat extends Module {

	public FastEat() {
		super("FastEat", Keyboard.KEY_NONE, Category.PLAYER, true);
	}

	@Override
	public void onUpdate() {
		if (!this.isEnabled) return;
	    mc.rightClickDelayTimer = 1;
	    if (mc.thePlayer.isUsingItem()) {
	      mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + 0.01D, mc.thePlayer.posZ, false));
	    }
		super.onUpdate();
	}

	@Override
	public void onDisable() {
		super.onDisable();
	}

	@Override
	public String getValue() {
		return "NCP";
	}

}
