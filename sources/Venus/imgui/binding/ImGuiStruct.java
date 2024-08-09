/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package imgui.binding;

import imgui.ImGui;

public abstract class ImGuiStruct {
    public long ptr;

    public ImGuiStruct(long l) {
        ImGui.init();
        this.ptr = l;
    }

    public final boolean isValidPtr() {
        return this.ptr != 0L;
    }

    public final boolean isNotValidPtr() {
        return !this.isValidPtr();
    }
}

