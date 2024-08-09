/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.packets;

import com.google.common.primitives.Ints;
import com.viaversion.viabackwards.ViaBackwards;
import com.viaversion.viabackwards.api.rewriters.EnchantmentRewriter;
import com.viaversion.viabackwards.api.rewriters.ItemRewriter;
import com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.Protocol1_12_2To1_13;
import com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.block_entity_handlers.FlowerPotHandler;
import com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.providers.BackwardsBlockEntityProvider;
import com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.storage.BackwardsBlockStorage;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.BlockChangeRecord;
import com.viaversion.viaversion.api.minecraft.Position;
import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
import com.viaversion.viaversion.api.minecraft.chunks.ChunkSection;
import com.viaversion.viaversion.api.minecraft.chunks.PaletteType;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.libs.opennbt.conversion.ConverterRegistry;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.ByteTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.IntTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.ListTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.NumberTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.ShortTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
import com.viaversion.viaversion.protocols.protocol1_12_1to1_12.ClientboundPackets1_12_1;
import com.viaversion.viaversion.protocols.protocol1_12_1to1_12.ServerboundPackets1_12_1;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ChatRewriter;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ClientboundPackets1_13;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.Protocol1_13To1_12_2;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.data.BlockIdData;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.data.SpawnEggRewriter;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.types.Chunk1_13Type;
import com.viaversion.viaversion.protocols.protocol1_9_1_2to1_9_3_4.types.Chunk1_9_3_4Type;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.storage.ClientWorld;
import com.viaversion.viaversion.util.Key;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

