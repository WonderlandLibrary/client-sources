package ez.h.ui.flatclickgui;

import ez.h.ui.fonts.*;
import ez.h.*;
import java.awt.*;
import ez.h.features.visual.*;
import ez.h.ui.clickgui.*;
import java.io.*;
import ez.h.features.*;
import java.util.stream.*;
import ez.h.animengine.*;
import org.lwjgl.opengl.*;
import java.util.*;
import org.lwjgl.input.*;
import ez.h.utils.*;
import ez.h.ui.clickgui.element.*;

public class FlatGuiScreen extends blk
{
    protected static Category selectedCategory;
    float categoryGlow;
    boolean isDraggingScrollBar;
    boolean isDragging;
    Counter counter;
    protected static float height;
    protected static float y;
    float scaleAnimationY;
    float scaleAnimationX;
    public static ColorPicker colorPicker;
    private float infoY;
    private boolean ready;
    boolean isSearching;
    private String i;
    protected static float x;
    ElementFeature hoveredFeature;
    float maxY;
    float mY;
    float scrollFactor;
    float mX;
    public static HashMap<Category, ArrayList<ElementFeature>> elements;
    float scrollBarY;
    List<ElementFeature> selectedFeatures;
    float mouseScroll;
    protected static float width;
    int viggneteAlpha;
    float categoryGlowAnimationX;
    static bje search;
    
    void drawWatermark() {
        CFontManager.montserratbold.drawScaledString("Wild ", FlatGuiScreen.x + 6.5f, FlatGuiScreen.y + 6.5f - 1.0f, -1, 1.1f, false);
        CFontManager.montserrat.drawScaledString(Main.version + "", FlatGuiScreen.x + 6.5f + 27.5f, FlatGuiScreen.y + 6.5f + 2.0f, new Color(-7500403, true).getRGB(), 0.75f, false);
    }
    
    void drawSettings() {
        RenderUtils.drawImg(new nf("wild/gear.png"), FlatGuiScreen.x + FlatGuiScreen.width - 16.5f, FlatGuiScreen.y + 6.0f, 10.0, 10.0);
    }
    
