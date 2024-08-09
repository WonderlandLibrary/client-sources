/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package imgui.flag;

public final class ImGuiDragDropFlags {
    public static final int None = 0;
    public static final int SourceNoPreviewTooltip = 1;
    public static final int SourceNoDisableHover = 2;
    public static final int SourceNoHoldToOpenOthers = 4;
    public static final int SourceAllowNullID = 8;
    public static final int SourceExtern = 16;
    public static final int SourceAutoExpirePayload = 32;
    public static final int AcceptBeforeDelivery = 1024;
    public static final int AcceptNoDrawDefaultRect = 2048;
    public static final int AcceptNoPreviewTooltip = 4096;
    public static final int AcceptPeekOnly = 3072;

    private ImGuiDragDropFlags() {
    }
}

