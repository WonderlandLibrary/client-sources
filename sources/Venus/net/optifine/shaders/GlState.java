/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.shaders;

import com.mojang.blaze3d.platform.GlStateManager;
import net.optifine.shaders.DrawBuffers;
import net.optifine.shaders.ShadersFramebuffer;

public class GlState {
    private static ShadersFramebuffer activeFramebuffer;

    public static void bindFramebuffer(ShadersFramebuffer shadersFramebuffer) {
        activeFramebuffer = shadersFramebuffer;
        GlStateManager.bindFramebuffer(36160, activeFramebuffer.getGlFramebuffer());
    }

    public static ShadersFramebuffer getFramebuffer() {
        return activeFramebuffer;
    }

    public static void setFramebufferTexture2D(int n, int n2, int n3, int n4, int n5) {
        activeFramebuffer.setFramebufferTexture2D(n, n2, n3, n4, n5);
    }

    public static void setDrawBuffers(DrawBuffers drawBuffers) {
        activeFramebuffer.setDrawBuffers(drawBuffers);
    }

    public static DrawBuffers getDrawBuffers() {
        return activeFramebuffer.getDrawBuffers();
    }
}

