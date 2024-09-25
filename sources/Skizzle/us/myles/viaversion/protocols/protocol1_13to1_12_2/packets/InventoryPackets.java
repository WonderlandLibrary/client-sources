/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Joiner
 *  com.google.common.primitives.Ints
 */
package us.myles.ViaVersion.protocols.protocol1_13to1_12_2.packets;

import com.google.common.base.Joiner;
import com.google.common.primitives.Ints;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Optional;
import us.myles.ViaVersion.api.PacketWrapper;
import us.myles.ViaVersion.api.Via;
import us.myles.ViaVersion.api.minecraft.item.Item;
import us.myles.ViaVersion.api.remapper.PacketHandler;
import us.myles.ViaVersion.api.remapper.PacketRemapper;
import us.myles.ViaVersion.api.rewriters.ItemRewriter;
import us.myles.ViaVersion.api.type.Type;
import us.myles.ViaVersion.protocols.protocol1_12_1to1_12.ClientboundPackets1_12_1;
import us.myles.ViaVersion.protocols.protocol1_13to1_12_2.ChatRewriter;
import us.myles.ViaVersion.protocols.protocol1_13to1_12_2.Protocol1_13To1_12_2;
import us.myles.ViaVersion.protocols.protocol1_13to1_12_2.ServerboundPackets1_13;
import us.myles.ViaVersion.protocols.protocol1_13to1_12_2.data.BlockIdData;
import us.myles.ViaVersion.protocols.protocol1_13to1_12_2.data.MappingData;
import us.myles.ViaVersion.protocols.protocol1_13to1_12_2.data.SoundSource;
import us.myles.ViaVersion.protocols.protocol1_13to1_12_2.data.SpawnEggRewriter;
import us.myles.viaversion.libs.bungeecordchat.api.ChatColor;
import us.myles.viaversion.libs.opennbt.conversion.ConverterRegistry;
import us.myles.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import us.myles.viaversion.libs.opennbt.tag.builtin.IntTag;
import us.myles.viaversion.libs.opennbt.tag.builtin.ListTag;
import us.myles.viaversion.libs.opennbt.tag.builtin.ShortTag;
import us.myles.viaversion.libs.opennbt.tag.builtin.StringTag;
import us.myles.viaversion.libs.opennbt.tag.builtin.Tag;

public class InventoryPackets {
    private static final String NBT_TAG_NAME = "ViaVersion|" + Protocol1_13To1_12_2.class.getSimpleName();

