/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.resources.data;

public class AnimationFrame {
    private final int frameIndex;
    private final int frameTime;

    public AnimationFrame(int n) {
        this(n, -1);
    }

    public AnimationFrame(int n, int n2) {
        this.frameIndex = n;
        this.frameTime = n2;
    }

    public boolean hasNoTime() {
        return this.frameTime == -1;
    }

    public int getFrameTime() {
        return this.frameTime;
    }

    public int getFrameIndex() {
        return this.frameIndex;
    }
}

