package com.viaversion.viaversion.libs.opennbt.tag.io;

import com.viaversion.viaversion.libs.opennbt.tag.TagRegistry;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
import com.viaversion.viaversion.libs.opennbt.tag.limiter.TagLimiter;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import org.jetbrains.annotations.Nullable;

public final class NBTIO {
   private NBTIO() {
   }

   public static TagReader<Tag> reader() {
      return new TagReader<>(null);
   }

   public static <T extends Tag> TagReader<T> reader(Class<T> expectedTagType) {
      return new TagReader<>(expectedTagType);
   }

   public static TagWriter writer() {
      return new TagWriter();
   }

   public static <T extends Tag> T readTag(DataInput in, TagLimiter tagLimiter, boolean named, @Nullable Class<T> expectedTagType) throws IOException {
      int id = in.readByte();
      if (expectedTagType != null && expectedTagType != TagRegistry.getClassFor(id)) {
         throw new IOException("Expected tag type " + expectedTagType.getSimpleName() + " but got " + TagRegistry.getClassFor(id).getSimpleName());
      } else {
         if (named) {
            in.skipBytes(in.readUnsignedShort());
         }

         return (T)TagRegistry.read(id, in, tagLimiter, 0);
      }
   }

   public static void writeTag(DataOutput out, Tag tag, boolean named) throws IOException {
      out.writeByte(tag.getTagId());
      if (named) {
         out.writeUTF("");
      }

      tag.write(out);
   }
}
