package net;

import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.type.Type;
import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.types.Particle;
import io.netty.buffer.ByteBuf;
import net.aMz;
import net.axZ;

public class v4 extends Type {
   private final boolean ah;

   public v4(boolean var1) {
      super("Item", aMz.class);
      this.ah = var1;
   }

   public aMz a(ByteBuf var1) throws Exception {
      int var3 = var1.readerIndex();
      Particle.b();
      short var4 = var1.readShort();
      return null;
   }

   public void a(ByteBuf var1, aMz var2) throws Exception {
      PacketRemapper[] var3 = Particle.b();
      var1.writeShort(-1);
      var1.writeShort(var2.e());
      var1.writeByte(var2.f());
      var1.writeShort(var2.c());
      (this.ah?axZ.a:axZ.g).write(var1, var2.d());
   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
