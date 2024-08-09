/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package imgui;

import imgui.binding.ImGuiStructDestroyable;

public final class ImGuiTextFilter
extends ImGuiStructDestroyable {
    public ImGuiTextFilter() {
        this("");
    }

    public ImGuiTextFilter(String string) {
        this.ptr = this.nCreate(string);
    }

    ImGuiTextFilter(long l) {
        super(l);
    }

    @Override
    protected long create() {
        return this.nCreate("");
    }

    private native long nCreate(String var1);

    public boolean draw() {
        return this.draw("Filter (inc,-exc)");
    }

    public boolean draw(String string) {
        return this.draw(string, 0.0f);
    }

    public native boolean draw(String var1, float var2);

    public native boolean passFilter(String var1);

    public native void build();

    public native void clear();

    public native boolean isActive();

    public native String getInputBuffer();

    public native void setInputBuffer(String var1);
}

