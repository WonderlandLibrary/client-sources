/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.system;

import java.util.Arrays;
import javax.annotation.Nullable;

final class StackWalkUtil {
    private StackWalkUtil() {
    }

    static StackTraceElement[] stackWalkArray(Object[] objectArray) {
        return (StackTraceElement[])objectArray;
    }

    static Object stackWalkGetMethod(Class<?> clazz) {
        StackTraceElement[] stackTraceElementArray = Thread.currentThread().getStackTrace();
        for (int i = 3; i < stackTraceElementArray.length; ++i) {
            if (stackTraceElementArray[i].getClassName().startsWith(clazz.getName())) continue;
            return stackTraceElementArray[i];
        }
        throw new IllegalStateException();
    }

    private static boolean isSameMethod(StackTraceElement stackTraceElement, StackTraceElement stackTraceElement2) {
        return StackWalkUtil.isSameMethod(stackTraceElement, stackTraceElement2, stackTraceElement2.getMethodName());
    }

    private static boolean isSameMethod(StackTraceElement stackTraceElement, StackTraceElement stackTraceElement2, String string) {
        return stackTraceElement.getMethodName().equals(string) && stackTraceElement.getClassName().equals(stackTraceElement2.getClassName()) && stackTraceElement.getFileName().equals(stackTraceElement2.getFileName());
    }

    private static boolean isAutoCloseable(StackTraceElement stackTraceElement, StackTraceElement stackTraceElement2) {
        if (StackWalkUtil.isSameMethod(stackTraceElement, stackTraceElement2, "$closeResource")) {
            return false;
        }
        return !"closeFinally".equals(stackTraceElement.getMethodName()) || !"AutoCloseable.kt".equals(stackTraceElement.getFileName());
    }

    @Nullable
    static Object stackWalkCheckPop(Class<?> clazz, Object object) {
        StackTraceElement[] stackTraceElementArray = Thread.currentThread().getStackTrace();
        for (int i = 3; i < stackTraceElementArray.length; ++i) {
            StackTraceElement stackTraceElement = stackTraceElementArray[i];
            if (stackTraceElement.getClassName().startsWith(clazz.getName())) continue;
            StackTraceElement stackTraceElement2 = (StackTraceElement)object;
            if (StackWalkUtil.isSameMethod(stackTraceElement, stackTraceElement2)) {
                return null;
            }
            if (StackWalkUtil.isAutoCloseable(stackTraceElement, stackTraceElement2) && i + 1 < stackTraceElementArray.length) {
                stackTraceElement = stackTraceElementArray[i + 1];
                if (StackWalkUtil.isSameMethod(stackTraceElement2, stackTraceElementArray[i + 1])) {
                    return null;
                }
            }
            return stackTraceElement;
        }
        throw new IllegalStateException();
    }

    static Object[] stackWalkGetTrace() {
        int n;
        StackTraceElement[] stackTraceElementArray = Thread.currentThread().getStackTrace();
        for (n = 3; n < stackTraceElementArray.length && stackTraceElementArray[n].getClassName().startsWith("org.lwjgl.system.Memory"); ++n) {
        }
        return Arrays.copyOfRange(stackTraceElementArray, n, stackTraceElementArray.length);
    }
}

