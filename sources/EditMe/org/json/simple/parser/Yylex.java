package org.json.simple.parser;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

class Yylex {
   public static final int YYEOF = -1;
   private static final int ZZ_BUFFERSIZE = 16384;
   public static final int YYINITIAL = 0;
   public static final int STRING_BEGIN = 2;
   private static final int[] ZZ_LEXSTATE = new int[]{0, 0, 1, 1};
   private static final String ZZ_CMAP_PACKED = "\t\u0000\u0001\u0007\u0001\u0007\u0002\u0000\u0001\u0007\u0012\u0000\u0001\u0007\u0001\u0000\u0001\t\b\u0000\u0001\u0006\u0001\u0019\u0001\u0002\u0001\u0004\u0001\n\n\u0003\u0001\u001a\u0006\u0000\u0004\u0001\u0001\u0005\u0001\u0001\u0014\u0000\u0001\u0017\u0001\b\u0001\u0018\u0003\u0000\u0001\u0012\u0001\u000b\u0002\u0001\u0001\u0011\u0001\f\u0005\u0000\u0001\u0013\u0001\u0000\u0001\r\u0003\u0000\u0001\u000e\u0001\u0014\u0001\u000f\u0001\u0010\u0005\u0000\u0001\u0015\u0001\u0000\u0001\u0016ﾂ\u0000";
   private static final char[] ZZ_CMAP = zzUnpackCMap("\t\u0000\u0001\u0007\u0001\u0007\u0002\u0000\u0001\u0007\u0012\u0000\u0001\u0007\u0001\u0000\u0001\t\b\u0000\u0001\u0006\u0001\u0019\u0001\u0002\u0001\u0004\u0001\n\n\u0003\u0001\u001a\u0006\u0000\u0004\u0001\u0001\u0005\u0001\u0001\u0014\u0000\u0001\u0017\u0001\b\u0001\u0018\u0003\u0000\u0001\u0012\u0001\u000b\u0002\u0001\u0001\u0011\u0001\f\u0005\u0000\u0001\u0013\u0001\u0000\u0001\r\u0003\u0000\u0001\u000e\u0001\u0014\u0001\u000f\u0001\u0010\u0005\u0000\u0001\u0015\u0001\u0000\u0001\u0016ﾂ\u0000");
   private static final int[] ZZ_ACTION = zzUnpackAction();
   private static final String ZZ_ACTION_PACKED_0 = "\u0002\u0000\u0002\u0001\u0001\u0002\u0001\u0003\u0001\u0004\u0003\u0001\u0001\u0005\u0001\u0006\u0001\u0007\u0001\b\u0001\t\u0001\n\u0001\u000b\u0001\f\u0001\r\u0005\u0000\u0001\f\u0001\u000e\u0001\u000f\u0001\u0010\u0001\u0011\u0001\u0012\u0001\u0013\u0001\u0014\u0001\u0000\u0001\u0015\u0001\u0000\u0001\u0015\u0004\u0000\u0001\u0016\u0001\u0017\u0002\u0000\u0001\u0018";
   private static final int[] ZZ_ROWMAP = zzUnpackRowMap();
   private static final String ZZ_ROWMAP_PACKED_0 = "\u0000\u0000\u0000\u001b\u00006\u0000Q\u0000l\u0000\u0087\u00006\u0000¢\u0000½\u0000Ø\u00006\u00006\u00006\u00006\u00006\u00006\u0000ó\u0000Ď\u00006\u0000ĩ\u0000ń\u0000ş\u0000ź\u0000ƕ\u00006\u00006\u00006\u00006\u00006\u00006\u00006\u00006\u0000ư\u0000ǋ\u0000Ǧ\u0000Ǧ\u0000ȁ\u0000Ȝ\u0000ȷ\u0000ɒ\u00006\u00006\u0000ɭ\u0000ʈ\u00006";
   private static final int[] ZZ_TRANS = new int[]{2, 2, 3, 4, 2, 2, 2, 5, 2, 6, 2, 2, 7, 8, 2, 9, 2, 2, 2, 2, 2, 10, 11, 12, 13, 14, 15, 16, 16, 16, 16, 16, 16, 16, 16, 17, 18, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 4, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 4, 19, 20, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 20, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 5, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 21, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 22, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 23, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 16, 16, 16, 16, 16, 16, 16, 16, -1, -1, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, -1, -1, -1, -1, -1, -1, -1, -1, 24, 25, 26, 27, 28, 29, 30, 31, 32, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 33, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 34, 35, -1, -1, 34, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 36, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 37, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 38, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 39, -1, 39, -1, 39, -1, -1, -1, -1, -1, 39, 39, -1, -1, -1, -1, 39, 39, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 33, -1, 20, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 20, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 35, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 38, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 40, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 41, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 42, -1, 42, -1, 42, -1, -1, -1, -1, -1, 42, 42, -1, -1, -1, -1, 42, 42, -1, -1, -1, -1, -1, -1, -1, -1, -1, 43, -1, 43, -1, 43, -1, -1, -1, -1, -1, 43, 43, -1, -1, -1, -1, 43, 43, -1, -1, -1, -1, -1, -1, -1, -1, -1, 44, -1, 44, -1, 44, -1, -1, -1, -1, -1, 44, 44, -1, -1, -1, -1, 44, 44, -1, -1, -1, -1, -1, -1, -1, -1};
   private static final int ZZ_UNKNOWN_ERROR = 0;
   private static final int ZZ_NO_MATCH = 1;
   private static final int ZZ_PUSHBACK_2BIG = 2;
   private static final String[] ZZ_ERROR_MSG = new String[]{"Unkown internal scanner error", "Error: could not match input", "Error: pushback value was too large"};
   private static final int[] ZZ_ATTRIBUTE = zzUnpackAttribute();
   private static final String ZZ_ATTRIBUTE_PACKED_0 = "\u0002\u0000\u0001\t\u0003\u0001\u0001\t\u0003\u0001\u0006\t\u0002\u0001\u0001\t\u0005\u0000\b\t\u0001\u0000\u0001\u0001\u0001\u0000\u0001\u0001\u0004\u0000\u0002\t\u0002\u0000\u0001\t";
   private Reader zzReader;
   private int zzState;
   private int zzLexicalState;
   private char[] zzBuffer;
   private int zzMarkedPos;
   private int zzCurrentPos;
   private int zzStartRead;
   private int zzEndRead;
   private int yyline;
   private int yychar;
   private int yycolumn;
   private boolean zzAtBOL;
   private boolean zzAtEOF;
   private StringBuffer sb;

