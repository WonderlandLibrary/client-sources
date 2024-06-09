package lunadevs.luna.module.player;

import lunadevs.luna.category.Category;
import lunadevs.luna.module.Module;
import lunadevs.luna.option.Option;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.client.C03PacketPlayer;

public class FastEatModes extends Module {

	public static boolean active;
	@Option.Op(name = "NCP")
	public static boolean NCP = true;
	@Option.Op(name = "AAC 3.2.0")
	public static boolean AAC = false;

	public FastEatModes() {
		super("FastEat", 0, Category.PLAYER, true);
	}

	public static String modname;

	@Override
	public void onUpdate() {
		if (!this.isEnabled)
			return;
		if (this.NCP == true) {
			NCP();
			if (this.AAC == true) {
				this.AAC = false;

			}
			modname = "NCP";
		}else if (this.AAC == true) {
			AAC();
			if (this.NCP == true) {
				this.NCP = false;

			}
			modname = "Mode 2";
			

		super.onUpdate();
	}}

	private void NCP() {
		if (this.NCP == true) {
			this.modname = "NCP";
			if(z.p().isEating()){
			mc.rightClickDelayTimer = 1;
		    if (mc.thePlayer.isUsingItem()) {
		      mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + 0.01D, mc.thePlayer.posZ, false));
		    }
	}}
}
	
	private void AAC() {
		if (this.AAC == true) {
			this.modname = "AAC 3.2.0";
			if(z.p().isEating()){
				z.mc().timer.timerSpeed = 1.2092F;
			}else if (!z.p().isEating()){
				z.mc().timer.timerSpeed = 1.0F;
			}
	}
}
			
	@Override
	public void onDisable() {
		super.onDisable();
		mc.thePlayer.stepHeight = 0.6f;
		z.mc().timer.timerSpeed = 1.0F;
		active = false;
	}

	@Override
	public void onEnable() {
		active = true;
		super.onEnable();
	}

	@Override
	public String getValue() {

		return modname;
	}

}
