/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.util;

import net.optifine.Config;

public class CompoundKey {
    private Object[] keys;
    private int hashcode = 0;

    public CompoundKey(Object[] objectArray) {
        this.keys = (Object[])objectArray.clone();
    }

    public CompoundKey(Object object, Object object2) {
        this(new Object[]{object, object2});
    }

    public CompoundKey(Object object, Object object2, Object object3) {
        this(new Object[]{object, object2, object3});
    }

    public CompoundKey(Object object, Object object2, Object object3, Object object4) {
        this(new Object[]{object, object2, object3, object4});
    }

    public int hashCode() {
        if (this.hashcode == 0) {
            this.hashcode = 7;
            for (int i = 0; i < this.keys.length; ++i) {
                Object object = this.keys[i];
                if (object == null) continue;
                this.hashcode = 31 * this.hashcode + object.hashCode();
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
        if (!(object instanceof CompoundKey)) {
            return true;
        }
        CompoundKey compoundKey = (CompoundKey)object;
        Object[] objectArray = compoundKey.getKeys();
        if (objectArray.length != this.keys.length) {
            return true;
        }
        for (int i = 0; i < this.keys.length; ++i) {
            if (CompoundKey.compareKeys(this.keys[i], objectArray[i])) continue;
            return true;
        }
        return false;
    }

    private static boolean compareKeys(Object object, Object object2) {
        if (object == object2) {
            return false;
        }
        if (object == null) {
            return true;
        }
        return object2 == null ? false : object.equals(object2);
    }

    private Object[] getKeys() {
        return this.keys;
    }

    public Object[] getKeysCopy() {
        return (Object[])this.keys.clone();
    }

    public String toString() {
        return "[" + Config.arrayToString(this.keys) + "]";
    }
}

