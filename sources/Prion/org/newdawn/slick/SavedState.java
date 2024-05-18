package org.newdawn.slick;

import java.io.IOException;
import java.util.HashMap;
import org.newdawn.slick.muffin.FileMuffin;
import org.newdawn.slick.muffin.Muffin;
import org.newdawn.slick.muffin.WebstartMuffin;














public class SavedState
{
  private String fileName;
  private Muffin muffin;
  private HashMap numericData = new HashMap();
  
  private HashMap stringData = new HashMap();
  






  public SavedState(String fileName)
    throws SlickException
  {
    this.fileName = fileName;
    
    if (isWebstartAvailable()) {
      muffin = new WebstartMuffin();
    }
    else {
      muffin = new FileMuffin();
    }
    try
    {
      load();
    } catch (IOException e) {
      throw new SlickException("Failed to load state on startup", e);
    }
  }
  





  public double getNumber(String nameOfField)
  {
    return getNumber(nameOfField, 0.0D);
  }
  






  public double getNumber(String nameOfField, double defaultValue)
  {
    Double value = (Double)numericData.get(nameOfField);
    
    if (value == null) {
      return defaultValue;
    }
    
    return value.doubleValue();
  }
  






  public void setNumber(String nameOfField, double value)
  {
    numericData.put(nameOfField, new Double(value));
  }
  





  public String getString(String nameOfField)
  {
    return getString(nameOfField, null);
  }
  






  public String getString(String nameOfField, String defaultValue)
  {
    String value = (String)stringData.get(nameOfField);
    
    if (value == null) {
      return defaultValue;
    }
    
    return value;
  }
  






  public void setString(String nameOfField, String value)
  {
    stringData.put(nameOfField, value);
  }
  



  public void save()
    throws IOException
  {
    muffin.saveFile(numericData, fileName + "_Number");
    muffin.saveFile(stringData, fileName + "_String");
  }
  



  public void load()
    throws IOException
  {
    numericData = muffin.loadFile(fileName + "_Number");
    stringData = muffin.loadFile(fileName + "_String");
  }
  


  public void clear()
  {
    numericData.clear();
    stringData.clear();
  }
  







  private boolean isWebstartAvailable()
  {
    throw new Error("Unresolved compilation problem: \n\tThe method lookup(String) is undefined for the type ServiceManager\n");
  }
}
