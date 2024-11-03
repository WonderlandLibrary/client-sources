package com.viaversion.viaversion.api.connection;

import com.viaversion.viaversion.api.data.entity.EntityTracker;
import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.protocol.packet.PacketTracker;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import org.checkerframework.checker.nullness.qual.Nullable;

public interface UserConnection {
   @Nullable
   <T extends StorableObject> T get(Class<T> var1);

   boolean has(Class<? extends StorableObject> var1);

   @Nullable
   <T extends StorableObject> T remove(Class<T> var1);

   void put(StorableObject var1);

   Collection<EntityTracker> getEntityTrackers();

   @Nullable
   <T extends EntityTracker> T getEntityTracker(Class<? extends Protocol> var1);

   void addEntityTracker(Class<? extends Protocol> var1, EntityTracker var2);

   default void clearStoredObjects() {
      this.clearStoredObjects(false);
   }

   void clearStoredObjects(boolean var1);

   void sendRawPacket(ByteBuf var1);

   void scheduleSendRawPacket(ByteBuf var1);

   ChannelFuture sendRawPacketFuture(ByteBuf var1);

   PacketTracker getPacketTracker();

   void disconnect(String var1);

   void sendRawPacketToServer(ByteBuf var1);

   void scheduleSendRawPacketToServer(ByteBuf var1);

   boolean checkServerboundPacket();

   boolean checkClientboundPacket();

   default boolean checkIncomingPacket() {
      return this.isClientSide() ? this.checkClientboundPacket() : this.checkServerboundPacket();
   }

   default boolean checkOutgoingPacket() {
      return this.isClientSide() ? this.checkServerboundPacket() : this.checkClientboundPacket();
   }

   boolean shouldTransformPacket();

   void transformClientbound(ByteBuf var1, Function<Throwable, Exception> var2) throws Exception;

   void transformServerbound(ByteBuf var1, Function<Throwable, Exception> var2) throws Exception;

   default void transformOutgoing(ByteBuf buf, Function<Throwable, Exception> cancelSupplier) throws Exception {
      if (this.isClientSide()) {
         this.transformServerbound(buf, cancelSupplier);
      } else {
         this.transformClientbound(buf, cancelSupplier);
      }
   }

   default void transformIncoming(ByteBuf buf, Function<Throwable, Exception> cancelSupplier) throws Exception {
      if (this.isClientSide()) {
         this.transformClientbound(buf, cancelSupplier);
      } else {
         this.transformServerbound(buf, cancelSupplier);
      }
   }

   long getId();

   @Nullable
   Channel getChannel();

   ProtocolInfo getProtocolInfo();

   Map<Class<?>, StorableObject> getStoredObjects();

   boolean isActive();

   void setActive(boolean var1);

   boolean isPendingDisconnect();

   void setPendingDisconnect(boolean var1);

   boolean isClientSide();

   boolean shouldApplyBlockProtocol();

   boolean isPacketLimiterEnabled();

   void setPacketLimiterEnabled(boolean var1);

   UUID generatePassthroughToken();
}
