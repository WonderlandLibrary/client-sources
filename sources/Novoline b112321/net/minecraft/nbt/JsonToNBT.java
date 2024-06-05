package net.minecraft.nbt;

import java.util.Stack;
import java.util.regex.Pattern;
import net.VF;
import net.minecraft.nbt.JsonToNBT$Any;
import net.minecraft.nbt.JsonToNBT$Compound;
import net.minecraft.nbt.JsonToNBT$List;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTTagCompound;

public final class JsonToNBT {
   private static final Pattern field_179273_b = Pattern.compile("\\[[-+\\d|,\\s]+]");

   public static NBTTagCompound getTagFromJson(String var0) throws NBTException {
      var0 = var0.trim();
      if(!var0.startsWith("{")) {
         throw new NBTException("Invalid tag encountered, expected \'{\' as first char.");
      } else if(func_150310_b(var0) != 1) {
         throw new NBTException("Encountered multiple top tags, only one expected");
      } else {
         return (NBTTagCompound)func_150316_a("tag", var0).parse();
      }
   }

   static int func_150310_b(String var0) throws NBTException {
      int var1 = 0;
      boolean var2 = false;
      Stack var3 = new Stack();

      for(int var4 = 0; var4 < var0.length(); ++var4) {
         char var5 = var0.charAt(var4);
         if(var5 == 34) {
            if(func_179271_b(var0, var4)) {
               throw new NBTException("Illegal use of \\\": " + var0);
            }

            var2 = true;
         } else if(var5 != 123 && var5 != 91) {
            if(var5 == 125 && (var3.isEmpty() || ((Character)var3.pop()).charValue() != 123)) {
               throw new NBTException("Unbalanced curly brackets {}: " + var0);
            }

            if(var5 == 93 && (var3.isEmpty() || ((Character)var3.pop()).charValue() != 91)) {
               throw new NBTException("Unbalanced square brackets []: " + var0);
            }
         } else {
            if(var3.isEmpty()) {
               ++var1;
            }

            var3.push(Character.valueOf(var5));
         }
      }

      throw new NBTException("Unbalanced quotation: " + var0);
   }

   static JsonToNBT$Any func_179272_a(String... var0) throws NBTException {
      return func_150316_a(var0[0], var0[1]);
   }

   static JsonToNBT$Any func_150316_a(String var0, String var1) throws NBTException {
      var1 = var1.trim();
      if(var1.startsWith("{")) {
         var1 = var1.substring(1, var1.length() - 1);

         JsonToNBT$Compound var8;
         String var9;
         for(var8 = new JsonToNBT$Compound(var0); !var1.isEmpty(); var1 = var1.substring(var9.length() + 1)) {
            var9 = func_150314_a(var1, true);
            if(!var9.isEmpty()) {
               boolean var11 = false;
               var8.field_150491_b.add(func_179270_a(var9, false));
            }

            if(var1.length() < var9.length() + 1) {
               break;
            }

            char var12 = var1.charAt(var9.length());
            if(var12 != 44 && var12 != 123 && var12 != 125 && var12 != 91 && var12 != 93) {
               throw new NBTException("Unexpected token \'" + var12 + "\' at: " + var1.substring(var9.length()));
            }
         }

         return var8;
      } else if(var1.startsWith("[") && !field_179273_b.matcher(var1).matches()) {
         var1 = var1.substring(1, var1.length() - 1);

         JsonToNBT$List var2;
         String var3;
         for(var2 = new JsonToNBT$List(var0); !var1.isEmpty(); var1 = var1.substring(var3.length() + 1)) {
            var3 = func_150314_a(var1, false);
            if(!var3.isEmpty()) {
               boolean var4 = true;
               var2.field_150492_b.add(func_179270_a(var3, true));
            }

            if(var1.length() < var3.length() + 1) {
               break;
            }

            char var10 = var1.charAt(var3.length());
            if(var10 != 44 && var10 != 123 && var10 != 125 && var10 != 91 && var10 != 93) {
               throw new NBTException("Unexpected token \'" + var10 + "\' at: " + var1.substring(var3.length()));
            }
         }

         return var2;
      } else {
         return new VF(var0, var1);
      }
   }

   private static JsonToNBT$Any func_179270_a(String var0, boolean var1) throws NBTException {
      String var2 = func_150313_b(var0, var1);
      String var3 = func_150311_c(var0, var1);
      return func_179272_a(new String[]{var2, var3});
   }

   private static String func_150314_a(String var0, boolean var1) throws NBTException {
      int var2 = func_150312_a(var0, ':');
      int var3 = func_150312_a(var0, ',');
      if(var2 == -1) {
         throw new NBTException("Unable to locate name/value separator for string: " + var0);
      } else if(var3 != -1 && var3 < var2) {
         throw new NBTException("Name error at: " + var0);
      } else {
         return func_179269_a(var0, var2);
      }
   }

   private static String func_179269_a(String var0, int var1) throws NBTException {
      Stack var2 = new Stack();
      int var3 = var1 + 1;
      boolean var4 = false;
      boolean var5 = false;
      boolean var6 = false;

      for(int var7 = 0; var3 < var0.length(); ++var3) {
         char var8 = var0.charAt(var3);
         if(var8 == 34) {
            if(func_179271_b(var0, var3)) {
               throw new NBTException("Illegal use of \\\": " + var0);
            }

            var4 = true;
            var5 = true;
            var7 = var3;
         } else if(var8 != 123 && var8 != 91) {
            if(var8 == 125 && (var2.isEmpty() || ((Character)var2.pop()).charValue() != 123)) {
               throw new NBTException("Unbalanced curly brackets {}: " + var0);
            }

            if(var8 == 93 && (var2.isEmpty() || ((Character)var2.pop()).charValue() != 91)) {
               throw new NBTException("Unbalanced square brackets []: " + var0);
            }

            if(var8 == 44 && var2.isEmpty()) {
               return var0.substring(0, var3);
            }
         } else {
            var2.push(Character.valueOf(var8));
         }

         if(!Character.isWhitespace(var8)) {
            if(var7 != var3) {
               return var0.substring(0, var7 + 1);
            }

            var6 = true;
         }
      }

      return var0.substring(0, var3);
   }

   private static String func_150313_b(String var0, boolean var1) throws NBTException {
      var0 = var0.trim();
      if(!var0.startsWith("{") && !var0.startsWith("[")) {
         int var2 = func_150312_a(var0, ':');
         return var2 == -1?"":var0.substring(0, var2).trim();
      } else {
         return "";
      }
   }

   private static String func_150311_c(String var0, boolean var1) throws NBTException {
      var0 = var0.trim();
      if(!var0.startsWith("{") && !var0.startsWith("[")) {
         int var2 = func_150312_a(var0, ':');
         return var2 == -1?var0:var0.substring(var2 + 1).trim();
      } else {
         return var0;
      }
   }

   private static int func_150312_a(String var0, char var1) {
      int var2 = 0;

      for(boolean var3 = true; var2 < var0.length(); ++var2) {
         char var4 = var0.charAt(var2);
         if(var4 == 34) {
            if(!func_179271_b(var0, var2)) {
               var3 = true;
            }
         } else {
            if(var4 == var1) {
               return var2;
            }

            if(var4 == 123 || var4 == 91) {
               return -1;
            }
         }
      }

      return -1;
   }

   private static boolean func_179271_b(String var0, int var1) {
      return var0.charAt(var1 - 1) == 92 && !func_179271_b(var0, var1 - 1);
   }

   private static NBTException a(NBTException var0) {
      return var0;
   }
}
