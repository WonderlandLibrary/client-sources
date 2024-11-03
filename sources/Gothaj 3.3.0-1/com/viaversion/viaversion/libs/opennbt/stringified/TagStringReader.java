package com.viaversion.viaversion.libs.opennbt.stringified;

import com.viaversion.viaversion.libs.fastutil.ints.IntArrayList;
import com.viaversion.viaversion.libs.fastutil.ints.IntList;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.ByteArrayTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.ByteTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.DoubleTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.FloatTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.IntArrayTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.IntTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.ListTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.LongArrayTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.LongTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.NumberTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.ShortTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.IntStream.Builder;

final class TagStringReader {
   private static final int MAX_DEPTH = 512;
   private static final byte[] EMPTY_BYTE_ARRAY = new byte[0];
   private static final int[] EMPTY_INT_ARRAY = new int[0];
   private static final long[] EMPTY_LONG_ARRAY = new long[0];
   private final CharBuffer buffer;
   private boolean acceptLegacy = true;
   private int depth;

   TagStringReader(CharBuffer buffer) {
      this.buffer = buffer;
   }

   public CompoundTag compound() throws StringifiedTagParseException {
      this.buffer.expect('{');
      CompoundTag compoundTag = new CompoundTag();
      if (this.buffer.takeIf('}')) {
         return compoundTag;
      } else {
         while (this.buffer.hasMore()) {
            compoundTag.put(this.key(), this.tag());
            if (this.separatorOrCompleteWith('}')) {
               return compoundTag;
            }
         }

         throw this.buffer.makeError("Unterminated compound tag!");
      }
   }

   public ListTag list() throws StringifiedTagParseException {
      ListTag listTag = new ListTag();
      this.buffer.expect('[');
      boolean prefixedIndex = this.acceptLegacy && this.buffer.peek() == '0' && this.buffer.peek(1) == ':';
      if (!prefixedIndex && this.buffer.takeIf(']')) {
         return listTag;
      } else {
         while (this.buffer.hasMore()) {
            if (prefixedIndex) {
               this.buffer.takeUntil(':');
            }

            Tag next = this.tag();
            listTag.add(next);
            if (this.separatorOrCompleteWith(']')) {
               return listTag;
            }
         }

         throw this.buffer.makeError("Reached end of file without end of list tag!");
      }
   }

   public Tag array(char elementType) throws StringifiedTagParseException {
      this.buffer.expect('[').expect(elementType).expect(';');
      elementType = Character.toLowerCase(elementType);
      if (elementType == 'b') {
         return new ByteArrayTag(this.byteArray());
      } else if (elementType == 'i') {
         return new IntArrayTag(this.intArray());
      } else if (elementType == 'l') {
         return new LongArrayTag(this.longArray());
      } else {
         throw this.buffer.makeError("Type " + elementType + " is not a valid element type in an array!");
      }
   }

   private byte[] byteArray() throws StringifiedTagParseException {
      if (this.buffer.takeIf(']')) {
         return EMPTY_BYTE_ARRAY;
      } else {
         IntList bytes = new IntArrayList();

         while (this.buffer.hasMore()) {
            CharSequence value = this.buffer.skipWhitespace().takeUntil('b');

            try {
               bytes.add(Byte.parseByte(value.toString()));
            } catch (NumberFormatException var5) {
               throw this.buffer.makeError("All elements of a byte array must be bytes!");
            }

            if (this.separatorOrCompleteWith(']')) {
               byte[] result = new byte[bytes.size()];

               for (int i = 0; i < bytes.size(); i++) {
                  result[i] = (byte)bytes.getInt(i);
               }

               return result;
            }
         }

         throw this.buffer.makeError("Reached end of document without array close");
      }
   }

   private int[] intArray() throws StringifiedTagParseException {
      if (this.buffer.takeIf(']')) {
         return EMPTY_INT_ARRAY;
      } else {
         Builder builder = IntStream.builder();

         while (this.buffer.hasMore()) {
            Tag value = this.tag();
            if (!(value instanceof IntTag)) {
               throw this.buffer.makeError("All elements of an int array must be ints!");
            }

            builder.add(((NumberTag)value).asInt());
            if (this.separatorOrCompleteWith(']')) {
               return builder.build().toArray();
            }
         }

         throw this.buffer.makeError("Reached end of document without array close");
      }
   }

   private long[] longArray() throws StringifiedTagParseException {
      if (this.buffer.takeIf(']')) {
         return EMPTY_LONG_ARRAY;
      } else {
         java.util.stream.LongStream.Builder longs = LongStream.builder();

         while (this.buffer.hasMore()) {
            CharSequence value = this.buffer.skipWhitespace().takeUntil('l');

            try {
               longs.add(Long.parseLong(value.toString()));
            } catch (NumberFormatException var4) {
               throw this.buffer.makeError("All elements of a long array must be longs!");
            }

            if (this.separatorOrCompleteWith(']')) {
               return longs.build().toArray();
            }
         }

         throw this.buffer.makeError("Reached end of document without array close");
      }
   }

