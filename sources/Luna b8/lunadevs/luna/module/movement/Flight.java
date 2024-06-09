package lunadevs.luna.module.movement;

import lunadevs.luna.category.Category;
import lunadevs.luna.module.Module;
import lunadevs.luna.option.Option;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.server.S2EPacketCloseWindow;

public class Flight extends Module {

	public static boolean active;
	@Option.Op(name = "Watchdog")
	public static boolean Mode1 = true;
	@Option.Op(name = "Vanilla")
	public static boolean Mode2 = false;

	public Flight() {
		super("Flight", 0, Category.MOVEMENT, true);
	}

	public static String modname;

	@Override
	public void onUpdate() {
		if (!this.isEnabled)
			return;
		if (this.Mode1 == true) {
			Minecraft.thePlayer.motionY = 0.005f;
			mode1();
			if (this.Mode2 == true) {
				this.Mode2 = false;

			}
			modname = "Watchdog";
		}else if (this.Mode2 == true) {
			//Check mode2\\
			mode2();
			if (this.Mode1 == true) {
				this.Mode1 = false;

			}
			modname = "Vanilla";
			

		super.onUpdate();
	}}

	private void mode1() {
		if (this.Mode1 == true) {
			this.modname = "Watchdog";
			if (mc.gameSettings.keyBindJump.isKeyDown()) {
				Minecraft.thePlayer.setPosition(Minecraft.thePlayer.posX, Minecraft.thePlayer.posY + 0.4f,
						Minecraft.thePlayer.posZ);

				Minecraft.thePlayer.motionY = 0.005f;
			}
				else if (mc.gameSettings.keyBindSneak.isKeyDown()) {
						Minecraft.thePlayer.setPosition(Minecraft.thePlayer.posX, Minecraft.thePlayer.posY - 0.4f,
								Minecraft.thePlayer.posZ);
			}
		}
	}
	
	private void mode2() {
		if (this.Mode2 == true) {
			this.modname = "Vanilla";
			z.p().onGround = true;
			z.p().capabilities.isFlying = true;
	}
}
			
	@Override
	public void onDisable() {
		z.p().onGround = false;
		z.p().capabilities.isFlying = false;
		active = false;
		super.onDisable();
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
