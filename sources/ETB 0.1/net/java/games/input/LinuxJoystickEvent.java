package net.java.games.input;





final class LinuxJoystickEvent
{
  private long nanos;
  



  private int value;
  



  private int type;
  



  private int number;
  




  LinuxJoystickEvent() {}
  




  public final void set(long millis, int value, int type, int number)
  {
    nanos = (millis * 1000000L);
    this.value = value;
    this.type = type;
    this.number = number;
  }
  
  public final int getValue() {
    return value;
  }
  
  public final int getType() {
    return type;
  }
  
  public final int getNumber() {
    return number;
  }
  
  public final long getNanos() {
    return nanos;
  }
}
