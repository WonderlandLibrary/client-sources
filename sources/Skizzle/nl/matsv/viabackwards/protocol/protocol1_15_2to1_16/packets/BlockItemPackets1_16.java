/*
 * Decompiled with CFR 0.150.
 */
package nl.matsv.viabackwards.protocol.protocol1_15_2to1_16.packets;

import java.util.ArrayList;
import java.util.UUID;
import nl.matsv.viabackwards.api.rewriters.EnchantmentRewriter;
import nl.matsv.viabackwards.api.rewriters.ItemRewriter;
import nl.matsv.viabackwards.api.rewriters.TranslatableRewriter;
import nl.matsv.viabackwards.protocol.protocol1_15_2to1_16.Protocol1_15_2To1_16;
import nl.matsv.viabackwards.protocol.protocol1_15_2to1_16.data.MapColorRewriter;
import us.myles.ViaVersion.api.PacketWrapper;
import us.myles.ViaVersion.api.minecraft.Position;
import us.myles.ViaVersion.api.minecraft.chunks.Chunk;
import us.myles.ViaVersion.api.minecraft.chunks.ChunkSection;
import us.myles.ViaVersion.api.minecraft.item.Item;
import us.myles.ViaVersion.api.remapper.PacketRemapper;
import us.myles.ViaVersion.api.rewriters.BlockRewriter;
import us.myles.ViaVersion.api.type.Type;
import us.myles.ViaVersion.api.type.types.UUIDIntArrayType;
import us.myles.ViaVersion.protocols.protocol1_14to1_13_2.ServerboundPackets1_14;
import us.myles.ViaVersion.protocols.protocol1_14to1_13_2.data.RecipeRewriter1_14;
import us.myles.ViaVersion.protocols.protocol1_15to1_14_4.ClientboundPackets1_15;
import us.myles.ViaVersion.protocols.protocol1_15to1_14_4.types.Chunk1_15Type;
import us.myles.ViaVersion.protocols.protocol1_16to1_15_2.ClientboundPackets1_16;
import us.myles.ViaVersion.protocols.protocol1_16to1_15_2.packets.InventoryPackets;
import us.myles.ViaVersion.protocols.protocol1_16to1_15_2.types.Chunk1_16Type;
import us.myles.ViaVersion.util.CompactArrayUtil;
import us.myles.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import us.myles.viaversion.libs.opennbt.tag.builtin.IntArrayTag;
import us.myles.viaversion.libs.opennbt.tag.builtin.LongArrayTag;
import us.myles.viaversion.libs.opennbt.tag.builtin.StringTag;
import us.myles.viaversion.libs.opennbt.tag.builtin.Tag;

