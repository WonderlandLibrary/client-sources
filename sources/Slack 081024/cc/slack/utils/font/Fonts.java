package cc.slack.utils.font;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;


public class Fonts {
    private static final Map<String, Font> loadedFonts = new HashMap<>();
    private static final Map<String, String> fontFiles = new HashMap<>();

    static {
        fontFiles.put("Poppins", "poppins-Light.ttf");
        fontFiles.put("IconFont", "guiicons.ttf");
        fontFiles.put("SFBold", "sfsemibold.ttf");
        fontFiles.put("SFReg", "sfregular.ttf");
        fontFiles.put("Axi", "axi.ttf");
        fontFiles.put("Apple", "apple.ttf");
        fontFiles.put("Roboto", "roboto.ttf");
        fontFiles.put("Arial", "Arial");
        fontFiles.put("Checkmark", "checkmark.ttf");
        fontFiles.put("Modern", "SF-Pro-Rounded-Bold.otf");
        fontFiles.put("Modern Regular", "SF-Pro-Rounded-Regular.otf");
    }

    public static MCFontRenderer getFontRenderer(String fontName, int fontSize, boolean antiAlias, boolean fractionalMetrics) {
        Font baseFont = getBaseFont(fontName);
        Font sizedFont = baseFont.deriveFont((float) fontSize);
        return new MCFontRenderer(sizedFont, antiAlias, fractionalMetrics);
    }

    public static MCFontRenderer getFontRenderer(String fontName, int fontSize) {
        return getFontRenderer(fontName, fontSize, true, false);
    }

    private static Font getBaseFont(String fontName) {
        if (!loadedFonts.containsKey(fontName)) {
            String fontFile = fontFiles.get(fontName);
            Font font = loadFont(fontFile);
            loadedFonts.put(fontName, font);
        }
        return loadedFonts.get(fontName);
    }

    private static Font loadFont(String fontFileName) {
        try {
            if (fontFileName.equals("Arial")) {
                return new Font("Arial", Font.PLAIN, 24);
            }
            InputStream is = Minecraft.getMinecraft().getResourceManager().getResource(new ResourceLocation("slack/fonts/" + fontFileName)).getInputStream();
            return Font.createFont(Font.TRUETYPE_FONT, is);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
            return new Font("Arial", Font.PLAIN, 24);
        }
    }

    public static final MCFontRenderer poppins10 = getFontRenderer("Poppins", 10);
    public static final MCFontRenderer poppins18 = getFontRenderer("Poppins", 18);
    public static final MCFontRenderer poppins20 = getFontRenderer("Poppins", 20);
    public static final MCFontRenderer poppins24 = getFontRenderer("Poppins", 24);

    // SF-Pro-Rounded-Bold
    public static final MCFontRenderer sfRoundedBold18 = getFontRenderer("Modern", 18);
    public static final MCFontRenderer sfRoundedBold20 = getFontRenderer("Modern", 20);
    public static final MCFontRenderer sfRoundedBold24 = getFontRenderer("Modern", 24);

    // SF-Pro-Rounded-Regular
    public static final MCFontRenderer sfRoundedRegular18 = getFontRenderer("Modern Regular", 18);
    public static final MCFontRenderer sfRoundedRegular20 = getFontRenderer("Modern Regular", 20);
    public static final MCFontRenderer sfRoundedRegular24 = getFontRenderer("Modern Regular", 24);

    public static final MCFontRenderer iconFont24 = getFontRenderer("IconFont", 24, true, true);
    public static final MCFontRenderer iconFont38 = getFontRenderer("IconFont", 38, true, true);

    public static final MCFontRenderer sfBold30 = getFontRenderer("SFBold", 30, true, true);
    public static final MCFontRenderer sfBold18 = getFontRenderer("SFBold", 18, true, true);
    public static final MCFontRenderer sfReg18 = getFontRenderer("SFReg", 18, true, true);
    public static final MCFontRenderer sfReg24 = getFontRenderer("SFReg", 24, true, true);
    public static final MCFontRenderer sfReg45 = getFontRenderer("SFReg", 45, true, true);

    public static final MCFontRenderer axi24 = getFontRenderer("Axi", 24);
    public static final MCFontRenderer axi12 = getFontRenderer("Axi", 12);
    public static final MCFontRenderer axi16 = getFontRenderer("Axi", 16);
    public static final MCFontRenderer axi18 = getFontRenderer("Axi", 18);
    public static final MCFontRenderer axi35 = getFontRenderer("Axi", 35);
    public static final MCFontRenderer axi45 = getFontRenderer("Axi", 45);

    public static final MCFontRenderer apple18 = getFontRenderer("Apple", 18);
    public static final MCFontRenderer apple20 = getFontRenderer("Apple", 20);
    public static final MCFontRenderer apple24 = getFontRenderer("Apple", 24);
    public static final MCFontRenderer apple45 = getFontRenderer("Apple", 45, true, true);

    public static final MCFontRenderer roboto10 = getFontRenderer("Roboto", 10);
    public static final MCFontRenderer roboto18 = getFontRenderer("Roboto", 18);
    public static final MCFontRenderer roboto20 = getFontRenderer("Roboto", 20);
    public static final MCFontRenderer roboto24 = getFontRenderer("Roboto", 24);

    public static final MCFontRenderer arial18 = getFontRenderer("Arial", 18, true, true);
    public static final MCFontRenderer arial45 = getFontRenderer("Arial", 45, true, false);
    public static final MCFontRenderer arial65 = getFontRenderer("Arial", 65, true, false);

    public static final MCFontRenderer checkmark24 = getFontRenderer("Checkmark", 24);


}
