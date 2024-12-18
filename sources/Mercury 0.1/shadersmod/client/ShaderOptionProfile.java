/*
 * Decompiled with CFR 0.145.
 */
package shadersmod.client;

import java.util.ArrayList;
import optifine.Lang;
import shadersmod.client.ShaderOption;
import shadersmod.client.ShaderProfile;
import shadersmod.client.ShaderUtils;
import shadersmod.client.Shaders;

public class ShaderOptionProfile
extends ShaderOption {
    private ShaderProfile[] profiles = null;
    private ShaderOption[] options = null;
    private static final String NAME_PROFILE = "<profile>";
    private static final String VALUE_CUSTOM = "<custom>";

    public ShaderOptionProfile(ShaderProfile[] profiles, ShaderOption[] options) {
        super(NAME_PROFILE, "", ShaderOptionProfile.detectProfileName(profiles, options), ShaderOptionProfile.getProfileNames(profiles), ShaderOptionProfile.detectProfileName(profiles, options, true), null);
        this.profiles = profiles;
        this.options = options;
    }

    @Override
    public void nextValue() {
        super.nextValue();
        if (this.getValue().equals(VALUE_CUSTOM)) {
            super.nextValue();
        }
        this.applyProfileOptions();
    }

    public void updateProfile() {
        ShaderProfile prof = this.getProfile(this.getValue());
        if (prof == null || !ShaderUtils.matchProfile(prof, this.options, false)) {
            String val = ShaderOptionProfile.detectProfileName(this.profiles, this.options);
            this.setValue(val);
        }
    }

    private void applyProfileOptions() {
        ShaderProfile prof = this.getProfile(this.getValue());
        if (prof != null) {
            String[] opts = prof.getOptions();
            for (int i2 = 0; i2 < opts.length; ++i2) {
                String name = opts[i2];
                ShaderOption so2 = this.getOption(name);
                if (so2 == null) continue;
                String val = prof.getValue(name);
                so2.setValue(val);
            }
        }
    }

    private ShaderOption getOption(String name) {
        for (int i2 = 0; i2 < this.options.length; ++i2) {
            ShaderOption so2 = this.options[i2];
            if (!so2.getName().equals(name)) continue;
            return so2;
        }
        return null;
    }

    private ShaderProfile getProfile(String name) {
        for (int i2 = 0; i2 < this.profiles.length; ++i2) {
            ShaderProfile prof = this.profiles[i2];
            if (!prof.getName().equals(name)) continue;
            return prof;
        }
        return null;
    }

    @Override
    public String getNameText() {
        return Lang.get("of.shaders.profile");
    }

    @Override
    public String getValueText(String val) {
        return val.equals(VALUE_CUSTOM) ? Lang.get("of.general.custom", VALUE_CUSTOM) : Shaders.translate("profile." + val, val);
    }

    @Override
    public String getValueColor(String val) {
        return val.equals(VALUE_CUSTOM) ? "\u00a7c" : "\u00a7a";
    }

    private static String detectProfileName(ShaderProfile[] profs, ShaderOption[] opts) {
        return ShaderOptionProfile.detectProfileName(profs, opts, false);
    }

    private static String detectProfileName(ShaderProfile[] profs, ShaderOption[] opts, boolean def) {
        ShaderProfile prof = ShaderUtils.detectProfile(profs, opts, def);
        return prof == null ? VALUE_CUSTOM : prof.getName();
    }

    private static String[] getProfileNames(ShaderProfile[] profs) {
        ArrayList<String> list = new ArrayList<String>();
        for (int names = 0; names < profs.length; ++names) {
            ShaderProfile prof = profs[names];
            list.add(prof.getName());
        }
        list.add(VALUE_CUSTOM);
        String[] var4 = list.toArray(new String[list.size()]);
        return var4;
    }
}

