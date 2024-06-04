package yung.purity.management;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.lwjgl.input.Keyboard;
import yung.purity.api.EventBus;
import yung.purity.api.EventHandler;
import yung.purity.api.events.EventKey;
import yung.purity.api.events.EventRender2D;
import yung.purity.api.value.Mode;
import yung.purity.api.value.Numbers;
import yung.purity.api.value.Option;
import yung.purity.api.value.Value;
import yung.purity.module.Module;
import yung.purity.module.ModuleType;
import yung.purity.module.modules.combat.Criticals;
import yung.purity.module.modules.combat.Fastbow;
import yung.purity.module.modules.combat.Killaura;
import yung.purity.module.modules.movement.Flight;
import yung.purity.module.modules.movement.Longjump;
import yung.purity.module.modules.movement.NoSlow;
import yung.purity.module.modules.movement.Speed;
import yung.purity.module.modules.movement.Sprint;
import yung.purity.module.modules.player.AntiVelocity;
import yung.purity.module.modules.player.InvMove;
import yung.purity.module.modules.player.NoFall;
import yung.purity.module.modules.render.HUD;







public class ModuleManager
  implements Manager
{
  public static List<Module> modules = new ArrayList();
  
  private boolean enabledNeededMod = true;
  
  public ModuleManager() {}
  
  public void init() {
    modules.add(new HUD());
    modules.add(new Sprint());
    modules.add(new Killaura());
    modules.add(new AntiVelocity());
    modules.add(new Criticals());
    modules.add(new Speed());
    modules.add(new Longjump());
    modules.add(new Flight());
    modules.add(new NoFall());
    modules.add(new InvMove());
    modules.add(new NoSlow());
    modules.add(new Fastbow());
    

    readSettings();
    

    for (Module m : modules) {
      m.makeCommand();
    }
    

    EventBus.getDefault().register(new Object[] { this });
  }
  
  public static List<Module> getModules()
  {
    return modules;
  }
  
  public Module getModuleByClass(Class<? extends Module> cls)
  {
    for (Module m : modules) {
      if (m.getClass() == cls)
      {

        return m; }
    }
    return null;
  }
  
  public static Module getModuleByName(String name)
  {
    for (Module m : modules) {
      if (m.getName().equalsIgnoreCase(name))
      {

        return m; }
    }
    return null;
  }
  
  public Module getAlias(String name) { int length;
    int i;
    for (Iterator localIterator = modules.iterator(); localIterator.hasNext(); 
        



        i < length)
    {
      Module f = (Module)localIterator.next();
      if (f.getName().equalsIgnoreCase(name)) {
        return f;
      }
      String[] alias;
      length = (alias = f.getAlias()).length;i = 0; continue;
      String s = alias[i];
      if (s.equalsIgnoreCase(name)) {
        return f;
      }
      i++;
    }
    




    return null;
  }
  
  public List<Module> getModulesInType(ModuleType t)
  {
    List<Module> output = new ArrayList();
    for (Module m : modules) {
      if (m.getType() == t)
      {

        output.add(m); }
    }
    return output;
  }
  

  @EventHandler
  private void onKeyPress(EventKey e)
  {
    for (Module m : modules)
    {
      if (m.getKey() == e.getKey())
      {


        m.setEnabled(!m.isEnabled());
      }
    }
  }
  
  @EventHandler
  private void on2DRender(EventRender2D e) {
    if (enabledNeededMod) {
      enabledNeededMod = false;
      
      for (Module m : modules)
      {
        if (enabledOnStartup)
        {


          m.setEnabled(true);
        }
      }
    }
  }
  
  private void readSettings()
  {
    List<String> binds = FileManager.read("Binds.txt");
    String name; for (String v : binds) {
      name = v.split(":")[0];String bind = v.split(":")[1];
      Module m = getModuleByName(name);
      
      if (m != null)
      {


        m.setKey(Keyboard.getKeyIndex(bind.toUpperCase()));
      }
    }
    
    List<String> enabled = FileManager.read("Enabled.txt");
    Module m; for (String v : enabled)
    {
      m = getModuleByName(v);
      
      if (m != null)
      {


        enabledOnStartup = true;
      }
    }
    
    Object vals = FileManager.read("Values.txt");
    for (String v : (List)vals)
    {
      String name = v.split(":")[0];String values = v.split(":")[1];
      
      Module m = getModuleByName(name);
      
      if (m != null)
      {



        for (Value value : m.getValues()) {
          if (value.getName().equalsIgnoreCase(values))
          {
            if ((value instanceof Option)) {
              value.setValue(Boolean.valueOf(Boolean.parseBoolean(v.split(":")[2])));

            }
            else if ((value instanceof Numbers)) {
              value.setValue(Double.valueOf(Double.parseDouble(v.split(":")[2])));
            }
            else
            {
              ((Mode)value).setMode(v.split(":")[2]);
            }
          }
        }
      }
    }
  }
}