   public String key() throws StringifiedTagParseException {
      this.buffer.skipWhitespace();
      char starChar = this.buffer.peek();

      String var8;
      try {
         if (starChar == '\'' || starChar == '"') {
            return unescape(this.buffer.takeUntil(this.buffer.take()).toString());
         }

         StringBuilder builder = new StringBuilder();

         while (this.buffer.hasMore()) {
            char peek = this.buffer.peek();
            if (!Tokens.id(peek)) {
               if (!this.acceptLegacy) {
                  break;
               }

               if (peek == '\\') {
                  this.buffer.take();
               } else {
                  if (peek == ':') {
                     break;
                  }

                  builder.append(this.buffer.take());
               }
            } else {
               builder.append(this.buffer.take());
            }
         }

         var8 = builder.toString();
      } finally {
         this.buffer.expect(':');
      }

      return var8;
   }

   public Tag tag() throws StringifiedTagParseException {
      if (this.depth++ > 512) {
         throw this.buffer.makeError("Exceeded maximum allowed depth of 512 when reading tag");
      } else {
         try {
            char startToken = this.buffer.skipWhitespace().peek();
            switch (startToken) {
               case '"':
               case '\'':
                  this.buffer.advance();
                  return new StringTag(unescape(this.buffer.takeUntil(startToken).toString()));
               case '[':
                  if (!this.buffer.hasMore(2) || this.buffer.peek(2) != ';') {
                     return this.list();
                  }

                  return this.array(this.buffer.peek(1));
               case '{':
                  return this.compound();
               default:
                  return this.scalar();
            }
         } finally {
            this.depth--;
         }
      }
   }

   private Tag scalar() {
      StringBuilder builder = new StringBuilder();
      int noLongerNumericAt = -1;

      while (this.buffer.hasMore()) {
         char current = this.buffer.peek();
         if (current == '\\') {
            this.buffer.advance();
            current = this.buffer.take();
         } else {
            if (!Tokens.id(current)) {
               break;
            }

            this.buffer.advance();
         }

         builder.append(current);
         if (noLongerNumericAt == -1 && !Tokens.numeric(current)) {
            noLongerNumericAt = builder.length();
         }
      }

      int length = builder.length();
      String built = builder.toString();
      if (noLongerNumericAt == length) {
         char last = built.charAt(length - 1);

         try {
            switch (Character.toLowerCase(last)) {
               case 'b':
                  return new ByteTag(Byte.parseByte(built.substring(0, length - 1)));
               case 'c':
               case 'e':
               case 'g':
               case 'h':
               case 'j':
               case 'k':
               case 'm':
               case 'n':
               case 'o':
               case 'p':
               case 'q':
               case 'r':
               default:
                  break;
               case 'd':
                  double doubleValue = Double.parseDouble(built.substring(0, length - 1));
                  if (Double.isFinite(doubleValue)) {
                     return new DoubleTag(doubleValue);
                  }
                  break;
               case 'f':
                  float floatValue = Float.parseFloat(built.substring(0, length - 1));
                  if (Float.isFinite(floatValue)) {
                     return new FloatTag(floatValue);
                  }
                  break;
               case 'i':
                  return new IntTag(Integer.parseInt(built.substring(0, length - 1)));
               case 'l':
                  return new LongTag(Long.parseLong(built.substring(0, length - 1)));
               case 's':
                  return new ShortTag(Short.parseShort(built.substring(0, length - 1)));
            }
         } catch (NumberFormatException var9) {
         }
      } else if (noLongerNumericAt == -1) {
         try {
            return new IntTag(Integer.parseInt(built));
         } catch (NumberFormatException var11) {
            if (built.indexOf(46) != -1) {
               try {
                  return new DoubleTag(Double.parseDouble(built));
               } catch (NumberFormatException var10) {
               }
            }
         }
      }

      if (built.equalsIgnoreCase("true")) {
         return new ByteTag((byte)1);
      } else {
         return (Tag)(built.equalsIgnoreCase("false") ? new ByteTag((byte)0) : new StringTag(built));
      }
   }

   private boolean separatorOrCompleteWith(char endCharacter) throws StringifiedTagParseException {
      if (this.buffer.takeIf(endCharacter)) {
         return true;
      } else {
         this.buffer.expect(',');
         return this.buffer.takeIf(endCharacter);
      }
   }

   private static String unescape(String withEscapes) {
      int escapeIdx = withEscapes.indexOf(92);
      if (escapeIdx == -1) {
         return withEscapes;
      } else {
         int lastEscape = 0;
         StringBuilder output = new StringBuilder(withEscapes.length());

         do {
            output.append(withEscapes, lastEscape, escapeIdx);
            lastEscape = escapeIdx + 1;
         } while ((escapeIdx = withEscapes.indexOf(92, lastEscape + 1)) != -1);

         output.append(withEscapes.substring(lastEscape));
         return output.toString();
      }
   }

   public void legacy(boolean acceptLegacy) {
      this.acceptLegacy = acceptLegacy;
   }
}
