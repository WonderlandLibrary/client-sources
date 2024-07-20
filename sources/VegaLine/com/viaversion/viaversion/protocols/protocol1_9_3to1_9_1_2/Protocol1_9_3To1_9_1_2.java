/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.Position;
import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
import com.viaversion.viaversion.api.minecraft.chunks.ChunkSection;
import com.viaversion.viaversion.api.minecraft.chunks.DataPalette;
import com.viaversion.viaversion.api.minecraft.chunks.PaletteType;
import com.viaversion.viaversion.api.protocol.AbstractProtocol;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.protocol.remapper.ValueTransformer;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.IntTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
import com.viaversion.viaversion.protocols.protocol1_9_1to1_9.types.Chunk1_9_1_2Type;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.ClientboundPackets1_9_3;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.ServerboundPackets1_9_3;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.chunks.FakeTileEntity;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.storage.ClientWorld;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.types.Chunk1_9_3_4Type;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.ClientboundPackets1_9;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.ServerboundPackets1_9;
import java.util.List;

public class Protocol1_9_3To1_9_1_2
extends AbstractProtocol<ClientboundPackets1_9, ClientboundPackets1_9_3, ServerboundPackets1_9, ServerboundPackets1_9_3> {
    public static final ValueTransformer<Short, Short> ADJUST_PITCH = new ValueTransformer<Short, Short>((Type)Type.UNSIGNED_BYTE, (Type)Type.UNSIGNED_BYTE){

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
        this.registerClientbound(ClientboundPackets1_9.UPDATE_SIGN, null, (PacketWrapper wrapper) -> {
            Position position = wrapper.read(Type.POSITION);
            JsonElement[] lines = new JsonElement[4];
            for (int i = 0; i < 4; ++i) {
                lines[i] = wrapper.read(Type.COMPONENT);
            }
            wrapper.clearInputBuffer();
            wrapper.setPacketType(ClientboundPackets1_9_3.BLOCK_ENTITY_DATA);
            wrapper.write(Type.POSITION, position);
            wrapper.write(Type.UNSIGNED_BYTE, (short)9);
            CompoundTag tag = new CompoundTag();
            tag.put("id", new StringTag("Sign"));
            tag.put("x", new IntTag(position.x()));
            tag.put("y", new IntTag(position.y()));
            tag.put("z", new IntTag(position.z()));
            for (int i = 0; i < lines.length; ++i) {
                tag.put("Text" + (i + 1), new StringTag(lines[i].toString()));
            }
            wrapper.write(Type.NBT, tag);
        });
        this.registerClientbound(ClientboundPackets1_9.CHUNK_DATA, (PacketWrapper wrapper) -> {
            ClientWorld clientWorld = wrapper.user().get(ClientWorld.class);
            Chunk chunk = wrapper.read(new Chunk1_9_1_2Type(clientWorld));
            wrapper.write(new Chunk1_9_3_4Type(clientWorld), chunk);
            List<CompoundTag> tags = chunk.getBlockEntities();
            for (int s = 0; s < chunk.getSections().length; ++s) {
                ChunkSection section = chunk.getSections()[s];
                if (section == null) continue;
                DataPalette blocks = section.palette(PaletteType.BLOCKS);
                for (int idx = 0; idx < 4096; ++idx) {
                    int id = blocks.idAt(idx) >> 4;
                    if (!FakeTileEntity.isTileEntity(id)) continue;
                    tags.add(FakeTileEntity.createTileEntity(ChunkSection.xFromIndex(idx) + (chunk.getX() << 4), ChunkSection.yFromIndex(idx) + (s << 4), ChunkSection.zFromIndex(idx) + (chunk.getZ() << 4), id));
                }
            }
        });
        this.registerClientbound(ClientboundPackets1_9.JOIN_GAME, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Type.INT);
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.INT);
                this.handler(wrapper -> {
                    ClientWorld clientWorld = wrapper.user().get(ClientWorld.class);
                    int dimensionId = wrapper.get(Type.INT, 1);
                    clientWorld.setEnvironment(dimensionId);
                });
            }
        });
        this.registerClientbound(ClientboundPackets1_9.RESPAWN, new PacketHandlers(){

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
        this.registerClientbound(ClientboundPackets1_9.SOUND, new PacketHandlers(){

            @Override
            public void register() {
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

