/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.util;

import net.optifine.Config;

public class CompoundIntKey {
    private int[] keys;
    private int hashcode = 0;

    public CompoundIntKey(int[] nArray) {
        this.keys = (int[])nArray.clone();
    }

    public CompoundIntKey(int n, int n2) {
        this(new int[]{n, n2});
    }

    public CompoundIntKey(int n, int n2, int n3) {
        this(new int[]{n, n2, n3});
    }

    public CompoundIntKey(int n, int n2, int n3, int n4) {
        this(new int[]{n, n2, n3, n4});
    }

    public int hashCode() {
        if (this.hashcode == 0) {
            this.hashcode = 7;
            for (int i = 0; i < this.keys.length; ++i) {
                int n = this.keys[i];
                this.hashcode = 31 * this.hashcode + n;
            }
        }
        return this.hashcode;
    }

    public boolean equals(Object object) {
        if (object == null) {
            return true;
        }
        if (object == this) {
            return false;
        }
        if (!(object instanceof CompoundIntKey)) {
            return true;
        }
        CompoundIntKey compoundIntKey = (CompoundIntKey)object;
        int[] nArray = compoundIntKey.getKeys();
        if (nArray.length != this.keys.length) {
            return true;
        }
        for (int i = 0; i < this.keys.length; ++i) {
            if (this.keys[i] == nArray[i]) continue;
            return true;
        }
        return false;
    }

    private int[] getKeys() {
        return this.keys;
    }

    public int[] getKeysCopy() {
        return (int[])this.keys.clone();
    }

    public String toString() {
        return "[" + Config.arrayToString(this.keys) + "]";
    }
}

