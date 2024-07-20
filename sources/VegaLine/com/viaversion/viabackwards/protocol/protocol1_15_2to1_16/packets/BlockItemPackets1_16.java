/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viabackwards.protocol.protocol1_15_2to1_16.packets;

import com.viaversion.viabackwards.ViaBackwards;
import com.viaversion.viabackwards.api.rewriters.EnchantmentRewriter;
import com.viaversion.viabackwards.api.rewriters.ItemRewriter;
import com.viaversion.viabackwards.api.rewriters.MapColorRewriter;
import com.viaversion.viabackwards.protocol.protocol1_15_2to1_16.Protocol1_15_2To1_16;
import com.viaversion.viabackwards.protocol.protocol1_15_2to1_16.data.MapColorRewrites;
import com.viaversion.viabackwards.protocol.protocol1_16_1to1_16_2.storage.BiomeStorage;
import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
import com.viaversion.viaversion.api.minecraft.chunks.ChunkSection;
import com.viaversion.viaversion.api.minecraft.chunks.DataPalette;
import com.viaversion.viaversion.api.minecraft.chunks.PaletteType;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.UUIDIntArrayType;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.IntArrayTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.ListTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.LongArrayTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.ServerboundPackets1_14;
import com.viaversion.viaversion.protocols.protocol1_15to1_14_4.ClientboundPackets1_15;
import com.viaversion.viaversion.protocols.protocol1_15to1_14_4.types.Chunk1_15Type;
import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.ClientboundPackets1_16;
import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.packets.InventoryPackets;
import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.types.Chunk1_16Type;
import com.viaversion.viaversion.rewriter.BlockRewriter;
import com.viaversion.viaversion.rewriter.RecipeRewriter;
import com.viaversion.viaversion.util.CompactArrayUtil;
import com.viaversion.viaversion.util.Key;
import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;

