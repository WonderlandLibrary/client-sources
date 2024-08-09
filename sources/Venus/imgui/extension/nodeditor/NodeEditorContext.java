/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package imgui.extension.nodeditor;

import imgui.binding.ImGuiStructDestroyable;
import imgui.extension.nodeditor.NodeEditorConfig;

public final class NodeEditorContext
extends ImGuiStructDestroyable {
    public NodeEditorContext() {
    }

    public NodeEditorContext(NodeEditorConfig nodeEditorConfig) {
        this(NodeEditorContext.nCreate(nodeEditorConfig.ptr));
    }

    public NodeEditorContext(long l) {
        super(l);
    }

    @Override
    protected long create() {
        return this.nCreate();
    }

    @Override
    public void destroy() {
        this.nDestroyEditorContext();
    }

    private native long nCreate();

    private static native long nCreate(long var0);

    private native void nDestroyEditorContext();
}

