/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package imgui;

import imgui.binding.ImGuiStructDestroyable;

public final class ImFontGlyph
extends ImGuiStructDestroyable {
    public ImFontGlyph() {
    }

    public ImFontGlyph(long l) {
        super(l);
    }

    @Override
    protected long create() {
        return this.nCreate();
    }

    private native long nCreate();

    public native int getColored();

    public native void setColored(int var1);

    public native int getVisible();

    public native void setVisible(int var1);

    public native int getCodepoint();

    public native void setCodepoint(int var1);

    public native float getAdvanceX();

    public native void setAdvanceX(float var1);

    public native float getX0();

    public native void setX0(float var1);

    public native float getY0();

    public native void setY0(float var1);

    public native float getX1();

    public native void setX1(float var1);

    public native float getY1();

    public native void setY1(float var1);

    public native float getU0();

    public native void setU0(float var1);

    public native float getV0();

    public native void setV0(float var1);

    public native float getU1();

    public native void setU1(float var1);

    public native float getV1();

    public native void setV1(float var1);
}

