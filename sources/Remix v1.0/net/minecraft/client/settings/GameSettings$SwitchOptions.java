package net.minecraft.client.settings;

static final class SwitchOptions
{
    static final int[] optionIds;
    
    static {
        optionIds = new int[Options.values().length];
        try {
            SwitchOptions.optionIds[Options.INVERT_MOUSE.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            SwitchOptions.optionIds[Options.VIEW_BOBBING.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError2) {}
        try {
            SwitchOptions.optionIds[Options.ANAGLYPH.ordinal()] = 3;
        }
        catch (NoSuchFieldError noSuchFieldError3) {}
        try {
            SwitchOptions.optionIds[Options.FBO_ENABLE.ordinal()] = 4;
        }
        catch (NoSuchFieldError noSuchFieldError4) {}
        try {
            SwitchOptions.optionIds[Options.RENDER_CLOUDS.ordinal()] = 5;
        }
        catch (NoSuchFieldError noSuchFieldError5) {}
        try {
            SwitchOptions.optionIds[Options.CHAT_COLOR.ordinal()] = 6;
        }
        catch (NoSuchFieldError noSuchFieldError6) {}
        try {
            SwitchOptions.optionIds[Options.CHAT_LINKS.ordinal()] = 7;
        }
        catch (NoSuchFieldError noSuchFieldError7) {}
        try {
            SwitchOptions.optionIds[Options.CHAT_LINKS_PROMPT.ordinal()] = 8;
        }
        catch (NoSuchFieldError noSuchFieldError8) {}
        try {
            SwitchOptions.optionIds[Options.SNOOPER_ENABLED.ordinal()] = 9;
        }
        catch (NoSuchFieldError noSuchFieldError9) {}
        try {
            SwitchOptions.optionIds[Options.USE_FULLSCREEN.ordinal()] = 10;
        }
        catch (NoSuchFieldError noSuchFieldError10) {}
        try {
            SwitchOptions.optionIds[Options.ENABLE_VSYNC.ordinal()] = 11;
        }
        catch (NoSuchFieldError noSuchFieldError11) {}
        try {
            SwitchOptions.optionIds[Options.USE_VBO.ordinal()] = 12;
        }
        catch (NoSuchFieldError noSuchFieldError12) {}
        try {
            SwitchOptions.optionIds[Options.TOUCHSCREEN.ordinal()] = 13;
        }
        catch (NoSuchFieldError noSuchFieldError13) {}
        try {
            SwitchOptions.optionIds[Options.STREAM_SEND_METADATA.ordinal()] = 14;
        }
        catch (NoSuchFieldError noSuchFieldError14) {}
        try {
            SwitchOptions.optionIds[Options.FORCE_UNICODE_FONT.ordinal()] = 15;
        }
        catch (NoSuchFieldError noSuchFieldError15) {}
        try {
            SwitchOptions.optionIds[Options.BLOCK_ALTERNATIVES.ordinal()] = 16;
        }
        catch (NoSuchFieldError noSuchFieldError16) {}
        try {
            SwitchOptions.optionIds[Options.REDUCED_DEBUG_INFO.ordinal()] = 17;
        }
        catch (NoSuchFieldError noSuchFieldError17) {}
    }
}
