package me.hexxed.mercury.values;

public class StringValue
  extends ModuleValue
{
  private String[] content;
  private String select;
  
  public StringValue(String name, String[] content, String select)
  {
    super(name);
    this.content = content;
    this.select = select;
  }
  
  public String[] getContents() {
    return content;
  }
  
  public String getSelected() {
    return select;
  }
  
  public boolean setSelected(String name) {
    boolean changed = false;
    for (String s : getContents()) {
      if (s.equalsIgnoreCase(name)) {
        s = select;
        changed = true;
      }
    }
    return changed;
  }
}
