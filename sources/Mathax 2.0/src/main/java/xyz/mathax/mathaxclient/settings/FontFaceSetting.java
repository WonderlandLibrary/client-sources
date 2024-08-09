package xyz.mathax.mathaxclient.settings;

import xyz.mathax.mathaxclient.renderer.text.FontFace;
import xyz.mathax.mathaxclient.renderer.text.FontFamily;
import xyz.mathax.mathaxclient.renderer.text.FontInfo;
import xyz.mathax.mathaxclient.renderer.text.Fonts;
import xyz.mathax.mathaxclient.utils.settings.IVisible;
import org.json.JSONObject;

import java.util.List;
import java.util.function.Consumer;

public class FontFaceSetting extends Setting<FontFace> {
    public FontFaceSetting(String name, String description, FontFace defaultValue, Consumer<FontFace> onChanged, Consumer<Setting<FontFace>> onModuleActivated, IVisible visible) {
        super(name, description, defaultValue, onChanged, onModuleActivated, visible);
    }

    @Override
    protected FontFace parseImpl(String str) {
        String[] split = str.replace(" ", "").split("-");
        if (split.length != 2) {
            return null;
        }

        for (FontFamily family : Fonts.FONT_FAMILIES) {
            if (family.getName().replace(" ", "").equals(split[0])) {
                try {
                    return family.get(FontInfo.Type.valueOf(split[1]));
                } catch (IllegalArgumentException ignored) {
                    return null;
                }
            }
        }

        return null;
    }

    @Override
    public List<String> getSuggestions() {
        return List.of("Comfortaa-Regular", "Arial-Bold");
    }

    @Override
    protected boolean isValueValid(FontFace value) {
        if (value == null) {
            return false;
        }

        for (FontFamily fontFamily : Fonts.FONT_FAMILIES) {
            if (fontFamily.hasType(value.info.type())) {
                return true;
            }
        }

        return false;
    }

    @Override
    protected JSONObject save(JSONObject json) {
        json.put("family", get().info.family());
        json.put("type", get().info.type().toString());
        return json;
    }

    @Override
    protected FontFace load(JSONObject json) {
        String family = json.getString("family");
        FontInfo.Type type;

        try {
            type = FontInfo.Type.valueOf(json.getString("type"));
        } catch (IllegalArgumentException ignored) {
            set(Fonts.DEFAULT_FONT);
            return get();
        }

        boolean changed = false;
        for (FontFamily fontFamily : Fonts.FONT_FAMILIES) {
            if (fontFamily.getName().equals(family)) {
                set(fontFamily.get(type));
                changed = true;
            }
        }

        if (!changed) {
            set(Fonts.DEFAULT_FONT);
        }

        return get();
    }

    public static class Builder extends SettingBuilder<Builder, FontFace, FontFaceSetting> {
        public Builder() {
            super(Fonts.DEFAULT_FONT);
        }

        @Override
        public FontFaceSetting build() {
            return new FontFaceSetting(name, description, defaultValue, onChanged, onModuleEnabled, visible);
        }
    }
}