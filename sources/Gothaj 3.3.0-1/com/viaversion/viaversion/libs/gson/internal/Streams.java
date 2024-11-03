package com.viaversion.viaversion.libs.gson.internal;

import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.libs.gson.JsonIOException;
import com.viaversion.viaversion.libs.gson.JsonNull;
import com.viaversion.viaversion.libs.gson.JsonParseException;
import com.viaversion.viaversion.libs.gson.JsonSyntaxException;
import com.viaversion.viaversion.libs.gson.internal.bind.TypeAdapters;
import com.viaversion.viaversion.libs.gson.stream.JsonReader;
import com.viaversion.viaversion.libs.gson.stream.JsonWriter;
import com.viaversion.viaversion.libs.gson.stream.MalformedJsonException;
import java.io.EOFException;
import java.io.IOException;
import java.io.Writer;
import java.util.Objects;

public final class Streams {
   private Streams() {
      throw new UnsupportedOperationException();
   }

   public static JsonElement parse(JsonReader reader) throws JsonParseException {
      boolean isEmpty = true;

      try {
         reader.peek();
         isEmpty = false;
         return TypeAdapters.JSON_ELEMENT.read(reader);
      } catch (EOFException var3) {
         if (isEmpty) {
            return JsonNull.INSTANCE;
         } else {
            throw new JsonSyntaxException(var3);
         }
      } catch (MalformedJsonException var4) {
         throw new JsonSyntaxException(var4);
      } catch (IOException var5) {
         throw new JsonIOException(var5);
      } catch (NumberFormatException var6) {
         throw new JsonSyntaxException(var6);
      }
   }

   public static void write(JsonElement element, JsonWriter writer) throws IOException {
      TypeAdapters.JSON_ELEMENT.write(writer, element);
   }

   public static Writer writerForAppendable(Appendable appendable) {
      return (Writer)(appendable instanceof Writer ? (Writer)appendable : new Streams.AppendableWriter(appendable));
   }

   private static final class AppendableWriter extends Writer {
      private final Appendable appendable;
      private final Streams.AppendableWriter.CurrentWrite currentWrite = new Streams.AppendableWriter.CurrentWrite();

      AppendableWriter(Appendable appendable) {
         this.appendable = appendable;
      }

      @Override
      public void write(char[] chars, int offset, int length) throws IOException {
         this.currentWrite.setChars(chars);
         this.appendable.append(this.currentWrite, offset, offset + length);
      }

      @Override
      public void flush() {
      }

      @Override
      public void close() {
      }

      @Override
      public void write(int i) throws IOException {
         this.appendable.append((char)i);
      }

      @Override
      public void write(String str, int off, int len) throws IOException {
         Objects.requireNonNull(str);
         this.appendable.append(str, off, off + len);
      }

      @Override
      public Writer append(CharSequence csq) throws IOException {
         this.appendable.append(csq);
         return this;
      }

      @Override
      public Writer append(CharSequence csq, int start, int end) throws IOException {
         this.appendable.append(csq, start, end);
         return this;
      }

      private static class CurrentWrite implements CharSequence {
         private char[] chars;
         private String cachedString;

         private CurrentWrite() {
         }

         void setChars(char[] chars) {
            this.chars = chars;
            this.cachedString = null;
         }

         @Override
         public int length() {
            return this.chars.length;
         }

         @Override
         public char charAt(int i) {
            return this.chars[i];
         }

         @Override
         public CharSequence subSequence(int start, int end) {
            return new String(this.chars, start, end - start);
         }

         @Override
         public String toString() {
            if (this.cachedString == null) {
               this.cachedString = new String(this.chars);
            }

            return this.cachedString;
         }
      }
   }
}
