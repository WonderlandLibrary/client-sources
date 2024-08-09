/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package imgui.extension.imnodes;

import imgui.binding.ImGuiStructDestroyable;

public final class ImNodesContext
extends ImGuiStructDestroyable {
    public ImNodesContext() {
    }

    public ImNodesContext(long l) {
        super(l);
    }

    @Override
    protected long create() {
        return this.nCreate();
    }

    @Override
    public void destroy() {
        this.nDestroy();
    }

    private native long nCreate();

    private native void nDestroy();
}

