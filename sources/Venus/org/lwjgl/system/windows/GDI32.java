/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.system.windows;

import javax.annotation.Nullable;
import org.lwjgl.system.APIUtil;
import org.lwjgl.system.Checks;
import org.lwjgl.system.Library;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;
import org.lwjgl.system.SharedLibrary;
import org.lwjgl.system.windows.PIXELFORMATDESCRIPTOR;

public class GDI32 {
    public static final int DISPLAY_DEVICE_ATTACHED_TO_DESKTOP = 1;
    public static final int DISPLAY_DEVICE_MULTI_DRIVER = 2;
    public static final int DISPLAY_DEVICE_PRIMARY_DEVICE = 4;
    public static final int DISPLAY_DEVICE_MIRRORING_DRIVER = 8;
    public static final int DISPLAY_DEVICE_VGA_COMPATIBLE = 16;
    public static final int DISPLAY_DEVICE_REMOVABLE = 32;
    public static final int DISPLAY_DEVICE_MODESPRUNED = 0x8000000;
    public static final int DISPLAY_DEVICE_REMOTE = 0x4000000;
    public static final int DISPLAY_DEVICE_DISCONNECT = 0x2000000;
    public static final int DISPLAY_DEVICE_TS_COMPATIBLE = 0x200000;
    public static final int DISPLAY_DEVICE_UNSAFE_MODES_ON = 524288;
    public static final int DISPLAY_DEVICE_ACTIVE = 1;
    public static final int DISPLAY_DEVICE_ATTACHED = 2;
    public static final int DM_SPECVERSION = 1025;
    public static final int DM_ORIENTATION = 1;
    public static final int DM_PAPERSIZE = 2;
    public static final int DM_PAPERLENGTH = 4;
    public static final int DM_PAPERWIDTH = 8;
    public static final int DM_SCALE = 16;
    public static final int DM_POSITION = 32;
    public static final int DM_NUP = 64;
    public static final int DM_DISPLAYORIENTATION = 128;
    public static final int DM_COPIES = 256;
    public static final int DM_DEFAULTSOURCE = 512;
    public static final int DM_PRINTQUALITY = 1024;
    public static final int DM_COLOR = 2048;
    public static final int DM_DUPLEX = 4096;
    public static final int DM_YRESOLUTION = 8192;
    public static final int DM_TTOPTION = 16384;
    public static final int DM_COLLATE = 32768;
    public static final int DM_FORMNAME = 65536;
    public static final int DM_LOGPIXELS = 131072;
    public static final int DM_BITSPERPEL = 262144;
    public static final int DM_PELSWIDTH = 524288;
    public static final int DM_PELSHEIGHT = 0x100000;
    public static final int DM_DISPLAYFLAGS = 0x200000;
    public static final int DM_DISPLAYFREQUENCY = 0x400000;
    public static final int DM_ICMMETHOD = 0x800000;
    public static final int DM_ICMINTENT = 0x1000000;
    public static final int DM_MEDIATYPE = 0x2000000;
    public static final int DM_DITHERTYPE = 0x4000000;
    public static final int DM_PANNINGWIDTH = 0x8000000;
    public static final int DM_PANNINGHEIGHT = 0x10000000;
    public static final int DM_DISPLAYFIXEDOUTPUT = 0x20000000;
    public static final int DMDO_DEFAULT = 0;
    public static final int DMDO_90 = 1;
    public static final int DMDO_180 = 2;
    public static final int DMDO_270 = 3;
    public static final int DMDFO_DEFAULT = 0;
    public static final int DMDFO_STRETCH = 1;
    public static final int DMDFO_CENTER = 2;
    public static final int DM_INTERLACED = 2;
    public static final int DMDISPLAYFLAGS_TEXTMODE = 4;
    public static final int PFD_DOUBLEBUFFER = 1;
    public static final int PFD_STEREO = 2;
    public static final int PFD_DRAW_TO_WINDOW = 4;
    public static final int PFD_DRAW_TO_BITMAP = 8;
    public static final int PFD_SUPPORT_GDI = 16;
    public static final int PFD_SUPPORT_OPENGL = 32;
    public static final int PFD_GENERIC_FORMAT = 64;
    public static final int PFD_NEED_PALETTE = 128;
    public static final int PFD_NEED_SYSTEM_PALETTE = 256;
    public static final int PFD_SWAP_EXCHANGE = 512;
    public static final int PFD_SWAP_COPY = 1024;
    public static final int PFD_SWAP_LAYER_BUFFERS = 2048;
    public static final int PFD_GENERIC_ACCELERATED = 4096;
    public static final int PFD_SUPPORT_DIRECTDRAW = 8192;
    public static final int PFD_DIRECT3D_ACCELERATED = 16384;
    public static final int PFD_SUPPORT_COMPOSITION = 32768;
    public static final int PFD_DEPTH_DONTCARE = 0x20000000;
    public static final int PFD_DOUBLEBUFFER_DONTCARE = 0x40000000;
    public static final int PFD_STEREO_DONTCARE = Integer.MIN_VALUE;
    public static final byte PFD_TYPE_RGBA = 0;
    public static final byte PFD_TYPE_COLORINDEX = 1;
    public static final byte PFD_MAIN_PLANE = 0;
    public static final byte PFD_OVERLAY_PLANE = 1;
    public static final byte PFD_UNDERLAY_PLANE = -1;
    private static final SharedLibrary GDI32 = Library.loadNative(GDI32.class, "gdi32");

