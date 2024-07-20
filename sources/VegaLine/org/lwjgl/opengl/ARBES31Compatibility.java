/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.opengl;

import org.lwjgl.opengl.GL45;

public final class ARBES31Compatibility {
    private ARBES31Compatibility() {
    }

    public static void glMemoryBarrierByRegion(int barriers) {
        GL45.glMemoryBarrierByRegion(barriers);
    }
}

