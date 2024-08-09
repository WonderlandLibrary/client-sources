package ez.h.ui.clickgui.element;

import ez.h.ui.clickgui.options.*;
import ez.h.ui.clickgui.*;
import java.awt.*;
import ez.h.ui.fonts.*;
import org.lwjgl.input.*;
import ez.h.features.visual.*;
import ez.h.utils.*;
import org.lwjgl.opengl.*;

public class ElementColor extends Element
{
    ElementButton button;
    public float alpha;
    double deltaXmouse;
    float clampBrightness;
    float clampAlpha;
    public boolean isDraggingBrightness;
    public OptionColor option;
    Panel panel;
    double deltaYmouse;
    public boolean isDraggingAlpha;
    public float brightness;
    
    @Override
    public void render(int n, final int n2, final float n3) {
        final bit bit = new bit(bib.z());
        RenderUtils.drawRect(this.x + 3.0f, this.y, this.x + this.width - 3.0f, this.y + this.height, new Color(0x4A ^ 0x40, 0xA ^ 0x0, 0x15 ^ 0x1F, 80 + 114 - 169 + 175).getRGB());
        CFontManager.manropesmall.drawCenteredString(this.option.getName(), this.x + this.width / 2.0f, this.y - 1.0f, RenderUtils.injectAlpha(-1, 105 + 67 - 142 + 120).getRGB());
        bus.G();
        RenderUtils.drawImg(new nf("wild/cpicker.png"), this.x + 28.0f - 20.0f, this.y + this.height / 2.0f + 2.0f - 20.0f, 40.0, 40.0);
        bus.H();
        RenderUtils.drawGradientRect(this.x + 3.0f, this.y + this.height - 10.0f, this.x + this.width - 3.0f, this.y + this.height, 0, this.option.getColor().getRGB());
        boolean b = false;
        if (Mouse.isButtonDown(0) && !this.isDraggingAlpha && !this.isDraggingBrightness && this.isPointInCircle(this.x + 28.0f, this.y + this.height / 2.0f + 2.0f, n, n2, 21.0)) {
            this.deltaXmouse = n - (this.x + 28.0f);
            this.deltaYmouse = n2 - (this.y + this.height / 2.0f + 2.0f);
            this.option.setColor(new Color(this.getColor()));
            b = true;
        }
        RenderUtils.drawCircleOut(this.x + this.deltaXmouse + this.width / 2.0f - 23.0, this.y + this.deltaYmouse + this.height / 2.0f + 2.0, 2.0, 0.0, 360.0f, 2.0f, new Color(0, 0, 0, 0xAF ^ 0xBB).getRGB());
        final float n4 = (float)n;
        n -= (int)this.x;
        if (this.isDraggingBrightness && !this.isDraggingAlpha) {
            this.clampBrightness = rk.a((n - (0x6F ^ 0x5D)) / (this.width / 2.0f), 0.0f, 1.0f);
            this.option.setColor(new Color(this.getColor()));
        }
        if (this.isDraggingAlpha && !this.isDraggingBrightness && this.option.needAlpha) {
            this.clampAlpha = rk.a((n - (0x30 ^ 0x2)) / (this.width / 2.0f), 0.0f, 1.0f);
            this.option.setColor(new Color(this.getColor()));
            this.option.alpha = (int)this.alpha;
        }
        this.alpha = (this.option.needAlpha ? (this.clampAlpha * 255.0f) : 255.0f);
        this.brightness = this.clampBrightness * 255.0f;
        final float a = rk.a(this.y * 2.0f, 0.0f, (float)bib.z().e);
        Color color;
        if (ClickGUI.easing.enabled) {
            color = Utils.getGradientOffset(ClickGUI.clickGUIColor.getColor(), ClickGUI.easingColor.getColor(), a / bib.z().e, 43 + 79 + 80 + 53);
        }
        else {
            color = ClickGUI.clickGUIColor.getColor();
        }
        bus.G();
        CFontManager.manropesmall.drawStringWithShadow("Bright", this.x + 52.0f, this.y + this.height / 2.0f - 20.0f, RenderUtils.injectAlpha(-1, 77 + 25 + 23 + 25).getRGB());
        final int rgb = color.getRGB();
        RenderUtils.drawRect(this.x + 50.0f, this.y + this.height / 2.0f - 5.0f, this.x + this.width - 7.0f, this.y + this.height / 2.0f - 3.0f, rgb);
        drawCircle(this.x + 50.0f + this.clampBrightness * 45.0f, this.y + this.height / 2.0f - 4.0f, 2.0, new Color(rgb).darker().getRGB());
        if (this.option.needAlpha) {
            CFontManager.manropesmall.drawStringWithShadow("Alpha", this.x + 52.0f, this.y + this.height / 2.0f - 2.0f, RenderUtils.injectAlpha(-1, 148 + 136 - 221 + 87).getRGB());
            RenderUtils.drawRect(this.x + 50.0f, this.y + this.height / 2.0f + 13.0f, this.x + this.width - 7.0f, this.y + this.height / 2.0f + 15.0f, rgb);
            drawCircle(this.x + 50.0f + this.clampAlpha * 45.0f, this.y + this.height / 2.0f + 14.0f, 2.0, new Color(rgb).darker().getRGB());
        }
        bus.I();
        bus.H();
        if (b) {
            RenderUtils.drawRoundedRect(n4 - 25.0f, n2 - (0x84 ^ 0x95), 50.0, 11.0, 6.0, Integer.MIN_VALUE);
            CFontManager.manropesmall.drawStringWithShadow("#" + Integer.toHexString(this.option.getColor().getRGB()).toUpperCase(), n4 - 22.0f, n2 - 17.5f, -1);
        }
    }
    
