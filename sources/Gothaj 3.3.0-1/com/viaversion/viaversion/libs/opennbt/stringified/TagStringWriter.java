package com.viaversion.viaversion.libs.opennbt.stringified;

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
import java.util.Map.Entry;

final class TagStringWriter {
   private final StringBuilder out;
   private boolean needsSeparator;

   public TagStringWriter(StringBuilder out) {
      this.out = out;
   }

   public TagStringWriter writeTag(Tag tag) {
      if (tag instanceof CompoundTag) {
         return this.writeCompound((CompoundTag)tag);
      } else if (tag instanceof ListTag) {
         return this.writeList((ListTag)tag);
      } else if (tag instanceof ByteArrayTag) {
         return this.writeByteArray((ByteArrayTag)tag);
      } else if (tag instanceof IntArrayTag) {
         return this.writeIntArray((IntArrayTag)tag);
      } else if (tag instanceof LongArrayTag) {
         return this.writeLongArray((LongArrayTag)tag);
      } else if (tag instanceof StringTag) {
         return this.value(((StringTag)tag).getValue(), '\u0000');
      } else if (tag instanceof ByteTag) {
         return this.value(Byte.toString(((NumberTag)tag).asByte()), 'b');
      } else if (tag instanceof ShortTag) {
         return this.value(Short.toString(((NumberTag)tag).asShort()), 's');
      } else if (tag instanceof IntTag) {
         return this.value(Integer.toString(((NumberTag)tag).asInt()), 'i');
      } else if (tag instanceof LongTag) {
         return this.value(Long.toString(((NumberTag)tag).asLong()), Character.toUpperCase('l'));
      } else if (tag instanceof FloatTag) {
         return this.value(Float.toString(((NumberTag)tag).asFloat()), 'f');
      } else if (tag instanceof DoubleTag) {
         return this.value(Double.toString(((NumberTag)tag).asDouble()), 'd');
      } else {
         throw new IllegalArgumentException("Unknown tag type: " + tag.getClass().getSimpleName());
      }
   }

   private TagStringWriter writeCompound(CompoundTag tag) {
      this.beginCompound();

      for (Entry<String, Tag> entry : tag.entrySet()) {
         this.key(entry.getKey());
         this.writeTag(entry.getValue());
      }

      this.endCompound();
      return this;
   }

   private TagStringWriter writeList(ListTag tag) {
      this.beginList();

      for (Tag el : tag) {
         this.printAndResetSeparator();
         this.writeTag(el);
      }

      this.endList();
      return this;
   }

   private TagStringWriter writeByteArray(ByteArrayTag tag) {
      this.beginArray('b');
      byte[] value = tag.getValue();
      int i = 0;

      for (int length = value.length; i < length; i++) {
         this.printAndResetSeparator();
         this.value(Byte.toString(value[i]), 'b');
      }

      this.endArray();
      return this;
   }

   private TagStringWriter writeIntArray(IntArrayTag tag) {
      this.beginArray('i');
      int[] value = tag.getValue();
      int i = 0;

      for (int length = value.length; i < length; i++) {
         this.printAndResetSeparator();
         this.value(Integer.toString(value[i]), 'i');
      }

      this.endArray();
      return this;
   }

   private TagStringWriter writeLongArray(LongArrayTag tag) {
      this.beginArray('l');
      long[] value = tag.getValue();
      int i = 0;

      for (int length = value.length; i < length; i++) {
         this.printAndResetSeparator();
         this.value(Long.toString(value[i]), 'l');
      }

      this.endArray();
      return this;
   }

   public TagStringWriter beginCompound() {
      this.printAndResetSeparator();
      this.out.append('{');
      return this;
   }

   public TagStringWriter endCompound() {
      this.out.append('}');
      this.needsSeparator = true;
      return this;
   }

   public TagStringWriter key(String key) {
      this.printAndResetSeparator();
      this.writeMaybeQuoted(key, false);
      this.out.append(':');
      return this;
   }

   public TagStringWriter value(String value, char valueType) {
      if (valueType == 0) {
         this.writeMaybeQuoted(value, true);
      } else {
         this.out.append(value);
         if (valueType != 'i') {
            this.out.append(valueType);
         }
      }

      this.needsSeparator = true;
      return this;
   }

   public TagStringWriter beginList() {
      this.printAndResetSeparator();
      this.out.append('[');
      return this;
   }

   public TagStringWriter endList() {
      this.out.append(']');
      this.needsSeparator = true;
      return this;
   }

   private TagStringWriter beginArray(char type) {
      this.beginList().out.append(type).append(';');
      return this;
   }

   private TagStringWriter endArray() {
      return this.endList();
   }

   private void writeMaybeQuoted(String content, boolean requireQuotes) {
      if (!requireQuotes) {
         for (int i = 0; i < content.length(); i++) {
            if (!Tokens.id(content.charAt(i))) {
               requireQuotes = true;
               break;
            }
         }
      }

      if (requireQuotes) {
         this.out.append('"');
         this.out.append(escape(content, '"'));
         this.out.append('"');
      } else {
         this.out.append(content);
      }
   }

   private static String escape(String content, char quoteChar) {
      StringBuilder output = new StringBuilder(content.length());

      for (int i = 0; i < content.length(); i++) {
         char c = content.charAt(i);
         if (c == quoteChar || c == '\\') {
            output.append('\\');
         }

         output.append(c);
      }

      return output.toString();
   }

   private void printAndResetSeparator() {
      if (this.needsSeparator) {
         this.out.append(',');
         this.needsSeparator = false;
      }
   }
}
