/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.scripts.interpreter;

import mpp.venusfr.scripts.interpreter.LuaValue;

public final class UpValue {
    LuaValue[] array;
    int index;

    public UpValue(LuaValue[] luaValueArray, int n) {
        this.array = luaValueArray;
        this.index = n;
    }

    public String toString() {
        return this.index + "/" + this.array.length + " " + this.array[this.index];
    }

    public String tojstring() {
        return this.array[this.index].tojstring();
    }

    public LuaValue getValue() {
        return this.array[this.index];
    }

    public void setValue(LuaValue luaValue) {
        this.array[this.index] = luaValue;
    }

    public void close() {
        LuaValue[] luaValueArray = this.array;
        this.array = new LuaValue[]{luaValueArray[this.index]};
        luaValueArray[this.index] = null;
        this.index = 0;
    }
}

