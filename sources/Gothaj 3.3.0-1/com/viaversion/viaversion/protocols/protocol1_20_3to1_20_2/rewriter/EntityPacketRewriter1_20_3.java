package com.viaversion.viaversion.protocols.protocol1_20_3to1_20_2.rewriter;

import com.viaversion.viaversion.api.data.ParticleMappings;
import com.viaversion.viaversion.api.minecraft.Particle;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_20_3;
import com.viaversion.viaversion.api.minecraft.metadata.MetaType;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.packet.State;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.version.Types1_20_2;
import com.viaversion.viaversion.api.type.types.version.Types1_20_3;
import com.viaversion.viaversion.protocols.protocol1_20_2to1_20.packet.ClientboundConfigurationPackets1_20_2;
import com.viaversion.viaversion.protocols.protocol1_20_2to1_20.packet.ClientboundPackets1_20_2;
import com.viaversion.viaversion.protocols.protocol1_20_3to1_20_2.Protocol1_20_3To1_20_2;
import com.viaversion.viaversion.protocols.protocol1_20_3to1_20_2.packet.ClientboundPackets1_20_3;
import com.viaversion.viaversion.rewriter.EntityRewriter;
import com.viaversion.viaversion.util.ComponentUtil;
import com.viaversion.viaversion.util.Key;

public final class EntityPacketRewriter1_20_3 extends EntityRewriter<ClientboundPackets1_20_2, Protocol1_20_3To1_20_2> {
   public EntityPacketRewriter1_20_3(Protocol1_20_3To1_20_2 protocol) {
      super(protocol);
   }

   @Override
   public void registerPackets() {
      this.registerTrackerWithData1_19(ClientboundPackets1_20_2.SPAWN_ENTITY, EntityTypes1_20_3.FALLING_BLOCK);
      this.registerMetadataRewriter(ClientboundPackets1_20_2.ENTITY_METADATA, Types1_20_2.METADATA_LIST, Types1_20_3.METADATA_LIST);
      this.registerRemoveEntities(ClientboundPackets1_20_2.REMOVE_ENTITIES);
      this.protocol.registerClientbound(State.CONFIGURATION, ClientboundConfigurationPackets1_20_2.REGISTRY_DATA, new PacketHandlers() {
         @Override
         protected void register() {
            this.map(Type.COMPOUND_TAG);
            this.handler(EntityPacketRewriter1_20_3.this.configurationDimensionDataHandler());
            this.handler(EntityPacketRewriter1_20_3.this.configurationBiomeSizeTracker());
         }
      });
      this.protocol.registerClientbound(ClientboundPackets1_20_2.JOIN_GAME, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.INT);
            this.map(Type.BOOLEAN);
            this.map(Type.STRING_ARRAY);
            this.map(Type.VAR_INT);
            this.map(Type.VAR_INT);
            this.map(Type.VAR_INT);
            this.map(Type.BOOLEAN);
            this.map(Type.BOOLEAN);
            this.map(Type.BOOLEAN);
            this.map(Type.STRING);
            this.map(Type.STRING);
            this.handler(EntityPacketRewriter1_20_3.this.worldDataTrackerHandlerByKey());
            this.handler(wrapper -> EntityPacketRewriter1_20_3.this.sendChunksSentGameEvent(wrapper));
         }
      });
      this.protocol.registerClientbound(ClientboundPackets1_20_2.RESPAWN, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.STRING);
            this.map(Type.STRING);
            this.handler(EntityPacketRewriter1_20_3.this.worldDataTrackerHandlerByKey());
            this.handler(wrapper -> EntityPacketRewriter1_20_3.this.sendChunksSentGameEvent(wrapper));
         }
      });
   }

   private void sendChunksSentGameEvent(PacketWrapper wrapper) throws Exception {
      wrapper.send(Protocol1_20_3To1_20_2.class);
      wrapper.cancel();
      PacketWrapper gameEventPacket = wrapper.create(ClientboundPackets1_20_3.GAME_EVENT);
      gameEventPacket.write(Type.UNSIGNED_BYTE, (short)13);
      gameEventPacket.write(Type.FLOAT, 0.0F);
      gameEventPacket.send(Protocol1_20_3To1_20_2.class);
   }

   @Override
   protected void registerRewrites() {
      this.filter().handler((event, meta) -> {
         MetaType type = meta.metaType();
         if (type == Types1_20_2.META_TYPES.componentType) {
            meta.setTypeAndValue(Types1_20_3.META_TYPES.componentType, ComponentUtil.jsonToTag(meta.value()));
         } else if (type == Types1_20_2.META_TYPES.optionalComponentType) {
            meta.setTypeAndValue(Types1_20_3.META_TYPES.optionalComponentType, ComponentUtil.jsonToTag(meta.value()));
         } else if (type == Types1_20_2.META_TYPES.particleType) {
            Particle particle = (Particle)meta.getValue();
            ParticleMappings particleMappings = this.protocol.getMappingData().getParticleMappings();
            if (particle.getId() == particleMappings.id("vibration")) {
               String resourceLocation = particle.<String>removeArgument(0).getValue();
               if (Key.stripMinecraftNamespace(resourceLocation).equals("block")) {
                  particle.add(0, Type.VAR_INT, 0);
               } else {
                  particle.add(0, Type.VAR_INT, 1);
               }
            }

            this.rewriteParticle(particle);
            meta.setMetaType(Types1_20_3.META_TYPES.particleType);
         } else {
            meta.setMetaType(Types1_20_3.META_TYPES.byId(type.typeId()));
         }
      });
      this.registerMetaTypeHandler(
         Types1_20_3.META_TYPES.itemType,
         Types1_20_3.META_TYPES.blockStateType,
         Types1_20_3.META_TYPES.optionalBlockStateType,
         Types1_20_3.META_TYPES.particleType
      );
      this.filter().filterFamily(EntityTypes1_20_3.MINECART_ABSTRACT).index(11).handler((event, meta) -> {
         int blockState = meta.<Integer>value();
         meta.setValue(this.protocol.getMappingData().getNewBlockStateId(blockState));
      });
   }

   @Override
   public void onMappingDataLoaded() {
      this.mapTypes();
   }

   @Override
   public EntityType typeFromId(int type) {
      return EntityTypes1_20_3.getTypeFromId(type);
   }
}
