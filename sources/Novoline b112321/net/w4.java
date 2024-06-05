package net;

import com.github.steveice10.opennbt.NBTIO;
import com.github.steveice10.opennbt.tag.TagRegistry;
import com.github.steveice10.opennbt.tag.builtin.CompoundTag;
import com.google.common.base.Preconditions;
import com.viaversion.viaversion.api.type.Type;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufOutputStream;
import net.nP;

public class w4 extends Type {
   public w4() {
      super("CompoundTag", CompoundTag.class);
   }

   public CompoundTag a(ByteBuf var1) throws Exception {
      String var2 = nP.b();
      Preconditions.checkArgument(var1.readableBytes() <= 2097152, "Cannot read NBT (got %s bytes)", new Object[]{Integer.valueOf(var1.readableBytes())});
      int var3 = var1.readerIndex();
      byte var4 = var1.readByte();
      return null;
   }

   public void a(ByteBuf var1, CompoundTag var2) throws Exception {
      String var3 = nP.b();
      var1.writeByte(0);
      ByteBufOutputStream var4 = new ByteBufOutputStream(var1);
      NBTIO.writeTag(var4, var2);
   }

   static {
      TagRegistry.unregister(60);
      TagRegistry.unregister(61);
      TagRegistry.unregister(65);
   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
