package ez.h.ui;

import java.awt.*;
import ez.h.features.*;
import ez.h.ui.fonts.*;
import ez.h.ui.clickgui.options.*;
import org.lwjgl.opengl.*;
import ez.h.features.movement.*;
import ez.h.event.*;
import java.util.stream.*;
import ez.h.*;
import java.text.*;
import java.util.*;
import ez.h.utils.*;
import ez.h.event.events.*;

public class HUD extends Feature
{
    OptionBoolean shadow;
    OptionSlider fadeSpeed;
    OptionColor arraylistColor;
    OptionMode fontStyle;
    public OptionBoolean timerInfo;
    OptionMode watermarkMode;
    OptionColor astolfoFirst;
    public OptionBoolean customChat;
    public OptionBoolean customInventory;
    OptionBoolean watermark;
    OptionBoolean arrayList;
    public static int packetsCounter;
    public static Color color;
    CFontRenderer fr;
    public OptionBoolean hotbar;
    OptionSlider shadowRange;
    OptionMode arraylistMode;
    OptionColor backGround;
    public OptionBoolean customPotions;
    public static Map<Feature, Float> anims;
    OptionColor rectColor;
    public static OptionMode suffixColor;
    OptionColor shadowColor;
    OptionColor astolfoSecond;
    
    public HUD() {
        super("HUD", "Renders overlay on the screen", Category.VISUAL);
        this.customChat = new OptionBoolean(this, "Custom Chat", true);
        this.hotbar = new OptionBoolean(this, "Custom HotBar", true);
        this.customInventory = new OptionBoolean(this, "Custom Inventory", true);
        this.customPotions = new OptionBoolean(this, "Custom Potions", true);
        this.watermark = new OptionBoolean(this, "Watermark", true);
        this.arrayList = new OptionBoolean(this, "Arraylist", true);
        this.timerInfo = new OptionBoolean(this, "Smart Timer", true);
        this.watermarkMode = new OptionMode(this, "Watermark Mode", "Modern", new String[] { "Modern", "Rise", "Gamesense", "NL", "skeet.cc", "Stylo" }, 0);
        this.rectColor = new OptionColor(this, "Rect Color", new Color(5680528 + 3278460 - 2073861 + 9826553));
        this.arraylistMode = new OptionMode(this, "Arraylist Mode", "Color", new String[] { "Color", "Aquafresh", "Astolfo", "Rainbow", "Rainbow2", "Rainbow3" }, 0);
        this.fontStyle = new OptionMode(this, "Font Style", "EuclidFlex", new String[] { "EuclidFlex", "Montserrat", "OpenSans", "Comfortaa" }, 0);
        this.backGround = new OptionColor(this, "Arraylist Back", new Color(185885 + 1317104 - 884784 + 1092413), true);
        this.arraylistColor = new OptionColor(this, "Arraylist Color", new Color(-1));
        this.astolfoFirst = new OptionColor(this, "Astolfo First", new Color(109035 + 121062 - 126543 + 39895));
        this.astolfoSecond = new OptionColor(this, "Astolfo Second", new Color(16525 + 40185 - 42567 + 30899));
        this.fadeSpeed = new OptionSlider(this, "Fade Speed", 1.0f, 0.0f, 1.0f, OptionSlider.SliderType.NULL);
        this.shadow = new OptionBoolean(this, "Shadow", true);
        this.shadowColor = new OptionColor(this, "Shadow Color", new Color(0), true);
        this.shadowRange = new OptionSlider(this, "Shadow Range", 5.0f, 2.0f, 10.0f, OptionSlider.SliderType.NULLINT);
        this.fr = CFontManager.manropesmall;
        HUD.suffixColor = new OptionMode(this, "Suffix Color", "None", new String[] { "None", "Gray", "White" }, 0);
        final Option[] array = new Option[0xB4 ^ 0xA0];
        array[0] = this.watermark;
        array[1] = this.arrayList;
        array[2] = this.fontStyle;
        array[3] = this.backGround;
        array[4] = this.arraylistColor;
        array[5] = HUD.suffixColor;
        array[6] = this.astolfoFirst;
        array[7] = this.astolfoSecond;
        array[8] = this.fadeSpeed;
        array[9] = this.customChat;
        array[0x6C ^ 0x66] = this.customPotions;
        array[0x54 ^ 0x5F] = this.hotbar;
        array[0x8B ^ 0x87] = this.customInventory;
        array[0x5B ^ 0x56] = this.timerInfo;
        array[0x9B ^ 0x95] = this.watermarkMode;
        array[0x31 ^ 0x3E] = this.rectColor;
        array[0xBA ^ 0xAA] = this.arraylistMode;
        array[0xA7 ^ 0xB6] = this.shadow;
        array[0xBD ^ 0xAF] = this.shadowColor;
        array[0x53 ^ 0x40] = this.shadowRange;
        this.addOptions(array);
    }
    