public class BlockItemPackets1_16
extends ItemRewriter<ClientboundPackets1_16, ServerboundPackets1_14, Protocol1_15_2To1_16> {
    private EnchantmentRewriter enchantmentRewriter;

    public BlockItemPackets1_16(Protocol1_15_2To1_16 protocol) {
        super(protocol);
    }

    @Override
    protected void registerPackets() {
        BlockRewriter<ClientboundPackets1_16> blockRewriter = new BlockRewriter<ClientboundPackets1_16>(this.protocol, Type.POSITION1_14);
        RecipeRewriter recipeRewriter = new RecipeRewriter(this.protocol);
        ((Protocol1_15_2To1_16)this.protocol).registerClientbound(ClientboundPackets1_16.DECLARE_RECIPES, wrapper -> {
            int size;
            int newSize = size = wrapper.passthrough(Type.VAR_INT).intValue();
            for (int i = 0; i < size; ++i) {
                String originalType = wrapper.read(Type.STRING);
                String type2 = Key.stripMinecraftNamespace(originalType);
                if (type2.equals("smithing")) {
                    --newSize;
                    wrapper.read(Type.STRING);
                    wrapper.read(Type.FLAT_VAR_INT_ITEM_ARRAY_VAR_INT);
                    wrapper.read(Type.FLAT_VAR_INT_ITEM_ARRAY_VAR_INT);
                    wrapper.read(Type.FLAT_VAR_INT_ITEM);
                    continue;
                }
                wrapper.write(Type.STRING, originalType);
                wrapper.passthrough(Type.STRING);
                recipeRewriter.handleRecipeType(wrapper, type2);
            }
            wrapper.set(Type.VAR_INT, 0, newSize);
        });
        this.registerSetCooldown(ClientboundPackets1_16.COOLDOWN);
        this.registerWindowItems(ClientboundPackets1_16.WINDOW_ITEMS, Type.FLAT_VAR_INT_ITEM_ARRAY);
        this.registerSetSlot(ClientboundPackets1_16.SET_SLOT, Type.FLAT_VAR_INT_ITEM);
        this.registerTradeList(ClientboundPackets1_16.TRADE_LIST);
        this.registerAdvancements(ClientboundPackets1_16.ADVANCEMENTS, Type.FLAT_VAR_INT_ITEM);
        blockRewriter.registerAcknowledgePlayerDigging(ClientboundPackets1_16.ACKNOWLEDGE_PLAYER_DIGGING);
        blockRewriter.registerBlockAction(ClientboundPackets1_16.BLOCK_ACTION);
        blockRewriter.registerBlockChange(ClientboundPackets1_16.BLOCK_CHANGE);
        blockRewriter.registerMultiBlockChange(ClientboundPackets1_16.MULTI_BLOCK_CHANGE);
        ((Protocol1_15_2To1_16)this.protocol).registerClientbound(ClientboundPackets1_16.ENTITY_EQUIPMENT, wrapper -> {
            byte slot;
            int entityId = wrapper.passthrough(Type.VAR_INT);
            ArrayList<EquipmentData> equipmentData = new ArrayList<EquipmentData>();
            do {
                slot = wrapper.read(Type.BYTE);
                Item item = this.handleItemToClient(wrapper.read(Type.FLAT_VAR_INT_ITEM));
                int rawSlot = slot & 0x7F;
                equipmentData.add(new EquipmentData(rawSlot, item));
            } while ((slot & 0xFFFFFF80) != 0);
            EquipmentData firstData = (EquipmentData)equipmentData.get(0);
            wrapper.write(Type.VAR_INT, firstData.slot);
            wrapper.write(Type.FLAT_VAR_INT_ITEM, firstData.item);
            for (int i = 1; i < equipmentData.size(); ++i) {
                PacketWrapper equipmentPacket = wrapper.create(ClientboundPackets1_15.ENTITY_EQUIPMENT);
                EquipmentData data = (EquipmentData)equipmentData.get(i);
                equipmentPacket.write(Type.VAR_INT, entityId);
                equipmentPacket.write(Type.VAR_INT, data.slot);
                equipmentPacket.write(Type.FLAT_VAR_INT_ITEM, data.item);
                equipmentPacket.send(Protocol1_15_2To1_16.class);
            }
        });
        ((Protocol1_15_2To1_16)this.protocol).registerClientbound(ClientboundPackets1_16.UPDATE_LIGHT, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Type.VAR_INT);
                this.map(Type.VAR_INT);
                this.map((Type)Type.BOOLEAN, Type.NOTHING);
            }
        });
        ((Protocol1_15_2To1_16)this.protocol).registerClientbound(ClientboundPackets1_16.CHUNK_DATA, wrapper -> {
            Chunk chunk = wrapper.read(new Chunk1_16Type());
            wrapper.write(new Chunk1_15Type(), chunk);
            for (int i2 = 0; i2 < chunk.getSections().length; ++i2) {
                ChunkSection section = chunk.getSections()[i2];
                if (section == null) continue;
                DataPalette palette = section.palette(PaletteType.BLOCKS);
                for (int j = 0; j < palette.size(); ++j) {
                    int mappedBlockStateId = ((Protocol1_15_2To1_16)this.protocol).getMappingData().getNewBlockStateId(palette.idByIndex(j));
                    palette.setIdByIndex(j, mappedBlockStateId);
                }
            }
            CompoundTag heightMaps = chunk.getHeightMap();
            for (Tag heightMapTag : heightMaps.values()) {
                if (!(heightMapTag instanceof LongArrayTag)) continue;
                LongArrayTag heightMap = (LongArrayTag)heightMapTag;
                int[] heightMapData = new int[256];
                CompactArrayUtil.iterateCompactArrayWithPadding(9, heightMapData.length, heightMap.getValue(), (i, v) -> {
                    heightMapData[i] = v;
                });
                heightMap.setValue(CompactArrayUtil.createCompactArray(9, heightMapData.length, i -> heightMapData[i]));
            }
            if (chunk.isBiomeData()) {
                if (wrapper.user().getProtocolInfo().getServerProtocolVersion() >= ProtocolVersion.v1_16_2.getVersion()) {
                    BiomeStorage biomeStorage = wrapper.user().get(BiomeStorage.class);
                    for (int i3 = 0; i3 < 1024; ++i3) {
                        int biome = chunk.getBiomeData()[i3];
                        int legacyBiome = biomeStorage.legacyBiome(biome);
                        if (legacyBiome == -1) {
                            ViaBackwards.getPlatform().getLogger().warning("Biome sent that does not exist in the biome registry: " + biome);
                            legacyBiome = 1;
                        }
                        chunk.getBiomeData()[i3] = legacyBiome;
                    }
                } else {
                    for (int i4 = 0; i4 < 1024; ++i4) {
                        int biome = chunk.getBiomeData()[i4];
                        switch (biome) {
                            case 170: 
                            case 171: 
                            case 172: 
                            case 173: {
                                chunk.getBiomeData()[i4] = 8;
                            }
                        }
                    }
                }
            }
            if (chunk.getBlockEntities() == null) {
                return;
            }
            for (CompoundTag blockEntity : chunk.getBlockEntities()) {
                this.handleBlockEntity(blockEntity);
            }
        });
        blockRewriter.registerEffect(ClientboundPackets1_16.EFFECT, 1010, 2001);
        this.registerSpawnParticle(ClientboundPackets1_16.SPAWN_PARTICLE, Type.FLAT_VAR_INT_ITEM, Type.DOUBLE);
        ((Protocol1_15_2To1_16)this.protocol).registerClientbound(ClientboundPackets1_16.WINDOW_PROPERTY, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.SHORT);
                this.map(Type.SHORT);
                this.handler(wrapper -> {
                    short property = wrapper.get(Type.SHORT, 0);
                    if (property >= 4 && property <= 6) {
                        short enchantmentId = wrapper.get(Type.SHORT, 1);
                        if (enchantmentId > 11) {
                            enchantmentId = (short)(enchantmentId - 1);
                            wrapper.set(Type.SHORT, 1, enchantmentId);
                        } else if (enchantmentId == 11) {
                            wrapper.set(Type.SHORT, 1, (short)9);
                        }
                    }
                });
            }
        });
        ((Protocol1_15_2To1_16)this.protocol).registerClientbound(ClientboundPackets1_16.MAP_DATA, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Type.VAR_INT);
                this.map(Type.BYTE);
                this.map(Type.BOOLEAN);
                this.map(Type.BOOLEAN);
                this.handler(MapColorRewriter.getRewriteHandler(MapColorRewrites::getMappedColor));
            }
        });
        ((Protocol1_15_2To1_16)this.protocol).registerClientbound(ClientboundPackets1_16.BLOCK_ENTITY_DATA, wrapper -> {
            wrapper.passthrough(Type.POSITION1_14);
            wrapper.passthrough(Type.UNSIGNED_BYTE);
            CompoundTag tag = wrapper.passthrough(Type.NBT);
            this.handleBlockEntity(tag);
        });
        this.registerClickWindow(ServerboundPackets1_14.CLICK_WINDOW, Type.FLAT_VAR_INT_ITEM);
        this.registerCreativeInvAction(ServerboundPackets1_14.CREATIVE_INVENTORY_ACTION, Type.FLAT_VAR_INT_ITEM);
        ((Protocol1_15_2To1_16)this.protocol).registerServerbound(ServerboundPackets1_14.EDIT_BOOK, wrapper -> this.handleItemToServer(wrapper.passthrough(Type.FLAT_VAR_INT_ITEM)));
    }

    private void handleBlockEntity(CompoundTag tag) {
        StringTag idTag = (StringTag)tag.get("id");
        if (idTag == null) {
            return;
        }
        String id = idTag.getValue();
        if (id.equals("minecraft:conduit")) {
            Object targetUuidTag = tag.remove("Target");
            if (!(targetUuidTag instanceof IntArrayTag)) {
                return;
            }
            UUID targetUuid = UUIDIntArrayType.uuidFromIntArray((int[])((Tag)targetUuidTag).getValue());
            tag.put("target_uuid", new StringTag(targetUuid.toString()));
        } else if (id.equals("minecraft:skull")) {
            Object skullOwnerTag = tag.remove("SkullOwner");
            if (!(skullOwnerTag instanceof CompoundTag)) {
                return;
            }
            CompoundTag skullOwnerCompoundTag = (CompoundTag)skullOwnerTag;
            Object ownerUuidTag = skullOwnerCompoundTag.remove("Id");
            if (ownerUuidTag instanceof IntArrayTag) {
                UUID ownerUuid = UUIDIntArrayType.uuidFromIntArray((int[])((Tag)ownerUuidTag).getValue());
                skullOwnerCompoundTag.put("Id", new StringTag(ownerUuid.toString()));
            }
            CompoundTag ownerTag = new CompoundTag();
            for (Map.Entry<String, Tag> entry : skullOwnerCompoundTag) {
                ownerTag.put(entry.getKey(), entry.getValue());
            }
            tag.put("Owner", ownerTag);
        }
    }

    @Override
    protected void registerRewrites() {
        this.enchantmentRewriter = new EnchantmentRewriter(this);
        this.enchantmentRewriter.registerEnchantment("minecraft:soul_speed", "\u00a77Soul Speed");
    }

    @Override
    public Item handleItemToClient(Item item) {
        Object pagesTag;
        CompoundTag ownerCompundTag;
        Object idTag;
        Object ownerTag;
        if (item == null) {
            return null;
        }
        super.handleItemToClient(item);
        CompoundTag tag = item.tag();
        if (item.identifier() == 771 && tag != null && (ownerTag = tag.get("SkullOwner")) instanceof CompoundTag && (idTag = (ownerCompundTag = (CompoundTag)ownerTag).get("Id")) instanceof IntArrayTag) {
            UUID ownerUuid = UUIDIntArrayType.uuidFromIntArray((int[])((Tag)idTag).getValue());
            ownerCompundTag.put("Id", new StringTag(ownerUuid.toString()));
        }
        if ((item.identifier() == 758 || item.identifier() == 759) && tag != null && (pagesTag = tag.get("pages")) instanceof ListTag) {
            for (Tag page : (ListTag)pagesTag) {
                if (!(page instanceof StringTag)) continue;
                StringTag pageTag = (StringTag)page;
                JsonElement jsonElement = ((Protocol1_15_2To1_16)this.protocol).getTranslatableRewriter().processText(pageTag.getValue());
                pageTag.setValue(jsonElement.toString());
            }
        }
        InventoryPackets.newToOldAttributes(item);
        this.enchantmentRewriter.handleToClient(item);
        return item;
    }

    @Override
    public Item handleItemToServer(Item item) {
        CompoundTag ownerCompundTag;
        Object idTag;
        Object ownerTag;
        if (item == null) {
            return null;
        }
        int identifier = item.identifier();
        super.handleItemToServer(item);
        CompoundTag tag = item.tag();
        if (identifier == 771 && tag != null && (ownerTag = tag.get("SkullOwner")) instanceof CompoundTag && (idTag = (ownerCompundTag = (CompoundTag)ownerTag).get("Id")) instanceof StringTag) {
            UUID ownerUuid = UUID.fromString((String)((Tag)idTag).getValue());
            ownerCompundTag.put("Id", new IntArrayTag(UUIDIntArrayType.uuidToIntArray(ownerUuid)));
        }
        InventoryPackets.oldToNewAttributes(item);
        this.enchantmentRewriter.handleToServer(item);
        return item;
    }

    private static final class EquipmentData {
        private final int slot;
        private final Item item;

        private EquipmentData(int slot, Item item) {
            this.slot = slot;
            this.item = item;
        }
    }
}

