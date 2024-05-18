package me.hexxed.mercury.commands;

import me.hexxed.mercury.commandbase.Command;
import me.hexxed.mercury.util.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Session;


public class Login
  extends Command
{
  public Login()
  {
    super("login", "login <username> <password>");
  }
  
  public void execute(String[] args)
  {
    if (args.length != 2) {
      Util.addChatMessage(getUsage());
      return;
    }
    String username = args[0];
    String password = args[1];
    boolean success = Util.login(username, password);
    if (success) {
      Util.sendInfo("Logged in successfully with the username §b" + Minecraft.getMinecraft().getSession().getUsername());
    } else {
      Util.sendInfo("Couldn't log in. Creating a cracked session");
    }
  }
}
