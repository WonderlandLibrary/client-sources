/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.shaders;

import com.mojang.blaze3d.platform.GlStateManager;
import java.nio.IntBuffer;
import java.util.Arrays;
import net.optifine.util.ArrayUtils;
import net.optifine.util.BufferUtil;
import org.lwjgl.BufferUtils;

public class FlipTextures {
    private String name;
    private IntBuffer texturesA;
    private IntBuffer texturesB;
    private boolean[] flips;
    private boolean[] changed;

    public FlipTextures(String string, int n) {
        this.name = string;
        this.texturesA = BufferUtils.createIntBuffer(n);
        this.texturesB = BufferUtils.createIntBuffer(n);
        this.flips = new boolean[n];
        this.changed = new boolean[n];
    }

    public int capacity() {
        return this.texturesA.capacity();
    }

    public int position() {
        return this.texturesA.position();
    }

    public int limit() {
        return this.texturesA.limit();
    }

    public FlipTextures position(int n) {
        this.texturesA.position(n);
        this.texturesB.position(n);
        return this;
    }

    public FlipTextures limit(int n) {
        this.texturesA.limit(n);
        this.texturesB.limit(n);
        return this;
    }

    public int get(boolean bl, int n) {
        return bl ? this.getA(n) : this.getB(n);
    }

    public int getA(int n) {
        return this.get(n, this.flips[n]);
    }

    public int getB(int n) {
        return this.get(n, !this.flips[n]);
    }

    private int get(int n, boolean bl) {
        IntBuffer intBuffer = bl ? this.texturesB : this.texturesA;
        return intBuffer.get(n);
    }

    public void flip(int n) {
        this.flips[n] = !this.flips[n];
        this.changed[n] = true;
    }

    public boolean isChanged(int n) {
        return this.changed[n];
    }

    public void reset() {
        Arrays.fill(this.flips, false);
        Arrays.fill(this.changed, false);
    }

    public void genTextures() {
        GlStateManager.genTextures(this.texturesA);
        GlStateManager.genTextures(this.texturesB);
    }

    public void deleteTextures() {
        GlStateManager.deleteTextures(this.texturesA);
        GlStateManager.deleteTextures(this.texturesB);
        this.reset();
    }

    public void fill(int n) {
        int n2 = this.limit();
        for (int i = 0; i < n2; ++i) {
            this.texturesA.put(i, n);
            this.texturesB.put(i, n);
        }
    }

    public FlipTextures clear() {
        this.position(0);
        this.limit(this.capacity());
        return this;
    }

    public String toString() {
        return this.name + ", A: " + BufferUtil.getBufferString(this.texturesA) + ", B: " + BufferUtil.getBufferString(this.texturesB) + ", flips: [" + ArrayUtils.arrayToString(this.flips, this.limit()) + "], changed: [" + ArrayUtils.arrayToString(this.changed, this.limit()) + "]";
    }
}

