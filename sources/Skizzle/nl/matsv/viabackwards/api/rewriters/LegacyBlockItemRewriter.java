/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Nullable
 */
package nl.matsv.viabackwards.api.rewriters;

import java.util.HashMap;
import java.util.Map;
import nl.matsv.viabackwards.api.BackwardsProtocol;
import nl.matsv.viabackwards.api.data.MappedLegacyBlockItem;
import nl.matsv.viabackwards.api.data.VBMappingDataLoader;
import nl.matsv.viabackwards.api.rewriters.ItemRewriterBase;
import nl.matsv.viabackwards.protocol.protocol1_11_1to1_12.data.BlockColors;
import nl.matsv.viabackwards.utils.Block;
import org.jetbrains.annotations.Nullable;
import us.myles.ViaVersion.api.minecraft.chunks.Chunk;
import us.myles.ViaVersion.api.minecraft.chunks.ChunkSection;
import us.myles.ViaVersion.api.minecraft.item.Item;
import us.myles.ViaVersion.protocols.protocol1_13to1_12_2.ChatRewriter;
import us.myles.viaversion.libs.bungeecordchat.api.ChatColor;
import us.myles.viaversion.libs.fastutil.ints.Int2ObjectMap;
import us.myles.viaversion.libs.fastutil.ints.Int2ObjectOpenHashMap;
import us.myles.viaversion.libs.gson.JsonElement;
import us.myles.viaversion.libs.gson.JsonObject;
import us.myles.viaversion.libs.gson.JsonPrimitive;
import us.myles.viaversion.libs.opennbt.tag.builtin.ByteTag;
import us.myles.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import us.myles.viaversion.libs.opennbt.tag.builtin.IntTag;
import us.myles.viaversion.libs.opennbt.tag.builtin.StringTag;
import us.myles.viaversion.libs.opennbt.tag.builtin.Tag;

