package org.yaml.snakeyaml.reader;

import java.io.IOException;
import java.io.Reader;
import java.nio.charset.Charset;
import org.yaml.snakeyaml.error.Mark;
import org.yaml.snakeyaml.error.YAMLException;
import org.yaml.snakeyaml.scanner.Constant;

public class StreamReader {
   private String name;
   private final Reader stream;
   private int pointer = 0;
   private boolean eof = true;
   private String buffer;
   private int index = 0;
   private int line = 0;
   private int column = 0;
   private char[] data;
   private static final int BUFFER_SIZE = 1025;

   public StreamReader(String var1) {
      this.name = "'string'";
      this.buffer = "";
      this.checkPrintable(var1);
      this.buffer = var1 + "\u0000";
      this.stream = null;
      this.eof = true;
      this.data = null;
   }

   public StreamReader(Reader var1) {
      this.name = "'reader'";
      this.buffer = "";
      this.stream = var1;
      this.eof = false;
      this.data = new char[1025];
      this.update();
   }

   void checkPrintable(String var1) {
      int var2 = var1.length();

      int var4;
      for(int var3 = 0; var3 < var2; var3 += Character.charCount(var4)) {
         var4 = var1.codePointAt(var3);
         if (!isPrintable(var4)) {
            throw new ReaderException(this.name, var3, var4, "special characters are not allowed");
         }
      }

   }

   public static boolean isPrintable(String var0) {
      int var1 = var0.length();

      int var3;
      for(int var2 = 0; var2 < var1; var2 += Character.charCount(var3)) {
         var3 = var0.codePointAt(var2);
         if (!isPrintable(var3)) {
            return false;
         }
      }

      return true;
   }

   public static boolean isPrintable(int var0) {
      return var0 >= 32 && var0 <= 126 || var0 == 9 || var0 == 10 || var0 == 13 || var0 == 133 || var0 >= 160 && var0 <= 55295 || var0 >= 57344 && var0 <= 65533 || var0 >= 65536 && var0 <= 1114111;
   }

   public Mark getMark() {
      return new Mark(this.name, this.index, this.line, this.column, this.buffer, this.pointer);
   }

   public void forward() {
      this.forward(1);
   }

   public void forward(int var1) {
      for(int var3 = 0; var3 < var1; ++var3) {
         if (this.pointer == this.buffer.length()) {
            this.update();
         }

         if (this.pointer == this.buffer.length()) {
            break;
         }

         int var2 = this.buffer.codePointAt(this.pointer);
         this.pointer += Character.charCount(var2);
         this.index += Character.charCount(var2);
         if (Constant.LINEBR.has(var2) || var2 == 13 && this.buffer.charAt(this.pointer) != '\n') {
            ++this.line;
            this.column = 0;
         } else if (var2 != 65279) {
            ++this.column;
         }
      }

      if (this.pointer == this.buffer.length()) {
         this.update();
      }

   }

   public int peek() {
      if (this.pointer == this.buffer.length()) {
         this.update();
      }

      return this.pointer == this.buffer.length() ? -1 : this.buffer.codePointAt(this.pointer);
   }

   public int peek(int var1) {
      int var2 = 0;
      int var3 = 0;

      int var4;
      do {
         if (this.pointer + var2 == this.buffer.length()) {
            this.update();
         }

         if (this.pointer + var2 == this.buffer.length()) {
            return -1;
         }

         var4 = this.buffer.codePointAt(this.pointer + var2);
         var2 += Character.charCount(var4);
         ++var3;
      } while(var3 <= var1);

      return var4;
   }

   public String prefix(int var1) {
      StringBuilder var2 = new StringBuilder();
      int var3 = 0;

      for(int var4 = 0; var4 < var1; ++var4) {
         if (this.pointer + var3 == this.buffer.length()) {
            this.update();
         }

         if (this.pointer + var3 == this.buffer.length()) {
            break;
         }

         int var5 = this.buffer.codePointAt(this.pointer + var3);
         var2.appendCodePoint(var5);
         var3 += Character.charCount(var5);
      }

      return var2.toString();
   }

   public String prefixForward(int var1) {
      String var2 = this.prefix(var1);
      this.pointer += var2.length();
      this.index += var2.length();
      this.column += var1;
      return var2;
   }

   private void update() {
      if (!this.eof) {
         this.buffer = this.buffer.substring(this.pointer);
         this.pointer = 0;

         try {
            boolean var1 = false;
            int var2 = this.stream.read(this.data, 0, 1024);
            if (var2 > 0) {
               if (Character.isHighSurrogate(this.data[var2 - 1])) {
                  int var3 = this.stream.read(this.data, var2, 1);
                  if (var3 != -1) {
                     var2 += var3;
                  } else {
                     var1 = true;
                  }
               }

               StringBuilder var5 = (new StringBuilder(this.buffer.length() + var2)).append(this.buffer).append(this.data, 0, var2);
               if (var1) {
                  this.eof = true;
                  var5.append('\u0000');
               }

               this.buffer = var5.toString();
               this.checkPrintable(this.buffer);
            } else {
               this.eof = true;
               this.buffer = this.buffer + "\u0000";
            }
         } catch (IOException var4) {
            throw new YAMLException(var4);
         }
      }

   }

   public int getColumn() {
      return this.column;
   }

   public Charset getEncoding() {
      return Charset.forName(((UnicodeReader)this.stream).getEncoding());
   }

   public int getIndex() {
      return this.index;
   }

   public int getLine() {
      return this.line;
   }
}
