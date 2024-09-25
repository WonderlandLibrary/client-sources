/*
 * Decompiled with CFR 0.150.
 */
package us.myles.ViaVersion.protocols.protocol1_16to1_15_2.packets;

import java.util.UUID;
import us.myles.ViaVersion.api.minecraft.item.Item;
import us.myles.ViaVersion.api.remapper.PacketRemapper;
import us.myles.ViaVersion.api.rewriters.ItemRewriter;
import us.myles.ViaVersion.api.type.Type;
import us.myles.ViaVersion.api.type.types.UUIDIntArrayType;
import us.myles.ViaVersion.protocols.protocol1_14to1_13_2.data.RecipeRewriter1_14;
import us.myles.ViaVersion.protocols.protocol1_15to1_14_4.ClientboundPackets1_15;
import us.myles.ViaVersion.protocols.protocol1_16to1_15_2.Protocol1_16To1_15_2;
import us.myles.ViaVersion.protocols.protocol1_16to1_15_2.ServerboundPackets1_16;
import us.myles.ViaVersion.protocols.protocol1_16to1_15_2.storage.InventoryTracker1_16;
import us.myles.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import us.myles.viaversion.libs.opennbt.tag.builtin.IntArrayTag;
import us.myles.viaversion.libs.opennbt.tag.builtin.ListTag;
import us.myles.viaversion.libs.opennbt.tag.builtin.LongTag;
import us.myles.viaversion.libs.opennbt.tag.builtin.StringTag;
import us.myles.viaversion.libs.opennbt.tag.builtin.Tag;