    @EventTarget
    public void render2D(final EventRender2D eventRender2D) {
        this.setSuffix(this.watermarkMode.getMode() + " | " + this.arraylistMode.getMode());
        if (this.fontStyle.isMode("EuclidFlex")) {
            this.fr = CFontManager.euclidflex;
        }
        if (this.fontStyle.isMode("OpenSans")) {
            this.fr = CFontManager.opensans;
        }
        if (this.fontStyle.isMode("Comfortaa")) {
            this.fr = CFontManager.comfortaa;
        }
        if (this.fontStyle.isMode("Montserrat")) {
            this.fr = CFontManager.montserrat;
        }
        final bit bit = new bit(HUD.mc);
        GL11.glPushMatrix();
        bus.g();
        this.renderArrayList(bit);
        this.renderWaterMark();
        if (this.timerInfo.enabled) {
            RenderUtils.drawCircleOut(eventRender2D.width / 2.0f, eventRender2D.height / 2.0f, 7.0, 0.0, HUD.packetsCounter / 40.0f * 360.0f, 1.0f, Timer.infoColor.getColor().getRGB());
        }
        GL11.glPopMatrix();
        bus.I();
    }
    
    void renderShadows(final bit bit, final List<Feature> list, final float n, final float n2, float n3) {
        for (final Feature feature : list) {
            final float floatValue = HUD.anims.get(feature);
            final float n4 = (float)bit.a();
            if (floatValue > this.fr.getStringWidth(feature.getSuffix()) * 1.6) {
                continue;
            }
            bus.I();
            final float num = this.shadowRange.getNum();
            RenderUtils.drawBlurredShadow(n + n4 - this.fr.getStringWidth(feature.getSuffix()) + floatValue - num, n2 + n3 * this.fr.FONT_HEIGHT - num + 2.0f, this.fr.getStringWidth(feature.getSuffix()) + num * 1.4f, this.fr.FONT_HEIGHT + num, 0x81 ^ 0x8E, this.shadowColor.getColor());
            if (floatValue >= this.fr.getStringWidth(feature.getSuffix()) * 1.5f) {
                continue;
            }
            ++n3;
        }
    }
    
    void renderFeatures(final bit bit, final List<Feature> list, final float n, final float n2, float n3) {
        for (final Feature feature : list) {
            final float floatValue = HUD.anims.get(feature);
            final float n4 = (float)bit.a();
            if (floatValue > this.fr.getStringWidth(feature.getSuffix()) * 1.6) {
                continue;
            }
            int n5 = -1;
            final float n6 = list.stream().filter(Feature::isEnabled).collect((Collector<? super Object, ?, List<? super Object>>)Collectors.toList()).indexOf(feature) / (float)list.stream().filter(Feature::isEnabled).count();
            final String mode = this.arraylistMode.getMode();
            switch (mode) {
                case "Astolfo": {
                    n5 = Utils.getGradientOffset(this.astolfoFirst.getColor(), this.astolfoSecond.getColor(), Math.abs(Main.millis / 40.0f / (8.0f + 0.2f / (this.fadeSpeed.getNum() * 4.0f)) + n3 / 5.0f), 229 + 5 - 92 + 113).getRGB();
                    break;
                }
                case "Rainbow": {
                    n5 = Color.getHSBColor((float)((Math.sin(Main.millis / 1000.0f * (this.fadeSpeed.getNum() * 2.0f)) + 1.0) / 2.0), 1.0f, 1.0f).getRGB();
                    break;
                }
                case "Rainbow2": {
                    n5 = Color.getHSBColor(n6 - Main.millis / 5000.0f * this.fadeSpeed.getNum() * 10.0f, 0.8f, 0.9f).getRGB();
                    break;
                }
                case "Rainbow3": {
                    n5 = Color.getHSBColor((float)(1.0 - (Math.cos(Main.millis / 2000.0f * this.fadeSpeed.getNum() * 10.0f) + 1.0) / 2.0 * n6), 0.8f, 0.9f).getRGB();
                    break;
                }
                case "Aquafresh": {
                    n5 = Utils.getGradientOffset(Utils.getGradientOffset(Color.BLUE, Color.RED, Main.millis / 2000.0f, 86 + 117 - 125 + 122), Color.WHITE.brighter(), Main.millis / 1000.0f, 188 + 58 - 120 + 74).getRGB();
                    break;
                }
                case "Color": {
                    n5 = this.arraylistColor.getColor().getRGB();
                    break;
                }
            }
            bus.I();
            RenderUtils.drawRectWH(n + n4 - this.fr.getStringWidth(feature.getSuffix()) + floatValue - 3.0f, n2 + n3 * this.fr.FONT_HEIGHT, (float)(this.fr.getStringWidth(feature.getSuffix()) + 3), this.fr.FONT_HEIGHT, this.backGround.getColor().getRGB());
            this.fr.drawString(feature.getSuffix(), n + n4 - this.fr.getStringWidth(feature.getSuffix()) + floatValue - 1.0f, n2 + n3 * this.fr.FONT_HEIGHT - 1.0f, n5);
            if (floatValue >= this.fr.getStringWidth(feature.getSuffix()) * 1.5f) {
                continue;
            }
            ++n3;
        }
    }
    
