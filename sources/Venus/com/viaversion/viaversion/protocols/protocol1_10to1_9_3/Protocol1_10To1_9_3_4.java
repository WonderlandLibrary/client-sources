/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.protocols.protocol1_10to1_9_3;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
import com.viaversion.viaversion.api.minecraft.chunks.ChunkSection;
import com.viaversion.viaversion.api.minecraft.chunks.PaletteType;
import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
import com.viaversion.viaversion.api.protocol.AbstractProtocol;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.protocol.remapper.ValueTransformer;
import com.viaversion.viaversion.api.rewriter.ItemRewriter;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.version.Types1_9;
import com.viaversion.viaversion.protocols.protocol1_10to1_9_3.packets.InventoryPackets;
import com.viaversion.viaversion.protocols.protocol1_10to1_9_3.storage.ResourcePackTracker;
import com.viaversion.viaversion.protocols.protocol1_9_1_2to1_9_3_4.types.Chunk1_9_3_4Type;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.ClientboundPackets1_9_3;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.ServerboundPackets1_9_3;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.storage.ClientWorld;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class Protocol1_10To1_9_3_4
extends AbstractProtocol<ClientboundPackets1_9_3, ClientboundPackets1_9_3, ServerboundPackets1_9_3, ServerboundPackets1_9_3> {
    public static final ValueTransformer<Short, Float> TO_NEW_PITCH = new ValueTransformer<Short, Float>((Type)Type.FLOAT){

        @Override
        public Float transform(PacketWrapper packetWrapper, Short s) throws Exception {
            return Float.valueOf((float)s.shortValue() / 63.0f);
        }

        @Override
        public Object transform(PacketWrapper packetWrapper, Object object) throws Exception {
            return this.transform(packetWrapper, (Short)object);
        }
    };
    public static final ValueTransformer<List<Metadata>, List<Metadata>> TRANSFORM_METADATA = new ValueTransformer<List<Metadata>, List<Metadata>>(Types1_9.METADATA_LIST){

        @Override
        public List<Metadata> transform(PacketWrapper packetWrapper, List<Metadata> list) throws Exception {
            CopyOnWriteArrayList<Metadata> copyOnWriteArrayList = new CopyOnWriteArrayList<Metadata>(list);
            for (Metadata metadata : copyOnWriteArrayList) {
                if (metadata.id() < 5) continue;
                metadata.setId(metadata.id() + 1);
            }
            return copyOnWriteArrayList;
        }

        @Override
        public Object transform(PacketWrapper packetWrapper, Object object) throws Exception {
            return this.transform(packetWrapper, (List)object);
        }
    };
    private final InventoryPackets itemRewriter = new InventoryPackets(this);

    public Protocol1_10To1_9_3_4() {
        super(ClientboundPackets1_9_3.class, ClientboundPackets1_9_3.class, ServerboundPackets1_9_3.class, ServerboundPackets1_9_3.class);
    }

    @Override
    protected void registerPackets() {
        this.itemRewriter.register();
        this.registerClientbound(ClientboundPackets1_9_3.NAMED_SOUND, new PacketHandlers(this){
            final Protocol1_10To1_9_3_4 this$0;
            {
                this.this$0 = protocol1_10To1_9_3_4;
            }

            @Override
            public void register() {
                this.map(Type.STRING);
                this.map(Type.VAR_INT);
                this.map(Type.INT);
                this.map(Type.INT);
                this.map(Type.INT);
                this.map(Type.FLOAT);
                this.map(Type.UNSIGNED_BYTE, TO_NEW_PITCH);
            }
        });
        this.registerClientbound(ClientboundPackets1_9_3.SOUND, new PacketHandlers(this){
            final Protocol1_10To1_9_3_4 this$0;
            {
                this.this$0 = protocol1_10To1_9_3_4;
            }

            @Override
            public void register() {
                this.map(Type.VAR_INT);
                this.map(Type.VAR_INT);
                this.map(Type.INT);
                this.map(Type.INT);
                this.map(Type.INT);
                this.map(Type.FLOAT);
                this.map(Type.UNSIGNED_BYTE, TO_NEW_PITCH);
                this.handler(this::lambda$register$0);
            }

            private void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                int n = packetWrapper.get(Type.VAR_INT, 0);
                packetWrapper.set(Type.VAR_INT, 0, this.this$0.getNewSoundId(n));
            }
        });
        this.registerClientbound(ClientboundPackets1_9_3.ENTITY_METADATA, new PacketHandlers(this){
            final Protocol1_10To1_9_3_4 this$0;
            {
                this.this$0 = protocol1_10To1_9_3_4;
            }

            @Override
            public void register() {
                this.map(Type.VAR_INT);
                this.map(Types1_9.METADATA_LIST, TRANSFORM_METADATA);
            }
        });
        this.registerClientbound(ClientboundPackets1_9_3.SPAWN_MOB, new PacketHandlers(this){
            final Protocol1_10To1_9_3_4 this$0;
            {
                this.this$0 = protocol1_10To1_9_3_4;
            }

            @Override
            public void register() {
                this.map(Type.VAR_INT);
                this.map(Type.UUID);
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.DOUBLE);
                this.map(Type.DOUBLE);
                this.map(Type.DOUBLE);
                this.map(Type.BYTE);
                this.map(Type.BYTE);
                this.map(Type.BYTE);
                this.map(Type.SHORT);
                this.map(Type.SHORT);
                this.map(Type.SHORT);
                this.map(Types1_9.METADATA_LIST, TRANSFORM_METADATA);
            }
        });
        this.registerClientbound(ClientboundPackets1_9_3.SPAWN_PLAYER, new PacketHandlers(this){
            final Protocol1_10To1_9_3_4 this$0;
            {
                this.this$0 = protocol1_10To1_9_3_4;
            }

            @Override
            public void register() {
                this.map(Type.VAR_INT);
                this.map(Type.UUID);
                this.map(Type.DOUBLE);
                this.map(Type.DOUBLE);
                this.map(Type.DOUBLE);
                this.map(Type.BYTE);
                this.map(Type.BYTE);
                this.map(Types1_9.METADATA_LIST, TRANSFORM_METADATA);
            }
        });
        this.registerClientbound(ClientboundPackets1_9_3.JOIN_GAME, new PacketHandlers(this){
            final Protocol1_10To1_9_3_4 this$0;
            {
                this.this$0 = protocol1_10To1_9_3_4;
            }

            @Override
            public void register() {
                this.map(Type.INT);
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.INT);
                this.handler(8::lambda$register$0);
            }

            private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                ClientWorld clientWorld = packetWrapper.user().get(ClientWorld.class);
                int n = packetWrapper.get(Type.INT, 1);
                clientWorld.setEnvironment(n);
            }
        });
        this.registerClientbound(ClientboundPackets1_9_3.RESPAWN, new PacketHandlers(this){
            final Protocol1_10To1_9_3_4 this$0;
            {
                this.this$0 = protocol1_10To1_9_3_4;
            }

            @Override
            public void register() {
                this.map(Type.INT);
                this.handler(9::lambda$register$0);
            }

            private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                ClientWorld clientWorld = packetWrapper.user().get(ClientWorld.class);
                int n = packetWrapper.get(Type.INT, 0);
                clientWorld.setEnvironment(n);
            }
        });
        this.registerClientbound(ClientboundPackets1_9_3.CHUNK_DATA, Protocol1_10To1_9_3_4::lambda$registerPackets$0);
        this.registerClientbound(ClientboundPackets1_9_3.RESOURCE_PACK, new PacketHandlers(this){
            final Protocol1_10To1_9_3_4 this$0;
            {
                this.this$0 = protocol1_10To1_9_3_4;
            }

            @Override
            public void register() {
                this.map(Type.STRING);
                this.map(Type.STRING);
                this.handler(10::lambda$register$0);
            }

            private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                ResourcePackTracker resourcePackTracker = packetWrapper.user().get(ResourcePackTracker.class);
                resourcePackTracker.setLastHash(packetWrapper.get(Type.STRING, 1));
            }
        });
        this.registerServerbound(ServerboundPackets1_9_3.RESOURCE_PACK_STATUS, new PacketHandlers(this){
            final Protocol1_10To1_9_3_4 this$0;
            {
                this.this$0 = protocol1_10To1_9_3_4;
            }

            @Override
            public void register() {
                this.handler(11::lambda$register$0);
            }

            private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                ResourcePackTracker resourcePackTracker = packetWrapper.user().get(ResourcePackTracker.class);
                packetWrapper.write(Type.STRING, resourcePackTracker.getLastHash());
                packetWrapper.write(Type.VAR_INT, packetWrapper.read(Type.VAR_INT));
            }
        });
    }

    public int getNewSoundId(int n) {
        int n2 = n;
        if (n >= 24) {
            ++n2;
        }
        if (n >= 248) {
            n2 += 4;
        }
        if (n >= 296) {
            n2 += 6;
        }
        if (n >= 354) {
            n2 += 4;
        }
        if (n >= 372) {
            n2 += 4;
        }
        return n2;
    }

    @Override
    public void init(UserConnection userConnection) {
        userConnection.put(new ResourcePackTracker());
        if (!userConnection.has(ClientWorld.class)) {
            userConnection.put(new ClientWorld(userConnection));
        }
    }

    public InventoryPackets getItemRewriter() {
        return this.itemRewriter;
    }

    @Override
    public ItemRewriter getItemRewriter() {
        return this.getItemRewriter();
    }

    private static void lambda$registerPackets$0(PacketWrapper packetWrapper) throws Exception {
        ClientWorld clientWorld = packetWrapper.user().get(ClientWorld.class);
        Chunk chunk = packetWrapper.passthrough(new Chunk1_9_3_4Type(clientWorld));
        if (Via.getConfig().isReplacePistons()) {
            int n = Via.getConfig().getPistonReplacementId();
            for (ChunkSection chunkSection : chunk.getSections()) {
                if (chunkSection == null) continue;
                chunkSection.palette(PaletteType.BLOCKS).replaceId(36, n);
            }
        }
    }
}

