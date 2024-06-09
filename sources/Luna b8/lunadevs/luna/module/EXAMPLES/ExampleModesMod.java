package lunadevs.luna.module.EXAMPLES;

import lunadevs.luna.category.Category;
import lunadevs.luna.module.Module;
import lunadevs.luna.option.Option;
import net.minecraft.client.Minecraft;

public class ExampleModesMod extends Module {

	public static boolean active;
	@Option.Op(name = "Mode 1")
	public static boolean Mode1 = true;
	@Option.Op(name = "Mode 2")
	public static boolean Mode2 = false;

	public ExampleModesMod() {
		super("ExampleModesMod", 0, Category.MOVEMENT, true);
	}

	public static String modname;

	@Override
	public void onUpdate() {
		if (!this.isEnabled)
			return;
		if (this.Mode1 == true) {
			//If you want a glide, put the motionY code here.//\\
			mode1();
			if (this.Mode2 == true) {
				this.Mode2 = false;

			}
			modname = "Mode 1";
		}else if (this.Mode2 == true) {
			//\\
			mode2();
			if (this.Mode1 == true) {
				this.Mode1 = false;

			}
			modname = "Mode 2";
			

		super.onUpdate();
	}}

	private void mode1() {
		if (this.Mode1 == true) {
			this.modname = "Mode 1";
			/** The code you want. */
	}
}
	
	private void mode2() {
		if (this.Mode2 == true) {
			this.modname = "Mode 2";
			/** The code you want. */
	}
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
