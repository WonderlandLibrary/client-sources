/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.system.linux;

import java.nio.ByteBuffer;
import javax.annotation.Nullable;
import org.lwjgl.PointerBuffer;
import org.lwjgl.system.APIUtil;
import org.lwjgl.system.Checks;
import org.lwjgl.system.JNI;
import org.lwjgl.system.Library;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;
import org.lwjgl.system.SharedLibrary;
import org.lwjgl.system.linux.Visual;
import org.lwjgl.system.linux.XSetWindowAttributes;

public class X11 {
    public static final int True = 1;
    public static final int False = 0;
    public static final int None = 0;
    public static final int ParentRelative = 1;
    public static final int CopyFromParent = 0;
    public static final int PointerWindow = 0;
    public static final int InputFocus = 1;
    public static final int PointerRoot = 1;
    public static final int AnyPropertyType = 0;
    public static final int AnyKey = 0;
    public static final int AnyButton = 0;
    public static final int AllTemporary = 0;
    public static final int CurrentTime = 0;
    public static final int NoSymbol = 0;
    public static final int Success = 0;
    public static final int BadRequest = 1;
    public static final int BadValue = 2;
    public static final int BadWindow = 3;
    public static final int BadPixmap = 4;
    public static final int BadAtom = 5;
    public static final int BadCursor = 6;
    public static final int BadFont = 7;
    public static final int BadMatch = 8;
    public static final int BadDrawable = 9;
    public static final int BadAccess = 10;
    public static final int BadAlloc = 11;
    public static final int BadColor = 12;
    public static final int BadGC = 13;
    public static final int BadIDChoice = 14;
    public static final int BadName = 15;
    public static final int BadLength = 16;
    public static final int BadImplementation = 17;
    public static final int CWBackPixmap = 1;
    public static final int CWBackPixel = 2;
    public static final int CWBorderPixmap = 4;
    public static final int CWBorderPixel = 8;
    public static final int CWBitGravity = 16;
    public static final int CWWinGravity = 32;
    public static final int CWBackingStore = 64;
    public static final int CWBackingPlanes = 128;
    public static final int CWBackingPixel = 256;
    public static final int CWOverrideRedirect = 512;
    public static final int CWSaveUnder = 1024;
    public static final int CWEventMask = 2048;
    public static final int CWDontPropagate = 4096;
    public static final int CWColormap = 8192;
    public static final int CWCursor = 16384;
    public static final int NoEventMask = 0;
    public static final int KeyPressMask = 1;
    public static final int KeyReleaseMask = 2;
    public static final int ButtonPressMask = 4;
    public static final int ButtonReleaseMask = 8;
    public static final int EnterWindowMask = 16;
    public static final int LeaveWindowMask = 32;
    public static final int PointerMotionMask = 64;
    public static final int PointerMotionHintMask = 128;
    public static final int Button1MotionMask = 256;
    public static final int Button2MotionMask = 512;
    public static final int Button3MotionMask = 1024;
    public static final int Button4MotionMask = 2048;
    public static final int Button5MotionMask = 4096;
    public static final int ButtonMotionMask = 8192;
    public static final int KeymapStateMask = 16384;
    public static final int ExposureMask = 32768;
    public static final int VisibilityChangeMask = 65536;
    public static final int StructureNotifyMask = 131072;
    public static final int ResizeRedirectMask = 262144;
    public static final int SubstructureNotifyMask = 524288;
    public static final int SubstructureRedirectMask = 0x100000;
    public static final int FocusChangeMask = 0x200000;
    public static final int PropertyChangeMask = 0x400000;
    public static final int ColormapChangeMask = 0x800000;
    public static final int OwnerGrabButtonMask = 0x1000000;
    public static final int KeyPress = 2;
    public static final int KeyRelease = 3;
    public static final int ButtonPress = 4;
    public static final int ButtonRelease = 5;
    public static final int MotionNotify = 6;
    public static final int EnterNotify = 7;
    public static final int LeaveNotify = 8;
    public static final int FocusIn = 9;
    public static final int FocusOut = 10;
    public static final int KeymapNotify = 11;
    public static final int Expose = 12;
    public static final int GraphicsExpose = 13;
    public static final int NoExpose = 14;
    public static final int VisibilityNotify = 15;
    public static final int CreateNotify = 16;
    public static final int DestroyNotify = 17;
    public static final int UnmapNotify = 18;
    public static final int MapNotify = 19;
    public static final int MapRequest = 20;
    public static final int ReparentNotify = 21;
    public static final int ConfigureNotify = 22;
    public static final int ConfigureRequest = 23;
    public static final int GravityNotify = 24;
    public static final int ResizeRequest = 25;
    public static final int CirculateNotify = 26;
    public static final int CirculateRequest = 27;
    public static final int PropertyNotify = 28;
    public static final int SelectionClear = 29;
    public static final int SelectionRequest = 30;
    public static final int SelectionNotify = 31;
    public static final int ColormapNotify = 32;
    public static final int ClientMessage = 33;
    public static final int MappingNotify = 34;
    public static final int GenericEvent = 35;
    public static final int LASTEvent = 36;
    public static final int ShiftMask = 1;
    public static final int LockMask = 2;
    public static final int ControlMask = 4;
    public static final int Mod1Mask = 8;
    public static final int Mod2Mask = 16;
    public static final int Mod3Mask = 32;
    public static final int Mod4Mask = 64;
    public static final int Mod5Mask = 128;
    public static final int ShiftMapIndex = 0;
    public static final int LockMapIndex = 1;
    public static final int ControlMapIndex = 2;
    public static final int Mod1MapIndex = 3;
    public static final int Mod2MapIndex = 4;
    public static final int Mod3MapIndex = 5;
    public static final int Mod4MapIndex = 6;
    public static final int Mod5MapIndex = 7;
    public static final int Button1Mask = 256;
    public static final int Button2Mask = 512;
    public static final int Button3Mask = 1024;
    public static final int Button4Mask = 2048;
    public static final int Button5Mask = 4096;
    public static final int AnyModifier = 32768;
    public static final int Button1 = 1;
    public static final int Button2 = 2;
    public static final int Button3 = 3;
    public static final int Button4 = 4;
    public static final int Button5 = 5;
    public static final int NotifyNormal = 0;
    public static final int NotifyGrab = 1;
    public static final int NotifyUngrab = 2;
    public static final int NotifyWhileGrabbed = 3;
    public static final int NotifyHint = 1;
    public static final int NotifyAncestor = 0;
    public static final int NotifyVirtual = 1;
    public static final int NotifyInferior = 2;
    public static final int NotifyNonlinear = 3;
    public static final int NotifyNonlinearVirtual = 4;
    public static final int NotifyPointer = 5;
    public static final int NotifyPointerRoot = 6;
    public static final int NotifyDetailNone = 7;
    public static final int VisibilityUnobscured = 0;
    public static final int VisibilityPartiallyObscured = 1;
    public static final int VisibilityFullyObscured = 2;
    public static final int PlaceOnTop = 0;
    public static final int PlaceOnBottom = 1;
    public static final int PropertyNewValue = 0;
    public static final int PropertyDelete = 1;
    public static final int ColormapUninstalled = 0;
    public static final int ColormapInstalled = 1;
    public static final int GrabModeSync = 0;
    public static final int GrabModeAsync = 1;
    public static final int GrabSuccess = 0;
    public static final int AlreadyGrabbed = 1;
    public static final int GrabInvalidTime = 2;
    public static final int GrabNotViewable = 3;
    public static final int GrabFrozen = 4;
    public static final int AsyncPointer = 0;
    public static final int SyncPointer = 1;
    public static final int ReplayPointer = 2;
    public static final int AsyncKeyboard = 3;
    public static final int SyncKeyboard = 4;
    public static final int ReplayKeyboard = 5;
    public static final int AsyncBoth = 6;
    public static final int SyncBoth = 7;
    public static final int AllocNone = 0;
    public static final int AllocAll = 1;
    public static final int RevertToNone = 0;
    public static final int RevertToPointerRoot = 1;
    public static final int RevertToParent = 2;
    public static final int InputOutput = 1;
    public static final int InputOnly = 2;
    public static final int DontPreferBlanking = 0;
    public static final int PreferBlanking = 1;
    public static final int DefaultBlanking = 2;
    public static final int DisableScreenSaver = 0;
    public static final int DisableScreenInterval = 0;
    public static final int DontAllowExposures = 0;
    public static final int AllowExposures = 1;
    public static final int DefaultExposures = 2;
    public static final int ScreenSaverReset = 0;
    public static final int ScreenSaverActive = 1;
    public static final int PropModeReplace = 0;
    public static final int PropModePrepend = 1;
    public static final int PropModeAppend = 2;
    public static final int GXclear = 0;
    public static final int GXand = 1;
    public static final int GXandReverse = 2;
    public static final int GXcopy = 3;
    public static final int GXandInverted = 4;
    public static final int GXnoop = 5;
    public static final int GXxor = 6;
    public static final int GXor = 7;
    public static final int GXnor = 8;
    public static final int GXequiv = 9;
    public static final int GXinvert = 10;
    public static final int GXorReverse = 11;
    public static final int GXcopyInverted = 12;
    public static final int GXorInverted = 13;
    public static final int GXnand = 14;
    public static final int GXset = 15;
    public static final int LineSolid = 0;
    public static final int LineOnOffDash = 1;
    public static final int LineDoubleDash = 2;
    public static final int CapNotLast = 0;
    public static final int CapButt = 1;
    public static final int CapRound = 2;
    public static final int CapProjecting = 3;
    public static final int JoinMiter = 0;
    public static final int JoinRound = 1;
    public static final int JoinBevel = 2;
    public static final int FillSolid = 0;
    public static final int FillTiled = 1;
    public static final int FillStippled = 2;
    public static final int FillOpaqueStippled = 3;
    public static final int EvenOddRule = 0;
    public static final int WindingRule = 1;
    public static final int ClipByChildren = 0;
    public static final int IncludeInferiors = 1;
    public static final int Unsorted = 0;
    public static final int YSorted = 1;
    public static final int YXSorted = 2;
    public static final int YXBanded = 3;
    public static final int CoordModeOrigin = 0;
    public static final int CoordModePrevious = 1;
    public static final int Complex = 0;
    public static final int Nonconvex = 1;
    public static final int Convex = 2;
    public static final int ArcChord = 0;
    public static final int ArcPieSlice = 1;
    public static final int GCFunction = 1;
    public static final int GCPlaneMask = 2;
    public static final int GCForeground = 4;
    public static final int GCBackground = 8;
    public static final int GCLineWidth = 16;
    public static final int GCLineStyle = 32;
    public static final int GCCapStyle = 64;
    public static final int GCJoinStyle = 128;
    public static final int GCFillStyle = 256;
    public static final int GCFillRule = 512;
    public static final int GCTile = 1024;
    public static final int GCStipple = 2048;
    public static final int GCTileStipXOrigin = 4096;
    public static final int GCTileStipYOrigin = 8192;
    public static final int GCFont = 16384;
    public static final int GCSubwindowMode = 32768;
    public static final int GCGraphicsExposures = 65536;
    public static final int GCClipXOrigin = 131072;
    public static final int GCClipYOrigin = 262144;
    public static final int GCClipMask = 524288;
    public static final int GCDashOffset = 0x100000;
    public static final int GCDashList = 0x200000;
    public static final int GCArcMode = 0x400000;
    public static final int GCLastBit = 22;
    private static final SharedLibrary X11 = Library.loadNative(X11.class, null, new String[]{"libX11.so.6", "libX11.so"});

