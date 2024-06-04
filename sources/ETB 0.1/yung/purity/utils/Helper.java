package yung.purity.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import yung.purity.Client;

public class Helper
{
  public static Minecraft mc = ;
  
  public Helper() {}
  
  public static void sendMessage(String msg) { mcthePlayer.addChatMessage(new ChatComponentText(String.format("%s%s", new Object[] { EnumChatFormatting.BLUE + instancename + EnumChatFormatting.GRAY + ": ", msg }))); }
}
