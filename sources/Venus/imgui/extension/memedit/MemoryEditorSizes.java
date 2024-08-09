/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package imgui.extension.memedit;

import imgui.binding.ImGuiStructDestroyable;

public final class MemoryEditorSizes
extends ImGuiStructDestroyable {
    public MemoryEditorSizes() {
    }

    public MemoryEditorSizes(long l) {
        super(l);
    }

    @Override
    protected long create() {
        return this.nCreate();
    }

    private native long nCreate();

    public native void setAddrDigitsCount(int var1);

    public native int getAddrDigitsCount();

    public native void setLineHeight(float var1);

    public native float getLineHeight();

    public native void setGlyphWidth(float var1);

    public native float getGlyphWidth();

    public native void setHexCellWidth(float var1);

    public native float getHexCellWidth();

    public native void setSpacingBetweenMidCols(float var1);

    public native float getSpacingBetweenMidCols();

    public native void setPosHexStart(float var1);

    public native float getPosHexStart();

    public native void setPosHexEnd(float var1);

    public native float getPosHexEnd();

    public native void setPosAsciiStart(float var1);

    public native float getPosAsciiStart();

    public native void setPosAsciiEnd(float var1);

    public native float getPosAsciiEnd();

    public native void setWindowWidth(float var1);

    public native float getWindowWidth();
}

