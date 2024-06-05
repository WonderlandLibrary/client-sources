package com.viaversion.viaversion.rewriter;

import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocol.packet.PacketWrapperImpl;
import net.EN;
import net.aMz;
import net.aQU;
import net.aTD;

class ItemRewriter$4 extends PacketRemapper {
   final Type val$type;
   final aQU c;

   ItemRewriter$4(aQU var1, Type var2) {
      this.c = var1;
      this.val$type = var2;
   }

   public void registerMap() {
      this.a(Type.VAR_INT);
      this.a(this::lambda$registerMap$0);
   }

   private void lambda$registerMap$0(Type var1, PacketWrapperImpl var2) throws Exception {
      boolean var3 = aTD.c();

      while(true) {
         byte var4 = ((Byte)var2.a(Type.k)).byteValue();
         aQU.b((aQU)this.c).a((aMz)var2.a(var1));
         if((var4 & -128) == 0) {
            break;
         }
      }

   }
}
