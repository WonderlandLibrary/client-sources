package com.viaversion.viabackwards.protocol.protocol1_10to1_11;

import com.viaversion.viabackwards.api.BackwardsProtocol;
import com.viaversion.viabackwards.api.data.BackwardsMappings;
import com.viaversion.viabackwards.api.rewriters.SoundRewriter;
import com.viaversion.viabackwards.protocol.protocol1_10to1_11.packets.BlockItemPackets1_11;
import com.viaversion.viabackwards.protocol.protocol1_10to1_11.packets.EntityPackets1_11;
import com.viaversion.viabackwards.protocol.protocol1_10to1_11.packets.PlayerPackets1_11;
import com.viaversion.viabackwards.protocol.protocol1_10to1_11.storage.WindowTracker;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.ClientWorld;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_11;
import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.data.entity.EntityTrackerBase;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.ClientboundPackets1_9_3;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.ServerboundPackets1_9_3;

public class Protocol1_10To1_11 extends BackwardsProtocol<ClientboundPackets1_9_3, ClientboundPackets1_9_3, ServerboundPackets1_9_3, ServerboundPackets1_9_3> {
   public static final BackwardsMappings MAPPINGS = new BackwardsMappings("1.11", "1.10");
   private final EntityPackets1_11 entityPackets = new EntityPackets1_11(this);
   private BlockItemPackets1_11 blockItemPackets;

   public Protocol1_10To1_11() {
      super(ClientboundPackets1_9_3.class, ClientboundPackets1_9_3.class, ServerboundPackets1_9_3.class, ServerboundPackets1_9_3.class);
   }

   @Override
   protected void registerPackets() {
      (this.blockItemPackets = new BlockItemPackets1_11(this)).register();
      this.entityPackets.register();
      new PlayerPackets1_11().register(this);
      SoundRewriter<ClientboundPackets1_9_3> soundRewriter = new SoundRewriter<>(this);
      soundRewriter.registerNamedSound(ClientboundPackets1_9_3.NAMED_SOUND);
      soundRewriter.registerSound(ClientboundPackets1_9_3.SOUND);
   }

   @Override
   public void init(UserConnection user) {
      if (!user.has(ClientWorld.class)) {
         user.put(new ClientWorld());
      }

      user.addEntityTracker((Class<? extends Protocol>)this.getClass(), new EntityTrackerBase(user, EntityTypes1_11.EntityType.PLAYER));
      if (!user.has(WindowTracker.class)) {
         user.put(new WindowTracker());
      }
   }

   @Override
   public BackwardsMappings getMappingData() {
      return MAPPINGS;
   }

   public EntityPackets1_11 getEntityRewriter() {
      return this.entityPackets;
   }

   public BlockItemPackets1_11 getItemRewriter() {
      return this.blockItemPackets;
   }

   @Override
   public boolean hasMappingDataToLoad() {
      return true;
   }
}
