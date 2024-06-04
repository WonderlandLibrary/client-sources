package net.java.games.input;





final class LinuxAbsInfo
{
  private int value;
  



  private int minimum;
  



  private int maximum;
  


  private int fuzz;
  


  private int flat;
  



  LinuxAbsInfo() {}
  



  public final void set(int value, int min, int max, int fuzz, int flat)
  {
    this.value = value;
    minimum = min;
    maximum = max;
    this.fuzz = fuzz;
    this.flat = flat;
  }
  
  public final String toString() {
    return "AbsInfo: value = " + value + " | min = " + minimum + " | max = " + maximum + " | fuzz = " + fuzz + " | flat = " + flat;
  }
  
  public final int getValue() {
    return value;
  }
  
  final int getMax() {
    return maximum;
  }
  
  final int getMin() {
    return minimum;
  }
  
  final int getFlat() {
    return flat;
  }
  
  final int getFuzz() {
    return fuzz;
  }
}
