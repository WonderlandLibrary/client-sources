package net.minecraft.client.stream;

static final class SwitchBroadcastState
{
    static final int[] field_177773_a;
    
    static {
        field_177773_a = new int[BroadcastState.values().length];
        try {
            SwitchBroadcastState.field_177773_a[BroadcastState.Authenticated.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            SwitchBroadcastState.field_177773_a[BroadcastState.LoggedIn.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError2) {}
        try {
            SwitchBroadcastState.field_177773_a[BroadcastState.ReceivedIngestServers.ordinal()] = 3;
        }
        catch (NoSuchFieldError noSuchFieldError3) {}
        try {
            SwitchBroadcastState.field_177773_a[BroadcastState.Starting.ordinal()] = 4;
        }
        catch (NoSuchFieldError noSuchFieldError4) {}
        try {
            SwitchBroadcastState.field_177773_a[BroadcastState.Stopping.ordinal()] = 5;
        }
        catch (NoSuchFieldError noSuchFieldError5) {}
        try {
            SwitchBroadcastState.field_177773_a[BroadcastState.FindingIngestServer.ordinal()] = 6;
        }
        catch (NoSuchFieldError noSuchFieldError6) {}
        try {
            SwitchBroadcastState.field_177773_a[BroadcastState.Authenticating.ordinal()] = 7;
        }
        catch (NoSuchFieldError noSuchFieldError7) {}
        try {
            SwitchBroadcastState.field_177773_a[BroadcastState.Initialized.ordinal()] = 8;
        }
        catch (NoSuchFieldError noSuchFieldError8) {}
        try {
            SwitchBroadcastState.field_177773_a[BroadcastState.Uninitialized.ordinal()] = 9;
        }
        catch (NoSuchFieldError noSuchFieldError9) {}
        try {
            SwitchBroadcastState.field_177773_a[BroadcastState.IngestTesting.ordinal()] = 10;
        }
        catch (NoSuchFieldError noSuchFieldError10) {}
        try {
            SwitchBroadcastState.field_177773_a[BroadcastState.Paused.ordinal()] = 11;
        }
        catch (NoSuchFieldError noSuchFieldError11) {}
        try {
            SwitchBroadcastState.field_177773_a[BroadcastState.Broadcasting.ordinal()] = 12;
        }
        catch (NoSuchFieldError noSuchFieldError12) {}
    }
}
