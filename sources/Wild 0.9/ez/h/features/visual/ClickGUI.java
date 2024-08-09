package ez.h.features.visual;

import ez.h.features.*;
import java.awt.*;
import ez.h.ui.clickgui.options.*;
import java.io.*;
import ez.h.ui.flatclickgui.*;
import ez.h.ui.clickgui.*;

public class ClickGUI extends Feature
{
    public static OptionSlider blurStrength;
    OptionMode clickGUI;
    public static OptionBoolean glow;
    public static OptionSlider glowStrength;
    public static OptionColor easingColor;
    public static OptionBoolean vignette;
    public static OptionBoolean snow;
    public static OptionColor vignetteColor;
    public static OptionMode animetyan;
    public static OptionBoolean desaturate;
    public static OptionBoolean icons;
    public static OptionBoolean blur;
    public static OptionBoolean descriptions;
    public static OptionColor clickGUIColor;
    public static OptionMode font;
    public static OptionBoolean easing;
    
    public ClickGUI() throws IOException {
        super("ClickGUI", "\u0418\u043d\u0442\u0435\u0440\u0444\u0435\u0439\u0441 \u043d\u0430\u0441\u0442\u0440\u043e\u0435\u043a", Category.VISUAL);
        this.clickGUI = new OptionMode(this, "ClickGUI", "Glass", new String[] { "Glass", "Flat" }, 0);
        ClickGUI.snow = new OptionBoolean(this, "Snow", true);
        ClickGUI.blur = new OptionBoolean(this, "Blur", true);
        ClickGUI.blurStrength = new OptionSlider(this, "Blur Strength", 4.0f, 1.0f, 20.0f, OptionSlider.SliderType.NULLINT);
        ClickGUI.glow = new OptionBoolean(this, "Glow", true);
        ClickGUI.glowStrength = new OptionSlider(this, "Glow Strength", 0.0f, 0.0f, 10.0f, OptionSlider.SliderType.NULLINT);
        ClickGUI.desaturate = new OptionBoolean(this, "Desaturate", true);
        ClickGUI.icons = new OptionBoolean(this, "Icons", true);
        ClickGUI.vignette = new OptionBoolean(this, "Vignette", true);
        ClickGUI.vignetteColor = new OptionColor(this, "Vignette Color", new Color(0x6B ^ 0x48, 18 + 27 + 84 + 22, 145 + 83 - 184 + 145), true);
        ClickGUI.vignetteColor.alpha = 27 + 25 + 33 + 42;
        ClickGUI.clickGUIColor = new OptionColor(this, "ClickGUI Color", new Color(855241 + 1289478 - 1584541 + 1772427), true);
        ClickGUI.animetyan = new OptionMode(this, "Anime Tyan", "ZeroTwo", new String[] { "ZeroTwo", "Siesta", "Tsundere", "None" }, 0);
        ClickGUI.descriptions = new OptionBoolean(this, "Descriptions", false);
        ClickGUI.easing = new OptionBoolean(this, "Easing", true);
        ClickGUI.easingColor = new OptionColor(this, "Easing Color", new Color(99 + 39 - 13 + 75, 194 + 106 - 224 + 124, 0x9A ^ 0x8E));
        final String s = "Font";
        final String s2 = "Manrope";
        final String[] array = new String[0x98 ^ 0x92];
        array[0] = "Manrope";
        array[1] = "EuclidFlex";
        array[2] = "Monaqi";
        array[3] = "Mont Blanc";
        array[4] = "Acrom";
        array[5] = "SimplySans";
        array[6] = "Momcake";
        array[7] = "Quicksand";
        array[8] = "Jelly Anica";
        array[9] = "Rany";
        ClickGUI.font = new OptionMode(this, s, s2, array, 0);
        final Option[] array2 = new Option[0x54 ^ 0x44];
        array2[0] = this.clickGUI;
        array2[1] = ClickGUI.font;
        array2[2] = ClickGUI.snow;
        array2[3] = ClickGUI.blur;
        array2[4] = ClickGUI.blurStrength;
        array2[5] = ClickGUI.desaturate;
        array2[6] = ClickGUI.vignette;
        array2[7] = ClickGUI.vignetteColor;
        array2[8] = ClickGUI.glow;
        array2[9] = ClickGUI.glowStrength;
        array2[0xB0 ^ 0xBA] = ClickGUI.clickGUIColor;
        array2[0x6E ^ 0x65] = ClickGUI.icons;
        array2[0x15 ^ 0x19] = ClickGUI.descriptions;
        array2[0x23 ^ 0x2E] = ClickGUI.animetyan;
        array2[0x47 ^ 0x49] = ClickGUI.easing;
        array2[0x8E ^ 0x81] = ClickGUI.easingColor;
        this.addOptions(array2);
        this.setKey(0x72 ^ 0x44);
    }
    
    @Override
    public void onEnable() {
        if (this.clickGUI.isMode("Flat")) {
            if (!(ClickGUI.mc.m instanceof FlatGuiScreen)) {
                ClickGUI.mc.a((blk)new FlatGuiScreen());
            }
        }
        else if (this.clickGUI.isMode("Glass") && !(ClickGUI.mc.m instanceof ClickGuiScreen)) {
            ClickGUI.mc.a((blk)new ClickGuiScreen());
        }
        this.toggle();
        super.onEnable();
    }
    
    @Override
    public void updateElements() {
        ClickGUI.blurStrength.display = ClickGUI.blur.enabled;
        ClickGUI.snow.display = this.clickGUI.isMode("Glass");
        ClickGUI.glow.display = this.clickGUI.isMode("Glass");
        ClickGUI.glowStrength.display = (this.clickGUI.isMode("Glass") && ClickGUI.glow.enabled);
        ClickGUI.icons.display = this.clickGUI.isMode("Glass");
        ClickGUI.vignette.display = ClickGUI.blur.enabled;
        ClickGUI.vignetteColor.display = (ClickGUI.vignette.enabled && ClickGUI.blur.enabled);
        ClickGUI.animetyan.display = this.clickGUI.isMode("Glass");
        ClickGUI.font.display = this.clickGUI.isMode("Glass");
        ClickGUI.easing.display = this.clickGUI.isMode("Glass");
        ClickGUI.easingColor.display = (this.clickGUI.isMode("Glass") && ClickGUI.easing.enabled);
    }
}
