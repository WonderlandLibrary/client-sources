/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer;

import java.util.OptionalInt;

public class ScreenSize {
    public final int width;
    public final int height;
    public final OptionalInt fullscreenWidth;
    public final OptionalInt fullscreenHeight;
    public final boolean fullscreen;

    public ScreenSize(int n, int n2, OptionalInt optionalInt, OptionalInt optionalInt2, boolean bl) {
        this.width = n;
        this.height = n2;
        this.fullscreenWidth = optionalInt;
        this.fullscreenHeight = optionalInt2;
        this.fullscreen = bl;
    }
}

