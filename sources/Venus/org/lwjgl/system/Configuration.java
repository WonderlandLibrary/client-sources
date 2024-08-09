/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.system;

import java.util.function.Function;
import javax.annotation.Nullable;

public class Configuration<T> {
    public static final Configuration<String> LIBRARY_PATH = new Configuration<String>("org.lwjgl.librarypath", StateInit.STRING);
    public static final Configuration<String> SHARED_LIBRARY_EXTRACT_DIRECTORY = new Configuration<String>("org.lwjgl.system.SharedLibraryExtractDirectory", StateInit.STRING);
    public static final Configuration<String> SHARED_LIBRARY_EXTRACT_PATH = new Configuration<String>("org.lwjgl.system.SharedLibraryExtractPath", StateInit.STRING);
    public static final Configuration<Boolean> EMULATE_SYSTEM_LOADLIBRARY = new Configuration<Boolean>("org.lwjgl.system.EmulateSystemLoadLibrary", StateInit.BOOLEAN);
    public static final Configuration<String> LIBRARY_NAME = new Configuration<String>("org.lwjgl.libname", StateInit.STRING);
    public static final Configuration<Object> MEMORY_ALLOCATOR = new Configuration<String>("org.lwjgl.system.allocator", StateInit.STRING);
    public static final Configuration<Integer> STACK_SIZE = new Configuration<Integer>("org.lwjgl.system.stackSize", StateInit.INT);
    public static final Configuration<Integer> ARRAY_TLC_SIZE = new Configuration<Integer>("org.lwjgl.system.arrayTLCSize", StateInit.INT);
    public static final Configuration<Boolean> DISABLE_CHECKS = new Configuration<Boolean>("org.lwjgl.util.NoChecks", StateInit.BOOLEAN);
    public static final Configuration<Boolean> DISABLE_FUNCTION_CHECKS = new Configuration<Boolean>("org.lwjgl.util.NoFunctionChecks", StateInit.BOOLEAN);
    public static final Configuration<Boolean> DEBUG = new Configuration<Boolean>("org.lwjgl.util.Debug", StateInit.BOOLEAN);
    public static final Configuration<Boolean> DEBUG_LOADER = new Configuration<Boolean>("org.lwjgl.util.DebugLoader", StateInit.BOOLEAN);
    public static final Configuration<Object> DEBUG_STREAM = new Configuration<String>("org.lwjgl.util.DebugStream", StateInit.STRING);
    public static final Configuration<Boolean> DEBUG_MEMORY_ALLOCATOR = new Configuration<Boolean>("org.lwjgl.util.DebugAllocator", StateInit.BOOLEAN);
    public static final Configuration<Boolean> DEBUG_MEMORY_ALLOCATOR_INTERNAL = new Configuration<Boolean>("org.lwjgl.util.DebugAllocator.internal", StateInit.BOOLEAN);
    public static final Configuration<Boolean> DEBUG_STACK = new Configuration<Boolean>("org.lwjgl.util.DebugStack", StateInit.BOOLEAN);
    public static final Configuration<Boolean> DEBUG_FUNCTIONS = new Configuration<Boolean>("org.lwjgl.util.DebugFunctions", StateInit.BOOLEAN);
    public static final Configuration<String> ASSIMP_LIBRARY_NAME = new Configuration<String>("org.lwjgl.assimp.libname", StateInit.STRING);
    public static final Configuration<String> BGFX_LIBRARY_NAME = new Configuration<String>("org.lwjgl.bgfx.libname", StateInit.STRING);
    public static final Configuration<String> CUDA_LIBRARY_NAME = new Configuration<String>("org.lwjgl.cuda.libname", StateInit.STRING);
    public static final Configuration<String> CUDA_TOOLKIT_VERSION = new Configuration<String>("org.lwjgl.cuda.toolkit.version", StateInit.STRING);
    public static final Configuration<String> CUDA_TOOLKIT_PATH = new Configuration<String>("org.lwjgl.cuda.toolkit.path", StateInit.STRING);
    public static final Configuration<String> CUDA_NVRTC_LIBRARY_NAME = new Configuration<String>("org.lwjgl.cuda.nvrtc.libname", StateInit.STRING);
    public static final Configuration<String> CUDA_NVRTC_BUILTINS_LIBRARY_NAME = new Configuration<String>("org.lwjgl.cuda.nvrtc-builtins.libname", StateInit.STRING);
    public static final Configuration<Boolean> CUDA_API_PER_THREAD_DEFAULT_STREAM = new Configuration<Boolean>("org.lwjgl.cuda.ptds", StateInit.BOOLEAN);
    public static final Configuration<Boolean> EGL_EXPLICIT_INIT = new Configuration<Boolean>("org.lwjgl.egl.explicitInit", StateInit.BOOLEAN);
    public static final Configuration<String> EGL_LIBRARY_NAME = new Configuration<String>("org.lwjgl.egl.libname", StateInit.STRING);
    public static final Configuration<String> GLFW_LIBRARY_NAME = new Configuration<String>("org.lwjgl.glfw.libname", StateInit.STRING);
    public static final Configuration<Boolean> GLFW_CHECK_THREAD0 = new Configuration<Boolean>("org.lwjgl.glfw.checkThread0", StateInit.BOOLEAN);
    public static final Configuration<String> JAWT_LIBRARY_NAME = new Configuration<String>("org.lwjgl.system.jawt.libname", StateInit.STRING);
    public static final Configuration<String> JEMALLOC_LIBRARY_NAME = new Configuration<String>("org.lwjgl.system.jemalloc.libname", StateInit.STRING);
    public static final Configuration<String> LLVM_LIBRARY_NAME = new Configuration<String>("org.lwjgl.llvm.libname", StateInit.STRING);
    public static final Configuration<String> LLVM_CLANG_LIBRARY_NAME = new Configuration<String>("org.lwjgl.llvm.clang.libname", StateInit.STRING);
    public static final Configuration<String> LLVM_LTO_LIBRARY_NAME = new Configuration<String>("org.lwjgl.llvm.lto.libname", StateInit.STRING);
    public static final Configuration<String> ODBC_LIBRARY_NAME = new Configuration<String>("org.lwjgl.odbc.libname", StateInit.STRING);
    public static final Configuration<Boolean> OPENAL_EXPLICIT_INIT = new Configuration<Boolean>("org.lwjgl.openal.explicitInit", StateInit.BOOLEAN);
    public static final Configuration<String> OPENAL_LIBRARY_NAME = new Configuration<String>("org.lwjgl.openal.libname", StateInit.STRING);
    public static final Configuration<Boolean> OPENCL_EXPLICIT_INIT = new Configuration<Boolean>("org.lwjgl.opencl.explicitInit", StateInit.BOOLEAN);
    public static final Configuration<String> OPENCL_LIBRARY_NAME = new Configuration<String>("org.lwjgl.opencl.libname", StateInit.STRING);
    public static final Configuration<Boolean> OPENGL_EXPLICIT_INIT = new Configuration<Boolean>("org.lwjgl.opengl.explicitInit", StateInit.BOOLEAN);
    public static final Configuration<String> OPENGL_LIBRARY_NAME = new Configuration<String>("org.lwjgl.opengl.libname", StateInit.STRING);
    public static final Configuration<Object> OPENGL_MAXVERSION = new Configuration<String>("org.lwjgl.opengl.maxVersion", StateInit.STRING);
    public static final Configuration<Boolean> OPENGLES_EXPLICIT_INIT = new Configuration<Boolean>("org.lwjgl.opengles.explicitInit", StateInit.BOOLEAN);
    public static final Configuration<String> OPENGLES_LIBRARY_NAME = new Configuration<String>("org.lwjgl.opengles.libname", StateInit.STRING);
    public static final Configuration<Object> OPENGLES_MAXVERSION = new Configuration<String>("org.lwjgl.opengles.maxVersion", StateInit.STRING);
    public static final Configuration<String> OPENVR_LIBRARY_NAME = new Configuration<String>("org.lwjgl.openvr.libname", StateInit.STRING);
    public static final Configuration<String> OPUS_LIBRARY_NAME = new Configuration<String>("org.lwjgl.opus.libname", StateInit.STRING);
    public static final Configuration<Boolean> VULKAN_EXPLICIT_INIT = new Configuration<Boolean>("org.lwjgl.vulkan.explicitInit", StateInit.BOOLEAN);
    public static final Configuration<String> VULKAN_LIBRARY_NAME = new Configuration<String>("org.lwjgl.vulkan.libname", StateInit.STRING);
    private final String property;
    @Nullable
    private T state;

    Configuration(String string, StateInit<? extends T> stateInit) {
        this.property = string;
        this.state = stateInit.apply(string);
    }

    public String getProperty() {
        return this.property;
    }

    public void set(@Nullable T t) {
        this.state = t;
    }

    @Nullable
    public T get() {
        return this.state;
    }

    public T get(T t) {
        T t2 = this.state;
        if (t2 == null) {
            t2 = t;
        }
        return t2;
    }

    private static interface StateInit<T>
    extends Function<String, T> {
        public static final StateInit<Boolean> BOOLEAN = StateInit::lambda$static$0;
        public static final StateInit<Integer> INT = Integer::getInteger;
        public static final StateInit<String> STRING = System::getProperty;

        private static Boolean lambda$static$0(String string) {
            String string2 = System.getProperty(string);
            return string2 == null ? null : Boolean.valueOf(Boolean.parseBoolean(string2));
        }
    }
}

