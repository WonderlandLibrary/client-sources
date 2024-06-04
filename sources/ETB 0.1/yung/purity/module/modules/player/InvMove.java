package yung.purity.module.modules.player;

import java.awt.Color;
import java.util.Random;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import org.lwjgl.input.Keyboard;
import yung.purity.api.EventHandler;
import yung.purity.api.events.EventRender2D;
import yung.purity.module.Module;
import yung.purity.module.ModuleType;
import yung.purity.utils.RotationUtil;





public class InvMove
  extends Module
{
  public InvMove()
  {
    super("Invmove", new String[] { "inventorywalk", "invwalk", "inventorymove" }, ModuleType.Player);
    setColor(new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255)).getRGB());
  }
  

  @EventHandler
  private void onRender(EventRender2D e)
  {
    if ((mc.currentScreen != null) && (!(mc.currentScreen instanceof GuiChat)))
    {
      if (Keyboard.isKeyDown(200)) {
        RotationUtil.pitch(RotationUtil.pitch() - 2.0F);
      }
      
      if (Keyboard.isKeyDown(208)) {
        RotationUtil.pitch(RotationUtil.pitch() + 2.0F);
      }
      
      if (Keyboard.isKeyDown(203)) {
        RotationUtil.yaw(RotationUtil.yaw() - 2.0F);
      }
      
      if (Keyboard.isKeyDown(205)) {
        RotationUtil.yaw(RotationUtil.yaw() + 2.0F);
      }
    }
  }
}
