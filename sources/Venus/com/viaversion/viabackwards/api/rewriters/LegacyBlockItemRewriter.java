/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viabackwards.api.rewriters;

import com.viaversion.viabackwards.api.BackwardsProtocol;
import com.viaversion.viabackwards.api.data.MappedLegacyBlockItem;
import com.viaversion.viabackwards.api.data.VBMappingDataLoader;
import com.viaversion.viabackwards.api.rewriters.ItemRewriterBase;
import com.viaversion.viabackwards.protocol.protocol1_11_1to1_12.data.BlockColors;
import com.viaversion.viabackwards.utils.Block;
import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
import com.viaversion.viaversion.api.minecraft.chunks.ChunkSection;
import com.viaversion.viaversion.api.minecraft.chunks.PaletteType;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
import com.viaversion.viaversion.api.protocol.packet.ServerboundPacketType;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMap;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectOpenHashMap;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.libs.gson.JsonObject;
import com.viaversion.viaversion.libs.gson.JsonPrimitive;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.ByteTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.IntTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.NumberTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ChatRewriter;
import java.util.HashMap;
import java.util.Map;
import org.checkerframework.checker.nullness.qual.Nullable;

public abstract class LegacyBlockItemRewriter<C extends ClientboundPacketType, S extends ServerboundPacketType, T extends BackwardsProtocol<C, ?, ?, S>>
extends ItemRewriterBase<C, S, T> {
    private static final Map<String, Int2ObjectMap<MappedLegacyBlockItem>> LEGACY_MAPPINGS = new HashMap<String, Int2ObjectMap<MappedLegacyBlockItem>>();
    protected final Int2ObjectMap<MappedLegacyBlockItem> replacementData;

    protected LegacyBlockItemRewriter(T t) {
        super(t, false);
        this.replacementData = LEGACY_MAPPINGS.get(t.getClass().getSimpleName().split("To")[1].replace("_", "."));
    }

    @Override
    public @Nullable Item handleItemToClient(@Nullable Item item) {
        if (item == null) {
            return null;
        }
        MappedLegacyBlockItem mappedLegacyBlockItem = (MappedLegacyBlockItem)this.replacementData.get(item.identifier());
        if (mappedLegacyBlockItem == null) {
            return super.handleItemToClient(item);
        }
        short s = item.data();
        item.setIdentifier(mappedLegacyBlockItem.getId());
        if (mappedLegacyBlockItem.getData() != -1) {
            item.setData(mappedLegacyBlockItem.getData());
        }
        if (mappedLegacyBlockItem.getName() != null) {
            String string;
            StringTag stringTag;
            CompoundTag compoundTag;
            if (item.tag() == null) {
                item.setTag(new CompoundTag());
            }
            if ((compoundTag = (CompoundTag)item.tag().get("display")) == null) {
                compoundTag = new CompoundTag();
                item.tag().put("display", compoundTag);
            }
            if ((stringTag = (StringTag)compoundTag.get("Name")) == null) {
                stringTag = new StringTag(mappedLegacyBlockItem.getName());
                compoundTag.put("Name", stringTag);
                compoundTag.put(this.nbtTagName + "|customName", new ByteTag());
            }
            if ((string = stringTag.getValue()).contains("%vb_color%")) {
                compoundTag.put("Name", new StringTag(string.replace("%vb_color%", BlockColors.get(s))));
            }
        }
        return item;
    }

    public int handleBlockID(int n) {
        int n2 = n >> 4;
        int n3 = n & 0xF;
        Block block = this.handleBlock(n2, n3);
        if (block == null) {
            return n;
        }
        return block.getId() << 4 | block.getData() & 0xF;
    }

    public @Nullable Block handleBlock(int n, int n2) {
        MappedLegacyBlockItem mappedLegacyBlockItem = (MappedLegacyBlockItem)this.replacementData.get(n);
        if (mappedLegacyBlockItem == null || !mappedLegacyBlockItem.isBlock()) {
            return null;
        }
        Block block = mappedLegacyBlockItem.getBlock();
        if (block.getData() == -1) {
            return block.withData(n2);
        }
        return block;
    }

    protected void handleChunk(Chunk chunk) {
        int n;
        MappedLegacyBlockItem mappedLegacyBlockItem;
        Object object;
        HashMap<Pos, CompoundTag> hashMap = new HashMap<Pos, CompoundTag>();
        for (CompoundTag object2 : chunk.getBlockEntities()) {
            int n6;
            ChunkSection k;
            Object t;
            Object t2 = object2.get("x");
            if (t2 == null || (object = object2.get("y")) == null || (t = object2.get("z")) == null) continue;
            Pos j = new Pos(((NumberTag)t2).asInt() & 0xF, ((NumberTag)object).asInt(), ((NumberTag)t).asInt() & 0xF, null);
            hashMap.put(j, object2);
            if (j.getY() < 0 || j.getY() > 255 || (k = chunk.getSections()[j.getY() >> 4]) == null || (mappedLegacyBlockItem = (MappedLegacyBlockItem)this.replacementData.get(n6 = (n = k.palette(PaletteType.BLOCKS).idAt(j.getX(), j.getY() & 0xF, j.getZ())) >> 4)) == null || !mappedLegacyBlockItem.hasBlockEntityHandler()) continue;
            mappedLegacyBlockItem.getBlockEntityHandler().handleOrNewCompoundTag(n, object2);
        }
        for (int i = 0; i < chunk.getSections().length; ++i) {
            int n2;
            ChunkSection chunkSection = chunk.getSections()[i];
            if (chunkSection == null) continue;
            boolean bl = false;
            object = chunkSection.palette(PaletteType.BLOCKS);
            for (n2 = 0; n2 < object.size(); ++n2) {
                int n3 = object.idByIndex(n2);
                int n4 = n3 >> 4;
                Block block = this.handleBlock(n4, n = n3 & 0xF);
                if (block != null) {
                    object.setIdByIndex(n2, block.getId() << 4 | block.getData() & 0xF);
                }
                if (bl || (mappedLegacyBlockItem = (MappedLegacyBlockItem)this.replacementData.get(n4)) == null || !mappedLegacyBlockItem.hasBlockEntityHandler()) continue;
                bl = true;
            }
            if (!bl) continue;
            for (n2 = 0; n2 < 16; ++n2) {
                for (int j = 0; j < 16; ++j) {
                    for (int k = 0; k < 16; ++k) {
                        Pos pos;
                        n = object.idAt(n2, j, k);
                        int n5 = n >> 4;
                        int n6 = n & 0xF;
                        MappedLegacyBlockItem mappedLegacyBlockItem2 = (MappedLegacyBlockItem)this.replacementData.get(n5);
                        if (mappedLegacyBlockItem2 == null || !mappedLegacyBlockItem2.hasBlockEntityHandler() || hashMap.containsKey(pos = new Pos(n2, j + (i << 4), k, null))) continue;
                        CompoundTag compoundTag = new CompoundTag();
                        compoundTag.put("x", new IntTag(n2 + (chunk.getX() << 4)));
                        compoundTag.put("y", new IntTag(j + (i << 4)));
                        compoundTag.put("z", new IntTag(k + (chunk.getZ() << 4)));
                        mappedLegacyBlockItem2.getBlockEntityHandler().handleOrNewCompoundTag(n, compoundTag);
                        chunk.getBlockEntities().add(compoundTag);
                    }
                }
            }
        }
    }

    protected CompoundTag getNamedTag(String string) {
        CompoundTag compoundTag = new CompoundTag();
        compoundTag.put("display", new CompoundTag());
        string = "\u00a7r" + string;
        ((CompoundTag)compoundTag.get("display")).put("Name", new StringTag(this.jsonNameFormat ? ChatRewriter.legacyTextToJsonString(string) : string));
        return compoundTag;
    }

    static {
        JsonObject jsonObject = VBMappingDataLoader.loadFromDataDir("legacy-mappings.json");
        for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
            Int2ObjectOpenHashMap<MappedLegacyBlockItem> int2ObjectOpenHashMap = new Int2ObjectOpenHashMap<MappedLegacyBlockItem>(8);
            LEGACY_MAPPINGS.put(entry.getKey(), int2ObjectOpenHashMap);
            for (Map.Entry<String, JsonElement> entry2 : entry.getValue().getAsJsonObject().entrySet()) {
                boolean bl;
                JsonObject jsonObject2 = entry2.getValue().getAsJsonObject();
                int n = jsonObject2.getAsJsonPrimitive("id").getAsInt();
                JsonPrimitive jsonPrimitive = jsonObject2.getAsJsonPrimitive("data");
                short s = jsonPrimitive != null ? jsonPrimitive.getAsShort() : (short)0;
                String string = jsonObject2.getAsJsonPrimitive("name").getAsString();
                JsonPrimitive jsonPrimitive2 = jsonObject2.getAsJsonPrimitive("block");
                boolean bl2 = bl = jsonPrimitive2 != null && jsonPrimitive2.getAsBoolean();
                if (entry2.getKey().indexOf(45) != -1) {
                    String[] stringArray = entry2.getKey().split("-", 2);
                    int n2 = Integer.parseInt(stringArray[0]);
                    int n3 = Integer.parseInt(stringArray[5]);
                    if (string.contains("%color%")) {
                        for (int i = n2; i <= n3; ++i) {
                            int2ObjectOpenHashMap.put(i, new MappedLegacyBlockItem(n, s, string.replace("%color%", BlockColors.get(i - n2)), bl));
                        }
                        continue;
                    }
                    MappedLegacyBlockItem mappedLegacyBlockItem = new MappedLegacyBlockItem(n, s, string, bl);
                    for (int i = n2; i <= n3; ++i) {
                        int2ObjectOpenHashMap.put(i, mappedLegacyBlockItem);
                    }
                    continue;
                }
                int2ObjectOpenHashMap.put(Integer.parseInt(entry2.getKey()), new MappedLegacyBlockItem(n, s, string, bl));
            }
        }
    }

    private static final class Pos {
        private final int x;
        private final short y;
        private final int z;

        private Pos(int n, int n2, int n3) {
            this.x = n;
            this.y = (short)n2;
            this.z = n3;
        }

        public int getX() {
            return this.x;
        }

        public int getY() {
            return this.y;
        }

        public int getZ() {
            return this.z;
        }

        public boolean equals(Object object) {
            if (this == object) {
                return false;
            }
            if (object == null || this.getClass() != object.getClass()) {
                return true;
            }
            Pos pos = (Pos)object;
            if (this.x != pos.x) {
                return true;
            }
            if (this.y != pos.y) {
                return true;
            }
            return this.z == pos.z;
        }

        public int hashCode() {
            int n = this.x;
            n = 31 * n + this.y;
            n = 31 * n + this.z;
            return n;
        }

        public String toString() {
            return "Pos{x=" + this.x + ", y=" + this.y + ", z=" + this.z + '}';
        }

        Pos(int n, int n2, int n3, 1 var4_4) {
            this(n, n2, n3);
        }
    }
}