   private static int[] zzUnpackAction() {
      int[] var0 = new int[45];
      byte var1 = 0;
      zzUnpackAction("\u0002\u0000\u0002\u0001\u0001\u0002\u0001\u0003\u0001\u0004\u0003\u0001\u0001\u0005\u0001\u0006\u0001\u0007\u0001\b\u0001\t\u0001\n\u0001\u000b\u0001\f\u0001\r\u0005\u0000\u0001\f\u0001\u000e\u0001\u000f\u0001\u0010\u0001\u0011\u0001\u0012\u0001\u0013\u0001\u0014\u0001\u0000\u0001\u0015\u0001\u0000\u0001\u0015\u0004\u0000\u0001\u0016\u0001\u0017\u0002\u0000\u0001\u0018", var1, var0);
      return var0;
   }

   private static int zzUnpackAction(String var0, int var1, int[] var2) {
      int var3 = 0;
      int var4 = var1;
      int var5 = var0.length();

      while(var3 < var5) {
         int var6 = var0.charAt(var3++);
         char var7 = var0.charAt(var3++);

         while(true) {
            var2[var4++] = var7;
            --var6;
            if (var6 <= 0) {
               break;
            }
         }
      }

      return var4;
   }

   private static int[] zzUnpackRowMap() {
      int[] var0 = new int[45];
      byte var1 = 0;
      zzUnpackRowMap("\u0000\u0000\u0000\u001b\u00006\u0000Q\u0000l\u0000\u0087\u00006\u0000¢\u0000½\u0000Ø\u00006\u00006\u00006\u00006\u00006\u00006\u0000ó\u0000Ď\u00006\u0000ĩ\u0000ń\u0000ş\u0000ź\u0000ƕ\u00006\u00006\u00006\u00006\u00006\u00006\u00006\u00006\u0000ư\u0000ǋ\u0000Ǧ\u0000Ǧ\u0000ȁ\u0000Ȝ\u0000ȷ\u0000ɒ\u00006\u00006\u0000ɭ\u0000ʈ\u00006", var1, var0);
      return var0;
   }

