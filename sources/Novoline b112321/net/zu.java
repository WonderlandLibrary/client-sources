package net;

import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocol.packet.PacketWrapperImpl;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.data.NamedSoundRewriter;
import net.EN;
import net.aWp;

class zu implements EN {
   final aWp a;

   zu(aWp var1) {
      this.a = var1;
   }

   public void a(PacketWrapperImpl var1) throws Exception {
      String var2 = NamedSoundRewriter.getNewId((String)var1.b(Type.STRING, 0));
      var1.a(Type.STRING, 0, var2);
   }
}
