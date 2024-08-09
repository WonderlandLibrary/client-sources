/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viabackwards.protocol.protocol1_17to1_17_1;

import com.viaversion.viabackwards.api.BackwardsProtocol;
import com.viaversion.viabackwards.protocol.protocol1_16_4to1_17.storage.PlayerLastCursorItem;
import com.viaversion.viabackwards.protocol.protocol1_17to1_17_1.storage.InventoryStateIds;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.ListTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
import com.viaversion.viaversion.protocols.protocol1_17_1to1_17.ClientboundPackets1_17_1;
import com.viaversion.viaversion.protocols.protocol1_17to1_16_4.ClientboundPackets1_17;
import com.viaversion.viaversion.protocols.protocol1_17to1_16_4.ServerboundPackets1_17;

public final class Protocol1_17To1_17_1
extends BackwardsProtocol<ClientboundPackets1_17_1, ClientboundPackets1_17, ServerboundPackets1_17, ServerboundPackets1_17> {
    private static final int MAX_PAGE_LENGTH = 8192;
    private static final int MAX_TITLE_LENGTH = 128;
    private static final int MAX_PAGES = 200;

    public Protocol1_17To1_17_1() {
        super(ClientboundPackets1_17_1.class, ClientboundPackets1_17.class, ServerboundPackets1_17.class, ServerboundPackets1_17.class);
    }

    @Override
    protected void registerPackets() {
        this.registerClientbound(ClientboundPackets1_17_1.REMOVE_ENTITIES, null, Protocol1_17To1_17_1::lambda$registerPackets$0);
        this.registerClientbound(ClientboundPackets1_17_1.CLOSE_WINDOW, Protocol1_17To1_17_1::lambda$registerPackets$1);
        this.registerClientbound(ClientboundPackets1_17_1.SET_SLOT, Protocol1_17To1_17_1::lambda$registerPackets$2);
        this.registerClientbound(ClientboundPackets1_17_1.WINDOW_ITEMS, Protocol1_17To1_17_1::lambda$registerPackets$3);
        this.registerServerbound(ServerboundPackets1_17.CLOSE_WINDOW, Protocol1_17To1_17_1::lambda$registerPackets$4);
        this.registerServerbound(ServerboundPackets1_17.CLICK_WINDOW, Protocol1_17To1_17_1::lambda$registerPackets$5);
        this.registerServerbound(ServerboundPackets1_17.EDIT_BOOK, Protocol1_17To1_17_1::lambda$registerPackets$6);
    }

    @Override
    public void init(UserConnection userConnection) {
        userConnection.put(new InventoryStateIds());
    }

    private static void lambda$registerPackets$6(PacketWrapper packetWrapper) throws Exception {
        ListTag listTag;
        Item item = packetWrapper.read(Type.FLAT_VAR_INT_ITEM);
        boolean bl = packetWrapper.read(Type.BOOLEAN);
        packetWrapper.passthrough(Type.VAR_INT);
        CompoundTag compoundTag = item.tag();
        StringTag stringTag = null;
        if (compoundTag == null || (listTag = (ListTag)compoundTag.get("pages")) == null || bl && (stringTag = (StringTag)compoundTag.get("title")) == null) {
            packetWrapper.write(Type.VAR_INT, 0);
            packetWrapper.write(Type.BOOLEAN, false);
            return;
        }
        if (listTag.size() > 200) {
            listTag = new ListTag(listTag.getValue().subList(0, 200));
        }
        packetWrapper.write(Type.VAR_INT, listTag.size());
        Object object = listTag.iterator();
        while (object.hasNext()) {
            Tag tag = object.next();
            String string = ((StringTag)tag).getValue();
            if (string.length() > 8192) {
                string = string.substring(0, 8192);
            }
            packetWrapper.write(Type.STRING, string);
        }
        packetWrapper.write(Type.BOOLEAN, bl);
        if (bl) {
            if (stringTag == null) {
                stringTag = (StringTag)compoundTag.get("title");
            }
            if (((String)(object = stringTag.getValue())).length() > 128) {
                object = ((String)object).substring(0, 128);
            }
            packetWrapper.write(Type.STRING, object);
        }
    }

    private static void lambda$registerPackets$5(PacketWrapper packetWrapper) throws Exception {
        short s = packetWrapper.passthrough(Type.UNSIGNED_BYTE);
        int n = packetWrapper.user().get(InventoryStateIds.class).removeStateId(s);
        packetWrapper.write(Type.VAR_INT, n == Integer.MAX_VALUE ? 0 : n);
    }

    private static void lambda$registerPackets$4(PacketWrapper packetWrapper) throws Exception {
        short s = packetWrapper.passthrough(Type.UNSIGNED_BYTE);
        packetWrapper.user().get(InventoryStateIds.class).removeStateId(s);
    }

    private static void lambda$registerPackets$3(PacketWrapper packetWrapper) throws Exception {
        short s = packetWrapper.passthrough(Type.UNSIGNED_BYTE);
        int n = packetWrapper.read(Type.VAR_INT);
        packetWrapper.user().get(InventoryStateIds.class).setStateId(s, n);
        packetWrapper.write(Type.FLAT_VAR_INT_ITEM_ARRAY, packetWrapper.read(Type.FLAT_VAR_INT_ITEM_ARRAY_VAR_INT));
        Item item = packetWrapper.read(Type.FLAT_VAR_INT_ITEM);
        PlayerLastCursorItem playerLastCursorItem = packetWrapper.user().get(PlayerLastCursorItem.class);
        if (playerLastCursorItem != null) {
            playerLastCursorItem.setLastCursorItem(item);
        }
    }

    private static void lambda$registerPackets$2(PacketWrapper packetWrapper) throws Exception {
        short s = packetWrapper.passthrough(Type.UNSIGNED_BYTE);
        int n = packetWrapper.read(Type.VAR_INT);
        packetWrapper.user().get(InventoryStateIds.class).setStateId(s, n);
    }

    private static void lambda$registerPackets$1(PacketWrapper packetWrapper) throws Exception {
        short s = packetWrapper.passthrough(Type.UNSIGNED_BYTE);
        packetWrapper.user().get(InventoryStateIds.class).removeStateId(s);
    }

    private static void lambda$registerPackets$0(PacketWrapper packetWrapper) throws Exception {
        int[] nArray = packetWrapper.read(Type.VAR_INT_ARRAY_PRIMITIVE);
        packetWrapper.cancel();
        for (int n : nArray) {
            PacketWrapper packetWrapper2 = packetWrapper.create(ClientboundPackets1_17.REMOVE_ENTITY);
            packetWrapper2.write(Type.VAR_INT, n);
            packetWrapper2.send(Protocol1_17To1_17_1.class);
        }
    }
}

