/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.opengl;

import java.nio.ByteBuffer;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GLCapabilities;
import org.lwjgl.system.Checks;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;

public class EXTSeparateShaderObjects {
    public static final int GL_ACTIVE_PROGRAM_EXT = 35725;

    protected EXTSeparateShaderObjects() {
        throw new UnsupportedOperationException();
    }

    static boolean isAvailable(GLCapabilities gLCapabilities) {
        return Checks.checkFunctions(gLCapabilities.glUseShaderProgramEXT, gLCapabilities.glActiveProgramEXT, gLCapabilities.glCreateShaderProgramEXT);
    }

    public static native void glUseShaderProgramEXT(@NativeType(value="GLenum") int var0, @NativeType(value="GLuint") int var1);

    public static native void glActiveProgramEXT(@NativeType(value="GLuint") int var0);

    public static native int nglCreateShaderProgramEXT(int var0, long var1);

    @NativeType(value="GLuint")
    public static int glCreateShaderProgramEXT(@NativeType(value="GLenum") int n, @NativeType(value="GLchar const *") ByteBuffer byteBuffer) {
        if (Checks.CHECKS) {
            Checks.checkNT1(byteBuffer);
        }
        return EXTSeparateShaderObjects.nglCreateShaderProgramEXT(n, MemoryUtil.memAddress(byteBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="GLuint")
    public static int glCreateShaderProgramEXT(@NativeType(value="GLenum") int n, @NativeType(value="GLchar const *") CharSequence charSequence) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n2 = memoryStack.getPointer();
        try {
            memoryStack.nUTF8(charSequence, false);
            long l = memoryStack.getPointerAddress();
            int n3 = EXTSeparateShaderObjects.nglCreateShaderProgramEXT(n, l);
            return n3;
        } finally {
            memoryStack.setPointer(n2);
        }
    }

    static {
        GL.initialize();
    }
}

