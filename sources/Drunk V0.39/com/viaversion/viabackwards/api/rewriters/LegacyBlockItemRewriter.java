/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viabackwards.api.rewriters;

import com.viaversion.viabackwards.api.BackwardsProtocol;
import com.viaversion.viabackwards.api.data.MappedLegacyBlockItem;
import com.viaversion.viabackwards.api.data.VBMappingDataLoader;
import com.viaversion.viabackwards.api.rewriters.ItemRewriterBase;
import com.viaversion.viabackwards.protocol.protocol1_11_1to1_12.data.BlockColors;
import com.viaversion.viabackwards.utils.Block;
import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMap;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectOpenHashMap;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.libs.gson.JsonObject;
import com.viaversion.viaversion.libs.gson.JsonPrimitive;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.ByteTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ChatRewriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.checkerframework.checker.nullness.qual.Nullable;

public abstract class LegacyBlockItemRewriter<T extends BackwardsProtocol>
extends ItemRewriterBase<T> {
    private static final Map<String, Int2ObjectMap<MappedLegacyBlockItem>> LEGACY_MAPPINGS = new HashMap<String, Int2ObjectMap<MappedLegacyBlockItem>>();
    protected final Int2ObjectMap<MappedLegacyBlockItem> replacementData;

    protected LegacyBlockItemRewriter(T protocol) {
        super(protocol, false);
        this.replacementData = LEGACY_MAPPINGS.get(protocol.getClass().getSimpleName().split("To")[1].replace("_", "."));
    }

    @Override
    public @Nullable Item handleItemToClient(@Nullable Item item) {
        String value;
        StringTag nameTag;
        CompoundTag display;
        if (item == null) {
            return null;
        }
        MappedLegacyBlockItem data = (MappedLegacyBlockItem)this.replacementData.get(item.identifier());
        if (data == null) {
            return super.handleItemToClient(item);
        }
        short originalData = item.data();
        item.setIdentifier(data.getId());
        if (data.getData() != -1) {
            item.setData(data.getData());
        }
        if (data.getName() == null) return item;
        if (item.tag() == null) {
            item.setTag(new CompoundTag());
        }
        if ((display = (CompoundTag)item.tag().get("display")) == null) {
            display = new CompoundTag();
            item.tag().put("display", display);
        }
        if ((nameTag = (StringTag)display.get("Name")) == null) {
            nameTag = new StringTag(data.getName());
            display.put("Name", nameTag);
            display.put(this.nbtTagName + "|customName", new ByteTag());
        }
        if (!(value = nameTag.getValue()).contains("%vb_color%")) return item;
        display.put("Name", new StringTag(value.replace("%vb_color%", BlockColors.get(originalData))));
        return item;
    }

    public int handleBlockID(int idx) {
        int type = idx >> 4;
        int meta = idx & 0xF;
        Block b = this.handleBlock(type, meta);
        if (b != null) return b.getId() << 4 | b.getData() & 0xF;
        return idx;
    }

    public @Nullable Block handleBlock(int blockId, int data) {
        MappedLegacyBlockItem settings = (MappedLegacyBlockItem)this.replacementData.get(blockId);
        if (settings == null) return null;
        if (!settings.isBlock()) {
            return null;
        }
        Block block = settings.getBlock();
        if (block.getData() != -1) return block;
        return block.withData(data);
    }

    /*
     * Exception decompiling
     */
    protected void handleChunk(Chunk chunk) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [1[UNCONDITIONALDOLOOP]], but top level block is 4[UNCONDITIONALDOLOOP]
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:435)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:484)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:736)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:850)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
         *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
         *     at org.benf.cfr.reader.entities.Method.analyse(Method.java:531)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1055)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:942)
         *     at org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:257)
         *     at org.benf.cfr.reader.Driver.doJar(Driver.java:139)
         *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:76)
         *     at org.benf.cfr.reader.Main.main(Main.java:54)
         *     at the.bytecode.club.bytecodeviewer.decompilers.impl.CFRDecompiler.decompileToZip(CFRDecompiler.java:306)
         *     at the.bytecode.club.bytecodeviewer.resources.ResourceDecompiling.lambda$null$5(ResourceDecompiling.java:159)
         *     at java.lang.Thread.run(Unknown Source)
         */
        throw new IllegalStateException("Decompilation failed");
    }

    protected CompoundTag getNamedTag(String text) {
        CompoundTag tag = new CompoundTag();
        tag.put("display", new CompoundTag());
        text = "\u00a7r" + text;
        ((CompoundTag)tag.get("display")).put("Name", new StringTag(this.jsonNameFormat ? ChatRewriter.legacyTextToJsonString(text) : text));
        return tag;
    }

    static {
        JsonObject jsonObject = VBMappingDataLoader.loadFromDataDir("legacy-mappings.json");
        Iterator<Map.Entry<String, JsonElement>> iterator = jsonObject.entrySet().iterator();
        block0: while (iterator.hasNext()) {
            Map.Entry<String, JsonElement> entry = iterator.next();
            Int2ObjectOpenHashMap<MappedLegacyBlockItem> mappings = new Int2ObjectOpenHashMap<MappedLegacyBlockItem>(8);
            LEGACY_MAPPINGS.put(entry.getKey(), mappings);
            Iterator<Map.Entry<String, JsonElement>> iterator2 = entry.getValue().getAsJsonObject().entrySet().iterator();
            block1: while (true) {
                boolean block;
                if (!iterator2.hasNext()) continue block0;
                Map.Entry<String, JsonElement> dataEntry = iterator2.next();
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
                        int i = from;
                        while (true) {
                            if (i > to) continue block1;
                            mappings.put(i, new MappedLegacyBlockItem(id, data, name.replace("%color%", BlockColors.get(i - from)), block));
                            ++i;
                        }
                    }
                    MappedLegacyBlockItem mappedBlockItem = new MappedLegacyBlockItem(id, data, name, block);
                    int i = from;
                    while (true) {
                        if (i > to) continue block1;
                        mappings.put(i, mappedBlockItem);
                        ++i;
                    }
                }
                mappings.put(Integer.parseInt(dataEntry.getKey()), new MappedLegacyBlockItem(id, data, name, block));
            }
            break;
        }
        return;
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
            if (o == null) return false;
            if (this.getClass() != o.getClass()) {
                return false;
            }
            Pos pos = (Pos)o;
            if (this.x != pos.x) {
                return false;
            }
            if (this.y != pos.y) {
                return false;
            }
            if (this.z != pos.z) return false;
            return true;
        }

        public int hashCode() {
            int result = this.x;
            result = 31 * result + this.y;
            return 31 * result + this.z;
        }

        public String toString() {
            return "Pos{x=" + this.x + ", y=" + this.y + ", z=" + this.z + '}';
        }
    }
}