public abstract class LegacyBlockItemRewriter<T extends BackwardsProtocol>
extends ItemRewriterBase<T> {
    private static final Map<String, Int2ObjectMap<MappedLegacyBlockItem>> LEGACY_MAPPINGS = new HashMap<String, Int2ObjectMap<MappedLegacyBlockItem>>();
    protected final Int2ObjectMap<MappedLegacyBlockItem> replacementData;

    protected LegacyBlockItemRewriter(T protocol) {
        super(protocol, false);
        this.replacementData = LEGACY_MAPPINGS.get(protocol.getClass().getSimpleName().split("To")[1].replace("_", "."));
    }

    @Override
    @Nullable
    public Item handleItemToClient(Item item) {
        if (item == null) {
            return null;
        }
        MappedLegacyBlockItem data = (MappedLegacyBlockItem)this.replacementData.get(item.getIdentifier());
        if (data == null) {
            return super.handleItemToClient(item);
        }
        short originalData = item.getData();
        item.setIdentifier(data.getId());
        if (data.getData() != -1) {
            item.setData(data.getData());
        }
        if (data.getName() != null) {
            String value;
            StringTag nameTag;
            CompoundTag display;
            if (item.getTag() == null) {
                item.setTag(new CompoundTag(""));
            }
            if ((display = (CompoundTag)item.getTag().get("display")) == null) {
                display = new CompoundTag("display");
                item.getTag().put(display);
            }
            if ((nameTag = (StringTag)display.get("Name")) == null) {
                nameTag = new StringTag("Name", data.getName());
                display.put(nameTag);
                display.put(new ByteTag(this.nbtTagName + "|customName"));
            }
            if ((value = nameTag.getValue()).contains("%vb_color%")) {
                display.put(new StringTag("Name", value.replace("%vb_color%", BlockColors.get(originalData))));
            }
        }
        return item;
    }

    public int handleBlockID(int idx) {
        int type = idx >> 4;
        int meta = idx & 0xF;
        Block b = this.handleBlock(type, meta);
        if (b == null) {
            return idx;
        }
        return b.getId() << 4 | b.getData() & 0xF;
    }

    @Nullable
    public Block handleBlock(int blockId, int data) {
        MappedLegacyBlockItem settings = (MappedLegacyBlockItem)this.replacementData.get(blockId);
        if (settings == null || !settings.isBlock()) {
            return null;
        }
        Block block = settings.getBlock();
        if (block.getData() == -1) {
            return block.withData(data);
        }
        return block;
    }

    protected void handleChunk(Chunk chunk) {
        MappedLegacyBlockItem settings;
        HashMap<Pos, CompoundTag> tags = new HashMap<Pos, CompoundTag>();
        for (CompoundTag tag : chunk.getBlockEntities()) {
            int block;
            int btype;
            Object zTag;
            Object yTag;
            Object xTag = tag.get("x");
            if (xTag == null || (yTag = tag.get("y")) == null || (zTag = tag.get("z")) == null) continue;
            Pos pos = new Pos((int)((Integer)((Tag)xTag).getValue() & 0xF), (Integer)((Tag)yTag).getValue(), (int)((Integer)((Tag)zTag).getValue() & 0xF));
            tags.put(pos, tag);
            ChunkSection section = chunk.getSections()[pos.getY() >> 4];
            if (section == null || (settings = (MappedLegacyBlockItem)this.replacementData.get(btype = (block = section.getFlatBlock(pos.getX(), pos.getY() & 0xF, pos.getZ())) >> 4)) == null || !settings.hasBlockEntityHandler()) continue;
            settings.getBlockEntityHandler().handleOrNewCompoundTag(block, tag);
        }
        for (int i = 0; i < chunk.getSections().length; ++i) {
            ChunkSection section = chunk.getSections()[i];
            if (section == null) continue;
            boolean hasBlockEntityHandler = false;
            for (int j = 0; j < section.getPaletteSize(); ++j) {
                MappedLegacyBlockItem settings2;
                int meta;
                int block = section.getPaletteEntry(j);
                int btype = block >> 4;
                Block b = this.handleBlock(btype, meta = block & 0xF);
                if (b != null) {
                    section.setPaletteEntry(j, b.getId() << 4 | b.getData() & 0xF);
                }
                if (hasBlockEntityHandler || (settings2 = (MappedLegacyBlockItem)this.replacementData.get(btype)) == null || !settings2.hasBlockEntityHandler()) continue;
                hasBlockEntityHandler = true;
            }
            if (!hasBlockEntityHandler) continue;
            for (int x = 0; x < 16; ++x) {
                for (int y = 0; y < 16; ++y) {
                    for (int z = 0; z < 16; ++z) {
                        Pos pos;
                        int block = section.getFlatBlock(x, y, z);
                        int btype = block >> 4;
                        int meta = block & 0xF;
                        settings = (MappedLegacyBlockItem)this.replacementData.get(btype);
                        if (settings == null || !settings.hasBlockEntityHandler() || tags.containsKey(pos = new Pos(x, y + (i << 4), z))) continue;
                        CompoundTag tag = new CompoundTag("");
                        tag.put(new IntTag("x", x + (chunk.getX() << 4)));
                        tag.put(new IntTag("y", y + (i << 4)));
                        tag.put(new IntTag("z", z + (chunk.getZ() << 4)));
                        settings.getBlockEntityHandler().handleOrNewCompoundTag(block, tag);
                        chunk.getBlockEntities().add(tag);
                    }
                }
            }
        }
    }

    protected CompoundTag getNamedTag(String text) {
        CompoundTag tag = new CompoundTag("");
        tag.put(new CompoundTag("display"));
        text = ChatColor.RESET + text;
        ((CompoundTag)tag.get("display")).put(new StringTag("Name", this.jsonNameFormat ? ChatRewriter.legacyTextToJson(text).toString() : text));
        return tag;
    }

    static {
        JsonObject jsonObject = VBMappingDataLoader.loadFromDataDir("legacy-mappings.json");
        for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
            Int2ObjectOpenHashMap<MappedLegacyBlockItem> mappings = new Int2ObjectOpenHashMap<MappedLegacyBlockItem>(8);
            LEGACY_MAPPINGS.put(entry.getKey(), mappings);
            for (Map.Entry<String, JsonElement> dataEntry : entry.getValue().getAsJsonObject().entrySet()) {
                boolean block;
                JsonObject object = dataEntry.getValue().getAsJsonObject();
                int id = object.getAsJsonPrimitive("id").getAsInt();
                JsonPrimitive jsonData = object.getAsJsonPrimitive("data");
                short data = jsonData != null ? jsonData.getAsShort() : (short)0;
                String name = object.getAsJsonPrimitive("name").getAsString();
                JsonPrimitive blockField = object.getAsJsonPrimitive("block");
                boolean bl = block = blockField != null && blockField.getAsBoolean();
                if (dataEntry.getKey().indexOf(45) != -1) {
                    String[] split = dataEntry.getKey().split("-", 2);
                    int from = Integer.parseInt(split[0]);
                    int to = Integer.parseInt(split[1]);
                    if (name.contains("%color%")) {
                        for (int i = from; i <= to; ++i) {
                            mappings.put(i, new MappedLegacyBlockItem(id, data, name.replace("%color%", BlockColors.get(i - from)), block));
                        }
                        continue;
                    }
                    MappedLegacyBlockItem mappedBlockItem = new MappedLegacyBlockItem(id, data, name, block);
                    for (int i = from; i <= to; ++i) {
                        mappings.put(i, mappedBlockItem);
                    }
                    continue;
                }
                mappings.put(Integer.parseInt(dataEntry.getKey()), new MappedLegacyBlockItem(id, data, name, block));
            }
        }
    }

    private static final class Pos {
        private final int x;
        private final short y;
        private final int z;

        private Pos(int x, int y, int z) {
            this.x = x;
            this.y = (short)y;
            this.z = z;
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

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || this.getClass() != o.getClass()) {
                return false;
            }
            Pos pos = (Pos)o;
            if (this.x != pos.x) {
                return false;
            }
            if (this.y != pos.y) {
                return false;
            }
            return this.z == pos.z;
        }

        public int hashCode() {
            int result = this.x;
            result = 31 * result + this.y;
            result = 31 * result + this.z;
            return result;
        }

        public String toString() {
            return "Pos{x=" + this.x + ", y=" + this.y + ", z=" + this.z + '}';
        }
    }
}

