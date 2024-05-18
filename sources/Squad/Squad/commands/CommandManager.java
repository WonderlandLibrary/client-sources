package Squad.commands;

import java.util.ArrayList;
import java.util.List;

import Squad.commands.CMDS.Bind;
import Squad.commands.CMDS.Toggle;
import Squad.commands.CMDS.settings;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;

public class CommandManager
{
  static List<Command> commands = new ArrayList();
  public static char cmdPrefix = '.';
  
  public CommandManager()
  {
	  this.commands.add(new Bind());
	  this.commands.add(new Toggle());
  this.commands.add(new settings());
  }
  
  
  public void runCommands(String text)
  {
    if ((!text.contains(Character.toString(cmdPrefix))) || (!text.startsWith(Character.toString(cmdPrefix)))) {
      return;
    }
    boolean commandResolved = false;
    String readString = text.trim().substring(Character.toString(cmdPrefix).length()).trim();
    boolean hasArgs = readString.trim().contains(" ");
    String commandName = hasArgs ? readString.split(" ")[0] : readString.trim();
    String[] args = hasArgs ? readString.substring(commandName.length()).trim().split(" ") : new String[0];
    for (Command command : commands) {
      if (command.getName().trim().equalsIgnoreCase(commandName.trim()))
      {
        command.execute(args);
        commandResolved = true;
        break;
      }
    }
    if (!commandResolved)
    {
   Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("§7[§cSquad§7]§fCommand not found!"));
    }
  }
  
  public static List<Command> getCommands()
  {
    return commands;
  }
}
