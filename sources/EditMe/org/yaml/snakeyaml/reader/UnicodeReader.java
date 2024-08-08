package org.yaml.snakeyaml.reader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PushbackInputStream;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CodingErrorAction;

public class UnicodeReader extends Reader {
   private static final Charset UTF8 = Charset.forName("UTF-8");
   private static final Charset UTF16BE = Charset.forName("UTF-16BE");
   private static final Charset UTF16LE = Charset.forName("UTF-16LE");
   PushbackInputStream internalIn;
   InputStreamReader internalIn2 = null;
   private static final int BOM_SIZE = 3;

   public UnicodeReader(InputStream var1) {
      this.internalIn = new PushbackInputStream(var1, 3);
   }

   public String getEncoding() {
      return this.internalIn2.getEncoding();
   }

   protected void init() throws IOException {
      if (this.internalIn2 == null) {
         byte[] var2 = new byte[3];
         int var3 = this.internalIn.read(var2, 0, var2.length);
         Charset var1;
         int var4;
         if (var2[0] == -17 && var2[1] == -69 && var2[2] == -65) {
            var1 = UTF8;
            var4 = var3 - 3;
         } else if (var2[0] == -2 && var2[1] == -1) {
            var1 = UTF16BE;
            var4 = var3 - 2;
         } else if (var2[0] == -1 && var2[1] == -2) {
            var1 = UTF16LE;
            var4 = var3 - 2;
         } else {
            var1 = UTF8;
            var4 = var3;
         }

         if (var4 > 0) {
            this.internalIn.unread(var2, var3 - var4, var4);
         }

         CharsetDecoder var5 = var1.newDecoder().onUnmappableCharacter(CodingErrorAction.REPORT);
         this.internalIn2 = new InputStreamReader(this.internalIn, var5);
      }
   }

   public void close() throws IOException {
      this.init();
      this.internalIn2.close();
   }

   public int read(char[] var1, int var2, int var3) throws IOException {
      this.init();
      return this.internalIn2.read(var1, var2, var3);
   }
}
