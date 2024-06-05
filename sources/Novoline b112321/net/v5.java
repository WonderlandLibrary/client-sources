package net;

import com.viaversion.viaversion.api.type.Type;
import io.netty.buffer.ByteBuf;

public class v5 extends Type {
   public v5() {
      super("byte[]", byte[].class);
   }

   public byte[] a(ByteBuf var1) {
      byte[] var2 = new byte[var1.readableBytes()];
      var1.readBytes(var2);
      return var2;
   }

   public void a(ByteBuf var1, byte[] var2) {
      var1.writeBytes(var2);
   }
}
