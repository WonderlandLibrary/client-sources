package exhibition.management.command.impl;

import exhibition.management.command.Command;
import exhibition.util.misc.ChatUtil;

public class Say extends Command {
   public Say(String[] names, String description) {
      super(names, description);
   }

   public void fire(String[] args) {
      if (args != null) {
         if (args.length > 0) {
            StringBuilder out = new StringBuilder();
            String[] var3 = args;
            int var4 = args.length;

            for(int var5 = 0; var5 < var4; ++var5) {
               String word = var3[var5];
               out.append(word + " ");
            }

            String message = out.substring(0, out.length() - 1);
            message = message.replaceAll("&", "§");
            ChatUtil.printChat(message);
         }

      }
   }

   public String getUsage() {
      return "Say <Message>";
   }
}
