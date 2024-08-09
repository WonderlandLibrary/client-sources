/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.opengl;

import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GLXCapabilities;
import org.lwjgl.system.Checks;
import org.lwjgl.system.JNI;
import org.lwjgl.system.NativeType;

public class GLXNVCopyBuffer {
    protected GLXNVCopyBuffer() {
        throw new UnsupportedOperationException();
    }

    static boolean isAvailable(GLXCapabilities gLXCapabilities) {
        return Checks.checkFunctions(gLXCapabilities.glXCopyBufferSubDataNV, gLXCapabilities.glXNamedCopyBufferSubDataNV);
    }

    public static void glXCopyBufferSubDataNV(@NativeType(value="Display *") long l, @NativeType(value="GLXContext") long l2, @NativeType(value="GLXContext") long l3, @NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLintptr") long l4, @NativeType(value="GLintptr") long l5, @NativeType(value="GLsizeiptr") long l6) {
        long l7 = GL.getCapabilitiesGLXClient().glXCopyBufferSubDataNV;
        if (Checks.CHECKS) {
            Checks.check(l7);
            Checks.check(l);
            Checks.check(l2);
            Checks.check(l3);
        }
        JNI.callPPPPPPV(l, l2, l3, n, n2, l4, l5, l6, l7);
    }

    public static void glXNamedCopyBufferSubDataNV(@NativeType(value="Display *") long l, @NativeType(value="GLXContext") long l2, @NativeType(value="GLXContext") long l3, @NativeType(value="GLuint") int n, @NativeType(value="GLuint") int n2, @NativeType(value="GLintptr") long l4, @NativeType(value="GLintptr") long l5, @NativeType(value="GLsizeiptr") long l6) {
        long l7 = GL.getCapabilitiesGLXClient().glXNamedCopyBufferSubDataNV;
        if (Checks.CHECKS) {
            Checks.check(l7);
            Checks.check(l);
            Checks.check(l2);
            Checks.check(l3);
        }
        JNI.callPPPPPPV(l, l2, l3, n, n2, l4, l5, l6, l7);
    }
}

