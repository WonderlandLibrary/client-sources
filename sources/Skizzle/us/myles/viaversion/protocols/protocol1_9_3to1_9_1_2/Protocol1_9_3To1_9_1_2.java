/*
 * Decompiled with CFR 0.150.
 */
package us.myles.ViaVersion.protocols.protocol1_9_3to1_9_1_2;

import java.util.List;
import us.myles.ViaVersion.api.PacketWrapper;
import us.myles.ViaVersion.api.data.UserConnection;
import us.myles.ViaVersion.api.minecraft.Position;
import us.myles.ViaVersion.api.minecraft.chunks.Chunk;
import us.myles.ViaVersion.api.minecraft.chunks.ChunkSection;
import us.myles.ViaVersion.api.protocol.Protocol;
import us.myles.ViaVersion.api.remapper.PacketHandler;
import us.myles.ViaVersion.api.remapper.PacketRemapper;
import us.myles.ViaVersion.api.remapper.ValueTransformer;
import us.myles.ViaVersion.api.type.Type;
import us.myles.ViaVersion.protocols.protocol1_9_3to1_9_1_2.ClientboundPackets1_9_3;
import us.myles.ViaVersion.protocols.protocol1_9_3to1_9_1_2.ServerboundPackets1_9_3;
import us.myles.ViaVersion.protocols.protocol1_9_3to1_9_1_2.chunks.FakeTileEntity;
import us.myles.ViaVersion.protocols.protocol1_9_3to1_9_1_2.storage.ClientWorld;
import us.myles.ViaVersion.protocols.protocol1_9_3to1_9_1_2.types.Chunk1_9_1_2Type;
import us.myles.ViaVersion.protocols.protocol1_9to1_8.ClientboundPackets1_9;
import us.myles.ViaVersion.protocols.protocol1_9to1_8.ServerboundPackets1_9;
import us.myles.viaversion.libs.gson.JsonElement;
import us.myles.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import us.myles.viaversion.libs.opennbt.tag.builtin.IntTag;
import us.myles.viaversion.libs.opennbt.tag.builtin.StringTag;

public class Protocol1_9_3To1_9_1_2
extends Protocol<ClientboundPackets1_9, ClientboundPackets1_9_3, ServerboundPackets1_9, ServerboundPackets1_9_3> {
    public static final ValueTransformer<Short, Short> ADJUST_PITCH = new ValueTransformer<Short, Short>(Type.UNSIGNED_BYTE, Type.UNSIGNED_BYTE){

        @Override
        public Short transform(PacketWrapper wrapper, Short inputValue) throws Exception {
            return (short)Math.round((float)inputValue.shortValue() / 63.5f * 63.0f);
        }
    };

    public Protocol1_9_3To1_9_1_2() {
        super(ClientboundPackets1_9.class, ClientboundPackets1_9_3.class, ServerboundPackets1_9.class, ServerboundPackets1_9_3.class);
    }

    @Override
    protected void registerPackets() {
        this.registerOutgoing(ClientboundPackets1_9.UPDATE_SIGN, null, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper wrapper) throws Exception {
                        Position position = wrapper.read(Type.POSITION);
                        JsonElement[] lines = new JsonElement[4];
                        for (int i = 0; i < 4; ++i) {
                            lines[i] = wrapper.read(Type.COMPONENT);
                        }
                        wrapper.clearInputBuffer();
                        wrapper.setId(9);
                        wrapper.write(Type.POSITION, position);
                        wrapper.write(Type.UNSIGNED_BYTE, (short)9);
                        CompoundTag tag = new CompoundTag("");
                        tag.put(new StringTag("id", "Sign"));
                        tag.put(new IntTag("x", position.getX()));
                        tag.put(new IntTag("y", position.getY()));
                        tag.put(new IntTag("z", position.getZ()));
                        for (int i = 0; i < lines.length; ++i) {
                            tag.put(new StringTag("Text" + (i + 1), lines[i].toString()));
                        }
                        wrapper.write(Type.NBT, tag);
                    }
                });
            }
        });
        this.registerOutgoing(ClientboundPackets1_9.CHUNK_DATA, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper wrapper) throws Exception {
                        ClientWorld clientWorld = wrapper.user().get(ClientWorld.class);
                        Chunk1_9_1_2Type type = new Chunk1_9_1_2Type(clientWorld);
                        Chunk chunk = wrapper.passthrough(type);
                        List<CompoundTag> tags = chunk.getBlockEntities();
                        for (int i = 0; i < chunk.getSections().length; ++i) {
                            ChunkSection section = chunk.getSections()[i];
                            if (section == null) continue;
                            for (int y = 0; y < 16; ++y) {
                                for (int z = 0; z < 16; ++z) {
                                    for (int x = 0; x < 16; ++x) {
                                        int block = section.getBlockId(x, y, z);
                                        if (!FakeTileEntity.hasBlock(block)) continue;
                                        tags.add(FakeTileEntity.getFromBlock(x + (chunk.getX() << 4), y + (i << 4), z + (chunk.getZ() << 4), block));
                                    }
                                }
                            }
                        }
                        wrapper.write(Type.NBT_ARRAY, chunk.getBlockEntities().toArray(new CompoundTag[0]));
                    }
                });
            }
        });
        this.registerOutgoing(ClientboundPackets1_9.JOIN_GAME, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.INT);
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.INT);
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper wrapper) throws Exception {
                        ClientWorld clientChunks = wrapper.user().get(ClientWorld.class);
                        int dimensionId = wrapper.get(Type.INT, 1);
                        clientChunks.setEnvironment(dimensionId);
                    }
                });
            }
        });
        this.registerOutgoing(ClientboundPackets1_9.RESPAWN, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.INT);
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper wrapper) throws Exception {
                        ClientWorld clientWorld = wrapper.user().get(ClientWorld.class);
                        int dimensionId = wrapper.get(Type.INT, 0);
                        clientWorld.setEnvironment(dimensionId);
                    }
                });
            }
        });
        this.registerOutgoing(ClientboundPackets1_9.SOUND, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.map(Type.VAR_INT);
                this.map(Type.INT);
                this.map(Type.INT);
                this.map(Type.INT);
                this.map(Type.FLOAT);
                this.map(ADJUST_PITCH);
            }
        });
    }

    @Override
    public void init(UserConnection user) {
        if (!user.has(ClientWorld.class)) {
            user.put(new ClientWorld(user));
        }
    }
}

