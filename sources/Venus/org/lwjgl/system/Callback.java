/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.system;

import java.lang.reflect.Method;
import javax.annotation.Nullable;
import org.lwjgl.PointerBuffer;
import org.lwjgl.system.CallbackI;
import org.lwjgl.system.Checks;
import org.lwjgl.system.Configuration;
import org.lwjgl.system.MemoryManage;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeResource;
import org.lwjgl.system.Platform;
import org.lwjgl.system.Pointer;
import org.lwjgl.system.dyncall.DynCallback;
import org.lwjgl.system.jni.JNINativeInterface;

public abstract class Callback
implements Pointer,
NativeResource {
    private static final boolean DEBUG_ALLOCATOR = Configuration.DEBUG_MEMORY_ALLOCATOR.get(false);
    private static final long VOID;
    private static final long BOOLEAN;
    private static final long BYTE;
    private static final long SHORT;
    private static final long INT;
    private static final long LONG;
    private static final long FLOAT;
    private static final long DOUBLE;
    private static final long PTR;
    private long address;

    protected Callback(String string) {
        this.address = Callback.create(string, this);
    }

    protected Callback(long l) {
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        this.address = l;
    }

    @Override
    public long address() {
        return this.address;
    }

    @Override
    public void free() {
        Callback.free(this.address());
    }

    private static native long getNativeCallbacks(Method[] var0, long var1);

    public static String __stdcall(String string) {
        return Platform.get() == Platform.WINDOWS && Pointer.BITS32 ? "_s" + string : string;
    }

    static long create(String string, Object object) {
        long l = Callback.getNativeFunction(string.charAt(string.length() - 1));
        long l2 = DynCallback.dcbNewCallback(string, l, JNINativeInterface.NewGlobalRef(object));
        if (l2 == 0L) {
            throw new IllegalStateException("Failed to create the DCCallback object");
        }
        if (DEBUG_ALLOCATOR) {
            MemoryManage.DebugAllocator.track(l2, 2L * (long)POINTER_SIZE);
        }
        return l2;
    }

    private static long getNativeFunction(char c) {
        switch (c) {
            case 'v': {
                return VOID;
            }
            case 'B': {
                return BOOLEAN;
            }
            case 'c': {
                return BYTE;
            }
            case 's': {
                return SHORT;
            }
            case 'i': {
                return INT;
            }
            case 'l': {
                return LONG;
            }
            case 'p': {
                return PTR;
            }
            case 'f': {
                return FLOAT;
            }
            case 'd': {
                return DOUBLE;
            }
        }
        throw new IllegalArgumentException();
    }

    public static <T extends CallbackI> T get(long l) {
        return (T)((CallbackI)MemoryUtil.memGlobalRefToObject(DynCallback.dcbGetUserData(l)));
    }

    @Nullable
    public static <T extends CallbackI> T getSafe(long l) {
        return l == 0L ? null : (T)Callback.get(l);
    }

    public static void free(long l) {
        JNINativeInterface.DeleteGlobalRef(DynCallback.dcbGetUserData(l));
        if (DEBUG_ALLOCATOR) {
            MemoryManage.DebugAllocator.untrack(l);
        }
        DynCallback.dcbFreeCallback(l);
    }

    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (!(object instanceof Callback)) {
            return true;
        }
        Callback callback = (Callback)object;
        return this.address == callback.address();
    }

    public int hashCode() {
        return (int)(this.address ^ this.address >>> 32);
    }

    public String toString() {
        return String.format("%s pointer [0x%X]", this.getClass().getSimpleName(), this.address);
    }

    static {
        try (MemoryStack memoryStack = MemoryStack.stackPush();){
            Class[] classArray = new Class[]{Long.TYPE};
            Method[] methodArray = new Method[]{CallbackI.V.class.getDeclaredMethod("callback", classArray), CallbackI.Z.class.getDeclaredMethod("callback", classArray), CallbackI.B.class.getDeclaredMethod("callback", classArray), CallbackI.S.class.getDeclaredMethod("callback", classArray), CallbackI.I.class.getDeclaredMethod("callback", classArray), CallbackI.J.class.getDeclaredMethod("callback", classArray), CallbackI.F.class.getDeclaredMethod("callback", classArray), CallbackI.D.class.getDeclaredMethod("callback", classArray), CallbackI.P.class.getDeclaredMethod("callback", classArray)};
            PointerBuffer pointerBuffer = memoryStack.mallocPointer(methodArray.length);
            Callback.getNativeCallbacks(methodArray, MemoryUtil.memAddress(pointerBuffer));
            VOID = pointerBuffer.get();
            BOOLEAN = pointerBuffer.get();
            BYTE = pointerBuffer.get();
            SHORT = pointerBuffer.get();
            INT = pointerBuffer.get();
            LONG = pointerBuffer.get();
            FLOAT = pointerBuffer.get();
            DOUBLE = pointerBuffer.get();
            PTR = pointerBuffer.get();
        } catch (Exception exception) {
            throw new IllegalStateException("Failed to initialize native callbacks.", exception);
        }
        MemoryUtil.getAllocator();
    }
}

