/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.opengl;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;
import javax.annotation.Nullable;
import org.lwjgl.opengl.GLCapabilities;
import org.lwjgl.opengl.GLX11;
import org.lwjgl.opengl.GLXCapabilities;
import org.lwjgl.opengl.WGL;
import org.lwjgl.opengl.WGLCapabilities;
import org.lwjgl.system.APIUtil;
import org.lwjgl.system.Checks;
import org.lwjgl.system.Configuration;
import org.lwjgl.system.FunctionProvider;
import org.lwjgl.system.JNI;
import org.lwjgl.system.Library;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeResource;
import org.lwjgl.system.Platform;
import org.lwjgl.system.SharedLibrary;
import org.lwjgl.system.ThreadLocalUtil;
import org.lwjgl.system.linux.X11;
import org.lwjgl.system.macosx.MacOSXLibrary;
import org.lwjgl.system.windows.GDI32;
import org.lwjgl.system.windows.PIXELFORMATDESCRIPTOR;
import org.lwjgl.system.windows.User32;
import org.lwjgl.system.windows.WNDCLASSEX;
import org.lwjgl.system.windows.WindowsLibrary;
import org.lwjgl.system.windows.WindowsUtil;

public final class GL {
    @Nullable
    private static final APIUtil.APIVersion MAX_VERSION;
    @Nullable
    private static FunctionProvider functionProvider;
    private static final ThreadLocal<GLCapabilities> capabilitiesTLS;
    private static ICD icd;
    @Nullable
    private static WGLCapabilities capabilitiesWGL;
    @Nullable
    private static GLXCapabilities capabilitiesGLXClient;
    @Nullable
    private static GLXCapabilities capabilitiesGLX;

    private GL() {
    }

    static void initialize() {
    }

    public static void create() {
        SharedLibrary sharedLibrary;
        switch (4.$SwitchMap$org$lwjgl$system$Platform[Platform.get().ordinal()]) {
            case 1: {
                sharedLibrary = Library.loadNative(GL.class, Configuration.OPENGL_LIBRARY_NAME, "libGL.so.1", "libGL.so");
                break;
            }
            case 2: {
                String string = Configuration.OPENGL_LIBRARY_NAME.get();
                sharedLibrary = string != null ? Library.loadNative(GL.class, string) : MacOSXLibrary.getWithIdentifier("com.apple.opengl");
                break;
            }
            case 3: {
                sharedLibrary = Library.loadNative(GL.class, Configuration.OPENGL_LIBRARY_NAME, "opengl32");
                break;
            }
            default: {
                throw new IllegalStateException();
            }
        }
        GL.create(sharedLibrary);
    }

    public static void create(String string) {
        GL.create(Library.loadNative(GL.class, string));
    }

    private static void create(SharedLibrary sharedLibrary) {
        try {
            SharedLibraryGL sharedLibraryGL;
            switch (4.$SwitchMap$org$lwjgl$system$Platform[Platform.get().ordinal()]) {
                case 3: {
                    sharedLibraryGL = new SharedLibraryGL(sharedLibrary){
                        private final long wglGetProcAddress;
                        {
                            this.wglGetProcAddress = this.library.getFunctionAddress("wglGetProcAddress");
                        }

                        @Override
                        long getExtensionAddress(long l) {
                            return JNI.callPP(l, this.wglGetProcAddress);
                        }
                    };
                    break;
                }
                case 1: {
                    sharedLibraryGL = new SharedLibraryGL(sharedLibrary){
                        private final long glXGetProcAddress;
                        {
                            long l = this.library.getFunctionAddress("glXGetProcAddress");
                            if (l == 0L) {
                                l = this.library.getFunctionAddress("glXGetProcAddressARB");
                            }
                            this.glXGetProcAddress = l;
                        }

                        @Override
                        long getExtensionAddress(long l) {
                            return this.glXGetProcAddress == 0L ? 0L : JNI.callPP(l, this.glXGetProcAddress);
                        }
                    };
                    break;
                }
                case 2: {
                    sharedLibraryGL = new SharedLibraryGL(sharedLibrary){

                        @Override
                        long getExtensionAddress(long l) {
                            return 0L;
                        }
                    };
                    break;
                }
                default: {
                    throw new IllegalStateException();
                }
            }
            GL.create((FunctionProvider)sharedLibraryGL);
        } catch (RuntimeException runtimeException) {
            sharedLibrary.free();
            throw runtimeException;
        }
    }