    public void a(int n, int n2, final float n3) {
        n = (int)ScaleUtils.getScale(n);
        n2 = (int)ScaleUtils.getScale(n2);
        this.viggneteAlpha = (int)MathUtils.lerp((float)this.viggneteAlpha, (float)ClickGUI.vignetteColor.getColor().getAlpha(), 0.05f);
        ClickGuiScreen.currentHovered = ((this.hoveredFeature != null) ? this.hoveredFeature.feature : null);
        if (ClickGuiScreen.currentHovered == null) {
            this.infoY = 0.0f;
        }
        FlatGuiScreen.width = 500.0f;
        if (this.counter.hasReached(30000.0f)) {
            try {
                Main.configManager.update(Main.configManager.currentCFG);
                Main.configManager.saveAlts();
            }
            catch (IOException ex) {}
            this.counter.reset();
        }
        if (ClickGUI.blur.enabled) {
            RenderUtils.Blur.blur((int)ClickGUI.blurStrength.getNum());
        }
        final Iterator<Feature> iterator = Main.features.iterator();
        while (iterator.hasNext()) {
            iterator.next().updateElements();
        }
        FlatGuiScreen.search.a = (int)(FlatGuiScreen.x + FlatGuiScreen.width / 5.0f + 5.0f);
        FlatGuiScreen.search.f = (int)(FlatGuiScreen.y + 8.0f);
        this.mouseScroll -= Mouse.getDWheel() / 2000.0f;
        this.scrollFactor = MathUtils.lerp(this.scrollFactor, this.scrollFactor + this.mouseScroll, 0.5f);
        this.mouseScroll = 0.0f;
        this.scrollFactor = rk.a(this.scrollFactor, 0.0f, 1.0f);
        if (FlatGuiScreen.search.b().isEmpty() && !FlatGuiScreen.search.m()) {
            this.selectedFeatures = FlatGuiScreen.elements.get(FlatGuiScreen.selectedCategory);
        }
        else {
            this.selectedFeatures = FlatGuiScreen.elements.get(FlatGuiScreen.selectedCategory).stream().filter(elementFeature -> elementFeature.feature.getName().toLowerCase().contains(FlatGuiScreen.search.b().toLowerCase()) || elementFeature.feature.getName().equalsIgnoreCase(FlatGuiScreen.search.b())).collect((Collector<? super Object, ?, List<ElementFeature>>)Collectors.toList());
        }
        if (!FlatGuiScreen.search.m() && FlatGuiScreen.search.b().isEmpty()) {
            this.isSearching = false;
            FlatGuiScreen.search.a("");
        }
        if (!FlatGuiScreen.search.b().isEmpty()) {
            Category category = null;
            for (final Feature feature : Main.features) {
                if (category != null) {
                    continue;
                }
                if (!feature.getName().toLowerCase().contains(FlatGuiScreen.search.b().toLowerCase()) && !feature.getName().equalsIgnoreCase(FlatGuiScreen.search.b())) {
                    continue;
                }
                category = (FlatGuiScreen.selectedCategory = feature.getCategory());
            }
        }
        if (this.scaleAnimationX < 1.0f) {
            this.scaleAnimationX += 8.0f / bib.af();
        }
        else {
            this.scaleAnimationX = 1.0f;
        }
        if (this.scaleAnimationX > 0.4f) {
            if (this.scaleAnimationY < 1.0f) {
                this.scaleAnimationY += (this.scaleAnimationX * 5.0f + 0.5f) / bib.af();
            }
            else {
                this.scaleAnimationY = 1.0f;
            }
        }
        final float a = rk.a(Easings.easeInSine(this.scaleAnimationX), 0.0f, 1.0f);
        final float a2 = rk.a(Easings.easeOutQubic(this.scaleAnimationY), 0.0f, 1.0f);
        this.drawDescriptions();
        if (ClickGUI.vignette.enabled) {
            this.renderVignetteScaledResolution(new bit(this.j));
        }
        GL11.glTranslatef((FlatGuiScreen.x + FlatGuiScreen.width / 2.0f) * (1.0f - a), (FlatGuiScreen.y + FlatGuiScreen.height / 2.0f) * (1.0f - a2), 0.0f);
        GL11.glScalef(a, a2, 1.0f);
        ScaleUtils.scale_pre();
        FlatGuiScreen.colorPicker.y = (int)FlatGuiScreen.y + (0xB7 ^ 0xBD);
        FlatGuiScreen.colorPicker.x = (int)(FlatGuiScreen.x + FlatGuiScreen.width);
        if (ColorPicker.currentOption != null) {
            FlatGuiScreen.colorPicker.draw(FlatGuiScreen.colorPicker.x, FlatGuiScreen.colorPicker.y, 0x3D ^ 0x45, 0xB8 ^ 0xC0, n, n2, ColorPicker.currentOption.getColor());
        }
        this.drawGuiBackground(true);
        this.drawBorderLines();
        this.drawWatermark();
        this.drawSearch();
        this.drawSettings();
        this.drawCategories((float)n, (float)n2);
        this.drawScrollBar((float)n, (float)n2);
        this.drawElements((float)n, (float)n2);
        ScaleUtils.scale_post();
        if (this.isDragging) {
            FlatGuiScreen.x = n + this.mX;
            FlatGuiScreen.y = n2 + this.mY;
        }
        super.a(n, n2, n3);
    }
    
    public boolean d() {
        return false;
    }
    
