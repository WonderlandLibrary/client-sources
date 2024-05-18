package net.SliceClient.modules;

import net.SliceClient.module.Module;

public class HitboxExpander extends Module
{
  public HitboxExpander()
  {
    super("HitboxExpander", net.SliceClient.module.Category.EXPLOITS, 16376546);
  }
  
  public void onUpdate() {
    if (!getState()) {}
  }
}
