package lunadevs.luna.module.combat;

import org.lwjgl.input.Keyboard;

import lunadevs.luna.category.Category;
import lunadevs.luna.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.client.C03PacketPlayer;

public class PacketFly extends Module {

	public PacketFly() {
		super("PacketFly", Keyboard.KEY_NONE, Category.COMBAT, false);
	}

	@Override
	public void onUpdate() {
		if (!this.isEnabled)
			return;
		if (!Minecraft.thePlayer.capabilities.isCreativeMode)
			for (int i = 0; i < 1; i++)
				mc.thePlayer.capabilities.isFlying=true;
		super.onUpdate();
	}

	@Override
	public void onEnable() {
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
