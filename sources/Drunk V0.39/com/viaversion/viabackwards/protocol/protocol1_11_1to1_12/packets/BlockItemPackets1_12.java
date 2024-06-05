/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viabackwards.protocol.protocol1_11_1to1_12.packets;

import com.viaversion.viabackwards.api.rewriters.LegacyBlockItemRewriter;
import com.viaversion.viabackwards.protocol.protocol1_11_1to1_12.Protocol1_11_1To1_12;
import com.viaversion.viabackwards.protocol.protocol1_11_1to1_12.data.MapColorMapping;
import com.viaversion.viaversion.api.minecraft.BlockChangeRecord;
import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.IntArrayTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.LongArrayTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
import com.viaversion.viaversion.protocols.protocol1_12to1_11_1.ClientboundPackets1_12;
import com.viaversion.viaversion.protocols.protocol1_12to1_11_1.ServerboundPackets1_12;
import com.viaversion.viaversion.protocols.protocol1_9_1_2to1_9_3_4.types.Chunk1_9_3_4Type;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.ServerboundPackets1_9_3;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.storage.ClientWorld;
import java.util.Iterator;
import java.util.Map;
import org.checkerframework.checker.nullness.qual.Nullable;

public class BlockItemPackets1_12
extends LegacyBlockItemRewriter<Protocol1_11_1To1_12> {
    public BlockItemPackets1_12(Protocol1_11_1To1_12 protocol) {
        super(protocol);
    }

    @Override
    protected void registerPackets() {
        ((Protocol1_11_1To1_12)this.protocol).registerClientbound(ClientboundPackets1_12.MAP_DATA, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.map(Type.BYTE);
                this.map(Type.BOOLEAN);
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper wrapper) throws Exception {
                        int count = wrapper.passthrough(Type.VAR_INT);
                        int i = 0;
                        while (i < count * 3) {
                            wrapper.passthrough(Type.BYTE);
                            ++i;
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
                        int i = 0;
                        while (true) {
                            if (i >= data.length) {
                                wrapper.write(Type.BYTE_ARRAY_PRIMITIVE, data);
                                return;
                            }
                            short color = (short)(data[i] & 0xFF);
                            if (color > 143) {
                                color = (short)MapColorMapping.getNearestOldColor(color);
                                data[i] = (byte)color;
                            }
                            ++i;
                        }
                    }
                });
            }
        });
        this.registerSetSlot(ClientboundPackets1_12.SET_SLOT, Type.ITEM);
        this.registerWindowItems(ClientboundPackets1_12.WINDOW_ITEMS, Type.ITEM_ARRAY);
        this.registerEntityEquipment(ClientboundPackets1_12.ENTITY_EQUIPMENT, Type.ITEM);
        ((Protocol1_11_1To1_12)this.protocol).registerClientbound(ClientboundPackets1_12.PLUGIN_MESSAGE, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.STRING);
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper wrapper) throws Exception {
                        if (!wrapper.get(Type.STRING, 0).equalsIgnoreCase("MC|TrList")) return;
                        wrapper.passthrough(Type.INT);
                        int size = wrapper.passthrough(Type.UNSIGNED_BYTE).shortValue();
                        int i = 0;
                        while (i < size) {
                            wrapper.write(Type.ITEM, BlockItemPackets1_12.this.handleItemToClient(wrapper.read(Type.ITEM)));
                            wrapper.write(Type.ITEM, BlockItemPackets1_12.this.handleItemToClient(wrapper.read(Type.ITEM)));
                            boolean secondItem = wrapper.passthrough(Type.BOOLEAN);
                            if (secondItem) {
                                wrapper.write(Type.ITEM, BlockItemPackets1_12.this.handleItemToClient(wrapper.read(Type.ITEM)));
                            }
                            wrapper.passthrough(Type.BOOLEAN);
                            wrapper.passthrough(Type.INT);
                            wrapper.passthrough(Type.INT);
                            ++i;
                        }
                    }
                });
            }
        });
        ((Protocol1_11_1To1_12)this.protocol).registerServerbound(ServerboundPackets1_9_3.CLICK_WINDOW, new PacketRemapper(){

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
                            PacketWrapper confirm = wrapper.create(ServerboundPackets1_12.WINDOW_CONFIRMATION);
                            confirm.write(Type.UNSIGNED_BYTE, wrapper.get(Type.UNSIGNED_BYTE, 0));
                            confirm.write(Type.SHORT, wrapper.get(Type.SHORT, 1));
                            confirm.write(Type.BOOLEAN, false);
                            wrapper.sendToServer(Protocol1_11_1To1_12.class);
                            wrapper.cancel();
                            confirm.sendToServer(Protocol1_11_1To1_12.class);
                            return;
                        }
                        Item item = wrapper.get(Type.ITEM, 0);
                        BlockItemPackets1_12.this.handleItemToServer(item);
                    }
                });
            }
        });
        this.registerCreativeInvAction(ServerboundPackets1_9_3.CREATIVE_INVENTORY_ACTION, Type.ITEM);
        ((Protocol1_11_1To1_12)this.protocol).registerClientbound(ClientboundPackets1_12.CHUNK_DATA, new PacketRemapper(){

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
        ((Protocol1_11_1To1_12)this.protocol).registerClientbound(ClientboundPackets1_12.BLOCK_CHANGE, new PacketRemapper(){

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
        ((Protocol1_11_1To1_12)this.protocol).registerClientbound(ClientboundPackets1_12.MULTI_BLOCK_CHANGE, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.INT);
                this.map(Type.INT);
                this.map(Type.BLOCK_CHANGE_RECORD_ARRAY);
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper wrapper) throws Exception {
                        BlockChangeRecord[] blockChangeRecordArray = wrapper.get(Type.BLOCK_CHANGE_RECORD_ARRAY, 0);
                        int n = blockChangeRecordArray.length;
                        int n2 = 0;
                        while (n2 < n) {
                            BlockChangeRecord record = blockChangeRecordArray[n2];
                            record.setBlockId(BlockItemPackets1_12.this.handleBlockID(record.getBlockId()));
                            ++n2;
                        }
                    }
                });
            }
        });
        ((Protocol1_11_1To1_12)this.protocol).registerClientbound(ClientboundPackets1_12.BLOCK_ENTITY_DATA, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.POSITION);
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.NBT);
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper wrapper) throws Exception {
                        if (wrapper.get(Type.UNSIGNED_BYTE, 0) != 11) return;
                        wrapper.cancel();
                    }
                });
            }
        });
        ((Protocol1_11_1To1_12)this.protocol).getEntityRewriter().filter().handler((event, meta) -> {
            if (!meta.metaType().type().equals(Type.ITEM)) return;
            meta.setValue(this.handleItemToClient((Item)meta.getValue()));
        });
        ((Protocol1_11_1To1_12)this.protocol).registerServerbound(ServerboundPackets1_9_3.CLIENT_STATUS, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper wrapper) throws Exception {
                        if (wrapper.get(Type.VAR_INT, 0) != 2) return;
                        wrapper.cancel();
                    }
                });
            }
        });
    }

    @Override
    public @Nullable Item handleItemToClient(Item item) {
        if (item == null) {
            return null;
        }
        super.handleItemToClient(item);
        if (item.tag() == null) return item;
        CompoundTag backupTag = new CompoundTag();
        if (!this.handleNbtToClient(item.tag(), backupTag)) return item;
        item.tag().put("Via|LongArrayTags", backupTag);
        return item;
    }

    private boolean handleNbtToClient(CompoundTag compoundTag, CompoundTag backupTag) {
        Iterator<Map.Entry<String, Tag>> iterator = compoundTag.iterator();
        boolean hasLongArrayTag = false;
        while (iterator.hasNext()) {
            Map.Entry<String, Tag> entry = iterator.next();
            if (entry.getValue() instanceof CompoundTag) {
                CompoundTag nestedBackupTag = new CompoundTag();
                backupTag.put(entry.getKey(), nestedBackupTag);
                hasLongArrayTag |= this.handleNbtToClient((CompoundTag)entry.getValue(), nestedBackupTag);
                continue;
            }
            if (!(entry.getValue() instanceof LongArrayTag)) continue;
            backupTag.put(entry.getKey(), this.fromLongArrayTag((LongArrayTag)entry.getValue()));
            iterator.remove();
            hasLongArrayTag = true;
        }
        return hasLongArrayTag;
    }

    @Override
    public @Nullable Item handleItemToServer(Item item) {
        if (item == null) {
            return null;
        }
        super.handleItemToServer(item);
        if (item.tag() == null) return item;
        Object tag = item.tag().remove("Via|LongArrayTags");
        if (!(tag instanceof CompoundTag)) return item;
        this.handleNbtToServer(item.tag(), (CompoundTag)tag);
        return item;
    }

    private void handleNbtToServer(CompoundTag compoundTag, CompoundTag backupTag) {
        Iterator<Map.Entry<String, Tag>> iterator = backupTag.iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Tag> entry = iterator.next();
            if (entry.getValue() instanceof CompoundTag) {
                CompoundTag nestedTag = (CompoundTag)compoundTag.get(entry.getKey());
                this.handleNbtToServer(nestedTag, (CompoundTag)entry.getValue());
                continue;
            }
            compoundTag.put(entry.getKey(), this.fromIntArrayTag((IntArrayTag)entry.getValue()));
        }
    }

    private IntArrayTag fromLongArrayTag(LongArrayTag tag) {
        int[] intArray = new int[tag.length() * 2];
        long[] longArray = tag.getValue();
        int i = 0;
        long[] lArray = longArray;
        int n = lArray.length;
        int n2 = 0;
        while (n2 < n) {
            long l = lArray[n2];
            intArray[i++] = (int)(l >> 32);
            intArray[i++] = (int)l;
            ++n2;
        }
        return new IntArrayTag(intArray);
    }

    private LongArrayTag fromIntArrayTag(IntArrayTag tag) {
        long[] longArray = new long[tag.length() / 2];
        int[] intArray = tag.getValue();
        int i = 0;
        int j = 0;
        while (i < intArray.length) {
            longArray[j] = (long)intArray[i] << 32 | (long)intArray[i + 1] & 0xFFFFFFFFL;
            i += 2;
            ++j;
        }
        return new LongArrayTag(longArray);
    }
}

