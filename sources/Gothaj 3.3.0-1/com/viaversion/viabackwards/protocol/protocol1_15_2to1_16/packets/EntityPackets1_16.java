package com.viaversion.viabackwards.protocol.protocol1_15_2to1_16.packets;

import com.viaversion.viabackwards.api.rewriters.EntityRewriter;
import com.viaversion.viabackwards.protocol.protocol1_15_2to1_16.Protocol1_15_2To1_16;
import com.viaversion.viabackwards.protocol.protocol1_15_2to1_16.data.WorldNameTracker;
import com.viaversion.viabackwards.protocol.protocol1_15_2to1_16.storage.WolfDataMaskStorage;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.data.entity.EntityTracker;
import com.viaversion.viaversion.api.data.entity.StoredEntityData;
import com.viaversion.viaversion.api.minecraft.ClientWorld;
import com.viaversion.viaversion.api.minecraft.Particle;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_15;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_16;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.minecraft.metadata.MetaType;
import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.protocol.remapper.ValueTransformer;
import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.version.Types1_14;
import com.viaversion.viaversion.api.type.types.version.Types1_16;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.protocols.protocol1_15to1_14_4.ClientboundPackets1_15;
import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.ClientboundPackets1_16;
import com.viaversion.viaversion.util.Key;

public class EntityPackets1_16 extends EntityRewriter<ClientboundPackets1_16, Protocol1_15_2To1_16> {
   private final ValueTransformer<String, Integer> dimensionTransformer = new ValueTransformer<String, Integer>(Type.STRING, Type.INT) {
      public Integer transform(PacketWrapper wrapper, String input) {
         switch (input) {
            case "minecraft:the_nether":
               return -1;
            case "minecraft:overworld":
            default:
               return 0;
            case "minecraft:the_end":
               return 1;
         }
      }
   };

   public EntityPackets1_16(Protocol1_15_2To1_16 protocol) {
      super(protocol);
   }

