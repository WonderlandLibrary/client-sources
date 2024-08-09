/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity;

import com.mojang.serialization.Codec;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
import net.minecraft.util.IStringSerializable;

public enum EntityClassification implements IStringSerializable
{
    MONSTER("monster", 70, false, false, 128),
    CREATURE("creature", 10, true, true, 128),
    AMBIENT("ambient", 15, true, false, 128),
    WATER_CREATURE("water_creature", 5, true, false, 128),
    WATER_AMBIENT("water_ambient", 20, true, false, 64),
    MISC("misc", -1, true, true, 128);

    public static final Codec<EntityClassification> CODEC;
    private static final Map<String, EntityClassification> VALUES_MAP;
    private final int maxNumberOfCreature;
    private final boolean isPeacefulCreature;
    private final boolean isAnimal;
    private final String name;
    private final int randomDespawnDistance = 32;
    private final int instantDespawnDistance;

    private EntityClassification(String string2, int n2, boolean bl, boolean bl2, int n3) {
        this.name = string2;
        this.maxNumberOfCreature = n2;
        this.isPeacefulCreature = bl;
        this.isAnimal = bl2;
        this.instantDespawnDistance = n3;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public String getString() {
        return this.name;
    }

    public static EntityClassification getClassificationByName(String string) {
        return VALUES_MAP.get(string);
    }

    public int getMaxNumberOfCreature() {
        return this.maxNumberOfCreature;
    }

    public boolean getPeacefulCreature() {
        return this.isPeacefulCreature;
    }

    public boolean getAnimal() {
        return this.isAnimal;
    }

    public int getInstantDespawnDistance() {
        return this.instantDespawnDistance;
    }

    public int getRandomDespawnDistance() {
        return 1;
    }

    private static EntityClassification lambda$static$0(EntityClassification entityClassification) {
        return entityClassification;
    }

    static {
        CODEC = IStringSerializable.createEnumCodec(EntityClassification::values, EntityClassification::getClassificationByName);
        VALUES_MAP = Arrays.stream(EntityClassification.values()).collect(Collectors.toMap(EntityClassification::getName, EntityClassification::lambda$static$0));
    }
}

