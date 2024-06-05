package com.viaversion.viaversion.protocols.base;

import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocol.packet.PacketWrapperImpl;
import java.util.UUID;
import net.aRu;

public class BaseProtocol1_16 extends aRu {
   protected UUID a(PacketWrapperImpl var1) throws Exception {
      return (UUID)var1.a(Type.UUID_INT_ARRAY);
   }
}
