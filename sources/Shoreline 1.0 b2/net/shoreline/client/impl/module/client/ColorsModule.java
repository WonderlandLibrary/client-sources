package net.shoreline.client.impl.module.client;

import net.shoreline.client.api.config.Config;
import net.shoreline.client.api.config.setting.ColorConfig;
import net.shoreline.client.api.config.setting.EnumConfig;
import net.shoreline.client.api.config.setting.NumberConfig;
import net.shoreline.client.api.module.ConcurrentModule;
import net.shoreline.client.api.module.ModuleCategory;

import java.awt.*;

/**
 *
 *
 * @author linus
 * @since 1.0
 */
public class ColorsModule extends ConcurrentModule
{
    //
    Config<Color> colorConfig = new ColorConfig("Color", "The Global" +
            " client color used in rendering", new Color(0x8149c1));
    // Rainbow settings
    Config<RainbowMode> rainbowModeConfig = new EnumConfig<>("Rainbow", "The " +
            "rendering mode for rainbow", RainbowMode.OFF, RainbowMode.values());
    Config<Float> rainbowSpeedConfig = new NumberConfig<>("Rainbow-Speed",
            "The speed for the rainbow color cycling", 0.1f, 50.0f, 100.0f);
    Config<Float> rainbowSaturationConfig = new NumberConfig<>("Rainbow-Saturation",
            "The saturation of rainbow colors", 0.01f, 0.35f, 1.0f);
    Config<Float> rainbowBrightnessConfig = new NumberConfig<>("Rainbow-Brightness",
            "The brightness of rainbow colors", 0.01f, 1.0f, 1.0f);
    Config<Float> rainbowDifferenceConfig = new NumberConfig<>("Rainbow-Difference",
            "The difference offset for rainbow colors", 0.1f, 40.0f, 100.0f);
    //
    private int rainbowOffset;

    /**
     *
     */
    public ColorsModule()
    {
        super("Colors", "Client color scheme", ModuleCategory.CLIENT);
    }

    /**
     *
     * @return
     */
    public Color getColor()
    {
        return switch (rainbowModeConfig.getValue())
                {
                    case OFF -> colorConfig.getValue();
                    case GRADIENT -> rainbow(rainbowOffset);
                    case STATIC -> rainbow(1L);
                };
    }

    /**
     *
     * @param a The alpha mask
     * @return
     *
     * @see #getColor()
     */
    public Color getColor(int a)
    {
        return switch (rainbowModeConfig.getValue())
                {
                    case OFF ->
                    {
                        int r = ((ColorConfig) colorConfig).getRed();
                        int g = ((ColorConfig) colorConfig).getGreen();
                        int b = ((ColorConfig) colorConfig).getBlue();
                        yield new Color(r, g, b, a);
                    }
                    case GRADIENT -> rainbow(rainbowOffset, a);
                    case STATIC -> rainbow(1L, a);
                };
    }

    /**
     *
     * @return
     */
    public Integer getRGB()
    {
        return getColor().getRGB();
    }

    /**
     *
     * @param a The alpha mask
     * @return
     */
    public int getRGB(int a)
    {
        return getColor(a).getRGB();
    }

    public int getAlpha()
    {
        return colorConfig.getValue().getAlpha();
    }

    public void setRainbowOffset()
    {
        rainbowOffset++;
    }

    public void resetRainbowOffset()
    {
        rainbowOffset = 0;
    }

    private Color rainbow(long offset)
    {
        return rainbow(offset, getAlpha());
    }

    private Color rainbow(long offset, int a)
    {
        float hue = (float) (((double) System.currentTimeMillis() * (rainbowSpeedConfig.getValue() / 10)
                + (double) (offset * 500L)) % (30000 / (rainbowDifferenceConfig.getValue() / 100))
                / (30000 / (rainbowDifferenceConfig.getValue() / 20.0f)));
        int rgb = Color.HSBtoRGB(hue, rainbowSaturationConfig.getValue(),
                rainbowBrightnessConfig.getValue());
        int red = rgb >> 16 & 255;
        int green = rgb >> 8 & 255;
        int blue = rgb & 255;
        return new Color(red, green, blue, a);
    }

    public enum RainbowMode
    {
        OFF,
        GRADIENT,
        STATIC
    }
}