    void drawGuiBackground(final boolean b) {
        if (b) {
            RenderUtils.drawBlurredShadow(FlatGuiScreen.x - 2.0f, FlatGuiScreen.y - 2.0f, FlatGuiScreen.width + 4.0f, FlatGuiScreen.height + 4.0f, 0x8E ^ 0x82, new Color(-1743580397, true));
        }
        RenderUtils.drawRoundedRect(FlatGuiScreen.x, FlatGuiScreen.y, FlatGuiScreen.width, FlatGuiScreen.height, 0.0, new Color(804634068 + 895659170 - 1431933496 + 1460943573, true).getRGB());
    }
    
    static {
        FlatGuiScreen.elements = new HashMap<Category, ArrayList<ElementFeature>>();
        FlatGuiScreen.colorPicker = new ColorPicker();
        FlatGuiScreen.x = 100.0f;
        FlatGuiScreen.y = 100.0f;
        FlatGuiScreen.width = 500.0f;
        FlatGuiScreen.height = 300.0f;
        FlatGuiScreen.selectedCategory = Category.COMBAT;
    }
    
    void drawPanels(final List<ElementFeature> list, final float n, final float n2) {
        float n3 = 0.0f;
        float n4 = 0.0f;
        final List<? super Object> list2 = list.stream().filter(elementFeature -> list.indexOf(elementFeature) % 2 == 0).collect((Collector<? super Object, ?, List<? super Object>>)Collectors.toList());
        final List<? super Object> list3 = list.stream().filter(elementFeature2 -> list.indexOf(elementFeature2) % 2 != 0).collect((Collector<? super Object, ?, List<? super Object>>)Collectors.toList());
        float n5 = 0.0f;
        float n6 = 0.0f;
        for (final ElementFeature hoveredFeature : list2) {
            final float n7 = FlatGuiScreen.x + 23.5f;
            final float n8 = FlatGuiScreen.y - this.maxY * this.scrollFactor + 65.0f + n3;
            if (n8 < FlatGuiScreen.y + FlatGuiScreen.height + 50.0f) {
                hoveredFeature.draw(n7, n8, n, n2);
            }
            if (n8 < FlatGuiScreen.y + FlatGuiScreen.height && n8 > FlatGuiScreen.y + 52.5f && this.isHover(n, n2, n7, n8, hoveredFeature.width, 18.0f)) {
                this.hoveredFeature = hoveredFeature;
            }
            if (n8 < FlatGuiScreen.y + FlatGuiScreen.height + 20.0f && n8 > FlatGuiScreen.y - hoveredFeature.height - 20.0f) {
                if (hoveredFeature.scaleAnimation < 1.0f) {
                    final ElementFeature elementFeature3 = hoveredFeature;
                    elementFeature3.scaleAnimation += 1.0f / bib.af();
                }
                else {
                    hoveredFeature.scaleAnimation = 1.0f;
                }
            }
            else {
                hoveredFeature.scaleAnimation = 0.0f;
            }
            n3 += hoveredFeature.height + 10.0f;
            n5 += hoveredFeature.height;
        }
        for (final ElementFeature hoveredFeature2 : list3) {
            final float n9 = FlatGuiScreen.x + 23.5f + 10.0f + hoveredFeature2.width;
            final float n10 = FlatGuiScreen.y - this.maxY * this.scrollFactor + 65.0f + n4;
            if (n10 < FlatGuiScreen.y + FlatGuiScreen.height + 50.0f) {
                hoveredFeature2.draw(n9, n10, n, n2);
            }
            if (n10 < FlatGuiScreen.y + FlatGuiScreen.height && n10 > FlatGuiScreen.y + 52.5f && this.isHover(n, n2, n9, n10, hoveredFeature2.width, 18.0f)) {
                this.hoveredFeature = hoveredFeature2;
            }
            if (n10 < FlatGuiScreen.y + FlatGuiScreen.height + 20.0f && n10 > FlatGuiScreen.y - hoveredFeature2.height - 20.0f) {
                if (hoveredFeature2.scaleAnimation < 1.0f) {
                    final ElementFeature elementFeature4 = hoveredFeature2;
                    elementFeature4.scaleAnimation += 1.0f / bib.af();
                }
                else {
                    hoveredFeature2.scaleAnimation = 1.0f;
                }
            }
            else {
                hoveredFeature2.scaleAnimation = 0.0f;
            }
            n4 += hoveredFeature2.height + 10.0f;
            n6 += hoveredFeature2.height;
        }
        this.maxY = Math.max(n5, n6) + 120.0f;
    }
    
