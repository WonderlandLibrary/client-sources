package net.shadersmod.client;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.optifine.Config;
import net.optifine.Lang;
import net.optifine.StrUtils;
import net.shadersmod.client.ShaderOption;

public class ShaderOptionSwitch extends ShaderOption {
   private static final Pattern PATTERN_DEFINE = Pattern.compile("^\\s*(//)?\\s*#define\\s+([A-Za-z0-9_]+)\\s*(//.*)?$");
   private static final Pattern PATTERN_IFDEF = Pattern.compile("^\\s*#if(n)?def\\s+([A-Za-z0-9_]+)(\\s*)?$");

   public ShaderOptionSwitch(String var1, String var2, String var3, String var4) {
      super(var1, var2, var3, new String[]{"true", "false"}, var3, var4);
   }

   public String getSourceLine() {
      String[] var1 = ShaderOption.p();
      return isTrue(this.getValue())?"#define " + this.getName() + " // Shader option ON":"//#define " + this.getName() + " // Shader option OFF";
   }

   public String getValueText(String var1) {
      String[] var2 = ShaderOption.p();
      return isTrue(var1)?Lang.getOn():Lang.getOff();
   }

   public String getValueColor(String var1) {
      String[] var2 = ShaderOption.p();
      return isTrue(var1)?"§a":"§c";
   }

   public static ShaderOption parseOption(String var0, String var1) {
      ShaderOption.p();
      Matcher var3 = PATTERN_DEFINE.matcher(var0);
      if(!var3.matches()) {
         return null;
      } else {
         String var4 = var3.group(1);
         String var5 = var3.group(2);
         String var6 = var3.group(3);
         if(var5 != null && !var5.isEmpty()) {
            boolean var7 = Config.equals(var4, "//");
            boolean var8 = !var7;
            var1 = StrUtils.removePrefix(var1, "/shaders/");
            ShaderOptionSwitch var9 = new ShaderOptionSwitch(var5, var6, String.valueOf(var8), var1);
            return var9;
         } else {
            return null;
         }
      }
   }

   public boolean matchesLine(String var1) {
      ShaderOption.p();
      Matcher var3 = PATTERN_DEFINE.matcher(var1);
      if(!var3.matches()) {
         return false;
      } else {
         String var4 = var3.group(2);
         return var4.matches(this.getName());
      }
   }

   public boolean checkUsed() {
      return true;
   }

   public boolean isUsedInLine(String var1) {
      ShaderOption.p();
      Matcher var3 = PATTERN_IFDEF.matcher(var1);
      if(var3.matches()) {
         String var4 = var3.group(2);
         return var4.equals(this.getName());
      } else {
         return false;
      }
   }

   public static boolean isTrue(String var0) {
      return Boolean.valueOf(var0).booleanValue();
   }
}
