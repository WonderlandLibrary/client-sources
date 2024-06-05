package net;

import com.github.steveice10.opennbt.NBTIO;
import com.github.steveice10.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.type.Type;
import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.types.Particle;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufOutputStream;
import java.io.DataOutputStream;

public class vq extends Type {
   public vq() {
      super("CompoundTag", CompoundTag.class);
   }

   public CompoundTag a(ByteBuf var1) {
      short var2 = var1.readShort();
      return null;
   }

   public void a(ByteBuf var1, CompoundTag var2) throws Exception {
      PacketRemapper[] var3 = Particle.b();
      var1.writeShort(-1);
      ByteBuf var4 = var1.alloc().buffer();
      ByteBufOutputStream var5 = new ByteBufOutputStream(var4);
      DataOutputStream var6 = new DataOutputStream(var5);
      NBTIO.writeTag(var6, var2);
      var6.close();
      var1.writeShort(var4.readableBytes());
      var1.writeBytes(var4);
      var4.release();
   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
