package rina.turok.bope.bopemod.manager;

import java.util.ArrayList;
import java.util.Iterator;
import rina.turok.bope.bopemod.BopeFriend;

public class BopeFriendManager {
   private ArrayList array_friend = new ArrayList();
   String tag;

   public BopeFriendManager(String tag) {
      this.tag = tag;
   }

   public void add_friend(String name) {
      this.array_friend.add(new BopeFriend(name));
   }

   public void remove_friend(BopeFriend friend) {
      this.array_friend.remove(friend);
   }

   public void clear() {
      this.array_friend.clear();
   }

   public ArrayList get_array_friends() {
      return this.array_friend;
   }

   public BopeFriend get_friend_with_name(String name) {
      BopeFriend friend_requested = null;
      Iterator var3 = this.get_array_friends().iterator();

      while(var3.hasNext()) {
         BopeFriend friends = (BopeFriend)var3.next();
         if (friends.get_name().equalsIgnoreCase(name)) {
            friend_requested = friends;
         }
      }

      return friend_requested;
   }

   public boolean is_friend(String name) {
      boolean state = false;
      BopeFriend friend_requested = this.get_friend_with_name(name);
      if (friend_requested != null) {
         state = true;
      }

      return state;
   }

   public String get_tag() {
      return this.tag;
   }
}
