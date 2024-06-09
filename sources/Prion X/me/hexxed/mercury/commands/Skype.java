package me.hexxed.mercury.commands;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import me.hexxed.mercury.commandbase.Command;
import me.hexxed.mercury.util.Util;

public class Skype extends Command
{
  public Skype()
  {
    super("skype", "skype <username>");
  }
  
  public void execute(String[] args)
  {
    if (args.length != 1) {
      Util.addChatMessage(getUsage());
      return;
    }
    Util.sendInfo("Starting to resolve.");
    final String username = args[0];
    new Thread()
    {
      public void run()
      {
        String ip = "";
        try
        {
          URL insultURL = new URL("http://skype-resolver.de/api/" + username);
          URLConnection connection = insultURL.openConnection();
          BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
          while ((ip = reader.readLine()) != null) {
            if ((ip.contains("No IP")) || (ip.contains("Timed Out"))) {
              Util.sendInfo("Failed to retrieve IP.");
            } else
              Util.sendInfo("Retrieved IP is §b" + ip.substring(3) + "§7.");
          }
          reader.close();
        } catch (Exception e) {
          Util.sendInfo("Failed to retrieve IP.");
        }
      }
    }.start();
  }
}
