package net;

import net.acE;
import net.amR;
import net.rX;
import viaversion.viaversion.api.rewriters.MetadataRewriter;
import viaversion.viaversion.api.type.Type;
import viaversion.viaversion.protocols.protocol1_11to1_10.EntityIdRewriter;
import viaversion.viaversion.protocols.protocol1_11to1_10.Protocol1_11To1_10;

class a75 extends acE {
   final MetadataRewriter c;
   final Protocol1_11To1_10 d;

   a75(Protocol1_11To1_10 var1, MetadataRewriter var2) {
      this.d = var1;
      this.c = var2;
   }

   public void registerMap() {
      this.a(Type.VAR_INT);
      this.a(Type.UUID);
      this.a(Type.UNSIGNED_BYTE, Type.VAR_INT);
      this.a(Type.DOUBLE);
      this.a(Type.DOUBLE);
      this.a(Type.DOUBLE);
      this.a(Type.BYTE);
      this.a(Type.BYTE);
      this.a(Type.BYTE);
      this.a(Type.SHORT);
      EntityIdRewriter.b();
      this.a(Type.SHORT);
      this.a(Type.SHORT);
      this.a(rX.a);
      this.a(new amR(this));
      if(acE.b() == null) {
         EntityIdRewriter.b(new String[4]);
      }

   }
}
