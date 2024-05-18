package net.minecraft.src;

public class RangeInt
{
  private int min = -1;
  private int max = -1;
  
  public RangeInt(int min, int max)
  {
    this.min = min;
    this.max = max;
  }
  
  public boolean isInRange(int val)
  {
    return (min < 0) || (val >= min);
  }
  
  public String toString()
  {
    return "min: " + min + ", max: " + max;
  }
}
