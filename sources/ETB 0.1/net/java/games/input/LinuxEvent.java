package net.java.games.input;















final class LinuxEvent
{
  private long nanos;
  












  private final LinuxAxisDescriptor descriptor = new LinuxAxisDescriptor();
  
  LinuxEvent() {}
  
  public final void set(long seconds, long microseconds, int type, int code, int value) { nanos = ((seconds * 1000000L + microseconds) * 1000L);
    descriptor.set(type, code);
    this.value = value;
  }
  
  private int value;
  public final int getValue() { return value; }
  

  public final LinuxAxisDescriptor getDescriptor() {
    return descriptor;
  }
  
  public final long getNanos() {
    return nanos;
  }
}
