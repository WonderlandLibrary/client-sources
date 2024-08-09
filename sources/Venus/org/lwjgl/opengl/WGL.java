/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.opengl;

import java.nio.ByteBuffer;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.APIUtil;
import org.lwjgl.system.Checks;
import org.lwjgl.system.JNI;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;

public class WGL {
    public static final int WGL_FONT_LINES = 0;
    public static final int WGL_FONT_POLYGONS = 1;
    public static final int WGL_SWAP_MAIN_PLANE = 1;
    public static final int WGL_SWAP_OVERLAY1 = 2;
    public static final int WGL_SWAP_OVERLAY2 = 4;
    public static final int WGL_SWAP_OVERLAY3 = 8;
    public static final int WGL_SWAP_OVERLAY4 = 16;
    public static final int WGL_SWAP_OVERLAY5 = 32;
    public static final int WGL_SWAP_OVERLAY6 = 64;
    public static final int WGL_SWAP_OVERLAY7 = 128;
    public static final int WGL_SWAP_OVERLAY8 = 256;
    public static final int WGL_SWAP_OVERLAY9 = 512;
    public static final int WGL_SWAP_OVERLAY10 = 1024;
    public static final int WGL_SWAP_OVERLAY11 = 2048;
    public static final int WGL_SWAP_OVERLAY12 = 4096;
    public static final int WGL_SWAP_OVERLAY13 = 8192;
    public static final int WGL_SWAP_OVERLAY14 = 16384;
    public static final int WGL_SWAP_OVERLAY15 = 32768;
    public static final int WGL_SWAP_UNDERLAY1 = 65536;
    public static final int WGL_SWAP_UNDERLAY2 = 131072;
    public static final int WGL_SWAP_UNDERLAY3 = 262144;
    public static final int WGL_SWAP_UNDERLAY4 = 524288;
    public static final int WGL_SWAP_UNDERLAY5 = 0x100000;
    public static final int WGL_SWAP_UNDERLAY6 = 0x200000;
    public static final int WGL_SWAP_UNDERLAY7 = 0x400000;
    public static final int WGL_SWAP_UNDERLAY8 = 0x800000;
    public static final int WGL_SWAP_UNDERLAY9 = 0x1000000;
    public static final int WGL_SWAP_UNDERLAY10 = 0x2000000;
    public static final int WGL_SWAP_UNDERLAY11 = 0x4000000;
    public static final int WGL_SWAP_UNDERLAY12 = 0x8000000;
    public static final int WGL_SWAP_UNDERLAY13 = 0x10000000;
    public static final int WGL_SWAP_UNDERLAY14 = 0x20000000;
    public static final int WGL_SWAP_UNDERLAY15 = 0x40000000;

    protected WGL() {
        throw new UnsupportedOperationException();
    }

    @NativeType(value="HGLRC")
    public static long wglCreateContext(@NativeType(value="HDC") long l) {
        long l2 = Functions.CreateContext;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        return JNI.callPP(l, l2);
    }

    @NativeType(value="HGLRC")
    public static long wglCreateLayerContext(@NativeType(value="HDC") long l, int n) {
        long l2 = Functions.CreateLayerContext;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        return JNI.callPP(l, n, l2);
    }

    @NativeType(value="BOOL")
    public static boolean wglCopyContext(@NativeType(value="HGLRC") long l, @NativeType(value="HGLRC") long l2, @NativeType(value="UINT") int n) {
        long l3 = Functions.CopyContext;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(l2);
        }
        return JNI.callPPI(l, l2, n, l3) != 0;
    }

    @NativeType(value="BOOL")
    public static boolean wglDeleteContext(@NativeType(value="HGLRC") long l) {
        long l2 = Functions.DeleteContext;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        return JNI.callPI(l, l2) != 0;
    }

    @NativeType(value="HGLRC")
    public static long wglGetCurrentContext() {
        long l = Functions.GetCurrentContext;
        return JNI.callP(l);
    }

    @NativeType(value="HDC")
    public static long wglGetCurrentDC() {
        long l = Functions.GetCurrentDC;
        return JNI.callP(l);
    }

    public static long nwglGetProcAddress(long l) {
        long l2 = Functions.GetProcAddress;
        return JNI.callPP(l, l2);
    }

    @NativeType(value="PROC")
    public static long wglGetProcAddress(@NativeType(value="LPCSTR") ByteBuffer byteBuffer) {
        if (Checks.CHECKS) {
            Checks.checkNT1(byteBuffer);
        }
        return WGL.nwglGetProcAddress(MemoryUtil.memAddress(byteBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="PROC")
    public static long wglGetProcAddress(@NativeType(value="LPCSTR") CharSequence charSequence) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n = memoryStack.getPointer();
        try {
            memoryStack.nASCII(charSequence, false);
            long l = memoryStack.getPointerAddress();
            long l2 = WGL.nwglGetProcAddress(l);
            return l2;
        } finally {
            memoryStack.setPointer(n);
        }
    }

    @NativeType(value="BOOL")
    public static boolean wglMakeCurrent(@NativeType(value="HDC") long l, @NativeType(value="HGLRC") long l2) {
        long l3 = Functions.MakeCurrent;
        return JNI.callPPI(l, l2, l3) != 0;
    }

    @NativeType(value="BOOL")
    public static boolean wglShareLists(@NativeType(value="HGLRC") long l, @NativeType(value="HGLRC") long l2) {
        long l3 = Functions.ShareLists;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(l2);
        }
        return JNI.callPPI(l, l2, l3) != 0;
    }

    public static final class Functions {
        public static final long CreateContext = APIUtil.apiGetFunctionAddress(GL.getFunctionProvider(), "wglCreateContext");
        public static final long CreateLayerContext = APIUtil.apiGetFunctionAddress(GL.getFunctionProvider(), "wglCreateLayerContext");
        public static final long CopyContext = APIUtil.apiGetFunctionAddress(GL.getFunctionProvider(), "wglCopyContext");
        public static final long DeleteContext = APIUtil.apiGetFunctionAddress(GL.getFunctionProvider(), "wglDeleteContext");
        public static final long GetCurrentContext = APIUtil.apiGetFunctionAddress(GL.getFunctionProvider(), "wglGetCurrentContext");
        public static final long GetCurrentDC = APIUtil.apiGetFunctionAddress(GL.getFunctionProvider(), "wglGetCurrentDC");
        public static final long GetProcAddress = APIUtil.apiGetFunctionAddress(GL.getFunctionProvider(), "wglGetProcAddress");
        public static final long MakeCurrent = APIUtil.apiGetFunctionAddress(GL.getFunctionProvider(), "wglMakeCurrent");
        public static final long ShareLists = APIUtil.apiGetFunctionAddress(GL.getFunctionProvider(), "wglShareLists");

        private Functions() {
        }
    }
}

