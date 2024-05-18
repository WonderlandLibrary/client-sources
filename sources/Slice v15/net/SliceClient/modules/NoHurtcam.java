package net.SliceClient.modules;

import com.darkmagician6.eventapi.EventTarget;
import net.SliceClient.event.HurtCam;
import net.SliceClient.module.Category;
import net.SliceClient.module.Module;

public class NoHurtcam
  extends Module
{
  public NoHurtcam()
  {
    super("NoHurtcam", Category.EXPLOITS, 16376546);
  }
  
  @EventTarget
  public void gdsavdbjh(HurtCam event)
  {
    event.setCancelled(true);
  }
}
