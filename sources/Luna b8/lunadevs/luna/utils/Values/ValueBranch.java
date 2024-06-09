package lunadevs.luna.utils.Values;

import java.util.ArrayList;
import java.util.List;

public class ValueBranch
{
  private String name;
  private List<Value> values = new ArrayList();
  
  public ValueBranch(String name)
  {
    this.name = name;
  }
  
  public String getName()
  {
    return this.name;
  }
  
  public List<Value> getValues()
  {
    return this.values;
  }
  
  public void addValue(Value value)
  {
    this.values.add(value);
  }
}
