package net.minecraft.src;

class EnumDoorHelper
{
    static final int[] doorEnum;
    
    static {
        doorEnum = new int[EnumDoor.values().length];
        try {
            EnumDoorHelper.doorEnum[EnumDoor.OPENING.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            EnumDoorHelper.doorEnum[EnumDoor.WOOD_DOOR.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError2) {}
        try {
            EnumDoorHelper.doorEnum[EnumDoor.GRATES.ordinal()] = 3;
        }
        catch (NoSuchFieldError noSuchFieldError3) {}
        try {
            EnumDoorHelper.doorEnum[EnumDoor.IRON_DOOR.ordinal()] = 4;
        }
        catch (NoSuchFieldError noSuchFieldError4) {}
    }
}
