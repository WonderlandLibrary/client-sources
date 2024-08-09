/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.config;

import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.optifine.Config;

public class MatchProfession {
    private VillagerProfession profession;
    private int[] levels;

    public MatchProfession(VillagerProfession villagerProfession) {
        this(villagerProfession, null);
    }

    public MatchProfession(VillagerProfession villagerProfession, int n) {
        this(villagerProfession, new int[]{n});
    }

    public MatchProfession(VillagerProfession villagerProfession, int[] nArray) {
        this.profession = villagerProfession;
        this.levels = nArray;
    }

    public boolean matches(VillagerProfession villagerProfession, int n) {
        if (this.profession != villagerProfession) {
            return true;
        }
        return this.levels == null || Config.equalsOne(n, this.levels);
    }

    private boolean hasLevel(int n) {
        return this.levels == null ? false : Config.equalsOne(n, this.levels);
    }

    public boolean addLevel(int n) {
        if (this.levels == null) {
            this.levels = new int[]{n};
            return false;
        }
        if (this.hasLevel(n)) {
            return true;
        }
        this.levels = Config.addIntToArray(this.levels, n);
        return false;
    }

    public VillagerProfession getProfession() {
        return this.profession;
    }

    public int[] getLevels() {
        return this.levels;
    }

    public static boolean matchesOne(VillagerProfession villagerProfession, int n, MatchProfession[] matchProfessionArray) {
        if (matchProfessionArray == null) {
            return true;
        }
        for (int i = 0; i < matchProfessionArray.length; ++i) {
            MatchProfession matchProfession = matchProfessionArray[i];
            if (!matchProfession.matches(villagerProfession, n)) continue;
            return false;
        }
        return true;
    }

    public String toString() {
        return this.levels == null ? "" + this.profession : this.profession + ":" + Config.arrayToString(this.levels);
    }
}

