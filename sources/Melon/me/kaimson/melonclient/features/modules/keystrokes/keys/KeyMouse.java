package me.kaimson.melonclient.features.modules.keystrokes.keys;

import me.kaimson.melonclient.features.modules.keystrokes.*;
import me.kaimson.melonclient.*;

public class KeyMouse extends Key
{
    private Type type;
    
    public KeyMouse(final int gapSize, final avb keyBinding, final KeystrokesModule keystrokesModule) {
        super(gapSize, keyBinding, keystrokesModule);
    }
    
    @Override
    public void render() {
        final boolean pressed = this.pressed();
        final float pressModifier = Math.min(1.0f, (System.currentTimeMillis() - this.pressTime) / (float)this.keystrokesModule.boxFade.getInt());
        final float brightness = (pressed ? pressModifier : (1.0f - pressModifier)) * 0.8f;
        this.renderBackground(pressed, brightness);
        final String keyname = (this.type == Type.LEFT) ? "LMB" : "RMB";
        final boolean cps = this.keystrokesModule.showCPS.getBoolean();
        this.drawString(keyname, (this.getWidth() - ave.A().k.a(keyname)) / 2.0f, cps ? (this.getHeight() / 5.0f) : (this.getHeight() / 2.0f - ave.A().k.a / 2.0f), pressed);
        if (cps) {
            bfl.E();
            final float cpsScale = 0.65f;
            bfl.a(cpsScale, cpsScale, 1.0f);
            final String cpsText = ((this.type == Type.LEFT) ? Client.left.getCPS() : Client.right.getCPS()) + " CPS";
            this.drawString(cpsText, (this.getWidth() / cpsScale - ave.A().k.a(cpsText)) / 2.0f, this.getHeight() / cpsScale - this.getHeight() / 5.0f - ave.A().k.a, pressed);
            bfl.F();
        }
    }
    
    @Override
    public float getWidth() {
        final float n = super.getWidth() + super.getWidth() / 2.0f;
        this.keystrokesModule.getClass();
        return n + 2.0f / 2.0f;
    }
    
    @Override
    public float getHeight() {
        return super.getHeight() + super.getHeight() / 5.0f;
    }
    
    public KeyMouse setLeft() {
        this.type = Type.LEFT;
        return this;
    }
    
    public KeyMouse setRight() {
        this.type = Type.RIGHT;
        return this;
    }
    
    public enum Type
    {
        LEFT, 
        RIGHT;
    }
}
