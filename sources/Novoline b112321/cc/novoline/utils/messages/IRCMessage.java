package cc.novoline.utils.messages;

import cc.novoline.utils.messages.TextMessage;
import net.Ux;
import net.acE;
import net.minecraft.util.EnumChatFormatting;
import org.jetbrains.annotations.NotNull;

public class IRCMessage extends TextMessage {
   private static final String IRC_PREFIX = EnumChatFormatting.LIGHT_PURPLE + "IRC" + EnumChatFormatting.GRAY + " » " + EnumChatFormatting.RESET;

   protected IRCMessage(@NotNull String var1) {
      int var10000 = Ux.b();
      super(IRC_PREFIX);
      int var2 = var10000;
      this.append(" Broadcast: ", EnumChatFormatting.RED);
      this.append(var1, EnumChatFormatting.WHITE);
      if(acE.b() == null) {
         ++var2;
         Ux.b(var2);
      }

   }

   @NotNull
   public static IRCMessage of(@NotNull String var0) {
      return new IRCMessage(var0);
   }

   private static EnumChatFormatting getColor(String var0) {
      Ux.a();
      byte var3 = -1;
      switch(var0.hashCode()) {
      case 76514:
         if(!var0.equals("MOD")) {
            break;
         }

         var3 = 0;
      case 62130991:
         if(var0.equals("ADMIN")) {
            var3 = 1;
         }
      }

      switch(var3) {
      case 0:
         return EnumChatFormatting.DARK_PURPLE;
      case 1:
         return EnumChatFormatting.RED;
      default:
         return EnumChatFormatting.GRAY;
      }
   }

   static String access$000() {
      return IRC_PREFIX;
   }

   static EnumChatFormatting access$100(String var0) {
      return getColor(var0);
   }
}
