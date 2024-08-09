/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.openal;

import org.lwjgl.openal.ALC;
import org.lwjgl.openal.ALCCapabilities;
import org.lwjgl.system.Checks;
import org.lwjgl.system.JNI;
import org.lwjgl.system.NativeType;

public class EXTThreadLocalContext {
    protected EXTThreadLocalContext() {
        throw new UnsupportedOperationException();
    }

    static boolean isAvailable(ALCCapabilities aLCCapabilities) {
        return Checks.checkFunctions(aLCCapabilities.alcSetThreadContext, aLCCapabilities.alcGetThreadContext);
    }

    @NativeType(value="ALCboolean")
    public static boolean alcSetThreadContext(@NativeType(value="ALCcontext *") long l) {
        long l2 = ALC.getICD().alcSetThreadContext;
        if (Checks.CHECKS) {
            Checks.check(l2);
        }
        return JNI.invokePZ(l, l2);
    }

    @NativeType(value="ALCcontext *")
    public static long alcGetThreadContext() {
        long l = ALC.getICD().alcGetThreadContext;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        return JNI.invokeP(l);
    }
}

