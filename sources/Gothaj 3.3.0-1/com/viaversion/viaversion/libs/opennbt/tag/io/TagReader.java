package com.viaversion.viaversion.libs.opennbt.tag.io;

import com.viaversion.viaversion.libs.fastutil.io.FastBufferedInputStream;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
import com.viaversion.viaversion.libs.opennbt.tag.limiter.TagLimiter;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.zip.GZIPInputStream;
import org.jetbrains.annotations.Nullable;

public final class TagReader<T extends Tag> {
   private final Class<T> expectedTagType;
   private TagLimiter tagLimiter = TagLimiter.noop();
   private boolean named;

   TagReader(@Nullable Class<T> expectedTagType) {
      this.expectedTagType = expectedTagType;
   }

   public TagReader<T> tagLimiter(TagLimiter tagLimiter) {
      this.tagLimiter = tagLimiter;
      return this;
   }

   public TagReader<T> named() {
      this.named = true;
      return this;
   }

   public T read(DataInput in) throws IOException {
      this.tagLimiter.reset();
      return NBTIO.readTag(in, this.tagLimiter, this.named, this.expectedTagType);
   }

   public T read(InputStream in) throws IOException {
      DataInput dataInput = new DataInputStream(in);
      return this.read(dataInput);
   }

   public T read(Path path, boolean compressed) throws IOException {
      InputStream in = new FastBufferedInputStream(Files.newInputStream(path));

      Tag var4;
      try {
         if (compressed) {
            in = new GZIPInputStream(in);
         }

         var4 = this.read(in);
      } finally {
         in.close();
      }

      return (T)var4;
   }
}
