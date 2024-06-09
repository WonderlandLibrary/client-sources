package lunadevs.luna.utils.Values;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class Manager<T>
{
  private List<T> data = new ArrayList();
  
  public abstract void load();
  
  protected void addData(T data)
  {
    this.data.add(data);
  }
  
  protected void addData(T... data)
  {
    this.data.addAll(Arrays.asList(data));
  }
  
  protected void addData(List<T> data)
  {
    this.data.addAll(data);
  }
  
  protected void removeData(T data)
  {
    this.data.remove(data);
  }
  
  public <I extends T> T get(Class<I> clazz)
  {
    for (T data : getData()) {
      if (data.getClass().equals(clazz)) {
        return data;
      }
    }
    return null;
  }
  
  protected List<T> getData()
  {
    return this.data;
  }
}
