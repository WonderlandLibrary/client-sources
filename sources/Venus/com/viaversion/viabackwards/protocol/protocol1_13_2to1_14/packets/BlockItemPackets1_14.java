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
import com.viaversion.viaversion.api.minecraft.chunks.PaletteType;
import com.viaversion.viaversion.api.minecraft.entities.Entity1_14Types;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
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
import java.util.Set;

public class BlockItemPackets1_14
extends ItemRewriter<ClientboundPackets1_14, ServerboundPackets1_13, Protocol1_13_2To1_14> {
    private EnchantmentRewriter enchantmentRewriter;

    public BlockItemPackets1_14(Protocol1_13_2To1_14 protocol1_13_2To1_14) {
        super(protocol1_13_2To1_14);
    }

    @Override
    protected void registerPackets() {
        ((Protocol1_13_2To1_14)this.protocol).registerServerbound(ServerboundPackets1_13.EDIT_BOOK, this::lambda$registerPackets$0);
        ((Protocol1_13_2To1_14)this.protocol).registerClientbound(ClientboundPackets1_14.OPEN_WINDOW, BlockItemPackets1_14::lambda$registerPackets$1);
        ((Protocol1_13_2To1_14)this.protocol).registerClientbound(ClientboundPackets1_14.OPEN_HORSE_WINDOW, ClientboundPackets1_13.OPEN_WINDOW, BlockItemPackets1_14::lambda$registerPackets$2);
        BlockRewriter<ClientboundPackets1_14> blockRewriter = new BlockRewriter<ClientboundPackets1_14>(this.protocol, Type.POSITION);
        this.registerSetCooldown(ClientboundPackets1_14.COOLDOWN);
        this.registerWindowItems(ClientboundPackets1_14.WINDOW_ITEMS, Type.FLAT_VAR_INT_ITEM_ARRAY);
        this.registerSetSlot(ClientboundPackets1_14.SET_SLOT, Type.FLAT_VAR_INT_ITEM);
        this.registerAdvancements(ClientboundPackets1_14.ADVANCEMENTS, Type.FLAT_VAR_INT_ITEM);
        ((Protocol1_13_2To1_14)this.protocol).registerClientbound(ClientboundPackets1_14.TRADE_LIST, ClientboundPackets1_13.PLUGIN_MESSAGE, this::lambda$registerPackets$3);
        ((Protocol1_13_2To1_14)this.protocol).registerClientbound(ClientboundPackets1_14.OPEN_BOOK, ClientboundPackets1_13.PLUGIN_MESSAGE, BlockItemPackets1_14::lambda$registerPackets$4);
        ((Protocol1_13_2To1_14)this.protocol).registerClientbound(ClientboundPackets1_14.ENTITY_EQUIPMENT, new PacketHandlers(this){
            final BlockItemPackets1_14 this$0;
            {
                this.this$0 = blockItemPackets1_14;
            }

            @Override
            public void register() {
                this.map(Type.VAR_INT);
                this.map(Type.VAR_INT);
                this.map(Type.FLAT_VAR_INT_ITEM);
                this.handler(this.this$0.itemToClientHandler(Type.FLAT_VAR_INT_ITEM));
                this.handler(1::lambda$register$0);
            }

            private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                int n = packetWrapper.get(Type.VAR_INT, 0);
                EntityType entityType = packetWrapper.user().getEntityTracker(Protocol1_13_2To1_14.class).entityType(n);
                if (entityType == null) {
                    return;
                }
                if (entityType.isOrHasParent(Entity1_14Types.ABSTRACT_HORSE)) {
                    int n2;
                    packetWrapper.setPacketType(ClientboundPackets1_13.ENTITY_METADATA);
                    packetWrapper.resetReader();
                    packetWrapper.passthrough(Type.VAR_INT);
                    packetWrapper.read(Type.VAR_INT);
                    Item item = packetWrapper.read(Type.FLAT_VAR_INT_ITEM);
                    int n3 = n2 = item == null || item.identifier() == 0 ? 0 : item.identifier() - 726;
                    if (n2 < 0 || n2 > 3) {
                        packetWrapper.cancel();
                        return;
                    }
                    ArrayList<Metadata> arrayList = new ArrayList<Metadata>();
                    arrayList.add(new Metadata(16, Types1_13_2.META_TYPES.varIntType, n2));
                    packetWrapper.write(Types1_13.METADATA_LIST, arrayList);
                }
            }
        });
        RecipeRewriter recipeRewriter = new RecipeRewriter(this.protocol);
        ImmutableSet<String> immutableSet = ImmutableSet.of("crafting_special_suspiciousstew", "blasting", "smoking", "campfire_cooking", "stonecutting");
        ((Protocol1_13_2To1_14)this.protocol).registerClientbound(ClientboundPackets1_14.DECLARE_RECIPES, arg_0 -> BlockItemPackets1_14.lambda$registerPackets$5(immutableSet, recipeRewriter, arg_0));
        this.registerClickWindow(ServerboundPackets1_13.CLICK_WINDOW, Type.FLAT_VAR_INT_ITEM);
        this.registerCreativeInvAction(ServerboundPackets1_13.CREATIVE_INVENTORY_ACTION, Type.FLAT_VAR_INT_ITEM);
        ((Protocol1_13_2To1_14)this.protocol).registerClientbound(ClientboundPackets1_14.BLOCK_BREAK_ANIMATION, new PacketHandlers(this){
            final BlockItemPackets1_14 this$0;
            {
                this.this$0 = blockItemPackets1_14;
            }

            @Override
            public void register() {
                this.map(Type.VAR_INT);
                this.map(Type.POSITION1_14, Type.POSITION);
                this.map(Type.BYTE);
            }
        });
        ((Protocol1_13_2To1_14)this.protocol).registerClientbound(ClientboundPackets1_14.BLOCK_ENTITY_DATA, new PacketHandlers(this){
            final BlockItemPackets1_14 this$0;
            {
                this.this$0 = blockItemPackets1_14;
            }

            @Override
            public void register() {
                this.map(Type.POSITION1_14, Type.POSITION);
            }
        });
        ((Protocol1_13_2To1_14)this.protocol).registerClientbound(ClientboundPackets1_14.BLOCK_ACTION, new PacketHandlers(this){
            final BlockItemPackets1_14 this$0;
            {
                this.this$0 = blockItemPackets1_14;
            }

            @Override
            public void register() {
                this.map(Type.POSITION1_14, Type.POSITION);
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.VAR_INT);
                this.handler(this::lambda$register$0);
            }

            private void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                int n = ((Protocol1_13_2To1_14)BlockItemPackets1_14.access$000(this.this$0)).getMappingData().getNewBlockId(packetWrapper.get(Type.VAR_INT, 0));
                if (n == -1) {
                    packetWrapper.cancel();
                    return;
                }
                packetWrapper.set(Type.VAR_INT, 0, n);
            }
        });
        ((Protocol1_13_2To1_14)this.protocol).registerClientbound(ClientboundPackets1_14.BLOCK_CHANGE, new PacketHandlers(this){
            final BlockItemPackets1_14 this$0;
            {
                this.this$0 = blockItemPackets1_14;
            }

            @Override
            public void register() {
                this.map(Type.POSITION1_14, Type.POSITION);
                this.map(Type.VAR_INT);
                this.handler(this::lambda$register$0);
            }

            private void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                int n = packetWrapper.get(Type.VAR_INT, 0);
                packetWrapper.set(Type.VAR_INT, 0, ((Protocol1_13_2To1_14)BlockItemPackets1_14.access$100(this.this$0)).getMappingData().getNewBlockStateId(n));
            }
        });
        blockRewriter.registerMultiBlockChange(ClientboundPackets1_14.MULTI_BLOCK_CHANGE);
        ((Protocol1_13_2To1_14)this.protocol).registerClientbound(ClientboundPackets1_14.EXPLOSION, new PacketHandlers(this){
            final BlockItemPackets1_14 this$0;
            {
                this.this$0 = blockItemPackets1_14;
            }

            @Override
            public void register() {
                this.map(Type.FLOAT);
                this.map(Type.FLOAT);
                this.map(Type.FLOAT);
                this.map(Type.FLOAT);
                this.handler(6::lambda$register$0);
            }

            private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                for (int i = 0; i < 3; ++i) {
                    float f = packetWrapper.get(Type.FLOAT, i).floatValue();
                    if (!(f < 0.0f)) continue;
                    f = (float)Math.floor(f);
                    packetWrapper.set(Type.FLOAT, i, Float.valueOf(f));
                }
            }
        });
        ((Protocol1_13_2To1_14)this.protocol).registerClientbound(ClientboundPackets1_14.CHUNK_DATA, this::lambda$registerPackets$6);
        ((Protocol1_13_2To1_14)this.protocol).registerClientbound(ClientboundPackets1_14.UNLOAD_CHUNK, BlockItemPackets1_14::lambda$registerPackets$7);
        ((Protocol1_13_2To1_14)this.protocol).registerClientbound(ClientboundPackets1_14.EFFECT, new PacketHandlers(this){
            final BlockItemPackets1_14 this$0;
            {
                this.this$0 = blockItemPackets1_14;
            }

            @Override
            public void register() {
                this.map(Type.INT);
                this.map(Type.POSITION1_14, Type.POSITION);
                this.map(Type.INT);
                this.handler(this::lambda$register$0);
            }

            private void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                int n = packetWrapper.get(Type.INT, 0);
                int n2 = packetWrapper.get(Type.INT, 1);
                if (n == 1010) {
                    packetWrapper.set(Type.INT, 1, ((Protocol1_13_2To1_14)BlockItemPackets1_14.access$200(this.this$0)).getMappingData().getNewItemId(n2));
                } else if (n == 2001) {
                    packetWrapper.set(Type.INT, 1, ((Protocol1_13_2To1_14)BlockItemPackets1_14.access$300(this.this$0)).getMappingData().getNewBlockStateId(n2));
                }
            }
        });
        this.registerSpawnParticle(ClientboundPackets1_14.SPAWN_PARTICLE, Type.FLAT_VAR_INT_ITEM, Type.FLOAT);
        ((Protocol1_13_2To1_14)this.protocol).registerClientbound(ClientboundPackets1_14.MAP_DATA, new PacketHandlers(this){
            final BlockItemPackets1_14 this$0;
            {
                this.this$0 = blockItemPackets1_14;
            }

            @Override
            public void register() {
                this.map(Type.VAR_INT);
                this.map(Type.BYTE);
                this.map(Type.BOOLEAN);
                this.map((Type)Type.BOOLEAN, Type.NOTHING);
            }
        });
        ((Protocol1_13_2To1_14)this.protocol).registerClientbound(ClientboundPackets1_14.SPAWN_POSITION, new PacketHandlers(this){
            final BlockItemPackets1_14 this$0;
            {
                this.this$0 = blockItemPackets1_14;
            }

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
        ListTag listTag;
        CompoundTag compoundTag;
        if (item == null) {
            return null;
        }
        super.handleItemToClient(item);
        CompoundTag compoundTag2 = item.tag();
        if (compoundTag2 != null && (compoundTag = (CompoundTag)compoundTag2.get("display")) != null && (listTag = (ListTag)compoundTag.get("Lore")) != null) {
            this.saveListTag(compoundTag, listTag, "Lore");
            for (Tag tag : listTag) {
                StringTag stringTag;
                String string;
                if (!(tag instanceof StringTag) || (string = (stringTag = (StringTag)tag).getValue()) == null || string.isEmpty()) continue;
                stringTag.setValue(ChatRewriter.jsonToLegacyText(string));
            }
        }
        this.enchantmentRewriter.handleToClient(item);
        return item;
    }

    @Override
    public Item handleItemToServer(Item item) {
        ListTag listTag;
        CompoundTag compoundTag;
        if (item == null) {
            return null;
        }
        CompoundTag compoundTag2 = item.tag();
        if (compoundTag2 != null && (compoundTag = (CompoundTag)compoundTag2.get("display")) != null && (listTag = (ListTag)compoundTag.get("Lore")) != null && !this.hasBackupTag(compoundTag, "Lore")) {
            for (Tag tag : listTag) {
                if (!(tag instanceof StringTag)) continue;
                StringTag stringTag = (StringTag)tag;
                stringTag.setValue(ChatRewriter.legacyTextToJsonString(stringTag.getValue()));
            }
        }
        this.enchantmentRewriter.handleToServer(item);
        super.handleItemToServer(item);
        return item;
    }

    private static void lambda$registerPackets$7(PacketWrapper packetWrapper) throws Exception {
        int n = packetWrapper.passthrough(Type.INT);
        int n2 = packetWrapper.passthrough(Type.INT);
        packetWrapper.user().get(ChunkLightStorage.class).unloadChunk(n, n2);
    }

    private void lambda$registerPackets$6(PacketWrapper packetWrapper) throws Exception {
        ClientWorld clientWorld = packetWrapper.user().get(ClientWorld.class);
        Chunk chunk = packetWrapper.read(new Chunk1_14Type());
        packetWrapper.write(new Chunk1_13Type(clientWorld), chunk);
        ChunkLightStorage.ChunkLight chunkLight = packetWrapper.user().get(ChunkLightStorage.class).getStoredLight(chunk.getX(), chunk.getZ());
        for (int i = 0; i < chunk.getSections().length; ++i) {
            int n;
            Object object;
            ChunkSection chunkSection = chunk.getSections()[i];
            if (chunkSection == null) continue;
            ChunkSectionLightImpl chunkSectionLightImpl = new ChunkSectionLightImpl();
            chunkSection.setLight(chunkSectionLightImpl);
            if (chunkLight == null) {
                chunkSectionLightImpl.setBlockLight(ChunkLightStorage.FULL_LIGHT);
                if (clientWorld.getEnvironment() == Environment.NORMAL) {
                    chunkSectionLightImpl.setSkyLight(ChunkLightStorage.FULL_LIGHT);
                }
            } else {
                object = chunkLight.getBlockLight()[i];
                chunkSectionLightImpl.setBlockLight((byte[])(object != null ? object : ChunkLightStorage.FULL_LIGHT));
                if (clientWorld.getEnvironment() == Environment.NORMAL) {
                    byte[] byArray = chunkLight.getSkyLight()[i];
                    chunkSectionLightImpl.setSkyLight(byArray != null ? byArray : ChunkLightStorage.FULL_LIGHT);
                }
            }
            object = chunkSection.palette(PaletteType.BLOCKS);
            if (Via.getConfig().isNonFullBlockLightFix() && chunkSection.getNonAirBlocksCount() != 0 && chunkSectionLightImpl.hasBlockLight()) {
                for (int j = 0; j < 16; ++j) {
                    for (n = 0; n < 16; ++n) {
                        for (int k = 0; k < 16; ++k) {
                            int n2 = object.idAt(j, n, k);
                            if (!Protocol1_14To1_13_2.MAPPINGS.getNonFullBlocks().contains(n2)) continue;
                            chunkSectionLightImpl.getBlockLightNibbleArray().set(j, n, k, 0);
                        }
                    }
                }
            }
            for (int j = 0; j < object.size(); ++j) {
                n = ((Protocol1_13_2To1_14)this.protocol).getMappingData().getNewBlockStateId(object.idByIndex(j));
                object.setIdByIndex(j, n);
            }
        }
    }

    private static void lambda$registerPackets$5(Set set, RecipeRewriter recipeRewriter, PacketWrapper packetWrapper) throws Exception {
        int n = packetWrapper.passthrough(Type.VAR_INT);
        int n2 = 0;
        for (int i = 0; i < n; ++i) {
            String string = packetWrapper.read(Type.STRING);
            String string2 = packetWrapper.read(Type.STRING);
            if (set.contains(string = string.replace("minecraft:", ""))) {
                switch (string) {
                    case "blasting": 
                    case "smoking": 
                    case "campfire_cooking": {
                        packetWrapper.read(Type.STRING);
                        packetWrapper.read(Type.FLAT_VAR_INT_ITEM_ARRAY_VAR_INT);
                        packetWrapper.read(Type.FLAT_VAR_INT_ITEM);
                        packetWrapper.read(Type.FLOAT);
                        packetWrapper.read(Type.VAR_INT);
                        break;
                    }
                    case "stonecutting": {
                        packetWrapper.read(Type.STRING);
                        packetWrapper.read(Type.FLAT_VAR_INT_ITEM_ARRAY_VAR_INT);
                        packetWrapper.read(Type.FLAT_VAR_INT_ITEM);
                    }
                }
                ++n2;
                continue;
            }
            packetWrapper.write(Type.STRING, string2);
            packetWrapper.write(Type.STRING, string);
            recipeRewriter.handleRecipeType(packetWrapper, string);
        }
        packetWrapper.set(Type.VAR_INT, 0, n - n2);
    }

    private static void lambda$registerPackets$4(PacketWrapper packetWrapper) throws Exception {
        packetWrapper.write(Type.STRING, "minecraft:book_open");
        packetWrapper.passthrough(Type.VAR_INT);
    }

    private void lambda$registerPackets$3(PacketWrapper packetWrapper) throws Exception {
        packetWrapper.write(Type.STRING, "minecraft:trader_list");
        int n = packetWrapper.read(Type.VAR_INT);
        packetWrapper.write(Type.INT, n);
        int n2 = packetWrapper.passthrough(Type.UNSIGNED_BYTE).shortValue();
        for (int i = 0; i < n2; ++i) {
            Item item = packetWrapper.read(Type.FLAT_VAR_INT_ITEM);
            item = this.handleItemToClient(item);
            packetWrapper.write(Type.FLAT_VAR_INT_ITEM, item);
            Item item2 = packetWrapper.read(Type.FLAT_VAR_INT_ITEM);
            item2 = this.handleItemToClient(item2);
            packetWrapper.write(Type.FLAT_VAR_INT_ITEM, item2);
            boolean bl = packetWrapper.passthrough(Type.BOOLEAN);
            if (bl) {
                Item item3 = packetWrapper.read(Type.FLAT_VAR_INT_ITEM);
                item3 = this.handleItemToClient(item3);
                packetWrapper.write(Type.FLAT_VAR_INT_ITEM, item3);
            }
            packetWrapper.passthrough(Type.BOOLEAN);
            packetWrapper.passthrough(Type.INT);
            packetWrapper.passthrough(Type.INT);
            packetWrapper.read(Type.INT);
            packetWrapper.read(Type.INT);
            packetWrapper.read(Type.FLOAT);
        }
        packetWrapper.read(Type.VAR_INT);
        packetWrapper.read(Type.VAR_INT);
        packetWrapper.read(Type.BOOLEAN);
    }

    private static void lambda$registerPackets$2(PacketWrapper packetWrapper) throws Exception {
        packetWrapper.passthrough(Type.UNSIGNED_BYTE);
        packetWrapper.write(Type.STRING, "EntityHorse");
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("translate", "minecraft.horse");
        packetWrapper.write(Type.COMPONENT, jsonObject);
        packetWrapper.write(Type.UNSIGNED_BYTE, packetWrapper.read(Type.VAR_INT).shortValue());
        packetWrapper.passthrough(Type.INT);
    }

    private static void lambda$registerPackets$1(PacketWrapper packetWrapper) throws Exception {
        JsonObject jsonObject;
        int n = packetWrapper.read(Type.VAR_INT);
        packetWrapper.write(Type.UNSIGNED_BYTE, (short)n);
        int n2 = packetWrapper.read(Type.VAR_INT);
        String string = null;
        String string2 = null;
        int n3 = 0;
        if (n2 < 6) {
            if (n2 == 2) {
                string2 = "Barrel";
            }
            string = "minecraft:container";
            n3 = (n2 + 1) * 9;
        } else {
            switch (n2) {
                case 11: {
                    string = "minecraft:crafting_table";
                    break;
                }
                case 9: 
                case 13: 
                case 14: 
                case 20: {
                    if (n2 == 9) {
                        string2 = "Blast Furnace";
                    } else if (n2 == 20) {
                        string2 = "Smoker";
                    } else if (n2 == 14) {
                        string2 = "Grindstone";
                    }
                    string = "minecraft:furnace";
                    n3 = 3;
                    break;
                }
                case 6: {
                    string = "minecraft:dropper";
                    n3 = 9;
                    break;
                }
                case 12: {
                    string = "minecraft:enchanting_table";
                    break;
                }
                case 10: {
                    string = "minecraft:brewing_stand";
                    n3 = 5;
                    break;
                }
                case 18: {
                    string = "minecraft:villager";
                    break;
                }
                case 8: {
                    string = "minecraft:beacon";
                    n3 = 1;
                    break;
                }
                case 7: 
                case 21: {
                    if (n2 == 21) {
                        string2 = "Cartography Table";
                    }
                    string = "minecraft:anvil";
                    break;
                }
                case 15: {
                    string = "minecraft:hopper";
                    n3 = 5;
                    break;
                }
                case 19: {
                    string = "minecraft:shulker_box";
                    n3 = 27;
                }
            }
        }
        if (string == null) {
            ViaBackwards.getPlatform().getLogger().warning("Can't open inventory for 1.13 player! Type: " + n2);
            packetWrapper.cancel();
            return;
        }
        packetWrapper.write(Type.STRING, string);
        JsonElement jsonElement = packetWrapper.read(Type.COMPONENT);
        if (string2 != null && jsonElement.isJsonObject() && (jsonObject = jsonElement.getAsJsonObject()).has("translate") && (n2 != 2 || jsonObject.getAsJsonPrimitive("translate").getAsString().equals("container.barrel"))) {
            jsonElement = ChatRewriter.legacyTextToJson(string2);
        }
        packetWrapper.write(Type.COMPONENT, jsonElement);
        packetWrapper.write(Type.UNSIGNED_BYTE, (short)n3);
    }

    private void lambda$registerPackets$0(PacketWrapper packetWrapper) throws Exception {
        this.handleItemToServer(packetWrapper.passthrough(Type.FLAT_VAR_INT_ITEM));
    }

    static Protocol access$000(BlockItemPackets1_14 blockItemPackets1_14) {
        return blockItemPackets1_14.protocol;
    }

    static Protocol access$100(BlockItemPackets1_14 blockItemPackets1_14) {
        return blockItemPackets1_14.protocol;
    }

    static Protocol access$200(BlockItemPackets1_14 blockItemPackets1_14) {
        return blockItemPackets1_14.protocol;
    }

    static Protocol access$300(BlockItemPackets1_14 blockItemPackets1_14) {
        return blockItemPackets1_14.protocol;
    }
}

