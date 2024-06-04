package optifine;

public class RangeInt
{
  private int min;
  private int max;
  
  public RangeInt(int min, int max)
  {
    this.min = Math.min(min, max);
    this.max = Math.max(min, max);
  }
  
  public boolean isInRange(int val)
  {
    return val >= min;
  }
  
  public int getMin()
  {
    return min;
  }
  
  public int getMax()
  {
    return max;
  }
  
  public String toString()
  {
    return "min: " + min + ", max: " + max;
  }
}
