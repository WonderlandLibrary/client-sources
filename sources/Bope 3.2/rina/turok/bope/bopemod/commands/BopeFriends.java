package rina.turok.bope.bopemod.commands;

import java.util.Iterator;
import rina.turok.bope.Bope;
import rina.turok.bope.bopemod.BopeCommand;
import rina.turok.bope.bopemod.BopeFriend;
import rina.turok.bope.bopemod.BopeMessage;

public class BopeFriends extends BopeCommand {
   public BopeFriends() {
      super("friends", "For get list friends.");
   }

   public boolean get_message(String[] message) {
      if (message.length > 1) {
         BopeMessage.send_client_error_message("friends");
         return true;
      } else {
         if (Bope.get_friend_manager().get_array_friends().isEmpty()) {
            BopeMessage.send_client_message("No friends :(");
         }

         int count = 0;
         int size = Bope.get_friend_manager().get_array_friends().size();
         StringBuilder names = new StringBuilder();
         Iterator var5 = Bope.get_friend_manager().get_array_friends().iterator();

         while(var5.hasNext()) {
            BopeFriend friends = (BopeFriend)var5.next();
            ++count;
            if (count >= size) {
               names.append(Bope.dg + friends.get_name() + Bope.r + ".");
            } else {
               names.append(Bope.dg + friends.get_name() + Bope.r + ", ");
            }
         }

         BopeMessage.send_client_message(names.toString());
         return true;
      }
   }
}
