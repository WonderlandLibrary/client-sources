/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package imgui;

import imgui.binding.ImGuiStructDestroyable;

public final class ImGuiStorage
extends ImGuiStructDestroyable {
    public ImGuiStorage() {
    }

    public ImGuiStorage(long l) {
        super(l);
    }

    @Override
    protected long create() {
        return this.nCreate();
    }

    private native long nCreate();

    public native void clear();

    public native int getInt(int var1);

    public native int getInt(int var1, int var2);

    public native void setInt(int var1, int var2);

    public native boolean getBool(int var1);

    public native boolean getBool(int var1, boolean var2);

    public native void setBool(int var1, boolean var2);

    public native float getFloat(int var1);

    public native float getFloat(int var1, float var2);

    public native void setFloat(int var1, float var2);

    public native void setAllInt(int var1);

    public native void buildSortByKey();
}

