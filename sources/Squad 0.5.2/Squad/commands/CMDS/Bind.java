package Squad.commands.CMDS;

import Squad.Squad;
import Squad.base.Module;
import Squad.base.ModuleManager;
import Squad.commands.Command;
import Squad.commands.KeyBindManager;
import Squad.info.ModuleInfo;

public class Bind
extends Command
{
	
	public static String usage = ".bind <mod> <name>";
	public static String info = "Binded deine Module auf eine gewünschte taste";
public Bind()
{
  super("bind", "bind <mod> <key>");
}

public void execute(String[] args)
{
  msg("§8§7§m------------§8| §rBind §8|§7§m------------§8");
  if (args.length == 0)
  {
    msg("§8| §rbind reset §8<§rmodule§8>");
    msg("§8| §rbind §8<§rmodule§8> <§rkey§8>");
  }
  else if (args.length == 2)
  {
    String key = args[0];
    String value = args[1];
    KeyBindManager mgr = Squad.keymgr;
    if (key.equalsIgnoreCase("reset"))
    {
      Module mod = ModuleManager.getModuleByName(value);
      if (mod == null)
      {
        msg("The module §8\"§r" + value + "§8\"§r cannot be resolved!");
        ModuleInfo.pushMessage("Cant find module ", value);
      }
      else
      {
        mgr.unbind(mod);
        msg("Resseted keybind of module §8\"§r" + mod.getName() + "§8\"");
        ModuleInfo.pushMessage("Resetted Bind of ", value);
      }
      return;
    }
    Module mod = ModuleManager.getModuleByName(key);
    if (mod == null)
    {
      msg("The module §8\"§r" + key + "§8\"§r cannot be resolved!");
      ModuleInfo.pushMessage("Cant find module ", value);
      
    }
    else
    {
      mgr.bind(mod, mgr.toInt(value));
      msg("Bound module §8\"§r" + mod.getName() + "§8\"§r to key " + value);
      ModuleInfo.pushMessage("Bound Module " + mod + " to", value);
    }
  }
  else
  {
    msg("Invalied arguments.");
    ModuleInfo.pushMessage("Invalid Arguments", "Try again");
  }
  msg("§8+§7§m-----------------------------§8+");
}

public String getName()
{
  return "bind";
}
}
