/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.protocols.protocol1_11to1_10;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
import com.viaversion.viaversion.api.minecraft.entities.Entity1_11Types;
import com.viaversion.viaversion.api.protocol.AbstractProtocol;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.protocol.remapper.ValueTransformer;
import com.viaversion.viaversion.api.rewriter.EntityRewriter;
import com.viaversion.viaversion.api.rewriter.ItemRewriter;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.version.Types1_9;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
import com.viaversion.viaversion.protocols.protocol1_11to1_10.BlockEntityRewriter;
import com.viaversion.viaversion.protocols.protocol1_11to1_10.EntityIdRewriter;
import com.viaversion.viaversion.protocols.protocol1_11to1_10.data.PotionColorMapping;
import com.viaversion.viaversion.protocols.protocol1_11to1_10.metadata.MetadataRewriter1_11To1_10;
import com.viaversion.viaversion.protocols.protocol1_11to1_10.packets.InventoryPackets;
import com.viaversion.viaversion.protocols.protocol1_11to1_10.storage.EntityTracker1_11;
import com.viaversion.viaversion.protocols.protocol1_9_1_2to1_9_3_4.types.Chunk1_9_3_4Type;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.ClientboundPackets1_9_3;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.ServerboundPackets1_9_3;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.storage.ClientWorld;
import com.viaversion.viaversion.rewriter.SoundRewriter;
import com.viaversion.viaversion.util.Pair;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class Protocol1_11To1_10
extends AbstractProtocol<ClientboundPackets1_9_3, ClientboundPackets1_9_3, ServerboundPackets1_9_3, ServerboundPackets1_9_3> {
    private static final ValueTransformer<Float, Short> toOldByte = new ValueTransformer<Float, Short>((Type)Type.UNSIGNED_BYTE){

        @Override
        public Short transform(PacketWrapper packetWrapper, Float f) throws Exception {
            return (short)(f.floatValue() * 16.0f);
        }

        @Override
        public Object transform(PacketWrapper packetWrapper, Object object) throws Exception {
            return this.transform(packetWrapper, (Float)object);
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
        this.registerClientbound(ClientboundPackets1_9_3.SPAWN_ENTITY, new PacketHandlers(this){
            final Protocol1_11To1_10 this$0;
            {
                this.this$0 = protocol1_11To1_10;
            }

            @Override
            public void register() {
                this.map(Type.VAR_INT);
                this.map(Type.UUID);
                this.map(Type.BYTE);
                this.handler(Protocol1_11To1_10.access$000(this.this$0).objectTrackerHandler());
            }
        });
        this.registerClientbound(ClientboundPackets1_9_3.SPAWN_MOB, new PacketHandlers(this){
            final Protocol1_11To1_10 this$0;
            {
                this.this$0 = protocol1_11To1_10;
            }

            @Override
            public void register() {
                this.map(Type.VAR_INT);
                this.map(Type.UUID);
                this.map((Type)Type.UNSIGNED_BYTE, Type.VAR_INT);
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
                this.handler(this::lambda$register$0);
            }

            private void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                int n = packetWrapper.get(Type.VAR_INT, 0);
                int n2 = packetWrapper.get(Type.VAR_INT, 1);
                Entity1_11Types.EntityType entityType = MetadataRewriter1_11To1_10.rewriteEntityType(n2, packetWrapper.get(Types1_9.METADATA_LIST, 0));
                if (entityType != null) {
                    packetWrapper.set(Type.VAR_INT, 1, entityType.getId());
                    packetWrapper.user().getEntityTracker(Protocol1_11To1_10.class).addEntity(n, entityType);
                    Protocol1_11To1_10.access$000(this.this$0).handleMetadata(n, packetWrapper.get(Types1_9.METADATA_LIST, 0), packetWrapper.user());
                }
            }
        });
        new SoundRewriter<ClientboundPackets1_9_3>(this, this::getNewSoundId).registerSound(ClientboundPackets1_9_3.SOUND);
        this.registerClientbound(ClientboundPackets1_9_3.COLLECT_ITEM, new PacketHandlers(this){
            final Protocol1_11To1_10 this$0;
            {
                this.this$0 = protocol1_11To1_10;
            }

            @Override
            public void register() {
                this.map(Type.VAR_INT);
                this.map(Type.VAR_INT);
                this.handler(4::lambda$register$0);
            }

            private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                packetWrapper.write(Type.VAR_INT, 1);
            }
        });
        this.entityRewriter.registerMetadataRewriter(ClientboundPackets1_9_3.ENTITY_METADATA, Types1_9.METADATA_LIST);
        this.registerClientbound(ClientboundPackets1_9_3.ENTITY_TELEPORT, new PacketHandlers(this){
            final Protocol1_11To1_10 this$0;
            {
                this.this$0 = protocol1_11To1_10;
            }

            @Override
            public void register() {
                this.map(Type.VAR_INT);
                this.map(Type.DOUBLE);
                this.map(Type.DOUBLE);
                this.map(Type.DOUBLE);
                this.map(Type.BYTE);
                this.map(Type.BYTE);
                this.map(Type.BOOLEAN);
                this.handler(5::lambda$register$0);
            }

            private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                EntityTracker1_11 entityTracker1_11;
                int n = packetWrapper.get(Type.VAR_INT, 0);
                if (Via.getConfig().isHologramPatch() && (entityTracker1_11 = (EntityTracker1_11)packetWrapper.user().getEntityTracker(Protocol1_11To1_10.class)).isHologram(n)) {
                    Double d = packetWrapper.get(Type.DOUBLE, 1);
                    d = d - Via.getConfig().getHologramYOffset();
                    packetWrapper.set(Type.DOUBLE, 1, d);
                }
            }
        });
        this.entityRewriter.registerRemoveEntities(ClientboundPackets1_9_3.DESTROY_ENTITIES);
        this.registerClientbound(ClientboundPackets1_9_3.TITLE, new PacketHandlers(this){
            final Protocol1_11To1_10 this$0;
            {
                this.this$0 = protocol1_11To1_10;
            }

            @Override
            public void register() {
                this.map(Type.VAR_INT);
                this.handler(6::lambda$register$0);
            }

            private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                int n = packetWrapper.get(Type.VAR_INT, 0);
                if (n >= 2) {
                    packetWrapper.set(Type.VAR_INT, 0, n + 1);
                }
            }
        });
        this.registerClientbound(ClientboundPackets1_9_3.BLOCK_ACTION, new PacketHandlers(this){
            final Protocol1_11To1_10 this$0;
            {
                this.this$0 = protocol1_11To1_10;
            }

            @Override
            public void register() {
                this.map(Type.POSITION);
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.VAR_INT);
                this.handler(7::lambda$register$0);
            }

            private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                int n;
                if (Via.getConfig().isPistonAnimationPatch() && ((n = packetWrapper.get(Type.VAR_INT, 0).intValue()) == 33 || n == 29)) {
                    packetWrapper.cancel();
                }
            }
        });
        this.registerClientbound(ClientboundPackets1_9_3.BLOCK_ENTITY_DATA, new PacketHandlers(this){
            final Protocol1_11To1_10 this$0;
            {
                this.this$0 = protocol1_11To1_10;
            }

            @Override
            public void register() {
                this.map(Type.POSITION);
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.NBT);
                this.handler(8::lambda$register$0);
            }

            private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                CompoundTag compoundTag = packetWrapper.get(Type.NBT, 0);
                if (packetWrapper.get(Type.UNSIGNED_BYTE, 0) == 1) {
                    EntityIdRewriter.toClientSpawner(compoundTag);
                }
                if (compoundTag.contains("id")) {
                    ((StringTag)compoundTag.get("id")).setValue(BlockEntityRewriter.toNewIdentifier((String)((Tag)compoundTag.get("id")).getValue()));
                }
            }
        });
        this.registerClientbound(ClientboundPackets1_9_3.CHUNK_DATA, Protocol1_11To1_10::lambda$registerPackets$0);
        this.registerClientbound(ClientboundPackets1_9_3.JOIN_GAME, new PacketHandlers(this){
            final Protocol1_11To1_10 this$0;
            {
                this.this$0 = protocol1_11To1_10;
            }

            @Override
            public void register() {
                this.map(Type.INT);
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.INT);
                this.handler(9::lambda$register$0);
            }

            private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                ClientWorld clientWorld = packetWrapper.user().get(ClientWorld.class);
                int n = packetWrapper.get(Type.INT, 1);
                clientWorld.setEnvironment(n);
            }
        });
        this.registerClientbound(ClientboundPackets1_9_3.RESPAWN, new PacketHandlers(this){
            final Protocol1_11To1_10 this$0;
            {
                this.this$0 = protocol1_11To1_10;
            }

            @Override
            public void register() {
                this.map(Type.INT);
                this.handler(10::lambda$register$0);
            }

            private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                ClientWorld clientWorld = packetWrapper.user().get(ClientWorld.class);
                int n = packetWrapper.get(Type.INT, 0);
                clientWorld.setEnvironment(n);
            }
        });
        this.registerClientbound(ClientboundPackets1_9_3.EFFECT, new PacketHandlers(this){
            final Protocol1_11To1_10 this$0;
            {
                this.this$0 = protocol1_11To1_10;
            }

            @Override
            public void register() {
                this.map(Type.INT);
                this.map(Type.POSITION);
                this.map(Type.INT);
                this.map(Type.BOOLEAN);
                this.handler(11::lambda$register$0);
            }

            private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                int n = packetWrapper.get(Type.INT, 0);
                if (n == 2002) {
                    int n2 = packetWrapper.get(Type.INT, 1);
                    boolean bl = false;
                    Pair<Integer, Boolean> pair = PotionColorMapping.getNewData(n2);
                    if (pair == null) {
                        Via.getPlatform().getLogger().warning("Received unknown 1.11 -> 1.10.2 potion data (" + n2 + ")");
                        n2 = 0;
                    } else {
                        n2 = pair.key();
                        bl = pair.value();
                    }
                    if (bl) {
                        packetWrapper.set(Type.INT, 0, 2007);
                    }
                    packetWrapper.set(Type.INT, 1, n2);
                }
            }
        });
        this.registerServerbound(ServerboundPackets1_9_3.PLAYER_BLOCK_PLACEMENT, new PacketHandlers(this){
            final Protocol1_11To1_10 this$0;
            {
                this.this$0 = protocol1_11To1_10;
            }

            @Override
            public void register() {
                this.map(Type.POSITION);
                this.map(Type.VAR_INT);
                this.map(Type.VAR_INT);
                this.map(Type.FLOAT, Protocol1_11To1_10.access$100());
                this.map(Type.FLOAT, Protocol1_11To1_10.access$100());
                this.map(Type.FLOAT, Protocol1_11To1_10.access$100());
            }
        });
        this.registerServerbound(ServerboundPackets1_9_3.CHAT_MESSAGE, new PacketHandlers(this){
            final Protocol1_11To1_10 this$0;
            {
                this.this$0 = protocol1_11To1_10;
            }

            @Override
            public void register() {
                this.map(Type.STRING);
                this.handler(13::lambda$register$0);
            }

            private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                String string = packetWrapper.get(Type.STRING, 0);
                if (string.length() > 100) {
                    packetWrapper.set(Type.STRING, 0, string.substring(0, 100));
                }
            }
        });
    }

    private int getNewSoundId(int n) {
        if (n == 196) {
            return 1;
        }
        if (n >= 85) {
            n += 2;
        }
        if (n >= 176) {
            ++n;
        }
        if (n >= 197) {
            n += 8;
        }
        if (n >= 207) {
            --n;
        }
        if (n >= 279) {
            n += 9;
        }
        if (n >= 296) {
            ++n;
        }
        if (n >= 390) {
            n += 4;
        }
        if (n >= 400) {
            n += 3;
        }
        if (n >= 450) {
            ++n;
        }
        if (n >= 455) {
            ++n;
        }
        if (n >= 470) {
            ++n;
        }
        return n;
    }

    @Override
    public void init(UserConnection userConnection) {
        userConnection.addEntityTracker(this.getClass(), new EntityTracker1_11(userConnection));
        if (!userConnection.has(ClientWorld.class)) {
            userConnection.put(new ClientWorld(userConnection));
        }
    }

    public MetadataRewriter1_11To1_10 getEntityRewriter() {
        return this.entityRewriter;
    }

    public InventoryPackets getItemRewriter() {
        return this.itemRewriter;
    }

    @Override
    public ItemRewriter getItemRewriter() {
        return this.getItemRewriter();
    }

    @Override
    public EntityRewriter getEntityRewriter() {
        return this.getEntityRewriter();
    }

    private static void lambda$registerPackets$0(PacketWrapper packetWrapper) throws Exception {
        ClientWorld clientWorld = packetWrapper.user().get(ClientWorld.class);
        Chunk chunk = packetWrapper.passthrough(new Chunk1_9_3_4Type(clientWorld));
        if (chunk.getBlockEntities() == null) {
            return;
        }
        for (CompoundTag compoundTag : chunk.getBlockEntities()) {
            if (!compoundTag.contains("id")) continue;
            String string = ((StringTag)compoundTag.get("id")).getValue();
            if (string.equals("MobSpawner")) {
                EntityIdRewriter.toClientSpawner(compoundTag);
            }
            ((StringTag)compoundTag.get("id")).setValue(BlockEntityRewriter.toNewIdentifier(string));
        }
    }

    static MetadataRewriter1_11To1_10 access$000(Protocol1_11To1_10 protocol1_11To1_10) {
        return protocol1_11To1_10.entityRewriter;
    }

    static ValueTransformer access$100() {
        return toOldByte;
    }
}

