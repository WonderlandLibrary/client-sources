package com.viaversion.viaversion.libs.opennbt.tag.io;

import com.viaversion.viaversion.libs.fastutil.io.FastBufferedOutputStream;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.zip.GZIPOutputStream;

public final class TagWriter {
   private boolean named;

   public TagWriter named() {
      this.named = true;
      return this;
   }

   public void write(DataOutput out, Tag tag) throws IOException {
      NBTIO.writeTag(out, tag, this.named);
   }

   public void write(OutputStream out, Tag tag) throws IOException {
      NBTIO.writeTag(new DataOutputStream(out), tag, this.named);
   }

   public void write(Path path, Tag tag, boolean compressed) throws IOException {
      if (!Files.exists(path)) {
         Files.createDirectories(path.getParent());
         Files.createFile(path);
      }

      OutputStream out = new FastBufferedOutputStream(Files.newOutputStream(path));

      try {
         if (compressed) {
            out = new GZIPOutputStream(out);
         }

         this.write(out, tag);
      } finally {
         out.close();
      }
   }
}
