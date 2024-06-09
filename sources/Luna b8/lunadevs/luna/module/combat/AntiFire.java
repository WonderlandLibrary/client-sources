package lunadevs.luna.module.combat;

import org.lwjgl.input.Keyboard;

import lunadevs.luna.category.Category;
import lunadevs.luna.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.client.C03PacketPlayer;

public class AntiFire extends Module {

	public AntiFire() {
		super("AntiFire", Keyboard.KEY_NONE, Category.COMBAT, false);
	}

	@Override
	public void onUpdate() {
		if (!this.isEnabled)
			return;
		if (!Minecraft.thePlayer.capabilities.isCreativeMode && Minecraft.thePlayer.onGround
				&& Minecraft.thePlayer.isBurning())
			for (int i = 0; i < 100; i++)
				Minecraft.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer());
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
