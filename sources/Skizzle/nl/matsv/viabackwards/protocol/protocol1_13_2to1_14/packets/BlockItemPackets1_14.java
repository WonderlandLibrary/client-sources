/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableSet
 */
package nl.matsv.viabackwards.protocol.protocol1_13_2to1_14.packets;

import com.google.common.collect.ImmutableSet;
import java.util.ArrayList;
import java.util.Set;
import nl.matsv.viabackwards.ViaBackwards;
import nl.matsv.viabackwards.api.BackwardsProtocol;
import nl.matsv.viabackwards.api.entities.storage.EntityTracker;
import nl.matsv.viabackwards.api.rewriters.EnchantmentRewriter;
import nl.matsv.viabackwards.api.rewriters.TranslatableRewriter;
import nl.matsv.viabackwards.protocol.protocol1_13_2to1_14.Protocol1_13_2To1_14;
import nl.matsv.viabackwards.protocol.protocol1_13_2to1_14.storage.ChunkLightStorage;
import us.myles.ViaVersion.api.PacketWrapper;
import us.myles.ViaVersion.api.Via;
import us.myles.ViaVersion.api.entities.Entity1_14Types;
import us.myles.ViaVersion.api.entities.EntityType;
import us.myles.ViaVersion.api.minecraft.Environment;
import us.myles.ViaVersion.api.minecraft.chunks.Chunk;
import us.myles.ViaVersion.api.minecraft.chunks.ChunkSection;
import us.myles.ViaVersion.api.minecraft.item.Item;
import us.myles.ViaVersion.api.minecraft.metadata.Metadata;
import us.myles.ViaVersion.api.minecraft.metadata.types.MetaType1_13_2;
import us.myles.ViaVersion.api.remapper.PacketHandler;
import us.myles.ViaVersion.api.remapper.PacketRemapper;
import us.myles.ViaVersion.api.rewriters.BlockRewriter;
import us.myles.ViaVersion.api.rewriters.ItemRewriter;
import us.myles.ViaVersion.api.type.Type;
import us.myles.ViaVersion.api.type.types.version.Types1_13;
import us.myles.ViaVersion.protocols.protocol1_13to1_12_2.ChatRewriter;
import us.myles.ViaVersion.protocols.protocol1_13to1_12_2.ClientboundPackets1_13;
import us.myles.ViaVersion.protocols.protocol1_13to1_12_2.ServerboundPackets1_13;
import us.myles.ViaVersion.protocols.protocol1_13to1_12_2.data.RecipeRewriter1_13_2;
import us.myles.ViaVersion.protocols.protocol1_13to1_12_2.types.Chunk1_13Type;
import us.myles.ViaVersion.protocols.protocol1_14to1_13_2.ClientboundPackets1_14;
import us.myles.ViaVersion.protocols.protocol1_14to1_13_2.Protocol1_14To1_13_2;
import us.myles.ViaVersion.protocols.protocol1_14to1_13_2.types.Chunk1_14Type;
import us.myles.ViaVersion.protocols.protocol1_9_3to1_9_1_2.storage.ClientWorld;
import us.myles.viaversion.libs.gson.JsonElement;
import us.myles.viaversion.libs.gson.JsonObject;
import us.myles.viaversion.libs.opennbt.conversion.ConverterRegistry;
import us.myles.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import us.myles.viaversion.libs.opennbt.tag.builtin.ListTag;
import us.myles.viaversion.libs.opennbt.tag.builtin.StringTag;
import us.myles.viaversion.libs.opennbt.tag.builtin.Tag;

