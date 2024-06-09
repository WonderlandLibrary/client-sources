/*
 * Decompiled with CFR 0_118.
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
        super("<profile>", "", ShaderOptionProfile.detectProfileName(profiles, options), ShaderOptionProfile.getProfileNames(profiles), ShaderOptionProfile.detectProfileName(profiles, options, true), null);
        this.profiles = profiles;
        this.options = options;
    }

    @Override
    public void nextValue() {
        super.nextValue();
        if (this.getValue().equals("<custom>")) {
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
            int i = 0;
            while (i < opts.length) {
                String name = opts[i];
                ShaderOption so = this.getOption(name);
                if (so != null) {
                    String val = prof.getValue(name);
                    so.setValue(val);
                }
                ++i;
            }
        }
    }

    private ShaderOption getOption(String name) {
        int i = 0;
        while (i < this.options.length) {
            ShaderOption so = this.options[i];
            if (so.getName().equals(name)) {
                return so;
            }
            ++i;
        }
        return null;
    }

    private ShaderProfile getProfile(String name) {
        int i = 0;
        while (i < this.profiles.length) {
            ShaderProfile prof = this.profiles[i];
            if (prof.getName().equals(name)) {
                return prof;
            }
            ++i;
        }
        return null;
    }

    @Override
    public String getNameText() {
        return Lang.get("of.shaders.profile");
    }

    @Override
    public String getValueText(String val) {
        return val.equals("<custom>") ? Lang.get("of.general.custom", "<custom>") : Shaders.translate("profile." + val, val);
    }

    @Override
    public String getValueColor(String val) {
        return val.equals("<custom>") ? "\u00a7c" : "\u00a7a";
    }

    private static String detectProfileName(ShaderProfile[] profs, ShaderOption[] opts) {
        return ShaderOptionProfile.detectProfileName(profs, opts, false);
    }

    private static String detectProfileName(ShaderProfile[] profs, ShaderOption[] opts, boolean def) {
        ShaderProfile prof = ShaderUtils.detectProfile(profs, opts, def);
        return prof == null ? "<custom>" : prof.getName();
    }

    private static String[] getProfileNames(ShaderProfile[] profs) {
        ArrayList<String> list = new ArrayList<String>();
        int names = 0;
        while (names < profs.length) {
            ShaderProfile prof = profs[names];
            list.add(prof.getName());
            ++names;
        }
        list.add("<custom>");
        String[] var4 = list.toArray(new String[list.size()]);
        return var4;
    }
}

