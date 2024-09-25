/*
 * Decompiled with CFR 0.150.
 */
package nl.matsv.viabackwards.protocol.protocol1_11_1to1_12.packets;

import nl.matsv.viabackwards.api.rewriters.LegacyBlockItemRewriter;
import nl.matsv.viabackwards.protocol.protocol1_11_1to1_12.Protocol1_11_1To1_12;
import nl.matsv.viabackwards.protocol.protocol1_11_1to1_12.data.MapColorMapping;
import us.myles.ViaVersion.api.PacketWrapper;
import us.myles.ViaVersion.api.minecraft.BlockChangeRecord;
import us.myles.ViaVersion.api.minecraft.chunks.Chunk;
import us.myles.ViaVersion.api.minecraft.item.Item;
import us.myles.ViaVersion.api.minecraft.metadata.Metadata;
import us.myles.ViaVersion.api.remapper.PacketHandler;
import us.myles.ViaVersion.api.remapper.PacketRemapper;
import us.myles.ViaVersion.api.rewriters.ItemRewriter;
import us.myles.ViaVersion.api.type.Type;
import us.myles.ViaVersion.protocols.protocol1_12to1_11_1.ClientboundPackets1_12;
import us.myles.ViaVersion.protocols.protocol1_9_1_2to1_9_3_4.types.Chunk1_9_3_4Type;
import us.myles.ViaVersion.protocols.protocol1_9_3to1_9_1_2.ServerboundPackets1_9_3;
import us.myles.ViaVersion.protocols.protocol1_9_3to1_9_1_2.storage.ClientWorld;

