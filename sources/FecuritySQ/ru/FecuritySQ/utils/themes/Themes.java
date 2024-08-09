package ru.FecuritySQ.utils.themes;

import ru.FecuritySQ.utils.ColorUtil;

import java.awt.*;

public enum Themes {
    Bloody("Bloody",new Color(56, 0, 0), new Color(163, 0, 0)),
    Toxic("Toxic",new Color(0, 196, 255), new Color(0, 255, 150)),
    Darkness("Darkness",new Color(193, 101, 221), new Color(92, 39, 254)),
    Twillight("Twillight",new Color(190, 177, 232), new Color(132, 79, 175, 255)),
    Harway("Harway",new Color(127, 222, 164), new Color(19, 18, 19, 255)),
    Persoo("Persoo",new Color(189, 30, 81),new Color(241, 184, 20)),
    Weigh("Weigh",new Color(0, 171, 225),new Color(22, 31, 109)),
    Simpatico("Simpatico",new Color(157, 170, 242),new Color(255, 106, 61)),
    Lewerentz("Lewerentz",new Color(160, 174, 205),new Color(0, 0, 0)),
    Festival("Festival",new Color(171, 246, 45),new Color(214, 163, 251)),
    Starnight("Starnight",new Color(255, 255, 0, 255), new Color(70, 130, 180, 255));


    public final Color color;
    public final Color color2;
    public final String name;

    Themes(String name,Color color, Color color2) {
        this.name = name;
        this.color = color;
        this.color2 = color2;
    }

    public int getColor() {
        return ColorUtil.TwoColorEffect(color, color2, Math.abs(System.currentTimeMillis() / 10L) / 100.0 + 6.0F / 60).getRGB();
    }

    public int getColor2() {
        return ColorUtil.TwoColorEffect(color2, color, Math.abs(System.currentTimeMillis() / 10L) / 100.0 + 6.0F / 60).getRGB();
    }
    public Color[] getColors(){
        return new Color[]{color,color2};
    }
    public int getColor(float offset) {
        return ColorUtil.TwoColorEffect(color, color2, (Math.abs(System.currentTimeMillis() / 10L) / 100.0 + 6.0F * (offset * 0.1) / 60)).getRGB();
    }

    public int getColor2(float offset) {
        return ColorUtil.TwoColorEffect(color2, color, (Math.abs(System.currentTimeMillis() / 10L) / 100.0 + 6.0F * (offset * 0.1) / 60)).getRGB();
    }
    public int getColor(float offset, float speed) {
        double sp = ((double) Math.abs(System.currentTimeMillis() / 4L) / 10.1275 * speed + offset) / 100.0;
        return ColorUtil.TwoColorEffect(color, color2, sp).getRGB();
    }

    public int getColor2(float offset, float speed) {
        double sp = ((double) Math.abs(System.currentTimeMillis() / 4L) / 10.1275 * speed + offset) / 100.0;
        return ColorUtil.TwoColorEffect(color2, color, sp).getRGB();
    }



}