   @Override
   protected void registerPackets() {
      this.protocol.registerClientbound(ClientboundPackets1_16.SPAWN_ENTITY, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.VAR_INT);
            this.map(Type.UUID);
            this.map(Type.VAR_INT);
            this.map(Type.DOUBLE);
            this.map(Type.DOUBLE);
            this.map(Type.DOUBLE);
            this.map(Type.BYTE);
            this.map(Type.BYTE);
            this.map(Type.INT);
            this.handler(wrapper -> {
               EntityType entityType = EntityPackets1_16.this.typeFromId(wrapper.get(Type.VAR_INT, 1));
               if (entityType == EntityTypes1_16.LIGHTNING_BOLT) {
                  wrapper.cancel();
                  PacketWrapper spawnLightningPacket = wrapper.create(ClientboundPackets1_15.SPAWN_GLOBAL_ENTITY);
                  spawnLightningPacket.write(Type.VAR_INT, wrapper.get(Type.VAR_INT, 0));
                  spawnLightningPacket.write(Type.BYTE, (byte)1);
                  spawnLightningPacket.write(Type.DOUBLE, wrapper.get(Type.DOUBLE, 0));
                  spawnLightningPacket.write(Type.DOUBLE, wrapper.get(Type.DOUBLE, 1));
                  spawnLightningPacket.write(Type.DOUBLE, wrapper.get(Type.DOUBLE, 2));
                  spawnLightningPacket.send(Protocol1_15_2To1_16.class);
               }
            });
            this.handler(EntityPackets1_16.this.getSpawnTrackerWithDataHandler(EntityTypes1_16.FALLING_BLOCK));
         }
      });
      this.registerSpawnTracker(ClientboundPackets1_16.SPAWN_MOB);
      this.protocol
         .registerClientbound(
            ClientboundPackets1_16.RESPAWN,
            new PacketHandlers() {
               @Override
               public void register() {
                  this.map(EntityPackets1_16.this.dimensionTransformer);
                  this.handler(
                     wrapper -> {
                        WorldNameTracker worldNameTracker = wrapper.user().get(WorldNameTracker.class);
                        String nextWorldName = wrapper.read(Type.STRING);
                        wrapper.passthrough(Type.LONG);
                        wrapper.passthrough(Type.UNSIGNED_BYTE);
                        wrapper.read(Type.BYTE);
                        ClientWorld clientWorld = wrapper.user().get(ClientWorld.class);
                        int dimension = wrapper.get(Type.INT, 0);
                        if (clientWorld.getEnvironment() != null
                           && dimension == clientWorld.getEnvironment().id()
                           && (
                              wrapper.user().isClientSide()
                                 || Via.getPlatform().isProxy()
                                 || wrapper.user().getProtocolInfo().getProtocolVersion() <= ProtocolVersion.v1_12_2.getVersion()
                                 || !nextWorldName.equals(worldNameTracker.getWorldName())
                           )) {
                           PacketWrapper packet = wrapper.create(ClientboundPackets1_15.RESPAWN);
                           packet.write(Type.INT, dimension == 0 ? -1 : 0);
                           packet.write(Type.LONG, 0L);
                           packet.write(Type.UNSIGNED_BYTE, (short)0);
                           packet.write(Type.STRING, "default");
                           packet.send(Protocol1_15_2To1_16.class);
                        }

                        clientWorld.setEnvironment(dimension);
                        wrapper.write(Type.STRING, "default");
                        wrapper.read(Type.BOOLEAN);
                        if (wrapper.read(Type.BOOLEAN)) {
                           wrapper.set(Type.STRING, 0, "flat");
                        }

                        wrapper.read(Type.BOOLEAN);
                        worldNameTracker.setWorldName(nextWorldName);
                     }
                  );
               }
            }
         );
      this.protocol.registerClientbound(ClientboundPackets1_16.JOIN_GAME, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.INT);
            this.map(Type.UNSIGNED_BYTE);
            this.read(Type.BYTE);
            this.read(Type.STRING_ARRAY);
            this.read(Type.NAMED_COMPOUND_TAG);
            this.map(EntityPackets1_16.this.dimensionTransformer);
            this.handler(wrapper -> {
               WorldNameTracker worldNameTracker = wrapper.user().get(WorldNameTracker.class);
               worldNameTracker.setWorldName(wrapper.read(Type.STRING));
            });
            this.map(Type.LONG);
            this.map(Type.UNSIGNED_BYTE);
            this.handler(wrapper -> {
               ClientWorld clientChunks = wrapper.user().get(ClientWorld.class);
               clientChunks.setEnvironment(wrapper.get(Type.INT, 1));
               EntityPackets1_16.this.<EntityTracker>tracker(wrapper.user()).addEntity(wrapper.get(Type.INT, 0), EntityTypes1_16.PLAYER);
               wrapper.write(Type.STRING, "default");
               wrapper.passthrough(Type.VAR_INT);
               wrapper.passthrough(Type.BOOLEAN);
               wrapper.passthrough(Type.BOOLEAN);
               wrapper.read(Type.BOOLEAN);
               if (wrapper.read(Type.BOOLEAN)) {
                  wrapper.set(Type.STRING, 0, "flat");
               }
            });
         }
      });
      this.registerTracker(ClientboundPackets1_16.SPAWN_EXPERIENCE_ORB, EntityTypes1_16.EXPERIENCE_ORB);
      this.registerTracker(ClientboundPackets1_16.SPAWN_PAINTING, EntityTypes1_16.PAINTING);
      this.registerTracker(ClientboundPackets1_16.SPAWN_PLAYER, EntityTypes1_16.PLAYER);
      this.registerRemoveEntities(ClientboundPackets1_16.DESTROY_ENTITIES);
      this.registerMetadataRewriter(ClientboundPackets1_16.ENTITY_METADATA, Types1_16.METADATA_LIST, Types1_14.METADATA_LIST);
      this.protocol.registerClientbound(ClientboundPackets1_16.ENTITY_PROPERTIES, wrapper -> {
         wrapper.passthrough(Type.VAR_INT);
         int size = wrapper.passthrough(Type.INT);

         for (int i = 0; i < size; i++) {
            String attributeIdentifier = wrapper.read(Type.STRING);
            String oldKey = this.protocol.getMappingData().getAttributeMappings().get(attributeIdentifier);
            wrapper.write(Type.STRING, oldKey != null ? oldKey : Key.stripMinecraftNamespace(attributeIdentifier));
            wrapper.passthrough(Type.DOUBLE);
            int modifierSize = wrapper.passthrough(Type.VAR_INT);

            for (int j = 0; j < modifierSize; j++) {
               wrapper.passthrough(Type.UUID);
               wrapper.passthrough(Type.DOUBLE);
               wrapper.passthrough(Type.BYTE);
            }
         }
      });
      this.protocol.registerClientbound(ClientboundPackets1_16.PLAYER_INFO, new PacketHandlers() {
         @Override
         public void register() {
            this.handler(packetWrapper -> {
               int action = packetWrapper.passthrough(Type.VAR_INT);
               int playerCount = packetWrapper.passthrough(Type.VAR_INT);

               for (int i = 0; i < playerCount; i++) {
                  packetWrapper.passthrough(Type.UUID);
                  if (action != 0) {
                     if (action == 1) {
                        packetWrapper.passthrough(Type.VAR_INT);
                     } else if (action == 2) {
                        packetWrapper.passthrough(Type.VAR_INT);
                     } else if (action == 3) {
                        EntityPackets1_16.this.protocol.getTranslatableRewriter().processText(packetWrapper.passthrough(Type.OPTIONAL_COMPONENT));
                     }
                  } else {
                     packetWrapper.passthrough(Type.STRING);
                     int properties = packetWrapper.passthrough(Type.VAR_INT);

                     for (int j = 0; j < properties; j++) {
                        packetWrapper.passthrough(Type.STRING);
                        packetWrapper.passthrough(Type.STRING);
                        packetWrapper.passthrough(Type.OPTIONAL_STRING);
                     }

                     packetWrapper.passthrough(Type.VAR_INT);
                     packetWrapper.passthrough(Type.VAR_INT);
                     EntityPackets1_16.this.protocol.getTranslatableRewriter().processText(packetWrapper.passthrough(Type.OPTIONAL_COMPONENT));
                  }
               }
            });
         }
      });
   }

   @Override
   protected void registerRewrites() {
      this.filter().handler((event, meta) -> {
         meta.setMetaType(Types1_14.META_TYPES.byId(meta.metaType().typeId()));
         MetaType type = meta.metaType();
         if (type == Types1_14.META_TYPES.itemType) {
            meta.setValue(this.protocol.getItemRewriter().handleItemToClient((Item)meta.getValue()));
         } else if (type == Types1_14.META_TYPES.blockStateType) {
            meta.setValue(this.protocol.getMappingData().getNewBlockStateId((Integer)meta.getValue()));
         } else if (type == Types1_14.META_TYPES.particleType) {
            this.rewriteParticle((Particle)meta.getValue());
         } else if (type == Types1_14.META_TYPES.optionalComponentType) {
            JsonElement text = meta.value();
            if (text != null) {
               this.protocol.getTranslatableRewriter().processText(text);
            }
         }
      });
      this.mapEntityType(EntityTypes1_16.ZOMBIFIED_PIGLIN, EntityTypes1_15.ZOMBIE_PIGMAN);
      this.mapTypes(EntityTypes1_16.values(), EntityTypes1_15.class);
      this.mapEntityTypeWithData(EntityTypes1_16.HOGLIN, EntityTypes1_16.COW).jsonName();
      this.mapEntityTypeWithData(EntityTypes1_16.ZOGLIN, EntityTypes1_16.COW).jsonName();
      this.mapEntityTypeWithData(EntityTypes1_16.PIGLIN, EntityTypes1_16.ZOMBIFIED_PIGLIN).jsonName();
      this.mapEntityTypeWithData(EntityTypes1_16.STRIDER, EntityTypes1_16.MAGMA_CUBE).jsonName();
      this.filter().type(EntityTypes1_16.ZOGLIN).cancel(16);
      this.filter().type(EntityTypes1_16.HOGLIN).cancel(15);
      this.filter().type(EntityTypes1_16.PIGLIN).cancel(16);
      this.filter().type(EntityTypes1_16.PIGLIN).cancel(17);
      this.filter().type(EntityTypes1_16.PIGLIN).cancel(18);
      this.filter().type(EntityTypes1_16.STRIDER).index(15).handler((event, meta) -> {
         boolean baby = meta.<Boolean>value();
         meta.setTypeAndValue(Types1_14.META_TYPES.varIntType, baby ? 1 : 3);
      });
      this.filter().type(EntityTypes1_16.STRIDER).cancel(16);
      this.filter().type(EntityTypes1_16.STRIDER).cancel(17);
      this.filter().type(EntityTypes1_16.STRIDER).cancel(18);
      this.filter().type(EntityTypes1_16.FISHING_BOBBER).cancel(8);
      this.filter().filterFamily(EntityTypes1_16.ABSTRACT_ARROW).cancel(8);
      this.filter().filterFamily(EntityTypes1_16.ABSTRACT_ARROW).handler((event, meta) -> {
         if (event.index() >= 8) {
            event.setIndex(event.index() + 1);
         }
      });
      this.filter().type(EntityTypes1_16.WOLF).index(16).handler((event, meta) -> {
         byte mask = meta.<Byte>value();
         StoredEntityData data = this.<EntityTracker>tracker(event.user()).entityData(event.entityId());
         data.put(new WolfDataMaskStorage(mask));
      });
      this.filter().type(EntityTypes1_16.WOLF).index(20).handler((event, meta) -> {
         StoredEntityData data = this.<EntityTracker>tracker(event.user()).entityDataIfPresent(event.entityId());
         byte previousMask = 0;
         if (data != null) {
            WolfDataMaskStorage wolfData = data.get(WolfDataMaskStorage.class);
            if (wolfData != null) {
               previousMask = wolfData.tameableMask();
            }
         }

         int angerTime = meta.<Integer>value();
         byte tameableMask = (byte)(angerTime > 0 ? previousMask | 2 : previousMask & -3);
         event.createExtraMeta(new Metadata(16, Types1_14.META_TYPES.byteType, tameableMask));
         event.cancel();
      });
   }

   @Override
   public EntityType typeFromId(int typeId) {
      return EntityTypes1_16.getTypeFromId(typeId);
   }
}
