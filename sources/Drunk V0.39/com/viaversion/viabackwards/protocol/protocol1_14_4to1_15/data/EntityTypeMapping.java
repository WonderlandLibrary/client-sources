/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viabackwards.protocol.protocol1_14_4to1_15.data;

import com.viaversion.viaversion.api.minecraft.entities.Entity1_14Types;

public class EntityTypeMapping {
    public static int getOldEntityId(int entityId) {
        int n;
        if (entityId == 4) {
            return Entity1_14Types.PUFFERFISH.getId();
        }
        if (entityId >= 5) {
            n = entityId - 1;
            return n;
        }
        n = entityId;
        return n;
    }
}

