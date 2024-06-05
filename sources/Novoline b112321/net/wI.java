package net;

import com.viaversion.viaversion.api.minecraft.Position;
import com.viaversion.viaversion.api.type.Type;
import io.netty.buffer.ByteBuf;

public class wI extends Type {
   public wI() {
      super("Position", Position.class);
   }

   public Position a(ByteBuf var1) throws Exception {
      boolean var2 = var1.readBoolean();
      return null;
   }

   public void a(ByteBuf var1, Position var2) throws Exception {
      var1.writeBoolean(true);
      Type.POSITION.write(var1, var2);
   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
