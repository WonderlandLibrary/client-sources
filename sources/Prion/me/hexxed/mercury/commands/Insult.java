package me.hexxed.mercury.commands;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import me.hexxed.mercury.Mercury;
import me.hexxed.mercury.commandbase.Command;
import me.hexxed.mercury.util.Util;
import net.minecraft.network.play.client.C01PacketChatMessage;

public class Insult extends Command
{
  public Insult()
  {
    super("insult", "insult");
  }
  
  public void execute(String[] args)
  {
    if (args.length != 0) {
      Util.addChatMessage(getUsage());
      return;
    }
    try {
      getMinecraftthePlayer.sendQueue.addToSendQueue(new C01PacketChatMessage(getInsult()));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  
  private String getInsult()
    throws IOException
  {
    String insult = "";
    try
    {
      URL insultURL = new URL("http://www.apteryx.tk/insult.php");
      URLConnection connection = insultURL.openConnection();
      BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
      if ((insult = reader.readLine()) != null) {
        return insult;
      }
    }
    catch (IOException e)
    {
      URL insultURL = new URL("http://www.insultgenerator.org/");
      URLConnection connection = insultURL.openConnection();
      BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
      while ((insult = reader.readLine()) != null) {
        if ((insult.startsWith("<TR align=center><TD>")) && 
          (insult.replace("<TR align=center><TD>", "").length() <= 100)) {
          return insult.replace("<TR align=center><TD>", "");
        }
      }
    }
    
    return insult;
  }
}
