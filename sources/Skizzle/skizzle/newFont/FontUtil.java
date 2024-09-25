/*
 * Decompiled with CFR 0.150.
 */
package skizzle.newFont;

import java.awt.Font;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import skizzle.newFont.MinecraftFontRenderer;

public class FontUtil {
    public static MinecraftFontRenderer regular2;
    public static MinecraftFontRenderer cleanmediumish;
    public static MinecraftFontRenderer expandedfont;
    public static Font large_;
    public static Font cleanhuge_;
    public static MinecraftFontRenderer cleanlarge;
    public static Font clean_;
    public static int completed;
    public static Font cleanSmall_;
    public static Font cleankindalarge_;
    public static MinecraftFontRenderer tabguimodule;
    public static Font tabguimodule_;
    public static Font cleanmedium_;
    public static MinecraftFontRenderer cleanSmall;
    public static MinecraftFontRenderer cleantiny;
    public static Font cleantiny_;
    public static Font regularSmall_;
    public static MinecraftFontRenderer tommysmallfont;
    public static MinecraftFontRenderer cleankindalarge;
    public static Font smallfont_;
    public static Font regular2_;
    public static MinecraftFontRenderer smallfont;
    public static MinecraftFontRenderer regular;
    public static MinecraftFontRenderer regularSmall;
    public static Font regular_;
    public static MinecraftFontRenderer mediumfont;
    public static Font cleanmediumish_;
    public static Font mediumfont_;
    public static MinecraftFontRenderer cleanhuge;
    public static MinecraftFontRenderer cleanmedium;
    public static Font tommysmallfont_;
    public static MinecraftFontRenderer clean;
    public static Font expandedfont_;
    public static MinecraftFontRenderer large;
    public static Font cleanlarge_;

    public static void lambda$1() {
        HashMap Nigga = new HashMap();
        ++completed;
    }

    public static void lambda$0() {
        HashMap<String, Font> Nigga = new HashMap<String, Font>();
        regular_ = FontUtil.getFont(Nigga, Qprot0.0("\udca6\u71c4\ue7d4\u2701\u7e54\u590a\u8c3b\ub0e7"), 28);
        regular2_ = FontUtil.getFont(Nigga, Qprot0.0("\udca6\u71c4\ue7d4\u2701\u7e54\u590a\u8c3b\ub0e7"), 24);
        clean_ = FontUtil.getFont(Nigga, Qprot0.0("\udc85\u71db\ue7c8\u2709\u7e1f\u5918\u8c21\ub0f5\ud7ad\ud544\ud327\uaf0a"), 24);
        cleanmediumish_ = FontUtil.getFont(Nigga, Qprot0.0("\udc85\u71db\ue7c8\u2709\u7e1f\u5918\u8c21\ub0f5\ud7ad\ud544\ud327\uaf0a"), 18);
        cleankindalarge_ = FontUtil.getFont(Nigga, Qprot0.0("\udc85\u71db\ue7c8\u2709\u7e1f\u5918\u8c21\ub0f5\ud7ad\ud544\ud327\uaf0a"), 28);
        cleantiny_ = FontUtil.getFont(Nigga, Qprot0.0("\udc85\u71db\ue7c8\u2709\u7e1f\u5918\u8c21\ub0f5\ud7ad\ud544\ud327\uaf0a"), 14);
        cleanhuge_ = FontUtil.getFont(Nigga, Qprot0.0("\udc85\u71db\ue7c8\u2709\u7e1f\u5918\u8c21\ub0f5\ud7ad\ud544\ud327\uaf0a"), 50);
        cleanlarge_ = FontUtil.getFont(Nigga, Qprot0.0("\udc85\u71db\ue7c8\u2709\u7e1f\u5918\u8c21\ub0f5\ud7ad\ud544\ud327\uaf0a"), 31);
        cleanmedium_ = FontUtil.getFont(Nigga, Qprot0.0("\udc85\u71db\ue7c8\u2709\u7e1f\u5918\u8c21\ub0f5\ud7ad\ud544\ud327\uaf0a"), 20);
        cleanSmall_ = FontUtil.getFont(Nigga, Qprot0.0("\udc85\u71db\ue7c8\u2709\u7e1f\u5918\u8c21\ub0f5\ud7ad\ud544\ud327\uaf0a"), 17);
        mediumfont_ = FontUtil.getFont(Nigga, Qprot0.0("\udca6\u71c4\ue7d4\u2701\u7e54\u590a\u8c3b\ub0e7"), 20);
        smallfont_ = FontUtil.getFont(Nigga, Qprot0.0("\udc85\u71db\ue7c8\u2709\u7e1f\u5918\u8c21\ub0f5\ud7ad\ud544\ud327\uaf0a"), 18);
        tommysmallfont_ = FontUtil.getFont(Nigga, Qprot0.0("\udca6\u71c4\ue7d4\u2701\u7e54\u590a\u8c3b\ub0e7"), 18);
        regularSmall_ = FontUtil.getFont(Nigga, Qprot0.0("\udca6\u71c4\ue7d4\u2701\u7e54\u590a\u8c3b\ub0e7"), 16);
        large_ = FontUtil.getFont(Nigga, Qprot0.0("\udc90\u71c4\ue7d5\u2708\u7e03\u594d\u8c61\ub0f5\ud7f7\ud556"), 25);
        expandedfont_ = FontUtil.getFont(Nigga, Qprot0.0("\udca6\u71c4\ue7d4\u2701\u7e54\u590a\u8c3b\ub0e7"), 38);
        tabguimodule_ = FontUtil.getFont(Nigga, Qprot0.0("\udcb0\u71c2\ue7cc\u2709\u7e1f\u5950\u8c3b\ub0f5\ud7e5"), 35);
        ++completed;
    }

