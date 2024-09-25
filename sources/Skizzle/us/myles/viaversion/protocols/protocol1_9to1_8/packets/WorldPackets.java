/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  io.netty.buffer.ByteBuf
 */
package us.myles.ViaVersion.protocols.protocol1_9to1_8.packets;

import io.netty.buffer.ByteBuf;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import us.myles.ViaVersion.api.PacketWrapper;
import us.myles.ViaVersion.api.Via;
import us.myles.ViaVersion.api.minecraft.Position;
import us.myles.ViaVersion.api.minecraft.chunks.Chunk1_8;
import us.myles.ViaVersion.api.minecraft.item.Item;
import us.myles.ViaVersion.api.protocol.Protocol;
import us.myles.ViaVersion.api.remapper.PacketHandler;
import us.myles.ViaVersion.api.remapper.PacketRemapper;
import us.myles.ViaVersion.api.remapper.ValueCreator;
import us.myles.ViaVersion.api.type.Type;
import us.myles.ViaVersion.protocols.protocol1_8.ClientboundPackets1_8;
import us.myles.ViaVersion.protocols.protocol1_9to1_8.ItemRewriter;
import us.myles.ViaVersion.protocols.protocol1_9to1_8.Protocol1_9To1_8;
import us.myles.ViaVersion.protocols.protocol1_9to1_8.ServerboundPackets1_9;
import us.myles.ViaVersion.protocols.protocol1_9to1_8.providers.BulkChunkTranslatorProvider;
import us.myles.ViaVersion.protocols.protocol1_9to1_8.providers.CommandBlockProvider;
import us.myles.ViaVersion.protocols.protocol1_9to1_8.sounds.Effect;
import us.myles.ViaVersion.protocols.protocol1_9to1_8.sounds.SoundEffect;
import us.myles.ViaVersion.protocols.protocol1_9to1_8.storage.ClientChunks;
import us.myles.ViaVersion.protocols.protocol1_9to1_8.storage.EntityTracker1_9;
import us.myles.ViaVersion.protocols.protocol1_9to1_8.storage.PlaceBlockTracker;
import us.myles.ViaVersion.protocols.protocol1_9to1_8.types.Chunk1_9to1_8Type;
import us.myles.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import us.myles.viaversion.libs.opennbt.tag.builtin.StringTag;
import us.myles.viaversion.libs.opennbt.tag.builtin.Tag;

