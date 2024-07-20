/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viabackwards.protocol.protocol1_13_2to1_14.packets;

import com.google.common.collect.ImmutableSet;
import com.viaversion.viabackwards.ViaBackwards;
import com.viaversion.viabackwards.api.rewriters.EnchantmentRewriter;
import com.viaversion.viabackwards.api.rewriters.ItemRewriter;
import com.viaversion.viabackwards.protocol.protocol1_13_2to1_14.Protocol1_13_2To1_14;
import com.viaversion.viabackwards.protocol.protocol1_13_2to1_14.storage.ChunkLightStorage;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.minecraft.Environment;
import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
import com.viaversion.viaversion.api.minecraft.chunks.ChunkSection;
import com.viaversion.viaversion.api.minecraft.chunks.ChunkSectionLightImpl;
import com.viaversion.viaversion.api.minecraft.chunks.DataPalette;
import com.viaversion.viaversion.api.minecraft.chunks.PaletteType;
import com.viaversion.viaversion.api.minecraft.entities.Entity1_14Types;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.version.Types1_13;
import com.viaversion.viaversion.api.type.types.version.Types1_13_2;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.libs.gson.JsonObject;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.ListTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ChatRewriter;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ClientboundPackets1_13;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ServerboundPackets1_13;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.types.Chunk1_13Type;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.ClientboundPackets1_14;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.Protocol1_14To1_13_2;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.types.Chunk1_14Type;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.storage.ClientWorld;
import com.viaversion.viaversion.rewriter.BlockRewriter;
import com.viaversion.viaversion.rewriter.RecipeRewriter;
import java.util.ArrayList;

