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
import com.viaversion.viaversion.api.minecraft.chunks.DataPalette;
import com.viaversion.viaversion.api.minecraft.chunks.PaletteType;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.UUIDIntArrayType;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.IntArrayTag;
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

    public BlockItemPackets1_16(Protocol1_15_2To1_16 protocol1_15_2To1_16) {
        super(protocol1_15_2To1_16);
    }

    @Override
    protected void registerPackets() {
        BlockRewriter<ClientboundPackets1_16> blockRewriter = new BlockRewriter<ClientboundPackets1_16>(this.protocol, Type.POSITION1_14);
        RecipeRewriter recipeRewriter = new RecipeRewriter(this.protocol);
        ((Protocol1_15_2To1_16)this.protocol).registerClientbound(ClientboundPackets1_16.DECLARE_RECIPES, arg_0 -> BlockItemPackets1_16.lambda$registerPackets$0(recipeRewriter, arg_0));
        this.registerSetCooldown(ClientboundPackets1_16.COOLDOWN);
        this.registerWindowItems(ClientboundPackets1_16.WINDOW_ITEMS, Type.FLAT_VAR_INT_ITEM_ARRAY);
        this.registerSetSlot(ClientboundPackets1_16.SET_SLOT, Type.FLAT_VAR_INT_ITEM);
        this.registerTradeList(ClientboundPackets1_16.TRADE_LIST);
        this.registerAdvancements(ClientboundPackets1_16.ADVANCEMENTS, Type.FLAT_VAR_INT_ITEM);
        blockRewriter.registerAcknowledgePlayerDigging(ClientboundPackets1_16.ACKNOWLEDGE_PLAYER_DIGGING);
        blockRewriter.registerBlockAction(ClientboundPackets1_16.BLOCK_ACTION);
        blockRewriter.registerBlockChange(ClientboundPackets1_16.BLOCK_CHANGE);
        blockRewriter.registerMultiBlockChange(ClientboundPackets1_16.MULTI_BLOCK_CHANGE);
        ((Protocol1_15_2To1_16)this.protocol).registerClientbound(ClientboundPackets1_16.ENTITY_EQUIPMENT, this::lambda$registerPackets$1);
        ((Protocol1_15_2To1_16)this.protocol).registerClientbound(ClientboundPackets1_16.UPDATE_LIGHT, new PacketHandlers(this){
            final BlockItemPackets1_16 this$0;
            {
                this.this$0 = blockItemPackets1_16;
            }

            @Override
            public void register() {
                this.map(Type.VAR_INT);
                this.map(Type.VAR_INT);
                this.map((Type)Type.BOOLEAN, Type.NOTHING);
            }
        });
        ((Protocol1_15_2To1_16)this.protocol).registerClientbound(ClientboundPackets1_16.CHUNK_DATA, this::lambda$registerPackets$4);
        blockRewriter.registerEffect(ClientboundPackets1_16.EFFECT, 1010, 2001);
        this.registerSpawnParticle(ClientboundPackets1_16.SPAWN_PARTICLE, Type.FLAT_VAR_INT_ITEM, Type.DOUBLE);
        ((Protocol1_15_2To1_16)this.protocol).registerClientbound(ClientboundPackets1_16.WINDOW_PROPERTY, new PacketHandlers(this){
            final BlockItemPackets1_16 this$0;
            {
                this.this$0 = blockItemPackets1_16;
            }

            @Override
            public void register() {
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.SHORT);
                this.map(Type.SHORT);
                this.handler(2::lambda$register$0);
            }

            private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                short s = packetWrapper.get(Type.SHORT, 0);
                if (s >= 4 && s <= 6) {
                    short s2 = packetWrapper.get(Type.SHORT, 1);
                    if (s2 > 11) {
                        s2 = (short)(s2 - 1);
                        packetWrapper.set(Type.SHORT, 1, s2);
                    } else if (s2 == 11) {
                        packetWrapper.set(Type.SHORT, 1, (short)9);
                    }
                }
            }
        });
        ((Protocol1_15_2To1_16)this.protocol).registerClientbound(ClientboundPackets1_16.MAP_DATA, new PacketHandlers(this){
            final BlockItemPackets1_16 this$0;
            {
                this.this$0 = blockItemPackets1_16;
            }

            @Override
            public void register() {
                this.map(Type.VAR_INT);
                this.map(Type.BYTE);
                this.map(Type.BOOLEAN);
                this.map(Type.BOOLEAN);
                this.handler(MapColorRewriter.getRewriteHandler(MapColorRewrites::getMappedColor));
            }
        });
        ((Protocol1_15_2To1_16)this.protocol).registerClientbound(ClientboundPackets1_16.BLOCK_ENTITY_DATA, this::lambda$registerPackets$5);
        this.registerClickWindow(ServerboundPackets1_14.CLICK_WINDOW, Type.FLAT_VAR_INT_ITEM);
        this.registerCreativeInvAction(ServerboundPackets1_14.CREATIVE_INVENTORY_ACTION, Type.FLAT_VAR_INT_ITEM);
        ((Protocol1_15_2To1_16)this.protocol).registerServerbound(ServerboundPackets1_14.EDIT_BOOK, this::lambda$registerPackets$6);
    }

    private void handleBlockEntity(CompoundTag compoundTag) {
        StringTag stringTag = (StringTag)compoundTag.get("id");
        if (stringTag == null) {
            return;
        }
        String string = stringTag.getValue();
        if (string.equals("minecraft:conduit")) {
            Object t = compoundTag.remove("Target");
            if (!(t instanceof IntArrayTag)) {
                return;
            }
            UUID uUID = UUIDIntArrayType.uuidFromIntArray((int[])((Tag)t).getValue());
            compoundTag.put("target_uuid", new StringTag(uUID.toString()));
        } else if (string.equals("minecraft:skull")) {
            Object object;
            Object t = compoundTag.remove("SkullOwner");
            if (!(t instanceof CompoundTag)) {
                return;
            }
            CompoundTag compoundTag2 = (CompoundTag)t;
            Object t2 = compoundTag2.remove("Id");
            if (t2 instanceof IntArrayTag) {
                object = UUIDIntArrayType.uuidFromIntArray((int[])((Tag)t2).getValue());
                compoundTag2.put("Id", new StringTag(((UUID)object).toString()));
            }
            object = new CompoundTag();
            for (Map.Entry<String, Tag> entry : compoundTag2) {
                ((CompoundTag)object).put(entry.getKey(), entry.getValue());
            }
            compoundTag.put("Owner", object);
        }
    }

    @Override
    protected void registerRewrites() {
        this.enchantmentRewriter = new EnchantmentRewriter(this);
        this.enchantmentRewriter.registerEnchantment("minecraft:soul_speed", "\u00a77Soul Speed");
    }

    @Override
    public Item handleItemToClient(Item item) {
        CompoundTag compoundTag;
        Object t;
        Object t2;
        if (item == null) {
            return null;
        }
        super.handleItemToClient(item);
        CompoundTag compoundTag2 = item.tag();
        if (item.identifier() == 771 && compoundTag2 != null && (t2 = compoundTag2.get("SkullOwner")) instanceof CompoundTag && (t = (compoundTag = (CompoundTag)t2).get("Id")) instanceof IntArrayTag) {
            UUID uUID = UUIDIntArrayType.uuidFromIntArray((int[])((Tag)t).getValue());
            compoundTag.put("Id", new StringTag(uUID.toString()));
        }
        InventoryPackets.newToOldAttributes(item);
        this.enchantmentRewriter.handleToClient(item);
        return item;
    }

    @Override
    public Item handleItemToServer(Item item) {
        CompoundTag compoundTag;
        Object t;
        Object t2;
        if (item == null) {
            return null;
        }
        int n = item.identifier();
        super.handleItemToServer(item);
        CompoundTag compoundTag2 = item.tag();
        if (n == 771 && compoundTag2 != null && (t2 = compoundTag2.get("SkullOwner")) instanceof CompoundTag && (t = (compoundTag = (CompoundTag)t2).get("Id")) instanceof StringTag) {
            UUID uUID = UUID.fromString((String)((Tag)t).getValue());
            compoundTag.put("Id", new IntArrayTag(UUIDIntArrayType.uuidToIntArray(uUID)));
        }
        InventoryPackets.oldToNewAttributes(item);
        this.enchantmentRewriter.handleToServer(item);
        return item;
    }

    private void lambda$registerPackets$6(PacketWrapper packetWrapper) throws Exception {
        this.handleItemToServer(packetWrapper.passthrough(Type.FLAT_VAR_INT_ITEM));
    }

    private void lambda$registerPackets$5(PacketWrapper packetWrapper) throws Exception {
        packetWrapper.passthrough(Type.POSITION1_14);
        packetWrapper.passthrough(Type.UNSIGNED_BYTE);
        CompoundTag compoundTag = packetWrapper.passthrough(Type.NBT);
        this.handleBlockEntity(compoundTag);
    }

    /*
     * WARNING - void declaration
     */
    private void lambda$registerPackets$4(PacketWrapper packetWrapper) throws Exception {
        Object object;
        Chunk chunk = packetWrapper.read(new Chunk1_16Type());
        packetWrapper.write(new Chunk1_15Type(), chunk);
        for (int i = 0; i < chunk.getSections().length; ++i) {
            object = chunk.getSections()[i];
            if (object == null) continue;
            DataPalette object2 = object.palette(PaletteType.BLOCKS);
            for (int n = 0; n < object2.size(); ++n) {
                int n2 = ((Protocol1_15_2To1_16)this.protocol).getMappingData().getNewBlockStateId(object2.idByIndex(n));
                object2.setIdByIndex(n, n2);
            }
        }
        CompoundTag compoundTag = chunk.getHeightMap();
        for (Tag n : compoundTag.values()) {
            LongArrayTag longArrayTag = (LongArrayTag)n;
            int[] nArray = new int[256];
            CompactArrayUtil.iterateCompactArrayWithPadding(9, nArray.length, longArrayTag.getValue(), (arg_0, arg_1) -> BlockItemPackets1_16.lambda$null$2(nArray, arg_0, arg_1));
            longArrayTag.setValue(CompactArrayUtil.createCompactArray(9, nArray.length, arg_0 -> BlockItemPackets1_16.lambda$null$3(nArray, arg_0)));
        }
        if (chunk.isBiomeData()) {
            if (packetWrapper.user().getProtocolInfo().getServerProtocolVersion() >= ProtocolVersion.v1_16_2.getVersion()) {
                void var5_12;
                object = packetWrapper.user().get(BiomeStorage.class);
                boolean compoundTag2 = false;
                while (var5_12 < 1024) {
                    int n = chunk.getBiomeData()[var5_12];
                    int n3 = ((BiomeStorage)object).legacyBiome(n);
                    if (n3 == -1) {
                        ViaBackwards.getPlatform().getLogger().warning("Biome sent that does not exist in the biome registry: " + n);
                        n3 = 1;
                    }
                    chunk.getBiomeData()[var5_12] = n3;
                    ++var5_12;
                }
            } else {
                for (int i = 0; i < 1024; ++i) {
                    int n = chunk.getBiomeData()[i];
                    switch (n) {
                        case 170: 
                        case 171: 
                        case 172: 
                        case 173: {
                            chunk.getBiomeData()[i] = 8;
                        }
                    }
                }
            }
        }
        if (chunk.getBlockEntities() == null) {
            return;
        }
        for (CompoundTag compoundTag2 : chunk.getBlockEntities()) {
            this.handleBlockEntity(compoundTag2);
        }
    }

    private static long lambda$null$3(int[] nArray, int n) {
        return nArray[n];
    }

    private static void lambda$null$2(int[] nArray, int n, int n2) {
        nArray[n] = n2;
    }

    private void lambda$registerPackets$1(PacketWrapper packetWrapper) throws Exception {
        int n;
        Object object;
        byte by;
        int n2 = packetWrapper.passthrough(Type.VAR_INT);
        ArrayList<EquipmentData> arrayList = new ArrayList<EquipmentData>();
        do {
            by = packetWrapper.read(Type.BYTE);
            object = this.handleItemToClient(packetWrapper.read(Type.FLAT_VAR_INT_ITEM));
            n = by & 0x7F;
            arrayList.add(new EquipmentData(n, (Item)object, null));
        } while ((by & 0xFFFFFF80) != 0);
        object = (EquipmentData)arrayList.get(0);
        packetWrapper.write(Type.VAR_INT, EquipmentData.access$100((EquipmentData)object));
        packetWrapper.write(Type.FLAT_VAR_INT_ITEM, EquipmentData.access$200((EquipmentData)object));
        for (n = 1; n < arrayList.size(); ++n) {
            PacketWrapper packetWrapper2 = packetWrapper.create(ClientboundPackets1_15.ENTITY_EQUIPMENT);
            EquipmentData equipmentData = (EquipmentData)arrayList.get(n);
            packetWrapper2.write(Type.VAR_INT, n2);
            packetWrapper2.write(Type.VAR_INT, EquipmentData.access$100(equipmentData));
            packetWrapper2.write(Type.FLAT_VAR_INT_ITEM, EquipmentData.access$200(equipmentData));
            packetWrapper2.send(Protocol1_15_2To1_16.class);
        }
    }

    private static void lambda$registerPackets$0(RecipeRewriter recipeRewriter, PacketWrapper packetWrapper) throws Exception {
        int n;
        int n2 = n = packetWrapper.passthrough(Type.VAR_INT).intValue();
        for (int i = 0; i < n; ++i) {
            String string = packetWrapper.read(Type.STRING);
            String string2 = Key.stripMinecraftNamespace(string);
            if (string2.equals("smithing")) {
                --n2;
                packetWrapper.read(Type.STRING);
                packetWrapper.read(Type.FLAT_VAR_INT_ITEM_ARRAY_VAR_INT);
                packetWrapper.read(Type.FLAT_VAR_INT_ITEM_ARRAY_VAR_INT);
                packetWrapper.read(Type.FLAT_VAR_INT_ITEM);
                continue;
            }
            packetWrapper.write(Type.STRING, string);
            packetWrapper.passthrough(Type.STRING);
            recipeRewriter.handleRecipeType(packetWrapper, string2);
        }
        packetWrapper.set(Type.VAR_INT, 0, n2);
    }

    private static final class EquipmentData {
        private final int slot;
        private final Item item;

        private EquipmentData(int n, Item item) {
            this.slot = n;
            this.item = item;
        }

        EquipmentData(int n, Item item, 1 var3_3) {
            this(n, item);
        }

        static int access$100(EquipmentData equipmentData) {
            return equipmentData.slot;
        }

        static Item access$200(EquipmentData equipmentData) {
            return equipmentData.item;
        }
    }
}

