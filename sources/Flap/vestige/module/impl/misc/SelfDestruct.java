package vestige.module.impl.misc;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.Timer;
import org.lwjgl.opengl.Display;
import vestige.Flap;
import vestige.module.Category;
import vestige.module.Module;

public class SelfDestruct extends Module {
   public SelfDestruct() {
      super("Self Destruct", Category.ULTILITY);
   }

   public void onEnable() {
      Display.setTitle("Minecraft 1.8.9");
      Flap.instance.getModuleManager().modules.forEach((m) -> {
         m.setEnabled(false);
      });
      Flap.instance.getPacketDelayHandler().stopAll();
      Flap.instance.getPacketBlinkHandler().stopAll();
      Flap.instance.getCameraHandler().setFreelooking(false);
      Flap.instance.getSlotSpoofHandler().stopSpoofing();
      mc.displayGuiScreen((GuiScreen)null);
      Timer var10000 = mc.timer;
      var10000.timerSpeed = 1.0F;
      Flap.instance.setDestructed(true);
   }
}
