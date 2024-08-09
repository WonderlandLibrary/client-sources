/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.protocols.protocol1_17_1to1_17;

import com.viaversion.viaversion.api.minecraft.item.DataItem;
import com.viaversion.viaversion.api.protocol.AbstractProtocol;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.StringType;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.ListTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
import com.viaversion.viaversion.protocols.protocol1_17_1to1_17.ClientboundPackets1_17_1;
import com.viaversion.viaversion.protocols.protocol1_17to1_16_4.ClientboundPackets1_17;
import com.viaversion.viaversion.protocols.protocol1_17to1_16_4.ServerboundPackets1_17;

public final class Protocol1_17_1To1_17
extends AbstractProtocol<ClientboundPackets1_17, ClientboundPackets1_17_1, ServerboundPackets1_17, ServerboundPackets1_17> {
    private static final StringType PAGE_STRING_TYPE = new StringType(8192);
    private static final StringType TITLE_STRING_TYPE = new StringType(128);

    public Protocol1_17_1To1_17() {
        super(ClientboundPackets1_17.class, ClientboundPackets1_17_1.class, ServerboundPackets1_17.class, ServerboundPackets1_17.class);
    }

    @Override
    protected void registerPackets() {
        this.registerClientbound(ClientboundPackets1_17.REMOVE_ENTITY, ClientboundPackets1_17_1.REMOVE_ENTITIES, Protocol1_17_1To1_17::lambda$registerPackets$0);
        this.registerClientbound(ClientboundPackets1_17.SET_SLOT, new PacketHandlers(this){
            final Protocol1_17_1To1_17 this$0;
            {
                this.this$0 = protocol1_17_1To1_17;
            }

            @Override
            public void register() {
                this.map(Type.UNSIGNED_BYTE);
                this.create(Type.VAR_INT, 0);
            }
        });
        this.registerClientbound(ClientboundPackets1_17.WINDOW_ITEMS, new PacketHandlers(this){
            final Protocol1_17_1To1_17 this$0;
            {
                this.this$0 = protocol1_17_1To1_17;
            }

            @Override
            public void register() {
                this.map(Type.UNSIGNED_BYTE);
                this.create(Type.VAR_INT, 0);
                this.handler(2::lambda$register$0);
            }

            private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                packetWrapper.write(Type.FLAT_VAR_INT_ITEM_ARRAY_VAR_INT, packetWrapper.read(Type.FLAT_VAR_INT_ITEM_ARRAY));
                packetWrapper.write(Type.FLAT_VAR_INT_ITEM, null);
            }
        });
        this.registerServerbound(ServerboundPackets1_17.CLICK_WINDOW, new PacketHandlers(this){
            final Protocol1_17_1To1_17 this$0;
            {
                this.this$0 = protocol1_17_1To1_17;
            }

            @Override
            public void register() {
                this.map(Type.UNSIGNED_BYTE);
                this.read(Type.VAR_INT);
            }
        });
        this.registerServerbound(ServerboundPackets1_17.EDIT_BOOK, Protocol1_17_1To1_17::lambda$registerPackets$1);
    }

    private static void lambda$registerPackets$1(PacketWrapper packetWrapper) throws Exception {
        CompoundTag compoundTag = new CompoundTag();
        DataItem dataItem = new DataItem(942, 1, 0, compoundTag);
        packetWrapper.write(Type.FLAT_VAR_INT_ITEM, dataItem);
        int n = packetWrapper.read(Type.VAR_INT);
        int n2 = packetWrapper.read(Type.VAR_INT);
        ListTag listTag = new ListTag(StringTag.class);
        for (int i = 0; i < n2; ++i) {
            String string = packetWrapper.read(PAGE_STRING_TYPE);
            listTag.add(new StringTag(string));
        }
        if (listTag.size() == 0) {
            listTag.add(new StringTag(""));
        }
        compoundTag.put("pages", listTag);
        if (packetWrapper.read(Type.BOOLEAN).booleanValue()) {
            String string = packetWrapper.read(TITLE_STRING_TYPE);
            compoundTag.put("title", new StringTag(string));
            compoundTag.put("author", new StringTag(packetWrapper.user().getProtocolInfo().getUsername()));
            packetWrapper.write(Type.BOOLEAN, true);
        } else {
            packetWrapper.write(Type.BOOLEAN, false);
        }
        packetWrapper.write(Type.VAR_INT, n);
    }

    private static void lambda$registerPackets$0(PacketWrapper packetWrapper) throws Exception {
        int n = packetWrapper.read(Type.VAR_INT);
        packetWrapper.write(Type.VAR_INT_ARRAY_PRIMITIVE, new int[]{n});
    }
}

