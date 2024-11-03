package com.viaversion.viabackwards.protocol.protocol1_20_2to1_20_3.rewriter;

import com.viaversion.viabackwards.api.rewriters.EntityRewriter;
import com.viaversion.viabackwards.protocol.protocol1_20_2to1_20_3.Protocol1_20_2To1_20_3;
import com.viaversion.viabackwards.protocol.protocol1_20_2to1_20_3.storage.SpawnPositionStorage;
import com.viaversion.viaversion.api.data.ParticleMappings;
import com.viaversion.viaversion.api.minecraft.Particle;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_20_3;
import com.viaversion.viaversion.api.minecraft.metadata.MetaType;
import com.viaversion.viaversion.api.protocol.packet.State;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.version.Types1_20_2;
import com.viaversion.viaversion.api.type.types.version.Types1_20_3;
import com.viaversion.viaversion.protocols.protocol1_20_2to1_20.packet.ClientboundConfigurationPackets1_20_2;
import com.viaversion.viaversion.protocols.protocol1_20_3to1_20_2.packet.ClientboundPackets1_20_3;
import com.viaversion.viaversion.util.ComponentUtil;

public final class EntityPacketRewriter1_20_3 extends EntityRewriter<ClientboundPackets1_20_3, Protocol1_20_2To1_20_3> {
   public EntityPacketRewriter1_20_3(Protocol1_20_2To1_20_3 protocol) {
      super(protocol, Types1_20_2.META_TYPES.optionalComponentType, Types1_20_2.META_TYPES.booleanType);
   }

   @Override
   public void registerPackets() {
      this.registerSpawnTracker(ClientboundPackets1_20_3.SPAWN_ENTITY);
      this.registerMetadataRewriter(ClientboundPackets1_20_3.ENTITY_METADATA, Types1_20_3.METADATA_LIST, Types1_20_2.METADATA_LIST);
      this.registerRemoveEntities(ClientboundPackets1_20_3.REMOVE_ENTITIES);
      this.protocol.registerClientbound(State.CONFIGURATION, ClientboundConfigurationPackets1_20_2.REGISTRY_DATA, new PacketHandlers() {
         @Override
         protected void register() {
            this.map(Type.COMPOUND_TAG);
            this.handler(EntityPacketRewriter1_20_3.this.configurationDimensionDataHandler());
            this.handler(EntityPacketRewriter1_20_3.this.configurationBiomeSizeTracker());
         }
      });
      this.protocol.registerClientbound(ClientboundPackets1_20_3.JOIN_GAME, new PacketHandlers() {
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
            this.handler(EntityPacketRewriter1_20_3.this.spawnPositionHandler());
            this.handler(EntityPacketRewriter1_20_3.this.worldDataTrackerHandlerByKey());
         }
      });
      this.protocol.registerClientbound(ClientboundPackets1_20_3.RESPAWN, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.STRING);
            this.map(Type.STRING);
            this.handler(EntityPacketRewriter1_20_3.this.spawnPositionHandler());
            this.handler(EntityPacketRewriter1_20_3.this.worldDataTrackerHandlerByKey());
         }
      });
   }

   private PacketHandler spawnPositionHandler() {
      return wrapper -> {
         String world = wrapper.get(Type.STRING, 1);
         wrapper.user().get(SpawnPositionStorage.class).setDimension(world);
      };
   }

   @Override
   protected void registerRewrites() {
      this.filter().handler((event, meta) -> {
         MetaType type = meta.metaType();
         if (type == Types1_20_3.META_TYPES.componentType) {
            meta.setTypeAndValue(Types1_20_2.META_TYPES.componentType, ComponentUtil.tagToJson(meta.value()));
         } else if (type == Types1_20_3.META_TYPES.optionalComponentType) {
            meta.setTypeAndValue(Types1_20_2.META_TYPES.optionalComponentType, ComponentUtil.tagToJson(meta.value()));
         } else {
            if (type == Types1_20_3.META_TYPES.particleType) {
               Particle particle = (Particle)meta.getValue();
               ParticleMappings particleMappings = this.protocol.getMappingData().getParticleMappings();
               if (particle.getId() == particleMappings.id("vibration")) {
                  int positionSourceType = particle.<Integer>removeArgument(0).getValue();
                  if (positionSourceType == 0) {
                     particle.add(0, Type.STRING, "minecraft:block");
                  } else {
                     particle.add(0, Type.STRING, "minecraft:entity");
                  }
               }

               this.rewriteParticle(particle);
            } else if (type == Types1_20_3.META_TYPES.poseType) {
               int pose = meta.<Integer>value();
               if (pose >= 15) {
                  event.cancel();
               }
            }

            meta.setMetaType(Types1_20_2.META_TYPES.byId(type.typeId()));
         }
      });
      this.registerMetaTypeHandler(
         Types1_20_2.META_TYPES.itemType,
         Types1_20_2.META_TYPES.blockStateType,
         Types1_20_2.META_TYPES.optionalBlockStateType,
         Types1_20_2.META_TYPES.particleType,
         Types1_20_2.META_TYPES.componentType,
         Types1_20_2.META_TYPES.optionalComponentType
      );
      this.filter().filterFamily(EntityTypes1_20_3.MINECART_ABSTRACT).index(11).handler((event, meta) -> {
         int blockState = meta.<Integer>value();
         meta.setValue(this.protocol.getMappingData().getNewBlockStateId(blockState));
      });
      this.filter().type(EntityTypes1_20_3.TNT).removeIndex(9);
   }

   @Override
   public void onMappingDataLoaded() {
      this.mapTypes();
      this.mapEntityTypeWithData(EntityTypes1_20_3.BREEZE, EntityTypes1_20_3.BLAZE).jsonName();
      this.mapEntityTypeWithData(EntityTypes1_20_3.WIND_CHARGE, EntityTypes1_20_3.LLAMA_SPIT).jsonName();
   }

   @Override
   public EntityType typeFromId(int type) {
      return EntityTypes1_20_3.getTypeFromId(type);
   }
}
