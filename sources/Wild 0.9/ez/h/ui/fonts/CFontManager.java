package ez.h.ui.fonts;

import java.awt.*;
import ez.h.utils.*;

public class CFontManager
{
    public static CFontRenderer euclidflexsemibold;
    public static CFontRenderer monaqi;
    public static CFontRenderer acrom;
    public static CFontRenderer montserratbold;
    public static CFontRenderer quicksandbig;
    private static boolean useGlobalFont;
    public static CFontRenderer oxygen;
    public static CFontRenderer momcake;
    public static CFontRenderer montserratmedium;
    public static CFontRenderer manrope;
    public static CFontRenderer montserrat;
    public static CFontRenderer simplysans;
    public static CFontRenderer rany;
    public static CFontRenderer jellyanica;
    public static CFontRenderer simplysansbold;
    public static CFontRenderer montserratbig;
    public static CFontRenderer partyconfetti;
    public static CFontRenderer gianesmall;
    public static CFontRenderer opensans;
    private static CFontRenderer currentfont;
    public static CFontRenderer museo;
    public static CFontRenderer wm;
    public static CFontRenderer euclidflex;
    public static CFontRenderer stylo2;
    public static CFontRenderer targethud;
    public static CFontRenderer comfortaa;
    public static CFontRenderer manropesmall;
    public static CFontRenderer euclidflexbold;
    public static CFontRenderer montblanc;
    public static CFontRenderer global;
    public static CFontRenderer stylo;
    public static CFontRenderer acrombold;
    public static CFontRenderer fontRenderer;
    
    public boolean useGlobalFont() {
        return CFontManager.useGlobalFont;
    }
    
    public CFontRenderer getDefaultFont() {
        return CFontManager.currentfont;
    }
    
    public static void setDefaultFont(final CFontRenderer currentfont) {
        CFontManager.currentfont = currentfont;
    }
    
    static {
        CFontManager.useGlobalFont = true;
    }
    
    public static boolean setUseGlobalFont(final boolean useGlobalFont) {
        return CFontManager.useGlobalFont = useGlobalFont;
    }
    
    public static void init() {
        CFontManager.oxygen = new CFontRenderer(new Font("Minecraft-Rus", 0, 0x36 ^ 0x1E), true, 8);
        CFontManager.wm = new CFontRenderer(new Font("Modern", 1, 0x2D ^ 0xE), true, 8);
        CFontManager.quicksandbig = new CFontRenderer(new Font("Quicksand-Bold", 1, 0xEE ^ 0xC6), true, 8);
        CFontManager.fontRenderer = new CFontRenderer(new Font("Roboto", 0, 0xAF ^ 0x87), true, 8);
        CFontManager.stylo = new CFontRenderer(Utils.getFontFromTTF(new nf("wild/fonts/stylo.ttf"), 100.0f, 0), true, 8);
        CFontManager.stylo2 = new CFontRenderer(Utils.getFontFromTTF(new nf("wild/fonts/stylo.ttf"), 60.0f, 0), true, 8);
        CFontManager.manrope = new CFontRenderer(Utils.getFontFromTTF(new nf("wild/fonts/manrope.ttf"), 37.0f, 0), true, 8);
        CFontManager.targethud = new CFontRenderer(Utils.getFontFromTTF(new nf("wild/fonts/prostirsans.ttf"), 26.0f, 0), true, 8);
        CFontManager.manropesmall = new CFontRenderer(Utils.getFontFromTTF(new nf("wild/fonts/manrope.ttf"), 32.0f, 0), true, 8);
        CFontManager.simplysans = new CFontRenderer(Utils.getFontFromTTF(new nf("wild/fonts/simplysans.ttf"), 36.0f, 0), true, 8);
        CFontManager.simplysansbold = new CFontRenderer(Utils.getFontFromTTF(new nf("wild/fonts/ssbold.ttf"), 36.0f, 0), true, 8);
        CFontManager.momcake = new CFontRenderer(Utils.getFontFromTTF(new nf("wild/fonts/momcake.ttf"), 45.0f, 0), true, 8);
        CFontManager.acrom = new CFontRenderer(Utils.getFontFromTTF(new nf("wild/fonts/acrom.ttf"), 38.0f, 0), true, 8);
        CFontManager.acrombold = new CFontRenderer(Utils.getFontFromTTF(new nf("wild/fonts/acrombold.ttf"), 38.0f, 0), true, 8);
        CFontManager.rany = new CFontRenderer(Utils.getFontFromTTF(new nf("wild/fonts/rany.ttf"), 36.0f, 0), true, 8);
        CFontManager.euclidflex = new CFontRenderer(Utils.getFontFromTTF(new nf("wild/fonts/euclidflex.ttf"), 36.0f, 0), true, 8);
        CFontManager.comfortaa = new CFontRenderer(Utils.getFontFromTTF(new nf("wild/fonts/comfortaa.ttf"), 36.0f, 0), true, 8);
        CFontManager.opensans = new CFontRenderer(Utils.getFontFromTTF(new nf("wild/fonts/opensans.ttf"), 36.0f, 0), true, 8);
        CFontManager.euclidflexsemibold = new CFontRenderer(Utils.getFontFromTTF(new nf("wild/fonts/euclidflexsemibold.ttf"), 36.0f, 0), true, 8);
        CFontManager.euclidflexbold = new CFontRenderer(Utils.getFontFromTTF(new nf("wild/fonts/euclidflexbold.ttf"), 36.0f, 0), true, 8);
        CFontManager.monaqi = new CFontRenderer(Utils.getFontFromTTF(new nf("wild/fonts/monaqi.ttf"), 36.0f, 0), true, 8);
        CFontManager.montblanc = new CFontRenderer(Utils.getFontFromTTF(new nf("wild/fonts/montblanc.ttf"), 36.0f, 0), true, 8);
        CFontManager.jellyanica = new CFontRenderer(Utils.getFontFromTTF(new nf("wild/fonts/jellyanica.ttf"), 40.0f, 0), true, 8);
        CFontManager.montserrat = new CFontRenderer(Utils.getFontFromTTF(new nf("wild/fonts/montserrat.ttf"), 40.0f, 0), true, 8);
        CFontManager.montserratmedium = new CFontRenderer(Utils.getFontFromTTF(new nf("wild/fonts/montserrat.ttf"), 55.0f, 0), true, 8);
        CFontManager.montserratbold = new CFontRenderer(Utils.getFontFromTTF(new nf("wild/fonts/montserratbold.ttf"), 40.0f, 0), true, 8);
        CFontManager.montserratbig = new CFontRenderer(Utils.getFontFromTTF(new nf("wild/fonts/montserrat.ttf"), 80.0f, 0), true, 8);
        CFontManager.gianesmall = new CFontRenderer(Utils.getFontFromTTF(new nf("wild/fonts/montserrat.ttf"), 31.0f, 0), true, 8);
        CFontManager.museo = new CFontRenderer(Utils.getFontFromTTF(new nf("wild/fonts/museo.ttf"), 46.0f, 0), true, 8);
        CFontManager.partyconfetti = new CFontRenderer(Utils.getFontFromTTF(new nf("wild/fonts/partyconfetti.ttf"), 130.0f, 0), true, 8);
        setDefaultFont(CFontManager.oxygen);
        setUseGlobalFont(true);
    }
}
