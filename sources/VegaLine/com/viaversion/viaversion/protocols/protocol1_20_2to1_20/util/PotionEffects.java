/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.protocols.protocol1_20_2to1_20.util;

import com.viaversion.viaversion.libs.fastutil.objects.Object2IntMap;
import com.viaversion.viaversion.libs.fastutil.objects.Object2IntOpenHashMap;
import com.viaversion.viaversion.util.Key;
import org.checkerframework.checker.nullness.qual.Nullable;

public final class PotionEffects {
    private static final Object2IntMap<String> KEY_TO_ID = new Object2IntOpenHashMap<String>();
    private static final String[] POTION_EFFECTS = new String[]{"", "speed", "slowness", "haste", "mining_fatigue", "strength", "instant_health", "instant_damage", "jump_boost", "nausea", "regeneration", "resistance", "fire_resistance", "water_breathing", "invisibility", "blindness", "night_vision", "hunger", "weakness", "poison", "wither", "health_boost", "absorption", "saturation", "glowing", "levitation", "luck", "unluck", "slow_falling", "conduit_power", "dolphins_grace", "bad_omen", "hero_of_the_village", "darkness"};

    public static @Nullable String idToKey(int id) {
        return id >= 1 && id < POTION_EFFECTS.length ? Key.namespaced(POTION_EFFECTS[id]) : null;
    }

    public static String idToKeyOrLuck(int id) {
        return id >= 1 && id < POTION_EFFECTS.length ? Key.namespaced(POTION_EFFECTS[id]) : "minecraft:luck";
    }

    public static int keyToId(String key) {
        return KEY_TO_ID.getInt(Key.stripMinecraftNamespace(key));
    }

    static {
        for (int i = 1; i < POTION_EFFECTS.length; ++i) {
            String effect = POTION_EFFECTS[i];
            KEY_TO_ID.put(effect, i);
        }
    }
}

