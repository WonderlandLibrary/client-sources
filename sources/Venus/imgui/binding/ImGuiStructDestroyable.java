/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package imgui.binding;

import imgui.binding.ImGuiStruct;

public abstract class ImGuiStructDestroyable
extends ImGuiStruct {
    public ImGuiStructDestroyable() {
        this(0L);
        this.ptr = this.create();
    }

    public ImGuiStructDestroyable(long l) {
        super(l);
    }

    protected abstract long create();

    public void destroy() {
        this.nDestroy(this.ptr);
    }

    private native void nDestroy(long var1);
}

