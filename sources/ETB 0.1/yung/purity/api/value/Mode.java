package yung.purity.api.value;




public class Mode<V extends Enum>
  extends Value<V>
{
  private V[] modes;
  



  public Mode(String displayName, String name, V[] modes, V value)
  {
    super(displayName, name);
    this.modes = modes;
    setValue(value);
  }
  
  public V[] getModes()
  {
    return modes;
  }
  
  public String getModeAsString()
  {
    return ((Enum)getValue()).name();
  }
  
  public void setMode(String mode)
  {
    for (Enum e : modes) {
      if (e.name().equalsIgnoreCase(mode))
      {

        setValue(e);
      }
    }
  }
  
  public boolean isValid(String name) {
    for (Enum e : modes) {
      if (e.name().equalsIgnoreCase(name))
      {

        return true; }
    }
    return false;
  }
}
