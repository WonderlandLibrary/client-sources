package net.shadersmod.client;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.optifine.Config;
import net.optifine.StrUtils;
import net.shadersmod.client.ShaderOption;

public class ShaderOptionVariable extends ShaderOption {
   private static final Pattern PATTERN_VARIABLE = Pattern.compile("^\\s*#define\\s+(\\w+)\\s+(-?[0-9\\.Ff]+|\\w+)\\s*(//.*)?$");

   public ShaderOptionVariable(String var1, String var2, String var3, String[] var4, String var5) {
      ShaderOption.p();
      super(var1, var2, var3, var4, var3, var5);
      this.setVisible(this.getValues().length > 1);
   }

   public String getSourceLine() {
      return "#define " + this.getName() + " " + this.getValue() + " // Shader option " + this.getValue();
   }

   public String getValueColor(String var1) {
      return "§a";
   }

   public boolean matchesLine(String var1) {
      ShaderOption.p();
      Matcher var3 = PATTERN_VARIABLE.matcher(var1);
      if(!var3.matches()) {
         return false;
      } else {
         String var4 = var3.group(1);
         return var4.matches(this.getName());
      }
   }

   public static ShaderOption parseOption(String var0, String var1) {
      ShaderOption.p();
      Matcher var3 = PATTERN_VARIABLE.matcher(var0);
      if(!var3.matches()) {
         return null;
      } else {
         String var4 = var3.group(1);
         String var5 = var3.group(2);
         String var6 = var3.group(3);
         String var7 = StrUtils.getSegment(var6, "[", "]");
         if(var7 != null && !var7.isEmpty()) {
            var6 = var6.replace(var7, "").trim();
         }

         String[] var8 = parseValues(var5, var7);
         if(var4 != null && !var4.isEmpty()) {
            var1 = StrUtils.removePrefix(var1, "/shaders/");
            ShaderOptionVariable var9 = new ShaderOptionVariable(var4, var6, var5, var8, var1);
            return var9;
         } else {
            return null;
         }
      }
   }

   public static String[] parseValues(String var0, String var1) {
      ShaderOption.p();
      String[] var3 = new String[]{var0};
      if(var1 == null) {
         return var3;
      } else {
         var1 = var1.trim();
         var1 = StrUtils.removePrefix(var1, "[");
         var1 = StrUtils.removeSuffix(var1, "]");
         var1 = var1.trim();
         if(var1.length() <= 0) {
            return var3;
         } else {
            String[] var4 = Config.tokenize(var1, " ");
            if(var4.length <= 0) {
               return var3;
            } else {
               if(!Arrays.asList(var4).contains(var0)) {
                  var4 = (String[])((String[])((String[])Config.addObjectToArray(var4, var0, 0)));
               }

               return var4;
            }
         }
      }
   }
}