    protected void a(int n, int n2, final int n3) throws IOException {
        n = (int)ScaleUtils.getScale(n);
        n2 = (int)ScaleUtils.getScale(n2);
        FlatGuiScreen.search.a(n, n2, n3);
        if (ColorPicker.alphaField != null) {
            ColorPicker.alphaField.a(n, n2, n3);
        }
        this.selectedFeatures.forEach(elementFeature3 -> elementFeature3.elements.forEach(flatElement -> flatElement.mouseClicked(n, n2, n3)));
        if (this.isHover((float)n, (float)n2, FlatGuiScreen.x, FlatGuiScreen.y, FlatGuiScreen.width / 5.0f, 30.0f)) {
            this.isDragging = true;
            this.mX = FlatGuiScreen.x - n;
            this.mY = FlatGuiScreen.y - n2;
        }
        if (this.isHover((float)n, (float)n2, FlatGuiScreen.x + FlatGuiScreen.width - 14.0f, FlatGuiScreen.y + 52.5f, 13.5f, FlatGuiScreen.height - 52.5f)) {
            this.isDraggingScrollBar = true;
        }
        if (this.hoveredFeature != null && !this.isDraggingScrollBar && !this.isDragging) {
            if (!Keyboard.isKeyDown(0x5C ^ 0x76)) {
                if (n3 == 0) {
                    this.hoveredFeature.feature.toggle();
                }
                if (n3 == 2) {
                    this.hoveredFeature.isBinding = true;
                    this.selectedFeatures.stream().filter(elementFeature2 -> elementFeature2 != this.hoveredFeature).forEach(elementFeature -> elementFeature.isBinding = false);
                }
            }
            else {
                this.hoveredFeature.feature.setHidden(!this.hoveredFeature.feature.isHidden());
            }
        }
        if (this.isHover((float)n, (float)n2, FlatGuiScreen.x + FlatGuiScreen.width / 5.0f, FlatGuiScreen.y, FlatGuiScreen.x + FlatGuiScreen.width - FlatGuiScreen.width / 5.0f, 25.0f) && n3 == 0) {
            this.isSearching = true;
        }
        super.a(n, n2, n3);
    }
    
    public boolean isHover(final float n, final float n2, final float n3, final float n4, final float n5, final float n6) {
        return n >= n3 && n <= n3 + n5 && n2 >= n4 && n2 <= n4 + n6;
    }
    
