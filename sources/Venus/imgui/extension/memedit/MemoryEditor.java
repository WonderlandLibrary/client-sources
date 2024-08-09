/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package imgui.extension.memedit;

import imgui.binding.ImGuiStructDestroyable;
import imgui.extension.memedit.MemoryEditorSizes;

public final class MemoryEditor
extends ImGuiStructDestroyable {
    public MemoryEditor() {
    }

    public MemoryEditor(long l) {
        super(l);
    }

    @Override
    protected long create() {
        return this.nCreate();
    }

    private native long nCreate();

    public native void gotoAddrAndHighlight(long var1, long var3);

    public void calcSizes(MemoryEditorSizes memoryEditorSizes, long l, long l2) {
        this.nCalcSizes(memoryEditorSizes.ptr, l, l2);
    }

    public native void nCalcSizes(long var1, long var3, long var5);

    public void drawWindow(String string, long l, long l2) {
        this.drawWindow(string, l, l2, 0L);
    }

    public native void drawWindow(String var1, long var2, long var4, long var6);

    public void drawContents(long l, long l2) {
        this.drawContents(l, l2, 0L);
    }

    public native void drawContents(long var1, long var3, long var5);

    public void drawOptionsLine(MemoryEditorSizes memoryEditorSizes, long l, long l2, long l3) {
        this.nDrawOptionsLine(memoryEditorSizes.ptr, l, l2, l3);
    }

    public native void nDrawOptionsLine(long var1, long var3, long var5, long var7);

    public void drawPreviewLine(MemoryEditorSizes memoryEditorSizes, long l, long l2, long l3) {
        this.nDrawPreviewLine(memoryEditorSizes.ptr, l, l2, l3);
    }

    public native void nDrawPreviewLine(long var1, long var3, long var5, long var7);
}

