/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util;

import net.minecraft.entity.MobEntity;
import net.minecraft.pathfinding.GroundPathNavigator;

public class GroundPathHelper {
    public static boolean isGroundNavigator(MobEntity mobEntity) {
        return mobEntity.getNavigator() instanceof GroundPathNavigator;
    }
}

