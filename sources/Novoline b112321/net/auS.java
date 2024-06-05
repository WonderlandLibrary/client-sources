package net;

import java.util.Arrays;
import net.Ua;
import net.aJ1;
import net.bgM;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

public class auS {
   private static Minecraft a = Minecraft.getMinecraft();

   public static void a(Object... var0) {
      if(bgM.d().a()) {
         String var1 = Arrays.toString(var0);
         a.ingameGUI.getChatGUI().printChatMessage(new ChatComponentText(var1));
      }

   }

   public static void a(Object var0) {
      String var1 = String.valueOf(var0);
      a.ingameGUI.getChatGUI().printChatMessage(new ChatComponentText(var1));
   }

   public static void a(boolean var0, Object var1) {
      String var2 = a().getFormattedText() + " " + var1;
      a.ingameGUI.getChatGUI().printChatMessage(new ChatComponentText(var2));
   }

   public static void a(String var0, Object var1) {
      String var2 = a(var0).getFormattedText() + " " + var1;
      a.ingameGUI.getChatGUI().printChatMessage(new ChatComponentText(var2));
   }

   public static Ua a(String var0) {
      return aJ1.a(var0, EnumChatFormatting.LIGHT_PURPLE).b(" » ", EnumChatFormatting.GRAY);
   }

   public static Ua a() {
      return aJ1.a("Novoline", EnumChatFormatting.LIGHT_PURPLE).b(" » ", EnumChatFormatting.GRAY);
   }
}
