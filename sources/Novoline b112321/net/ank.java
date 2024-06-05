package net;

import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.type.Type;

final class ank extends PacketRemapper {
   public void registerMap() {
      this.map(Type.VAR_INT, Type.I);
      this.a(Type.k);
   }
}
