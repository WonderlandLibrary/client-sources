package dev.stephen.nexus.utils.font;

import dev.stephen.nexus.Client;
import dev.stephen.nexus.utils.font.fonts.FontRenderer;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import java.awt.*;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

// some random library i forgot

public class FontManager {

    private final Map<FontKey, FontRenderer> fontCache = new HashMap<>();

    public void initialize() {
        for (Type type : Type.values()) {
            for (int size = 4; size <= 32; size++) {
                fontCache.put(new FontKey(size, type), create(size, type.getType()));
            }
        }
    }

    @SneakyThrows
    public FontRenderer create(float size, String name) {
        String path = "assets/nexus/fonts/" + name + ".ttf";

        try (InputStream inputStream = Client.class.getClassLoader().getResourceAsStream(path)) {
            Font[] font = Font.createFonts(Objects.requireNonNull(inputStream));

            return new FontRenderer(font, size, 256, 2);
        }
    }

    public FontRenderer getSize(int size, Type type) {
        return fontCache.computeIfAbsent(new FontKey(size, type), k -> create(size, type.getType()));
    }

    @Getter
    @RequiredArgsConstructor
    public enum Type {
        PRODUCT_SANS_BOLD("product_sans_bold"),
        PRODUCT_SANS_MEDIUM("product_sans_medium"),
        PRODUCT_SANS_REGULAR("product_sans_regular"),
        VERDANA("verdana_pro_regular"),
        SFUI("sfui_display_regular");

        private final String type;
    }

    private static final class FontKey {
        private final int size;
        private final Type type;

        private FontKey(int size, Type type) {
            this.size = size;
            this.type = type;
        }

        public int size() {
            return size;
        }

        public Type type() {
            return type;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) return true;
            if (obj == null || obj.getClass() != this.getClass()) return false;
            var that = (FontKey) obj;
            return this.size == that.size &&
                    Objects.equals(this.type, that.type);
        }

        @Override
        public int hashCode() {
            return Objects.hash(size, type);
        }

        @Override
        public String toString() {
            return "FontKey[" +
                    "size=" + size + ", " +
                    "type=" + type + ']';
        }

        }
}
