package net;

import com.viaversion.viabackwards.api.entities.storage.WrappedMetadata;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocol.packet.PacketWrapperImpl;
import java.util.List;
import net.EN;
import net.aqr;
import net.aqu;

class ac3 extends PacketRemapper {
   final Type d;
   final Type c;
   final aqr e;

   ac3(aqr var1, Type var2, Type var3) {
      this.e = var1;
      this.d = var2;
      this.c = var3;
   }

   public void registerMap() {
      aqu.d();
      this.a(Type.VAR_INT);
      if(this.d != null) {
         this.map(this.d, this.c);
      }

      this.a(this.c);
      this.a(this::lambda$registerMap$0);
   }

   private void lambda$registerMap$0(Type var1, PacketWrapperImpl var2) throws Exception {
      List var3 = (List)var2.b(var1, 0);
      var2.a(var1, 0, this.e.a(var2.e(), ((Integer)var2.b(Type.VAR_INT, 0)).intValue(), new WrappedMetadata(var3)).metadataList());
   }
}
