package Hydro.util;


import org.lwjgl.opengl.GL11;

import java.awt.*;

/**
 * @author: AmirCC
 * 01:36 pm, 11/10/2020, Wednesday
 **/
public enum ColorUtil {
    INSTANCE;

    public static void glColor(Color color) {
        GL11.glColor4f((float) color.getRed() / 255.0f, (float) color.getGreen() / 255.0f, (float) color.getBlue() / 255.0f, (float) color.getAlpha() / 255.0f);
    }

    public static void glColor(int hex) {
        float alpha = (float) (hex >> 24 & 255) / 255.0f;
        float red = (float) (hex >> 16 & 255) / 255.0f;
        float green = (float) (hex >> 8 & 255) / 255.0f;
        float blue = (float) (hex & 255) / 255.0f;
        GL11.glColor4f(red, green, blue, alpha);
    }


    public enum Colors {

        NONE(new Color(255, 255, 255, 0).getRGB()),
        TRANSPARENT(new Color(0, 0, 0, 0).getRGB()),
        WHITE(new Color(255, 255, 255, 255).getRGB()),
        CLOUD(new Color(236, 240, 241, 255).getRGB()),
        DARK_GRAY(new Color(44, 44, 44, 255).getRGB()),
        GREEN(new Color(39, 174, 96, 255).getRGB()),
        TURQUOISE(new Color(22, 160, 133).getRGB()),
        BLUE(new Color(3, 152, 252, 255).getRGB()),
        GOLD(new Color(255, 215, 0, 255).getRGB()),
        RED(new Color(231, 76, 60, 255).getRGB());

        int rgb;
        Colors(int rgb){
            this.rgb = rgb;
        }

        public int getRGB() {
            return rgb;
        }
    }


    public static Color fade(long offset, float fade) {
        float hue = (float) (System.nanoTime() + offset) / 1.0E10F % 1.0F;
        long color = Long.parseLong(Integer.toHexString(Integer.valueOf(Color.HSBtoRGB(hue, 1.0F, 1.0F)).intValue()),
                16);
        Color c = new Color((int) color);
        return new Color(c.getRed() / 255.0F * fade, c.getGreen() / 255.0F * fade, c.getBlue() / 255.0F * fade,
                c.getAlpha() / 155.0F);
    }

    public static int getRainbow(int speed, int offset) {
        float hue = (float) ((System.currentTimeMillis() * 1 + offset / 2) % speed * 2);
        hue /= speed;
        float saturation = 1;
        return Color.getHSBColor(hue, saturation, 1.0F).getRGB();
    }
    
    public static int HSVToColor(int alpha, float[] hsv) {
        /*
         * Copyright (C) 2006 The Android Open Source Project
         *
         * Licensed under the Apache License, Version 2.0 (the "License");
         * you may not use this file except in compliance with the License.
         * You may obtain a copy of the License at
         *
         *      http://www.apache.org/licenses/LICENSE-2.0
         *
         * Unless required by applicable law or agreed to in writing, software
         * distributed under the License is distributed on an "AS IS" BASIS,
         * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
         * See the License for the specific language governing permissions and
         * limitations under the License.
         */
        float h = hsv[0];
        float s = hsv[1];
        float b = hsv[2];

        if (h < 0) {
            h = 0;
        }

        if (s < 0) {
            s = 0;
        }

        if (b < 0) {
            b = 0;
        }

        if (h > 1) {
            h = 1;
        }

        if (s > 1) {
            s = 1;
        }

        if (b > 1) {
            b = 1;
        }

        float red = 0.0f;
        float green = 0.0f;
        float blue = 0.0f;
        final float hf = (h - (int) h) * 6.0f;
        final int ihf = (int) hf;
        final float f = hf - ihf;
        final float pv = b * (1.0f - s);
        final float qv = b * (1.0f - s * f);
        final float tv = b * (1.0f - s * (1.0f - f));

        switch (ihf) {
        case 0: // Red is the dominant color
            red = b;
            green = tv;
            blue = pv;

            break;

        case 1: // Green is the dominant color
            red = qv;
            green = b;
            blue = pv;

            break;

        case 2:
            red = pv;
            green = b;
            blue = tv;

            break;

        case 3: // Blue is the dominant color
            red = pv;
            green = qv;
            blue = b;

            break;

        case 4:
            red = tv;
            green = pv;
            blue = b;

            break;

        case 5: // Red is the dominant color
            red = b;
            green = pv;
            blue = qv;

            break;
        }

        return ((alpha & 0xff) << 24) | (((int) (red * 255.0f)) << 16) | (((int) (green * 255.0f)) << 8)
                | ((int) (blue * 255.0f));
    }

}
