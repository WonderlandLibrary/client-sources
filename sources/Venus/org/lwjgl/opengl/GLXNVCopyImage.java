/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.opengl;

import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GLXCapabilities;
import org.lwjgl.system.Checks;
import org.lwjgl.system.JNI;
import org.lwjgl.system.NativeType;

public class GLXNVCopyImage {
    protected GLXNVCopyImage() {
        throw new UnsupportedOperationException();
    }

    static boolean isAvailable(GLXCapabilities gLXCapabilities) {
        return Checks.checkFunctions(gLXCapabilities.glXCopyImageSubDataNV);
    }

    public static void glXCopyImageSubDataNV(@NativeType(value="Display *") long l, @NativeType(value="GLXContext") long l2, @NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLint") int n5, @NativeType(value="GLint") int n6, @NativeType(value="GLXContext") long l3, @NativeType(value="GLuint") int n7, @NativeType(value="GLenum") int n8, @NativeType(value="GLint") int n9, @NativeType(value="GLint") int n10, @NativeType(value="GLint") int n11, @NativeType(value="GLint") int n12, @NativeType(value="GLsizei") int n13, @NativeType(value="GLsizei") int n14, @NativeType(value="GLsizei") int n15) {
        long l4 = GL.getCapabilitiesGLXClient().glXCopyImageSubDataNV;
        if (Checks.CHECKS) {
            Checks.check(l4);
            Checks.check(l);
        }
        JNI.callPPPV(l, l2, n, n2, n3, n4, n5, n6, l3, n7, n8, n9, n10, n11, n12, n13, n14, n15, l4);
    }
}

