/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.protocols.protocol1_9to1_8.storage;

import com.viaversion.viaversion.api.connection.StorableObject;
import com.viaversion.viaversion.api.minecraft.Position;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.ByteTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.util.Pair;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class CommandBlockStorage
implements StorableObject {
    private final Map<Pair<Integer, Integer>, Map<Position, CompoundTag>> storedCommandBlocks = new ConcurrentHashMap<Pair<Integer, Integer>, Map<Position, CompoundTag>>();
    private boolean permissions = false;

    public void unloadChunk(int n, int n2) {
        Pair<Integer, Integer> pair = new Pair<Integer, Integer>(n, n2);
        this.storedCommandBlocks.remove(pair);
    }

    public void addOrUpdateBlock(Position position, CompoundTag compoundTag) {
        Map<Position, CompoundTag> map;
        Pair<Integer, Integer> pair = this.getChunkCoords(position);
        if (!this.storedCommandBlocks.containsKey(pair)) {
            this.storedCommandBlocks.put(pair, new ConcurrentHashMap());
        }
        if ((map = this.storedCommandBlocks.get(pair)).containsKey(position) && map.get(position).equals(compoundTag)) {
            return;
        }
        map.put(position, compoundTag);
    }

    private Pair<Integer, Integer> getChunkCoords(Position position) {
        int n = Math.floorDiv(position.x(), 16);
        int n2 = Math.floorDiv(position.z(), 16);
        return new Pair<Integer, Integer>(n, n2);
    }

    public Optional<CompoundTag> getCommandBlock(Position position) {
        Pair<Integer, Integer> pair = this.getChunkCoords(position);
        Map<Position, CompoundTag> map = this.storedCommandBlocks.get(pair);
        if (map == null) {
            return Optional.empty();
        }
        CompoundTag compoundTag = map.get(position);
        if (compoundTag == null) {
            return Optional.empty();
        }
        compoundTag = compoundTag.clone();
        compoundTag.put("powered", new ByteTag(0));
        compoundTag.put("auto", new ByteTag(0));
        compoundTag.put("conditionMet", new ByteTag(0));
        return Optional.of(compoundTag);
    }

    public void unloadChunks() {
        this.storedCommandBlocks.clear();
    }

    public boolean isPermissions() {
        return this.permissions;
    }

    public void setPermissions(boolean bl) {
        this.permissions = bl;
    }
}

