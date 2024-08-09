/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viabackwards.protocol.protocol1_9_4to1_10.packets;

import com.viaversion.viabackwards.api.rewriters.LegacyBlockItemRewriter;
import com.viaversion.viabackwards.protocol.protocol1_9_4to1_10.Protocol1_9_4To1_10;
import com.viaversion.viaversion.api.minecraft.BlockChangeRecord;
import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocols.protocol1_9_1_2to1_9_3_4.types.Chunk1_9_3_4Type;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.ClientboundPackets1_9_3;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.ServerboundPackets1_9_3;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.storage.ClientWorld;
import com.viaversion.viaversion.rewriter.meta.MetaHandlerEvent;

public class BlockItemPackets1_10
extends LegacyBlockItemRewriter<ClientboundPackets1_9_3, ServerboundPackets1_9_3, Protocol1_9_4To1_10> {
    public BlockItemPackets1_10(Protocol1_9_4To1_10 protocol1_9_4To1_10) {
        super(protocol1_9_4To1_10);
    }

    @Override
    protected void registerPackets() {
        this.registerSetSlot(ClientboundPackets1_9_3.SET_SLOT, Type.ITEM);
        this.registerWindowItems(ClientboundPackets1_9_3.WINDOW_ITEMS, Type.ITEM_ARRAY);
        this.registerEntityEquipment(ClientboundPackets1_9_3.ENTITY_EQUIPMENT, Type.ITEM);
        ((Protocol1_9_4To1_10)this.protocol).registerClientbound(ClientboundPackets1_9_3.PLUGIN_MESSAGE, new PacketHandlers(this){
            final BlockItemPackets1_10 this$0;
            {
                this.this$0 = blockItemPackets1_10;
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
        this.registerClickWindow(ServerboundPackets1_9_3.CLICK_WINDOW, Type.ITEM);
        this.registerCreativeInvAction(ServerboundPackets1_9_3.CREATIVE_INVENTORY_ACTION, Type.ITEM);
        ((Protocol1_9_4To1_10)this.protocol).registerClientbound(ClientboundPackets1_9_3.CHUNK_DATA, this::lambda$registerPackets$0);
        ((Protocol1_9_4To1_10)this.protocol).registerClientbound(ClientboundPackets1_9_3.BLOCK_CHANGE, new PacketHandlers(this){
            final BlockItemPackets1_10 this$0;
            {
                this.this$0 = blockItemPackets1_10;
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
        ((Protocol1_9_4To1_10)this.protocol).registerClientbound(ClientboundPackets1_9_3.MULTI_BLOCK_CHANGE, new PacketHandlers(this){
            final BlockItemPackets1_10 this$0;
            {
                this.this$0 = blockItemPackets1_10;
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
        ((Protocol1_9_4To1_10)this.protocol).getEntityRewriter().filter().handler(this::lambda$registerPackets$1);
        ((Protocol1_9_4To1_10)this.protocol).registerClientbound(ClientboundPackets1_9_3.SPAWN_PARTICLE, new PacketHandlers(this){
            final BlockItemPackets1_10 this$0;
            {
                this.this$0 = blockItemPackets1_10;
            }

            @Override
            public void register() {
                this.map(Type.INT);
                this.map(Type.BOOLEAN);
                this.map(Type.FLOAT);
                this.map(Type.FLOAT);
                this.map(Type.FLOAT);
                this.map(Type.FLOAT);
                this.map(Type.FLOAT);
                this.map(Type.FLOAT);
                this.map(Type.FLOAT);
                this.map(Type.INT);
                this.handler(4::lambda$register$0);
            }

            private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                int n = packetWrapper.get(Type.INT, 0);
                if (n == 46) {
                    packetWrapper.set(Type.INT, 0, 38);
                }
            }
        });
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

