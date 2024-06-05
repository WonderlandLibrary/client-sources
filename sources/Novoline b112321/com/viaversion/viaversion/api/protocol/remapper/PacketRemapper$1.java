package com.viaversion.viaversion.api.protocol.remapper;

import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.protocol.remapper.ValueTransformer;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocol.packet.PacketWrapperImpl;
import java.util.function.Function;

class PacketRemapper$1 extends ValueTransformer {
   final Function val$transformer;
   final PacketRemapper this$0;

   PacketRemapper$1(PacketRemapper var1, Type var2, Function var3) {
      super(var2);
      this.this$0 = var1;
      this.val$transformer = var3;
   }

   public Object a(PacketWrapperImpl var1, Object var2) throws Exception {
      return this.val$transformer.apply(var2);
   }
}
