package com.viaversion.viaversion.api.protocol.remapper;

import com.viaversion.viaversion.protocol.packet.PacketWrapperImpl;

@FunctionalInterface
public interface ValueWriter {
   void a(PacketWrapperImpl var1, Object var2) throws Exception;
}
