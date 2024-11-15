package exhibition.management.command.impl;

import exhibition.management.command.Command;
import exhibition.management.friend.FriendManager;
import exhibition.util.misc.ChatUtil;
import net.minecraft.util.EnumChatFormatting;

public class Friend extends Command {
   public Friend(String[] names, String description) {
      super(names, description);
   }

   public void fire(String[] args) {
      if (args != null && args.length >= 2) {
         try {
            if (!args[0].equalsIgnoreCase("add") && !args[0].equalsIgnoreCase("a")) {
               if (args[0].equalsIgnoreCase("del") || args[0].equalsIgnoreCase("d")) {
                  if (FriendManager.isFriend(args[1])) {
                     FriendManager.removeFriend(args[1]);
                     ChatUtil.printChat("§4[§cE§4]§8 Removed friend: " + args[1]);
                  } else {
                     ChatUtil.printChat("§4[§cE§4]§8 " + args[1] + " is not your friend.");
                  }
               }
            } else {
               if (FriendManager.isFriend(args[1])) {
                  ChatUtil.printChat("§4[§cE§4]§8 " + args[1] + " is already your friend.");
               }

               FriendManager.removeFriend(args[1]);
               FriendManager.addFriend(args[1], args.length == 3 ? args[2] : args[1]);
               ChatUtil.printChat("§4[§cE§4]§8 Added " + args[1]);
            }
         } catch (NullPointerException var3) {
            this.printUsage();
         }

      } else {
         this.printUsage();
      }
   }

   public String getUsage() {
      return "friend <add/del> " + EnumChatFormatting.RESET + "<name> " + EnumChatFormatting.RESET + "<alias>";
   }
}