public class BlockItemPackets1_16
extends ItemRewriter<Protocol1_15_2To1_16> {
    private EnchantmentRewriter enchantmentRewriter;

    public BlockItemPackets1_16(Protocol1_15_2To1_16 protocol, TranslatableRewriter translatableRewriter) {
        super(protocol, translatableRewriter);
    }

    @Override
    protected void registerPackets() {
        us.myles.ViaVersion.api.rewriters.ItemRewriter itemRewriter = new us.myles.ViaVersion.api.rewriters.ItemRewriter(this.protocol, this::handleItemToClient, this::handleItemToServer);
        BlockRewriter blockRewriter = new BlockRewriter(this.protocol, Type.POSITION1_14);
        final RecipeRewriter1_14 recipeRewriter = new RecipeRewriter1_14(this.protocol, this::handleItemToClient);
        ((Protocol1_15_2To1_16)this.protocol).registerOutgoing(ClientboundPackets1_16.DECLARE_RECIPES, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.handler(wrapper -> {
                    int size;
                    int newSize = size = wrapper.passthrough(Type.VAR_INT).intValue();
                    for (int i = 0; i < size; ++i) {
                        String originalType = wrapper.read(Type.STRING);
                        String type = originalType.replace("minecraft:", "");
                        if (type.equals("smithing")) {
                            --newSize;
                            wrapper.read(Type.STRING);
                            wrapper.read(Type.FLAT_VAR_INT_ITEM_ARRAY_VAR_INT);
                            wrapper.read(Type.FLAT_VAR_INT_ITEM_ARRAY_VAR_INT);
                            wrapper.read(Type.FLAT_VAR_INT_ITEM);
                            continue;
                        }
                        wrapper.write(Type.STRING, originalType);
                        String id = wrapper.passthrough(Type.STRING);
                        recipeRewriter.handle(wrapper, type);
                    }
                    wrapper.set(Type.VAR_INT, 0, newSize);
                });
            }
        });
        itemRewriter.registerSetCooldown(ClientboundPackets1_16.COOLDOWN);
        itemRewriter.registerWindowItems(ClientboundPackets1_16.WINDOW_ITEMS, Type.FLAT_VAR_INT_ITEM_ARRAY);
        itemRewriter.registerSetSlot(ClientboundPackets1_16.SET_SLOT, Type.FLAT_VAR_INT_ITEM);
        itemRewriter.registerTradeList(ClientboundPackets1_16.TRADE_LIST, Type.FLAT_VAR_INT_ITEM);
        itemRewriter.registerAdvancements(ClientboundPackets1_16.ADVANCEMENTS, Type.FLAT_VAR_INT_ITEM);
        blockRewriter.registerAcknowledgePlayerDigging(ClientboundPackets1_16.ACKNOWLEDGE_PLAYER_DIGGING);
        blockRewriter.registerBlockAction(ClientboundPackets1_16.BLOCK_ACTION);
        blockRewriter.registerBlockChange(ClientboundPackets1_16.BLOCK_CHANGE);
        blockRewriter.registerMultiBlockChange(ClientboundPackets1_16.MULTI_BLOCK_CHANGE);
        ((Protocol1_15_2To1_16)this.protocol).registerOutgoing(ClientboundPackets1_16.ENTITY_EQUIPMENT, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.handler(wrapper -> {
                    byte slot;
                    int entityId = wrapper.passthrough(Type.VAR_INT);
                    ArrayList<EquipmentData> equipmentData = new ArrayList<EquipmentData>();
                    do {
                        slot = wrapper.read(Type.BYTE);
                        Item item = BlockItemPackets1_16.this.handleItemToClient(wrapper.read(Type.FLAT_VAR_INT_ITEM));
                        int rawSlot = slot & 0x7F;
                        equipmentData.add(new EquipmentData(rawSlot, item));
                    } while ((slot & 0xFFFFFF80) != 0);
                    EquipmentData firstData = (EquipmentData)equipmentData.get(0);
                    wrapper.write(Type.VAR_INT, firstData.slot);
                    wrapper.write(Type.FLAT_VAR_INT_ITEM, firstData.item);
                    for (int i = 1; i < equipmentData.size(); ++i) {
                        PacketWrapper equipmentPacket = wrapper.create(ClientboundPackets1_15.ENTITY_EQUIPMENT.ordinal());
                        EquipmentData data = (EquipmentData)equipmentData.get(i);
                        equipmentPacket.write(Type.VAR_INT, entityId);
                        equipmentPacket.write(Type.VAR_INT, data.slot);
                        equipmentPacket.write(Type.FLAT_VAR_INT_ITEM, data.item);
                        equipmentPacket.send(Protocol1_15_2To1_16.class);
                    }
                });
            }
        });
        ((Protocol1_15_2To1_16)this.protocol).registerOutgoing(ClientboundPackets1_16.UPDATE_LIGHT, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.map(Type.VAR_INT);
                this.map(Type.BOOLEAN, Type.NOTHING);
            }
        });
        ((Protocol1_15_2To1_16)this.protocol).registerOutgoing(ClientboundPackets1_16.CHUNK_DATA, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.handler(wrapper -> {
                    Chunk chunk = wrapper.read(new Chunk1_16Type());
                    wrapper.write(new Chunk1_15Type(), chunk);
                    for (int i2 = 0; i2 < chunk.getSections().length; ++i2) {
                        ChunkSection section = chunk.getSections()[i2];
                        if (section == null) continue;
                        for (int j = 0; j < section.getPaletteSize(); ++j) {
                            int old = section.getPaletteEntry(j);
                            section.setPaletteEntry(j, ((Protocol1_15_2To1_16)BlockItemPackets1_16.this.protocol).getMappingData().getNewBlockStateId(old));
                        }
                    }
                    CompoundTag heightMaps = chunk.getHeightMap();
                    for (Tag heightMapTag : heightMaps) {
                        LongArrayTag heightMap = (LongArrayTag)heightMapTag;
                        int[] heightMapData = new int[256];
                        CompactArrayUtil.iterateCompactArrayWithPadding(9, heightMapData.length, heightMap.getValue(), (i, v) -> {
                            heightMapData[i] = v;
                        });
                        heightMap.setValue(CompactArrayUtil.createCompactArray(9, heightMapData.length, i -> heightMapData[i]));
                    }
                    if (chunk.isBiomeData()) {
                        for (int i3 = 0; i3 < 1024; ++i3) {
                            int biome = chunk.getBiomeData()[i3];
                            switch (biome) {
                                case 170: 
                                case 171: 
                                case 172: 
                                case 173: {
                                    chunk.getBiomeData()[i3] = 8;
                                }
                            }
                        }
                    }
                    if (chunk.getBlockEntities() == null) {
                        return;
                    }
                    for (CompoundTag blockEntity : chunk.getBlockEntities()) {
                        BlockItemPackets1_16.this.handleBlockEntity(blockEntity);
                    }
                });
            }
        });
        blockRewriter.registerEffect(ClientboundPackets1_16.EFFECT, 1010, 2001);
        itemRewriter.registerSpawnParticle(ClientboundPackets1_16.SPAWN_PARTICLE, Type.FLAT_VAR_INT_ITEM, Type.DOUBLE);
        ((Protocol1_15_2To1_16)this.protocol).registerOutgoing(ClientboundPackets1_16.WINDOW_PROPERTY, new PacketRemapper(){

            @Override
            public void registerMap() {
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
        ((Protocol1_15_2To1_16)this.protocol).registerOutgoing(ClientboundPackets1_16.MAP_DATA, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.map(Type.BYTE);
                this.map(Type.BOOLEAN);
                this.map(Type.BOOLEAN);
                this.handler(wrapper -> {
                    int iconCount = wrapper.passthrough(Type.VAR_INT);
                    for (int i = 0; i < iconCount; ++i) {
                        wrapper.passthrough(Type.VAR_INT);
                        wrapper.passthrough(Type.BYTE);
                        wrapper.passthrough(Type.BYTE);
                        wrapper.passthrough(Type.BYTE);
                        if (!wrapper.passthrough(Type.BOOLEAN).booleanValue()) continue;
                        wrapper.passthrough(Type.COMPONENT);
                    }
                    short columns = wrapper.passthrough(Type.UNSIGNED_BYTE);
                    if (columns < 1) {
                        return;
                    }
                    wrapper.passthrough(Type.UNSIGNED_BYTE);
                    wrapper.passthrough(Type.UNSIGNED_BYTE);
                    wrapper.passthrough(Type.UNSIGNED_BYTE);
                    byte[] data = wrapper.passthrough(Type.BYTE_ARRAY_PRIMITIVE);
                    for (int i = 0; i < data.length; ++i) {
                        int color = data[i] & 0xFF;
                        int mappedColor = MapColorRewriter.getMappedColor(color);
                        if (mappedColor == -1) continue;
                        data[i] = (byte)mappedColor;
                    }
                });
            }
        });
        ((Protocol1_15_2To1_16)this.protocol).registerOutgoing(ClientboundPackets1_16.BLOCK_ENTITY_DATA, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.handler(wrapper -> {
                    Position position = wrapper.passthrough(Type.POSITION1_14);
                    short action = wrapper.passthrough(Type.UNSIGNED_BYTE);
                    CompoundTag tag = wrapper.passthrough(Type.NBT);
                    BlockItemPackets1_16.this.handleBlockEntity(tag);
                });
            }
        });
        itemRewriter.registerClickWindow(ServerboundPackets1_14.CLICK_WINDOW, Type.FLAT_VAR_INT_ITEM);
        itemRewriter.registerCreativeInvAction(ServerboundPackets1_14.CREATIVE_INVENTORY_ACTION, Type.FLAT_VAR_INT_ITEM);
        ((Protocol1_15_2To1_16)this.protocol).registerIncoming(ServerboundPackets1_14.EDIT_BOOK, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.handler(wrapper -> BlockItemPackets1_16.this.handleItemToServer(wrapper.passthrough(Type.FLAT_VAR_INT_ITEM)));
            }
        });
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
            tag.put(new StringTag("target_uuid", targetUuid.toString()));
        } else if (id.equals("minecraft:skull")) {
            Object skullOwnerTag = tag.remove("SkullOwner");
            if (!(skullOwnerTag instanceof CompoundTag)) {
                return;
            }
            CompoundTag skullOwnerCompoundTag = (CompoundTag)skullOwnerTag;
            Object ownerUuidTag = skullOwnerCompoundTag.remove("Id");
            if (ownerUuidTag instanceof IntArrayTag) {
                UUID ownerUuid = UUIDIntArrayType.uuidFromIntArray((int[])((Tag)ownerUuidTag).getValue());
                skullOwnerCompoundTag.put(new StringTag("Id", ownerUuid.toString()));
            }
            CompoundTag ownerTag = new CompoundTag("Owner");
            for (Tag t : skullOwnerCompoundTag) {
                ownerTag.put(t);
            }
            tag.put(ownerTag);
        }
    }

    @Override
    protected void registerRewrites() {
        this.enchantmentRewriter = new EnchantmentRewriter(this.nbtTagName);
        this.enchantmentRewriter.registerEnchantment("minecraft:soul_speed", "\u00a77Soul Speed");
    }

    @Override
    public Item handleItemToClient(Item item) {
        CompoundTag ownerCompundTag;
        Object idTag;
        Object ownerTag;
        if (item == null) {
            return null;
        }
        super.handleItemToClient(item);
        CompoundTag tag = item.getTag();
        if (item.getIdentifier() == 771 && tag != null && (ownerTag = tag.get("SkullOwner")) instanceof CompoundTag && (idTag = (ownerCompundTag = (CompoundTag)ownerTag).get("Id")) instanceof IntArrayTag) {
            UUID ownerUuid = UUIDIntArrayType.uuidFromIntArray((int[])((Tag)idTag).getValue());
            ownerCompundTag.put(new StringTag("Id", ownerUuid.toString()));
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
        int identifier = item.getIdentifier();
        super.handleItemToServer(item);
        CompoundTag tag = item.getTag();
        if (identifier == 771 && tag != null && (ownerTag = tag.get("SkullOwner")) instanceof CompoundTag && (idTag = (ownerCompundTag = (CompoundTag)ownerTag).get("Id")) instanceof StringTag) {
            UUID ownerUuid = UUID.fromString((String)((Tag)idTag).getValue());
            ownerCompundTag.put(new IntArrayTag("Id", UUIDIntArrayType.uuidToIntArray(ownerUuid)));
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

