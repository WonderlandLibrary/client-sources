package Squad.commands.CMDS;

import Squad.base.Module;
import Squad.base.ModuleManager;
import Squad.commands.Command;
import Squad.info.ModuleInfo;

public class Toggle
  extends Command
{
  public Toggle()
  {
    super("t", ".t <mod>");
  }
  
  public void execute(String[] args)
  {
    Module m = ModuleManager.getModuleByName(args[0]);
    if (m == null)
    {
      msg("§7Module was not found.");
    ModuleInfo.pushMessage("Cant Find", args[0]);
    }
    else
    {
      m.toggle();
      msg((m.getState() ? "Toggled" : "Disabled") + " module " + m.getName());
      ModuleInfo.pushMessage("Toggled", args[0]);
    }
  }
  
  public String getName()
  {
    return "t";
  }
}
