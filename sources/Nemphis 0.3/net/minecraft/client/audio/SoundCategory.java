/*
 * Decompiled with CFR 0_118.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Maps
 */
package net.minecraft.client.audio;

import com.google.common.collect.Maps;
import java.util.Map;

public enum SoundCategory {
    MASTER("MASTER", 0, "master", 0),
    MUSIC("MUSIC", 1, "music", 1),
    RECORDS("RECORDS", 2, "record", 2),
    WEATHER("WEATHER", 3, "weather", 3),
    BLOCKS("BLOCKS", 4, "block", 4),
    MOBS("MOBS", 5, "hostile", 5),
    ANIMALS("ANIMALS", 6, "neutral", 6),
    PLAYERS("PLAYERS", 7, "player", 7),
    AMBIENT("AMBIENT", 8, "ambient", 8);
    
    private static final Map field_147168_j;
    private static final Map field_147169_k;
    private final String categoryName;
    private final int categoryId;
    private static final SoundCategory[] $VALUES;
    private static final String __OBFID = "CL_00001686";

    static {
        field_147168_j = Maps.newHashMap();
        field_147169_k = Maps.newHashMap();
        $VALUES = new SoundCategory[]{MASTER, MUSIC, RECORDS, WEATHER, BLOCKS, MOBS, ANIMALS, PLAYERS, AMBIENT};
        SoundCategory[] var0 = SoundCategory.values();
        int var1 = var0.length;
        int var2 = 0;
        while (var2 < var1) {
            SoundCategory var3 = var0[var2];
            if (field_147168_j.containsKey(var3.getCategoryName()) || field_147169_k.containsKey(var3.getCategoryId())) {
                throw new Error("Clash in Sound Category ID & Name pools! Cannot insert " + (Object)((Object)var3));
            }
            field_147168_j.put(var3.getCategoryName(), var3);
            field_147169_k.put(var3.getCategoryId(), var3);
            ++var2;
        }
    }

    private SoundCategory(String p_i45126_1_, int p_i45126_2_, String p_i45126_3_, int p_i45126_4_, String string2, int n2) {
        this.categoryName = p_i45126_3_;
        this.categoryId = p_i45126_4_;
    }

    public String getCategoryName() {
        return this.categoryName;
    }

    public int getCategoryId() {
        return this.categoryId;
    }

    public static SoundCategory func_147154_a(String p_147154_0_) {
        return (SoundCategory)((Object)field_147168_j.get(p_147154_0_));
    }
}

