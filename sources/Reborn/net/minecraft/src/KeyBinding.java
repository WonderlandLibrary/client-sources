package net.minecraft.src;

import java.util.*;

public class KeyBinding
{
    public static List keybindArray;
    public static IntHashMap hash;
    public String keyDescription;
    public int keyCode;
    public boolean pressed;
    public int pressTime;
    
    static {
        KeyBinding.keybindArray = new ArrayList();
        KeyBinding.hash = new IntHashMap();
    }
    
    public static void onTick(final int par0) {
        final KeyBinding var1 = (KeyBinding)KeyBinding.hash.lookup(par0);
        if (var1 != null) {
            final KeyBinding keyBinding = var1;
            ++keyBinding.pressTime;
        }
    }
    
    public static void setKeyBindState(final int par0, final boolean par1) {
        final KeyBinding var2 = (KeyBinding)KeyBinding.hash.lookup(par0);
        if (var2 != null) {
            var2.pressed = par1;
        }
    }
    
    public static void unPressAllKeys() {
        for (final KeyBinding var2 : KeyBinding.keybindArray) {
            var2.unpressKey();
        }
    }
    
    public static void resetKeyBindingArrayAndHash() {
        KeyBinding.hash.clearMap();
        for (final KeyBinding var2 : KeyBinding.keybindArray) {
            KeyBinding.hash.addKey(var2.keyCode, var2);
        }
    }
    
    public KeyBinding(final String par1Str, final int par2) {
        this.pressTime = 0;
        this.keyDescription = par1Str;
        this.keyCode = par2;
        KeyBinding.keybindArray.add(this);
        KeyBinding.hash.addKey(par2, this);
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
}
