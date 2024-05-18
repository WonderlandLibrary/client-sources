package net.SliceClient.Utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Path;
import java.util.List;
import net.SliceClient.module.Module;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.play.client.C03PacketPlayer.C04PacketPlayerPosition;
import net.minecraft.util.BlockPos;
import net.minecraft.util.IChatComponent;

public class Util
{
  public Util() {}
  
  public static Block getBlock(double posX, double posY, double posZ)
  {
    BlockPos bpos = new BlockPos(posX, posY, posZ);
    Minecraft.getMinecraft();Minecraft.getMinecraft();Block block = Minecraft.theWorld.getBlockState(bpos).getBlock();
    return block;
  }
  
  public static void SaveToggledModules()
  {
    try
    {
      Path path = java.nio.file.Paths.get(System.getProperty("user.home") + "\\HoneyPot", new String[0]);
      
      File file = new File(path.toString() + "\\toggledMods.txt");
      
      PrintWriter writer = new PrintWriter(file.getAbsolutePath(), "UTF-8");
      for (Module module : net.SliceClient.module.ModuleManager.getModules()) {
        if (module.isEnabled()) {
          writer.println(module.getName());
        }
      }
      writer.close();
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
  }
  
  public static String getText(String url)
    throws Exception
  {
    URL website = new URL(url);
    URLConnection connection = website.openConnection();
    BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
    
    StringBuilder response = new StringBuilder();
    String inputLine;
    while ((inputLine = in.readLine()) != null)
    {
      String inputLine;
      response.append(inputLine);
    }
    in.close();
    
    return response.toString();
  }
  
  public static void addChatMessage(String str) {
    Object chat = new net.minecraft.util.ChatComponentText(str);
    if (str != null) {
      getMinecraftingameGUI.getChatGUI().printChatMessage((IChatComponent)chat);
    }
  }
  
  public static List<String> getWebsite(String url)
    throws Exception
  {
    List<String> lines = new java.util.ArrayList();
    URL website = new URL(url);
    URLConnection connection = website.openConnection();
    BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
    
    StringBuilder response = new StringBuilder();
    String inputLine;
    while ((inputLine = in.readLine()) != null)
    {
      String inputLine;
      lines.add(inputLine);
    }
    in.close();
    return lines;
  }
  

  public static Double distance(double x1, double y1, double z1, double x2, double y2, double z2)
  {
    Double distance = null;
    distance = Double.valueOf(Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2) + (z1 - z2) * (z1 - z2)));
    return distance;
  }
  
  public static void damagePlayer(int amount)
  {
    for (int i = 0; i < 3 + amount; i++)
    {
      Minecraft.getMinecraft();Minecraft.getMinecraft();Minecraft.getMinecraft();Minecraft.getMinecraft();thePlayersendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(thePlayerposX, thePlayerposY + 1.01D, thePlayerposZ, false));
      Minecraft.getMinecraft();Minecraft.getMinecraft();Minecraft.getMinecraft();Minecraft.getMinecraft();thePlayersendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(thePlayerposX, thePlayerposY, thePlayerposZ, false));
    }
    Minecraft.getMinecraft();Minecraft.getMinecraft();Minecraft.getMinecraft();Minecraft.getMinecraft();thePlayersendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(thePlayerposX, thePlayerposY + 0.4D, thePlayerposZ, false));
  }
}
