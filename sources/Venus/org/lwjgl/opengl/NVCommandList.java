/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.opengl;

import java.nio.Buffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;
import org.lwjgl.PointerBuffer;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GLCapabilities;
import org.lwjgl.system.Checks;
import org.lwjgl.system.JNI;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;

public class NVCommandList {
    public static final int GL_TERMINATE_SEQUENCE_COMMAND_NV = 0;
    public static final int GL_NOP_COMMAND_NV = 1;
    public static final int GL_DRAW_ELEMENTS_COMMAND_NV = 2;
    public static final int GL_DRAW_ARRAYS_COMMAND_NV = 3;
    public static final int GL_DRAW_ELEMENTS_STRIP_COMMAND_NV = 4;
    public static final int GL_DRAW_ARRAYS_STRIP_COMMAND_NV = 5;
    public static final int GL_DRAW_ELEMENTS_INSTANCED_COMMAND_NV = 6;
    public static final int GL_DRAW_ARRAYS_INSTANCED_COMMAND_NV = 7;
    public static final int GL_ELEMENT_ADDRESS_COMMAND_NV = 8;
    public static final int GL_ATTRIBUTE_ADDRESS_COMMAND_NV = 9;
    public static final int GL_UNIFORM_ADDRESS_COMMAND_NV = 10;
    public static final int GL_BLEND_COLOR_COMMAND_NV = 11;
    public static final int GL_STENCIL_REF_COMMAND_NV = 12;
    public static final int GL_LINE_WIDTH_COMMAND_NV = 13;
    public static final int GL_POLYGON_OFFSET_COMMAND_NV = 14;
    public static final int GL_ALPHA_REF_COMMAND_NV = 15;
    public static final int GL_VIEWPORT_COMMAND_NV = 16;
    public static final int GL_SCISSOR_COMMAND_NV = 17;
    public static final int GL_FRONT_FACE_COMMAND_NV = 18;

    protected NVCommandList() {
        throw new UnsupportedOperationException();
    }

    static boolean isAvailable(GLCapabilities gLCapabilities) {
        return Checks.checkFunctions(gLCapabilities.glCreateStatesNV, gLCapabilities.glDeleteStatesNV, gLCapabilities.glIsStateNV, gLCapabilities.glStateCaptureNV, gLCapabilities.glGetCommandHeaderNV, gLCapabilities.glGetStageIndexNV, gLCapabilities.glDrawCommandsNV, gLCapabilities.glDrawCommandsAddressNV, gLCapabilities.glDrawCommandsStatesNV, gLCapabilities.glDrawCommandsStatesAddressNV, gLCapabilities.glCreateCommandListsNV, gLCapabilities.glDeleteCommandListsNV, gLCapabilities.glIsCommandListNV, gLCapabilities.glListDrawCommandsStatesClientNV, gLCapabilities.glCommandListSegmentsNV, gLCapabilities.glCompileCommandListNV, gLCapabilities.glCallCommandListNV);
    }

    public static native void nglCreateStatesNV(int var0, long var1);

    public static void glCreateStatesNV(@NativeType(value="GLuint *") IntBuffer intBuffer) {
        NVCommandList.nglCreateStatesNV(intBuffer.remaining(), MemoryUtil.memAddress(intBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="void")
    public static int glCreateStatesNV() {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n = memoryStack.getPointer();
        try {
            IntBuffer intBuffer = memoryStack.callocInt(1);
            NVCommandList.nglCreateStatesNV(1, MemoryUtil.memAddress(intBuffer));
            int n2 = intBuffer.get(0);
            return n2;
        } finally {
            memoryStack.setPointer(n);
        }
    }

    public static native void nglDeleteStatesNV(int var0, long var1);

    public static void glDeleteStatesNV(@NativeType(value="GLuint const *") IntBuffer intBuffer) {
        NVCommandList.nglDeleteStatesNV(intBuffer.remaining(), MemoryUtil.memAddress(intBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void glDeleteStatesNV(@NativeType(value="GLuint const *") int n) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n2 = memoryStack.getPointer();
        try {
            IntBuffer intBuffer = memoryStack.ints(n);
            NVCommandList.nglDeleteStatesNV(1, MemoryUtil.memAddress(intBuffer));
        } finally {
            memoryStack.setPointer(n2);
        }
    }

    @NativeType(value="GLboolean")
    public static native boolean glIsStateNV(@NativeType(value="GLuint") int var0);

    public static native void glStateCaptureNV(@NativeType(value="GLuint") int var0, @NativeType(value="GLenum") int var1);

    @NativeType(value="GLuint")
    public static native int glGetCommandHeaderNV(@NativeType(value="GLenum") int var0, @NativeType(value="GLuint") int var1);

    @NativeType(value="GLushort")
    public static native short glGetStageIndexNV(@NativeType(value="GLenum") int var0);

    public static native void nglDrawCommandsNV(int var0, int var1, long var2, long var4, int var6);

    public static void glDrawCommandsNV(@NativeType(value="GLenum") int n, @NativeType(value="GLuint") int n2, @NativeType(value="GLintptr const *") PointerBuffer pointerBuffer, @NativeType(value="GLsizei const *") IntBuffer intBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, pointerBuffer.remaining());
        }
        NVCommandList.nglDrawCommandsNV(n, n2, MemoryUtil.memAddress(pointerBuffer), MemoryUtil.memAddress(intBuffer), pointerBuffer.remaining());
    }

    public static native void nglDrawCommandsAddressNV(int var0, long var1, long var3, int var5);

    public static void glDrawCommandsAddressNV(@NativeType(value="GLenum") int n, @NativeType(value="GLuint64 const *") LongBuffer longBuffer, @NativeType(value="GLsizei const *") IntBuffer intBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, longBuffer.remaining());
        }
        NVCommandList.nglDrawCommandsAddressNV(n, MemoryUtil.memAddress(longBuffer), MemoryUtil.memAddress(intBuffer), longBuffer.remaining());
    }

