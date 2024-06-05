package net.minecraft.client.gui.stream;

import net.minecraft.client.stream.*;
import net.minecraft.util.*;

static final class SwitchReason
{
    static final int[] field_152577_a;
    static final int[] field_152578_b;
    static final int[] field_152579_c;
    
    static {
        field_152579_c = new int[IStream.AuthFailureReason.values().length];
        try {
            SwitchReason.field_152579_c[IStream.AuthFailureReason.INVALID_TOKEN.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            SwitchReason.field_152579_c[IStream.AuthFailureReason.ERROR.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError2) {}
        field_152578_b = new int[Util.EnumOS.values().length];
        try {
            SwitchReason.field_152578_b[Util.EnumOS.WINDOWS.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError3) {}
        try {
            SwitchReason.field_152578_b[Util.EnumOS.OSX.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError4) {}
        field_152577_a = new int[Reason.values().length];
        try {
            SwitchReason.field_152577_a[Reason.ACCOUNT_NOT_BOUND.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError5) {}
        try {
            SwitchReason.field_152577_a[Reason.FAILED_TWITCH_AUTH.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError6) {}
        try {
            SwitchReason.field_152577_a[Reason.ACCOUNT_NOT_MIGRATED.ordinal()] = 3;
        }
        catch (NoSuchFieldError noSuchFieldError7) {}
        try {
            SwitchReason.field_152577_a[Reason.UNSUPPORTED_OS_MAC.ordinal()] = 4;
        }
        catch (NoSuchFieldError noSuchFieldError8) {}
        try {
            SwitchReason.field_152577_a[Reason.UNKNOWN.ordinal()] = 5;
        }
        catch (NoSuchFieldError noSuchFieldError9) {}
        try {
            SwitchReason.field_152577_a[Reason.LIBRARY_FAILURE.ordinal()] = 6;
        }
        catch (NoSuchFieldError noSuchFieldError10) {}
        try {
            SwitchReason.field_152577_a[Reason.INITIALIZATION_FAILURE.ordinal()] = 7;
        }
        catch (NoSuchFieldError noSuchFieldError11) {}
    }
}
