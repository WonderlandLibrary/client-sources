/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.opengl;

import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GLCapabilities;
import org.lwjgl.system.Checks;
import org.lwjgl.system.NativeType;

public class EXTWin32KeyedMutex {
    protected EXTWin32KeyedMutex() {
        throw new UnsupportedOperationException();
    }

    static boolean isAvailable(GLCapabilities gLCapabilities) {
        return Checks.checkFunctions(gLCapabilities.glAcquireKeyedMutexWin32EXT, gLCapabilities.glReleaseKeyedMutexWin32EXT);
    }

    @NativeType(value="GLboolean")
    public static native boolean glAcquireKeyedMutexWin32EXT(@NativeType(value="GLuint") int var0, @NativeType(value="GLuint64") long var1, @NativeType(value="GLuint") int var3);

    @NativeType(value="GLboolean")
    public static native boolean glReleaseKeyedMutexWin32EXT(@NativeType(value="GLuint") int var0, @NativeType(value="GLuint64") long var1);

    static {
        GL.initialize();
    }
}

