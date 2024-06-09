package winter.module;

import java.util.ArrayList;
import net.minecraft.client.Minecraft;
import winter.Client;
import winter.event.EventSystem;
import winter.module.modules.modes.Mode;
import winter.utils.file.FileManager;
import winter.utils.file.FileManager.CustomFile;
import winter.utils.file.files.Modules;
import winter.utils.value.Value;

public class Module
{
  private String name;
  private Category cat;
  private boolean enabled;
  private boolean vis;
  protected Minecraft mc;
  private int bind;
  private String mode;
  public int color;
  protected ArrayList<Value> values;
  protected ArrayList<Mode> modes;
  protected int modeInt;
  
  public Module(String name, Category cat, int color)
  {
    this.mode = "";
    this.vis = true;
    this.name = name;
    this.cat = cat;
    this.enabled = false;
    this.color = color;
    setBind(0);
    this.mc = Minecraft.getMinecraft();
    this.values = new ArrayList();
    this.modes = new ArrayList();
  }
  
  public String mode()
  {
    return this.mode;
  }
  
  public Mode getNextMode()
  {
    if (this.modeInt + 1 <= getModes().size() - 1) {
      this.modeInt += 1;
    } else if (this.modeInt + 1 > getModes().size() - 1) {
      this.modeInt = 0;
    }
    return (Mode)this.modes.get(this.modeInt);
  }
  
  public Mode getPreviousMode()
  {
    if (this.modeInt - 1 >= 0) {
      this.modeInt -= 1;
    } else if (this.modeInt - 1 < 0) {
      this.modeInt = (getModes().size() - 1);
    }
    return (Mode)this.modes.get(this.modeInt);
  }
  
  public void mode(String newmode)
  {
    this.mode = newmode;
  }
  
  public void visible(boolean vis)
  {
    this.vis = vis;
  }
  
  public boolean visible()
  {
    return this.vis;
  }
  
  public String getModeForArrayListLongNameLOL()
  {
    if (mode().isEmpty()) {
      return "";
    }
    String xd = mode().replaceAll(" ", " ");
    return xd;
  }
  
  public ArrayList<Value> getValues()
  {
    return this.values;
  }
  
  public void addValue(Value value)
  {
    this.values.add(value);
  }
  
  public void onDisable() {}
  
  public void onEnable() {}
  
  public boolean isEnabled()
  {
    return this.enabled;
  }
  
  public void setEnabled(boolean enabled)
  {
    this.enabled = enabled;
    if (Client.fileManager != null) {
      try
      {
        Client.fileManager.getFile(Modules.class).saveFile();
      }
      catch (Exception localException) {}
    }
  }
  
  public void setName(String newName)
  {
    this.name = newName;
  }
  
  public void toggle()
  {
    if (this.enabled)
    {
      onDisable();
      EventSystem.unregister(this);
      setEnabled(false);
    }
    else
    {
      onEnable();
      EventSystem.register(this);
      setEnabled(true);
    }
    if (Client.fileManager != null) {
      try
      {
        Client.fileManager.getFile(Modules.class).saveFile();
      }
      catch (Exception localException) {}
    }
  }
  
  public void toggleBoolean(boolean enabled)
  {
    if (!enabled)
    {
      EventSystem.unregister(this);
      setEnabled(false);
    }
    else
    {
      onEnable();
      EventSystem.register(this);
      setEnabled(true);
    }
    if (Client.fileManager != null) {
      try
      {
        Client.fileManager.getFile(Modules.class).saveFile();
      }
      catch (Exception localException) {}
    }
  }
  
  public String getName()
  {
    return this.name;
  }
  
  public String getMode()
  {
    return this.mode;
  }
  
  public Category getCategory()
  {
    return this.cat;
  }
  
  public Value getValue(String name)
  {
    for (Value v : this.values) {
      if (v.getName().equalsIgnoreCase(name)) {
        return v;
      }
    }
    return null;
  }
  
  public static enum Category
  {
    Combat("Combat"),  Exploits("Exploits"),  Other("Other"),  Movement("Movement"),  Render("Render"),  World("World");
    
    private Category(String catName) {}
  }
  
  public ArrayList<Mode> getModes()
  {
    return this.modes;
  }
  
  public void addMode(Mode mode)
  {
    this.modes.add(mode);
  }
  
  public void setMode(String newMode)
  {
    this.mode = newMode;
  }
  
  public int getBind()
  {
    return this.bind;
  }
  
  public void setBind(int keyBind)
  {
    this.bind = keyBind;
    if (Client.fileManager != null) {
      try
      {
        Client.fileManager.getFile(Modules.class).saveFile();
      }
      catch (Exception localException) {}
    }
  }
}