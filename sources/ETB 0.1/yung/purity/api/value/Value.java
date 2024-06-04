package yung.purity.api.value;



public abstract class Value<V>
{
  private String displayName;
  
  private String name;
  
  private V value;
  

  public Value(String displayName, String name)
  {
    this.displayName = displayName;
    this.name = name;
  }
  
  public String getDisplayName()
  {
    return displayName;
  }
  
  public String getName()
  {
    return name;
  }
  
  public V getValue()
  {
    return value;
  }
  
  public void setValue(V value)
  {
    this.value = value;
  }
}
