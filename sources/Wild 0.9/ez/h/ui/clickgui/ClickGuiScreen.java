package ez.h.ui.clickgui;

import ez.h.features.*;
import ez.h.*;
import java.io.*;
import org.lwjgl.input.*;
import ez.h.features.visual.*;
import java.awt.*;
import ez.h.utils.*;
import ez.h.animengine.*;
import org.lwjgl.opengl.*;
import ez.h.ui.fonts.*;
import java.util.*;
import ez.h.ui.clickgui.element.*;

public class ClickGuiScreen extends blk
{
    float scrollFactor;
    float easedScale;
    int alpha;
    public static bje search;
    int viggneteAlpha;
    public static float blur;
    float xLerped;
    boolean ready;
    public static List<Panel> panels;
    float yLerped;
    public ArrayList<Snow> snow;
    float infoY;
    public static ClickGuiScreen instance;
    public static boolean clickGUIPanel;
    public static Feature currentHovered;
    Counter counter;
    String i;
    float scale;
    
    public void a(final int n, final int n2, final float n3) {
        if (this.counter.hasReached(30000.0f)) {
            try {
                Main.configManager.update(Main.configManager.currentCFG);
                Main.configManager.saveAlts();
            }
            catch (IOException ex) {}
            this.counter.reset();
        }
        if (Keyboard.isKeyDown(0x47 ^ 0x6D) && Keyboard.isKeyDown(115 + 92 - 19 + 23)) {
            for (final Panel panel2 : ClickGuiScreen.panels) {
                panel2.x = 75.0f;
                panel2.y = (float)((0xC2 ^ 0x89) + ClickGuiScreen.panels.indexOf(panel2) * (0xBD ^ 0x9E));
                panel2.isOpen = false;
            }
        }
        final bit bit = new bit(this.j);
        this.xLerped = MathUtils.lerp(this.xLerped, n / 40.0f, 0.1f);
        this.yLerped = MathUtils.lerp(this.yLerped, n2 / 60.0f, 0.1f);
        this.scrollFactor += Mouse.getDWheel() / 10.0f;
        this.alpha = (int)AnimUtils.lerp((float)this.alpha, 140.0f, 0.05f);
        this.viggneteAlpha = (int)MathUtils.lerp((float)this.viggneteAlpha, (float)ClickGUI.vignetteColor.getColor().getAlpha(), 0.05f);
        ClickGuiScreen.blur = MathUtils.lerp(ClickGuiScreen.blur, ClickGUI.blurStrength.getNum(), 0.2f);
        bir.drawRect(0.0f, 0.0f, (float)bit.a(), (float)bit.b(), new Color(0x51 ^ 0x5B, 0x95 ^ 0x9F, 0x19 ^ 0x13, 73 + 50 - 94 + 111).getRGB());
        this.snow.removeIf(snow2 -> snow2.y > bit.b() + (11 + 139 + 43 + 7));
        if (this.j.m instanceof ClickGuiScreen && ClickGUI.snow.enabled) {
            for (final Snow snow4 : this.snow) {
                if (snow4.y < bit.b() - 5) {
                    continue;
                }
                snow4.alpha = AnimUtils.lerp(snow4.alpha, 0.0f, 0.1f);
            }
            this.snow.forEach(snow -> {
                snow.y = MathUtils.lerp(snow.y, 3000.0f, snow.height * 0.02f + snow.factor);
                snow.x = MathUtils.lerp(snow.x, 3000.0f, 0.001f + snow.factor);
                snow.factor += (float)1.0E-6;
                return;
            });
            this.snow.forEach(snow3 -> {
                bus.G();
                bus.c(1.0f, 1.0f, 1.0f, snow3.height * 200.0f);
                bus.m();
                bus.a(bus.r.l, bus.l.j, bus.r.e, bus.l.n);
                this.j.N().a(new nf("wild/snow.png"));
                bus.b(snow3.height, snow3.height, snow3.height);
                bus.c(this.xLerped * 100.0f, this.yLerped * 100.0f, 0.0f);
                bir.a((int)(snow3.x / snow3.height), (int)(snow3.y / snow3.height), 0.0f, 0.0f, 0x38 ^ 0x5C, 0x44 ^ 0x20, 100.0f, 100.0f);
                bus.H();
                return;
            });
            if (this.counter.hasReached(200.0f)) {
                final float nextFloat = MathUtils.nextFloat(0.01f, 0.05f);
                this.snow.add(new Snow((float)MathUtils.nextInt(-400, bit.a()), 0.0f, nextFloat, nextFloat, (float)MathUtils.nextInt(0x3D ^ 0x6D, 72 + 63 - 104 + 224)));
                this.snow.add(new Snow(-400.0f, (float)MathUtils.nextInt(-400, bit.b()), nextFloat, nextFloat, (float)MathUtils.nextInt(0x4B ^ 0x1B, 135 + 219 - 259 + 160)));
            }
        }
        if (ClickGUI.blur.enabled) {
            RenderUtils.Blur.blur((int)ClickGuiScreen.blur);
        }
        if (ClickGUI.desaturate.enabled) {
            RenderUtils.Desaturate.desaturate(0.0f);
        }
        bus.e();
        if (ClickGUI.vignette.enabled) {
            if (this.j.h != null) {
                this.renderVignetteScaledResolution(bit);
            }
        }
        else {
            bus.k();
            bus.a(bus.r.l, bus.l.j, bus.r.e, bus.l.n);
        }
        final int n4 = (int)ScaleUtils.getScale(n);
        final int n5 = (int)ScaleUtils.getScale(n2);
        if (this.easedScale < 1.0f) {
            this.easedScale += 4.0f / bib.af();
        }
        else {
            this.easedScale = 1.0f;
        }
        this.scale = Easings.easeInOutCubic(this.easedScale);
        GL11.glPushMatrix();
        final float n6 = 225.0f;
        final String mode = ClickGUI.animetyan.getMode();
        switch (mode) {
            case "ZeroTwo": {
                RenderUtils.drawImg(new nf("wild/anime/zerotwo.png"), bit.a() - n6 - 20.0f, bit.b() - n6, n6, n6);
                break;
            }
            case "Siesta": {
                RenderUtils.drawImg(new nf("wild/anime/siesta.png"), bit.a() - n6 - 20.0f, bit.b() - n6, n6, n6);
                break;
            }
            case "Tsundere": {
                RenderUtils.drawImg(new nf("wild/anime/tsundere.png"), bit.a() - n6 - 20.0f, bit.b() - n6, n6, n6);
                break;
            }
        }
        ScaleUtils.scale_pre();
        final int n8;
        final int n9;
        ClickGuiScreen.panels.forEach(panel -> {
            bus.c(panel.x * (1.0f - this.scale), panel.y * (1.0f - this.scale), 0.0f);
            bus.b(this.scale, this.scale, 0.0f);
            panel.render(n8, n9, n3);
            panel.y += this.scrollFactor;
            return;
        });
        ClickGuiScreen.search.g();
        ClickGuiScreen.search.a = (int)ScaleUtils.getScale(5);
        ClickGuiScreen.search.f = (int)ScaleUtils.getScale(bit.b() - (0xAF ^ 0xB6));
        if (!ClickGuiScreen.search.m() && ClickGuiScreen.search.b().isEmpty()) {
            CFontManager.rany.drawCenteredString("Search", ScaleUtils.getScale(0x1C ^ 0x7), ScaleUtils.getScale(bit.b() - (0x4 ^ 0x10)), Color.WHITE.hashCode());
        }
        ScaleUtils.scale_post();
        GL11.glPopMatrix();
        if (ClickGUI.descriptions.enabled) {
            boolean isOpen = false;
            if (ClickGuiScreen.currentHovered != null) {
                final Iterator<Panel> iterator3 = ClickGuiScreen.panels.iterator();
                while (iterator3.hasNext()) {
                    for (final ElementButton elementButton : iterator3.next().buttons) {
                        if (elementButton.getFeature() == ClickGuiScreen.currentHovered) {
                            isOpen = elementButton.panel.isOpen;
                            break;
                        }
                    }
                }
            }
            if (ClickGuiScreen.currentHovered != null && isOpen) {
                this.infoY = AnimUtils.lerp(this.infoY, 30.0f, 0.15f);
            }
            else {
                this.infoY = AnimUtils.lerp(this.infoY, -30.0f, 0.2f);
            }
            final String s = (ClickGuiScreen.currentHovered == null) ? "" : ClickGuiScreen.currentHovered.getDescription();
            final float n10 = this.j.k.a(s) / 2.0f;
            final float n11 = 20.0f;
            RenderUtils.drawRect(bit.a() / 2.0f - n11 - n10, bit.b() - this.infoY, bit.a() / 2.0f + n11 + n10, (float)bit.b(), Integer.MIN_VALUE);
            CFontManager.manropesmall.drawCenteredStringNoShadow((ClickGuiScreen.currentHovered == null) ? "" : ClickGuiScreen.currentHovered.getName(), bit.a() / 2.0f, bit.b() - this.infoY, -1);
            RenderUtils.drawBlurredShadow(bit.a() / 2.0f - n11 - n10, bit.b() - this.infoY + 12.0f, n11 * 2.0f + n10 * 2.0f, 3.0f, 5, ClickGUI.clickGUIColor.getColor());
            RenderUtils.drawRect(bit.a() / 2.0f - n11 - n10, bit.b() - this.infoY + 13.0f, bit.a() / 2.0f + n11 + n10, bit.b() - this.infoY + 14.0f, ClickGUI.clickGUIColor.getColor().getRGB());
            GL11.glPushMatrix();
            this.j.k.drawString(s, bit.a() / 2.0f - this.j.k.a(s) / 2.0f, bit.b() - this.infoY / 2.0f + 3.0f, -1);
            GL11.glPopMatrix();
            if (ClickGuiScreen.currentHovered == null) {
                this.i = "";
            }
            else if (this.ready && !this.i.equals(ClickGuiScreen.currentHovered.getDescription())) {
                this.i = "";
            }
            final String string = Keyboard.getKeyName(Keyboard.getEventKey()) + ":" + Keyboard.getEventKey();
            CFontManager.acrombold.drawStringWithShadow("Last pressed key: ", (float)(bit.a() - CFontManager.acrombold.getStringWidth("Last pressed key: ")), (float)(bit.b() - (0xB9 ^ 0xA7)), new Color(175 + 11 - 149 + 218, 18 + 114 - 10 + 133, 195 + 226 - 295 + 129, 70 + 155 - 187 + 162).getRGB());
            CFontManager.acrombold.drawStringWithShadow(string + " ", (float)(bit.a() - CFontManager.acrombold.getStringWidth(string) - 5), (float)(bit.b() - (0x56 ^ 0x59)), new Color(104 + 236 - 203 + 118, 113 + 89 + 36 + 17, 157 + 116 - 238 + 220, 5 + 27 + 84 + 84).getRGB());
        }
        this.scrollFactor = 0.0f;
        ClickGuiScreen.currentHovered = null;
    }
    
