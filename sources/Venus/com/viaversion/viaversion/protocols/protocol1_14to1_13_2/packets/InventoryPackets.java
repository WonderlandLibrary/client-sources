/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.protocols.protocol1_14to1_13_2.packets;

import com.google.common.collect.Sets;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.minecraft.item.DataItem;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.libs.gson.JsonObject;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.DoubleTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.ListTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ChatRewriter;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ClientboundPackets1_13;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ServerboundPackets1_13;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.ClientboundPackets1_14;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.Protocol1_14To1_13_2;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.ServerboundPackets1_14;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.storage.EntityTracker1_14;
import com.viaversion.viaversion.rewriter.ComponentRewriter;
import com.viaversion.viaversion.rewriter.ItemRewriter;
import com.viaversion.viaversion.rewriter.RecipeRewriter;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class InventoryPackets
extends ItemRewriter<ClientboundPackets1_13, ServerboundPackets1_14, Protocol1_14To1_13_2> {
    private static final String NBT_TAG_NAME = "ViaVersion|" + Protocol1_14To1_13_2.class.getSimpleName();
    private static final Set<String> REMOVED_RECIPE_TYPES = Sets.newHashSet("crafting_special_banneraddpattern", "crafting_special_repairitem");
    private static final ComponentRewriter<ClientboundPackets1_13> COMPONENT_REWRITER = new ComponentRewriter<ClientboundPackets1_13>(){

        @Override
        protected void handleTranslate(JsonObject jsonObject, String string) {
            super.handleTranslate(jsonObject, string);
            if (string.startsWith("block.") && string.endsWith(".name")) {
                jsonObject.addProperty("translate", string.substring(0, string.length() - 5));
            }
        }
    };

    public InventoryPackets(Protocol1_14To1_13_2 protocol1_14To1_13_2) {
        super(protocol1_14To1_13_2);
    }

    @Override
    public void registerPackets() {
        this.registerSetCooldown(ClientboundPackets1_13.COOLDOWN);
        this.registerAdvancements(ClientboundPackets1_13.ADVANCEMENTS, Type.FLAT_VAR_INT_ITEM);
        ((Protocol1_14To1_13_2)this.protocol).registerClientbound(ClientboundPackets1_13.OPEN_WINDOW, null, InventoryPackets::lambda$registerPackets$0);
        this.registerWindowItems(ClientboundPackets1_13.WINDOW_ITEMS, Type.FLAT_VAR_INT_ITEM_ARRAY);
        this.registerSetSlot(ClientboundPackets1_13.SET_SLOT, Type.FLAT_VAR_INT_ITEM);
        ((Protocol1_14To1_13_2)this.protocol).registerClientbound(ClientboundPackets1_13.PLUGIN_MESSAGE, new PacketHandlers(this){
            final InventoryPackets this$0;
            {
                this.this$0 = inventoryPackets;
            }

            @Override
            public void register() {
                this.map(Type.STRING);
                this.handler(this::lambda$register$0);
            }

            private void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                String string = packetWrapper.get(Type.STRING, 0);
                if (string.equals("minecraft:trader_list") || string.equals("trader_list")) {
                    packetWrapper.setPacketType(ClientboundPackets1_14.TRADE_LIST);
                    packetWrapper.resetReader();
                    packetWrapper.read(Type.STRING);
                    int n = packetWrapper.read(Type.INT);
                    EntityTracker1_14 entityTracker1_14 = (EntityTracker1_14)packetWrapper.user().getEntityTracker(Protocol1_14To1_13_2.class);
                    entityTracker1_14.setLatestTradeWindowId(n);
                    packetWrapper.write(Type.VAR_INT, n);
                    int n2 = packetWrapper.passthrough(Type.UNSIGNED_BYTE).shortValue();
                    for (int i = 0; i < n2; ++i) {
                        this.this$0.handleItemToClient(packetWrapper.passthrough(Type.FLAT_VAR_INT_ITEM));
                        this.this$0.handleItemToClient(packetWrapper.passthrough(Type.FLAT_VAR_INT_ITEM));
                        boolean bl = packetWrapper.passthrough(Type.BOOLEAN);
                        if (bl) {
                            this.this$0.handleItemToClient(packetWrapper.passthrough(Type.FLAT_VAR_INT_ITEM));
                        }
                        packetWrapper.passthrough(Type.BOOLEAN);
                        packetWrapper.passthrough(Type.INT);
                        packetWrapper.passthrough(Type.INT);
                        packetWrapper.write(Type.INT, 0);
                        packetWrapper.write(Type.INT, 0);
                        packetWrapper.write(Type.FLOAT, Float.valueOf(0.0f));
                    }
                    packetWrapper.write(Type.VAR_INT, 0);
                    packetWrapper.write(Type.VAR_INT, 0);
                    packetWrapper.write(Type.BOOLEAN, false);
                } else if (string.equals("minecraft:book_open") || string.equals("book_open")) {
                    int n = packetWrapper.read(Type.VAR_INT);
                    packetWrapper.clearPacket();
                    packetWrapper.setPacketType(ClientboundPackets1_14.OPEN_BOOK);
                    packetWrapper.write(Type.VAR_INT, n);
                }
            }
        });
        this.registerEntityEquipment(ClientboundPackets1_13.ENTITY_EQUIPMENT, Type.FLAT_VAR_INT_ITEM);
        RecipeRewriter recipeRewriter = new RecipeRewriter(this.protocol);
        ((Protocol1_14To1_13_2)this.protocol).registerClientbound(ClientboundPackets1_13.DECLARE_RECIPES, arg_0 -> InventoryPackets.lambda$registerPackets$1(recipeRewriter, arg_0));
        this.registerClickWindow(ServerboundPackets1_14.CLICK_WINDOW, Type.FLAT_VAR_INT_ITEM);
        ((Protocol1_14To1_13_2)this.protocol).registerServerbound(ServerboundPackets1_14.SELECT_TRADE, InventoryPackets::lambda$registerPackets$2);
        this.registerCreativeInvAction(ServerboundPackets1_14.CREATIVE_INVENTORY_ACTION, Type.FLAT_VAR_INT_ITEM);
        this.registerSpawnParticle(ClientboundPackets1_13.SPAWN_PARTICLE, Type.FLAT_VAR_INT_ITEM, Type.FLOAT);
    }

    @Override
    public Item handleItemToClient(Item item) {
        CompoundTag compoundTag;
        Object t;
        if (item == null) {
            return null;
        }
        item.setIdentifier(Protocol1_14To1_13_2.MAPPINGS.getNewItemId(item.identifier()));
        if (item.tag() == null) {
            return item;
        }
        Object t2 = item.tag().get("display");
        if (t2 instanceof CompoundTag && (t = (compoundTag = (CompoundTag)t2).get("Lore")) instanceof ListTag) {
            ListTag listTag = (ListTag)t;
            compoundTag.put(NBT_TAG_NAME + "|Lore", new ListTag((List<Tag>)listTag.clone().getValue()));
            for (Tag tag : listTag) {
                if (!(tag instanceof StringTag)) continue;
                String string = ChatRewriter.legacyTextToJsonString(((StringTag)tag).getValue(), true);
                ((StringTag)tag).setValue(string);
            }
        }
        return item;
    }

    @Override
    public Item handleItemToServer(Item item) {
        CompoundTag compoundTag;
        Object t;
        if (item == null) {
            return null;
        }
        item.setIdentifier(Protocol1_14To1_13_2.MAPPINGS.getOldItemId(item.identifier()));
        if (item.tag() == null) {
            return item;
        }
        Object t2 = item.tag().get("display");
        if (t2 instanceof CompoundTag && (t = (compoundTag = (CompoundTag)t2).get("Lore")) instanceof ListTag) {
            ListTag listTag = (ListTag)t;
            ListTag listTag2 = (ListTag)compoundTag.remove(NBT_TAG_NAME + "|Lore");
            if (listTag2 != null) {
                compoundTag.put("Lore", new ListTag((List<Tag>)listTag2.getValue()));
            } else {
                for (Tag tag : listTag) {
                    if (!(tag instanceof StringTag)) continue;
                    ((StringTag)tag).setValue(ChatRewriter.jsonToLegacyText(((StringTag)tag).getValue()));
                }
            }
        }
        return item;
    }

    private static void lambda$registerPackets$2(PacketWrapper packetWrapper) throws Exception {
        PacketWrapper packetWrapper2 = packetWrapper.create(ServerboundPackets1_13.CLICK_WINDOW);
        EntityTracker1_14 entityTracker1_14 = (EntityTracker1_14)packetWrapper.user().getEntityTracker(Protocol1_14To1_13_2.class);
        packetWrapper2.write(Type.UNSIGNED_BYTE, (short)entityTracker1_14.getLatestTradeWindowId());
        packetWrapper2.write(Type.SHORT, (short)-999);
        packetWrapper2.write(Type.BYTE, (byte)2);
        packetWrapper2.write(Type.SHORT, (short)ThreadLocalRandom.current().nextInt());
        packetWrapper2.write(Type.VAR_INT, 5);
        CompoundTag compoundTag = new CompoundTag();
        compoundTag.put("force_resync", new DoubleTag(Double.NaN));
        packetWrapper2.write(Type.FLAT_VAR_INT_ITEM, new DataItem(1, 1, 0, compoundTag));
        packetWrapper2.scheduleSendToServer(Protocol1_14To1_13_2.class);
    }

    private static void lambda$registerPackets$1(RecipeRewriter recipeRewriter, PacketWrapper packetWrapper) throws Exception {
        int n = packetWrapper.passthrough(Type.VAR_INT);
        int n2 = 0;
        for (int i = 0; i < n; ++i) {
            String string = packetWrapper.read(Type.STRING);
            String string2 = packetWrapper.read(Type.STRING);
            if (REMOVED_RECIPE_TYPES.contains(string2)) {
                ++n2;
                continue;
            }
            packetWrapper.write(Type.STRING, string2);
            packetWrapper.write(Type.STRING, string);
            recipeRewriter.handleRecipeType(packetWrapper, string2);
        }
        packetWrapper.set(Type.VAR_INT, 0, n - n2);
    }

    private static void lambda$registerPackets$0(PacketWrapper packetWrapper) throws Exception {
        Short s = packetWrapper.read(Type.UNSIGNED_BYTE);
        String string = packetWrapper.read(Type.STRING);
        JsonElement jsonElement = packetWrapper.read(Type.COMPONENT);
        COMPONENT_REWRITER.processText(jsonElement);
        Short s2 = packetWrapper.read(Type.UNSIGNED_BYTE);
        if (string.equals("EntityHorse")) {
            packetWrapper.setPacketType(ClientboundPackets1_14.OPEN_HORSE_WINDOW);
            int n = packetWrapper.read(Type.INT);
            packetWrapper.write(Type.UNSIGNED_BYTE, s);
            packetWrapper.write(Type.VAR_INT, s2.intValue());
            packetWrapper.write(Type.INT, n);
        } else {
            packetWrapper.setPacketType(ClientboundPackets1_14.OPEN_WINDOW);
            packetWrapper.write(Type.VAR_INT, s.intValue());
            int n = -1;
            switch (string) {
                case "minecraft:crafting_table": {
                    n = 11;
                    break;
                }
                case "minecraft:furnace": {
                    n = 13;
                    break;
                }
                case "minecraft:dropper": 
                case "minecraft:dispenser": {
                    n = 6;
                    break;
                }
                case "minecraft:enchanting_table": {
                    n = 12;
                    break;
                }
                case "minecraft:brewing_stand": {
                    n = 10;
                    break;
                }
                case "minecraft:villager": {
                    n = 18;
                    break;
                }
                case "minecraft:beacon": {
                    n = 8;
                    break;
                }
                case "minecraft:anvil": {
                    n = 7;
                    break;
                }
                case "minecraft:hopper": {
                    n = 15;
                    break;
                }
                case "minecraft:shulker_box": {
                    n = 19;
                    break;
                }
                default: {
                    if (s2 <= 0 || s2 > 54) break;
                    n = s2 / 9 - 1;
                }
            }
            if (n == -1) {
                Via.getPlatform().getLogger().warning("Can't open inventory for 1.14 player! Type: " + string + " Size: " + s2);
            }
            packetWrapper.write(Type.VAR_INT, n);
            packetWrapper.write(Type.COMPONENT, jsonElement);
        }
    }
}

