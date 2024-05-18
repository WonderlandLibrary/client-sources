package net.SliceClient.commandbase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.SliceClient.commands.AACDamage;
import net.SliceClient.commands.Clear;
import net.SliceClient.commands.Credits;
import net.SliceClient.commands.Damage;
import net.SliceClient.commands.Enchant;
import net.SliceClient.commands.Help;
import net.SliceClient.commands.KillAura;
import net.SliceClient.commands.Modules;
import net.SliceClient.commands.OneHitSword;
import net.SliceClient.commands.Say;
import net.SliceClient.commands.Timer;
import net.SliceClient.commands.Toggle;
import net.SliceClient.commands.exit;
import net.SliceClient.commands.gm1;




public class CommandManager
{
  private List<Command> commands = new ArrayList();
  
  public CommandManager()
  {
    commands.add(new Help());
    commands.add(new Modules());
    commands.add(new KillAura());
    commands.add(new Damage());
    commands.add(new Clear());
    commands.add(new Say());
    commands.add(new Credits());
    commands.add(new Enchant());
    commands.add(new OneHitSword());
    commands.add(new Timer());
    commands.add(new gm1());
    commands.add(new Toggle());
    commands.add(new AACDamage());
    commands.add(new exit());
  }
  


  public boolean execute(String text)
  {
    if (!text.startsWith(".")) {
      return false;
    }
    text = text.substring(1);
    
    String[] arguments = text.split(" ");
    for (Command cmd : commands) {
      if (cmd.getName().equalsIgnoreCase(arguments[0]))
      {
        String[] args = (String[])Arrays.copyOfRange(arguments, 1, arguments.length);
        cmd.execute(args);
        return true;
      }
    }
    return false;
  }
}
