/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.protocols.protocol1_9to1_8.storage;

import com.viaversion.viaversion.api.connection.StorableObject;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.Protocol1_9To1_8;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.storage.EntityTracker1_9;
import java.util.HashMap;
import java.util.Map;

public class InventoryTracker
implements StorableObject {
    private String inventory;
    private final Map<Short, Map<Short, Integer>> windowItemCache = new HashMap<Short, Map<Short, Integer>>();
    private int itemIdInCursor = 0;
    private boolean dragging = false;

    public String getInventory() {
        return this.inventory;
    }

    public void setInventory(String string) {
        this.inventory = string;
    }

    public void resetInventory(short s) {
        if (this.inventory == null) {
            this.itemIdInCursor = 0;
            this.dragging = false;
            if (s != 0) {
                this.windowItemCache.remove(s);
            }
        }
    }

    public int getItemId(short s, short s2) {
        Map<Short, Integer> map = this.windowItemCache.get(s);
        if (map == null) {
            return 1;
        }
        return map.getOrDefault(s2, 0);
    }

    public void setItemId(short s, short s2, int n) {
        if (s == -1 && s2 == -1) {
            this.itemIdInCursor = n;
        } else {
            this.windowItemCache.computeIfAbsent(s, InventoryTracker::lambda$setItemId$0).put(s2, n);
        }
    }

    public void handleWindowClick(UserConnection userConnection, short s, byte by, short s2, byte by2) {
        EntityTracker1_9 entityTracker1_9 = (EntityTracker1_9)userConnection.getEntityTracker(Protocol1_9To1_8.class);
        if (s2 == -1) {
            return;
        }
        if (s2 == 45) {
            entityTracker1_9.setSecondHand(null);
            return;
        }
        boolean bl = s2 >= 5 && s2 <= 8 || s2 == 0;
        switch (by) {
            case 0: {
                if (this.itemIdInCursor == 0) {
                    this.itemIdInCursor = this.getItemId(s, s2);
                    this.setItemId(s, s2, 0);
                    break;
                }
                if (s2 == -999) {
                    this.itemIdInCursor = 0;
                    break;
                }
                if (bl) break;
                int n = this.getItemId(s, s2);
                this.setItemId(s, s2, this.itemIdInCursor);
                this.itemIdInCursor = n;
                break;
            }
            case 2: {
                if (bl) break;
                short s3 = (short)(by2 + 36);
                int n = this.getItemId(s, s2);
                int n2 = this.getItemId(s, s3);
                this.setItemId(s, s3, n);
                this.setItemId(s, s2, n2);
                break;
            }
            case 4: {
                int n = this.getItemId(s, s2);
                if (n == 0) break;
                this.setItemId(s, s2, 0);
                break;
            }
            case 5: {
                switch (by2) {
                    case 0: 
                    case 4: {
                        this.dragging = true;
                        break;
                    }
                    case 1: 
                    case 5: {
                        if (!this.dragging || this.itemIdInCursor == 0 || bl) break;
                        int n = this.getItemId(s, s2);
                        this.setItemId(s, s2, this.itemIdInCursor);
                        this.itemIdInCursor = n;
                        break;
                    }
                    case 2: 
                    case 6: {
                        this.dragging = false;
                    }
                }
                break;
            }
        }
        entityTracker1_9.syncShieldWithSword();
    }

    private static Map lambda$setItemId$0(Short s) {
        return new HashMap();
    }
}

