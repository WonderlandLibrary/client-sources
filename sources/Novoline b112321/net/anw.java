package net;

import com.github.steveice10.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocol.packet.PacketWrapperImpl;
import net.EN;
import net.aqv;

class anw extends PacketRemapper {
   final aqv c;

   anw(aqv var1) {
      this.c = var1;
   }

   public void registerMap() {
      this.a(this::lambda$registerMap$0);
   }

   private void lambda$registerMap$0(PacketWrapperImpl var1) throws Exception {
      CompoundTag var2 = (CompoundTag)var1.b(Type.ac);
      var1.a(Type.STRING, aqv.a(this.c, var2));
   }
}