public class BlockItemPackets1_14
extends nl.matsv.viabackwards.api.rewriters.ItemRewriter<Protocol1_13_2To1_14> {
    private EnchantmentRewriter enchantmentRewriter;

    public BlockItemPackets1_14(Protocol1_13_2To1_14 protocol, TranslatableRewriter translatableRewriter) {
        super(protocol, translatableRewriter);
    }

    @Override
    protected void registerPackets() {
        ((Protocol1_13_2To1_14)this.protocol).registerIncoming(ServerboundPackets1_13.EDIT_BOOK, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.handler(wrapper -> BlockItemPackets1_14.this.handleItemToServer(wrapper.passthrough(Type.FLAT_VAR_INT_ITEM)));
            }
        });
        ((Protocol1_13_2To1_14)this.protocol).registerOutgoing(ClientboundPackets1_14.OPEN_WINDOW, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper wrapper) throws Exception {
                        JsonObject object;
                        int windowId = wrapper.read(Type.VAR_INT);
                        wrapper.write(Type.UNSIGNED_BYTE, (short)windowId);
                        int type = wrapper.read(Type.VAR_INT);
                        String stringType = null;
                        String containerTitle = null;
                        int slotSize = 0;
                        if (type < 6) {
                            if (type == 2) {
                                containerTitle = "Barrel";
                            }
                            stringType = "minecraft:container";
                            slotSize = (type + 1) * 9;
                        } else {
                            switch (type) {
                                case 11: {
                                    stringType = "minecraft:crafting_table";
                                    break;
                                }
                                case 9: 
                                case 13: 
                                case 14: 
                                case 20: {
                                    if (type == 9) {
                                        containerTitle = "Blast Furnace";
                                    } else if (type == 20) {
                                        containerTitle = "Smoker";
                                    } else if (type == 14) {
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
                                    if (type == 21) {
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
                            ViaBackwards.getPlatform().getLogger().warning("Can't open inventory for 1.13 player! Type: " + type);
                            wrapper.cancel();
                            return;
                        }
                        wrapper.write(Type.STRING, stringType);
                        JsonElement title = wrapper.read(Type.COMPONENT);
                        if (containerTitle != null && title.isJsonObject() && (object = title.getAsJsonObject()).has("translate") && (type != 2 || object.getAsJsonPrimitive("translate").getAsString().equals("container.barrel"))) {
                            title = ChatRewriter.legacyTextToJson(containerTitle);
                        }
                        wrapper.write(Type.COMPONENT, title);
                        wrapper.write(Type.UNSIGNED_BYTE, (short)slotSize);
                    }
                });
            }
        });
        ((Protocol1_13_2To1_14)this.protocol).registerOutgoing(ClientboundPackets1_14.OPEN_HORSE_WINDOW, ClientboundPackets1_13.OPEN_WINDOW, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper wrapper) throws Exception {
                        wrapper.passthrough(Type.UNSIGNED_BYTE);
                        wrapper.write(Type.STRING, "EntityHorse");
                        JsonObject object = new JsonObject();
                        object.addProperty("translate", "minecraft.horse");
                        wrapper.write(Type.COMPONENT, object);
                        wrapper.write(Type.UNSIGNED_BYTE, wrapper.read(Type.VAR_INT).shortValue());
                        wrapper.passthrough(Type.INT);
                    }
                });
            }
        });
        final ItemRewriter itemRewriter = new ItemRewriter(this.protocol, this::handleItemToClient, this::handleItemToServer);
        BlockRewriter blockRewriter = new BlockRewriter(this.protocol, Type.POSITION);
        itemRewriter.registerSetCooldown(ClientboundPackets1_14.COOLDOWN);
        itemRewriter.registerWindowItems(ClientboundPackets1_14.WINDOW_ITEMS, Type.FLAT_VAR_INT_ITEM_ARRAY);
        itemRewriter.registerSetSlot(ClientboundPackets1_14.SET_SLOT, Type.FLAT_VAR_INT_ITEM);
        itemRewriter.registerAdvancements(ClientboundPackets1_14.ADVANCEMENTS, Type.FLAT_VAR_INT_ITEM);
        ((Protocol1_13_2To1_14)this.protocol).registerOutgoing(ClientboundPackets1_14.TRADE_LIST, ClientboundPackets1_13.PLUGIN_MESSAGE, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper wrapper) throws Exception {
                        wrapper.write(Type.STRING, "minecraft:trader_list");
                        int windowId = wrapper.read(Type.VAR_INT);
                        wrapper.write(Type.INT, windowId);
                        int size = wrapper.passthrough(Type.UNSIGNED_BYTE).shortValue();
                        for (int i = 0; i < size; ++i) {
                            Item input = wrapper.read(Type.FLAT_VAR_INT_ITEM);
                            input = BlockItemPackets1_14.this.handleItemToClient(input);
                            wrapper.write(Type.FLAT_VAR_INT_ITEM, input);
                            Item output = wrapper.read(Type.FLAT_VAR_INT_ITEM);
                            output = BlockItemPackets1_14.this.handleItemToClient(output);
                            wrapper.write(Type.FLAT_VAR_INT_ITEM, output);
                            boolean secondItem = wrapper.passthrough(Type.BOOLEAN);
                            if (secondItem) {
                                Item second = wrapper.read(Type.FLAT_VAR_INT_ITEM);
                                second = BlockItemPackets1_14.this.handleItemToClient(second);
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
                    }
                });
            }
        });
        ((Protocol1_13_2To1_14)this.protocol).registerOutgoing(ClientboundPackets1_14.OPEN_BOOK, ClientboundPackets1_13.PLUGIN_MESSAGE, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper wrapper) throws Exception {
                        wrapper.write(Type.STRING, "minecraft:book_open");
                        wrapper.passthrough(Type.VAR_INT);
                    }
                });
            }
        });
        ((Protocol1_13_2To1_14)this.protocol).registerOutgoing(ClientboundPackets1_14.ENTITY_EQUIPMENT, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.map(Type.VAR_INT);
                this.map(Type.FLAT_VAR_INT_ITEM);
                this.handler(itemRewriter.itemToClientHandler(Type.FLAT_VAR_INT_ITEM));
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper wrapper) throws Exception {
                        int entityId = wrapper.get(Type.VAR_INT, 0);
                        EntityType entityType = wrapper.user().get(EntityTracker.class).get((BackwardsProtocol)BlockItemPackets1_14.this.getProtocol()).getEntityType(entityId);
                        if (entityType == null) {
                            return;
                        }
                        if (entityType.isOrHasParent(Entity1_14Types.EntityType.ABSTRACT_HORSE)) {
                            int armorType;
                            wrapper.setId(63);
                            wrapper.resetReader();
                            wrapper.passthrough(Type.VAR_INT);
                            wrapper.read(Type.VAR_INT);
                            Item item = wrapper.read(Type.FLAT_VAR_INT_ITEM);
                            int n = armorType = item == null || item.getIdentifier() == 0 ? 0 : item.getIdentifier() - 726;
                            if (armorType < 0 || armorType > 3) {
                                ViaBackwards.getPlatform().getLogger().warning("Received invalid horse armor: " + item);
                                wrapper.cancel();
                                return;
                            }
                            ArrayList<Metadata> metadataList = new ArrayList<Metadata>();
                            metadataList.add(new Metadata(16, MetaType1_13_2.VarInt, armorType));
                            wrapper.write(Types1_13.METADATA_LIST, metadataList);
                        }
                    }
                });
            }
        });
        final RecipeRewriter1_13_2 recipeHandler = new RecipeRewriter1_13_2(this.protocol, this::handleItemToClient);
        ((Protocol1_13_2To1_14)this.protocol).registerOutgoing(ClientboundPackets1_14.DECLARE_RECIPES, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.handler(new PacketHandler(){
                    private final Set<String> removedTypes = ImmutableSet.of((Object)"crafting_special_suspiciousstew", (Object)"blasting", (Object)"smoking", (Object)"campfire_cooking", (Object)"stonecutting");

                    @Override
                    public void handle(PacketWrapper wrapper) throws Exception {
                        int size = wrapper.passthrough(Type.VAR_INT);
                        int deleted = 0;
                        for (int i = 0; i < size; ++i) {
                            String type = wrapper.read(Type.STRING);
                            String id = wrapper.read(Type.STRING);
                            if (this.removedTypes.contains(type = type.replace("minecraft:", ""))) {
                                switch (type) {
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
                            wrapper.write(Type.STRING, type);
                            recipeHandler.handle(wrapper, type);
                        }
                        wrapper.set(Type.VAR_INT, 0, size - deleted);
                    }
                });
            }
        });
        itemRewriter.registerClickWindow(ServerboundPackets1_13.CLICK_WINDOW, Type.FLAT_VAR_INT_ITEM);
        itemRewriter.registerCreativeInvAction(ServerboundPackets1_13.CREATIVE_INVENTORY_ACTION, Type.FLAT_VAR_INT_ITEM);
        ((Protocol1_13_2To1_14)this.protocol).registerOutgoing(ClientboundPackets1_14.BLOCK_BREAK_ANIMATION, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.map(Type.POSITION1_14, Type.POSITION);
                this.map(Type.BYTE);
            }
        });
        ((Protocol1_13_2To1_14)this.protocol).registerOutgoing(ClientboundPackets1_14.BLOCK_ENTITY_DATA, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.POSITION1_14, Type.POSITION);
            }
        });
        ((Protocol1_13_2To1_14)this.protocol).registerOutgoing(ClientboundPackets1_14.BLOCK_ACTION, new PacketRemapper(){

            @Override
            public void registerMap() {
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
        ((Protocol1_13_2To1_14)this.protocol).registerOutgoing(ClientboundPackets1_14.BLOCK_CHANGE, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.POSITION1_14, Type.POSITION);
                this.map(Type.VAR_INT);
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper wrapper) throws Exception {
                        int id = wrapper.get(Type.VAR_INT, 0);
                        wrapper.set(Type.VAR_INT, 0, ((Protocol1_13_2To1_14)BlockItemPackets1_14.this.protocol).getMappingData().getNewBlockStateId(id));
                    }
                });
            }
        });
        blockRewriter.registerMultiBlockChange(ClientboundPackets1_14.MULTI_BLOCK_CHANGE);
        ((Protocol1_13_2To1_14)this.protocol).registerOutgoing(ClientboundPackets1_14.EXPLOSION, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.FLOAT);
                this.map(Type.FLOAT);
                this.map(Type.FLOAT);
                this.map(Type.FLOAT);
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper wrapper) throws Exception {
                        for (int i = 0; i < 3; ++i) {
                            float coord = wrapper.get(Type.FLOAT, i).floatValue();
                            if (!(coord < 0.0f)) continue;
                            coord = (float)Math.floor(coord);
                            wrapper.set(Type.FLOAT, i, Float.valueOf(coord));
                        }
                    }
                });
            }
        });
        ((Protocol1_13_2To1_14)this.protocol).registerOutgoing(ClientboundPackets1_14.CHUNK_DATA, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper wrapper) throws Exception {
                        ClientWorld clientWorld = wrapper.user().get(ClientWorld.class);
                        Chunk chunk = wrapper.read(new Chunk1_14Type());
                        wrapper.write(new Chunk1_13Type(clientWorld), chunk);
                        ChunkLightStorage.ChunkLight chunkLight = wrapper.user().get(ChunkLightStorage.class).getStoredLight(chunk.getX(), chunk.getZ());
                        for (int i = 0; i < chunk.getSections().length; ++i) {
                            ChunkSection section = chunk.getSections()[i];
                            if (section == null) continue;
                            if (chunkLight == null) {
                                section.setBlockLight(ChunkLightStorage.FULL_LIGHT);
                                if (clientWorld.getEnvironment() == Environment.NORMAL) {
                                    section.setSkyLight(ChunkLightStorage.FULL_LIGHT);
                                }
                            } else {
                                byte[] blockLight = chunkLight.getBlockLight()[i];
                                section.setBlockLight(blockLight != null ? blockLight : ChunkLightStorage.FULL_LIGHT);
                                if (clientWorld.getEnvironment() == Environment.NORMAL) {
                                    byte[] skyLight = chunkLight.getSkyLight()[i];
                                    section.setSkyLight(skyLight != null ? skyLight : ChunkLightStorage.FULL_LIGHT);
                                }
                            }
                            if (Via.getConfig().isNonFullBlockLightFix() && section.getNonAirBlocksCount() != 0 && section.hasBlockLight()) {
                                for (int x = 0; x < 16; ++x) {
                                    for (int y = 0; y < 16; ++y) {
                                        for (int z = 0; z < 16; ++z) {
                                            int id = section.getFlatBlock(x, y, z);
                                            if (!Protocol1_14To1_13_2.MAPPINGS.getNonFullBlocks().contains(id)) continue;
                                            section.getBlockLightNibbleArray().set(x, y, z, 0);
                                        }
                                    }
                                }
                            }
                            for (int j = 0; j < section.getPaletteSize(); ++j) {
                                int old = section.getPaletteEntry(j);
                                int newId = ((Protocol1_13_2To1_14)BlockItemPackets1_14.this.protocol).getMappingData().getNewBlockStateId(old);
                                section.setPaletteEntry(j, newId);
                            }
                        }
                    }
                });
            }
        });
        ((Protocol1_13_2To1_14)this.protocol).registerOutgoing(ClientboundPackets1_14.UNLOAD_CHUNK, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper wrapper) throws Exception {
                        int x = wrapper.passthrough(Type.INT);
                        int z = wrapper.passthrough(Type.INT);
                        wrapper.user().get(ChunkLightStorage.class).unloadChunk(x, z);
                    }
                });
            }
        });
        ((Protocol1_13_2To1_14)this.protocol).registerOutgoing(ClientboundPackets1_14.EFFECT, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.INT);
                this.map(Type.POSITION1_14, Type.POSITION);
                this.map(Type.INT);
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper wrapper) throws Exception {
                        int id = wrapper.get(Type.INT, 0);
                        int data = wrapper.get(Type.INT, 1);
                        if (id == 1010) {
                            wrapper.set(Type.INT, 1, ((Protocol1_13_2To1_14)BlockItemPackets1_14.this.protocol).getMappingData().getNewItemId(data));
                        } else if (id == 2001) {
                            wrapper.set(Type.INT, 1, ((Protocol1_13_2To1_14)BlockItemPackets1_14.this.protocol).getMappingData().getNewBlockStateId(data));
                        }
                    }
                });
            }
        });
        itemRewriter.registerSpawnParticle(ClientboundPackets1_14.SPAWN_PARTICLE, Type.FLAT_VAR_INT_ITEM, Type.FLOAT);
        ((Protocol1_13_2To1_14)this.protocol).registerOutgoing(ClientboundPackets1_14.MAP_DATA, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.map(Type.BYTE);
                this.map(Type.BOOLEAN);
                this.map(Type.BOOLEAN, Type.NOTHING);
            }
        });
        ((Protocol1_13_2To1_14)this.protocol).registerOutgoing(ClientboundPackets1_14.SPAWN_POSITION, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.POSITION1_14, Type.POSITION);
            }
        });
    }

    @Override
    protected void registerRewrites() {
        this.enchantmentRewriter = new EnchantmentRewriter(this.nbtTagName, false);
        this.enchantmentRewriter.registerEnchantment("minecraft:multishot", "\u00a77Multishot");
        this.enchantmentRewriter.registerEnchantment("minecraft:quick_charge", "\u00a77Quick Charge");
        this.enchantmentRewriter.registerEnchantment("minecraft:piercing", "\u00a77Piercing");
    }

    @Override
    public Item handleItemToClient(Item item) {
        if (item == null) {
            return null;
        }
        super.handleItemToClient(item);
        CompoundTag tag = item.getTag();
        if (tag != null) {
            if (tag.get("display") instanceof CompoundTag) {
                CompoundTag display = (CompoundTag)tag.get("display");
                if (((CompoundTag)tag.get("display")).get("Lore") instanceof ListTag) {
                    ListTag lore = (ListTag)display.get("Lore");
                    ListTag via = (ListTag)display.remove(this.nbtTagName + "|Lore");
                    if (via != null) {
                        display.put(ConverterRegistry.convertToTag("Lore", ConverterRegistry.convertToValue(via)));
                    } else {
                        for (Tag loreEntry : lore) {
                            String value;
                            if (!(loreEntry instanceof StringTag) || (value = ((StringTag)loreEntry).getValue()) == null || value.isEmpty()) continue;
                            ((StringTag)loreEntry).setValue(ChatRewriter.jsonTextToLegacy(value));
                        }
                    }
                }
            }
            this.enchantmentRewriter.handleToClient(item);
        }
        return item;
    }

    @Override
    public Item handleItemToServer(Item item) {
        if (item == null) {
            return null;
        }
        super.handleItemToServer(item);
        CompoundTag tag = item.getTag();
        if (tag != null) {
            CompoundTag display;
            if (tag.get("display") instanceof CompoundTag && (display = (CompoundTag)tag.get("display")).get("Lore") instanceof ListTag) {
                ListTag lore = (ListTag)display.get("Lore");
                display.put(ConverterRegistry.convertToTag(this.nbtTagName + "|Lore", ConverterRegistry.convertToValue(lore)));
                for (Tag loreEntry : lore) {
                    if (!(loreEntry instanceof StringTag)) continue;
                    ((StringTag)loreEntry).setValue(ChatRewriter.legacyTextToJson(((StringTag)loreEntry).getValue()).toString());
                }
            }
            this.enchantmentRewriter.handleToServer(item);
        }
        return item;
    }
}

