package optifine;

public class RangeListInt
{
  private RangeInt[] ranges = new RangeInt[0];
  
  public RangeListInt() {}
  
  public void addRange(RangeInt ri) { ranges = ((RangeInt[])Config.addObjectToArray(ranges, ri)); }
  

  public boolean isInRange(int val)
  {
    for (int i = 0; i < ranges.length; i++)
    {
      RangeInt ri = ranges[i];
      
      if (ri.isInRange(val))
      {
        return true;
      }
    }
    
    return false;
  }
  
  public int getCountRanges()
  {
    return ranges.length;
  }
  
  public RangeInt getRange(int i)
  {
    return ranges[i];
  }
  
  public String toString()
  {
    StringBuffer sb = new StringBuffer();
    sb.append("[");
    
    for (int i = 0; i < ranges.length; i++)
    {
      RangeInt ri = ranges[i];
      
      if (i > 0)
      {
        sb.append(", ");
      }
      
      sb.append(ri.toString());
    }
    
    sb.append("]");
    return sb.toString();
  }
}
