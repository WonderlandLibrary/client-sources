package rina.turok.bope.bopemod.commands;

import java.util.ArrayList;
import java.util.Random;
import net.minecraft.client.network.NetworkPlayerInfo;
import rina.turok.bope.Bope;
import rina.turok.bope.bopemod.BopeCommand;
import rina.turok.bope.bopemod.BopeFriend;
import rina.turok.bope.bopemod.BopeMessage;

public class BopeFriendC extends BopeCommand {
   String[] errors_for_add = new String[]{"This account does not exist", "Who?", "Huh, who?", "Added Rina as your friend.", "????Who??"};
   String[] errors_for_remove = new String[]{"This account does not exist", "Does this person exist?", "Sorry but you have imaginary friends...", "Who is this person?", ":("};

   public BopeFriendC() {
      super("friend", "For add friend or remove.");
   }

   public boolean get_message(String[] message) {
      String type = "null";
      String friend = "null";
      if (message.length > 1) {
         type = message[1];
      }

      if (message.length > 2) {
         friend = message[2];
      }

      if (message.length > 3) {
         BopeMessage.send_client_error_message("friend add/new/rem/remove/del/delete name");
         return true;
      } else if (type.equals("null")) {
         BopeMessage.send_client_error_message("friend add/new/rem/remove/del/delete name");
         return true;
      } else if (friend.equals("null")) {
         BopeMessage.send_client_error_message("friend add/new/rem/remove/del/delete name");
         return true;
      } else {
         boolean is_for = true;
         if (!type.equalsIgnoreCase("add") && !type.equalsIgnoreCase("new")) {
            if (type.equalsIgnoreCase("rem") || type.equalsIgnoreCase("remove") || type.equalsIgnoreCase("del") || type.equalsIgnoreCase("delete")) {
               is_for = false;
            }
         } else {
            is_for = true;
         }

         if (is_for) {
            ArrayList info_map = new ArrayList(this.mc.getConnection().getPlayerInfoMap());
            NetworkPlayerInfo profile = this.requested_player_from_server(info_map, friend);
            if (profile == null) {
               BopeMessage.send_client_error_message(this.random_funny(this.errors_for_add));
               return true;
            } else if (Bope.get_friend_manager().is_friend(friend)) {
               BopeMessage.send_client_message("Already added as friend.");
               return true;
            } else {
               Bope.get_friend_manager().add_friend(friend);
               BopeMessage.send_client_message("Added " + Bope.dg + friend + Bope.r + " as friend.");
               return true;
            }
         } else {
            BopeFriend friend_requested = Bope.get_friend_manager().get_friend_with_name(friend);
            if (friend_requested == null) {
               BopeMessage.send_client_error_message(this.random_funny(this.errors_for_remove));
               return true;
            } else {
               Bope.get_friend_manager().remove_friend(friend_requested);
               BopeMessage.send_client_message("Removed " + Bope.re + friend + Bope.r + " :(.");
               return true;
            }
         }
      }
   }

   public NetworkPlayerInfo requested_player_from_server(ArrayList list, String friend) {
      return (NetworkPlayerInfo)list.stream().filter((player) -> {
         //return player.getGameProfile().getName().equalsIgnoreCase(friend);
         return mc.player.getGameProfile().getName().equalsIgnoreCase(friend);
      }).findFirst().orElse((Object)null);
   }

   public String random_funny(String[] list) {
      return list[(new Random()).nextInt(list.length)];
   }
}
