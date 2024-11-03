package com.viaversion.viaversion.protocols.protocol1_14_1to1_14;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_14;
import com.viaversion.viaversion.api.protocol.AbstractProtocol;
import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.data.entity.EntityTrackerBase;
import com.viaversion.viaversion.protocols.protocol1_14_1to1_14.metadata.MetadataRewriter1_14_1To1_14;
import com.viaversion.viaversion.protocols.protocol1_14_1to1_14.packets.EntityPackets;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.ClientboundPackets1_14;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.ServerboundPackets1_14;

public class Protocol1_14_1To1_14 extends AbstractProtocol<ClientboundPackets1_14, ClientboundPackets1_14, ServerboundPackets1_14, ServerboundPackets1_14> {
   private final MetadataRewriter1_14_1To1_14 metadataRewriter = new MetadataRewriter1_14_1To1_14(this);

   public Protocol1_14_1To1_14() {
      super(ClientboundPackets1_14.class, ClientboundPackets1_14.class, ServerboundPackets1_14.class, ServerboundPackets1_14.class);
   }

   @Override
   protected void registerPackets() {
      this.metadataRewriter.register();
      EntityPackets.register(this);
   }

   @Override
   public void init(UserConnection userConnection) {
      userConnection.addEntityTracker((Class<? extends Protocol>)this.getClass(), new EntityTrackerBase(userConnection, EntityTypes1_14.PLAYER));
   }

   public MetadataRewriter1_14_1To1_14 getEntityRewriter() {
      return this.metadataRewriter;
   }
}
