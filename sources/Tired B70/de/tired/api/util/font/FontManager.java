package de.tired.api.util.font;

import de.tired.interfaces.IHook;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class FontManager implements IHook {

    public static volatile int completed;

    public CustomFont customFontEye;
    public CustomFont customFontEye2;

    public static CustomFont entypo;
    public static CustomFont bebasF;
    public static CustomFont bebasFBig;
    public static CustomFont notosansF;
    public static CustomFont notosansFBig;
    public static CustomFont IBMPlexSans;
    public static CustomFont SFPRO;
    public static CustomFont light;
    public static CustomFont SFPROBold;
    public static CustomFont SFPROBig;
    public static CustomFont robotoF;
    public static CustomFont confortaa;
    public static CustomFont confortaaBig;
    public static CustomFont inter;
    public static CustomFont robotoT;

    @Getter @Setter
    public double size;

    public static CustomFont moonF;

    private Font getFont(Map<String, Font> locationMap, String location, int size) {
        Font font;

        try {
            if (locationMap.containsKey(location)) {
                font = locationMap.get(location).deriveFont(Font.PLAIN, size);
            } else {
                InputStream is = getClass().getResourceAsStream("/assets/minecraft/client/fonts/" + location);
                font = Font.createFont(0, is);
                locationMap.put(location, font);
                font = font.deriveFont(Font.PLAIN, size);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error loading font");
            font = new Font("default", Font.PLAIN, +10);
        }

        return font;
    }

    public static boolean hasLoaded() {
        return completed >= 3;
    }

    public void bootstrap() {

        Map<String, Font> locationMap = new HashMap<>();
        Font customFontEye_ = getFont(locationMap, "font.ttf", 127);
        Font customFontEye_2 = getFont(locationMap, "font.ttf", 67);

        int SIZE = getSize() == 0 ? 20 : (int) getSize();

        Font SFPROF = getFont(locationMap, "SFPro.ttf", getSize() == 0 ? 21 : (int) getSize());

        SFPRO = new CustomFont(SFPROF, true, true);

        Font SFPROF2 = getFont(locationMap, "SFPro.ttf", 34);

        SFPROBig = new CustomFont(SFPROF2, true, true);

        Font SFPROFBoldF = getFont(locationMap, "test.ttf", 19);

        SFPROBold = new CustomFont(SFPROFBoldF, true, true);

        Font IBMPlexSansF = getFont(locationMap, "IBMPlexSans.ttf", 21);

        IBMPlexSans =  new CustomFont(IBMPlexSansF, true, true);

        Font confortaa3 = getFont(locationMap, "confortaa.ttf", getSize() == 0 ? 28 : (int) getSize());

        confortaaBig = new CustomFont(confortaa3, true, true);

        Font lightF = getFont(locationMap, "light.ttf", getSize() == 0 ? 17 : (int) getSize());

        light = new CustomFont(lightF, true, true);


        Font interF = getFont(locationMap, "inter.ttf", getSize() == 0 ? 23 : (int) getSize());

        inter = new CustomFont(interF, true, true);

        Font confortaa2 = getFont(locationMap, "confortaa.ttf", getSize() == 0 ? 18 : (int) getSize());

        confortaa = new CustomFont(confortaa2, true, true);

        Font bebas = getFont(locationMap, "bebas.ttf", getSize() == 0 ? 25 : (int) getSize());

        bebasF = new CustomFont(bebas, true, true);

        Font bebas2 = getFont(locationMap, "bebas.ttf", getSize() == 0 ? 45 : (int) getSize());

        bebasFBig = new CustomFont(bebas2, true, true);

        Font notosans2 = getFont(locationMap, "notosans.ttf", getSize() == 0 ? 33 : (int) getSize());

        notosansFBig = new CustomFont(notosans2, true, true);

        Font notosans = getFont(locationMap, "notosans.ttf", 16);

        notosansF = new CustomFont(notosans, true, true);

        Font roboto = getFont(locationMap, "roboto.ttf", SIZE);

        robotoF = new CustomFont(roboto, true, true);

        Font moon = getFont(locationMap, "moon.ttf", getSize() == 0 ? 21 : (int) getSize());

        Font entypoA = getFont(locationMap, "entypo.otf", getSize() == 0 ? 42 : (int) getSize());

        entypo =  new CustomFont(entypoA, true, true);

        moonF = new CustomFont(moon, true, true);

        Font robotoSus = getFont(locationMap, "robotoT.ttf",20);

        robotoT = new CustomFont(robotoSus, true, true);

        customFontEye2 = new CustomFont(customFontEye_2, true, true);
        customFontEye = new CustomFont(customFontEye_, true, true);
    }

}