public class InventoryPackets {
    public static void register(Protocol1_16To1_15_2 protocol) {
        ItemRewriter itemRewriter = new ItemRewriter(protocol, InventoryPackets::toClient, InventoryPackets::toServer);
        protocol.registerOutgoing(ClientboundPackets1_15.OPEN_WINDOW, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.map(Type.VAR_INT);
                this.map(Type.COMPONENT);
                this.handler(wrapper -> {
                    InventoryTracker1_16 inventoryTracker = wrapper.user().get(InventoryTracker1_16.class);
                    int windowId = wrapper.get(Type.VAR_INT, 0);
                    int windowType = wrapper.get(Type.VAR_INT, 1);
                    if (windowType >= 20) {
                        wrapper.set(Type.VAR_INT, 1, ++windowType);
                    }
                    inventoryTracker.setInventory((short)windowId);
                });
            }
        });
        protocol.registerOutgoing(ClientboundPackets1_15.CLOSE_WINDOW, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.UNSIGNED_BYTE);
                this.handler(wrapper -> {
                    InventoryTracker1_16 inventoryTracker = wrapper.user().get(InventoryTracker1_16.class);
                    inventoryTracker.setInventory((short)-1);
                });
            }
        });
        protocol.registerOutgoing(ClientboundPackets1_15.WINDOW_PROPERTY, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.SHORT);
                this.map(Type.SHORT);
                this.handler(wrapper -> {
                    short enchantmentId;
                    short property = wrapper.get(Type.SHORT, 0);
                    if (property >= 4 && property <= 6 && (enchantmentId = wrapper.get(Type.SHORT, 1).shortValue()) >= 11) {
                        enchantmentId = (short)(enchantmentId + 1);
                        wrapper.set(Type.SHORT, 1, enchantmentId);
                    }
                });
            }
        });
        itemRewriter.registerSetCooldown(ClientboundPackets1_15.COOLDOWN);
        itemRewriter.registerWindowItems(ClientboundPackets1_15.WINDOW_ITEMS, Type.FLAT_VAR_INT_ITEM_ARRAY);
        itemRewriter.registerTradeList(ClientboundPackets1_15.TRADE_LIST, Type.FLAT_VAR_INT_ITEM);
        itemRewriter.registerSetSlot(ClientboundPackets1_15.SET_SLOT, Type.FLAT_VAR_INT_ITEM);
        itemRewriter.registerAdvancements(ClientboundPackets1_15.ADVANCEMENTS, Type.FLAT_VAR_INT_ITEM);
        protocol.registerOutgoing(ClientboundPackets1_15.ENTITY_EQUIPMENT, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.handler(wrapper -> {
                    int slot = wrapper.read(Type.VAR_INT);
                    wrapper.write(Type.BYTE, (byte)slot);
                    InventoryPackets.toClient(wrapper.passthrough(Type.FLAT_VAR_INT_ITEM));
                });
            }
        });
        new RecipeRewriter1_14(protocol, InventoryPackets::toClient).registerDefaultHandler(ClientboundPackets1_15.DECLARE_RECIPES);
        itemRewriter.registerClickWindow(ServerboundPackets1_16.CLICK_WINDOW, Type.FLAT_VAR_INT_ITEM);
        itemRewriter.registerCreativeInvAction(ServerboundPackets1_16.CREATIVE_INVENTORY_ACTION, Type.FLAT_VAR_INT_ITEM);
        protocol.registerIncoming(ServerboundPackets1_16.CLOSE_WINDOW, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.UNSIGNED_BYTE);
                this.handler(wrapper -> {
                    InventoryTracker1_16 inventoryTracker = wrapper.user().get(InventoryTracker1_16.class);
                    inventoryTracker.setInventory((short)-1);
                });
            }
        });
        protocol.registerIncoming(ServerboundPackets1_16.EDIT_BOOK, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.handler(wrapper -> InventoryPackets.toServer(wrapper.passthrough(Type.FLAT_VAR_INT_ITEM)));
            }
        });
        itemRewriter.registerSpawnParticle(ClientboundPackets1_15.SPAWN_PARTICLE, Type.FLAT_VAR_INT_ITEM, Type.DOUBLE);
    }

    public static void toClient(Item item) {
        CompoundTag ownerCompundTag;
        Object idTag;
        CompoundTag tag;
        Object ownerTag;
        if (item == null) {
            return;
        }
        if (item.getIdentifier() == 771 && item.getTag() != null && (ownerTag = (tag = item.getTag()).get("SkullOwner")) instanceof CompoundTag && (idTag = (ownerCompundTag = (CompoundTag)ownerTag).get("Id")) instanceof StringTag) {
            UUID id = UUID.fromString((String)((Tag)idTag).getValue());
            ownerCompundTag.put(new IntArrayTag("Id", UUIDIntArrayType.uuidToIntArray(id)));
        }
        InventoryPackets.oldToNewAttributes(item);
        item.setIdentifier(Protocol1_16To1_15_2.MAPPINGS.getNewItemId(item.getIdentifier()));
    }

    public static void toServer(Item item) {
        CompoundTag ownerCompundTag;
        Object idTag;
        CompoundTag tag;
        Object ownerTag;
        if (item == null) {
            return;
        }
        item.setIdentifier(Protocol1_16To1_15_2.MAPPINGS.getOldItemId(item.getIdentifier()));
        if (item.getIdentifier() == 771 && item.getTag() != null && (ownerTag = (tag = item.getTag()).get("SkullOwner")) instanceof CompoundTag && (idTag = (ownerCompundTag = (CompoundTag)ownerTag).get("Id")) instanceof IntArrayTag) {
            UUID id = UUIDIntArrayType.uuidFromIntArray((int[])((Tag)idTag).getValue());
            ownerCompundTag.put(new StringTag("Id", id.toString()));
        }
        InventoryPackets.newToOldAttributes(item);
    }

    public static void oldToNewAttributes(Item item) {
        if (item.getTag() == null) {
            return;
        }
        ListTag attributes = (ListTag)item.getTag().get("AttributeModifiers");
        if (attributes == null) {
            return;
        }
        for (Tag tag : attributes) {
            CompoundTag attribute = (CompoundTag)tag;
            InventoryPackets.rewriteAttributeName(attribute, "AttributeName", false);
            InventoryPackets.rewriteAttributeName(attribute, "Name", false);
            Object leastTag = attribute.get("UUIDLeast");
            if (leastTag == null) continue;
            Object mostTag = attribute.get("UUIDMost");
            int[] uuidIntArray = UUIDIntArrayType.bitsToIntArray(((Number)((Tag)leastTag).getValue()).longValue(), ((Number)((Tag)mostTag).getValue()).longValue());
            attribute.put(new IntArrayTag("UUID", uuidIntArray));
        }
    }

    public static void newToOldAttributes(Item item) {
        if (item.getTag() == null) {
            return;
        }
        ListTag attributes = (ListTag)item.getTag().get("AttributeModifiers");
        if (attributes == null) {
            return;
        }
        for (Tag tag : attributes) {
            CompoundTag attribute = (CompoundTag)tag;
            InventoryPackets.rewriteAttributeName(attribute, "AttributeName", true);
            InventoryPackets.rewriteAttributeName(attribute, "Name", true);
            IntArrayTag uuidTag = (IntArrayTag)attribute.get("UUID");
            if (uuidTag == null) continue;
            UUID uuid = UUIDIntArrayType.uuidFromIntArray(uuidTag.getValue());
            attribute.put(new LongTag("UUIDLeast", uuid.getLeastSignificantBits()));
            attribute.put(new LongTag("UUIDMost", uuid.getMostSignificantBits()));
        }
    }

    public static void rewriteAttributeName(CompoundTag compoundTag, String entryName, boolean inverse) {
        String mappedAttribute;
        StringTag attributeNameTag = (StringTag)compoundTag.get(entryName);
        if (attributeNameTag == null) {
            return;
        }
        String attributeName = attributeNameTag.getValue();
        if (inverse && !attributeName.startsWith("minecraft:")) {
            attributeName = "minecraft:" + attributeName;
        }
        if ((mappedAttribute = (String)(inverse ? Protocol1_16To1_15_2.MAPPINGS.getAttributeMappings().inverse() : Protocol1_16To1_15_2.MAPPINGS.getAttributeMappings()).get((Object)attributeName)) == null) {
            return;
        }
        attributeNameTag.setValue(mappedAttribute);
    }
}