    public void a(final char c, final int n) {
        int n2 = 0;
        for (final Panel panel2 : ClickGuiScreen.panels) {
            if (n2 != 0) {
                break;
            }
            final Iterator<ElementButton> iterator2 = panel2.buttons.iterator();
            while (iterator2.hasNext()) {
                if (iterator2.next().isBinding()) {
                    n2 = 1;
                    break;
                }
            }
        }
        if (n == 1 && n2 == 0) {
            this.j.a((blk)null);
        }
        ClickGuiScreen.search.a(c, n);
        ClickGuiScreen.panels.forEach(panel -> panel.keyPressed(c, n));
    }
    
    public void m() {
        this.easedScale = 0.0f;
        ClickGuiScreen.panels.forEach(panel -> panel.buttons.forEach(elementButton -> elementButton.getElements().stream().filter(element -> element instanceof ElementSlider).forEach(elementSlider -> {
            elementSlider.isDragging = false;
            elementSlider.clamp = 0.0f;
        })));
        ClickGuiScreen.panels.forEach(panel2 -> panel2.buttons.forEach(elementButton2 -> elementButton2.getElements().stream().filter(element2 -> element2 instanceof ElementMode).forEach(elementMode -> elementMode.lerpedWidth = 0.0f)));
        if (this.j.o.af != null) {
            this.j.o.af.a();
        }
        ClickGuiScreen.panels.forEach(panel3 -> panel3.isDragging = false);
        this.viggneteAlpha = 0;
        ClickGuiScreen.blur = 0.0f;
        try {
            Main.configManager.update(Main.configManager.currentCFG);
            Main.configManager.saveAlts();
        }
        catch (IOException ex) {}
    }
    
