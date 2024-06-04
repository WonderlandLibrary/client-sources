package yung.purity;

import java.io.PrintStream;
import java.util.Iterator;
import java.util.List;
import yung.purity.api.value.Value;
import yung.purity.management.CommandManager;
import yung.purity.management.FileManager;
import yung.purity.management.ModuleManager;
import yung.purity.module.Module;
import yung.purity.module.modules.render.UI.TabUI;




public class Client
{
  public String name;
  public String version;
  public String date;
  public static final Client instance = new Client();
  public ModuleManager modulemanager;
  public CommandManager commandmanager;
  private TabUI tabui;
  
  public Client() { name = "ETB";
    version = "0.1";
    date = "#{170218}";
  }
  
  public void initiate() {
    System.out.println("Firing client...");
    (this.commandmanager = new CommandManager()).init();
    (this.modulemanager = new ModuleManager()).init();
    (this.tabui = new TabUI()).init();
    FileManager.init();
  }
  
  public ModuleManager getModuleManager() {
    return modulemanager;
  }
  
  public CommandManager getCommandManager() {
    return commandmanager;
  }
  
  public void shutDown() {
    String values = "";
    instance.getModuleManager();
    Iterator localIterator2; Value v; for (Iterator localIterator1 = ModuleManager.getModules().iterator(); localIterator1.hasNext(); 
        localIterator2.hasNext())
    {
      Module m = (Module)localIterator1.next();
      localIterator2 = m.getValues().iterator(); continue;v = (Value)localIterator2.next();
      values = String.valueOf(values) + String.format("%s:%s:%s%s", new Object[] { m.getName(), v.getName(), v.getValue(), System.lineSeparator() });
    }
    
    FileManager.save("Values.txt", values);
    String enabled = "";
    instance.getModuleManager();
    for (Module i : ModuleManager.getModules()) {
      if (i.isEnabled())
      {

        enabled = String.valueOf(enabled) + String.format("%s%s", new Object[] { i.getName(), System.lineSeparator() }); }
    }
    FileManager.save("Enabled.txt", enabled);
  }
}
