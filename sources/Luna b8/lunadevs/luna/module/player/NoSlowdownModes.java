package lunadevs.luna.module.player;

import lunadevs.luna.category.Category;
import lunadevs.luna.module.Module;
import lunadevs.luna.option.Option;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

public class NoSlowdownModes extends Module {

	public static boolean active;
	@Option.Op(name = "NCP")
	public static boolean Mode1 = true;
	@Option.Op(name = "Client")
	public static boolean Mode2 = false;

	public NoSlowdownModes() {
		super("NoSlow", 0, Category.PLAYER, true);
	}

	public static String modname;

	@Override
	public void onUpdate() {
		if (!this.isEnabled)
			return;
		if (this.Mode1 == true) {
			
			mode1();
			if (this.Mode1 == true) {
				this.Mode1 = false;

			}
			modname = "NCP";
		}else if (this.Mode2 == true) {
			
			mode2();
			if (this.Mode1 == true) {
				this.Mode1 = false;

			}
			modname = "Client";
			

		super.onUpdate();
	}}

	private void mode1() {
		if (this.Mode1 == true) {
			this.modname = "NCP";
	}
}
	
	private void mode2() {
		if (this.Mode2 == true) {
			this.modname = "Client";
			if(z.p().isEating()){
			Minecraft.getMinecraft().getNetHandler().addToSendQueue(
		  	        new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, 
		  	        new BlockPos(0, 0, 0), EnumFacing.DOWN));
	}}
}
			
	@Override
	public void onDisable() {
		super.onDisable();
		mc.thePlayer.stepHeight = 0.6f;
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
