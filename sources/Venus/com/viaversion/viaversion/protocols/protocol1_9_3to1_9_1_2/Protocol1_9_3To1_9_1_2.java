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
import com.viaversion.viaversion.protocols.protocol1_9_1_2to1_9_3_4.types.Chunk1_9_3_4Type;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.ClientboundPackets1_9_3;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.ServerboundPackets1_9_3;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.chunks.FakeTileEntity;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.storage.ClientWorld;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.types.Chunk1_9_1_2Type;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.ClientboundPackets1_9;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.ServerboundPackets1_9;
import java.util.List;

public class Protocol1_9_3To1_9_1_2
extends AbstractProtocol<ClientboundPackets1_9, ClientboundPackets1_9_3, ServerboundPackets1_9, ServerboundPackets1_9_3> {
    public static final ValueTransformer<Short, Short> ADJUST_PITCH = new ValueTransformer<Short, Short>((Type)Type.UNSIGNED_BYTE, (Type)Type.UNSIGNED_BYTE){

        @Override
        public Short transform(PacketWrapper packetWrapper, Short s) throws Exception {
            return (short)Math.round((float)s.shortValue() / 63.5f * 63.0f);
        }

        @Override
        public Object transform(PacketWrapper packetWrapper, Object object) throws Exception {
            return this.transform(packetWrapper, (Short)object);
        }
    };

    public Protocol1_9_3To1_9_1_2() {
        super(ClientboundPackets1_9.class, ClientboundPackets1_9_3.class, ServerboundPackets1_9.class, ServerboundPackets1_9_3.class);
    }

    @Override
    protected void registerPackets() {
        this.registerClientbound(ClientboundPackets1_9.UPDATE_SIGN, null, Protocol1_9_3To1_9_1_2::lambda$registerPackets$0);
        this.registerClientbound(ClientboundPackets1_9.CHUNK_DATA, Protocol1_9_3To1_9_1_2::lambda$registerPackets$1);
        this.registerClientbound(ClientboundPackets1_9.JOIN_GAME, new PacketHandlers(this){
            final Protocol1_9_3To1_9_1_2 this$0;
            {
                this.this$0 = protocol1_9_3To1_9_1_2;
            }

            @Override
            public void register() {
                this.map(Type.INT);
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.INT);
                this.handler(2::lambda$register$0);
            }

            private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                ClientWorld clientWorld = packetWrapper.user().get(ClientWorld.class);
                int n = packetWrapper.get(Type.INT, 1);
                clientWorld.setEnvironment(n);
            }
        });
        this.registerClientbound(ClientboundPackets1_9.RESPAWN, new PacketHandlers(this){
            final Protocol1_9_3To1_9_1_2 this$0;
            {
                this.this$0 = protocol1_9_3To1_9_1_2;
            }

            @Override
            public void register() {
                this.map(Type.INT);
                this.handler(3::lambda$register$0);
            }

            private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                ClientWorld clientWorld = packetWrapper.user().get(ClientWorld.class);
                int n = packetWrapper.get(Type.INT, 0);
                clientWorld.setEnvironment(n);
            }
        });
        this.registerClientbound(ClientboundPackets1_9.SOUND, new PacketHandlers(this){
            final Protocol1_9_3To1_9_1_2 this$0;
            {
                this.this$0 = protocol1_9_3To1_9_1_2;
            }

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
    public void init(UserConnection userConnection) {
        if (!userConnection.has(ClientWorld.class)) {
            userConnection.put(new ClientWorld(userConnection));
        }
    }

    private static void lambda$registerPackets$1(PacketWrapper packetWrapper) throws Exception {
        ClientWorld clientWorld = packetWrapper.user().get(ClientWorld.class);
        Chunk chunk = packetWrapper.read(new Chunk1_9_1_2Type(clientWorld));
        packetWrapper.write(new Chunk1_9_3_4Type(clientWorld), chunk);
        List<CompoundTag> list = chunk.getBlockEntities();
        for (int i = 0; i < chunk.getSections().length; ++i) {
            ChunkSection chunkSection = chunk.getSections()[i];
            if (chunkSection == null) continue;
            DataPalette dataPalette = chunkSection.palette(PaletteType.BLOCKS);
            for (int j = 0; j < 4096; ++j) {
                int n = dataPalette.idAt(j) >> 4;
                if (!FakeTileEntity.isTileEntity(n)) continue;
                list.add(FakeTileEntity.createTileEntity(ChunkSection.xFromIndex(j) + (chunk.getX() << 4), ChunkSection.yFromIndex(j) + (i << 4), ChunkSection.zFromIndex(j) + (chunk.getZ() << 4), n));
            }
        }
    }

    private static void lambda$registerPackets$0(PacketWrapper packetWrapper) throws Exception {
        Position position = packetWrapper.read(Type.POSITION);
        JsonElement[] jsonElementArray = new JsonElement[4];
        for (int i = 0; i < 4; ++i) {
            jsonElementArray[i] = packetWrapper.read(Type.COMPONENT);
        }
        packetWrapper.clearInputBuffer();
        packetWrapper.setPacketType(ClientboundPackets1_9_3.BLOCK_ENTITY_DATA);
        packetWrapper.write(Type.POSITION, position);
        packetWrapper.write(Type.UNSIGNED_BYTE, (short)9);
        CompoundTag compoundTag = new CompoundTag();
        compoundTag.put("id", new StringTag("Sign"));
        compoundTag.put("x", new IntTag(position.x()));
        compoundTag.put("y", new IntTag(position.y()));
        compoundTag.put("z", new IntTag(position.z()));
        for (int i = 0; i < jsonElementArray.length; ++i) {
            compoundTag.put("Text" + (i + 1), new StringTag(jsonElementArray[i].toString()));
        }
        packetWrapper.write(Type.NBT, compoundTag);
    }
}

