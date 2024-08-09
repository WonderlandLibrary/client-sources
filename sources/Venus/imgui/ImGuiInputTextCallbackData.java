/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package imgui;

import imgui.binding.ImGuiStruct;

public class ImGuiInputTextCallbackData
extends ImGuiStruct {
    public ImGuiInputTextCallbackData(long l) {
        super(l);
    }

    public native int getEventFlag();

    public native int getFlags();

    public native int getEventChar();

    public void setEventChar(char c) {
        this.setEventChar((int)c);
    }

    public native void setEventChar(int var1);

    public native int getEventKey();

    public native String getBuf();

    public native boolean getBufDirty();

    public native void setBufDirty(boolean var1);

    public native int getCursorPos();

    public native void setCursorPos(int var1);

    public native int getSelectionStart();

    public native void setSelectionStart(int var1);

    public native int getSelectionEnd();

    public native void setSelectionEnd(int var1);

    public native void deleteChars(int var1, int var2);

    public native void insertChars(int var1, String var2);
}

