/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.system.macosx;

import org.lwjgl.system.APIUtil;
import org.lwjgl.system.JNI;
import org.lwjgl.system.NativeType;
import org.lwjgl.system.macosx.LibSystem;

public class LibC {
    protected LibC() {
        throw new UnsupportedOperationException();
    }

    @NativeType(value="pid_t")
    public static long getpid() {
        long l = Functions.getpid;
        return JNI.invokeP(l);
    }

    public static final class Functions {
        public static final long getpid = APIUtil.apiGetFunctionAddress(LibSystem.getLibrary(), "getpid");

        private Functions() {
        }
    }
}

