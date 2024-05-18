package me.hexxed.mercury.modules;

import me.hexxed.mercury.modulebase.Module;
import me.hexxed.mercury.modulebase.ModuleCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.network.Packet;
import org.lwjgl.input.Keyboard;

public class Speed
  extends Module
{
  private int timerDelay;
  private int groundTimer;
  int speed = 0;
  private int motionDelay;
  
  public Speed() {
    super("Speed", 50, true, ModuleCategory.MOVEMENT);
  }
  
  private boolean air = false;
  
  public void onPreMotionUpdate()
  {
    if ((Keyboard.isKeyDown(17)) || (Keyboard.isKeyDown(31)) || (Keyboard.isKeyDown(30)) || (Keyboard.isKeyDown(32))) {
      if (mc.thePlayer.onGround) {
        motionDelay += 1;
        motionDelay %= 2;
        if (motionDelay == 1) {
          mc.thePlayer.motionX *= 2.58D;
          mc.thePlayer.motionZ *= 2.58D;
        } else {
          mc.thePlayer.motionX /= 1.5D;
          mc.thePlayer.motionZ /= 1.5D;
        }
        mc.thePlayer.moveStrafing *= 0.0F;
        
        mc.thePlayer.motionY = 0.001D;
      }
      mc.thePlayer.isAirBorne = false;
    }
  }
  




  private boolean offset = false;
  
  public void onPostMotionUpdate() {}
  
  public void onPacketSend(Packet packet) {}
}