    public static void create(FunctionProvider functionProvider) {
        if (GL.functionProvider != null) {
            throw new IllegalStateException("OpenGL library has already been loaded.");
        }
        GL.functionProvider = functionProvider;
        ThreadLocalUtil.setFunctionMissingAddresses(GLCapabilities.class, 3);
    }

    public static void destroy() {
        if (functionProvider == null) {
            return;
        }
        ThreadLocalUtil.setFunctionMissingAddresses(null, 3);
        capabilitiesWGL = null;
        capabilitiesGLX = null;
        if (functionProvider instanceof NativeResource) {
            ((NativeResource)((Object)functionProvider)).free();
        }
        functionProvider = null;
    }

    @Nullable
    public static FunctionProvider getFunctionProvider() {
        return functionProvider;
    }

    public static void setCapabilities(@Nullable GLCapabilities gLCapabilities) {
        capabilitiesTLS.set(gLCapabilities);
        ThreadLocalUtil.setEnv(gLCapabilities == null ? 0L : MemoryUtil.memAddress(gLCapabilities.addresses), 3);
        icd.set(gLCapabilities);
    }

    public static GLCapabilities getCapabilities() {
        return GL.checkCapabilities(capabilitiesTLS.get());
    }

    private static GLCapabilities checkCapabilities(@Nullable GLCapabilities gLCapabilities) {
        if (Checks.CHECKS && gLCapabilities == null) {
            throw new IllegalStateException("No GLCapabilities instance set for the current thread. Possible solutions:\n\ta) Call GL.createCapabilities() after making a context current in the current thread.\n\tb) Call GL.setCapabilities() if a GLCapabilities instance already exists for the current context.");
        }
        return gLCapabilities;
    }

    public static WGLCapabilities getCapabilitiesWGL() {
        if (capabilitiesWGL == null) {
            capabilitiesWGL = GL.createCapabilitiesWGLDummy();
        }
        return capabilitiesWGL;
    }

    static GLXCapabilities getCapabilitiesGLXClient() {
        if (capabilitiesGLXClient == null) {
            capabilitiesGLXClient = GL.initCapabilitiesGLX(true);
        }
        return capabilitiesGLXClient;
    }

