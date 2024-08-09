/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package imgui.internal;

import imgui.binding.ImGuiStruct;

public class ImGuiWindow
extends ImGuiStruct {
    public ImGuiWindow(long l) {
        super(l);
    }

    public native boolean isScrollbarX();

    public native boolean isScrollbarY();
}

