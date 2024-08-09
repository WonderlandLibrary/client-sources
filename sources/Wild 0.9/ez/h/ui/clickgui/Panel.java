package ez.h.ui.clickgui;

import ez.h.features.*;
import ez.h.ui.clickgui.element.*;
import org.lwjgl.input.*;
import ez.h.features.visual.*;
import org.lwjgl.opengl.*;
import ez.h.utils.*;
import java.awt.*;
import ez.h.ui.fonts.*;
import java.util.*;

public class Panel implements IEventListener
{
    float prevY;
    public boolean isOpen;
    public float offset;
    public float height;
    public float width;
    public int alpha;
    public float x;
    Counter counter;
    public Category category;
    public float y;
    public float x1;
    public float motionX;
    public boolean isDragging;
    float easingTest;
    float prevX;
    public float y1;
    public int openedArea;
    public float motionY;
    float buttonsY;
    public List<ElementButton> buttons;
    
    @Override
    public void render(final int n, final int n2, final float n3) {
        final bit bit = new bit(bib.z());
        if (Keyboard.isKeyDown(0x1C ^ 0x1)) {
            if (Keyboard.isKeyDown(157 + 192 - 192 + 46)) {
                this.x -= 3.0f;
            }
            if (Keyboard.isKeyDown(126 + 76 - 138 + 141)) {
                this.x += 3.0f;
            }
            if (Keyboard.isKeyDown(44 + 7 + 51 + 98)) {
                this.y -= 3.0f;
            }
            if (Keyboard.isKeyDown(84 + 79 - 11 + 56)) {
                this.y += 3.0f;
            }
        }
        if (this.counter.hasReached(80.0f)) {
            this.buttons.forEach(elementButton -> elementButton.getFeature().updateElements());
            this.counter.reset();
        }
        if (this.isDragging) {
            this.x = n + this.x1;
            this.y = n2 + this.y1;
        }
        this.x = rk.a(this.x, 0.0f, ScaleUtils.getScale(bit.a()));
        for (final ElementButton elementButton3 : this.buttons) {
            elementButton3.y = this.buttons.indexOf(elementButton3) * elementButton3.height + this.height + this.y;
            elementButton3.x = this.x + 1.0f;
        }
        Color color;
        if (ClickGUI.easing.enabled) {
            color = Utils.getGradientOffset(ClickGUI.clickGUIColor.getColor(), ClickGUI.easingColor.getColor(), rk.a(this.y * 2.0f, 0.0f, (float)bib.z().e) / bib.z().e, 134 + 217 - 286 + 190);
        }
        else {
            color = ClickGUI.clickGUIColor.getColor();
        }
        final String name = this.category.name();
        final String string = Character.toUpperCase(name.charAt(0)) + name.substring(1).toLowerCase();
        int n4 = (int)this.height;
        for (final ElementButton elementButton4 : this.buttons) {
            n4 += (int)elementButton4.height;
            if (elementButton4.isOpen) {
                n4 += (int)elementButton4.getHeightExtended();
            }
        }
        this.buttonsY = MathUtils.lerp(this.buttonsY, this.isOpen ? 0.0f : ((float)n4), 0.2f);
        this.openedArea = (int)AnimUtils.lerp((float)this.openedArea, (float)n4, 0.1f);
        if (this.isOpen) {
            this.alpha = (int)AnimUtils.lerp((float)this.alpha, 110.0f, 0.11f);
        }
        else {
            this.alpha = (int)AnimUtils.lerp((float)this.alpha, 0.0f, 0.11f);
        }
        bus.G();
        GL11.glEnable(680 + 485 - 116 + 2040);
        bus.c(0.0f, -this.buttonsY, 0.0f);
        RenderUtils.setupScissor(this.x, this.y + this.height, this.width, this.openedArea + this.height);
        this.buttons.forEach(elementButton2 -> elementButton2.render(n, n2, n3));
        GL11.glDisable(614 + 2839 - 1472 + 1108);
        bus.H();
        RenderUtils.drawBlurredShadow(this.x, this.y, this.width, this.height, 5, new Color(0, 0, 0, 0xE7 ^ 0xA1));
        RenderUtils.drawRectWH(this.x, this.y, this.width, this.height, new Color(0, 0, 0, 0x67 ^ 0x5B).getRGB());
        CFontManager.rany.drawCenteredString(string, this.x + this.width / 2.0f, this.y + 6.0f, RenderUtils.injectAlpha(color.getRGB(), this.alpha + (0xC9 ^ 0xAD)).getRGB());
        if (this.isOpen) {
            RenderUtils.drawGradientRectWH(this.x + 2.0f, this.y + this.height, this.width - 4.0f, 3.0, new Color(0, 0, 0, 0xCA ^ 0x8C).getRGB(), 0);
        }
        if (ClickGUI.icons.enabled) {
            GL11.glPushMatrix();
            bus.e();
            bus.m();
            bus.a(bus.r.l, bus.l.j);
            bus.a(bus.r.l, bus.l.j, bus.r.e, bus.l.n);
            bib.z().N().a(new nf("wild/icons/" + this.category.name().toLowerCase() + ".png"));
            RenderUtils.color(color.getRGB());
            bir.a((int)this.x + 3, (int)this.y + 3, 0.0f, 0.0f, 126 + 38 - 83 + 144, 203 + 190 - 309 + 141, 0x16 ^ 0x19, 0x91 ^ 0x9E, 225.0f, 225.0f);
            bus.I();
            GL11.glPopMatrix();
        }
        this.prevX = this.x;
        this.prevY = this.y;
    }
    
    public static boolean isHover(final float n, final float n2, final float n3, final float n4, final float n5, final float n6) {
        return n >= n3 && n <= n3 + n5 && n2 >= n4 && n2 <= n4 + n6;
    }
    
    @Override
    public void mouseClicked(final int n, final int n2, final int n3) {
        if (this.isOpen) {
            this.buttons.forEach(elementButton -> elementButton.mouseClicked(n, n2, n3));
        }
        if (isHover((float)n, (float)n2, this.x, this.y, this.width, this.height)) {
            switch (n3) {
                case 0: {
                    this.isDragging = true;
                    this.x1 = this.x - n;
                    this.y1 = this.y - n2;
                    break;
                }
                case 1: {
                    this.isOpen = !this.isOpen;
                    break;
                }
            }
        }
    }
    
    @Override
    public void mouseRealesed(final int n, final int n2, final int n3) {
        this.isDragging = false;
        this.buttons.forEach(elementButton -> elementButton.mouseRealesed(n, n2, n3));
    }
    
    @Override
    public void keyPressed(final char c, final int n) {
        if (this.isOpen) {
            this.buttons.forEach(elementButton -> elementButton.keyPressed(c, n));
        }
    }
    
    public Panel(final Category category, final float width, final float height, final float n, final float n2) {
        this.buttons = new ArrayList<ElementButton>();
        this.counter = new Counter();
        this.category = category;
        this.width = width;
        this.height = height;
        this.x = n;
        this.y = n2;
        this.prevX = n;
        this.prevY = n2;
        this.buttonsY = (this.isOpen ? ((float)(this.buttons.size() * (0x45 ^ 0x49))) : 0.0f);
    }
}
