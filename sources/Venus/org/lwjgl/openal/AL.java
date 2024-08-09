/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.openal;

import java.nio.ByteBuffer;
import java.util.HashSet;
import java.util.StringTokenizer;
import javax.annotation.Nullable;
import org.lwjgl.openal.ALC;
import org.lwjgl.openal.ALCCapabilities;
import org.lwjgl.openal.ALCapabilities;
import org.lwjgl.openal.EXTThreadLocalContext;
import org.lwjgl.system.APIUtil;
import org.lwjgl.system.Checks;
import org.lwjgl.system.FunctionProvider;
import org.lwjgl.system.JNI;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.ThreadLocalUtil;

public final class AL {
    @Nullable
    private static FunctionProvider functionProvider;
    @Nullable
    private static ALCapabilities processCaps;
    private static final ThreadLocal<ALCapabilities> capabilitiesTLS;
    private static ICD icd;

    private AL() {
    }

    static void init() {
        functionProvider = new FunctionProvider(){
            private final long alGetProcAddress = ALC.getFunctionProvider().getFunctionAddress("alGetProcAddress");

            @Override
            public long getFunctionAddress(ByteBuffer byteBuffer) {
                long l = JNI.invokePP(MemoryUtil.memAddress(byteBuffer), this.alGetProcAddress);
                if (l == 0L && Checks.DEBUG_FUNCTIONS) {
                    APIUtil.apiLog("Failed to locate address for AL function " + MemoryUtil.memASCII(byteBuffer));
                }
                return l;
            }
        };
    }

    static void destroy() {
        if (functionProvider == null) {
            return;
        }
        AL.setCurrentProcess(null);
        functionProvider = null;
    }

    public static void setCurrentProcess(@Nullable ALCapabilities aLCapabilities) {
        processCaps = aLCapabilities;
        capabilitiesTLS.set(null);
        icd.set(aLCapabilities);
    }

    public static void setCurrentThread(@Nullable ALCapabilities aLCapabilities) {
        capabilitiesTLS.set(aLCapabilities);
        icd.set(aLCapabilities);
    }

    public static ALCapabilities getCapabilities() {
        ALCapabilities aLCapabilities = capabilitiesTLS.get();
        if (aLCapabilities == null) {
            aLCapabilities = processCaps;
        }
        return AL.checkCapabilities(aLCapabilities);
    }

