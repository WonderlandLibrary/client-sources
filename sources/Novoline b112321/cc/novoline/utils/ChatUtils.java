package cc.novoline.utils;

import cc.novoline.utils.ChatUtils$1;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;

public final class ChatUtils {
   private final ChatComponentText message;

   private ChatUtils(ChatComponentText var1) {
      this.message = var1;
   }

   public static String addFormat(String var0, String var1) {
      return var0.replaceAll("(?i)" + var1 + "([0-9a-fklmnor])", "§$1");
   }

   public void displayClientSided() {
      Minecraft.getInstance().player.addChatMessage(this.message);
   }

   private ChatComponentText getChatComponent() {
      return this.message;
   }

   static ChatComponentText access$000(ChatUtils var0) {
      return var0.getChatComponent();
   }

   ChatUtils(ChatComponentText var1, ChatUtils$1 var2) {
      this(var1);
   }
}
