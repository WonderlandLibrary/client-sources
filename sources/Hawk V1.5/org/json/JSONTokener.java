package org.json;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;

public class JSONTokener {
   private long index;
   private char previous;
   private boolean eof;
   private long line;
   private final Reader reader;
   private long characterPreviousLine;
   private boolean usePrevious;
   private long character;

   public JSONTokener(InputStream var1) {
      this((Reader)(new InputStreamReader(var1)));
   }

   public boolean end() {
      return this.eof && !this.usePrevious;
   }

   public String nextTo(String var1) throws JSONException {
      StringBuilder var3 = new StringBuilder();

      while(true) {
         char var2 = this.next();
         if (var1.indexOf(var2) >= 0 || var2 == 0 || var2 == '\n' || var2 == '\r') {
            if (var2 != 0) {
               this.back();
            }

            return String.valueOf(var3).trim();
         }

         var3.append(var2);
      }
   }

   private void decrementIndexes() {
      --this.index;
      if (this.previous != '\r' && this.previous != '\n') {
         if (this.character > 0L) {
            --this.character;
         }
      } else {
         --this.line;
         this.character = this.characterPreviousLine;
      }

   }

   public char skipTo(char var1) throws JSONException {
      char var2;
      try {
         long var3 = this.index;
         long var5 = this.character;
         long var7 = this.line;
         this.reader.mark(1000000);

         while(true) {
            var2 = this.next();
            if (var2 == 0) {
               this.reader.reset();
               this.index = var3;
               this.character = var5;
               this.line = var7;
               return '\u0000';
            }

            if (var2 == var1) {
               this.reader.mark(1);
               break;
            }
         }
      } catch (IOException var9) {
         throw new JSONException(var9);
      }

      this.back();
      return var2;
   }

   public static int dehexchar(char var0) {
      if (var0 >= '0' && var0 <= '9') {
         return var0 - 48;
      } else if (var0 >= 'A' && var0 <= 'F') {
         return var0 - 55;
      } else {
         return var0 >= 'a' && var0 <= 'f' ? var0 - 87 : -1;
      }
   }

   public JSONTokener(Reader var1) {
      this.reader = (Reader)(var1.markSupported() ? var1 : new BufferedReader(var1));
      this.eof = false;
      this.usePrevious = false;
      this.previous = 0;
      this.index = 0L;
      this.character = 1L;
      this.characterPreviousLine = 0L;
      this.line = 1L;
   }

   public String toString() {
      return String.valueOf((new StringBuilder()).append(" at ").append(this.index).append(" [character ").append(this.character).append(" line ").append(this.line).append("]"));
   }

   public String nextString(char var1) throws JSONException {
      StringBuilder var3 = new StringBuilder();

      while(true) {
         char var2 = this.next();
         switch(var2) {
         case '\u0000':
         case '\n':
         case '\r':
            throw this.syntaxError("Unterminated string");
         case '\\':
            var2 = this.next();
            switch(var2) {
            case '"':
            case '\'':
            case '/':
            case '\\':
               var3.append(var2);
               continue;
            case 'b':
               var3.append('\b');
               continue;
            case 'f':
               var3.append('\f');
               continue;
            case 'n':
               var3.append('\n');
               continue;
            case 'r':
               var3.append('\r');
               continue;
            case 't':
               var3.append('\t');
               continue;
            case 'u':
               try {
                  var3.append((char)Integer.parseInt(this.next((int)4), 16));
                  continue;
               } catch (NumberFormatException var5) {
                  throw this.syntaxError("Illegal escape.", var5);
               }
            default:
               throw this.syntaxError("Illegal escape.");
            }
         default:
            if (var2 == var1) {
               return String.valueOf(var3);
            }

            var3.append(var2);
         }
      }
   }

   public String nextTo(char var1) throws JSONException {
      StringBuilder var2 = new StringBuilder();

      while(true) {
         char var3 = this.next();
         if (var3 == var1 || var3 == 0 || var3 == '\n' || var3 == '\r') {
            if (var3 != 0) {
               this.back();
            }

            return String.valueOf(var2).trim();
         }

         var2.append(var3);
      }
   }

