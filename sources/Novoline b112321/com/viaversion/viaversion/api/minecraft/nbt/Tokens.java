package com.viaversion.viaversion.api.minecraft.nbt;

import net.aPX;

final class Tokens {
   static final char COMPOUND_BEGIN = '{';
   static final char COMPOUND_END = '}';
   static final char COMPOUND_KEY_TERMINATOR = ':';
   static final char ARRAY_BEGIN = '[';
   static final char ARRAY_END = ']';
   static final char ARRAY_SIGNATURE_SEPARATOR = ';';
   static final char VALUE_SEPARATOR = ',';
   static final char SINGLE_QUOTE = '\'';
   static final char DOUBLE_QUOTE = '\"';
   static final char ESCAPE_MARKER = '\\';
   static final char TYPE_BYTE = 'B';
   static final char TYPE_SHORT = 'S';
   static final char TYPE_INT = 'I';
   static final char TYPE_LONG = 'L';
   static final char TYPE_FLOAT = 'F';
   static final char TYPE_DOUBLE = 'D';
   static final char EOF = '\u0000';

   static boolean id(char var0) {
      String var1 = aPX.b();
      return var0 >= 97 && var0 <= 122 || var0 >= 65 && var0 <= 90 || var0 >= 48 && var0 <= 57 || var0 == 45 || var0 == 95 || var0 == 46 || var0 == 43;
   }

   static boolean numeric(char var0) {
      String var1 = aPX.b();
      return var0 >= 48 && var0 <= 57 || var0 == 43 || var0 == 45 || var0 == 101 || var0 == 69 || var0 == 46;
   }
}
