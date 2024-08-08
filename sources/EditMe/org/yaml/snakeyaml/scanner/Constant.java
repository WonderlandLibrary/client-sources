package org.yaml.snakeyaml.scanner;

import java.util.Arrays;

public final class Constant {
   private static final String ALPHA_S = "abcdefghijklmnopqrstuvwxyz0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ-_";
   private static final String LINEBR_S = "\n\u0085\u2028\u2029";
   private static final String FULL_LINEBR_S = "\r\n\u0085\u2028\u2029";
   private static final String NULL_OR_LINEBR_S = "\u0000\r\n\u0085\u2028\u2029";
   private static final String NULL_BL_LINEBR_S = " \u0000\r\n\u0085\u2028\u2029";
   private static final String NULL_BL_T_LINEBR_S = "\t \u0000\r\n\u0085\u2028\u2029";
   private static final String NULL_BL_T_S = "\u0000 \t";
   private static final String URI_CHARS_S = "abcdefghijklmnopqrstuvwxyz0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ-_-;/?:@&=+$,_.!~*'()[]%";
   public static final Constant LINEBR = new Constant("\n\u0085\u2028\u2029");
   public static final Constant FULL_LINEBR = new Constant("\r\n\u0085\u2028\u2029");
   public static final Constant NULL_OR_LINEBR = new Constant("\u0000\r\n\u0085\u2028\u2029");
   public static final Constant NULL_BL_LINEBR = new Constant(" \u0000\r\n\u0085\u2028\u2029");
   public static final Constant NULL_BL_T_LINEBR = new Constant("\t \u0000\r\n\u0085\u2028\u2029");
   public static final Constant NULL_BL_T = new Constant("\u0000 \t");
   public static final Constant URI_CHARS = new Constant("abcdefghijklmnopqrstuvwxyz0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ-_-;/?:@&=+$,_.!~*'()[]%");
   public static final Constant ALPHA = new Constant("abcdefghijklmnopqrstuvwxyz0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ-_");
   private String content;
   boolean[] contains = new boolean[128];
   boolean noASCII = false;

   private Constant(String var1) {
      Arrays.fill(this.contains, false);
      StringBuilder var2 = new StringBuilder();

      for(int var3 = 0; var3 < var1.length(); ++var3) {
         int var4 = var1.codePointAt(var3);
         if (var4 < 128) {
            this.contains[var4] = true;
         } else {
            var2.appendCodePoint(var4);
         }
      }

      if (var2.length() > 0) {
         this.noASCII = true;
         this.content = var2.toString();
      }

   }

   public boolean has(int var1) {
      return var1 < 128 ? this.contains[var1] : this.noASCII && this.content.indexOf(var1, 0) != -1;
   }

   public boolean hasNo(int var1) {
      return !this.has(var1);
   }

   public boolean has(int var1, String var2) {
      return this.has(var1) || var2.indexOf(var1, 0) != -1;
   }

   public boolean hasNo(int var1, String var2) {
      return !this.has(var1, var2);
   }
}
