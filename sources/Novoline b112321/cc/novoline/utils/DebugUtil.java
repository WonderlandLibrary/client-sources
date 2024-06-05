package cc.novoline.utils;

import cc.novoline.Initializer;
import cc.novoline.utils.messages.MessageFactory;
import cc.novoline.utils.messages.TextMessage;
import java.util.Arrays;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;

public class DebugUtil {
   private static Minecraft mc = Minecraft.getInstance();

   public static void print(Object... var0) {
      if(Initializer.getInstance().a()) {
         String var1 = Arrays.toString(var0);
         mc.ingameGUI.n().a((IChatComponent)(new ChatComponentText(var1)));
      }

   }

   public static void log(Object var0) {
      String var1 = String.valueOf(var0);
      mc.ingameGUI.n().a((IChatComponent)(new ChatComponentText(var1)));
   }

   public static void log(boolean var0, Object var1) {
      String var2 = prefix().getFormattedText() + " " + var1;
      mc.ingameGUI.n().a((IChatComponent)(new ChatComponentText(var2)));
   }

   public static void log(String var0, Object var1) {
      String var2 = prefix(var0).getFormattedText() + " " + var1;
      mc.ingameGUI.n().a((IChatComponent)(new ChatComponentText(var2)));
   }

   public static TextMessage prefix(String var0) {
      return MessageFactory.text(var0, EnumChatFormatting.LIGHT_PURPLE).append(" » ", EnumChatFormatting.GRAY);
   }

   public static TextMessage prefix() {
      return MessageFactory.text("Novoline", EnumChatFormatting.LIGHT_PURPLE).append(" » ", EnumChatFormatting.GRAY);
   }
}
