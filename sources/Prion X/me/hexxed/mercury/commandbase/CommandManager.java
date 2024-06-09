package me.hexxed.mercury.commandbase;

import java.util.Arrays;
import java.util.List;
import me.hexxed.mercury.commands.Account;
import me.hexxed.mercury.commands.Bind;
import me.hexxed.mercury.commands.Calculate;
import me.hexxed.mercury.commands.Cmd;
import me.hexxed.mercury.commands.Commands;
import me.hexxed.mercury.commands.Crash;
import me.hexxed.mercury.commands.Cure;
import me.hexxed.mercury.commands.Damage;
import me.hexxed.mercury.commands.Enchant;
import me.hexxed.mercury.commands.FriendCMD;
import me.hexxed.mercury.commands.GUICMD;
import me.hexxed.mercury.commands.GetIp;
import me.hexxed.mercury.commands.Gliderate;
import me.hexxed.mercury.commands.Help;
import me.hexxed.mercury.commands.Insult;
import me.hexxed.mercury.commands.Inventory;
import me.hexxed.mercury.commands.Killaurarate;
import me.hexxed.mercury.commands.Login;
import me.hexxed.mercury.commands.MacroCMD;
import me.hexxed.mercury.commands.Modules;
import me.hexxed.mercury.commands.Print;
import me.hexxed.mercury.commands.Say;
import me.hexxed.mercury.commands.Skype;
import me.hexxed.mercury.commands.Speedrate;
import me.hexxed.mercury.commands.StepHeight;
import me.hexxed.mercury.commands.Timer;
import me.hexxed.mercury.commands.Toggle;
import me.hexxed.mercury.commands.Tp;
import me.hexxed.mercury.commands.TranslateCMD;
import me.hexxed.mercury.commands.Vclip;
import me.hexxed.mercury.commands.Whoison;
import me.hexxed.mercury.commands.Zoot;



public class CommandManager
{
  public static List<Command> commandList = Arrays.asList(new Command[] {
    new Damage(), 
    new Help(), 
    new Commands(), 
    new Modules(), 
    new Insult(), 
    new Say(), 
    new Timer(), 
    new Login(), 
    new Enchant(), 
    new Cure(), 
    new Gliderate(), 
    new Tp(), 
    new GetIp(), 
    new Vclip(), 
    new Cmd(), 
    new Speedrate(), 
    new Crash(), 
    new Killaurarate(), 
    new Skype(), 
    new Toggle(), 
    new FriendCMD(), 
    new Inventory(), 
    new TranslateCMD(), 
    new Account(), 
    new Zoot(), 
    new Bind(), 
    new StepHeight(), 
    new Print(), 
    new GUICMD(), 
    new MacroCMD(), 
    new Calculate(), 
    new Whoison() });
  
  public CommandManager() {}
  
  public static Command getModByName(String name) { for (Command cmd : commandList) {
      if ((cmd.getName().trim().equalsIgnoreCase(name.trim())) || (cmd.toString().trim().equalsIgnoreCase(name.trim()))) {
        return cmd;
      }
    }
    
    return null;
  }
  
  public static Command getModByClassName(String name)
  {
    for (Command cmd : commandList) {
      if (cmd.getClass().getSimpleName().toLowerCase().trim().equals(name.toLowerCase().trim())) {
        return cmd;
      }
    }
    
    return null;
  }
}