    static {
        ClickGuiScreen.panels = new ArrayList<Panel>();
        ClickGuiScreen.instance = new ClickGuiScreen();
        ClickGuiScreen.blur = 0.001f;
    }
    
    public ClickGuiScreen() {
        this.snow = new ArrayList<Snow>();
        this.i = " ";
        this.counter = new Counter();
        this.infoY = -100.0f;
        this.scale = 5.0f;
        this.alpha = 1;
    }
    
    void renderVignetteScaledResolution(final bit bit) {
        final Color color = ClickGUI.vignetteColor.getColor();
        bus.y();
        bus.I();
        bus.a(bus.r.l, bus.l.j, bus.r.e, bus.l.n);
        bus.d();
        RenderUtils.color(RenderUtils.injectAlpha(color.getRGB(), rk.a(this.viggneteAlpha, 0, 200 + 216 - 268 + 107)).getRGB());
        bus.m();
        this.j.N().a(new nf("textures/misc/vignette.png"));
        final bve a = bve.a();
        final buk c = a.c();
        c.a(7, cdy.g);
        c.b(0.0, (double)bit.b(), -90.0).a(0.0, 1.0).d();
        c.b((double)bit.a(), (double)bit.b(), -90.0).a(1.0, 1.0).d();
        c.b((double)bit.a(), 0.0, -90.0).a(1.0, 0.0).d();
        c.b(0.0, 0.0, -90.0).a(0.0, 0.0).d();
        a.b();
        bus.e();
        bus.l();
    }
    
