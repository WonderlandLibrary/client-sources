package me.kaimson.melonclient.features.modules.keystrokes.keys;

import me.kaimson.melonclient.features.modules.keystrokes.*;
import me.kaimson.melonclient.gui.utils.*;

public class KeySpacebar extends Key
{
    public KeySpacebar(final int gapSize, final avb keyBinding, final KeystrokesModule keystrokesModule) {
        super(gapSize, keyBinding, keystrokesModule);
    }
    
    @Override
    public void render() {
        final boolean pressed = this.pressed();
        final float pressModifier = Math.min(1.0f, (System.currentTimeMillis() - this.pressTime) / (float)this.keystrokesModule.boxFade.getInt());
        final float brightness = (pressed ? pressModifier : (1.0f - pressModifier)) * 0.8f;
        this.renderBackground(pressed, brightness);
        final float x = this.getWidth() / 2.0f - 10.0f;
        final float x2 = this.getWidth() / 2.0f + 10.0f;
        GLRectUtils.drawGradientRect((int)x, 3, (int)x2, 4, this.getColor(this.keystrokesModule.textColor.getColorObject(), x, pressed), this.getColor(this.keystrokesModule.textColor.getColorObject(), x2, pressed), this.getColor(this.keystrokesModule.textColor.getColorObject(), x, pressed), this.getColor(this.keystrokesModule.textColor.getColorObject(), x2, pressed), 0);
    }
    
    @Override
    public float getWidth() {
        final float n = super.getWidth() * 3.0f;
        this.keystrokesModule.getClass();
        return n + 2 * 2;
    }
    
    @Override
    public float getHeight() {
        return super.getHeight() / 2.0f;
    }
}
