package club.bluezenith.util.font;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;

import java.awt.*;
import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static java.lang.String.valueOf;
import static java.lang.System.out;

public class FontUtil {
    public static ArrayList<FontRenderer> fonts = new ArrayList<>();
    public static FontRenderer minecraft = Minecraft.getMinecraft().fontRendererObj;
    public static TFontRenderer jetBrainsLight36 = new TFontRenderer(getEpicFont("jet-brains-mono-light", 32)).setIcon(true);
    public static TFontRenderer comicSans42 = new TFontRenderer(getEpicFont("comic-sans-ms", 42));
    public static TFontRenderer SFLight35 = new TFontRenderer(getEpicFont("sf-ui-display-light", 35)).setIcon(true);
    public static TFontRenderer SFLight30 = new TFontRenderer(getEpicFont("sf-ui-display-light", 30)).setIcon(true);
    public static TFontRenderer SFLight42 = new TFontRenderer(getEpicFont("sf-ui-display-light", 42));
    public static TFontRenderer quickSandRegular31 = new TFontRenderer(getEpicFont("quicksand-regular", 31));
    public static TFontRenderer mulishMedium35 = new TFontRenderer(getEpicFont("Mulish-Medium", 35));
    public static TFontRenderer vkDemibold = new TFontRenderer(getEpicFont("vk-demibold", 80)).setIcon(true);

    public static TFontRenderer vkMedium35 = new TFontRenderer(getEpicFont("vk-medium", 42));
    public static TFontRenderer vkMedium35Actual = new TFontRenderer(getEpicFont("vk-medium", 35));
    public static TFontRenderer vkChangelog50 = new TFontRenderer(getEpicFont("vk-demibold", 55)).setIcon(true);
    public static TFontRenderer vkChangelog45 = new TFontRenderer(getEpicFont("vk-demibold", 30)).setIcon(true);
    public static TFontRenderer fluxIcons = new TFontRenderer(getEpicFont("flux_targethud", 27)).setIcon(true);
    public static TFontRenderer mavenMedium42 = new TFontRenderer(getEpicFont("mvnmedium", 42));
    public static TFontRenderer newIconsMedium = new TFontRenderer(getEpicFont("notifIcons", 48)).setIcon(true);
    public static TFontRenderer newIconsSmall = new TFontRenderer(getEpicFont("notifIcons", 30)).setIcon(true);
    public static TFontRenderer ICON_fontArrow28 = new TFontRenderer(getEpicFont("icons-levzzz", 18)).setIcon(true);
    public static TFontRenderer ICON_testFont = new TFontRenderer(getEpicFont("test-font", 20)).setIcon(true);
    public static TFontRenderer ICON_testFont2 = new TFontRenderer(getEpicFont("test-font", 28)).setIcon(true);
    public static TFontRenderer ICON_testFont3 = new TFontRenderer(getEpicFont("test-font", 36)).setIcon(true);
    public static TFontRenderer ICON_searchIcon = new TFontRenderer(getEpicFont("search-icon", 42)).setIcon(true);
    public static TFontRenderer inter28 = new TFontRenderer(getEpicFont("Rubik-Regular", 32F)).setIcon(true);
    public static TFontRenderer inter35 = new TFontRenderer(getEpicFont("Rubik-Regular", 35F)).setIcon(true);
    public static TFontRenderer rubikR30 = new TFontRenderer(getEpicFont("Rubik-Regular", 30F)).setIcon(true);
    public static TFontRenderer rubikR32 = new TFontRenderer(getEpicFont("Rubik-Regular", 32F)).setIcon(true);
    public static TFontRenderer rubikR27 = new TFontRenderer(getEpicFont("Rubik-Regular", 27.5F)).setIcon(true);
    public static TFontRenderer rubikMedium32 = new TFontRenderer(getEpicFont("Rubik-Medium", 33F)).setIcon(true);
    public static TFontRenderer rubikMedium37 = new TFontRenderer(getEpicFont("Rubik-Medium", 37F)).setIcon(true);
    public static TFontRenderer rubikMedium45 = new TFontRenderer(getEpicFont("Rubik-Medium", 45F)).setIcon(true);
    public static TFontRenderer interMedium28 = new TFontRenderer(getEpicFont("Inter-Medium", 28)).setIcon(true);
    public static TFontRenderer starIcon = new TFontRenderer(getEpicFont("star", 28)).setIcon(true);
    public static TFontRenderer sfx = new TFontRenderer(getEpicFont("fx", 120)).setIcon(true);

    private static final List<TFontRenderer> dynamicFonts = new ArrayList<>();

    static {
        try {
            for (Field i : FontUtil.class.getDeclaredFields()) {
                i.setAccessible(true);
                Object o = null;
                try {
                    o = i.get(FontUtil.class);
                } catch (IllegalAccessException ignored) {
                }
                if(o instanceof TFontRenderer) {
                    if(((TFontRenderer)o).isIconFont) continue;
                }
                if (o instanceof FontRenderer) {
                    fonts.add((FontRenderer) o);
                }
            }
            fonts.sort((sigma, client) -> Float.compare(client.getStringWidthF("sigma"), sigma.getStringWidthF("sigma")));
        } catch(Exception ex) {
            out.println("ERROR");
            ex.printStackTrace();
        }
    }

    public static TFontRenderer createFont(String path, float size) {
        for (TFontRenderer dynamicFont : dynamicFonts) {
            if(dynamicFont.getName().equals(path) && dynamicFont.size == size) return dynamicFont;
        }
        final TFontRenderer created = new TFontRenderer(getEpicFont(path, size));
        created.setName(path);
        created.size = size;
        dynamicFonts.add(created);
        return created;
    }
    private static final String FONT_PATH = "/assets/minecraft/club/bluezenith/fonts/";

    private static Font getEpicFont(String str, float size) {
        Font font = new Font("default", Font.PLAIN, (int) (size / 2));
        try {
            InputStream is = FontUtil.class.getResourceAsStream(FONT_PATH + str + ".ttf");
            if (is == null) {
                is = FontUtil.class.getResourceAsStream(FONT_PATH + str + ".otf");
                if (is == null) {
                    throw new NullPointerException("Couldn't find font in path " + str + ".");
                }
            }
            font = Font.createFont(Font.TRUETYPE_FONT, is);
            font = font.deriveFont(Font.PLAIN, size / 2f);
            return font;
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Error loading ttf font");
        }

        return font;
    }

    public static Font getUnepicFont(String str, float size) {
        Font font = new Font("default", Font.PLAIN, (int) (size / 2));
        try {
            final File fontFile = new File(str);
            if(!fontFile.exists()) {
                out.println("Could not find font: " + fontFile.getAbsolutePath());
            }
            font = Font.createFont(Font.TRUETYPE_FONT, fontFile);
            font = font.deriveFont(Font.PLAIN, size / 2f);
            return font;
        } catch (Exception ex) {
            out.println("Error loading ttf font");
        }

        return font;
    }

    public static void drawGradientWithShadow(FontRenderer.sex colorSupplier, FontRenderer fontRenderer, String text, float x, float y) {
        final char[] string = text.toCharArray();
        int index = 0;

        for (char ch : string) {
            if(ch == '§') continue;

            ++index;
            final String character = valueOf(ch);

            fontRenderer.drawString(character, x, y, colorSupplier.colour(index).getRGB(), true);
            x += fontRenderer.getStringWidthF(character);
        }
    }
}
