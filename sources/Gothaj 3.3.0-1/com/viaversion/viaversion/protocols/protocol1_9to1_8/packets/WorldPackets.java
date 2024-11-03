package com.viaversion.viaversion.protocols.protocol1_9to1_8.packets;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.minecraft.BlockFace;
import com.viaversion.viaversion.api.minecraft.ClientWorld;
import com.viaversion.viaversion.api.minecraft.Position;
import com.viaversion.viaversion.api.minecraft.chunks.BaseChunk;
import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
import com.viaversion.viaversion.api.minecraft.chunks.ChunkSection;
import com.viaversion.viaversion.api.minecraft.item.DataItem;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.chunk.BulkChunkType1_8;
import com.viaversion.viaversion.api.type.types.chunk.ChunkType1_8;
import com.viaversion.viaversion.api.type.types.chunk.ChunkType1_9_1;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
import com.viaversion.viaversion.protocols.protocol1_8.ClientboundPackets1_8;
import com.viaversion.viaversion.protocols.protocol1_8.ServerboundPackets1_8;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.ClientboundPackets1_9;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.ItemRewriter;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.Protocol1_9To1_8;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.ServerboundPackets1_9;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.providers.CommandBlockProvider;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.sounds.Effect;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.sounds.SoundEffect;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.storage.ClientChunks;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.storage.EntityTracker1_9;
import java.util.ArrayList;
import java.util.Optional;