    void renderWaterMark() {
        if (!this.watermark.enabled) {
            return;
        }
        final String mode = this.watermarkMode.getMode();
        switch (mode) {
            case "NL": {
                final String string = " | " + Main.version + " | " + HUD.mc.af.c();
                RenderUtils.drawRoundedRect(3.0, 4.0, 3 + CFontManager.museo.getStringWidth("WILD" + string), 20.0, 5.0, new Color(0, 0x77 ^ 0x63, 0x69 ^ 0x7D, 60 + 101 - 112 + 206).getRGB());
                CFontManager.museo.drawString("WILD", 5.0f, 7.0f, new Color(0x28 ^ 0xA, 59 + 86 + 6 + 28, 223 + 156 - 251 + 108, 41 + 144 + 35 + 35).getRGB());
                CFontManager.museo.drawString("WILD", 6.0f, 8.0f, -1);
                CFontManager.museo.drawString(string, 35.0f, 7.0f, -1);
                break;
            }
            case "Rise": {
                final float n2 = (float)(CFontManager.manropesmall.getStringWidth(Main.version) / 2 - 2);
                RenderUtils.drawBlurredShadow(6.0f, 6.0f, 61.0f + n2, 25.0f, 8, new Color(-1962934271, true));
                RenderUtils.drawRectWH(8.0f, 8.0f, 56.0f + n2, 22.0f, new Color(-1962934271, true).getRGB());
                RenderUtils.drawRectWH(8.0f, 8.0f, 1.0f, 22.0f, this.rectColor.getColor().getRGB());
                RenderUtils.drawBlurredShadow(6.0f, 6.0f, 3.0f, 24.0f, 8, this.rectColor.getColor());
                CFontManager.montserrat.drawStringWithShadow("Wild", 12.5f, 8.0f, -1);
                CFontManager.manropesmall.drawScaledString(Main.version + " build", 36.5f, 8.0f, new Color(7226314 + 2651360 - 2117683 + 1056271).getRGB(), 0.8f, false);
                CFontManager.montserrat.drawScaledString("by ez.h", 13.5f, 18.5f, -1, 0.8f, false);
                break;
            }
            case "Gamesense": {
                final String string2 = "wildsense | " + HUD.mc.af.c() + " | " + Calendar.getInstance().get(0x34 ^ 0x3F) + ":" + Calendar.getInstance().get(0x66 ^ 0x6A);
                RenderUtils.drawGradientSidewaysWH(3.0, 7.0, 3 + HUD.mc.k.a(string2), 1.5, new Color(85 + 57 + 60 + 18, 0x65 ^ 0x5C, 27 + 147 + 7 + 37, 137 + 20 - 112 + 210).getRGB(), new Color(51 + 91 - 5 + 83, 131 + 0 - 12 + 113, 0x8C ^ 0xA3, 148 + 28 - 25 + 104).getRGB());
                RenderUtils.drawRectWH(3.0f, 9.0f, (float)(3 + HUD.mc.k.a(string2)), 11.0f, new Color(0x57 ^ 0x74, 0x67 ^ 0x44, 0x84 ^ 0xA7, 248 + 49 - 176 + 134).getRGB());
                HUD.mc.k.drawString(string2, 5.0f, 11.0f, -1);
                break;
            }
            case "Modern": {
                if (this.shadow.enabled) {
                    RenderUtils.drawBlurredShadow(2.0f, 2.0f, 57.0f, 18.0f, 6, this.shadowColor.getColor());
                }
                CFontManager.stylo2.drawCenteredString("W", 12.0f, 5.0f, Color.getHSBColor((float)((Math.sin(Main.millis / 800.0f) + 1.0) / 2.0), 1.0f, 1.0f).getRGB());
                CFontManager.stylo2.drawCenteredString("ILD", 31.0f, 5.0f, -1);
                CFontManager.rany.drawCenteredString(Main.version, 49.0f, 5.0f, -1);
                break;
            }
            case "skeet.cc": {
                final String string3 = "§2wild§fsense §8|§f " + bib.af() + "fps §8|§f " + HUD.mc.h.h_() + " §8|§f " + (HUD.mc.E() ? 0L : HUD.mc.C().e) + "ms §8|§f " + (HUD.mc.E() ? "local" : HUD.mc.C().b);
                RenderUtils.drawRect(2.0f, 2.0f, (float)(HUD.mc.k.a(string3) + 6), (float)(HUD.mc.k.a + 5), new Color(1835595 + 1292364 - 2751215 + 2781320).hashCode());
                RenderUtils.drawRect(3.0f, 3.0f, (float)(HUD.mc.k.a(string3) + 5), (float)(HUD.mc.k.a + 4), new Color(2031023 + 1156165 - 1631605 + 878758).hashCode());
                RenderUtils.drawRect(4.0f, 4.0f, (float)(HUD.mc.k.a(string3) + 4), (float)(HUD.mc.k.a + 3), new Color(314130 + 665021 - 499501 + 902003).hashCode());
                HUD.mc.k.a(string3, 4.0f, 4.0f, -1);
                break;
            }
            case "Stylo": {
                final Date date = new Date();
                RenderUtils.drawGradientSideways(0.0, 23.0, CFontManager.manrope.getStringWidth(new SimpleDateFormat("HH:mm").format(date) + "") + (0x52 ^ 0x5F), 33.0, Integer.MIN_VALUE, 0);
                CFontManager.manrope.drawStringWithShadow(new SimpleDateFormat("HH:mm").format(date) + "", 5.0f, 21.0f, new Color(178 + 211 - 299 + 165, 3 + 123 + 2 + 127, 174 + 137 - 259 + 203, 229 + 143 - 150 + 8).getRGB());
                CFontManager.stylo.drawString("WILD", 4.0f, 2.0f, new Color(0, 0, 0, 181 + 167 - 127 + 34).getRGB(), false);
                CFontManager.stylo.drawString("WILD", 3.0f, 1.0f, -1, false);
                CFontManager.stylo2.drawString(Main.version, 65.5f, 3.0f, new Color(0, 0, 0, 32 + 38 + 130 + 55).getRGB(), false);
                CFontManager.stylo2.drawString(Main.version, 65.0f, 2.0f, -1, false);
                break;
            }
        }
    }
    
