/*
 * This file is part of Clientbase - https://github.com/DietrichPaul/Clientbase
 * by DietrichPaul, FlorianMichael and contributors
 *
 * To the extent possible under law, the person who associated CC0 with
 * Clientbase has waived all copyright and related or neighboring rights
 * to Clientbase.
 *
 * You should have received a copy of the CC0 legalcode along with this
 * work.  If not, see <http://creativecommons.org/publicdomain/zero/1.0/>.
 */
package de.dietrichpaul.clientbase.util.render;

import org.lwjgl.glfw.GLFW;

public class ColorUtil {

    public static int getRainbow(float offset, float saturation, float brightness) {
        double timing = GLFW.glfwGetTime() * (50.0/360) + offset / 360.0F;
        timing = Math.abs(timing) % 1.0F;
        return java.awt.Color.HSBtoRGB((float) timing, saturation, brightness);
    }

}
