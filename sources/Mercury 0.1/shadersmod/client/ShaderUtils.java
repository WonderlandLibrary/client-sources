/*
 * Decompiled with CFR 0.145.
 */
package shadersmod.client;

import optifine.Config;
import shadersmod.client.ShaderOption;
import shadersmod.client.ShaderProfile;

public class ShaderUtils {
    public static ShaderOption getShaderOption(String name, ShaderOption[] opts) {
        if (opts == null) {
            return null;
        }
        for (int i2 = 0; i2 < opts.length; ++i2) {
            ShaderOption so2 = opts[i2];
            if (!so2.getName().equals(name)) continue;
            return so2;
        }
        return null;
    }

    public static ShaderProfile detectProfile(ShaderProfile[] profs, ShaderOption[] opts, boolean def) {
        if (profs == null) {
            return null;
        }
        for (int i2 = 0; i2 < profs.length; ++i2) {
            ShaderProfile prof = profs[i2];
            if (!ShaderUtils.matchProfile(prof, opts, def)) continue;
            return prof;
        }
        return null;
    }

    public static boolean matchProfile(ShaderProfile prof, ShaderOption[] opts, boolean def) {
        if (prof == null) {
            return false;
        }
        if (opts == null) {
            return false;
        }
        String[] optsProf = prof.getOptions();
        for (int p2 = 0; p2 < optsProf.length; ++p2) {
            String optVal;
            String profVal;
            String opt = optsProf[p2];
            ShaderOption so2 = ShaderUtils.getShaderOption(opt, opts);
            if (so2 == null || Config.equals(optVal = def ? so2.getValueDefault() : so2.getValue(), profVal = prof.getValue(opt))) continue;
            return false;
        }
        return true;
    }
}

