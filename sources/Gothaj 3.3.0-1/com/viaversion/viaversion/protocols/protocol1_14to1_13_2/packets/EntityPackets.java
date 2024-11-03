package com.viaversion.viaversion.protocols.protocol1_14to1_13_2.packets;

import com.viaversion.viaversion.api.data.entity.EntityTracker;
import com.viaversion.viaversion.api.minecraft.ClientWorld;
import com.viaversion.viaversion.api.minecraft.Position;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_13;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_14;
import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.version.Types1_13_2;
import com.viaversion.viaversion.api.type.types.version.Types1_14;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ClientboundPackets1_13;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.ClientboundPackets1_14;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.Protocol1_14To1_13_2;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.metadata.MetadataRewriter1_14To1_13_2;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.storage.EntityTracker1_14;
import java.util.LinkedList;
import java.util.List;

public class EntityPackets {
   public static void register(final Protocol1_14To1_13_2 protocol) {
      final MetadataRewriter1_14To1_13_2 metadataRewriter = protocol.get(MetadataRewriter1_14To1_13_2.class);
      protocol.registerClientbound(ClientboundPackets1_13.SPAWN_ENTITY, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.VAR_INT);
            this.map(Type.UUID);
            this.map(Type.BYTE, Type.VAR_INT);
            this.map(Type.DOUBLE);
            this.map(Type.DOUBLE);
            this.map(Type.DOUBLE);
            this.map(Type.BYTE);
            this.map(Type.BYTE);
            this.map(Type.INT);
            this.map(Type.SHORT);
            this.map(Type.SHORT);
            this.map(Type.SHORT);
            this.handler(wrapper -> {
               int entityId = wrapper.get(Type.VAR_INT, 0);
               int typeId = wrapper.get(Type.VAR_INT, 1);
               EntityTypes1_13.EntityType type1_13 = EntityTypes1_13.getTypeFromId(typeId, true);
               typeId = metadataRewriter.newEntityId(type1_13.getId());
               EntityType type1_14 = EntityTypes1_14.getTypeFromId(typeId);
               if (type1_14 != null) {
                  int data = wrapper.get(Type.INT, 0);
                  if (type1_14.is(EntityTypes1_14.FALLING_BLOCK)) {
                     wrapper.set(Type.INT, 0, protocol.getMappingData().getNewBlockStateId(data));
                  } else if (type1_14.is(EntityTypes1_14.MINECART)) {
                     switch (data) {
                        case 1:
                           typeId = EntityTypes1_14.CHEST_MINECART.getId();
                           break;
                        case 2:
                           typeId = EntityTypes1_14.FURNACE_MINECART.getId();
                           break;
                        case 3:
                           typeId = EntityTypes1_14.TNT_MINECART.getId();
                           break;
                        case 4:
                           typeId = EntityTypes1_14.SPAWNER_MINECART.getId();
                           break;
                        case 5:
                           typeId = EntityTypes1_14.HOPPER_MINECART.getId();
                           break;
                        case 6:
                           typeId = EntityTypes1_14.COMMAND_BLOCK_MINECART.getId();
                     }
                  } else if (type1_14.is(EntityTypes1_14.ITEM) && data > 0 || type1_14.isOrHasParent(EntityTypes1_14.ABSTRACT_ARROW)) {
                     if (type1_14.isOrHasParent(EntityTypes1_14.ABSTRACT_ARROW)) {
                        wrapper.set(Type.INT, 0, data - 1);
                     }

                     PacketWrapper velocity = wrapper.create(69);
                     velocity.write(Type.VAR_INT, entityId);
                     velocity.write(Type.SHORT, wrapper.get(Type.SHORT, 0));
                     velocity.write(Type.SHORT, wrapper.get(Type.SHORT, 1));
                     velocity.write(Type.SHORT, wrapper.get(Type.SHORT, 2));
                     velocity.scheduleSend(Protocol1_14To1_13_2.class);
                  }

                  wrapper.user().<EntityTracker>getEntityTracker(Protocol1_14To1_13_2.class).addEntity(entityId, type1_14);
               }

               wrapper.set(Type.VAR_INT, 1, typeId);
            });
         }
      });
      protocol.registerClientbound(ClientboundPackets1_13.SPAWN_MOB, new PacketHandlers() {
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
            this.map(Type.BYTE);
            this.map(Type.SHORT);
            this.map(Type.SHORT);
            this.map(Type.SHORT);
            this.map(Types1_13_2.METADATA_LIST, Types1_14.METADATA_LIST);
            this.handler(metadataRewriter.trackerAndRewriterHandler(Types1_14.METADATA_LIST));
         }
      });
      protocol.registerClientbound(ClientboundPackets1_13.SPAWN_PAINTING, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.VAR_INT);
            this.map(Type.UUID);
            this.map(Type.VAR_INT);
            this.map(Type.POSITION1_8, Type.POSITION1_14);
            this.map(Type.BYTE);
         }
      });
      protocol.registerClientbound(ClientboundPackets1_13.SPAWN_PLAYER, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.VAR_INT);
            this.map(Type.UUID);
            this.map(Type.DOUBLE);
            this.map(Type.DOUBLE);
            this.map(Type.DOUBLE);
            this.map(Type.BYTE);
            this.map(Type.BYTE);
            this.map(Types1_13_2.METADATA_LIST, Types1_14.METADATA_LIST);
            this.handler(metadataRewriter.trackerAndRewriterHandler(Types1_14.METADATA_LIST, EntityTypes1_14.PLAYER));
         }
      });
      protocol.registerClientbound(ClientboundPackets1_13.ENTITY_ANIMATION, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.VAR_INT);
            this.handler(wrapper -> {
               short animation = wrapper.passthrough(Type.UNSIGNED_BYTE);
               if (animation == 2) {
                  EntityTracker1_14 tracker = wrapper.user().getEntityTracker(Protocol1_14To1_13_2.class);
                  int entityId = wrapper.get(Type.VAR_INT, 0);
                  tracker.setSleeping(entityId, false);
                  PacketWrapper metadataPacket = wrapper.create(ClientboundPackets1_14.ENTITY_METADATA);
                  metadataPacket.write(Type.VAR_INT, entityId);
                  List<Metadata> metadataList = new LinkedList<>();
                  if (tracker.clientEntityId() != entityId) {
                     metadataList.add(new Metadata(6, Types1_14.META_TYPES.poseType, MetadataRewriter1_14To1_13_2.recalculatePlayerPose(entityId, tracker)));
                  }

                  metadataList.add(new Metadata(12, Types1_14.META_TYPES.optionalPositionType, null));
                  metadataPacket.write(Types1_14.METADATA_LIST, metadataList);
                  metadataPacket.scheduleSend(Protocol1_14To1_13_2.class);
               }
            });
         }
      });
      protocol.registerClientbound(ClientboundPackets1_13.JOIN_GAME, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.INT);
            this.map(Type.UNSIGNED_BYTE);
            this.map(Type.INT);
            this.handler(wrapper -> {
               ClientWorld clientChunks = wrapper.user().get(ClientWorld.class);
               int dimensionId = wrapper.get(Type.INT, 1);
               clientChunks.setEnvironment(dimensionId);
            });
            this.handler(metadataRewriter.playerTrackerHandler());
            this.handler(wrapper -> {
               short difficulty = wrapper.read(Type.UNSIGNED_BYTE);
               PacketWrapper difficultyPacket = wrapper.create(ClientboundPackets1_14.SERVER_DIFFICULTY);
               difficultyPacket.write(Type.UNSIGNED_BYTE, difficulty);
               difficultyPacket.write(Type.BOOLEAN, false);
               difficultyPacket.scheduleSend((Class<? extends Protocol>)protocol.getClass());
               wrapper.passthrough(Type.UNSIGNED_BYTE);
               wrapper.passthrough(Type.STRING);
               wrapper.write(Type.VAR_INT, 64);
            });
            this.handler(wrapper -> {
               wrapper.send(Protocol1_14To1_13_2.class);
               wrapper.cancel();
               WorldPackets.sendViewDistancePacket(wrapper.user());
            });
         }
      });
      protocol.registerClientbound(ClientboundPackets1_13.USE_BED, ClientboundPackets1_14.ENTITY_METADATA, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.VAR_INT);
            this.handler(wrapper -> {
               EntityTracker1_14 tracker = wrapper.user().getEntityTracker(Protocol1_14To1_13_2.class);
               int entityId = wrapper.get(Type.VAR_INT, 0);
               tracker.setSleeping(entityId, true);
               Position position = wrapper.read(Type.POSITION1_8);
               List<Metadata> metadataList = new LinkedList<>();
               metadataList.add(new Metadata(12, Types1_14.META_TYPES.optionalPositionType, position));
               if (tracker.clientEntityId() != entityId) {
                  metadataList.add(new Metadata(6, Types1_14.META_TYPES.poseType, MetadataRewriter1_14To1_13_2.recalculatePlayerPose(entityId, tracker)));
               }

               wrapper.write(Types1_14.METADATA_LIST, metadataList);
            });
         }
      });
      metadataRewriter.registerRemoveEntities(ClientboundPackets1_13.DESTROY_ENTITIES);
      metadataRewriter.registerMetadataRewriter(ClientboundPackets1_13.ENTITY_METADATA, Types1_13_2.METADATA_LIST, Types1_14.METADATA_LIST);
   }
}
