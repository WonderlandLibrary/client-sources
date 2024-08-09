package xyz.mathax.mathaxclient.systems.hud.elements;

import xyz.mathax.mathaxclient.gui.renderer.OverlayRenderer;
import xyz.mathax.mathaxclient.renderer.Renderer2D;
import xyz.mathax.mathaxclient.settings.*;
import xyz.mathax.mathaxclient.systems.Systems;
import xyz.mathax.mathaxclient.systems.hud.Hud;
import xyz.mathax.mathaxclient.systems.hud.HudElement;
import xyz.mathax.mathaxclient.systems.modules.Module;
import xyz.mathax.mathaxclient.systems.modules.Modules;
import xyz.mathax.mathaxclient.systems.themes.Theme;
import xyz.mathax.mathaxclient.systems.themes.Themes;
import xyz.mathax.mathaxclient.utils.Utils;
import xyz.mathax.mathaxclient.utils.render.color.Color;
import xyz.mathax.mathaxclient.utils.render.color.SettingColor;

import java.util.ArrayList;
import java.util.List;

public class EnabledModulesHudElement extends HudElement {
    private final List<Module> modules = new ArrayList<>();

    private final Color rainbow = new Color(255, 255, 255);
    private double rainbowHue1, rainbowHue2;

    private double prevX;
    private double prevTextLength;
    private Color prevColor = new Color();

    private final SettingGroup generalSettings = settings.createGroup("General");
    private final SettingGroup colorsSettings = settings.createGroup("Colors");
    private final SettingGroup outlinesSettings = settings.createGroup("Outlines");

    // General

    private final Setting<List<Module>> hiddenModulesSetting = generalSettings.add(new ModuleListSetting.Builder()
        .name("Hidden modules")
        .description("Which modules not to show in the list.")
        .build()
    );

    private final Setting<Sort> sortSetting = generalSettings.add(new EnumSetting.Builder<Sort>()
        .name("Sort")
        .description("How to sort enabled modules.")
        .defaultValue(Sort.Shortest)
        .build()
    );

    private final Setting<Boolean> enabledInfoSetting = generalSettings.add(new BoolSetting.Builder()
        .name("Additional info")
        .description("Shows additional info from the module next to the name in the enabled modules list.")
        .defaultValue(true)
        .build()
    );
    
    // Colors

    private final Setting<ColorMode> colorModeSetting = colorsSettings.add(new EnumSetting.Builder<ColorMode>()
        .name("Color mode")
        .description("Color mode to use for enabled modules.")
        .defaultValue(ColorMode.Rainbow)
        .build()
    );

    private final Setting<SettingColor> flatColorSetting = colorsSettings.add(new ColorSetting.Builder()
        .name("Color")
        .description("The color.")
        .defaultValue(new SettingColor(225, 25, 25))
        .visible(() -> colorModeSetting.get() == ColorMode.Flat)
        .build()
    );

    private final Setting<Double> rainbowSpreadSetting = colorsSettings.add(new DoubleSetting.Builder()
            .name("Rainbow spread")
            .description("Spread of the rainbow.")
            .defaultValue(1)
            .min(0.001)
            .sliderRange(0.001, 2)
            .visible(() -> colorModeSetting.get() == ColorMode.Rainbow)
            .build()
    );

    // Outlines
    
    private final Setting<Boolean> outlinesSetting = outlinesSettings.add(new BoolSetting.Builder()
        .name("Outlines")
        .description("Whether or not to render outlines.")
        .defaultValue(false)
        .build()
    );

    private final Setting<Integer> outlineWidthSetting = outlinesSettings.add(new IntSetting.Builder()
        .name("Outline width")
        .description("Width of the outline.")
        .defaultValue(4)
        .min(1)
        .sliderRange(1, 5)
        .visible(outlinesSetting::get)
        .build()
    );

    public EnabledModulesHudElement(Hud hud) {
        super(hud, "Enabled Modules", "Displays your enabled modules.");
    }

    @Override
    public void update(OverlayRenderer renderer) {
        if (Modules.get() == null) {
            box.setSize(renderer.textWidth(name), renderer.textHeight());
            return;
        }

        modules.clear();

        for (Module module : Modules.get().getEnabled()) {
            if (!hiddenModulesSetting.get().contains(module)) {
                modules.add(module);
            }
        }

        modules.sort((o1, o2) -> {
            double _1 = getModuleWidth(renderer, o1);
            double _2 = getModuleWidth(renderer, o2);
            if (sortSetting.get() == Sort.Shortest) {
                double temp = _1;
                _1 = _2;
                _2 = temp;
            }

            int a = Double.compare(_1, _2);
            if (a == 0) {
                return 0;
            }

            return a < 0 ? 1 : -1;
        });

        double width = 0;
        double height = 0;
        for (int i = 0; i < modules.size(); i++) {
            Module module = modules.get(i);

            width = Math.max(width, getModuleWidth(renderer, module));
            height += renderer.textHeight();
            if (i > 0) {
                height += 2;
            }
        }

        box.setSize(width, height);
    }

