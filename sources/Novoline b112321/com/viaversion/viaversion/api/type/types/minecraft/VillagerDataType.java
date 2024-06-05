package com.viaversion.viaversion.api.type.types.minecraft;

import com.viaversion.viaversion.api.type.Type;
import io.netty.buffer.ByteBuf;
import net.MB;

public class VillagerDataType extends Type {
   public VillagerDataType() {
      super("VillagerData", MB.class);
   }

   public MB a(ByteBuf var1) throws Exception {
      return new MB(Type.VAR_INT.readPrimitive(var1), Type.VAR_INT.readPrimitive(var1), Type.VAR_INT.readPrimitive(var1));
   }

   public void a(ByteBuf var1, MB var2) throws Exception {
      Type.VAR_INT.writePrimitive(var1, var2.a());
      Type.VAR_INT.writePrimitive(var1, var2.c());
      Type.VAR_INT.writePrimitive(var1, var2.b());
   }
}
