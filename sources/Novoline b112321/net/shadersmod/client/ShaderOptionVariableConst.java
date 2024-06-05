package net.shadersmod.client;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.optifine.StrUtils;
import net.shadersmod.client.ShaderOption;
import net.shadersmod.client.ShaderOptionVariable;

public class ShaderOptionVariableConst extends ShaderOptionVariable {
   private String type = null;
   private static final Pattern PATTERN_CONST = Pattern.compile("^\\s*const\\s*(float|int)\\s*([A-Za-z0-9_]+)\\s*=\\s*(-?[0-9\\.]+f?F?)\\s*;\\s*(//.*)?$");

   public ShaderOptionVariableConst(String var1, String var2, String var3, String var4, String[] var5, String var6) {
      super(var1, var3, var4, var5, var6);
      this.type = var2;
   }

   public String getSourceLine() {
      return "const " + this.type + " " + this.getName() + " = " + this.getValue() + "; // Shader option " + this.getValue();
   }

   public boolean matchesLine(String var1) {
      ShaderOption.p();
      Matcher var3 = PATTERN_CONST.matcher(var1);
      if(!var3.matches()) {
         return false;
      } else {
         String var4 = var3.group(2);
         return var4.matches(this.getName());
      }
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
         String var7 = var3.group(4);
         String var8 = StrUtils.getSegment(var7, "[", "]");
         if(var8 != null && !var8.isEmpty()) {
            var7 = var7.replace(var8, "").trim();
         }

         String[] var9 = parseValues(var6, var8);
         if(var5 != null && !var5.isEmpty()) {
            var1 = StrUtils.removePrefix(var1, "/shaders/");
            ShaderOptionVariableConst var10 = new ShaderOptionVariableConst(var5, var4, var7, var6, var9, var1);
            return var10;
         } else {
            return null;
         }
      }
   }
}
