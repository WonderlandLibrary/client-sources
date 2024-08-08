package org.yaml.snakeyaml.external.com.google.gdata.util.common.base;

import java.io.IOException;

public abstract class UnicodeEscaper implements Escaper {
   private static final int DEST_PAD = 32;
   private static final ThreadLocal DEST_TL = new ThreadLocal() {
      protected char[] initialValue() {
         return new char[1024];
      }

      protected Object initialValue() {
         return this.initialValue();
      }
   };

   protected abstract char[] escape(int var1);

   protected int nextEscapeIndex(CharSequence var1, int var2, int var3) {
      int var4;
      int var5;
      for(var4 = var2; var4 < var3; var4 += Character.isSupplementaryCodePoint(var5) ? 2 : 1) {
         var5 = codePointAt(var1, var4, var3);
         if (var5 < 0 || this.escape(var5) != null) {
            break;
         }
      }

      return var4;
   }

   public String escape(String var1) {
      int var2 = var1.length();
      int var3 = this.nextEscapeIndex(var1, 0, var2);
      return var3 == var2 ? var1 : this.escapeSlow(var1, var3);
   }

   protected final String escapeSlow(String var1, int var2) {
      int var3 = var1.length();
      char[] var4 = (char[])DEST_TL.get();
      int var5 = 0;

      int var6;
      int var7;
      for(var6 = 0; var2 < var3; var2 = this.nextEscapeIndex(var1, var6, var3)) {
         var7 = codePointAt(var1, var2, var3);
         if (var7 < 0) {
            throw new IllegalArgumentException("Trailing high surrogate at end of input");
         }

         char[] var8 = this.escape(var7);
         if (var8 != null) {
            int var9 = var2 - var6;
            int var10 = var5 + var9 + var8.length;
            if (var4.length < var10) {
               int var11 = var10 + (var3 - var2) + 32;
               var4 = growBuffer(var4, var5, var11);
            }

            if (var9 > 0) {
               var1.getChars(var6, var2, var4, var5);
               var5 += var9;
            }

            if (var8.length > 0) {
               System.arraycopy(var8, 0, var4, var5, var8.length);
               var5 += var8.length;
            }
         }

         var6 = var2 + (Character.isSupplementaryCodePoint(var7) ? 2 : 1);
      }

      var7 = var3 - var6;
      if (var7 > 0) {
         int var12 = var5 + var7;
         if (var4.length < var12) {
            var4 = growBuffer(var4, var5, var12);
         }

         var1.getChars(var6, var3, var4, var5);
         var5 = var12;
      }

      return new String(var4, 0, var5);
   }

   public Appendable escape(Appendable var1) {
      assert var1 != null;

      return new Appendable(this, var1) {
         int pendingHighSurrogate;
         char[] decodedChars;
         final Appendable val$out;
         final UnicodeEscaper this$0;

         {
            this.this$0 = var1;
            this.val$out = var2;
            this.pendingHighSurrogate = -1;
            this.decodedChars = new char[2];
         }

         public Appendable append(CharSequence var1) throws IOException {
            return this.append(var1, 0, var1.length());
         }

         public Appendable append(CharSequence var1, int var2, int var3) throws IOException {
            int var4 = var2;
            if (var2 < var3) {
               int var5 = var2;
               char[] var7;
               if (this.pendingHighSurrogate != -1) {
                  var4 = var2 + 1;
                  char var6 = var1.charAt(var2);
                  if (!Character.isLowSurrogate(var6)) {
                     throw new IllegalArgumentException("Expected low surrogate character but got " + var6);
                  }

                  var7 = this.this$0.escape(Character.toCodePoint((char)this.pendingHighSurrogate, var6));
                  if (var7 != null) {
                     this.outputChars(var7, var7.length);
                     var5 = var2 + 1;
                  } else {
                     this.val$out.append((char)this.pendingHighSurrogate);
                  }

                  this.pendingHighSurrogate = -1;
               }

               while(true) {
                  var4 = this.this$0.nextEscapeIndex(var1, var4, var3);
                  if (var4 > var5) {
                     this.val$out.append(var1, var5, var4);
                  }

                  if (var4 == var3) {
                     break;
                  }

                  int var9 = UnicodeEscaper.codePointAt(var1, var4, var3);
                  if (var9 < 0) {
                     this.pendingHighSurrogate = -var9;
                     break;
                  }

                  var7 = this.this$0.escape(var9);
                  if (var7 != null) {
                     this.outputChars(var7, var7.length);
                  } else {
                     int var8 = Character.toChars(var9, this.decodedChars, 0);
                     this.outputChars(this.decodedChars, var8);
                  }

                  var4 += Character.isSupplementaryCodePoint(var9) ? 2 : 1;
                  var5 = var4;
               }
            }

            return this;
         }

         public Appendable append(char var1) throws IOException {
            char[] var2;
            if (this.pendingHighSurrogate != -1) {
               if (!Character.isLowSurrogate(var1)) {
                  throw new IllegalArgumentException("Expected low surrogate character but got '" + var1 + "' with value " + var1);
               }

               var2 = this.this$0.escape(Character.toCodePoint((char)this.pendingHighSurrogate, var1));
               if (var2 != null) {
                  this.outputChars(var2, var2.length);
               } else {
                  this.val$out.append((char)this.pendingHighSurrogate);
                  this.val$out.append(var1);
               }

               this.pendingHighSurrogate = -1;
            } else if (Character.isHighSurrogate(var1)) {
               this.pendingHighSurrogate = var1;
            } else {
               if (Character.isLowSurrogate(var1)) {
                  throw new IllegalArgumentException("Unexpected low surrogate character '" + var1 + "' with value " + var1);
               }

               var2 = this.this$0.escape(var1);
               if (var2 != null) {
                  this.outputChars(var2, var2.length);
               } else {
                  this.val$out.append(var1);
               }
            }

            return this;
         }

         private void outputChars(char[] var1, int var2) throws IOException {
            for(int var3 = 0; var3 < var2; ++var3) {
               this.val$out.append(var1[var3]);
            }

         }
      };
   }

   protected static final int codePointAt(CharSequence var0, int var1, int var2) {
      if (var1 < var2) {
         char var3 = var0.charAt(var1++);
         if (var3 >= '\ud800' && var3 <= '\udfff') {
            if (var3 <= '\udbff') {
               if (var1 == var2) {
                  return -var3;
               } else {
                  char var4 = var0.charAt(var1);
                  if (Character.isLowSurrogate(var4)) {
                     return Character.toCodePoint(var3, var4);
                  } else {
                     throw new IllegalArgumentException("Expected low surrogate but got char '" + var4 + "' with value " + var4 + " at index " + var1);
                  }
               }
            } else {
               throw new IllegalArgumentException("Unexpected low surrogate character '" + var3 + "' with value " + var3 + " at index " + (var1 - 1));
            }
         } else {
            return var3;
         }
      } else {
         throw new IndexOutOfBoundsException("Index exceeds specified range");
      }
   }

   private static final char[] growBuffer(char[] var0, int var1, int var2) {
      char[] var3 = new char[var2];
      if (var1 > 0) {
         System.arraycopy(var0, 0, var3, 0, var1);
      }

      return var3;
   }
}
