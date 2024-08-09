package ez.h.ui.clickgui.element;

import ez.h.features.*;
import java.util.function.*;
import ez.h.ui.clickgui.*;
import ez.h.features.visual.*;
import org.lwjgl.opengl.*;
import org.lwjgl.input.*;
import java.awt.*;
import ez.h.utils.*;
import java.util.stream.*;
import ez.h.animengine.*;
import ez.h.ui.fonts.*;
import java.util.*;
import ez.h.ui.clickgui.options.*;

public class ElementButton extends Element
{
    public float extendedLerpedHeight;
    float scaling;
    final Feature feature;
    ArrayList<Element> elements;
    float offset;
    float openScaleFactor;
    boolean binding;
    
    @Override
    public void mouseRealesed(final int n, final int n2, final int n3) {
        this.elements.forEach(element -> element.mouseRealesed(n, n2, n3));
    }
    
    @Override
    public void render(final int n, final int n2, final float n3) {
        if (this.isHover((float)n, (float)n2, this.panel.x, this.y, this.width, this.height)) {
            ClickGuiScreen.currentHovered = this.feature;
        }
        CFontRenderer cFontRenderer = CFontManager.manropesmall;
        final String mode = ClickGUI.font.getMode();
        switch (mode) {
            case "Manrope": {
                cFontRenderer = CFontManager.manropesmall;
                break;
            }
            case "Acrom": {
                cFontRenderer = CFontManager.acrombold;
                break;
            }
            case "Monaqi": {
                cFontRenderer = CFontManager.monaqi;
                break;
            }
            case "EuclidFlex": {
                cFontRenderer = CFontManager.euclidflex;
                break;
            }
            case "Mont Blanc": {
                cFontRenderer = CFontManager.montblanc;
                break;
            }
            case "SimplySans": {
                cFontRenderer = CFontManager.simplysans;
                break;
            }
            case "Momcake": {
                cFontRenderer = CFontManager.momcake;
                break;
            }
            case "Quicksand": {
                cFontRenderer = CFontManager.quicksandbig;
                break;
            }
            case "Rany": {
                cFontRenderer = CFontManager.rany;
                break;
            }
            case "Jelly Anica": {
                cFontRenderer = CFontManager.jellyanica;
                break;
            }
        }
        if (this.isOpen) {
            this.extendedLerpedHeight = AnimUtils.lerp(this.extendedLerpedHeight, this.getHeightExtended(), 0.2f);
            this.panel.buttons.subList(this.panel.buttons.indexOf(this) + 1, this.panel.buttons.size()).forEach(elementButton -> elementButton.y += this.extendedLerpedHeight);
            this.openScaleFactor = Math.min(1.0f, this.openScaleFactor + 0.1f);
        }
        else {
            this.extendedLerpedHeight = AnimUtils.lerp(this.extendedLerpedHeight, 0.0f, 0.1f);
            this.openScaleFactor = Math.max(0.0f, this.openScaleFactor - 0.1f);
        }
        GL11.glPushMatrix();
        RenderUtils.drawRect(this.x + 1.0f, this.y, this.x + this.width - 1.0f, this.y + this.height, Integer.MIN_VALUE);
        final String mode2 = ClickGUI.font.getMode();
        if (mode2.equals("Rany") || mode2.equals("Jelly Anica") || mode2.equals("Monaqi") || mode2.equals("Momcake")) {
            bus.c(0.0f, 2.0f, 0.0f);
        }
        if (this.isBinding() || (Keyboard.isKeyDown(0x32 ^ 0x2F) && this.feature.getKey() > 0)) {
            cFontRenderer.drawCenteredString(Keyboard.getKeyName(this.feature.getKey()), this.panel.x + this.panel.width / 2.0f, this.y - 1.0f, new Color(242 + 136 - 376 + 253, 20 + 201 - 58 + 92, 167 + 221 - 164 + 31, 103 + 210 - 152 + 59).getRGB());
        }
        else {
            final float a = rk.a(this.y * 2.0f, 0.0f, (float)bib.z().e);
            Color color;
            if (ClickGUI.easing.enabled) {
                color = Utils.getGradientOffset(ClickGUI.clickGUIColor.getColor(), ClickGUI.easingColor.getColor(), a / bib.z().e, 156 + 212 - 162 + 49);
            }
            else {
                color = ClickGUI.clickGUIColor.getColor();
            }
            if (this.getFeature().getName().toLowerCase().contains(ClickGuiScreen.search.b().toLowerCase()) && !ClickGuiScreen.search.b().isEmpty()) {
                color = new Color(~ClickGUI.clickGUIColor.getColor().getRGB());
            }
            if (this.feature.isEnabled() && ClickGUI.glow.enabled) {
                RenderUtils.drawBlurredShadow((float)(int)(this.x + this.width / 2.0f - cFontRenderer.getStringWidth(this.feature.getName()) / 2 - 2.0f), this.y + 2.0f, (float)(cFontRenderer.getStringWidth(this.feature.getName()) + 4), 7.0f, (int)(10.0f + (10.0f - ClickGUI.glowStrength.getNum())), RenderUtils.injectAlpha(color.brighter().getRGB(), this.feature.isEnabled() ? (0x31 ^ 0x55) : 0));
            }
            if (this.isHover((float)n, (float)n2, this.panel.x, this.y, this.width, this.height)) {
                bus.b(this.scaling = MathUtils.lerp(this.scaling, 1.055f, 0.25f), this.scaling, 0.0f);
                cFontRenderer.drawCenteredString(this.feature.getName(), (this.panel.x + this.panel.width / 2.0f) / this.scaling, (this.y - 1.0f) / this.scaling, RenderUtils.injectAlpha(color.brighter().getRGB(), this.feature.isEnabled() ? (132 + 184 - 130 + 69) : (134 + 57 - 101 + 60)).getRGB());
            }
            else {
                this.scaling = 1.0f;
                cFontRenderer.drawCenteredString(this.feature.getName(), (this.panel.x + this.panel.width / 2.0f) / this.scaling, (this.y - 1.0f) / this.scaling, RenderUtils.injectAlpha(color.getRGB(), this.feature.isEnabled() ? (161 + 129 - 195 + 160) : (30 + 77 + 26 + 17)).getRGB());
            }
        }
        GL11.glPopMatrix();
        GL11.glPushMatrix();
        if (this.isOpen) {
            float n5 = 0.0f;
            for (final Element element2 : this.elements.stream().filter(element -> element.displayOption.display).collect((Collector<? super Object, ?, List<? super Object>>)Collectors.toList())) {
                final float n6 = this.extendedLerpedHeight / this.getHeightExtended() * Easings.easeInOutQuart(this.openScaleFactor);
                GL11.glTranslatef((1.0f - n6) * this.x, (1.0f - n6) * this.y, 0.0f);
                GL11.glScaled((double)n6, (double)n6, 1.0);
                element2.x = this.x;
                element2.y = this.y + this.height + n5;
                element2.width = this.panel.width - 2.0f;
                n5 += element2.height;
                element2.render(n, n2, n3);
            }
        }
        GL11.glPopMatrix();
        super.render(n, n2, n3);
    }
    
