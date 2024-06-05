package net.minecraft.util;

static final class SwitchPlane
{
    static final int[] AXIS_LOOKUP;
    static final int[] FACING_LOOKUP;
    static final int[] PLANE_LOOKUP;
    
    static {
        PLANE_LOOKUP = new int[Plane.values().length];
        try {
            SwitchPlane.PLANE_LOOKUP[Plane.HORIZONTAL.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            SwitchPlane.PLANE_LOOKUP[Plane.VERTICAL.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError2) {}
        FACING_LOOKUP = new int[EnumFacing.values().length];
        try {
            SwitchPlane.FACING_LOOKUP[EnumFacing.NORTH.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError3) {}
        try {
            SwitchPlane.FACING_LOOKUP[EnumFacing.EAST.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError4) {}
        try {
            SwitchPlane.FACING_LOOKUP[EnumFacing.SOUTH.ordinal()] = 3;
        }
        catch (NoSuchFieldError noSuchFieldError5) {}
        try {
            SwitchPlane.FACING_LOOKUP[EnumFacing.WEST.ordinal()] = 4;
        }
        catch (NoSuchFieldError noSuchFieldError6) {}
        try {
            SwitchPlane.FACING_LOOKUP[EnumFacing.UP.ordinal()] = 5;
        }
        catch (NoSuchFieldError noSuchFieldError7) {}
        try {
            SwitchPlane.FACING_LOOKUP[EnumFacing.DOWN.ordinal()] = 6;
        }
        catch (NoSuchFieldError noSuchFieldError8) {}
        AXIS_LOOKUP = new int[Axis.values().length];
        try {
            SwitchPlane.AXIS_LOOKUP[Axis.X.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError9) {}
        try {
            SwitchPlane.AXIS_LOOKUP[Axis.Y.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError10) {}
        try {
            SwitchPlane.AXIS_LOOKUP[Axis.Z.ordinal()] = 3;
        }
        catch (NoSuchFieldError noSuchFieldError11) {}
    }
}