   private static int zzUnpackRowMap(String var0, int var1, int[] var2) {
      int var3 = 0;
      int var4 = var1;

      int var6;
      for(int var5 = var0.length(); var3 < var5; var2[var4++] = var6 | var0.charAt(var3++)) {
         var6 = var0.charAt(var3++) << 16;
      }

      return var4;
   }

   private static int[] zzUnpackAttribute() {
      int[] var0 = new int[45];
      byte var1 = 0;
      zzUnpackAttribute("\u0002\u0000\u0001\t\u0003\u0001\u0001\t\u0003\u0001\u0006\t\u0002\u0001\u0001\t\u0005\u0000\b\t\u0001\u0000\u0001\u0001\u0001\u0000\u0001\u0001\u0004\u0000\u0002\t\u0002\u0000\u0001\t", var1, var0);
      return var0;
   }

   private static int zzUnpackAttribute(String var0, int var1, int[] var2) {
      int var3 = 0;
      int var4 = var1;
      int var5 = var0.length();

      while(var3 < var5) {
         int var6 = var0.charAt(var3++);
         char var7 = var0.charAt(var3++);

         while(true) {
            var2[var4++] = var7;
            --var6;
            if (var6 <= 0) {
               break;
            }
         }
      }

      return var4;
   }

   int getPosition() {
      return this.yychar;
   }

   Yylex(Reader var1) {
      this.zzLexicalState = 0;
      this.zzBuffer = new char[16384];
      this.zzAtBOL = true;
      this.sb = new StringBuffer();
      this.zzReader = var1;
   }

   Yylex(InputStream var1) {
      this((Reader)(new InputStreamReader(var1)));
   }

   private static char[] zzUnpackCMap(String var0) {
      char[] var1 = new char[65536];
      int var2 = 0;
      int var3 = 0;

      while(var2 < 90) {
         int var4 = var0.charAt(var2++);
         char var5 = var0.charAt(var2++);

         while(true) {
            var1[var3++] = var5;
            --var4;
            if (var4 <= 0) {
               break;
            }
         }
      }

      return var1;
   }

   private boolean zzRefill() throws IOException {
      if (this.zzStartRead > 0) {
         System.arraycopy(this.zzBuffer, this.zzStartRead, this.zzBuffer, 0, this.zzEndRead - this.zzStartRead);
         this.zzEndRead -= this.zzStartRead;
         this.zzCurrentPos -= this.zzStartRead;
         this.zzMarkedPos -= this.zzStartRead;
         this.zzStartRead = 0;
      }

      if (this.zzCurrentPos >= this.zzBuffer.length) {
         char[] var1 = new char[this.zzCurrentPos * 2];
         System.arraycopy(this.zzBuffer, 0, var1, 0, this.zzBuffer.length);
         this.zzBuffer = var1;
      }

      int var3 = this.zzReader.read(this.zzBuffer, this.zzEndRead, this.zzBuffer.length - this.zzEndRead);
      if (var3 > 0) {
         this.zzEndRead += var3;
         return false;
      } else if (var3 == 0) {
         int var2 = this.zzReader.read();
         if (var2 == -1) {
            return true;
         } else {
            this.zzBuffer[this.zzEndRead++] = (char)var2;
            return false;
         }
      } else {
         return true;
      }
   }

   public final void yyclose() throws IOException {
      this.zzAtEOF = true;
      this.zzEndRead = this.zzStartRead;
      if (this.zzReader != null) {
         this.zzReader.close();
      }

   }

   public final void yyreset(Reader var1) {
      this.zzReader = var1;
      this.zzAtBOL = true;
      this.zzAtEOF = false;
      this.zzEndRead = this.zzStartRead = 0;
      this.zzCurrentPos = this.zzMarkedPos = 0;
      this.yyline = this.yychar = this.yycolumn = 0;
      this.zzLexicalState = 0;
   }

   public final int yystate() {
      return this.zzLexicalState;
   }

   public final void yybegin(int var1) {
      this.zzLexicalState = var1;
   }

   public final String yytext() {
      return new String(this.zzBuffer, this.zzStartRead, this.zzMarkedPos - this.zzStartRead);
   }

   public final char yycharat(int var1) {
      return this.zzBuffer[this.zzStartRead + var1];
   }

   public final int yylength() {
      return this.zzMarkedPos - this.zzStartRead;
   }

   private void zzScanError(int var1) {
      String var2;
      try {
         var2 = ZZ_ERROR_MSG[var1];
      } catch (ArrayIndexOutOfBoundsException var4) {
         var2 = ZZ_ERROR_MSG[0];
      }

      throw new Error(var2);
   }

