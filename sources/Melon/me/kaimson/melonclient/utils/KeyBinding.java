package me.kaimson.melonclient.utils;

import java.util.*;
import com.google.common.collect.*;

public class KeyBinding
{
    public static final List<KeyBinding> keyBindings;
    private int keyCode;
    private boolean pressed;
    private int pressTime;
    
    public KeyBinding(final int keyCode) {
        this.keyCode = keyCode;
        KeyBinding.keyBindings.add(this);
    }
    
    public void onTick() {
        ++this.pressTime;
    }
    
    public void setKeyBindState(final int keyCode, final boolean pressed) {
        this.pressed = pressed;
    }
    
    public boolean isKeyDown() {
        return this.pressed;
    }
    
    public boolean isPressed() {
        if (this.pressTime == 0) {
            return false;
        }
        --this.pressTime;
        return true;
    }
    
    private void unpressKey() {
        this.pressTime = 0;
        this.pressed = false;
    }
    
    public int getKeyCode() {
        return this.keyCode;
    }
    
    static {
        keyBindings = Lists.newArrayList();
    }
}
