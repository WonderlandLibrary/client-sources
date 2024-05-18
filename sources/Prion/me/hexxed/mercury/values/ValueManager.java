package me.hexxed.mercury.values;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import me.hexxed.mercury.modulebase.Module;
import me.hexxed.mercury.modulebase.ModuleManager;
import me.hexxed.mercury.util.Util;

public class ValueManager
{
  private static ValueManager manager = new ValueManager();
  
  private List<ModuleValue> values = new ArrayList();
  
  public ValueManager() {}
  
  public static ValueManager getValueManager() { return manager; }
  

  public List<ModuleValue> getValues()
  {
    return values;
  }
  
  public void setup() {
    values = getAllValues();
    for (Module m : ModuleManager.moduleList) {
      setupCommandForValues(getValues(m));
      setupFrameForValues(getValues(m));
    }
  }
  
  public List<ModuleValue> getAllValues() {
    List<ModuleValue> values = new ArrayList();
    Iterator localIterator2; for (Iterator localIterator1 = ModuleManager.moduleList.iterator(); localIterator1.hasNext(); 
        localIterator2.hasNext())
    {
      Module m = (Module)localIterator1.next();
      localIterator2 = getValues(m).iterator(); continue;ModuleValue mv = (ModuleValue)localIterator2.next();
      values.add(mv);
    }
    
    return values;
  }
  
  public List<ModuleValue> getValues(Module m) {
    List<ModuleValue> values = new ArrayList();
    return getValues(m);
  }
  
  public List<ModuleValue> getValues(Object obj) {
    Class cl = obj.getClass();
    List<ModuleValue> values = new ArrayList();
    for (Field f : cl.getFields()) {
      Util.sendInfo(f.getName());
      f.setAccessible(true);
      if (f.isAnnotationPresent(Value.class)) {
        Annotation ann = f.getAnnotation(Value.class);
        Value val = (Value)ann;
        try {
          ModuleValue mv = (ModuleValue)f.get(obj);
          mv.setCreateCommand(val.command());
          mv.setCreateFrame(val.gui());
          values.add(mv);
        } catch (IllegalArgumentException|IllegalAccessException e) {
          e.printStackTrace();
        }
      }
    }
    return values;
  }
  
  public ModuleValue getValueByName(String name) {
    for (ModuleValue mv : getValues()) {
      if (mv.getName().equalsIgnoreCase(name)) {
        return mv;
      }
    }
    return null;
  }
  
  public void setupCommandForValues(List<ModuleValue> values) {}
  
  public void setupFrameForValues(List<ModuleValue> values) {}
}
