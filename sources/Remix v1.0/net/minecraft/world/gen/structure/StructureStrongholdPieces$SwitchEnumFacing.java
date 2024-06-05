package net.minecraft.world.gen.structure;

import net.minecraft.util.*;

static final class SwitchEnumFacing
{
    static final int[] doorEnum;
    static final int[] field_175951_b;
    
    static {
        field_175951_b = new int[EnumFacing.values().length];
        try {
            SwitchEnumFacing.field_175951_b[EnumFacing.NORTH.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            SwitchEnumFacing.field_175951_b[EnumFacing.SOUTH.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError2) {}
        try {
            SwitchEnumFacing.field_175951_b[EnumFacing.WEST.ordinal()] = 3;
        }
        catch (NoSuchFieldError noSuchFieldError3) {}
        try {
            SwitchEnumFacing.field_175951_b[EnumFacing.EAST.ordinal()] = 4;
        }
        catch (NoSuchFieldError noSuchFieldError4) {}
        doorEnum = new int[Stronghold.Door.values().length];
        try {
            SwitchEnumFacing.doorEnum[Stronghold.Door.OPENING.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError5) {}
        try {
            SwitchEnumFacing.doorEnum[Stronghold.Door.WOOD_DOOR.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError6) {}
        try {
            SwitchEnumFacing.doorEnum[Stronghold.Door.GRATES.ordinal()] = 3;
        }
        catch (NoSuchFieldError noSuchFieldError7) {}
        try {
            SwitchEnumFacing.doorEnum[Stronghold.Door.IRON_DOOR.ordinal()] = 4;
        }
        catch (NoSuchFieldError noSuchFieldError8) {}
    }
}
