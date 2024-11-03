package com.viaversion.viaversion.api.protocol.packet;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
import com.viaversion.viaversion.api.type.Type;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import java.util.List;
import org.checkerframework.checker.nullness.qual.Nullable;

public interface PacketWrapper {
   int PASSTHROUGH_ID = 1000;

   static PacketWrapper create(@Nullable PacketType packetType, UserConnection connection) {
      return create(packetType, null, connection);
   }

   static PacketWrapper create(@Nullable PacketType packetType, @Nullable ByteBuf inputBuffer, UserConnection connection) {
      return Via.getManager().getProtocolManager().createPacketWrapper(packetType, inputBuffer, connection);
   }

   @Deprecated
   static PacketWrapper create(int packetId, @Nullable ByteBuf inputBuffer, UserConnection connection) {
      return Via.getManager().getProtocolManager().createPacketWrapper(packetId, inputBuffer, connection);
   }

   <T> T get(Type<T> var1, int var2) throws Exception;

   boolean is(Type var1, int var2);

   boolean isReadable(Type var1, int var2);

   <T> void set(Type<T> var1, int var2, T var3) throws Exception;

   <T> T read(Type<T> var1) throws Exception;

   <T> void write(Type<T> var1, T var2);

   <T> T passthrough(Type<T> var1) throws Exception;

   void passthroughAll() throws Exception;

   void writeToBuffer(ByteBuf var1) throws Exception;

   void clearInputBuffer();

   void clearPacket();

   default void send(Class<? extends Protocol> protocol) throws Exception {
      this.send(protocol, true);
   }

   void send(Class<? extends Protocol> var1, boolean var2) throws Exception;

   default void scheduleSend(Class<? extends Protocol> protocol) throws Exception {
      this.scheduleSend(protocol, true);
   }

   void scheduleSend(Class<? extends Protocol> var1, boolean var2) throws Exception;

   ChannelFuture sendFuture(Class<? extends Protocol> var1) throws Exception;

   @Deprecated
   default void send() throws Exception {
      this.sendRaw();
   }

   void sendRaw() throws Exception;

   void scheduleSendRaw() throws Exception;

   default PacketWrapper create(PacketType packetType) {
      return this.create(packetType.getId());
   }

   default PacketWrapper create(PacketType packetType, PacketHandler handler) throws Exception {
      return this.create(packetType.getId(), handler);
   }

   PacketWrapper create(int var1);

   PacketWrapper create(int var1, PacketHandler var2) throws Exception;

   PacketWrapper apply(Direction var1, State var2, int var3, List<Protocol> var4, boolean var5) throws Exception;

   PacketWrapper apply(Direction var1, State var2, int var3, List<Protocol> var4) throws Exception;

   boolean isCancelled();

   default void cancel() {
      this.setCancelled(true);
   }

   void setCancelled(boolean var1);

   UserConnection user();

   void resetReader();

   @Deprecated
   default void sendToServer() throws Exception {
      this.sendToServerRaw();
   }

   void sendToServerRaw() throws Exception;

   void scheduleSendToServerRaw() throws Exception;

   default void sendToServer(Class<? extends Protocol> protocol) throws Exception {
      this.sendToServer(protocol, true);
   }

   void sendToServer(Class<? extends Protocol> var1, boolean var2) throws Exception;

   default void scheduleSendToServer(Class<? extends Protocol> protocol) throws Exception {
      this.scheduleSendToServer(protocol, true);
   }

   void scheduleSendToServer(Class<? extends Protocol> var1, boolean var2) throws Exception;

   @Nullable
   PacketType getPacketType();

   void setPacketType(@Nullable PacketType var1);

   int getId();

   @Deprecated
   default void setId(PacketType packetType) {
      this.setPacketType(packetType);
   }

   @Deprecated
   void setId(int var1);
}
