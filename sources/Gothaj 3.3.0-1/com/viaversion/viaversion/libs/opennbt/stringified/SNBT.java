package com.viaversion.viaversion.libs.opennbt.stringified;

import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;

public final class SNBT {
   private SNBT() {
   }

   public static Tag deserialize(String snbt) {
      CharBuffer buffer = new CharBuffer(snbt);
      TagStringReader parser = new TagStringReader(buffer);
      Tag tag = parser.tag();
      if (buffer.skipWhitespace().hasMore()) {
         throw new StringifiedTagParseException("Input has trailing content", buffer.index());
      } else {
         return tag;
      }
   }

   public static CompoundTag deserializeCompoundTag(String snbt) {
      CharBuffer buffer = new CharBuffer(snbt);
      TagStringReader reader = new TagStringReader(buffer);
      CompoundTag tag = reader.compound();
      if (buffer.skipWhitespace().hasMore()) {
         throw new StringifiedTagParseException("Input has trailing content", buffer.index());
      } else {
         return tag;
      }
   }

   public static String serialize(Tag tag) {
      StringBuilder builder = new StringBuilder();
      TagStringWriter writer = new TagStringWriter(builder);
      writer.writeTag(tag);
      return builder.toString();
   }
}
