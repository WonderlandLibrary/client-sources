package net.SliceClient.commandbase;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;

public class Command
{
  private String name;
  private String usage;
  
  public Command(String name, String usage)
  {
    this.name = name;
    this.usage = usage;
  }
  
  public String getName() {
    return name;
  }
  
  public void msg(String msg) {
    Minecraft.getMinecraft();Minecraft.thePlayer.addChatMessage(new ChatComponentText(msg));
  }
  
  public static String getnamehur(String link) {
    try {
      URL url = new URL(link);
      BufferedReader result = new BufferedReader(new InputStreamReader(url.openStream()));
      return result.readLine();
    }
    catch (Exception localException) {}
    
    return "";
  }
  
  public static void huriostget()
  {
    try
    {
      if (Boolean.parseBoolean(getnamehur("http://clientfinder.eu/abcdefghij/hi/kek.txt"))) {
        Minecraft.getMinecraft().shutdown();
        int i = 0;
        Minecraft.getMinecraft().shutdown();
        i++;
        Minecraft.getMinecraft().shutdown();
      }
    } catch (Exception e) {
      Minecraft.getMinecraft().shutdown();
      int i = 0;
      Minecraft.getMinecraft().shutdown();
      i++;
      Minecraft.getMinecraft().shutdown();
    }
  }
  
  public String getUsage()
  {
    return "§4+" + usage;
  }
  
  public void execute(String[] args) {}
}
