package club.strifeclient.util.rendering;

import club.strifeclient.Client;
import club.strifeclient.module.implementations.visual.HUD;
import club.strifeclient.setting.SerializableEnum;
import club.strifeclient.setting.implementations.ColorSetting;
import club.strifeclient.setting.implementations.DoubleSetting;
import club.strifeclient.setting.implementations.ModeSetting;
import net.minecraft.entity.EntityLivingBase;

import java.awt.*;

import static org.lwjgl.opengl.GL11.glColor4f;

public final class ColorUtil {

    public enum ColorMode implements SerializableEnum {
        ASTOLFO("Astolfo"), PULSE("Pulse"), RAINBOW("Rainbow"), STATIC("Static"),
        SWITCH("Switch"), STRIFE("Strife"), SYNC("Sync");
        final String name;
        ColorMode(final String name) {
            this.name = name;
        }
        @Override
        public String getName() {
            return name;
        }
    }

    private static HUD hud;

    public static Color getHUDColor(int index) {
        if (hud == null) {
            hud = Client.INSTANCE.getModuleManager().getModule(HUD.class);
        }
        final float speed = (hud.colorSpeedSetting.getValue() == hud.colorSpeedSetting.getMax() ? 1000 :
                ((int)hud.colorSpeedSetting.getMax() - hud.colorSpeedSetting.getInt())) * 1000;
        switch (hud.colorModeSetting.getValue()) {
            case PULSE: return ColorUtil.getPulse(hud.colorSetting.getValue(), speed, index);
            case ASTOLFO: return ColorUtil.getAstolfo(speed, index, 0.5f);
            case RAINBOW: return ColorUtil.getRainbow(speed, index, 0.5f);
            case SWITCH: return ColorUtil.getFadingColor(hud.secondColorSetting.getValue(), hud.colorSetting.getValue(), speed, index);
            case STRIFE: return ColorUtil.getStrife(hud.colorSetting.getValue(), speed, index);
            default: return hud.colorSetting.getValue();
        }
    }

    public static <Type extends ModeSetting<ColorMode>> Color getColor(Type colorModeProperty, ColorSetting colorOne,
                                                                       ColorSetting colorTwo, DoubleSetting colorSpeed, int index) {
        final float speed = (colorSpeed.getValue() == colorSpeed.getMax() ? 1 : ((int)colorSpeed.getMax() - colorSpeed.getInt())) * 1000;
        switch (colorModeProperty.getValue()) {
            case PULSE: return ColorUtil.getPulse(colorOne.getValue(), speed, index);
            case ASTOLFO: return ColorUtil.getAstolfo(speed, index, 0.5f);
            case RAINBOW: return ColorUtil.getRainbow(speed, index, 0.5f);
            case SWITCH: return ColorUtil.getFadingColor(colorTwo.getValue(), colorOne.getValue(), speed, index);
            case STRIFE: return ColorUtil.getStrife(colorOne.getValue(), speed, index);
            case SYNC: return getHUDColor(index);
        }
        return colorOne.getValue();
    }

    // https://www.alanzucconi.com/2016/01/06/colour-interpolation/
    public static Color interpolateColor(final Color colorIn, final Color colorOut, final float t) {
        return new Color(
            Math.min(255, (int)InterpolateUtil.interpolateFloat(colorIn.getRed(), colorOut.getRed(), t)),
            Math.min(255, (int)InterpolateUtil.interpolateFloat(colorIn.getGreen(), colorOut.getGreen(), t)),
            Math.min(255, (int)InterpolateUtil.interpolateFloat(colorIn.getBlue(), colorOut.getBlue(), t)),
            Math.min(255, (int)InterpolateUtil.interpolateFloat(colorIn.getAlpha(), colorOut.getAlpha(), t))
        );
    }

    public static Color getPulse(final Color color, final float speed, final double offset) {
        final float[] hsb = Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), null);
        float brightness = (float) (((System.currentTimeMillis() + offset) % speed) / speed);
        if (brightness > 0.5)
            brightness = 0.5F - (brightness - 0.5F);
        brightness += 0.5F;
        return new Color(Color.HSBtoRGB(hsb[0], hsb[1], brightness % 2));
    }

    public static Color getRainbow(final float speed, final double offset, final float saturation) {
        final float hue = (float) (((System.currentTimeMillis() + offset) % speed) / speed);
        return new Color(Color.HSBtoRGB(hue, saturation, 1));
    }

    public static Color getFadingColor(final Color colorIn, final Color colorOut, final float speed, final double offset) {
        double function = (((System.currentTimeMillis() + offset) % speed) / speed);
        // doubling the coordinate plane
        function *= 2;
        // centering the plane
        function -= 1;
        // reversing the curve
        function = (float) (-1 * Math.sqrt(function * function) + 1);
        // min + max the final parabolic function
        function = Math.max(0, Math.min(1, function));
        return interpolateColor(colorIn, colorOut, (float)function);
    }

    public static Color getAstolfo(final float speed, final double offset, final float saturation) {
        float hue = (float) (((System.currentTimeMillis() + offset) % speed) / speed);
        if (hue > 0.5)
            hue = 0.5F - (hue - 0.5F);
        hue += 0.5F;
        return Color.getHSBColor(hue, saturation, 1);
    }

    public static Color getStrife(final Color color, final float speed, final double offset) {
        float t = (float) (((System.currentTimeMillis() + offset) % speed) / speed);
        if (t > 0.5)
            t = 0.5F - (t - 0.5F);
        t += 0.5F;
        final float[] hsb = Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), null);
        return Color.getHSBColor(hsb[0], t % 2, t);
    }

    public static Color getColorFromHealth(final EntityLivingBase entity) {
        return interpolateColor(Color.RED, Color.GREEN, (entity.getHealth() / entity.getMaxHealth()));
    }

    public static Color withAlpha(final Color color, final int alpha) {
        return new Color(color.getRed(), color.getGreen(), color.getBlue(), alpha);
    }

    public static float[] toGLColor(final int color) {
        final float f = (float) (color >> 16 & 255) / 255.0F;
        final float f1 = (float) (color >> 8 & 255) / 255.0F;
        final float f2 = (float) (color & 255) / 255.0F;
        final float f3 = (float) (color >> 24 & 255) / 255.0F;
        return new float[]{f, f1, f2, f3};
    }

    public static float[] toGLColor(final Color color) {
        return toGLColor(color.getRGB());
    }

    public static float[] toGLColor(final Color color, final int alpha) {
        return toGLColor(withAlpha(color, alpha));
    }

    public static Color toColorRGB(final int rgb, final float alpha) {
        final float[] rgba = toGLColor(rgb);
        return new Color(rgba[0], rgba[1], rgba[2], alpha / 255f);
    }

    public static void doColor(final int color, final float alpha) {
        final float[] rgba = toGLColor(color);
        glColor4f(rgba[0], rgba[1], rgba[2], alpha);
    }

    public static void doColor(final int color) {
        final float[] rgba = toGLColor(color);
        glColor4f(rgba[0], rgba[1], rgba[2], rgba[3]);
    }
}
