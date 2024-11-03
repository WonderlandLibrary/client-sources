package com.viaversion.viabackwards.protocol.protocol1_14to1_14_1;

import com.viaversion.viabackwards.api.BackwardsProtocol;
import com.viaversion.viabackwards.protocol.protocol1_14to1_14_1.packets.EntityPackets1_14_1;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_15;
import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.data.entity.EntityTrackerBase;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.ClientboundPackets1_14;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.ServerboundPackets1_14;

public class Protocol1_14To1_14_1 extends BackwardsProtocol<ClientboundPackets1_14, ClientboundPackets1_14, ServerboundPackets1_14, ServerboundPackets1_14> {
   private final EntityPackets1_14_1 entityRewriter = new EntityPackets1_14_1(this);

   public Protocol1_14To1_14_1() {
      super(ClientboundPackets1_14.class, ClientboundPackets1_14.class, ServerboundPackets1_14.class, ServerboundPackets1_14.class);
   }

   @Override
   protected void registerPackets() {
      this.entityRewriter.register();
   }

   @Override
   public void init(UserConnection user) {
      user.addEntityTracker((Class<? extends Protocol>)this.getClass(), new EntityTrackerBase(user, EntityTypes1_15.PLAYER));
   }

   public EntityPackets1_14_1 getEntityRewriter() {
      return this.entityRewriter;
   }
}
