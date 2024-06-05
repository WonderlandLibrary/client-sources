package alphentus.utils;

import org.lwjgl.opengl.GL11;

import java.awt.*;

/**
 * @author avox | lmao
 * @since on 05/08/2020.
 */
public class Test {

    public static void draw (float x, float y, float width, float height) {
        GL11.glPushMatrix();
        int redraw = 5;


        float posX = x;
        float posY = y;

        float destAlpha = 0.1F;


        for (int i = 0; i < redraw; ++i) {

            float frac = i / (float) redraw;

            float invFrac = 1.0F - frac;

            float offsetX = invFrac * 3;

            float offsetY = invFrac * 2;

            float currX = posX - offsetX;

            float currY = posY - offsetY;

            float currW = width + 2 * offsetX;

            float currH = height + 2 * offsetY;

            float currAlpha = destAlpha * frac;

            RenderUtils.relativeRect(currX + offsetX - 0.01F, currY + offsetY , currW + 0.1F, currH + offsetY, new Color(0, 0, 0, currAlpha).getRGB());
        }

        GL11.glPopMatrix();
    }
}