   public void yypushback(int var1) {
      if (var1 > this.yylength()) {
         this.zzScanError(2);
      }

      this.zzMarkedPos -= var1;
   }

   public Yytoken yylex() throws IOException, ParseException {
      int var5 = this.zzEndRead;
      char[] var6 = this.zzBuffer;
      char[] var7 = ZZ_CMAP;
      int[] var8 = ZZ_TRANS;
      int[] var9 = ZZ_ROWMAP;
      int[] var10 = ZZ_ATTRIBUTE;

      while(true) {
         int var4 = this.zzMarkedPos;
         this.yychar += var4 - this.zzStartRead;
         int var2 = -1;
         int var3 = this.zzCurrentPos = this.zzStartRead = var4;
         this.zzState = ZZ_LEXSTATE[this.zzLexicalState];

         int var1;
         int var14;
         while(true) {
            if (var3 < var5) {
               var1 = var6[var3++];
            } else {
               if (this.zzAtEOF) {
                  var1 = -1;
                  break;
               }

               this.zzCurrentPos = var3;
               this.zzMarkedPos = var4;
               boolean var11 = this.zzRefill();
               var3 = this.zzCurrentPos;
               var4 = this.zzMarkedPos;
               var6 = this.zzBuffer;
               var5 = this.zzEndRead;
               if (var11) {
                  var1 = -1;
                  break;
               }

               var1 = var6[var3++];
            }

            var14 = var8[var9[this.zzState] + var7[var1]];
            if (var14 == -1) {
               break;
            }

            this.zzState = var14;
            int var12 = var10[this.zzState];
            if ((var12 & 1) == 1) {
               var2 = this.zzState;
               var4 = var3;
               if ((var12 & 8) == 8) {
                  break;
               }
            }
         }

         this.zzMarkedPos = var4;
         switch(var2 < 0 ? var2 : ZZ_ACTION[var2]) {
         case 1:
            throw new ParseException(this.yychar, 0, new Character(this.yycharat(0)));
         case 2:
            Long var17 = Long.valueOf(this.yytext());
            return new Yytoken(0, var17);
         case 3:
         case 25:
         case 26:
         case 27:
         case 28:
         case 29:
         case 30:
         case 31:
         case 32:
         case 33:
         case 34:
         case 35:
         case 36:
         case 37:
         case 38:
         case 39:
         case 40:
         case 41:
         case 42:
         case 43:
         case 44:
         case 45:
         case 46:
         case 47:
         case 48:
            break;
         case 4:
            this.sb.delete(0, this.sb.length());
            this.yybegin(2);
            break;
         case 5:
            return new Yytoken(1, (Object)null);
         case 6:
            return new Yytoken(2, (Object)null);
         case 7:
            return new Yytoken(3, (Object)null);
         case 8:
            return new Yytoken(4, (Object)null);
         case 9:
            return new Yytoken(5, (Object)null);
         case 10:
            return new Yytoken(6, (Object)null);
         case 11:
            this.sb.append(this.yytext());
            break;
         case 12:
            this.sb.append('\\');
            break;
         case 13:
            this.yybegin(0);
            return new Yytoken(0, this.sb.toString());
         case 14:
            this.sb.append('"');
            break;
         case 15:
            this.sb.append('/');
            break;
         case 16:
            this.sb.append('\b');
            break;
         case 17:
            this.sb.append('\f');
            break;
         case 18:
            this.sb.append('\n');
            break;
         case 19:
            this.sb.append('\r');
            break;
         case 20:
            this.sb.append('\t');
            break;
         case 21:
            Double var16 = Double.valueOf(this.yytext());
            return new Yytoken(0, var16);
         case 22:
            return new Yytoken(0, (Object)null);
         case 23:
            Boolean var15 = Boolean.valueOf(this.yytext());
            return new Yytoken(0, var15);
         case 24:
            try {
               var14 = Integer.parseInt(this.yytext().substring(2), 16);
               this.sb.append((char)var14);
               break;
            } catch (Exception var13) {
               throw new ParseException(this.yychar, 2, var13);
            }
         default:
            if (var1 == -1 && this.zzStartRead == this.zzCurrentPos) {
               this.zzAtEOF = true;
               return null;
            }

            this.zzScanError(1);
         }
      }
   }
}
