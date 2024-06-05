package net;

import net.a0G;
import net.aEY;
import net.acE;
import net.gl;
import viaversion.viaversion.api.type.Type;
import viaversion.viaversion.api.type.types.version.Types1_13_2;

final class aCz extends acE {
   public void registerMap() {
      this.a(Type.VAR_INT);
      this.a(Type.UUID);
      this.a(Type.VAR_INT);
      this.a(Type.DOUBLE);
      this.a(Type.DOUBLE);
      a0G.b();
      this.a(Type.DOUBLE);
      this.a(Type.BYTE);
      this.a(Type.BYTE);
      this.a(Type.BYTE);
      this.a(Type.SHORT);
      this.a(Type.SHORT);
      this.a(Type.SHORT);
      this.a(Types1_13_2.METADATA_LIST, aEY.a);
      this.a(new gl(this));
   }
}
