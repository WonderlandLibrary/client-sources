package yung.purity.api.value;




public class Numbers<T extends Number>
  extends Value<T>
{
  private String name;
  

  public T min;
  

  public T max;
  

  public T inc;
  

  private boolean integer;
  


  public Numbers(String displayName, String name, T value, T min, T max, T inc)
  {
    super(displayName, name);
    setValue(value);
    this.min = min;
    this.max = max;
    this.inc = inc;
    integer = false;
  }
  
  public T getMinimum()
  {
    return min;
  }
  
  public T getMaximum()
  {
    return max;
  }
  
  public void setIncrement(T inc)
  {
    this.inc = inc;
  }
  
  public T getIncrement()
  {
    return inc;
  }
  
  public String getId()
  {
    return name;
  }
  
  public boolean isInteger()
  {
    return integer;
  }
}
