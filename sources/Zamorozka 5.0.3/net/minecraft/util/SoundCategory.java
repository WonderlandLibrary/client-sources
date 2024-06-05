package net.minecraft.util;

import com.google.common.collect.Maps;

import java.util.Map;
import java.util.Set;

public enum SoundCategory {
    MASTER("master"),
    MUSIC("music"),
    RECORDS("record"),
    WEATHER("weather"),
    BLOCKS("block"),
    HOSTILE("hostile"),
    NEUTRAL("neutral"),
    PLAYERS("player"),
    AMBIENT("ambient"),
    VOICE("voice");

    private static final Map<String, SoundCategory> SOUND_CATEGORIES = Maps.newHashMap();

    static {
        for (SoundCategory soundcategory : values()) {
            if (SOUND_CATEGORIES.containsKey(soundcategory.getName())) {
                throw new Error("Clash in Sound Category name pools! Cannot insert " + soundcategory);
            }

            SOUND_CATEGORIES.put(soundcategory.getName(), soundcategory);
        }
    }

    private final String name;

    SoundCategory(String nameIn) {
        this.name = nameIn;
    }

    public static SoundCategory getByName(String categoryName) {
        return SOUND_CATEGORIES.get(categoryName);
    }

    public static Set<String> getSoundCategoryNames() {
        return SOUND_CATEGORIES.keySet();
    }

    public String getName() {
        return this.name;
    }
}
