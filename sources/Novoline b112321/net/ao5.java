package net;

import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocol.packet.PacketWrapperImpl;
import java.util.ArrayList;
import net.EN;
import net.Zv;
import net.aSG;
import net.agc;
import net.aq3;
import net.g4;
import net.y4;

class ao5 extends PacketRemapper {
   final aq3 c;

   ao5(aq3 var1) {
      this.c = var1;
   }

   public void registerMap() {
      this.a(Type.VAR_INT);
      this.a(Type.UUID);
      this.a(Type.VAR_INT);
      this.a(Type.m);
      this.a(Type.m);
      this.a(Type.m);
      this.a(Type.k);
      this.a(Type.k);
      this.a(Type.k);
      this.a(Type.SHORT);
      this.a(Type.SHORT);
      this.a(Type.SHORT);
      this.a(ao5::lambda$registerMap$0);
      this.a(this::lambda$registerMap$1);
   }

   private void lambda$registerMap$1(PacketWrapperImpl var1) throws Exception {
      int var2 = ((Integer)var1.b(Type.VAR_INT, 1)).intValue();
      g4 var3 = agc.a(var2);
      aq3.a(this.c, var1, ((Integer)var1.b(Type.VAR_INT, 0)).intValue(), var3);
      var1.a(Type.VAR_INT, 1, Integer.valueOf(y4.a(var2)));
   }

   private static void lambda$registerMap$0(PacketWrapperImpl var0) throws Exception {
      var0.a(aSG.c, new ArrayList());
   }
}
