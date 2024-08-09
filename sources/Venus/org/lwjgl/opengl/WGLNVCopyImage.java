/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.opengl;

import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.WGLCapabilities;
import org.lwjgl.system.Checks;
import org.lwjgl.system.JNI;
import org.lwjgl.system.NativeType;

public class WGLNVCopyImage {
    protected WGLNVCopyImage() {
        throw new UnsupportedOperationException();
    }

    static boolean isAvailable(WGLCapabilities wGLCapabilities) {
        return Checks.checkFunctions(wGLCapabilities.wglCopyImageSubDataNV);
    }

    @NativeType(value="BOOL")
    public static boolean wglCopyImageSubDataNV(@NativeType(value="HGLRC") long l, @NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLint") int n5, @NativeType(value="GLint") int n6, @NativeType(value="HGLRC") long l2, @NativeType(value="GLuint") int n7, @NativeType(value="GLenum") int n8, @NativeType(value="GLint") int n9, @NativeType(value="GLint") int n10, @NativeType(value="GLint") int n11, @NativeType(value="GLint") int n12, @NativeType(value="GLsizei") int n13, @NativeType(value="GLsizei") int n14, @NativeType(value="GLsizei") int n15) {
        long l3 = GL.getCapabilitiesWGL().wglCopyImageSubDataNV;
        if (Checks.CHECKS) {
            Checks.check(l3);
            Checks.check(l);
            Checks.check(l2);
        }
        return JNI.callPPI(l, n, n2, n3, n4, n5, n6, l2, n7, n8, n9, n10, n11, n12, n13, n14, n15, l3) != 0;
    }
}

