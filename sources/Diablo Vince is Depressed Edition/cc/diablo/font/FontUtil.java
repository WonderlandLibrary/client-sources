/*
 * Decompiled with CFR 0.152.
 */
package cc.diablo.font;

import cc.diablo.font.MinecraftFontRenderer;
import java.awt.Font;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

public class FontUtil {
    public static volatile int completed;
    public static MinecraftFontRenderer cleanmedium;
    public static MinecraftFontRenderer clean;
    public static MinecraftFontRenderer cleanSmall;
    public static MinecraftFontRenderer testFont;
    private static Font cleanmedium_;
    private static Font clean_;
    private static Font cleanSmall_;
    private static Font _testFont;

    private static Font getFont(Map<String, Font> locationMap, String location, int size) {
        Font font = null;
        try {
            if (locationMap.containsKey(location)) {
                font = locationMap.get(location).deriveFont(0, size);
            } else {
                InputStream is = Minecraft.getMinecraft().getResourceManager().getResource(new ResourceLocation("Client/fonts/" + location)).getInputStream();
                font = Font.createFont(0, is);
                locationMap.put(location, font);
                font = font.deriveFont(0, size);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error loading font");
            font = new Font("default", 0, 10);
        }
        return font;
    }

    public static boolean hasLoaded() {
        return completed >= 3;
    }

    public static void bootstrap() {
        new Thread(() -> {
            HashMap<String, Font> locationMap = new HashMap<String, Font>();
            clean_ = FontUtil.getFont(locationMap, "Applefnt.ttf", 24);
            cleanmedium_ = FontUtil.getFont(locationMap, "Applefnt.ttf", 20);
            cleanSmall_ = FontUtil.getFont(locationMap, "Applefnt.ttf", 17);
            _testFont = FontUtil.getFont(locationMap, "Dreamscape.ttf", 45);
            completed = 3;
        }).start();
        while (!FontUtil.hasLoaded()) {
            try {
                Thread.sleep(5L);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        cleanmedium = new MinecraftFontRenderer(cleanmedium_, true, true);
        clean = new MinecraftFontRenderer(clean_, true, true);
        cleanSmall = new MinecraftFontRenderer(cleanSmall_, true, true);
        testFont = new MinecraftFontRenderer(_testFont, true, true);
    }
}