    void drawDescriptions() {
        if (ClickGUI.descriptions.enabled && ClickGuiScreen.currentHovered != null && ClickGuiScreen.currentHovered.getCategory() == FlatGuiScreen.selectedCategory) {
            final bit bit = new bit(this.j);
            if (ClickGuiScreen.currentHovered != null) {
                this.infoY = AnimUtils.lerp(this.infoY, 30.0f, 0.15f);
            }
            else {
                this.infoY = AnimUtils.lerp(this.infoY, -30.0f, 0.2f);
            }
            final String s = (ClickGuiScreen.currentHovered == null) ? "" : ClickGuiScreen.currentHovered.getDescription();
            final float n = this.j.k.a(s) / 2.0f;
            final float n2 = 20.0f;
            RenderUtils.drawRect(bit.a() / 2.0f - n2 - n, bit.b() - this.infoY, bit.a() / 2.0f + n2 + n, (float)bit.b(), new Color(-671088367, true).getRGB());
            CFontManager.manropesmall.drawCenteredStringNoShadow((ClickGuiScreen.currentHovered == null) ? "" : ClickGuiScreen.currentHovered.getName(), bit.a() / 2.0f, bit.b() - this.infoY, -1);
            RenderUtils.drawBlurredShadow(bit.a() / 2.0f - n2 - n, bit.b() - this.infoY + 12.0f, n2 * 2.0f + n * 2.0f, 3.0f, 5, ClickGUI.clickGUIColor.getColor());
            RenderUtils.drawRect(bit.a() / 2.0f - n2 - n, bit.b() - this.infoY + 13.0f, bit.a() / 2.0f + n2 + n, bit.b() - this.infoY + 14.0f, ClickGUI.clickGUIColor.getColor().getRGB());
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
            CFontManager.acrombold.drawStringWithShadow("Last pressed key: ", (float)(bit.a() - CFontManager.acrombold.getStringWidth("Last pressed key: ")), (float)(bit.b() - (0xE ^ 0x10)), new Color(250 + 245 - 307 + 67, 124 + 113 - 208 + 226, 6 + 80 - 55 + 224, 69 + 42 - 100 + 189).getRGB());
            CFontManager.acrombold.drawStringWithShadow(string + " ", (float)(bit.a() - CFontManager.acrombold.getStringWidth(string) - 5), (float)(bit.b() - (0x28 ^ 0x27)), new Color(44 + 9 - 21 + 223, 170 + 248 - 412 + 249, 156 + 195 - 210 + 114, 184 + 31 - 155 + 140).getRGB());
        }
    }
    
    void drawBorderLines() {
        final int rgb = new Color(149291 + 783950 + 16410 + 3326894).getRGB();
        RenderUtils.drawRectWH(FlatGuiScreen.x, FlatGuiScreen.y + 52.0f, FlatGuiScreen.width, 0.5f, rgb);
        RenderUtils.drawRectWH(FlatGuiScreen.x + FlatGuiScreen.width / 5.0f, FlatGuiScreen.y, 0.5f, 24.0f, rgb);
        RenderUtils.drawRectWH(FlatGuiScreen.x + FlatGuiScreen.width - FlatGuiScreen.width / 5.0f, FlatGuiScreen.y, 0.5f, 24.0f, rgb);
        RenderUtils.drawRectWH(FlatGuiScreen.x, FlatGuiScreen.y + 24.0f, FlatGuiScreen.width, 0.5f, rgb);
        RenderUtils.drawRectWH(FlatGuiScreen.x + FlatGuiScreen.width - 15.0f, FlatGuiScreen.y + 52.0f, 0.5f, FlatGuiScreen.height - 52.0f, rgb);
    }
    
    void drawCategories(final float n, final float n2) {
        final float n3 = FlatGuiScreen.width / Category.values().length - 4.0f * Category.values().length;
        float n4 = FlatGuiScreen.x + (FlatGuiScreen.width - n3 * Category.values().length) / 2.0f - 10.0f;
        this.categoryGlow = MathUtils.lerp(this.categoryGlow, 10.0f, 0.08f);
        for (final Category selectedCategory : Category.values()) {
            if (selectedCategory != FlatGuiScreen.selectedCategory && Mouse.isButtonDown(0) && this.isHover(n, n2, n4, FlatGuiScreen.y + 50.0f - 16.0f, n3, 18.0f) && !this.isDraggingScrollBar) {
                FlatGuiScreen.selectedCategory = selectedCategory;
                FlatGuiScreen.search.a("");
                final Category[] values2 = Category.values();
                for (int length2 = values2.length, j = 0; j < length2; ++j) {
                    FlatGuiScreen.elements.get(values2[j]).forEach(elementFeature -> elementFeature.isBinding = false);
                }
                this.categoryGlow = 40.0f;
            }
            int rgb = (selectedCategory == FlatGuiScreen.selectedCategory) ? ClickGUI.clickGUIColor.getColor().getRGB() : new Color(1060978 + 5431647 - 3907564 + 5836443).getRGB();
            if (selectedCategory == FlatGuiScreen.selectedCategory) {
                rgb = Utils.getGradientOffset(new Color(3894472 + 1704425 - 1487798 + 1086548), ClickGUI.clickGUIColor.getColor(), 1.0f - (this.categoryGlow - 10.0f) / 40.0f, 159 + 43 - 162 + 215).getRGB();
                GL11.glPushMatrix();
                bus.I();
                RenderUtils.drawBlurredShadow(n4 - 4.0f, FlatGuiScreen.y + 45.0f - 2.5f, n3 + 8.0f, 6.0f, (int)this.categoryGlow, new Color(rgb));
                GL11.glPopMatrix();
            }
            CFontManager.montserrat.drawCenteredStringNoShadow(selectedCategory.name().substring(0, 1).toUpperCase() + selectedCategory.name().substring(1).toLowerCase(), n4 + n3 / 2.0f, FlatGuiScreen.y + 45.0f - 13.5f, -1);
            RenderUtils.drawRectWH(n4, FlatGuiScreen.y + 45.0f, n3, 1.5f, new Color(rgb).getRGB());
            n4 += 4.0f + n3;
        }
    }
    
