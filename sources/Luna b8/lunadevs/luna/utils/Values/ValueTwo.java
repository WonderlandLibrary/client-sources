package lunadevs.luna.utils.Values;

	public class ValueTwo<T>
	{
	  private final T defaultValue;
	  private final String name;
	  private T value;
	  
	  public ValueTwo(String name, T value)
	  {
	    this.name = name;
	    this.value = value;
	    this.defaultValue = value;
	  }
	  
	  public T getDefaultValue()
	  {
	    return (T)this.defaultValue;
	  }
	  
	  public String getName()
	  {
	    return this.name;
	  }
	  
	  public T getValue()
	  {
	    return (T)this.value;
	  }
	  
	  public void setValue(T value)
	  {
	    this.value = value;
	  }
	}
