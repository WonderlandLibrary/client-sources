package net.shadersmod.client;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.optifine.StrUtils;
import net.shadersmod.client.ShaderOption;
import net.shadersmod.client.ShaderOptionSwitch;

public class ShaderOptionSwitchConst extends ShaderOptionSwitch {
   private static final Pattern PATTERN_CONST = Pattern.compile("^\\s*const\\s*bool\\s*([A-Za-z0-9_]+)\\s*=\\s*(true|false)\\s*;\\s*(//.*)?$");

   public ShaderOptionSwitchConst(String var1, String var2, String var3, String var4) {
      super(var1, var2, var3, var4);
   }

   public String getSourceLine() {
      return "const bool " + this.getName() + " = " + this.getValue() + "; // Shader option " + this.getValue();
   }

   public static ShaderOption parseOption(String var0, String var1) {
      ShaderOption.p();
      Matcher var3 = PATTERN_CONST.matcher(var0);
      if(!var3.matches()) {
         return null;
      } else {
         String var4 = var3.group(1);
         String var5 = var3.group(2);
         String var6 = var3.group(3);
         if(var4 != null && !var4.isEmpty()) {
            var1 = StrUtils.removePrefix(var1, "/shaders/");
            ShaderOptionSwitchConst var7 = new ShaderOptionSwitchConst(var4, var6, var5, var1);
            var7.setVisible(false);
            return var7;
         } else {
            return null;
         }
      }
   }

   public boolean matchesLine(String var1) {
      ShaderOption.p();
      Matcher var3 = PATTERN_CONST.matcher(var1);
      if(!var3.matches()) {
         return false;
      } else {
         String var4 = var3.group(1);
         return var4.matches(this.getName());
      }
   }

   public boolean checkUsed() {
      return false;
   }
}
