/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.packets;

import com.google.common.base.Joiner;
import com.google.common.primitives.Ints;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.libs.opennbt.conversion.ConverterRegistry;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.IntTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.ListTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.NumberTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.ShortTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
import com.viaversion.viaversion.protocols.protocol1_12_1to1_12.ClientboundPackets1_12_1;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ChatRewriter;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ClientboundPackets1_13;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.Protocol1_13To1_12_2;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ServerboundPackets1_13;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.data.BlockIdData;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.data.MappingData;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.data.SoundSource;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.data.SpawnEggRewriter;
import com.viaversion.viaversion.rewriter.ItemRewriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Optional;

public class InventoryPackets
extends ItemRewriter<ClientboundPackets1_12_1, ServerboundPackets1_13, Protocol1_13To1_12_2> {
    private static final String NBT_TAG_NAME = "ViaVersion|" + Protocol1_13To1_12_2.class.getSimpleName();

    public InventoryPackets(Protocol1_13To1_12_2 protocol1_13To1_12_2) {
        super(protocol1_13To1_12_2);
    }

    @Override
    public void registerPackets() {
        ((Protocol1_13To1_12_2)this.protocol).registerClientbound(ClientboundPackets1_12_1.SET_SLOT, new PacketHandlers(this){
            final InventoryPackets this$0;
            {
                this.this$0 = inventoryPackets;
            }

            @Override
            public void register() {
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.SHORT);
                this.map(Type.ITEM, Type.FLAT_ITEM);
                this.handler(this.this$0.itemToClientHandler(Type.FLAT_ITEM));
            }
        });
        ((Protocol1_13To1_12_2)this.protocol).registerClientbound(ClientboundPackets1_12_1.WINDOW_ITEMS, new PacketHandlers(this){
            final InventoryPackets this$0;
            {
                this.this$0 = inventoryPackets;
            }

            @Override
            public void register() {
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.ITEM_ARRAY, Type.FLAT_ITEM_ARRAY);
                this.handler(this.this$0.itemArrayHandler(Type.FLAT_ITEM_ARRAY));
            }
        });
        ((Protocol1_13To1_12_2)this.protocol).registerClientbound(ClientboundPackets1_12_1.WINDOW_PROPERTY, new PacketHandlers(this){
            final InventoryPackets this$0;
            {
                this.this$0 = inventoryPackets;
            }

            @Override
            public void register() {
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.SHORT);
                this.map(Type.SHORT);
                this.handler(this::lambda$register$0);
            }

            private void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                short s = packetWrapper.get(Type.SHORT, 0);
                if (s >= 4 && s <= 6) {
                    packetWrapper.set(Type.SHORT, 1, (short)((Protocol1_13To1_12_2)InventoryPackets.access$000(this.this$0)).getMappingData().getEnchantmentMappings().getNewId(packetWrapper.get(Type.SHORT, 1).shortValue()));
                }
            }
        });
        ((Protocol1_13To1_12_2)this.protocol).registerClientbound(ClientboundPackets1_12_1.PLUGIN_MESSAGE, new PacketHandlers(this){
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
                if (string.equalsIgnoreCase("MC|StopSound")) {
                    String string2 = packetWrapper.read(Type.STRING);
                    String string3 = packetWrapper.read(Type.STRING);
                    packetWrapper.clearPacket();
                    packetWrapper.setPacketType(ClientboundPackets1_13.STOP_SOUND);
                    byte by = 0;
                    packetWrapper.write(Type.BYTE, by);
                    if (!string2.isEmpty()) {
                        by = (byte)(by | 1);
                        Optional<SoundSource> optional = SoundSource.findBySource(string2);
                        if (!optional.isPresent()) {
                            if (!Via.getConfig().isSuppressConversionWarnings() || Via.getManager().isDebug()) {
                                Via.getPlatform().getLogger().info("Could not handle unknown sound source " + string2 + " falling back to default: master");
                            }
                            optional = Optional.of(SoundSource.MASTER);
                        }
                        packetWrapper.write(Type.VAR_INT, optional.get().getId());
                    }
                    if (!string3.isEmpty()) {
                        by = (byte)(by | 2);
                        packetWrapper.write(Type.STRING, string3);
                    }
                    packetWrapper.set(Type.BYTE, 0, by);
                    return;
                }
                if (string.equalsIgnoreCase("MC|TrList")) {
                    string = "minecraft:trader_list";
                    packetWrapper.passthrough(Type.INT);
                    int n = packetWrapper.passthrough(Type.UNSIGNED_BYTE).shortValue();
                    for (int i = 0; i < n; ++i) {
                        Item item = packetWrapper.read(Type.ITEM);
                        this.this$0.handleItemToClient(item);
                        packetWrapper.write(Type.FLAT_ITEM, item);
                        Item item2 = packetWrapper.read(Type.ITEM);
                        this.this$0.handleItemToClient(item2);
                        packetWrapper.write(Type.FLAT_ITEM, item2);
                        boolean bl = packetWrapper.passthrough(Type.BOOLEAN);
                        if (bl) {
                            Item item3 = packetWrapper.read(Type.ITEM);
                            this.this$0.handleItemToClient(item3);
                            packetWrapper.write(Type.FLAT_ITEM, item3);
                        }
                        packetWrapper.passthrough(Type.BOOLEAN);
                        packetWrapper.passthrough(Type.INT);
                        packetWrapper.passthrough(Type.INT);
                    }
                } else {
                    String string4 = string;
                    if ((string = InventoryPackets.getNewPluginChannelId(string)) == null) {
                        if (!Via.getConfig().isSuppressConversionWarnings() || Via.getManager().isDebug()) {
                            Via.getPlatform().getLogger().warning("Ignoring outgoing plugin message with channel: " + string4);
                        }
                        packetWrapper.cancel();
                        return;
                    }
                    if (string.equals("minecraft:register") || string.equals("minecraft:unregister")) {
                        String[] stringArray = new String(packetWrapper.read(Type.REMAINING_BYTES), StandardCharsets.UTF_8).split("\u0000");
                        ArrayList<String> arrayList = new ArrayList<String>();
                        for (String string5 : stringArray) {
                            String string6 = InventoryPackets.getNewPluginChannelId(string5);
                            if (string6 != null) {
                                arrayList.add(string6);
                                continue;
                            }
                            if (Via.getConfig().isSuppressConversionWarnings() && !Via.getManager().isDebug()) continue;
                            Via.getPlatform().getLogger().warning("Ignoring plugin channel in outgoing REGISTER: " + string5);
                        }
                        if (!arrayList.isEmpty()) {
                            packetWrapper.write(Type.REMAINING_BYTES, Joiner.on('\u0000').join(arrayList).getBytes(StandardCharsets.UTF_8));
                        } else {
                            packetWrapper.cancel();
                            return;
                        }
                    }
                }
                packetWrapper.set(Type.STRING, 0, string);
            }
        });
        ((Protocol1_13To1_12_2)this.protocol).registerClientbound(ClientboundPackets1_12_1.ENTITY_EQUIPMENT, new PacketHandlers(this){
            final InventoryPackets this$0;
            {
                this.this$0 = inventoryPackets;
            }

            @Override
            public void register() {
                this.map(Type.VAR_INT);
                this.map(Type.VAR_INT);
                this.map(Type.ITEM, Type.FLAT_ITEM);
                this.handler(this.this$0.itemToClientHandler(Type.FLAT_ITEM));
            }
        });
        ((Protocol1_13To1_12_2)this.protocol).registerServerbound(ServerboundPackets1_13.CLICK_WINDOW, new PacketHandlers(this){
            final InventoryPackets this$0;
            {
                this.this$0 = inventoryPackets;
            }

            @Override
            public void register() {
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.SHORT);
                this.map(Type.BYTE);
                this.map(Type.SHORT);
                this.map(Type.VAR_INT);
                this.map(Type.FLAT_ITEM, Type.ITEM);
                this.handler(this.this$0.itemToServerHandler(Type.ITEM));
            }
        });
        ((Protocol1_13To1_12_2)this.protocol).registerServerbound(ServerboundPackets1_13.PLUGIN_MESSAGE, new PacketHandlers(this){
            final InventoryPackets this$0;
            {
                this.this$0 = inventoryPackets;
            }

            @Override
            public void register() {
                this.map(Type.STRING);
                this.handler(7::lambda$register$0);
            }

            private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                String string;
                String string2 = string = packetWrapper.get(Type.STRING, 0);
                if ((string = InventoryPackets.getOldPluginChannelId(string)) == null) {
                    if (!Via.getConfig().isSuppressConversionWarnings() || Via.getManager().isDebug()) {
                        Via.getPlatform().getLogger().warning("Ignoring incoming plugin message with channel: " + string2);
                    }
                    packetWrapper.cancel();
                    return;
                }
                if (string.equals("REGISTER") || string.equals("UNREGISTER")) {
                    String[] stringArray = new String(packetWrapper.read(Type.REMAINING_BYTES), StandardCharsets.UTF_8).split("\u0000");
                    ArrayList<String> arrayList = new ArrayList<String>();
                    for (String string3 : stringArray) {
                        String string4 = InventoryPackets.getOldPluginChannelId(string3);
                        if (string4 != null) {
                            arrayList.add(string4);
                            continue;
                        }
                        if (Via.getConfig().isSuppressConversionWarnings() && !Via.getManager().isDebug()) continue;
                        Via.getPlatform().getLogger().warning("Ignoring plugin channel in incoming REGISTER: " + string3);
                    }
                    packetWrapper.write(Type.REMAINING_BYTES, Joiner.on('\u0000').join(arrayList).getBytes(StandardCharsets.UTF_8));
                }
                packetWrapper.set(Type.STRING, 0, string);
            }
        });
        ((Protocol1_13To1_12_2)this.protocol).registerServerbound(ServerboundPackets1_13.CREATIVE_INVENTORY_ACTION, new PacketHandlers(this){
            final InventoryPackets this$0;
            {
                this.this$0 = inventoryPackets;
            }

            @Override
            public void register() {
                this.map(Type.SHORT);
                this.map(Type.FLAT_ITEM, Type.ITEM);
                this.handler(this.this$0.itemToServerHandler(Type.ITEM));
            }
        });
    }

    @Override
    public Item handleItemToClient(Item item) {
        if (item == null) {
            return null;
        }
        CompoundTag compoundTag = item.tag();
        int n = item.identifier() << 16 | item.data() & 0xFFFF;
        int n2 = item.identifier() << 4 | item.data() & 0xF;
        if (InventoryPackets.isDamageable(item.identifier())) {
            if (compoundTag == null) {
                compoundTag = new CompoundTag();
                item.setTag(compoundTag);
            }
            compoundTag.put("Damage", new IntTag(item.data()));
        }
        if (item.identifier() == 358) {
            if (compoundTag == null) {
                compoundTag = new CompoundTag();
                item.setTag(compoundTag);
            }
            compoundTag.put("map", new IntTag(item.data()));
        }
        if (compoundTag != null) {
            String[] stringArray;
            Object object;
            Tag tag;
            Object object22;
            Object object3;
            Tag tag2;
            boolean bl;
            boolean bl2 = bl = item.identifier() == 425;
            if ((bl || item.identifier() == 442) && compoundTag.get("BlockEntityTag") instanceof CompoundTag) {
                tag2 = (CompoundTag)compoundTag.get("BlockEntityTag");
                if (((CompoundTag)tag2).get("Base") instanceof IntTag) {
                    object3 = (IntTag)((CompoundTag)tag2).get("Base");
                    if (bl) {
                        n2 = 6800 + ((IntTag)object3).asInt();
                    }
                    ((IntTag)object3).setValue(15 - ((IntTag)object3).asInt());
                }
                if (((CompoundTag)tag2).get("Patterns") instanceof ListTag) {
                    for (Object object22 : (ListTag)((CompoundTag)tag2).get("Patterns")) {
                        if (!(object22 instanceof CompoundTag) || !((tag = ((CompoundTag)object22).get("Color")) instanceof NumberTag)) continue;
                        ((CompoundTag)object22).put("Color", new IntTag(15 - ((NumberTag)tag).asInt()));
                    }
                }
            }
            if (compoundTag.get("display") instanceof CompoundTag && ((CompoundTag)(tag2 = (CompoundTag)compoundTag.get("display"))).get("Name") instanceof StringTag) {
                object3 = (StringTag)((CompoundTag)tag2).get("Name");
                ((CompoundTag)tag2).put(NBT_TAG_NAME + "|Name", new StringTag(((StringTag)object3).getValue()));
                ((StringTag)object3).setValue(ChatRewriter.legacyTextToJsonString(((StringTag)object3).getValue(), true));
            }
            if (compoundTag.get("ench") instanceof ListTag) {
                tag2 = (ListTag)compoundTag.get("ench");
                object3 = new ListTag(CompoundTag.class);
                object22 = ((ListTag)tag2).iterator();
                while (object22.hasNext()) {
                    tag = object22.next();
                    if (!(tag instanceof CompoundTag) || (object = (NumberTag)((CompoundTag)tag).get("id")) == null) continue;
                    CompoundTag compoundTag2 = new CompoundTag();
                    short s = ((NumberTag)object).asShort();
                    stringArray = (String[])Protocol1_13To1_12_2.MAPPINGS.getOldEnchantmentsIds().get(s);
                    if (stringArray == null) {
                        stringArray = "viaversion:legacy/" + s;
                    }
                    compoundTag2.put("id", new StringTag((String)stringArray));
                    compoundTag2.put("lvl", new ShortTag(((NumberTag)((CompoundTag)tag).get("lvl")).asShort()));
                    ((ListTag)object3).add(compoundTag2);
                }
                compoundTag.remove("ench");
                compoundTag.put("Enchantments", object3);
            }
            if (compoundTag.get("StoredEnchantments") instanceof ListTag) {
                tag2 = (ListTag)compoundTag.get("StoredEnchantments");
                object3 = new ListTag(CompoundTag.class);
                object22 = ((ListTag)tag2).iterator();
                while (object22.hasNext()) {
                    tag = (Tag)object22.next();
                    if (!(tag instanceof CompoundTag)) continue;
                    object = new CompoundTag();
                    short s = ((NumberTag)((CompoundTag)tag).get("id")).asShort();
                    String string = (String)Protocol1_13To1_12_2.MAPPINGS.getOldEnchantmentsIds().get(s);
                    if (string == null) {
                        string = "viaversion:legacy/" + s;
                    }
                    ((CompoundTag)object).put("id", new StringTag(string));
                    ((CompoundTag)object).put("lvl", new ShortTag(((NumberTag)((CompoundTag)tag).get("lvl")).asShort()));
                    ((ListTag)object3).add((Tag)object);
                }
                compoundTag.remove("StoredEnchantments");
                compoundTag.put("StoredEnchantments", object3);
            }
            if (compoundTag.get("CanPlaceOn") instanceof ListTag) {
                tag2 = (ListTag)compoundTag.get("CanPlaceOn");
                object3 = new ListTag(StringTag.class);
                compoundTag.put(NBT_TAG_NAME + "|CanPlaceOn", ConverterRegistry.convertToTag(ConverterRegistry.convertToValue(tag2)));
                object22 = ((ListTag)tag2).iterator();
                while (object22.hasNext()) {
                    tag = object22.next();
                    object = tag.getValue();
                    String string = object.toString().replace("minecraft:", "");
                    String string2 = BlockIdData.numberIdToString.get(Ints.tryParse(string));
                    if (string2 != null) {
                        string = string2;
                    }
                    if ((stringArray = BlockIdData.blockIdMapping.get(string.toLowerCase(Locale.ROOT))) != null) {
                        for (String string3 : stringArray) {
                            ((ListTag)object3).add(new StringTag(string3));
                        }
                        continue;
                    }
                    ((ListTag)object3).add(new StringTag(string.toLowerCase(Locale.ROOT)));
                }
                compoundTag.put("CanPlaceOn", object3);
            }
            if (compoundTag.get("CanDestroy") instanceof ListTag) {
                tag2 = (ListTag)compoundTag.get("CanDestroy");
                object3 = new ListTag(StringTag.class);
                compoundTag.put(NBT_TAG_NAME + "|CanDestroy", ConverterRegistry.convertToTag(ConverterRegistry.convertToValue(tag2)));
                object22 = ((ListTag)tag2).iterator();
                while (object22.hasNext()) {
                    tag = object22.next();
                    object = tag.getValue();
                    String string = object.toString().replace("minecraft:", "");
                    String string4 = BlockIdData.numberIdToString.get(Ints.tryParse(string));
                    if (string4 != null) {
                        string = string4;
                    }
                    if ((stringArray = BlockIdData.blockIdMapping.get(string.toLowerCase(Locale.ROOT))) != null) {
                        for (String string3 : stringArray) {
                            ((ListTag)object3).add(new StringTag(string3));
                        }
                        continue;
                    }
                    ((ListTag)object3).add(new StringTag(string.toLowerCase(Locale.ROOT)));
                }
                compoundTag.put("CanDestroy", object3);
            }
            if (item.identifier() == 383) {
                if (compoundTag.get("EntityTag") instanceof CompoundTag) {
                    tag2 = (CompoundTag)compoundTag.get("EntityTag");
                    if (((CompoundTag)tag2).get("id") instanceof StringTag) {
                        object3 = (StringTag)((CompoundTag)tag2).get("id");
                        n2 = SpawnEggRewriter.getSpawnEggId(((StringTag)object3).getValue());
                        if (n2 == -1) {
                            n2 = 25100288;
                        } else {
                            ((CompoundTag)tag2).remove("id");
                            if (((CompoundTag)tag2).isEmpty()) {
                                compoundTag.remove("EntityTag");
                            }
                        }
                    } else {
                        n2 = 25100288;
                    }
                } else {
                    n2 = 25100288;
                }
            }
            if (compoundTag.isEmpty()) {
                compoundTag = null;
                item.setTag(null);
            }
        }
        if (Protocol1_13To1_12_2.MAPPINGS.getItemMappings().getNewId(n2) == -1) {
            if (!InventoryPackets.isDamageable(item.identifier()) && item.identifier() != 358) {
                if (compoundTag == null) {
                    compoundTag = new CompoundTag();
                    item.setTag(compoundTag);
                }
                compoundTag.put(NBT_TAG_NAME, new IntTag(n));
            }
            if (item.identifier() == 31 && item.data() == 0) {
                n2 = 512;
            } else if (Protocol1_13To1_12_2.MAPPINGS.getItemMappings().getNewId(n2 & 0xFFFFFFF0) != -1) {
                n2 &= 0xFFFFFFF0;
            } else {
                if (!Via.getConfig().isSuppressConversionWarnings() || Via.getManager().isDebug()) {
                    Via.getPlatform().getLogger().warning("Failed to get 1.13 item for " + item.identifier());
                }
                n2 = 16;
            }
        }
        item.setIdentifier(Protocol1_13To1_12_2.MAPPINGS.getItemMappings().getNewId(n2));
        item.setData((short)0);
        return item;
    }

    public static String getNewPluginChannelId(String string) {
        switch (string) {
            case "MC|TrList": {
                return "minecraft:trader_list";
            }
            case "MC|Brand": {
                return "minecraft:brand";
            }
            case "MC|BOpen": {
                return "minecraft:book_open";
            }
            case "MC|DebugPath": {
                return "minecraft:debug/paths";
            }
            case "MC|DebugNeighborsUpdate": {
                return "minecraft:debug/neighbors_update";
            }
            case "REGISTER": {
                return "minecraft:register";
            }
            case "UNREGISTER": {
                return "minecraft:unregister";
            }
            case "BungeeCord": {
                return "bungeecord:main";
            }
            case "bungeecord:main": {
                return null;
            }
        }
        String string2 = (String)Protocol1_13To1_12_2.MAPPINGS.getChannelMappings().get(string);
        if (string2 != null) {
            return string2;
        }
        return MappingData.validateNewChannel(string);
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public Item handleItemToServer(Item item) {
        Object object42;
        Optional<String> optional;
        int n;
        if (item == null) {
            return null;
        }
        Integer n2 = null;
        boolean bl = false;
        CompoundTag compoundTag = item.tag();
        if (compoundTag != null && compoundTag.get(NBT_TAG_NAME) instanceof IntTag) {
            n2 = ((NumberTag)compoundTag.get(NBT_TAG_NAME)).asInt();
            compoundTag.remove(NBT_TAG_NAME);
            bl = true;
        }
        if (n2 == null && (n = Protocol1_13To1_12_2.MAPPINGS.getItemMappings().inverse().getNewId(item.identifier())) != -1) {
            optional = SpawnEggRewriter.getEntityId(n);
            if (optional.isPresent()) {
                n2 = 25100288;
                if (compoundTag == null) {
                    compoundTag = new CompoundTag();
                    item.setTag(compoundTag);
                }
                if (!compoundTag.contains("EntityTag")) {
                    object42 = new CompoundTag();
                    ((CompoundTag)object42).put("id", new StringTag(optional.get()));
                    compoundTag.put("EntityTag", object42);
                }
            } else {
                n2 = n >> 4 << 16 | n & 0xF;
            }
        }
        if (n2 == null) {
            if (!Via.getConfig().isSuppressConversionWarnings() || Via.getManager().isDebug()) {
                Via.getPlatform().getLogger().warning("Failed to get 1.12 item for " + item.identifier());
            }
            n2 = 65536;
        }
        item.setIdentifier((short)(n2 >> 16));
        item.setData((short)(n2 & 0xFFFF));
        if (compoundTag != null) {
            String[] stringArray;
            Object object3;
            CompoundTag compoundTag2;
            if (InventoryPackets.isDamageable(item.identifier()) && compoundTag.get("Damage") instanceof IntTag) {
                if (!bl) {
                    item.setData((short)((Integer)((Tag)compoundTag.get("Damage")).getValue()).intValue());
                }
                compoundTag.remove("Damage");
            }
            if (item.identifier() == 358 && compoundTag.get("map") instanceof IntTag) {
                if (!bl) {
                    item.setData((short)((Integer)((Tag)compoundTag.get("map")).getValue()).intValue());
                }
                compoundTag.remove("map");
            }
            if ((item.identifier() == 442 || item.identifier() == 425) && compoundTag.get("BlockEntityTag") instanceof CompoundTag) {
                CompoundTag compoundTag3 = (CompoundTag)compoundTag.get("BlockEntityTag");
                if (compoundTag3.get("Base") instanceof IntTag) {
                    optional = (IntTag)compoundTag3.get("Base");
                    ((IntTag)((Object)optional)).setValue(15 - ((IntTag)((Object)optional)).asInt());
                }
                if (compoundTag3.get("Patterns") instanceof ListTag) {
                    for (Object object42 : (ListTag)compoundTag3.get("Patterns")) {
                        if (!(object42 instanceof CompoundTag)) continue;
                        Tag tag = (IntTag)((CompoundTag)object42).get("Color");
                        ((IntTag)tag).setValue(15 - ((IntTag)tag).asInt());
                    }
                }
            }
            if (compoundTag.get("display") instanceof CompoundTag && (compoundTag2 = (CompoundTag)compoundTag.get("display")).get("Name") instanceof StringTag) {
                optional = (StringTag)compoundTag2.get("Name");
                object42 = (StringTag)compoundTag2.remove(NBT_TAG_NAME + "|Name");
                ((StringTag)((Object)optional)).setValue(object42 != null ? ((StringTag)object42).getValue() : ChatRewriter.jsonToLegacyText(((StringTag)((Object)optional)).getValue()));
            }
            if (compoundTag.get("Enchantments") instanceof ListTag) {
                ListTag listTag = (ListTag)compoundTag.get("Enchantments");
                optional = new ListTag(CompoundTag.class);
                for (Tag tag : listTag) {
                    void object2;
                    if (!(tag instanceof CompoundTag)) continue;
                    object3 = new CompoundTag();
                    stringArray = (String[])((Tag)((CompoundTag)tag).get("id")).getValue();
                    Short s = (Short)Protocol1_13To1_12_2.MAPPINGS.getOldEnchantmentsIds().inverse().get(stringArray);
                    if (s == null && stringArray.startsWith("viaversion:legacy/")) {
                        Short s2 = Short.valueOf(stringArray.substring(18));
                    }
                    if (object2 == null) continue;
                    ((CompoundTag)object3).put("id", new ShortTag(object2.shortValue()));
                    ((CompoundTag)object3).put("lvl", new ShortTag(((NumberTag)((CompoundTag)tag).get("lvl")).asShort()));
                    ((ListTag)((Object)optional)).add((Tag)object3);
                }
                compoundTag.remove("Enchantments");
                compoundTag.put("ench", optional);
            }
            if (compoundTag.get("StoredEnchantments") instanceof ListTag) {
                ListTag listTag = (ListTag)compoundTag.get("StoredEnchantments");
                optional = new ListTag(CompoundTag.class);
                for (Tag tag : listTag) {
                    void var11_23;
                    if (!(tag instanceof CompoundTag)) continue;
                    object3 = new CompoundTag();
                    stringArray = (String)((Tag)((CompoundTag)tag).get("id")).getValue();
                    Short s = (Short)Protocol1_13To1_12_2.MAPPINGS.getOldEnchantmentsIds().inverse().get(stringArray);
                    if (s == null && stringArray.startsWith("viaversion:legacy/")) {
                        Short s3 = Short.valueOf(stringArray.substring(18));
                    }
                    if (var11_23 == null) continue;
                    ((CompoundTag)object3).put("id", new ShortTag(var11_23.shortValue()));
                    ((CompoundTag)object3).put("lvl", new ShortTag(((NumberTag)((CompoundTag)tag).get("lvl")).asShort()));
                    ((ListTag)((Object)optional)).add((Tag)object3);
                }
                compoundTag.remove("StoredEnchantments");
                compoundTag.put("StoredEnchantments", optional);
            }
            if (compoundTag.get(NBT_TAG_NAME + "|CanPlaceOn") instanceof ListTag) {
                compoundTag.put("CanPlaceOn", ConverterRegistry.convertToTag(ConverterRegistry.convertToValue(compoundTag.get(NBT_TAG_NAME + "|CanPlaceOn"))));
                compoundTag.remove(NBT_TAG_NAME + "|CanPlaceOn");
            } else if (compoundTag.get("CanPlaceOn") instanceof ListTag) {
                ListTag listTag = (ListTag)compoundTag.get("CanPlaceOn");
                optional = new ListTag(StringTag.class);
                for (Tag tag : listTag) {
                    object3 = tag.getValue();
                    stringArray = BlockIdData.fallbackReverseMapping.get(object3 instanceof String ? ((String)object3).replace("minecraft:", "") : null);
                    if (stringArray != null) {
                        for (String string : stringArray) {
                            ((ListTag)((Object)optional)).add(new StringTag(string));
                        }
                        continue;
                    }
                    ((ListTag)((Object)optional)).add(tag);
                }
                compoundTag.put("CanPlaceOn", optional);
            }
            if (compoundTag.get(NBT_TAG_NAME + "|CanDestroy") instanceof ListTag) {
                compoundTag.put("CanDestroy", ConverterRegistry.convertToTag(ConverterRegistry.convertToValue(compoundTag.get(NBT_TAG_NAME + "|CanDestroy"))));
                compoundTag.remove(NBT_TAG_NAME + "|CanDestroy");
            } else if (compoundTag.get("CanDestroy") instanceof ListTag) {
                ListTag listTag = (ListTag)compoundTag.get("CanDestroy");
                optional = new ListTag(StringTag.class);
                for (Tag tag : listTag) {
                    object3 = tag.getValue();
                    stringArray = BlockIdData.fallbackReverseMapping.get(object3 instanceof String ? ((String)object3).replace("minecraft:", "") : null);
                    if (stringArray != null) {
                        for (String string : stringArray) {
                            ((ListTag)((Object)optional)).add(new StringTag(string));
                        }
                        continue;
                    }
                    ((ListTag)((Object)optional)).add(tag);
                }
                compoundTag.put("CanDestroy", optional);
            }
        }
        return item;
    }

    public static String getOldPluginChannelId(String string) {
        if ((string = MappingData.validateNewChannel(string)) == null) {
            return null;
        }
        switch (string) {
            case "minecraft:trader_list": {
                return "MC|TrList";
            }
            case "minecraft:book_open": {
                return "MC|BOpen";
            }
            case "minecraft:debug/paths": {
                return "MC|DebugPath";
            }
            case "minecraft:debug/neighbors_update": {
                return "MC|DebugNeighborsUpdate";
            }
            case "minecraft:register": {
                return "REGISTER";
            }
            case "minecraft:unregister": {
                return "UNREGISTER";
            }
            case "minecraft:brand": {
                return "MC|Brand";
            }
            case "bungeecord:main": {
                return "BungeeCord";
            }
        }
        String string2 = (String)Protocol1_13To1_12_2.MAPPINGS.getChannelMappings().inverse().get(string);
        if (string2 != null) {
            return string2;
        }
        return string.length() > 20 ? string.substring(0, 20) : string;
    }

    public static boolean isDamageable(int n) {
        return n >= 256 && n <= 259 || n == 261 || n >= 267 && n <= 279 || n >= 283 && n <= 286 || n >= 290 && n <= 294 || n >= 298 && n <= 317 || n == 346 || n == 359 || n == 398 || n == 442 || n == 443;
    }

    static Protocol access$000(InventoryPackets inventoryPackets) {
        return inventoryPackets.protocol;
    }
}

