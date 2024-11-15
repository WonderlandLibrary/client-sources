package exhibition.management.command;

import exhibition.util.MinecraftUtil;
import exhibition.util.misc.ChatUtil;
import java.util.Arrays;
import net.minecraft.client.Minecraft;
import net.minecraft.util.EnumChatFormatting;

public abstract class Command implements Fireable, MinecraftUtil {
   private final String[] names;
   private final String description;
   public static final String chatPrefix = "§4[§cE§4]§8 ";
   public Minecraft mc = Minecraft.getMinecraft();

   public Command(String[] names, String description) {
      this.names = names;
      this.description = description;
   }

   protected void printDescription() {
      String message = "§4[§cE§4]§8 " + this.getName() + EnumChatFormatting.GRAY + ": " + this.description;
      ChatUtil.printChat(message);
   }

   protected void printUsage() {
      String message = "§4[§cE§4]§8 " + this.getName() + EnumChatFormatting.GRAY + ": " + this.getUsage();
      ChatUtil.printChat(message);
   }

   public void register(CommandManager manager) {
      String[] var2 = this.names;
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         String name = var2[var4];
         manager.addCommand(name.toLowerCase(), this);
      }

   }

   public abstract String getUsage();

   public String getName() {
      return this.names[0];
   }

   public boolean isMatch(String text) {
      return Arrays.asList(this.names).contains(text.toLowerCase());
   }

   public String getDescription() {
      return this.description;
   }
}