    public ElementColor(final Panel panel, final OptionColor optionColor, final ElementButton button) {
        super(panel);
        this.brightness = 255.0f;
        this.alpha = 255.0f;
        this.clampBrightness = 1.0f;
        this.clampAlpha = 1.0f;
        this.panel = panel;
        this.option = optionColor;
        this.button = button;
        this.height = 60.0f;
        this.alpha = (float)optionColor.alpha;
        this.displayOption = optionColor;
    }
    
    float getSaturation() {
        return (float)(Math.hypot(this.deltaXmouse, this.deltaYmouse) / 20.0);
    }
    
    float getHue() {
        return (float)(-(Math.toDegrees(Math.atan2(this.deltaYmouse, this.deltaXmouse)) + 270.0) % 360.0) / 360.0f;
    }
    
    String toHex(final Color color) {
        return "";
    }
    
    boolean isPointInCircle(final double n, final double n2, final double n3, final double n4, final double n5) {
        return (n3 - n) * (n3 - n) + (n4 - n2) * (n4 - n2) <= n5 * n5;
    }
    
    @Override
    public void mouseRealesed(final int n, final int n2, final int n3) {
        this.isDraggingBrightness = false;
        this.isDraggingAlpha = false;
        super.mouseRealesed(n, n2, n3);
    }
    
    public static void drawCircle(final double n, final double n2, final double n3, final int n4) {
        GL11.glPushMatrix();
        GL11.glEnable(1150 + 244 + 1488 + 160);
        GL11.glDisable(2356 + 477 - 1260 + 1356);
        GL11.glBlendFunc(274 + 248 - 211 + 459, 616 + 727 - 1068 + 496);
        GL11.glDisable(1529 + 1471 + 419 + 134);
        GL11.glBegin(3);
        for (float n5 = 0.0f; n5 < 360.0f; n5 += 0.2f) {
            RenderUtils.color(n4);
            GL11.glVertex2d(n, n2);
            GL11.glVertex2d(n + Math.sin(Math.toRadians(n5)) * n3, n2 + Math.cos(Math.toRadians(n5)) * n3);
        }
        GL11.glEnd();
        GL11.glEnable(3399 + 1123 - 4289 + 3320);
        GL11.glDisable(112 + 1923 - 164 + 1171);
        GL11.glEnable(814 + 1084 - 1109 + 2140);
        GL11.glPopMatrix();
    }
    
    void setColor(final Color color) {
        final float[] rgBtoHSB = Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), null);
        this.deltaXmouse = rgBtoHSB[1] * 20.0f * (Math.sin(Math.toRadians(rgBtoHSB[0] * 360.0f)) / Math.sin(Math.toRadians(90.0)));
        this.deltaYmouse = rgBtoHSB[1] * 20.0f * (Math.sin(Math.toRadians(90.0f - rgBtoHSB[0] * 360.0f)) / Math.sin(Math.toRadians(90.0)));
    }
    
    int getColor() {
        return RenderUtils.injectAlpha(Color.getHSBColor(this.getHue(), this.getSaturation(), this.brightness / 255.0f).getRGB(), (int)this.alpha / (142 + 155 - 215 + 173)).getRGB();
    }
    
    @Override
    public void mouseClicked(final int n, final int n2, final int n3) {
        if (this.isHover((float)n, (float)n2, this.x + 50.0f, this.y + this.height / 2.0f - 7.0f, this.width - 5.0f, 5.0f) && n3 == 0) {
            this.isDraggingBrightness = true;
            this.isDraggingAlpha = false;
        }
        if (this.isHover((float)n, (float)n2, this.x + 50.0f, this.y + this.height / 2.0f - 7.0f + 18.0f, this.width - 5.0f, 5.0f) && n3 == 0 && this.option.needAlpha) {
            this.isDraggingAlpha = true;
            this.isDraggingBrightness = false;
        }
        super.mouseClicked(n, n2, n3);
    }
}
