/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.opengl;

import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GLCapabilities;
import org.lwjgl.system.Checks;
import org.lwjgl.system.NativeType;

public class EXTX11SyncObject {
    public static final int GL_SYNC_X11_FENCE_EXT = 37089;

    protected EXTX11SyncObject() {
        throw new UnsupportedOperationException();
    }

    static boolean isAvailable(GLCapabilities gLCapabilities) {
        return Checks.checkFunctions(gLCapabilities.glImportSyncEXT);
    }

    @NativeType(value="GLsync")
    public static native long glImportSyncEXT(@NativeType(value="GLenum") int var0, @NativeType(value="GLintptr") long var1, @NativeType(value="GLbitfield") int var3);

    static {
        GL.initialize();
    }
}

