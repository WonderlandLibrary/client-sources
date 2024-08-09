package dev.excellent.impl.client.theme;

import dev.excellent.Excellent;
import dev.excellent.impl.util.math.Mathf;
import dev.excellent.impl.util.render.color.ColorUtil;
import lombok.Getter;

import java.awt.*;

@Getter
public enum Themes {
    RAINBOW("Rainbow", new Color(0), new Color(0)),
    ASTOLFO("Astolfo", new Color(0), new Color(0)),
    MANGO("Mango", new Color(0xFF9900), new Color(0x068600)),
    SPRING("Spring", new Color(0x47FF00), new Color(0x196714)),
    LOVE_KISS("Love Kiss", new Color(0xFF9FB0), new Color(0xFF0000)),
    DREAM_WORLD("Dream World", new Color(0xA356FF), new Color(0x22FF00)),
    MOONLIT_ASTEROID("Moonlit Asteroid", new Color(0x0030FF), new Color(0xFFFFFF)),
    FRUIT_BLEND("Fruit Blend", new Color(0xFF0000), new Color(0xFF8000)),
    BLUEBERRY("BlueBerry", new Color(0x7C00FF), new Color(0x36196B)),
    SOLUS("Solus", new Color(0xFF5900), new Color(0xFFFFFF)),
    KARI("Kari", new Color(0xA25828), new Color(0x00FFE2)),
    PEACE("Peace", new Color(0xFFD500), new Color(0x002AFF)),
    TRUE_SUNSET("True Sunset", new Color(0xFF5E00), new Color(0xFF00EA)),
    GLIDDER("Glidder", new Color(0xFF0000), new Color(0x5E00FF)),
    DARTS("Darts", new Color(0x000DFF), new Color(0x080808)),
    POLICE("Police", new Color(0xFF0000), new Color(0x0014FF)),
    WHITEBLACK("White & Black", new Color(0xAFAFAF), new Color(0x181818)),
    JELLY("Jelly", new Color(0xFFCB00), new Color(0x8400FF)),
    GOLDEN_PHOENIX("Gоlden Phoenix", new Color(0xFFDB00), new Color(0xFF5900)),
    WATERMELON("Watermelon", new Color(0x44FF00), new Color(0xFF0000)),
    SKIN("Skin", new Color(0xE8D9CD), new Color(0xC76464)),
    MELLO("Mello", new Color(0xFF009D), new Color(0x70425A)),
    ORCA("Orca", new Color(0x08203E), new Color(0x557C93)),
    HYPER("Hyper", new Color(0xFF0000), new Color(0xFFFFFF)),
    PREDAWN("Predawn", new Color(0x0968e5), new Color(0x091970)),
    HOLY("Holy", new Color(0x006FFF), new Color(0x00D9FF)),
    BURNING_BLUE("Burning Blue", new Color(0x1E3C72), new Color(0x2A5298)),
    LILAC("Lilac", new Color(0xCD00FF), new Color(0xFFFFFF)),
    VAMP_BLOOD("Vamp Blood", new Color(0xFF0000), new Color(0x1E0000));
    private final String name;
    private final Color first, second;
    private final int speed = 10;

    public Color getFirstColor() {
        return getClientColorC(0);
    }

    public Color getSecondColor() {
        return getClientColorC(180);
    }

    public Color getClientColor() {
        return getClientColorC(0);
    }

    public int getClientColor(int index, float alphaPC) {
        return ColorUtil.multAlpha(getClientColor(index), alphaPC);
    }

    public Color getClientColorC(int index, float alphaPC) {
        return ColorUtil.withAlpha(getClientColorC(index), (int) Mathf.clamp(0, 255, (alphaPC * 255F)));
    }

    public int getClientColor(int index) {
        Themes selected = Excellent.getInst().getThemeManager().getTheme();
        return switch (selected) {
            case RAINBOW -> ColorUtil.rainbow(speed, index, 1F, 1, 1);
            case ASTOLFO -> ColorUtil.skyRainbow(speed, index);
            default -> ColorUtil.lerp(speed, index, first, second).hashCode();
        };
    }

    public Color getClientColorC(int index) {
        Themes selected = Excellent.getInst().getThemeManager().getTheme();
        return switch (selected) {
            case RAINBOW -> ColorUtil.rainbowC(speed, index, 1F, 1, 1);
            case ASTOLFO -> ColorUtil.skyRainbowC(speed, index);
            default -> ColorUtil.lerp(speed, index, first, second);
        };
    }

    public float round() {
        return 2F;
    }

    public float shadow() {
        return 8F;
    }

    Themes(String name, Color first, Color second) {
        this.name = name;
        this.first = first;
        this.second = second;
    }
}