public class BlockItemPackets1_14
extends ItemRewriter<ClientboundPackets1_14, ServerboundPackets1_13, Protocol1_13_2To1_14> {
    private EnchantmentRewriter enchantmentRewriter;

    public BlockItemPackets1_14(Protocol1_13_2To1_14 protocol) {
        super(protocol);
    }

    @Override
    protected void registerPackets() {
        ((Protocol1_13_2To1_14)this.protocol).registerServerbound(ServerboundPackets1_13.EDIT_BOOK, wrapper -> this.handleItemToServer(wrapper.passthrough(Type.FLAT_VAR_INT_ITEM)));
        ((Protocol1_13_2To1_14)this.protocol).registerClientbound(ClientboundPackets1_14.OPEN_WINDOW, wrapper -> {
            JsonObject object;
            int windowId = wrapper.read(Type.VAR_INT);
            wrapper.write(Type.UNSIGNED_BYTE, (short)windowId);
            int type2 = wrapper.read(Type.VAR_INT);
            String stringType = null;
            String containerTitle = null;
            int slotSize = 0;
            if (type2 < 6) {
                if (type2 == 2) {
                    containerTitle = "Barrel";
                }
                stringType = "minecraft:container";
                slotSize = (type2 + 1) * 9;
            } else {
                switch (type2) {
                    case 11: {
                        stringType = "minecraft:crafting_table";
                        break;
                    }
                    case 9: 
                    case 13: 
                    case 14: 
                    case 20: {
                        if (type2 == 9) {
                            containerTitle = "Blast Furnace";
                        } else if (type2 == 20) {
                            containerTitle = "Smoker";
                        } else if (type2 == 14) {
                            containerTitle = "Grindstone";
                        }
                        stringType = "minecraft:furnace";
                        slotSize = 3;
                        break;
                    }
                    case 6: {
                        stringType = "minecraft:dropper";
                        slotSize = 9;
                        break;
                    }
                    case 12: {
                        stringType = "minecraft:enchanting_table";
                        break;
                    }
                    case 10: {
                        stringType = "minecraft:brewing_stand";
                        slotSize = 5;
                        break;
                    }
                    case 18: {
                        stringType = "minecraft:villager";
                        break;
                    }
                    case 8: {
                        stringType = "minecraft:beacon";
                        slotSize = 1;
                        break;
                    }
                    case 7: 
                    case 21: {
                        if (type2 == 21) {
                            containerTitle = "Cartography Table";
                        }
                        stringType = "minecraft:anvil";
                        break;
                    }
                    case 15: {
                        stringType = "minecraft:hopper";
                        slotSize = 5;
                        break;
                    }
                    case 19: {
                        stringType = "minecraft:shulker_box";
                        slotSize = 27;
                    }
                }
            }
            if (stringType == null) {
                ViaBackwards.getPlatform().getLogger().warning("Can't open inventory for 1.13 player! Type: " + type2);
                wrapper.cancel();
                return;
            }
            wrapper.write(Type.STRING, stringType);
            JsonElement title = wrapper.read(Type.COMPONENT);
            if (containerTitle != null && title.isJsonObject() && (object = title.getAsJsonObject()).has("translate") && (type2 != 2 || object.getAsJsonPrimitive("translate").getAsString().equals("container.barrel"))) {
                title = ChatRewriter.legacyTextToJson(containerTitle);
            }
            wrapper.write(Type.COMPONENT, title);
            wrapper.write(Type.UNSIGNED_BYTE, (short)slotSize);
        });
        ((Protocol1_13_2To1_14)this.protocol).registerClientbound(ClientboundPackets1_14.OPEN_HORSE_WINDOW, ClientboundPackets1_13.OPEN_WINDOW, wrapper -> {
            wrapper.passthrough(Type.UNSIGNED_BYTE);
            wrapper.write(Type.STRING, "EntityHorse");
            JsonObject object = new JsonObject();
            object.addProperty("translate", "minecraft.horse");
            wrapper.write(Type.COMPONENT, object);
            wrapper.write(Type.UNSIGNED_BYTE, wrapper.read(Type.VAR_INT).shortValue());
            wrapper.passthrough(Type.INT);
        });
        BlockRewriter<ClientboundPackets1_14> blockRewriter = new BlockRewriter<ClientboundPackets1_14>(this.protocol, Type.POSITION);
        this.registerSetCooldown(ClientboundPackets1_14.COOLDOWN);
        this.registerWindowItems(ClientboundPackets1_14.WINDOW_ITEMS, Type.FLAT_VAR_INT_ITEM_ARRAY);
        this.registerSetSlot(ClientboundPackets1_14.SET_SLOT, Type.FLAT_VAR_INT_ITEM);
        this.registerAdvancements(ClientboundPackets1_14.ADVANCEMENTS, Type.FLAT_VAR_INT_ITEM);
        ((Protocol1_13_2To1_14)this.protocol).registerClientbound(ClientboundPackets1_14.TRADE_LIST, ClientboundPackets1_13.PLUGIN_MESSAGE, wrapper -> {
            wrapper.write(Type.STRING, "minecraft:trader_list");
            int windowId = wrapper.read(Type.VAR_INT);
            wrapper.write(Type.INT, windowId);
            int size = wrapper.passthrough(Type.UNSIGNED_BYTE).shortValue();
            for (int i = 0; i < size; ++i) {
                Item input = wrapper.read(Type.FLAT_VAR_INT_ITEM);
                input = this.handleItemToClient(input);
                wrapper.write(Type.FLAT_VAR_INT_ITEM, input);
                Item output = wrapper.read(Type.FLAT_VAR_INT_ITEM);
                output = this.handleItemToClient(output);
                wrapper.write(Type.FLAT_VAR_INT_ITEM, output);
                boolean secondItem = wrapper.passthrough(Type.BOOLEAN);
                if (secondItem) {
                    Item second = wrapper.read(Type.FLAT_VAR_INT_ITEM);
                    second = this.handleItemToClient(second);
                    wrapper.write(Type.FLAT_VAR_INT_ITEM, second);
                }
                wrapper.passthrough(Type.BOOLEAN);
                wrapper.passthrough(Type.INT);
                wrapper.passthrough(Type.INT);
                wrapper.read(Type.INT);
                wrapper.read(Type.INT);
                wrapper.read(Type.FLOAT);
            }
            wrapper.read(Type.VAR_INT);
            wrapper.read(Type.VAR_INT);
            wrapper.read(Type.BOOLEAN);
        });
        ((Protocol1_13_2To1_14)this.protocol).registerClientbound(ClientboundPackets1_14.OPEN_BOOK, ClientboundPackets1_13.PLUGIN_MESSAGE, wrapper -> {
            wrapper.write(Type.STRING, "minecraft:book_open");
            wrapper.passthrough(Type.VAR_INT);
        });
        ((Protocol1_13_2To1_14)this.protocol).registerClientbound(ClientboundPackets1_14.ENTITY_EQUIPMENT, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Type.VAR_INT);
                this.map(Type.VAR_INT);
                this.map(Type.FLAT_VAR_INT_ITEM);
                this.handler(BlockItemPackets1_14.this.itemToClientHandler(Type.FLAT_VAR_INT_ITEM));
                this.handler(wrapper -> {
                    int entityId = wrapper.get(Type.VAR_INT, 0);
                    EntityType entityType = wrapper.user().getEntityTracker(Protocol1_13_2To1_14.class).entityType(entityId);
                    if (entityType == null) {
                        return;
                    }
                    if (entityType.isOrHasParent(Entity1_14Types.ABSTRACT_HORSE)) {
                        int armorType;
                        wrapper.setPacketType(ClientboundPackets1_13.ENTITY_METADATA);
                        wrapper.resetReader();
                        wrapper.passthrough(Type.VAR_INT);
                        wrapper.read(Type.VAR_INT);
                        Item item = wrapper.read(Type.FLAT_VAR_INT_ITEM);
                        int n = armorType = item == null || item.identifier() == 0 ? 0 : item.identifier() - 726;
                        if (armorType < 0 || armorType > 3) {
                            wrapper.cancel();
                            return;
                        }
                        ArrayList<Metadata> metadataList = new ArrayList<Metadata>();
                        metadataList.add(new Metadata(16, Types1_13_2.META_TYPES.varIntType, armorType));
                        wrapper.write(Types1_13.METADATA_LIST, metadataList);
                    }
                });
            }
        });
        RecipeRewriter recipeHandler = new RecipeRewriter(this.protocol);
        ImmutableSet<String> removedTypes = ImmutableSet.of("crafting_special_suspiciousstew", "blasting", "smoking", "campfire_cooking", "stonecutting");
        ((Protocol1_13_2To1_14)this.protocol).registerClientbound(ClientboundPackets1_14.DECLARE_RECIPES, wrapper -> {
            int size = wrapper.passthrough(Type.VAR_INT);
            int deleted = 0;
            for (int i = 0; i < size; ++i) {
                String type2 = wrapper.read(Type.STRING);
                String id = wrapper.read(Type.STRING);
                if (removedTypes.contains(type2 = type2.replace("minecraft:", ""))) {
                    switch (type2) {
                        case "blasting": 
                        case "smoking": 
                        case "campfire_cooking": {
                            wrapper.read(Type.STRING);
                            wrapper.read(Type.FLAT_VAR_INT_ITEM_ARRAY_VAR_INT);
                            wrapper.read(Type.FLAT_VAR_INT_ITEM);
                            wrapper.read(Type.FLOAT);
                            wrapper.read(Type.VAR_INT);
                            break;
                        }
                        case "stonecutting": {
                            wrapper.read(Type.STRING);
                            wrapper.read(Type.FLAT_VAR_INT_ITEM_ARRAY_VAR_INT);
                            wrapper.read(Type.FLAT_VAR_INT_ITEM);
                        }
                    }
                    ++deleted;
                    continue;
                }
                wrapper.write(Type.STRING, id);
                wrapper.write(Type.STRING, type2);
                recipeHandler.handleRecipeType(wrapper, type2);
            }
            wrapper.set(Type.VAR_INT, 0, size - deleted);
        });
        this.registerClickWindow(ServerboundPackets1_13.CLICK_WINDOW, Type.FLAT_VAR_INT_ITEM);
        this.registerCreativeInvAction(ServerboundPackets1_13.CREATIVE_INVENTORY_ACTION, Type.FLAT_VAR_INT_ITEM);
        ((Protocol1_13_2To1_14)this.protocol).registerClientbound(ClientboundPackets1_14.BLOCK_BREAK_ANIMATION, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Type.VAR_INT);
                this.map(Type.POSITION1_14, Type.POSITION);
                this.map(Type.BYTE);
            }
        });
        ((Protocol1_13_2To1_14)this.protocol).registerClientbound(ClientboundPackets1_14.BLOCK_ENTITY_DATA, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Type.POSITION1_14, Type.POSITION);
            }
        });
        ((Protocol1_13_2To1_14)this.protocol).registerClientbound(ClientboundPackets1_14.BLOCK_ACTION, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Type.POSITION1_14, Type.POSITION);
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.VAR_INT);
                this.handler(wrapper -> {
                    int mappedId = ((Protocol1_13_2To1_14)BlockItemPackets1_14.this.protocol).getMappingData().getNewBlockId(wrapper.get(Type.VAR_INT, 0));
                    if (mappedId == -1) {
                        wrapper.cancel();
                        return;
                    }
                    wrapper.set(Type.VAR_INT, 0, mappedId);
                });
            }
        });
        ((Protocol1_13_2To1_14)this.protocol).registerClientbound(ClientboundPackets1_14.BLOCK_CHANGE, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Type.POSITION1_14, Type.POSITION);
                this.map(Type.VAR_INT);
                this.handler(wrapper -> {
                    int id = wrapper.get(Type.VAR_INT, 0);
                    wrapper.set(Type.VAR_INT, 0, ((Protocol1_13_2To1_14)BlockItemPackets1_14.this.protocol).getMappingData().getNewBlockStateId(id));
                });
            }
        });
        blockRewriter.registerMultiBlockChange(ClientboundPackets1_14.MULTI_BLOCK_CHANGE);
        ((Protocol1_13_2To1_14)this.protocol).registerClientbound(ClientboundPackets1_14.EXPLOSION, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Type.FLOAT);
                this.map(Type.FLOAT);
                this.map(Type.FLOAT);
                this.map(Type.FLOAT);
                this.handler(wrapper -> {
                    for (int i = 0; i < 3; ++i) {
                        float coord = wrapper.get(Type.FLOAT, i).floatValue();
                        if (!(coord < 0.0f)) continue;
                        coord = (float)Math.floor(coord);
                        wrapper.set(Type.FLOAT, i, Float.valueOf(coord));
                    }
                });
            }
        });
        ((Protocol1_13_2To1_14)this.protocol).registerClientbound(ClientboundPackets1_14.CHUNK_DATA, wrapper -> {
            ClientWorld clientWorld = wrapper.user().get(ClientWorld.class);
            Chunk chunk = wrapper.read(new Chunk1_14Type());
            wrapper.write(new Chunk1_13Type(clientWorld), chunk);
            ChunkLightStorage.ChunkLight chunkLight = wrapper.user().get(ChunkLightStorage.class).getStoredLight(chunk.getX(), chunk.getZ());
            for (int i = 0; i < chunk.getSections().length; ++i) {
                ChunkSection section = chunk.getSections()[i];
                if (section == null) continue;
                ChunkSectionLightImpl sectionLight = new ChunkSectionLightImpl();
                section.setLight(sectionLight);
                if (chunkLight == null) {
                    sectionLight.setBlockLight(ChunkLightStorage.FULL_LIGHT);
                    if (clientWorld.getEnvironment() == Environment.NORMAL) {
                        sectionLight.setSkyLight(ChunkLightStorage.FULL_LIGHT);
                    }
                } else {
                    byte[] blockLight = chunkLight.getBlockLight()[i];
                    sectionLight.setBlockLight(blockLight != null ? blockLight : ChunkLightStorage.FULL_LIGHT);
                    if (clientWorld.getEnvironment() == Environment.NORMAL) {
                        byte[] skyLight = chunkLight.getSkyLight()[i];
                        sectionLight.setSkyLight(skyLight != null ? skyLight : ChunkLightStorage.FULL_LIGHT);
                    }
                }
                DataPalette palette = section.palette(PaletteType.BLOCKS);
                if (Via.getConfig().isNonFullBlockLightFix() && section.getNonAirBlocksCount() != 0 && sectionLight.hasBlockLight()) {
                    for (int x = 0; x < 16; ++x) {
                        for (int y = 0; y < 16; ++y) {
                            for (int z = 0; z < 16; ++z) {
                                int id = palette.idAt(x, y, z);
                                if (!Protocol1_14To1_13_2.MAPPINGS.getNonFullBlocks().contains(id)) continue;
                                sectionLight.getBlockLightNibbleArray().set(x, y, z, 0);
                            }
                        }
                    }
                }
                for (int j = 0; j < palette.size(); ++j) {
                    int mappedBlockStateId = ((Protocol1_13_2To1_14)this.protocol).getMappingData().getNewBlockStateId(palette.idByIndex(j));
                    palette.setIdByIndex(j, mappedBlockStateId);
                }
            }
        });
        ((Protocol1_13_2To1_14)this.protocol).registerClientbound(ClientboundPackets1_14.UNLOAD_CHUNK, wrapper -> {
            int x = wrapper.passthrough(Type.INT);
            int z = wrapper.passthrough(Type.INT);
            wrapper.user().get(ChunkLightStorage.class).unloadChunk(x, z);
        });
        ((Protocol1_13_2To1_14)this.protocol).registerClientbound(ClientboundPackets1_14.EFFECT, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Type.INT);
                this.map(Type.POSITION1_14, Type.POSITION);
                this.map(Type.INT);
                this.handler(wrapper -> {
                    int id = wrapper.get(Type.INT, 0);
                    int data = wrapper.get(Type.INT, 1);
                    if (id == 1010) {
                        wrapper.set(Type.INT, 1, ((Protocol1_13_2To1_14)BlockItemPackets1_14.this.protocol).getMappingData().getNewItemId(data));
                    } else if (id == 2001) {
                        wrapper.set(Type.INT, 1, ((Protocol1_13_2To1_14)BlockItemPackets1_14.this.protocol).getMappingData().getNewBlockStateId(data));
                    }
                });
            }
        });
        this.registerSpawnParticle(ClientboundPackets1_14.SPAWN_PARTICLE, Type.FLAT_VAR_INT_ITEM, Type.FLOAT);
        ((Protocol1_13_2To1_14)this.protocol).registerClientbound(ClientboundPackets1_14.MAP_DATA, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Type.VAR_INT);
                this.map(Type.BYTE);
                this.map(Type.BOOLEAN);
                this.map((Type)Type.BOOLEAN, Type.NOTHING);
            }
        });
        ((Protocol1_13_2To1_14)this.protocol).registerClientbound(ClientboundPackets1_14.SPAWN_POSITION, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Type.POSITION1_14, Type.POSITION);
            }
        });
    }

    @Override
    protected void registerRewrites() {
        this.enchantmentRewriter = new EnchantmentRewriter(this, false);
        this.enchantmentRewriter.registerEnchantment("minecraft:multishot", "\u00a77Multishot");
        this.enchantmentRewriter.registerEnchantment("minecraft:quick_charge", "\u00a77Quick Charge");
        this.enchantmentRewriter.registerEnchantment("minecraft:piercing", "\u00a77Piercing");
    }

    @Override
    public Item handleItemToClient(Item item) {
        ListTag lore;
        CompoundTag display;
        if (item == null) {
            return null;
        }
        super.handleItemToClient(item);
        CompoundTag tag = item.tag();
        if (tag != null && (display = (CompoundTag)tag.get("display")) != null && (lore = (ListTag)display.get("Lore")) != null) {
            this.saveListTag(display, lore, "Lore");
            for (Tag loreEntry : lore) {
                StringTag loreEntryTag;
                String value;
                if (!(loreEntry instanceof StringTag) || (value = (loreEntryTag = (StringTag)loreEntry).getValue()) == null || value.isEmpty()) continue;
                loreEntryTag.setValue(ChatRewriter.jsonToLegacyText(value));
            }
        }
        this.enchantmentRewriter.handleToClient(item);
        return item;
    }

    @Override
    public Item handleItemToServer(Item item) {
        ListTag lore;
        CompoundTag display;
        if (item == null) {
            return null;
        }
        CompoundTag tag = item.tag();
        if (tag != null && (display = (CompoundTag)tag.get("display")) != null && (lore = (ListTag)display.get("Lore")) != null && !this.hasBackupTag(display, "Lore")) {
            for (Tag loreEntry : lore) {
                if (!(loreEntry instanceof StringTag)) continue;
                StringTag loreEntryTag = (StringTag)loreEntry;
                loreEntryTag.setValue(ChatRewriter.legacyTextToJsonString(loreEntryTag.getValue()));
            }
        }
        this.enchantmentRewriter.handleToServer(item);
        super.handleItemToServer(item);
        return item;
    }
}

