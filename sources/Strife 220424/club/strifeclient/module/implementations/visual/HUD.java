package club.strifeclient.module.implementations.visual;

import club.strifeclient.module.Category;
import club.strifeclient.module.Module;
import club.strifeclient.module.ModuleInfo;
import club.strifeclient.setting.SerializableEnum;
import club.strifeclient.setting.implementations.*;

import java.awt.*;

@ModuleInfo(name = "HUD", description = "Edit HUD settings.", category = Category.VISUAL)
public final class HUD extends Module {

    public final ModeSetting<ColorMode> colorModeSetting = new ModeSetting<>("Color Mode", ColorMode.STATIC);
    public final ColorSetting colorSetting = new ColorSetting("Color", new Color(209, 50, 50),
            () -> colorModeSetting.getValue() != ColorMode.RAINBOW || colorModeSetting.getValue() != ColorMode.ASTOLFO);
    public final ColorSetting secondColorSetting = new ColorSetting("Second Color", new Color(29, 205, 200),
            () -> colorModeSetting.getValue() == ColorMode.SWITCH);
    public final DoubleSetting colorSpeedSetting = new DoubleSetting("Color Speed", 8, 0.5, 10, 0.5,
            () -> colorModeSetting.getValue() != ColorMode.STATIC);
    public final ModeSetting<ToggleAnimation> animationSetting = new ModeSetting<>("Animation", ToggleAnimation.STRIFE);
    public final ModeSetting<SuffixMode> suffixModeSetting = new ModeSetting<>("Suffix", SuffixMode.SIMPLE);
    public final ModeSetting<Font> fontSetting = new ModeSetting<>("Font", Font.CIRCULAR);
    public final DoubleSetting fontSizeSetting = new DoubleSetting("Font Size", 19, 10, 40, 1, () -> fontSetting.getValue() != Font.VANILLA);
    public final DoubleSetting scaleSetting = new DoubleSetting("Font Scale", 1, 1,4, 0.1, () -> fontSetting.getValue() == Font.VANILLA);
    public final ModeSetting<Background> backgroundSetting = new ModeSetting<>("Background", Background.BAR_RIGHT);
    public final BooleanSetting lowercaseSetting = new BooleanSetting("Lowercase", false);
    public final DoubleSetting backgroundOpacitySetting = new DoubleSetting("Background Opacity", 180, 1, 255, 1,
            () -> backgroundSetting.getValue() != Background.NONE);
    public final MultiSelectSetting<Element> elementsSetting = new MultiSelectSetting<>("Elements", Element.FPS);

    public enum Font implements SerializableEnum {
        CLIENT("Client", "SF"), CIRCULAR("Circular", "Circular-B"), VANILLA("Vanilla", "");

        final String name;
        final String fontName;

        Font(final String name, final String fontName) {
            this.name = name;
            this.fontName = fontName;
        }

        @Override
        public String getName() {
            return name;
        }
        public String getFontName() {
            return fontName;
        }
    }
    public enum ColorMode implements SerializableEnum {
        ASTOLFO("Astolfo"), PULSE("Pulse"), RAINBOW("Rainbow"),
        STATIC("Static"), SWITCH("Switch"), STRIFE("Strife");
        final String name;
        ColorMode(final String name) {
            this.name = name;
        }
        @Override
        public String getName() {
            return name;
        }
    }
    public enum Background implements SerializableEnum {
        OUTLINE("Outline"), BAR_LEFT("Bar Left"), BAR_RIGHT("Bar Right"), NONE("None");
        final String name;
        Background(final String name) {
            this.name = name;
        }
        @Override
        public String getName() {
            return name;
        }
    }
    public enum ToggleAnimation implements SerializableEnum {
        STRIFE("Strife"), ASTOLFO("Astolfo");
        final String name;
        ToggleAnimation(final String name) {
            this.name = name;
        }
        @Override
        public String getName() {
            return name;
        }
    }
    public enum Element implements SerializableEnum {
        NAME("Name"), ARRAYLIST("Array List"), TIME("Time"), NOTIFICATIONS("Notifications"), FPS("FPS"), SPEED("Speed"),
        BALANCE("Balance"), COORDS("Coords"), POTIONSTATUS("Potion Status"), BOSSBAR("Boss Bar"), ARMORHUD("Armor HUD");
        final String name;
        Element(final String name) {
            this.name = name;
        }
        @Override
        public String getName() {
            return name;
        }
    }
    public enum LogoMode implements SerializableEnum {
        CSGO("CSGO"), TEXT("Text"), PICTURE("Picture");
        final String name;
        LogoMode(final String name) {
            this.name = name;
        }
        @Override
        public String getName() {
            return name;
        }
    }
    public enum SuffixMode implements SerializableEnum {
        NONE("None"), SIMPLE("Simple"), BRACKET("Bracket"), DASH("Dash");
        final String name;
        SuffixMode(final String name) {
            this.name = name;
        }
        @Override
        public String getName() {
            return name;
        }
    }
}
