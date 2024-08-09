/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.storage;

import com.viaversion.viaversion.api.connection.StorableObject;
import com.viaversion.viaversion.api.minecraft.Position;
import com.viaversion.viaversion.libs.fastutil.ints.IntOpenHashSet;
import com.viaversion.viaversion.libs.fastutil.ints.IntSet;
import com.viaversion.viaversion.libs.flare.SyncMap;
import java.util.Map;

public class BlockStorage
implements StorableObject {
    private static final IntSet WHITELIST;
    private final Map<Position, ReplacementData> blocks = SyncMap.hashmap();

    public void store(Position position, int n) {
        this.store(position, n, -1);
    }

    public void store(Position position, int n, int n2) {
        if (!WHITELIST.contains(n)) {
            return;
        }
        this.blocks.put(position, new ReplacementData(n, n2));
    }

    public boolean isWelcome(int n) {
        return WHITELIST.contains(n);
    }

    public boolean contains(Position position) {
        return this.blocks.containsKey(position);
    }

    public ReplacementData get(Position position) {
        return this.blocks.get(position);
    }

    public ReplacementData remove(Position position) {
        return this.blocks.remove(position);
    }

    static {
        int n;
        WHITELIST = new IntOpenHashSet(46, 0.99f);
        WHITELIST.add(5266);
        for (n = 0; n < 16; ++n) {
            WHITELIST.add(972 + n);
        }
        for (n = 0; n < 20; ++n) {
            WHITELIST.add(6854 + n);
        }
        for (n = 0; n < 4; ++n) {
            WHITELIST.add(7110 + n);
        }
        for (n = 0; n < 5; ++n) {
            WHITELIST.add(5447 + n);
        }
    }

    public static final class ReplacementData {
        private final int original;
        private int replacement;

        public ReplacementData(int n, int n2) {
            this.original = n;
            this.replacement = n2;
        }

        public int getOriginal() {
            return this.original;
        }

        public int getReplacement() {
            return this.replacement;
        }

        public void setReplacement(int n) {
            this.replacement = n;
        }
    }
}

