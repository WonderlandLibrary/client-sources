package net.minecraft.entity.item;

import net.minecraft.block.*;

static final class SwitchEnumMinecartType
{
    static final int[] field_180037_a;
    static final int[] field_180036_b;
    
    static {
        field_180036_b = new int[BlockRailBase.EnumRailDirection.values().length];
        try {
            SwitchEnumMinecartType.field_180036_b[BlockRailBase.EnumRailDirection.ASCENDING_EAST.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            SwitchEnumMinecartType.field_180036_b[BlockRailBase.EnumRailDirection.ASCENDING_WEST.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError2) {}
        try {
            SwitchEnumMinecartType.field_180036_b[BlockRailBase.EnumRailDirection.ASCENDING_NORTH.ordinal()] = 3;
        }
        catch (NoSuchFieldError noSuchFieldError3) {}
        try {
            SwitchEnumMinecartType.field_180036_b[BlockRailBase.EnumRailDirection.ASCENDING_SOUTH.ordinal()] = 4;
        }
        catch (NoSuchFieldError noSuchFieldError4) {}
        field_180037_a = new int[EnumMinecartType.values().length];
        try {
            SwitchEnumMinecartType.field_180037_a[EnumMinecartType.CHEST.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError5) {}
        try {
            SwitchEnumMinecartType.field_180037_a[EnumMinecartType.FURNACE.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError6) {}
        try {
            SwitchEnumMinecartType.field_180037_a[EnumMinecartType.TNT.ordinal()] = 3;
        }
        catch (NoSuchFieldError noSuchFieldError7) {}
        try {
            SwitchEnumMinecartType.field_180037_a[EnumMinecartType.SPAWNER.ordinal()] = 4;
        }
        catch (NoSuchFieldError noSuchFieldError8) {}
        try {
            SwitchEnumMinecartType.field_180037_a[EnumMinecartType.HOPPER.ordinal()] = 5;
        }
        catch (NoSuchFieldError noSuchFieldError9) {}
        try {
            SwitchEnumMinecartType.field_180037_a[EnumMinecartType.COMMAND_BLOCK.ordinal()] = 6;
        }
        catch (NoSuchFieldError noSuchFieldError10) {}
    }
}
