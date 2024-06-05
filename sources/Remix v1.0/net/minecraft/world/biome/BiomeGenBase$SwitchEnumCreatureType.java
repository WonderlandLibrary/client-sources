package net.minecraft.world.biome;

import net.minecraft.entity.*;

static final class SwitchEnumCreatureType
{
    static final int[] field_180275_a;
    
    static {
        field_180275_a = new int[EnumCreatureType.values().length];
        try {
            SwitchEnumCreatureType.field_180275_a[EnumCreatureType.MONSTER.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            SwitchEnumCreatureType.field_180275_a[EnumCreatureType.CREATURE.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError2) {}
        try {
            SwitchEnumCreatureType.field_180275_a[EnumCreatureType.WATER_CREATURE.ordinal()] = 3;
        }
        catch (NoSuchFieldError noSuchFieldError3) {}
        try {
            SwitchEnumCreatureType.field_180275_a[EnumCreatureType.AMBIENT.ordinal()] = 4;
        }
        catch (NoSuchFieldError noSuchFieldError4) {}
    }
}
