/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.merchant.villager;

import com.mojang.datafixers.kinds.App;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraft.entity.villager.VillagerType;
import net.minecraft.util.registry.Registry;

public class VillagerData {
    private static final int[] LEVEL_EXPERIENCE_AMOUNTS = new int[]{0, 10, 70, 150, 250};
    public static final Codec<VillagerData> CODEC = RecordCodecBuilder.create(VillagerData::lambda$static$5);
    private final VillagerType type;
    private final VillagerProfession profession;
    private final int level;

    public VillagerData(VillagerType villagerType, VillagerProfession villagerProfession, int n) {
        this.type = villagerType;
        this.profession = villagerProfession;
        this.level = Math.max(1, n);
    }

    public VillagerType getType() {
        return this.type;
    }

    public VillagerProfession getProfession() {
        return this.profession;
    }

    public int getLevel() {
        return this.level;
    }

    public VillagerData withType(VillagerType villagerType) {
        return new VillagerData(villagerType, this.profession, this.level);
    }

    public VillagerData withProfession(VillagerProfession villagerProfession) {
        return new VillagerData(this.type, villagerProfession, this.level);
    }

    public VillagerData withLevel(int n) {
        return new VillagerData(this.type, this.profession, n);
    }

    public static int getExperiencePrevious(int n) {
        return VillagerData.canLevelUp(n) ? LEVEL_EXPERIENCE_AMOUNTS[n - 1] : 0;
    }

    public static int getExperienceNext(int n) {
        return VillagerData.canLevelUp(n) ? LEVEL_EXPERIENCE_AMOUNTS[n] : 0;
    }

    public static boolean canLevelUp(int n) {
        return n >= 1 && n < 5;
    }

    private static App lambda$static$5(RecordCodecBuilder.Instance instance) {
        return instance.group(((MapCodec)Registry.VILLAGER_TYPE.fieldOf("type")).orElseGet(VillagerData::lambda$static$0).forGetter(VillagerData::lambda$static$1), ((MapCodec)Registry.VILLAGER_PROFESSION.fieldOf("profession")).orElseGet(VillagerData::lambda$static$2).forGetter(VillagerData::lambda$static$3), ((MapCodec)Codec.INT.fieldOf("level")).orElse(1).forGetter(VillagerData::lambda$static$4)).apply(instance, VillagerData::new);
    }

    private static Integer lambda$static$4(VillagerData villagerData) {
        return villagerData.level;
    }

    private static VillagerProfession lambda$static$3(VillagerData villagerData) {
        return villagerData.profession;
    }

    private static VillagerProfession lambda$static$2() {
        return VillagerProfession.NONE;
    }

    private static VillagerType lambda$static$1(VillagerData villagerData) {
        return villagerData.type;
    }

    private static VillagerType lambda$static$0() {
        return VillagerType.PLAINS;
    }
}

