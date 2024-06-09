package org.newdawn.slick.font.effects;

import java.util.List;

public abstract interface ConfigurableEffect
  extends Effect
{
  public abstract List getValues();
  
  public abstract void setValues(List paramList);
  
  public static abstract interface Value
  {
    public abstract String getName();
    
    public abstract void setString(String paramString);
    
    public abstract String getString();
    
    public abstract Object getObject();
    
    public abstract void showDialog();
  }
}
