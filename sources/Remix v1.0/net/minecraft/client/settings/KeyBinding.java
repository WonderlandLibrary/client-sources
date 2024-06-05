package net.minecraft.client.settings;

import net.minecraft.util.*;
import java.util.*;
import net.minecraft.client.resources.*;
import com.google.common.collect.*;

public class KeyBinding implements Comparable
{
    private static final List keybindArray;
    private static final IntHashMap hash;
    private static final Set keybindSet;
    private final String keyDescription;
    private final int keyCodeDefault;
    private final String keyCategory;
    public boolean pressed;
    private int keyCode;
    private int pressTime;
    
    public KeyBinding(final String description, final int keyCode, final String category) {
        this.keyDescription = description;
        this.keyCode = keyCode;
        this.keyCodeDefault = keyCode;
        this.keyCategory = category;
        KeyBinding.keybindArray.add(this);
        KeyBinding.hash.addKey(keyCode, this);
        KeyBinding.keybindSet.add(category);
    }
    
    public static void onTick(final int keyCode) {
        if (keyCode != 0) {
            final KeyBinding var1 = (KeyBinding)KeyBinding.hash.lookup(keyCode);
            if (var1 != null) {
                final KeyBinding keyBinding = var1;
                ++keyBinding.pressTime;
            }
        }
    }
    
    public static void setKeyBindState(final int keyCode, final boolean pressed) {
        if (keyCode != 0) {
            final KeyBinding var2 = (KeyBinding)KeyBinding.hash.lookup(keyCode);
            if (var2 != null) {
                var2.pressed = pressed;
            }
        }
    }
    
    public static void unPressAllKeys() {
        for (final Object aKeybindArray : KeyBinding.keybindArray) {
            final KeyBinding var1 = (KeyBinding)aKeybindArray;
            var1.unpressKey();
        }
    }
    
    public static void resetKeyBindingArrayAndHash() {
        KeyBinding.hash.clearMap();
        for (final KeyBinding var2 : KeyBinding.keybindArray) {
            KeyBinding.hash.addKey(var2.keyCode, var2);
        }
    }
    
    public static Set getKeybinds() {
        return KeyBinding.keybindSet;
    }
    
    public boolean getIsKeyPressed() {
        return this.pressed;
    }
    
    public String getKeyCategory() {
        return this.keyCategory;
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
    
    public String getKeyDescription() {
        return this.keyDescription;
    }
    
    public int getKeyCodeDefault() {
        return this.keyCodeDefault;
    }
    
    public int getKeyCode() {
        return this.keyCode;
    }
    
    public void setKeyCode(final int keyCode) {
        this.keyCode = keyCode;
    }
    
    public int compareTo(final KeyBinding p_compareTo_1_) {
        int var2 = I18n.format(this.keyCategory, new Object[0]).compareTo(I18n.format(p_compareTo_1_.keyCategory, new Object[0]));
        if (var2 == 0) {
            var2 = I18n.format(this.keyDescription, new Object[0]).compareTo(I18n.format(p_compareTo_1_.keyDescription, new Object[0]));
        }
        return var2;
    }
    
    @Override
    public int compareTo(final Object p_compareTo_1_) {
        return this.compareTo((KeyBinding)p_compareTo_1_);
    }
    
    static {
        keybindArray = Lists.newArrayList();
        hash = new IntHashMap();
        keybindSet = Sets.newHashSet();
    }
}
