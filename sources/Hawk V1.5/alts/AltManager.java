package alts;

import java.util.ArrayList;

public class AltManager {
   public static Alt lastAlt;
   public static ArrayList<Alt> registry = new ArrayList();

   public void setLastAlt(Alt var1) {
      lastAlt = var1;
   }

   public ArrayList<Alt> getRegistry() {
      return registry;
   }
}
