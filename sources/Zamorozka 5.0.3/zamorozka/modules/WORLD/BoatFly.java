package zamorozka.modules.WORLD;

import net.minecraft.network.play.client.CPacketPlayer;
import zamorozka.main.Zamorozka;
import zamorozka.module.Category;
import zamorozka.module.Module;

public class BoatFly extends Module
{
public BoatFly()
{
  super("BoatFly", 0, Category.WORLD);
}

public void onUpdate()
{
  if (getState()) {
	  BoatFly();
  }
}

private void BoatFly()
{
 if (Zamorozka.player().isRiding()) {
      float dir = mc.player.rotationYaw + ((mc.player.moveForward < 0) ? 180 : 0) + ((mc.player.moveStrafing > 0) ? (-90F * ((mc.player.moveForward < 0) ? -.5F : ((mc.player.moveForward > 0) ? .4F : 1F))) : 0);
      float xDir = (float)Math.cos((dir + 90F) * Math.PI / 180);
      float zDir = (float)Math.sin((dir + 90F) * Math.PI / 180);
      Zamorozka.player().getRidingEntity().motionY = (mc.gameSettings.keyBindJump.pressed ? 0.5D : 0.0D);

 }
}
}

