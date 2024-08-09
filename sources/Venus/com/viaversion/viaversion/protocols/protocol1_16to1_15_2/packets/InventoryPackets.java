/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.protocols.protocol1_16to1_15_2.packets;

import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.UUIDIntArrayType;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.IntArrayTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.ListTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.LongTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.NumberTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
import com.viaversion.viaversion.protocols.protocol1_15to1_14_4.ClientboundPackets1_15;
import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.ClientboundPackets1_16;
import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.Protocol1_16To1_15_2;
import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.ServerboundPackets1_16;
import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.storage.InventoryTracker1_16;
import com.viaversion.viaversion.rewriter.ItemRewriter;
import com.viaversion.viaversion.rewriter.RecipeRewriter;
import com.viaversion.viaversion.util.Key;
import java.util.UUID;

public class InventoryPackets
extends ItemRewriter<ClientboundPackets1_15, ServerboundPackets1_16, Protocol1_16To1_15_2> {
    public InventoryPackets(Protocol1_16To1_15_2 protocol1_16To1_15_2) {
        super(protocol1_16To1_15_2);
    }

    @Override
    public void registerPackets() {
        PacketHandler packetHandler = InventoryPackets::lambda$registerPackets$0;
        ((Protocol1_16To1_15_2)this.protocol).registerClientbound(ClientboundPackets1_15.OPEN_WINDOW, new PacketHandlers(this, packetHandler){
            final PacketHandler val$cursorRemapper;
            final InventoryPackets this$0;
            {
                this.this$0 = inventoryPackets;
                this.val$cursorRemapper = packetHandler;
            }

            @Override
            public void register() {
                this.map(Type.VAR_INT);
                this.map(Type.VAR_INT);
                this.map(Type.COMPONENT);
                this.handler(this.val$cursorRemapper);
                this.handler(1::lambda$register$0);
            }

            private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                InventoryTracker1_16 inventoryTracker1_16 = packetWrapper.user().get(InventoryTracker1_16.class);
                int n = packetWrapper.get(Type.VAR_INT, 1);
                if (n >= 20) {
                    packetWrapper.set(Type.VAR_INT, 1, ++n);
                }
                inventoryTracker1_16.setInventoryOpen(false);
            }
        });
        ((Protocol1_16To1_15_2)this.protocol).registerClientbound(ClientboundPackets1_15.CLOSE_WINDOW, new PacketHandlers(this, packetHandler){
            final PacketHandler val$cursorRemapper;
            final InventoryPackets this$0;
            {
                this.this$0 = inventoryPackets;
                this.val$cursorRemapper = packetHandler;
            }

            @Override
            public void register() {
                this.handler(this.val$cursorRemapper);
                this.handler(2::lambda$register$0);
            }

            private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                InventoryTracker1_16 inventoryTracker1_16 = packetWrapper.user().get(InventoryTracker1_16.class);
                inventoryTracker1_16.setInventoryOpen(true);
            }
        });
        ((Protocol1_16To1_15_2)this.protocol).registerClientbound(ClientboundPackets1_15.WINDOW_PROPERTY, new PacketHandlers(this){
            final InventoryPackets this$0;
            {
                this.this$0 = inventoryPackets;
            }

            @Override
            public void register() {
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.SHORT);
                this.map(Type.SHORT);
                this.handler(3::lambda$register$0);
            }

            private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                short s;
                short s2 = packetWrapper.get(Type.SHORT, 0);
                if (s2 >= 4 && s2 <= 6 && (s = packetWrapper.get(Type.SHORT, 1).shortValue()) >= 11) {
                    s = (short)(s + 1);
                    packetWrapper.set(Type.SHORT, 1, s);
                }
            }
        });
        this.registerSetCooldown(ClientboundPackets1_15.COOLDOWN);
        this.registerWindowItems(ClientboundPackets1_15.WINDOW_ITEMS, Type.FLAT_VAR_INT_ITEM_ARRAY);
        this.registerTradeList(ClientboundPackets1_15.TRADE_LIST);
        this.registerSetSlot(ClientboundPackets1_15.SET_SLOT, Type.FLAT_VAR_INT_ITEM);
        this.registerAdvancements(ClientboundPackets1_15.ADVANCEMENTS, Type.FLAT_VAR_INT_ITEM);
        ((Protocol1_16To1_15_2)this.protocol).registerClientbound(ClientboundPackets1_15.ENTITY_EQUIPMENT, new PacketHandlers(this){
            final InventoryPackets this$0;
            {
                this.this$0 = inventoryPackets;
            }

            @Override
            public void register() {
                this.map(Type.VAR_INT);
                this.handler(this::lambda$register$0);
            }

            private void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                int n = packetWrapper.read(Type.VAR_INT);
                packetWrapper.write(Type.BYTE, (byte)n);
                this.this$0.handleItemToClient(packetWrapper.passthrough(Type.FLAT_VAR_INT_ITEM));
            }
        });
        new RecipeRewriter<ClientboundPackets1_15>(this.protocol).register(ClientboundPackets1_15.DECLARE_RECIPES);
        this.registerClickWindow(ServerboundPackets1_16.CLICK_WINDOW, Type.FLAT_VAR_INT_ITEM);
        this.registerCreativeInvAction(ServerboundPackets1_16.CREATIVE_INVENTORY_ACTION, Type.FLAT_VAR_INT_ITEM);
        ((Protocol1_16To1_15_2)this.protocol).registerServerbound(ServerboundPackets1_16.CLOSE_WINDOW, InventoryPackets::lambda$registerPackets$1);
        ((Protocol1_16To1_15_2)this.protocol).registerServerbound(ServerboundPackets1_16.EDIT_BOOK, this::lambda$registerPackets$2);
        this.registerSpawnParticle(ClientboundPackets1_15.SPAWN_PARTICLE, Type.FLAT_VAR_INT_ITEM, Type.DOUBLE);
    }

    @Override
    public Item handleItemToClient(Item item) {
        Object t;
        if (item == null) {
            return null;
        }
        CompoundTag compoundTag = item.tag();
        if (item.identifier() == 771 && compoundTag != null) {
            CompoundTag compoundTag2;
            Object t2;
            Object t3 = compoundTag.get("SkullOwner");
            if (t3 instanceof CompoundTag && (t2 = (compoundTag2 = (CompoundTag)t3).get("Id")) instanceof StringTag) {
                UUID uUID = UUID.fromString((String)((Tag)t2).getValue());
                compoundTag2.put("Id", new IntArrayTag(UUIDIntArrayType.uuidToIntArray(uUID)));
            }
        } else if (item.identifier() == 759 && compoundTag != null && (t = compoundTag.get("pages")) instanceof ListTag) {
            for (Tag tag : (ListTag)t) {
                if (!(tag instanceof StringTag)) continue;
                StringTag stringTag = (StringTag)tag;
                stringTag.setValue(((Protocol1_16To1_15_2)this.protocol).getComponentRewriter().processText(stringTag.getValue()).toString());
            }
        }
        InventoryPackets.oldToNewAttributes(item);
        item.setIdentifier(Protocol1_16To1_15_2.MAPPINGS.getNewItemId(item.identifier()));
        return item;
    }

    @Override
    public Item handleItemToServer(Item item) {
        CompoundTag compoundTag;
        Object t;
        CompoundTag compoundTag2;
        Object t2;
        if (item == null) {
            return null;
        }
        item.setIdentifier(Protocol1_16To1_15_2.MAPPINGS.getOldItemId(item.identifier()));
        if (item.identifier() == 771 && item.tag() != null && (t2 = (compoundTag2 = item.tag()).get("SkullOwner")) instanceof CompoundTag && (t = (compoundTag = (CompoundTag)t2).get("Id")) instanceof IntArrayTag) {
            UUID uUID = UUIDIntArrayType.uuidFromIntArray((int[])((Tag)t).getValue());
            compoundTag.put("Id", new StringTag(uUID.toString()));
        }
        InventoryPackets.newToOldAttributes(item);
        return item;
    }

    public static void oldToNewAttributes(Item item) {
        if (item.tag() == null) {
            return;
        }
        ListTag listTag = (ListTag)item.tag().get("AttributeModifiers");
        if (listTag == null) {
            return;
        }
        for (Tag tag : listTag) {
            CompoundTag compoundTag = (CompoundTag)tag;
            InventoryPackets.rewriteAttributeName(compoundTag, "AttributeName", false);
            InventoryPackets.rewriteAttributeName(compoundTag, "Name", false);
            Object t = compoundTag.get("UUIDLeast");
            if (t == null) continue;
            Object t2 = compoundTag.get("UUIDMost");
            int[] nArray = UUIDIntArrayType.bitsToIntArray(((NumberTag)t).asLong(), ((NumberTag)t2).asLong());
            compoundTag.put("UUID", new IntArrayTag(nArray));
        }
    }

    public static void newToOldAttributes(Item item) {
        if (item.tag() == null) {
            return;
        }
        ListTag listTag = (ListTag)item.tag().get("AttributeModifiers");
        if (listTag == null) {
            return;
        }
        for (Tag tag : listTag) {
            CompoundTag compoundTag = (CompoundTag)tag;
            InventoryPackets.rewriteAttributeName(compoundTag, "AttributeName", true);
            InventoryPackets.rewriteAttributeName(compoundTag, "Name", true);
            IntArrayTag intArrayTag = (IntArrayTag)compoundTag.get("UUID");
            if (intArrayTag == null || intArrayTag.getValue().length != 4) continue;
            UUID uUID = UUIDIntArrayType.uuidFromIntArray(intArrayTag.getValue());
            compoundTag.put("UUIDLeast", new LongTag(uUID.getLeastSignificantBits()));
            compoundTag.put("UUIDMost", new LongTag(uUID.getMostSignificantBits()));
        }
    }

    public static void rewriteAttributeName(CompoundTag compoundTag, String string, boolean bl) {
        String string2;
        StringTag stringTag = (StringTag)compoundTag.get(string);
        if (stringTag == null) {
            return;
        }
        String string3 = stringTag.getValue();
        if (bl) {
            string3 = Key.namespaced(string3);
        }
        if ((string2 = (String)(bl ? Protocol1_16To1_15_2.MAPPINGS.getAttributeMappings().inverse() : Protocol1_16To1_15_2.MAPPINGS.getAttributeMappings()).get(string3)) == null) {
            return;
        }
        stringTag.setValue(string2);
    }

    private void lambda$registerPackets$2(PacketWrapper packetWrapper) throws Exception {
        this.handleItemToServer(packetWrapper.passthrough(Type.FLAT_VAR_INT_ITEM));
    }

    private static void lambda$registerPackets$1(PacketWrapper packetWrapper) throws Exception {
        InventoryTracker1_16 inventoryTracker1_16 = packetWrapper.user().get(InventoryTracker1_16.class);
        inventoryTracker1_16.setInventoryOpen(true);
    }

    private static void lambda$registerPackets$0(PacketWrapper packetWrapper) throws Exception {
        PacketWrapper packetWrapper2 = packetWrapper.create(ClientboundPackets1_16.SET_SLOT);
        packetWrapper2.write(Type.UNSIGNED_BYTE, (short)-1);
        packetWrapper2.write(Type.SHORT, (short)-1);
        packetWrapper2.write(Type.FLAT_VAR_INT_ITEM, null);
        packetWrapper2.send(Protocol1_16To1_15_2.class);
    }
}

