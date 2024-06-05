package net.minecraft.src;

class EnumOptionsHelper
{
    static final int[] enumOptionsMappingHelperArray;
    
    static {
        enumOptionsMappingHelperArray = new int[EnumOptions.values().length];
        try {
            EnumOptionsHelper.enumOptionsMappingHelperArray[EnumOptions.INVERT_MOUSE.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            EnumOptionsHelper.enumOptionsMappingHelperArray[EnumOptions.VIEW_BOBBING.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError2) {}
        try {
            EnumOptionsHelper.enumOptionsMappingHelperArray[EnumOptions.ANAGLYPH.ordinal()] = 3;
        }
        catch (NoSuchFieldError noSuchFieldError3) {}
        try {
            EnumOptionsHelper.enumOptionsMappingHelperArray[EnumOptions.ADVANCED_OPENGL.ordinal()] = 4;
        }
        catch (NoSuchFieldError noSuchFieldError4) {}
        try {
            EnumOptionsHelper.enumOptionsMappingHelperArray[EnumOptions.RENDER_CLOUDS.ordinal()] = 5;
        }
        catch (NoSuchFieldError noSuchFieldError5) {}
        try {
            EnumOptionsHelper.enumOptionsMappingHelperArray[EnumOptions.CHAT_COLOR.ordinal()] = 6;
        }
        catch (NoSuchFieldError noSuchFieldError6) {}
        try {
            EnumOptionsHelper.enumOptionsMappingHelperArray[EnumOptions.CHAT_LINKS.ordinal()] = 7;
        }
        catch (NoSuchFieldError noSuchFieldError7) {}
        try {
            EnumOptionsHelper.enumOptionsMappingHelperArray[EnumOptions.CHAT_LINKS_PROMPT.ordinal()] = 8;
        }
        catch (NoSuchFieldError noSuchFieldError8) {}
        try {
            EnumOptionsHelper.enumOptionsMappingHelperArray[EnumOptions.USE_SERVER_TEXTURES.ordinal()] = 9;
        }
        catch (NoSuchFieldError noSuchFieldError9) {}
        try {
            EnumOptionsHelper.enumOptionsMappingHelperArray[EnumOptions.SNOOPER_ENABLED.ordinal()] = 10;
        }
        catch (NoSuchFieldError noSuchFieldError10) {}
        try {
            EnumOptionsHelper.enumOptionsMappingHelperArray[EnumOptions.USE_FULLSCREEN.ordinal()] = 11;
        }
        catch (NoSuchFieldError noSuchFieldError11) {}
        try {
            EnumOptionsHelper.enumOptionsMappingHelperArray[EnumOptions.ENABLE_VSYNC.ordinal()] = 12;
        }
        catch (NoSuchFieldError noSuchFieldError12) {}
        try {
            EnumOptionsHelper.enumOptionsMappingHelperArray[EnumOptions.SHOW_CAPE.ordinal()] = 13;
        }
        catch (NoSuchFieldError noSuchFieldError13) {}
        try {
            EnumOptionsHelper.enumOptionsMappingHelperArray[EnumOptions.TOUCHSCREEN.ordinal()] = 14;
        }
        catch (NoSuchFieldError noSuchFieldError14) {}
    }
}
