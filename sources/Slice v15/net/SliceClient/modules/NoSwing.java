package net.SliceClient.modules;

import com.darkmagician6.eventapi.EventManager;
import com.darkmagician6.eventapi.EventTarget;
import net.SliceClient.event.EventSwingItem;
import net.SliceClient.module.Category;
import net.SliceClient.module.Module;

public class NoSwing
  extends Module
{
  public NoSwing()
  {
    super("NoSwing", Category.MISC, 16376546);
  }
  
  public void onEnable()
  {
    EventManager.register(this);
    super.onEnable();
  }
  
  public void onDisable()
  {
    EventManager.unregister(this);
    super.onDisable();
  }
  
  @EventTarget
  public void onSwing(EventSwingItem e)
  {
    if (getState()) {
      e.setCancelled(true);
    }
  }
}
