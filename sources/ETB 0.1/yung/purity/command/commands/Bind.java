package yung.purity.command.commands;

import org.lwjgl.input.Keyboard;
import yung.purity.Client;
import yung.purity.command.Command;
import yung.purity.management.ModuleManager;
import yung.purity.module.Module;
import yung.purity.utils.Helper;





public class Bind
  extends Command
{
  public Bind()
  {
    super("Bind", new String[] { "b" }, "", "sketit");
  }
  

  public String execute(String[] args)
  {
    if (args.length >= 2)
    {
      Module m = Client.instance.getModuleManager().getAlias(args[0]);
      
      if (m != null) {
        int k = Keyboard.getKeyIndex(args[1].toUpperCase());
        
        m.setKey(k);
        
        Helper.sendMessage(
          String.format("bound %s to %s", new Object[] { m.getName(), k == 0 ? "none" : args[1].toUpperCase() }));
      } else {
        syntaxError("module not found");
      }
    } else {
      Helper.sendMessage("invalid syntax Valid .bind <module> <key>");
    }
    return null;
  }
}
