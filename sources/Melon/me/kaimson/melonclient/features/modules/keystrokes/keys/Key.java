package me.kaimson.melonclient.features.modules.keystrokes.keys;

import me.kaimson.melonclient.features.modules.keystrokes.*;
import org.lwjgl.input.*;
import java.awt.*;
import me.kaimson.melonclient.utils.*;
import me.kaimson.melonclient.gui.utils.*;

public class Key
{
    private final int gapSize;
    protected final avb keyBinding;
    protected final KeystrokesModule keystrokesModule;
    protected long pressTime;
    private boolean wasPressed;
    private float faded;
    
    public void render() {
        final boolean pressed = this.pressed();
        final float pressModifier = Math.min(1.0f, (System.currentTimeMillis() - this.pressTime) / (float)this.keystrokesModule.boxFade.getInt());
        final float brightness = (pressed ? pressModifier : (1.0f - pressModifier)) * 0.8f;
        this.renderBackground(pressed, brightness);
        final String keyname = Keyboard.getKeyName(this.keyBinding.i());
        this.drawString(keyname, (this.getWidth() - ave.A().k.a(keyname)) / 2.0f, (this.getHeight() - ave.A().k.a) / 2.0f + 1.0f, pressed);
    }
    
    public void renderBackground(final boolean pressed, final float brightness) {
        bfl.l();
        bfl.x();
        bfl.a(770, 771, 1, 0);
        final bfx tessellator = bfx.a();
        final bfd worldRenderer = tessellator.c();
        this.setBackgroundColor(pressed, brightness);
        worldRenderer.a(7, bms.e);
        worldRenderer.b(0.0, (double)this.getHeight(), 0.0).d();
        worldRenderer.b((double)this.getWidth(), (double)this.getHeight(), 0.0).d();
        worldRenderer.b((double)this.getWidth(), 0.0, 0.0).d();
        worldRenderer.b(0.0, 0.0, 0.0).d();
        tessellator.b();
        bfl.w();
        bfl.k();
        if (this.keystrokesModule.outline.getBoolean()) {
            final int color = this.getColor(this.keystrokesModule.outlineColor.getColorObject(), this.keystrokesModule.offset, pressed);
            final int color2 = this.getColor(this.keystrokesModule.outlineColor.getColorObject(), this.keystrokesModule.offset + this.getWidth(), pressed);
            GLRectUtils.drawGradientRect(0.0f, 0.0f, this.getWidth(), 1.0f, color, color2, color, color2, 0);
            GLRectUtils.drawGradientRect(this.getWidth() - 1.0f, 1.0f, this.getWidth(), this.getHeight() - 1.0f, color2, color2, color2, color2, 0);
            GLRectUtils.drawGradientRect(this.getWidth(), this.getHeight(), 0.0f, this.getHeight() - 1.0f, color2, color, color2, color, 0);
            GLRectUtils.drawGradientRect(1.0f, this.getHeight() - 1.0f, 0.0f, 1.0f, color, color, color, color, 0);
        }
    }
    
    public float getWidth() {
        return this.keystrokesModule.boxSize.getFloat();
    }
    
    public float getHeight() {
        return this.keystrokesModule.boxSize.getFloat();
    }
    
    protected boolean pressed() {
        final int keycode = this.keyBinding.i();
        final boolean pressed = (keycode < 0) ? Mouse.isButtonDown(keycode + 100) : Keyboard.isKeyDown(keycode);
        if (this.wasPressed != pressed) {
            this.pressTime = System.currentTimeMillis();
        }
        this.faded = (System.currentTimeMillis() - this.pressTime) / (float)this.keystrokesModule.boxFade.getInt();
        return this.wasPressed = pressed;
    }
    
    private void setBackgroundColor(final boolean pressed, final float brightness) {
        int bgColor = 0;
        int bgPressed = 0;
        final int converted = GuiUtils.convertPercentToValue(brightness);
        if (this.keystrokesModule.boxColor.getColor() != 0) {
            bgColor = this.keystrokesModule.boxColor.getColor();
        }
        if (this.keystrokesModule.boxPressedColor.getColor() != 0) {
            bgPressed = this.keystrokesModule.boxPressedColor.getColor();
        }
        if (bgColor == 0) {
            bgColor = new Color(converted, converted, converted, 102).getRGB();
        }
        if (bgPressed == 0) {
            bgPressed = new Color(converted, converted, converted, 102).getRGB();
        }
        final int bg = pressed ? bgPressed : bgColor;
        if (this.faded < 1.0f) {
            final int lastColor = pressed ? bgColor : bgPressed;
            GuiUtils.setGlColor(GuiUtils.getIntermediateColor(bg, lastColor, this.faded));
        }
        else {
            GuiUtils.setGlColor(bg);
        }
    }
    
    protected int getColor(final ColorObject colorObject, final float offset, final boolean invert) {
        if (!colorObject.isChroma()) {
            return colorObject.getColor();
        }
        if (invert) {
            return -16777216;
        }
        final float v = this.keystrokesModule.textColor.getColorObject().getActualChromaSpeed();
        final int i = new Color(Color.HSBtoRGB((float)((System.currentTimeMillis() - offset * 10.0 - this.keystrokesModule.offset * 10.0) % v) / v, 0.8f, 0.8f)).getRGB();
        return i;
    }
    
    protected void drawString(final String text, float x, final float y, final boolean invert) {
        final ColorObject colorObject = this.keystrokesModule.textColor.getColorObject();
        if (colorObject.isChroma()) {
            if (invert) {
                FontUtils.drawString(text, x, y, -16777216);
            }
            else {
                for (final char c : text.toCharArray()) {
                    final float v = this.keystrokesModule.textColor.getColorObject().getActualChromaSpeed();
                    final int i = new Color(Color.HSBtoRGB((float)((System.currentTimeMillis() - x * 10.0 - this.keystrokesModule.offset * 10.0) % v) / v, 0.8f, 0.8f)).getRGB();
                    final String tmp = String.valueOf(c);
                    FontUtils.drawString(tmp, x, y, i);
                    x += ave.A().k.a(tmp);
                }
            }
        }
        else {
            FontUtils.drawString(text, x, y, colorObject.getColor());
        }
    }
    
    public Key(final int gapSize, final avb keyBinding, final KeystrokesModule keystrokesModule) {
        this.gapSize = gapSize;
        this.keyBinding = keyBinding;
        this.keystrokesModule = keystrokesModule;
    }
}
