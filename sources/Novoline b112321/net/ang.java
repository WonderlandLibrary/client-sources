package net;

import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocol.packet.PacketWrapperImpl;
import net.EN;
import net.aq3;
import net.cW;
import net.g4;

class ang extends PacketRemapper {
   final aq3 c;

   ang(aq3 var1) {
      this.c = var1;
   }

   public void registerMap() {
      this.a(Type.I);
      this.a(Type.M);
      this.a(Type.I);
      this.map(Type.g, Type.af);
      this.a(Type.M);
      this.a(Type.STRING);
      this.a(Type.VAR_INT);
      this.a(Type.c);
      this.a(aq3.b(this.c, g4.PLAYER, Type.I));
      this.a(ang::lambda$registerMap$0);
   }

   private static void lambda$registerMap$0(PacketWrapperImpl var0) throws Exception {
      ((cW)var0.e().b(cW.class)).a(((Boolean)var0.b(Type.c)).booleanValue());
   }
}
