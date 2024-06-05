package alphentus.utils.fontrenderer;

import java.awt.Font;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.client.Minecraft;

/**
 * @author avox
 * @since avox on 17/07/2020.
 */

public class FontManager {
    private HashMap fonts = new HashMap();

    public UnicodeFontRenderer comfortaa45 = this.getFont("comfortaa", 45.0F);
    public UnicodeFontRenderer comfortaa16 = this.getFont("comfortaa", 16.0F);
    public UnicodeFontRenderer comfortaa26 = this.getFont("comfortaa", 26.0F);
    public UnicodeFontRenderer comfortaa22 = this.getFont("comfortaa", 22.0F);
    public UnicodeFontRenderer comfortaa18 = this.getFont("comfortaa", 18.0F);
    public UnicodeFontRenderer comfortaa14 = this.getFont("comfortaa", 14.0F);

    public UnicodeFontRenderer SF12 = this.getFont("SF-UI-Display-Regular", 12.0F);
    public UnicodeFontRenderer SF13 = this.getFont("SF-UI-Display-Regular", 13.0F);
    public UnicodeFontRenderer SF14 = this.getFont("SF-UI-Display-Regular", 14.0F);
    public UnicodeFontRenderer SF15 = this.getFont("SF-UI-Display-Regular", 15.0F);
    public UnicodeFontRenderer SF16 = this.getFont("SF-UI-Display-Regular", 16.0F);
    public UnicodeFontRenderer SF17 = this.getFont("SF-UI-Display-Regular", 17.0F);
    public UnicodeFontRenderer SF18 = this.getFont("SF-UI-Display-Regular", 18.0F);
    public UnicodeFontRenderer SF19 = this.getFont("SF-UI-Display-Regular", 19.0F);
    public UnicodeFontRenderer SF20 = this.getFont("SF-UI-Display-Regular", 20.0F);

    public UnicodeFontRenderer azuirHeavy = this.getFont("Azur-Heavy", 32.0F);

    public UnicodeFontRenderer nirmalaS19 = this.getFont("NirmalaS", 19.0F);
    public UnicodeFontRenderer nirmalaS20 = this.getFont("NirmalaS", 20);
    public UnicodeFontRenderer nirmalaS21 = this.getFont("NirmalaS", 21);
    public UnicodeFontRenderer nirmalaS24 = this.getFont("NirmalaS", 24);
    public UnicodeFontRenderer nirmalaS26 = this.getFont("NirmalaS", 26);
    public UnicodeFontRenderer nirmalaS28 = this.getFont("NirmalaS", 28);
    public UnicodeFontRenderer nirmalaS30 = this.getFont("NirmalaS", 30);
    public UnicodeFontRenderer nirmalaS60 = this.getFont("NirmalaS", 60);

    public UnicodeFontRenderer verdana16 = this.getFont("VERDANA", 16.0F);
    public UnicodeFontRenderer verdana17 = this.getFont("VERDANA", 17.0F);
    public UnicodeFontRenderer verdana18 = this.getFont("VERDANA", 18.0F);
    public UnicodeFontRenderer verdana22 = this.getFont("VERDANA", 22.0F);
    public UnicodeFontRenderer verdana24 = this.getFont("VERDANA", 24.0F);

    public UnicodeFontRenderer roobert18 = this.getFont("Roobert-Light", 18F);
    public UnicodeFontRenderer roobert19 = this.getFont("Roobert-Light", 19F);
    public UnicodeFontRenderer roobert20 = this.getFont("Roobert-Light", 20F);

    public UnicodeFontRenderer stem25 = this.getFont("Stem-Light", 25F);
    public UnicodeFontRenderer stem20 = this.getFont("Stem-Light", 20F);
    public UnicodeFontRenderer stem19 = this.getFont("M Ying Hei PRC W48 W3", 20F);
    public UnicodeFontRenderer stem17 = this.getFont("Stem-Light", 17F);
    public UnicodeFontRenderer stem18 = this.getFont("Stem-Light", 18F);
    public UnicodeFontRenderer stem15 = this.getFont("M Ying Hei PRC W48 W3", 15F);
    public UnicodeFontRenderer stem16 = this.getFont("Stem-Light", 16F);
    public UnicodeFontRenderer stem35 = this.getFont("Stem-Light", 35F);