public class BlockItemPackets1_13
extends ItemRewriter<ClientboundPackets1_13, ServerboundPackets1_12_1, Protocol1_12_2To1_13> {
    private final Map<String, String> enchantmentMappings = new HashMap<String, String>();
    private final String extraNbtTag;

    public BlockItemPackets1_13(Protocol1_12_2To1_13 protocol1_12_2To1_13) {
        super(protocol1_12_2To1_13);
        this.extraNbtTag = "VB|" + protocol1_12_2To1_13.getClass().getSimpleName() + "|2";
    }

    public static boolean isDamageable(int n) {
        return n >= 256 && n <= 259 || n == 261 || n >= 267 && n <= 279 || n >= 283 && n <= 286 || n >= 290 && n <= 294 || n >= 298 && n <= 317 || n == 346 || n == 359 || n == 398 || n == 442 || n == 443;
    }

    @Override
    protected void registerPackets() {
        ((Protocol1_12_2To1_13)this.protocol).registerClientbound(ClientboundPackets1_13.COOLDOWN, this::lambda$registerPackets$0);
        ((Protocol1_12_2To1_13)this.protocol).registerClientbound(ClientboundPackets1_13.BLOCK_ACTION, new PacketHandlers(this){
            final BlockItemPackets1_13 this$0;
            {
                this.this$0 = blockItemPackets1_13;
            }

            @Override
            public void register() {
                this.map(Type.POSITION);
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.VAR_INT);
                this.handler(1::lambda$register$0);
            }

            private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                int n = packetWrapper.get(Type.VAR_INT, 0);
                if (n == 73) {
                    n = 25;
                } else if (n == 99) {
                    n = 33;
                } else if (n == 92) {
                    n = 29;
                } else if (n == 142) {
                    n = 54;
                } else if (n == 305) {
                    n = 146;
                } else if (n == 249) {
                    n = 130;
                } else if (n == 257) {
                    n = 138;
                } else if (n == 140) {
                    n = 52;
                } else if (n == 472) {
                    n = 209;
                } else if (n >= 483 && n <= 498) {
                    n = n - 483 + 219;
                }
                packetWrapper.set(Type.VAR_INT, 0, n);
            }
        });
        ((Protocol1_12_2To1_13)this.protocol).registerClientbound(ClientboundPackets1_13.BLOCK_ENTITY_DATA, new PacketHandlers(this){
            final BlockItemPackets1_13 this$0;
            {
                this.this$0 = blockItemPackets1_13;
            }

            @Override
            public void register() {
                this.map(Type.POSITION);
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.NBT);
                this.handler(2::lambda$register$0);
            }

            private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                BackwardsBlockEntityProvider backwardsBlockEntityProvider = Via.getManager().getProviders().get(BackwardsBlockEntityProvider.class);
                if (packetWrapper.get(Type.UNSIGNED_BYTE, 0) == 5) {
                    packetWrapper.cancel();
                }
                packetWrapper.set(Type.NBT, 0, backwardsBlockEntityProvider.transform(packetWrapper.user(), packetWrapper.get(Type.POSITION, 0), packetWrapper.get(Type.NBT, 0)));
            }
        });
        ((Protocol1_12_2To1_13)this.protocol).registerClientbound(ClientboundPackets1_13.UNLOAD_CHUNK, BlockItemPackets1_13::lambda$registerPackets$2);
        ((Protocol1_12_2To1_13)this.protocol).registerClientbound(ClientboundPackets1_13.BLOCK_CHANGE, new PacketHandlers(this){
            final BlockItemPackets1_13 this$0;
            {
                this.this$0 = blockItemPackets1_13;
            }

            @Override
            public void register() {
                this.map(Type.POSITION);
                this.handler(this::lambda$register$0);
            }

            private void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                int n = packetWrapper.read(Type.VAR_INT);
                Position position = packetWrapper.get(Type.POSITION, 0);
                BackwardsBlockStorage backwardsBlockStorage = packetWrapper.user().get(BackwardsBlockStorage.class);
                backwardsBlockStorage.checkAndStore(position, n);
                packetWrapper.write(Type.VAR_INT, ((Protocol1_12_2To1_13)BlockItemPackets1_13.access$000(this.this$0)).getMappingData().getNewBlockStateId(n));
                BlockItemPackets1_13.access$100(packetWrapper.user(), n, position);
            }
        });
        ((Protocol1_12_2To1_13)this.protocol).registerClientbound(ClientboundPackets1_13.MULTI_BLOCK_CHANGE, new PacketHandlers(this){
            final BlockItemPackets1_13 this$0;
            {
                this.this$0 = blockItemPackets1_13;
            }

            @Override
            public void register() {
                this.map(Type.INT);
                this.map(Type.INT);
                this.map(Type.BLOCK_CHANGE_RECORD_ARRAY);
                this.handler(this::lambda$register$0);
            }

            private void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                BackwardsBlockStorage backwardsBlockStorage = packetWrapper.user().get(BackwardsBlockStorage.class);
                for (BlockChangeRecord blockChangeRecord : packetWrapper.get(Type.BLOCK_CHANGE_RECORD_ARRAY, 0)) {
                    int n = packetWrapper.get(Type.INT, 0);
                    int n2 = packetWrapper.get(Type.INT, 1);
                    int n3 = blockChangeRecord.getBlockId();
                    Position position = new Position(blockChangeRecord.getSectionX() + n * 16, blockChangeRecord.getY(), blockChangeRecord.getSectionZ() + n2 * 16);
                    backwardsBlockStorage.checkAndStore(position, n3);
                    BlockItemPackets1_13.access$100(packetWrapper.user(), n3, position);
                    blockChangeRecord.setBlockId(((Protocol1_12_2To1_13)BlockItemPackets1_13.access$200(this.this$0)).getMappingData().getNewBlockStateId(n3));
                }
            }
        });
        ((Protocol1_12_2To1_13)this.protocol).registerClientbound(ClientboundPackets1_13.WINDOW_ITEMS, new PacketHandlers(this){
            final BlockItemPackets1_13 this$0;
            {
                this.this$0 = blockItemPackets1_13;
            }

            @Override
            public void register() {
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.FLAT_ITEM_ARRAY, Type.ITEM_ARRAY);
                this.handler(this.this$0.itemArrayHandler(Type.ITEM_ARRAY));
            }
        });
        ((Protocol1_12_2To1_13)this.protocol).registerClientbound(ClientboundPackets1_13.SET_SLOT, new PacketHandlers(this){
            final BlockItemPackets1_13 this$0;
            {
                this.this$0 = blockItemPackets1_13;
            }

            @Override
            public void register() {
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.SHORT);
                this.map(Type.FLAT_ITEM, Type.ITEM);
                this.handler(this.this$0.itemToClientHandler(Type.ITEM));
            }
        });
        ((Protocol1_12_2To1_13)this.protocol).registerClientbound(ClientboundPackets1_13.CHUNK_DATA, this::lambda$registerPackets$3);
        ((Protocol1_12_2To1_13)this.protocol).registerClientbound(ClientboundPackets1_13.EFFECT, new PacketHandlers(this){
            final BlockItemPackets1_13 this$0;
            {
                this.this$0 = blockItemPackets1_13;
            }

            @Override
            public void register() {
                this.map(Type.INT);
                this.map(Type.POSITION);
                this.map(Type.INT);
                this.handler(this::lambda$register$0);
            }

            private void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                int n = packetWrapper.get(Type.INT, 0);
                int n2 = packetWrapper.get(Type.INT, 1);
                if (n == 1010) {
                    packetWrapper.set(Type.INT, 1, ((Protocol1_12_2To1_13)BlockItemPackets1_13.access$300(this.this$0)).getMappingData().getItemMappings().getNewId(n2) >> 4);
                } else if (n == 2001) {
                    n2 = ((Protocol1_12_2To1_13)BlockItemPackets1_13.access$400(this.this$0)).getMappingData().getNewBlockStateId(n2);
                    int n3 = n2 >> 4;
                    int n4 = n2 & 0xF;
                    packetWrapper.set(Type.INT, 1, n3 & 0xFFF | n4 << 12);
                }
            }
        });
        ((Protocol1_12_2To1_13)this.protocol).registerClientbound(ClientboundPackets1_13.MAP_DATA, new PacketHandlers(this){
            final BlockItemPackets1_13 this$0;
            {
                this.this$0 = blockItemPackets1_13;
            }

            @Override
            public void register() {
                this.map(Type.VAR_INT);
                this.map(Type.BYTE);
                this.map(Type.BOOLEAN);
                this.handler(8::lambda$register$0);
            }

            private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                int n = packetWrapper.passthrough(Type.VAR_INT);
                for (int i = 0; i < n; ++i) {
                    int n2 = packetWrapper.read(Type.VAR_INT);
                    byte by = packetWrapper.read(Type.BYTE);
                    byte by2 = packetWrapper.read(Type.BYTE);
                    byte by3 = packetWrapper.read(Type.BYTE);
                    if (packetWrapper.read(Type.BOOLEAN).booleanValue()) {
                        packetWrapper.read(Type.COMPONENT);
                    }
                    if (n2 > 9) {
                        packetWrapper.set(Type.VAR_INT, 1, packetWrapper.get(Type.VAR_INT, 1) - 1);
                        continue;
                    }
                    packetWrapper.write(Type.BYTE, (byte)(n2 << 4 | by3 & 0xF));
                    packetWrapper.write(Type.BYTE, by);
                    packetWrapper.write(Type.BYTE, by2);
                }
            }
        });
        ((Protocol1_12_2To1_13)this.protocol).registerClientbound(ClientboundPackets1_13.ENTITY_EQUIPMENT, new PacketHandlers(this){
            final BlockItemPackets1_13 this$0;
            {
                this.this$0 = blockItemPackets1_13;
            }

            @Override
            public void register() {
                this.map(Type.VAR_INT);
                this.map(Type.VAR_INT);
                this.map(Type.FLAT_ITEM, Type.ITEM);
                this.handler(this.this$0.itemToClientHandler(Type.ITEM));
            }
        });
        ((Protocol1_12_2To1_13)this.protocol).registerClientbound(ClientboundPackets1_13.WINDOW_PROPERTY, new PacketHandlers(this){
            final BlockItemPackets1_13 this$0;
            {
                this.this$0 = blockItemPackets1_13;
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
                    short s2 = packetWrapper.get(Type.SHORT, 1);
                    packetWrapper.set(Type.SHORT, 1, (short)((Protocol1_12_2To1_13)BlockItemPackets1_13.access$500(this.this$0)).getMappingData().getEnchantmentMappings().getNewId(s2));
                }
            }
        });
        ((Protocol1_12_2To1_13)this.protocol).registerServerbound(ServerboundPackets1_12_1.CREATIVE_INVENTORY_ACTION, new PacketHandlers(this){
            final BlockItemPackets1_13 this$0;
            {
                this.this$0 = blockItemPackets1_13;
            }

            @Override
            public void register() {
                this.map(Type.SHORT);
                this.map(Type.ITEM, Type.FLAT_ITEM);
                this.handler(this.this$0.itemToServerHandler(Type.FLAT_ITEM));
            }
        });
        ((Protocol1_12_2To1_13)this.protocol).registerServerbound(ServerboundPackets1_12_1.CLICK_WINDOW, new PacketHandlers(this){
            final BlockItemPackets1_13 this$0;
            {
                this.this$0 = blockItemPackets1_13;
            }

            @Override
            public void register() {
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.SHORT);
                this.map(Type.BYTE);
                this.map(Type.SHORT);
                this.map(Type.VAR_INT);
                this.map(Type.ITEM, Type.FLAT_ITEM);
                this.handler(this.this$0.itemToServerHandler(Type.FLAT_ITEM));
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
        Object t;
        if (item == null) {
            return null;
        }
        int n = item.identifier();
        Integer n2 = null;
        boolean bl = false;
        CompoundTag compoundTag = item.tag();
        if (compoundTag != null && (t = compoundTag.remove(this.extraNbtTag)) != null) {
            n2 = ((NumberTag)t).asInt();
            bl = true;
        }
        if (n2 == null) {
            super.handleItemToClient(item);
            if (item.identifier() == -1) {
                if (n == 362) {
                    n2 = 0xE50000;
                } else {
                    if (!Via.getConfig().isSuppressConversionWarnings() || Via.getManager().isDebug()) {
                        ViaBackwards.getPlatform().getLogger().warning("Failed to get 1.12 item for " + n);
                    }
                    n2 = 65536;
                }
            } else {
                if (compoundTag == null) {
                    compoundTag = item.tag();
                }
                n2 = this.itemIdToRaw(item.identifier(), item, compoundTag);
            }
        }
        item.setIdentifier(n2 >> 16);
        item.setData((short)(n2 & 0xFFFF));
        if (compoundTag != null) {
            StringTag stringTag;
            Object object;
            if (BlockItemPackets1_13.isDamageable(item.identifier())) {
                object = compoundTag.remove("Damage");
                if (!bl && object instanceof IntTag) {
                    item.setData((short)((Integer)((Tag)object).getValue()).intValue());
                }
            }
            if (item.identifier() == 358) {
                object = compoundTag.remove("map");
                if (!bl && object instanceof IntTag) {
                    item.setData((short)((Integer)((Tag)object).getValue()).intValue());
                }
            }
            this.invertShieldAndBannerId(item, compoundTag);
            object = (CompoundTag)compoundTag.get("display");
            if (object != null && (stringTag = (StringTag)((CompoundTag)object).get("Name")) != null) {
                ((CompoundTag)object).put(this.extraNbtTag + "|Name", new StringTag(stringTag.getValue()));
                stringTag.setValue(((Protocol1_12_2To1_13)this.protocol).jsonToLegacy(stringTag.getValue()));
            }
            this.rewriteEnchantmentsToClient(compoundTag, false);
            this.rewriteEnchantmentsToClient(compoundTag, true);
            this.rewriteCanPlaceToClient(compoundTag, "CanPlaceOn");
            this.rewriteCanPlaceToClient(compoundTag, "CanDestroy");
        }
        return item;
    }

    private int itemIdToRaw(int n, Item item, CompoundTag compoundTag) {
        Optional<String> optional = SpawnEggRewriter.getEntityId(n);
        if (optional.isPresent()) {
            if (compoundTag == null) {
                compoundTag = new CompoundTag();
                item.setTag(compoundTag);
            }
            if (!compoundTag.contains("EntityTag")) {
                CompoundTag compoundTag2 = new CompoundTag();
                compoundTag2.put("id", new StringTag(optional.get()));
                compoundTag.put("EntityTag", compoundTag2);
            }
            return 1;
        }
        return n >> 4 << 16 | n & 0xF;
    }

    private void rewriteCanPlaceToClient(CompoundTag compoundTag, String string) {
        if (!(compoundTag.get(string) instanceof ListTag)) {
            return;
        }
        ListTag listTag = (ListTag)compoundTag.get(string);
        if (listTag == null) {
            return;
        }
        ListTag listTag2 = new ListTag(StringTag.class);
        compoundTag.put(this.extraNbtTag + "|" + string, ConverterRegistry.convertToTag(ConverterRegistry.convertToValue(listTag)));
        for (Tag tag : listTag) {
            String[] stringArray;
            Object object = tag.getValue();
            String[] stringArray2 = stringArray = object instanceof String ? BlockIdData.fallbackReverseMapping.get(((String)object).replace("minecraft:", "")) : null;
            if (stringArray != null) {
                for (String string2 : stringArray) {
                    listTag2.add(new StringTag(string2));
                }
                continue;
            }
            listTag2.add(tag);
        }
        compoundTag.put(string, listTag2);
    }

    private void rewriteEnchantmentsToClient(CompoundTag compoundTag, boolean bl) {
        Object object;
        Tag tag;
        String string = bl ? "StoredEnchantments" : "Enchantments";
        ListTag listTag = (ListTag)compoundTag.get(string);
        if (listTag == null) {
            return;
        }
        ListTag listTag2 = new ListTag(CompoundTag.class);
        ListTag listTag3 = new ListTag(CompoundTag.class);
        ArrayList<Tag> arrayList = new ArrayList<Tag>();
        boolean bl2 = false;
        for (Tag tag2 : listTag.clone()) {
            Object object2;
            tag = (CompoundTag)tag2;
            Object object3 = ((CompoundTag)tag).get("id");
            if (!(object3 instanceof StringTag)) continue;
            Object object4 = (String)((Tag)object3).getValue();
            NumberTag numberTag = (NumberTag)((CompoundTag)tag).get("lvl");
            if (numberTag == null) continue;
            int n = numberTag.asInt();
            int n2 = n < Short.MAX_VALUE ? (int)n : Short.MAX_VALUE;
            String string2 = this.enchantmentMappings.get(object4);
            if (string2 != null) {
                arrayList.add(new StringTag(string2 + " " + EnchantmentRewriter.getRomanNumber(n2)));
                listTag2.add(tag);
                continue;
            }
            if (((String)object4).isEmpty()) continue;
            Short s = (Short)Protocol1_13To1_12_2.MAPPINGS.getOldEnchantmentsIds().inverse().get(Key.stripMinecraftNamespace((String)object4));
            if (s == null) {
                if (!((String)object4).startsWith("viaversion:legacy/")) {
                    listTag2.add(tag);
                    if (ViaBackwards.getConfig().addCustomEnchantsToLore()) {
                        object2 = object4;
                        int n3 = ((String)object2).indexOf(58) + 1;
                        if (n3 != 0 && n3 != ((String)object2).length()) {
                            object2 = ((String)object2).substring(n3);
                        }
                        object2 = "\u00a77" + Character.toUpperCase(((String)object2).charAt(0)) + ((String)object2).substring(1).toLowerCase(Locale.ENGLISH);
                        arrayList.add(new StringTag((String)object2 + " " + EnchantmentRewriter.getRomanNumber(n2)));
                    }
                    if (!Via.getManager().isDebug()) continue;
                    ViaBackwards.getPlatform().getLogger().warning("Found unknown enchant: " + (String)object4);
                    continue;
                }
                s = Short.valueOf(((String)object4).substring(18));
            }
            if (n2 != 0) {
                bl2 = true;
            }
            object2 = new CompoundTag();
            ((CompoundTag)object2).put("id", new ShortTag(s));
            ((CompoundTag)object2).put("lvl", new ShortTag((short)n2));
            listTag3.add((Tag)object2);
        }
        if (!bl && !bl2) {
            object = (IntTag)compoundTag.get("HideFlags");
            if (object == null) {
                object = new IntTag();
                compoundTag.put(this.extraNbtTag + "|DummyEnchant", new ByteTag());
            } else {
                compoundTag.put(this.extraNbtTag + "|OldHideFlags", new IntTag(((IntTag)object).asByte()));
            }
            if (listTag3.size() == 0) {
                Tag tag2;
                tag2 = new CompoundTag();
                ((CompoundTag)tag2).put("id", new ShortTag(0));
                ((CompoundTag)tag2).put("lvl", new ShortTag(0));
                listTag3.add(tag2);
            }
            int n = ((IntTag)object).asByte() | 1;
            ((IntTag)object).setValue(n);
            compoundTag.put("HideFlags", object);
        }
        if (listTag2.size() != 0) {
            compoundTag.put(this.extraNbtTag + "|" + string, listTag2);
            if (!arrayList.isEmpty()) {
                ListTag listTag4;
                object = (CompoundTag)compoundTag.get("display");
                if (object == null) {
                    object = new CompoundTag();
                    compoundTag.put("display", object);
                }
                if ((listTag4 = (ListTag)((CompoundTag)object).get("Lore")) == null) {
                    listTag4 = new ListTag(StringTag.class);
                    ((CompoundTag)object).put("Lore", listTag4);
                    compoundTag.put(this.extraNbtTag + "|DummyLore", new ByteTag());
                } else if (listTag4.size() != 0) {
                    tag = new ListTag(StringTag.class);
                    for (Object object4 : listTag4) {
                        ((ListTag)tag).add(((Tag)object4).clone());
                    }
                    compoundTag.put(this.extraNbtTag + "|OldLore", tag);
                    arrayList.addAll((Collection<Tag>)listTag4.getValue());
                }
                listTag4.setValue(arrayList);
            }
        }
        compoundTag.remove("Enchantments");
        compoundTag.put(bl ? string : "ench", listTag3);
    }

    @Override
    public Item handleItemToServer(Item item) {
        if (item == null) {
            return null;
        }
        CompoundTag compoundTag = item.tag();
        int n = item.identifier() << 16 | item.data() & 0xFFFF;
        int n2 = item.identifier() << 4 | item.data() & 0xF;
        if (BlockItemPackets1_13.isDamageable(item.identifier())) {
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
            CompoundTag compoundTag2;
            StringTag stringTag;
            this.invertShieldAndBannerId(item, compoundTag);
            Object t = compoundTag.get("display");
            if (t instanceof CompoundTag && (stringTag = (StringTag)(compoundTag2 = (CompoundTag)t).get("Name")) != null) {
                StringTag stringTag2 = (StringTag)compoundTag2.remove(this.extraNbtTag + "|Name");
                stringTag.setValue(stringTag2 != null ? stringTag2.getValue() : ChatRewriter.legacyTextToJsonString(stringTag.getValue()));
            }
            this.rewriteEnchantmentsToServer(compoundTag, false);
            this.rewriteEnchantmentsToServer(compoundTag, true);
            this.rewriteCanPlaceToServer(compoundTag, "CanPlaceOn");
            this.rewriteCanPlaceToServer(compoundTag, "CanDestroy");
            if (item.identifier() == 383) {
                compoundTag2 = (CompoundTag)compoundTag.get("EntityTag");
                if (compoundTag2 != null && (stringTag = (StringTag)compoundTag2.get("id")) != null) {
                    n2 = SpawnEggRewriter.getSpawnEggId(stringTag.getValue());
                    if (n2 == -1) {
                        n2 = 25100288;
                    } else {
                        compoundTag2.remove("id");
                        if (compoundTag2.isEmpty()) {
                            compoundTag.remove("EntityTag");
                        }
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
        int n3 = item.identifier();
        item.setIdentifier(n2);
        super.handleItemToServer(item);
        if (item.identifier() != n2 && item.identifier() != -1) {
            return item;
        }
        item.setIdentifier(n3);
        int n4 = -1;
        if (((Protocol1_12_2To1_13)this.protocol).getMappingData().getItemMappings().inverse().getNewId(n2) == -1) {
            if (!BlockItemPackets1_13.isDamageable(item.identifier()) && item.identifier() != 358) {
                if (compoundTag == null) {
                    compoundTag = new CompoundTag();
                    item.setTag(compoundTag);
                }
                compoundTag.put(this.extraNbtTag, new IntTag(n));
            }
            if (item.identifier() == 229) {
                n4 = 362;
            } else if (item.identifier() == 31 && item.data() == 0) {
                n2 = 512;
            } else if (((Protocol1_12_2To1_13)this.protocol).getMappingData().getItemMappings().inverse().getNewId(n2 & 0xFFFFFFF0) != -1) {
                n2 &= 0xFFFFFFF0;
            } else {
                if (!Via.getConfig().isSuppressConversionWarnings() || Via.getManager().isDebug()) {
                    ViaBackwards.getPlatform().getLogger().warning("Failed to get 1.13 item for " + item.identifier());
                }
                n2 = 16;
            }
        }
        if (n4 == -1) {
            n4 = ((Protocol1_12_2To1_13)this.protocol).getMappingData().getItemMappings().inverse().getNewId(n2);
        }
        item.setIdentifier(n4);
        item.setData((short)0);
        return item;
    }

    private void rewriteCanPlaceToServer(CompoundTag compoundTag, String string) {
        if (!(compoundTag.get(string) instanceof ListTag)) {
            return;
        }
        ListTag listTag = (ListTag)compoundTag.remove(this.extraNbtTag + "|" + string);
        if (listTag != null) {
            compoundTag.put(string, ConverterRegistry.convertToTag(ConverterRegistry.convertToValue(listTag)));
        } else {
            listTag = (ListTag)compoundTag.get(string);
            if (listTag != null) {
                ListTag listTag2 = new ListTag(StringTag.class);
                for (Tag tag : listTag) {
                    String string2;
                    String[] stringArray;
                    Object object = tag.getValue();
                    String string3 = object.toString().replace("minecraft:", "");
                    int n = Ints.tryParse(string3);
                    String string4 = (String)BlockIdData.numberIdToString.get(n);
                    if (string4 != null) {
                        string3 = string4;
                    }
                    if ((stringArray = BlockIdData.blockIdMapping.get(string2 = string3.toLowerCase(Locale.ROOT))) != null) {
                        for (String string5 : stringArray) {
                            listTag2.add(new StringTag(string5));
                        }
                        continue;
                    }
                    listTag2.add(new StringTag(string2));
                }
                compoundTag.put(string, listTag2);
            }
        }
    }

    private void rewriteEnchantmentsToServer(CompoundTag compoundTag, boolean bl) {
        Tag tag;
        Object object3;
        Object object2;
        String string = bl ? "StoredEnchantments" : "Enchantments";
        ListTag listTag = (ListTag)compoundTag.get(bl ? string : "ench");
        if (listTag == null) {
            return;
        }
        ListTag listTag2 = new ListTag(CompoundTag.class);
        boolean bl2 = false;
        if (!bl) {
            object2 = (IntTag)compoundTag.remove(this.extraNbtTag + "|OldHideFlags");
            if (object2 != null) {
                compoundTag.put("HideFlags", new IntTag(((IntTag)object2).asByte()));
                bl2 = true;
            } else if (compoundTag.remove(this.extraNbtTag + "|DummyEnchant") != null) {
                compoundTag.remove("HideFlags");
                bl2 = true;
            }
        }
        for (Object object3 : listTag) {
            tag = new CompoundTag();
            short s = ((NumberTag)((CompoundTag)object3).get("id")).asShort();
            short s2 = ((NumberTag)((CompoundTag)object3).get("lvl")).asShort();
            if (bl2 && s == 0 && s2 == 0) continue;
            String string2 = (String)Protocol1_13To1_12_2.MAPPINGS.getOldEnchantmentsIds().get(s);
            if (string2 == null) {
                string2 = "viaversion:legacy/" + s;
            }
            ((CompoundTag)tag).put("id", new StringTag(string2));
            ((CompoundTag)tag).put("lvl", new ShortTag(s2));
            listTag2.add(tag);
        }
        object2 = (ListTag)compoundTag.remove(this.extraNbtTag + "|Enchantments");
        if (object2 != null) {
            object3 = ((ListTag)object2).iterator();
            while (object3.hasNext()) {
                tag = (Tag)object3.next();
                listTag2.add(tag);
            }
        }
        if ((object3 = (CompoundTag)compoundTag.get("display")) == null) {
            object3 = new CompoundTag();
            compoundTag.put("display", object3);
        }
        if ((tag = (ListTag)compoundTag.remove(this.extraNbtTag + "|OldLore")) != null) {
            ListTag listTag3 = (ListTag)((CompoundTag)object3).get("Lore");
            if (listTag3 == null) {
                listTag3 = new ListTag();
                compoundTag.put("Lore", listTag3);
            }
            listTag3.setValue((List<Tag>)((ListTag)tag).getValue());
        } else if (compoundTag.remove(this.extraNbtTag + "|DummyLore") != null) {
            ((CompoundTag)object3).remove("Lore");
            if (((CompoundTag)object3).isEmpty()) {
                compoundTag.remove("display");
            }
        }
        if (!bl) {
            compoundTag.remove("ench");
        }
        compoundTag.put(string, listTag2);
    }

    private void invertShieldAndBannerId(Item item, CompoundTag compoundTag) {
        IntTag intTag;
        if (item.identifier() != 442 && item.identifier() != 425) {
            return;
        }
        Object t = compoundTag.get("BlockEntityTag");
        if (!(t instanceof CompoundTag)) {
            return;
        }
        CompoundTag compoundTag2 = (CompoundTag)t;
        Object t2 = compoundTag2.get("Base");
        if (t2 instanceof IntTag) {
            intTag = (IntTag)t2;
            intTag.setValue(15 - intTag.asInt());
        }
        if ((intTag = compoundTag2.get("Patterns")) instanceof ListTag) {
            ListTag listTag = (ListTag)((Object)intTag);
            for (Tag tag : listTag) {
                if (!(tag instanceof CompoundTag)) continue;
                IntTag intTag2 = (IntTag)((CompoundTag)tag).get("Color");
                intTag2.setValue(15 - intTag2.asInt());
            }
        }
    }

    private static void flowerPotSpecialTreatment(UserConnection userConnection, int n, Position position) throws Exception {
        if (FlowerPotHandler.isFlowah(n)) {
            BackwardsBlockEntityProvider backwardsBlockEntityProvider = Via.getManager().getProviders().get(BackwardsBlockEntityProvider.class);
            CompoundTag compoundTag = backwardsBlockEntityProvider.transform(userConnection, position, "minecraft:flower_pot");
            PacketWrapper packetWrapper = PacketWrapper.create(ClientboundPackets1_12_1.BLOCK_CHANGE, userConnection);
            packetWrapper.write(Type.POSITION, position);
            packetWrapper.write(Type.VAR_INT, 0);
            packetWrapper.scheduleSend(Protocol1_12_2To1_13.class);
            PacketWrapper packetWrapper2 = PacketWrapper.create(ClientboundPackets1_12_1.BLOCK_CHANGE, userConnection);
            packetWrapper2.write(Type.POSITION, position);
            packetWrapper2.write(Type.VAR_INT, Protocol1_12_2To1_13.MAPPINGS.getNewBlockStateId(n));
            packetWrapper2.scheduleSend(Protocol1_12_2To1_13.class);
            PacketWrapper packetWrapper3 = PacketWrapper.create(ClientboundPackets1_12_1.BLOCK_ENTITY_DATA, userConnection);
            packetWrapper3.write(Type.POSITION, position);
            packetWrapper3.write(Type.UNSIGNED_BYTE, (short)5);
            packetWrapper3.write(Type.NBT, compoundTag);
            packetWrapper3.scheduleSend(Protocol1_12_2To1_13.class);
        }
    }

    private void lambda$registerPackets$3(PacketWrapper packetWrapper) throws Exception {
        int n;
        int n2;
        int n3;
        Object object;
        ClientWorld clientWorld = packetWrapper.user().get(ClientWorld.class);
        Chunk1_9_3_4Type chunk1_9_3_4Type = new Chunk1_9_3_4Type(clientWorld);
        Chunk1_13Type chunk1_13Type = new Chunk1_13Type(clientWorld);
        Chunk chunk = packetWrapper.read(chunk1_13Type);
        BackwardsBlockEntityProvider backwardsBlockEntityProvider = Via.getManager().getProviders().get(BackwardsBlockEntityProvider.class);
        BackwardsBlockStorage backwardsBlockStorage = packetWrapper.user().get(BackwardsBlockStorage.class);
        for (CompoundTag object2 : chunk.getBlockEntities()) {
            String string;
            object = object2.get("id");
            if (object == null || !backwardsBlockEntityProvider.isHandled(string = (String)((Tag)object).getValue()) || (n3 = ((NumberTag)object2.get("y")).asInt() >> 4) < 0 || n3 > 15) continue;
            ChunkSection chunkSection = chunk.getSections()[n3];
            n2 = ((NumberTag)object2.get("x")).asInt();
            int n4 = ((NumberTag)object2.get("y")).asInt();
            int n5 = ((NumberTag)object2.get("z")).asInt();
            Position position = new Position(n2, (short)n4, n5);
            int n6 = chunkSection.palette(PaletteType.BLOCKS).idAt(n2 & 0xF, n4 & 0xF, n5 & 0xF);
            backwardsBlockStorage.checkAndStore(position, n6);
            backwardsBlockEntityProvider.transform(packetWrapper.user(), position, object2);
        }
        for (n = 0; n < chunk.getSections().length; ++n) {
            int n7;
            ChunkSection chunkSection = chunk.getSections()[n];
            if (chunkSection == null) continue;
            object = chunkSection.palette(PaletteType.BLOCKS);
            for (n7 = 0; n7 < 16; ++n7) {
                for (n3 = 0; n3 < 16; ++n3) {
                    for (int i = 0; i < 16; ++i) {
                        n2 = object.idAt(i, n7, n3);
                        if (!FlowerPotHandler.isFlowah(n2)) continue;
                        Position position = new Position(i + (chunk.getX() << 4), (short)(n7 + (n << 4)), n3 + (chunk.getZ() << 4));
                        backwardsBlockStorage.checkAndStore(position, n2);
                        CompoundTag compoundTag = backwardsBlockEntityProvider.transform(packetWrapper.user(), position, "minecraft:flower_pot");
                        chunk.getBlockEntities().add(compoundTag);
                    }
                }
            }
            for (n7 = 0; n7 < object.size(); ++n7) {
                n3 = ((Protocol1_12_2To1_13)this.protocol).getMappingData().getNewBlockStateId(object.idByIndex(n7));
                object.setIdByIndex(n7, n3);
            }
        }
        if (chunk.isBiomeData()) {
            for (n = 0; n < 256; ++n) {
                int n8 = chunk.getBiomeData()[n];
                int n9 = -1;
                switch (n8) {
                    case 40: 
                    case 41: 
                    case 42: 
                    case 43: {
                        n9 = 9;
                        break;
                    }
                    case 47: 
                    case 48: 
                    case 49: {
                        n9 = 24;
                        break;
                    }
                    case 50: {
                        n9 = 10;
                        break;
                    }
                    case 44: 
                    case 45: 
                    case 46: {
                        n9 = 0;
                    }
                }
                if (n9 == -1) continue;
                chunk.getBiomeData()[n] = n9;
            }
        }
        packetWrapper.write(chunk1_9_3_4Type, chunk);
    }

    private static void lambda$registerPackets$2(PacketWrapper packetWrapper) throws Exception {
        int n = packetWrapper.passthrough(Type.INT) << 4;
        int n2 = packetWrapper.passthrough(Type.INT) << 4;
        int n3 = n + 15;
        int n4 = n2 + 15;
        BackwardsBlockStorage backwardsBlockStorage = packetWrapper.user().get(BackwardsBlockStorage.class);
        backwardsBlockStorage.getBlocks().entrySet().removeIf(arg_0 -> BlockItemPackets1_13.lambda$null$1(n, n2, n3, n4, arg_0));
    }

    private static boolean lambda$null$1(int n, int n2, int n3, int n4, Map.Entry entry) {
        Position position = (Position)entry.getKey();
        return position.x() >= n && position.z() >= n2 && position.x() <= n3 && position.z() <= n4;
    }

    private void lambda$registerPackets$0(PacketWrapper packetWrapper) throws Exception {
        int n = packetWrapper.read(Type.VAR_INT);
        int n2 = ((Protocol1_12_2To1_13)this.protocol).getMappingData().getItemMappings().getNewId(n);
        if (n2 == -1) {
            packetWrapper.cancel();
            return;
        }
        if (SpawnEggRewriter.getEntityId(n2).isPresent()) {
            packetWrapper.write(Type.VAR_INT, 6128);
            return;
        }
        packetWrapper.write(Type.VAR_INT, n2 >> 4);
    }

    static Protocol access$000(BlockItemPackets1_13 blockItemPackets1_13) {
        return blockItemPackets1_13.protocol;
    }

    static void access$100(UserConnection userConnection, int n, Position position) throws Exception {
        BlockItemPackets1_13.flowerPotSpecialTreatment(userConnection, n, position);
    }

    static Protocol access$200(BlockItemPackets1_13 blockItemPackets1_13) {
        return blockItemPackets1_13.protocol;
    }

    static Protocol access$300(BlockItemPackets1_13 blockItemPackets1_13) {
        return blockItemPackets1_13.protocol;
    }

    static Protocol access$400(BlockItemPackets1_13 blockItemPackets1_13) {
        return blockItemPackets1_13.protocol;
    }

    static Protocol access$500(BlockItemPackets1_13 blockItemPackets1_13) {
        return blockItemPackets1_13.protocol;
    }
}

