/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.google.common.primitives.Ints
 */
package nl.matsv.viabackwards.protocol.protocol1_12_2to1_13.packets;

import com.google.common.primitives.Ints;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import nl.matsv.viabackwards.ViaBackwards;
import nl.matsv.viabackwards.api.rewriters.EnchantmentRewriter;
import nl.matsv.viabackwards.protocol.protocol1_12_2to1_13.Protocol1_12_2To1_13;
import nl.matsv.viabackwards.protocol.protocol1_12_2to1_13.block_entity_handlers.FlowerPotHandler;
import nl.matsv.viabackwards.protocol.protocol1_12_2to1_13.providers.BackwardsBlockEntityProvider;
import nl.matsv.viabackwards.protocol.protocol1_12_2to1_13.storage.BackwardsBlockStorage;
import us.myles.ViaVersion.api.PacketWrapper;
import us.myles.ViaVersion.api.Via;
import us.myles.ViaVersion.api.data.UserConnection;
import us.myles.ViaVersion.api.minecraft.BlockChangeRecord;
import us.myles.ViaVersion.api.minecraft.Position;
import us.myles.ViaVersion.api.minecraft.chunks.Chunk;
import us.myles.ViaVersion.api.minecraft.chunks.ChunkSection;
import us.myles.ViaVersion.api.minecraft.item.Item;
import us.myles.ViaVersion.api.remapper.PacketHandler;
import us.myles.ViaVersion.api.remapper.PacketRemapper;
import us.myles.ViaVersion.api.rewriters.ItemRewriter;
import us.myles.ViaVersion.api.type.Type;
import us.myles.ViaVersion.protocols.protocol1_12_1to1_12.ServerboundPackets1_12_1;
import us.myles.ViaVersion.protocols.protocol1_13to1_12_2.ChatRewriter;
import us.myles.ViaVersion.protocols.protocol1_13to1_12_2.ClientboundPackets1_13;
import us.myles.ViaVersion.protocols.protocol1_13to1_12_2.Protocol1_13To1_12_2;
import us.myles.ViaVersion.protocols.protocol1_13to1_12_2.data.BlockIdData;
import us.myles.ViaVersion.protocols.protocol1_13to1_12_2.data.SpawnEggRewriter;
import us.myles.ViaVersion.protocols.protocol1_13to1_12_2.types.Chunk1_13Type;
import us.myles.ViaVersion.protocols.protocol1_9_1_2to1_9_3_4.types.Chunk1_9_3_4Type;
import us.myles.ViaVersion.protocols.protocol1_9_3to1_9_1_2.storage.ClientWorld;
import us.myles.viaversion.libs.opennbt.conversion.ConverterRegistry;
import us.myles.viaversion.libs.opennbt.tag.builtin.ByteTag;
import us.myles.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import us.myles.viaversion.libs.opennbt.tag.builtin.IntTag;
import us.myles.viaversion.libs.opennbt.tag.builtin.ListTag;
import us.myles.viaversion.libs.opennbt.tag.builtin.ShortTag;
import us.myles.viaversion.libs.opennbt.tag.builtin.StringTag;
import us.myles.viaversion.libs.opennbt.tag.builtin.Tag;

