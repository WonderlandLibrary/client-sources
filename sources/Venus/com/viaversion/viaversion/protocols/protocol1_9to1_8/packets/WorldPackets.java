/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.protocols.protocol1_9to1_8.packets;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.minecraft.BlockFace;
import com.viaversion.viaversion.api.minecraft.Position;
import com.viaversion.viaversion.api.minecraft.chunks.BaseChunk;
import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
import com.viaversion.viaversion.api.minecraft.chunks.ChunkSection;
import com.viaversion.viaversion.api.minecraft.item.DataItem;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
import com.viaversion.viaversion.protocols.protocol1_8.ClientboundPackets1_8;
import com.viaversion.viaversion.protocols.protocol1_8.ServerboundPackets1_8;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.storage.ClientWorld;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.types.Chunk1_9_1_2Type;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.ClientboundPackets1_9;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.ItemRewriter;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.Protocol1_9To1_8;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.ServerboundPackets1_9;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.providers.CommandBlockProvider;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.sounds.Effect;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.sounds.SoundEffect;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.storage.ClientChunks;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.storage.EntityTracker1_9;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.types.Chunk1_8Type;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.types.ChunkBulk1_8Type;
import java.util.ArrayList;
import java.util.Optional;

public class WorldPackets {
    public static void register(Protocol1_9To1_8 protocol1_9To1_8) {
        protocol1_9To1_8.registerClientbound(ClientboundPackets1_8.UPDATE_SIGN, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Type.POSITION);
                this.map(Type.STRING, Protocol1_9To1_8.FIX_JSON);
                this.map(Type.STRING, Protocol1_9To1_8.FIX_JSON);
                this.map(Type.STRING, Protocol1_9To1_8.FIX_JSON);
                this.map(Type.STRING, Protocol1_9To1_8.FIX_JSON);
            }
        });
        protocol1_9To1_8.registerClientbound(ClientboundPackets1_8.EFFECT, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Type.INT);
                this.map(Type.POSITION);
                this.map(Type.INT);
                this.map(Type.BOOLEAN);
                this.handler(2::lambda$register$0);
                this.handler(2::lambda$register$1);
            }

            private static void lambda$register$1(PacketWrapper packetWrapper) throws Exception {
                int n = packetWrapper.get(Type.INT, 0);
                if (n == 2002) {
                    int n2 = packetWrapper.get(Type.INT, 1);
                    int n3 = ItemRewriter.getNewEffectID(n2);
                    packetWrapper.set(Type.INT, 1, n3);
                }
            }

            private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                int n = packetWrapper.get(Type.INT, 0);
                n = Effect.getNewId(n);
                packetWrapper.set(Type.INT, 0, n);
            }
        });
        protocol1_9To1_8.registerClientbound(ClientboundPackets1_8.NAMED_SOUND, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Type.STRING);
                this.handler(3::lambda$register$0);
            }

            private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                String string = packetWrapper.get(Type.STRING, 0);
                SoundEffect soundEffect = SoundEffect.getByName(string);
                int n = 0;
                String string2 = string;
                if (soundEffect != null) {
                    n = soundEffect.getCategory().getId();
                    string2 = soundEffect.getNewName();
                }
                packetWrapper.set(Type.STRING, 0, string2);
                packetWrapper.write(Type.VAR_INT, n);
                if (soundEffect != null && soundEffect.isBreaksound()) {
                    EntityTracker1_9 entityTracker1_9 = (EntityTracker1_9)packetWrapper.user().getEntityTracker(Protocol1_9To1_8.class);
                    int n2 = packetWrapper.passthrough(Type.INT);
                    int n3 = packetWrapper.passthrough(Type.INT);
                    int n4 = packetWrapper.passthrough(Type.INT);
                    if (entityTracker1_9.interactedBlockRecently((int)Math.floor((double)n2 / 8.0), (int)Math.floor((double)n3 / 8.0), (int)Math.floor((double)n4 / 8.0))) {
                        packetWrapper.cancel();
                    }
                }
            }
        });
        protocol1_9To1_8.registerClientbound(ClientboundPackets1_8.CHUNK_DATA, WorldPackets::lambda$register$0);
        protocol1_9To1_8.registerClientbound(ClientboundPackets1_8.MAP_BULK_CHUNK, null, WorldPackets::lambda$register$1);
        protocol1_9To1_8.registerClientbound(ClientboundPackets1_8.BLOCK_ENTITY_DATA, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Type.POSITION);
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.NBT);
                this.handler(4::lambda$register$0);
            }

            private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                Object object;
                short s = packetWrapper.get(Type.UNSIGNED_BYTE, 0);
                if (s == 1 && (object = packetWrapper.get(Type.NBT, 0)) != null) {
                    if (((CompoundTag)object).contains("EntityId")) {
                        String string = (String)((Tag)((CompoundTag)object).get("EntityId")).getValue();
                        CompoundTag compoundTag = new CompoundTag();
                        compoundTag.put("id", new StringTag(string));
                        ((CompoundTag)object).put("SpawnData", compoundTag);
                    } else {
                        CompoundTag compoundTag = new CompoundTag();
                        compoundTag.put("id", new StringTag("AreaEffectCloud"));
                        ((CompoundTag)object).put("SpawnData", compoundTag);
                    }
                }
                if (s == 2) {
                    object = Via.getManager().getProviders().get(CommandBlockProvider.class);
                    ((CommandBlockProvider)object).addOrUpdateBlock(packetWrapper.user(), packetWrapper.get(Type.POSITION, 0), packetWrapper.get(Type.NBT, 0));
                    packetWrapper.cancel();
                }
            }
        });
        protocol1_9To1_8.registerServerbound(ServerboundPackets1_9.UPDATE_SIGN, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Type.POSITION);
                this.map(Type.STRING, Protocol1_9To1_8.FIX_JSON);
                this.map(Type.STRING, Protocol1_9To1_8.FIX_JSON);
                this.map(Type.STRING, Protocol1_9To1_8.FIX_JSON);
                this.map(Type.STRING, Protocol1_9To1_8.FIX_JSON);
            }
        });
        protocol1_9To1_8.registerServerbound(ServerboundPackets1_9.PLAYER_DIGGING, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Type.VAR_INT);
                this.map(Type.POSITION);
                this.handler(6::lambda$register$0);
                this.handler(6::lambda$register$1);
            }

            private static void lambda$register$1(PacketWrapper packetWrapper) throws Exception {
                EntityTracker1_9 entityTracker1_9;
                int n = packetWrapper.get(Type.VAR_INT, 0);
                if ((n == 5 || n == 4 || n == 3) && (entityTracker1_9 = (EntityTracker1_9)packetWrapper.user().getEntityTracker(Protocol1_9To1_8.class)).isBlocking()) {
                    entityTracker1_9.setBlocking(true);
                    if (!Via.getConfig().isShowShieldWhenSwordInHand()) {
                        entityTracker1_9.setSecondHand(null);
                    }
                }
            }

            private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                int n = packetWrapper.get(Type.VAR_INT, 0);
                if (n == 6) {
                    packetWrapper.cancel();
                }
            }
        });
        protocol1_9To1_8.registerServerbound(ServerboundPackets1_9.USE_ITEM, null, WorldPackets::lambda$register$2);
        protocol1_9To1_8.registerServerbound(ServerboundPackets1_9.PLAYER_BLOCK_PLACEMENT, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Type.POSITION);
                this.map((Type)Type.VAR_INT, Type.UNSIGNED_BYTE);
                this.handler(7::lambda$register$0);
                this.handler(7::lambda$register$1);
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.UNSIGNED_BYTE);
                this.handler(7::lambda$register$2);
                this.handler(7::lambda$register$3);
            }

            private static void lambda$register$3(PacketWrapper packetWrapper) throws Exception {
                CommandBlockProvider commandBlockProvider = Via.getManager().getProviders().get(CommandBlockProvider.class);
                Position position = packetWrapper.get(Type.POSITION, 0);
                Optional<CompoundTag> optional = commandBlockProvider.get(packetWrapper.user(), position);
                if (optional.isPresent()) {
                    PacketWrapper packetWrapper2 = PacketWrapper.create(ClientboundPackets1_9.BLOCK_ENTITY_DATA, null, packetWrapper.user());
                    packetWrapper2.write(Type.POSITION, position);
                    packetWrapper2.write(Type.UNSIGNED_BYTE, (short)2);
                    packetWrapper2.write(Type.NBT, optional.get());
                    packetWrapper2.scheduleSend(Protocol1_9To1_8.class);
                }
            }

            private static void lambda$register$2(PacketWrapper packetWrapper) throws Exception {
                short s = packetWrapper.get(Type.UNSIGNED_BYTE, 0);
                if (s == 255) {
                    return;
                }
                Position position = packetWrapper.get(Type.POSITION, 0);
                int n = position.x();
                int n2 = position.y();
                int n3 = position.z();
                switch (s) {
                    case 0: {
                        --n2;
                        break;
                    }
                    case 1: {
                        ++n2;
                        break;
                    }
                    case 2: {
                        --n3;
                        break;
                    }
                    case 3: {
                        ++n3;
                        break;
                    }
                    case 4: {
                        --n;
                        break;
                    }
                    case 5: {
                        ++n;
                    }
                }
                EntityTracker1_9 entityTracker1_9 = (EntityTracker1_9)packetWrapper.user().getEntityTracker(Protocol1_9To1_8.class);
                entityTracker1_9.addBlockInteraction(new Position(n, n2, n3));
            }

            private static void lambda$register$1(PacketWrapper packetWrapper) throws Exception {
                Item item = Protocol1_9To1_8.getHandItem(packetWrapper.user());
                packetWrapper.write(Type.ITEM, item);
            }

            private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                int n = packetWrapper.read(Type.VAR_INT);
                if (n != 0) {
                    packetWrapper.cancel();
                }
            }
        });
    }

    private static void lambda$register$2(PacketWrapper packetWrapper) throws Exception {
        int n = packetWrapper.read(Type.VAR_INT);
        packetWrapper.clearInputBuffer();
        packetWrapper.setPacketType(ServerboundPackets1_8.PLAYER_BLOCK_PLACEMENT);
        packetWrapper.write(Type.POSITION, new Position(-1, -1, -1));
        packetWrapper.write(Type.UNSIGNED_BYTE, (short)255);
        Item item = Protocol1_9To1_8.getHandItem(packetWrapper.user());
        if (Via.getConfig().isShieldBlocking()) {
            boolean bl;
            EntityTracker1_9 entityTracker1_9 = (EntityTracker1_9)packetWrapper.user().getEntityTracker(Protocol1_9To1_8.class);
            boolean bl2 = Via.getConfig().isShowShieldWhenSwordInHand();
            boolean bl3 = bl2 ? entityTracker1_9.hasSwordInHand() : (bl = item != null && Protocol1_9To1_8.isSword(item.identifier()));
            if (bl) {
                boolean bl4;
                if (n == 0 && !entityTracker1_9.isBlocking()) {
                    entityTracker1_9.setBlocking(false);
                    if (!bl2 && entityTracker1_9.getItemInSecondHand() == null) {
                        DataItem dataItem = new DataItem(442, 1, 0, null);
                        entityTracker1_9.setSecondHand(dataItem);
                    }
                }
                boolean bl5 = bl4 = Via.getConfig().isNoDelayShieldBlocking() && !bl2;
                if (bl4 && n == 1 || !bl4 && n == 0) {
                    packetWrapper.cancel();
                }
            } else {
                if (!bl2) {
                    entityTracker1_9.setSecondHand(null);
                }
                entityTracker1_9.setBlocking(true);
            }
        }
        packetWrapper.write(Type.ITEM, item);
        packetWrapper.write(Type.UNSIGNED_BYTE, (short)0);
        packetWrapper.write(Type.UNSIGNED_BYTE, (short)0);
        packetWrapper.write(Type.UNSIGNED_BYTE, (short)0);
    }

    private static void lambda$register$1(PacketWrapper packetWrapper) throws Exception {
        packetWrapper.cancel();
        ClientWorld clientWorld = packetWrapper.user().get(ClientWorld.class);
        ClientChunks clientChunks = packetWrapper.user().get(ClientChunks.class);
        Chunk[] chunkArray = packetWrapper.read(new ChunkBulk1_8Type(clientWorld));
        Chunk1_9_1_2Type chunk1_9_1_2Type = new Chunk1_9_1_2Type(clientWorld);
        for (Chunk chunk : chunkArray) {
            PacketWrapper packetWrapper2 = packetWrapper.create(ClientboundPackets1_9.CHUNK_DATA);
            packetWrapper2.write(chunk1_9_1_2Type, chunk);
            packetWrapper2.send(Protocol1_9To1_8.class);
            clientChunks.getLoadedChunks().add(ClientChunks.toLong(chunk.getX(), chunk.getZ()));
            if (!Via.getConfig().isChunkBorderFix()) continue;
            for (BlockFace blockFace : BlockFace.HORIZONTAL) {
                int n = chunk.getX() + blockFace.modX();
                int n2 = chunk.getZ() + blockFace.modZ();
                if (clientChunks.getLoadedChunks().contains(ClientChunks.toLong(n, n2))) continue;
                PacketWrapper packetWrapper3 = packetWrapper.create(ClientboundPackets1_9.CHUNK_DATA);
                BaseChunk baseChunk = new BaseChunk(n, n2, true, false, 0, new ChunkSection[16], new int[256], new ArrayList<CompoundTag>());
                packetWrapper3.write(chunk1_9_1_2Type, baseChunk);
                packetWrapper3.send(Protocol1_9To1_8.class);
            }
        }
    }

    private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
        block4: {
            long l;
            Chunk chunk;
            ClientChunks clientChunks;
            ClientWorld clientWorld;
            block3: {
                clientWorld = packetWrapper.user().get(ClientWorld.class);
                clientChunks = packetWrapper.user().get(ClientChunks.class);
                chunk = packetWrapper.read(new Chunk1_8Type(clientWorld));
                l = ClientChunks.toLong(chunk.getX(), chunk.getZ());
                if (!chunk.isFullChunk() || chunk.getBitmask() != 0) break block3;
                packetWrapper.setPacketType(ClientboundPackets1_9.UNLOAD_CHUNK);
                packetWrapper.write(Type.INT, chunk.getX());
                packetWrapper.write(Type.INT, chunk.getZ());
                CommandBlockProvider commandBlockProvider = Via.getManager().getProviders().get(CommandBlockProvider.class);
                commandBlockProvider.unloadChunk(packetWrapper.user(), chunk.getX(), chunk.getZ());
                clientChunks.getLoadedChunks().remove(l);
                if (!Via.getConfig().isChunkBorderFix()) break block4;
                for (BlockFace blockFace : BlockFace.HORIZONTAL) {
                    int n = chunk.getX() + blockFace.modX();
                    int n2 = chunk.getZ() + blockFace.modZ();
                    if (clientChunks.getLoadedChunks().contains(ClientChunks.toLong(n, n2))) continue;
                    PacketWrapper packetWrapper2 = packetWrapper.create(ClientboundPackets1_9.UNLOAD_CHUNK);
                    packetWrapper2.write(Type.INT, n);
                    packetWrapper2.write(Type.INT, n2);
                    packetWrapper2.send(Protocol1_9To1_8.class);
                }
                break block4;
            }
            Chunk1_9_1_2Type chunk1_9_1_2Type = new Chunk1_9_1_2Type(clientWorld);
            packetWrapper.write(chunk1_9_1_2Type, chunk);
            clientChunks.getLoadedChunks().add(l);
            if (Via.getConfig().isChunkBorderFix()) {
                for (BlockFace blockFace : BlockFace.HORIZONTAL) {
                    int n = chunk.getX() + blockFace.modX();
                    int n3 = chunk.getZ() + blockFace.modZ();
                    if (clientChunks.getLoadedChunks().contains(ClientChunks.toLong(n, n3))) continue;
                    PacketWrapper packetWrapper3 = packetWrapper.create(ClientboundPackets1_9.CHUNK_DATA);
                    BaseChunk baseChunk = new BaseChunk(n, n3, true, false, 0, new ChunkSection[16], new int[256], new ArrayList<CompoundTag>());
                    packetWrapper3.write(chunk1_9_1_2Type, baseChunk);
                    packetWrapper3.send(Protocol1_9To1_8.class);
                }
            }
        }
    }
}

