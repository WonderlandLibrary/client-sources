package exhibition.module.data;

import java.util.Iterator;
import java.util.List;

public class MultiBool {
   private List booleans;
   private String name;

   public MultiBool(String name, List settings) {
      this.booleans = settings;
      this.name = name;
   }

   public String getName() {
      return this.name;
   }

   public List getBooleans() {
      return this.booleans;
   }

   public Setting getSetting(String name) {
      Iterator var2 = this.booleans.iterator();

      Setting setting;
      do {
         if (!var2.hasNext()) {
            return null;
         }

         setting = (Setting)var2.next();
      } while(!setting.getName().equalsIgnoreCase(name));

      return setting;
   }

   public boolean getValue(String name) {
      Iterator var2 = this.booleans.iterator();

      Setting setting;
      do {
         if (!var2.hasNext()) {
            return false;
         }

         setting = (Setting)var2.next();
      } while(!setting.getName().equalsIgnoreCase(name));

      return ((Boolean)setting.getValue()).booleanValue();
   }
}
