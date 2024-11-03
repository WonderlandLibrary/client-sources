package com.viaversion.viaversion.api.protocol.packet;

import com.viaversion.viaversion.api.connection.UserConnection;
import java.util.function.Consumer;
import org.checkerframework.checker.nullness.qual.Nullable;

public interface VersionedPacketTransformer<C extends ClientboundPacketType, S extends ServerboundPacketType> {
   boolean send(PacketWrapper var1) throws Exception;

   boolean send(UserConnection var1, C var2, Consumer<PacketWrapper> var3) throws Exception;

   boolean send(UserConnection var1, S var2, Consumer<PacketWrapper> var3) throws Exception;

   boolean scheduleSend(PacketWrapper var1) throws Exception;

   boolean scheduleSend(UserConnection var1, C var2, Consumer<PacketWrapper> var3) throws Exception;

   boolean scheduleSend(UserConnection var1, S var2, Consumer<PacketWrapper> var3) throws Exception;

   @Nullable
   PacketWrapper transform(PacketWrapper var1) throws Exception;

   @Nullable
   PacketWrapper transform(UserConnection var1, C var2, Consumer<PacketWrapper> var3) throws Exception;

   @Nullable
   PacketWrapper transform(UserConnection var1, S var2, Consumer<PacketWrapper> var3) throws Exception;
}
