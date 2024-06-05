package zamorozka.modules.PLAYER;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.Minecraft;
import zamorozka.module.Category;
import zamorozka.module.Module;

public class Parkour extends Module {

	public Parkour() {
		super("Parkour", Keyboard.KEY_NONE, Category.PLAYER);
	}
	
	public void onUpdate()
	  {
	  if (getState()) {
	
	if (Minecraft.getMinecraft().player.onGround && !Minecraft.getMinecraft().player.isSneaking()
			&& !Minecraft.getMinecraft().gameSettings.keyBindJump.isPressed()
			&& Minecraft.getMinecraft().world
					.getCollisionBoxes(Minecraft.getMinecraft().player, Minecraft.getMinecraft().player
							.getEntityBoundingBox().offset(0, -0.5, 0).expand(-0.001, 0, -0.001))
					.isEmpty()) {
		Minecraft.getMinecraft().player.jump();
	}
	  }
	  }
}