package me.hexxed.mercury.util;

public class TimeHelper { public TimeHelper() {}
  private long lastMS = 0L;
  
  public boolean isDelayComplete(long delay) {
    if (System.currentTimeMillis() - lastMS >= delay) {
      return true;
    }
    
    return false;
  }
  
  public void setLastMS(long lastMS) {
    this.lastMS = lastMS;
  }
  
  public void setLastMS() {
    lastMS = System.currentTimeMillis();
  }
  
  public int convertToMS(int perSecond) {
    return 1000 / perSecond;
  }
}
