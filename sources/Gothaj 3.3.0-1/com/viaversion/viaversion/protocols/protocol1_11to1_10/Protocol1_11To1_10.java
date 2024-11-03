package com.viaversion.viaversion.protocols.protocol1_11to1_10;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.data.entity.EntityTracker;
import com.viaversion.viaversion.api.minecraft.ClientWorld;
import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_11;
import com.viaversion.viaversion.api.protocol.AbstractProtocol;
import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.protocol.remapper.ValueTransformer;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.chunk.ChunkType1_9_3;
import com.viaversion.viaversion.api.type.types.version.Types1_9;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
import com.viaversion.viaversion.protocols.protocol1_11to1_10.data.PotionColorMapping;
import com.viaversion.viaversion.protocols.protocol1_11to1_10.metadata.MetadataRewriter1_11To1_10;
import com.viaversion.viaversion.protocols.protocol1_11to1_10.packets.InventoryPackets;
import com.viaversion.viaversion.protocols.protocol1_11to1_10.storage.EntityTracker1_11;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.ClientboundPackets1_9_3;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.ServerboundPackets1_9_3;
import com.viaversion.viaversion.rewriter.SoundRewriter;
import com.viaversion.viaversion.util.Pair;

public class Protocol1_11To1_10 extends AbstractProtocol<ClientboundPackets1_9_3, ClientboundPackets1_9_3, ServerboundPackets1_9_3, ServerboundPackets1_9_3> {
   private static final ValueTransformer<Float, Short> toOldByte = new ValueTransformer<Float, Short>(Type.UNSIGNED_BYTE) {
      public Short transform(PacketWrapper wrapper, Float inputValue) throws Exception {
         return (short)((int)(inputValue * 16.0F));
      }
   };
   private final MetadataRewriter1_11To1_10 entityRewriter = new MetadataRewriter1_11To1_10(this);
   private final InventoryPackets itemRewriter = new InventoryPackets(this);

   public Protocol1_11To1_10() {
      super(ClientboundPackets1_9_3.class, ClientboundPackets1_9_3.class, ServerboundPackets1_9_3.class, ServerboundPackets1_9_3.class);
   }