public class WorldPackets {
    public static void register(Protocol protocol) {
        protocol.registerOutgoing(ClientboundPackets1_8.UPDATE_SIGN, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.POSITION);
                this.map(Type.STRING, Protocol1_9To1_8.FIX_JSON);
                this.map(Type.STRING, Protocol1_9To1_8.FIX_JSON);
                this.map(Type.STRING, Protocol1_9To1_8.FIX_JSON);
                this.map(Type.STRING, Protocol1_9To1_8.FIX_JSON);
            }
        });
        protocol.registerOutgoing(ClientboundPackets1_8.EFFECT, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.INT);
                this.map(Type.POSITION);
                this.map(Type.INT);
                this.map(Type.BOOLEAN);
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper wrapper) throws Exception {
                        int id = wrapper.get(Type.INT, 0);
                        id = Effect.getNewId(id);
                        wrapper.set(Type.INT, 0, id);
                    }
                });
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper wrapper) throws Exception {
                        int id = wrapper.get(Type.INT, 0);
                        if (id == 2002) {
                            int data = wrapper.get(Type.INT, 1);
                            int newData = ItemRewriter.getNewEffectID(data);
                            wrapper.set(Type.INT, 1, newData);
                        }
                    }
                });
            }
        });
        protocol.registerOutgoing(ClientboundPackets1_8.NAMED_SOUND, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.STRING);
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper wrapper) throws Exception {
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
                            EntityTracker1_9 tracker = wrapper.user().get(EntityTracker1_9.class);
                            int x = wrapper.passthrough(Type.INT);
                            int y = wrapper.passthrough(Type.INT);
                            int z = wrapper.passthrough(Type.INT);
                            if (tracker.interactedBlockRecently((int)Math.floor((double)x / 8.0), (int)Math.floor((double)y / 8.0), (int)Math.floor((double)z / 8.0))) {
                                wrapper.cancel();
                            }
                        }
                    }
                });
            }
        });
        protocol.registerOutgoing(ClientboundPackets1_8.CHUNK_DATA, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper wrapper) throws Exception {
                        ClientChunks clientChunks = wrapper.user().get(ClientChunks.class);
                        Chunk1_9to1_8Type type = new Chunk1_9to1_8Type(clientChunks);
                        Chunk1_8 chunk = (Chunk1_8)wrapper.read(type);
                        if (chunk.isUnloadPacket()) {
                            wrapper.setId(29);
                            wrapper.write(Type.INT, chunk.getX());
                            wrapper.write(Type.INT, chunk.getZ());
                            CommandBlockProvider provider = Via.getManager().getProviders().get(CommandBlockProvider.class);
                            provider.unloadChunk(wrapper.user(), chunk.getX(), chunk.getZ());
                        } else {
                            wrapper.write(type, chunk);
                        }
                        wrapper.read(Type.REMAINING_BYTES);
                    }
                });
            }
        });
        protocol.registerOutgoing(ClientboundPackets1_8.MAP_BULK_CHUNK, null, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.handler(new PacketHandler(){

                    /*
                     * WARNING - Removed try catching itself - possible behaviour change.
                     */
                    @Override
                    public void handle(PacketWrapper wrapper) throws Exception {
                        wrapper.cancel();
                        BulkChunkTranslatorProvider provider = Via.getManager().getProviders().get(BulkChunkTranslatorProvider.class);
                        if (!provider.isPacketLevel()) {
                            return;
                        }
                        List<Object> list = provider.transformMapChunkBulk(wrapper, wrapper.user().get(ClientChunks.class));
                        for (Object obj : list) {
                            if (!(obj instanceof PacketWrapper)) {
                                throw new IOException("transformMapChunkBulk returned the wrong object type");
                            }
                            PacketWrapper output = (PacketWrapper)obj;
                            ByteBuf buffer = wrapper.user().getChannel().alloc().buffer();
                            try {
                                output.setId(-1);
                                output.writeToBuffer(buffer);
                                PacketWrapper chunkPacket = new PacketWrapper(33, buffer, wrapper.user());
                                chunkPacket.send(Protocol1_9To1_8.class, false, true);
                            }
                            finally {
                                buffer.release();
                            }
                        }
                    }
                });
            }
        });
        protocol.registerOutgoing(ClientboundPackets1_8.BLOCK_ENTITY_DATA, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.POSITION);
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.NBT);
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper wrapper) throws Exception {
                        CompoundTag tag;
                        short action = wrapper.get(Type.UNSIGNED_BYTE, 0);
                        if (action == 1 && (tag = wrapper.get(Type.NBT, 0)) != null) {
                            if (tag.contains("EntityId")) {
                                String entity = (String)((Tag)tag.get("EntityId")).getValue();
                                CompoundTag spawn = new CompoundTag("SpawnData");
                                spawn.put(new StringTag("id", entity));
                                tag.put(spawn);
                            } else {
                                CompoundTag spawn = new CompoundTag("SpawnData");
                                spawn.put(new StringTag("id", "AreaEffectCloud"));
                                tag.put(spawn);
                            }
                        }
                        if (action == 2) {
                            CommandBlockProvider provider = Via.getManager().getProviders().get(CommandBlockProvider.class);
                            provider.addOrUpdateBlock(wrapper.user(), wrapper.get(Type.POSITION, 0), wrapper.get(Type.NBT, 0));
                            wrapper.cancel();
                        }
                    }
                });
            }
        });
        protocol.registerOutgoing(ClientboundPackets1_8.BLOCK_CHANGE, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.POSITION);
                this.map(Type.VAR_INT);
            }
        });
        protocol.registerIncoming(ServerboundPackets1_9.UPDATE_SIGN, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.POSITION);
                this.map(Type.STRING, Protocol1_9To1_8.FIX_JSON);
                this.map(Type.STRING, Protocol1_9To1_8.FIX_JSON);
                this.map(Type.STRING, Protocol1_9To1_8.FIX_JSON);
                this.map(Type.STRING, Protocol1_9To1_8.FIX_JSON);
            }
        });
        protocol.registerIncoming(ServerboundPackets1_9.PLAYER_DIGGING, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map((Type)Type.VAR_INT, Type.UNSIGNED_BYTE);
                this.map(Type.POSITION);
                this.map(Type.BYTE);
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper wrapper) throws Exception {
                        short status = wrapper.get(Type.UNSIGNED_BYTE, 0);
                        if (status == 6) {
                            wrapper.cancel();
                        }
                    }
                });
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper wrapper) throws Exception {
                        EntityTracker1_9 entityTracker;
                        short status = wrapper.get(Type.UNSIGNED_BYTE, 0);
                        if ((status == 5 || status == 4 || status == 3) && (entityTracker = wrapper.user().get(EntityTracker1_9.class)).isBlocking()) {
                            entityTracker.setBlocking(false);
                            entityTracker.setSecondHand(null);
                        }
                    }
                });
            }
        });
        protocol.registerIncoming(ServerboundPackets1_9.USE_ITEM, null, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper wrapper) throws Exception {
                        int hand = wrapper.read(Type.VAR_INT);
                        wrapper.clearInputBuffer();
                        wrapper.setId(8);
                        wrapper.write(Type.POSITION, new Position(-1, -1, -1));
                        wrapper.write(Type.UNSIGNED_BYTE, (short)255);
                        Item item = Protocol1_9To1_8.getHandItem(wrapper.user());
                        if (Via.getConfig().isShieldBlocking()) {
                            EntityTracker1_9 tracker = wrapper.user().get(EntityTracker1_9.class);
                            if (item != null && Protocol1_9To1_8.isSword(item.getIdentifier())) {
                                if (hand == 0) {
                                    if (!tracker.isBlocking()) {
                                        tracker.setBlocking(true);
                                        Item shield = new Item(442, 1, 0, null);
                                        tracker.setSecondHand(shield);
                                    }
                                    wrapper.cancel();
                                }
                            } else {
                                tracker.setSecondHand(null);
                                tracker.setBlocking(false);
                            }
                        }
                        wrapper.write(Type.ITEM, item);
                        wrapper.write(Type.UNSIGNED_BYTE, (short)0);
                        wrapper.write(Type.UNSIGNED_BYTE, (short)0);
                        wrapper.write(Type.UNSIGNED_BYTE, (short)0);
                    }
                });
            }
        });
        protocol.registerIncoming(ServerboundPackets1_9.PLAYER_BLOCK_PLACEMENT, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.POSITION);
                this.map((Type)Type.VAR_INT, Type.UNSIGNED_BYTE);
                this.map((Type)Type.VAR_INT, Type.NOTHING);
                this.create(new ValueCreator(){

                    @Override
                    public void write(PacketWrapper wrapper) throws Exception {
                        Item item = Protocol1_9To1_8.getHandItem(wrapper.user());
                        wrapper.write(Type.ITEM, item);
                    }
                });
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.UNSIGNED_BYTE);
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper wrapper) throws Exception {
                        Position position = wrapper.get(Type.POSITION, 0);
                        PlaceBlockTracker tracker = wrapper.user().get(PlaceBlockTracker.class);
                        if (tracker.getLastPlacedPosition() != null && tracker.getLastPlacedPosition().equals(position) && !tracker.isExpired(50)) {
                            wrapper.cancel();
                        }
                        tracker.updateTime();
                        tracker.setLastPlacedPosition(position);
                    }
                });
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper wrapper) throws Exception {
                        short face = wrapper.get(Type.UNSIGNED_BYTE, 0);
                        if (face == 255) {
                            return;
                        }
                        Position p = wrapper.get(Type.POSITION, 0);
                        int x = p.getX();
                        short y = p.getY();
                        int z = p.getZ();
                        switch (face) {
                            case 0: {
                                y = (short)(y - 1);
                                break;
                            }
                            case 1: {
                                y = (short)(y + 1);
                                break;
                            }
                            case 2: {
                                --z;
                                break;
                            }
                            case 3: {
                                ++z;
                                break;
                            }
                            case 4: {
                                --x;
                                break;
                            }
                            case 5: {
                                ++x;
                            }
                        }
                        EntityTracker1_9 tracker = wrapper.user().get(EntityTracker1_9.class);
                        tracker.addBlockInteraction(new Position(x, y, z));
                    }
                });
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper wrapper) throws Exception {
                        CommandBlockProvider provider = Via.getManager().getProviders().get(CommandBlockProvider.class);
                        Position pos = wrapper.get(Type.POSITION, 0);
                        Optional<CompoundTag> tag = provider.get(wrapper.user(), pos);
                        if (tag.isPresent()) {
                            PacketWrapper updateBlockEntity = new PacketWrapper(9, null, wrapper.user());
                            updateBlockEntity.write(Type.POSITION, pos);
                            updateBlockEntity.write(Type.UNSIGNED_BYTE, (short)2);
                            updateBlockEntity.write(Type.NBT, tag.get());
                            updateBlockEntity.send(Protocol1_9To1_8.class);
                        }
                    }
                });
            }
        });
    }
}