    private static ALCapabilities checkCapabilities(@Nullable ALCapabilities aLCapabilities) {
        if (aLCapabilities == null) {
            throw new IllegalStateException("No ALCapabilities instance set for the current thread or process. Possible solutions:\n\ta) Call AL.createCapabilities() after making a context current.\n\tb) Call AL.setCurrentProcess() or AL.setCurrentThread() if an ALCapabilities instance already exists.");
        }
        return aLCapabilities;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static ALCapabilities createCapabilities(ALCCapabilities aLCCapabilities) {
        Object object;
        FunctionProvider functionProvider = ALC.check(AL.functionProvider);
        ALCapabilities aLCapabilities = null;
        try {
            long l = functionProvider.getFunctionAddress("alGetString");
            long l2 = functionProvider.getFunctionAddress("alGetError");
            long l3 = functionProvider.getFunctionAddress("alIsExtensionPresent");
            if (l == 0L || l2 == 0L || l3 == 0L) {
                throw new IllegalStateException("Core OpenAL functions could not be found. Make sure that the OpenAL library has been loaded correctly.");
            }
            String string = MemoryUtil.memASCIISafe(JNI.invokeP(45058, l));
            if (string == null || JNI.invokeI(l2) != 0) {
                throw new IllegalStateException("There is no OpenAL context current in the current thread or process.");
            }
            APIUtil.APIVersion aPIVersion = APIUtil.apiParseVersion(string);
            int n = aPIVersion.major;
            int n2 = aPIVersion.minor;
            int[][] nArrayArray = new int[][]{{0, 1}};
            HashSet<String> hashSet = new HashSet<String>(32);
            for (int i = 1; i <= nArrayArray.length; ++i) {
                for (Object object2 : object = nArrayArray[i - 1]) {
                    if (i >= n && (i != n || object2 > n2)) continue;
                    hashSet.add("OpenAL" + i + (int)object2);
                }
            }
            String string2 = MemoryUtil.memASCIISafe(JNI.invokeP(45060, l));
            if (string2 != null) {
                object = MemoryStack.stackGet();
                Object object3 = new StringTokenizer(string2);
                while (((StringTokenizer)object3).hasMoreTokens()) {
                    String string3 = ((StringTokenizer)object3).nextToken();
                    MemoryStack memoryStack = ((MemoryStack)object).push();
                    Throwable throwable = null;
                    try {
                        if (!JNI.invokePZ(MemoryUtil.memAddress(memoryStack.ASCII(string3, false)), l3)) continue;
                        hashSet.add(string3);
                    } catch (Throwable throwable2) {
                        throwable = throwable2;
                        throw throwable2;
                    } finally {
                        if (memoryStack == null) continue;
                        if (throwable != null) {
                            try {
                                memoryStack.close();
                            } catch (Throwable throwable3) {
                                throwable.addSuppressed(throwable3);
                            }
                            continue;
                        }
                        memoryStack.close();
                    }
                }
            }
            if (aLCCapabilities.ALC_EXT_EFX) {
                hashSet.add("ALC_EXT_EFX");
            }
            aLCapabilities = new ALCapabilities(functionProvider, hashSet);
            object = aLCapabilities;
        } catch (Throwable throwable) {
            if (aLCCapabilities.ALC_EXT_thread_local_context && EXTThreadLocalContext.alcGetThreadContext() != 0L) {
                AL.setCurrentThread(aLCapabilities);
            } else {
                AL.setCurrentProcess(aLCapabilities);
            }
            throw throwable;
        }
        if (aLCCapabilities.ALC_EXT_thread_local_context && EXTThreadLocalContext.alcGetThreadContext() != 0L) {
            AL.setCurrentThread(aLCapabilities);
        } else {
            AL.setCurrentProcess(aLCapabilities);
        }
        return object;
    }

    static ALCapabilities getICD() {
        return ALC.check(icd.get());
    }

    static ICD access$102(ICD iCD) {
        icd = iCD;
        return icd;
    }

    static {
        capabilitiesTLS = new ThreadLocal();
        icd = new ICDStatic(null);
    }

    private static class ICDStatic
    implements ICD {
        @Nullable
        private static ALCapabilities tempCaps;

        private ICDStatic() {
        }

        @Override
        public void set(@Nullable ALCapabilities aLCapabilities) {
            if (tempCaps == null) {
                tempCaps = aLCapabilities;
            } else if (aLCapabilities != null && aLCapabilities != tempCaps && ThreadLocalUtil.areCapabilitiesDifferent(ICDStatic.tempCaps.addresses, aLCapabilities.addresses)) {
                APIUtil.apiLog("[WARNING] Incompatible context detected. Falling back to thread/process lookup for AL contexts.");
                AL.access$102(AL::getCapabilities);
            }
        }

        @Override
        @Nullable
        public ALCapabilities get() {
            return WriteOnce.caps;
        }

        ICDStatic(1 var1_1) {
            this();
        }

        static ALCapabilities access$200() {
            return tempCaps;
        }

        private static final class WriteOnce {
            @Nullable
            static final ALCapabilities caps = ICDStatic.access$200();

            private WriteOnce() {
            }

            static {
                if (caps == null) {
                    throw new IllegalStateException("No ALCapabilities instance has been set");
                }
            }
        }
    }

    private static interface ICD {
        default public void set(@Nullable ALCapabilities aLCapabilities) {
        }

        @Nullable
        public ALCapabilities get();
    }
}

