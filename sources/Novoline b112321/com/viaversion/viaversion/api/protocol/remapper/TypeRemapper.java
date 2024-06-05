package com.viaversion.viaversion.api.protocol.remapper;

import com.viaversion.viaversion.api.protocol.remapper.ValueWriter;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocol.packet.PacketWrapperImpl;
import net.aQd;

public class TypeRemapper implements aQd, ValueWriter {
   private final Type type;

   public TypeRemapper(Type var1) {
      this.type = var1;
   }

   public Object a(PacketWrapperImpl var1) throws Exception {
      return var1.b(this.type);
   }

   public void a(PacketWrapperImpl var1, Object var2) {
      var1.a(this.type, var2);
   }
}
