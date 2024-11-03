package com.viaversion.viabackwards.protocol.protocol1_20to1_20_2.storage;

import com.google.common.base.Preconditions;
import com.viaversion.viabackwards.protocol.protocol1_20to1_20_2.Protocol1_20To1_20_2;
import com.viaversion.viaversion.api.connection.StorableObject;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.protocol.packet.PacketType;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.protocols.protocol1_19_4to1_19_3.ClientboundPackets1_19_4;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import java.util.ArrayList;
import java.util.List;
import org.checkerframework.checker.nullness.qual.Nullable;

public final class ConfigurationPacketStorage implements StorableObject {
   private final List<ConfigurationPacketStorage.QueuedPacket> rawPackets = new ArrayList<>();
   private CompoundTag registry;
   private String[] enabledFeatures;
   private boolean finished;
   private ConfigurationPacketStorage.QueuedPacket resourcePack;

   public void setResourcePack(PacketWrapper wrapper) throws Exception {
      this.resourcePack = this.toQueuedPacket(wrapper, ClientboundPackets1_19_4.RESOURCE_PACK);
   }

   public CompoundTag registry() {
      Preconditions.checkNotNull(this.registry);
      return this.registry;
   }

   public void setRegistry(CompoundTag registry) {
      this.registry = registry;
   }

   @Nullable
   public String[] enabledFeatures() {
      return this.enabledFeatures;
   }

   public void setEnabledFeatures(String[] enabledFeatures) {
      this.enabledFeatures = enabledFeatures;
   }

   public void addRawPacket(PacketWrapper wrapper, PacketType type) throws Exception {
      this.rawPackets.add(this.toQueuedPacket(wrapper, type));
   }

   private ConfigurationPacketStorage.QueuedPacket toQueuedPacket(PacketWrapper wrapper, PacketType type) throws Exception {
      Preconditions.checkArgument(!wrapper.isCancelled(), "Wrapper should be cancelled AFTER calling toQueuedPacket");
      ByteBuf buf = Unpooled.buffer();
      wrapper.setId(-1);
      wrapper.writeToBuffer(buf);
      return new ConfigurationPacketStorage.QueuedPacket(buf, type);
   }

   public void sendQueuedPackets(UserConnection connection) throws Exception {
      if (this.resourcePack != null) {
         this.rawPackets.add(this.resourcePack);
         this.resourcePack = null;
      }

      for (ConfigurationPacketStorage.QueuedPacket queuedPacket : this.rawPackets) {
         try {
            PacketWrapper packet = PacketWrapper.create(queuedPacket.packetType(), queuedPacket.buf(), connection);
            packet.send(Protocol1_20To1_20_2.class);
         } finally {
            queuedPacket.buf().release();
         }
      }
   }

   public boolean isFinished() {
      return this.finished;
   }

   public void setFinished(boolean finished) {
      this.finished = finished;
   }

   public static final class QueuedPacket {
      private final ByteBuf buf;
      private final PacketType packetType;

      public QueuedPacket(ByteBuf buf, PacketType packetType) {
         this.buf = buf;
         this.packetType = packetType;
      }

      public ByteBuf buf() {
         return this.buf;
      }

      public PacketType packetType() {
         return this.packetType;
      }
   }
}
