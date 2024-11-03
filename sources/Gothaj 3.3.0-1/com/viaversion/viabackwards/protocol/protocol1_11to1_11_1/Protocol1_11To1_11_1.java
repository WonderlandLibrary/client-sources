package com.viaversion.viabackwards.protocol.protocol1_11to1_11_1;

import com.viaversion.viabackwards.api.BackwardsProtocol;
import com.viaversion.viabackwards.protocol.protocol1_11to1_11_1.packets.EntityPackets1_11_1;
import com.viaversion.viabackwards.protocol.protocol1_11to1_11_1.packets.ItemPackets1_11_1;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.ClientWorld;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_11;
import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.data.entity.EntityTrackerBase;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.ClientboundPackets1_9_3;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.ServerboundPackets1_9_3;

public class Protocol1_11To1_11_1 extends BackwardsProtocol<ClientboundPackets1_9_3, ClientboundPackets1_9_3, ServerboundPackets1_9_3, ServerboundPackets1_9_3> {
   private final EntityPackets1_11_1 entityPackets = new EntityPackets1_11_1(this);
   private final ItemPackets1_11_1 itemRewriter = new ItemPackets1_11_1(this);

   public Protocol1_11To1_11_1() {
      super(ClientboundPackets1_9_3.class, ClientboundPackets1_9_3.class, ServerboundPackets1_9_3.class, ServerboundPackets1_9_3.class);
   }

   @Override
   protected void registerPackets() {
      this.entityPackets.register();
      this.itemRewriter.register();
   }

   @Override
   public void init(UserConnection user) {
      if (!user.has(ClientWorld.class)) {
         user.put(new ClientWorld());
      }

      user.addEntityTracker((Class<? extends Protocol>)this.getClass(), new EntityTrackerBase(user, EntityTypes1_11.EntityType.PLAYER));
   }

   public EntityPackets1_11_1 getEntityRewriter() {
      return this.entityPackets;
   }

   public ItemPackets1_11_1 getItemRewriter() {
      return this.itemRewriter;
   }
}
