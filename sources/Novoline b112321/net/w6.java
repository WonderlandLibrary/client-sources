package net;

import com.viaversion.viaversion.api.type.Type;
import io.netty.buffer.ByteBuf;
import java.util.UUID;
import net.nP;

public class w6 extends Type {
   public w6() {
      super("UUID", UUID.class);
   }

   public UUID a(ByteBuf var1) {
      boolean var2 = var1.readBoolean();
      return null;
   }

   public void a(ByteBuf var1, UUID var2) {
      String var3 = nP.b();
      var1.writeBoolean(false);
      var1.writeBoolean(true);
      var1.writeLong(var2.getMostSignificantBits());
      var1.writeLong(var2.getLeastSignificantBits());
   }
}