public class WorldPackets {
   public static void register(Protocol1_9To1_8 protocol) {
      protocol.registerClientbound(ClientboundPackets1_8.UPDATE_SIGN, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.POSITION1_8);
            this.map(Type.STRING, Protocol1_9To1_8.FIX_JSON);
            this.map(Type.STRING, Protocol1_9To1_8.FIX_JSON);
            this.map(Type.STRING, Protocol1_9To1_8.FIX_JSON);
            this.map(Type.STRING, Protocol1_9To1_8.FIX_JSON);
         }
      });
      protocol.registerClientbound(ClientboundPackets1_8.EFFECT, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.INT);
            this.map(Type.POSITION1_8);
            this.map(Type.INT);
            this.map(Type.BOOLEAN);
            this.handler(wrapper -> {
               int id = wrapper.get(Type.INT, 0);
               id = Effect.getNewId(id);
               wrapper.set(Type.INT, 0, id);
            });
            this.handler(wrapper -> {
               int id = wrapper.get(Type.INT, 0);
               if (id == 2002) {
                  int data = wrapper.get(Type.INT, 1);
                  int newData = ItemRewriter.getNewEffectID(data);
                  wrapper.set(Type.INT, 1, newData);
               }
            });
         }
      });
      protocol.registerClientbound(ClientboundPackets1_8.NAMED_SOUND, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.STRING);
            this.handler(wrapper -> {
               String name = wrapper.get(Type.STRING, 0);
               SoundEffect effect = SoundEffect.getByName(name);
               int catid = 0;
               String newname = name;
               if (effect != null) {
                  catid = effect.getCategory().getId();
                  newname = effect.getNewName();
               }

               wrapper.set(Type.STRING, 0, newname);
               wrapper.write(Type.VAR_INT, catid);
               if (effect != null && effect.isBreaksound()) {
                  EntityTracker1_9 tracker = wrapper.user().getEntityTracker(Protocol1_9To1_8.class);
                  int x = wrapper.passthrough(Type.INT);
                  int y = wrapper.passthrough(Type.INT);
                  int z = wrapper.passthrough(Type.INT);
                  if (tracker.interactedBlockRecently((int)Math.floor((double)x / 8.0), (int)Math.floor((double)y / 8.0), (int)Math.floor((double)z / 8.0))) {
                     wrapper.cancel();
                  }
               }
            });
         }
      });
      protocol.registerClientbound(ClientboundPackets1_8.CHUNK_DATA, wrapper -> {
         ClientWorld clientWorld = wrapper.user().get(ClientWorld.class);
         ClientChunks clientChunks = wrapper.user().get(ClientChunks.class);
         Chunk chunk = wrapper.read(ChunkType1_8.forEnvironment(clientWorld.getEnvironment()));
         long chunkHash = ClientChunks.toLong(chunk.getX(), chunk.getZ());
         if (chunk.isFullChunk() && chunk.getBitmask() == 0) {
            wrapper.setPacketType(ClientboundPackets1_9.UNLOAD_CHUNK);
            wrapper.write(Type.INT, chunk.getX());
            wrapper.write(Type.INT, chunk.getZ());
            CommandBlockProvider provider = Via.getManager().getProviders().get(CommandBlockProvider.class);
            provider.unloadChunk(wrapper.user(), chunk.getX(), chunk.getZ());
            clientChunks.getLoadedChunks().remove(chunkHash);
            if (Via.getConfig().isChunkBorderFix()) {
               for (BlockFace face : BlockFace.HORIZONTAL) {
                  int chunkX = chunk.getX() + face.modX();
                  int chunkZ = chunk.getZ() + face.modZ();
                  if (!clientChunks.getLoadedChunks().contains(ClientChunks.toLong(chunkX, chunkZ))) {
                     PacketWrapper unloadChunk = wrapper.create(ClientboundPackets1_9.UNLOAD_CHUNK);
                     unloadChunk.write(Type.INT, chunkX);
                     unloadChunk.write(Type.INT, chunkZ);
                     unloadChunk.send(Protocol1_9To1_8.class);
                  }
               }
            }
         } else {
            Type<Chunk> chunkType = ChunkType1_9_1.forEnvironment(clientWorld.getEnvironment());
            wrapper.write(chunkType, chunk);
            clientChunks.getLoadedChunks().add(chunkHash);
            if (Via.getConfig().isChunkBorderFix()) {
               for (BlockFace facex : BlockFace.HORIZONTAL) {
                  int chunkX = chunk.getX() + facex.modX();
                  int chunkZ = chunk.getZ() + facex.modZ();
                  if (!clientChunks.getLoadedChunks().contains(ClientChunks.toLong(chunkX, chunkZ))) {
                     PacketWrapper emptyChunk = wrapper.create(ClientboundPackets1_9.CHUNK_DATA);
                     Chunk c = new BaseChunk(chunkX, chunkZ, true, false, 0, new ChunkSection[16], new int[256], new ArrayList<>());
                     emptyChunk.write(chunkType, c);
                     emptyChunk.send(Protocol1_9To1_8.class);
                  }
               }
            }
         }
      });
      protocol.registerClientbound(ClientboundPackets1_8.MAP_BULK_CHUNK, null, wrapper -> {
         wrapper.cancel();
         ClientWorld clientWorld = wrapper.user().get(ClientWorld.class);
         ClientChunks clientChunks = wrapper.user().get(ClientChunks.class);
         Chunk[] chunks = wrapper.read(BulkChunkType1_8.TYPE);
         Type<Chunk> chunkType = ChunkType1_9_1.forEnvironment(clientWorld.getEnvironment());

         for (Chunk chunk : chunks) {
            PacketWrapper chunkData = wrapper.create(ClientboundPackets1_9.CHUNK_DATA);
            chunkData.write(chunkType, chunk);
            chunkData.send(Protocol1_9To1_8.class);
            clientChunks.getLoadedChunks().add(ClientChunks.toLong(chunk.getX(), chunk.getZ()));
            if (Via.getConfig().isChunkBorderFix()) {
               for (BlockFace face : BlockFace.HORIZONTAL) {
                  int chunkX = chunk.getX() + face.modX();
                  int chunkZ = chunk.getZ() + face.modZ();
                  if (!clientChunks.getLoadedChunks().contains(ClientChunks.toLong(chunkX, chunkZ))) {
                     PacketWrapper emptyChunk = wrapper.create(ClientboundPackets1_9.CHUNK_DATA);
                     Chunk c = new BaseChunk(chunkX, chunkZ, true, false, 0, new ChunkSection[16], new int[256], new ArrayList<>());
                     emptyChunk.write(chunkType, c);
                     emptyChunk.send(Protocol1_9To1_8.class);
                  }
               }
            }
         }
      });
      protocol.registerClientbound(ClientboundPackets1_8.BLOCK_ENTITY_DATA, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.POSITION1_8);
            this.map(Type.UNSIGNED_BYTE);
            this.map(Type.NAMED_COMPOUND_TAG);
            this.handler(wrapper -> {
               int action = wrapper.get(Type.UNSIGNED_BYTE, 0);
               if (action == 1) {
                  CompoundTag tag = wrapper.get(Type.NAMED_COMPOUND_TAG, 0);
                  if (tag != null) {
                     if (tag.contains("EntityId")) {
                        String entity = (String)tag.<Tag>get("EntityId").getValue();
                        CompoundTag spawn = new CompoundTag();
                        spawn.put("id", new StringTag(entity));
                        tag.put("SpawnData", spawn);
                     } else {
                        CompoundTag spawn = new CompoundTag();
                        spawn.put("id", new StringTag("AreaEffectCloud"));
                        tag.put("SpawnData", spawn);
                     }
                  }
               }

               if (action == 2) {
                  CommandBlockProvider provider = Via.getManager().getProviders().get(CommandBlockProvider.class);
                  provider.addOrUpdateBlock(wrapper.user(), wrapper.get(Type.POSITION1_8, 0), wrapper.get(Type.NAMED_COMPOUND_TAG, 0));
                  wrapper.cancel();
               }
            });
         }
      });
      protocol.registerServerbound(ServerboundPackets1_9.UPDATE_SIGN, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.POSITION1_8);
            this.map(Type.STRING, Protocol1_9To1_8.FIX_JSON);
            this.map(Type.STRING, Protocol1_9To1_8.FIX_JSON);
            this.map(Type.STRING, Protocol1_9To1_8.FIX_JSON);
            this.map(Type.STRING, Protocol1_9To1_8.FIX_JSON);
         }
      });
      protocol.registerServerbound(ServerboundPackets1_9.PLAYER_DIGGING, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.VAR_INT);
            this.map(Type.POSITION1_8);
            this.handler(wrapper -> {
               int status = wrapper.get(Type.VAR_INT, 0);
               if (status == 6) {
                  wrapper.cancel();
               }
            });
            this.handler(wrapper -> {
               int status = wrapper.get(Type.VAR_INT, 0);
               if (status == 5 || status == 4 || status == 3) {
                  EntityTracker1_9 entityTracker = wrapper.user().getEntityTracker(Protocol1_9To1_8.class);
                  if (entityTracker.isBlocking()) {
                     entityTracker.setBlocking(false);
                     if (!Via.getConfig().isShowShieldWhenSwordInHand()) {
                        entityTracker.setSecondHand(null);
                     }
                  }
               }
            });
         }
      });
      protocol.registerServerbound(ServerboundPackets1_9.USE_ITEM, null, wrapper -> {
         int hand = wrapper.read(Type.VAR_INT);
         wrapper.clearInputBuffer();
         wrapper.setPacketType(ServerboundPackets1_8.PLAYER_BLOCK_PLACEMENT);
         wrapper.write(Type.POSITION1_8, new Position(-1, (short)-1, -1));
         wrapper.write(Type.UNSIGNED_BYTE, (short)255);
         Item item = Protocol1_9To1_8.getHandItem(wrapper.user());
         if (Via.getConfig().isShieldBlocking()) {
            EntityTracker1_9 tracker = wrapper.user().getEntityTracker(Protocol1_9To1_8.class);
            boolean showShieldWhenSwordInHand = Via.getConfig().isShowShieldWhenSwordInHand();
            boolean isSword = showShieldWhenSwordInHand ? tracker.hasSwordInHand() : item != null && Protocol1_9To1_8.isSword(item.identifier());
            if (isSword) {
               if (hand == 0 && !tracker.isBlocking()) {
                  tracker.setBlocking(true);
                  if (!showShieldWhenSwordInHand && tracker.getItemInSecondHand() == null) {
                     Item shield = new DataItem(442, (byte)1, (short)0, null);
                     tracker.setSecondHand(shield);
                  }
               }

               boolean blockUsingMainHand = Via.getConfig().isNoDelayShieldBlocking() && !showShieldWhenSwordInHand;
               if (blockUsingMainHand && hand == 1 || !blockUsingMainHand && hand == 0) {
                  wrapper.cancel();
               }
            } else {
               if (!showShieldWhenSwordInHand) {
                  tracker.setSecondHand(null);
               }

               tracker.setBlocking(false);
            }
         }

         wrapper.write(Type.ITEM1_8, item);
         wrapper.write(Type.UNSIGNED_BYTE, (short)0);
         wrapper.write(Type.UNSIGNED_BYTE, (short)0);
         wrapper.write(Type.UNSIGNED_BYTE, (short)0);
      });
      protocol.registerServerbound(ServerboundPackets1_9.PLAYER_BLOCK_PLACEMENT, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.POSITION1_8);
            this.map(Type.VAR_INT, Type.UNSIGNED_BYTE);
            this.handler(wrapper -> {
               int hand = wrapper.read(Type.VAR_INT);
               if (hand != 0) {
                  wrapper.cancel();
               }
            });
            this.handler(wrapper -> {
               Item item = Protocol1_9To1_8.getHandItem(wrapper.user());
               wrapper.write(Type.ITEM1_8, item);
            });
            this.map(Type.UNSIGNED_BYTE);
            this.map(Type.UNSIGNED_BYTE);
            this.map(Type.UNSIGNED_BYTE);
            this.handler(wrapper -> {
               int face = wrapper.get(Type.UNSIGNED_BYTE, 0);
               if (face != 255) {
                  Position p = wrapper.get(Type.POSITION1_8, 0);
                  int x = p.x();
                  int y = p.y();
                  int z = p.z();
                  switch (face) {
                     case 0:
                        y--;
                        break;
                     case 1:
                        y++;
                        break;
                     case 2:
                        z--;
                        break;
                     case 3:
                        z++;
                        break;
                     case 4:
                        x--;
                        break;
                     case 5:
                        x++;
                  }

                  EntityTracker1_9 tracker = wrapper.user().getEntityTracker(Protocol1_9To1_8.class);
                  tracker.addBlockInteraction(new Position(x, y, z));
               }
            });
            this.handler(wrapper -> {
               CommandBlockProvider provider = Via.getManager().getProviders().get(CommandBlockProvider.class);
               Position pos = wrapper.get(Type.POSITION1_8, 0);
               Optional<CompoundTag> tag = provider.get(wrapper.user(), pos);
               if (tag.isPresent()) {
                  PacketWrapper updateBlockEntity = PacketWrapper.create(ClientboundPackets1_9.BLOCK_ENTITY_DATA, null, wrapper.user());
                  updateBlockEntity.write(Type.POSITION1_8, pos);
                  updateBlockEntity.write(Type.UNSIGNED_BYTE, (short)2);
                  updateBlockEntity.write(Type.NAMED_COMPOUND_TAG, tag.get());
                  updateBlockEntity.scheduleSend(Protocol1_9To1_8.class);
               }
            });
         }
      });
   }
}