    protected X11() {
        throw new UnsupportedOperationException();
    }

    public static SharedLibrary getLibrary() {
        return X11;
    }

    public static long nXOpenDisplay(long l) {
        long l2 = Functions.XOpenDisplay;
        return JNI.invokePP(l, l2);
    }

    @NativeType(value="Display *")
    public static long XOpenDisplay(@Nullable @NativeType(value="char const *") ByteBuffer byteBuffer) {
        if (Checks.CHECKS) {
            Checks.checkNT1Safe(byteBuffer);
        }
        return org.lwjgl.system.linux.X11.nXOpenDisplay(MemoryUtil.memAddressSafe(byteBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="Display *")
    public static long XOpenDisplay(@Nullable @NativeType(value="char const *") CharSequence charSequence) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n = memoryStack.getPointer();
        try {
            memoryStack.nASCIISafe(charSequence, false);
            long l = charSequence == null ? 0L : memoryStack.getPointerAddress();
            long l2 = org.lwjgl.system.linux.X11.nXOpenDisplay(l);
            return l2;
        } finally {
            memoryStack.setPointer(n);
        }
    }

    public static void XCloseDisplay(@NativeType(value="Display *") long l) {
        long l2 = Functions.XCloseDisplay;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.invokePV(l, l2);
    }

    public static int XDefaultScreen(@NativeType(value="Display *") long l) {
        long l2 = Functions.XDefaultScreen;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        return JNI.invokePI(l, l2);
    }

    @NativeType(value="Window")
    public static long XRootWindow(@NativeType(value="Display *") long l, int n) {
        long l2 = Functions.XRootWindow;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        return JNI.invokePP(l, n, l2);
    }

    public static long nXCreateColormap(long l, long l2, long l3, int n) {
        long l4 = Functions.XCreateColormap;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        return JNI.invokePPPP(l, l2, l3, n, l4);
    }

    @NativeType(value="Colormap")
    public static long XCreateColormap(@NativeType(value="Display *") long l, @NativeType(value="Window") long l2, @NativeType(value="Visual *") Visual visual, int n) {
        return org.lwjgl.system.linux.X11.nXCreateColormap(l, l2, visual.address(), n);
    }

    public static int XFreeColormap(@NativeType(value="Display *") long l, @NativeType(value="Colormap") long l2) {
        long l3 = Functions.XFreeColormap;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        return JNI.invokePPI(l, l2, l3);
    }

    public static long nXCreateWindow(long l, long l2, int n, int n2, int n3, int n4, int n5, int n6, int n7, long l3, long l4, long l5) {
        long l6 = Functions.XCreateWindow;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        return JNI.invokePPPPPP(l, l2, n, n2, n3, n4, n5, n6, n7, l3, l4, l5, l6);
    }

    @NativeType(value="Window")
    public static long XCreateWindow(@NativeType(value="Display *") long l, @NativeType(value="Window") long l2, int n, int n2, @NativeType(value="unsigned int") int n3, @NativeType(value="unsigned int") int n4, @NativeType(value="unsigned int") int n5, int n6, @NativeType(value="unsigned int") int n7, @NativeType(value="Visual *") Visual visual, @NativeType(value="unsigned long") long l3, @NativeType(value="XSetWindowAttributes *") XSetWindowAttributes xSetWindowAttributes) {
        return org.lwjgl.system.linux.X11.nXCreateWindow(l, l2, n, n2, n3, n4, n5, n6, n7, visual.address(), l3, xSetWindowAttributes.address());
    }

    public static int XDestroyWindow(@NativeType(value="Display *") long l, @NativeType(value="Window") long l2) {
        long l3 = Functions.XDestroyWindow;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        return JNI.invokePPI(l, l2, l3);
    }

    public static int nXFree(long l) {
        long l2 = Functions.XFree;
        return JNI.invokePI(l, l2);
    }

    public static int XFree(@NativeType(value="void *") ByteBuffer byteBuffer) {
        return org.lwjgl.system.linux.X11.nXFree(MemoryUtil.memAddress(byteBuffer));
    }

    public static int XFree(@NativeType(value="void *") PointerBuffer pointerBuffer) {
        return org.lwjgl.system.linux.X11.nXFree(MemoryUtil.memAddress(pointerBuffer));
    }

    static SharedLibrary access$000() {
        return X11;
    }

    public static final class Functions {
        public static final long XOpenDisplay = APIUtil.apiGetFunctionAddress(org.lwjgl.system.linux.X11.access$000(), "XOpenDisplay");
        public static final long XCloseDisplay = APIUtil.apiGetFunctionAddress(org.lwjgl.system.linux.X11.access$000(), "XCloseDisplay");
        public static final long XDefaultScreen = APIUtil.apiGetFunctionAddress(org.lwjgl.system.linux.X11.access$000(), "XDefaultScreen");
        public static final long XRootWindow = APIUtil.apiGetFunctionAddress(org.lwjgl.system.linux.X11.access$000(), "XRootWindow");
        public static final long XCreateColormap = APIUtil.apiGetFunctionAddress(org.lwjgl.system.linux.X11.access$000(), "XCreateColormap");
        public static final long XFreeColormap = APIUtil.apiGetFunctionAddress(org.lwjgl.system.linux.X11.access$000(), "XFreeColormap");
        public static final long XCreateWindow = APIUtil.apiGetFunctionAddress(org.lwjgl.system.linux.X11.access$000(), "XCreateWindow");
        public static final long XDestroyWindow = APIUtil.apiGetFunctionAddress(org.lwjgl.system.linux.X11.access$000(), "XDestroyWindow");
        public static final long XFree = APIUtil.apiGetFunctionAddress(org.lwjgl.system.linux.X11.access$000(), "XFree");

        private Functions() {
        }
    }
}