    @Override
    public void render(OverlayRenderer renderer) {
        double x = box.getX();
        double y = box.getY();

        if (Modules.get() == null) {
            renderer.text(name, x, y, hud.primaryColorSetting.get());
            return;
        }

        Theme theme = Systems.get(Themes.class).getTheme();
        rainbowHue1 += (theme.rainbowSpeed() / 1000) * renderer.delta;
        if (rainbowHue1 > 1) {
            rainbowHue1 -= 1;
        } else if (rainbowHue1 < -1) {
            rainbowHue1 += 1;
        }

        rainbowHue2 = rainbowHue1;

        prevX = x;

        Renderer2D.COLOR.begin();

        for (int i = 0; i < modules.size(); i++) {
            renderModule(renderer, modules, i, x + box.alignX(getModuleWidth(renderer, modules.get(i))), y);

            prevX = x + box.alignX(getModuleWidth(renderer, modules.get(i)));

            y += 2 + renderer.textHeight();
        }

        Renderer2D.COLOR.render(null);
    }

    private void renderModule(OverlayRenderer renderer, List<Module> modules, int index, double x, double y) {
        Theme theme = Systems.get(Themes.class).getTheme();
        Module module = modules.get(index);
        Color color = flatColorSetting.get();
        ColorMode colorMode = colorModeSetting.get();
        if (colorMode == ColorMode.Random) {
            color = Color.fromHsv(Utils.random(0.0, 360.0), 0.35, 1);
        } else if (colorMode == ColorMode.Rainbow) {
            rainbowHue2 += rainbowSpreadSetting.get() / 100;
            int color1 = java.awt.Color.HSBtoRGB((float) rainbowHue2, (float) theme.rainbowSaturation(), (float) theme.rainbowBrightness());

            rainbow.r = Color.toRGBAR(color1);
            rainbow.g = Color.toRGBAG(color1);
            rainbow.b = Color.toRGBAB(color1);

            color = rainbow;
        }

        renderer.text(module.name, x, y, color);

        double textLength = renderer.textWidth(module.name);
        if (enabledInfoSetting.get()) {
            String info = module.getInfoString();
            if (info != null) {
                renderer.text(info, x + renderer.textWidth(module.name) + renderer.textWidth(" "), y, hud.secondaryColorSetting.get());
                textLength += renderer.textWidth(" ") + renderer.textWidth(info);
            }
        }

        if (outlinesSetting.get()) {
            int outlineWidth = outlineWidthSetting.get();
            if (index == 0) {
                Renderer2D.COLOR.quad(x - 2 - outlineWidth, y - 2, outlineWidth, renderer.textHeight() + 4, prevColor, prevColor, color, color); // Left quad
                Renderer2D.COLOR.quad(x + textLength + 2, y - 2, outlineWidth, renderer.textHeight() + 4, prevColor, prevColor, color, color); // Right quad
                Renderer2D.COLOR.quad(x - 2 - outlineWidth, y - 2 - outlineWidth, textLength + 4 + (outlineWidth * 2), outlineWidth, prevColor, prevColor, color, color); // Top quad
            } else if (index == modules.size() - 1) {
                Renderer2D.COLOR.quad(x - 2 - outlineWidth, y, outlineWidth, renderer.textHeight() + 2 + outlineWidth, prevColor, prevColor, color, color); // Left quad
                Renderer2D.COLOR.quad(x + textLength + 2, y, outlineWidth, renderer.textHeight() + 2 + outlineWidth, prevColor, prevColor, color, color); // Right quad
                Renderer2D.COLOR.quad(x - 2 - outlineWidth, y + renderer.textHeight() + 2, textLength + 4 + (outlineWidth * 2), outlineWidth, prevColor, prevColor, color, color); // Bottom quad
            }

            if (index > 0) {
                if (index < modules.size() - 1) {
                    Renderer2D.COLOR.quad(x - 2 - outlineWidth, y, outlineWidth, renderer.textHeight() + 2, prevColor, prevColor, color, color); // Left quad
                    Renderer2D.COLOR.quad(x + textLength + 2, y, outlineWidth, renderer.textHeight() + 2, prevColor, prevColor, color, color); // Right quad
                }

                Renderer2D.COLOR.quad(Math.min(prevX, x) - 2 - outlineWidth, Math.max(prevX, x) == x ? y : y - outlineWidth, (Math.max(prevX, x) - 2) - (Math.min(prevX, x) - 2 - outlineWidth), outlineWidth, prevColor, prevColor, color, color); // Left inbetween quad
                Renderer2D.COLOR.quad(Math.min(prevX + prevTextLength, x + textLength) + 2, Math.min(prevX + prevTextLength, x + textLength) == x + textLength ? y : y - outlineWidth, (Math.max(prevX + prevTextLength, x + textLength) + 2 + outlineWidth) - (Math.min(prevX + prevTextLength, x + textLength) + 2), outlineWidth, prevColor, prevColor, color, color); // Right inbetween quad
            }
        }

        prevTextLength = textLength;
        prevColor = color;
    }

    private double getModuleWidth(OverlayRenderer renderer, Module module) {
        double width = renderer.textWidth(module.name);
        if (enabledInfoSetting.get()) {
            String info = module.getInfoString();
            if (info != null) {
                width += renderer.textWidth(" ") + renderer.textWidth(info);
            }
        }

        return width;
    }

    public enum Sort {
        Longest("Longest"),
        Shortest("Shortest");

        private final String name;

        Sort(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    public enum ColorMode {
        Flat("Flat"),
        Category("Category"),
        Random("Random"),
        Rainbow("Rainbow");

        private final String name;

        ColorMode(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }
}
