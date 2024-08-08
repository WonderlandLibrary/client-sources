package com.example.editme.util.setting;

import com.example.editme.settings.Setting;
import com.example.editme.util.client.Pair;
import java.util.HashMap;
import java.util.StringTokenizer;

public class SettingsRegister {
   public static final SettingsRegister ROOT = new SettingsRegister();
   public HashMap settingHashMap = new HashMap();
   public HashMap registerHashMap = new HashMap();

   private static Pair dig(String var0) {
      SettingsRegister var1 = ROOT;
      StringTokenizer var2 = new StringTokenizer(var0, ".");
      String var3 = null;

      while(var2.hasMoreTokens()) {
         if (var3 == null) {
            var3 = var2.nextToken();
         } else {
            String var4 = var2.nextToken();
            var1 = var1.subregister(var3);
            var3 = var4;
         }
      }

      return new Pair(var3 == null ? "" : var3, var1);
   }

   public SettingsRegister subregister(String var1) {
      if (this.registerHashMap.containsKey(var1)) {
         return (SettingsRegister)this.registerHashMap.get(var1);
      } else {
         SettingsRegister var2 = new SettingsRegister();
         this.registerHashMap.put(var1, var2);
         return var2;
      }
   }

   public Setting getSetting(String var1) {
      return (Setting)this.settingHashMap.get(var1);
   }

   private void put(String var1, Setting var2) {
      this.settingHashMap.put(var1, var2);
   }

   public static Setting get(String var0) {
      Pair var1 = dig(var0);
      return ((SettingsRegister)var1.getValue()).getSetting((String)var1.getKey());
   }

   public static void register(String var0, Setting var1) {
      Pair var2 = dig(var0);
      ((SettingsRegister)var2.getValue()).put((String)var2.getKey(), var1);
   }
}
