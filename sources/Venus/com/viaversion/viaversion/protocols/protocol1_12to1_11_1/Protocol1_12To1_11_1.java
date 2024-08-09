/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.protocols.protocol1_12to1_11_1;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
import com.viaversion.viaversion.api.minecraft.chunks.ChunkSection;
import com.viaversion.viaversion.api.minecraft.chunks.DataPalette;
import com.viaversion.viaversion.api.minecraft.chunks.PaletteType;
import com.viaversion.viaversion.api.minecraft.entities.Entity1_12Types;
import com.viaversion.viaversion.api.platform.providers.ViaProviders;
import com.viaversion.viaversion.api.protocol.AbstractProtocol;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import com.viaversion.viaversion.api.rewriter.EntityRewriter;
import com.viaversion.viaversion.api.rewriter.ItemRewriter;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.version.Types1_12;
import com.viaversion.viaversion.data.entity.EntityTrackerBase;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.IntTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
import com.viaversion.viaversion.protocols.protocol1_12to1_11_1.ChatItemRewriter;
import com.viaversion.viaversion.protocols.protocol1_12to1_11_1.ClientboundPackets1_12;
import com.viaversion.viaversion.protocols.protocol1_12to1_11_1.ServerboundPackets1_12;
import com.viaversion.viaversion.protocols.protocol1_12to1_11_1.TranslateRewriter;
import com.viaversion.viaversion.protocols.protocol1_12to1_11_1.metadata.MetadataRewriter1_12To1_11_1;
import com.viaversion.viaversion.protocols.protocol1_12to1_11_1.packets.InventoryPackets;
import com.viaversion.viaversion.protocols.protocol1_12to1_11_1.providers.InventoryQuickMoveProvider;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ClientboundPackets1_13;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.Protocol1_13To1_12_2;
import com.viaversion.viaversion.protocols.protocol1_9_1_2to1_9_3_4.types.Chunk1_9_3_4Type;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.ClientboundPackets1_9_3;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.ServerboundPackets1_9_3;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.storage.ClientWorld;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.Protocol1_9To1_8;
import com.viaversion.viaversion.rewriter.SoundRewriter;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class Protocol1_12To1_11_1
extends AbstractProtocol<ClientboundPackets1_9_3, ClientboundPackets1_12, ServerboundPackets1_9_3, ServerboundPackets1_12> {
    private final MetadataRewriter1_12To1_11_1 metadataRewriter = new MetadataRewriter1_12To1_11_1(this);
    private final InventoryPackets itemRewriter = new InventoryPackets(this);

    public Protocol1_12To1_11_1() {
        super(ClientboundPackets1_9_3.class, ClientboundPackets1_12.class, ServerboundPackets1_9_3.class, ServerboundPackets1_12.class);
    }

    @Override
    protected void registerPackets() {
        this.metadataRewriter.register();
        this.itemRewriter.register();
        this.registerClientbound(ClientboundPackets1_9_3.SPAWN_ENTITY, new PacketHandlers(this){
            final Protocol1_12To1_11_1 this$0;
            {
                this.this$0 = protocol1_12To1_11_1;
            }

            @Override
            public void register() {
                this.map(Type.VAR_INT);
                this.map(Type.UUID);
                this.map(Type.BYTE);
                this.handler(Protocol1_12To1_11_1.access$000(this.this$0).objectTrackerHandler());
            }
        });
        this.registerClientbound(ClientboundPackets1_9_3.SPAWN_MOB, new PacketHandlers(this){
            final Protocol1_12To1_11_1 this$0;
            {
                this.this$0 = protocol1_12To1_11_1;
            }

            @Override
            public void register() {
                this.map(Type.VAR_INT);
                this.map(Type.UUID);
                this.map(Type.VAR_INT);
                this.map(Type.DOUBLE);
                this.map(Type.DOUBLE);
                this.map(Type.DOUBLE);
                this.map(Type.BYTE);
                this.map(Type.BYTE);
                this.map(Type.BYTE);
                this.map(Type.SHORT);
                this.map(Type.SHORT);
                this.map(Type.SHORT);
                this.map(Types1_12.METADATA_LIST);
                this.handler(Protocol1_12To1_11_1.access$000(this.this$0).trackerAndRewriterHandler(Types1_12.METADATA_LIST));
            }
        });
        this.registerClientbound(ClientboundPackets1_9_3.CHAT_MESSAGE, Protocol1_12To1_11_1::lambda$registerPackets$0);
        this.registerClientbound(ClientboundPackets1_9_3.CHUNK_DATA, Protocol1_12To1_11_1::lambda$registerPackets$1);
        this.metadataRewriter.registerRemoveEntities(ClientboundPackets1_9_3.DESTROY_ENTITIES);
        this.metadataRewriter.registerMetadataRewriter(ClientboundPackets1_9_3.ENTITY_METADATA, Types1_12.METADATA_LIST);
        this.registerClientbound(ClientboundPackets1_9_3.JOIN_GAME, new PacketHandlers(this){
            final Protocol1_12To1_11_1 this$0;
            {
                this.this$0 = protocol1_12To1_11_1;
            }

            @Override
            public void register() {
                this.map(Type.INT);
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.INT);
                this.handler(3::lambda$register$1);
            }

            private static void lambda$register$1(PacketWrapper packetWrapper) throws Exception {
                UserConnection userConnection = packetWrapper.user();
                ClientWorld clientWorld = userConnection.get(ClientWorld.class);
                int n = packetWrapper.get(Type.INT, 1);
                clientWorld.setEnvironment(n);
                if (userConnection.getProtocolInfo().getProtocolVersion() >= ProtocolVersion.v1_13.getVersion()) {
                    packetWrapper.create(ClientboundPackets1_13.DECLARE_RECIPES, 3::lambda$null$0).scheduleSend(Protocol1_13To1_12_2.class);
                }
            }

            private static void lambda$null$0(PacketWrapper packetWrapper) throws Exception {
                packetWrapper.write(Type.VAR_INT, 0);
            }
        });
        this.registerClientbound(ClientboundPackets1_9_3.RESPAWN, new PacketHandlers(this){
            final Protocol1_12To1_11_1 this$0;
            {
                this.this$0 = protocol1_12To1_11_1;
            }

            @Override
            public void register() {
                this.map(Type.INT);
                this.handler(4::lambda$register$0);
            }

            private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                ClientWorld clientWorld = packetWrapper.user().get(ClientWorld.class);
                int n = packetWrapper.get(Type.INT, 0);
                clientWorld.setEnvironment(n);
            }
        });
        new SoundRewriter<ClientboundPackets1_9_3>(this, this::getNewSoundId).registerSound(ClientboundPackets1_9_3.SOUND);
        this.cancelServerbound(ServerboundPackets1_12.PREPARE_CRAFTING_GRID);
        this.registerServerbound(ServerboundPackets1_12.CLIENT_SETTINGS, new PacketHandlers(this){
            final Protocol1_12To1_11_1 this$0;
            {
                this.this$0 = protocol1_12To1_11_1;
            }

            @Override
            public void register() {
                this.map(Type.STRING);
                this.map(Type.BYTE);
                this.map(Type.VAR_INT);
                this.map(Type.BOOLEAN);
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.VAR_INT);
                this.handler(5::lambda$register$0);
            }

            private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                String string = packetWrapper.get(Type.STRING, 0);
                if (string.length() > 7) {
                    packetWrapper.set(Type.STRING, 0, string.substring(0, 7));
                }
            }
        });
        this.cancelServerbound(ServerboundPackets1_12.RECIPE_BOOK_DATA);
        this.cancelServerbound(ServerboundPackets1_12.ADVANCEMENT_TAB);
    }

    private int getNewSoundId(int n) {
        int n2 = n;
        if (n >= 26) {
            n2 += 2;
        }
        if (n >= 70) {
            n2 += 4;
        }
        if (n >= 74) {
            ++n2;
        }
        if (n >= 143) {
            n2 += 3;
        }
        if (n >= 185) {
            ++n2;
        }
        if (n >= 263) {
            n2 += 7;
        }
        if (n >= 301) {
            n2 += 33;
        }
        if (n >= 317) {
            n2 += 2;
        }
        if (n >= 491) {
            n2 += 3;
        }
        return n2;
    }

    @Override
    public void register(ViaProviders viaProviders) {
        viaProviders.register(InventoryQuickMoveProvider.class, new InventoryQuickMoveProvider());
    }

    @Override
    public void init(UserConnection userConnection) {
        userConnection.addEntityTracker(this.getClass(), new EntityTrackerBase(userConnection, Entity1_12Types.EntityType.PLAYER));
        if (!userConnection.has(ClientWorld.class)) {
            userConnection.put(new ClientWorld(userConnection));
        }
    }

    public MetadataRewriter1_12To1_11_1 getEntityRewriter() {
        return this.metadataRewriter;
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

    private static void lambda$registerPackets$1(PacketWrapper packetWrapper) throws Exception {
        ClientWorld clientWorld = packetWrapper.user().get(ClientWorld.class);
        Chunk1_9_3_4Type chunk1_9_3_4Type = new Chunk1_9_3_4Type(clientWorld);
        Chunk chunk = packetWrapper.passthrough(chunk1_9_3_4Type);
        for (int i = 0; i < chunk.getSections().length; ++i) {
            ChunkSection chunkSection = chunk.getSections()[i];
            if (chunkSection == null) continue;
            DataPalette dataPalette = chunkSection.palette(PaletteType.BLOCKS);
            for (int j = 0; j < 4096; ++j) {
                int n = dataPalette.idAt(j) >> 4;
                if (n != 26) continue;
                CompoundTag compoundTag = new CompoundTag();
                compoundTag.put("color", new IntTag(14));
                compoundTag.put("x", new IntTag(ChunkSection.xFromIndex(j) + (chunk.getX() << 4)));
                compoundTag.put("y", new IntTag(ChunkSection.yFromIndex(j) + (i << 4)));
                compoundTag.put("z", new IntTag(ChunkSection.zFromIndex(j) + (chunk.getZ() << 4)));
                compoundTag.put("id", new StringTag("minecraft:bed"));
                chunk.getBlockEntities().add(compoundTag);
            }
        }
    }

    private static void lambda$registerPackets$0(PacketWrapper packetWrapper) throws Exception {
        if (!Via.getConfig().is1_12NBTArrayFix()) {
            return;
        }
        try {
            JsonElement jsonElement = Protocol1_9To1_8.FIX_JSON.transform(null, packetWrapper.passthrough(Type.COMPONENT).toString());
            TranslateRewriter.toClient(jsonElement, packetWrapper.user());
            ChatItemRewriter.toClient(jsonElement, packetWrapper.user());
            packetWrapper.set(Type.COMPONENT, 0, jsonElement);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    static MetadataRewriter1_12To1_11_1 access$000(Protocol1_12To1_11_1 protocol1_12To1_11_1) {
        return protocol1_12To1_11_1.metadataRewriter;
    }
}

