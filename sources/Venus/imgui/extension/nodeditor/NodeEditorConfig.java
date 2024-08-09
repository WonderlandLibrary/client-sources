/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package imgui.extension.nodeditor;

import imgui.binding.ImGuiStructDestroyable;

public final class NodeEditorConfig
extends ImGuiStructDestroyable {
    public NodeEditorConfig() {
    }

    public NodeEditorConfig(long l) {
        super(l);
    }

    @Override
    protected long create() {
        return this.nCreate();
    }

    private native long nCreate();

    public native String getSettingsFile();

    public native void setSettingsFile(String var1);
}

