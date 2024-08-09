/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.openal;

import java.io.Serializable;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;
import javax.annotation.Nullable;
import org.lwjgl.openal.AL;
import org.lwjgl.openal.ALCCapabilities;
import org.lwjgl.system.APIUtil;
import org.lwjgl.system.Checks;
import org.lwjgl.system.Configuration;
import org.lwjgl.system.FunctionProviderLocal;
import org.lwjgl.system.JNI;
import org.lwjgl.system.Library;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeResource;
import org.lwjgl.system.Platform;
import org.lwjgl.system.SharedLibrary;

public final class ALC {
    @Nullable
    private static FunctionProviderLocal functionProvider;
    @Nullable
    private static ALCCapabilities icd;

    private ALC() {
    }

    public static void create() {
        String string;
        switch (1.$SwitchMap$org$lwjgl$system$Platform[Platform.get().ordinal()]) {
            case 1: 
            case 2: {
                string = "openal";
                break;
            }
            case 3: {
                string = "OpenAL";
                break;
            }
            default: {
                throw new IllegalStateException();
            }
        }
        ALC.create(Configuration.OPENAL_LIBRARY_NAME.get(Platform.mapLibraryNameBundled(string)));
    }

    public static void create(String string) {
        SharedLibrary sharedLibrary = Library.loadNative(ALC.class, string, true);
        try {
            ALC.create(new SharedLibraryAL(sharedLibrary));
        } catch (RuntimeException runtimeException) {
            sharedLibrary.free();
            throw runtimeException;
        }
    }

    public static void create(FunctionProviderLocal functionProviderLocal) {
        if (functionProvider != null) {
            throw new IllegalStateException("ALC has already been created.");
        }
        functionProvider = functionProviderLocal;
        icd = new ALCCapabilities(functionProviderLocal, 0L, Collections.emptySet());
        AL.init();
    }

    public static void destroy() {
        if (functionProvider == null) {
            return;
        }
        AL.destroy();
        icd = null;
        if (functionProvider instanceof NativeResource) {
            ((NativeResource)((Object)functionProvider)).free();
        }
        functionProvider = null;
    }

    static <T> T check(@Nullable T t) {
        if (t == null) {
            throw new IllegalStateException("OpenAL library has not been loaded.");
        }
        return t;
    }

    public static FunctionProviderLocal getFunctionProvider() {
        return ALC.check(functionProvider);
    }

    static ALCCapabilities getICD() {
        return ALC.check(icd);
    }

    public static ALCCapabilities createCapabilities(long l) {
        String string;
        int n;
        int n2;
        FunctionProviderLocal functionProviderLocal = ALC.getFunctionProvider();
        long l2 = functionProviderLocal.getFunctionAddress("alcGetIntegerv");
        long l3 = functionProviderLocal.getFunctionAddress("alcGetString");
        long l4 = functionProviderLocal.getFunctionAddress("alcIsExtensionPresent");
        if (l2 == 0L || l3 == 0L || l4 == 0L) {
            throw new IllegalStateException("Core ALC functions could not be found. Make sure that OpenAL has been loaded.");
        }
        Object object = MemoryStack.stackPush();
        Serializable serializable = null;
        try {
            IntBuffer intBuffer = ((MemoryStack)object).mallocInt(1);
            JNI.invokePPV(l, 4096, 1, MemoryUtil.memAddress(intBuffer), l2);
            n2 = intBuffer.get(0);
            JNI.invokePPV(l, 4097, 1, MemoryUtil.memAddress(intBuffer), l2);
            n = intBuffer.get(0);
        } catch (Throwable throwable) {
            serializable = throwable;
            throw throwable;
        } finally {
            if (object != null) {
                ALC.$closeResource((Throwable)serializable, (AutoCloseable)object);
            }
        }
        object = new int[][]{{0, 1}};
        serializable = new HashSet(16);
        for (int i = 1; i <= ((Object)object).length; ++i) {
            for (Object object2 : string = object[i - 1]) {
                if (i >= n2 && (i != n2 || object2 > n)) continue;
                serializable.add("OpenALC" + i + (int)object2);
            }
        }
        String string2 = MemoryUtil.memASCIISafe(JNI.invokePP(l, 4102, l3));
        if (string2 != null) {
            string = new StringTokenizer(string2);
            while (((StringTokenizer)((Object)string)).hasMoreTokens()) {
                String string3 = ((StringTokenizer)((Object)string)).nextToken();
                MemoryStack memoryStack = MemoryStack.stackPush();
                Throwable throwable = null;
                try {
                    if (!JNI.invokePPZ(l, MemoryUtil.memAddress(memoryStack.ASCII(string3, false)), l4)) continue;
                    serializable.add(string3);
                } catch (Throwable throwable2) {
                    throwable = throwable2;
                    throw throwable2;
                } finally {
                    if (memoryStack != null) {
                        ALC.$closeResource(throwable, memoryStack);
                    }
                }
            }
        }
        return new ALCCapabilities(functionProviderLocal, l, (Set<String>)((Object)serializable));
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

    static {
        if (!Configuration.OPENAL_EXPLICIT_INIT.get(false).booleanValue()) {
            ALC.create();
        }
    }

    private static class SharedLibraryAL
    extends SharedLibrary.Delegate
    implements FunctionProviderLocal {
        private final long alcGetProcAddress = this.getFunctionAddress("alcGetProcAddress");

        protected SharedLibraryAL(SharedLibrary sharedLibrary) {
            super(sharedLibrary);
            if (this.alcGetProcAddress == 0L) {
                throw new RuntimeException("A core ALC function is missing. Make sure that the OpenAL library has been loaded correctly.");
            }
        }

        @Override
        public long getFunctionAddress(ByteBuffer byteBuffer) {
            long l = this.library.getFunctionAddress(byteBuffer);
            if (l == 0L && Checks.DEBUG_FUNCTIONS) {
                APIUtil.apiLog("Failed to locate address for ALC core function " + MemoryUtil.memASCII(byteBuffer));
            }
            return l;
        }

        @Override
        public long getFunctionAddress(long l, ByteBuffer byteBuffer) {
            long l2 = JNI.invokePPP(l, MemoryUtil.memAddress(byteBuffer), this.alcGetProcAddress);
            if (l2 == 0L && Checks.DEBUG_FUNCTIONS) {
                APIUtil.apiLog("Failed to locate address for ALC extension function " + MemoryUtil.memASCII(byteBuffer));
            }
            return l2;
        }
    }
}

