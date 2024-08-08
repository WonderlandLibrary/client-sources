// 
// Decompiled by Procyon v0.5.36
// 

package me.perry.mcdonalds.features.setting;

import com.google.gson.JsonPrimitive;
import com.google.gson.JsonElement;
import com.google.common.base.Converter;

public class EnumConverter extends Converter<Enum, JsonElement>
{
    private final Class<? extends Enum> clazz;
    
    public EnumConverter(final Class<? extends Enum> clazz) {
        this.clazz = clazz;
    }
    
    public static int currentEnum(final Enum clazz) {
        for (int i = 0; i < ((Enum[])clazz.getClass().getEnumConstants()).length; ++i) {
            final Enum e = ((Enum[])clazz.getClass().getEnumConstants())[i];
            if (e.name().equalsIgnoreCase(clazz.name())) {
                return i;
            }
        }
        return -1;
    }
    
    public static Enum increaseEnum(final Enum clazz) {
        final int index = currentEnum(clazz);
        for (int i = 0; i < ((Enum[])clazz.getClass().getEnumConstants()).length; ++i) {
            final Enum e = ((Enum[])clazz.getClass().getEnumConstants())[i];
            if (i == index + 1) {
                return e;
            }
        }
        return ((Enum[])clazz.getClass().getEnumConstants())[0];
    }
    
    public static String getProperName(final Enum clazz) {
        return Character.toUpperCase(clazz.name().charAt(0)) + clazz.name().toLowerCase().substring(1);
    }
    
    public JsonElement doForward(final Enum anEnum) {
        return (JsonElement)new JsonPrimitive(anEnum.toString());
    }
    
    public Enum doBackward(final JsonElement jsonElement) {
        try {
            return (Enum)Enum.valueOf(this.clazz, jsonElement.getAsString());
        }
        catch (IllegalArgumentException e) {
            return null;
        }
    }
}