    public static boolean hasLoaded() {
        return completed >= 3;
    }

    public static void bootstrap() {
        new Thread(FontUtil::lambda$0).start();
        new Thread(FontUtil::lambda$1).start();
        new Thread(FontUtil::lambda$2).start();
        while (!FontUtil.hasLoaded()) {
            try {
                Thread.sleep((long)-2037985539 ^ 0xFFFFFFFF8686CEF8L);
            }
            catch (InterruptedException Nigga) {
                Nigga.printStackTrace();
            }
        }
        cleantiny = new MinecraftFontRenderer(cleantiny_, true, true);
        cleankindalarge = new MinecraftFontRenderer(cleankindalarge_, true, true);
        regular = new MinecraftFontRenderer(regular_, true, true);
        regular2 = new MinecraftFontRenderer(regular2_, true, true);
        cleanlarge = new MinecraftFontRenderer(cleanlarge_, true, true);
        cleanhuge = new MinecraftFontRenderer(cleanhuge_, true, true);
        cleanmedium = new MinecraftFontRenderer(cleanmedium_, true, true);
        clean = new MinecraftFontRenderer(clean_, true, true);
        cleanSmall = new MinecraftFontRenderer(cleanSmall_, true, true);
        mediumfont = new MinecraftFontRenderer(mediumfont_, true, true);
        smallfont = new MinecraftFontRenderer(smallfont_, true, true);
        tommysmallfont = new MinecraftFontRenderer(tommysmallfont_, true, true);
        regularSmall = new MinecraftFontRenderer(regularSmall_, true, true);
        large = new MinecraftFontRenderer(large_, true, true);
        expandedfont = new MinecraftFontRenderer(expandedfont_, true, true);
        tabguimodule = new MinecraftFontRenderer(expandedfont_, true, true);
        cleanmediumish = new MinecraftFontRenderer(cleanmediumish_, true, true);
    }

    public static Font getFont(Map<String, Font> Nigga, String Nigga2, int Nigga3) {
        Font Nigga4 = null;
        try {
            if (Nigga.containsKey(Nigga2)) {
                Nigga4 = Nigga.get(Nigga2).deriveFont(0, Nigga3);
            } else {
                InputStream Nigga5 = Minecraft.getMinecraft().getResourceManager().getResource(new ResourceLocation(Qprot0.0("\udc87\u71c7\ue7d1\ud156\u6c2a\u590a\u8c60") + Nigga2)).getInputStream();
                Nigga4 = Font.createFont(0, Nigga5);
                Nigga.put(Nigga2, Nigga4);
                Nigga4 = Nigga4.deriveFont(0, Nigga3);
            }
        }
        catch (Exception Nigga6) {
            Nigga6.printStackTrace();
            System.out.println(Qprot0.0("\udc81\u71d9\ue7ca\ud15c\u6c36\u595e\u8c23\ub0ee\u21b4\uc76a\ud33a\uaf02\u5e3d\u04ba\u181b\u40ae\u42e7\u6ad7"));
            Nigga4 = new Font(Qprot0.0("\udca0\u71ce\ue7de\ud152\u6c31\u5912\u8c3b"), 0, 10);
        }
        return Nigga4;
    }

    public static {
        throw throwable;
    }

    public FontUtil() {
        FontUtil Nigga;
    }

    public static void lambda$2() {
        HashMap Nigga = new HashMap();
        ++completed;
    }
}

