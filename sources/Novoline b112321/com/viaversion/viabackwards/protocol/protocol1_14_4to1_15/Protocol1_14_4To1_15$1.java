package com.viaversion.viabackwards.protocol.protocol1_14_4to1_15;

import com.viaversion.viabackwards.protocol.protocol1_14_4to1_15.Protocol1_14_4To1_15;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocol.packet.PacketWrapperImpl;
import net.EN;

class Protocol1_14_4To1_15$1 extends PacketRemapper {
   final Protocol1_14_4To1_15 this$0;

   Protocol1_14_4To1_15$1(Protocol1_14_4To1_15 var1) {
      this.this$0 = var1;
   }

   public void registerMap() {
      this.a(Type.FLOAT);
      this.a(Type.FLOAT);
      this.a(Type.FLOAT);
      this.a(this::lambda$registerMap$0);
   }

   private int toEffectCoordinate(float var1) {
      return (int)(var1 * 8.0F);
   }

   private void lambda$registerMap$0(PacketWrapperImpl var1) throws Exception {
      PacketWrapperImpl var2 = var1.a(81);
      var2.a(Type.VAR_INT, Integer.valueOf(243));
      var2.a(Type.VAR_INT, Integer.valueOf(4));
      var2.a(Type.I, Integer.valueOf(this.toEffectCoordinate(((Float)var1.b(Type.FLOAT, 0)).floatValue())));
      var2.a(Type.I, Integer.valueOf(this.toEffectCoordinate(((Float)var1.b(Type.FLOAT, 1)).floatValue())));
      var2.a(Type.I, Integer.valueOf(this.toEffectCoordinate(((Float)var1.b(Type.FLOAT, 2)).floatValue())));
      var2.a(Type.FLOAT, Float.valueOf(4.0F));
      var2.a(Type.FLOAT, Float.valueOf(1.0F));
      var2.a(Protocol1_14_4To1_15.class);
   }
}
