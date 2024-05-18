package net.SliceClient.Utils;

import net.SliceClient.bindmanager.BindManager;

public class UpdateThread
{
  public UpdateThread() {}
  
  public static TimeHelper t = new TimeHelper();
  
  public static void update() {
    if (t.isDelayComplete(5000L)) {
      t.setLastMS();
      net.SliceClient.Slice.bindmanager.saveKeybinds();
    }
  }
}
