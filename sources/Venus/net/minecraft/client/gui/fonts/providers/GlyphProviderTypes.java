/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.gui.fonts.providers;

import com.google.common.collect.Maps;
import com.google.gson.JsonObject;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import net.minecraft.client.gui.fonts.providers.IGlyphProviderFactory;
import net.minecraft.client.gui.fonts.providers.TextureGlyphProvider;
import net.minecraft.client.gui.fonts.providers.TrueTypeGlyphProviderFactory;
import net.minecraft.client.gui.fonts.providers.UnicodeTextureGlyphProvider;
import net.minecraft.util.Util;

public enum GlyphProviderTypes {
    BITMAP("bitmap", TextureGlyphProvider.Factory::deserialize),
    TTF("ttf", TrueTypeGlyphProviderFactory::deserialize),
    LEGACY_UNICODE("legacy_unicode", UnicodeTextureGlyphProvider.Factory::deserialize);

    private static final Map<String, GlyphProviderTypes> TYPES_BY_NAME;
    private final String name;
    private final Function<JsonObject, IGlyphProviderFactory> factoryDeserializer;

    private GlyphProviderTypes(String string2, Function<JsonObject, IGlyphProviderFactory> function) {
        this.name = string2;
        this.factoryDeserializer = function;
    }

    public static GlyphProviderTypes byName(String string) {
        GlyphProviderTypes glyphProviderTypes = TYPES_BY_NAME.get(string);
        if (glyphProviderTypes == null) {
            throw new IllegalArgumentException("Invalid type: " + string);
        }
        return glyphProviderTypes;
    }

    public IGlyphProviderFactory getFactory(JsonObject jsonObject) {
        return this.factoryDeserializer.apply(jsonObject);
    }

    private static void lambda$static$0(HashMap hashMap) {
        for (GlyphProviderTypes glyphProviderTypes : GlyphProviderTypes.values()) {
            hashMap.put(glyphProviderTypes.name, glyphProviderTypes);
        }
    }

    static {
        TYPES_BY_NAME = Util.make(Maps.newHashMap(), GlyphProviderTypes::lambda$static$0);
    }
}