    static {
        HUD.anims = new HashMap<Feature, Float>();
        HUD.color = new Color(-1);
        HUD.packetsCounter = 0;
    }
    
    void renderArrayList(final bit bit) {
        if (!this.arrayList.enabled) {
            return;
        }
        final List<? super Object> list = HUD.anims.keySet().stream().filter(feature -> !feature.isHidden()).sorted(Comparator.comparingDouble(feature2 -> -this.fr.getStringWidth(feature2.getSuffix()))).collect((Collector<? super Object, ?, List<? super Object>>)Collectors.toList());
        for (final Map.Entry<Feature, Float> entry : HUD.anims.entrySet()) {
            final Feature feature3 = entry.getKey();
            entry.setValue(MathUtils.lerp(entry.getValue(), feature3.isEnabled() ? 0.0f : ((float)(this.fr.getStringWidth(feature3.getSuffix()) * 2)), 0.1f));
        }
        if (this.shadow.enabled) {
            this.renderShadows(bit, (List<Feature>)list, -5.0f, 5.0f, 0.0f);
        }
        this.renderFeatures(bit, (List<Feature>)list, -5.0f, 5.0f, 0.0f);
    }
    
    @EventTarget
    public void onPacketSent(final EventPacketSend eventPacketSend) {
        if (eventPacketSend.getPacket() instanceof lk && Main.getFeatureByName("Timer").isEnabled() && (HUD.mc.h.isMoving() || HUD.mc.h.t != 0.0) && HUD.packetsCounter <= (0x5F ^ 0x77)) {
            ++HUD.packetsCounter;
        }
    }
    
    @Override
    public void updateElements() {
        this.shadowColor.display = this.shadow.enabled;
        this.shadowRange.display = this.shadow.enabled;
        this.watermarkMode.display = this.watermark.enabled;
        this.arraylistMode.display = this.arrayList.enabled;
        this.rectColor.display = (this.arrayList.enabled && this.watermarkMode.isMode("Rise"));
        this.arraylistColor.display = (this.arrayList.enabled && this.arraylistMode.isMode("Color"));
        HUD.suffixColor.display = this.arrayList.enabled;
        this.fadeSpeed.display = (this.arrayList.enabled && (this.arraylistMode.isMode("Astolfo") || this.arraylistMode.isMode("Rainbow") || this.arraylistMode.isMode("Rainbow2") || this.arraylistMode.isMode("Rainbow3")));
        this.backGround.display = this.arrayList.enabled;
        this.fontStyle.display = this.arrayList.enabled;
        this.astolfoFirst.display = (this.arrayList.enabled && this.arraylistMode.isMode("Astolfo"));
        this.astolfoSecond.display = (this.arrayList.enabled && this.arraylistMode.isMode("Astolfo"));
        super.updateElements();
    }
}