public class BlockItemPackets1_13
extends nl.matsv.viabackwards.api.rewriters.ItemRewriter<Protocol1_12_2To1_13> {
    private final Map<String, String> enchantmentMappings = new HashMap<String, String>();
    private final String extraNbtTag;

    public BlockItemPackets1_13(Protocol1_12_2To1_13 protocol) {
        super(protocol, null);
        this.extraNbtTag = "VB|" + protocol.getClass().getSimpleName() + "|2";
    }

    public static boolean isDamageable(int id) {
        return id >= 256 && id <= 259 || id == 261 || id >= 267 && id <= 279 || id >= 283 && id <= 286 || id >= 290 && id <= 294 || id >= 298 && id <= 317 || id == 346 || id == 359 || id == 398 || id == 442 || id == 443;
    }

    @Override
    protected void registerPackets() {
        ((Protocol1_12_2To1_13)this.protocol).registerOutgoing(ClientboundPackets1_13.COOLDOWN, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper wrapper) throws Exception {
                        int itemId = wrapper.read(Type.VAR_INT);
                        int oldId = ((Protocol1_12_2To1_13)BlockItemPackets1_13.this.protocol).getMappingData().getItemMappings().get(itemId);
                        if (oldId != -1) {
                            Optional<String> eggEntityId = SpawnEggRewriter.getEntityId(oldId);
                            itemId = eggEntityId.isPresent() ? 25100288 : oldId >> 4 << 16 | oldId & 0xF;
                        }
                        wrapper.write(Type.VAR_INT, itemId);
                    }
                });
            }
        });
        ((Protocol1_12_2To1_13)this.protocol).registerOutgoing(ClientboundPackets1_13.BLOCK_ACTION, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.POSITION);
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.VAR_INT);
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper wrapper) throws Exception {
                        int blockId = wrapper.get(Type.VAR_INT, 0);
                        if (blockId == 73) {
                            blockId = 25;
                        } else if (blockId == 99) {
                            blockId = 33;
                        } else if (blockId == 92) {
                            blockId = 29;
                        } else if (blockId == 142) {
                            blockId = 54;
                        } else if (blockId == 305) {
                            blockId = 146;
                        } else if (blockId == 249) {
                            blockId = 130;
                        } else if (blockId == 257) {
                            blockId = 138;
                        } else if (blockId == 140) {
                            blockId = 52;
                        } else if (blockId == 472) {
                            blockId = 209;
                        } else if (blockId >= 483 && blockId <= 498) {
                            blockId = blockId - 483 + 219;
                        }
                        wrapper.set(Type.VAR_INT, 0, blockId);
                    }
                });
            }
        });
        ((Protocol1_12_2To1_13)this.protocol).registerOutgoing(ClientboundPackets1_13.BLOCK_ENTITY_DATA, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.POSITION);
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.NBT);
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper wrapper) throws Exception {
                        BackwardsBlockEntityProvider provider = Via.getManager().getProviders().get(BackwardsBlockEntityProvider.class);
                        if (wrapper.get(Type.UNSIGNED_BYTE, 0) == 5) {
                            wrapper.cancel();
                        }
                        wrapper.set(Type.NBT, 0, provider.transform(wrapper.user(), wrapper.get(Type.POSITION, 0), wrapper.get(Type.NBT, 0)));
                    }
                });
            }
        });
        ((Protocol1_12_2To1_13)this.protocol).registerOutgoing(ClientboundPackets1_13.UNLOAD_CHUNK, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper wrapper) throws Exception {
                        int chunkMinX = wrapper.passthrough(Type.INT) << 4;
                        int chunkMinZ = wrapper.passthrough(Type.INT) << 4;
                        int chunkMaxX = chunkMinX + 15;
                        int chunkMaxZ = chunkMinZ + 15;
                        BackwardsBlockStorage blockStorage = wrapper.user().get(BackwardsBlockStorage.class);
                        blockStorage.getBlocks().entrySet().removeIf(entry -> {
                            Position position = (Position)entry.getKey();
                            return position.getX() >= chunkMinX && position.getZ() >= chunkMinZ && position.getX() <= chunkMaxX && position.getZ() <= chunkMaxZ;
                        });
                    }
                });
            }
        });
        ((Protocol1_12_2To1_13)this.protocol).registerOutgoing(ClientboundPackets1_13.BLOCK_CHANGE, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.POSITION);
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper wrapper) throws Exception {
                        int blockState = wrapper.read(Type.VAR_INT);
                        Position position = wrapper.get(Type.POSITION, 0);
                        BackwardsBlockStorage storage = wrapper.user().get(BackwardsBlockStorage.class);
                        storage.checkAndStore(position, blockState);
                        wrapper.write(Type.VAR_INT, ((Protocol1_12_2To1_13)BlockItemPackets1_13.this.protocol).getMappingData().getNewBlockStateId(blockState));
                        BlockItemPackets1_13.flowerPotSpecialTreatment(wrapper.user(), blockState, position);
                    }
                });
            }
        });
        ((Protocol1_12_2To1_13)this.protocol).registerOutgoing(ClientboundPackets1_13.MULTI_BLOCK_CHANGE, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.INT);
                this.map(Type.INT);
                this.map(Type.BLOCK_CHANGE_RECORD_ARRAY);
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper wrapper) throws Exception {
                        BackwardsBlockStorage storage = wrapper.user().get(BackwardsBlockStorage.class);
                        for (BlockChangeRecord record : wrapper.get(Type.BLOCK_CHANGE_RECORD_ARRAY, 0)) {
                            int chunkX = wrapper.get(Type.INT, 0);
                            int chunkZ = wrapper.get(Type.INT, 1);
                            int block = record.getBlockId();
                            Position position = new Position(record.getSectionX() + chunkX * 16, record.getY(), record.getSectionZ() + chunkZ * 16);
                            storage.checkAndStore(position, block);
                            BlockItemPackets1_13.flowerPotSpecialTreatment(wrapper.user(), block, position);
                            record.setBlockId(((Protocol1_12_2To1_13)BlockItemPackets1_13.this.protocol).getMappingData().getNewBlockStateId(block));
                        }
                    }
                });
            }
        });
        final ItemRewriter itemRewriter = new ItemRewriter(this.protocol, this::handleItemToClient, this::handleItemToServer);
        ((Protocol1_12_2To1_13)this.protocol).registerOutgoing(ClientboundPackets1_13.WINDOW_ITEMS, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.FLAT_ITEM_ARRAY, Type.ITEM_ARRAY);
                this.handler(itemRewriter.itemArrayHandler(Type.ITEM_ARRAY));
            }
        });
        ((Protocol1_12_2To1_13)this.protocol).registerOutgoing(ClientboundPackets1_13.SET_SLOT, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.BYTE);
                this.map(Type.SHORT);
                this.map(Type.FLAT_ITEM, Type.ITEM);
                this.handler(itemRewriter.itemToClientHandler(Type.ITEM));
            }
        });
        ((Protocol1_12_2To1_13)this.protocol).registerOutgoing(ClientboundPackets1_13.CHUNK_DATA, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.handler(wrapper -> {
                    int i;
                    ClientWorld clientWorld = wrapper.user().get(ClientWorld.class);
                    Chunk1_9_3_4Type type_old = new Chunk1_9_3_4Type(clientWorld);
                    Chunk1_13Type type = new Chunk1_13Type(clientWorld);
                    Chunk chunk = wrapper.read(type);
                    BackwardsBlockEntityProvider provider = Via.getManager().getProviders().get(BackwardsBlockEntityProvider.class);
                    BackwardsBlockStorage storage = wrapper.user().get(BackwardsBlockStorage.class);
                    for (CompoundTag tag : chunk.getBlockEntities()) {
                        String id;
                        Object idTag = tag.get("id");
                        if (idTag == null || !provider.isHandled(id = (String)((Tag)idTag).getValue())) continue;
                        int sectionIndex = (Integer)((Tag)tag.get("y")).getValue() >> 4;
                        ChunkSection section = chunk.getSections()[sectionIndex];
                        int x = (Integer)((Tag)tag.get("x")).getValue();
                        int y = (Integer)((Tag)tag.get("y")).getValue();
                        int z = (Integer)((Tag)tag.get("z")).getValue();
                        Position position = new Position(x, (short)y, z);
                        int block = section.getFlatBlock(x & 0xF, y & 0xF, z & 0xF);
                        storage.checkAndStore(position, block);
                        provider.transform(wrapper.user(), position, tag);
                    }
                    for (i = 0; i < chunk.getSections().length; ++i) {
                        ChunkSection section = chunk.getSections()[i];
                        if (section == null) continue;
                        for (int y = 0; y < 16; ++y) {
                            for (int z = 0; z < 16; ++z) {
                                for (int x = 0; x < 16; ++x) {
                                    int block = section.getFlatBlock(x, y, z);
                                    if (!FlowerPotHandler.isFlowah(block)) continue;
                                    Position pos = new Position(x + (chunk.getX() << 4), (short)(y + (i << 4)), z + (chunk.getZ() << 4));
                                    storage.checkAndStore(pos, block);
                                    CompoundTag nbt = provider.transform(wrapper.user(), pos, "minecraft:flower_pot");
                                    chunk.getBlockEntities().add(nbt);
                                }
                            }
                        }
                        for (int p = 0; p < section.getPaletteSize(); ++p) {
                            int old = section.getPaletteEntry(p);
                            if (old == 0) continue;
                            int oldId = ((Protocol1_12_2To1_13)BlockItemPackets1_13.this.protocol).getMappingData().getNewBlockStateId(old);
                            section.setPaletteEntry(p, oldId);
                        }
                    }
                    if (chunk.isBiomeData()) {
                        for (i = 0; i < 256; ++i) {
                            int biome = chunk.getBiomeData()[i];
                            int newId = -1;
                            switch (biome) {
                                case 40: 
                                case 41: 
                                case 42: 
                                case 43: {
                                    newId = 9;
                                    break;
                                }
                                case 47: 
                                case 48: 
                                case 49: {
                                    newId = 24;
                                    break;
                                }
                                case 50: {
                                    newId = 10;
                                    break;
                                }
                                case 44: 
                                case 45: 
                                case 46: {
                                    newId = 0;
                                }
                            }
                            if (newId == -1) continue;
                            chunk.getBiomeData()[i] = newId;
                        }
                    }
                    wrapper.write(type_old, chunk);
                });
            }
        });
        ((Protocol1_12_2To1_13)this.protocol).registerOutgoing(ClientboundPackets1_13.EFFECT, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.INT);
                this.map(Type.POSITION);
                this.map(Type.INT);
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper wrapper) throws Exception {
                        int id = wrapper.get(Type.INT, 0);
                        int data = wrapper.get(Type.INT, 1);
                        if (id == 1010) {
                            wrapper.set(Type.INT, 1, ((Protocol1_12_2To1_13)BlockItemPackets1_13.this.protocol).getMappingData().getItemMappings().get(data) >> 4);
                        } else if (id == 2001) {
                            data = ((Protocol1_12_2To1_13)BlockItemPackets1_13.this.protocol).getMappingData().getNewBlockStateId(data);
                            int blockId = data >> 4;
                            int blockData = data & 0xF;
                            wrapper.set(Type.INT, 1, blockId & 0xFFF | blockData << 12);
                        }
                    }
                });
            }
        });
        ((Protocol1_12_2To1_13)this.protocol).registerOutgoing(ClientboundPackets1_13.MAP_DATA, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.map(Type.BYTE);
                this.map(Type.BOOLEAN);
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper wrapper) throws Exception {
                        int iconCount = wrapper.passthrough(Type.VAR_INT);
                        for (int i = 0; i < iconCount; ++i) {
                            int type = wrapper.read(Type.VAR_INT);
                            byte x = wrapper.read(Type.BYTE);
                            byte z = wrapper.read(Type.BYTE);
                            byte direction = wrapper.read(Type.BYTE);
                            if (wrapper.read(Type.BOOLEAN).booleanValue()) {
                                wrapper.read(Type.COMPONENT);
                            }
                            if (type > 9) {
                                wrapper.set(Type.VAR_INT, 1, wrapper.get(Type.VAR_INT, 1) - 1);
                                continue;
                            }
                            wrapper.write(Type.BYTE, (byte)(type << 4 | direction & 0xF));
                            wrapper.write(Type.BYTE, x);
                            wrapper.write(Type.BYTE, z);
                        }
                    }
                });
            }
        });
        ((Protocol1_12_2To1_13)this.protocol).registerOutgoing(ClientboundPackets1_13.ENTITY_EQUIPMENT, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.map(Type.VAR_INT);
                this.map(Type.FLAT_ITEM, Type.ITEM);
                this.handler(itemRewriter.itemToClientHandler(Type.ITEM));
            }
        });
        ((Protocol1_12_2To1_13)this.protocol).registerOutgoing(ClientboundPackets1_13.WINDOW_PROPERTY, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.SHORT);
                this.map(Type.SHORT);
                this.handler(wrapper -> {
                    short property = wrapper.get(Type.SHORT, 0);
                    if (property >= 4 && property <= 6) {
                        short oldId = wrapper.get(Type.SHORT, 1);
                        wrapper.set(Type.SHORT, 1, (short)((Protocol1_12_2To1_13)BlockItemPackets1_13.this.protocol).getMappingData().getEnchantmentMappings().getNewId(oldId));
                    }
                });
            }
        });
        ((Protocol1_12_2To1_13)this.protocol).registerIncoming(ServerboundPackets1_12_1.CREATIVE_INVENTORY_ACTION, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.SHORT);
                this.map(Type.ITEM, Type.FLAT_ITEM);
                this.handler(itemRewriter.itemToServerHandler(Type.FLAT_ITEM));
            }
        });
        ((Protocol1_12_2To1_13)this.protocol).registerIncoming(ServerboundPackets1_12_1.CLICK_WINDOW, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.SHORT);
                this.map(Type.BYTE);
                this.map(Type.SHORT);
                this.map(Type.VAR_INT);
                this.map(Type.ITEM, Type.FLAT_ITEM);
                this.handler(itemRewriter.itemToServerHandler(Type.FLAT_ITEM));
            }
        });
    }

    @Override
    protected void registerRewrites() {
        this.enchantmentMappings.put("minecraft:loyalty", "\u00a77Loyalty");
        this.enchantmentMappings.put("minecraft:impaling", "\u00a77Impaling");
        this.enchantmentMappings.put("minecraft:riptide", "\u00a77Riptide");
        this.enchantmentMappings.put("minecraft:channeling", "\u00a77Channeling");
    }

    @Override
    public Item handleItemToClient(Item item) {
        Object originalIdTag;
        if (item == null) {
            return null;
        }
        int originalId = item.getIdentifier();
        Integer rawId = null;
        boolean gotRawIdFromTag = false;
        CompoundTag tag = item.getTag();
        if (tag != null && (originalIdTag = tag.remove(this.extraNbtTag)) != null) {
            rawId = (Integer)((Tag)originalIdTag).getValue();
            gotRawIdFromTag = true;
        }
        if (rawId == null) {
            super.handleItemToClient(item);
            if (item.getIdentifier() == -1) {
                if (originalId == 362) {
                    rawId = 0xE50000;
                } else {
                    if (!Via.getConfig().isSuppressConversionWarnings() || Via.getManager().isDebug()) {
                        ViaBackwards.getPlatform().getLogger().warning("Failed to get 1.12 item for " + originalId);
                    }
                    rawId = 65536;
                }
            } else {
                if (tag == null) {
                    tag = item.getTag();
                }
                rawId = this.itemIdToRaw(item.getIdentifier(), item, tag);
            }
        }
        item.setIdentifier(rawId >> 16);
        item.setData((short)(rawId & 0xFFFF));
        if (tag != null) {
            StringTag name;
            if (BlockItemPackets1_13.isDamageable(item.getIdentifier())) {
                Object damageTag = tag.remove("Damage");
                if (!gotRawIdFromTag && damageTag instanceof IntTag) {
                    item.setData((short)((Integer)((Tag)damageTag).getValue()).intValue());
                }
            }
            if (item.getIdentifier() == 358) {
                Object mapTag = tag.remove("map");
                if (!gotRawIdFromTag && mapTag instanceof IntTag) {
                    item.setData((short)((Integer)((Tag)mapTag).getValue()).intValue());
                }
            }
            this.invertShieldAndBannerId(item, tag);
            CompoundTag display = (CompoundTag)tag.get("display");
            if (display != null && (name = (StringTag)display.get("Name")) instanceof StringTag) {
                display.put(new StringTag(this.extraNbtTag + "|Name", name.getValue()));
                name.setValue(ChatRewriter.jsonTextToLegacy(name.getValue()));
            }
            this.rewriteEnchantmentsToClient(tag, false);
            this.rewriteEnchantmentsToClient(tag, true);
            this.rewriteCanPlaceToClient(tag, "CanPlaceOn");
            this.rewriteCanPlaceToClient(tag, "CanDestroy");
        }
        return item;
    }

    private int itemIdToRaw(int oldId, Item item, CompoundTag tag) {
        Optional<String> eggEntityId = SpawnEggRewriter.getEntityId(oldId);
        if (eggEntityId.isPresent()) {
            if (tag == null) {
                tag = new CompoundTag("tag");
                item.setTag(tag);
            }
            if (!tag.contains("EntityTag")) {
                CompoundTag entityTag = new CompoundTag("EntityTag");
                entityTag.put(new StringTag("id", eggEntityId.get()));
                tag.put(entityTag);
            }
            return 25100288;
        }
        return oldId >> 4 << 16 | oldId & 0xF;
    }

    private void rewriteCanPlaceToClient(CompoundTag tag, String tagName) {
        if (!(tag.get(tagName) instanceof ListTag)) {
            return;
        }
        ListTag blockTag = (ListTag)tag.get(tagName);
        if (blockTag == null) {
            return;
        }
        ListTag newCanPlaceOn = new ListTag(tagName, StringTag.class);
        tag.put(ConverterRegistry.convertToTag(this.extraNbtTag + "|" + tagName, ConverterRegistry.convertToValue(blockTag)));
        for (Tag oldTag : blockTag) {
            String[] newValues;
            Object value = oldTag.getValue();
            String[] arrstring = newValues = value instanceof String ? BlockIdData.fallbackReverseMapping.get(((String)value).replace("minecraft:", "")) : null;
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

    private void rewriteEnchantmentsToClient(CompoundTag tag, boolean storedEnch) {
        String key = storedEnch ? "StoredEnchantments" : "Enchantments";
        ListTag enchantments = (ListTag)tag.get(key);
        if (enchantments == null) {
            return;
        }
        ListTag noMapped = new ListTag(this.extraNbtTag + "|" + key, CompoundTag.class);
        ListTag newEnchantments = new ListTag(storedEnch ? key : "ench", CompoundTag.class);
        ArrayList<Tag> lore = new ArrayList<Tag>();
        boolean hasValidEnchants = false;
        for (Tag enchantmentEntryTag : enchantments.clone()) {
            CompoundTag enchantmentEntry = (CompoundTag)enchantmentEntryTag;
            String newId = (String)((Tag)enchantmentEntry.get("id")).getValue();
            Number levelValue = (Number)((Tag)enchantmentEntry.get("lvl")).getValue();
            int intValue = levelValue.intValue();
            short level = intValue < 32767 ? (short)levelValue.shortValue() : (short)32767;
            String mappedEnchantmentId = this.enchantmentMappings.get(newId);
            if (mappedEnchantmentId != null) {
                lore.add(new StringTag("", mappedEnchantmentId + " " + EnchantmentRewriter.getRomanNumber(level)));
                noMapped.add(enchantmentEntry);
                continue;
            }
            if (newId.isEmpty()) continue;
            Short oldId = (Short)Protocol1_13To1_12_2.MAPPINGS.getOldEnchantmentsIds().inverse().get((Object)newId);
            if (oldId == null) {
                if (!newId.startsWith("viaversion:legacy/")) {
                    noMapped.add(enchantmentEntry);
                    if (ViaBackwards.getConfig().addCustomEnchantsToLore()) {
                        String name = newId;
                        int index = name.indexOf(58) + 1;
                        if (index != 0 && index != name.length()) {
                            name = name.substring(index);
                        }
                        name = "\u00a77" + Character.toUpperCase(name.charAt(0)) + name.substring(1).toLowerCase(Locale.ENGLISH);
                        lore.add(new StringTag("", name + " " + EnchantmentRewriter.getRomanNumber(level)));
                    }
                    if (!Via.getManager().isDebug()) continue;
                    ViaBackwards.getPlatform().getLogger().warning("Found unknown enchant: " + newId);
                    continue;
                }
                oldId = Short.valueOf(newId.substring(18));
            }
            if (level != 0) {
                hasValidEnchants = true;
            }
            CompoundTag newEntry = new CompoundTag("");
            newEntry.put(new ShortTag("id", oldId));
            newEntry.put(new ShortTag("lvl", level));
            newEnchantments.add(newEntry);
        }
        if (!storedEnch && !hasValidEnchants) {
            IntTag hideFlags = (IntTag)tag.get("HideFlags");
            if (hideFlags == null) {
                hideFlags = new IntTag("HideFlags");
                tag.put(new ByteTag(this.extraNbtTag + "|DummyEnchant"));
            } else {
                tag.put(new IntTag(this.extraNbtTag + "|OldHideFlags", hideFlags.getValue()));
            }
            if (newEnchantments.size() == 0) {
                CompoundTag enchEntry = new CompoundTag("");
                enchEntry.put(new ShortTag("id", 0));
                enchEntry.put(new ShortTag("lvl", 0));
                newEnchantments.add(enchEntry);
            }
            int value = hideFlags.getValue() | 1;
            hideFlags.setValue(value);
            tag.put(hideFlags);
        }
        if (noMapped.size() != 0) {
            tag.put(noMapped);
            if (!lore.isEmpty()) {
                ListTag loreTag;
                CompoundTag display = (CompoundTag)tag.get("display");
                if (display == null) {
                    display = new CompoundTag("display");
                    tag.put(display);
                }
                if ((loreTag = (ListTag)display.get("Lore")) == null) {
                    loreTag = new ListTag("Lore", StringTag.class);
                    display.put(loreTag);
                    tag.put(new ByteTag(this.extraNbtTag + "|DummyLore"));
                } else if (loreTag.size() != 0) {
                    ListTag oldLore = new ListTag(this.extraNbtTag + "|OldLore", StringTag.class);
                    for (Tag value : loreTag) {
                        oldLore.add(value.clone());
                    }
                    tag.put(oldLore);
                    lore.addAll((Collection<Tag>)loreTag.getValue());
                }
                loreTag.setValue(lore);
            }
        }
        tag.remove("Enchantments");
        tag.put(newEnchantments);
    }

    @Override
    public Item handleItemToServer(Item item) {
        if (item == null) {
            return null;
        }
        CompoundTag tag = item.getTag();
        int originalId = item.getIdentifier() << 16 | item.getData() & 0xFFFF;
        int rawId = item.getIdentifier() << 4 | item.getData() & 0xF;
        if (BlockItemPackets1_13.isDamageable(item.getIdentifier())) {
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
            CompoundTag displayTag;
            StringTag name;
            this.invertShieldAndBannerId(item, tag);
            Object display = tag.get("display");
            if (display instanceof CompoundTag && (name = (StringTag)(displayTag = (CompoundTag)display).get("Name")) instanceof StringTag) {
                StringTag via = (StringTag)displayTag.remove(this.extraNbtTag + "|Name");
                name.setValue(via != null ? via.getValue() : ChatRewriter.legacyTextToJson(name.getValue()).toString());
            }
            this.rewriteEnchantmentsToServer(tag, false);
            this.rewriteEnchantmentsToServer(tag, true);
            this.rewriteCanPlaceToServer(tag, "CanPlaceOn");
            this.rewriteCanPlaceToServer(tag, "CanDestroy");
            if (item.getIdentifier() == 383) {
                StringTag identifier;
                CompoundTag entityTag = (CompoundTag)tag.get("EntityTag");
                if (entityTag != null && (identifier = (StringTag)entityTag.get("id")) != null) {
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
            }
            if (tag.isEmpty()) {
                tag = null;
                item.setTag(null);
            }
        }
        int identifier = item.getIdentifier();
        item.setIdentifier(rawId);
        super.handleItemToServer(item);
        if (item.getIdentifier() != rawId && item.getIdentifier() != -1) {
            return item;
        }
        item.setIdentifier(identifier);
        int newId = -1;
        if (!((Protocol1_12_2To1_13)this.protocol).getMappingData().getItemMappings().inverse().containsKey(rawId)) {
            if (!BlockItemPackets1_13.isDamageable(item.getIdentifier()) && item.getIdentifier() != 358) {
                if (tag == null) {
                    tag = new CompoundTag("tag");
                    item.setTag(tag);
                }
                tag.put(new IntTag(this.extraNbtTag, originalId));
            }
            if (item.getIdentifier() == 229) {
                newId = 362;
            } else if (item.getIdentifier() == 31 && item.getData() == 0) {
                rawId = 512;
            } else if (((Protocol1_12_2To1_13)this.protocol).getMappingData().getItemMappings().inverse().containsKey(rawId & 0xFFFFFFF0)) {
                rawId &= 0xFFFFFFF0;
            } else {
                if (!Via.getConfig().isSuppressConversionWarnings() || Via.getManager().isDebug()) {
                    ViaBackwards.getPlatform().getLogger().warning("Failed to get 1.13 item for " + item.getIdentifier());
                }
                rawId = 16;
            }
        }
        if (newId == -1) {
            newId = ((Protocol1_12_2To1_13)this.protocol).getMappingData().getItemMappings().inverse().get(rawId);
        }
        item.setIdentifier(newId);
        item.setData((short)0);
        return item;
    }

    private void rewriteCanPlaceToServer(CompoundTag tag, String tagName) {
        if (!(tag.get(tagName) instanceof ListTag)) {
            return;
        }
        ListTag blockTag = (ListTag)tag.remove(this.extraNbtTag + "|" + tagName);
        if (blockTag != null) {
            tag.put(ConverterRegistry.convertToTag(tagName, ConverterRegistry.convertToValue(blockTag)));
        } else {
            blockTag = (ListTag)tag.get(tagName);
            if (blockTag != null) {
                ListTag newCanPlaceOn = new ListTag(tagName, StringTag.class);
                for (Tag oldTag : blockTag) {
                    String lowerCaseId;
                    String[] newValues;
                    Object value = oldTag.getValue();
                    String oldId = value.toString().replace("minecraft:", "");
                    String numberConverted = BlockIdData.numberIdToString.get(Ints.tryParse((String)oldId));
                    if (numberConverted != null) {
                        oldId = numberConverted;
                    }
                    if ((newValues = BlockIdData.blockIdMapping.get(lowerCaseId = oldId.toLowerCase(Locale.ROOT))) != null) {
                        for (String newValue : newValues) {
                            newCanPlaceOn.add(new StringTag("", newValue));
                        }
                        continue;
                    }
                    newCanPlaceOn.add(new StringTag("", lowerCaseId));
                }
                tag.put(newCanPlaceOn);
            }
        }
    }

    private void rewriteEnchantmentsToServer(CompoundTag tag, boolean storedEnch) {
        ListTag oldLore;
        CompoundTag display;
        String key = storedEnch ? "StoredEnchantments" : "Enchantments";
        ListTag enchantments = (ListTag)tag.get(storedEnch ? key : "ench");
        if (enchantments == null) {
            return;
        }
        ListTag newEnchantments = new ListTag(key, CompoundTag.class);
        boolean dummyEnchant = false;
        if (!storedEnch) {
            IntTag hideFlags = (IntTag)tag.remove(this.extraNbtTag + "|OldHideFlags");
            if (hideFlags != null) {
                tag.put(new IntTag("HideFlags", hideFlags.getValue()));
                dummyEnchant = true;
            } else if (tag.remove(this.extraNbtTag + "|DummyEnchant") != null) {
                tag.remove("HideFlags");
                dummyEnchant = true;
            }
        }
        for (Object enchEntry : enchantments) {
            CompoundTag enchantmentEntry = new CompoundTag("");
            short oldId = ((Number)((Tag)((CompoundTag)enchEntry).get("id")).getValue()).shortValue();
            short level = ((Number)((Tag)((CompoundTag)enchEntry).get("lvl")).getValue()).shortValue();
            if (dummyEnchant && oldId == 0 && level == 0) continue;
            String newId = (String)Protocol1_13To1_12_2.MAPPINGS.getOldEnchantmentsIds().get((Object)oldId);
            if (newId == null) {
                newId = "viaversion:legacy/" + oldId;
            }
            enchantmentEntry.put(new StringTag("id", newId));
            enchantmentEntry.put(new ShortTag("lvl", level));
            newEnchantments.add(enchantmentEntry);
        }
        ListTag noMapped = (ListTag)tag.remove(this.extraNbtTag + "|Enchantments");
        if (noMapped != null) {
            for (Tag value : noMapped) {
                newEnchantments.add(value);
            }
        }
        if ((display = (CompoundTag)tag.get("display")) == null) {
            display = new CompoundTag("display");
            tag.put(display);
        }
        if ((oldLore = (ListTag)tag.remove(this.extraNbtTag + "|OldLore")) != null) {
            ListTag lore = (ListTag)display.get("Lore");
            if (lore == null) {
                lore = new ListTag("Lore");
                tag.put(lore);
            }
            lore.setValue((List<Tag>)oldLore.getValue());
        } else if (tag.remove(this.extraNbtTag + "|DummyLore") != null) {
            display.remove("Lore");
            if (display.isEmpty()) {
                tag.remove("display");
            }
        }
        if (!storedEnch) {
            tag.remove("ench");
        }
        tag.put(newEnchantments);
    }

    private void invertShieldAndBannerId(Item item, CompoundTag tag) {
        Object patterns;
        if (item.getIdentifier() != 442 && item.getIdentifier() != 425) {
            return;
        }
        Object blockEntityTag = tag.get("BlockEntityTag");
        if (!(blockEntityTag instanceof CompoundTag)) {
            return;
        }
        CompoundTag blockEntityCompoundTag = (CompoundTag)blockEntityTag;
        Object base = blockEntityCompoundTag.get("Base");
        if (base instanceof IntTag) {
            IntTag baseTag = (IntTag)base;
            baseTag.setValue(15 - baseTag.getValue());
        }
        if ((patterns = blockEntityCompoundTag.get("Patterns")) instanceof ListTag) {
            ListTag patternsTag = (ListTag)patterns;
            for (Tag pattern : patternsTag) {
                if (!(pattern instanceof CompoundTag)) continue;
                IntTag colorTag = (IntTag)((CompoundTag)pattern).get("Color");
                colorTag.setValue(15 - colorTag.getValue());
            }
        }
    }

    private static void flowerPotSpecialTreatment(UserConnection user, int blockState, Position position) throws Exception {
        if (FlowerPotHandler.isFlowah(blockState)) {
            BackwardsBlockEntityProvider beProvider = Via.getManager().getProviders().get(BackwardsBlockEntityProvider.class);
            CompoundTag nbt = beProvider.transform(user, position, "minecraft:flower_pot");
            PacketWrapper blockUpdateRemove = new PacketWrapper(11, null, user);
            blockUpdateRemove.write(Type.POSITION, position);
            blockUpdateRemove.write(Type.VAR_INT, 0);
            blockUpdateRemove.send(Protocol1_12_2To1_13.class, true);
            PacketWrapper blockCreate = new PacketWrapper(11, null, user);
            blockCreate.write(Type.POSITION, position);
            blockCreate.write(Type.VAR_INT, Protocol1_12_2To1_13.MAPPINGS.getNewBlockStateId(blockState));
            blockCreate.send(Protocol1_12_2To1_13.class, true);
            PacketWrapper wrapper = new PacketWrapper(9, null, user);
            wrapper.write(Type.POSITION, position);
            wrapper.write(Type.UNSIGNED_BYTE, (short)5);
            wrapper.write(Type.NBT, nbt);
            wrapper.send(Protocol1_12_2To1_13.class, true);
        }
    }
}

