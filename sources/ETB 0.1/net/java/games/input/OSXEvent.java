package net.java.games.input;








class OSXEvent
{
  private long type;
  






  private long cookie;
  





  private int value;
  





  private long nanos;
  






  OSXEvent() {}
  






  public void set(long type, long cookie, int value, long nanos)
  {
    this.type = type;
    this.cookie = cookie;
    this.value = value;
    this.nanos = nanos;
  }
  
  public long getType() {
    return type;
  }
  
  public long getCookie() {
    return cookie;
  }
  
  public int getValue() {
    return value;
  }
  
  public long getNanos() {
    return nanos;
  }
}
