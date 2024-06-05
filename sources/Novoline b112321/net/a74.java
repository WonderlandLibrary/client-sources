package net;

import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocol.packet.PacketWrapperImpl;
import java.util.List;
import net.EN;
import net.aTD;

class a74 extends PacketRemapper {
   final Type e;
   final Type d;
   final aTD c;

   a74(aTD var1, Type var2, Type var3) {
      this.c = var1;
      this.e = var2;
      this.d = var3;
   }

   public void registerMap() {
      aTD.e();
      this.a(Type.VAR_INT);
      if(this.e != null) {
         this.map(this.e, this.d);
      }

      this.a(this.d);
      this.a(this::lambda$registerMap$0);
   }

   private void lambda$registerMap$0(Type var1, PacketWrapperImpl var2) throws Exception {
      int var3 = ((Integer)var2.b(Type.VAR_INT, 0)).intValue();
      List var4 = (List)var2.b(var1, 0);
      this.c.a(var3, var4, var2.e());
   }
}