    public static GLXCapabilities getCapabilitiesGLX() {
        if (capabilitiesGLX == null) {
            capabilitiesGLX = GL.initCapabilitiesGLX(false);
        }
        return capabilitiesGLX;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private static GLXCapabilities initCapabilitiesGLX(boolean bl) {
        long l = X11.nXOpenDisplay(0L);
        try {
            GLXCapabilities gLXCapabilities = GL.createCapabilitiesGLX(l, bl ? -1 : X11.XDefaultScreen(l));
            return gLXCapabilities;
        } finally {
            X11.XCloseDisplay(l);
        }
    }

    public static GLCapabilities createCapabilities() {
        return GL.createCapabilities(false);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Unable to fully structure code
     * Could not resolve type clashes
     */
    public static GLCapabilities createCapabilities(boolean var0) {
        var1_1 = GL.functionProvider;
        if (var1_1 == null) {
            throw new IllegalStateException("OpenGL library has not been loaded.");
        }
        var2_2 = null;
        try {
            var3_3 = var1_1.getFunctionAddress("glGetError");
            var5_4 = var1_1.getFunctionAddress("glGetString");
            var7_5 = var1_1.getFunctionAddress("glGetIntegerv");
            if (var3_3 == 0L || var5_4 == 0L || var7_5 == 0L) {
                throw new IllegalStateException("Core OpenGL functions could not be found. Make sure that the OpenGL library has been loaded correctly.");
            }
            var9_6 = JNI.callI(var3_3);
            if (var9_6 != 0) {
                APIUtil.apiLog(String.format("An OpenGL context was in an error state before the creation of its capabilities instance. Error: 0x%X", new Object[]{var9_6}));
            }
            var12_7 = MemoryStack.stackPush();
            var13_8 = null;
            try {
                var14_9 = var12_7.ints(0);
                JNI.callPV(33307, MemoryUtil.memAddress(var14_9), var7_5);
                if (JNI.callI(var3_3) == 0 && 3 <= (var10_12 = var14_9.get(0))) {
                    JNI.callPV(33308, MemoryUtil.memAddress(var14_9), var7_5);
                    var11_13 = var14_9.get(0);
                } else {
                    var15_14 = MemoryUtil.memUTF8Safe(JNI.callP(7938, var5_4));
                    if (var15_14 == null || JNI.callI(var3_3) != 0) {
                        throw new IllegalStateException("There is no OpenGL context current in the current thread.");
                    }
                    var16_17 = APIUtil.apiParseVersion(var15_14);
                    var10_12 = var16_17.major;
                    var11_13 = var16_17.minor;
                }
            } catch (Throwable var14_10) {
                var13_8 = var14_10;
                throw var14_10;
            } finally {
                if (var12_7 != null) {
                    GL.$closeResource((Throwable)var13_8, (AutoCloseable)var12_7);
                }
            }
            if (var10_12 < 1 || var10_12 == 1 && var11_13 < 1) {
                throw new IllegalStateException("OpenGL 1.1 is required.");
            }
            var12_7 = new int[]{5, 1, 3, 6};
            var13_8 = new HashSet<E>(512);
            var14_11 = Math.min(var10_12, ((Object)var12_7).length);
            if (GL.MAX_VERSION != null) {
                var14_11 = Math.min(GL.MAX_VERSION.major, var14_11);
            }
            for (var15_15 = 1; var15_15 <= var14_11; ++var15_15) {
                var16_18 /* !! */  = var12_7[var15_15 - 1];
                if (var15_15 == var10_12) {
                    var16_18 /* !! */  = Math.min(var11_13, (int)var16_18 /* !! */ );
                }
                if (GL.MAX_VERSION != null && var15_15 == GL.MAX_VERSION.major) {
                    var16_18 /* !! */  = Math.min(GL.MAX_VERSION.minor, (int)var16_18 /* !! */ );
                }
                v0 = var17_20 = var15_15 == 1 ? 1 : 0;
                while (var17_20 <= var16_18 /* !! */ ) {
                    var13_8.add(String.format("OpenGL%d%d", new Object[]{var15_15, var17_20}));
                    ++var17_20;
                }
            }
            if (var10_12 < 3) {
                var15_16 = MemoryUtil.memASCIISafe(JNI.callP(7939, var5_4));
                if (var15_16 != null) {
                    var16_17 = new StringTokenizer((String)var15_16);
                    while (var16_17.hasMoreTokens()) {
                        var13_8.add(var16_17.nextToken());
                    }
                }
            } else {
                var15_16 = MemoryStack.stackPush();
                var16_17 = null;
                try {
                    var17_21 = var15_16.ints(0);
                    JNI.callPV(33309, MemoryUtil.memAddress(var17_21), var7_5);
                    var18_23 = var17_21.get(0);
                    var19_24 = APIUtil.apiGetFunctionAddress(var1_1, "glGetStringi");
                    for (var21_25 = 0; var21_25 < var18_23; ++var21_25) {
                        var13_8.add(MemoryUtil.memASCII(JNI.callP(7939, var21_25, var19_24)));
                    }
                    JNI.callPV(33310, MemoryUtil.memAddress(var17_21), var7_5);
                    if ((var17_21.get(0) & 1) != 0) {
                        var0 = true;
                    }
                    if (3 >= var10_12 && 1 > var11_13) ** GOTO lbl98
                    if (3 < var10_12 || 2 <= var11_13) {
                        JNI.callPV(37158, MemoryUtil.memAddress(var17_21), var7_5);
                        if ((var17_21.get(0) & 1) == 0) ** GOTO lbl98
                        var0 = true;
                    }
                    var0 = var13_8.contains("GL_ARB_compatibility") == false;
                } catch (Throwable var17_22) {
                    var16_17 = var17_22;
                    throw var17_22;
                } finally {
                    if (var15_16 != null) {
                        GL.$closeResource((Throwable)var16_17, (AutoCloseable)var15_16);
                    }
                }
            }
lbl98:
            // 6 sources

            var2_2 = new GLCapabilities(var1_1, (Set<String>)var13_8, var0);
            var15_16 = var2_2;
        } catch (Throwable var23_27) {
            GL.setCapabilities(var2_2);
            throw var23_27;
        }
        GL.setCapabilities(var2_2);
        return var15_16;
    }

    /*
     * Loose catch block
     */
    private static WGLCapabilities createCapabilitiesWGLDummy() {
        long l = WGL.wglGetCurrentDC();
        if (l != 0L) {
            return GL.createCapabilitiesWGL(l);
        }
        int n = 0;
        long l2 = 0L;
        long l3 = 0L;
        try {
            MemoryStack memoryStack = MemoryStack.stackPush();
            Throwable throwable = null;
            try {
                PIXELFORMATDESCRIPTOR pIXELFORMATDESCRIPTOR;
                WNDCLASSEX wNDCLASSEX = WNDCLASSEX.callocStack(memoryStack).cbSize(WNDCLASSEX.SIZEOF).style(3).hInstance(WindowsLibrary.HINSTANCE).lpszClassName(memoryStack.UTF16("WGL"));
                MemoryUtil.memPutAddress(wNDCLASSEX.address() + (long)WNDCLASSEX.LPFNWNDPROC, User32.Functions.DefWindowProc);
                n = User32.RegisterClassEx(wNDCLASSEX);
                if (n == 0) {
                    throw new IllegalStateException("Failed to register WGL window class");
                }
                l2 = Checks.check(User32.nCreateWindowEx(0, n & 0xFFFF, 0L, 114229248, 0, 0, 1, 1, 0L, 0L, 0L, 0L));
                l = Checks.check(User32.GetDC(l2));
                int n2 = GDI32.ChoosePixelFormat(l, pIXELFORMATDESCRIPTOR = PIXELFORMATDESCRIPTOR.callocStack(memoryStack).nSize((short)PIXELFORMATDESCRIPTOR.SIZEOF).nVersion((short)1).dwFlags(32));
                if (n2 == 0) {
                    WindowsUtil.windowsThrowException("Failed to choose an OpenGL-compatible pixel format");
                }
                if (GDI32.DescribePixelFormat(l, n2, pIXELFORMATDESCRIPTOR) == 0) {
                    WindowsUtil.windowsThrowException("Failed to obtain pixel format information");
                }
                if (!GDI32.SetPixelFormat(l, n2, pIXELFORMATDESCRIPTOR)) {
                    WindowsUtil.windowsThrowException("Failed to set the pixel format");
                }
                l3 = Checks.check(WGL.wglCreateContext(l));
                WGL.wglMakeCurrent(l, l3);
                WGLCapabilities wGLCapabilities = GL.createCapabilitiesWGL(l);
                return wGLCapabilities;
            } catch (Throwable throwable2) {
                throwable = throwable2;
                throw throwable2;
            } finally {
                if (memoryStack != null) {
                    GL.$closeResource(throwable, memoryStack);
                }
            }
            {
                catch (Throwable throwable3) {
                    throw throwable3;
                }
            }
        } finally {
            if (l3 != 0L) {
                WGL.wglMakeCurrent(0L, 0L);
                WGL.wglDeleteContext(l3);
            }
            if (l2 != 0L) {
                User32.DestroyWindow(l2);
            }
            if (n != 0) {
                User32.nUnregisterClass(n & 0xFFFF, WindowsLibrary.HINSTANCE);
            }
        }
    }

    public static WGLCapabilities createCapabilitiesWGL() {
        long l = WGL.wglGetCurrentDC();
        if (l == 0L) {
            throw new IllegalStateException("Failed to retrieve the device context of the current OpenGL context");
        }
        return GL.createCapabilitiesWGL(l);
    }

    private static WGLCapabilities createCapabilitiesWGL(long l) {
        FunctionProvider functionProvider = GL.functionProvider;
        if (functionProvider == null) {
            throw new IllegalStateException("OpenGL library has not been loaded.");
        }
        String string = null;
        long l2 = functionProvider.getFunctionAddress("wglGetExtensionsStringARB");
        if (l2 != 0L) {
            string = MemoryUtil.memASCII(JNI.callPP(l, l2));
        } else {
            l2 = functionProvider.getFunctionAddress("wglGetExtensionsStringEXT");
            if (l2 != 0L) {
                string = MemoryUtil.memASCII(JNI.callP(l2));
            }
        }
        HashSet<String> hashSet = new HashSet<String>(32);
        if (string != null) {
            StringTokenizer stringTokenizer = new StringTokenizer(string);
            while (stringTokenizer.hasMoreTokens()) {
                hashSet.add(stringTokenizer.nextToken());
            }
        }
        return new WGLCapabilities(functionProvider, hashSet);
    }

    public static GLXCapabilities createCapabilitiesGLX(long l) {
        return GL.createCapabilitiesGLX(l, X11.XDefaultScreen(l));
    }

    public static GLXCapabilities createCapabilitiesGLX(long l, int n) {
        int n2;
        int n3;
        Object object;
        FunctionProvider functionProvider = GL.functionProvider;
        if (functionProvider == null) {
            throw new IllegalStateException("OpenGL library has not been loaded.");
        }
        Object object2 = MemoryStack.stackPush();
        Object object3 = null;
        try {
            IntBuffer intBuffer = ((MemoryStack)object2).ints(0);
            object = ((MemoryStack)object2).ints(0);
            if (!GLX11.glXQueryVersion(l, intBuffer, (IntBuffer)object)) {
                throw new IllegalStateException("Failed to query GLX version");
            }
            n3 = intBuffer.get(0);
            n2 = ((IntBuffer)object).get(0);
            if (n3 != 1) {
                throw new IllegalStateException("Invalid GLX major version: " + n3);
            }
        } catch (Throwable throwable) {
            object3 = throwable;
            throw throwable;
        } finally {
            if (object2 != null) {
                GL.$closeResource((Throwable)object3, (AutoCloseable)object2);
            }
        }
        object2 = new HashSet(32);
        object3 = new int[][]{{1, 2, 3, 4}};
        for (int i = 1; i <= ((Object)object3).length; ++i) {
            for (Object object4 : object = object3[i - 1]) {
                if (i >= n3 && (i != n3 || object4 > n2)) continue;
                object2.add("GLX" + i + (int)object4);
            }
        }
        if (1 <= n2) {
            String string;
            long l2;
            if (n == -1) {
                l2 = functionProvider.getFunctionAddress("glXGetClientString");
                string = MemoryUtil.memASCIISafe(JNI.callPP(l, 3, l2));
            } else {
                l2 = functionProvider.getFunctionAddress("glXQueryExtensionsString");
                string = MemoryUtil.memASCIISafe(JNI.callPP(l, n, l2));
            }
            if (string != null) {
                StringTokenizer stringTokenizer = new StringTokenizer(string);
                while (stringTokenizer.hasMoreTokens()) {
                    object2.add(stringTokenizer.nextToken());
                }
            }
        }
        return new GLXCapabilities(functionProvider, (Set<String>)object2);
    }

    static GLCapabilities getICD() {
        return GL.checkCapabilities(icd.get());
    }

    private static void $closeResource(Throwable throwable, AutoCloseable autoCloseable) {
        if (throwable != null) {
            try {
                autoCloseable.close();
            } catch (Throwable throwable2) {
                throwable.addSuppressed(throwable2);
            }
        } else {
            autoCloseable.close();
        }
    }

    static ICD access$102(ICD iCD) {
        icd = iCD;
        return icd;
    }

    static {
        capabilitiesTLS = new ThreadLocal();
        icd = new ICDStatic(null);
        Library.loadSystem(System::load, System::loadLibrary, GL.class, Platform.mapLibraryNameBundled("lwjgl_opengl"));
        MAX_VERSION = APIUtil.apiParseVersion(Configuration.OPENGL_MAXVERSION);
        if (!Configuration.OPENGL_EXPLICIT_INIT.get(false).booleanValue()) {
            GL.create();
        }
    }

    private static class ICDStatic
    implements ICD {
        @Nullable
        private static GLCapabilities tempCaps;

        private ICDStatic() {
        }

        @Override
        public void set(@Nullable GLCapabilities gLCapabilities) {
            if (tempCaps == null) {
                tempCaps = gLCapabilities;
            } else if (gLCapabilities != null && gLCapabilities != tempCaps && ThreadLocalUtil.areCapabilitiesDifferent(ICDStatic.tempCaps.addresses, gLCapabilities.addresses)) {
                APIUtil.apiLog("[WARNING] Incompatible context detected. Falling back to thread-local lookup for GL contexts.");
                GL.access$102(GL::getCapabilities);
            }
        }

        @Override
        public GLCapabilities get() {
            return WriteOnce.caps;
        }

        ICDStatic(1 var1_1) {
            this();
        }

        static GLCapabilities access$200() {
            return tempCaps;
        }

        private static final class WriteOnce {
            @Nullable
            static final GLCapabilities caps = ICDStatic.access$200();

            private WriteOnce() {
            }

            static {
                if (caps == null) {
                    throw new IllegalStateException("No GLCapabilities instance has been set");
                }
            }
        }
    }

    private static interface ICD {
        default public void set(@Nullable GLCapabilities gLCapabilities) {
        }

        @Nullable
        public GLCapabilities get();
    }

    private static abstract class SharedLibraryGL
    extends SharedLibrary.Delegate {
        SharedLibraryGL(SharedLibrary sharedLibrary) {
            super(sharedLibrary);
        }

        abstract long getExtensionAddress(long var1);

        @Override
        public long getFunctionAddress(ByteBuffer byteBuffer) {
            long l = this.getExtensionAddress(MemoryUtil.memAddress(byteBuffer));
            if (l == 0L && (l = this.library.getFunctionAddress(byteBuffer)) == 0L && Checks.DEBUG_FUNCTIONS) {
                APIUtil.apiLog("Failed to locate address for GL function " + MemoryUtil.memASCII(byteBuffer));
            }
            return l;
        }
    }
}

