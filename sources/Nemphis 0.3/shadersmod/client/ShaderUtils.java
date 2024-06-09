/*
 * Decompiled with CFR 0_118.
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
        int i = 0;
        while (i < opts.length) {
            ShaderOption so = opts[i];
            if (so.getName().equals(name)) {
                return so;
            }
            ++i;
        }
        return null;
    }

    public static ShaderProfile detectProfile(ShaderProfile[] profs, ShaderOption[] opts, boolean def) {
        if (profs == null) {
            return null;
        }
        int i = 0;
        while (i < profs.length) {
            ShaderProfile prof = profs[i];
            if (ShaderUtils.matchProfile(prof, opts, def)) {
                return prof;
            }
            ++i;
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
        int p = 0;
        while (p < optsProf.length) {
            String optVal;
            String profVal;
            String opt = optsProf[p];
            ShaderOption so = ShaderUtils.getShaderOption(opt, opts);
            if (so != null && !Config.equals(optVal = def ? so.getValueDefault() : so.getValue(), profVal = prof.getValue(opt))) {
                return false;
            }
            ++p;
        }
        return true;
    }
}

