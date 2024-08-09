package dev.excellent.impl.font;

import it.unimi.dsi.fastutil.floats.Float2ObjectArrayMap;
import it.unimi.dsi.fastutil.floats.Float2ObjectMap;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Fonts {
    ICON("icon/icon.ttf"),
    SOCIAL_ICON("icon/social_icon.ttf"),
    CATEGORY_ICON("icon/category_icon.ttf"),
    ARROW("icon/arrow.ttf"),
    STARS("icon/stars.ttf"),
    SETTINGS("icon/settings.ttf"),

    MINECRAFT("minecraft/minecraft.ttf"),
    SMALL_PIXEL("smallpixel/small_pixel.ttf"),

    INTER_BLACK("inter/inter_black.ttf"),
    INTER_BOLD("inter/inter_bold.ttf"),
    INTER_EXTRABOLD("inter/inter_extrabold.ttf"),
    INTER_EXTRALIGHT("inter/inter_extralight.ttf"),
    INTER_LIGHT("inter/inter_light.ttf"),
    INTER_MEDIUM("inter/inter_medium.ttf"),
    INTER_REGULAR("inter/inter_regular.ttf"),
    INTER_SEMIBOLD("inter/inter_semibold.ttf"),
    INTER_THIN("inter/inter_thin.ttf");

    private final String file;
    private final Float2ObjectMap<Font> fontMap = new Float2ObjectArrayMap<>();

    public Font get(float size) {
        return fontMap.computeIfAbsent(size, font -> {
            try {
                return Font.create(getFile(), size);
            } catch(Exception e) {
                throw new RuntimeException("Unable to load font: " + this, e);
            }
        });
    }

}