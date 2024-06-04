package net.java.games.input;








final class DIDeviceObjectData
{
  private int format_offset;
  






  private int data;
  





  private int millis;
  





  private int sequence;
  






  DIDeviceObjectData() {}
  






  public final void set(int format_offset, int data, int millis, int sequence)
  {
    this.format_offset = format_offset;
    this.data = data;
    this.millis = millis;
    this.sequence = sequence;
  }
  
  public final void set(DIDeviceObjectData other) {
    set(format_offset, data, millis, sequence);
  }
  
  public final int getData() {
    return data;
  }
  
  public final int getFormatOffset() {
    return format_offset;
  }
  
  public final long getNanos() {
    return millis * 1000000L;
  }
}
