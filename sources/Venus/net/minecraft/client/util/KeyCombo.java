/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.util;

import java.util.Arrays;

public class KeyCombo {
    private final char[] field_224801_a;
    private int field_224802_b;
    private final Runnable field_224803_c;

    public KeyCombo(char[] cArray, Runnable runnable) {
        this.field_224803_c = runnable;
        if (cArray.length < 1) {
            throw new IllegalArgumentException("Must have at least one char");
        }
        this.field_224801_a = cArray;
    }

    public boolean func_224799_a(char c) {
        if (c == this.field_224801_a[this.field_224802_b++]) {
            if (this.field_224802_b == this.field_224801_a.length) {
                this.func_224800_a();
                this.field_224803_c.run();
                return false;
            }
        } else {
            this.func_224800_a();
        }
        return true;
    }

    public void func_224800_a() {
        this.field_224802_b = 0;
    }

    public String toString() {
        return "KeyCombo{chars=" + Arrays.toString(this.field_224801_a) + ", matchIndex=" + this.field_224802_b + "}";
    }
}

