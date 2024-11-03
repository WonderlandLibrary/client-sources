package com.viaversion.viaversion.protocols.protocol1_9to1_8.packets;

import com.google.common.collect.ImmutableList;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.data.entity.EntityTracker;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.protocol.remapper.ValueTransformer;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.version.Types1_8;
import com.viaversion.viaversion.api.type.types.version.Types1_9;
import com.viaversion.viaversion.protocols.protocol1_8.ClientboundPackets1_8;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.ClientboundPackets1_9;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.ItemRewriter;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.Protocol1_9To1_8;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.ServerboundPackets1_9;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.metadata.MetadataRewriter1_9To1_8;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.storage.EntityTracker1_9;
import com.viaversion.viaversion.util.Pair;
import com.viaversion.viaversion.util.Triple;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.Map.Entry;

public class EntityPackets {
   public static final ValueTransformer<Byte, Short> toNewShort = new ValueTransformer<Byte, Short>(Type.SHORT) {
      public Short transform(PacketWrapper wrapper, Byte inputValue) {
         return (short)(inputValue * 128);
      }
   };

   public static void register(final Protocol1_9To1_8 protocol) {
      protocol.registerClientbound(ClientboundPackets1_8.ATTACH_ENTITY, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.INT);
            this.map(Type.INT);
            this.handler(wrapper -> {
               short leashState = wrapper.read(Type.UNSIGNED_BYTE);
               EntityTracker1_9 tracker = wrapper.user().getEntityTracker(Protocol1_9To1_8.class);
               if (leashState == 0) {
                  int passenger = wrapper.get(Type.INT, 0);
                  int vehicle = wrapper.get(Type.INT, 1);
                  wrapper.cancel();
                  PacketWrapper passengerPacket = wrapper.create(ClientboundPackets1_9.SET_PASSENGERS);
                  if (vehicle == -1) {
                     if (!tracker.getVehicleMap().containsKey(passenger)) {
                        return;
                     }

                     passengerPacket.write(Type.VAR_INT, tracker.getVehicleMap().remove(passenger));
                     passengerPacket.write(Type.VAR_INT_ARRAY_PRIMITIVE, new int[0]);
                  } else {
                     passengerPacket.write(Type.VAR_INT, vehicle);
                     passengerPacket.write(Type.VAR_INT_ARRAY_PRIMITIVE, new int[]{passenger});
                     tracker.getVehicleMap().put(passenger, vehicle);
                  }

                  passengerPacket.send(Protocol1_9To1_8.class);
               }
            });
         }
      });
      protocol.registerClientbound(ClientboundPackets1_8.ENTITY_TELEPORT, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.VAR_INT);
            this.map(Type.INT, SpawnPackets.toNewDouble);
            this.map(Type.INT, SpawnPackets.toNewDouble);
            this.map(Type.INT, SpawnPackets.toNewDouble);
            this.map(Type.BYTE);
            this.map(Type.BYTE);
            this.map(Type.BOOLEAN);
            this.handler(wrapper -> {
               int entityID = wrapper.get(Type.VAR_INT, 0);
               if (Via.getConfig().isHologramPatch()) {
                  EntityTracker1_9 tracker = wrapper.user().getEntityTracker(Protocol1_9To1_8.class);
                  if (tracker.getKnownHolograms().contains(entityID)) {
                     Double newValue = wrapper.get(Type.DOUBLE, 1);
                     newValue = newValue + Via.getConfig().getHologramYOffset();
                     wrapper.set(Type.DOUBLE, 1, newValue);
                  }
               }
            });
         }
      });
      protocol.registerClientbound(ClientboundPackets1_8.ENTITY_POSITION_AND_ROTATION, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.VAR_INT);
            this.map(Type.BYTE, EntityPackets.toNewShort);
            this.map(Type.BYTE, EntityPackets.toNewShort);
            this.map(Type.BYTE, EntityPackets.toNewShort);
            this.map(Type.BYTE);
            this.map(Type.BYTE);
            this.map(Type.BOOLEAN);
         }
      });
      protocol.registerClientbound(ClientboundPackets1_8.ENTITY_POSITION, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.VAR_INT);
            this.map(Type.BYTE, EntityPackets.toNewShort);
            this.map(Type.BYTE, EntityPackets.toNewShort);
            this.map(Type.BYTE, EntityPackets.toNewShort);
            this.map(Type.BOOLEAN);
         }
      });
      protocol.registerClientbound(ClientboundPackets1_8.ENTITY_EQUIPMENT, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.VAR_INT);
            this.map(Type.SHORT, new ValueTransformer<Short, Integer>(Type.VAR_INT) {
               public Integer transform(PacketWrapper wrapper, Short slot) throws Exception {
                  int entityId = wrapper.get(Type.VAR_INT, 0);
                  int receiverId = wrapper.user().<EntityTracker>getEntityTracker(Protocol1_9To1_8.class).clientEntityId();
                  if (slot >= 0 && slot <= 4 && (entityId != receiverId || slot <= 3)) {
                     return entityId == receiverId ? slot.intValue() + 2 : slot > 0 ? slot.intValue() + 1 : slot.intValue();
                  } else {
                     wrapper.cancel();
                     return 0;
                  }
               }
            });
            this.map(Type.ITEM1_8);
            this.handler(wrapper -> {
               Item stack = wrapper.get(Type.ITEM1_8, 0);
               ItemRewriter.toClient(stack);
            });
            this.handler(wrapper -> {
               EntityTracker1_9 entityTracker = wrapper.user().getEntityTracker(Protocol1_9To1_8.class);
               int entityID = wrapper.get(Type.VAR_INT, 0);
               Item stack = wrapper.get(Type.ITEM1_8, 0);
               if (stack != null && Protocol1_9To1_8.isSword(stack.identifier())) {
                  entityTracker.getValidBlocking().add(entityID);
               } else {
                  entityTracker.getValidBlocking().remove(entityID);
               }
            });
         }
      });
      protocol.registerClientbound(ClientboundPackets1_8.ENTITY_METADATA, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.VAR_INT);
            this.map(Types1_8.METADATA_LIST, Types1_9.METADATA_LIST);
            this.handler(wrapper -> {
               List<Metadata> metadataList = wrapper.get(Types1_9.METADATA_LIST, 0);
               int entityId = wrapper.get(Type.VAR_INT, 0);
               EntityTracker1_9 tracker = wrapper.user().getEntityTracker(Protocol1_9To1_8.class);
               if (tracker.hasEntity(entityId)) {
                  protocol.get(MetadataRewriter1_9To1_8.class).handleMetadata(entityId, metadataList, wrapper.user());
               } else {
                  tracker.addMetadataToBuffer(entityId, metadataList);
                  wrapper.cancel();
               }
            });
            this.handler(wrapper -> {
               List<Metadata> metadataList = wrapper.get(Types1_9.METADATA_LIST, 0);
               int entityID = wrapper.get(Type.VAR_INT, 0);
               EntityTracker1_9 tracker = wrapper.user().getEntityTracker(Protocol1_9To1_8.class);
               tracker.handleMetadata(entityID, metadataList);
            });
            this.handler(wrapper -> {
               List<Metadata> metadataList = wrapper.get(Types1_9.METADATA_LIST, 0);
               if (metadataList.isEmpty()) {
                  wrapper.cancel();
               }
            });
         }
      });
      protocol.registerClientbound(ClientboundPackets1_8.ENTITY_EFFECT, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.VAR_INT);
            this.map(Type.BYTE);
            this.map(Type.BYTE);
            this.map(Type.VAR_INT);
            this.handler(wrapper -> {
               boolean showParticles = wrapper.read(Type.BOOLEAN);
               boolean newEffect = Via.getConfig().isNewEffectIndicator();
               wrapper.write(Type.BYTE, (byte)(showParticles ? (newEffect ? 2 : 1) : 0));
            });
         }
      });
      protocol.cancelClientbound(ClientboundPackets1_8.UPDATE_ENTITY_NBT);
      protocol.registerClientbound(ClientboundPackets1_8.COMBAT_EVENT, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.VAR_INT);
            this.handler(wrapper -> {
               if (wrapper.get(Type.VAR_INT, 0) == 2) {
                  wrapper.passthrough(Type.VAR_INT);
                  wrapper.passthrough(Type.INT);
                  Protocol1_9To1_8.FIX_JSON.write(wrapper, wrapper.read(Type.STRING));
               }
            });
         }
      });
      protocol.registerClientbound(
         ClientboundPackets1_8.ENTITY_PROPERTIES,
         new PacketHandlers() {
            @Override
            public void register() {
               this.map(Type.VAR_INT);
               this.handler(
                  wrapper -> {
                     if (Via.getConfig().isMinimizeCooldown()) {
                        EntityTracker1_9 tracker = wrapper.user().getEntityTracker(Protocol1_9To1_8.class);
                        if (wrapper.get(Type.VAR_INT, 0) == tracker.getProvidedEntityId()) {
                           int propertiesToRead = wrapper.read(Type.INT);
                           Map<String, Pair<Double, List<Triple<UUID, Double, Byte>>>> properties = new HashMap<>(propertiesToRead);

                           for (int i = 0; i < propertiesToRead; i++) {
                              String key = wrapper.read(Type.STRING);
                              Double value = wrapper.read(Type.DOUBLE);
                              int modifiersToRead = wrapper.read(Type.VAR_INT);
                              List<Triple<UUID, Double, Byte>> modifiers = new ArrayList<>(modifiersToRead);

                              for (int j = 0; j < modifiersToRead; j++) {
                                 modifiers.add(new Triple<>(wrapper.read(Type.UUID), wrapper.read(Type.DOUBLE), wrapper.read(Type.BYTE)));
                              }

                              properties.put(key, new Pair<>(value, modifiers));
                           }

                           properties.put(
                              "generic.attackSpeed",
                              new Pair<>(
                                 15.9,
                                 ImmutableList.of(
                                    new Triple<>(UUID.fromString("FA233E1C-4180-4865-B01B-BCCE9785ACA3"), 0.0, (byte)0),
                                    new Triple<>(UUID.fromString("AF8B6E3F-3328-4C0A-AA36-5BA2BB9DBEF3"), 0.0, (byte)2),
                                    new Triple<>(UUID.fromString("55FCED67-E92A-486E-9800-B47F202C4386"), 0.0, (byte)2)
                                 )
                              )
                           );
                           wrapper.write(Type.INT, properties.size());

                           for (Entry<String, Pair<Double, List<Triple<UUID, Double, Byte>>>> entry : properties.entrySet()) {
                              wrapper.write(Type.STRING, entry.getKey());
                              wrapper.write(Type.DOUBLE, entry.getValue().key());
                              wrapper.write(Type.VAR_INT, entry.getValue().value().size());

                              for (Triple<UUID, Double, Byte> modifier : entry.getValue().value()) {
                                 wrapper.write(Type.UUID, modifier.first());
                                 wrapper.write(Type.DOUBLE, modifier.second());
                                 wrapper.write(Type.BYTE, modifier.third());
                              }
                           }
                        }
                     }
                  }
               );
            }
         }
      );
      protocol.registerClientbound(ClientboundPackets1_8.ENTITY_ANIMATION, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.VAR_INT);
            this.map(Type.UNSIGNED_BYTE);
            this.handler(wrapper -> {
               if (wrapper.get(Type.UNSIGNED_BYTE, 0) == 3) {
                  wrapper.cancel();
               }
            });
         }
      });
      protocol.registerServerbound(ServerboundPackets1_9.ENTITY_ACTION, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.VAR_INT);
            this.map(Type.VAR_INT);
            this.map(Type.VAR_INT);
            this.handler(wrapper -> {
               int action = wrapper.get(Type.VAR_INT, 1);
               if (action == 6 || action == 8) {
                  wrapper.cancel();
               }

               if (action == 7) {
                  wrapper.set(Type.VAR_INT, 1, 6);
               }
            });
         }
      });
      protocol.registerServerbound(ServerboundPackets1_9.INTERACT_ENTITY, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.VAR_INT);
            this.map(Type.VAR_INT);
            this.handler(wrapper -> {
               int type = wrapper.get(Type.VAR_INT, 1);
               if (type == 2) {
                  wrapper.passthrough(Type.FLOAT);
                  wrapper.passthrough(Type.FLOAT);
                  wrapper.passthrough(Type.FLOAT);
               }

               if (type == 0 || type == 2) {
                  int hand = wrapper.read(Type.VAR_INT);
                  if (hand == 1) {
                     wrapper.cancel();
                  }
               }
            });
         }
      });
   }
}
