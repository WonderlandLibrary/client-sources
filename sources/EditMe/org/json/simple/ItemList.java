package org.json.simple;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class ItemList {
   private String sp = ",";
   List items = new ArrayList();

   public ItemList() {
   }

   public ItemList(String var1) {
      this.split(var1, this.sp, this.items);
   }

   public ItemList(String var1, String var2) {
      this.sp = var1;
      this.split(var1, var2, this.items);
   }

   public ItemList(String var1, String var2, boolean var3) {
      this.split(var1, var2, this.items, var3);
   }

   public List getItems() {
      return this.items;
   }

   public String[] getArray() {
      return (String[])((String[])this.items.toArray());
   }

   public void split(String var1, String var2, List var3, boolean var4) {
      if (var1 != null && var2 != null) {
         if (var4) {
            StringTokenizer var5 = new StringTokenizer(var1, var2);

            while(var5.hasMoreTokens()) {
               var3.add(var5.nextToken().trim());
            }
         } else {
            this.split(var1, var2, var3);
         }

      }
   }

   public void split(String var1, String var2, List var3) {
      if (var1 != null && var2 != null) {
         int var4 = 0;
         boolean var5 = false;

         int var6;
         do {
            var6 = var4;
            var4 = var1.indexOf(var2, var4);
            if (var4 == -1) {
               break;
            }

            var3.add(var1.substring(var6, var4).trim());
            var4 += var2.length();
         } while(var4 != -1);

         var3.add(var1.substring(var6).trim());
      }
   }

   public void setSP(String var1) {
      this.sp = var1;
   }

   public void add(int var1, String var2) {
      if (var2 != null) {
         this.items.add(var1, var2.trim());
      }
   }

   public void add(String var1) {
      if (var1 != null) {
         this.items.add(var1.trim());
      }
   }

   public void addAll(ItemList var1) {
      this.items.addAll(var1.items);
   }

   public void addAll(String var1) {
      this.split(var1, this.sp, this.items);
   }

   public void addAll(String var1, String var2) {
      this.split(var1, var2, this.items);
   }

   public void addAll(String var1, String var2, boolean var3) {
      this.split(var1, var2, this.items, var3);
   }

   public String get(int var1) {
      return (String)this.items.get(var1);
   }

   public int size() {
      return this.items.size();
   }

   public String toString() {
      return this.toString(this.sp);
   }

   public String toString(String var1) {
      StringBuffer var2 = new StringBuffer();

      for(int var3 = 0; var3 < this.items.size(); ++var3) {
         if (var3 == 0) {
            var2.append(this.items.get(var3));
         } else {
            var2.append(var1);
            var2.append(this.items.get(var3));
         }
      }

      return var2.toString();
   }

   public void clear() {
      this.items.clear();
   }

   public void reset() {
      this.sp = ",";
      this.items.clear();
   }
}