    public void b(final int n, final int n2, final int n3) {
        ClickGuiScreen.panels.forEach(panel -> panel.mouseRealesed((int)ScaleUtils.getScale(n), (int)ScaleUtils.getScale(n2), n3));
        final ArrayList<ElementColor> list = new ArrayList<ElementColor>();
        final Iterator<Panel> iterator = ClickGuiScreen.panels.iterator();
        while (iterator.hasNext()) {
            final Iterator<ElementButton> iterator2 = iterator.next().buttons.iterator();
            while (iterator2.hasNext()) {
                for (final Element element : iterator2.next().getElements()) {
                    if (element instanceof ElementColor) {
                        list.add((ElementColor)element);
                    }
                }
            }
        }
        list.forEach(elementColor -> {
            elementColor.isDraggingAlpha = false;
            elementColor.isDraggingBrightness = false;
        });
    }
    
    public String getI(final String s) {
        if (this.i.length() < s.length()) {
            this.ready = false;
            return this.i += s.charAt(this.i.length());
        }
        this.ready = true;
        return this.i;
    }
    
    public boolean d() {
        return false;
    }
    
    public void a(final int n, final int n2, final int n3) {
        ClickGuiScreen.search.a((int)ScaleUtils.getScale(n), (int)ScaleUtils.getScale(n2), n3);
        final int n4;
        final int n5;
        ClickGuiScreen.panels.forEach(panel -> panel.mouseClicked(n4, n5, n3));
    }
    
    public void b() {
        ClickGuiScreen.search = new bje(1, this.j.k, 0x39 ^ 0x33, new bit(this.j).b() - (0x0 ^ 0x19), 0xEF ^ 0x97, 0x8F ^ 0x9B);
        super.b();
    }
    
    public class Snow
    {
        public float alpha;
        public float width;
        public float y;
        public float factor;
        public float height;
        public float x;
        
        public Snow(final float x, final float y, final float width, final float height, final float alpha) {
            this.alpha = 255.0f;
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            this.alpha = alpha;
        }
    }
}
