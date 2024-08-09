/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viabackwards.protocol.protocol1_14_4to1_15.data;

import com.viaversion.viaversion.api.minecraft.entities.Entity1_14Types;

public class EntityTypeMapping {
    public static int getOldEntityId(int n) {
        if (n == 4) {
            return Entity1_14Types.PUFFERFISH.getId();
        }
        return n >= 5 ? n - 1 : n;
    }
}

