package com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.types.item;

import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.io.NBTIO;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class NBTType extends Type<CompoundTag> {
   public NBTType() {
      super(CompoundTag.class);
   }

   public CompoundTag read(ByteBuf buffer) throws IOException {
      short length = buffer.readShort();
      if (length <= 0) {
         return null;
      } else {
         ByteBuf compressed = buffer.readSlice(length);
         GZIPInputStream gzipStream = new GZIPInputStream(new ByteBufInputStream(compressed));

         CompoundTag var5;
         try {
            var5 = NBTIO.reader(CompoundTag.class).named().read(gzipStream);
         } catch (Throwable var8) {
            try {
               gzipStream.close();
            } catch (Throwable var7) {
               var8.addSuppressed(var7);
            }

            throw var8;
         }

         gzipStream.close();
         return var5;
      }
   }

   public void write(ByteBuf buffer, CompoundTag nbt) throws Exception {
      if (nbt == null) {
         buffer.writeShort(-1);
      } else {
         ByteBuf compressedBuf = buffer.alloc().buffer();

         try {
            GZIPOutputStream gzipStream = new GZIPOutputStream(new ByteBufOutputStream(compressedBuf));

            try {
               NBTIO.writer().named().write(gzipStream, nbt);
            } catch (Throwable var12) {
               try {
                  gzipStream.close();
               } catch (Throwable var11) {
                  var12.addSuppressed(var11);
               }

               throw var12;
            }

            gzipStream.close();
            buffer.writeShort(compressedBuf.readableBytes());
            buffer.writeBytes(compressedBuf);
         } finally {
            compressedBuf.release();
         }
      }
   }
}
