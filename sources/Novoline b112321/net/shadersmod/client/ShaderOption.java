package net.shadersmod.client;

import java.util.Arrays;
import java.util.List;
import net.optifine.Config;
import net.optifine.StrUtils;
import net.shadersmod.client.Shaders;

public abstract class ShaderOption {
   private String name = null;
   private String description = null;
   private String value = null;
   private String[] values = null;
   private String valueDefault = null;
   private String[] paths = null;
   private boolean enabled = true;
   private boolean visible = true;
   public static final String g = "§a";
   public static final String h = "§c";
   public static final String a = "§9";
   private static String[] l;

   public ShaderOption(String var1, String var2, String var3, String[] var4, String var5, String var6) {
      this.name = var1;
      this.description = var2;
      p();
      this.value = var3;
      this.values = var4;
      this.valueDefault = var5;
      this.paths = new String[]{var6};
   }

   public String getName() {
      return this.name;
   }

   public String getDescription() {
      return this.description;
   }

   public String getDescriptionText() {
      String var1 = Config.c(this.description);
      var1 = StrUtils.removePrefix(var1, "//");
      var1 = Shaders.translate("option." + this.getName() + ".comment", var1);
      return var1;
   }

   public void setDescription(String var1) {
      this.description = var1;
   }

   public String getValue() {
      return this.value;
   }

   public boolean setValue(String var1) {
      p();
      int var3 = getIndex(var1, this.values);
      if(var3 < 0) {
         return false;
      } else {
         this.value = var1;
         return true;
      }
   }

   public String getValueDefault() {
      return this.valueDefault;
   }

   public void resetValue() {
      this.value = this.valueDefault;
   }

   public void nextValue() {
      p();
      int var2 = getIndex(this.value, this.values);
      if(var2 >= 0) {
         var2 = (var2 + 1) % this.values.length;
         this.value = this.values[var2];
      }

   }

   public void prevValue() {
      p();
      int var2 = getIndex(this.value, this.values);
      if(var2 >= 0) {
         var2 = (var2 - 1 + this.values.length) % this.values.length;
         this.value = this.values[var2];
      }

   }

   private static int getIndex(String var0, String[] var1) {
      p();
      int var3 = 0;
      if(var3 < var1.length) {
         String var4 = var1[var3];
         if(var4.equals(var0)) {
            return var3;
         }

         ++var3;
      }

      return -1;
   }

   public String[] getPaths() {
      return this.paths;
   }

   public void addPaths(String[] var1) {
      List var3 = Arrays.asList(this.paths);
      String[] var2 = p();
      int var5 = var1.length;
      int var6 = 0;
      if(var6 < var5) {
         String var7 = var1[var6];
         if(!var3.contains(var7)) {
            this.paths = (String[])((String[])((String[])Config.addObjectToArray(this.paths, var7)));
         }

         ++var6;
      }

   }

   public boolean isEnabled() {
      return this.enabled;
   }

   public void setEnabled(boolean var1) {
      this.enabled = var1;
   }

   public boolean isChanged() {
      String[] var1 = p();
      return !Config.equals(this.value, this.valueDefault);
   }

   public boolean isVisible() {
      return this.visible;
   }

   public void setVisible(boolean var1) {
      this.visible = var1;
   }

   public boolean isValidValue(String var1) {
      String[] var2 = p();
      return getIndex(var1, this.values) >= 0;
   }

   public String getNameText() {
      return Shaders.translate("option." + this.name, this.name);
   }

   public String getValueText(String var1) {
      return Shaders.translate("value." + this.name + "." + var1, var1);
   }

   public String getValueColor(String var1) {
      return "";
   }

   public boolean matchesLine(String var1) {
      return false;
   }

   public boolean checkUsed() {
      return false;
   }

   public boolean isUsedInLine(String var1) {
      return false;
   }

   public String getSourceLine() {
      return null;
   }

   public String[] getValues() {
      return (String[])((String[])this.values.clone());
   }

   public String toString() {
      return "" + this.name + ", value: " + this.value + ", valueDefault: " + this.valueDefault + ", paths: " + Config.a((Object[])this.paths);
   }

   public static void b(String[] var0) {
      l = var0;
   }

   public static String[] p() {
      return l;
   }

   static {
      b((String[])null);
   }
}