    public UnicodeFontRenderer tahoma24 = this.getFont("tahoma", 24.0F);
    public UnicodeFontRenderer tahoma21 = this.getFont("tahoma", 21.0F);
    public UnicodeFontRenderer tahoma20 = this.getFont("tahoma", 20.0F);
    public UnicodeFontRenderer tahoma18 = this.getFont("tahoma", 18.0F);


    public UnicodeFontRenderer myinghei18 = this.getFont("JelloLight", 18.0F);
    public UnicodeFontRenderer myinghei19 = this.getFont("JelloLight", 19.0F);
    public UnicodeFontRenderer myinghei20 = this.getFont("JelloLight", 20.0F);
    public UnicodeFontRenderer myinghei21 = this.getFont("JelloLight", 21.0F);
    public UnicodeFontRenderer myinghei22 = this.getFont("JelloLight", 22.0F);
    public UnicodeFontRenderer myinghei25 = this.getFont("JelloLight", 25.0F);
    public UnicodeFontRenderer myinghei30 = this.getFont("JelloLight", 30F);
    public UnicodeFontRenderer myinghei35 = this.getFont("JelloLight", 35F);
    public UnicodeFontRenderer myinghei40 = this.getFont("JelloLight", 40F);

    public UnicodeFontRenderer thruster24 = this.getFont("Thruster-Regular", 24.0F);
    public UnicodeFontRenderer thruster20 = this.getFont("Thruster-Regular", 20.0F);

    public UnicodeFontRenderer arial14 = this.getFont("Arial", 14.0F);
    public UnicodeFontRenderer arial18 = this.getFont("Arial", 18.0F);
    public UnicodeFontRenderer arial20 = this.getFont("Arial", 20.0F);
    public UnicodeFontRenderer arial24 = this.getFont("Arial", 24.0F);



    public UnicodeFontRenderer getFont(String name, float size) {
        UnicodeFontRenderer unicodeFont = null;

        try {
            if(this.fonts.containsKey(name) && ((HashMap)this.fonts.get(name)).containsKey(Float.valueOf(size))) {
                return (UnicodeFontRenderer)((HashMap)this.fonts.get(name)).get(Float.valueOf(size));
            }

            InputStream e = this.getClass().getResourceAsStream("fonts/" + name + ".ttf");
            Font font = null;
            font = Font.createFont(0, e);
            unicodeFont = new UnicodeFontRenderer(font.deriveFont(size));
            unicodeFont.setUnicodeFlag(true);
            unicodeFont.setBidiFlag(Minecraft.getMinecraft().getLanguageManager().isCurrentLanguageBidirectional());
            HashMap map = new HashMap();
            if(this.fonts.containsKey(name)) {
                map.putAll((Map)this.fonts.get(name));
            }

            map.put(Float.valueOf(size), unicodeFont);
            this.fonts.put(name, map);
        } catch (Exception var7) {
            var7.printStackTrace();
        }

        return unicodeFont;
    }



    public UnicodeFontRenderer getFont(String name, float size, boolean b) {
        UnicodeFontRenderer unicodeFont = null;

        try {
            if(this.fonts.containsKey(name) && ((HashMap)this.fonts.get(name)).containsKey(Float.valueOf(size))) {
                return (UnicodeFontRenderer)((HashMap)this.fonts.get(name)).get(Float.valueOf(size));
            }

            InputStream e = this.getClass().getResourceAsStream("fonts/" + name + ".otf");
            Font font = null;
            font = Font.createFont(0, e);
            unicodeFont = new UnicodeFontRenderer(font.deriveFont(size));
            unicodeFont.setUnicodeFlag(true);
            unicodeFont.setBidiFlag(Minecraft.getMinecraft().getLanguageManager().isCurrentLanguageBidirectional());
            HashMap map = new HashMap();
            if(this.fonts.containsKey(name)) {
                map.putAll((Map)this.fonts.get(name));
            }

            map.put(Float.valueOf(size), unicodeFont);
            this.fonts.put(name, map);
        } catch (Exception var8) {
            var8.printStackTrace();
        }

        return unicodeFont;
    }
}
