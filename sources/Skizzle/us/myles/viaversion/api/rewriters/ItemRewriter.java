/*
 * Decompiled with CFR 0.150.
 */
package us.myles.ViaVersion.api.rewriters;

import us.myles.ViaVersion.api.data.ParticleMappings;
import us.myles.ViaVersion.api.minecraft.item.Item;
import us.myles.ViaVersion.api.protocol.ClientboundPacketType;
import us.myles.ViaVersion.api.protocol.Protocol;
import us.myles.ViaVersion.api.protocol.ServerboundPacketType;
import us.myles.ViaVersion.api.remapper.PacketHandler;
import us.myles.ViaVersion.api.remapper.PacketRemapper;
import us.myles.ViaVersion.api.type.Type;

public class ItemRewriter {
    private final Protocol protocol;
    private final RewriteFunction toClient;
    private final RewriteFunction toServer;

    public ItemRewriter(Protocol protocol, RewriteFunction toClient, RewriteFunction toServer) {
        this.protocol = protocol;
        this.toClient = toClient;
        this.toServer = toServer;
    }

    public void registerWindowItems(ClientboundPacketType packetType, final Type<Item[]> type) {
        this.protocol.registerOutgoing(packetType, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.UNSIGNED_BYTE);
                this.map(type);
                this.handler(ItemRewriter.this.itemArrayHandler(type));
            }
        });
    }

    public void registerSetSlot(ClientboundPacketType packetType, final Type<Item> type) {
        this.protocol.registerOutgoing(packetType, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.BYTE);
                this.map(Type.SHORT);
                this.map(type);
                this.handler(ItemRewriter.this.itemToClientHandler(type));
            }
        });
    }

    public void registerEntityEquipment(ClientboundPacketType packetType, final Type<Item> type) {
        this.protocol.registerOutgoing(packetType, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.map(Type.VAR_INT);
                this.map(type);
                this.handler(ItemRewriter.this.itemToClientHandler(type));
            }
        });
    }

    public void registerEntityEquipmentArray(ClientboundPacketType packetType, final Type<Item> type) {
        this.protocol.registerOutgoing(packetType, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.handler(wrapper -> {
                    byte slot;
                    do {
                        slot = wrapper.passthrough(Type.BYTE);
                        ItemRewriter.this.toClient.rewrite((Item)wrapper.passthrough(type));
                    } while ((slot & 0xFFFFFF80) != 0);
                });
            }
        });
    }

    public void registerCreativeInvAction(ServerboundPacketType packetType, final Type<Item> type) {
        this.protocol.registerIncoming(packetType, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.SHORT);
                this.map(type);
                this.handler(ItemRewriter.this.itemToServerHandler(type));
            }
        });
    }

    public void registerClickWindow(ServerboundPacketType packetType, final Type<Item> type) {
        this.protocol.registerIncoming(packetType, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.SHORT);
                this.map(Type.BYTE);
                this.map(Type.SHORT);
                this.map(Type.VAR_INT);
                this.map(type);
                this.handler(ItemRewriter.this.itemToServerHandler(type));
            }
        });
    }

    public void registerSetCooldown(ClientboundPacketType packetType) {
        this.protocol.registerOutgoing(packetType, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.handler(wrapper -> {
                    int itemId = wrapper.read(Type.VAR_INT);
                    wrapper.write(Type.VAR_INT, ItemRewriter.this.protocol.getMappingData().getNewItemId(itemId));
                });
            }
        });
    }

    public void registerTradeList(ClientboundPacketType packetType, final Type<Item> type) {
        this.protocol.registerOutgoing(packetType, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.handler(wrapper -> {
                    wrapper.passthrough(Type.VAR_INT);
                    int size = wrapper.passthrough(Type.UNSIGNED_BYTE).shortValue();
                    for (int i = 0; i < size; ++i) {
                        ItemRewriter.this.toClient.rewrite((Item)wrapper.passthrough(type));
                        ItemRewriter.this.toClient.rewrite((Item)wrapper.passthrough(type));
                        if (wrapper.passthrough(Type.BOOLEAN).booleanValue()) {
                            ItemRewriter.this.toClient.rewrite((Item)wrapper.passthrough(type));
                        }
                        wrapper.passthrough(Type.BOOLEAN);
                        wrapper.passthrough(Type.INT);
                        wrapper.passthrough(Type.INT);
                        wrapper.passthrough(Type.INT);
                        wrapper.passthrough(Type.INT);
                        wrapper.passthrough(Type.FLOAT);
                        wrapper.passthrough(Type.INT);
                    }
                });
            }
        });
    }

    public void registerAdvancements(ClientboundPacketType packetType, final Type<Item> type) {
        this.protocol.registerOutgoing(packetType, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.handler(wrapper -> {
                    wrapper.passthrough(Type.BOOLEAN);
                    int size = wrapper.passthrough(Type.VAR_INT);
                    for (int i = 0; i < size; ++i) {
                        wrapper.passthrough(Type.STRING);
                        if (wrapper.passthrough(Type.BOOLEAN).booleanValue()) {
                            wrapper.passthrough(Type.STRING);
                        }
                        if (wrapper.passthrough(Type.BOOLEAN).booleanValue()) {
                            wrapper.passthrough(Type.COMPONENT);
                            wrapper.passthrough(Type.COMPONENT);
                            ItemRewriter.this.toClient.rewrite((Item)wrapper.passthrough(type));
                            wrapper.passthrough(Type.VAR_INT);
                            int flags = wrapper.passthrough(Type.INT);
                            if ((flags & 1) != 0) {
                                wrapper.passthrough(Type.STRING);
                            }
                            wrapper.passthrough(Type.FLOAT);
                            wrapper.passthrough(Type.FLOAT);
                        }
                        wrapper.passthrough(Type.STRING_ARRAY);
                        int arrayLength = wrapper.passthrough(Type.VAR_INT);
                        for (int array = 0; array < arrayLength; ++array) {
                            wrapper.passthrough(Type.STRING_ARRAY);
                        }
                    }
                });
            }
        });
    }

    public void registerSpawnParticle(ClientboundPacketType packetType, final Type<Item> itemType, final Type<?> coordType) {
        this.protocol.registerOutgoing(packetType, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.INT);
                this.map(Type.BOOLEAN);
                this.map(coordType);
                this.map(coordType);
                this.map(coordType);
                this.map(Type.FLOAT);
                this.map(Type.FLOAT);
                this.map(Type.FLOAT);
                this.map(Type.FLOAT);
                this.map(Type.INT);
                this.handler(wrapper -> {
                    int id = wrapper.get(Type.INT, 0);
                    if (id == -1) {
                        return;
                    }
                    ParticleMappings mappings = ItemRewriter.this.protocol.getMappingData().getParticleMappings();
                    if (id == mappings.getBlockId() || id == mappings.getFallingDustId()) {
                        int data = wrapper.passthrough(Type.VAR_INT);
                        wrapper.set(Type.VAR_INT, 0, ItemRewriter.this.protocol.getMappingData().getNewBlockStateId(data));
                    } else if (id == mappings.getItemId()) {
                        ItemRewriter.this.toClient.rewrite((Item)wrapper.passthrough(itemType));
                    }
                    int newId = ItemRewriter.this.protocol.getMappingData().getNewParticleId(id);
                    if (newId != id) {
                        wrapper.set(Type.INT, 0, newId);
                    }
                });
            }
        });
    }

    public PacketHandler itemArrayHandler(Type<Item[]> type) {
        return wrapper -> {
            Item[] items;
            for (Item item : items = (Item[])wrapper.get(type, 0)) {
                this.toClient.rewrite(item);
            }
        };
    }

    public PacketHandler itemToClientHandler(Type<Item> type) {
        return wrapper -> this.toClient.rewrite((Item)wrapper.get(type, 0));
    }

    public PacketHandler itemToServerHandler(Type<Item> type) {
        return wrapper -> this.toServer.rewrite((Item)wrapper.get(type, 0));
    }

    @FunctionalInterface
    public static interface RewriteFunction {
        public void rewrite(Item var1);
    }
}

