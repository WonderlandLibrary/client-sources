package org.yaml.snakeyaml.error;

import java.io.Serializable;
import org.yaml.snakeyaml.scanner.Constant;

public final class Mark implements Serializable {
   private String name;
   private int index;
   private int line;
   private int column;
   private String buffer;
   private int pointer;

   public Mark(String var1, int var2, int var3, int var4, String var5, int var6) {
      this.name = var1;
      this.index = var2;
      this.line = var3;
      this.column = var4;
      this.buffer = var5;
      this.pointer = var6;
   }

   private boolean isLineBreak(int var1) {
      return Constant.NULL_OR_LINEBR.has(var1);
   }

   public String get_snippet(int var1, int var2) {
      if (this.buffer == null) {
         return null;
      } else {
         float var3 = (float)(var2 / 2 - 1);
         int var4 = this.pointer;
         String var5 = "";

         while(var4 > 0 && !this.isLineBreak(this.buffer.codePointAt(var4 - 1))) {
            --var4;
            if ((float)(this.pointer - var4) > var3) {
               var5 = " ... ";
               var4 += 5;
               break;
            }
         }

         String var6 = "";
         int var7 = this.pointer;

         while(var7 < this.buffer.length() && !this.isLineBreak(this.buffer.codePointAt(var7))) {
            ++var7;
            if ((float)(var7 - this.pointer) > var3) {
               var6 = " ... ";
               var7 -= 5;
               break;
            }
         }

         String var8 = this.buffer.substring(var4, var7);
         StringBuilder var9 = new StringBuilder();

         int var10;
         for(var10 = 0; var10 < var1; ++var10) {
            var9.append(" ");
         }

         var9.append(var5);
         var9.append(var8);
         var9.append(var6);
         var9.append("\n");

         for(var10 = 0; var10 < var1 + this.pointer - var4 + var5.length(); ++var10) {
            var9.append(" ");
         }

         var9.append("^");
         return var9.toString();
      }
   }

   public String get_snippet() {
      return this.get_snippet(4, 75);
   }

   public String toString() {
      String var1 = this.get_snippet();
      StringBuilder var2 = new StringBuilder(" in ");
      var2.append(this.name);
      var2.append(", line ");
      var2.append(this.line + 1);
      var2.append(", column ");
      var2.append(this.column + 1);
      if (var1 != null) {
         var2.append(":\n");
         var2.append(var1);
      }

      return var2.toString();
   }

   public String getName() {
      return this.name;
   }

   public int getLine() {
      return this.line;
   }

   public int getColumn() {
      return this.column;
   }

   public int getIndex() {
      return this.index;
   }
}
