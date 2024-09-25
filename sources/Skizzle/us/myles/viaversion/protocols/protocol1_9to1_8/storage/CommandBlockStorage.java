/*
 * Decompiled with CFR 0.150.
 */
package us.myles.ViaVersion.protocols.protocol1_9to1_8.storage;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import us.myles.ViaVersion.api.Pair;
import us.myles.ViaVersion.api.data.StoredObject;
import us.myles.ViaVersion.api.data.UserConnection;
import us.myles.ViaVersion.api.minecraft.Position;
import us.myles.viaversion.libs.opennbt.tag.builtin.ByteTag;
import us.myles.viaversion.libs.opennbt.tag.builtin.CompoundTag;

public class CommandBlockStorage
extends StoredObject {
    private final Map<Pair<Integer, Integer>, Map<Position, CompoundTag>> storedCommandBlocks = new ConcurrentHashMap<Pair<Integer, Integer>, Map<Position, CompoundTag>>();
    private boolean permissions = false;

    public CommandBlockStorage(UserConnection user) {
        super(user);
    }

    public void unloadChunk(int x, int z) {
        Pair<Integer, Integer> chunkPos = new Pair<Integer, Integer>(x, z);
        this.storedCommandBlocks.remove(chunkPos);
    }

    public void addOrUpdateBlock(Position position, CompoundTag tag) {
        Map<Position, CompoundTag> blocks;
        Pair<Integer, Integer> chunkPos = this.getChunkCoords(position);
        if (!this.storedCommandBlocks.containsKey(chunkPos)) {
            this.storedCommandBlocks.put(chunkPos, new ConcurrentHashMap());
        }
        if ((blocks = this.storedCommandBlocks.get(chunkPos)).containsKey(position) && blocks.get(position).equals(tag)) {
            return;
        }
        blocks.put(position, tag);
    }

    private Pair<Integer, Integer> getChunkCoords(Position position) {
        int chunkX = Math.floorDiv(position.getX(), 16);
        int chunkZ = Math.floorDiv(position.getZ(), 16);
        return new Pair<Integer, Integer>(chunkX, chunkZ);
    }

    public Optional<CompoundTag> getCommandBlock(Position position) {
        Pair<Integer, Integer> chunkCoords = this.getChunkCoords(position);
        Map<Position, CompoundTag> blocks = this.storedCommandBlocks.get(chunkCoords);
        if (blocks == null) {
            return Optional.empty();
        }
        CompoundTag tag = blocks.get(position);
        if (tag == null) {
            return Optional.empty();
        }
        tag = tag.clone();
        tag.put(new ByteTag("powered", 0));
        tag.put(new ByteTag("auto", 0));
        tag.put(new ByteTag("conditionMet", 0));
        return Optional.of(tag);
    }

    public void unloadChunks() {
        this.storedCommandBlocks.clear();
    }

    public boolean isPermissions() {
        return this.permissions;
    }

    public void setPermissions(boolean permissions) {
        this.permissions = permissions;
    }
}

