package vestige.util.misc;

import java.util.Objects;
import net.minecraft.util.ChatComponentText;
import vestige.Flap;
import vestige.util.IMinecraft;

public class LogUtil implements IMinecraft {
   private static final String prefix;

   public static void print(Object message) {
      System.out.println(prefix + " " + message);
   }

   public static final String fix(String string) {
      return string.replace("&", "§").replace(">>", "»").replace("<<", "«").replace("->", "→").replace("<-", "←");
   }

   public static void addChatMessage(String message) {
      mc.thePlayer.addChatMessage(new ChatComponentText(fix(message)));
   }

   static {
      StringBuilder var10000 = (new StringBuilder()).append("[");
      Objects.requireNonNull(Flap.instance);
      prefix = var10000.append("Flap").append("]").toString();
   }
}
