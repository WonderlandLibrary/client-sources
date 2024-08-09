/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.openal;

import javax.annotation.Nullable;
import org.lwjgl.openal.AL;
import org.lwjgl.openal.ALCapabilities;
import org.lwjgl.system.Checks;
import org.lwjgl.system.JNI;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;

public class SOFTSourceResampler {
    public static final int AL_NUM_RESAMPLERS_SOFT = 4624;
    public static final int AL_DEFAULT_RESAMPLER_SOFT = 4625;
    public static final int AL_SOURCE_RESAMPLER_SOFT = 4626;
    public static final int AL_RESAMPLER_NAME_SOFT = 4627;

    protected SOFTSourceResampler() {
        throw new UnsupportedOperationException();
    }

    static boolean isAvailable(ALCapabilities aLCapabilities) {
        return Checks.checkFunctions(aLCapabilities.alGetStringiSOFT);
    }

    public static long nalGetStringiSOFT(int n, int n2) {
        long l = AL.getICD().alGetStringiSOFT;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        return JNI.invokeP(n, n2, l);
    }

    @Nullable
    @NativeType(value="ALchar const *")
    public static String alGetStringiSOFT(@NativeType(value="ALenum") int n, @NativeType(value="ALsizei") int n2) {
        long l = SOFTSourceResampler.nalGetStringiSOFT(n, n2);
        return MemoryUtil.memUTF8Safe(l);
    }
}