   @Override
   protected void registerPackets() {
      this.entityRewriter.register();
      this.itemRewriter.register();
      this.registerClientbound(ClientboundPackets1_9_3.SPAWN_ENTITY, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.VAR_INT);
            this.map(Type.UUID);
            this.map(Type.BYTE);
            this.handler(Protocol1_11To1_10.this.entityRewriter.objectTrackerHandler());
         }
      });
      this.registerClientbound(ClientboundPackets1_9_3.SPAWN_MOB, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.VAR_INT);
            this.map(Type.UUID);
            this.map(Type.UNSIGNED_BYTE, Type.VAR_INT);
            this.map(Type.DOUBLE);
            this.map(Type.DOUBLE);
            this.map(Type.DOUBLE);
            this.map(Type.BYTE);
            this.map(Type.BYTE);
            this.map(Type.BYTE);
            this.map(Type.SHORT);
            this.map(Type.SHORT);
            this.map(Type.SHORT);
            this.map(Types1_9.METADATA_LIST);
            this.handler(wrapper -> {
               int entityId = wrapper.get(Type.VAR_INT, 0);
               int type = wrapper.get(Type.VAR_INT, 1);
               EntityTypes1_11.EntityType entType = MetadataRewriter1_11To1_10.rewriteEntityType(type, wrapper.get(Types1_9.METADATA_LIST, 0));
               if (entType != null) {
                  wrapper.set(Type.VAR_INT, 1, entType.getId());
                  wrapper.user().<EntityTracker>getEntityTracker(Protocol1_11To1_10.class).addEntity(entityId, entType);
                  Protocol1_11To1_10.this.entityRewriter.handleMetadata(entityId, wrapper.get(Types1_9.METADATA_LIST, 0), wrapper.user());
               }
            });
         }
      });
      new SoundRewriter<>(this, this::getNewSoundId).registerSound(ClientboundPackets1_9_3.SOUND);
      this.registerClientbound(ClientboundPackets1_9_3.COLLECT_ITEM, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.VAR_INT);
            this.map(Type.VAR_INT);
            this.handler(wrapper -> wrapper.write(Type.VAR_INT, 1));
         }
      });
      this.entityRewriter.registerMetadataRewriter(ClientboundPackets1_9_3.ENTITY_METADATA, Types1_9.METADATA_LIST);
      this.registerClientbound(ClientboundPackets1_9_3.ENTITY_TELEPORT, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.VAR_INT);
            this.map(Type.DOUBLE);
            this.map(Type.DOUBLE);
            this.map(Type.DOUBLE);
            this.map(Type.BYTE);
            this.map(Type.BYTE);
            this.map(Type.BOOLEAN);
            this.handler(wrapper -> {
               int entityID = wrapper.get(Type.VAR_INT, 0);
               if (Via.getConfig().isHologramPatch()) {
                  EntityTracker1_11 tracker = wrapper.user().getEntityTracker(Protocol1_11To1_10.class);
                  if (tracker.isHologram(entityID)) {
                     Double newValue = wrapper.get(Type.DOUBLE, 1);
                     newValue = newValue - Via.getConfig().getHologramYOffset();
                     wrapper.set(Type.DOUBLE, 1, newValue);
                  }
               }
            });
         }
      });
      this.entityRewriter.registerRemoveEntities(ClientboundPackets1_9_3.DESTROY_ENTITIES);
      this.registerClientbound(ClientboundPackets1_9_3.TITLE, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.VAR_INT);
            this.handler(wrapper -> {
               int action = wrapper.get(Type.VAR_INT, 0);
               if (action >= 2) {
                  wrapper.set(Type.VAR_INT, 0, action + 1);
               }
            });
         }
      });
      this.registerClientbound(ClientboundPackets1_9_3.BLOCK_ACTION, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.POSITION1_8);
            this.map(Type.UNSIGNED_BYTE);
            this.map(Type.UNSIGNED_BYTE);
            this.map(Type.VAR_INT);
            this.handler(actionWrapper -> {
               if (Via.getConfig().isPistonAnimationPatch()) {
                  int id = actionWrapper.get(Type.VAR_INT, 0);
                  if (id == 33 || id == 29) {
                     actionWrapper.cancel();
                  }
               }
            });
         }
      });
      this.registerClientbound(ClientboundPackets1_9_3.BLOCK_ENTITY_DATA, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.POSITION1_8);
            this.map(Type.UNSIGNED_BYTE);
            this.map(Type.NAMED_COMPOUND_TAG);
            this.handler(wrapper -> {
               CompoundTag tag = wrapper.get(Type.NAMED_COMPOUND_TAG, 0);
               if (wrapper.get(Type.UNSIGNED_BYTE, 0) == 1) {
                  EntityIdRewriter.toClientSpawner(tag);
               }

               if (tag.contains("id")) {
                  tag.<StringTag>get("id").setValue(BlockEntityRewriter.toNewIdentifier((String)tag.<Tag>get("id").getValue()));
               }
            });
         }
      });
      this.registerClientbound(ClientboundPackets1_9_3.CHUNK_DATA, wrapper -> {
         ClientWorld clientWorld = wrapper.user().get(ClientWorld.class);
         Chunk chunk = wrapper.passthrough(ChunkType1_9_3.forEnvironment(clientWorld.getEnvironment()));
         if (chunk.getBlockEntities() != null) {
            for (CompoundTag tag : chunk.getBlockEntities()) {
               if (tag.contains("id")) {
                  String identifier = tag.<StringTag>get("id").getValue();
                  if (identifier.equals("MobSpawner")) {
                     EntityIdRewriter.toClientSpawner(tag);
                  }

                  tag.<StringTag>get("id").setValue(BlockEntityRewriter.toNewIdentifier(identifier));
               }
            }
         }
      });
      this.registerClientbound(ClientboundPackets1_9_3.JOIN_GAME, new PacketHandlers() {
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
         }
      });
      this.registerClientbound(ClientboundPackets1_9_3.RESPAWN, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.INT);
            this.handler(wrapper -> {
               ClientWorld clientWorld = wrapper.user().get(ClientWorld.class);
               int dimensionId = wrapper.get(Type.INT, 0);
               clientWorld.setEnvironment(dimensionId);
            });
         }
      });
      this.registerClientbound(ClientboundPackets1_9_3.EFFECT, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.INT);
            this.map(Type.POSITION1_8);
            this.map(Type.INT);
            this.map(Type.BOOLEAN);
            this.handler(packetWrapper -> {
               int effectID = packetWrapper.get(Type.INT, 0);
               if (effectID == 2002) {
                  int data = packetWrapper.get(Type.INT, 1);
                  boolean isInstant = false;
                  Pair<Integer, Boolean> newData = PotionColorMapping.getNewData(data);
                  if (newData == null) {
                     Via.getPlatform().getLogger().warning("Received unknown 1.11 -> 1.10.2 potion data (" + data + ")");
                     data = 0;
                  } else {
                     data = newData.key();
                     isInstant = newData.value();
                  }

                  if (isInstant) {
                     packetWrapper.set(Type.INT, 0, 2007);
                  }

                  packetWrapper.set(Type.INT, 1, data);
               }
            });
         }
      });
      this.registerServerbound(ServerboundPackets1_9_3.PLAYER_BLOCK_PLACEMENT, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.POSITION1_8);
            this.map(Type.VAR_INT);
            this.map(Type.VAR_INT);
            this.map(Type.FLOAT, Protocol1_11To1_10.toOldByte);
            this.map(Type.FLOAT, Protocol1_11To1_10.toOldByte);
            this.map(Type.FLOAT, Protocol1_11To1_10.toOldByte);
         }
      });
      this.registerServerbound(ServerboundPackets1_9_3.CHAT_MESSAGE, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.STRING);
            this.handler(wrapper -> {
               String msg = wrapper.get(Type.STRING, 0);
               if (msg.length() > 100) {
                  wrapper.set(Type.STRING, 0, msg.substring(0, 100));
               }
            });
         }
      });
   }

   private int getNewSoundId(int id) {
      if (id == 196) {
         return -1;
      } else {
         if (id >= 85) {
            id += 2;
         }

         if (id >= 176) {
            id++;
         }

         if (id >= 197) {
            id += 8;
         }

         if (id >= 207) {
            id--;
         }

         if (id >= 279) {
            id += 9;
         }

         if (id >= 296) {
            id++;
         }

         if (id >= 390) {
            id += 4;
         }

         if (id >= 400) {
            id += 3;
         }

         if (id >= 450) {
            id++;
         }

         if (id >= 455) {
            id++;
         }

         if (id >= 470) {
            id++;
         }

         return id;
      }
   }

   @Override
   public void init(UserConnection userConnection) {
      userConnection.addEntityTracker((Class<? extends Protocol>)this.getClass(), new EntityTracker1_11(userConnection));
      if (!userConnection.has(ClientWorld.class)) {
         userConnection.put(new ClientWorld());
      }
   }

   public MetadataRewriter1_11To1_10 getEntityRewriter() {
      return this.entityRewriter;
   }

   public InventoryPackets getItemRewriter() {
      return this.itemRewriter;
   }
}
