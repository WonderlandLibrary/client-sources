package exhibition.module.data;

import java.util.HashMap;
import java.util.Iterator;

public class SettingsMap extends HashMap {
   public void update(HashMap newMap) {
      Iterator var2 = newMap.keySet().iterator();

      while(var2.hasNext()) {
         String key = (String)var2.next();
         if (this.containsKey(key)) {
            ((Setting)this.get(key)).update((Setting)newMap.get(key));
         }
      }

   }
}
