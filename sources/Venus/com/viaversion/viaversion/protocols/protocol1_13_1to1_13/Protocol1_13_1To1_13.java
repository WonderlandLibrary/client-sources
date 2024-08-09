/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.protocols.protocol1_13_1to1_13;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.data.MappingData;
import com.viaversion.viaversion.api.data.MappingDataBase;
import com.viaversion.viaversion.api.minecraft.RegistryType;
import com.viaversion.viaversion.api.minecraft.entities.Entity1_13Types;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.AbstractProtocol;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.protocol.remapper.ValueTransformer;
import com.viaversion.viaversion.api.rewriter.EntityRewriter;
import com.viaversion.viaversion.api.rewriter.ItemRewriter;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.data.entity.EntityTrackerBase;
import com.viaversion.viaversion.protocols.protocol1_13_1to1_13.metadata.MetadataRewriter1_13_1To1_13;
import com.viaversion.viaversion.protocols.protocol1_13_1to1_13.packets.EntityPackets;
import com.viaversion.viaversion.protocols.protocol1_13_1to1_13.packets.InventoryPackets;
import com.viaversion.viaversion.protocols.protocol1_13_1to1_13.packets.WorldPackets;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ClientboundPackets1_13;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ServerboundPackets1_13;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.storage.ClientWorld;
import com.viaversion.viaversion.rewriter.StatisticsRewriter;
import com.viaversion.viaversion.rewriter.TagRewriter;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class Protocol1_13_1To1_13
extends AbstractProtocol<ClientboundPackets1_13, ClientboundPackets1_13, ServerboundPackets1_13, ServerboundPackets1_13> {
    public static final MappingData MAPPINGS = new MappingDataBase("1.13", "1.13.2");
    private final MetadataRewriter1_13_1To1_13 entityRewriter = new MetadataRewriter1_13_1To1_13(this);
    private final InventoryPackets itemRewriter = new InventoryPackets(this);

    public Protocol1_13_1To1_13() {
        super(ClientboundPackets1_13.class, ClientboundPackets1_13.class, ServerboundPackets1_13.class, ServerboundPackets1_13.class);
    }

    @Override
    protected void registerPackets() {
        this.entityRewriter.register();
        this.itemRewriter.register();
        EntityPackets.register(this);
        WorldPackets.register(this);
        this.registerServerbound(ServerboundPackets1_13.TAB_COMPLETE, new PacketHandlers(this){
            final Protocol1_13_1To1_13 this$0;
            {
                this.this$0 = protocol1_13_1To1_13;
            }

            @Override
            public void register() {
                this.map(Type.VAR_INT);
                this.map(Type.STRING, new ValueTransformer<String, String>(this, Type.STRING){
                    final 1 this$1;
                    {
                        this.this$1 = var1_1;
                        super(type);
                    }

                    @Override
                    public String transform(PacketWrapper packetWrapper, String string) {
                        return string.startsWith("/") ? string.substring(1) : string;
                    }

                    @Override
                    public Object transform(PacketWrapper packetWrapper, Object object) throws Exception {
                        return this.transform(packetWrapper, (String)object);
                    }
                });
            }
        });
        this.registerServerbound(ServerboundPackets1_13.EDIT_BOOK, new PacketHandlers(this){
            final Protocol1_13_1To1_13 this$0;
            {
                this.this$0 = protocol1_13_1To1_13;
            }

            @Override
            public void register() {
                this.map(Type.FLAT_ITEM);
                this.map(Type.BOOLEAN);
                this.handler(this::lambda$register$0);
                this.handler(2::lambda$register$1);
            }

            private static void lambda$register$1(PacketWrapper packetWrapper) throws Exception {
                int n = packetWrapper.read(Type.VAR_INT);
                if (n == 1) {
                    packetWrapper.cancel();
                }
            }

            private void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                Item item = packetWrapper.get(Type.FLAT_ITEM, 0);
                Protocol1_13_1To1_13.access$000(this.this$0).handleItemToServer(item);
            }
        });
        this.registerClientbound(ClientboundPackets1_13.TAB_COMPLETE, new PacketHandlers(this){
            final Protocol1_13_1To1_13 this$0;
            {
                this.this$0 = protocol1_13_1To1_13;
            }

            @Override
            public void register() {
                this.map(Type.VAR_INT);
                this.map(Type.VAR_INT);
                this.map(Type.VAR_INT);
                this.map(Type.VAR_INT);
                this.handler(3::lambda$register$0);
            }

            private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                int n = packetWrapper.get(Type.VAR_INT, 1);
                packetWrapper.set(Type.VAR_INT, 1, n + 1);
                int n2 = packetWrapper.get(Type.VAR_INT, 3);
                for (int i = 0; i < n2; ++i) {
                    packetWrapper.passthrough(Type.STRING);
                    boolean bl = packetWrapper.passthrough(Type.BOOLEAN);
                    if (!bl) continue;
                    packetWrapper.passthrough(Type.STRING);
                }
            }
        });
        this.registerClientbound(ClientboundPackets1_13.BOSSBAR, new PacketHandlers(this){
            final Protocol1_13_1To1_13 this$0;
            {
                this.this$0 = protocol1_13_1To1_13;
            }

            @Override
            public void register() {
                this.map(Type.UUID);
                this.map(Type.VAR_INT);
                this.handler(4::lambda$register$0);
            }

            private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                int n = packetWrapper.get(Type.VAR_INT, 0);
                if (n == 0) {
                    packetWrapper.passthrough(Type.COMPONENT);
                    packetWrapper.passthrough(Type.FLOAT);
                    packetWrapper.passthrough(Type.VAR_INT);
                    packetWrapper.passthrough(Type.VAR_INT);
                    short s = packetWrapper.read(Type.BYTE).byteValue();
                    if ((s & 2) != 0) {
                        s = (short)(s | 4);
                    }
                    packetWrapper.write(Type.UNSIGNED_BYTE, s);
                }
            }
        });
        new TagRewriter<ClientboundPackets1_13>(this).register(ClientboundPackets1_13.TAGS, RegistryType.ITEM);
        new StatisticsRewriter<ClientboundPackets1_13>(this).register(ClientboundPackets1_13.STATISTICS);
    }

    @Override
    public void init(UserConnection userConnection) {
        userConnection.addEntityTracker(this.getClass(), new EntityTrackerBase(userConnection, Entity1_13Types.EntityType.PLAYER));
        if (!userConnection.has(ClientWorld.class)) {
            userConnection.put(new ClientWorld(userConnection));
        }
    }

    @Override
    public MappingData getMappingData() {
        return MAPPINGS;
    }

    public MetadataRewriter1_13_1To1_13 getEntityRewriter() {
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

    static InventoryPackets access$000(Protocol1_13_1To1_13 protocol1_13_1To1_13) {
        return protocol1_13_1To1_13.itemRewriter;
    }
}

