/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package imgui.flag;

public final class ImGuiPopupFlags {
    public static final int None = 0;
    public static final int MouseButtonLeft = 0;
    public static final int MouseButtonRight = 1;
    public static final int MouseButtonMiddle = 2;
    public static final int MouseButtonMask = 31;
    public static final int MouseButtonDefault = 1;
    public static final int NoOpenOverExistingPopup = 32;
    public static final int NoOpenOverItems = 64;
    public static final int AnyPopupId = 128;
    public static final int AnyPopupLevel = 256;
    public static final int AnyPopup = 384;

    private ImGuiPopupFlags() {
    }
}

