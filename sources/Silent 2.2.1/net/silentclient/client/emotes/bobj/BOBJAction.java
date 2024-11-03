package net.silentclient.client.emotes.bobj;

import java.util.HashMap;
import java.util.Map;

public class BOBJAction {
   public String name;
   public Map<String, BOBJGroup> groups = new HashMap<>();

   public BOBJAction(String s) {
      this.name = s;
   }

   public int getDuration() {
      int i = 0;

      for (BOBJGroup bobjgroup : this.groups.values()) {
         i = Math.max(i, bobjgroup.getDuration());
      }

      return i;
   }
}
