/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viarewind.protocol.protocol1_8to1_9.storage;

import com.viaversion.viarewind.protocol.protocol1_8to1_9.storage.EntityTracker;
import com.viaversion.viaversion.api.connection.StoredObject;
import com.viaversion.viaversion.api.connection.UserConnection;

public class BlockPlaceDestroyTracker
extends StoredObject {
    private long blockPlaced;
    private long lastMining;
    private boolean mining;

    public BlockPlaceDestroyTracker(UserConnection user) {
        super(user);
    }

    public long getBlockPlaced() {
        return this.blockPlaced;
    }

    public void place() {
        this.blockPlaced = System.currentTimeMillis();
    }

    public boolean isMining() {
        long time = System.currentTimeMillis() - this.lastMining;
        return time < 75L;
    }

    public void setMining(boolean mining) {
        this.mining = mining && this.getUser().get(EntityTracker.class).getPlayerGamemode() != 1;
        this.lastMining = System.currentTimeMillis();
    }

    public long getLastMining() {
        return this.lastMining;
    }

    public void updateMining() {
        if (this.isMining()) {
            this.lastMining = System.currentTimeMillis();
        }
    }

    public void setLastMining(long lastMining) {
        this.lastMining = lastMining;
    }
}

