package net.minecraft.client;

import net.minecraft.entity.item.*;
import net.minecraft.util.*;

static final class SwitchEnumMinecartType
{
    static final int[] field_152390_a;
    static final int[] field_178901_b;
    
    static {
        field_178901_b = new int[EntityMinecart.EnumMinecartType.values().length];
        try {
            SwitchEnumMinecartType.field_178901_b[EntityMinecart.EnumMinecartType.FURNACE.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            SwitchEnumMinecartType.field_178901_b[EntityMinecart.EnumMinecartType.CHEST.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError2) {}
        try {
            SwitchEnumMinecartType.field_178901_b[EntityMinecart.EnumMinecartType.TNT.ordinal()] = 3;
        }
        catch (NoSuchFieldError noSuchFieldError3) {}
        try {
            SwitchEnumMinecartType.field_178901_b[EntityMinecart.EnumMinecartType.HOPPER.ordinal()] = 4;
        }
        catch (NoSuchFieldError noSuchFieldError4) {}
        try {
            SwitchEnumMinecartType.field_178901_b[EntityMinecart.EnumMinecartType.COMMAND_BLOCK.ordinal()] = 5;
        }
        catch (NoSuchFieldError noSuchFieldError5) {}
        field_152390_a = new int[MovingObjectPosition.MovingObjectType.values().length];
        try {
            SwitchEnumMinecartType.field_152390_a[MovingObjectPosition.MovingObjectType.ENTITY.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError6) {}
        try {
            SwitchEnumMinecartType.field_152390_a[MovingObjectPosition.MovingObjectType.BLOCK.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError7) {}
        try {
            SwitchEnumMinecartType.field_152390_a[MovingObjectPosition.MovingObjectType.MISS.ordinal()] = 3;
        }
        catch (NoSuchFieldError noSuchFieldError8) {}
    }
}
