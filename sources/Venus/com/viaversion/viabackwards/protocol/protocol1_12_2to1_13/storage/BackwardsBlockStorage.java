/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.storage;

import com.viaversion.viaversion.api.connection.StorableObject;
import com.viaversion.viaversion.api.minecraft.Position;
import com.viaversion.viaversion.libs.fastutil.ints.IntOpenHashSet;
import com.viaversion.viaversion.libs.fastutil.ints.IntSet;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class BackwardsBlockStorage
implements StorableObject {
    private static final IntSet WHITELIST;
    private final Map<Position, Integer> blocks = new ConcurrentHashMap<Position, Integer>();

    public void checkAndStore(Position position, int n) {
        if (!WHITELIST.contains(n)) {
            this.blocks.remove(position);
            return;
        }
        this.blocks.put(position, n);
    }

    public boolean isWelcome(int n) {
        return WHITELIST.contains(n);
    }

    public Integer get(Position position) {
        return this.blocks.get(position);
    }

    public int remove(Position position) {
        return this.blocks.remove(position);
    }

    public void clear() {
        this.blocks.clear();
    }

    public Map<Position, Integer> getBlocks() {
        return this.blocks;
    }

    static {
        int n;
        WHITELIST = new IntOpenHashSet(779);
        for (n = 5265; n <= 5286; ++n) {
            WHITELIST.add(n);
        }
        for (n = 0; n < 256; ++n) {
            WHITELIST.add(748 + n);
        }
        for (n = 6854; n <= 7173; ++n) {
            WHITELIST.add(n);
        }
        WHITELIST.add(1647);
        for (n = 5447; n <= 5566; ++n) {
            WHITELIST.add(n);
        }
        for (n = 1028; n <= 1039; ++n) {
            WHITELIST.add(n);
        }
        for (n = 1047; n <= 1082; ++n) {
            WHITELIST.add(n);
        }
        for (n = 1099; n <= 1110; ++n) {
            WHITELIST.add(n);
        }
    }
}