    public static native void nglDrawCommandsStatesNV(int var0, long var1, long var3, long var5, long var7, int var9);

    public static void glDrawCommandsStatesNV(@NativeType(value="GLuint") int n, @NativeType(value="GLintptr const *") PointerBuffer pointerBuffer, @NativeType(value="GLsizei const *") IntBuffer intBuffer, @NativeType(value="GLuint const *") IntBuffer intBuffer2, @NativeType(value="GLuint const *") IntBuffer intBuffer3) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, pointerBuffer.remaining());
            Checks.check((Buffer)intBuffer2, pointerBuffer.remaining());
            Checks.check((Buffer)intBuffer3, pointerBuffer.remaining());
        }
        NVCommandList.nglDrawCommandsStatesNV(n, MemoryUtil.memAddress(pointerBuffer), MemoryUtil.memAddress(intBuffer), MemoryUtil.memAddress(intBuffer2), MemoryUtil.memAddress(intBuffer3), pointerBuffer.remaining());
    }

    public static native void nglDrawCommandsStatesAddressNV(long var0, long var2, long var4, long var6, int var8);

    public static void glDrawCommandsStatesAddressNV(@NativeType(value="GLuint64 const *") LongBuffer longBuffer, @NativeType(value="GLsizei const *") IntBuffer intBuffer, @NativeType(value="GLuint const *") IntBuffer intBuffer2, @NativeType(value="GLuint const *") IntBuffer intBuffer3) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, longBuffer.remaining());
            Checks.check((Buffer)intBuffer2, longBuffer.remaining());
            Checks.check((Buffer)intBuffer3, longBuffer.remaining());
        }
        NVCommandList.nglDrawCommandsStatesAddressNV(MemoryUtil.memAddress(longBuffer), MemoryUtil.memAddress(intBuffer), MemoryUtil.memAddress(intBuffer2), MemoryUtil.memAddress(intBuffer3), longBuffer.remaining());
    }

    public static native void nglCreateCommandListsNV(int var0, long var1);

    public static void glCreateCommandListsNV(@NativeType(value="GLuint *") IntBuffer intBuffer) {
        NVCommandList.nglCreateCommandListsNV(intBuffer.remaining(), MemoryUtil.memAddress(intBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="void")
    public static int glCreateCommandListsNV() {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n = memoryStack.getPointer();
        try {
            IntBuffer intBuffer = memoryStack.callocInt(1);
            NVCommandList.nglCreateCommandListsNV(1, MemoryUtil.memAddress(intBuffer));
            int n2 = intBuffer.get(0);
            return n2;
        } finally {
            memoryStack.setPointer(n);
        }
    }

    public static native void nglDeleteCommandListsNV(int var0, long var1);

    public static void glDeleteCommandListsNV(@NativeType(value="GLuint const *") IntBuffer intBuffer) {
        NVCommandList.nglDeleteCommandListsNV(intBuffer.remaining(), MemoryUtil.memAddress(intBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void glDeleteCommandListsNV(@NativeType(value="GLuint const *") int n) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n2 = memoryStack.getPointer();
        try {
            IntBuffer intBuffer = memoryStack.ints(n);
            NVCommandList.nglDeleteCommandListsNV(1, MemoryUtil.memAddress(intBuffer));
        } finally {
            memoryStack.setPointer(n2);
        }
    }

    @NativeType(value="GLboolean")
    public static native boolean glIsCommandListNV(@NativeType(value="GLuint") int var0);

    public static native void nglListDrawCommandsStatesClientNV(int var0, int var1, long var2, long var4, long var6, long var8, int var10);

    public static void glListDrawCommandsStatesClientNV(@NativeType(value="GLuint") int n, @NativeType(value="GLuint") int n2, @NativeType(value="void const **") PointerBuffer pointerBuffer, @NativeType(value="size_t const *") PointerBuffer pointerBuffer2, @NativeType(value="GLuint const *") IntBuffer intBuffer, @NativeType(value="GLuint const *") IntBuffer intBuffer2) {
        if (Checks.CHECKS) {
            Checks.check(pointerBuffer2, pointerBuffer.remaining());
            Checks.check((Buffer)intBuffer, pointerBuffer.remaining());
            Checks.check((Buffer)intBuffer2, pointerBuffer.remaining());
        }
        NVCommandList.nglListDrawCommandsStatesClientNV(n, n2, MemoryUtil.memAddress(pointerBuffer), MemoryUtil.memAddress(pointerBuffer2), MemoryUtil.memAddress(intBuffer), MemoryUtil.memAddress(intBuffer2), pointerBuffer.remaining());
    }

    public static native void glCommandListSegmentsNV(@NativeType(value="GLuint") int var0, @NativeType(value="GLuint") int var1);

    public static native void glCompileCommandListNV(@NativeType(value="GLuint") int var0);

    public static native void glCallCommandListNV(@NativeType(value="GLuint") int var0);

    public static void glCreateStatesNV(@NativeType(value="GLuint *") int[] nArray) {
        long l = GL.getICD().glCreateStatesNV;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(nArray.length, nArray, l);
    }

    public static void glDeleteStatesNV(@NativeType(value="GLuint const *") int[] nArray) {
        long l = GL.getICD().glDeleteStatesNV;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(nArray.length, nArray, l);
    }

    public static void glDrawCommandsNV(@NativeType(value="GLenum") int n, @NativeType(value="GLuint") int n2, @NativeType(value="GLintptr const *") PointerBuffer pointerBuffer, @NativeType(value="GLsizei const *") int[] nArray) {
        long l = GL.getICD().glDrawCommandsNV;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(nArray, pointerBuffer.remaining());
        }
        JNI.callPPV(n, n2, MemoryUtil.memAddress(pointerBuffer), nArray, pointerBuffer.remaining(), l);
    }

    public static void glDrawCommandsAddressNV(@NativeType(value="GLenum") int n, @NativeType(value="GLuint64 const *") long[] lArray, @NativeType(value="GLsizei const *") int[] nArray) {
        long l = GL.getICD().glDrawCommandsAddressNV;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(nArray, lArray.length);
        }
        JNI.callPPV(n, lArray, nArray, lArray.length, l);
    }

    public static void glDrawCommandsStatesNV(@NativeType(value="GLuint") int n, @NativeType(value="GLintptr const *") PointerBuffer pointerBuffer, @NativeType(value="GLsizei const *") int[] nArray, @NativeType(value="GLuint const *") int[] nArray2, @NativeType(value="GLuint const *") int[] nArray3) {
        long l = GL.getICD().glDrawCommandsStatesNV;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(nArray, pointerBuffer.remaining());
            Checks.check(nArray2, pointerBuffer.remaining());
            Checks.check(nArray3, pointerBuffer.remaining());
        }
        JNI.callPPPPV(n, MemoryUtil.memAddress(pointerBuffer), nArray, nArray2, nArray3, pointerBuffer.remaining(), l);
    }

    public static void glDrawCommandsStatesAddressNV(@NativeType(value="GLuint64 const *") long[] lArray, @NativeType(value="GLsizei const *") int[] nArray, @NativeType(value="GLuint const *") int[] nArray2, @NativeType(value="GLuint const *") int[] nArray3) {
        long l = GL.getICD().glDrawCommandsStatesAddressNV;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(nArray, lArray.length);
            Checks.check(nArray2, lArray.length);
            Checks.check(nArray3, lArray.length);
        }
        JNI.callPPPPV(lArray, nArray, nArray2, nArray3, lArray.length, l);
    }

    public static void glCreateCommandListsNV(@NativeType(value="GLuint *") int[] nArray) {
        long l = GL.getICD().glCreateCommandListsNV;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(nArray.length, nArray, l);
    }

    public static void glDeleteCommandListsNV(@NativeType(value="GLuint const *") int[] nArray) {
        long l = GL.getICD().glDeleteCommandListsNV;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(nArray.length, nArray, l);
    }

    public static void glListDrawCommandsStatesClientNV(@NativeType(value="GLuint") int n, @NativeType(value="GLuint") int n2, @NativeType(value="void const **") PointerBuffer pointerBuffer, @NativeType(value="size_t const *") PointerBuffer pointerBuffer2, @NativeType(value="GLuint const *") int[] nArray, @NativeType(value="GLuint const *") int[] nArray2) {
        long l = GL.getICD().glListDrawCommandsStatesClientNV;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(pointerBuffer2, pointerBuffer.remaining());
            Checks.check(nArray, pointerBuffer.remaining());
            Checks.check(nArray2, pointerBuffer.remaining());
        }
        JNI.callPPPPV(n, n2, MemoryUtil.memAddress(pointerBuffer), MemoryUtil.memAddress(pointerBuffer2), nArray, nArray2, pointerBuffer.remaining(), l);
    }

    static {
        GL.initialize();
    }
}

