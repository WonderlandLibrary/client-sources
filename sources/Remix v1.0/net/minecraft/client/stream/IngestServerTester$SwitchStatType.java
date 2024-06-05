package net.minecraft.client.stream;

import tv.twitch.broadcast.*;

static final class SwitchStatType
{
    static final int[] field_176003_a;
    static final int[] field_176002_b;
    
    static {
        field_176002_b = new int[IngestTestState.values().length];
        try {
            SwitchStatType.field_176002_b[IngestTestState.Starting.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            SwitchStatType.field_176002_b[IngestTestState.DoneTestingServer.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError2) {}
        try {
            SwitchStatType.field_176002_b[IngestTestState.ConnectingToServer.ordinal()] = 3;
        }
        catch (NoSuchFieldError noSuchFieldError3) {}
        try {
            SwitchStatType.field_176002_b[IngestTestState.TestingServer.ordinal()] = 4;
        }
        catch (NoSuchFieldError noSuchFieldError4) {}
        try {
            SwitchStatType.field_176002_b[IngestTestState.Cancelling.ordinal()] = 5;
        }
        catch (NoSuchFieldError noSuchFieldError5) {}
        try {
            SwitchStatType.field_176002_b[IngestTestState.Uninitalized.ordinal()] = 6;
        }
        catch (NoSuchFieldError noSuchFieldError6) {}
        try {
            SwitchStatType.field_176002_b[IngestTestState.Finished.ordinal()] = 7;
        }
        catch (NoSuchFieldError noSuchFieldError7) {}
        try {
            SwitchStatType.field_176002_b[IngestTestState.Cancelled.ordinal()] = 8;
        }
        catch (NoSuchFieldError noSuchFieldError8) {}
        try {
            SwitchStatType.field_176002_b[IngestTestState.Failed.ordinal()] = 9;
        }
        catch (NoSuchFieldError noSuchFieldError9) {}
        field_176003_a = new int[StatType.values().length];
        try {
            SwitchStatType.field_176003_a[StatType.TTV_ST_RTMPSTATE.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError10) {}
        try {
            SwitchStatType.field_176003_a[StatType.TTV_ST_RTMPDATASENT.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError11) {}
    }
}
