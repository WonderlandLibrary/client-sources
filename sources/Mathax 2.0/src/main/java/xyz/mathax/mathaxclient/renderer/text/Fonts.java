package xyz.mathax.mathaxclient.renderer.text;

import xyz.mathax.mathaxclient.MatHax;
import xyz.mathax.mathaxclient.gui.WidgetScreen;
import xyz.mathax.mathaxclient.init.PreInit;
import xyz.mathax.mathaxclient.renderer.Shaders;
import xyz.mathax.mathaxclient.systems.themes.Themes;
import xyz.mathax.mathaxclient.utils.Utils;
import xyz.mathax.mathaxclient.utils.text.FontUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static xyz.mathax.mathaxclient.MatHax.mc;

public class Fonts {
    public static CustomTextRenderer RENDERER;

    public static final List<FontFamily> FONT_FAMILIES = new ArrayList<>();

    public static final String[] BUILTIN_FONTS = {
            "Comfortaa",
            "JetBrains Mono",
            "Pixelation",
            "Tw Cen MT"
    };

    public static String DEFAULT_FONT_FAMILY;
    public static FontFace DEFAULT_FONT;

    @PreInit(dependencies = Shaders.class)
    public static void refresh() {
        FONT_FAMILIES.clear();

        for (String builtinFont : BUILTIN_FONTS) {
            FontUtils.loadBuiltin(FONT_FAMILIES, builtinFont);
        }

        for (String fontPath : FontUtils.getSearchPaths()) {
            FontUtils.loadSystem(FONT_FAMILIES, new File(fontPath));
        }

        FONT_FAMILIES.sort(Comparator.comparing(FontFamily::getName));

        MatHax.LOG.info("Searching for font families...");

        long start = Utils.getCurrentTimeMillis();

        DEFAULT_FONT_FAMILY = FontUtils.getBuiltinFontInfo(BUILTIN_FONTS[0]).family();
        DEFAULT_FONT = getFamily(DEFAULT_FONT_FAMILY).get(FontInfo.Type.Regular);

        MatHax.LOG.info("Found {} font families in {} milliseconds.", FONT_FAMILIES.size(), Utils.getCurrentTimeMillis() - start);

        DEFAULT_FONT = getFamily(DEFAULT_FONT_FAMILY).get(FontInfo.Type.Regular);
    }

    public static void load(FontFace fontFace) {
        if (fontFace == null) {
            fontFace = DEFAULT_FONT;
        }

        if (RENDERER != null && RENDERER.fontFace.equals(fontFace)) {
            return;
        }

        MatHax.LOG.info("Loading font {} {}...", fontFace.info.family(), fontFace.info.type());

        long start = Utils.getCurrentTimeMillis();

        try {
            RENDERER = new CustomTextRenderer(fontFace);

            MatHax.LOG.info("Loaded font {} {} in {} milliseconds.", fontFace.info.family(), fontFace.info.type(),  Utils.getCurrentTimeMillis() - start);
        } catch (Exception exception) {
            if (fontFace.equals(DEFAULT_FONT)) {
                throw new RuntimeException("Failed to load default font: " + fontFace, exception);
            }

            MatHax.LOG.error("Failed to load font: " + fontFace, exception);

            load(Fonts.DEFAULT_FONT);
        }

        if (mc.currentScreen instanceof WidgetScreen && Themes.get().getTheme().customFont()) {
            ((WidgetScreen) mc.currentScreen).invalidate();
        }
    }

    public static FontFamily getFamily(String name) {
        for (FontFamily fontFamily : Fonts.FONT_FAMILIES) {
            if (fontFamily.getName().equalsIgnoreCase(name)) {
                return fontFamily;
            }
        }

        return null;
    }
}