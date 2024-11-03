package com.viaversion.viaversion.protocols.protocol1_18to1_17_1.packets;

import com.viaversion.viaversion.api.data.entity.EntityTracker;
import com.viaversion.viaversion.api.minecraft.Particle;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_17;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.version.Types1_17;
import com.viaversion.viaversion.api.type.types.version.Types1_18;
import com.viaversion.viaversion.protocols.protocol1_17_1to1_17.ClientboundPackets1_17_1;
import com.viaversion.viaversion.protocols.protocol1_18to1_17_1.Protocol1_18To1_17_1;
import com.viaversion.viaversion.protocols.protocol1_18to1_17_1.storage.ChunkLightStorage;
import com.viaversion.viaversion.rewriter.EntityRewriter;

public final class EntityPackets extends EntityRewriter<ClientboundPackets1_17_1, Protocol1_18To1_17_1> {
   public EntityPackets(Protocol1_18To1_17_1 protocol) {
      super(protocol);
   }

   @Override
   public void registerPackets() {
      this.registerMetadataRewriter(ClientboundPackets1_17_1.ENTITY_METADATA, Types1_17.METADATA_LIST, Types1_18.METADATA_LIST);
      this.protocol.registerClientbound(ClientboundPackets1_17_1.JOIN_GAME, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.INT);
            this.map(Type.BOOLEAN);
            this.map(Type.BYTE);
            this.map(Type.BYTE);
            this.map(Type.STRING_ARRAY);
            this.map(Type.NAMED_COMPOUND_TAG);
            this.map(Type.NAMED_COMPOUND_TAG);
            this.map(Type.STRING);
            this.map(Type.LONG);
            this.map(Type.VAR_INT);
            this.handler(wrapper -> {
               int chunkRadius = wrapper.passthrough(Type.VAR_INT);
               wrapper.write(Type.VAR_INT, chunkRadius);
            });
            this.handler(EntityPackets.this.worldDataTrackerHandler(1));
            this.handler(EntityPackets.this.biomeSizeTracker());
         }
      });
      this.protocol.registerClientbound(ClientboundPackets1_17_1.RESPAWN, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.NAMED_COMPOUND_TAG);
            this.map(Type.STRING);
            this.handler(wrapper -> {
               String world = wrapper.get(Type.STRING, 0);
               EntityTracker tracker = EntityPackets.this.tracker(wrapper.user());
               if (!world.equals(tracker.currentWorld())) {
                  wrapper.user().get(ChunkLightStorage.class).clear();
               }
            });
            this.handler(EntityPackets.this.worldDataTrackerHandler(0));
         }
      });
   }

   @Override
   protected void registerRewrites() {
      this.filter().handler((event, meta) -> {
         meta.setMetaType(Types1_18.META_TYPES.byId(meta.metaType().typeId()));
         if (meta.metaType() == Types1_18.META_TYPES.particleType) {
            Particle particle = (Particle)meta.getValue();
            if (particle.getId() == 2) {
               particle.setId(3);
               particle.add(Type.VAR_INT, 7754);
            } else if (particle.getId() == 3) {
               particle.add(Type.VAR_INT, 7786);
            } else {
               this.rewriteParticle(particle);
            }
         }
      });
      this.registerMetaTypeHandler(Types1_18.META_TYPES.itemType, null, null, null);
   }

   @Override
   public EntityType typeFromId(int type) {
      return EntityTypes1_17.getTypeFromId(type);
   }
}
