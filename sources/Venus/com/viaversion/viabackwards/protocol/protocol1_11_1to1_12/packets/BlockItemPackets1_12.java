/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viabackwards.protocol.protocol1_11_1to1_12.packets;

import com.viaversion.viabackwards.api.rewriters.LegacyBlockItemRewriter;
import com.viaversion.viabackwards.protocol.protocol1_11_1to1_12.Protocol1_11_1To1_12;
import com.viaversion.viabackwards.protocol.protocol1_11_1to1_12.data.MapColorMapping;
import com.viaversion.viaversion.api.minecraft.BlockChangeRecord;
import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
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
import com.viaversion.viaversion.rewriter.meta.MetaHandlerEvent;
import java.util.Iterator;
import java.util.Map;
import org.checkerframework.checker.nullness.qual.Nullable;

public class BlockItemPackets1_12
extends LegacyBlockItemRewriter<ClientboundPackets1_12, ServerboundPackets1_9_3, Protocol1_11_1To1_12> {
    public BlockItemPackets1_12(Protocol1_11_1To1_12 protocol1_11_1To1_12) {
        super(protocol1_11_1To1_12);
    }

    @Override
    protected void registerPackets() {
        ((Protocol1_11_1To1_12)this.protocol).registerClientbound(ClientboundPackets1_12.MAP_DATA, new PacketHandlers(this){
            final BlockItemPackets1_12 this$0;
            {
                this.this$0 = blockItemPackets1_12;
            }

            @Override
            public void register() {
                this.map(Type.VAR_INT);
                this.map(Type.BYTE);
                this.map(Type.BOOLEAN);
                this.handler(1::lambda$register$0);
                this.handler(1::lambda$register$1);
            }

            private static void lambda$register$1(PacketWrapper packetWrapper) throws Exception {
                short s = packetWrapper.passthrough(Type.UNSIGNED_BYTE);
                if (s <= 0) {
                    return;
                }
                packetWrapper.passthrough(Type.UNSIGNED_BYTE);
                packetWrapper.passthrough(Type.UNSIGNED_BYTE);
                packetWrapper.passthrough(Type.UNSIGNED_BYTE);
                byte[] byArray = packetWrapper.read(Type.BYTE_ARRAY_PRIMITIVE);
                for (int i = 0; i < byArray.length; ++i) {
                    short s2 = (short)(byArray[i] & 0xFF);
                    if (s2 <= 143) continue;
                    s2 = (short)MapColorMapping.getNearestOldColor(s2);
                    byArray[i] = (byte)s2;
                }
                packetWrapper.write(Type.BYTE_ARRAY_PRIMITIVE, byArray);
            }

            private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                int n = packetWrapper.passthrough(Type.VAR_INT);
                for (int i = 0; i < n * 3; ++i) {
                    packetWrapper.passthrough(Type.BYTE);
                }
            }
        });
        this.registerSetSlot(ClientboundPackets1_12.SET_SLOT, Type.ITEM);
        this.registerWindowItems(ClientboundPackets1_12.WINDOW_ITEMS, Type.ITEM_ARRAY);
        this.registerEntityEquipment(ClientboundPackets1_12.ENTITY_EQUIPMENT, Type.ITEM);
        ((Protocol1_11_1To1_12)this.protocol).registerClientbound(ClientboundPackets1_12.PLUGIN_MESSAGE, new PacketHandlers(this){
            final BlockItemPackets1_12 this$0;
            {
                this.this$0 = blockItemPackets1_12;
            }

            @Override
            public void register() {
                this.map(Type.STRING);
                this.handler(this::lambda$register$0);
            }

            private void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                if (packetWrapper.get(Type.STRING, 0).equalsIgnoreCase("MC|TrList")) {
                    packetWrapper.passthrough(Type.INT);
                    int n = packetWrapper.passthrough(Type.UNSIGNED_BYTE).shortValue();
                    for (int i = 0; i < n; ++i) {
                        packetWrapper.write(Type.ITEM, this.this$0.handleItemToClient(packetWrapper.read(Type.ITEM)));
                        packetWrapper.write(Type.ITEM, this.this$0.handleItemToClient(packetWrapper.read(Type.ITEM)));
                        boolean bl = packetWrapper.passthrough(Type.BOOLEAN);
                        if (bl) {
                            packetWrapper.write(Type.ITEM, this.this$0.handleItemToClient(packetWrapper.read(Type.ITEM)));
                        }
                        packetWrapper.passthrough(Type.BOOLEAN);
                        packetWrapper.passthrough(Type.INT);
                        packetWrapper.passthrough(Type.INT);
                    }
                }
            }
        });
        ((Protocol1_11_1To1_12)this.protocol).registerServerbound(ServerboundPackets1_9_3.CLICK_WINDOW, new PacketHandlers(this){
            final BlockItemPackets1_12 this$0;
            {
                this.this$0 = blockItemPackets1_12;
            }

            @Override
            public void register() {
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.SHORT);
                this.map(Type.BYTE);
                this.map(Type.SHORT);
                this.map(Type.VAR_INT);
                this.map(Type.ITEM);
                this.handler(this::lambda$register$0);
            }

            private void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                if (packetWrapper.get(Type.VAR_INT, 0) == 1) {
                    packetWrapper.set(Type.ITEM, 0, null);
                    PacketWrapper packetWrapper2 = packetWrapper.create(ServerboundPackets1_12.WINDOW_CONFIRMATION);
                    packetWrapper2.write(Type.UNSIGNED_BYTE, packetWrapper.get(Type.UNSIGNED_BYTE, 0));
                    packetWrapper2.write(Type.SHORT, packetWrapper.get(Type.SHORT, 1));
                    packetWrapper2.write(Type.BOOLEAN, false);
                    packetWrapper.sendToServer(Protocol1_11_1To1_12.class);
                    packetWrapper.cancel();
                    packetWrapper2.sendToServer(Protocol1_11_1To1_12.class);
                    return;
                }
                Item item = packetWrapper.get(Type.ITEM, 0);
                this.this$0.handleItemToServer(item);
            }
        });
        this.registerCreativeInvAction(ServerboundPackets1_9_3.CREATIVE_INVENTORY_ACTION, Type.ITEM);
        ((Protocol1_11_1To1_12)this.protocol).registerClientbound(ClientboundPackets1_12.CHUNK_DATA, this::lambda$registerPackets$0);
        ((Protocol1_11_1To1_12)this.protocol).registerClientbound(ClientboundPackets1_12.BLOCK_CHANGE, new PacketHandlers(this){
            final BlockItemPackets1_12 this$0;
            {
                this.this$0 = blockItemPackets1_12;
            }

            @Override
            public void register() {
                this.map(Type.POSITION);
                this.map(Type.VAR_INT);
                this.handler(this::lambda$register$0);
            }

            private void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                int n = packetWrapper.get(Type.VAR_INT, 0);
                packetWrapper.set(Type.VAR_INT, 0, this.this$0.handleBlockID(n));
            }
        });
        ((Protocol1_11_1To1_12)this.protocol).registerClientbound(ClientboundPackets1_12.MULTI_BLOCK_CHANGE, new PacketHandlers(this){
            final BlockItemPackets1_12 this$0;
            {
                this.this$0 = blockItemPackets1_12;
            }

            @Override
            public void register() {
                this.map(Type.INT);
                this.map(Type.INT);
                this.map(Type.BLOCK_CHANGE_RECORD_ARRAY);
                this.handler(this::lambda$register$0);
            }

            private void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                for (BlockChangeRecord blockChangeRecord : packetWrapper.get(Type.BLOCK_CHANGE_RECORD_ARRAY, 0)) {
                    blockChangeRecord.setBlockId(this.this$0.handleBlockID(blockChangeRecord.getBlockId()));
                }
            }
        });
        ((Protocol1_11_1To1_12)this.protocol).registerClientbound(ClientboundPackets1_12.BLOCK_ENTITY_DATA, new PacketHandlers(this){
            final BlockItemPackets1_12 this$0;
            {
                this.this$0 = blockItemPackets1_12;
            }

            @Override
            public void register() {
                this.map(Type.POSITION);
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.NBT);
                this.handler(6::lambda$register$0);
            }

            private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                if (packetWrapper.get(Type.UNSIGNED_BYTE, 0) == 11) {
                    packetWrapper.cancel();
                }
            }
        });
        ((Protocol1_11_1To1_12)this.protocol).getEntityRewriter().filter().handler(this::lambda$registerPackets$1);
        ((Protocol1_11_1To1_12)this.protocol).registerServerbound(ServerboundPackets1_9_3.CLIENT_STATUS, new PacketHandlers(this){
            final BlockItemPackets1_12 this$0;
            {
                this.this$0 = blockItemPackets1_12;
            }

            @Override
            public void register() {
                this.map(Type.VAR_INT);
                this.handler(7::lambda$register$0);
            }

            private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                if (packetWrapper.get(Type.VAR_INT, 0) == 2) {
                    packetWrapper.cancel();
                }
            }
        });
    }

    @Override
    public @Nullable Item handleItemToClient(Item item) {
        if (item == null) {
            return null;
        }
        super.handleItemToClient(item);
        if (item.tag() != null) {
            CompoundTag compoundTag = new CompoundTag();
            if (this.handleNbtToClient(item.tag(), compoundTag)) {
                item.tag().put("Via|LongArrayTags", compoundTag);
            }
        }
        return item;
    }

    private boolean handleNbtToClient(CompoundTag compoundTag, CompoundTag compoundTag2) {
        Iterator<Map.Entry<String, Tag>> iterator2 = compoundTag.iterator();
        boolean bl = false;
        while (iterator2.hasNext()) {
            Map.Entry<String, Tag> entry = iterator2.next();
            if (entry.getValue() instanceof CompoundTag) {
                CompoundTag compoundTag3 = new CompoundTag();
                compoundTag2.put(entry.getKey(), compoundTag3);
                bl |= this.handleNbtToClient((CompoundTag)entry.getValue(), compoundTag3);
                continue;
            }
            if (!(entry.getValue() instanceof LongArrayTag)) continue;
            compoundTag2.put(entry.getKey(), this.fromLongArrayTag((LongArrayTag)entry.getValue()));
            iterator2.remove();
            bl = true;
        }
        return bl;
    }

    @Override
    public @Nullable Item handleItemToServer(Item item) {
        Object t;
        if (item == null) {
            return null;
        }
        super.handleItemToServer(item);
        if (item.tag() != null && (t = item.tag().remove("Via|LongArrayTags")) instanceof CompoundTag) {
            this.handleNbtToServer(item.tag(), (CompoundTag)t);
        }
        return item;
    }

    private void handleNbtToServer(CompoundTag compoundTag, CompoundTag compoundTag2) {
        for (Map.Entry<String, Tag> entry : compoundTag2) {
            if (entry.getValue() instanceof CompoundTag) {
                CompoundTag compoundTag3 = (CompoundTag)compoundTag.get(entry.getKey());
                this.handleNbtToServer(compoundTag3, (CompoundTag)entry.getValue());
                continue;
            }
            compoundTag.put(entry.getKey(), this.fromIntArrayTag((IntArrayTag)entry.getValue()));
        }
    }

    private IntArrayTag fromLongArrayTag(LongArrayTag longArrayTag) {
        int[] nArray = new int[longArrayTag.length() * 2];
        long[] lArray = longArrayTag.getValue();
        int n = 0;
        for (long l : lArray) {
            nArray[n++] = (int)(l >> 32);
            nArray[n++] = (int)l;
        }
        return new IntArrayTag(nArray);
    }

    private LongArrayTag fromIntArrayTag(IntArrayTag intArrayTag) {
        long[] lArray = new long[intArrayTag.length() / 2];
        int[] nArray = intArrayTag.getValue();
        int n = 0;
        int n2 = 0;
        while (n < nArray.length) {
            lArray[n2] = (long)nArray[n] << 32 | (long)nArray[n + 1] & 0xFFFFFFFFL;
            n += 2;
            ++n2;
        }
        return new LongArrayTag(lArray);
    }

    private void lambda$registerPackets$1(MetaHandlerEvent metaHandlerEvent, Metadata metadata) {
        if (metadata.metaType().type().equals(Type.ITEM)) {
            metadata.setValue(this.handleItemToClient((Item)metadata.getValue()));
        }
    }

    private void lambda$registerPackets$0(PacketWrapper packetWrapper) throws Exception {
        ClientWorld clientWorld = packetWrapper.user().get(ClientWorld.class);
        Chunk1_9_3_4Type chunk1_9_3_4Type = new Chunk1_9_3_4Type(clientWorld);
        Chunk chunk = packetWrapper.passthrough(chunk1_9_3_4Type);
        this.handleChunk(chunk);
    }
}

