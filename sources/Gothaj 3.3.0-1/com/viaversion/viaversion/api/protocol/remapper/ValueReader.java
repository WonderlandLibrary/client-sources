package com.viaversion.viaversion.api.protocol.remapper;

import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;

@FunctionalInterface
public interface ValueReader<T> {
   T read(PacketWrapper var1) throws Exception;
}