    void drawSearch() {
        if (!this.isSearching) {
            CFontManager.montserrat.drawScaledString("Search for a module...", FlatGuiScreen.x + FlatGuiScreen.width / 4.5f, FlatGuiScreen.y + 6.0f, new Color(1776861 + 8150494 - 8731823 + 7554937).getRGB(), 1.0f, false);
            RenderUtils.drawImg(new nf("wild/lupa.png"), FlatGuiScreen.x + FlatGuiScreen.width - FlatGuiScreen.width / 4.5f - 5.0f, FlatGuiScreen.y + 6.0f, 10.0, 10.0);
        }
        else {
            FlatGuiScreen.search.g();
        }
    }
    
    public void m() {
        this.viggneteAlpha = 0;
        final Category[] values = Category.values();
        for (int length = values.length, i = 0; i < length; ++i) {
            FlatGuiScreen.elements.get(values[i]).forEach(elementFeature -> elementFeature.isBinding = false);
        }
        if (this.j.o.af != null) {
            this.j.o.af.a();
        }
        this.isDragging = false;
        this.scaleAnimationX = 0.0f;
        this.scaleAnimationY = 0.0f;
        ColorPicker.fadeCounter = 0;
        ColorPicker.isOpen = false;
        try {
            Main.configManager.update(Main.configManager.currentCFG);
            Main.configManager.saveAlts();
        }
        catch (IOException ex) {}
        super.m();
    }
    
    void drawElements(final float n, final float n2) {
        if (FlatGuiScreen.selectedCategory == null) {
            FlatGuiScreen.selectedCategory = Category.COMBAT;
        }
        this.hoveredFeature = null;
        GL11.glPushMatrix();
        GL11.glEnable(1844 + 461 - 1041 + 1825);
        RenderUtils.setupScissor(FlatGuiScreen.x, FlatGuiScreen.y + 54.5f, FlatGuiScreen.width - 15.0f, rk.a((FlatGuiScreen.height - 55.0f) * this.scaleAnimationY, 0.0f, FlatGuiScreen.height - 55.0f));
        for (final Category category : Category.values()) {
            if (category != FlatGuiScreen.selectedCategory) {
                final Iterator<ElementFeature> iterator = FlatGuiScreen.elements.get(category).iterator();
                while (iterator.hasNext()) {
                    iterator.next().scaleAnimation = 0.0f;
                }
            }
        }
        this.drawPanels(this.selectedFeatures, n, n2);
        GL11.glDisable(2345 + 42 - 59 + 761);
        GL11.glPopMatrix();
        RenderUtils.drawGradientRect(FlatGuiScreen.x, FlatGuiScreen.y + FlatGuiScreen.height - 20.0f, FlatGuiScreen.x + FlatGuiScreen.width - 15.0f, FlatGuiScreen.y + FlatGuiScreen.height, 0, new Color(629549760 + 98025642 - 330936262 + 1314636892, true).getRGB());
        RenderUtils.drawGradientRect(FlatGuiScreen.x, FlatGuiScreen.y + 52.5f, FlatGuiScreen.x + FlatGuiScreen.width - 15.0f, FlatGuiScreen.y + 64.5f, new Color(1351283510 + 821328495 - 684067498 + 222731525, true).getRGB(), 0);
    }
    
