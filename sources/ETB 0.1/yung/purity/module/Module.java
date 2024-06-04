package yung.purity.module;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;
import yung.purity.Client;
import yung.purity.api.EventBus;
import yung.purity.api.value.Mode;
import yung.purity.api.value.Numbers;
import yung.purity.api.value.Option;
import yung.purity.api.value.Value;
import yung.purity.command.Command;
import yung.purity.management.CommandManager;
import yung.purity.management.FileManager;
import yung.purity.management.ModuleManager;
import yung.purity.utils.Helper;
import yung.purity.utils.MathUtil;















public class Module
{
  public String name;
  private String suffix;
  private int color;
  private String[] alias;
  private boolean enabled;
  public boolean enabledOnStartup = false;
  

  private int key;
  

  public List<Value> values;
  

  public ModuleType type;
  

  public Minecraft mc = Minecraft.getMinecraft();
  

  public static Random random = new Random();
  
  public Module(String name, String[] alias, ModuleType type)
  {
    this.name = name;
    this.alias = alias;
    this.type = type;
    suffix = "";
    key = 0;
    enabled = false;
    values = new ArrayList();
  }
  
  public String getName()
  {
    return name;
  }
  
  public String[] getAlias()
  {
    return alias;
  }
  
  public ModuleType getType()
  {
    return type;
  }
  
  public boolean isEnabled()
  {
    return enabled;
  }
  
  public String getSuffix()
  {
    return suffix;
  }
  
  public void setEnabled(boolean enabled)
  {
    this.enabled = enabled;
    if (enabled) {
      onEnable();
      EventBus.getDefault().register(new Object[] { this });
    } else {
      EventBus.getDefault().unregister(new Object[] { this });
      onDisable();
    }
  }
  
  public void setColor(int color)
  {
    this.color = color;
  }
  
  public int getColor()
  {
    return color;
  }
  
  protected void addValues(Value... values)
  {
    for (Value value : values) {
      this.values.add(value);
    }
  }
  
  public List<Value> getValues()
  {
    return values;
  }
  
  public int getKey()
  {
    return key;
  }
  

  public void setKey(int key)
  {
    this.key = key;
    
    String content = "";
    
    Client.instance.getModuleManager(); for (Module m : ModuleManager.getModules())
    {
      content = content + String.format("%s:%s%s", new Object[] { m.getName(), Keyboard.getKeyName(m.getKey()), 
        System.lineSeparator() });
    }
    
    FileManager.save("Binds.txt", content);
  }
  

  public void onEnable() {}
  

  public void onDisable() {}
  

  public void makeCommand()
  {
    if (values.size() > 0)
    {
      String options = "";
      String other = "";
      
      for (Value value : values) {
        if (!(value instanceof Mode))
        {

          if (options.isEmpty()) {
            options = String.valueOf(options) + value.getName();
          }
          else {
            options = String.valueOf(options) + String.format(", %s", new Object[] { value.getName() });
          }
        }
      }
      for (Value v : values) {
        if ((v instanceof Mode))
        {


          Mode mode = (Mode)v;
          
          Enum[] modes;
          int length = (modes = mode.getModes()).length; for (int i = 0; i < length; i++) {
            Enum e = modes[i];
            
            if (other.isEmpty()) {
              other = String.valueOf(other) + e.name().toLowerCase();
            }
            else {
              other = String.valueOf(other) + String.format(", %s", new Object[] { e.name().toLowerCase() });
            }
          }
        }
      }
      
      instancecommandmanager.add(new Command(name, alias, String.format("%s%s", new Object[] { options.isEmpty() ? "" : String.format("%s,", new Object[] { options }), other.isEmpty() ? "" : String.format("%s", new Object[] { other }) }), "Setup this module")
      {
        private final Module m = Module.this;
        
        public String execute(String[] args) {
          Mode mode;
          if (args.length >= 2)
          {
            Option<Boolean> option = null;
            Numbers<Double> val = null;
            mode = null;
            
            for (Value value : m.values) {
              if (((value instanceof Option)) && 
                (value.getName().equalsIgnoreCase(args[0])))
              {

                option = (Option)value;
              }
            }
            
            if (option != null) {
              option.setValue(Boolean.valueOf(!((Boolean)option.getValue()).booleanValue()));
              Helper.sendMessage(String.format("%s has been set to %s", new Object[] { option.getName(), option.getValue() }));
            }
            else {
              for (Value value2 : m.values) {
                if (((value2 instanceof Numbers)) && 
                  (value2.getName().equalsIgnoreCase(args[0])))
                {

                  val = (Numbers)value2;
                }
              }
              
              if (val != null) {
                if (MathUtil.parsable(args[1], (byte)4)) {
                  double value3 = MathUtil.round(Double.parseDouble(args[1]), 1);
                  val.setValue(Double.valueOf(value3 > ((Double)val.getMaximum()).doubleValue() ? ((Double)val.getMaximum()).doubleValue() : value3));
                  Helper.sendMessage(String.format("%s has been set to %s", new Object[] { val.getName(), val.getValue() }));
                }
                else {
                  Helper.sendMessage(args[1] + " is not a number");
                }
              }
              
              for (Value v : m.values) {
                if (args[0].equalsIgnoreCase(v.getDisplayName()))
                {
                  if ((v instanceof Mode))
                  {

                    mode = (Mode)v;
                  }
                }
              }
              if (mode != null) {
                if (mode.isValid(args[1])) {
                  mode.setMode(args[1]);
                  Helper.sendMessage(String.format("%s set to %s", new Object[] { mode.getName(), mode.getModeAsString() }));
                }
                else
                {
                  Helper.sendMessage(args[1] + " is an invalid mode");
                }
              }
            }
            
            if ((val == null) && (option == null) && (mode == null)) {
              syntaxError("Valid .<module> <setting> <mode if needed>");
            }
            
          }
          else if (args.length >= 1) {
            Option<Boolean> option = null;
            
            for (Value value : m.values) {
              if (((value instanceof Option)) && 
                (value.getName().equalsIgnoreCase(args[0])))
              {

                option = (Option)value;
              }
            }
            
            if (option != null) {
              option.setValue(Boolean.valueOf(!((Boolean)option.getValue()).booleanValue()));
              Helper.sendMessage(String.format("%s: %s", new Object[] { option.getName(), option.getValue() }));
            }
            else
            {
              syntaxError("Valid .<module> <setting> <mode if needed>");
            }
          }
          else
          {
            Helper.sendMessage(String.format("%s Values: \n %s", new Object[] { getName().substring(0, 1).toUpperCase() + getName().substring(1).toLowerCase(), getSyntax(), "false" }));
          }
          
          return null;
        }
      });
    }
  }
}
