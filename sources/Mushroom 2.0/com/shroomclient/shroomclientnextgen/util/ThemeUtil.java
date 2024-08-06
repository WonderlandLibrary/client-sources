package com.shroomclient.shroomclientnextgen.util;

import com.shroomclient.shroomclientnextgen.modules.ModuleManager;
import com.shroomclient.shroomclientnextgen.modules.impl.client.Theme;
import java.awt.*;

public class ThemeUtil {

    private static final Color[][] colorsByAxioo = {
        new Color[] { new Color(0x2BFF72), new Color(0x4FFFFF) },
        new Color[] { new Color(0xFF0010), new Color(0xFF7F1F) },
        new Color[] { new Color(0xA30000), new Color(0xA30000) },
        new Color[] { new Color(0xFFD149), new Color(0xFF00D4) },
        new Color[] { new Color(0x7041DD), new Color(0x56DEFC) },
        new Color[] { new Color(0xC9DBD0), new Color(0x82F977) },
        new Color[] { new Color(0xD8000A), new Color(0xFFFFFF) },
        new Color[] { new Color(0xD28DD6), new Color(0xC3F4BA) },
        new Color[] { new Color(0x658AD3), new Color(0x6AD2F2) },
        new Color[] { new Color(0x9823D1), new Color(0xF50087) },
        new Color[] { new Color(0xAA94CE), new Color(0xF4BE92) },
        new Color[] { new Color(0x43ACCC), new Color(0xF23E47) },
        new Color[] { new Color(0xCC00FF), new Color(0x3BC5EF) },
        new Color[] { new Color(0xFFBAEC), new Color(0xC7D4ED) },
        new Color[] { new Color(0xFFBAEC), new Color(0xC49AEA) },
        new Color[] { new Color(0x84FFB9), new Color(0xA0B8E8) },
        new Color[] { new Color(0x515151), new Color(0xFFFFFF) },
        new Color[] { new Color(0xFF00BF), new Color(0xFFA0EF) },
        new Color[] { new Color(0xF1FF87), new Color(0xFF9D50) },
        new Color[] { new Color(0xFFFCFC), new Color(0xFFADC1) },
        new Color[] {
            new Color(0x5BCEFA),
            new Color(0xF5A9B8),
            new Color(0xFFFFFF),
        },
        new Color[] {
            new Color(228, 3, 3),
            new Color(255, 140, 0),
            new Color(255, 237, 0),
            new Color(0, 128, 38),
            new Color(36, 64, 142),
            new Color(115, 41, 130),
        },
        new Color[] {
            new Color(213, 45, 0),
            new Color(239, 118, 39),
            new Color(255, 154, 86),
            new Color(255, 255, 255),
            new Color(209, 98, 164),
            new Color(181, 86, 144),
            new Color(163, 2, 98),
        },
        new Color[] {
            new Color(7, 141, 112),
            new Color(38, 206, 170),
            new Color(152, 232, 193),
            new Color(255, 255, 255),
            new Color(123, 173, 226),
            new Color(80, 73, 204),
            new Color(61, 26, 120),
        },
        new Color[] {
            new Color(214, 2, 112),
            new Color(214, 2, 112),
            new Color(155, 79, 150),
            new Color(0, 56, 168),
            new Color(0, 56, 168),
        },
        new Color[] {
            new Color(0xa18276),
            new Color(0xb9d2b1),
            new Color(0xdac6b5),
            new Color(0xf1d6b8),
            new Color(0xfbacbe),
        },
        new Color[] {
            new Color(0xa4036f),
            new Color(0x048ba8),
            new Color(0x16db93),
            new Color(0xefea5a),
            new Color(0xf29e4c),
        },
        new Color[] {
            new Color(0xcfe8ef),
            new Color(0xc6dbf0),
            new Color(0xaed1e6),
            new Color(0xa0c4e2),
            new Color(0x85c7de),
        },
        new Color[] {
            new Color(0xAB0404),
            new Color(0x420423),
            new Color(0x65031E),
            new Color(0x770000),
            new Color(0xFF0000),
        },
        new Color[] {
            new Color(0xFF6666),
            new Color(0xFF2891),
            new Color(0x872BFC),
            new Color(0xD27EFF),
            new Color(0xFFBBBB),
        },
    };

    public static Color[] getGradient() {
        for (
            int i = 0;
            i <
            colorsByAxioo[ModuleManager.getModule(
                    Theme.class
                ).clientTheme.ordinal()].length;
            i++
        ) {}
        return colorsByAxioo[ModuleManager.getModule(
                Theme.class
            ).clientTheme.ordinal()];
    }

    public static Color[] themeColors(
        int y,
        int h,
        int fadecentre,
        float fadespeed
    ) {
        Color[] gradientColors = getGradient();

        return RenderUtil.getColorsFade(
            y,
            h,
            fadecentre,
            gradientColors,
            fadespeed
        );
    }

    public static Color[] themeColors() {
        Color[] gradientColors = getGradient();

        return RenderUtil.getColorsFade(0, 100, 100, gradientColors, 0.04f);
    }

    public static Color[] themeColors(int opacity) {
        Color[] gradientColors = getGradient();
        Color[] colors = RenderUtil.getColorsFade(
            0,
            100,
            100,
            gradientColors,
            0.04f
        );

        for (int i = 0; i < colors.length; i++) {
            colors[i] = new Color(
                colors[i].getRed(),
                colors[i].getGreen(),
                colors[i].getBlue(),
                opacity
            );
        }

        return colors;
    }

    public static Color[] themeColors(
        int y,
        int h,
        int fadecentre,
        float fadespeed,
        int opacity
    ) {
        Color[] gradientColors = getGradient();

        Color[] colors = RenderUtil.getColorsFade(
            y,
            h,
            fadecentre,
            gradientColors,
            fadespeed
        );

        for (int i = 0; i < colors.length; i++) {
            colors[i] = new Color(
                colors[i].getRed(),
                colors[i].getGreen(),
                colors[i].getBlue(),
                opacity
            );
        }

        return colors;
    }
}
