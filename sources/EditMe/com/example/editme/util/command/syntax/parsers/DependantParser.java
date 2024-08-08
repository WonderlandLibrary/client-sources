package com.example.editme.util.command.syntax.parsers;

import com.example.editme.util.command.syntax.SyntaxChunk;

public class DependantParser extends AbstractParser {
   int dependantIndex;
   private DependantParser.Dependency dependancy;

   public String getChunk(SyntaxChunk[] var1, SyntaxChunk var2, String[] var3, String var4) {
      if (var4 != null && !var4.equals("")) {
         return "";
      } else if (var3.length <= this.dependantIndex) {
         return this.getDefaultChunk(var2);
      } else {
         return var3[this.dependantIndex] != null && !var3[this.dependantIndex].equals("") ? this.dependancy.feed(var3[this.dependantIndex]) : "";
      }
   }

   public DependantParser(int var1, DependantParser.Dependency var2) {
      this.dependantIndex = var1;
      this.dependancy = var2;
   }

   protected String getDefaultChunk(SyntaxChunk var1) {
      return this.dependancy.getEscape();
   }

   public static class Dependency {
      String[][] map = new String[0][];
      String escape;

      public String[][] getMap() {
         return this.map;
      }

      public String getEscape() {
         return this.escape;
      }

      public String feed(String var1) {
         String[] var2 = this.containsKey(this.map, var1);
         return var2 != null ? var2[1] : this.getEscape();
      }

      public Dependency(String[][] var1, String var2) {
         this.map = var1;
         this.escape = var2;
      }

      private String[] containsKey(String[][] var1, String var2) {
         String[][] var3 = var1;
         int var4 = var1.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            String[] var6 = var3[var5];
            if (var6[0].equals(var2)) {
               return var6;
            }
         }

         return null;
      }
   }
}