    public boolean isOpen() {
        return this.isOpen;
    }
    
    public ArrayList<Element> getElements() {
        return this.elements;
    }
    
    public ElementButton(final Feature feature, final Panel panel) {
        super(panel);
        this.elements = new ArrayList<Element>();
        this.scaling = 1.0f;
        this.openScaleFactor = 0.0f;
        this.feature = feature;
        this.width = panel.width - 2.0f;
        this.height = 12.0f;
        for (final Option option : feature.options) {
            if (option instanceof OptionMode) {
                this.elements.add(new ElementMode(panel, (OptionMode)option, this));
            }
            if (option instanceof OptionBoolean) {
                this.elements.add(new ElementCheckBox(panel, (OptionBoolean)option, this));
            }
            if (option instanceof OptionSlider) {
                this.elements.add(new ElementSlider(panel, (OptionSlider)option, this));
            }
            if (option instanceof OptionColor) {
                final ElementColor elementColor;
                this.elements.add(elementColor = new ElementColor(panel, (OptionColor)option, this));
                ((OptionColor)option).setColor(((OptionColor)option).getColor());
                elementColor.setColor(((OptionColor)option).getColor());
                elementColor.alpha = (float)((OptionColor)option).alpha;
                elementColor.clampAlpha = ((OptionColor)option).alpha / 255.0f;
                final Color color = new Color(((OptionColor)option).getColor().getRGB());
                elementColor.clampBrightness = Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), null)[2];
            }
        }
    }
    
    @Override
    public void keyPressed(final char c, final int key) {
        if (this.isOpen()) {
            this.elements.forEach(element -> element.keyPressed(c, key));
        }
        if ((1 == key || (0x1C ^ 0x25) == key) && this.isBinding()) {
            this.feature.setKey(0);
            this.setBinding(false);
        }
        if (this.isBinding()) {
            this.feature.setKey(key);
            this.setBinding(false);
        }
        super.keyPressed(c, key);
    }
    
    public boolean isBinding() {
        return this.binding;
    }
    
    public float getElementsHeight() {
        float n = 0.0f;
        for (final Element element : this.elements) {
            n += (element.isOpen ? element.height : 0.0f);
        }
        return n;
    }
    
    @Override
    public void mouseClicked(final int n, final int n2, final int n3) {
        if (this.isOpen()) {
            this.elements.forEach(element3 -> element3.mouseClicked(n, n2, n3));
        }
        if (this.isHover((float)n, (float)n2, this.panel.x, this.y, this.width, this.height)) {
            if (!Keyboard.isKeyDown(0x7D ^ 0x57)) {
                switch (n3) {
                    case 0: {
                        this.feature.toggle();
                        break;
                    }
                    case 1: {
                        if (this.getElements().size() == 0) {
                            break;
                        }
                        if (!(this.isOpen = !this.isOpen)) {
                            this.elements.stream().filter(element -> element instanceof ElementSlider).forEach(elementSlider -> elementSlider.clamp = 0.0f);
                            this.elements.stream().filter(element2 -> element2 instanceof ElementMode).forEach(elementMode -> elementMode.lerpedWidth = 0.0f);
                            break;
                        }
                        break;
                    }
                    case 2: {
                        this.setBinding(true);
                        ClickGuiScreen.panels.forEach(panel -> panel.buttons.stream().filter(Objects::nonNull).filter(elementButton2 -> elementButton2 != this).forEach(elementButton -> elementButton.binding = false));
                        break;
                    }
                }
            }
            else {
                this.feature.setHidden(!this.feature.isHidden());
            }
        }
        super.mouseClicked(n, n2, n3);
    }
    
    public void setBinding(final boolean binding) {
        this.binding = binding;
    }
    
    public float getHeightExtended() {
        float n = 0.0f;
        final Iterator<Element> iterator = this.elements.stream().filter(element -> element.displayOption.display).collect((Collector<? super Object, ?, List<? super Object>>)Collectors.toList()).iterator();
        while (iterator.hasNext()) {
            n += iterator.next().height;
        }
        return n;
    }
    
    public void setOpen(final boolean isOpen) {
        this.isOpen = isOpen;
    }
    
    public Feature getFeature() {
        return this.feature;
    }
}
