/*
 * Decompiled with CFR 0_118.
 * 
 * Could not load the following classes:
 *  org.newdawn.slick.UnicodeFont
 */
package pw.vertexcode.util.font;

import java.awt.Font;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.newdawn.slick.UnicodeFont;
import pw.vertexcode.util.font.UnicodeFontRenderer;

public class FontManager {
    private static final Map<String, UnicodeFontRenderer> REGISTERED_RENDERERS = new HashMap<String, UnicodeFontRenderer>();

    private FontManager() {
    }

    public static UnicodeFontRenderer getFont(String fontName, int size) {
        return FontManager.getFont("pw.vertexcode.util.font.fonts", fontName, size);
    }

    public static UnicodeFontRenderer getFont(String packageName, String fontName, int size) {
        packageName = "." + packageName;
        packageName = packageName.replace(".", "/");
        for (Map.Entry<String, UnicodeFontRenderer> fr : REGISTERED_RENDERERS.entrySet()) {
            if (fr.getValue().getFont().getFont().getSize() != size || !fr.getKey().toLowerCase().equalsIgnoreCase(fontName.toLowerCase())) continue;
            return fr.getValue();
        }
        String pkg = String.valueOf(packageName) + "/" + fontName + ".ttf";
        InputStream is = FontManager.class.getResourceAsStream(pkg);
        UnicodeFontRenderer fr2 = new UnicodeFontRenderer(is, size);
        REGISTERED_RENDERERS.put(fontName, fr2);
        return fr2;
    }
}

