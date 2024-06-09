package lunadevs.luna.manage;

import java.util.ArrayList;

import lunadevs.luna.module.Module;
import lunadevs.luna.utils.Property;

public class PropertyManager
{
  public static ArrayList<Property> properties = new ArrayList();
  
  public static ArrayList<Property> getProperties()
  {
    return properties;
  }
  
  public static Property getPropertybyName(String name)
  {
    for (Property s : getProperties()) {
      if (s.getName().equalsIgnoreCase(name)) {
        return s;
      }
    }
    return null;
  }
  
  public ArrayList<Property> getPropertiesFromModule(Module module)
  {
    ArrayList<Property> array = new ArrayList();
    for (Property p : properties) {
      if (p.getModule() == module) {
        array.add(p);
      }
    }
    return array;
  }
}