    protected GDI32() {
        throw new UnsupportedOperationException();
    }

    public static SharedLibrary getLibrary() {
        return GDI32;
    }

    public static native int nChoosePixelFormat(long var0, long var2, long var4);

    public static int nChoosePixelFormat(long l, long l2) {
        long l3 = Functions.ChoosePixelFormat;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        return org.lwjgl.system.windows.GDI32.nChoosePixelFormat(l, l2, l3);
    }

    public static int ChoosePixelFormat(@NativeType(value="HDC") long l, @NativeType(value="PIXELFORMATDESCRIPTOR const *") PIXELFORMATDESCRIPTOR pIXELFORMATDESCRIPTOR) {
        return org.lwjgl.system.windows.GDI32.nChoosePixelFormat(l, pIXELFORMATDESCRIPTOR.address());
    }

    public static native int nDescribePixelFormat(long var0, int var2, int var3, long var4, long var6);

    public static int nDescribePixelFormat(long l, int n, int n2, long l2) {
        long l3 = Functions.DescribePixelFormat;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        return org.lwjgl.system.windows.GDI32.nDescribePixelFormat(l, n, n2, l2, l3);
    }

    public static int DescribePixelFormat(@NativeType(value="HDC") long l, int n, @NativeType(value="UINT") int n2, @Nullable @NativeType(value="LPPIXELFORMATDESCRIPTOR") PIXELFORMATDESCRIPTOR pIXELFORMATDESCRIPTOR) {
        return org.lwjgl.system.windows.GDI32.nDescribePixelFormat(l, n, n2, MemoryUtil.memAddressSafe(pIXELFORMATDESCRIPTOR));
    }

    public static int DescribePixelFormat(@NativeType(value="HDC") long l, int n, @Nullable @NativeType(value="LPPIXELFORMATDESCRIPTOR") PIXELFORMATDESCRIPTOR pIXELFORMATDESCRIPTOR) {
        return org.lwjgl.system.windows.GDI32.nDescribePixelFormat(l, n, PIXELFORMATDESCRIPTOR.SIZEOF, MemoryUtil.memAddressSafe(pIXELFORMATDESCRIPTOR));
    }

    public static native int nGetPixelFormat(long var0, long var2);

    public static int GetPixelFormat(@NativeType(value="HDC") long l) {
        long l2 = Functions.GetPixelFormat;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        return org.lwjgl.system.windows.GDI32.nGetPixelFormat(l, l2);
    }

    public static native int nSetPixelFormat(long var0, int var2, long var3, long var5);

    public static int nSetPixelFormat(long l, int n, long l2) {
        long l3 = Functions.SetPixelFormat;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        return org.lwjgl.system.windows.GDI32.nSetPixelFormat(l, n, l2, l3);
    }

    @NativeType(value="BOOL")
    public static boolean SetPixelFormat(@NativeType(value="HDC") long l, int n, @Nullable @NativeType(value="PIXELFORMATDESCRIPTOR const *") PIXELFORMATDESCRIPTOR pIXELFORMATDESCRIPTOR) {
        return org.lwjgl.system.windows.GDI32.nSetPixelFormat(l, n, MemoryUtil.memAddressSafe(pIXELFORMATDESCRIPTOR)) != 0;
    }

    public static native int nSwapBuffers(long var0, long var2);

    @NativeType(value="BOOL")
    public static boolean SwapBuffers(@NativeType(value="HDC") long l) {
        long l2 = Functions.SwapBuffers;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        return org.lwjgl.system.windows.GDI32.nSwapBuffers(l, l2) != 0;
    }

    static SharedLibrary access$000() {
        return GDI32;
    }

    public static final class Functions {
        public static final long ChoosePixelFormat = APIUtil.apiGetFunctionAddress(org.lwjgl.system.windows.GDI32.access$000(), "ChoosePixelFormat");
        public static final long DescribePixelFormat = APIUtil.apiGetFunctionAddress(org.lwjgl.system.windows.GDI32.access$000(), "DescribePixelFormat");
        public static final long GetPixelFormat = APIUtil.apiGetFunctionAddress(org.lwjgl.system.windows.GDI32.access$000(), "GetPixelFormat");
        public static final long SetPixelFormat = APIUtil.apiGetFunctionAddress(org.lwjgl.system.windows.GDI32.access$000(), "SetPixelFormat");
        public static final long SwapBuffers = APIUtil.apiGetFunctionAddress(org.lwjgl.system.windows.GDI32.access$000(), "SwapBuffers");

        private Functions() {
        }
    }
}

