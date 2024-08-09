/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.util;

import net.minecraft.util.RegistryKey;
import net.minecraft.world.World;

public class WorldUtils {
    public static int getDimensionId(World world) {
        return world == null ? 0 : WorldUtils.getDimensionId(world.getDimensionKey());
    }

    public static int getDimensionId(RegistryKey<World> registryKey) {
        if (registryKey == World.THE_NETHER) {
            return 1;
        }
        if (registryKey == World.OVERWORLD) {
            return 1;
        }
        return registryKey == World.THE_END ? 1 : 0;
    }

    public static boolean isNether(World world) {
        return world.getDimensionKey() == World.THE_NETHER;
    }

    public static boolean isOverworld(World world) {
        RegistryKey<World> registryKey = world.getDimensionKey();
        return WorldUtils.getDimensionId(registryKey) == 0;
    }

    public static boolean isEnd(World world) {
        return world.getDimensionKey() == World.THE_END;
    }
}