   public boolean more() throws JSONException {
      if (this.usePrevious) {
         return true;
      } else {
         try {
            this.reader.mark(1);
         } catch (IOException var3) {
            throw new JSONException("Unable to preserve stream position", var3);
         }

         try {
            if (this.reader.read() <= 0) {
               this.eof = true;
               return false;
            } else {
               this.reader.reset();
               return true;
            }
         } catch (IOException var2) {
            throw new JSONException("Unable to read the next character from the stream", var2);
         }
      }
   }

   public char next() throws JSONException {
      int var1;
      if (this.usePrevious) {
         this.usePrevious = false;
         var1 = this.previous;
      } else {
         try {
            var1 = this.reader.read();
         } catch (IOException var3) {
            throw new JSONException(var3);
         }
      }

      if (var1 <= 0) {
         this.eof = true;
         return '\u0000';
      } else {
         this.incrementIndexes(var1);
         this.previous = (char)var1;
         return this.previous;
      }
   }

   public JSONException syntaxError(String var1) {
      return new JSONException(String.valueOf((new StringBuilder()).append(var1).append(this.toString())));
   }

   public Object nextValue() throws JSONException {
      char var1 = this.nextClean();
      switch(var1) {
      case '"':
      case '\'':
         return this.nextString(var1);
      case '[':
         this.back();
         return new JSONArray(this);
      case '{':
         this.back();
         return new JSONObject(this);
      default:
         StringBuilder var3;
         for(var3 = new StringBuilder(); var1 >= ' ' && ",:]}/\\\"[{;=#".indexOf(var1) < 0; var1 = this.next()) {
            var3.append(var1);
         }

         if (!this.eof) {
            this.back();
         }

         String var2 = String.valueOf(var3).trim();
         if ("".equals(var2)) {
            throw this.syntaxError("Missing value");
         } else {
            return JSONObject.stringToValue(var2);
         }
      }
   }

   public char next(char var1) throws JSONException {
      char var2 = this.next();
      if (var2 != var1) {
         if (var2 > 0) {
            throw this.syntaxError(String.valueOf((new StringBuilder()).append("Expected '").append(var1).append("' and instead saw '").append(var2).append("'")));
         } else {
            throw this.syntaxError(String.valueOf((new StringBuilder()).append("Expected '").append(var1).append("' and instead saw ''")));
         }
      } else {
         return var2;
      }
   }

   public JSONException syntaxError(String var1, Throwable var2) {
      return new JSONException(String.valueOf((new StringBuilder()).append(var1).append(this.toString())), var2);
   }

   public char nextClean() throws JSONException {
      char var1;
      do {
         var1 = this.next();
      } while(var1 != 0 && var1 <= ' ');

      return var1;
   }

   public JSONTokener(String var1) {
      this((Reader)(new StringReader(var1)));
   }

   private void incrementIndexes(int var1) {
      if (var1 > 0) {
         ++this.index;
         if (var1 == 13) {
            ++this.line;
            this.characterPreviousLine = this.character;
            this.character = 0L;
         } else if (var1 == 10) {
            if (this.previous != '\r') {
               ++this.line;
               this.characterPreviousLine = this.character;
            }

            this.character = 0L;
         } else {
            ++this.character;
         }
      }

   }

   public String next(int var1) throws JSONException {
      if (var1 == 0) {
         return "";
      } else {
         char[] var2 = new char[var1];

         for(int var3 = 0; var3 < var1; ++var3) {
            var2[var3] = this.next();
            if (this.end()) {
               throw this.syntaxError("Substring bounds error");
            }
         }

         return new String(var2);
      }
   }

   public void back() throws JSONException {
      if (!this.usePrevious && this.index > 0L) {
         this.decrementIndexes();
         this.usePrevious = true;
         this.eof = false;
      } else {
         throw new JSONException("Stepping back two steps is not supported");
      }
   }
}
