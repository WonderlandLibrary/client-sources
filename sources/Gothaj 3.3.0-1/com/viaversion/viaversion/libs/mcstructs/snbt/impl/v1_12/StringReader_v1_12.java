package com.viaversion.viaversion.libs.mcstructs.snbt.impl.v1_12;

import com.viaversion.viaversion.libs.mcstructs.snbt.exceptions.SNbtDeserializeException;

public class StringReader_v1_12 {
   private final String s;
   private int index;

   public StringReader_v1_12(String s) {
      this.s = s;
   }

   public String getString() {
      return this.s;
   }

   public int getIndex() {
      return this.index;
   }

   public char read() {
      return this.s.charAt(this.index++);
   }

   public char charAt(int offset) {
      return this.s.charAt(this.index + offset);
   }

   public char peek() {
      return this.charAt(0);
   }

   public void skip() {
      this.index++;
   }

   public boolean canRead() {
      return this.canRead(0);
   }

   public boolean canRead(int count) {
      return this.index + count < this.s.length();
   }

   public void skipWhitespaces() {
      while (this.canRead() && Character.isWhitespace(this.peek())) {
         this.index++;
      }
   }

   public String readString() throws SNbtDeserializeException {
      this.skipWhitespaces();
      if (!this.canRead()) {
         return null;
      } else {
         return this.isQuote(this.peek()) ? this.readQuotedString() : this.readUnquotedString();
      }
   }

   public String readUnquotedString() {
      int start = this.index;

      while (this.canRead() && this.isAlphanumeric(this.peek())) {
         this.index++;
      }

      return this.s.substring(start, this.index);
   }

   public String readQuotedString() throws SNbtDeserializeException {
      char quoteStart = this.read();
      int start = this.index;
      StringBuilder out = null;
      boolean escaped = false;

      while (this.canRead()) {
         char c = this.read();
         if (escaped) {
            if (c != '\\' && c != quoteStart) {
               throw new SNbtDeserializeException("Invalid escape of '" + c + "'");
            }

            escaped = false;
         } else {
            if (c == '\\') {
               escaped = true;
               if (out == null) {
                  out = new StringBuilder(this.s.substring(start, this.index - 1));
               }
               continue;
            }

            if (c == quoteStart) {
               return out == null ? this.s.substring(start, this.index - 1) : out.toString();
            }
         }

         if (out != null) {
            out.append(c);
         }
      }

      throw new SNbtDeserializeException("Missing termination quote", this.s, start - 1);
   }

   public void jumpTo(char wanted) throws SNbtDeserializeException {
      this.skipWhitespaces();
      boolean canRead = this.canRead();
      if (canRead && this.peek() == wanted) {
         this.index++;
      } else {
         throw new SNbtDeserializeException("Expected '" + wanted + "' but got '" + (canRead ? this.peek() : "<EOL>") + "'", this.s, this.index + 1);
      }
   }

   protected boolean isQuote(char c) {
      return c == '"';
   }

   private boolean isAlphanumeric(char c) {
      return c >= 'a' && c <= 'z' || c >= 'A' && c <= 'Z' || c >= '0' && c <= '9' || c == '_' || c == '-' || c == '.' || c == '+';
   }
}
