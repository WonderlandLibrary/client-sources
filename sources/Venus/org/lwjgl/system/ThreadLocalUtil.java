/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.system;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;
import org.lwjgl.BufferUtils;
import org.lwjgl.PointerBuffer;
import org.lwjgl.system.APIUtil;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.Pointer;
import org.lwjgl.system.jni.JNINativeInterface;

public final class ThreadLocalUtil {
    private static final long JNI_NATIVE_INTERFACE;
    private static final long FUNCTION_MISSING_ABORT;
    private static final long SIZE_OF_JNI_NATIVE_INTERFACE;

    private ThreadLocalUtil() {
    }

    private static native long getThreadJNIEnv();

    private static native void setThreadJNIEnv(long var0);

    private static native long getFunctionMissingAbort();

    public static void setEnv(long l, int n) {
        if (n < 0 || 3 < n) {
            throw new IndexOutOfBoundsException();
        }
        long l2 = ThreadLocalUtil.getThreadJNIEnv();
        if (l == 0L) {
            if (l2 != JNI_NATIVE_INTERFACE) {
                ThreadLocalUtil.setThreadJNIEnv(JNI_NATIVE_INTERFACE);
                MemoryUtil.nmemFree(l2);
            }
        } else {
            if (l2 == JNI_NATIVE_INTERFACE) {
                long l3 = MemoryUtil.nmemAllocChecked(SIZE_OF_JNI_NATIVE_INTERFACE);
                MemoryUtil.memCopy(l2, l3, SIZE_OF_JNI_NATIVE_INTERFACE);
                l2 = l3;
                ThreadLocalUtil.setThreadJNIEnv(l2);
            }
            MemoryUtil.memPutAddress(l2 + Integer.toUnsignedLong(n) * (long)Pointer.POINTER_SIZE, l);
        }
    }

    private static List<Field> getFieldsFromCapabilities(Class<?> clazz) {
        ArrayList<Field> arrayList = new ArrayList<Field>();
        for (Field field : clazz.getFields()) {
            if (field.getType() != Long.TYPE) continue;
            arrayList.add(field);
        }
        return arrayList;
    }

    public static void setFunctionMissingAddresses(@Nullable Class<?> clazz, int n) {
        if (clazz == null) {
            long l = MemoryUtil.memGetAddress(JNI_NATIVE_INTERFACE + Integer.toUnsignedLong(n) * (long)Pointer.POINTER_SIZE);
            if (l != 0L) {
                MemoryUtil.getAllocator().free(l);
                MemoryUtil.memPutAddress(JNI_NATIVE_INTERFACE + Integer.toUnsignedLong(n) * (long)Pointer.POINTER_SIZE, 0L);
            }
        } else {
            int n2 = ThreadLocalUtil.getFieldsFromCapabilities(clazz).size();
            long l = MemoryUtil.getAllocator().malloc(Integer.toUnsignedLong(n2) * (long)Pointer.POINTER_SIZE);
            for (int i = 0; i < n2; ++i) {
                MemoryUtil.memPutAddress(l + Integer.toUnsignedLong(i) * (long)Pointer.POINTER_SIZE, FUNCTION_MISSING_ABORT);
            }
            MemoryUtil.memPutAddress(JNI_NATIVE_INTERFACE + Integer.toUnsignedLong(n) * (long)Pointer.POINTER_SIZE, l);
        }
    }

    public static PointerBuffer getAddressesFromCapabilities(Object object) {
        List<Field> list = ThreadLocalUtil.getFieldsFromCapabilities(object.getClass());
        PointerBuffer pointerBuffer = BufferUtils.createPointerBuffer(list.size());
        try {
            for (int i = 0; i < list.size(); ++i) {
                long l = list.get(i).getLong(object);
                pointerBuffer.put(i, l != 0L ? l : FUNCTION_MISSING_ABORT);
            }
        } catch (IllegalAccessException illegalAccessException) {
            throw new RuntimeException(illegalAccessException);
        }
        return pointerBuffer;
    }

    public static boolean areCapabilitiesDifferent(PointerBuffer pointerBuffer, PointerBuffer pointerBuffer2) {
        for (int i = 0; i < pointerBuffer.remaining(); ++i) {
            if (pointerBuffer.get(i) == pointerBuffer2.get(i) || pointerBuffer2.get(i) == 0L) continue;
            return false;
        }
        return true;
    }

    static {
        int n;
        int n2;
        JNI_NATIVE_INTERFACE = ThreadLocalUtil.getThreadJNIEnv();
        FUNCTION_MISSING_ABORT = ThreadLocalUtil.getFunctionMissingAbort();
        int n3 = JNINativeInterface.GetVersion();
        switch (n3) {
            case 65537: {
                n2 = 12;
                break;
            }
            default: {
                n2 = 4;
            }
        }
        switch (n3) {
            case 65537: {
                n = 208;
                break;
            }
            case 65538: {
                n = 225;
                break;
            }
            case 65540: {
                n = 228;
                break;
            }
            case 65542: 
            case 65544: {
                n = 229;
                break;
            }
            case 589824: 
            case 655360: {
                n = 230;
                break;
            }
            default: {
                n = 230;
                APIUtil.DEBUG_STREAM.println("[LWJGL] [ThreadLocalUtil] Unsupported JNI version detected, this may result in a crash. Please inform LWJGL developers.");
            }
        }
        SIZE_OF_JNI_NATIVE_INTERFACE = Integer.toUnsignedLong(n2 + n) * (long)Pointer.POINTER_SIZE;
    }
}

