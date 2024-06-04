package net.java.games.input;







final class LinuxAxisDescriptor
{
  private int type;
  





  private int code;
  






  LinuxAxisDescriptor() {}
  






  public final void set(int type, int code)
  {
    this.type = type;
    this.code = code;
  }
  
  public final int getType() {
    return type;
  }
  
  public final int getCode() {
    return code;
  }
  
  public final int hashCode() {
    return type ^ code;
  }
  
  public final boolean equals(Object other) {
    if (!(other instanceof LinuxAxisDescriptor))
      return false;
    LinuxAxisDescriptor descriptor = (LinuxAxisDescriptor)other;
    return (type == type) && (code == code);
  }
  
  public final String toString() {
    return "LinuxAxis: type = 0x" + Integer.toHexString(type) + ", code = 0x" + Integer.toHexString(code);
  }
}