public class BlockItemPackets1_12
extends LegacyBlockItemRewriter<Protocol1_11_1To1_12> {
    public BlockItemPackets1_12(Protocol1_11_1To1_12 protocol) {
        super(protocol);
    }

    @Override
    protected void registerPackets() {
        ((Protocol1_11_1To1_12)this.protocol).registerOutgoing(ClientboundPackets1_12.MAP_DATA, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.map(Type.BYTE);
                this.map(Type.BOOLEAN);
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper wrapper) throws Exception {
                        int count = wrapper.passthrough(Type.VAR_INT);
                        for (int i = 0; i < count * 3; ++i) {
                            wrapper.passthrough(Type.BYTE);
                        }
                    }
                });
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper wrapper) throws Exception {
                        short columns = wrapper.passthrough(Type.UNSIGNED_BYTE);
                        if (columns <= 0) {
                            return;
                        }
                        short rows = wrapper.passthrough(Type.UNSIGNED_BYTE);
                        wrapper.passthrough(Type.UNSIGNED_BYTE);
                        wrapper.passthrough(Type.UNSIGNED_BYTE);
                        byte[] data = wrapper.read(Type.BYTE_ARRAY_PRIMITIVE);
                        for (int i = 0; i < data.length; ++i) {
                            short color = (short)(data[i] & 0xFF);
                            if (color <= 143) continue;
                            color = (short)MapColorMapping.getNearestOldColor(color);
                            data[i] = (byte)color;
                        }
                        wrapper.write(Type.BYTE_ARRAY_PRIMITIVE, data);
                    }
                });
            }
        });
        ItemRewriter itemRewriter = new ItemRewriter(this.protocol, this::handleItemToClient, this::handleItemToServer);
        itemRewriter.registerSetSlot(ClientboundPackets1_12.SET_SLOT, Type.ITEM);
        itemRewriter.registerWindowItems(ClientboundPackets1_12.WINDOW_ITEMS, Type.ITEM_ARRAY);
        itemRewriter.registerEntityEquipment(ClientboundPackets1_12.ENTITY_EQUIPMENT, Type.ITEM);
        ((Protocol1_11_1To1_12)this.protocol).registerOutgoing(ClientboundPackets1_12.PLUGIN_MESSAGE, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.STRING);
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper wrapper) throws Exception {
                        if (wrapper.get(Type.STRING, 0).equalsIgnoreCase("MC|TrList")) {
                            wrapper.passthrough(Type.INT);
                            int size = wrapper.passthrough(Type.UNSIGNED_BYTE).shortValue();
                            for (int i = 0; i < size; ++i) {
                                wrapper.write(Type.ITEM, BlockItemPackets1_12.this.handleItemToClient(wrapper.read(Type.ITEM)));
                                wrapper.write(Type.ITEM, BlockItemPackets1_12.this.handleItemToClient(wrapper.read(Type.ITEM)));
                                boolean secondItem = wrapper.passthrough(Type.BOOLEAN);
                                if (secondItem) {
                                    wrapper.write(Type.ITEM, BlockItemPackets1_12.this.handleItemToClient(wrapper.read(Type.ITEM)));
                                }
                                wrapper.passthrough(Type.BOOLEAN);
                                wrapper.passthrough(Type.INT);
                                wrapper.passthrough(Type.INT);
                            }
                        }
                    }
                });
            }
        });
        ((Protocol1_11_1To1_12)this.protocol).registerIncoming(ServerboundPackets1_9_3.CLICK_WINDOW, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.SHORT);
                this.map(Type.BYTE);
                this.map(Type.SHORT);
                this.map(Type.VAR_INT);
                this.map(Type.ITEM);
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper wrapper) throws Exception {
                        if (wrapper.get(Type.VAR_INT, 0) == 1) {
                            wrapper.set(Type.ITEM, 0, null);
                            PacketWrapper confirm = wrapper.create(6);
                            confirm.write(Type.BYTE, wrapper.get(Type.UNSIGNED_BYTE, 0).byteValue());
                            confirm.write(Type.SHORT, wrapper.get(Type.SHORT, 1));
                            confirm.write(Type.BOOLEAN, false);
                            wrapper.sendToServer(Protocol1_11_1To1_12.class, true, true);
                            wrapper.cancel();
                            confirm.sendToServer(Protocol1_11_1To1_12.class, true, true);
                            return;
                        }
                        Item item = wrapper.get(Type.ITEM, 0);
                        BlockItemPackets1_12.this.handleItemToServer(item);
                    }
                });
            }
        });
        itemRewriter.registerCreativeInvAction(ServerboundPackets1_9_3.CREATIVE_INVENTORY_ACTION, Type.ITEM);
        ((Protocol1_11_1To1_12)this.protocol).registerOutgoing(ClientboundPackets1_12.CHUNK_DATA, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper wrapper) throws Exception {
                        ClientWorld clientWorld = wrapper.user().get(ClientWorld.class);
                        Chunk1_9_3_4Type type = new Chunk1_9_3_4Type(clientWorld);
                        Chunk chunk = wrapper.passthrough(type);
                        BlockItemPackets1_12.this.handleChunk(chunk);
                    }
                });
            }
        });
        ((Protocol1_11_1To1_12)this.protocol).registerOutgoing(ClientboundPackets1_12.BLOCK_CHANGE, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.POSITION);
                this.map(Type.VAR_INT);
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper wrapper) throws Exception {
                        int idx = wrapper.get(Type.VAR_INT, 0);
                        wrapper.set(Type.VAR_INT, 0, BlockItemPackets1_12.this.handleBlockID(idx));
                    }
                });
            }
        });
        ((Protocol1_11_1To1_12)this.protocol).registerOutgoing(ClientboundPackets1_12.MULTI_BLOCK_CHANGE, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.INT);
                this.map(Type.INT);
                this.map(Type.BLOCK_CHANGE_RECORD_ARRAY);
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper wrapper) throws Exception {
                        for (BlockChangeRecord record : wrapper.get(Type.BLOCK_CHANGE_RECORD_ARRAY, 0)) {
                            record.setBlockId(BlockItemPackets1_12.this.handleBlockID(record.getBlockId()));
                        }
                    }
                });
            }
        });
        ((Protocol1_11_1To1_12)this.protocol).registerOutgoing(ClientboundPackets1_12.BLOCK_ENTITY_DATA, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.POSITION);
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.NBT);
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper wrapper) throws Exception {
                        if (wrapper.get(Type.UNSIGNED_BYTE, 0) == 11) {
                            wrapper.cancel();
                        }
                    }
                });
            }
        });
        ((Protocol1_11_1To1_12)this.protocol).getEntityPackets().registerMetaHandler().handle(e -> {
            Metadata data = e.getData();
            if (data.getMetaType().getType().equals(Type.ITEM)) {
                data.setValue(this.handleItemToClient((Item)data.getValue()));
            }
            return data;
        });
        ((Protocol1_11_1To1_12)this.protocol).registerIncoming(ServerboundPackets1_9_3.CLIENT_STATUS, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper wrapper) throws Exception {
                        if (wrapper.get(Type.VAR_INT, 0) == 2) {
                            wrapper.cancel();
                        }
                    }
                });
            }
        });
    }
}

