package lunadevs.luna.utils.Values;

public class Value<T>
{
  private String name;
  private T defaultValue;
  public T value;

  public Value(String name, T defaultValue)
  {
    this.name = name;
    this.defaultValue = defaultValue;
    this.value = defaultValue;
  }

  public T getValue()
  {
    return (T)this.value;
  }

  public void setValue(T value)
  {
    this.value = value;
  }

  public String getName()
  {
    return this.name;
  }

  public T getDefaultValue()
  {
    return (T)this.defaultValue;
  }

  public Class getGenericClass()
  {
    return this.defaultValue.getClass();
  }

public Object getParent() {
	return value;
}
}
