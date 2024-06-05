package net.minecraft.client.stream;

import tv.twitch.chat.*;

static final class SwitchEnumEmoticonMode
{
    static final int[] field_175976_a;
    static final int[] field_175974_b;
    static final int[] field_175975_c;
    
    static {
        field_175975_c = new int[EnumEmoticonMode.values().length];
        try {
            SwitchEnumEmoticonMode.field_175975_c[EnumEmoticonMode.None.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            SwitchEnumEmoticonMode.field_175975_c[EnumEmoticonMode.Url.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError2) {}
        try {
            SwitchEnumEmoticonMode.field_175975_c[EnumEmoticonMode.TextureAtlas.ordinal()] = 3;
        }
        catch (NoSuchFieldError noSuchFieldError3) {}
        field_175974_b = new int[ChatEvent.values().length];
        try {
            SwitchEnumEmoticonMode.field_175974_b[ChatEvent.TTV_CHAT_JOINED_CHANNEL.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError4) {}
        try {
            SwitchEnumEmoticonMode.field_175974_b[ChatEvent.TTV_CHAT_LEFT_CHANNEL.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError5) {}
        field_175976_a = new int[EnumChannelState.values().length];
        try {
            SwitchEnumEmoticonMode.field_175976_a[EnumChannelState.Connected.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError6) {}
        try {
            SwitchEnumEmoticonMode.field_175976_a[EnumChannelState.Connecting.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError7) {}
        try {
            SwitchEnumEmoticonMode.field_175976_a[EnumChannelState.Created.ordinal()] = 3;
        }
        catch (NoSuchFieldError noSuchFieldError8) {}
        try {
            SwitchEnumEmoticonMode.field_175976_a[EnumChannelState.Disconnected.ordinal()] = 4;
        }
        catch (NoSuchFieldError noSuchFieldError9) {}
        try {
            SwitchEnumEmoticonMode.field_175976_a[EnumChannelState.Disconnecting.ordinal()] = 5;
        }
        catch (NoSuchFieldError noSuchFieldError10) {}
    }
}
