package com.example.editme.util.command.syntax.parsers;

import com.example.editme.util.command.syntax.SyntaxChunk;
import java.util.ArrayList;
import java.util.Collections;

public class EnumParser extends AbstractParser {
   String[] modes;

   public EnumParser(String[] var1) {
      this.modes = var1;
   }

   public String getChunk(SyntaxChunk[] var1, SyntaxChunk var2, String[] var3, String var4) {
      String[] var6;
      int var7;
      int var8;
      String var9;
      if (var4 != null) {
         ArrayList var10 = new ArrayList();
         var6 = this.modes;
         var7 = var6.length;

         for(var8 = 0; var8 < var7; ++var8) {
            var9 = var6[var8];
            if (var9.toLowerCase().startsWith(var4.toLowerCase())) {
               var10.add(var9);
            }
         }

         if (var10.isEmpty()) {
            return "";
         } else {
            Collections.sort(var10);
            String var11 = (String)var10.get(0);
            return var11.substring(var4.length());
         }
      } else {
         String var5 = "";
         var6 = this.modes;
         var7 = var6.length;

         for(var8 = 0; var8 < var7; ++var8) {
            var9 = var6[var8];
            var5 = String.valueOf((new StringBuilder()).append(var5).append(var9).append(":"));
         }

         var5 = var5.substring(0, var5.length() - 1);
         return String.valueOf((new StringBuilder()).append(var2.isHeadless() ? "" : var2.getHead()).append(var2.isNecessary() ? "<" : "[").append(var5).append(var2.isNecessary() ? ">" : "]"));
      }
   }
}
