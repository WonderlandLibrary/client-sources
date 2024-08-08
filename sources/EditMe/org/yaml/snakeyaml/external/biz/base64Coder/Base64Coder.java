package org.yaml.snakeyaml.external.biz.base64Coder;

public class Base64Coder {
   private static final String systemLineSeparator = System.getProperty("line.separator");
   private static char[] map1 = new char[64];
   private static byte[] map2;

   public static String encodeString(String var0) {
      return new String(encode(var0.getBytes()));
   }

   public static String encodeLines(byte[] var0) {
      return encodeLines(var0, 0, var0.length, 76, systemLineSeparator);
   }

   public static String encodeLines(byte[] var0, int var1, int var2, int var3, String var4) {
      int var5 = var3 * 3 / 4;
      if (var5 <= 0) {
         throw new IllegalArgumentException();
      } else {
         int var6 = (var2 + var5 - 1) / var5;
         int var7 = (var2 + 2) / 3 * 4 + var6 * var4.length();
         StringBuilder var8 = new StringBuilder(var7);

         int var10;
         for(int var9 = 0; var9 < var2; var9 += var10) {
            var10 = Math.min(var2 - var9, var5);
            var8.append(encode(var0, var1 + var9, var10));
            var8.append(var4);
         }

         return var8.toString();
      }
   }

   public static char[] encode(byte[] var0) {
      return encode(var0, 0, var0.length);
   }

   public static char[] encode(byte[] var0, int var1) {
      return encode(var0, 0, var1);
   }

   public static char[] encode(byte[] var0, int var1, int var2) {
      int var3 = (var2 * 4 + 2) / 3;
      int var4 = (var2 + 2) / 3 * 4;
      char[] var5 = new char[var4];
      int var6 = var1;
      int var7 = var1 + var2;

      for(int var8 = 0; var6 < var7; ++var8) {
         int var9 = var0[var6++] & 255;
         int var10 = var6 < var7 ? var0[var6++] & 255 : 0;
         int var11 = var6 < var7 ? var0[var6++] & 255 : 0;
         int var12 = var9 >>> 2;
         int var13 = (var9 & 3) << 4 | var10 >>> 4;
         int var14 = (var10 & 15) << 2 | var11 >>> 6;
         int var15 = var11 & 63;
         var5[var8++] = map1[var12];
         var5[var8++] = map1[var13];
         var5[var8] = var8 < var3 ? map1[var14] : 61;
         ++var8;
         var5[var8] = var8 < var3 ? map1[var15] : 61;
      }

      return var5;
   }

   public static String decodeString(String var0) {
      return new String(decode(var0));
   }

   public static byte[] decodeLines(String var0) {
      char[] var1 = new char[var0.length()];
      int var2 = 0;

      for(int var3 = 0; var3 < var0.length(); ++var3) {
         char var4 = var0.charAt(var3);
         if (var4 != ' ' && var4 != '\r' && var4 != '\n' && var4 != '\t') {
            var1[var2++] = var4;
         }
      }

      return decode(var1, 0, var2);
   }

   public static byte[] decode(String var0) {
      return decode(var0.toCharArray());
   }

   public static byte[] decode(char[] var0) {
      return decode(var0, 0, var0.length);
   }

   public static byte[] decode(char[] var0, int var1, int var2) {
      if (var2 % 4 != 0) {
         throw new IllegalArgumentException("Length of Base64 encoded input string is not a multiple of 4.");
      } else {
         while(var2 > 0 && var0[var1 + var2 - 1] == '=') {
            --var2;
         }

         int var3 = var2 * 3 / 4;
         byte[] var4 = new byte[var3];
         int var5 = var1;
         int var6 = var1 + var2;
         int var7 = 0;

         while(var5 < var6) {
            char var8 = var0[var5++];
            char var9 = var0[var5++];
            char var10 = var5 < var6 ? var0[var5++] : 65;
            char var11 = var5 < var6 ? var0[var5++] : 65;
            if (var8 <= 127 && var9 <= 127 && var10 <= 127 && var11 <= 127) {
               byte var12 = map2[var8];
               byte var13 = map2[var9];
               byte var14 = map2[var10];
               byte var15 = map2[var11];
               if (var12 >= 0 && var13 >= 0 && var14 >= 0 && var15 >= 0) {
                  int var16 = var12 << 2 | var13 >>> 4;
                  int var17 = (var13 & 15) << 4 | var14 >>> 2;
                  int var18 = (var14 & 3) << 6 | var15;
                  var4[var7++] = (byte)var16;
                  if (var7 < var3) {
                     var4[var7++] = (byte)var17;
                  }

                  if (var7 < var3) {
                     var4[var7++] = (byte)var18;
                  }
                  continue;
               }

               throw new IllegalArgumentException("Illegal character in Base64 encoded data.");
            }

            throw new IllegalArgumentException("Illegal character in Base64 encoded data.");
         }

         return var4;
      }
   }

   private Base64Coder() {
   }

   static {
      int var0 = 0;

      char var1;
      for(var1 = 'A'; var1 <= 'Z'; map1[var0++] = var1++) {
      }

      for(var1 = 'a'; var1 <= 'z'; map1[var0++] = var1++) {
      }

      for(var1 = '0'; var1 <= '9'; map1[var0++] = var1++) {
      }

      map1[var0++] = '+';
      map1[var0++] = '/';
      map2 = new byte[128];

      for(var0 = 0; var0 < map2.length; ++var0) {
         map2[var0] = -1;
      }

      for(var0 = 0; var0 < 64; ++var0) {
         map2[map1[var0]] = (byte)var0;
      }

   }
}
