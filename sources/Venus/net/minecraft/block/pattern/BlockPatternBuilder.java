/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.block.pattern;

import com.google.common.base.Joiner;
import com.google.common.base.Predicates;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import net.minecraft.block.pattern.BlockPattern;
import net.minecraft.util.CachedBlockInfo;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

public class BlockPatternBuilder {
    private static final Joiner COMMA_JOIN = Joiner.on(",");
    private final List<String[]> depth = Lists.newArrayList();
    private final Map<Character, Predicate<CachedBlockInfo>> symbolMap = Maps.newHashMap();
    private int aisleHeight;
    private int rowWidth;

    private BlockPatternBuilder() {
        this.symbolMap.put(Character.valueOf(' '), Predicates.alwaysTrue());
    }

    public BlockPatternBuilder aisle(String ... stringArray) {
        if (!ArrayUtils.isEmpty(stringArray) && !StringUtils.isEmpty(stringArray[0])) {
            if (this.depth.isEmpty()) {
                this.aisleHeight = stringArray.length;
                this.rowWidth = stringArray[0].length();
            }
            if (stringArray.length != this.aisleHeight) {
                throw new IllegalArgumentException("Expected aisle with height of " + this.aisleHeight + ", but was given one with a height of " + stringArray.length + ")");
            }
            for (String string : stringArray) {
                if (string.length() != this.rowWidth) {
                    throw new IllegalArgumentException("Not all rows in the given aisle are the correct width (expected " + this.rowWidth + ", found one with " + string.length() + ")");
                }
                for (char c : string.toCharArray()) {
                    if (this.symbolMap.containsKey(Character.valueOf(c))) continue;
                    this.symbolMap.put(Character.valueOf(c), null);
                }
            }
            this.depth.add(stringArray);
            return this;
        }
        throw new IllegalArgumentException("Empty pattern for aisle");
    }

    public static BlockPatternBuilder start() {
        return new BlockPatternBuilder();
    }

    public BlockPatternBuilder where(char c, Predicate<CachedBlockInfo> predicate) {
        this.symbolMap.put(Character.valueOf(c), predicate);
        return this;
    }

    public BlockPattern build() {
        return new BlockPattern(this.makePredicateArray());
    }

    private Predicate<CachedBlockInfo>[][][] makePredicateArray() {
        this.checkMissingPredicates();
        Predicate[][][] predicateArray = (Predicate[][][])Array.newInstance(Predicate.class, this.depth.size(), this.aisleHeight, this.rowWidth);
        for (int i = 0; i < this.depth.size(); ++i) {
            for (int j = 0; j < this.aisleHeight; ++j) {
                for (int k = 0; k < this.rowWidth; ++k) {
                    predicateArray[i][j][k] = this.symbolMap.get(Character.valueOf(this.depth.get(i)[j].charAt(k)));
                }
            }
        }
        return predicateArray;
    }

    private void checkMissingPredicates() {
        ArrayList<Character> arrayList = Lists.newArrayList();
        for (Map.Entry<Character, Predicate<CachedBlockInfo>> entry : this.symbolMap.entrySet()) {
            if (entry.getValue() != null) continue;
            arrayList.add(entry.getKey());
        }
        if (!arrayList.isEmpty()) {
            throw new IllegalStateException("Predicates for character(s) " + COMMA_JOIN.join(arrayList) + " are missing");
        }
    }
}

