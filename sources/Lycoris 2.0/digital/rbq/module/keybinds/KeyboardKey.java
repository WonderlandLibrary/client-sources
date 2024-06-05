/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.lwjgl.input.Keyboard
 */
package digital.rbq.module.keybinds;

import org.lwjgl.input.Keyboard;

public class KeyboardKey {
    private String key;

    public KeyboardKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setKeyCode(int keyCode) {
        this.key = Keyboard.getKeyName((int)keyCode);
    }

    public int getKeyCode() {
        return Keyboard.getKeyIndex((String)this.key.toUpperCase());
    }
}