    public static void register(final Protocol1_13To1_12_2 protocol) {
        final ItemRewriter itemRewriter = new ItemRewriter(protocol, InventoryPackets::toClient, InventoryPackets::toServer);
        protocol.registerOutgoing(ClientboundPackets1_12_1.SET_SLOT, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.BYTE);
                this.map(Type.SHORT);
                this.map(Type.ITEM, Type.FLAT_ITEM);
                this.handler(itemRewriter.itemToClientHandler(Type.FLAT_ITEM));
            }
        });
        protocol.registerOutgoing(ClientboundPackets1_12_1.WINDOW_ITEMS, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.ITEM_ARRAY, Type.FLAT_ITEM_ARRAY);
                this.handler(itemRewriter.itemArrayHandler(Type.FLAT_ITEM_ARRAY));
            }
        });
        protocol.registerOutgoing(ClientboundPackets1_12_1.WINDOW_PROPERTY, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.SHORT);
                this.map(Type.SHORT);
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper wrapper) throws Exception {
                        short property = wrapper.get(Type.SHORT, 0);
                        if (property >= 4 && property <= 6) {
                            wrapper.set(Type.SHORT, 1, (short)protocol.getMappingData().getEnchantmentMappings().getNewId(wrapper.get(Type.SHORT, 1).shortValue()));
                        }
                    }
                });
            }
        });
        protocol.registerOutgoing(ClientboundPackets1_12_1.PLUGIN_MESSAGE, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.STRING);
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper wrapper) throws Exception {
                        String channel = wrapper.get(Type.STRING, 0);
                        if (channel.equalsIgnoreCase("MC|StopSound")) {
                            String originalSource = wrapper.read(Type.STRING);
                            String originalSound = wrapper.read(Type.STRING);
                            wrapper.clearPacket();
                            wrapper.setId(76);
                            byte flags = 0;
                            wrapper.write(Type.BYTE, flags);
                            if (!originalSource.isEmpty()) {
                                flags = (byte)(flags | 1);
                                Optional<SoundSource> finalSource = SoundSource.findBySource(originalSource);
                                if (!finalSource.isPresent()) {
                                    if (!Via.getConfig().isSuppressConversionWarnings() || Via.getManager().isDebug()) {
                                        Via.getPlatform().getLogger().info("Could not handle unknown sound source " + originalSource + " falling back to default: master");
                                    }
                                    finalSource = Optional.of(SoundSource.MASTER);
                                }
                                wrapper.write(Type.VAR_INT, finalSource.get().getId());
                            }
                            if (!originalSound.isEmpty()) {
                                flags = (byte)(flags | 2);
                                wrapper.write(Type.STRING, originalSound);
                            }
                            wrapper.set(Type.BYTE, 0, flags);
                            return;
                        }
                        if (channel.equalsIgnoreCase("MC|TrList")) {
                            channel = "minecraft:trader_list";
                            wrapper.passthrough(Type.INT);
                            int size = wrapper.passthrough(Type.UNSIGNED_BYTE).shortValue();
                            for (int i = 0; i < size; ++i) {
                                Item input = wrapper.read(Type.ITEM);
                                InventoryPackets.toClient(input);
                                wrapper.write(Type.FLAT_ITEM, input);
                                Item output = wrapper.read(Type.ITEM);
                                InventoryPackets.toClient(output);
                                wrapper.write(Type.FLAT_ITEM, output);
                                boolean secondItem = wrapper.passthrough(Type.BOOLEAN);
                                if (secondItem) {
                                    Item second = wrapper.read(Type.ITEM);
                                    InventoryPackets.toClient(second);
                                    wrapper.write(Type.FLAT_ITEM, second);
                                }
                                wrapper.passthrough(Type.BOOLEAN);
                                wrapper.passthrough(Type.INT);
                                wrapper.passthrough(Type.INT);
                            }
                        } else {
                            String old = channel;
                            if ((channel = InventoryPackets.getNewPluginChannelId(channel)) == null) {
                                if (!Via.getConfig().isSuppressConversionWarnings() || Via.getManager().isDebug()) {
                                    Via.getPlatform().getLogger().warning("Ignoring outgoing plugin message with channel: " + old);
                                }
                                wrapper.cancel();
                                return;
                            }
                            if (channel.equals("minecraft:register") || channel.equals("minecraft:unregister")) {
                                String[] channels = new String(wrapper.read(Type.REMAINING_BYTES), StandardCharsets.UTF_8).split("\u0000");
                                ArrayList<String> rewrittenChannels = new ArrayList<String>();
                                for (int i = 0; i < channels.length; ++i) {
                                    String rewritten = InventoryPackets.getNewPluginChannelId(channels[i]);
                                    if (rewritten != null) {
                                        rewrittenChannels.add(rewritten);
                                        continue;
                                    }
                                    if (Via.getConfig().isSuppressConversionWarnings() && !Via.getManager().isDebug()) continue;
                                    Via.getPlatform().getLogger().warning("Ignoring plugin channel in outgoing REGISTER: " + channels[i]);
                                }
                                if (!rewrittenChannels.isEmpty()) {
                                    wrapper.write(Type.REMAINING_BYTES, Joiner.on((char)'\u0000').join(rewrittenChannels).getBytes(StandardCharsets.UTF_8));
                                } else {
                                    wrapper.cancel();
                                    return;
                                }
                            }
                        }
                        wrapper.set(Type.STRING, 0, channel);
                    }
                });
            }
        });
        protocol.registerOutgoing(ClientboundPackets1_12_1.ENTITY_EQUIPMENT, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.map(Type.VAR_INT);
                this.map(Type.ITEM, Type.FLAT_ITEM);
                this.handler(itemRewriter.itemToClientHandler(Type.FLAT_ITEM));
            }
        });
        protocol.registerIncoming(ServerboundPackets1_13.CLICK_WINDOW, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.SHORT);
                this.map(Type.BYTE);
                this.map(Type.SHORT);
                this.map(Type.VAR_INT);
                this.map(Type.FLAT_ITEM, Type.ITEM);
                this.handler(itemRewriter.itemToServerHandler(Type.ITEM));
            }
        });
        protocol.registerIncoming(ServerboundPackets1_13.PLUGIN_MESSAGE, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.STRING);
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper wrapper) throws Exception {
                        String channel;
                        String old = channel = wrapper.get(Type.STRING, 0);
                        if ((channel = InventoryPackets.getOldPluginChannelId(channel)) == null) {
                            if (!Via.getConfig().isSuppressConversionWarnings() || Via.getManager().isDebug()) {
                                Via.getPlatform().getLogger().warning("Ignoring incoming plugin message with channel: " + old);
                            }
                            wrapper.cancel();
                            return;
                        }
                        if (channel.equals("REGISTER") || channel.equals("UNREGISTER")) {
                            String[] channels = new String(wrapper.read(Type.REMAINING_BYTES), StandardCharsets.UTF_8).split("\u0000");
                            ArrayList<String> rewrittenChannels = new ArrayList<String>();
                            for (int i = 0; i < channels.length; ++i) {
                                String rewritten = InventoryPackets.getOldPluginChannelId(channels[i]);
                                if (rewritten != null) {
                                    rewrittenChannels.add(rewritten);
                                    continue;
                                }
                                if (Via.getConfig().isSuppressConversionWarnings() && !Via.getManager().isDebug()) continue;
                                Via.getPlatform().getLogger().warning("Ignoring plugin channel in incoming REGISTER: " + channels[i]);
                            }
                            wrapper.write(Type.REMAINING_BYTES, Joiner.on((char)'\u0000').join(rewrittenChannels).getBytes(StandardCharsets.UTF_8));
                        }
                        wrapper.set(Type.STRING, 0, channel);
                    }
                });
            }
        });
        protocol.registerIncoming(ServerboundPackets1_13.CREATIVE_INVENTORY_ACTION, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.SHORT);
                this.map(Type.FLAT_ITEM, Type.ITEM);
                this.handler(itemRewriter.itemToServerHandler(Type.ITEM));
            }
        });
    }

    public static void toClient(Item item) {
        if (item == null) {
            return;
        }
        CompoundTag tag = item.getTag();
        int originalId = item.getIdentifier() << 16 | item.getData() & 0xFFFF;
        int rawId = item.getIdentifier() << 4 | item.getData() & 0xF;
        if (InventoryPackets.isDamageable(item.getIdentifier())) {
            if (tag == null) {
                tag = new CompoundTag("tag");
                item.setTag(tag);
            }
            tag.put(new IntTag("Damage", item.getData()));
        }
        if (item.getIdentifier() == 358) {
            if (tag == null) {
                tag = new CompoundTag("tag");
                item.setTag(tag);
            }
            tag.put(new IntTag("map", item.getData()));
        }
        if (tag != null) {
            String[] newValues;
            String numberConverted;
            Object value;
            ListTag old;
            String newId;
            short oldId;
            CompoundTag enchantmentEntry;
            CompoundTag display;
            boolean banner;
            boolean bl = banner = item.getIdentifier() == 425;
            if ((banner || item.getIdentifier() == 442) && tag.get("BlockEntityTag") instanceof CompoundTag) {
                CompoundTag blockEntityTag = (CompoundTag)tag.get("BlockEntityTag");
                if (blockEntityTag.get("Base") instanceof IntTag) {
                    IntTag base = (IntTag)blockEntityTag.get("Base");
                    if (banner) {
                        rawId = 6800 + base.getValue();
                    }
                    base.setValue(15 - base.getValue());
                }
                if (blockEntityTag.get("Patterns") instanceof ListTag) {
                    for (Tag pattern : (ListTag)blockEntityTag.get("Patterns")) {
                        if (!(pattern instanceof CompoundTag)) continue;
                        IntTag c = (IntTag)((CompoundTag)pattern).get("Color");
                        c.setValue(15 - c.getValue());
                    }
                }
            }
            if (tag.get("display") instanceof CompoundTag && (display = (CompoundTag)tag.get("display")).get("Name") instanceof StringTag) {
                StringTag name = (StringTag)display.get("Name");
                display.put(new StringTag(NBT_TAG_NAME + "|Name", name.getValue()));
                name.setValue(ChatRewriter.fromLegacyTextAsString(name.getValue(), ChatColor.WHITE, true));
            }
            if (tag.get("ench") instanceof ListTag) {
                ListTag ench = (ListTag)tag.get("ench");
                ListTag enchantments = new ListTag("Enchantments", CompoundTag.class);
                for (Tag enchEntry : ench) {
                    if (!(enchEntry instanceof CompoundTag)) continue;
                    enchantmentEntry = new CompoundTag("");
                    oldId = ((Number)((Tag)((CompoundTag)enchEntry).get("id")).getValue()).shortValue();
                    newId = (String)Protocol1_13To1_12_2.MAPPINGS.getOldEnchantmentsIds().get((Object)oldId);
                    if (newId == null) {
                        newId = "viaversion:legacy/" + oldId;
                    }
                    enchantmentEntry.put(new StringTag("id", newId));
                    enchantmentEntry.put(new ShortTag("lvl", ((Number)((Tag)((CompoundTag)enchEntry).get("lvl")).getValue()).shortValue()));
                    enchantments.add(enchantmentEntry);
                }
                tag.remove("ench");
                tag.put(enchantments);
            }
            if (tag.get("StoredEnchantments") instanceof ListTag) {
                ListTag storedEnch = (ListTag)tag.get("StoredEnchantments");
                ListTag newStoredEnch = new ListTag("StoredEnchantments", CompoundTag.class);
                for (Tag enchEntry : storedEnch) {
                    if (!(enchEntry instanceof CompoundTag)) continue;
                    enchantmentEntry = new CompoundTag("");
                    oldId = ((Number)((Tag)((CompoundTag)enchEntry).get("id")).getValue()).shortValue();
                    newId = (String)Protocol1_13To1_12_2.MAPPINGS.getOldEnchantmentsIds().get((Object)oldId);
                    if (newId == null) {
                        newId = "viaversion:legacy/" + oldId;
                    }
                    enchantmentEntry.put(new StringTag("id", newId));
                    enchantmentEntry.put(new ShortTag("lvl", ((Number)((Tag)((CompoundTag)enchEntry).get("lvl")).getValue()).shortValue()));
                    newStoredEnch.add(enchantmentEntry);
                }
                tag.remove("StoredEnchantments");
                tag.put(newStoredEnch);
            }
            if (tag.get("CanPlaceOn") instanceof ListTag) {
                old = (ListTag)tag.get("CanPlaceOn");
                ListTag newCanPlaceOn = new ListTag("CanPlaceOn", StringTag.class);
                tag.put(ConverterRegistry.convertToTag(NBT_TAG_NAME + "|CanPlaceOn", ConverterRegistry.convertToValue(old)));
                for (Tag oldTag : old) {
                    value = oldTag.getValue();
                    String oldId2 = value.toString().replace("minecraft:", "");
                    numberConverted = BlockIdData.numberIdToString.get(Ints.tryParse((String)oldId2));
                    if (numberConverted != null) {
                        oldId2 = numberConverted;
                    }
                    if ((newValues = BlockIdData.blockIdMapping.get(oldId2.toLowerCase(Locale.ROOT))) != null) {
                        for (String newValue : newValues) {
                            newCanPlaceOn.add(new StringTag("", newValue));
                        }
                        continue;
                    }
                    newCanPlaceOn.add(new StringTag("", oldId2.toLowerCase(Locale.ROOT)));
                }
                tag.put(newCanPlaceOn);
            }
            if (tag.get("CanDestroy") instanceof ListTag) {
                old = (ListTag)tag.get("CanDestroy");
                ListTag newCanDestroy = new ListTag("CanDestroy", StringTag.class);
                tag.put(ConverterRegistry.convertToTag(NBT_TAG_NAME + "|CanDestroy", ConverterRegistry.convertToValue(old)));
                for (Tag oldTag : old) {
                    value = oldTag.getValue();
                    String oldId3 = value.toString().replace("minecraft:", "");
                    numberConverted = BlockIdData.numberIdToString.get(Ints.tryParse((String)oldId3));
                    if (numberConverted != null) {
                        oldId3 = numberConverted;
                    }
                    if ((newValues = BlockIdData.blockIdMapping.get(oldId3.toLowerCase(Locale.ROOT))) != null) {
                        for (String newValue : newValues) {
                            newCanDestroy.add(new StringTag("", newValue));
                        }
                        continue;
                    }
                    newCanDestroy.add(new StringTag("", oldId3.toLowerCase(Locale.ROOT)));
                }
                tag.put(newCanDestroy);
            }
            if (item.getIdentifier() == 383) {
                if (tag.get("EntityTag") instanceof CompoundTag) {
                    CompoundTag entityTag = (CompoundTag)tag.get("EntityTag");
                    if (entityTag.get("id") instanceof StringTag) {
                        StringTag identifier = (StringTag)entityTag.get("id");
                        rawId = SpawnEggRewriter.getSpawnEggId(identifier.getValue());
                        if (rawId == -1) {
                            rawId = 25100288;
                        } else {
                            entityTag.remove("id");
                            if (entityTag.isEmpty()) {
                                tag.remove("EntityTag");
                            }
                        }
                    } else {
                        rawId = 25100288;
                    }
                } else {
                    rawId = 25100288;
                }
            }
            if (tag.isEmpty()) {
                tag = null;
                item.setTag(null);
            }
        }
        if (!Protocol1_13To1_12_2.MAPPINGS.getItemMappings().containsKey(rawId)) {
            if (!InventoryPackets.isDamageable(item.getIdentifier()) && item.getIdentifier() != 358) {
                if (tag == null) {
                    tag = new CompoundTag("tag");
                    item.setTag(tag);
                }
                tag.put(new IntTag(NBT_TAG_NAME, originalId));
            }
            if (item.getIdentifier() == 31 && item.getData() == 0) {
                rawId = 512;
            } else if (Protocol1_13To1_12_2.MAPPINGS.getItemMappings().containsKey(rawId & 0xFFFFFFF0)) {
                rawId &= 0xFFFFFFF0;
            } else {
                if (!Via.getConfig().isSuppressConversionWarnings() || Via.getManager().isDebug()) {
                    Via.getPlatform().getLogger().warning("Failed to get 1.13 item for " + item.getIdentifier());
                }
                rawId = 16;
            }
        }
        item.setIdentifier(Protocol1_13To1_12_2.MAPPINGS.getItemMappings().get(rawId));
        item.setData((short)0);
    }

    public static String getNewPluginChannelId(String old) {
        switch (old) {
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
        String mappedChannel = (String)Protocol1_13To1_12_2.MAPPINGS.getChannelMappings().get((Object)old);
        if (mappedChannel != null) {
            return mappedChannel;
        }
        return MappingData.isValid1_13Channel(old) ? old : null;
    }

    public static void toServer(Item item) {
        int oldId;
        if (item == null) {
            return;
        }
        Integer rawId = null;
        boolean gotRawIdFromTag = false;
        CompoundTag tag = item.getTag();
        if (tag != null && tag.get(NBT_TAG_NAME) instanceof IntTag) {
            rawId = (Integer)((Tag)tag.get(NBT_TAG_NAME)).getValue();
            tag.remove(NBT_TAG_NAME);
            gotRawIdFromTag = true;
        }
        if (rawId == null && (oldId = Protocol1_13To1_12_2.MAPPINGS.getItemMappings().inverse().get(item.getIdentifier())) != -1) {
            Optional<String> eggEntityId = SpawnEggRewriter.getEntityId(oldId);
            if (eggEntityId.isPresent()) {
                rawId = 25100288;
                if (tag == null) {
                    tag = new CompoundTag("tag");
                    item.setTag(tag);
                }
                if (!tag.contains("EntityTag")) {
                    CompoundTag entityTag = new CompoundTag("EntityTag");
                    entityTag.put(new StringTag("id", eggEntityId.get()));
                    tag.put(entityTag);
                }
            } else {
                rawId = oldId >> 4 << 16 | oldId & 0xF;
            }
        }
        if (rawId == null) {
            if (!Via.getConfig().isSuppressConversionWarnings() || Via.getManager().isDebug()) {
                Via.getPlatform().getLogger().warning("Failed to get 1.12 item for " + item.getIdentifier());
            }
            rawId = 65536;
        }
        item.setIdentifier((short)(rawId >> 16));
        item.setData((short)(rawId & 0xFFFF));
        if (tag != null) {
            String[] newValues;
            Object value;
            Short oldId2;
            String newId;
            CompoundTag enchEntry;
            CompoundTag display;
            if (InventoryPackets.isDamageable(item.getIdentifier()) && tag.get("Damage") instanceof IntTag) {
                if (!gotRawIdFromTag) {
                    item.setData((short)((Integer)((Tag)tag.get("Damage")).getValue()).intValue());
                }
                tag.remove("Damage");
            }
            if (item.getIdentifier() == 358 && tag.get("map") instanceof IntTag) {
                if (!gotRawIdFromTag) {
                    item.setData((short)((Integer)((Tag)tag.get("map")).getValue()).intValue());
                }
                tag.remove("map");
            }
            if ((item.getIdentifier() == 442 || item.getIdentifier() == 425) && tag.get("BlockEntityTag") instanceof CompoundTag) {
                CompoundTag blockEntityTag = (CompoundTag)tag.get("BlockEntityTag");
                if (blockEntityTag.get("Base") instanceof IntTag) {
                    IntTag base = (IntTag)blockEntityTag.get("Base");
                    base.setValue(15 - base.getValue());
                }
                if (blockEntityTag.get("Patterns") instanceof ListTag) {
                    for (Tag pattern : (ListTag)blockEntityTag.get("Patterns")) {
                        if (!(pattern instanceof CompoundTag)) continue;
                        IntTag c = (IntTag)((CompoundTag)pattern).get("Color");
                        c.setValue(15 - c.getValue());
                    }
                }
            }
            if (tag.get("display") instanceof CompoundTag && (display = (CompoundTag)tag.get("display")).get("Name") instanceof StringTag) {
                StringTag name = (StringTag)display.get("Name");
                StringTag via = (StringTag)display.remove(NBT_TAG_NAME + "|Name");
                name.setValue(via != null ? via.getValue() : ChatRewriter.jsonTextToLegacy(name.getValue()));
            }
            if (tag.get("Enchantments") instanceof ListTag) {
                ListTag enchantments = (ListTag)tag.get("Enchantments");
                ListTag ench = new ListTag("ench", CompoundTag.class);
                for (Tag enchantmentEntry : enchantments) {
                    if (!(enchantmentEntry instanceof CompoundTag)) continue;
                    enchEntry = new CompoundTag("");
                    newId = (String)((Tag)((CompoundTag)enchantmentEntry).get("id")).getValue();
                    oldId2 = (Short)Protocol1_13To1_12_2.MAPPINGS.getOldEnchantmentsIds().inverse().get((Object)newId);
                    if (oldId2 == null && newId.startsWith("viaversion:legacy/")) {
                        oldId2 = Short.valueOf(newId.substring(18));
                    }
                    if (oldId2 == null) continue;
                    enchEntry.put(new ShortTag("id", oldId2));
                    enchEntry.put(new ShortTag("lvl", (Short)((Tag)((CompoundTag)enchantmentEntry).get("lvl")).getValue()));
                    ench.add(enchEntry);
                }
                tag.remove("Enchantments");
                tag.put(ench);
            }
            if (tag.get("StoredEnchantments") instanceof ListTag) {
                ListTag storedEnch = (ListTag)tag.get("StoredEnchantments");
                ListTag newStoredEnch = new ListTag("StoredEnchantments", CompoundTag.class);
                for (Tag enchantmentEntry : storedEnch) {
                    if (!(enchantmentEntry instanceof CompoundTag)) continue;
                    enchEntry = new CompoundTag("");
                    newId = (String)((Tag)((CompoundTag)enchantmentEntry).get("id")).getValue();
                    oldId2 = (Short)Protocol1_13To1_12_2.MAPPINGS.getOldEnchantmentsIds().inverse().get((Object)newId);
                    if (oldId2 == null && newId.startsWith("viaversion:legacy/")) {
                        oldId2 = Short.valueOf(newId.substring(18));
                    }
                    if (oldId2 == null) continue;
                    enchEntry.put(new ShortTag("id", oldId2));
                    enchEntry.put(new ShortTag("lvl", (Short)((Tag)((CompoundTag)enchantmentEntry).get("lvl")).getValue()));
                    newStoredEnch.add(enchEntry);
                }
                tag.remove("StoredEnchantments");
                tag.put(newStoredEnch);
            }
            if (tag.get(NBT_TAG_NAME + "|CanPlaceOn") instanceof ListTag) {
                tag.put(ConverterRegistry.convertToTag("CanPlaceOn", ConverterRegistry.convertToValue(tag.get(NBT_TAG_NAME + "|CanPlaceOn"))));
                tag.remove(NBT_TAG_NAME + "|CanPlaceOn");
            } else if (tag.get("CanPlaceOn") instanceof ListTag) {
                ListTag old = (ListTag)tag.get("CanPlaceOn");
                ListTag newCanPlaceOn = new ListTag("CanPlaceOn", StringTag.class);
                for (Tag oldTag : old) {
                    value = oldTag.getValue();
                    newValues = BlockIdData.fallbackReverseMapping.get(value instanceof String ? ((String)value).replace("minecraft:", "") : null);
                    if (newValues != null) {
                        for (String newValue : newValues) {
                            newCanPlaceOn.add(new StringTag("", newValue));
                        }
                        continue;
                    }
                    newCanPlaceOn.add(oldTag);
                }
                tag.put(newCanPlaceOn);
            }
            if (tag.get(NBT_TAG_NAME + "|CanDestroy") instanceof ListTag) {
                tag.put(ConverterRegistry.convertToTag("CanDestroy", ConverterRegistry.convertToValue(tag.get(NBT_TAG_NAME + "|CanDestroy"))));
                tag.remove(NBT_TAG_NAME + "|CanDestroy");
            } else if (tag.get("CanDestroy") instanceof ListTag) {
                ListTag old = (ListTag)tag.get("CanDestroy");
                ListTag newCanDestroy = new ListTag("CanDestroy", StringTag.class);
                for (Tag oldTag : old) {
                    value = oldTag.getValue();
                    newValues = BlockIdData.fallbackReverseMapping.get(value instanceof String ? ((String)value).replace("minecraft:", "") : null);
                    if (newValues != null) {
                        for (String newValue : newValues) {
                            newCanDestroy.add(new StringTag("", newValue));
                        }
                        continue;
                    }
                    newCanDestroy.add(oldTag);
                }
                tag.put(newCanDestroy);
            }
        }
    }

    public static String getOldPluginChannelId(String newId) {
        if ((newId = MappingData.validateNewChannel(newId)) == null) {
            return null;
        }
        switch (newId) {
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
        String mappedChannel = (String)Protocol1_13To1_12_2.MAPPINGS.getChannelMappings().inverse().get((Object)newId);
        if (mappedChannel != null) {
            return mappedChannel;
        }
        return newId.length() > 20 ? newId.substring(0, 20) : newId;
    }

    public static boolean isDamageable(int id) {
        return id >= 256 && id <= 259 || id == 261 || id >= 267 && id <= 279 || id >= 283 && id <= 286 || id >= 290 && id <= 294 || id >= 298 && id <= 317 || id == 346 || id == 359 || id == 398 || id == 442 || id == 443;
    }
}

