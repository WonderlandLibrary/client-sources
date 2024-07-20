/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.opengl;

import org.lwjgl.opengl.GL45;

public final class ARBTextureBarrier {
    private ARBTextureBarrier() {
    }

    public static void glTextureBarrier() {
        GL45.glTextureBarrier();
    }
}

