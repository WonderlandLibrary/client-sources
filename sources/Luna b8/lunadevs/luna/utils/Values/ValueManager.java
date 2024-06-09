package lunadevs.luna.utils.Values;

import java.util.ArrayList;
import java.util.List;

public class ValueManager
  extends Manager<Value<? extends Object>>
{
  public static final ValueManager INSTANCE = new ValueManager();
  
  public void load() {}
  
  public void register(Value<? extends Object> avalue)
  {
    addData(avalue);
  }
  
  public List<Value<? extends Object>> getValues(Object object)
  {
    List<Value<? extends Object>> values = new ArrayList();
    for (Value value : getValues()) {
      if (value.getParent().equals(object)) {
        values.add(value);
      }
    }
    return values;
  }
  
  public List<Value<? extends Object>> getValues()
  {
    return getData();
  }
}