    protected void b(int n, int n2, final int n3) {
        this.isDragging = false;
        this.isDraggingScrollBar = false;
        n = (int)ScaleUtils.getScale(n);
        n2 = (int)ScaleUtils.getScale(n2);
        this.selectedFeatures.forEach(elementFeature -> elementFeature.elements.forEach(flatElement -> flatElement.mouseRealesed(n, n2, n3)));
        super.b(n, n2, n3);
    }
    
    public void b() {
        if (ClickGUI.desaturate.enabled && cii.O) {
            if (this.j.o.af != null) {
                this.j.o.af.a();
            }
            this.j.o.af = null;
            this.j.o.a(new nf("wild/desaturate.json"));
            this.j.o.af.d.get(0).c().a("Saturation").a(1.0E-9f);
        }
        (FlatGuiScreen.search = new bje(1, bib.z().k, (int)(FlatGuiScreen.x + FlatGuiScreen.width / 5.0f), (int)(FlatGuiScreen.y + 3.0f), (int)(FlatGuiScreen.width - FlatGuiScreen.width / 5.0f * 2.0f), 0x35 ^ 0x21)).a(false);
        super.b();
    }
    
    void renderVignetteScaledResolution(final bit bit) {
        final Color color = ClickGUI.vignetteColor.getColor();
        bus.y();
        bus.I();
        bus.a(bus.r.l, bus.l.j, bus.r.e, bus.l.n);
        bus.d();
        RenderUtils.color(RenderUtils.injectAlpha(color.getRGB(), rk.a(this.viggneteAlpha, 0, 97 + 227 - 75 + 6)).getRGB());
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
    
    public FlatGuiScreen() {
        this.counter = new Counter();
        this.infoY = 0.0f;
        this.i = "";
        this.ready = false;
        this.selectedFeatures = FlatGuiScreen.elements.get(Category.COMBAT);
    }
    
    protected void a(final char c, final int key) throws IOException {
        if (this.hoveredFeature != null && this.hoveredFeature.isBinding) {
            if (key == (0x9F ^ 0xA6) || key == 1) {
                this.hoveredFeature.feature.setKey(0);
            }
            else {
                this.hoveredFeature.feature.setKey(key);
            }
            this.hoveredFeature.isBinding = false;
        }
        if (ColorPicker.alphaField != null && (key == 2 || key == 3 || key == 4 || key == 5 || key == 6 || key == 7 || key == 8 || key == 9 || key == (0x77 ^ 0x7D) || key == (0x5 ^ 0xE) || key == (0x68 ^ 0x66) || key == 49 + 118 - 63 + 99 || key == 72 + 108 - 61 + 86)) {
            ColorPicker.alphaField.a(c, key);
        }
        FlatGuiScreen.search.a(c, key);
        super.a(c, key);
    }
    
    void drawScrollBar(final float n, float n2) {
        n2 -= FlatGuiScreen.y;
        if (this.isDraggingScrollBar && !this.isDragging) {
            this.scrollFactor = n2 / FlatGuiScreen.height;
        }
        this.scrollFactor += this.mouseScroll;
        this.scrollFactor = rk.a(this.scrollFactor, 0.0f, 1.0f);
        this.scrollBarY = MathUtils.lerp(FlatGuiScreen.y + 52.5f, FlatGuiScreen.y + FlatGuiScreen.height - 15.0f, this.scrollFactor);
        RenderUtils.drawRectWH(FlatGuiScreen.x + FlatGuiScreen.width - 14.0f, this.scrollBarY, 13.5f, 14.0f, new Color(1928039 + 1268035 - 2112612 + 1548258).getRGB());
        RenderUtils.drawRectWH(FlatGuiScreen.x + FlatGuiScreen.width - 12.0f, this.scrollBarY + 5.0f, 9.5f, 1.0f, new Color(6577166 + 6207737 - 9922388 + 7269607).getRGB());
        RenderUtils.drawRectWH(FlatGuiScreen.x + FlatGuiScreen.width - 12.0f, this.scrollBarY + 9.0f, 9.5f, 1.0f, new Color(3255555 + 854011 - 763921 + 6786477).getRGB());
        this.mouseScroll = 0.0f;
    }
    
    public static class ElementFeature
    {
        public float height;
        public Feature feature;
        public float scaleAnimation;
        public boolean isBinding;
        public float width;
        public ArrayList<FlatElement> elements;
        float glowAnim;
        
        public void draw(final float x, final float n, final float n2, final float n3) {
            this.glowAnim = MathUtils.lerp(this.glowAnim, this.feature.isEnabled() ? 10.0f : 40.0f, 0.1f);
            final int rgb = Utils.getGradientOffset(new Color(-1), ClickGUI.clickGUIColor.getColor(), 1.0f - (this.glowAnim - 10.0f) / 40.0f, 241 + 59 - 203 + 158).getRGB();
            GL11.glPushMatrix();
            final float easeOutQubic = Easings.easeOutQubic(this.scaleAnimation);
            GL11.glTranslatef(x * (1.0f - easeOutQubic), n * (1.0f - easeOutQubic), 0.0f);
            GL11.glScalef(easeOutQubic, easeOutQubic, 1.0f);
            float height = 20.0f;
            for (final FlatElement flatElement : this.elements) {
                if (!flatElement.displayOption.display) {
                    continue;
                }
                height += flatElement.height;
            }
            this.height = height;
            this.width = FlatGuiScreen.width / 2.0f - 35.0f;
            bus.I();
            if (this.width > 0.0f && this.height > 0.0f) {
                RenderUtils.drawBlurredShadow(x - 2.0f, n - 2.0f, this.width + 4.0f, this.height + 4.0f, 8, new Color(-1744830464, true));
            }
            RenderUtils.drawRectWH(x, n, this.width, this.height, new Color(-1743251432, true).getRGB());
            CFontManager.montserratbold.drawScaledString(this.feature.getName() + a.i + " [" + (this.isBinding ? "PRESS KEY" : Keyboard.getKeyName(this.feature.getKey())) + "]", x + 6.0f, n + 1.5f, -1, 0.9f, false);
            RenderUtils.drawRectWH(x, n + 15.0f, this.width, 0.5f, new Color(985203 + 2864142 - 2410086 + 2837286).getRGB());
            if (this.feature.isEnabled()) {
                RenderUtils.drawBlurredShadowCircle(x + this.width - 21.0f + 6.0f, n + 2.0f, 11.0f, 11.0f, (int)this.glowAnim, new Color(rgb));
            }
            ElementColor.drawCircle(x + this.width - 15.0f + 6.0f, n + 8.0f, 3.0, rgb);
            float n4 = 17.0f;
            for (final FlatElement flatElement2 : this.elements) {
                if (!flatElement2.displayOption.display) {
                    continue;
                }
                flatElement2.x = x;
                flatElement2.y = n4 + n;
                flatElement2.width = this.width;
                flatElement2.render((int)n2, (int)n3, bib.z().aj());
                n4 += flatElement2.height;
            }
            GL11.glPopMatrix();
        }
        
        public ElementFeature(final Feature feature) {
            this.elements = new ArrayList<FlatElement>();
            this.width = 210.0f;
            this.height = 100.0f;
            this.glowAnim = 40.0f;
            this.feature = feature;
        }
    }
}
